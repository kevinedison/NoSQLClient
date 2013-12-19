/**
 * PreferencesRightPanel.java
 * kevin 2013-2-21
 * @version 0.1
 */
package org.kevin.redis.panel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.kevin.db.sqlite.SqliteDB;
import org.kevin.redis.data.vo.Host;
import org.kevin.redis.msg.PopMessage;
import org.kevin.redis.window.PreferencesWindow;

/**
 * @author kevin
 * @since jdk1.6
 */
public class PreferencesRightPanel {

    private static String hostNode = "Hosts";
    private static JTable data_table = null;

    /**
     * @param pathNode
     */
    public static void showPreferences(String pathNode) {
        // PopMessage.popMsg(pathNode);
        if (hostNode.equalsIgnoreCase(pathNode))
            showHostTable();
    }
    public static JPanel showHostTable(String pathNode) {
        return showHostTable();
    }

    private static JPanel showHostTable() {

        data_table = initJTable();

        // JScrollPane scrollPane = new JScrollPane();
        // scrollPane.setBounds(100, 30, 400, 400);

        JButton remove_btn = new JButton("删除数据");
        remove_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //removeDatas();
                removeData();
                showHostTable();
            }
        });
        // scrollPane.add(button_1, BorderLayout.NORTH);
        // scrollPane.add(button_1);
        // scrollPane.add(table, BorderLayout.SOUTH);
        // scrollPane.add(table);
        JPanel tablePanle = PreferencesWindow.instance().getTablePanel();
        JScrollPane scrollPanle = new JScrollPane();
        //tablePanle.setLayout(new GridLayout(2, 1));
        //tablePanle.setAlignmentY(0);
        tablePanle.removeAll();
        tablePanle.setLayout(new BoxLayout(tablePanle, BoxLayout.Y_AXIS));
        //tablePanle.setSize(tablePanle.getX(), tablePanle.getY());
        //tablePanle.setAlignmentX(0);
        
        scrollPanle.setViewportView(data_table);
        
        tablePanle.add(scrollPanle);
        scrollPanle.setBounds(10, 10, tablePanle.getX(), tablePanle.getY());
        //scrollPanle.setVisible(true);
        
        tablePanle.add(remove_btn);
        data_table.setBounds(10, 10, tablePanle.getX(), tablePanle.getY());

        data_table.getColumn("ID").setCellRenderer(
                new DefaultTableCellRenderer() { // rewrite setValue method
                    public void setValue(Object value) {
                        this.setHorizontalAlignment(SwingConstants.CENTER);// 居中
                        super.setValue(value);
                    }
                });
        // PreferencesWindow.instance().getContentPane().add(scrollPane,
        // BorderLayout.EAST);
        //PreferencesWindow.instance().getContentPane().add(tablePanle);
        //tablePanle.setVisible(true);
        tablePanle.updateUI();
        // scrollPane.updateUI();
        return tablePanle;
    }
    private static void removeData() {
        int selectId = data_table.getSelectedRow();
        //PopMessage.popMsg("selectId:" + selectId);
        
        Host host = new Host();
        host.setId(Integer.valueOf((String) data_table.getValueAt(selectId, 0)));
        host.setIp((String) data_table.getValueAt(selectId, 1));
        host.setPwd((String) data_table.getValueAt(selectId, 2));
        host.setPort(Integer.valueOf((String) data_table.getValueAt(selectId, 3)));

        PopMessage.popMsg("selectId:" + host.getId() + ", ip:" + host.getIp() + ", pwd:" + host.getPwd()+ ", ip:" + host.getPort() );
        SqliteDB.getInstance().removeHost(host);
        //data_table.remove(selectId);
    }
    
    private static int removeDatas() {
        //int selectId = data_table.getSelectedRow();
        int[] selectRows = data_table.getSelectedRows();
        int effectedRows = 0;
        
        int response = JOptionPane.showConfirmDialog(null, "Do you want to delete row [" + compArray(selectRows) + "]", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(JOptionPane.YES_OPTION == response){
            int allRows = selectRows.length;
            for(int rowId : selectRows){
                Host host = new Host();
                host.setId(Integer.valueOf((String) data_table.getValueAt(rowId, 0)));
                host.setIp((String) data_table.getValueAt(rowId, 1));
                host.setPwd((String) data_table.getValueAt(rowId, 2));
                host.setPort(Integer.valueOf((String) data_table.getValueAt(rowId, 3)));
                
                //PopMessage.popMsg("selectId:" + host.getId() + ", ip:" + host.getIp() + ", pwd:" + host.getPwd()+ ", ip:" + host.getPort() );
                effectedRows += SqliteDB.getInstance().removeHost(host);
            }
            if(effectedRows == allRows) PopMessage.popMsg("Delete successful!");
            else if(effectedRows > 0 ) PopMessage.popMsg("Success:[" + effectedRows +"], fail:[" + (allRows - effectedRows) + "]");
            else PopMessage.popMsg("Exception has occured!");
        }
        return effectedRows;
        //data_table.remove(selectId);
    }

    private static String compArray(int[] selectRows){
        StringBuilder sbu = new StringBuilder();
        for(int rowId : selectRows){
            sbu.append(data_table.getValueAt(rowId, 0)).append(",");
        }
        sbu = sbu.deleteCharAt(sbu.length()-1);
        return sbu.toString();
    }
    public static JTable initJTable() {
        final String space = "";
        //List<Host> hosts = DerbyDB.getInstance().gethosts();
        List<Host> hosts = SqliteDB.getInstance().gethosts();
        String[] headers = { "ID", "IP", "PWD", "DBIndex", "Port" };
        Object[][] table_data = null;
        //DefaultTableModel dtm = new DefaultTableModel(new Object[] { "ID", "IP", "PWD", "DBIndex", "Port" }, 0);
        DefaultTableModel dtm = new DefaultTableModel(headers, 0);
        if (null != hosts && hosts.size() > 0) {
            int arraySize = hosts.size();
            table_data = new String[arraySize][headers.length];
            int i = 0;
            for (Host host : hosts) {
                table_data[i][0] = space + host.getId();
                table_data[i][1] = host.getIp();
                table_data[i][2] = host.getPwd();
                table_data[i][3] = space + host.getDbIndex();
                table_data[i][4] = space + host.getPort();
                dtm.addRow(table_data[i]);
                i++;
                //System.out.println("[id:" + host.getId() + ", ip:" + host.getIp() +  ", pwd:" + host.getPwd() + ", port:" + host.getPort() + "]");
            }
        } else {
            table_data = new String[1][4];
            table_data[0][0] = "";
            table_data[0][1] = "";
            table_data[0][2] = "";
            table_data[0][3] = "";
            table_data[0][4] = "";
        }

        // data_table = new JTable(table_data, header_name);
        data_table = new JTable();
        data_table.setModel(dtm);
        
        // set the table header to be sorted
        data_table.setRowSorter(new TableRowSorter<TableModel>(dtm));

        JTableHeader th = data_table.getTableHeader();
        th.setUI(new BasicTableHeaderUI() {
            JCheckBox chk = new JCheckBox("All");
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                //g.setColor(Color.red);
                g.draw3DRect(5, 5, 10, 10, false);
            }
        });

        // data_table.setTableHeader(data_table.getTableHeader());

        // JTableHeader tableHeader = data_table.getTableHeader();
        // data_table.setTableHeader(tableHeader);
        return data_table;
    }
}

/**
 * TableModel类，继承了AbstractTableModel
 * 
 */
class Table_Model extends AbstractTableModel {

    private static final long serialVersionUID = -7495940408592595397L;

    private Vector content = null;

    private String[] title_name = { "ID", "IP", "PWD", "Port" };

    public Table_Model() {
        content = new Vector();
    }

    public Table_Model(int count) {
        content = new Vector(count);
    }

    public void addRow(String ip, String pwd, int port) {
        Vector v = new Vector(4);
        v.add(0, new Integer(content.size()));
        v.add(1, ip);
        v.add(2, pwd);
        v.add(3, port);
        content.add(v);
    }

    public void removeRow(int row) {
        content.remove(row);
    }

    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (content.size() > row) {
                content.remove(row);
            }
        }
    }

    /**
     * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        }
        return true;
    }

    /**
     * 使修改的内容生效
     */
    public void setValueAt(Object value, int row, int col) {
        ((Vector) content.get(row)).remove(col);
        ((Vector) content.get(row)).add(col, value);
        this.fireTableCellUpdated(row, col);
    }

    public String getColumnName(int col) { // 返回列名，即表头
        return title_name[col];
    }

    public int getColumnCount() {
        return title_name.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }

    /**
     * 返回数据类型
     */
    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }
}