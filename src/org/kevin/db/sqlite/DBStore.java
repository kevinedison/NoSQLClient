/**
* * DBStore.java
 * kevin 2013-1-29
 * @version 0.1
 */
package org.kevin.db.sqlite;

/**
 * @author kevin
 * @since jdk1.6
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DBStore {

    Logger logger = Logger.getLogger(DBStore.class);
    private static String dbName = "_nosql.db";
    private static String basedir = System.getProperty("user.dir");
    private static String dataDir = basedir + File.separator + "data";
    private Connection conn = null;
    
    public DBStore() {
        dbName = dataDir + dbName;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class DBStoreHolder {
        private static DBStore instance;
        public static DBStore getInstance(){
            if(null == instance)
                instance = new DBStore();
            return instance;
        }
    }

    public static DBStore getInstance() {
        return DBStoreHolder.getInstance();
    }

    public synchronized void init() {
        //System.out.println("init dataDir:" + dataDir);
        logger.info("init dataDir:" + dataDir);
        File dbDir = new File(dataDir);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        File dbFile = new File(dbName);
        if (!dbFile.exists()) {
//            System.out.println("DB:" + dbName + " not existed");
            try {
                dbFile.createNewFile();
                logger.info("create db file:" + dbName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //System.out.println("DB path:" + dbName);
        }
        // if not existed, create it
        if(!isExisted())
            createTable();
        
        // update the sqlite db
        //UpgreadDB.updateDB();
    }

    public Connection getConnection() {
        //Connection conn = null;
        close();
        try {
            //System.setProperty("java.library.path", "native/Windows/amd64/sqlitejdbc.dll");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            logger.info("getConnection dbName:" + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException:" + e.getMessage());
//            e.printStackTrace();
        }
        return conn;
    }

    //public int execute(String sql, Connection connection) {
    public int execute(String sql) {
        Statement statement;
        int result = 0;
        conn = getConnection();
        try {
            statement = conn.createStatement();
            statement.setQueryTimeout(30);
            result = statement.executeUpdate(sql);
            //conn.commit();
            //statement.close();
            //close(conn);
            close(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQLException:" + e.getMessage());
            //e.printStackTrace();
        }

        return result;
    }

    public void createTable() {
        conn = getConnection();
        logger.info("Start create table hosts...");
        String sql = "create table hosts(id integer primary key autoincrement, ip string, pwd string, dbindex integer, port integer)";
        //execute(sql, conn);
        execute(sql);
        sql = "create table systemversiom(id integer primary key autoincrement, version string, upgradeDate date, updateTime time)";
        //execute(sql, conn);
        execute(sql);
        logger.info("Create table hosts end");
    }
    
    public boolean isExisted(){
        conn = getConnection();
        //String sql = "select name from SQLITE_SEQUENCE where name='hosts'";
        String sql = "select 1 from hosts";
        ResultSet rs = query(sql);
        try {
            if(null != rs && rs.next())
                return true;
            else{
                //return false;
                logger.warn("Table is not existed.");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            logger.error("SQLException:" + e.getMessage());
            return false;
        }
        //close(conn);
        return false;
    }
    public void dropTable() {
        conn = getConnection();
        String sql = "drop table hosts";
        execute(sql);
        close(conn);
    }

    //public ResultSet query(String sql, Connection connection) {
    public ResultSet query(String sql) {
        Statement statement;
        ResultSet result = null;
        conn = getConnection();
        try {
            statement = conn.createStatement();
            statement.setQueryTimeout(30);
            result = statement.executeQuery(sql);
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQLException query:" + e.getMessage());
            //return  null;
            //e.printStackTrace();
        }
        return result;
    }

    public int delete(String sql) {
        Statement statement;
        int result = 0;
        conn = getConnection();
        try {
            statement = conn.createStatement();
            //statement.setQueryTimeout(30);
            result = statement.executeUpdate(sql);
            //conn.commit();
            //statement.close();
            //close(conn);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
