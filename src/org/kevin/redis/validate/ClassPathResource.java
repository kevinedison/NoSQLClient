/**
 * ClassPathResource.java
 * 2012 Jan 5, 2012
 */
package org.kevin.redis.validate;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kevin
 * @since Jdk 1.6 
 */
public class ClassPathResource {

    /**
     * @param args
     */
    public static boolean isMobileNO(String mobiles){     
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);     
        System.out.println(m.matches()+"---");     
        return m.matches();     
    }     
    public static void main(String[] args) throws IOException {     
        System.out.println(ClassPathResource.isMobileNO("18616155153"));     
    }   

}
