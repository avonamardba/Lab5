package bigdata.labs.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;

import java.util.Optional;

public class Tester {
    private final ActorMaterializer materializer;
    private final ActorRef storage;
    private final AsyncHttpClient httpClient;
    private final int numOfRequests;
    private static final int NUM_OF_REQUESTS = 10;
    private static final String COUNT = "count";

    public Tester(ActorMaterializer materializer, ActorSystem system, AsyncHttpClient httpClient) {
       this.materializer = materializer;
       this.storage = system.actorOf(StorageActor.props());
       this.httpClient = httpClient;
       this.numOfRequests = NUM_OF_REQUESTS;
    }

    public TestURL parseRequest(HttpRequest request) {
        Query q = request.getUri().query();
        Optional<String> testUrl = q.get()
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> createRoute() {
        return Flow.of(HttpRequest.class)
                .map(this::)
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
