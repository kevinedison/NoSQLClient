/**
 * SimpleApp.java
 * kevin 2013-2-20
 * @version 0.1
 */
package org.kevin.db.derby.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author kevin
 * @since jdk1.6
 */
public class SimpleApp {
    /* the default framework is embedded*/
    private String framework = "embedded";
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    
    public static void main(String[] args)
    {
        new SimpleApp().go(args);
        System.out.println("SimpleApp finished");
    }
   
    void go(String[] args)
    {
       
        parseArguments(args);
        System.out.println("SimpleApp starting in " + framework + " mode");
        /* load the desired JDBC driver */
        loadDriver();
       
        Connection conn = null;
        ArrayList statements = new ArrayList(); //list of Statements, PreparedStatements
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
         Properties props = new Properties(); // connection properties
        // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");
            String dbName = "derbyDB"; // the name of the database
            conn = DriverManager.getConnection(protocol + dbName+ ";create=true", props);
            System.out.println("Connected to and created database " + dbName);
            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
            conn.setAutoCommit(false);
            /* Creating a statement object that we can use for running various
             * SQL statements commands against the database.*/
            s = conn.createStatement();
            statements.add(s);
            // We create a table...
            s.execute("create table location(num int, addr varchar(40))");
            System.out.println("Created table location");
            // and add a few rows...
            
            // parameter 1 is num (int), parameter 2 is addr (varchar)
            psInsert = conn.prepareStatement(
                        "insert into location values (?, ?)");
            statements.add(psInsert);
            psInsert.setInt(1, 1956);
            psInsert.setString(2, "Webster St.");
            psInsert.executeUpdate();
            System.out.println("Inserted 1956 Webster");
            psInsert.setInt(1, 1910);
            psInsert.setString(2, "Union St.");
            psInsert.executeUpdate();
            System.out.println("Inserted 1910 Union");
            // Let's update some rows as well...
            // parameter 1 and 3 are num (int), parameter 2 is addr (varchar)
            psUpdate = conn.prepareStatement(
                     "update location set num=?, addr=? where num=?");
            statements.add(psUpdate);
            psUpdate.setInt(1, 180);
            psUpdate.setString(2, "Grand Ave.");
            psUpdate.setInt(3, 1956);
            psUpdate.executeUpdate();
            System.out.println("Updated 1956 Webster to 180 Grand");
            psUpdate.setInt(1, 300);
            psUpdate.setString(2, "Lakeshore Ave.");
            psUpdate.setInt(3, 180);
            psUpdate.executeUpdate();
            System.out.println("Updated 180 Grand to 300 Lakeshore");
            /*
               We select the rows and verify the results.
             */
            rs = s.executeQuery("SELECT num, addr FROM location ORDER BY num");
            int number; // street number retrieved from the database
            boolean failure = false;
            if (!rs.next())
            {
                failure = true;
                reportFailure("No rows in ResultSet");
            }
            if ((number = rs.getInt(1)) != 300)
            {
                failure = true;
                reportFailure("Wrong row returned, expected num=300, got " + number);
            }
            if (!rs.next())
            {
                failure = true;
                reportFailure("Too few rows");
            }
            if ((number = rs.getInt(1)) != 1910)
            {
                failure = true;
                reportFailure("Wrong row returned, expected num=1910, got " + number);
            }
            if (rs.next())
            {
                failure = true;
                reportFailure("Too many rows");
            }
            if (!failure) {
                System.out.println("Verified the rows");
            }
            // delete the table
            s.execute("drop table location");
            System.out.println("Dropped table location");
            /*
               We commit the transaction. Any changes will be persisted to
               the database now.
             */
            conn.commit();
            System.out.println("Committed the transaction");
          
            if (framework.equals("embedded"))
            {
                try
                {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                }
                catch (SQLException se)//关闭数据库时会产生异常
                {
                    if (( (se.getErrorCode() == 50000)
                && ("XJ015".equals(se.getSQLState()) ))) {//这是正常关闭
                        // we got the expected exception
                System.out.println("Derby shut down normally");
               // Note that for single database shutdown, the expected
              // SQL state is "08006", and the error code is 45000.
                    } else {
               // if the error code or SQLState is different, we have
                        // an unexpected exception (shutdown failed)
               System.err.println("Derby did not shut down normally");
                        printSQLException(se);
                    }
                }
            }
        }
        catch (SQLException sqle)
        {
            printSQLException(sqle);
        } finally {
       // release all open resources to avoid unnecessary memory usage
            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement)statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    printSQLException(sqle);
                }
            }
            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
    }
    private void loadDriver() {
        
        try {
            Class.forName(driver).newInstance();
            System.out.println("Loaded the appropriate driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("\nUnable to load the JDBC driver " + driver);
            System.err.println("Please check your CLASSPATH.");
            cnfe.printStackTrace(System.err);
        } catch (InstantiationException ie) {
            System.err.println("\nUnable to instantiate the JDBC driver " + driver);
            ie.printStackTrace(System.err);
        } catch (IllegalAccessException iae) {
            System.err.println(
                "\nNot allowed to access the JDBC driver " + driver);
            iae.printStackTrace(System.err);
        }
    }
    /**
     * Reports a data verification failure to System.err with the given message.
     *
     * @param message A message describing what failed.
     */
    private void reportFailure(String message) {
        System.err.println("\nData verification failed:");
        System.err.println('\t' + message);
    }
    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
   
    private void parseArguments(String[] args)
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient"))
            {
                framework = "derbyclient";
                driver = "org.apache.derby.jdbc.ClientDriver";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }
}
