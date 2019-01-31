package zws.actor;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractActor implements Actor {

    private static final int IDLE = 0;
    private static final int RUNNING = 1;
    private static final int STOP = 2;

    private String name;
    private ActorSystem system;
    private BlockingQueue<Mail> mailbox;
    private ActorTask task = new ActorTask();
    private DeadMessageHandler deadMessageHandler;
    private AtomicInteger state = new AtomicInteger(IDLE);

    private boolean isDone = false;
    private final Object stopLock = new Object();
    private volatile Thread currThread = null;

    public AbstractActor(int queueSize) {
        this.mailbox = new ArrayBlockingQueue<>(queueSize);
    }

    public AbstractActor(BlockingQueue<AbstractActor.Mail> queue) {
        this.mailbox = queue;
    }

    public AbstractActor() {
        this(512);
    }

    @Override
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    void setSystem(ActorSystem system) {
        Objects.requireNonNull(system, "");
        if (this.system != null) {
            throw new IllegalStateException();
        }
        this.system = system;
    }

    @Override
    public void tell(Object message, Actor sender) {
        if (state.get() == STOP) {
            throw new IllegalStateException();
        }
        sender = (sender == null) ? Actor.noSender : sender;
        Mail mail = new Mail(message, sender);
        if (mailbox.offer(mail)) {
            if (state.compareAndSet(IDLE, RUNNING)) {
                system.submit(task);
            } else if (state.get() == STOP) {
                synchronized (stopLock) {
                    if (isDone && mailbox.contains(mail)) {
                        throw new IllegalStateException();
                    }
                }
            }
        } else {
            throw new IllegalStateException("Actor's mailbox is full. actor=" + name);
        }
    }

    @Override
    public void stop() {
        if (currThread != Thread.currentThread()) {

        }
        state.set(STOP);
        synchronized (stopLock) {
            isDone = true;
            if (deadMessageHandler != null) {
                Mail mail = null;
                while ((mail = mailbox.poll()) != null) {
                    try {

                    } catch (Throwable t) {

                    }
                }
            }
            system.removeActor(this);
        }
    }

    @Override
    public void setDeadMessageHandler(DeadMessageHandler handler) {
        if (this.system == null && this.deadMessageHandler == null) {
            this.deadMessageHandler = handler;
        }
    }

    private class ActorTask implements Runnable {
        @Override
        public void run() {
            try {
                currThread = Thread.currentThread();
                Mail mail = null;
                while ((mail = mailbox.poll()) != null) {
                    try {
                        if (currThread != Thread.currentThread()) {

                        }
                        onReceived(mail.message, mail.sender);
                        if (isDone) {
                            break;
                        }
                    } catch (Throwable cause) {

                    }
                }
                currThread = null;
                if (state.compareAndSet(RUNNING, IDLE)) {
                    if (mailbox.peek() != null && state.compareAndSet(IDLE, RUNNING)) {
                        try {
                            system.submit(task);
                        } catch (Throwable cause) {
                            state.compareAndSet(RUNNING, IDLE);
                        }
                    }
                }
            } catch (Throwable cause) {

            }
        }
    }

    public class Mail {
        private Object message;
        private Actor sender;

        public Mail(Object message, Actor sender) {
            this.message = message;
            this.sender = sender;
        }
    }

    public static void main(String[] args) {
        ActorSystem system = new ActorSystem();
        Actor actor = new AbstractActor() {
            @Override
            public void onReceived(Object message, Actor sender) {
                System.out.println("hello world, " + getName());
            }
        };
        system.addActor("AA", actor);
//        system.addActor("BB", actor);

        actor.tell(new Object(), null);
    }
}
