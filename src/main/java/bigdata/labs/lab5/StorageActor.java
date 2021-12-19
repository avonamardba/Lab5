package bigdata.labs.lab5;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class StorageActor extends AbstractActor {
    private final HashMap<TestURL, Long> storage = new HashMap<>();

    public StorageActor() {
        this.storage = new TreeMap<m>();
    }

    public static Props props() {
        return Props.create(StorageActor.class);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMessage.class,
                        this::putTest)
                .match(String.class,
                        id -> sender().tell(makeResults(id), self()))
                .build();
    }
}
