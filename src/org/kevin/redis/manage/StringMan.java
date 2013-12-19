/**
 * StringManagerment.java
 * kevin 2013-3-20
 * @version 0.1
 */
package org.kevin.redis.manage;


/**
 * @author kevin
 * @since jdk1.6
 */
public class StringMan extends RedisTableMan {

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#add()
     */
    @Override
    public int add(String key, Object value) {
        String strValue = (String) value;
        return add(key, strValue);
        //redisConn.set(key, strValue);
        //disconn();
    }
    
    public int edit(String key, String value) {
        remove(key);
        return add(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String,
     * java.util.Map)
     */
    public int add(String key, String value) {
        //getconn();
        int effectRows = 0;
        try{
            redisConn.set(key, value);
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
        //getconn();
        redisConn.del(key);
        //disconn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kevin.redis.manage.RedisTableMan#list()
     */
    @Override
    public String list(String key) {
        redisConn.get(key);
        return key;
    }

}
