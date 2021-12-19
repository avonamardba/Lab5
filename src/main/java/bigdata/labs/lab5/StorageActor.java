package bigdata.labs.lab5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.TreeMap;

public class StorageActor extends AbstractActor {
    private final TreeMap<TestURL, Long> storage;

    public StorageActor() {
        this.storage = new TreeMap<>();
    }

    public static Props props() {
        return Props.create(StorageActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TestURL.class,
                        msg -> getSender().tell(new TestResult(msg, storage.get(msg)), ActorRef.noSender()))
                .match(TestResult.class,
                        msg -> storage.put(msg.getTest(), msg.getTime()))
                .build();
    }
}
