package com.localaihub.platform.framework.common.develop.redis;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:40
 */
public class RedisListUtils {
    public JedisPool2 jedisPool2;

    public RedisListUtils(RedisConfig redisConfig) {
        this.jedisPool2 = new JedisPool2(redisConfig);
    }

    public long lGetListSize(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        long l = jedis.llen(key);
        jedis.close();
        return l;
    }

    public Object lGet(String key, int start, int end) {
        Jedis jedis = this.jedisPool2.getJedis();
        Object o = jedis.lrange(key, (long)start, (long)end);
        jedis.close();
        return JSONArray.parseArray(String.valueOf(o));
    }

    public Object lGetAll(String key) {
        Jedis jedis = this.jedisPool2.getJedis();
        List<String> o = jedis.lrange(key, 0L, -1L);
        jedis.close();
        return JSONArray.parseArray(String.valueOf(o));
    }

    public Object lGetIndex(String key, long index) {
        Jedis jedis = this.jedisPool2.getJedis();
        Object o = jedis.lindex(key, index);
        jedis.close();
        return o;
    }

    public boolean rPush(String key, Object value) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var4 = null;

            boolean var5;
            try {
                jedis.rpush(key, new String[]{JSONObject.toJSONString(value)});
                var5 = true;
            } catch (Throwable var15) {
                var4 = var15;
                throw var15;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var14) {
                            var4.addSuppressed(var14);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var5;
        } catch (Exception var17) {
            var17.printStackTrace();
            return false;
        }
    }

    public boolean rPush(String key, Object value, long time) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            boolean var7;
            try {
                jedis.rpush(key, new String[]{JSONObject.toJSONString(value)});
                if (time > 0L) {
                    jedis.expire(key, time);
                }

                var7 = true;
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } catch (Exception var19) {
            var19.printStackTrace();
            return false;
        }
    }

    public boolean rPush(String key, List<Object> value) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var4 = null;

            try {
                Iterator var5 = value.iterator();

                while(var5.hasNext()) {
                    Object value1 = var5.next();
                    jedis.rpush(key, new String[]{JSONObject.toJSONString(value1)});
                }

                boolean var18 = true;
                return var18;
            } catch (Throwable var15) {
                var4 = var15;
                throw var15;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var14) {
                            var4.addSuppressed(var14);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
            return false;
        }
    }

    public boolean rPush(String key, List<Object> value, long time) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            try {
                Iterator var7 = value.iterator();

                while(var7.hasNext()) {
                    Object value1 = var7.next();
                    jedis.rpush(key, new String[]{JSONObject.toJSONString(value1)});
                }

                if (time > 0L) {
                    jedis.expire(key, time);
                }

                boolean var20 = true;
                return var20;
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }
        } catch (Exception var19) {
            var19.printStackTrace();
            return false;
        }
    }

    public boolean lPush(String key, Object value) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var4 = null;

            boolean var5;
            try {
                jedis.lpush(key, new String[]{JSONObject.toJSONString(value)});
                var5 = true;
            } catch (Throwable var15) {
                var4 = var15;
                throw var15;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var14) {
                            var4.addSuppressed(var14);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var5;
        } catch (Exception var17) {
            var17.printStackTrace();
            return false;
        }
    }

    public boolean lPush(String key, Object value, long time) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            boolean var7;
            try {
                jedis.lpush(key, new String[]{JSONObject.toJSONString(value)});
                if (time > 0L) {
                    jedis.expire(key, time);
                }

                var7 = true;
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } catch (Exception var19) {
            var19.printStackTrace();
            return false;
        }
    }

    public boolean lPush(String key, List<Object> value) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var4 = null;

            try {
                Iterator var5 = value.iterator();

                while(var5.hasNext()) {
                    Object value1 = var5.next();
                    jedis.lpush(key, new String[]{JSONObject.toJSONString(value1)});
                }

                boolean var18 = true;
                return var18;
            } catch (Throwable var15) {
                var4 = var15;
                throw var15;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var14) {
                            var4.addSuppressed(var14);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }
        } catch (Exception var17) {
            var17.printStackTrace();
            return false;
        }
    }

    public boolean lPush(String key, List<Object> value, long time) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            try {
                Iterator var7 = value.iterator();

                while(var7.hasNext()) {
                    Object value1 = var7.next();
                    jedis.lpush(key, new String[]{JSONObject.toJSONString(value1)});
                }

                if (time > 0L) {
                    jedis.expire(key, time);
                }

                boolean var20 = true;
                return var20;
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }
        } catch (Exception var19) {
            var19.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, List<Object> value, long time) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            try {
                jedis.del(key);
                Iterator var7 = value.iterator();

                while(var7.hasNext()) {
                    Object value1 = var7.next();
                    jedis.rpush(key, new String[]{JSONObject.toJSONString(value1)});
                }

                if (time > 0L) {
                    jedis.expire(key, time);
                }

                boolean var20 = true;
                return var20;
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }
        } catch (Exception var19) {
            var19.printStackTrace();
            return false;
        }
    }

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            boolean var7;
            try {
                jedis.lset(key, index, JSONObject.toJSONString(value));
                var7 = true;
            } catch (Throwable var17) {
                var6 = var17;
                throw var17;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var16) {
                            var6.addSuppressed(var16);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } catch (Exception var19) {
            var19.printStackTrace();
            return false;
        }
    }

    public long setRemove(String key, Object values) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var4 = null;

            long var5;
            try {
                var5 = jedis.lrem(key, 0L, JSONObject.toJSONString(values));
            } catch (Throwable var16) {
                var4 = var16;
                throw var16;
            } finally {
                if (jedis != null) {
                    if (var4 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var15) {
                            var4.addSuppressed(var15);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var5;
        } catch (Exception var18) {
            var18.printStackTrace();
            return 0L;
        }
    }

    public long lRemove(String key, long count, Object value) {
        try {
            Jedis jedis = this.jedisPool2.getJedis();
            Throwable var6 = null;

            long var7;
            try {
                var7 = jedis.lrem(key, count, JSONObject.toJSONString(value));
            } catch (Throwable var18) {
                var6 = var18;
                throw var18;
            } finally {
                if (jedis != null) {
                    if (var6 != null) {
                        try {
                            jedis.close();
                        } catch (Throwable var17) {
                            var6.addSuppressed(var17);
                        }
                    } else {
                        jedis.close();
                    }
                }

            }

            return var7;
        } catch (Exception var20) {
            var20.printStackTrace();
            return 0L;
        }
    }
}
