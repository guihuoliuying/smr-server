package zws.actor.method;

import zws.actor.Actor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodActorInvocationHandler implements InvocationHandler {

    private MethodActor actor;

    public MethodActorInvocationHandler(MethodActor actor) {
        this.actor = actor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String name = method.getName();
        Class[] paramTypeArray = method.getParameterTypes();
        MethodReq req = new MethodReq(name, paramTypeArray, args);
        actor.tell(req, Actor.noSender);
        return null;
    }
}
