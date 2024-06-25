package com.localaihub.platform.framework.common.develop.redis;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:40
 */
public class RedisConfig {
    public String host = "127.0.0.1";
    public int port = 6379;
    public int db = 2;
    public int timeout = 2000;

    public RedisConfig() {
    }

    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RedisConfig(String host, int port, int db) {
        this.host = host;
        this.port = port;
        this.db = db;
    }

    public RedisConfig(String host, int port, int db, int timeout) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.timeout = timeout;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDb() {
        return this.db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }
}