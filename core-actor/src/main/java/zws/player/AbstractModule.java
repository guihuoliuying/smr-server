package zws.player;

import zws.event.Event;
import zws.module.Module;

public class AbstractModule implements Module {

    private Player self;

    protected Player self() { return self; }

    protected <T> T module(String name) { return self.module(name); }

    protected void fire(Event event) { self.fire(event); }



}
