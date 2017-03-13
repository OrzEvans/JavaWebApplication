package com.web.application.project.redis;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis工具类
 */
@Component
public class RedisUtil {

    private static Logger logger = Logger.getLogger(RedisUtil.class);

    @Resource
    private RedisDataSource redisDataSource;

    /**
     * <p>通过key获取储存在redis中的value</p>
     * <p>并释放连接</p>
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key){
        ShardedJedis shardedJedis = null;
        String value = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            value = shardedJedis.get(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return value;
    }

    /**
     * <p>向redis存入key和value,并释放连接资源</p>
     * <p>如果key已经存在 则覆盖</p>
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key,String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String result=null;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.set(key, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return result;
    }


    /**
     * <p>删除指定的key</p>
     * @param key 一个key
     * @return 返回删除成功的个数
     */
    public Long del(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.del(key);
        }catch (Exception e) {
            broken=true;
            e.printStackTrace();
            return 0L;
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }

    /**
     * <p>通过key向指定的value值追加值</p>
     * @param key
     * @param str
     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度  异常返回0L
     */
    public Long append(String key ,String str){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.append(key, str);
        } catch (Exception e) {
            broken=true;
            return 0L;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return res;
    }

    /**
     * <p>判断key是否存在</p>
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.exists(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
            return false;
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }

    /**
     * <p>设置key value,如果key已经存在则返回0,nx==> not exist</p>
     * @param key
     * @param value
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long setnx(String key ,String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.setnx(key, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
            return 0L;
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }

    /**
     * <p>设置key value并制定这个键值的有效期</p>
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key,String value,int seconds){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String result=null;
        try {
            shardedJedis = redisDataSource.getResource();
            result = shardedJedis.setex(key, seconds, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return result;
    }


    /**
     * <p>通过key 和offset 从指定的位置开始将原先value替换</p>
     * <p>下标从0开始,offset表示从offset下标开始替换</p>
     * <p>如果替换的字符串长度过小则会这样</p>
     * <p>example:</p>
     * <p>value : bigsea@zto.cn</p>
     * <p>str : abc </p>
     * <P>从下标7开始替换  则结果为</p>
     * <p>RES : bigsea.abc.cn</p>
     * @param key
     * @param str
     * @param offset 下标位置
     * @return 返回替换后  value 的长度
     */
    public Long setrange(String key,String str,int offset){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.setrange(key, offset, str);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
            return 0L;
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }

    /**
     * <p>设置key的值,并返回一个旧值</p>
     * @param key
     * @param value
     * @return 旧值 如果key不存在 则返回null
     */
    public String getset(String key,String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.getSet(key, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过下标 和key 获取指定下标位置的 value</p>
     * @param key
     * @param startOffset 开始位置 从0 开始 负数表示从右边开始截取
     * @param endOffset
     * @return 如果没有返回null
     */
    public String getrange(String key, int startOffset ,int endOffset){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1</p>
     * @param key
     * @return 加值后的结果
     */
    public Long incr(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.incr(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key给指定的value加值,如果key不存在,则这是value为该值</p>
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key,Long integer){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.incrBy(key, integer);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>对key的值做减减操作,如果key不存在,则设置key为-1</p>
     * @param key
     * @return
     */
    public Long decr(String key) {
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.decr(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>减去指定的值</p>
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key,Long integer){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.decrBy(key, integer);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取value值的长度</p>
     * @param key
     * @return 失败返回null
     */
    public Long serlen(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.strlen(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在,则先创建</p>
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public Long hset(String key,String field,String value) {
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hset(key, field, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0</p>
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key,String field,String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hsetnx(key, field, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key同时设置 hash的多个field</p>
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    public String hmset(String key,Map<String, String> hash){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hmset(key, hash);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key 和 field 获取指定的 value</p>
     * @param key
     * @param field
     * @return 没有返回null
     */
    public String hget(String key, String field){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hget(key, field);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key 和 fields 获取指定的value 如果没有对应的value则返回null</p>
     * @param key
     * @param fields 可以使 一个String 也可以是 String数组
     * @return
     */
    public List<String> hmget(String key,String...fields){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        List<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hmget(key, fields);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key给指定的field的value加上给定的值</p>
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrby(String key ,String field ,Long value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hincrBy(key, field, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key和field判断是否有指定的value存在</p>
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key , String field){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Boolean res = false;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hexists(key, field);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回field的数量</p>
     * @param key
     * @return
     */
    public Long hlen(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hlen(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;

    }

    /**
     * <p>通过key 删除指定的 field </p>
     * @param key
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return
     */
    public Long hdel(String key ,String...fields){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();      
            res = shardedJedis.hdel(key, fields);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回所有的field</p>
     * @param key
     * @return
     */
    public Set<String> hkeys(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Set<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hkeys(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回所有和key有关的value</p>
     * @param key
     * @return
     */
    public List<String> hvals(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        List<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hvals(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取所有的field和value</p>
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Map<String, String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hgetAll(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key向list头部添加字符串</p>
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long lpush(String key ,String...strs){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.lpush(key, strs);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key向list尾部添加字符串</p>
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long rpush(String key ,String...strs){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.rpush(key, strs);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key在list指定的位置之前或者之后 添加字符串元素</p>
     * @param key
     * @param where LIST_POSITION枚举类型
     * @param pivot list里面的value
     * @param value 添加的value
     * @return
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where,
                        String pivot, String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.linsert(key, where, pivot, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key设置list指定下标位置的value</p>
     * <p>如果下标超过list里面value的个数则报错</p>
     * @param key
     * @param index 从0开始
     * @param value
     * @return 成功返回OK
     */
    public String lset(String key ,Long index, String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.lset(key, index, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key从对应的list中删除指定的count个 和 value相同的元素</p>
     * @param key
     * @param count 当count为0时删除全部
     * @param value
     * @return 返回被删除的个数
     */
    public Long lrem(String key,long count,String value){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.lrem(key, count, value);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key保留list中从strat下标开始到end下标结束的value值</p>
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    public String ltrim(String key ,long start ,long end){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.ltrim(key, start, end);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key从list的头部删除一个value,并返回该value</p>
     * @param key
     * @return
     */
    public String lpop(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.lpop(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key从list尾部删除一个value,并返回该元素</p>
     * @param key
     * @return
     */
    public String rpop(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.rpop(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取list中指定下标位置的value</p>
     * @param key
     * @param index
     * @return 如果没有返回null
     */
    public String lindex(String key,long index){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.lindex(key, index);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回list的长度</p>
     * @param key
     * @return
     */
    public Long llen(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.llen(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取list指定下标位置的value</p>
     * <p>如果start 为 0 end 为 -1 则返回全部的list中的value</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key,long start,long end){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        List<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.lrange(key, start, end);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key向指定的set中添加value</p>
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    public Long sadd(String key,String...members){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.sadd(key, members);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key删除set中对应的value值</p>
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    public Long srem(String key,String...members){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.srem(key, members);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key随机删除一个set中的value并返回该值</p>
     * @param key
     * @return
     */
    public String spop(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.spop(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取set中value的个数</p>
     * @param key
     * @return
     */
    public Long scard(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.scard(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key判断value是否是set中的元素</p>
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key,String member){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Boolean res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.sismember(key, member);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取set中随机的value,不删除元素</p>
     * @param key
     * @return
     */
    public String srandmember(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.srandmember(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取set中所有的value</p>
     * @param key
     * @return
     */
    public Set<String> smembers(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Set<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.smembers(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key向zset中添加value,score,其中score就是用来排序的</p>
     * <p>如果该value已经存在则根据score更新元素</p>
     * @param key
     * @param scoreMembers
     * @return
     */
    public Long zadd(String key,Map<String, Double> scoreMembers){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key向zset中添加value,score,其中score就是用来排序的</p>
     * <p>如果该value已经存在则根据score更新元素</p>
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key,String member,double score){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zadd(key, score, member);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key删除在zset中指定的value</p>
     * @param key
     * @param members 可以使一个string 也可以是一个string数组
     * @return
     */
    public Long zrem(String key,String...members){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zrem(key, members);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key增加该zset中value的score的值</p>
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key ,double score ,String member){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Double res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zincrby(key, score, member);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回zset中value的排名</p>
     * <p>下标从小到大排序</p>
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key,String member){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zrank(key, member);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回zset中value的排名</p>
     * <p>下标从大到小排序</p>
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key,String member){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zrevrank(key, member);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key将获取score从start到end中zset的value</p>
     * <p>socre从大到小排序</p>
     * <p>当start为0 end为-1时返回全部</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key ,long start ,long end){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Set<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zrevrange(key, start, end);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrangebyscore(String key,String max,String min){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Set<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrangeByScore(String key ,double max,double min){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Set<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zrevrangeByScore(key,max,min);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>返回指定区间内zset中value的数量</p>
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key,String min,String max){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zcount(key, min, max);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key返回zset中的value个数</p>
     * @param key
     * @return
     */
    public Long zcard(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zcard(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key获取zset中value的score值</p>
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key,String member){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Double res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zscore(key, member);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key删除给定区间内的元素</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(String key ,long start, long end){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key删除指定score内的元素</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(String key,double start,double end){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Long res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }
    /**
     * <p>返回满足pattern表达式的所有key</p>
     * <p>keys(*)</p>
     * <p>返回所有的key</p>
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        Set<String> res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.hkeys(pattern);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }

    /**
     * <p>通过key判断值得类型</p>
     * @param key
     * @return
     */
    public String type(String key){
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        String res = null;
        try {
            shardedJedis = redisDataSource.getResource();
            res = shardedJedis.type(key);
        } catch (Exception e) {
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return res;
    }
    /**
     * 获取redis键值-object
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            byte[] bytes = shardedJedis.get(key.getBytes());
            if(!StringUtils.isEmpty(bytes)) {
                return SerializeUtil.unserialize(bytes);
            }
        } catch (Exception e) {
            logger.error("getObject获取redis键值异常:key=" + key + " cause:" + e.getMessage());
            broken=true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return null;
    }

    /**
     * 设置key-object
     * @param key
     * @param value
     * @return
     */
    public String setObject(String key, Object value) {
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.set(key.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception e) {
            logger.error("setObject设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
            broken=true;
            return null;
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }

    /**
     * 设置key-object并指定有效期
     * @param key
     * @param value
     * @param expireime 有效期(秒)
     * @return
     */
    public String setObject(String key, Object value,int expireime) {
        String result = null;
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            result = shardedJedis.set(key.getBytes(), SerializeUtil.serialize(value));
            if(result!=null&&result.equals("OK")) {
                shardedJedis.expire(key.getBytes(), expireime);
            }
            return result;
        } catch (Exception e) {
            logger.error("设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
            broken=true;
        } finally {
            redisDataSource.returnResource(shardedJedis,broken);
        }
        return result;
    }

    /**
     * 删除指定key 的对象
     */
    public Long delkeyObject(String key) {
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.del(key.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
            broken=true;
            logger.error("[删除key时异常]"+e);
            return 0L;
        }finally{
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }

    /**
     * 指定key的对象是否存在
     * @param key
     * @return
     */
    public Boolean existsObject(String key) {
        ShardedJedis shardedJedis = null;
        boolean broken=false;
        try {
            shardedJedis = redisDataSource.getResource();
            return shardedJedis.exists(key.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("[查询指定key的对象是否存在异常]"+e);
            broken=true;
            return false;
        }finally{
            redisDataSource.returnResource(shardedJedis,broken);
        }
    }
}

