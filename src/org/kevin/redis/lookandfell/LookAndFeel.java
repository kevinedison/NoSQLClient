/**
 * LookAndFeel.java
 * kevin 2013-4-24
 * @version 0.1
 */
package org.kevin.redis.lookandfell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author kevin
 * @since jdk1.6
 */
public class LookAndFeel extends JFrame {
    private JPanel buttonPanel = null;

    public void launchFrame() {
        //this.setSize(200, 300);
        this.setSize(400, 600);
        buttonPanel = new JPanel();
        // 得到所有安装的观感
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        // new for
        //JLabel lfClass = new JLabel();
        //StringBuilder stb = new StringBuilder();
        for (UIManager.LookAndFeelInfo info : infos) {
            makeButton(info.getName(), info.getClassName());
            System.out.println("LookAndFeels Name:[" + info.getName() + "]");
            System.out.println("LookAndFeels Class:[" + info.getClass() + "]");
            //stb.append(info.getName()).append("\r\n");
        }
        this.add(buttonPanel);
        //lfClass.setText(stb.toString());
        //this.add(lfClass);
        this.setVisible(true);
    }

    private void makeButton(String bName, final String lookName) {
        JButton b = new JButton(bName);
        buttonPanel.add(b);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(lookName);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(LookAndFeel.this);
            }
        });
    }

    public static void main(String[] args) {
        new LookAndFeel().launchFrame();
    }
}
