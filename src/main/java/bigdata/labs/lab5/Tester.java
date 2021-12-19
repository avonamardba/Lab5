package bigdata.labs.lab5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import org.asynchttpclient.AsyncHttpClient;

public class Tester {
    private final ActorMaterializer materializer;
    private final ActorRef storage;
    private final AsyncHttpClient httpClient;
    private final int numOfRequests;
    private static final int NUM_OF_REQUESTS = 10;

    public Tester(ActorMaterializer materializer, ActorSystem system, AsyncHttpClient httpClient) {
       this.materializer = materializer;
       this.storage = system.actorOf(StorageActor.props());
       this.httpClient = httpClient;
       this.numOfRequests = NUM_OF_REQUESTS;
    }
}
