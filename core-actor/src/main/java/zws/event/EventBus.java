package zws.event;

import zws.player.Player;

import java.util.*;
import java.util.function.BiConsumer;

public class EventBus {

    private Map<Class<? extends Event>, List<BiConsumer<Module, Event>>> listenerMap = new HashMap<>();
    private Queue<Event> queue = new LinkedList<>();
    private Player self;

    public EventBus(Player self) {
        this.self = self;
    }

    public void register(Class<? extends Event> clazz, BiConsumer<Module, Event> listener) {
        List<BiConsumer<Module, Event>> listenerList = listenerMap.get(clazz);
        if (listenerList == null) {
            listenerMap.put(clazz, (listenerList = new ArrayList<>()));
        }
        listenerList.add(listener);
    }

    public void fire(Event event) {
        queue.offer(event);
        if (queue.size() == 1) {
            Event e = null;
            int c = 0;
            while ((e = queue.poll()) != null) {
                List<BiConsumer<Module, Event>> listenerList = listenerMap.get(event.getClass());
                for (BiConsumer<Module, Event> l : listenerList) {
                    try {
//                    l.accept(module, e);
                    } catch (Throwable t) {
                        // do something
                    }
                }
                if (c++ > 128) {
                    System.err.println("event bus: there may be a loop in a event bus");
                    return;
                }
            }
        }
    }

    @OnEvent
    public void listenEvent(Module m, Event e) {
        System.out.println("hello hell");
    }

    public static class TestEvent implements Event { }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus(null);
        eventBus.register(TestEvent.class, (m, e) -> { System.out.println("hello world"); });
        eventBus.register(TestEvent.class, eventBus::listenEvent);

        eventBus.fire(new TestEvent());
    }

}
