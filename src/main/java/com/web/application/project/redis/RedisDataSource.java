package com.web.application.project.redis;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;


/**
 * Redis连接池
 */
@Component
public class RedisDataSource {
    private static Logger logger = Logger.getLogger(RedisDataSource.class);

    @Resource
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis getResource() {
        ShardedJedis shardJedis = null;
        try {
            shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            logger.error("[JedisDS] getRedisClent error:" + e.getMessage());
            if (null != shardJedis)
                shardJedis.close();
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        shardedJedis.close();
    }

}
