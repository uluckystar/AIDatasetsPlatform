package com.localaihub.platform.framework.common.config;

import com.localaihub.platform.framework.common.develop.redis.RedisConfig;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:56
 */
public  class userRedis {


    /**
     * 验证码、用户信息
     *
     */
    public static RedisConfig UserRedisConfig11 = new RedisConfig("127.0.0.1", 6379, 11);

    /**
     * 按时查询提现批次处理结果
     *
     */
    public static RedisConfig UserRedisConfig6 = new RedisConfig("127.0.0.1", 6379, 12);

    /**
     * access_token  ticket
     *
     */
    public static RedisConfig UserRedisConfig0 = new RedisConfig("127.0.0.1", 6379, 13);

}
