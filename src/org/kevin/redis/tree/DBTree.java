/**
 * DBTree.java
 * 2011 Dec 16, 2011
 */
package org.kevin.redis.tree;

import java.util.Iterator;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.kevin.redis.constant.RedisConstants;
import org.kevin.redis.manage.RedistDataManagement;
import org.kevin.redis.table.RedisTable;

/**
 * @author kevin
 * @since Jdk 1.6 
 */
public class DBTree {

    private static DBTree dbTree;
    public static JTree tree;
    DefaultMutableTreeNode rootNode;
    private String rootNodeTitle = "RedisDB";
    private String keysTitle = "Redis";
    private String spaceNodeTitle = "";
    DefaultMutableTreeNode spaceNode, allKeysNode;
    DefaultMutableTreeNode profileNode, encodingTaskNode, finishTaskNode;
    //JPanel tree_panel;
    JScrollPane jsp;
    
    public static DBTree getDBTree(){
        if(null == dbTree)
            dbTree = new DBTree();
        return dbTree;
    }
    
    private DBTree(){
        tree = new JTree();
        //jsp = new JScrollPane();
        //tree = new JTree();
    }
    // Create the tree for redis db
    public javax.swing.JTree createTree() {
        // Create the default tree
        //tree = new JTree();
        // create the root node
        rootNode = new DefaultMutableTreeNode();
        // set the title for the root node
        rootNode.setUserObject(rootNodeTitle);

        spaceNode = new DefaultMutableTreeNode();
        spaceNode.setUserObject(spaceNodeTitle);
        rootNode.add(spaceNode);

        // Create the model object for the tree and accepted the root node while generate
        javax.swing.tree.DefaultTreeModel dm = new DefaultTreeModel(rootNode);
        // set the model to the tree and show the loaded node on the tree
        tree.setModel(dm);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                Object selected = tree.getLastSelectedPathComponent();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)selected;
                if(null!= node && node.isRoot()){
                    //PopMessage.popMsg("Root node:[" + node +"]");
                } else {
                    String pathNode = tse.getPath().getLastPathComponent().toString();
                    RedisTable.updateDetail(pathNode);
                }
            }
        });
        /*tree.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });*/
        /*tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            
            public void treeWillCollapse(TreeExpansionEvent event) {// close node --折叠
            }
            public void treeWillExpand(TreeExpansionEvent event)
                    throws ExpandVetoException {// expend node --打开
                String pathNode = event.getPath().getLastPathComponent().toString();
                //System.out.println("pathNode:" + pathNode);
                //PopMessage.popMsg(pathNode);
            }
        });*/
        return tree;
    }
    
    public javax.swing.JTree clearTree() {
        rootNode.removeAllChildren();
        return tree;
    }

    public JTree getkeys(String filterText){
        // Get the encoding task list node:
        allKeysNode = new DefaultMutableTreeNode();
        allKeysNode.setUserObject(keysTitle + "<" + RedisConstants.REDIS_SERVER_IP + ":" + RedisConstants.REDIS_SERVER_PORT + ":" + RedisConstants.REDIS_SERVER_DBINDEX +">");
        rootNode.add(allKeysNode);

        // get the encoding task id list from redis and add under this node
        java.util.Set<String> allKeysList = RedistDataManagement.listKeys(filterText);

        if(null != allKeysList && allKeysList.size() > 0){
            Iterator<String> ite = allKeysList.iterator();
            String iteId = null;
            //Integer setID = null;
            while (ite.hasNext()) {
                iteId = (String) ite.next();
                //System.out.println("iteId:" + iteId);
                //setID = (null == iteId) ? 0 : Integer.valueOf(iteId);
                // create the new leaf 
                DefaultMutableTreeNode allKeysLeaf = new DefaultMutableTreeNode();
                allKeysLeaf.setUserObject(iteId);
                // add the profile leaf to the profile node
                allKeysNode.add(allKeysLeaf);
            }
        } else {
            // add the profile leaf to the profile node
            DefaultMutableTreeNode allKeysNodeLeaf = new DefaultMutableTreeNode();
            allKeysNodeLeaf.setUserObject("no keys");
            // add the profile leaf to the profile node
            allKeysNode.add(allKeysNodeLeaf);
        }
        
        tree.removeAll();
        // Create the model object for the tree and accepted the root node while generate
        javax.swing.tree.DefaultTreeModel dm = new DefaultTreeModel(allKeysNode);
        //dm.setRoot(rootNode);
        // set the model to the tree and show the loaded node on the tree
        tree.setModel(dm);
        return tree;
    }

    /**
     * @param filterText
     * @return
     */
//    public JScrollPane getTreePanel(String filterText) {
//        tree = getkeys(filterText);
//        jsp.add(tree);
//        tree.setBounds(10, 10, 200, 430);
//        tree.setVisible(true);
//        //int width  = jsp.getWidth();
//        //int height = jsp.getHeight();
//        //jsp.setPreferredSize(new Dimension(width, height));
//        //jsp.setBounds(10, 70, width, height);
//        //jsp.setBounds(10, 70, 200, 380);
//        //jsp.updateUI();
//        return jsp;
//    }
}
