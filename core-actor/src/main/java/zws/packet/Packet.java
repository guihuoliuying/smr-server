package zws.packet;

public interface Packet {

    short id();

    int seqId();

    void exec();

}
