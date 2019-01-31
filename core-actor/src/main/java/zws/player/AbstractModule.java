package zws.player;

public class AbstractModule implements Module {

    private Player self;

    protected Player self() { return self; }

    protected <T> T module(String name) { return self.module(name); }

}
