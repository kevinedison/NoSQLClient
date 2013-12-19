/**
 * StringOperation.java
 * kevin 2013-3-8
 * @version 0.1
 */
package org.kevin.redis.operation;

import org.kevin.redis.msg.PopMessage;

/**
 * @author kevin
 * @since jdk1.6
 */
public class StringOperation  {

    /**
     * @param key
     */
    public void remove(String key) {
        PopMessage.popMsg("remove for string key:" + key);
        
    }
 
}
