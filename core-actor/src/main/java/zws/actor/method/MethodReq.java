package zws.actor.method;

public class MethodReq {

    private final String methodName;
    private final Class[] paramTypeArray;
    private final Object[] paramValueArray;

    public MethodReq(String methodName, Class[] paramTypeArray, Object[] paramValueArray) {
        this.methodName = methodName;
        this.paramTypeArray = paramTypeArray;
        this.paramValueArray = paramValueArray;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParamTypeArray() {
        return paramTypeArray;
    }

    public Object[] getParamValueArray() {
        return paramValueArray;
    }
}
