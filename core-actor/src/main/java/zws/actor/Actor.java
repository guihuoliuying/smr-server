package zws.actor;

public interface Actor {

    String getName();

    void tell(Object message, Actor sender);

    void onReceived(Object message, Actor sender);

    void stop();

    void setDeadMessageHandler(DeadMessageHandler handler);

    Actor noSender = new Actor() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public void tell(Object message, Actor sender) {

        }

        @Override
        public void onReceived(Object message, Actor sender) {

        }

        @Override
        public void stop() {

        }

        @Override
        public void setDeadMessageHandler(DeadMessageHandler handler) {

        }
    };

}
