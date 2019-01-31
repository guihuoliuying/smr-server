package zws.player;

import zws.actor.AbstractActor;
import zws.actor.Actor;
import zws.event.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

public class Player extends AbstractActor {

    private Map<String, Module> moduleMap = new LinkedHashMap<>();
    private EventBus eventBus;

    @Override
    public void onReceived(Object message, Actor sender) {
        if (message instanceof Packet) {
            // method.invoke(module, message);
        }
    }

    @SuppressWarnings("")
    <T> T module(String name) {
        return (T) moduleMap.get(name);
    }

}
