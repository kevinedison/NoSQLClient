/**
 * Menu.java
 * 2012 Jan 5, 2012
 */
package org.kevin.redis.menu;

import javax.swing.JMenuBar;

import org.kevin.redis.menu.subs.DBMenu;
import org.kevin.redis.menu.subs.EditMenu;
import org.kevin.redis.menu.subs.FileMenu;
import org.kevin.redis.menu.subs.HelpMenu;

/**
 * @author kevin
 * @since Jdk 1.6 
 */
public class NoSQLMenu extends JMenuBar {
    /**
     * 
     */
    private static final long serialVersionUID = 6565758005176550685L;
    //JMenuItem menuItem_exit;
    //JMenuItem menuItem_option;
    //JMenuItem menuItem_about;

    public JMenuBar initManu(){
        JMenuBar menuBar;
        //JMenu menu_File, menu_edit, menu_help;
        menuBar = new JMenuBar();

        //-------------------- file menu ----------------//
        new FileMenu(menuBar).init();
//        menu_File = new JMenu("File(F)");
//        menu_File.setMnemonic(KeyEvent.VK_F);
//
//        menuItem_exit = new JMenuItem("Exit(X)", KeyEvent.VK_X);
//        //menuItem_option.addActionListener();
//        menuItem_exit.addActionListener(this);
//        menuItem_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
//                ActionEvent.CTRL_MASK));
//
//        menu_File.add(menuItem_exit);

        //-------------------- edit menu ----------------//
        //EditMenu.init(menuBar);
        new EditMenu(menuBar).init();
//        menu_edit = new JMenu("Edit(E)");
//        menu_edit.setMnemonic(KeyEvent.VK_E);
//        // add the item to the menu
//        menuItem_option = new JMenuItem("Option(O)", KeyEvent.VK_O);
//        menuItem_option.addActionListener(this);
//        menuItem_option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
//                ActionEvent.CTRL_MASK));
//        menu_edit.add(menuItem_option);

        new DBMenu(menuBar).init();
        //-------------------- help menu ----------------//
        new HelpMenu(menuBar).init();
//        menu_help = new JMenu("Help(H)");
//        menu_help.setMnemonic(KeyEvent.VK_H);
//        // add the item to the menu
//        menuItem_about = new JMenuItem("About(A)", KeyEvent.VK_A);
//        menuItem_about.addActionListener(this);
//        menuItem_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
//                ActionEvent.CTRL_MASK));
//        menu_help.add(menuItem_about);

        // add the menu to the bar
        //menuBar.add(menu_File);
        //menuBar.add(menu_edit);
        //menuBar.add(menu_help);

        return menuBar;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == menuItem_exit){
//            System.exit(0);
//            return;
//        }
//        if(e.getSource() == menuItem_option){
//            //new PreferencesWindow("NoSQL Preferences");
//            PreferencesWindow pw = PreferencesWindow.instance();
//            pw.showWindow();
//            return;
//        }
//        if(e.getSource() == menuItem_about){
//            JOptionPane.showMessageDialog(this, "This is redis client done by java.\n Author: kevin edison\n Version: 1.0 ", "About", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//    }

    /*
     * This class is a pop window for configure the redis info
     */
   /* class RedisConfig extends JFrame implements ActionListener {
        *//**
         * 
         *//*
        private static final long serialVersionUID = -7899889629830144406L;
        // button and button text
        JLabel ip_lable, port_lable;
        JTextField ip_text, port_text;
        JPanel ip_panel, port_panel, btn_panel, control_panel;
        JButton[] btn = new JButton[3];
        String[] btnText = {"Save", "Reset", "Cancel"};
        
        RedisConfig(String title) {
            this.setTitle(title);
            //this.setBounds(400, 300, 200, 200);
            //this.setAlwaysOnTop(true);

            ip_lable = new JLabel("Redis IP:");
            ip_text = new JTextField(15);
            port_lable = new JLabel("Redis Port:");
            port_text = new JTextField(15);

            for (int i = 0; i < btn.length; i++) {
             btn[i] = new JButton(btnText[i]);
             btn[i].setActionCommand(String.valueOf(i));
             btn[i].addActionListener(this);
            }
            ip_panel = new JPanel();
            ip_panel.add(ip_lable);
            ip_panel.add(ip_text);
            //ip_panel.setLayout(null);
            
            port_panel = new JPanel();
            port_panel.add(port_lable);
            port_panel.add(port_text);
            //port_panel.setLayout(null);
            
            btn_panel = new JPanel();
            btn_panel.add(btn[0]);
            btn_panel.add(btn[1]);
            btn_panel.add(btn[2]);
            //btn_panel.setLayout(null);

            control_panel = new JPanel();
            //control_panel.setLayout(new GridLayout(3, 1));
            control_panel.add(ip_panel);
            control_panel.add(port_panel);
            control_panel.add(btn_panel);
            //control_panel.setLayout(null);

            GridLayout grid = new GridLayout(3, 1);
            grid.setHgap(0);//set the component space between h type 设置组件之间的水平距离为h（int型）
            grid.setVgap(0);//set the component space between V type设置组件之间的垂直距离为v（int型）
            control_panel.setLayout(grid);

            this.setLayout(new BorderLayout());
            this.getContentPane().add(control_panel, BorderLayout.CENTER);
            

            // make the frame to be loadded at windows center
            Toolkit kit=Toolkit.getDefaultToolkit();
            Dimension screenSize=kit.getScreenSize();
            int screenHeight=screenSize.height;
            int screenWidth=screenSize.width;
            int width = 300;
            int height = 200;
            setLocation( (screenWidth-width)/2, (screenHeight-height)/2 );

            setSize(width, height);
            
            //this.setLocation(100, 100);
            //this.setSize(300, 200);
            //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            this.setVisible(true);
            this.setAlwaysOnTop(true);
            addWindowListener(new HandlePopWin());
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int i = Integer.parseInt(e.getActionCommand());
            switch (i) {
            case 0:
                btn[0] = (JButton) e.getSource();
                String redisIP, redisPort;
                redisIP = ip_text.getText();
                redisPort = port_text.getText();
                if (null == redisIP || redisIP.length() == 0 || null == redisPort || redisPort.length() == 0)
                    // JOptionPane.showMessageDialog(this, "IP or port Can't be null！");
                    JOptionPane.showMessageDialog(this, "IP or port Can't be null！", "Warn", JOptionPane.ERROR_MESSAGE);
                else {
                    int port = Integer.valueOf(redisPort);
                    if (IPValidate.isIP(redisIP) && (port > 0 && port < 65535)) {
                        JOptionPane.showMessageDialog(this, "Set success!", "save", JOptionPane.INFORMATION_MESSAGE);//.showMessageDialog(this, "Save success!");
                        RedisConstants.REDIS_SERVER_IP = redisIP;
                        RedisConstants.REDIS_SERVER_PORT = port;
                        break;
                    }
                    else{
                        //JOptionPane.showMessageDialog(this, "IP or port unvaliad，please check!");
                        JOptionPane.showMessageDialog(this, "IP or port unvaliad，please check!", "Warn", JOptionPane.ERROR_MESSAGE);
                        ip_text.setText("");
                        port_text.setText("");
                        ip_text.setFocusable(true);
                        break;
                    }
                }
            case 1:
                btn[1] = (JButton) e.getSource();
                ip_text.setText("");
                port_text.setText("");
                ip_text.setFocusable(true);
                break;
            case 2:
                btn[2] = (JButton) e.getSource();
                this.setVisible(false);
                this.dispose();
                break;
            }
            
        }
    }

    class HandlePopWin extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            //e.getWindow().setVisible(false);
            (e.getWindow()).dispose();
            //(new Frame()).setVisible(false);
            //this.windowClosed(e);
        }
    }*/
}
