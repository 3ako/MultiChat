package hw.zako.multichat.redis.packet;

public interface RedisPacket {
    void read();
    void write();
}
