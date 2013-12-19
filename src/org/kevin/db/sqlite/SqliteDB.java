/**
 * DB.java
 * kevin 2013-2-11
 * @version 0.1
 */
package org.kevin.db.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.kevin.redis.data.vo.Host;

/**
 * @author kevin
 * @since jdk1.6
 */
public class SqliteDB {
    
    //private static Connection conn = null;

    private static SqliteDB sqliteDB = null;
    
    public static SqliteDB getInstance(){
        if(null == sqliteDB)
            sqliteDB = new SqliteDB();
        return sqliteDB;
    }
    public List<Host> gethosts(){
        DBStore dbStore = DBStore.getInstance();
//        if(null == conn)
//            conn = dbStore.getConnection();
        //ResultSet rs = dbStore.query("select * from hosts", conn);
        ResultSet rs = dbStore.query("select * from hosts");
        List<Host>  hostsList = new ArrayList<Host>();
        Host host  = null;
        try {
            if(null != rs){
                while (rs.next()) {
                    // read the result set
                    host = new Host();
                    host.setId(rs.getInt("id"));
                    host.setIp(rs.getString("ip"));
                    host.setPwd(rs.getString("pwd"));
                    host.setDbIndex(rs.getInt("dbIndex"));
                    host.setPort(rs.getInt("port"));
                    hostsList.add(host);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Table is not existed");
            //e.printStackTrace();
        }
        dbStore.close();
        return hostsList;
    }
    
    public void addHost(Host host){
        DBStore dbStore = DBStore.getInstance();
//        if(null == conn)
//            conn = dbStore.getConnection();
        String sql = "select ip, port, dbindex from hosts where ip='" + host.getIp() + "' and port=" + host.getPort() + " and dbindex=" + host.getDbIndex();
        //System.out.println(sql);
        ResultSet rs = dbStore.query(sql);
        boolean isexisted = false;
        try {
            isexisted = rs.next();
            dbStore.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        if(isexisted){
            sql = "update hosts set ip='" + host.getIp() + "', port=" + host.getPort() + ", pwd='" + host.getPwd() + "', dbindex=" + host.getDbIndex() + " where ip='" + host.getIp() + "' and port=" + host.getPort() + " and dbindex=" + host.getDbIndex();
            //System.out.println("update: " + sql);
            dbStore.execute(sql);
        } else {
            //int hostSeq = 0;
            //ResultSet rseq = dbStore.query("select seq from SQLITE_SEQUENCE where name='hosts'", conn);
            //if(rseq.next()) hostSeq = rseq.getInt("seq");
            //hostSeq += 1;
            //dbStore.execute("insert into hosts values(" + hostSeq + ", '" + host.getIp() + "', 'pwd', " + host.getPort() +")", conn);
            sql = "insert into hosts(id, ip, pwd, dbindex, port) values(NULL, '" + host.getIp() + "', '" + host.getPwd() + "', " + host.getDbIndex() + ", " + host.getPort() +")";
            System.out.println("insert: " + sql);
            dbStore.execute(sql);
        }
        //dbStore.close();
    }

    public int removeHost(Host host){
        DBStore dbStore = DBStore.getInstance();
//        if(null == conn)
//            conn = dbStore.getConnection();
        String sql = "delete from hosts where id=" + host.getId() + "";
        System.out.println("Delete SQL:" + sql);
        int result = dbStore.delete(sql); 
        //dbStore.close();
        return result;
    }
     
}
