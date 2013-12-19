/**
 * RedisDB.java
 * kevin 2013-2-5
 * @version 0.1
 */
package org.kevin.redis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.kevin.db.sqlite.SqliteDB;
import org.kevin.redis.adapter.ComponentResizeAdapter;
import org.kevin.redis.adapter.MainTreeResizeAdapter;
import org.kevin.redis.comboBox.HistoryComboBox;
import org.kevin.redis.constant.RedisConstants;
import org.kevin.redis.data.vo.Host;
import org.kevin.redis.menu.NoSQLMenu;
import org.kevin.redis.msg.PopMessage;
import org.kevin.redis.operation.DBOperation;
import org.kevin.redis.tree.DBTree;
import org.kevin.redis.window.AddWindow;
import org.kevin.redis.window.CopyWindow;
import org.kevin.redis.window.EditWindow;

/**
 * @author kevin
 * @since jdk1.6
 */
public class RedisDB {
    /**
     * 
     */
    private JFrame frame;
    private static JPanel toolbar_panel, tree_panel, status_panel, dataTable_panel;
    private JTextField ip_textField, port_textField, pwd_textField;
    private JLabel lblHistory, lblIp, lblPort, lbldbIndex, lblPwd;
    private JButton search_btn;
    private JLabel lblOptstatuslb;
    private JTree db_tree;
    private static JScrollPane jsp, toolJsp;
    private static JComboBox history_comboBox;
    private JComboBox jcmbHosts;
    private JComboBox dbIndex_comboBox;
    private final String status = "Status:";

    /**
    * Auto-generated main method to display this JFrame
    */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RedisDB redisDB = new RedisDB();
                    redisDB.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public RedisDB() {
        initGUI();
    }
    
    private void initGUI() {
        frame = new JFrame();
        frame.setTitle("Redis DB");
        //frame.setBounds(100, 100, 669, 486);
        frame.setBounds(200, 200, 720, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (InstantiationException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (UnsupportedLookAndFeelException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        //frame.addComponentListener(new ComponentResizeAdapter(frame));
        
        String src = "/org/kevin/image/nosql.jpg"; 
        //String src = "/org/kevin/image/nosql.png"; 
        Image image;
        try {
            //create the image icon obj
            image = ImageIO.read(this.getClass().getResource(src));
            frame.setIconImage(image);  //set the icon
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        try {
            //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //getContentPane().setLayout(null);
            frame.setTitle("Java NoSQL Client " + RedisConstants.CLIENT_VERSION);
            {
                JMenuBar menu = new NoSQLMenu().initManu();
                frame.setJMenuBar(menu);
                //menu.setBounds(10, 10, 180, 450);
            }
            {
                toolbar_panel = new JPanel();
                toolbar_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                toolbar_panel.setBounds(10, 10, 640, 50);
                //frame.setLocationRelativeTo(null);
            }
            {
//                JScrollPane tools_short = new ToolsPanel().createToolsPanel();
//                frame.getContentPane().add(tools_short);
//                tools_short.setAutoscrolls(true);
//                tools_short.setBounds(230, 10, 220, 300);
                //frame.getContentPane().setLayout(new GridLayout(1, 2, 0, 0));
            }
            {
                tree_panel = new JPanel();
                tree_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                tree_panel.setToolTipText("DB tree");
                tree_panel.addComponentListener(new MainTreeResizeAdapter(tree_panel));// for resize
                //tree_panel.setBounds(10, 50, 200, 400);
                //frame.setLocationRelativeTo(null);
            }
               JPanel  searchPanel = new JPanel();
               searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
               final JTextField searchText = new JTextField("*");
               final JButton filterBtn = new JButton("Filter");
               filterBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String action = e.getActionCommand();
                        //PopMessage.popMsg("searchBtn Name : " + searchBtn.getActionCommand()+ ", action:" + action);
                        if(filterBtn.getActionCommand().equalsIgnoreCase(action)){
                            String filterText = searchText.getText();
                            db_tree = DBTree.getDBTree().getkeys(filterText);
                            jsp = new JScrollPane(db_tree);
                            //jsp = DBTree.getDBTree().getTreePanel(filterText);
                            tree_panel.add(jsp, 0);
                            //jsp.setBounds(10, 70, 200, 380);
                            resize();
                            //int width  = tree_panel.getWidth();
                            //int height = tree_panel.getHeight();
                            //jsp.setPreferredSize(new Dimension(width, height));
                            //tree.setPreferredSize(new Dimension())
                            //jsp.setBounds(10, 70, (width - 20), (height - 80));
                            // comment the updateUI as the resize to do it
                            tree_panel.updateUI();
                            lblOptstatuslb.setText(status + "Query success");
                        }
                    }
               });
               
               searchPanel.add(searchText);
               searchPanel.add(filterBtn);
               //JScrollPane jspTree = new JScrollPane();
               //jspTree.add(searchPanel);
               tree_panel.add(searchPanel);
               searchPanel.setBounds(10, 10, 220, 25);
               searchPanel.setVisible(true);
               
               // Add the operation buttons add delete edit
               JPanel  actionPanel = new JPanel();
               actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
               
               final JButton addBtn = new JButton("ADD");
               //ImageIcon icon = new ImageIcon("src/org/kevin/image/add.png");
               //final JButton addBtn = new JButton(icon);
               //final ImageButton addBtn = new ImageButton(icon);
               addBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       //String action = e.getActionCommand();
                       AddWindow.instance().showWindow();
                       //PopMessage.popMsg("addBtn Name : " + addBtn.getActionCommand()+ ", action:" + action);
                   }
               });
               actionPanel.add(addBtn);
               
               final JButton copyBtn = new JButton("COPY");
               copyBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       //String action = e.getActionCommand();
                       //AddWindow.instance().showWindow();
                       //String inputValue = JOptionPane.showInputDialog("Please input a value");

                       int selectedNode = db_tree.getSelectionCount();
                       if(0 == selectedNode || null == db_tree.getSelectionPath())
                           PopMessage.popMsg("No key was selected");
                       else {
                           Object selected = db_tree.getSelectionModel().getSelectionPath().getLastPathComponent();
                           DefaultMutableTreeNode node = (DefaultMutableTreeNode)selected;
                           if(null!= node && node.isRoot()){
                               PopMessage.popMsg("The Key [" + node.toString() + "] is uneditable" );
                           } else {
                               TreePath parent = db_tree.getSelectionPath().getParentPath();
                               if(null == parent){
                                   PopMessage.popMsg("The Key [" + node.toString() + "] is uneditable" );
                               } else {
                                   //String inputValue = JOptionPane.showInputDialog(null, "Please input a value", "Copy", 1); 
                                   //PopMessage.popMsg("copyBtn Name : " + copyBtn.getActionCommand()+ ", action:" + action+ ", inputValue:" + inputValue );
                                   //if(null != inputValue && !inputValue.isEmpty()){
                                       CopyWindow.instance().showWindow(node.toString());
                                       //System.out.println("show COPY window");

                                       //filterBtn.doClick();
                                   //}
                               }
                           }
                       }
                   }
               });
               actionPanel.add(copyBtn);
               //addBtn.setBounds(10, 5, 20, 10);

               final JButton delBtn = new JButton("DEL");
               delBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       //String action = e.getActionCommand();
                       int selectedNode = db_tree.getSelectionCount();
                       if(0 == selectedNode || null == db_tree.getSelectionPath())
                           PopMessage.popMsg("No key was selected");
                       else {
                           TreePath selectedPath = db_tree.getSelectionPath();
                           Object selectedObj = selectedPath.getPathComponent(selectedNode);
                           //PopMessage.popMsg("selectedNode:" + selectedNode + ", selected:" + selectedObj );
                           JDialog.setDefaultLookAndFeelDecorated(true);
                           int response = JOptionPane.showConfirmDialog(null, "Do you want to delete key [" + selectedObj.toString() + "]", "Confirm",
                                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                           
                           if(JOptionPane.YES_OPTION == response){
                               int affectKeys = new DBOperation().remove(selectedObj.toString());
                               if(affectKeys > 0){
                                   PopMessage.popMsg("Remove the key ["+ selectedObj.toString() +"]' success" );
                               } else {
                                   PopMessage.popWarn("Remove the key ["+ selectedObj.toString() +"]' fail" );
                               }
                               //Robot robottest;
                               //try {
                               //     robottest = new Robot();
                               //     robottest.waitForIdle();
                               //     robottest.mousePress(InputEvent.BUTTON1_MASK );
                               // } catch (AWTException e1) {
                               //     e1.printStackTrace();
                               // } 
                               filterBtn.doClick();
                               //String tableype = RedistDataManagement.getType(selectedObj.toString());
                           } else {
                               // Do nothing
                           }
                       }
                   }
               });
               actionPanel.add(delBtn);
               //delBtn.setBounds(10, 5, 20, 10);

               final JButton editBtn = new JButton("EDIT");
               editBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {

                       //String action = e.getActionCommand();
                       int selectedNode = db_tree.getSelectionCount();
                       if(0 == selectedNode || null == db_tree.getSelectionPath())
                           PopMessage.popMsg("No key was selected");
                       else {
                           //TreePath selectedPath = db_tree.getSelectionPath();
                           //Object selectedObj = selectedPath.getPathComponent(selectedNode);

                           //Object selected = db_tree.getLastSelectedPathComponent();
                           Object selected = db_tree.getSelectionModel().getSelectionPath().getLastPathComponent();
                           DefaultMutableTreeNode node = (DefaultMutableTreeNode)selected;
                           if(null!= node && node.isRoot()){
                               PopMessage.popMsg("The Key [" + node.toString() + "] is uneditable" );
                           } else {
                               TreePath parent = db_tree.getSelectionPath().getParentPath();
                               if(null == parent){
                                   PopMessage.popMsg("The Key [" + node.toString() + "] is uneditable" );
                               } else {
                                   //PopMessage.popMsg("The Key [" + node.toString() + "]" );
                                   EditWindow.instance().showWindow(node.toString());
                                   //EditWindow.instance().showWindow();
                               }
                               //String pathNode = node.getParent().getPath().getLastPathComponent().toString();
                               //RedisTable.updateDetail(pathNode);
                           }
                       }
                   }
               });
               actionPanel.add(editBtn);
               //editBtn.setBounds(10, 5, 20, 10);
               //JScrollPane actionJsp = new JScrollPane();
               //actionJsp.add(actionPanel);
               tree_panel.add(actionPanel);
               //tree_panel.add(actionJsp);
               actionPanel.setBounds(10, 40, 220, 25);
               actionPanel.setVisible(true);
            {
                db_tree = DBTree.getDBTree().createTree();
                //frame.getContentPane().add(jRedisTree);
                //db_tree.setVisible(true);
                db_tree.setBounds(10, 70, 200, 380);
                jsp = new JScrollPane(db_tree);
                //db_tree.setBounds(20, 20, tree_panel.getX(), tree_panel.getY());
                //JScrollPane jspTree = new JScrollPane();
                //jspTree.add(db_tree);
                //jspTree.setVisible(true);
                //tree_panel.add(db_tree);
                tree_panel.add(jsp);
                //int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
                //int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
                //getContentPane().add(jsp, BorderLayout.CENTER);
                //tree_panel.add(jsp);
            }
            {
//                JScrollPane pane = RedisTable.componentInfo();
//                //frame.getContentPane().add(pane);
//                tree_panel.add(pane);
//                tree_panel.setLayout(new GridLayout(1, 2, 0, 0));
//                //pane.setBounds(x, y, width, height);
//                pane.setAutoscrolls(true);
//                pane.setBounds(20, 10, 220, 300);
            }
            {
                status_panel = new JPanel();
                status_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
            }
            {
                dataTable_panel = new JPanel();
                //dataTable_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                dataTable_panel.setBorder(BorderFactory.createTitledBorder("Table Data"));
                dataTable_panel.addComponentListener(new ComponentResizeAdapter(dataTable_panel));
                //frame.add(dataTable_panel);
            }
            {
                // the status process
                lblOptstatuslb = new JLabel(status);
                
//                histBox = new JComboBox();
//                histBox.addItem("default(212)");
//                top_panel.add(histBox);
//                frame.setLocationRelativeTo(null);
                
                lblIp = new JLabel("IP:");
                lblIp.setHorizontalAlignment(SwingConstants.RIGHT);
                frame.setLocationRelativeTo(null);
                
                ip_textField = new JTextField();
                ip_textField.setText("192.168.30.212");
                ip_textField.setColumns(10);
                
                lblPort = new JLabel("Port:");
                lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
                frame.setLocationRelativeTo(null);
                
                port_textField = new JTextField();
                port_textField.setText("6380");
                port_textField.setColumns(10);

                lblPwd = new JLabel("PWD:");
                pwd_textField = new JTextField();
                pwd_textField.setColumns(10);
                
                lbldbIndex = new JLabel("DB:");
                dbIndex_comboBox = new JComboBox();
                for(int i=0; i < 16; i++)
                    dbIndex_comboBox.addItem("" + i);
                
                search_btn = new JButton("Search");
                search_btn.addMouseListener(new MouseAdapter() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        /*
                        String ipStr= ip_textField.getText();
                        String portStr= port_textField.getText();
                        String pwdStr= pwd_textField.getText();
                        String dbIndexStr= dbIndex_comboBox.getSelectedItem().toString();
                        
                        Integer dbIndex = Integer.valueOf(dbIndexStr);
                        RedisConstants.REDIS_SERVER_IP = ipStr;
                        RedisConstants.REDIS_SERVER_PWD = pwdStr;
                        RedisConstants.REDIS_SERVER_DBINDEX = dbIndex;
                        Integer port = Integer.valueOf(portStr);
                        try{
                            RedisConstants.REDIS_SERVER_PORT = "".equals(port)? 6379:port;//6379 is the default port for redis
                            port_textField.setBackground(Color.WHITE);
                        } catch (Exception ex ){
                            port_textField.setBackground(Color.red);
                            lblOptstatuslb.setText(status + "port error");
                            return;
                        }*/
                        constantHostInfo();
                        // add host
                        Host host = new Host();
                        host.setIp(RedisConstants.REDIS_SERVER_IP);
                        host.setPort(RedisConstants.REDIS_SERVER_PORT);
                        //host.setDbIndex(dbIndex);
                        host.setDbIndex(RedisConstants.REDIS_SERVER_DBINDEX);
                        //host.setPwd(pwdStr);
                        host.setPwd(RedisConstants.REDIS_SERVER_PWD);
                        //DerbyDB.getInstance().addHost(host);
                        SqliteDB.getInstance().addHost(host);

                        // fresh the history
                        String newaddObj = host.getIp() + ":" + host.getPort() + ":" + host.getDbIndex() + ":" + host.getPwd();
                        history_comboBox.removeItem(newaddObj);
                        history_comboBox.addItem(newaddObj);
                        history_comboBox.setSelectedItem(newaddObj);
                        
                        db_tree = DBTree.getDBTree().getkeys("*");
                        //db_tree.setBounds(10, 10, 200, 430);

                        //int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
                        //int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
                        //JScrollPane jsp = new JScrollPane(db_tree, v, h);
                        jsp = new JScrollPane(db_tree);
                        //jsp = DBTree.getDBTree().getTreePanel("*");
                        //tree_panel.add(jsp, BorderLayout.CENTER);
                        tree_panel.add(jsp, 0);
                        //jsp.setVisible(true);
                        
                        //jsp.setBounds(10, 70, 200, 380);
                        int width  = tree_panel.getWidth();
                        int height = tree_panel.getHeight();
                        //jsp.setPreferredSize(new Dimension(width, height));
                        //tree.setPreferredSize(new Dimension())
                        jsp.setBounds(10, 70, (width - 20), (height - 80));
                        //resize();
                        
                        //tree_panel.add(db_tree, 0);
                        lblOptstatuslb.setText(status + "Query success");
                        //String  about_content = "Test redis pop msg.";
                        //JOptionPane.showMessageDialog(null, about_content, "redis data", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }

            lblHistory = new JLabel("History:");
            history_comboBox = new JComboBox();
            history_comboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jcmbHosts = (JComboBox) e.getSource();
                    String host = (String) jcmbHosts.getSelectedItem();
                    String[] ipAndPort = host.split(":");
                    ip_textField.setText(ipAndPort[0]);
                    port_textField.setText(ipAndPort[1]);
                    dbIndex_comboBox.setSelectedItem(ipAndPort[2]);
                    if(ipAndPort.length > 3)
                        pwd_textField.setText(ipAndPort[3]);
                    else
                        pwd_textField.setText("");
                    // reinit the db connection pool
                    constantHostInfo();
                    HistoryComboBox.getInstances().reInitConnectPool();
                    
                }
            });
            //List<Host> hosts = DerbyDB.getInstance().gethosts();
            List<Host> hosts = SqliteDB.getInstance().gethosts();
            for(Host host : hosts){
                //System.out.println("[id:" + host.getId() + ", ip:" + host.getIp() + ", pwd:" + host.getPwd() + ", port:" + host.getPort() + "]");
                history_comboBox.addItem(host.getIp() + ":" + host.getPort() + ":" + host.getDbIndex() + ":" + host.getPwd());
            }
            //history_comboBox.addItem("192.168.30.212:6380");
            //history_comboBox.addItem("192.168.30.212:6379");
            //history_comboBox.setSelectedIndex(0);
            
            toolJsp = new JScrollPane();
            JPanel jpa = new JPanel();
            jpa.add(lblHistory);
            jpa.add(history_comboBox);
            //history_comboBox.setBounds(10, 0, 100, 15);
            
            jpa.add(lblIp);
            lblIp.setBounds(180, 0, 50, 15);
            jpa.add(ip_textField);
            //ip_textField.setBounds(250, 0, 100, 15);
            
            jpa.add(lblPort);
            //lblPort.setBounds(260, 0, 50, 15);
            jpa.add(port_textField);
            //port_textField.setBounds(320, 0, 20, 15);
            
            jpa.add(lbldbIndex);
            //lbldbIndex.setBounds(380, 0, 50, 15);
            jpa.add(dbIndex_comboBox);
            //dbIndex_comboBox.setBounds(440, 0, 50, 15);
            
            jpa.add(lblPwd);
            //lblPwd.setBounds(500, 0, 50, 15);
            jpa.add(pwd_textField);
            //pwd_textField.setBounds(600, 0, 50, 15);
            
            jpa.add(search_btn);
            //search_btn.setBounds(660, 0, 100, 15);
            //jpa.setVisible(true);

            //toolJsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            //toolJsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            
            GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
            groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                    .addGroup(groupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                            .addComponent(toolbar_panel, GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                            .addComponent(status_panel, GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(tree_panel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(dataTable_panel, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)))
                        .addContainerGap())
            );
            groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                    .addGroup(groupLayout.createSequentialGroup()
                        .addGap(1)
                        .addComponent(toolbar_panel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                            .addComponent(dataTable_panel, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(tree_panel, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(status_panel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
            

            toolJsp.getViewport().add(jpa);
            
            toolbar_panel.add(toolJsp);
            int tool_width = toolbar_panel.getWidth();
            int tool_height = toolbar_panel.getHeight();
            //System.out.println("tool_width:[" + tool_width + "], tool_height:[" + tool_height + "]");
            
            if(null != history_comboBox)
                tool_height = history_comboBox.getHeight();
            jpa.setBounds(0, 0, tool_width, (tool_height + 20));
            toolJsp.setBounds(0, 0, (tool_width), (tool_height + 20));
            //toolbar_panel.setVisible(true);
            
            GroupLayout gl_toolbar_panel = new GroupLayout(toolbar_panel);
            gl_toolbar_panel.setHorizontalGroup(
                    gl_toolbar_panel.createParallelGroup(Alignment.LEADING)
                        .addGap(0, 640, Short.MAX_VALUE)
                );
            gl_toolbar_panel.setVerticalGroup(
                    gl_toolbar_panel.createParallelGroup(Alignment.LEADING)
                        .addGap(0, 50, Short.MAX_VALUE)
                );
            toolbar_panel.setLayout(gl_toolbar_panel);
            //toolbar_panel.setVisible(true);
            //toolbar_panel.setBounds(x, y, width, height);
             
             
            /*
            gl_toolbar_panel.setHorizontalGroup(
                gl_toolbar_panel.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(history_comboBox, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(lblIp, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(ip_textField, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(lblPort)
                        .addGap(10)
                        .addComponent(port_textField, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(lbldbIndex)
                        .addGap(10)
                        .addComponent(dbIndex_comboBox, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(lblPwd)
                        .addGap(10)
                        .addComponent(pwd_textField, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(search_btn)
                        .addGap(0))
            );
            gl_toolbar_panel.setVerticalGroup(
                gl_toolbar_panel.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addGap(2)
                        .addComponent(lblIp, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addGap(2)
                        .addComponent(ip_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPort, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(port_textField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldbIndex, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbIndex_comboBox, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addGap(2)
                        .addComponent(lblPwd, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addGap(2)
                        .addComponent(pwd_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addGap(2)
                        .addComponent(history_comboBox, GroupLayout.PREFERRED_SIZE, 17, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(gl_toolbar_panel.createSequentialGroup()
                        .addComponent(search_btn)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            toolbar_panel.setLayout(gl_toolbar_panel);
            */
            
            GroupLayout gl_dataTable_panel = new GroupLayout(dataTable_panel);
            gl_dataTable_panel.setHorizontalGroup(
                gl_dataTable_panel.createParallelGroup(Alignment.LEADING)
                    .addGap(0, 332, Short.MAX_VALUE)
            );
            gl_dataTable_panel.setVerticalGroup(
                gl_dataTable_panel.createParallelGroup(Alignment.LEADING)
                    .addGap(0, 463, Short.MAX_VALUE)
            );
            dataTable_panel.setLayout(gl_dataTable_panel);
            
            GroupLayout gl_tree_panel = new GroupLayout(tree_panel);
            gl_tree_panel.setHorizontalGroup(
                gl_tree_panel.createParallelGroup(Alignment.LEADING)
                    .addGap(0, 245, Short.MAX_VALUE)
            );
            gl_tree_panel.setVerticalGroup(
                gl_tree_panel.createParallelGroup(Alignment.LEADING)
                    .addGap(0, 463, Short.MAX_VALUE)
            );
            tree_panel.setLayout(gl_tree_panel);
            
            GroupLayout gl_status_panel = new GroupLayout(status_panel);
            gl_status_panel.setHorizontalGroup(
                gl_status_panel.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_status_panel.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblOptstatuslb, GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                        .addContainerGap())
            );
            gl_status_panel.setVerticalGroup(
                gl_status_panel.createParallelGroup(Alignment.LEADING)
                    .addComponent(lblOptstatuslb, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
            );
            gl_status_panel.setHonorsVisibility(false);
            
            status_panel.setLayout(gl_status_panel);
            frame.getContentPane().setLayout(groupLayout);
            
            /*
            {
                jTextField1 = new JTextField();
                getContentPane().add(jTextField1);
                jTextField1.setText("jTextField1");
                jTextField1.setBounds(133, 7, 161, 28);
            }
            {
                jTextField2 = new JTextField();
                getContentPane().add(jTextField2);
                jTextField2.setText("jTextField2");
                jTextField2.setBounds(133, 49, 161, 28);
            }
            {
                jButton1 = new JButton();
                getContentPane().add(jButton1);
                jButton1.setText("OK");
                jButton1.setBounds(175, 98, 63, 28);
                jButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        jButton1ActionPerformed(evt);
                    }
                });
            }*/
            //frame.pack();

            // make the frame to be loadded at windows center
            Toolkit kit=Toolkit.getDefaultToolkit();
            Dimension screenSize=kit.getScreenSize();
            int screenHeight=screenSize.height;
            int screenWidth=screenSize.width;
            int width = 800;
            int height = 600;
            frame.setLocation( (screenWidth-width)/2, (screenHeight-height)/2 );
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            //setSize(575, 272);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateMiddleTable(JScrollPane scrollPane){
        
    }

    public static void resize(){
        treeResize();
        tollbarResize();
    }

    private void constantHostInfo(){

        String ipStr= ip_textField.getText();
        String portStr= port_textField.getText();
        String pwdStr= pwd_textField.getText();
        String dbIndexStr= dbIndex_comboBox.getSelectedItem().toString();
        
        Integer dbIndex = Integer.valueOf(dbIndexStr);
        RedisConstants.REDIS_SERVER_IP = ipStr;
        RedisConstants.REDIS_SERVER_PWD = pwdStr;
        RedisConstants.REDIS_SERVER_DBINDEX = dbIndex;
        Integer port = Integer.valueOf(portStr);
        try{
            RedisConstants.REDIS_SERVER_PORT = "".equals(port)? 6379:port;//6379 is the default port for redis
            port_textField.setBackground(Color.WHITE);
        } catch (Exception ex ){
            port_textField.setBackground(Color.red);
            lblOptstatuslb.setText(status + "port error");
            return;
        }
    }
    
    private static void treeResize(){
        if(null != jsp){
            int width  = tree_panel.getWidth();
            int height = tree_panel.getHeight();
            //jsp.setPreferredSize(new Dimension(width, height));
            //tree.setPreferredSize(new Dimension())
            jsp.setBounds(10, 70, (width - 20), (height - 90));
            //jsp.updateUI();
        }
        tree_panel.updateUI();
    }
    
    private static void tollbarResize(){
        if(null != toolJsp){
            int width  = toolbar_panel.getWidth();
            int height = toolbar_panel.getHeight();
            if(null != history_comboBox)
                height = history_comboBox.getHeight();
            //jsp.setPreferredSize(new Dimension(width, height));
            //tree.setPreferredSize(new Dimension())
            //System.out.println("tool_width:[" + width + "], tool_height:[" + height + "]");
            toolJsp.setBounds(0, 0, (width), (height + 20));
            //jsp.updateUI();
        }
        toolbar_panel.updateUI();
    }
    
    /**
     * @return the toolbar_panel
     */
    public static final JPanel getToolbar_panel() {
        return toolbar_panel;
    }

    /**
     * @return the tree_panel
     */
    public static final JPanel getTree_panel() {
        return tree_panel;
    }

    /**
     * @return the status_panel
     */
    public static final JPanel getStatus_panel() {
        return status_panel;
    }

    /**
     * @return the dataTable_panel
     */
    public static final JPanel getDataTable_panel() {
        return dataTable_panel;
    }
 
}
