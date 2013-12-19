/**
 * JedisThreadPool.java
 * 2011 Nov 27, 2011
 */
package org.kevin.redis.connection;

import java.util.Set;

import org.kevin.redis.constant.RedisConstants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author kevin
 * @since Jdk 1.6
 */
public class JedisThreadPool {

    private static JedisPool pool;

    static {
        //String ip = "192.168.30.212";
        String ip = RedisConstants.REDIS_SERVER_IP;
        // int port = 6380;
        int port = RedisConstants.REDIS_SERVER_PORT;
        int timeout = 1000;
        String password = RedisConstants.REDIS_SERVER_PWD;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(100);
        config.setMaxIdle(20);
        // config.setMaxWait(1000);
        config.setTestOnBorrow(true);
        // pool = new JedisPool(config, "localhost");
        // pool = new JedisPool(config, "192.168.12.93");
        pool = new JedisPool(config, ip, port, timeout, password);
    }

    public void initConnPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(100);
        config.setMaxIdle(20);
        config.setMaxWait(1000l);
        JedisPool pool = null;
        Jedis jedis = null;
        pool = new JedisPool(config, "", 6379);
        boolean borrowOrOprSuccess = true;
        try {
            jedis = pool.getResource();
            // do redis opt by instance
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                pool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        jedis = pool.getResource();
    }

    public static void main(String[] args) {
        JedisThreadPool demo = new JedisThreadPool();
        demo.test();
    }

    public void test() {
        initInsert();
        // testThread();
    }

    private void initInsert() {
        System.out.println("JedisThreadPool 插入数据开始");
        Jedis jedis = pool.getResource();
        //for (int i = 0; i < 20000; i++) {
            //jedis.set(String.valueOf("lijian-key" + i), String.valueOf("lijian-value" + i));
        //}
        Set<String> sets = jedis.keys("*");
        for(String setkey : sets){
            System.out.println("Key: " + setkey);
        }
        pool.returnResource(jedis);
        System.out.println("JedisThreadPool 插入数据结束");
    }

    private void testThread() {
        long begin = System.currentTimeMillis();

        Thread thread[] = new Thread[100];
        for (int i = 0; i < thread.length; i++) {
            thread[i] = new MyThread();
            thread[i].start();
        }

        for (int i = 0; i < thread.length; i++) {
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("线程执行时间是：" + (end - begin) + " ms");
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            Jedis jedis = JedisThreadPool.pool.getResource();
            for (int i = 0; i < 20000; i++) {
                System.out.println(jedis.get(String.valueOf(i)));
            }
            JedisThreadPool.pool.returnResource(jedis);
        }
    }

    public static JedisPool getPool() {
        return pool;
    }

    public static void setPool(JedisPool pool) {
        JedisThreadPool.pool = pool;
    }
}
