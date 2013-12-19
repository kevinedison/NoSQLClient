/**
 * DerbyClient.java
 * kevin 2013-2-20
 * @version 0.1
 */
package org.kevin.db.derby;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.kevin.redis.data.vo.Host;

/**
 * @author kevin
 * @since jdk1.6
 */
public class DerbyClient {

    private static void testHostsAll() {
        final String seplite = ", ";
        DerbyDAO derbyStore = DerbyDAO.getInstance();
        // derbyStore.dropTable();
        // derbyStore.createTable();
        Connection conn = derbyStore.getConnection();
        derbyStore.execute("insert into hosts(ip, pwd, port) values('localhost', 'pwd', 6379)",conn);
        derbyStore.execute("insert into hosts(ip, pwd, port) values('127.0.0.2', 'pwd', 6380)",conn);
        derbyStore.execute("insert into hosts(ip, pwd, port) values('127.0.0.3', '   ', 6381)",conn);
        derbyStore.execute("insert into hosts(ip, pwd, port) values('127.0.0.4', 'pwd', 6382)",conn);
        // derbyStore.execute("insert into hosts values(1, 'localhost', 'pwd', 6379)", conn);
        // derbyStore.execute("insert into hosts values(2, '127.0.0.2', 'pwd', 6380)", conn);
        // derbyStore.execute("insert into hosts values(3, '127.0.0.3', 'pwd', 6381)", conn);
        // derbyStore.execute("insert into hosts values(4, '127.0.0.4', 'pwd', 6382)", conn);
        // System.out.println(conn);
        ResultSet rs = derbyStore.query("select * from hosts", conn);
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
        derbyStore.close(conn);
    }

    private static void testDBList() {
        List<Host> hosts = DerbyDB.getInstance().gethosts();
        for (Host host : hosts) {
            System.out.println("[id:" + host.getId() + ", ip:" + host.getIp() +  ", pwd:" + host.getPwd() + ", port:" + host.getPort() + "]");
        }
    }

    public static void main(String[] args) {
        testHostsAll();
        testDBList();
    }
}
