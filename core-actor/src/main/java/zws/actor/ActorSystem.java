package zws.actor;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.*;

@Component
public class ActorSystem {

    private ThreadPoolExecutor pool;
    private ConcurrentMap<String, AbstractActor> actors = new ConcurrentHashMap<>();

    public ActorSystem(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit) {
        pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());
        pool.prestartCoreThread();
    }

    public ActorSystem() {
        this(Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2,
                60, TimeUnit.SECONDS);
    }

    public Actor addActor(String name, Actor actor) {
        Objects.requireNonNull(actor, "actor can not be null");
        Objects.requireNonNull(name, "actor must have a name");
        if (!(actor instanceof AbstractActor)) {
            throw new IllegalArgumentException("actor must be an instance of AbstractActor");
        }
        AbstractActor newActor = (AbstractActor) actor;
        newActor.setName(name);
        Actor oldActor = actors.putIfAbsent(name, newActor);
        if (oldActor == null) {
            newActor.setSystem(this);
            return newActor;
        }
        return oldActor;
    }

    public Actor addActor(long id, Actor actor) {
        return addActor(Long.toString(id), actor);
    }

    public void removeActor(String name) {
        actors.remove(name);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor.getName());
    }

    public void removeActor(long id) {
        actors.remove(Long.toString(id));
    }

    public Actor getActor(String name) {
        return actors.get(name);
    }

    public Actor getActor(long id) {
        return actors.get(Long.toString(id));
    }

    public int size() {
        return actors.size();
    }

    void submit(Runnable task) {
        pool.submit(task);
    }

    public void shutdownNow() {
        pool.shutdownNow();
        actors.clear();
    }

    public ConcurrentMap<String, AbstractActor> getActors() {
        return actors;
    }

}
