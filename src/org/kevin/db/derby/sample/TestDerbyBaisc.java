/**
 * TestDerbyBaisc.java
 * kevin 2013-2-20
 * @version 0.1
 */
package org.kevin.db.derby.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author kevin
 * @since jdk1.6
 */
public class TestDerbyBaisc {
    public static void main(String[] args) throws SQLException{
        try {          
              
         Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();//加载驱动
         Connection conn = DriverManager.getConnection("jdbc:derby:TESTDB;create=true");//连接数据库
         Statement st = conn.createStatement();
         st.execute("create table USER_INFO (ID INT NOT NULL,NAME VARCHAR(10) NOT NULL)");//建表
         st.executeUpdate("insert into USER_INFO(ID,NAME) values (1,'hermit')");//插入数据
         st.executeUpdate("insert into USER_INFO(ID,NAME) values (2,'test')");//插入数据
         ResultSet rs = st.executeQuery("select * from USER_INFO");//读取刚插入的数据
                while(rs.next()){
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    System.out.println("ID="+id);
                    System.out.println("NAME="+name);
                }
            } catch(Exception e){
                DriverManager.getConnection("jdbc:derby:;shutdown=true");//关闭数据库
                e.printStackTrace();
            }
        }
}
