/**
 * DBCopyWindow.java
 * kevin 2013-4-25
 * @version 0.1
 */
package org.kevin.redis.window.dbcopy;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * @author kevin
 * @since jdk1.6
 */
public class DBCopyWindow extends JFrame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1977098215935047379L;
    private static String dbCopyTitle = "DB Copy";
    private static DBCopyWindow dbCopyWindow;

    private DBCopyWindow() {
    }

    public static DBCopyWindow instance() {
        if (null == dbCopyWindow) {
            dbCopyWindow = new DBCopyWindow(dbCopyTitle);
        }
        return dbCopyWindow;
    }

    /**
     * 
     */
    private DBCopyWindow(String dbCopyTitle) {
        this.setTitle(dbCopyTitle);
    }

    public void showWindow() {
        // make the frame to be loadded at windows center
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        // int width = 300;
        int width = 800;
        // int height = 200;
        int height = 600;
        setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);

        // setBounds(200, 200, 400, 300);
        setSize(600, 480);
        // this.setLocation(100, 100);
        // this.setSize(300, 200);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        // this.setAlwaysOnTop(true);
        addWindowListener(new HandlePopWin());
    }

    class HandlePopWin extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            //e.getWindow().setVisible(false);
            (e.getWindow()).dispose();
            //(new Frame()).setVisible(false);
            //this.windowClosed(e);
        }
    }
    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
