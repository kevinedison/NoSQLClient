/**
 * ComponentResizeAdapter.java
 * kevin 2013-4-17
 * @version 0.1
 */
package org.kevin.redis.adapter;

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import org.kevin.redis.table.RedisTable;

/**
 * @author kevin
 * @since jdk1.6
 */
public class ComponentResizeAdapter extends ComponentAdapter {

    Frame adaptee;
    JPanel adapteep;
    

    public ComponentResizeAdapter(Frame adaptee) {
        this.adaptee = adaptee;
    }

    /**
     * @param adapteep
     */
    public ComponentResizeAdapter(JPanel adapteep) {
        this.adapteep = adapteep;
    }

    public void componentResized(ComponentEvent e) {
        RedisTable.resize();
        //adapteep.updateUI();
        //adapteep.revalidate();
        //System.out.println("adaptee Resize: " + adaptee.getTitle());
        //Component[] comps = adaptee.getComponents();
        //for(Component comp : comps){
            //System.out.println("adaptee Resize: " + comp.getName() + ", size: H" + comp.getSize().getHeight() + ", W:"+ comp.getSize().getWidth() );
            //Rectangle rt = comp.getBounds();
            //rt.setBounds(rt.getX(), rt.getY(), comp.getSize().getWidth(), comp.getSize().getHeight());
            //rt.setRect(comp.getX(), comp.getY(), comp.getSize().getWidth(), comp.getSize().getHeight());
            //int width = comp.getWidth() - 100;
            //int height = comp.getHeight();
            //comp.setPreferredSize(new Dimension(width, height));
            //comp.revalidate();
//            JPanel  treePanel = RedisDB.getTree_panel();
//            int width = treePanel.getWidth();
//            int height = treePanel.getHeight();
//            treePanel.setPreferredSize(new Dimension(width, height));
//            treePanel.getComponents();
//            treePanel.revalidate();
        //}
    }
 
}
