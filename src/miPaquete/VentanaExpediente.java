package miPaquete;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Insets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.ListSelectionModel;
import org.jpedal.examples.viewer.Viewer;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import java.awt.Frame;

public class VentanaExpediente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ConexionBd conexion;
	private String actor, demandado, abg_actor, abg_dem, expte, estado, dom_actor, dom_dem, tipo, caratula_completa,casillero_Actor,casillero_Dem, 
					localidad_Act, localidad_Dem, caracter_abg_Actor, caracter_abg_Dem;
	private String fechaInicio, unidad, area, responsable, dni_actor, dni_dem, mail_dem, mail_actor, telefono_act, telefono_dem, dom_dig_act, 
					dom_dig_dem, tel_abg_actor, tel_abg_dem, mail_abg_act, mail_abg_dem, dom_abg_act, dom_abg_dem;
	private JTable table_historia;
	private ModeloTablaBuscar model;
	private TableRowSorter<TableModel> sorter;
	private JTextField tf_fecha;
	private JTextField tf_asunto;
	private JTextField tf_tipo;
	private JTextField tf_estado;

	
	public VentanaExpediente(int id, ConexionBd con) {
		setMinimumSize(new Dimension(1200, 800));
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conexion = con;
		
		actualizarDatos(conexion, id);
		
		setTitle(caratula_completa);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1151, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(0, 0));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel historia = new JPanel();
		tabbedPane.addTab("Historia", null, historia, null);
		
		
		table_historia = new JTable();
		table_historia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_historia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirActuacion(e);
			}
		});
		table_historia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					borraActuacion();
				}
			}
		});
		
		table_historia.setFont(new Font("Tahoma", Font.BOLD, 14));
		model = new ModeloTablaBuscar(new String[] {"ID","FECHA","ASUNTO","TIPO","ESTADO","PDF"},0);
		
		table_historia.setModel(model);
		TableColumn column = table_historia.getColumnModel().getColumn(0);
		table_historia.getColumnModel().removeColumn(column); // saco columna ID, no es visible pero sigue estado presente en el modelo
		column=table_historia.getColumnModel().getColumn(4);
		table_historia.getColumnModel().removeColumn(column); // remuevo column PDF, pero sigue disponible.
		
		table_historia.getColumnModel().getColumn(0).setPreferredWidth(80);
		table_historia.getColumnModel().getColumn(0).setMaxWidth(80); 
		table_historia.getColumnModel().getColumn(0).setMinWidth(80);
		table_historia.getColumnModel().getColumn(1).setPreferredWidth(500);
		table_historia.getColumnModel().getColumn(2).setMaxWidth(170);
		table_historia.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_historia.getColumnModel().getColumn(3).setMaxWidth(140);
		table_historia.getColumnModel().getColumn(3).setPreferredWidth(140);
	
		// Centro encabezado de tabla 
		JTableHeader encabezado = table_historia.getTableHeader();
		TableColumnModel columnModel = encabezado.getColumnModel();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) encabezado.getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column1 = columnModel.getColumn(i);
            column1.setHeaderRenderer(renderer);
        }
		
		sorter = new TableRowSorter<>(table_historia.getModel());
		historia.setLayout(new BorderLayout(0, 0));
		table_historia.setRowSorter(sorter);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_historia);
		
		JPanel panel_Izquierdo = new JPanel();
		panel_Izquierdo.setPreferredSize(new Dimension(800, 0));
		historia.add(panel_Izquierdo, BorderLayout.WEST);
		panel_Izquierdo.setLayout(new BorderLayout(0, 0));
		
		panel_Izquierdo.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_Busqueda = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_Busqueda.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_Busqueda.setPreferredSize(new Dimension(10, 40));
		panel_Busqueda.setFont(new Font("Arial", Font.BOLD, 12));
		panel_Busqueda.setBorder(new LineBorder(new Color(128, 128, 128), 2,true));
		panel_Izquierdo.add(panel_Busqueda, BorderLayout.NORTH);
		
		JLabel lblActor_1 = new JLabel("Fecha:");
		lblActor_1.setFont(new Font("Arial", Font.BOLD, 12));
		panel_Busqueda.add(lblActor_1);
		
		tf_fecha = new JTextField();
		tf_fecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabla();
			}
		});
		tf_fecha.setFont(new Font("Arial", Font.BOLD, 12));
		tf_fecha.setColumns(5);
		panel_Busqueda.add(tf_fecha);
		
		JLabel lblDemandado_1 = new JLabel("  Asunto:");
		lblDemandado_1.setFont(new Font("Arial", Font.BOLD, 12));
		panel_Busqueda.add(lblDemandado_1);
		
		tf_asunto = new JTextField();
		tf_asunto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabla();
			}
		});
		tf_asunto.setFont(new Font("Arial", Font.BOLD, 12));
		tf_asunto.setColumns(14);
		panel_Busqueda.add(tf_asunto);
		
		JLabel lblExpte_1 = new JLabel("  Tipo:");
		lblExpte_1.setFont(new Font("Arial", Font.BOLD, 12));
		panel_Busqueda.add(lblExpte_1);
		
		tf_tipo = new JTextField();
		tf_tipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabla();
			}
		});
		tf_tipo.setFont(new Font("Arial", Font.BOLD, 12));
		tf_tipo.setColumns(8);
		panel_Busqueda.add(tf_tipo);
		
		JLabel lblExpte_1_1 = new JLabel("  Estado:");
		lblExpte_1_1.setFont(new Font("Arial", Font.BOLD, 12));
		panel_Busqueda.add(lblExpte_1_1);
		
		tf_estado = new JTextField();
		tf_estado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabla();
			}
		});
		tf_estado.setFont(new Font("Arial", Font.BOLD, 12));
		tf_estado.setColumns(8);
		panel_Busqueda.add(tf_estado);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_estado.setText("");
				tf_tipo.setText("");
				tf_asunto.setText("");
				tf_fecha.setText("");
				sorter.setRowFilter(null);
			}
		});
		panel_Busqueda.add(btnLimpiar);
		
		JPanel panel_Botones_historia = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_Botones_historia.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_Botones_historia.setPreferredSize(new Dimension(500, 40));
		panel_Botones_historia.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panel_Izquierdo.add(panel_Botones_historia, BorderLayout.SOUTH);
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_Botones_historia.add(btnAgregar);
		
		JButton btnBorrar = new JButton("BORRAR");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borraActuacion();
			}
		});
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_Botones_historia.add(btnBorrar);
		
		JButton btnModificar = new JButton("MODIFICAR");
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_Botones_historia.add(btnModificar);
		
		cargarHistoria(id);
		JPanel tramites = new JPanel();
		tabbedPane.addTab("Tramites", null, tramites, null);
		
		JPanel Datos = new JPanel();
		Datos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Datos.setBorder(null);
		tabbedPane.addTab("Datos", null, Datos, null);
		Datos.setLayout(new BoxLayout(Datos, BoxLayout.Y_AXIS));
		
		JPanel panel_expediente = new JPanel();
		panel_expediente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_expediente.setBorder(new TitledBorder(null, "Expediente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_expediente.setPreferredSize(new Dimension(0, 0));
		Datos.add(panel_expediente);
		panel_expediente.setLayout(null);
		
		JLabel lblCaratula_pd = new JLabel("<html> Caratula: <b>" + caratula_completa + "</b></html>");
		lblCaratula_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCaratula_pd.setBounds(15, 29, 525, 22);
		panel_expediente.add(lblCaratula_pd);
		
		JLabel lblExpte_pd = new JLabel("<html> Expte:<b>" + expte + "</b></html>");
		lblExpte_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExpte_pd.setBounds(550, 29, 142, 22);
		panel_expediente.add(lblExpte_pd);
		
		JLabel lblTipo_proceso_pd = new JLabel("<html> Tipo proceso: <b>" + tipo + "</b></html>");
		lblTipo_proceso_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipo_proceso_pd.setBounds(240, 54, 248, 24);
		panel_expediente.add(lblTipo_proceso_pd);
		
		JLabel lblInicio_pd = new JLabel("<html> Inicio: <b>" + fechaInicio + "</b></html>");
		lblInicio_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInicio_pd.setBounds(15, 54, 154, 24);
		panel_expediente.add(lblInicio_pd);
		
		JLabel lblEstado_pd = new JLabel("<html> Estado: <b>" + estado + "</b></html>");
		lblEstado_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstado_pd.setBounds(550, 54, 182, 24);
		panel_expediente.add(lblEstado_pd);
		
		JLabel lblArea_pd = new JLabel("<html> Area: <b>" + area + "</b></html>");
		lblArea_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblArea_pd.setBounds(15, 79, 182, 22);
		panel_expediente.add(lblArea_pd);
		
		JLabel lblResponsable_pd = new JLabel("<html>Responsable: <b>" + responsable + "</b></html>");
		lblResponsable_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResponsable_pd.setBounds(240, 79, 276, 22);
		panel_expediente.add(lblResponsable_pd);
		
		JLabel lblUnidad_pd = new JLabel("<html>Unidad: <b>" + unidad + "</b></html>");
		lblUnidad_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUnidad_pd.setBounds(550, 79, 276, 22);
		panel_expediente.add(lblUnidad_pd);
		
		JPanel panel_actor = new JPanel();
		panel_actor.setBorder(new TitledBorder(null, "Actor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_actor.setPreferredSize(new Dimension(0, 80));
		Datos.add(panel_actor);
		panel_actor.setLayout(null);
		
		JLabel lblNombreActor_pd = new JLabel("<html>Nombre: <b>" + actor +"</b></html>");
		lblNombreActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreActor_pd.setBounds(15, 29, 406, 19);
		panel_actor.add(lblNombreActor_pd);
		
		JLabel lblDniActor_pd = new JLabel("<html>DNI: <b>" + dni_actor + "</b></html>");
		lblDniActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDniActor_pd.setBounds(437, 29, 214, 19);
		panel_actor.add(lblDniActor_pd);
		
		JLabel lblMailActor_pd = new JLabel("<html>Mail: <b>" + mail_actor + "</b></html>");
		lblMailActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMailActor_pd.setBounds(15, 79, 272, 19);
		panel_actor.add(lblMailActor_pd);
		
		JLabel lblDomicilioActor_pd = new JLabel("<html>Domicilio: <b>" + dom_actor + "</b></html>");
		lblDomicilioActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDomicilioActor_pd.setBounds(15, 54, 533, 19);
		panel_actor.add(lblDomicilioActor_pd);
		
		JLabel lblTelefonoActor_pd = new JLabel("<html> Telefono: <b>" + telefono_act + "</b></html>");
		lblTelefonoActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefonoActor_pd.setBounds(437, 79, 308, 19);
		panel_actor.add(lblTelefonoActor_pd);
		
		JLabel lblAbogadoActor_pd = new JLabel("<html> Abogado: <b>" + abg_actor +"</b></html>");
		lblAbogadoActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAbogadoActor_pd.setBounds(15, 104, 416, 19);
		panel_actor.add(lblAbogadoActor_pd);
		
		JLabel lblCaracterActor_pd = new JLabel("<html> Caracter: <b>" + caracter_abg_Actor + "</b></html>");
		lblCaracterActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCaracterActor_pd.setBounds(437, 104, 257, 19);
		panel_actor.add(lblCaracterActor_pd);
		
		JLabel lblTelAgbActor_pd = new JLabel("<html> Telefono abogado: <b>" + tel_abg_actor + "</b></html>");
		lblTelAgbActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelAgbActor_pd.setBounds(15, 129, 309, 19);
		panel_actor.add(lblTelAgbActor_pd);
		
		JLabel lblMailAbgActor_pd = new JLabel("<html> Mail Abog.: <b>"+ mail_abg_act + "</b></html>" );
		lblMailAbgActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMailAbgActor_pd.setBounds(437, 129, 272, 19);
		panel_actor.add(lblMailAbgActor_pd);
		
		JLabel lblDomAbgActor_pd = new JLabel("<html> Domicilio Abogado: <b>" + dom_abg_act + "</b></html>" );
		lblDomAbgActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDomAbgActor_pd.setBounds(15, 154, 500, 19);
		panel_actor.add(lblDomAbgActor_pd);
		
		JLabel lblCasDigAbgActor_pd = new JLabel("<html> Casillero digital: <b>" + casillero_Actor + "</b></html>");
		lblCasDigAbgActor_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCasDigAbgActor_pd.setBounds(15, 179, 230, 19);
		panel_actor.add(lblCasDigAbgActor_pd);
		
		JPanel panel_demando = new JPanel();
		panel_demando.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_demando.setBorder(new TitledBorder(null, "Demandado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_demando.setPreferredSize(new Dimension(0, 80));
		Datos.add(panel_demando);
		panel_demando.setLayout(null);
		
		JLabel lblNombre_dem_pd = new JLabel("<html> Nombre: <b>" + demandado + "</b></html>");
		lblNombre_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre_dem_pd.setBounds(15, 29, 406, 19);
		panel_demando.add(lblNombre_dem_pd);
		
		JLabel lblDomicilio_dem_pd = new JLabel("<html> Domicilio: <b>" + dom_dem + "</b></html>");
		lblDomicilio_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDomicilio_dem_pd.setBounds(15, 54, 533, 19);
		panel_demando.add(lblDomicilio_dem_pd);
		
		JLabel lblDni_dem_pd = new JLabel("<html> DNI: <b>" + dni_dem + "</b></html>");
		lblDni_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDni_dem_pd.setBounds(437, 29, 214, 19);
		panel_demando.add(lblDni_dem_pd);
		
		JLabel lblMail_dem_pd = new JLabel("<html> Mail: <b>" + mail_dem + "</b></html>");
		lblMail_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMail_dem_pd.setBounds(15, 79, 290, 19);
		panel_demando.add(lblMail_dem_pd);
		
		JLabel lblTelefono_dem_pd = new JLabel("<html> Telefono: <b>" + telefono_dem + "</b></html>");
		lblTelefono_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefono_dem_pd.setBounds(437, 79, 208, 19);
		panel_demando.add(lblTelefono_dem_pd);
		
		JLabel lblAbogadoDem_pd = new JLabel("<html> Abogado: <b>" + abg_dem + "</b></html>");
		lblAbogadoDem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAbogadoDem_pd.setBounds(15, 104, 439, 19);
		panel_demando.add(lblAbogadoDem_pd);
		
		JLabel lblCaracterDem_pd = new JLabel("<html> Caracter: <b>" + caracter_abg_Dem + "</b></html>");
		lblCaracterDem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCaracterDem_pd.setBounds(437, 104, 257, 19);
		panel_demando.add(lblCaracterDem_pd);
		
		JLabel lblTelAbgDem_pd = new JLabel("<html> Telefono abogado: <b>" + tel_abg_dem + "</b></html>");
		lblTelAbgDem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelAbgDem_pd.setBounds(15, 129, 333, 19);
		panel_demando.add(lblTelAbgDem_pd);
		
		JLabel lblMailAbogado_dem_pd = new JLabel("<html> Mail Abog.: <b>" + mail_abg_dem + "</b></html>" );
		lblMailAbogado_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMailAbogado_dem_pd.setBounds(437, 129, 274, 19);
		panel_demando.add(lblMailAbogado_dem_pd);
		
		JLabel lblDomicilioAbogado_dem_pd = new JLabel("<html> Domicilio Abogado: <b>" + dom_abg_dem + "</b></html>");
		lblDomicilioAbogado_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDomicilioAbogado_dem_pd.setBounds(15, 154, 445, 19);
		panel_demando.add(lblDomicilioAbogado_dem_pd);
		
		JLabel lblCasilleroDigital_dem_pd = new JLabel("<html> Casillero digital: <b>" + casillero_Dem + "</b></html>");
		lblCasilleroDigital_dem_pd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCasilleroDigital_dem_pd.setBounds(15, 179, 349, 19);
		panel_demando.add(lblCasilleroDigital_dem_pd);
		
		JPanel panel_Datos = new JPanel();
		panel_Datos.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panel_Datos.setPreferredSize(new Dimension(500, 90));
		contentPane.add(panel_Datos, BorderLayout.NORTH);
		panel_Datos.setLayout(null);
		
		JLabel lblActor = new JLabel("Actor:");
		lblActor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblActor.setBounds(5, 5, 36, 14);
		panel_Datos.add(lblActor);
		
		JLabel lblActor_ps = new JLabel(actor);
		lblActor_ps.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblActor_ps.setBounds(41, 5, 185, 14);
		panel_Datos.add(lblActor_ps);
		
		JLabel lblDemandado = new JLabel("Dem:");
		lblDemandado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDemandado.setBounds(5, 25, 29, 14);
		panel_Datos.add(lblDemandado);
		
		JLabel lblJulietaRoco = new JLabel(demandado);
		lblJulietaRoco.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblJulietaRoco.setBounds(34, 25, 192, 14);
		panel_Datos.add(lblJulietaRoco);
		
		JLabel lblNewLabel = new JLabel("Abg:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(259, 5, 29, 14);
		panel_Datos.add(lblNewLabel);
		
		JLabel lblAbg = new JLabel("Abg:");
		lblAbg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAbg.setBounds(259, 25, 29, 14);
		panel_Datos.add(lblAbg);
		
		JLabel lblNewLabel_1 = new JLabel(abg_actor);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(287, 5, 291, 14);
		panel_Datos.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel(abg_dem);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(287, 25, 291, 14);
		panel_Datos.add(lblNewLabel_1_1);
		
		JLabel lblDom = new JLabel("Dom:");
		lblDom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDom.setBounds(588, 5, 29, 14);
		panel_Datos.add(lblDom);
		
		JLabel lblNewLabel_1_2 = new JLabel(dom_actor + " | " + casillero_Actor);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(621, 5, 398, 14);
		panel_Datos.add(lblNewLabel_1_2);
		
		JLabel lblDom_1 = new JLabel("Dom:");
		lblDom_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDom_1.setBounds(588, 25, 29, 14);
		panel_Datos.add(lblDom_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel(dom_dem + " | " + casillero_Dem);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_1.setBounds(621, 25, 398, 14);
		panel_Datos.add(lblNewLabel_1_1_1);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTipo.setBounds(5, 45, 29, 14);
		panel_Datos.add(lblTipo);
		
		JLabel lblTipo1 = new JLabel(tipo);
		lblTipo1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTipo1.setBounds(34, 45, 171, 14);
		panel_Datos.add(lblTipo1);
		
		JLabel lblExpte = new JLabel("Expte:");
		lblExpte.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblExpte.setBounds(5, 64, 36, 14);
		panel_Datos.add(lblExpte);
		
		JLabel lblExpte1 = new JLabel(expte);
		lblExpte1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblExpte1.setBounds(44, 64, 161, 14);
		panel_Datos.add(lblExpte1);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEstado.setBounds(259, 45, 42, 14);
		panel_Datos.add(lblEstado);
		
		JLabel lblEstadoi = new JLabel(estado);
		lblEstadoi.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstadoi.setBounds(297, 44, 184, 14);
		panel_Datos.add(lblEstadoi);
		
		JPanel panel_derecho = new JPanel();
		panel_derecho.setPreferredSize(new Dimension(800, 0));
		panel_derecho.setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
		contentPane.add(panel_derecho, BorderLayout.EAST);
		panel_derecho.setLayout(new BorderLayout(0, 0));
	}
	
	private void abrirActuacion(MouseEvent e) {
		if(e.getClickCount() == 2) {
			int filaSeleccionada = table_historia.getSelectedRow();
			if(filaSeleccionada != -1) {
				
				InputStream input =  (InputStream) model.getValueAt(filaSeleccionada, 5);
				String fecha= (String) model.getValueAt(filaSeleccionada, 1);
				
				if(input.markSupported()) {
					input.mark(0);
				}
				abrirPDF(input,fecha);

				try {
					input.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void abrirPDF(InputStream input, String fecha) {
		File archivoTemp;
		String fechaConFormato = fecha.replace("/", "-");
		try {
			archivoTemp = File.createTempFile(fechaConFormato + "    ", ".pdf");
			archivoTemp.deleteOnExit();
			try (OutputStream output = new FileOutputStream(archivoTemp)) {
				byte[] buffer= new byte[1024];
				int bytesRead;
				while((bytesRead = input.read(buffer)) != -1) {
					output.write(buffer,0,bytesRead);
				}
			}
			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(archivoTemp);
				
			} else {
				JOptionPane.showConfirmDialog(null, "No se puede abrir PDF");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void filtrarTabla() {
		List<RowFilter<Object, Object>> filters = new ArrayList<>();
		filters.add(RowFilter.regexFilter(tf_asunto.getText().toUpperCase(), 2));
		filters.add(RowFilter.regexFilter(tf_tipo.getText().toUpperCase(), 3));
		filters.add(RowFilter.regexFilter(tf_estado.getText().toUpperCase(), 4));
		filters.add(RowFilter.regexFilter(tf_fecha.getText(), 1));
		sorter.setRowFilter(RowFilter.andFilter(filters));
	}

	private void borraActuacion() {
		int filaSeleccionada = table_historia.getSelectedRow();
		if(filaSeleccionada != -1 && !model.getValueAt(filaSeleccionada, 4).equals("FIRMADO")) { // si no esta algo seleccionado o la actuacion esta firmado no hace nada
			int id=(int) model.getValueAt(filaSeleccionada, 0);
			String query = "DELETE FROM `actuaciones_exptes` WHERE `actuaciones_exptes`.`id`="+id;
			int respuesta = JOptionPane.showConfirmDialog(this, "Se eliminará una actuación","Está seguro en eliminar?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			if(respuesta == JOptionPane.YES_OPTION) {
				conexion.sendQuery(query);
				model.removeRow(filaSeleccionada);
			}	
		}	
	}

	private void cargarHistoria(int id) {
		String query = "SELECT `actuaciones_exptes`.`id`,`actuaciones_exptes`.`fecha`,`actuaciones_exptes`.`asunto`,`actuaciones_exptes`.`estado`,`tipos`.`tipo`,`actuaciones_exptes`.`doc_pdf` FROM `actuaciones_exptes` JOIN `tipos` ON `actuaciones_exptes`.`tipo` = `tipos`.`id` WHERE `actuaciones_exptes`.`expte_id`="+id+" ORDER BY `actuaciones_exptes`.`fecha` DESC";                                                     
		conexion.sendQueryExecute(query);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		

		try {
			while(conexion.resul.next()) {
				String fecha = dateFormat.format(conexion.resul.getTimestamp("fecha"));        
				String asunto = conexion.resul.getString("asunto");
				String tipo = conexion.resul.getString("tipo");
				String estado = conexion.resul.getString("estado");
				int id1 = conexion.resul.getInt("id");
				InputStream input = conexion.resul.getBinaryStream("doc_pdf");
				model.addRow(new Object[] {id1,fecha,asunto,tipo,estado,input});
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private void actualizarDatos(ConexionBd con, int id) { // actualiza datos de cabecera de ventana "expediente"
		String query = "SELECT `expedientes`.`expediente`, `expedientes`.`tipo_proceso`, `expedientes`.`fechaInicio`, `expedientes`.`unidad`,"
				+ "`expedientes`.`estado`, `expedientes`.`area`, `expedientes`.`responsable`,`expedientes`.`caracter_abg_Act`, `expedientes`.`caracter_abg_Dem`,"
				
				+ "`parte_Act`.`nombre` AS `actor`, `parte_Act`.`dni` AS `dni_Actor`, `parte_Act`.`direccion` AS `domAct`, `parte_Act`.`localidad` AS `loc_Act`,"
				+ "`parte_Act`.`provincia` AS `provincia_Act`, `parte_Act`.`mail` AS `mail_act`, `parte_Act`.`telefono` AS `tel_Act`, `parte_Act`.`dom_digital` AS `dom_dig_Act`, "
				
				+ "`parte_Dem`.`nombre` AS `demandado`, `parte_Dem`.`dni` AS `dni_Demandado`, `parte_Dem`.`direccion` AS `domDem`, `parte_Dem`.`localidad` AS `loc_Dem`,"
				+ "`parte_Dem`.`provincia` AS `provincia_Dem`, `parte_Dem`.`mail` AS `mail_dem`, `parte_Dem`.`telefono` AS `tel_Dem`, `parte_Dem`.`dom_digital` AS `dom_dig_Dem`, "
				
				+ "`abgActor`.`apellido` AS `abgApellidoAct`, `abgActor`.`nombre` AS `abgNomAct`, `abgActor`.`casillero_dig` AS `abgAct_casillero`, "
				+ "`abgActor`.`direccion` AS `abgAct_direccion`, `abgActor`.`telefono` AS `abgAct_tel`, `abgActor`.`mail` AS `abgAct_mail`, "
				
				+ "`abgDem`.`apellido` AS `abgApellidoDem`, `abgDem`.`nombre` AS `abgNomDem`, `abgDem`.`casillero_dig` AS `abgDem_casillero`, "
				+ "`abgDem`.`direccion` AS `abgDem_direccion`, `abgDem`.`telefono` AS `abgDem_tel`, `abgDem`.`mail` AS `abgDem_mail` "

				+ "FROM `expedientes` "
				+ "LEFT JOIN `abogados` AS `abgActor` ON `expedientes`.`abg_actor` = `abgActor`.`id` "
				+ "LEFT JOIN `abogados` AS `abgDem` ON `expedientes`.`abg_dem`=`abgDem`.`id` "
				+ "LEFT JOIN `partes_demandado` AS `parte_Dem` ON `expedientes`.`demandado`=`parte_Dem`.`id` "
				+ "LEFT JOIN `partes_actora` AS `parte_Act` ON `expedientes`.`actor`=`parte_Act`.`id` "
				+ "WHERE `expedientes`.`id`="+id+";";
		con.sendQueryExecute(query);
		try {
			if(con.resul.next()) {
				actor = con.resul.getString("actor").toUpperCase();
				expte = con.resul.getString("expediente");
				demandado = con.resul.getString("demandado").toUpperCase();
				estado = con.resul.getString("estado");
				caracter_abg_Actor = con.resul.getString("caracter_abg_Act");
				caracter_abg_Dem = con.resul.getString("caracter_abg_Dem");
				abg_actor = con.resul.getString("abgApellidoAct").toUpperCase() +" " + con.resul.getString("abgNomAct").toUpperCase();
				abg_dem = con.resul.getString("abgApellidoDem").toUpperCase()+" "+con.resul.getString("abgNomDem").toUpperCase();
				casillero_Actor = con.resul.getString("dom_dig_Act");
				casillero_Dem = con.resul.getString("dom_dig_Dem");
				localidad_Act = con.resul.getString("loc_Act");
				localidad_Dem = con.resul.getString("loc_Dem");
				dom_actor = con.resul.getString("domAct")+ " - "+ localidad_Act;
				dom_dem = con.resul.getString("domDem")+ " - "+ localidad_Dem;
				tipo = con.resul.getString("tipo_proceso");
				caratula_completa = actor + " C/ " + demandado + " S/ " + tipo ;	
				fechaInicio = con.resul.getString("fechaInicio");
				unidad = con.resul.getString("unidad");
				area = con.resul.getString("area");
				responsable = con.resul.getString("responsable");
				dni_actor = con.resul.getString("dni_Actor");
				dni_dem = con.resul.getString("dni_Demandado");
				mail_dem = con.resul.getString("mail_dem");
				mail_actor = con.resul.getString("mail_act");
				telefono_act = con.resul.getString("tel_Act");
				telefono_dem = con.resul.getString("tel_Dem");
				tel_abg_actor = con.resul.getString("abgAct_tel");
				tel_abg_dem = con.resul.getString("abgDem_tel");
				unidad = con.resul.getString("unidad");
				mail_abg_act = con.resul.getString("abgAct_mail");
				mail_abg_dem = con.resul.getString("abgDem_mail");
				dom_abg_act = con.resul.getString("abgAct_direccion");
				dom_abg_dem = con.resul.getString("abgDem_direccion");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
