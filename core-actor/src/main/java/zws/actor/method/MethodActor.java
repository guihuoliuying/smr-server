package zws.actor.method;

import zws.actor.AbstractActor;
import zws.actor.Actor;

import java.lang.reflect.Method;

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

    protected void onReceived0(Object message, Actor sender) {

    }

}
