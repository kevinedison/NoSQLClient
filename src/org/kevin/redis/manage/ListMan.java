/**
 * ListMan.java
 * kevin 2013-3-20
 * @version 0.1
 */
package org.kevin.redis.manage;

import java.util.List;

/**
 * @author kevin
 * @since jdk1.6
 */
public class ListMan extends RedisTableMan {

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String,
     * java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public int add(String key, Object obj) {
        List<String> values = (List<String>) obj;
        return add(key, values);
        //for (String value : values)
        //    redisConn.sadd(key, value);
        //disconn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String,
     * java.util.Map)
     */
    public int add(String key, List<String> valueList) {

        int effectRows = 0;
        try{
            if (null != valueList) {
                for (String value : valueList)
                    redisConn.sadd(key, value);
            }
            effectRows++;
        } catch(Exception ex){
            
        }
        //disconn();
        return effectRows;
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
 
    /* (non-Javadoc)
     * @see org.kevin.redis.manage.RedisTableMan#list()
     */
    @Override
    public List<String> list(String key) {
        Long llen = redisConn.llen(key);
        return redisConn.lrange(key, 0, llen);
    }
 
}
