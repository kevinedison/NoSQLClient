/**
 * RedisConnectionPool.java
 * kevin 2013-4-15
 * @version 0.1
 */
package org.kevin.redis.connection;

import org.kevin.redis.constant.RedisConstants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author kevin
 * @since jdk1.6
 */
public class RedisConnectionPool {

    private static JedisPool pool;

    static {
        initPool();
    }

    public static void refreshPool() {
        initPool();
    }

    private static void initPool() {
//         String ip = "192.168.30.212";
        String ip = RedisConstants.REDIS_SERVER_IP;
//         int port = 6380;
        int port = RedisConstants.REDIS_SERVER_PORT;
        int timeout = 1000;
//        String password = "authpwd";
        String password = RedisConstants.REDIS_SERVER_PWD;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(20);
        config.setMaxIdle(5);
        config.setMaxWait(1000);
        config.setTestOnBorrow(true);
        // pool = new JedisPool(config, "localhost");
        // pool = new JedisPool(config, "192.168.12.93");
        System.out.println("Init pool with ip:[" + ip + "], port:[" + port + "], password:[" + password + "]");
        if(null == password || password.isEmpty())
            pool = new JedisPool(config, ip, port, timeout);
        else 
            pool = new JedisPool(config, ip, port, timeout, password);
    }

    public void freshPoolConn(){
        if (null == pool){
            initPool();
        } else {
            pool.destroy();
            initPool();
        }
    }
    
    public static Jedis getConn() {
        if (null == pool)
            initPool();
        Jedis jedisConn = null;
        try{
            //System.out.println("Pool size: "  + pool.toString());
            jedisConn = pool.getResource();
            //System.out.println("jedisConn: "  + jedisConn);
            //jedisConn = new Jedis(RedisConstants.REDIS_SERVER_IP, RedisConstants.REDIS_SERVER_PORT);
            //System.out.println("Get conn at dbindex:[" + RedisConstants.REDIS_SERVER_DBINDEX + "]");
            //jedisConn.auth(RedisConstants.REDIS_SERVER_PWD);
            //jedisConn.select(RedisConstants.REDIS_SERVER_DBINDEX);
        } catch (Exception ex){
            //System.out.println("renew pool");
            initPool();
            jedisConn = pool.getResource();
            //ex.printStackTrace();
            //jedisConn = new Jedis(RedisConstants.REDIS_SERVER_IP, RedisConstants.REDIS_SERVER_PORT);
            //jedisConn.auth(RedisConstants.REDIS_SERVER_PWD);
            //jedisConn.select(RedisConstants.REDIS_SERVER_DBINDEX);
        }
        //jedisConn.auth(RedisConstants.REDIS_SERVER_PWD);
        jedisConn.select(RedisConstants.REDIS_SERVER_DBINDEX);
        return jedisConn;
    }

    public static int returnConn(Jedis jedisConn) {
        pool.returnResource(jedisConn);
        return 1;
    }
}
