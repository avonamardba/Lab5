package bigdata.labs.lab4;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestActor extends AbstractActor {
    private static final String SCRIPT_ENGINE_NAME = "nashorn";
    private final ActorRef storageActor;

    TestActor(ActorRef storageActor) {
        this.storageActor = storageActor;
    }

    private String executeTest(TestMessage testMessage)
            throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new
                ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_NAME);
        engine.eval(testMessage.getParent().getJsScript());
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(testMessage.getParent().getFunctionName(),
                testMessage.getParams()).toString();
    }

    private TestMessage setResult(TestMessage testMessage)
            throws ScriptException, NoSuchMethodException {
        String actualResult = executeTest(testMessage);
        testMessage.setActualResult(actualResult);
        return testMessage;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMessage.class,
                        testMessage -> storageActor.tell(setResult(testMessage), ActorRef.noSender()))
                .build();
    }
}
