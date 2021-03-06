/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Interfaces.Evento.Combate;

import Interfaces.*;
import Clases.Agregado;
import Clases.CombateC;
import Clases.EventoC;
import Controlador.DBController;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aless
 */
public class Combate extends javax.swing.JFrame {

    private EventoC evento;
    private int etapaNum;
    private int numContienda = -1;
    private Calendar fechaInicial;
    
    /**
     * Creates new form VerEvento
     */
    public Combate(EventoC evento, int numEtapa) {
        
        fechaInicial = Calendar.getInstance();
        fechaInicial.setTime(new Date()); 
        
        this.evento = evento;
        this.etapaNum = numEtapa;
        
        this.setResizable(false);
        initComponents();
        
        btnNextEtapa.setVisible(false);
        this.setLocationRelativeTo(null);
        setTitle("Etapa #"+etapaNum);
        
        fillTable();
        fillCbStatus();
        
        tableContienda.addMouseListener(new MouseAdapter() 
        {
           public void mouseClicked(MouseEvent e) 
           {
               
            DefaultTableModel model = (DefaultTableModel) tableContienda.getModel();    
        
              int fila = tableContienda.rowAtPoint(e.getPoint());
              int columna = tableContienda.columnAtPoint(e.getPoint());
              if ((fila > -1) && (columna > -1)){
                  
                int id = (int) model.getValueAt(fila, 0);
                String c1 = (String) model.getValueAt(fila, 1);
                String c2 = (String) model.getValueAt(fila, 2);
                  
                numContienda = id;
                txtPeleador1.setText(c1);
                txtPeleador2.setText(c2);
              }
           }
        });
    }
    
    
    public void emptyTable(){
        DefaultTableModel model = (DefaultTableModel) tableContienda.getModel();
        
        int filas = tableContienda.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
   
    public void fillTable(){
        emptyTable();
        
        JSONArray peleadores = CombateC.getPeleadores(evento, etapaNum);
        DefaultTableModel model = (DefaultTableModel) tableContienda.getModel();        
        
        for (int i = 0; i<peleadores.length(); i++){
            JSONObject peleador = peleadores.getJSONObject(i);
            
            model.addRow(new Object[]{peleador.getInt("id"),peleador.getString("c1"), peleador.getString("c2")});
        }
        
        if (peleadores.length() == 0){
            btnNextEtapa.setVisible(true);
        }
        
    }
    
    
    private void fillCbStatus(){
        cbStatus.removeAllItems();
        
        if (etapaNum == 1){
            
            cbStatus.addItem("----------------------------------------");
            cbStatus.addItem("1.Ganador #1");
            cbStatus.addItem("2.Ganador #2");
            cbStatus.addItem("3.Empate");
        }
        else{
            
            cbStatus.addItem("----------------------------------------");
            cbStatus.addItem("1.Ganador #1");
            cbStatus.addItem("2.Ganador #2");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableContienda = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        labelPeleador2 = new javax.swing.JLabel();
        labelPeleador1 = new javax.swing.JLabel();
        txtPeleador1 = new javax.swing.JTextField();
        txtPeleador2 = new javax.swing.JTextField();
        cbStatus = new javax.swing.JComboBox<>();
        labelStatus = new javax.swing.JLabel();
        btnChangePelea = new javax.swing.JButton();
        btnNextEtapa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(34, 38, 42));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(321, 321, 321)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        tableContienda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Contrincante 1", "Contrincante 2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableContienda);
        if (tableContienda.getColumnModel().getColumnCount() > 0) {
            tableContienda.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Modificar"));

        labelPeleador2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelPeleador2.setForeground(new java.awt.Color(255, 255, 255));
        labelPeleador2.setText("CONTRINCANTE #2:");

        labelPeleador1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelPeleador1.setForeground(new java.awt.Color(255, 255, 255));
        labelPeleador1.setText("CONTRINCANTE #1:");

        txtPeleador1.setEditable(false);

        txtPeleador2.setEditable(false);

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----------------------------------------", "1.Ganador #1", "2.Ganador #2", "3.Empate" }));

        labelStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelStatus.setForeground(new java.awt.Color(255, 255, 255));
        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelStatus.setText("STATUS PELEA:");
        labelStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnChangePelea.setBackground(new java.awt.Color(0, 153, 51));
        btnChangePelea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnChangePelea.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePelea.setText("ACTUALIZAR");
        btnChangePelea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePeleaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPeleador1)
                            .addComponent(labelPeleador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelPeleador2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPeleador2)))
                    .addComponent(cbStatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnChangePelea)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPeleador2)
                    .addComponent(labelPeleador1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPeleador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPeleador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnChangePelea)
                .addContainerGap())
        );

        btnNextEtapa.setBackground(new java.awt.Color(0, 153, 51));
        btnNextEtapa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNextEtapa.setForeground(new java.awt.Color(255, 255, 255));
        btnNextEtapa.setText("SIGUIENTE");
        btnNextEtapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextEtapaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNextEtapa)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNextEtapa)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangePeleaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePeleaActionPerformed
        // TODO add your handling code here:
        
        if (numContienda > 0){
            if (cbStatus.getSelectedIndex() > 0){
                
                String[] ganador = cbStatus.getSelectedItem().toString().split("\\.");
                
                switch(etapaNum){
                    case 1:

                        CombateC.setStatusPeleaEtapa1(fechaInicial, numContienda, Integer.parseInt(ganador[0]));

                        fillTable();
                        fillCbStatus();

                        txtPeleador1.setText("");
                        txtPeleador2.setText("");
                        numContienda = -1;
                        break;
                    case 2:

                        CombateC.setStatusPeleaEtapa2y3(fechaInicial, numContienda, Integer.parseInt(ganador[0]));

                        fillTable();
                        fillCbStatus();

                        txtPeleador1.setText("");
                        txtPeleador2.setText("");
                        numContienda = -1;
                        break;
                    case 3:

                        CombateC.setStatusPeleaEtapa2y3(fechaInicial, numContienda, Integer.parseInt(ganador[0]));

                        fillTable();
                        fillCbStatus();

                        txtPeleador1.setText("");
                        txtPeleador2.setText("");
                        numContienda = -1;
                        break;
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Debes seleccionar un estatus", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Debes seleccionar una pelea", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnChangePeleaActionPerformed

    private void btnNextEtapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextEtapaActionPerformed
        // TODO add your handling code here:
        
        if (etapaNum == 1){
            DBController.createPeleasEtapa2(evento);

            Combate abrir = new Combate(evento, 2);
            abrir.setVisible(true);
            dispose();
        }
        
        if (etapaNum == 2){
            DBController.createPeleasEtapa3(evento);

            Combate abrir = new Combate(evento, 3);
            abrir.setVisible(true);
            dispose();
        }
        
        if (etapaNum == 3){
            
            JSONObject ganador = CombateC.setWinner(evento);
            CombateC.setPoints(ganador.getInt("id"));
            
            JOptionPane.showMessageDialog(null, "El ganador del evento es: "+ganador.getInt("id"), "Excelente", JOptionPane.PLAIN_MESSAGE);
            
            Principal abrir = new Principal();
            abrir.setVisible(true);
            dispose();
            
        }
    }//GEN-LAST:event_btnNextEtapaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePelea;
    private javax.swing.JButton btnNextEtapa;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelPeleador1;
    private javax.swing.JLabel labelPeleador2;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JTable tableContienda;
    private javax.swing.JTextField txtPeleador1;
    private javax.swing.JTextField txtPeleador2;
    // End of variables declaration//GEN-END:variables
}
