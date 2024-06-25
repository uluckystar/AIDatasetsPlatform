package com.localaihub.platform.framework.common.develop.redis;

import redis.clients.jedis.Jedis;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:40
 */
public class RedisUtils {
    public Jedis jedis;

    public RedisUtils(RedisConfig redisConfig) {
        this.jedis = new Jedis(redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout());
        this.jedis.select(redisConfig.getDb());
    }

    public RedisUtils(String host) {
        this.jedis = new Jedis(host, 6379, 2000);
    }

    public RedisUtils(String host, int port) {
        this.jedis = new Jedis(host, port, 2000);
    }

    public Jedis getJedis() {
        return this.jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
