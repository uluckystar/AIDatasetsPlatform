package com.localaihub.platform.framework.common.develop.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:38
 */
public class JedisPool2 {
    private String host = "192.168.1.236";
    private Integer port = 6379;
    private Integer db = 0;
    private Integer timeout = 2000;
    private final JedisPool jedisPool;

    public JedisPool2() {
        JedisPoolConfig jedisPoolConfig = this.getJedisConfig();

        try {
            this.jedisPool = new JedisPool(jedisPoolConfig, this.host, this.port, this.timeout);
        } catch (Exception var6) {
            throw new RuntimeException();
        } finally {
            ;
        }
    }

    public JedisPool2(RedisConfig redisConfig) {
        this.host = redisConfig.getHost();
        this.port = redisConfig.getPort();
        this.db = redisConfig.getDb();
        this.timeout = redisConfig.getTimeout();
        JedisPoolConfig jedisPoolConfig = this.getJedisConfig();

        try {
            this.jedisPool = new JedisPool(jedisPoolConfig, this.host, this.port, this.timeout);
        } catch (Exception var7) {
            throw new RuntimeException();
        } finally {
            ;
        }
    }

    public JedisPoolConfig getJedisConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxTotal(36);
        return jedisPoolConfig;
    }

    public Jedis getJedis() {
        Jedis jedis = this.jedisPool.getResource();
        jedis.select(this.db);
        return jedis;
    }

    protected void finalize() throws Throwable {
        this.jedisPool.close();
        super.finalize();
    }
}
