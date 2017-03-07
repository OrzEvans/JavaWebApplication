package com.web.application.project.redis;


import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 使用第三方缓存服务器，处理二级缓存
 */
public class RedisCache implements Cache {

    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final long CACHE_LIVE_TIME = 60 * 2;

    private String name;

    private  RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCache() {
    }

    public RedisCache(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public void putObject(Object key, Object value) {
        final String keyf = key.toString();
        final Object valuef = value;
        try {
            redisTemplate.execute(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();
                    byte[] keyb = serializer.serialize(keyf);
                    connection.set(keyb, serializer.serialize(valuef));
                    connection.expire(keyb, CACHE_LIVE_TIME);
                    return 1L;
                }

            });
        } catch (Exception e) {
            logger.error("",e);
        }
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        try {
            final String keyf = key.toString();
            result = redisTemplate.execute(new RedisCallback<Object>() {

                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();
                    Object value = serializer.deserialize(connection.get(serializer.serialize(keyf)));
                    return value;
                }

            });
        } catch (Exception e) {
            logger.error("",e);
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        Object result = null;
        try {
            final Object keyf = key;

            result = redisTemplate.execute(new RedisCallback<Object>() {

                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();
                    byte[] keyb = serializer.serialize(keyf);
                    Long l = connection.del(keyb.toString().getBytes());
                    return l;
                }

            });
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    @Override
    public void clear() {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                connection.flushAll();
                return Boolean.TRUE;
            }
        });
    }

    @Override
    public int getSize() {
        return (int) redisTemplate.execute(new RedisCallback<Integer>() {
            @Override
            public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize().intValue();
            }

        });
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}