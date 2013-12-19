/**
 * AddWindow.java
 * kevin 2013-3-12
 * @version 0.1
 */
package org.kevin.redis.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.kevin.redis.manage.HashMan;
import org.kevin.redis.manage.ListMan;
import org.kevin.redis.manage.SetMan;
import org.kevin.redis.manage.StringMan;
import org.kevin.redis.msg.PopMessage;

/**
 * @author kevin
 * @since jdk1.6
 */
public class AddWindow extends JFrame implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 6004860134205994966L;
    private static AddWindow addWindow;
    private static String addWindowTitle = "Add window";
    private static JLabel addTypeLbl = new JLabel("Table Type:");
    //private static JLabel tableDetailLbl = new JLabel("Table Detail:");
    private JComboBox<String> type_comboBox = null;
    JScrollPane typejsp, detailjsp;
    JPanel comboBoxPanel = null;
    JTable table = null;
    DefaultTableModel defaultModel = null;
    private String selectedTableType = "Set";

    //public static void main(String[] args) {
    //    new AddWindow().instance().showWindow();
    //}

    private AddWindow() {
        setTitle(addWindowTitle);

        JPanel selectPanle = new JPanel(); 
        selectPanle.setLayout(new BoxLayout(selectPanle, BoxLayout.X_AXIS));
        // typejsp = new JScrollPane();
        // JLabel addTypeLbl = new JLabel("Table Type:");
        comboBoxPanel = new JPanel();
        // comboBoxPanel.setSize(100, 50);
        // comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));
        // comboBoxPanel.setVisible(true);

        type_comboBox = new JComboBox<String>();
        type_comboBox.addItem("Set");
        type_comboBox.addItem("Hash");
        type_comboBox.addItem("List");
        type_comboBox.addItem("String");
        type_comboBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox selectedObj = (JComboBox)ae.getSource();
                //PopMessage.popMsg("type_comboBox selected : " + selectedObj.getSelectedItem());
                selectedTableType =  selectedObj.getSelectedItem().toString();
                showDiffContents(selectedTableType);
            }
        });
        selectPanle.add(addTypeLbl);
        selectPanle.add(type_comboBox);
        //type_comboBox.setPreferredSize(new Dimension(100,50));
        type_comboBox.setLocation(80, 20);
        
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(selectPanle, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addTypeLbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(comboBoxPanel, GroupLayout.PREFERRED_SIZE, 600,GroupLayout.PREFERRED_SIZE)
                );
        hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        //hGroup.addGroup(layout.createParallelGroup(Alignment.LEADING)
        //        .addComponent(type_comboBox, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(comboBoxPanel, GroupLayout.PREFERRED_SIZE, 680,GroupLayout.PREFERRED_SIZE)
        //        );
        //hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup().addComponent(comboBoxPanel, GroupLayout.PREFERRED_SIZE, 300,GroupLayout.PREFERRED_SIZE));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                //.addComponent(addTypeLbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(type_comboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
                .addComponent(selectPanle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(comboBoxPanel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                );
        vGroup.addGap(5);
        layout.setVerticalGroup(vGroup);

        showDiffContents("Set");
        // comboBoxPanel.setLayout(layout);
        // type_comboBox.setPreferredSize(new Dimension(50,50));
        // comboBoxPanel.setPreferredSize(new Dimension(50,50));
        // typejsp.add(addTypeLbl);
        // comboBoxPanel.add(addTypeLbl);
        // addTypeLbl.setBounds(10, 0, 50, 50);

        // typejsp.add(type_comboBox);
        // comboBoxPanel.add(typejsp);
        // comboBoxPanel.add(type_comboBox);
        // type_comboBox.setBounds(60, 0, 50, 50);
        // type_comboBox.setPreferredSize(new Dimension(50,50));

        // this.getContentPane().add(typejsp, BorderLayout.NORTH);
        // this.getContentPane().add(comboBoxPanel);
        // comboBoxPanel.setPreferredSize(new Dimension(30,50));

        // comboBoxPanel.setVisible(true);
        // addTypeLbl.setVisible(true);
        // comboBoxPanel.setBounds(10, 10, 100, 200);
        // typejsp.updateUI();
        // typejsp.setVisible(true);

        // detailjsp = new JScrollPane();
        // JPanel detailPanel = new JPanel();
        // detailPanel.add(tableDetailLbl);
        // detailjsp.add(detailPanel);
        // this.getContentPane().add(detailPanel, BorderLayout.SOUTH);
        // detailjsp.setBounds(10, 50, 400, 100);
        // detailjsp.updateUI();
        // PopMessage.popMsg("Create the add window");
    };

    public void showDiffContents(String tableType) {
        comboBoxPanel.removeAll();

        //JLabel tableTypelbl = new JLabel("Add type:" + tableType);

        //comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));
        //comboBoxPanel.add(tableTypelbl);
        
        // init the ui
        if("set".equalsIgnoreCase(tableType)){
            initSetTable(tableType);
        } else if("list".equalsIgnoreCase(tableType)){
            initListTable(tableType);
        } else if("hash".equalsIgnoreCase(tableType)){
            initHashTable(tableType);
        } else if("string".equalsIgnoreCase(tableType)){
            initStringTable(tableType);
        } else {
            PopMessage.popMsg("Unsupported table type");
        }
        // fresh
        comboBoxPanel.updateUI();
    }

    public void initHashTable(final String tableType){

        JLabel tableTypelbl = new JLabel("Add type:");
        JLabel tableTypevalue = new JLabel("[" + tableType + "]");
        
        JLabel tableNamelbl = new JLabel("Table Key:" );
        final JTextField tableNameText = new JTextField();
        
        JLabel strTableValuelbl = new JLabel("Table value:" );
        //final JTextField strTableNameText = new JTextField();
        // set table
        String[] taskHeaders = { "Field", "Value", "Type" };
        String[][] setInfo = {{"key", "", ""}};
        
        defaultModel = new DefaultTableModel(setInfo, taskHeaders);
        
        table = new JTable(defaultModel);
        table.setName(tableType + "<" + tableNameText.getText() + ">");
        AlignColumn(table, taskHeaders[0] , SwingConstants.CENTER);

        JScrollPane panel = new JScrollPane();
        panel.setViewportView(table);
        
        JButton addSetBtn = new JButton("Save Data");
        addSetBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = tableNameText.getText();
                if(key.isEmpty()){
                    PopMessage.popMsg("Added type:[" + tableType + "] Table Key is required");
                } else {

                    Map<String, String> vMap = new HashMap<String, String>();
                    int rows = table.getRowCount();
                    //table.getCellEditor(0, 0).getCellEditorValue();
                    String cellField = null;
                    String cellValue = null;
                    for(int i = 0; i < rows; i ++){
                        cellField = (String)  table.getValueAt(i, 0);
                        cellValue = (String) table.getValueAt(i, 1);
                        //System.out.println("Get cellValue:[" + cellValue +"] at:["+ i + ", 0]");
                        vMap.put(cellField, cellValue);
                    }
                    //StringBuilder values = new StringBuilder();
                    //for(String value: vectorValues){
                    //System.out.println("Get cellValue:[" + value +"] from vector");
                    //    values.append(value).append(",");
                    // }
                    //PopMessage.popMsg("Added type:[" + tableType + "] key:[" + key + "] value:[ ... ]");
                    //new HashMan().add(key, vMap);
                    int effectRow = new HashMan().add(key, vMap);
                    if(effectRow > 0){
                        PopMessage.popMsg("Save data for key:[" + key + "] success");
                    } else {
                        PopMessage.popMsg("Save data for key:[" + key + "] fail");
                    }
                }
            }
        });

        //JPanel cellPanel = new JPanel();
        //cellPanel.add(addSetBtn);
        
        JPanel btnPanel = new JPanel();
        //btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(addSetBtn);
        //JScrollPane btnPanel = new JScrollPane();
        //addSetBtn.setSize(60, 20);
        
        JButton addRow = new JButton("Add Row");
        btnPanel.add(addRow);
        addRow.addActionListener(this);
        //addRow.setSize(60, 20);
        
        JButton addColumn = new JButton("Add Column");
        //btnPanel.add(addColumn);
        addColumn.addActionListener(this);
        //addColumn.setSize(60, 20);
        
        JButton removeRow = new JButton("Remove Row");
        btnPanel.add(removeRow);
        removeRow.addActionListener(this);
        //removeRow.setSize(60, 20);
        
        JButton removeColumn = new JButton("Remove Column");
        //btnPanel.add(removeColumn);
        removeColumn.addActionListener(this);
        //removeColumn.setSize(60, 20);

        GroupLayout layout = new GroupLayout(comboBoxPanel);
        comboBoxPanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                );
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
        //        .addComponent(addSetBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addRow, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addColumn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeColumn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                );
        //hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup()
        //        .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        //        );
        //btnPanel.setAlignmentX(SwingConstants.RIGHT);
        hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE));
        //hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE));
        //vGroup.addGap(5);
        //vGroup.addGroup(layout.createParallelGroup()
        //        .addComponent(addSetBtn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addRow, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        //        );
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                );
        //vGroup.addGap(5);
        //vGroup.addGroup(layout.createParallelGroup().addComponent(addRow, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        layout.setVerticalGroup(vGroup);
        
    }
    
    public void initListTable(final String tableType){

        JLabel tableTypelbl = new JLabel("Add type:");
        JLabel tableTypevalue = new JLabel("[" + tableType + "]");
        
        JLabel tableNamelbl = new JLabel("Table Key:" );
        final JTextField tableNameText = new JTextField();
        
        JLabel strTableValuelbl = new JLabel("Table value:" );
        //final JTextField strTableNameText = new JTextField();
        // set table
        String[] taskHeaders = { "Index", "Value", "Type" };
        String[][] setInfo = {{"1", "", ""}};
        
        defaultModel = new DefaultTableModel(setInfo, taskHeaders);
        
        table = new JTable(defaultModel);
        table.setName(tableType + "<" + tableNameText.getText() + ">");
        AlignColumn(table, taskHeaders[0] , SwingConstants.CENTER);

        JScrollPane panel = new JScrollPane();
        panel.setViewportView(table);
        
        JButton addSetBtn = new JButton("Save Data");
        addSetBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = tableNameText.getText();
                if(key.isEmpty()){
                    PopMessage.popMsg("Added type:[" + tableType + "] Table Key is required");
                } else {

                    List<String> vlist = new ArrayList<String>();
                    int rows = table.getRowCount();
                    //table.getCellEditor(0, 0).getCellEditorValue();
                    String cellValue = null;
                    for(int i = 0; i < rows; i ++){
                        //cellValue = (String) table.getCellEditor(i, 1).getCellEditorValue();
                        cellValue = (String) table.getValueAt(i, 1);
                        //System.out.println("Get cellValue:[" + cellValue +"] at:["+ i + ", 0]");
                        vlist.add(i, cellValue);
                    }
                    //StringBuilder values = new StringBuilder();
                    //for(String value: vectorValues){
                    //System.out.println("Get cellValue:[" + value +"] from vector");
                    //    values.append(value).append(",");
                    // }
                    //PopMessage.popMsg("Added type:[" + tableType + "] key:[" + tableNameText.getText() + "] value:[ ... ]");
                    //new SetMan().add(key, vlist);
                    int effectRow = new ListMan().add(key, vlist);
                    if(effectRow > 0){
                        PopMessage.popMsg("Save data for key:[" + key + "] success");
                    } else {
                        PopMessage.popMsg("Save data for key:[" + key + "] fail");
                    }
                }
                //PopMessage.popMsg("Added type:[" + tableType + "] key:[" + tableNameText.getText() + "] value:[" + strTableNameText.getText() + "]");
            }
        });

        //JPanel cellPanel = new JPanel();
        //cellPanel.add(addSetBtn);
        
        JPanel btnPanel = new JPanel();
        //btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(addSetBtn);
        //JScrollPane btnPanel = new JScrollPane();
        //addSetBtn.setSize(60, 20);
        
        JButton addRow = new JButton("Add Row");
        btnPanel.add(addRow);
        addRow.addActionListener(this);
        //addRow.setSize(60, 20);
        
        JButton addColumn = new JButton("Add Column");
        //btnPanel.add(addColumn);
        addColumn.addActionListener(this);
        //addColumn.setSize(60, 20);
        
        JButton removeRow = new JButton("Remove Row");
        btnPanel.add(removeRow);
        removeRow.addActionListener(this);
        //removeRow.setSize(60, 20);
        
        JButton removeColumn = new JButton("Remove Column");
        //btnPanel.add(removeColumn);
        removeColumn.addActionListener(this);
        //removeColumn.setSize(60, 20);

        GroupLayout layout = new GroupLayout(comboBoxPanel);
        comboBoxPanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                );
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
        //        .addComponent(addSetBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addRow, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addColumn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeColumn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                );
        //hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup()
        //        .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        //        );
        //btnPanel.setAlignmentX(SwingConstants.RIGHT);
        hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE));
        //hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE));
        //vGroup.addGap(5);
        //vGroup.addGroup(layout.createParallelGroup()
        //        .addComponent(addSetBtn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addRow, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        //        );
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                );
        //vGroup.addGap(5);
        //vGroup.addGroup(layout.createParallelGroup().addComponent(addRow, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        layout.setVerticalGroup(vGroup);
        
    }
    
    public void initSetTable(final String tableType){

        JLabel tableTypelbl = new JLabel("Add type:");
        JLabel tableTypevalue = new JLabel("[" + tableType + "]");
        
        JLabel tableNamelbl = new JLabel("Table Key:" );
        final JTextField tableNameText = new JTextField();
        
        JLabel strTableValuelbl = new JLabel("Table value:" );
        //final JTextField strTableNameText = new JTextField();
        // set table
        String[] taskHeaders = { "Field", "Value", "Type" };
        String[][] setInfo = {{"", "", ""}};
        
        defaultModel = new DefaultTableModel(setInfo, taskHeaders);
        
        table = new JTable(defaultModel);
        table.setName(tableType + "<" + tableNameText.getText() + ">");
        AlignColumn(table, taskHeaders[0] , SwingConstants.CENTER);

        JScrollPane panel = new JScrollPane();
        panel.setViewportView(table);
        
        JButton addSetBtn = new JButton("Save Data");
        addSetBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = tableNameText.getText();
                if(key.isEmpty()){
                    PopMessage.popMsg("Added type:[" + tableType + "] Table Key is required");
                } else {
                    Vector<String> vectorValues = new Vector<String>();
                    int rows = table.getRowCount();
                    //table.getCellEditor(0, 0).getCellEditorValue();
                    String cellValue = null;
                    for(int i = 0; i < rows; i ++){
                        //cellValue = (String) table.getCellEditor(i, 1).getCellEditorValue();
                        cellValue = (String) table.getValueAt(i, 1);
                        //System.out.println("Get cellValue:[" + cellValue +"] at:["+ i + ", 0]");
                        vectorValues.add(i, cellValue);
                    }
                    //StringBuilder values = new StringBuilder();
                    //for(String value: vectorValues){
                    //System.out.println("Get cellValue:[" + value +"] from vector");
                    //    values.append(value).append(",");
                    // }
                    //PopMessage.popMsg("Added type:[" + tableType + "] key:[" + tableNameText.getText() + "] value:[" + values.toString() + "]");
                    //new SetMan().add(key, vectorValues);
                    int effectRow = new SetMan().add(key, vectorValues);
                    if(effectRow > 0){
                        PopMessage.popMsg("Save data for key:[" + key + "] success");
                    } else {
                        PopMessage.popMsg("Save data for key:[" + key + "] fail");
                    }
                }
            }
        });

        //JPanel cellPanel = new JPanel();
        //cellPanel.add(addSetBtn);
        
        JPanel btnPanel = new JPanel();
        //btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(addSetBtn);
        //JScrollPane btnPanel = new JScrollPane();
        //addSetBtn.setSize(60, 20);
        
        JButton addRow = new JButton("Add Row");
        btnPanel.add(addRow);
        addRow.addActionListener(this);
        //addRow.setSize(60, 20);
        
        JButton addColumn = new JButton("Add Column");
        //btnPanel.add(addColumn);
        addColumn.addActionListener(this);
        //addColumn.setSize(60, 20);
        
        JButton removeRow = new JButton("Remove Row");
        btnPanel.add(removeRow);
        removeRow.addActionListener(this);
        //removeRow.setSize(60, 20);
        
        JButton removeColumn = new JButton("Remove Column");
        //btnPanel.add(removeColumn);
        removeColumn.addActionListener(this);
        //removeColumn.setSize(60, 20);

        GroupLayout layout = new GroupLayout(comboBoxPanel);
        comboBoxPanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                );
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
        //        .addComponent(addSetBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addRow, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addColumn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeColumn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                );
        //hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup()
        //        .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        //        );
        //btnPanel.setAlignmentX(SwingConstants.RIGHT);
        hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE));
        //hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE));
        //vGroup.addGap(5);
        //vGroup.addGroup(layout.createParallelGroup()
        //        .addComponent(addSetBtn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addRow, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(addColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeRow, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                //.addComponent(removeColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        //        );
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(btnPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                );
        //vGroup.addGap(5);
        //vGroup.addGroup(layout.createParallelGroup().addComponent(addRow, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        layout.setVerticalGroup(vGroup);
        
    }
    
    public void initStringTable(final String tableType){

        JLabel tableTypelbl = new JLabel("Add type:");
        JLabel tableTypevalue = new JLabel("[" + tableType + "]");
        
        JLabel tableNamelbl = new JLabel("Table Key:" );
        final JTextField tableNameText = new JTextField();
        //comboBoxPanel.add(tableNamelbl);
        //comboBoxPanel.add(tableName);
        
        JLabel strTableValuelbl = new JLabel("Table value:" );
        final JTextField strTableNameText = new JTextField();
        
        JButton  addStrBtn = new JButton("Save Data");
        //addStrBtn.addActionListener(this);
        addStrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = tableNameText.getText();
                String value = strTableNameText.getText();
                //int effectRows = new StringMan().add(key, value);
                int effectRow = new StringMan().add(key, value);
                if(effectRow > 0){
                    PopMessage.popMsg("Save data for key:[" + key + "] success");
                } else {
                    PopMessage.popMsg("Save data for key:[" + key + "] fail");
                }
                //if(effectRows > 0){
                //    PopMessage.popMsg("Added type:[" + tableType + "] key:[" + key + "] value:[" + value + "] success");
                //} else {
                //    PopMessage.popWarn("Added type:[" + tableType + "] key:[" + key + "] value:[" + value + "] Fail");
                //}
            }
        });
        
        GroupLayout layout = new GroupLayout(comboBoxPanel);
        comboBoxPanel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addComponent(strTableNameText, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addComponent(addStrBtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE));
        //hGroup.addGap(5);
        //hGroup.addGroup(layout.createParallelGroup().addComponent(addbtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableTypelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableTypevalue, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(tableNamelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(tableNameText, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(strTableValuelbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addComponent(strTableNameText, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(addStrBtn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        vGroup.addGap(5);
        layout.setVerticalGroup(vGroup);
        
        //comboBoxPanel.add(strTableValuelbl);
        //comboBoxPanel.add(strTableNameText);
    }

    private static void AlignColumn(JTable comp, String column, final int position){
         comp.getColumn(column).setCellRenderer(
                new DefaultTableCellRenderer() { // rewrite setValue method
                    private static final long serialVersionUID = 1L;

                    public void setValue(Object value) {
                        this.setHorizontalAlignment(position);
                        super.setValue(value);
                    }
                });
    }
    
    public static AddWindow instance() {
        if (null == addWindow)
            addWindow = new AddWindow();
        return addWindow;
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
        setSize(680, 480);
        // this.setLocation(100, 100);
        // this.setSize(300, 200);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        // this.setAlwaysOnTop(true);
        addWindowListener(new HandlePopWin());
    }
 
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Column")){
            PopMessage.popMsg("Invalid new column for this window");
            //defaultModel.addColumn("AddColumn");
        }
        if (e.getActionCommand().equals("Add Row"))
            defaultModel.addRow(new Vector());
        if (e.getActionCommand().equals("Remove Column")) {
            PopMessage.popMsg("Invalid new column for this window");
//            int columncount = defaultModel.getColumnCount() - 1;
//            if (columncount >= 0) {  
//                TableColumnModel columnModel = table.getColumnModel();
//                TableColumn tableColumn = columnModel.getColumn(columncount);
//                columnModel.removeColumn(tableColumn);
//                defaultModel.setColumnCount(columncount);
//            }
        }
        if (e.getActionCommand().equals("Remove Row")) {
            int rowcount = defaultModel.getRowCount() - 1; 
            if (rowcount >= 0) {
                defaultModel.removeRow(rowcount);
                defaultModel.setRowCount(rowcount);
            }
        }
        table.revalidate();
    }
    
    class HandlePopWin extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            // e.getWindow().setVisible(false);
            (e.getWindow()).dispose();
            //System.exit(0);
            // (new Frame()).setVisible(false);
            // this.windowClosed(e);
        }
    }
}
