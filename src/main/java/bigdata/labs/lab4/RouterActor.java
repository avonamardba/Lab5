package bigdata.labs.lab4;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

import java.time.Duration;
import java.util.Collections;

public class RouterActor extends AbstractActor {
    private static final int RETRIES_COUNT = 5;
    private static final int WORKERS_COUNT = 5;
    private ActorRef storageActor;
    private ActorRef testActor;
    private SupervisorStrategy strategy;

    RouterActor(ActorSystem system) {
        this.storageActor = system.actorOf(Props.create(StorageActor.class));
        this.strategy = new OneForOneStrategy(
                RETRIES_COUNT,
                Duration.ofMinutes(1),
                Collections.singletonList(Exception.class)
        );
        this.testActor = system.actorOf(
                new RoundRobinPool(WORKERS_COUNT)
                        .withSupervisorStrategy(strategy)
                        .props(Props.create(TestActor.class, storageActor))
        );
    }

    private void executeTests(TestPackage testPackage) {
        for (TestMessage testMessage : testPackage.getTests()) {
            testMessage.setParent(testPackage);
            testActor.tell(testMessage, ActorRef.noSender());
        }
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestPackage.class,
                        this::executeTests)
                .match(String.class,
                        message -> storageActor.forward(message, getContext()))
                .build();
    }
}