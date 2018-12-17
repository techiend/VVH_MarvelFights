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
import Clases.Categorias;
import DBHelper.DBClass;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cverd
 */
public class BusquedaCategoria extends javax.swing.JFrame {

    /**
     * Creates new form Inscritos
     */
    public BusquedaCategoria() {
        this.setResizable(false);
        initComponents();
        fillTable();
        fillCB();
        Categoria.addItem("todos");
        this.setLocationRelativeTo(null);
        setTitle("Consulta Personaje");
        
        
    }
 @Override
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Images/People2.png"));
        return retValue;
    }
    
     
    public void emptyTable(){
        DefaultTableModel model = (DefaultTableModel) tablePersonaje.getModel();
        
        int filas = tablePersonaje.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
   
    public void fillTable(){
        emptyTable();
        
        JSONArray listaPersonaje = Agregado.getPersonaje();
        DefaultTableModel model = (DefaultTableModel) tablePersonaje.getModel();        
        
        for (int i = 0; i<listaPersonaje.length(); i++){
            JSONObject personaje = listaPersonaje.getJSONObject(i);
            
            model.addRow(new Object[]{personaje.getInt("id"),personaje.getString("tipo"), personaje.getString("nameo"), personaje.getString("namer"), personaje.getString("lname"), personaje.getString("identidad"), personaje.getString("biografia"), personaje.getString("estadocivil"), personaje.getString("genero"), personaje.getInt("altura"), personaje.getInt("peso"), personaje.getString("colorojos"), personaje.getString("colorpelo")});
        }
        
    }
    
    

    
    public void fillCB(){
        try{
            Connection conn = DBClass.getConn();
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("select id_grupoafiliacion, nombre_grupoafiliacion from acc_grupo_afiliacion");
            
            Categoria.removeAllItems();
            
            while (rs.next())
            {
            Categoria.addItem(rs.getString(1).concat(" - ").concat(rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusquedaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         public JSONArray getPersonajeFiltrado(){
        JSONArray listaPersonaje = new JSONArray();
        String[] parts = Categoria.getSelectedItem().toString().split(" ");
        String Codigo = parts[0];
        //String Codigo = Categoria.getSelectedItem().toString().substring(0,1);
        //TomaCodigo(Codigo);
         System.out.print(Codigo);
        if(Categoria.getSelectedItem() == "todos"){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetAlumnos = conn.prepareStatement("SELECT id_personaje \"ID\", tipo_personaje \"Tipo Personaje\", "
                    + "nombreoriginal_personaje \"Nombre Original\", nombrereal_personaje \"Nombre Real]\", "
                    + "apellidoreal_personaje \"Apellido Real\", identidad_personaje \"Identidad\", "
                    + "biografia_personaje \"Biografia\", estadocivil_personaje \"Estado Civil\" , genero_personaje \"Genero\","
                    + "altura_personaje \"Altura\", peso_personaje \"Peso\", "
                    + "colorojos_personaje \"Color ojos\",colorpelo_personaje \"Color cabello\""
                    + " FROM acc_personaje;")
        ){
            
            ResultSet rsGetPersonaje = pstGetAlumnos.executeQuery();
            
            while (rsGetPersonaje.next()){
                JSONObject personaje = new JSONObject();
                
                personaje.put("id", rsGetPersonaje.getInt(1));
                personaje.put("tipo", rsGetPersonaje.getString(2));
                personaje.put("nameo", rsGetPersonaje.getString(3));
                personaje.put("namer", rsGetPersonaje.getString(4));
                personaje.put("lname", rsGetPersonaje.getString(5));
                personaje.put("identidad", rsGetPersonaje.getString(6));
                personaje.put("biografia", rsGetPersonaje.getString(7));
                personaje.put("estadocivil", rsGetPersonaje.getString(8));
                personaje.put("genero", rsGetPersonaje.getString(9));
                personaje.put("altura", rsGetPersonaje.getInt(10));
                personaje.put("peso", rsGetPersonaje.getInt(11));
                personaje.put("colorojos", rsGetPersonaje.getString(12));
                personaje.put("colorpelo", rsGetPersonaje.getString(13));
                
                

                listaPersonaje.put(personaje);
            }
            
            return listaPersonaje;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else {
        try(

            Connection conn = DBClass.getConn();
            PreparedStatement pstGetAlumnos = conn.prepareStatement("SELECT a.id_personaje \"ID\", a.tipo_personaje \"Tipo Personaje\", "
                    + "a.nombreoriginal_personaje \"Nombre Original\", a.nombrereal_personaje \"Nombre Real]\", "
                    + "a.apellidoreal_personaje \"Apellido Real\", a.identidad_personaje \"Identidad\", "
                    + "a.biografia_personaje \"Biografia\", a.estadocivil_personaje \"Estado Civil\" , a.genero_personaje \"Genero\","
                    + "a.altura_personaje \"Altura\", a.peso_personaje \"Peso\", "
                    + "a.colorojos_personaje \"Color ojos\",a.colorpelo_personaje \"Color cabello\""
                    + " FROM acc_personaje a, acc_grupo_afiliacion b, acc_hist_per_ga c where b.id_grupoafiliacion = "+Codigo+" and b.id_grupoafiliacion = c.grupoafiliacion_fk and c.personaje_fk = a.id_personaje;")
        ){
            
            ResultSet rsGetPersonaje = pstGetAlumnos.executeQuery();
            
            while (rsGetPersonaje.next()){
                JSONObject personaje = new JSONObject();
                
                personaje.put("id", rsGetPersonaje.getInt(1));
                personaje.put("tipo", rsGetPersonaje.getString(2));
                personaje.put("nameo", rsGetPersonaje.getString(3));
                personaje.put("namer", rsGetPersonaje.getString(4));
                personaje.put("lname", rsGetPersonaje.getString(5));
                personaje.put("identidad", rsGetPersonaje.getString(6));
                personaje.put("biografia", rsGetPersonaje.getString(7));
                personaje.put("estadocivil", rsGetPersonaje.getString(8));
                personaje.put("genero", rsGetPersonaje.getString(9));
                personaje.put("altura", rsGetPersonaje.getInt(10));
                personaje.put("peso", rsGetPersonaje.getInt(11));
                personaje.put("colorojos", rsGetPersonaje.getString(12));
                personaje.put("colorpelo", rsGetPersonaje.getString(13));
                
                

                listaPersonaje.put(personaje);
            }
            
            return listaPersonaje;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return listaPersonaje;
    }
    
         
         public String TomaCodigo(String Codigo) 
         {
         String categoria1 = (String) Categoria.getSelectedItem();
                     try{
            Connection conn1 = DBClass.getConn();
            Statement st=conn1.createStatement();
            ResultSet rs=st.executeQuery("select id_grupoafiliacion from acc_grupo_afiliacion where nombre_grupoafiliacion = '"+categoria1+"';");
            while (rs.next()){
                       Codigo = rs.getString("id_grupoafiliacion");
                       rs.next();
           }
            rs.close();
            return Codigo;
                     
        } catch (SQLException ex) {
            Logger.getLogger(BusquedaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             return Codigo;
         }
         
                 public void fillsearch(){
        emptyTable();
        
        JSONArray listaPersonaje = getPersonajeFiltrado();
        DefaultTableModel model = (DefaultTableModel) tablePersonaje.getModel();        
        
        for (int i = 0; i<listaPersonaje.length(); i++){
            JSONObject personaje = listaPersonaje.getJSONObject(i);
            
            model.addRow(new Object[]{personaje.getInt("id"),personaje.getString("tipo"), personaje.getString("nameo"), personaje.getString("namer"), personaje.getString("lname"), personaje.getString("identidad"), personaje.getString("biografia"), personaje.getString("estadocivil"), personaje.getString("genero"), personaje.getInt("altura"), personaje.getInt("peso"), personaje.getString("colorojos"), personaje.getString("colorpelo")});
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

        buttonGroupSecretaPublica = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePersonaje = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Categoria = new javax.swing.JComboBox<>();
        Cambio = new javax.swing.JButton();
        btnCancelarP = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        jPanel1.setBackground(new java.awt.Color(34, 38, 42));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));

        tablePersonaje.setBackground(new java.awt.Color(153, 153, 153));
        tablePersonaje.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TIPO", "NOMBRE O", "NOMBRE R", "APELLIDO REAL", "IDENTIDAD", "BIOGRAFIA", "EDO CIVIL", "GENERO", "ALTURA", "PESO", "COLOR OJOS", "COLOR CABELLO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablePersonaje);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PERSONAJES:");

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----------", "Personaje", "Alias", "Parafernalia", "Profesion" }));
        Categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoriaActionPerformed(evt);
            }
        });

        Cambio.setText("Buscar");
        Cambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CambioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(Cambio)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(696, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnCancelarP.setBackground(new java.awt.Color(153, 0, 0));
        btnCancelarP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCancelarP.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarP.setText("CANCELAR");
        btnCancelarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCancelarP)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarP)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 200, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPActionPerformed
        Principal abrir = new Principal();
        abrir.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCancelarPActionPerformed


    private void CategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CategoriaActionPerformed

    private void CambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CambioActionPerformed
    fillsearch();
    }//GEN-LAST:event_CambioActionPerformed

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
            java.util.logging.Logger.getLogger(BusquedaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusquedaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusquedaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusquedaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BusquedaCategoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cambio;
    private javax.swing.JComboBox<String> Categoria;
    private javax.swing.JButton btnCancelarP;
    private javax.swing.ButtonGroup buttonGroupSecretaPublica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablePersonaje;
    // End of variables declaration//GEN-END:variables
}
