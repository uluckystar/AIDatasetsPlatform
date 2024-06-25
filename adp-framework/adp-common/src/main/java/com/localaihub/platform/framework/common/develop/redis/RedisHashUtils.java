package com.localaihub.platform.framework.common.develop.redis;

import com.alibaba.fastjson2.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:40
 */
public class RedisHashUtils {
    public JedisPool2 jedisPool2;

    public RedisHashUtils(RedisConfig redisConfig) {
        this.jedisPool2 = new JedisPool2(redisConfig);
    }

    public Object mSet(JSONObject hashJson) {
        return null;
    }

    public Object mGet() {
        return null;
    }

    public boolean hSet(String key, String hKey, JSONObject hValue) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var5 = null;

            boolean var6;
            try {
                jedis.hset(key, hKey, hValue.toJSONString());
                var6 = true;
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (jedis != null) {
                    if (var5 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var6;
        } catch (Exception var18) {
            var18.printStackTrace();
            return false;
        }
    }

    public boolean hSet(String key, String hKey, String hValue) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var5 = null;

            boolean var6;
            try {
                jedis.hset(key, hKey, hValue);
                var6 = true;
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (jedis != null) {
                    if (var5 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var6;
        } catch (Exception var18) {
            var18.printStackTrace();
            return false;
        }
    }

    public long hSetNx(String key, String hKey, JSONObject hValue) {
        Jedis jedis = this.jedisPool2.getJedis();
        long l = jedis.hsetnx(key, hKey, hValue.toJSONString());
        jedis.close();
        return l;
    }

    public Map<String, String> hGetAll(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        Map<String, String> h = jedis.hgetAll(key);
        jedis.close();
        return h;
    }

    public JSONObject hGet(String key, String hKey) {
        Jedis jedis = this.jedisPool2.getJedis();
        String h = jedis.hget(key, hKey);
        jedis.close();
        return JSONObject.parseObject(h);
    }

    public Object hExists(String key, String hKey) {
        return null;
    }

    public Object hHincrby() {
        return null;
    }

    public long hLen(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        long l = jedis.hlen(key);
        jedis.close();
        return l;
    }

    public long del(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        long l = jedis.del(key);
        jedis.close();
        return l;
    }

    public long hDel(String key, String hKey) {
        Jedis jedis = this.jedisPool2.getJedis();
        long l = jedis.hdel(key, new String[]{hKey});
        jedis.close();
        return l;
    }

    public long hDel(String key, String... hKeys) {
        Jedis jedis = this.jedisPool2.getJedis();
        long l = jedis.hdel(key, hKeys);
        jedis.close();
        return l;
    }

    public List<String> hValue(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        List<String> valueList = jedis.hvals(key);
        jedis.close();
        return valueList;
    }

    public Set<String> hKeys(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        Set<String> keyList = jedis.hkeys(key);
        jedis.close();
        return keyList;
    }
}
