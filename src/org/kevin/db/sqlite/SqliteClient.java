/**
 * SqliteClient.java
 * kevin 2013-2-11
 * @version 0.1
 */
package org.kevin.db.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.kevin.redis.data.vo.Host;

/**
 * @author kevin
 * @since jdk1.6
 */
public class SqliteClient {

    private void testsqliteAll() {
        final String seplite = ", ";
        DBStore dbStore = DBStore.getInstance();
        dbStore.dropTable();
        dbStore.createTable();
        Connection conn = dbStore.getConnection();
        dbStore.execute(
                "insert into hosts values(1, 'localhost', 'pwd', 6390)");
        dbStore.execute(
                "insert into hosts values(2, '127.0.0.2', 'pwd', 6390)");
        dbStore.execute(
                "insert into hosts values(3, '127.0.0.3', 'pwd', 6390)");
        dbStore.execute(
                "insert into hosts values(4, '127.0.0.4', 'pwd', 6390)");
        // System.out.println(conn);
        ResultSet rs = dbStore.query("select * from hosts");
        try {
            while (rs.next()) {
                // read the result set
                System.out.print("[");
                System.out.print("id=" + rs.getInt("id") + seplite);
                System.out.print("ip=" + rs.getString("ip") + seplite);
                System.out.print("pwd=" + rs.getString("pwd") + seplite);
                System.out.print("port=" + rs.getInt("port"));
                System.out.print("]");
                System.out.print("\r\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testDBList(){
        List<Host> hosts = SqliteDB.getInstance().gethosts();
        for(Host host : hosts){
            System.out.println("[id:" + host.getId() + ", ip:" + host.getIp() + ", pwd:" + host.getPwd() + ", port:" + host.getPort() + "]");
        }
    }
    public static void main(String[] args) {
        testDBList();
    }
}
