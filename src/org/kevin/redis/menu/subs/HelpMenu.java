/**
 * HelpMenu.java
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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.kevin.redis.constant.RedisConstants;

/**
 * @author kevin
 * @since jdk1.6
 */
public class HelpMenu implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu_help;
    private JMenuItem menuItem_about;

    /**
     * 
     */
    public HelpMenu() {
    }

    /**
     * @param menuBar2
     */
    public HelpMenu(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void init() {

        menu_help = new JMenu("Help(H)");
        menu_help.setMnemonic(KeyEvent.VK_H);
        // add the item to the menu
        menuItem_about = new JMenuItem("About(A)", KeyEvent.VK_A);
        menuItem_about.addActionListener(this);
        menuItem_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                ActionEvent.CTRL_MASK));
        menu_help.add(menuItem_about);
        menuBar.add(menu_help);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == menuItem_about){
          JOptionPane.showMessageDialog(null, "This is redis client done by java.\n Author: kevin edison\n Version: " + RedisConstants.CLIENT_VERSION, "About", JOptionPane.INFORMATION_MESSAGE);
          //JOptionPane.showInternalMessageDialog(this, "This is redis client done by java.\n Author: kevin edison\n Version: 1.0 ", "About", JOptionPane.INFORMATION_MESSAGE);
          return;
      }
    }

}
