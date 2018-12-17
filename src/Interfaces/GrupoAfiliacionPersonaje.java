/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Interfaces;

import Clases.Agregado;
import Clases.GrupoAfiliacionC;
import Clases.ProfesionC;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cverd
 */
public class GrupoAfiliacionPersonaje extends javax.swing.JFrame {

    private int personajeID;
    private boolean isPart = false;
    private boolean isNext = false;
    
    /**
     * Creates new form ParafernaliaList
     */
    public GrupoAfiliacionPersonaje(int id_personaje, boolean isNext) {
        this.personajeID = id_personaje;
        this.isNext = isNext;
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setTitle("Unirse a grupo de afiliacion");
        
        initComponents();
        
        if (!isNext){
            btnNext.setVisible(false);
        }
       
        fillTable();
        
        
        tableGrupoAfiliacion.addMouseListener(new MouseAdapter() 
        {
           public void mouseClicked(MouseEvent e) 
           {
               
            DefaultTableModel model = (DefaultTableModel) tableGrupoAfiliacion.getModel();    
        
              int fila = tableGrupoAfiliacion.rowAtPoint(e.getPoint());
              int columna = tableGrupoAfiliacion.columnAtPoint(e.getPoint());
              if ((fila > -1) && (columna > -1)){
                  
                  int id = (int) model.getValueAt(fila, 0);
                  
                  System.out.println(id);
                  txtIDGrupoAfiliacion.setText(Integer.toString(id));
                
              }
           }
        });
    }

    public void emptyTable(){
        DefaultTableModel model = (DefaultTableModel) tableGrupoAfiliacion.getModel();
        
        int filas = tableGrupoAfiliacion.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
   
    public void fillTable(){
        emptyTable();
        
        JSONArray listaGrupoAfiliacion = GrupoAfiliacionC.getGruposAfiliacion();
        DefaultTableModel model = (DefaultTableModel) tableGrupoAfiliacion.getModel();        
        
        for (int i = 0; i<listaGrupoAfiliacion.length(); i++){
            JSONObject grupoAfiliacion = listaGrupoAfiliacion.getJSONObject(i);
            
            model.addRow(new Object[]{grupoAfiliacion.getInt("id"),grupoAfiliacion.getString("name"), grupoAfiliacion.getString("desc"), grupoAfiliacion.getInt("ipa"), grupoAfiliacion.getString("base"), grupoAfiliacion.getString("dir")});
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtIDGrupoAfiliacion = new javax.swing.JTextField();
        btnJoin = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGrupoAfiliacion = new javax.swing.JTable();
        btnCreateGrupo = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnAtrasPL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(34, 38, 42));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtIDGrupoAfiliacion.setEditable(false);

        btnJoin.setText("UNIRSE");
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIDGrupoAfiliacion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnJoin)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDGrupoAfiliacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJoin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableGrupoAfiliacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripccion", "Ind. Poder Aumentado", "Base", "Direccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableGrupoAfiliacion);

        btnCreateGrupo.setBackground(new java.awt.Color(0, 153, 51));
        btnCreateGrupo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCreateGrupo.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateGrupo.setText("CREAR NUEVO GRUPO DE AFILIACION");
        btnCreateGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateGrupoActionPerformed(evt);
            }
        });

        btnRefresh.setText("ACTUALIZAR");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCreateGrupo)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCreateGrupo)
                        .addComponent(btnRefresh)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNext.setBackground(new java.awt.Color(0, 153, 51));
        btnNext.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText("SIGUIENTE");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnAtrasPL.setBackground(new java.awt.Color(153, 0, 0));
        btnAtrasPL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAtrasPL.setForeground(new java.awt.Color(255, 255, 255));
        btnAtrasPL.setText("CANCELAR");
        btnAtrasPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAtrasPL)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNext)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnAtrasPL))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasPLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPLActionPerformed
        // TODO add your handling code here:
        int decision = 0;
        
        if (isNext){
            String [] botones = { "Continuar", "Cancelar"};
            decision = JOptionPane.showOptionDialog (null, "¿Estas seguro que deseas cancelar? Deberas llenar esta informacion luego", "¡CUIDADO!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null/*icono*/, botones, botones[0]);
             
            if (decision == 1){
                Personajes abrir = new Personajes();
                abrir.setVisible(true);
                dispose();
            }
        }
        else{
            dispose();
        }
        
    }//GEN-LAST:event_btnAtrasPLActionPerformed

    private void btnCreateGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateGrupoActionPerformed
        // TODO add your handling code here:
        
        GrupoAfiliacionList abrir = new GrupoAfiliacionList();
        abrir.setVisible(true);
        
    }//GEN-LAST:event_btnCreateGrupoActionPerformed

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        // TODO add your handling code here:
        if (!txtIDGrupoAfiliacion.getText().isEmpty()){
        
            int response = GrupoAfiliacionC.joinGrupoAfiliacion(Integer.parseInt(txtIDGrupoAfiliacion.getText()), personajeID, false);
            
            switch(response){
            
                case 0:
                    JOptionPane.showMessageDialog(this, "Se ha unido satisfactoriamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(this, "Personaje ya se encuentra en este Grupo", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Error al unirse al grupo", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
            
    }//GEN-LAST:event_btnJoinActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        
        fillTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        AddHabilidad abrir = new AddHabilidad(personajeID);
        abrir.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNextActionPerformed
  /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddPersonaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPersonaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPersonaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPersonaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrupoAfiliacionPersonaje(6, false).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtrasPL;
    private javax.swing.JButton btnCreateGrupo;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableGrupoAfiliacion;
    private javax.swing.JTextField txtIDGrupoAfiliacion;
    // End of variables declaration//GEN-END:variables
}
