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
public class ParafernaliaList extends javax.swing.JFrame {

    private int personajeID;
    private boolean isPart = false;
    
    /**
     * Creates new form ParafernaliaList
     */
    public ParafernaliaList(int id_pj) {
        this.personajeID = id_pj;
        
        initComponents();
       
        txtAltura_m.setEditable(false);
        txtPeso_m.setEditable(false);
        
        fillTable();
       
        fillcbParafernalia();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setTitle("Agregar parafernalia");
        
          tableParafernalia.addMouseListener(new MouseAdapter() 
        {
           public void mouseClicked(MouseEvent e) 
           {
               
            DefaultTableModel model = (DefaultTableModel) tableParafernalia.getModel();    
        
              int fila = tableParafernalia.rowAtPoint(e.getPoint());
              int columna = tableParafernalia.columnAtPoint(e.getPoint());
              if ((fila > -1) && (columna > -1)){
                  
                  int id = (int) model.getValueAt(fila, 0);
                  
                  System.out.println(id);
                  txtIDParafernalia.setText(Integer.toString(id));
                
              }
           }
        });
    }

    private void fillcbParafernalia(){
        cbParafernalia.removeAllItems();
        System.out.println("entro");
        ArrayList<String> lista = Agregado.listParafernalia(personajeID);
        
        cbParafernalia.addItem("--------------------");
        for (int i = 0; i < lista.size(); i++){
            cbParafernalia.addItem(lista.get(i));
        }
        
        cbParafernalia.setEnabled(false);
    
    }
    
    public void emptyTable(){
        DefaultTableModel model = (DefaultTableModel) tableParafernalia.getModel();
        
        int filas = tableParafernalia.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
   
    public void fillTable(){
        emptyTable();
        
        JSONArray listaParafernalia = Agregado.getParafernalia(personajeID);
        DefaultTableModel model = (DefaultTableModel) tableParafernalia.getModel();        
        
        for (int i = 0; i<listaParafernalia.length(); i++){
            JSONObject parafernalia = listaParafernalia.getJSONObject(i);
            
            if (parafernalia.getBoolean("esPP")){
//                System.out.println("SOY PP");
                model.addRow(new Object[]{parafernalia.getInt("id"),parafernalia.getString("name"), parafernalia.getString("tipo"),parafernalia.getDouble("peso"),parafernalia.getDouble("altura")});
            }
            else{
//                System.out.println("NO SOY PP");
                model.addRow(new Object[]{parafernalia.getInt("id"),parafernalia.getString("name"), parafernalia.getString("tipo"),"",""});
            }
        }
        
    }
    
    private void fillPanelMod(String id) {
        JSONObject parafernalia = Agregado.getParafernaliaMod(id);
       
        if (parafernalia.getBoolean("esPP")){
            txtAltura_m.setEditable(true);
            txtPeso_m.setEditable(true);
            
            txtID_m.setText(id);
            txtNombreParafernalia_m.setText(parafernalia.getString("nombre"));
            txtAltura_m.setText(Double.toString(parafernalia.getDouble("altura")));
            txtPeso_m.setText(Double.toString(parafernalia.getDouble("peso")));
        }
        else{
            txtID_m.setText(id);
            txtAltura_m.setEditable(false);
            txtPeso_m.setEditable(false);
            
            txtNombreParafernalia_m.setText(parafernalia.getString("nombre"));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableParafernalia = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnAddParafernalia = new javax.swing.JButton();
        cbTipoP = new javax.swing.JComboBox<>();
        checkIsParte = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        txtNombreParafernalia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbParafernalia = new javax.swing.JComboBox<>();
        txtAltura = new javax.swing.JTextField();
        txtPeso = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtIDParafernalia = new javax.swing.JTextField();
        btnDelParafernalia = new javax.swing.JButton();
        btnModParafernalia = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnAddParafernalia1 = new javax.swing.JButton();
        cbTipoP_m = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtNombreParafernalia_m = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtAltura_m = new javax.swing.JTextField();
        txtPeso_m = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtID_m = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnAtrasPL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(34, 38, 42));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(315, 315, 315))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        tableParafernalia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Tipo", "Kg", "Mts"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableParafernalia);
        if (tableParafernalia.getColumnModel().getColumnCount() > 0) {
            tableParafernalia.getColumnModel().getColumn(0).setMinWidth(40);
            tableParafernalia.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableParafernalia.getColumnModel().getColumn(0).setMaxWidth(40);
            tableParafernalia.getColumnModel().getColumn(1).setPreferredWidth(10);
            tableParafernalia.getColumnModel().getColumn(2).setPreferredWidth(5);
            tableParafernalia.getColumnModel().getColumn(3).setPreferredWidth(6);
            tableParafernalia.getColumnModel().getColumn(4).setPreferredWidth(6);
        }

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar"));

        btnAddParafernalia.setBackground(new java.awt.Color(0, 153, 51));
        btnAddParafernalia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAddParafernalia.setForeground(new java.awt.Color(255, 255, 255));
        btnAddParafernalia.setText("AGREGAR");
        btnAddParafernalia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddParafernaliaActionPerformed(evt);
            }
        });

        cbTipoP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Armadura", "Arma", "Otro" }));

        checkIsParte.setBackground(new java.awt.Color(153, 153, 153));
        checkIsParte.setText("Parte");
        checkIsParte.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkIsParteStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TIPO:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMBRE:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("COMPONENTE DE:");

        cbParafernalia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ALTURA:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PESO:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbParafernalia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbTipoP, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(checkIsParte))
                    .addComponent(txtNombreParafernalia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddParafernalia))
                    .addComponent(jLabel6))
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreParafernalia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkIsParte)
                    .addComponent(cbTipoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddParafernalia)
                    .addComponent(cbParafernalia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtIDParafernalia.setEditable(false);

        btnDelParafernalia.setText("ELIMINAR");
        btnDelParafernalia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelParafernaliaActionPerformed(evt);
            }
        });

        btnModParafernalia.setText("MODIFICAR");
        btnModParafernalia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModParafernaliaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIDParafernalia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModParafernalia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelParafernalia)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDParafernalia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModParafernalia)
                    .addComponent(btnDelParafernalia))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Modificar"));

        btnAddParafernalia1.setBackground(new java.awt.Color(153, 0, 204));
        btnAddParafernalia1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAddParafernalia1.setForeground(new java.awt.Color(255, 255, 255));
        btnAddParafernalia1.setText("ACTUALIZAR");
        btnAddParafernalia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddParafernalia1ActionPerformed(evt);
            }
        });

        cbTipoP_m.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Armadura", "Arma", "Otro" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("TIPO:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NOMBRE:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ALTURA:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("PESO:");

        txtID_m.setEditable(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ID:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(cbTipoP_m, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreParafernalia_m, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID_m, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtPeso_m, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAltura_m, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddParafernalia1))
                    .addComponent(jLabel11))
                .addGap(10, 10, 10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreParafernalia_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAltura_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTipoP_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPeso_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddParafernalia1)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton2.setBackground(new java.awt.Color(0, 153, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SIGUIENTE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnAtrasPL.setBackground(new java.awt.Color(153, 0, 0));
        btnAtrasPL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAtrasPL.setForeground(new java.awt.Color(255, 255, 255));
        btnAtrasPL.setText("ATRAS");
        btnAtrasPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasPLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAtrasPL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtrasPL)
                    .addComponent(jButton2))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkIsParteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkIsParteStateChanged
        // TODO add your handling code here:
        
        if (!isPart){
            isPart = true;
            cbParafernalia.setEnabled(true);    
            txtAltura.setEnabled(false);
            txtPeso.setEnabled(false);
        }
        else{
            
            isPart = false;
            cbParafernalia.setEnabled(false);
            txtAltura.setEnabled(true);
            txtPeso.setEnabled(true);
        }
        
        
    }//GEN-LAST:event_checkIsParteStateChanged

    private void btnAddParafernaliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddParafernaliaActionPerformed
        // TODO add your handling code here:
        JSONObject parafernalia = new JSONObject();
        
        if (!txtNombreParafernalia.getText().isEmpty()){
            if(checkIsParte.isSelected()){
                if(cbParafernalia.getSelectedIndex()>0){
                    parafernalia.put("name",txtNombreParafernalia.getText());
                    parafernalia.put("tipo", cbTipoP.getSelectedItem().toString());
                    
                    String[] infoParafernalia = cbParafernalia.getSelectedItem().toString().split("\\.");
                    
                    parafernalia.put("id_pa", infoParafernalia[0]);
                    
                    Agregado.AddParafernalia(parafernalia, personajeID, true);
                   
                    fillTable();
                    fillcbParafernalia();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Debes seleccionar una parafernalia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                if (!txtPeso.getText().isEmpty()){
                    if (!txtAltura.getText().isEmpty()){
                        parafernalia.put("name",txtNombreParafernalia.getText());
                        parafernalia.put("tipo", cbTipoP.getSelectedItem().toString());
                        parafernalia.put("peso",txtPeso.getText());
                        parafernalia.put("altura",txtAltura.getText());
                        
                        
                        Agregado.AddParafernalia(parafernalia, personajeID, false);
                   
                        fillTable();
                        fillcbParafernalia();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Altura no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Peso no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Nombre no puede estar vacio", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddParafernaliaActionPerformed

    private void btnAtrasPLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasPLActionPerformed
        // TODO add your handling code here:
        AddPersonaje abrir = new AddPersonaje();
        abrir.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnAtrasPLActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnModParafernaliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModParafernaliaActionPerformed
        // TODO add your handling code here:
        
        if (!txtIDParafernalia.getText().isEmpty() && Integer.parseInt(txtIDParafernalia.getText()) > 0 ){
            fillPanelMod(txtIDParafernalia.getText());
        }
    }//GEN-LAST:event_btnModParafernaliaActionPerformed

    private void btnAddParafernalia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddParafernalia1ActionPerformed
        // TODO add your handling code here:
        
        JSONObject personaje = new JSONObject();

        if (!txtNombreParafernalia_m.getText().isEmpty()){
            
            if (txtAltura_m.isEditable()){
                
                if(!txtAltura_m.getText().isEmpty()){
                    if(!txtPeso_m.getText().isEmpty()){

                        personaje.put("esPP", true);
                        personaje.put("nombre", txtNombreParafernalia_m.getText());
                        personaje.put("tipo", cbTipoP_m.getSelectedItem());
                        personaje.put("altura", txtAltura_m.getText());
                        personaje.put("peso", txtPeso_m.getText());
                        personaje.put("id", Integer.parseInt(txtID_m.getText()));

                        Agregado.ModParafernalia(personaje);

                        fillTable();
                        txtIDParafernalia.setText("");
                        
                    }else 
                        JOptionPane.showMessageDialog(this, "Peso no insertado", "Error", JOptionPane.ERROR_MESSAGE);
                }else
                    JOptionPane.showMessageDialog(this, "Altura no insertada", "Error", JOptionPane.ERROR_MESSAGE);
                
            }
            else{
                personaje.put("esPP", false);
                personaje.put("nombre", txtNombreParafernalia_m.getText());
                personaje.put("tipo", cbTipoP_m.getSelectedItem());
                personaje.put("id", Integer.parseInt(txtID_m.getText()));

                Agregado.ModParafernalia(personaje);

                fillTable();
                txtIDParafernalia.setText("");
            }
            
            
        }else
            JOptionPane.showMessageDialog(this, "Nombre no seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
            
    }//GEN-LAST:event_btnAddParafernalia1ActionPerformed

    private void btnDelParafernaliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelParafernaliaActionPerformed
        // TODO add your handling code here:
        
        if (!txtIDParafernalia.getText().isEmpty() && Integer.parseInt(txtIDParafernalia.getText()) > 0 ){
            Agregado.DelParafernalia(txtIDParafernalia.getText());
            fillTable();
        }
    }//GEN-LAST:event_btnDelParafernaliaActionPerformed
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
                new ParafernaliaList(2).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddParafernalia;
    private javax.swing.JButton btnAddParafernalia1;
    private javax.swing.JButton btnAtrasPL;
    private javax.swing.JButton btnDelParafernalia;
    private javax.swing.JButton btnModParafernalia;
    private javax.swing.JComboBox<String> cbParafernalia;
    private javax.swing.JComboBox<String> cbTipoP;
    private javax.swing.JComboBox<String> cbTipoP_m;
    private javax.swing.JCheckBox checkIsParte;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableParafernalia;
    private javax.swing.JTextField txtAltura;
    private javax.swing.JTextField txtAltura_m;
    private javax.swing.JTextField txtIDParafernalia;
    private javax.swing.JTextField txtID_m;
    private javax.swing.JTextField txtNombreParafernalia;
    private javax.swing.JTextField txtNombreParafernalia_m;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtPeso_m;
    // End of variables declaration//GEN-END:variables
}
