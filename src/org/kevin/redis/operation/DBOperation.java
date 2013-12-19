/**
 * DBOperation.java
 * kevin 2013-3-8
 * @version 0.1
 */
package org.kevin.redis.operation;

import org.kevin.redis.manage.HashMan;
import org.kevin.redis.manage.ListMan;
import org.kevin.redis.manage.RedistDataManagement;
import org.kevin.redis.manage.SetMan;
import org.kevin.redis.manage.StringMan;


/**
 * @author kevin
 * @since jdk1.6
 */
public class DBOperation {

    public int remove(String key) {
        int affectKeys = 0;
        String key_type = RedistDataManagement.getType(key);
        if ( "list".equalsIgnoreCase(key_type)) {
            //ListOperation listO = new ListOperation();
            //listO.remove(key);// this not implement
            ListMan listM = new ListMan();
            listM.remove(key);
            affectKeys ++;
        } else if ( "hash".equalsIgnoreCase(key_type)) {
            //HashOperation hash = new HashOperation();
            //hash.remove(key);// this not implement
            HashMan hashm = new HashMan();
            hashm.remove(key);
            affectKeys ++;
        } else if ( "set".equalsIgnoreCase(key_type)) {
            //SetOperation set = new SetOperation();
            //set.remove(key);// this not implement
            SetMan setm = new SetMan();
            setm.remove(key);
            affectKeys ++;
        } else if ( "string".equalsIgnoreCase(key_type)) {
            //StringOperation str = new StringOperation();
            //str.remove(key);// this not implement
            StringMan strM =  new StringMan();
            strM.remove(key);
            affectKeys ++;
        } else {
            //PopMessage.popMsg("Unsupported key [" + key + "] for type[" + key_type + "]");
        }
        return affectKeys;
    }
}
