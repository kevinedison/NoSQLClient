/**
 * ToolsPanel.java
 * kevin 2013-2-5
 * @version 0.1
 */
package org.kevin.redis.panel;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author kevin
 * @since jdk1.6
 */
public class ToolsPanel {

    public JScrollPane createToolsPanel() {
        String[] headers = { };
        Object[][] playerInfo = { };
        JTable table = new JTable(playerInfo, headers);
        table.setPreferredScrollableViewportSize(new Dimension(550, 30));
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

}
