/**
 * RedistDataManagement.java
 * 2011 Dec 16, 2011
 */
package org.kevin.redis.manage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kevin.redis.connection.RedisConnection;

import redis.clients.jedis.Jedis;

/**
 * @author kevin
 * @since Jdk 1.6 
 */
public class RedistDataManagement {

    /** all key partten is * */
    private static String ALL_KEY_PATTEN = "*";

    /**
     * @param filterText
     * @return
     */
    public static Set<String> listKeys(String filterText) {
        if(null == filterText || ALL_KEY_PATTEN.equals(filterText))
            return listKeys();
        Jedis jedis = RedisConnection.getJedis();
        Set<String> keys = null;
        try{
             keys = jedis.keys("*" + filterText + "*");
        }catch (Exception ex ){
            System.out.println("The redis db is down.");
        }
        RedisConnection.returnConn(jedis);
        return keys;
    }
    
    /**
     * Get all the db tables
     * @return keys set
     */
    public static Set<String> listKeys(){
        Jedis jedis = RedisConnection.getJedis();
        Set<String> keys = null;
        try{
             keys = jedis.keys(ALL_KEY_PATTEN);
        }catch (Exception ex ){
            System.out.println("The redis db is down.");
        }
        RedisConnection.returnConn(jedis);
        return keys;
    }

    /**
     * @param pathNode 
     * @return
     */
    public static String getType(String pathNode) {
        Jedis jedis = RedisConnection.getJedis();
        String type = jedis.type(pathNode);
        //PopMessage.popMsg(type);
        RedisConnection.returnConn(jedis);
        return type;
    }
    
    /**
     * @param pathNode 
     * @return
     */
    public static Set<String> getSetDetail(String pathNode) {
        Jedis jedis = RedisConnection.getJedis();
        Set<String> setMembers = jedis.smembers(pathNode);
        RedisConnection.returnConn(jedis);
        return setMembers;
    }
    /**
     * @param pathNode 
     * @return
     */
    public static Map<String, String> getHashDetail(String pathNode) {
        Jedis jedis = RedisConnection.getJedis();
        Map<String, String> hashMembers = jedis.hgetAll(pathNode);
        RedisConnection.returnConn(jedis);
        return hashMembers;
    }

    /**
     * @param pathNode
     * @return
     */
    public static String getStringDetail(String pathNode) {
        Jedis jedis = RedisConnection.getJedis();
        String stringMembers = jedis.get(pathNode);
        RedisConnection.returnConn(jedis);
        return stringMembers;
    }

    /**
     * @param pathNode
     * @return
     */
    public static List<String> getListDetail(String pathNode) {
        Jedis jedis = RedisConnection.getJedis();
        List<String> listMembers = jedis.lrange(pathNode, 0, -1);
        RedisConnection.returnConn(jedis);
        return listMembers;
    }


}
