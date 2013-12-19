/**
 * RedisConnection.java
 * 2011 Dec 16, 2011
 */
package org.kevin.redis.connection;

import org.kevin.redis.constant.RedisConstants;
import org.kevin.redis.msg.PopMessage;

import redis.clients.jedis.Jedis;

/**
 * @author kevin
 * @since Jdk 1.6 
 */
public class RedisConnection {


    public static Jedis getJedis(String IP, int port){
        return new Jedis(RedisConstants.REDIS_SERVER_IP, RedisConstants.REDIS_SERVER_PORT);
    }

    public static Jedis getJedis(int index){
        if(null == RedisConstants.REDIS_SERVER_IP){
            //TODO init or update redis db.
        }
        Jedis jedisDb = null;
        try{
            jedisDb = new Jedis(RedisConstants.REDIS_SERVER_IP, RedisConstants.REDIS_SERVER_PORT);
            jedisDb.select(RedisConstants.REDIS_SERVER_DBINDEX);
        } catch (Exception ex ){
            PopMessage.popError("DB is unvaliable Cause: " + ex.getMessage());
        }
        return jedisDb;
    }
    
    public static Jedis getJedis(){
        return RedisConnectionPool.getConn();
    }
    
    public static int returnConn(Jedis jedisConn){
        return RedisConnectionPool.returnConn(jedisConn);
    }
}
