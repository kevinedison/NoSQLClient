/**
 * EditMenu.java
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

import org.kevin.redis.window.PreferencesWindow;

/**
 * @author kevin
 * @since jdk1.6
 */
public class EditMenu implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menu_edit;
    JMenuItem menuItem_option;

    /**
     * 
     */
    public EditMenu() {
    }

    /**
     * 
     */
    public EditMenu(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void init() {

        menu_edit = new JMenu("Edit(E)");
        menu_edit.setMnemonic(KeyEvent.VK_E);
        // add the item to the menu
        menuItem_option = new JMenuItem("Option(O)", KeyEvent.VK_O);
        menuItem_option.addActionListener(this);
        menuItem_option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
        menu_edit.add(menuItem_option);
        menuBar.add(menu_edit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItem_option) {
            // new PreferencesWindow("NoSQL Preferences");
            PreferencesWindow pw = PreferencesWindow.instance();
            pw.showWindow();
            return;
        }
    }
}
