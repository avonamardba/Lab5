package bigdata.labs.lab5;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import org.asynchttpclient.AsyncHttpClient;

public class Tester {
    private ActorMaterializer materializer;
    private ActorRef storage;
    private AsyncHttpClient httpClient;
    private int numOfRequests;

    public Tester(ActorMaterializer materializer, ActorSystem system, AsyncHttpClient httpClient) {
       this.materializer = materializer;
       this.storage = system.actorOf(StorageActor.props());
       this.httpClient = httpClient;
       this.numOfRequests = 
    }
}
