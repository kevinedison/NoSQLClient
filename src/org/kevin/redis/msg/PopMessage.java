/**
 * PopMessage.java
 * kevin 2013-2-5
 * @version 0.1
 */
package org.kevin.redis.msg;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * @author kevin
 * @since jdk1.6
 */
public class PopMessage {

    public static void popMsg(String msg){
        JOptionPane.showMessageDialog(null, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void popWarn(String msg){
        JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void popError(String msg){
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static int popConfirm(String msg){
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Do you want to " + msg, "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if (response == JOptionPane.NO_OPTION) {
//            System.out.println("No button clicked");
//        } else if (response == JOptionPane.YES_OPTION) {
//            System.out.println("Yes button clicked");
//        } else if (response == JOptionPane.CLOSED_OPTION) {
//            System.out.println("JOptionPane closed");
//        }
        return response;
    }
    
}
