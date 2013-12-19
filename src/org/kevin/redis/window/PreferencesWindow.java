/**
 * PreferencesWindow.java
 * kevin 2013-2-18
 * @version 0.1
 */
package org.kevin.redis.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.kevin.redis.constant.RedisConstants;
import org.kevin.redis.panel.PreferencesRightPanel;
import org.kevin.redis.validate.IPValidate;

/**
 * @author kevin
 * @since jdk1.6
 */
public class PreferencesWindow extends JFrame implements ActionListener {

    private static PreferencesWindow prefWindow;
    static JScrollPane treePanel;
    private static JPanel tablePanel;
    private static String preferencesTitle = "NoSQL Preferences";
    private static final long serialVersionUID = -7899889629830144406L;
    // button and button text
    JLabel ip_lable, port_lable;
    JTextField ip_text, port_text;
    JPanel ip_panel, port_panel, btn_panel, control_panel;
    JButton[] btn = new JButton[3];
    String[] btnText = {"Save", "Reset", "Cancel"};
    
    private PreferencesWindow(){
    }
    
    public static PreferencesWindow instance(){
        if(null == prefWindow){
            prefWindow = new PreferencesWindow(preferencesTitle);
        }
        return prefWindow;
    }
    
    private PreferencesWindow(String preferencesTitle) {
        this.setTitle(preferencesTitle);
        //this.getContentPane().setLayout(new GridLayout(1, 2));
        
        treePanel = new JScrollPane();
        //treePanel.setBounds(10, 10, this.getX(), this.getY());
        //treePanel.setLayout(new GridLayout(1, 2));
        
        //scrollPane = new JScrollPane();
        JTree prefTree = preferencesTree();

        //scrollPane.setLayout(new ScrollPaneLayout());
        //treePanel.add(tablePanel);
        //scrollPane.add(prefTree);
        
        treePanel.setViewportView(prefTree);
        this.getContentPane().add(treePanel, BorderLayout.WEST);
        treePanel.setVisible(true);
        //this.getContentPane().add(treePanel);
        //this.getContentPane().add(prefTree, BorderLayout.WEST);
        //scrollPane.setBounds(10, 10, this.getX(), this.getY());
        //prefTree.setBounds(30, 30, this.getX(), this.getY());
        //treePanel.setBounds(30, 30, this.getX(), this.getY());
        //scrollPane.setVisible(true);
        
        //*****for table panel***//
        
        tablePanel = new JPanel();
        //tablePanel.setLayout(new ScrollPaneLayout());
        
        //JTable data_table = PreferencesRightPanel.initJTable();
        //tablePanel.add(data_table);
        
        //JButton remove_btn = new JButton("删除数据");
        //tablePanel.add(remove_btn);
        //tablePanel.setLayout(new GridLayout(1, 1));
        this.getContentPane().add(tablePanel);
        //this.getContentPane().add(tablePanel);
        //tablePanel.setBounds((treePanel.getX() + 10), (treePanel.getY() + 10), this.getX(), this.getY());
        //tablePanel.setLayout(new GridLayout(1, 2));
        //tablePanel.setVisible(true);
        //this.getContentPane().add(prefTree, BorderLayout.WEST);
        //showWindow();
    }
    
    private JTree preferencesTree(){
        String rootNodeTitle = "Preferences";
        String initNodeTitle = "Hosts";
        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        // set the title for the root node
        rootNode.setUserObject(rootNodeTitle);

        DefaultMutableTreeNode initNode = new DefaultMutableTreeNode();
        initNode.setUserObject(initNodeTitle);
        rootNode.add(initNode);

        JTree tree = new JTree();
        // Create the model object for the tree and accepted the root node while generate
        javax.swing.tree.DefaultTreeModel dm = new DefaultTreeModel(rootNode);
        // set the model to the tree and show the loaded node on the tree
        tree.setModel(dm);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String pathNode = e.getPath().getLastPathComponent().toString();
                //JScrollPane rightpanel = PreferencesRightPanel.showHostTable(pathNode);
                //tablePanel = rightpanel;
                //PreferencesWindow.instance().getContentPane().add(rightpanel, BorderLayout.EAST);
                //rightpanel.setLayout(new ScrollPaneLayout() );
                //tablePanel.updateUI();
                //rightpanel.updateUI();
                PreferencesRightPanel.showPreferences(pathNode);
            }
        });
        return tree;
    }

    private void PreferencesWindow(String title, int index) {
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

    public void showWindow(){
        // make the frame to be loadded at windows center
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();
        int screenHeight=screenSize.height;
        int screenWidth=screenSize.width;
        //int width = 300;
        int width = 800;
        //int height = 200;
        int height = 600;
        setLocation( (screenWidth-width)/2, (screenHeight-height)/2 );

        //setBounds(200, 200, 400, 300);
        setSize(600, 480);
        //this.setLocation(100, 100);
        //this.setSize(300, 200);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setVisible(true);
        //this.setAlwaysOnTop(true);
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
 
    /**
     * @return the tablePanel
     */
    public JPanel getTablePanel() {
        return tablePanel;
    }

    class HandlePopWin extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            //e.getWindow().setVisible(false);
            (e.getWindow()).dispose();
            //(new Frame()).setVisible(false);
            //this.windowClosed(e);
        }
    }
}
