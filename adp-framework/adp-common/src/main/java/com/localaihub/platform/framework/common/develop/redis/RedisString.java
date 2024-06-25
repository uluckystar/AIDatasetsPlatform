package com.localaihub.platform.framework.common.develop.redis;

import redis.clients.jedis.Jedis;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:40
 */
public class RedisString {
    public RedisUtils redisUtils;

    public RedisString(RedisConfig redisConfig) {
        this.redisUtils = new RedisUtils(redisConfig);
    }

    public void set(String key, String value) {
        Jedis jedis = this.redisUtils.getJedis();
        jedis.set(key, value);
        jedis.close();
    }

    public String get(String s) {
        Jedis jedis = this.redisUtils.getJedis();
        String value = jedis.get(s);
        jedis.close();
        return value;
    }

    public void rm(String key) {
        Jedis jedis = this.redisUtils.getJedis();
        jedis.del(key);
        jedis.close();
    }
}
