/**
 * UpgreadDB.java
 * kevin 2013-4-24
 * @version 0.1
 */
package org.kevin.db.sqlite.upgrade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.kevin.db.sqlite.DBStore;

/**
 * @author kevin
 * @since jdk1.6
 */
public class UpgreadDB {

    private static int current_version = 13;
    
    private void updateDB(){
        String sql = "select version from systemversiom";
        ResultSet rs = DBStore.getInstance().query(sql);
        String version = "1.0";
        if(null!= rs){
            try {
                while(rs.next()){
                    version = rs.getString("version");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int _version = Integer.valueOf(version.replaceAll(".", ""));
            while(_version < current_version){
                //updateVersion13();
                
                try {
                    Class clazz = Class.forName("org.kevin.db.sqlite.DBStore");
                    Method mtd = clazz.getMethod(("updateVersion" + _version), new Class[]{String.class});
                    Object obj = (Object) clazz.newInstance();
                    mtd.invoke(obj,new Object[]{"Jacky"});
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                _version++;
            }
        }
    }
    
    private void updateVersion13(){
        DBStore dbStore = DBStore.getInstance();
        String sql = "delete from systemversiom";
        dbStore.execute(sql);
        sql = "insert into systemversiom(id, version, upgradeDate, updateTime) values(NULL, '1.3', date(), time())";
        dbStore.execute(sql);
    }
    
}
