/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Interfaces.Evento;

import Clases.EventoC;
import Interfaces.Principal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;

/**
 *
 * @author cverd
 */
public class GruposEvento extends javax.swing.JFrame {

    private EventoC evento;
    private int personajeInscriID = -1;
    
    /**
     * Creates new form GruposEvento
     */
    public GruposEvento(EventoC evento) {
        this.evento = evento;
        initComponents();
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setTitle("Agrupar Inscritos");
        
        fillCbPjInscri();
        fillCbNumGroup();
        
        tableInscriAgrupados.addMouseListener(new MouseAdapter() 
        {
           public void mouseClicked(MouseEvent e) 
           {
               
            DefaultTableModel model = (DefaultTableModel) tableInscriAgrupados.getModel();    
        
              int fila = tableInscriAgrupados.rowAtPoint(e.getPoint());
              int columna = tableInscriAgrupados.columnAtPoint(e.getPoint());
              if ((fila > -1) && (columna > -1)){
                  
                  int id = (int) model.getValueAt(fila, 0);
                  
//                  System.out.println(id);
                  personajeInscriID = id;
                
              }
           }
        });
    }
    
    
    private void fillCbPjInscri(){
        cbPjInscri.removeAllItems();
        JSONArray lista = evento.getInscritos();
        
        cbPjInscri.addItem("------------------------------");
        for (int i = 0; i < lista.length(); i++){
            cbPjInscri.addItem(Integer.toString(lista.getJSONObject(i).getInt("id"))+"."+lista.getJSONObject(i).getString("name"));
        }
    
    }

    private void fillCbNumGroup(){
        cbNumGroup.removeAllItems();
        
        for (int i = 1; i <= evento.numGroups; i++){
            cbNumGroup.addItem(Integer.toString(i));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cbPjInscri = new javax.swing.JComboBox<>();
        lblPJinscri = new javax.swing.JLabel();
        btnAgrupar = new javax.swing.JButton();
        lblPJinscri1 = new javax.swing.JLabel();
        cbNumGroup = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInscriAgrupados = new javax.swing.JTable();
        btnQuitar = new javax.swing.JButton();
        btnCancelarPE = new javax.swing.JButton();
        btnContinuarPE = new javax.swing.JButton();
        btnAtrasPE = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelHeader.setBackground(new java.awt.Color(34, 38, 42));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        panelContainer.setBackground(new java.awt.Color(153, 153, 153));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agrupar"));

        lblPJinscri.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPJinscri.setForeground(new java.awt.Color(255, 255, 255));
        lblPJinscri.setText("PERSONAJE:");

        btnAgrupar.setBackground(new java.awt.Color(0, 153, 51));
        btnAgrupar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgrupar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgrupar.setText("AGRUPAR");
        btnAgrupar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgruparActionPerformed(evt);
            }
        });

        lblPJinscri1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPJinscri1.setForeground(new java.awt.Color(255, 255, 255));
        lblPJinscri1.setText("# de GRUPO:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbNumGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgrupar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPJinscri)
                            .addComponent(cbPjInscri, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPJinscri1))
                        .addGap(0, 78, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPJinscri)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPjInscri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPJinscri1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNumGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgrupar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableInscriAgrupados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Grupo Afiliacion", "# Grupo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableInscriAgrupados.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableInscriAgrupados);
        if (tableInscriAgrupados.getColumnModel().getColumnCount() > 0) {
            tableInscriAgrupados.getColumnModel().getColumn(0).setResizable(false);
            tableInscriAgrupados.getColumnModel().getColumn(0).setPreferredWidth(1);
            tableInscriAgrupados.getColumnModel().getColumn(1).setResizable(false);
            tableInscriAgrupados.getColumnModel().getColumn(1).setPreferredWidth(5);
            tableInscriAgrupados.getColumnModel().getColumn(2).setResizable(false);
            tableInscriAgrupados.getColumnModel().getColumn(2).setPreferredWidth(5);
            tableInscriAgrupados.getColumnModel().getColumn(3).setResizable(false);
            tableInscriAgrupados.getColumnModel().getColumn(3).setPreferredWidth(3);
        }

        btnQuitar.setBackground(new java.awt.Color(153, 0, 0));
        btnQuitar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnQuitar.setForeground(new java.awt.Color(255, 255, 255));
        btnQuitar.setText("QUITAR");

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnQuitar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancelarPE.setBackground(new java.awt.Color(153, 0, 0));
        btnCancelarPE.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCancelarPE.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarPE.setText("CANCELAR");
        btnCancelarPE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPEActionPerformed(evt);
            }
        });

        btnContinuarPE.setBackground(new java.awt.Color(0, 153, 51));
        btnContinuarPE.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnContinuarPE.setForeground(new java.awt.Color(255, 255, 255));
        btnContinuarPE.setText("CONTINUAR");

        btnAtrasPE.setBackground(new java.awt.Color(0, 153, 204));
        btnAtrasPE.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAtrasPE.setForeground(new java.awt.Color(255, 255, 255));
        btnAtrasPE.setText("ATRAS");
        btnAtrasPE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCancelarPE)
                        .addGap(411, 411, 411)
                        .addComponent(btnAtrasPE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContinuarPE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarPE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAtrasPE)
                        .addComponent(btnContinuarPE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarPEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPEActionPerformed
        Principal abrir = new Principal();
        abrir.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCancelarPEActionPerformed

    private void btnAtrasPEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPEActionPerformed
        // TODO add your handling code here:
        PersonajeEvento abrir = new PersonajeEvento(evento);
        abrir.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnAtrasPEActionPerformed

    private void btnAgruparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgruparActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_btnAgruparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgrupar;
    private javax.swing.JButton btnAtrasPE;
    private javax.swing.JButton btnCancelarPE;
    private javax.swing.JButton btnContinuarPE;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cbNumGroup;
    private javax.swing.JComboBox<String> cbPjInscri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPJinscri;
    private javax.swing.JLabel lblPJinscri1;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTable tableInscriAgrupados;
    // End of variables declaration//GEN-END:variables
}
