package zws.player;

import zws.actor.AbstractActor;
import zws.actor.Actor;
import zws.event.Event;
import zws.event.EventBus;
import zws.module.Module;
import zws.packet.Packet;

import java.util.LinkedHashMap;
import java.util.Map;

public class Player extends AbstractActor {

    private Map<String, Module> moduleMap = new LinkedHashMap<>();
    private EventBus eventBus;

    @Override
    public void onReceived(Object message, Actor sender) {
        if (message instanceof PlayerPacket) {
            PlayerPacket pkt = (PlayerPacket) message;
            pkt.player(this);
            try {
                pkt.exec();
            } catch (Throwable t) {

            }
        }
    }

    @SuppressWarnings("")
    <T> T module(String name) {
        return (T) moduleMap.get(name);
    }

    void fire(Event event) {
        eventBus.fire(event);
    }

}
