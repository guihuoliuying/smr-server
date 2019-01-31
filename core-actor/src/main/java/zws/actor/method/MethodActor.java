package zws.actor.method;

import zws.actor.AbstractActor;
import zws.actor.Actor;
import zws.actor.ActorSystem;
import zws.actor.Greeting;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public abstract class MethodActor extends AbstractActor {

    protected MethodActor() {
    }

    @Override
    public void onReceived(Object message, Actor sender) {
        if (message instanceof MethodReq) {
            try {
                MethodReq req = (MethodReq) message;
                Method method = this.getClass().getMethod(req.getMethodName(), req.getParamTypeArray());
                method.invoke(this, req.getParamValueArray());
            } catch (Exception e) {

            }
        } else {
            onReceived0(message, sender);
        }
    }

    public void onReceived0(Object message, Actor sender) {

    }

    public static void main(String[] args) {

        class TestActor extends MethodActor implements Greeting {
            public void sayHello() {
                while (true) {
                    System.out.println("Hello");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {

                    }
                }
            }
        }

        ActorSystem system = new ActorSystem();
        TestActor actor = new TestActor();
        system.addActor("testActor", actor);

        Greeting greeting = (Greeting) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { Greeting.class }, new MethodActorInvocationHandler(actor));

        greeting.sayHello();

//        while (true) {
//            System.out.println("World");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//
//            }
//        }
    }
}
