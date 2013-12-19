/**
 * Hosts.java
 * kevin 2013-2-11
 * @version 0.1
 */
package org.kevin.redis.data.vo;

/**
 * @author kevin
 * @since jdk1.6
 */
public class Host {

    private int id;
    private String ip;
    private int dbIndex;
    private String pwd;
    private int port;

    /**
     * @return the id
     */
    public final int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(int id) {
        this.id = id;
    }

    /**
     * @return the ip
     */
    public final String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public final void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the pwd
     */
    public final String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     *            the pwd to set
     */
    public final void setPwd(String pwd) {
        this.pwd = pwd;
    }
 
    /**
     * @return the dbIndex
     */
    public final int getDbIndex() {
        return dbIndex;
    }

    /**
     * @param dbIndex the dbIndex to set
     */
    public final void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    /**
     * @return the port
     */
    public final int getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public final void setPort(int port) {
        this.port = port;
    }

}
