package kg.apc.jmeter.vizualizers;

import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import kg.apc.jmeter.charting.AbstractGraphRow;
import kg.apc.jmeter.charting.RowsCollector;

/**
 *
 * @author Stephane Hoblingre
 */
public class JCompositeRowsSelectorPanel extends javax.swing.JPanel implements GraphRendererInterface {

    private DefaultMutableTreeNode root1;
    private DefaultMutableTreeNode root2;
    private DefaultTreeModel model1;
    private DefaultTreeModel model2;

    /** Creates new form JRowsSelectorPanel */
    public JCompositeRowsSelectorPanel() {
        initComponents();
        root1 = new DefaultMutableTreeNode("Test Plan", true);
        model1 = new DefaultTreeModel(root1);
        root2 = new DefaultMutableTreeNode("Composite Graph", true);
        model2 = new DefaultTreeModel(root2);
        jTreeGraph1.setModel(model1);
        jTreeGraph2.setModel(model2);

    }

    public void refreshPreview()
    {
        jPanelGraphPreview.invalidate();
        jPanelGraphPreview.repaint();
    }

    private boolean isNodeContained(String nodeName, DefaultMutableTreeNode root)
    {
        Enumeration children = root.children();
        while(children.hasMoreElements())
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
            if(nodeName.equals(child.toString()))
            {
                return true;
            }
        }
        return false;
    }

    private DefaultMutableTreeNode getNode(String nodeName, DefaultMutableTreeNode root)
    {
        Enumeration children = root.children();
        while(children.hasMoreElements())
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
            if(nodeName.equals(child.toString()))
            {
                return child;
            }
        }
        return null;
    }

    public void clearData()
    {
        root1.removeAllChildren();
        //root2.removeAllChildren();
        model1.nodeStructureChanged(root1);
        //model2.nodeStructureChanged(root2);
    }

    public void updateTree()
    {
        //rows will not disapear, only chart if cleared...
        RowsCollector rowsCollector = RowsCollector.getInstance();
        boolean chartsUpdated = false;
        boolean rowsUpdated = false;
        //first check if graph cleared
        Iterator<String> chartsIter = rowsCollector.getVizualizerNamesIterator();
        while(chartsIter.hasNext())
        {
            String chartName = chartsIter.next();
            if(!isNodeContained(chartName, root1))
            {
                chartsUpdated = true;
                DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(chartName, true);
                //DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(chartName, true);
                root1.add(node1);
                //root2.add(node2);
                Iterator<AbstractGraphRow> rowsIter = rowsCollector.getRowsIterator(chartName);
                while(rowsIter.hasNext())
                {
                    AbstractGraphRow row = rowsIter.next();
                    node1.add(new DefaultMutableTreeNode(row.getLabel(), false));
                    //node2.add(new DefaultMutableTreeNode(row.getLabel(), false));
                }
            } else
            {
                Iterator<AbstractGraphRow> rowsIter = rowsCollector.getRowsIterator(chartName);
                DefaultMutableTreeNode chartNode1 = getNode(chartName, root1);
                //DefaultMutableTreeNode chartNode2 = getNode(chartName, root2);

                while(rowsIter.hasNext())
                {
                   String rowName = rowsIter.next().getLabel();
                   if(!isNodeContained(rowName, chartNode1))
                   {
                       rowsUpdated = true;
                       chartNode1.add(new DefaultMutableTreeNode(rowName, false));
                       //chartNode2.add(new DefaultMutableTreeNode(rowName, false));
                   }
                }
                if(rowsUpdated)
                {
                    model1.nodeStructureChanged(chartNode1);
                    //model1.nodeStructureChanged(chartNode2);
                }
            }
        }

        if(chartsUpdated)
        {
            model1.nodeStructureChanged(root1);
            //model2.nodeStructureChanged(root2);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelLogo = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        jPanelMain = new javax.swing.JPanel();
        jPanelRowsTable = new javax.swing.JPanel();
        jLabelGraph1 = new javax.swing.JLabel();
        jLabelGraph2 = new javax.swing.JLabel();
        jScrollPaneGraph1 = new javax.swing.JScrollPane();
        jTreeGraph1 = new javax.swing.JTree();
        jScrollPaneGraph = new javax.swing.JScrollPane();
        jTreeGraph2 = new javax.swing.JTree();
        jPanelButtons = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanelGraphPreview = new javax.swing.JPanel();
        jLabelPreview = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        setLayout(new java.awt.BorderLayout());

        jPanelLogo.setLayout(new java.awt.GridLayout(1, 0));

        jLabelLogo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kg/apc/jmeter/vizualizers/logoSimple.png"))); // NOI18N
        jPanelLogo.add(jLabelLogo);

        add(jPanelLogo, java.awt.BorderLayout.PAGE_END);

        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jPanelRowsTable.setMaximumSize(new java.awt.Dimension(206, 23));
        jPanelRowsTable.setPreferredSize(new java.awt.Dimension(206, 23));
        jPanelRowsTable.setLayout(new java.awt.GridBagLayout());

        jLabelGraph1.setText("Available Sources:");
        jLabelGraph1.setMaximumSize(new java.awt.Dimension(120, 14));
        jLabelGraph1.setMinimumSize(new java.awt.Dimension(120, 14));
        jLabelGraph1.setPreferredSize(new java.awt.Dimension(120, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanelRowsTable.add(jLabelGraph1, gridBagConstraints);

        jLabelGraph2.setText("Composed Graph:");
        jLabelGraph2.setMaximumSize(new java.awt.Dimension(120, 14));
        jLabelGraph2.setMinimumSize(new java.awt.Dimension(120, 14));
        jLabelGraph2.setPreferredSize(new java.awt.Dimension(120, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanelRowsTable.add(jLabelGraph2, gridBagConstraints);

        jScrollPaneGraph1.setMaximumSize(new java.awt.Dimension(72, 64));
        jScrollPaneGraph1.setMinimumSize(new java.awt.Dimension(72, 64));
        jScrollPaneGraph1.setPreferredSize(new java.awt.Dimension(72, 64));
        jScrollPaneGraph1.setViewportView(jTreeGraph1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanelRowsTable.add(jScrollPaneGraph1, gridBagConstraints);

        jScrollPaneGraph.setViewportView(jTreeGraph2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanelRowsTable.add(jScrollPaneGraph, gridBagConstraints);

        jPanelButtons.setFocusable(false);
        jPanelButtons.setLayout(new java.awt.GridLayout(0, 1, 0, 6));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kg/apc/jmeter/vizualizers/rightArrow.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setMaximumSize(new java.awt.Dimension(30, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(30, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(30, 25));
        jPanelButtons.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kg/apc/jmeter/vizualizers/leftArrow.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setMaximumSize(new java.awt.Dimension(30, 25));
        jButton2.setMinimumSize(new java.awt.Dimension(30, 25));
        jButton2.setPreferredSize(new java.awt.Dimension(30, 25));
        jPanelButtons.add(jButton2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanelRowsTable.add(jPanelButtons, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelMain.add(jPanelRowsTable, gridBagConstraints);

        jPanelGraphPreview.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanelMain.add(jPanelGraphPreview, gridBagConstraints);

        jLabelPreview.setText("Preview:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        jPanelMain.add(jLabelPreview, gridBagConstraints);

        add(jPanelMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabelGraph1;
    private javax.swing.JLabel jLabelGraph2;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelPreview;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelGraphPreview;
    private javax.swing.JPanel jPanelLogo;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelRowsTable;
    private javax.swing.JScrollPane jScrollPaneGraph;
    private javax.swing.JScrollPane jScrollPaneGraph1;
    private javax.swing.JTree jTreeGraph1;
    private javax.swing.JTree jTreeGraph2;
    // End of variables declaration//GEN-END:variables

    @Override
    public JPanel getGraphDisplayPanel()
    {
        return jPanelGraphPreview;
    }

}
