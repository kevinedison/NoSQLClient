/**
 * DerbyDAO.java
 * kevin 2013-2-20
 * @version 0.1
 */
package org.kevin.db.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author kevin
 * @since jdk1.6
 */
public class DerbyDAO {

    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String protocol = "jdbc:derby:";

    Properties props;
    
    Logger logger = Logger.getLogger(DerbyDAO.class);

    public DerbyDAO() {
        init();
    }

    private static class DerbyDAOHolder {
        static DerbyDAO instance = new DerbyDAO();
    }

    public static DerbyDAO getInstance() {
        return DerbyDAOHolder.instance;
    }

    private void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            logger.info("Load the embedded driver");
            Connection conn = null;
            props = new Properties();
            props.put("user", "kevin");
            props.put("password", "edison");
            // create and connect the database named nosql
            //conn = DriverManager.getConnection("jdbc:derby:nosql;create=true", props);
            conn = DriverManager.getConnection("jdbc:derby:nosql;create=true");
            logger.info("create and connect to nosql");
            // System.out.println("create and connect to nosql");
            Statement s = conn.createStatement();
            try {
                String checkhosts = "select 1 from hosts";
                s.execute(checkhosts);
            } catch (SQLException e) {
                String sql = "create table hosts(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key, ip varchar(32), pwd varchar(32), port integer, dbindex integer)";
                s.execute(sql);
                logger.error("hosts not existed, create it:");
                //e.printStackTrace();
            }
            //conn.setAutoCommit(false);
        } catch (InstantiationException e) {
            logger.error("InstantiationException:" + e.getCause().getMessage());
            // e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException:" + e.getCause().getMessage());
            // e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException:" + e.getCause().getMessage());
            // e.printStackTrace();
        } catch (SQLException e) {
            logger.error("SQLException:" + e.getCause().getMessage());
            // e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            //conn = DriverManager.getConnection("jdbc:derby:nosql;create=true", props);
            conn = DriverManager.getConnection("jdbc:derby:nosql;create=true");
            //logger.info("create and connect to nosql");
            logger.info("connect to db nosql");
            //conn.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("SQLException:" + e.getCause().getMessage());
            //e.printStackTrace();
        }
        return conn;
    }
    

    public int execute(String sql, Connection connection) {
        Statement statement;
        int result = 0;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            result = statement.executeUpdate(sql);
            statement.close();
            //result = statement.execute(sql);
        } catch (SQLException e) {
            logger.error("SQLException:" + e.getMessage());
        }
        return result;
    }

    public void dropTable() {
        Connection conn = getConnection();
        String sql = "drop table hosts";
        execute(sql, conn);
        close(conn);
    }
    
    public void alertTable() {
        Connection conn = getConnection();
        String sql = "alter table hosts add column dbindex integer default 0";
        execute(sql, conn);
        close(conn);
    }

    public ResultSet query(String sql, Connection connection) {
        Statement statement;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            logger.error("SQLException:" + e.getNextException());
        }
        return result;
    }

    public int delete(String sql, Connection connection) {
        Statement statement;
        int result = 0;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("SQLException:" + e.getCause().getMessage());
            //e.printStackTrace();
        }
        return result;
    }

    public void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("SQLException:" + e.getCause().getMessage());
            //e.printStackTrace();
        }
    }
}
