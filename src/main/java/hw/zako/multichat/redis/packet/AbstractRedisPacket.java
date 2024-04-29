package hw.zako.multichat.redis.packet;

import lombok.*;

import javax.annotation.Nullable;

public abstract class AbstractRedisPacket implements RedisPacket {
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PROTECTED)
    private String source;

    protected AbstractRedisPacket(@Nullable String source) {
        this.source = source;
        if (source != null) {
            read();
        }
    }
}
