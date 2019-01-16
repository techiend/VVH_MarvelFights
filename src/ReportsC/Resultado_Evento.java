/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package ReportsC;

import Clases.CombateC;
import Clases.ReporteEvento;
import Interfaces.Principal;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author cverd
 */
public class Resultado_Evento extends javax.swing.JFrame {

    private int EventoID;
    
    /**
     * Creates new form Evento
     */
    public Resultado_Evento(int EventoID) {
        this.EventoID = EventoID;
        this.setResizable(true);
        
        initComponents();
        
        this.setLocationRelativeTo(null);
        setTitle("Resultados Evento");
        
        fillInfo(this.EventoID);
    }
    
    private void fillInfo(int EventoID) {
        JSONObject  info = ReporteEvento.getInfo(EventoID);
        
        /*EVENTO*/
        txtName.setText(info.getString("name"));
        txtFechaI.setText(info.getString("fechaI"));
        txtFechaF.setText(info.getString("fechaF"));
        txtDesc.setText(info.getString("desc"));
        
        /*GANADOR*/
        txtNameWin.setText(info.getString("winner"));
        txtNameG.setText(info.getString("nameGrupo"));
        txtGrupoIndPow.setText(Double.toString(info.getDouble("indicador")));
        
        JSONArray habs = info.getJSONArray("habs");
        for (int i = 0; i<habs.length(); i++){
            JSONObject habilidad = habs.getJSONObject(i);
            
            switch(habilidad.getString("name")){
                case "Resistencia":
                    txtResis.setText(Integer.toString(habilidad.getInt("value")));
                    break;
                case "Proyeccion de energia":
                    txtEnerg.setText(Integer.toString(habilidad.getInt("value")));
                    break;
                case "Habilidades de combate":
                    txtPelea.setText(Integer.toString(habilidad.getInt("value")));
                    break;
                case "Inteligencia":
                    txtIntelig.setText(Integer.toString(habilidad.getInt("value")));
                    break;
                case "Velocidad":
                    txtSpeed.setText(Integer.toString(habilidad.getInt("value")));
                    break;
                case "Fuerza":
                    txtFuerza.setText(Integer.toString(habilidad.getInt("value")));
                    break;
            }
        }
        
        fillTableInscrito(info.getJSONArray("inscritos"));
        
        fillTableE1(info.getJSONArray("etapa1"));
        fillTableE2(info.getJSONArray("etapa2"));
        fillTableE3(info.getJSONArray("etapa3"));
    }
    
    public void emptyTableInscritos(){
        DefaultTableModel model = (DefaultTableModel) tableInscritos.getModel();
        
        int filas = tableInscritos.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
    
    public void emptyTableE1(){
        DefaultTableModel model = (DefaultTableModel) tableEtapa1.getModel();
        
        int filas = tableEtapa1.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
    
    public void emptyTableE2(){
        DefaultTableModel model = (DefaultTableModel) tableEtapa2.getModel();
        
        int filas = tableEtapa2.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
    
    public void emptyTableE3(){
        DefaultTableModel model = (DefaultTableModel) tableEtapa3.getModel();
        
        int filas = tableEtapa3.getRowCount();
        for (int i = 1; i <= filas; i++){
            model.removeRow(0);
        }
    }
   
    public void fillTableInscrito(JSONArray inscritos){
        emptyTableInscritos();
        
        DefaultTableModel model = (DefaultTableModel) tableInscritos.getModel();        
        
        for (int i = 0; i<inscritos.length(); i++){
            JSONObject inscrito = inscritos.getJSONObject(i);
            
            model.addRow(new Object[]{inscrito.getString("name"),inscrito.getString("nameGrupo"), inscrito.getInt("numGrupo"), inscrito.getInt("puntosEtapa1")});
        }
        
    }
    
    public void fillTableE1(JSONArray etapa){
        emptyTableE1();
        
        DefaultTableModel model = (DefaultTableModel) tableEtapa1.getModel();        
        
        for (int i = 0; i<etapa.length(); i++){
            JSONObject contienda = etapa.getJSONObject(i);
            
            model.addRow(new Object[]{contienda.getString("peleador1"),contienda.getString("peleador2"),contienda.getString("fecha"),contienda.getString("duracion"),
                (contienda.getInt("ganador") == 1)?contienda.getString("peleador1"):contienda.getString("peleador2")});
        }
        
    }
    
    public void fillTableE2(JSONArray etapa){
        emptyTableE2();
        
        DefaultTableModel model = (DefaultTableModel) tableEtapa2.getModel();        
        
        for (int i = 0; i<etapa.length(); i++){
            JSONObject contienda = etapa.getJSONObject(i);
            
            model.addRow(new Object[]{contienda.getString("peleador1"),contienda.getString("peleador2"),contienda.getString("fecha"),contienda.getString("duracion"),
                (contienda.getInt("ganador") == 1)?contienda.getString("peleador1"):contienda.getString("peleador2")});
        }
        
    }
    
    public void fillTableE3(JSONArray etapa){
        emptyTableE3();
        
        DefaultTableModel model = (DefaultTableModel) tableEtapa3.getModel();        
        
        for (int i = 0; i<etapa.length(); i++){
            JSONObject contienda = etapa.getJSONObject(i);
            
            model.addRow(new Object[]{contienda.getString("peleador1"),contienda.getString("peleador2"),contienda.getString("fecha"),contienda.getString("duracion"),
                (contienda.getInt("ganador") == 1)?contienda.getString("peleador1"):contienda.getString("peleador2")});
        }
        
    }
    
    @Override
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Images/Battle1.png"));
        return retValue;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Body = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        SubBody = new javax.swing.JPanel();
        lbName = new javax.swing.JLabel();
        txtName = new javax.swing.JLabel();
        lbFechaI = new javax.swing.JLabel();
        txtFechaI = new javax.swing.JLabel();
        txtFechaF = new javax.swing.JLabel();
        lbFechaF = new javax.swing.JLabel();
        lbDesc = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        lbEtapa1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableEtapa1 = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        lbEtapa2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txtGrupoIndPow = new javax.swing.JLabel();
        lbEtapa4 = new javax.swing.JLabel();
        txtNameWin = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableInscritos = new javax.swing.JTable();
        jSeparator5 = new javax.swing.JSeparator();
        lbWin1 = new javax.swing.JLabel();
        txtNameG = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbWin2 = new javax.swing.JLabel();
        lbWin3 = new javax.swing.JLabel();
        lbWin4 = new javax.swing.JLabel();
        lbWin5 = new javax.swing.JLabel();
        lbWin6 = new javax.swing.JLabel();
        lbWin7 = new javax.swing.JLabel();
        txtResis = new javax.swing.JLabel();
        txtEnerg = new javax.swing.JLabel();
        txtPelea = new javax.swing.JLabel();
        txtIntelig = new javax.swing.JLabel();
        txtSpeed = new javax.swing.JLabel();
        txtFuerza = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbWin14 = new javax.swing.JLabel();
        lbWin15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableEtapa2 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableEtapa3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setPreferredSize(new java.awt.Dimension(825, 570));
        setResizable(false);

        Header.setBackground(new java.awt.Color(34, 38, 42));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(jLabel1)
                .addContainerGap(361, Short.MAX_VALUE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        Body.setBackground(new java.awt.Color(255, 255, 255));
        Body.setEnabled(false);
        Body.setPreferredSize(new java.awt.Dimension(800, 1100));

        btnCancelar.setBackground(new java.awt.Color(153, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jScrollPane7.setPreferredSize(new java.awt.Dimension(780, 1200));

        SubBody.setBackground(new java.awt.Color(153, 153, 153));
        SubBody.setPreferredSize(new java.awt.Dimension(778, 1420));

        lbName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setText("NOMBRE EVENTO:");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 255, 255));
        txtName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtName.setText("NOMBRE DEL EVENTO");
        txtName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbFechaI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbFechaI.setForeground(new java.awt.Color(255, 255, 255));
        lbFechaI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbFechaI.setText("FECHA INICIO:");

        txtFechaI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFechaI.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtFechaI.setText("DD/MM/YYYY");

        txtFechaF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFechaF.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtFechaF.setText("DD/MM/YYYY");

        lbFechaF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbFechaF.setForeground(new java.awt.Color(255, 255, 255));
        lbFechaF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbFechaF.setText("FECHA FIN:");

        lbDesc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbDesc.setForeground(new java.awt.Color(255, 255, 255));
        lbDesc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDesc.setText("DESCRIPCIÓN:");

        txtDesc.setEditable(false);
        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        jScrollPane1.setViewportView(txtDesc);

        lbEtapa1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEtapa1.setForeground(new java.awt.Color(255, 255, 255));
        lbEtapa1.setText("ETAPA #1:");

        tableEtapa1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Contrincante #1", "Contrincante #2", "Fecha", "Duracion", "Ganador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane2.setViewportView(tableEtapa1);

        lbEtapa2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEtapa2.setForeground(new java.awt.Color(255, 255, 255));
        lbEtapa2.setText("ETAPA #2:");

        txtGrupoIndPow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtGrupoIndPow.setForeground(new java.awt.Color(255, 255, 255));
        txtGrupoIndPow.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtGrupoIndPow.setText("0.00");

        lbEtapa4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEtapa4.setForeground(new java.awt.Color(255, 255, 255));
        lbEtapa4.setText("ETAPA #3:");

        txtNameWin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNameWin.setForeground(new java.awt.Color(255, 255, 255));
        txtNameWin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtNameWin.setText("NOMBRE DEL GANADOR");
        txtNameWin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tableInscritos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Grupo Afiliacion", "# de Grupo", "Ptos. Etapa #1"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        jScrollPane6.setViewportView(tableInscritos);

        lbWin1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin1.setForeground(new java.awt.Color(255, 255, 255));
        lbWin1.setText("GRUPO DE AFILIACIÓN REPRESENTADO:");

        txtNameG.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNameG.setForeground(new java.awt.Color(255, 255, 255));
        txtNameG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtNameG.setText("NOMBRE DEL GRUPO");
        txtNameG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 28)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("¡GANADOR!");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 28)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("HABILIDADES");

        lbWin2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin2.setForeground(new java.awt.Color(255, 255, 255));
        lbWin2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWin2.setText("ENERGÍA");

        lbWin3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin3.setForeground(new java.awt.Color(255, 255, 255));
        lbWin3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWin3.setText("RESISTENCIA");

        lbWin4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin4.setForeground(new java.awt.Color(255, 255, 255));
        lbWin4.setText("HABILIDADES DE PELEA");

        lbWin5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin5.setForeground(new java.awt.Color(255, 255, 255));
        lbWin5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWin5.setText("INTELIGENCIA");

        lbWin6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin6.setForeground(new java.awt.Color(255, 255, 255));
        lbWin6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWin6.setText("VELOCIDAD");

        lbWin7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin7.setForeground(new java.awt.Color(255, 255, 255));
        lbWin7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWin7.setText("FUERZA");

        txtResis.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        txtResis.setForeground(new java.awt.Color(255, 255, 255));
        txtResis.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtResis.setText("0");

        txtEnerg.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        txtEnerg.setForeground(new java.awt.Color(255, 255, 255));
        txtEnerg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEnerg.setText("0");

        txtPelea.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        txtPelea.setForeground(new java.awt.Color(255, 255, 255));
        txtPelea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtPelea.setText("0");

        txtIntelig.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        txtIntelig.setForeground(new java.awt.Color(255, 255, 255));
        txtIntelig.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtIntelig.setText("0");

        txtSpeed.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        txtSpeed.setForeground(new java.awt.Color(255, 255, 255));
        txtSpeed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSpeed.setText("0");

        txtFuerza.setFont(new java.awt.Font("Tw Cen MT", 1, 48)); // NOI18N
        txtFuerza.setForeground(new java.awt.Color(255, 255, 255));
        txtFuerza.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtFuerza.setText("0");

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 1, 28)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ETAPAS");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 1, 28)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("INSCRITOS");

        lbWin14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin14.setForeground(new java.awt.Color(255, 255, 255));
        lbWin14.setText("GANADOR:");

        lbWin15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbWin15.setForeground(new java.awt.Color(255, 255, 255));
        lbWin15.setText("INDICADOR DE PODER AUMENTADO:");

        tableEtapa2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Contrincante #1", "Contrincante #2", "Fecha", "Duracion", "Ganador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane3.setViewportView(tableEtapa2);

        tableEtapa3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Contrincante #1", "Contrincante #2", "Fecha", "Duracion", "Ganador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane8.setViewportView(tableEtapa3);

        javax.swing.GroupLayout SubBodyLayout = new javax.swing.GroupLayout(SubBody);
        SubBody.setLayout(SubBodyLayout);
        SubBodyLayout.setHorizontalGroup(
            SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator5)
                    .addGroup(SubBodyLayout.createSequentialGroup()
                        .addComponent(txtResis, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEnerg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPelea, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIntelig, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFuerza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator6)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubBodyLayout.createSequentialGroup()
                        .addGap(0, 26, Short.MAX_VALUE)
                        .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbFechaF, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SubBodyLayout.createSequentialGroup()
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubBodyLayout.createSequentialGroup()
                        .addComponent(lbEtapa1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jSeparator3)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(SubBodyLayout.createSequentialGroup()
                        .addComponent(lbWin3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbWin2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbWin4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbWin5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbWin6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbWin7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(SubBodyLayout.createSequentialGroup()
                        .addComponent(lbEtapa2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3))
                    .addGroup(SubBodyLayout.createSequentialGroup()
                        .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SubBodyLayout.createSequentialGroup()
                                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbWin14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbWin1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbWin15, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNameWin, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNameG, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGrupoIndPow, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(SubBodyLayout.createSequentialGroup()
                        .addComponent(lbEtapa4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane8)))
                .addContainerGap())
        );
        SubBodyLayout.setVerticalGroup(
            SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SubBodyLayout.createSequentialGroup()
                        .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbFechaF, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaF, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbWin14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameWin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbWin1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameG, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbWin15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrupoIndPow, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbWin2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWin3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWin7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWin6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWin5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbWin4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEnerg, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtResis, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFuerza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIntelig, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPelea, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEtapa1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEtapa2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEtapa4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane7.setViewportView(SubBody);

        javax.swing.GroupLayout BodyLayout = new javax.swing.GroupLayout(Body);
        Body.setLayout(BodyLayout);
        BodyLayout.setHorizontalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BodyLayout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        BodyLayout.setVerticalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addGap(1128, 1128, 1128))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Body, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Body, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        Principal abrir = new Principal();
        abrir.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    
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
            java.util.logging.Logger.getLogger(Resultado_Evento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Resultado_Evento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Resultado_Evento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Resultado_Evento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Resultado_Evento(3).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Body;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel SubBody;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lbDesc;
    private javax.swing.JLabel lbEtapa1;
    private javax.swing.JLabel lbEtapa2;
    private javax.swing.JLabel lbEtapa4;
    private javax.swing.JLabel lbFechaF;
    private javax.swing.JLabel lbFechaI;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbWin1;
    private javax.swing.JLabel lbWin14;
    private javax.swing.JLabel lbWin15;
    private javax.swing.JLabel lbWin2;
    private javax.swing.JLabel lbWin3;
    private javax.swing.JLabel lbWin4;
    private javax.swing.JLabel lbWin5;
    private javax.swing.JLabel lbWin6;
    private javax.swing.JLabel lbWin7;
    private javax.swing.JTable tableEtapa1;
    private javax.swing.JTable tableEtapa2;
    private javax.swing.JTable tableEtapa3;
    private javax.swing.JTable tableInscritos;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JLabel txtEnerg;
    private javax.swing.JLabel txtFechaF;
    private javax.swing.JLabel txtFechaI;
    private javax.swing.JLabel txtFuerza;
    private javax.swing.JLabel txtGrupoIndPow;
    private javax.swing.JLabel txtIntelig;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtNameG;
    private javax.swing.JLabel txtNameWin;
    private javax.swing.JLabel txtPelea;
    private javax.swing.JLabel txtResis;
    private javax.swing.JLabel txtSpeed;
    // End of variables declaration//GEN-END:variables

}
