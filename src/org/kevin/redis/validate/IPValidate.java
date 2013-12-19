/**
 * IPValidate.java
 * 2012 Jan 5, 2012
 */
package org.kevin.redis.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kevin
 * @since Jdk 1.6
 */
public class IPValidate {
    
    public static void main(String args[]) {
        /*
        ipv4，如果需要依次group出来的话
        ^([01]?dd?|2[0-4]d|25[0-5]).([01]?dd?|2[0-4]d|25[0-5]). ([01]?dd?|2[0-4]d|25[0-5]).([01]?dd?|2[0-4]d|25[0-5])$

        如果只是验证，可以精简为
        ^(([01]?dd?|2[0-4]d|25[0-5]).){3}([01]?dd?|2[0-4]d|25[0-5])$
        */
        
        //p("127.0.0.1".matches("[0,255]{1,3}.[0,255]{1,3}.[0,255]{1,3}.[0,255]{1,3}"));
        //String IpRule = "^(([01]?dd?|2[0-4]d|25[0-5]).){3}([01]?dd?|2[0-4]d|25[0-5])$";
        //p("127.0.0.1".matches(""));
       // System.out.println(ClassPathResource.isMobileNO("18616155153"));
        System.out.println(IPValidate.isIP("192.168.30.7"));
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,D])|(18[0,5-9]))d{8}$");     
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

    public static boolean isIP(String ip) {
        System.out.println("Given Ip:" + ip);
        //Pattern p = Pattern.compile("[0-255]+.[0-255]+.[0-255]+.[0-255]+");
        Pattern p = Pattern.compile("[0-9]+.[0-9]+.[0-9]+.[0-9]+");
        //Pattern p = Pattern.compile("((25[0-5]|2[0-4]d|1dd|[1-9]d|d).){3}(25[0-5]|2[0-4]d|1dd|[1-9]d|[1-9])");
        //Pattern p = Pattern.compile("^([01]?dd?|2[0-4]d|25[0-5]).([01]?dd?|2[0-4]d|25[0-5]).([01]?dd?|2[0-4]d|25[0-5]).([01]?dd?|2[0-4]d|25[0-5])$");
        //Pattern p = Pattern.compile("^(([01]?dd?|2[0-4]d|25[0-5]).){3}([01]?dd?|2[0-4]d|25[0-5])$");
        Matcher m = p.matcher(ip);
        System.out.println(m.matches()+"---");
        return m.matches();
    }
}
