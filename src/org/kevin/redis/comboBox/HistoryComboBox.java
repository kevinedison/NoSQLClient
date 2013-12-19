/**
 * HistoryComboBox.java
 * kevin 2013-2-22
 * @version 0.1
 */
package org.kevin.redis.comboBox;

import org.kevin.redis.connection.RedisConnectionPool;

/**
 * @author kevin
 * @since jdk1.6
 */
public class HistoryComboBox {

    private static HistoryComboBox hisComb = null;

    public static HistoryComboBox getInstances() {
        if (null == hisComb)
            hisComb = new HistoryComboBox();
        return hisComb;
    }

    public void reInitConnectPool(){
        RedisConnectionPool.refreshPool();
    }
}
