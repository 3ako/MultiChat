package hw.zako.multichat.redis;

import io.lettuce.core.pubsub.RedisPubSubListener;

public abstract class RedisMessageListener implements RedisPubSubListener<String, String> {

    @Override
    public void message(String s, String k1, String s2) {

    }

    @Override
    public void subscribed(String s, long l) {

    }

    @Override
    public void psubscribed(String s, long l) {

    }

    @Override
    public void unsubscribed(String s, long l) {

    }

    @Override
    public void punsubscribed(String s, long l) {

    }
}
