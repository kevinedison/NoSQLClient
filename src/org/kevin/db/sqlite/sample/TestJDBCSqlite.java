/**
 * TestJDBCSqlite.java
 * kevin 2013-4-16
 * @version 0.1
 */
package org.kevin.db.sqlite.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author kevin
 * @since jdk1.6
 */
public class TestJDBCSqlite {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            long start = System.currentTimeMillis();

            // 连接SQLite的JDBC
            Class.forName("org.sqlite.JDBC");

            // 建立一个数据库名winnie.db的连接，如果不存在就在当前目录下创建之
            // 红色部分路径要求全小写，大写会报错
            Connection conn = DriverManager
                    .getConnection("jdbc:sqlite:winnie.db");
            long end = System.currentTimeMillis();
            System.out.println("创建数据库文件并连接耗费时间：" + (end - start));
            // 关闭连接
            conn.close();

            start = System.currentTimeMillis();
            conn = DriverManager.getConnection("jdbc:sqlite:winnie.db");
            end = System.currentTimeMillis();
            System.out.println("数据库连接耗费时间：" + (end - start));

            start = System.currentTimeMillis();
            Statement stat = conn.createStatement();
            // 创建一个表，两列
            stat.executeUpdate("create table tbl1(name varchar(20), salary int);");
            end = System.currentTimeMillis();
            System.out.println("创建表耗费时间：" + (end - start));

            // 插入数据
            start = System.currentTimeMillis();
            stat.executeUpdate("insert into tbl1 values('ZhangSan',8000);");
            stat.executeUpdate("insert into tbl1 values('LiSi',7800);");
            stat.executeUpdate("insert into tbl1 values('WangWu',5800);");
            stat.executeUpdate("insert into tbl1 values('ZhaoLiu',9100);");
            end = System.currentTimeMillis();
            System.out.println("插入四行数据耗费时间：" + (end - start));

            start = System.currentTimeMillis();
            ResultSet rs = stat.executeQuery("select * from tbl1;"); // 查询数据
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + " "); // 列属性一
                System.out.println("salary = " + rs.getString("salary")); // 列属性二
            }
            rs.close();
            end = System.currentTimeMillis();
            System.out.println("查询数据耗费时间：" + (end - start));

            conn.close(); // 结束数据库的连接

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
