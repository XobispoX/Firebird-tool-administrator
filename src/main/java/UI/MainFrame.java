/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import db.ConnectionProfile;
import db.ConnectionManager;
import db.FirebirdConnector;
import UI.LoginFrame;
import UI.newConnectionFrame;
import db.MetadataService;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author danie
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());
    
    private ConnectionManager connectionManager;
    public MainFrame(ConnectionManager manager) {
        initComponents();
        this.connectionManager = manager;
        loadTree();
        treeDatabase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // double-click
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeDatabase.getLastSelectedPathComponent();
                    if (node == null) return;

                    // Nodes Hojas 
                    if (node.isLeaf()) {
                        String tableName = node.getUserObject().toString();
                        // El sistema del arbol y sus nodos
                        if (!tableName.equals("Tables") && !tableName.equals("Views") &&
                            !tableName.equals("Procedures") && !tableName.equals("Functions") &&
                            !tableName.equals("Triggers") && !tableName.equals("Indexes") &&
                            !tableName.equals("Sequences") && !tableName.equals("Users")) {

                            txtsql.setText("SELECT * FROM " + tableName);
                            executeSQL();
                        }
                    }
                }
            }
        });
        ButtonExecute.addActionListener(e -> executeSQL());
        System.out.println("Active connections: " + connectionManager.getProfileNames());
        setLocationRelativeTo(null); 
        
    }
    
   
    private void loadTree() {
     try {
        Connection conn = connectionManager.getActiveConnection();
        MetadataService metadata = new MetadataService(conn);

        DefaultMutableTreeNode root =
                new DefaultMutableTreeNode(
                        connectionManager.getActiveProfile().getName()
                );

        DefaultMutableTreeNode tablesNode = new DefaultMutableTreeNode("Tables");
        DefaultMutableTreeNode viewsNode = new DefaultMutableTreeNode("Views");
        DefaultMutableTreeNode proceduresNode = new DefaultMutableTreeNode("Procedures");
        DefaultMutableTreeNode functionsNode = new DefaultMutableTreeNode("Functions");
        DefaultMutableTreeNode triggersNode = new DefaultMutableTreeNode("Triggers");
        DefaultMutableTreeNode indexesNode = new DefaultMutableTreeNode("Indexes");
        DefaultMutableTreeNode generatorsNode = new DefaultMutableTreeNode("Sequences");
        DefaultMutableTreeNode usersNode = new DefaultMutableTreeNode("Users");

        for (String name : metadata.getTables())
            tablesNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getViews())
            viewsNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getProcedures())
            proceduresNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getFunctions())
            functionsNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getTriggers())
            triggersNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getIndexes())
            indexesNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getGenerators())
            generatorsNode.add(new DefaultMutableTreeNode(name));

        for (String name : metadata.getUsers())
            usersNode.add(new DefaultMutableTreeNode(name));

        root.add(tablesNode);
        root.add(viewsNode);
        root.add(proceduresNode);
        root.add(functionsNode);
        root.add(triggersNode);
        root.add(indexesNode);
        root.add(generatorsNode);
        root.add(usersNode);

        treeDatabase.setModel(new DefaultTreeModel(root));

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Error loading metadata: " + e.getMessage());
    }
}
    

    
    public void showGui() {
        setVisible(true);
    }
    
    private void executeSQL() {
    try {
        Connection conn = connectionManager.getActiveConnection();
        String sql = txtsql.getText().trim();

        if (sql.isEmpty()) return;

        try (Statement stmt = conn.createStatement()) {
            if (sql.toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(sql);
                TblResults.setModel(buildTableModel(rs));
            } else {
                int affected = stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(this,
                        "Statement executed successfully. Rows affected: " + affected);
            }
        }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error executing SQL: " + e.getMessage());
        }
    }
    
    private javax.swing.table.TableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        //Nombre de las columnas
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i).trim());
        }

        // Lo mismo pero con filas
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }

        return new javax.swing.table.DefaultTableModel(data, columnNames);
    }
    
    /**
     * Creates new form MainFrame
     */
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jSplitPane1 = new javax.swing.JSplitPane();
        LeftSidePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        treeDatabase = new javax.swing.JTree();
        RightSidePanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtsql = new javax.swing.JTextArea();
        SQLEditorPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblResults = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        ButtonPanel = new javax.swing.JPanel();
        ButtonExecute = new javax.swing.JButton();
        MenuBar = new javax.swing.JMenuBar();
        MenuFile = new javax.swing.JMenu();
        MenuExit = new javax.swing.JMenuItem();
        MenuConnection = new javax.swing.JMenu();
        MenuNewConn = new javax.swing.JMenuItem();
        MenuCloseConn = new javax.swing.JMenuItem();
        MenuRefresh = new javax.swing.JMenuItem();
        MenuTools = new javax.swing.JMenu();
        MenuCreateTbl = new javax.swing.JMenuItem();
        MenuCreateView = new javax.swing.JMenuItem();
        MenuHelp = new javax.swing.JMenu();
        MenuAbout = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Firebird Database Manager");

        jSplitPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setToolTipText("");

        jScrollPane3.setViewportView(treeDatabase);

        javax.swing.GroupLayout LeftSidePanelLayout = new javax.swing.GroupLayout(LeftSidePanel);
        LeftSidePanel.setLayout(LeftSidePanelLayout);
        LeftSidePanelLayout.setHorizontalGroup(
            LeftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftSidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );
        LeftSidePanelLayout.setVerticalGroup(
            LeftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftSidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(LeftSidePanel);

        RightSidePanel.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("SQL Editor");

        txtsql.setColumns(20);
        txtsql.setRows(5);
        jScrollPane2.setViewportView(txtsql);

        SQLEditorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TblResults);

        jLabel2.setText("Resultados");

        javax.swing.GroupLayout SQLEditorPanelLayout = new javax.swing.GroupLayout(SQLEditorPanel);
        SQLEditorPanel.setLayout(SQLEditorPanelLayout);
        SQLEditorPanelLayout.setHorizontalGroup(
            SQLEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SQLEditorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SQLEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        SQLEditorPanelLayout.setVerticalGroup(
            SQLEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SQLEditorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        ButtonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ButtonExecute.setText("Execute");
        ButtonExecute.addActionListener(this::ButtonExecuteActionPerformed);
        ButtonPanel.add(ButtonExecute);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(SQLEditorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(ButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SQLEditorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        RightSidePanel.add(jPanel6, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(RightSidePanel);

        MenuFile.setText("File");

        MenuExit.setText("Exit");
        MenuExit.addActionListener(this::MenuExitActionPerformed);
        MenuFile.add(MenuExit);

        MenuBar.add(MenuFile);

        MenuConnection.setText("Connection");

        MenuNewConn.setText("New Connection");
        MenuNewConn.addActionListener(this::MenuNewConnActionPerformed);
        MenuConnection.add(MenuNewConn);

        MenuCloseConn.setText("Close Connection");
        MenuCloseConn.addActionListener(this::MenuCloseConnActionPerformed);
        MenuConnection.add(MenuCloseConn);

        MenuRefresh.setText("Refresh");
        MenuRefresh.addActionListener(this::MenuRefreshActionPerformed);
        MenuConnection.add(MenuRefresh);

        MenuBar.add(MenuConnection);

        MenuTools.setText("Tools");

        MenuCreateTbl.setText("Create Table");
        MenuTools.add(MenuCreateTbl);

        MenuCreateView.setText("Create View");
        MenuCreateView.addActionListener(this::MenuCreateViewActionPerformed);
        MenuTools.add(MenuCreateView);

        MenuBar.add(MenuTools);

        MenuHelp.setText("Help");

        MenuAbout.setText("About");
        MenuHelp.add(MenuAbout);

        MenuBar.add(MenuHelp);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        jSplitPane1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuRefreshActionPerformed
        // TODO add your handling code here:
        loadTree();
    }//GEN-LAST:event_MenuRefreshActionPerformed

    private void MenuNewConnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuNewConnActionPerformed
        // TODO add your handling code here:
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MenuNewConnActionPerformed

    private void MenuCloseConnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCloseConnActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_MenuCloseConnActionPerformed

    private void MenuCreateViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCreateViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuCreateViewActionPerformed

    private void ButtonExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExecuteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonExecuteActionPerformed

    private void MenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuExitActionPerformed
        // TODO add your handling code here:
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MenuExitActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonExecute;
    private javax.swing.JPanel ButtonPanel;
    private javax.swing.JPanel LeftSidePanel;
    private javax.swing.JMenuItem MenuAbout;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem MenuCloseConn;
    private javax.swing.JMenu MenuConnection;
    private javax.swing.JMenuItem MenuCreateTbl;
    private javax.swing.JMenuItem MenuCreateView;
    private javax.swing.JMenuItem MenuExit;
    private javax.swing.JMenu MenuFile;
    private javax.swing.JMenu MenuHelp;
    private javax.swing.JMenuItem MenuNewConn;
    private javax.swing.JMenuItem MenuRefresh;
    private javax.swing.JMenu MenuTools;
    private javax.swing.JPanel RightSidePanel;
    private javax.swing.JPanel SQLEditorPanel;
    private javax.swing.JTable TblResults;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree treeDatabase;
    private javax.swing.JTextArea txtsql;
    // End of variables declaration//GEN-END:variables
}
