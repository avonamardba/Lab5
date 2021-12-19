package bigdata.labs.lab5;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class StorageActor extends AbstractActor {
    private HashMap<TestURL, Long> storage;

    public StorageActor() {
        this.storage = new TreeMap<>();
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
