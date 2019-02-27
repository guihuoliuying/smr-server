package zws.player;

import zws.packet.Packet;

abstract public class PlayerPacket implements Packet {

    private int seqId;

    private Player player;

    public void seqId(int seqId) {
        this.seqId = seqId;
    }

    public int seqId() {
        return seqId;
    }

    void player(Player player) {
        this.player = player;
    }

    Player player() {
        return player;
    }

    <T extends Module> T module(String moduleName) {
        return player.module(moduleName);
    }

}
