/**
 * RedisHash.java
 * 2011 Dec 16, 2011
 */
package org.kevin.redis.table;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.kevin.redis.RedisDB;
import org.kevin.redis.manage.RedistDataManagement;
import org.kevin.redis.msg.PopMessage;

/**
 * @author kevin
 * @since Jdk 1.6
 */
public class RedisTable {

    /**The value is [TABLE name:] **/
    private static String TABLE_NAME = "TABLE name:";
    /**The value is [TABLE Type:] **/
    private static String TABLE_TYPE = "TABLE Type:";
    private static String CONTETN_FILTR = "Contetn Filter:";
    private static String TAB_SPACE = "  ";
    /**The value is [ **/
    private static String VALUE_BRACE_START = "[";
    /**The value is ] **/
    private static String VALUE_BRACE_END = "]";
    private static ScrollPaneLayout spLayout = new ScrollPaneLayout();
    //private static BorderLayout borderLayout = new BorderLayout();
    //private static int tableW=400, tableH=400;
    
    private static JScrollPane scrollPane;
    private static JTable table;
    static JPanel dateTable_panel = RedisDB.getDataTable_panel();
    
    /**
     * Create the table for the hash task from redis
     * 
     * @return
     */
    public static JScrollPane componentInfo() {
        String[] taskHeaders = { "Column", "value", "type" };
        Object[][] playerInfo = { { "task:id", new Integer(66), "String" },
                { "status", new Integer(82), "int" } };
        JTable table = new JTable(playerInfo, taskHeaders);
        table.setPreferredScrollableViewportSize(new Dimension(550, 30));
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }
       
    /**
     * @param pathNode
     */
    public static void updateDetail(String pathNode) {
        //PopMessage.popMsg("The selected key is:[" + pathNode + "]");
        if(null == pathNode || pathNode.isEmpty()){
            PopMessage.popMsg("The selected key is:[" + pathNode + "]");
        } else {
            String tableype = RedistDataManagement.getType(pathNode);
            //PopMessage.popMsg(tableype);
            if("set".equals(tableype)) {
                // get the id set from redis and add under this node
                Set<String> allKeysList = RedistDataManagement.getSetDetail(pathNode);
                contractSetTable(pathNode, tableype, allKeysList);
            } else if("hash".equals(tableype)){
                // get the hash from redis and add under this node
                Map<String, String> allHashList = RedistDataManagement.getHashDetail(pathNode);
                contractHashTable(pathNode, tableype, allHashList);
            } else if("list".equals(tableype)){
                // get the list from redis and add under this node
                List<String> allList = RedistDataManagement.getListDetail(pathNode);
                contractListTable(pathNode, tableype, allList);
            } else if("string".equals(tableype)){
                String stringValue = RedistDataManagement.getStringDetail(pathNode);
                contractStringTable(pathNode, tableype, stringValue);
            } else {
                contractNoneTable(pathNode, tableype);
            }
        }
    }
    
    /**
     * @param pathNode
     * @param tableype
     * @param allList
     */
    private static void contractListTable(String pathNode, String tableype, List<String> members) {
        String[] tableHeaders = { "Index", "Value", "Type" };
        Object[][] listInfo = null;
        final String valueType = "String";
        if(null != members && members.size() > 0){
            int arraySize = members.size();
            //final String space = "";
            listInfo = new String[arraySize][3];
            for(int index = 0; index < arraySize; index++ ){
                listInfo[index][0] = String.valueOf(index + 1);
                listInfo[index][1] = members.get(index);
                listInfo[index][2] = valueType;
            }
        } else {
            listInfo = new String[1][3];
            listInfo[0][0] = "";
            listInfo[0][1] = "";
            listInfo[0][2] = "";
        }

        //JPanel dateTable_panel = RedisDB.getDataTable_panel();
        if(null == dateTable_panel){
            dateTable_panel = RedisDB.getDataTable_panel();
        }
        dateTable_panel.removeAll();

        //JTable table = new JTable(listInfo, listHeaders);
        //table.setName(tableype + "<" + pathNode + ">");

        // set the table header to be sorted
        DefaultTableModel dtm = new DefaultTableModel(listInfo, tableHeaders);
        table = new JTable(dtm);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtm);
        table.setRowSorter(sorter);

        AlignColumn(table, tableHeaders[0] , SwingConstants.CENTER);
        //table.setPreferredScrollableViewportSize(new Dimension(550, 30));
        //JScrollPane scrollPane = new JScrollPane(table);
        scrollPane = new JScrollPane(table);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        //String _desc = TABLE_NAME + pathNode + TAB_SPACE + TABLE_VALUE + tableype;
        JLabel tableName = new JLabel(TABLE_NAME + TAB_SPACE + VALUE_BRACE_START + pathNode + VALUE_BRACE_END);
        JLabel tableType = new JLabel(TABLE_TYPE + TAB_SPACE + VALUE_BRACE_START + tableype + VALUE_BRACE_END);
        labelPanel.add(tableName);
        labelPanel.add(tableType);
        dateTable_panel.add(labelPanel);
        labelPanel.setVisible(true);
        tableType.setVisible(true);
        labelPanel.setBounds(10, 20, 400, 35);

        JPanel  filter = new JPanel();
        filter.setLayout(new BoxLayout(filter, BoxLayout.X_AXIS));
        JLabel contentFilter = new JLabel(CONTETN_FILTR + TAB_SPACE);
        final JTextArea filterText = new JTextArea();
        JButton filterButton = new JButton("Filter");
        filter.add(contentFilter);
        filter.add(filterText);
        filter.add(filterButton);
        dateTable_panel.add(filter);
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = filterText.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });
        filter.setBounds(10, 50, 400, 25);
        /*
        filterText.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void removeUpdate(DocumentEvent de) {
                filterDE(de);                
            }
            
            @Override
            public void insertUpdate(DocumentEvent de) {
                filterDE(de);                
            }
            
            @Override
            public void changedUpdate(DocumentEvent de) {
                filterDE(de);                
            }

        });*/
        
        //table.setLayout(borderLayout);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        
        //table.setBounds(10, 20, 300, 440);
        //RedisDB.updateMiddleTable(scrollPane);
        //panel.remove(1);
        scrollPane.setVisible(true);
        //scrollPane.setBounds(10, 50, 400, 400);
        scrollPane.setLayout(spLayout);
        //scrollPane.resetKeyboardActions();
        //scrollPane.setBounds(30, 30, dateTable_panel.getX(), dateTable_panel.getY());
        dateTable_panel.add(scrollPane);

        // according to the auto width and height
        int width = dateTable_panel.getWidth();
        int height = dateTable_panel.getHeight();
        //System.out.println("dateTable_panel: width:[" + width + "], height:[" + height + "]");
        scrollPane.setBounds(10, 80, (width - 20), (height - 90));
        dateTable_panel.setPreferredSize(new Dimension(width, height));
        
        dateTable_panel.updateUI();
        
    }

    /**
     * @param de
     */
    protected static void filterDE(DocumentEvent de) {
        if(null != scrollPane) {
            PopMessage.popMsg("The table filter:" + de.getOffset());
            //table.get
        }
    }

    private static void contractSetTable(String pathNode, String tableype, Set<String>  members){
        String[] tableHeaders = { "Field", "Value", "Type" };
        //Object[][] playerInfo = { { "", "", "" } };
        //PopMessage.popMsg("members.size():" + members.size());
        Object[][] setInfo = null;
        //JTable table = new JTable(playerInfo, taskHeaders);
        final String space = "";
        //JPanel dateTable_panel = RedisDB.getDataTable_panel();
        if(null == dateTable_panel){
            dateTable_panel = RedisDB.getDataTable_panel();
        }
        if(null != members && members.size() > 0){
            int arraySize = members.size();
            setInfo = new String[arraySize][3];
            Iterator<?> ite = members.iterator();
            String iteId = null;
            //Integer setID = null;
            int i = 0;
            while (ite.hasNext()) {
                iteId = (String) ite.next();
                //System.out.println("iteId:" + iteId);
                //setID = (null == iteId) ? 0 : Integer.valueOf(iteId);
                // create the new leaf 
                setInfo[i][0] = space + (i + 1);
                setInfo[i][1] = iteId;
                setInfo[i][2] = "String";
                i++;
            }
        } else {
            setInfo = new String[1][3];
            setInfo[0][0] = "";
            setInfo[0][1] = "";
            setInfo[0][2] = "";
            // add the profile leaf to the profile node
            //DefaultMutableTreeNode allKeysNodeLeaf = new DefaultMutableTreeNode();
            //allKeysNodeLeaf.setUserObject("no keys");
            // add the profile leaf to the profile node
            //allKeysNode.add(allKeysNodeLeaf);
        }
        
        dateTable_panel.removeAll();
        //Component comp = panel.getComponentAt(2, 2);
        //JTable table = new JTable(setInfo, taskHeaders);
        //table.setName(tableype + "<" + pathNode + ">");

        // set the table header to be sorted
        DefaultTableModel dtm = new DefaultTableModel(setInfo, tableHeaders);
        //JTable table = new JTable(dtm);
        table = new JTable(dtm);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtm);
        table.setRowSorter(sorter);
        //table.setRowSorter(new TableRowSorter<TableModel>(dtm));

        AlignColumn(table, tableHeaders[0] , SwingConstants.CENTER);
        //table.setPreferredScrollableViewportSize(new Dimension(550, 30));
        //JScrollPane scrollPane = new JScrollPane(table);
        scrollPane = new JScrollPane(table);
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        //String _desc = TABLE_NAME + pathNode + TAB_SPACE + TABLE_VALUE + tableype;
        JLabel tableName = new JLabel(TABLE_NAME + TAB_SPACE + VALUE_BRACE_START + pathNode + VALUE_BRACE_END);
        JLabel tableType = new JLabel(TABLE_TYPE + TAB_SPACE + VALUE_BRACE_START + tableype + VALUE_BRACE_END);
        labelPanel.add(tableName);
        labelPanel.add(tableType);
        dateTable_panel.add(labelPanel);
        labelPanel.setVisible(true);
        tableType.setVisible(true);
        labelPanel.setBounds(10, 20, 400, 35);
        
        //table.setLayout(borderLayout);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        JPanel  filter = new JPanel();
        filter.setLayout(new BoxLayout(filter, BoxLayout.X_AXIS));
        JLabel contentFilter = new JLabel(CONTETN_FILTR + TAB_SPACE);
        final JTextArea filterText = new JTextArea();
        JButton filterButton = new JButton("Filter");
        filter.add(contentFilter);
        filter.add(filterText);
        filter.add(filterButton);
        dateTable_panel.add(filter);
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = filterText.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });
        filter.setBounds(10, 50, 400, 25);
        /*
        filterText.getDocument().addDocumentListener(new DocumentListener() {
        
        @Override
        public void removeUpdate(DocumentEvent de) {
            filterDE(de);                
        }
        
        @Override
        public void insertUpdate(DocumentEvent de) {
            filterDE(de);                
        }
        
        @Override
        public void changedUpdate(DocumentEvent de) {
            filterDE(de);                
        }

    });*/
        
        //table.setBounds(100, 20, 300, 440);
        //RedisDB.updateMiddleTable(scrollPane);
        //panel.remove(1);
        scrollPane.setVisible(true);
        //scrollPane.setBounds(10, 50, 400, 400);
        scrollPane.setLayout(spLayout);
        //scrollPane.setBounds(30, 30, dateTable_panel.getX(), dateTable_panel.getY());
        dateTable_panel.add(scrollPane);

        // according to the auto width and height
        int width = dateTable_panel.getWidth();
        int height = dateTable_panel.getHeight();
        //System.out.println("dateTable_panel: width:[" + width + "], height:[" + height + "]");
        scrollPane.setBounds(10, 80, (width - 20), (height - 90));
        dateTable_panel.setPreferredSize(new Dimension(width, height));
        
        dateTable_panel.updateUI();
        //dateTable_panel.setVisible(true);
        //dateTable_panel.setLocale(null);
    }
    
    private static void contractHashTable(String pathNode, String tableype, Map<String, String>  members){
        String[] tableHeaders = { "Field", "value", "type" };
        Object[][] tableInfo = new String[members.size()][3];
        //JTable table = new JTable(playerInfo, taskHeaders);
        final String space = "";
        //JPanel dateTable_panel = RedisDB.getDataTable_panel();
        if(null == dateTable_panel){
            dateTable_panel = RedisDB.getDataTable_panel();
        }
        if(null != members && members.size() > 0){
            Set<String> memberSet = members.keySet();
            Iterator<?> ite = memberSet.iterator();
            String iteId = null;
            //Integer setID = null;
            int i = 0;
            while (ite.hasNext()) {
                iteId = (String) ite.next();
                //System.out.println("iteId:" + iteId);
                //setID = (null == iteId) ? 0 : Integer.valueOf(iteId);
                // create the new leaf 
                tableInfo[i][0] = iteId;
                tableInfo[i][1] = members.get(iteId);
                tableInfo[i][2] = space;
                i++;
            }
        } else {
            tableInfo = new String[1][3];
            tableInfo[0][0] = "";
            tableInfo[0][1] = "";
            tableInfo[0][2] = "";
            // add the profile leaf to the profile node
            //DefaultMutableTreeNode allKeysNodeLeaf = new DefaultMutableTreeNode();
            //allKeysNodeLeaf.setUserObject("no keys");
            // add the profile leaf to the profile node
            //allKeysNode.add(allKeysNodeLeaf);
        }

        dateTable_panel.removeAll();
        //JTable table = new JTable(tableInfo, taskHeaders);
        //table.setLayout(borderLayout);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        // set the table header to be sorted
        DefaultTableModel dtm = new DefaultTableModel(tableInfo, tableHeaders);
        //JTable table = new JTable(dtm);
        table = new JTable(dtm);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtm);
        table.setRowSorter(sorter);
        //table.setRowSorter(new TableRowSorter<TableModel>(dtm));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        AlignColumn(table, tableHeaders[0] , SwingConstants.CENTER);
        //table.setPreferredScrollableViewportSize(new Dimension(550, 30));
        //table.setBounds(10, 30, 300, 400);
        //table.setVisible(true);
        //dateTable_panel.add(table);

        //JScrollPane scrollPane = new JScrollPane(table);
        scrollPane = new JScrollPane(table);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        //String _desc = TABLE_NAME + pathNode + TAB_SPACE + TABLE_VALUE + tableype;
        JLabel tableName = new JLabel(TABLE_NAME + TAB_SPACE + VALUE_BRACE_START + pathNode + VALUE_BRACE_END);
        JLabel tableType = new JLabel(TABLE_TYPE + TAB_SPACE + VALUE_BRACE_START + tableype + VALUE_BRACE_END);
        labelPanel.add(tableName);
        labelPanel.add(tableType);
        dateTable_panel.add(labelPanel);
        labelPanel.setVisible(true);
        tableType.setVisible(true);
        labelPanel.setBounds(10, 20, 400, 35);

        JPanel  filter = new JPanel();
        filter.setLayout(new BoxLayout(filter, BoxLayout.X_AXIS));
        JLabel contentFilter = new JLabel(CONTETN_FILTR + TAB_SPACE);
        final JTextArea filterText = new JTextArea();
        JButton filterButton = new JButton("Filter");
        filter.add(contentFilter);
        filter.add(filterText);
        filter.add(filterButton);
        dateTable_panel.add(filter);
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = filterText.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });
        filter.setBounds(10, 50, 400, 25);
        /*
        filterText.getDocument().addDocumentListener(new DocumentListener() {
        
        @Override
        public void removeUpdate(DocumentEvent de) {
            filterDE(de);                
        }
        
        @Override
        public void insertUpdate(DocumentEvent de) {
            filterDE(de);                
        }
        
        @Override
        public void changedUpdate(DocumentEvent de) {
            filterDE(de);                
        }

    });*/
        
        dateTable_panel.add(scrollPane);
        //scrollPane.setBounds(10, 50, 400, 400);
        scrollPane.setLayout(spLayout);
        //scrollPane.setVisible(true);
        //scrollPane.setBounds(30, 30, dateTable_panel.getX(), dateTable_panel.getY());
        int width = dateTable_panel.getWidth();
        int height = dateTable_panel.getHeight();
        //System.out.println("dateTable_panel: width:[" + width + "], height:[" + height + "]");

        scrollPane.setBounds(10, 80, (width - 20), (height - 90));
        dateTable_panel.setPreferredSize(new Dimension(width, height));
        //dateTable_panel.removeAll();
        dateTable_panel.updateUI();
    }
    

    public static void resize(){

        if(null == dateTable_panel){
            dateTable_panel = RedisDB.getDataTable_panel();
        }
        int width = dateTable_panel.getWidth();
        int height = dateTable_panel.getHeight();
        if(null != scrollPane){
            scrollPane.setBounds(10, 50, (width - 20), (height - 60));
        }
        //System.out.println("dateTable_panel: width:[" + width + "], height:[" + height + "]");
        dateTable_panel.setPreferredSize(new Dimension(width, height));
        dateTable_panel.updateUI();
    }
    
    private static void contractStringTable(String pathNode, String tableype, String stringValue){
        //JPanel dateTable_panel = RedisDB.getDataTable_panel();
        if(null == dateTable_panel){
            dateTable_panel = RedisDB.getDataTable_panel();
        }
        dateTable_panel.removeAll();

        JPanel stringPanel = new JPanel();
        stringPanel.setLayout(new BoxLayout(stringPanel, BoxLayout.Y_AXIS));
        //JLabel labLeft=new JLabel("align left", SwingConstants.LEFT);
        //stringPanel.add(labLeft);
        //stringPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //JPanel namePanel = new JPanel();
        //namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        // cant set the size for FlowLayout
        //namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String nameLine = TABLE_NAME + TAB_SPACE + VALUE_BRACE_START + pathNode + VALUE_BRACE_END;
        JLabel tableNameLab = new JLabel(nameLine);
        //JLabel tableNameLab = new JLabel(TABLE_NAME);
        //tableNameLab.setHorizontalAlignment(SwingConstants.LEFT);
        //JLabel tableNameValue = new JLabel(pathNode);
        //namePanel.add(tableNameLab);
        //namePanel.add(tableNameValue);
        
        //JPanel typePanel = new JPanel();
        //typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
        //typePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String typeLine = TABLE_TYPE + TAB_SPACE + VALUE_BRACE_START + tableype + VALUE_BRACE_END;
        JLabel tableTypeLab = new JLabel(typeLine);
        //JLabel tableTypeLab = new JLabel(TABLE_TYPE);
        //JLabel tableTypeValue = new JLabel(tableype);
        //tableTypeLab.setHorizontalAlignment(SwingConstants.LEFT);
        //typePanel.add(tableTypeLab);
        //typePanel.add(tableTypeValue);
        
        //JPanel valuePanel = new JPanel();
        //valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.X_AXIS));
        //valuePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String value = "Table Value:" + TAB_SPACE + VALUE_BRACE_START + stringValue + VALUE_BRACE_END;
        JLabel tablevalueLab = new JLabel(value);
        //JLabel tablevalueLab = new JLabel("Table Value:");
        //JTextField tableValue = new JTextField(20);
        //tableValue.setText(stringValue);
        //tableValue.setColumns(30);
        //valuePanel.add(tablevalueLab);
        //valuePanel.add(tableValue);
        //tableValue.setPreferredSize(new Dimension(50, 20));
        
        stringPanel.add(tableNameLab);
        stringPanel.add(tableTypeLab);
        stringPanel.add(tablevalueLab);
        //stringPanel.add(valuePanel);
        //dateTable_panel.setLayout(new GridLayout(2, 2, 0, 0));
        dateTable_panel.add(stringPanel);
        stringPanel.setVisible(true);
        //tableNameLab.setLocation(10, 10);
        //typePanel.setLocation(10, 50);
        //valuePanel.setLocation(10, 70);
        //stringPanel.setLocation(10, 10);
        //tableNameLab.setBounds(10, 10, 400, 20);
        //typePanel.setBounds(10, 50, 400, 20);
        //valuePanel.setBounds(10, 70, 50, 20);
        stringPanel.setBounds(10, 20, 400, 90);
        
        //dateTable_panel.add(tableNameLab);
        //dateTable_panel.add(tableNameValue);
        //dateTable_panel.add(tableTypeLab);
        //dateTable_panel.add(tableTypeValue);
        //dateTable_panel.add(tablevalueLab);
        //dateTable_panel.add(tableValue);
        
        //tableNameLab.setBounds(10, 30, 100, 20);
        //tableNameValue.setBounds(100, 30, 100, 20);
        //tablevalueLab.setBounds(10, 50, 100, 20);
        //tableValue.setBounds(100, 50, 100, 20);
        //stringPanel.updateUI();
        dateTable_panel.updateUI();
        //dateTable_panel.repaint();
        //dateTable_panel.setVisible(true);
    }
    
    private static void contractNoneTable(String pathNode, String tableype){
        
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
}
