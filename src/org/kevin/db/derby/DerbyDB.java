/**
 * DerbyDB.java
 * kevin 2013-2-20
 * @version 0.1
 */
package org.kevin.db.derby;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.kevin.redis.data.vo.Host;
import org.kevin.redis.msg.PopMessage;

/**
 * @author kevin
 * @since jdk1.6
 */
public class DerbyDB {

    private static Connection conn = null;
    private static DerbyDB derbyDB = null;
    
    private DerbyDB(){
    }
    
    public static DerbyDB getInstance(){
        if(null == derbyDB)
            derbyDB = new DerbyDB();
        return derbyDB;
    }

    public List<Host> gethosts(){
        DerbyDAO dbStore = DerbyDAO.getInstance();
        if(null == conn)
            conn = dbStore.getConnection();
        ResultSet rs = dbStore.query("select * from hosts", conn);
        List<Host>  hostsList = new ArrayList<Host>();
        Host host  = null;
        try {
            if(null != rs){
                while (rs.next()) {
                    // read the result set
                    host = new Host();
                    host.setId(rs.getInt("id"));
                    host.setIp(rs.getString("ip"));
                    host.setDbIndex(rs.getInt("dbIndex"));
                    host.setPwd(rs.getString("pwd"));
                    host.setPort(rs.getInt("port"));
                    hostsList.add(host);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("table is not existed");
            //e.printStackTrace();
        }
        return hostsList;
    }
    
    public void addHost(Host host){
        DerbyDAO dbStore = DerbyDAO.getInstance();
        if(null == conn)
            conn = dbStore.getConnection();
        //ResultSet rs = dbStore.query("select ip, port, dbindex from hosts where ip='" + host.getIp() + "' and port=" + host.getPort() + "' and dbindex=" + host.getDbIndex(), conn);
        ResultSet rs = dbStore.query("desc hosts ", conn);
        if(null == rs) {
            PopMessage.popWarn("No host is existed");
            dbStore.alertTable();
        } else {
            try {
                PopMessage.popWarn("Host table desc:" + rs.findColumn("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if(rs.next())
                dbStore.execute("update hosts set ip='" + host.getIp() + "', port=" + host.getPort() + ", pwd='" + host.getPwd() + "' where ip='" + host.getIp() + "' and port=" + host.getPort()+ "' and dbindex=" + host.getDbIndex(), conn);
            else{
                //int hostSeq = 0;
                //ResultSet rseq = dbStore.query("select seq from SQLITE_SEQUENCE where name='hosts'", conn);
                //if(rseq.next()) hostSeq = rseq.getInt("seq");
                //hostSeq += 1;
                //dbStore.execute("insert into hosts values(" + hostSeq + ", '" + host.getIp() + "', 'pwd', " + host.getPort() +")", conn);
                dbStore.execute("insert into hosts(ip, pwd, port, dbindex)  values('" + host.getIp() + "', '" + host.getPwd() + "', " + host.getPort()  + "', " + host.getDbIndex() +")", conn);
            }
        } catch (SQLException ex) {
            PopMessage.popError("DB Query exception Cause: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public int removeHost(Host host){
        DerbyDAO dbStore = DerbyDAO.getInstance();
        if(null == conn)
            conn = dbStore.getConnection();
        int result = dbStore.execute("delete from hosts where id=" + host.getId() + "", conn);
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return result;
    }
}
