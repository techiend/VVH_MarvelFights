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
import Clases.GrupoAfiliacionC;
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
public class FichaGrupoAfiliacion extends javax.swing.JFrame {

    /**
     * Creates new form Inscritos
     */
    public FichaGrupoAfiliacion() {
        this.setResizable(false);
        initComponents();
        //fillTable();
        fillCB();
        //Categoria.addItem("todos");
        this.setLocationRelativeTo(null);
        setTitle("Consulta Personaje");
        tablePersonaje.addMouseListener(new MouseAdapter() 
        {
           public void mouseClicked(MouseEvent e) 
           {
               
            DefaultTableModel model = (DefaultTableModel) tablePersonaje.getModel();    
        
              int fila = tablePersonaje.rowAtPoint(e.getPoint());
              int columna = tablePersonaje.columnAtPoint(e.getPoint());
              if ((fila > -1) && (columna > -1)){
                  
                  int id = (int) model.getValueAt(fila, 0);
                  
                  System.out.println(id);
                  //txtIDPersonaje.setText(Integer.toString(id));
                  FichaPersonaje abrirFicha = new FichaPersonaje(id);
                  abrirFicha.setVisible(true);
              }
           }
        });
        
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
            Logger.getLogger(FichaGrupoAfiliacion.class.getName()).log(Level.SEVERE, null, ex);
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
                personaje.put("lname", (rsGetPersonaje.getString(5) == null) ? "" : rsGetPersonaje.getString(5));
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
                personaje.put("lname", (rsGetPersonaje.getString(5) == null) ? "" : rsGetPersonaje.getString(5));
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
            Logger.getLogger(FichaGrupoAfiliacion.class.getName()).log(Level.SEVERE, null, ex);
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
        panelModProfesion = new javax.swing.JPanel();
        txtNameGrupoAfiliacion_m = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescGrupoAfiliacion_m = new javax.swing.JTextArea();
        txtIDGrupoAfiliacion_m = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtIPA_m = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtEstado_m = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPais_m = new javax.swing.JTextField();
        txtCiudad_m = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtBaseOperaciones_m = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDescBaseOperaciones_m = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        txtIDBase_m = new javax.swing.JTextField();
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

        Cambio.setText("Consultar");
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

        panelModProfesion.setBackground(new java.awt.Color(153, 153, 153));
        panelModProfesion.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion General"));

        txtNameGrupoAfiliacion_m.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NOMBRE:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("DESCRIPCION:");

        txtDescGrupoAfiliacion_m.setEditable(false);
        txtDescGrupoAfiliacion_m.setColumns(20);
        txtDescGrupoAfiliacion_m.setRows(5);
        jScrollPane3.setViewportView(txtDescGrupoAfiliacion_m);

        txtIDGrupoAfiliacion_m.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ID GRUPO:");

        txtIPA_m.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ind. Poder Aumentado:");

        txtEstado_m.setEditable(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ESTADO:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("PAIS:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("CIUDAD:");

        txtPais_m.setEditable(false);

        txtCiudad_m.setEditable(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("BASE DE OPERACIONES:");

        txtBaseOperaciones_m.setEditable(false);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("DESCRIPCION BASE DE OPERACIONES:");

        txtDescBaseOperaciones_m.setEditable(false);
        txtDescBaseOperaciones_m.setColumns(20);
        txtDescBaseOperaciones_m.setRows(5);
        jScrollPane5.setViewportView(txtDescBaseOperaciones_m);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("ID BASE:");

        txtIDBase_m.setEditable(false);

        javax.swing.GroupLayout panelModProfesionLayout = new javax.swing.GroupLayout(panelModProfesion);
        panelModProfesion.setLayout(panelModProfesionLayout);
        panelModProfesionLayout.setHorizontalGroup(
            panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModProfesionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelModProfesionLayout.createSequentialGroup()
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addComponent(txtNameGrupoAfiliacion_m, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelModProfesionLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel18))
                            .addGroup(panelModProfesionLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(txtBaseOperaciones_m, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelModProfesionLayout.createSequentialGroup()
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIPA_m, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelModProfesionLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtIDBase_m, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelModProfesionLayout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtIDGrupoAfiliacion_m, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(79, 79, 79)
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelModProfesionLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel13)
                                .addGroup(panelModProfesionLayout.createSequentialGroup()
                                    .addComponent(txtCiudad_m, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addComponent(txtEstado_m, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtPais_m, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelModProfesionLayout.setVerticalGroup(
            panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModProfesionLayout.createSequentialGroup()
                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel18))
                .addGap(5, 5, 5)
                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameGrupoAfiliacion_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBaseOperaciones_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelModProfesionLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIPA_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtIDGrupoAfiliacion_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtIDBase_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelModProfesionLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelModProfesionLayout.createSequentialGroup()
                                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(32, 32, 32)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPais_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelModProfesionLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(panelModProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCiudad_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstado_m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelarP)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addComponent(panelModProfesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelModProfesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelarP))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    String[] parts = Categoria.getSelectedItem().toString().split(" ");
        String Codigo1 = parts[0];
            JSONObject info = Categorias.getGrupoAfiliacion(Integer.parseInt(Codigo1));
        
        txtNameGrupoAfiliacion_m.setText(info.getString("name_grupo"));
        txtDescGrupoAfiliacion_m.setText(info.getString("desc_grupo"));
        txtBaseOperaciones_m.setText(info.getString("base_base"));
        txtDescBaseOperaciones_m.setText(info.getString("desc_base"));
        
        txtIPA_m.setText(Double.toString(info.getDouble("ipa_grupo")));
        
        txtIDGrupoAfiliacion_m.setText(Integer.toString(info.getInt("id_grupo")));
        txtIDBase_m.setText(Integer.toString(info.getInt("id_base")));
        txtCiudad_m.setText(info.getString("ciudad_base"));
        txtEstado_m.setText(info.getString("estado_base"));
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
            java.util.logging.Logger.getLogger(FichaGrupoAfiliacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FichaGrupoAfiliacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FichaGrupoAfiliacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FichaGrupoAfiliacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new FichaGrupoAfiliacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cambio;
    private javax.swing.JComboBox<String> Categoria;
    private javax.swing.JButton btnCancelarP;
    private javax.swing.ButtonGroup buttonGroupSecretaPublica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel panelModProfesion;
    private javax.swing.JTable tablePersonaje;
    private javax.swing.JTextField txtBaseOperaciones_m;
    private javax.swing.JTextField txtCiudad_m;
    private javax.swing.JTextArea txtDescBaseOperaciones_m;
    private javax.swing.JTextArea txtDescGrupoAfiliacion_m;
    private javax.swing.JTextField txtEstado_m;
    private javax.swing.JTextField txtIDBase_m;
    private javax.swing.JTextField txtIDGrupoAfiliacion_m;
    private javax.swing.JTextField txtIPA_m;
    private javax.swing.JTextField txtNameGrupoAfiliacion_m;
    private javax.swing.JTextField txtPais_m;
    // End of variables declaration//GEN-END:variables
}
