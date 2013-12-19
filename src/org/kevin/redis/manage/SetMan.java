/**
 * SetMan.java
 * kevin 2013-3-20
 * @version 0.1
 */
package org.kevin.redis.manage;

import java.util.Set;
import java.util.Vector;

/**
 * @author kevin
 * @since jdk1.6
 */
public class SetMan extends RedisTableMan {

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String,
     * java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public int add(String key, Object obj) {
        Vector<String> values = (Vector<String>) obj;
        return add(key, values);
        //for (String value : values)
        //    redisConn.sadd(key, value);
        //disconn();
    }

    public int edit(String key, Vector<String> values) {
        remove(key);
        return add(key, values);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String,
     * java.util.Map)
     */
    public int add(String key, Vector<String> values) {
        int effectRows = 0;
        try{
            for (String value : values){
                //System.out.println("key:[" + key + "], value:[" + value + "]");
                redisConn.sadd(key, value);
            }
            effectRows++;
        } catch(Exception ex){
            
        }
        return effectRows;
        //disconn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#remove()
     */
    @Override
    public void remove(String key) {
        redisConn.del(key);
        //disconn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#list()
     */
    @Override
    public Set<String> list(String key) {
        return redisConn.smembers(key);
        //return null;
    }

}
