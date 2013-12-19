/**
 * DBMenu.java
 * kevin 2013-4-25
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

import org.kevin.redis.window.dbcopy.DBCopyWindow;

/**
 * @author kevin
 * @since jdk1.6
 */
public class DBMenu implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menu_db;
    JMenuItem menuItem_option;
    
    /**
     * 
     */
    public DBMenu() {
    }

    /**
     * @param menuBar2
     */
    public DBMenu(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void init() {

        menu_db = new JMenu("DB(D)");
        menu_db.setMnemonic(KeyEvent.VK_D);
        // add the item to the menu
        menuItem_option = new JMenuItem("DB COPY(B)", KeyEvent.VK_B);
        menuItem_option.addActionListener(this);
        menuItem_option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
                ActionEvent.CTRL_MASK));
        menu_db.add(menuItem_option);
        menuBar.add(menu_db);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItem_option) {
            DBCopyWindow.instance().showWindow();
        }
    }

}
