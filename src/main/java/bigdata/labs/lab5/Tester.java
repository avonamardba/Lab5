package bigdata.labs.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.*;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Tester {
    private final ActorMaterializer materializer;
    private final ActorRef storage;
    private final AsyncHttpClient httpClient;
    private final int numOfRequests;
    private static final int NUM_OF_REQUESTS = 10;
    private static final String COUNT = "count";
    private static final String URL = "testUrl";

    public Tester(ActorMaterializer materializer, ActorSystem system, AsyncHttpClient httpClient) {
        this.materializer = materializer;
        this.storage = system.actorOf(StorageActor.props());
        this.httpClient = httpClient;
        this.numOfRequests = NUM_OF_REQUESTS;
    }

    public TestURL parseRequest(HttpRequest request) {
        Query q = request.getUri().query();
        Optional<String> testUrl = q.get(URL);
        Optional<String> count = q.get(COUNT);
        return new TestURL(testUrl.orElse(null), Integer.parseInt(count.orElse(null)));
    }

    private CompletionStage<TestResult> executeTest(TestURL test) {
        Sink<TestURL, CompletionStage<Long>> testSink;
        testSink = Flow.of(TestURL.class)
                .mapConcat(t -> Collections.nCopies(t.getCount(), t.getUrl()))
                .mapAsync(numOfRequests, url -> {
                    Instant requestStart = Instant.now();
                    return httpClient.prepareGet(url).execute()
                            .toCompletableFuture()
                            .thenCompose(response -> CompletableFuture.completedFuture(
                                    Duration.between(requestStart, Instant.now()).getSeconds())
                            );
                })
                .toMat(Sink.fold(0L, Long::sum), Keep.right());
        return Source.from(Collections.singleton(test))
                .toMat(testSink, Keep.right())
                .run(materializer)
                .thenApply(sum -> new TestResult(test, sum / test.getCount()));
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> createRoute() {
        return Flow.of(HttpRequest.class)
                .map(this::parseRequest)
                .mapAsync(numOfRequests,
                        test -> Patterns.ask(storage, test, Duration.ofSeconds(5))
                                .thenApply(r -> (TestResult) r)
                                .thenCompose(result -> result.get().isPresent() ?
                                        CompletableFuture.completedFuture(result.get().get()) : executeTest(test)))
                .map(res -> {
                    storage.tell(res, ActorRef.noSender());
                    return HttpResponse.create()
                            .withStatus(StatusCodes.OK)
                            .withEntity(ContentTypes.APPLICATION_JSON,
                                    ByteString.fromString(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(res)));
                });
    }

    public ActorMaterializer getMaterializer() {
        return materializer;
    }

    public ActorRef getStorage() {
        return storage;
    }

    public AsyncHttpClient getHttpClient() {
        return httpClient;
    }

    public int getNumOfRequests() {
        return numOfRequests;
    }
}