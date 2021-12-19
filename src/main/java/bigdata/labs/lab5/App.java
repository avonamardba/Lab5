package bigdata.labs.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;
import scala.concurrent.Future;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.segment;
import static org.asynchttpclient.Dsl.asyncHttpClient;

public class App extends AllDirectives {
    private static final String ACTOR_SYSTEM_NAME = "routes";
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final AsyncHttpClient asyncHttpClient = asyncHttpClient();
        final Tester tester = new Tester(materializer, system, asyncHttpClient);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = tester.createRoute();
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOSTNAME, PORT),
                materializer);

        String startMessage = String.format("App started at http://%s:%d/\\", HOSTNAME, PORT);
        System.out.println(startMessage);
        System.in.read();
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
        try{
            asyncHttpClient.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}