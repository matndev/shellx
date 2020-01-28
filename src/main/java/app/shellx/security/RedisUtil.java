/***
 * 
 * class RedisUtil copied from tutorial :
 * https://hellokoding.com/scalable-authentication-single-sign-on-out-sso-example-with-json-web-token-jwt-cookie-redis-java-spring-boot-freemarker/
 * Author : Giau Ngo
 * 
 */

package app.shellx.security;

import java.time.Duration;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum RedisUtil {
	   INSTANCE;

    private final JedisPool pool;
    private final JedisPoolConfig poolConfig = buildPoolConfig();

    RedisUtil() {
        pool = new JedisPool(poolConfig, "localhost");
    }
     
    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public void sadd(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void srem(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.srem(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.smembers(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }    
}
