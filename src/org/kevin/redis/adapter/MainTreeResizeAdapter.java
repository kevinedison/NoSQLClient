/**
 * MainTreeResizeAdapter.java
 * kevin 2013-4-19
 * @version 0.1
 */
package org.kevin.redis.adapter;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import org.kevin.redis.RedisDB;

/**
 * @author kevin
 * @since jdk1.6
 */
public class MainTreeResizeAdapter extends ComponentAdapter {

    JPanel adapteep;
    
    public MainTreeResizeAdapter(JPanel adapteep) {
        this.adapteep = adapteep;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        RedisDB.resize();
    }

}
