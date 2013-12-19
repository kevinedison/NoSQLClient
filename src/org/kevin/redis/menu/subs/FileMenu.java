/**
 * FileMenu.java
 * kevin 2013-4-24
 * @version 0.1
 */
package org.kevin.redis.menu.subs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author kevin
 * @since jdk1.6
 */
public class FileMenu implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu_File;
    private JMenuItem menuItem_exit;

    /**
     * 
     */
    public FileMenu() {
    }

    /**
     * @param menuBar
     */
    public FileMenu(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void init() {

        menu_File = new JMenu("File(F)");
        menu_File.setMnemonic(KeyEvent.VK_F);

        menuItem_exit = new JMenuItem("Exit(X)", KeyEvent.VK_X);
        // menuItem_option.addActionListener();
        menuItem_exit.addActionListener(this);
        menuItem_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                ActionEvent.CTRL_MASK));

        menu_File.add(menuItem_exit);
        menuBar.add(menu_File);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItem_exit){
            System.exit(0);
            return;
        }
    }

}
