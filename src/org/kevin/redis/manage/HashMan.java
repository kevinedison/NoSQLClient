/**
 * HashMan.java
 * kevin 2013-3-20
 * @version 0.1
 */
package org.kevin.redis.manage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin
 * @since jdk1.6
 */
public class HashMan extends RedisTableMan {

    /* (non-Javadoc)
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public int add(String key, Object obj) {
        Map<String, String> values = (HashMap<String, String>) obj;
        return add(key, values);
    }

    /* (non-Javadoc)
     * @see org.kevin.redis.manage.RedisTableMan#add(java.lang.String, java.util.Map)
     */
    public int add(String key, Map<String, String> valueMap) {

        int effectRows = 0;
        try{
            //Iterator<String> valueKeys = valueMap.keySet().iterator();
            redisConn.hmset(key, valueMap);
            effectRows++;
        } catch(Exception ex){
            
        }
        return effectRows;
        //String mapKey = null;
        //String mapValue = null;
        //while(valueKeys.hasNext()){
        //    mapKey = valueKeys.next();
        //    mapValue = valueMap.get(mapKey);
        //}
    }

    /* (non-Javadoc)
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
    public Map<String, String> list(String key) {
        return redisConn.hgetAll(key);
    }

}
