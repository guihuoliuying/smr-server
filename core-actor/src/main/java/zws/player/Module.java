package zws.player;

interface Module {

    default void onLoginLoadData() {}

    default void onLoginSelfInit() {}

    default void onLoginCompInit() {}

    default void onLoginSync() {}

    default void onLoginSucc() {}

    //

    default void onLogout() {}

    default void onRemoveFromCache() {}

}
