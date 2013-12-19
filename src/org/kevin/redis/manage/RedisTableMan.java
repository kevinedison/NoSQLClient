/**
 * RedisTableMan.java
 * kevin 2013-3-20
 * @version 0.1
 */
package org.kevin.redis.manage;

import org.kevin.redis.connection.RedisConnection;

import redis.clients.jedis.Jedis;

/**
 * @author kevin
 * @since jdk1.6
 */
public abstract class RedisTableMan {

    protected static Jedis redisConn = null;

    
    public RedisTableMan() {
        getconn();
    }

    /**
     * Abstract method for add new data to redis, save data should instance the sub class implements
     * 
     * @param value
     *     the String should make the value be a string
     *     the set should make the value be a hash map
     *     the list should make the value be a hash map for index
     *     the hash should make the value be a hash map for key and value
     * @return 
     */
    protected abstract int add(String key, Object value);

    protected abstract void remove(String key);

    //static {
    //    redisConn = RedisConnection.getJedis();
    //}
    
    protected void getconn(){
        if(null == redisConn || !redisConn.isConnected())
            redisConn = RedisConnection.getJedis();
    }
    
    protected void disconn(){
        redisConn.disconnect();
        redisConn = null;
    }

    /**
     * @param key
     */
    protected abstract Object list(String key);
    
    public static String getType(String key) {
        //Jedis jedis = RedisConnection.getJedis();
        //String type = jedis.type(key);
        String type = RedistDataManagement.getType(key);
        return type;
    }
}
