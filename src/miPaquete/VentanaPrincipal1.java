package miPaquete;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;

public class VentanaPrincipal1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldActor;
	private JTextField textFieldDem;
	private JTextField textFieldExpte;
	private ConexionBd conexion;
	private JTable table;
	private ModeloTablaBuscar model;
	
	public VentanaPrincipal1(ConexionBd con) {
		setTitle("USUARIO: " + con.getUser().toUpperCase());
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
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(VentanaPrincipal1.this, "Salir del sistema","¿Está seguro que desea salir?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(respuesta == JOptionPane.YES_OPTION) {
					registrarDesLogeo(conexion);
					conexion.cerrarConexin();
					System.exit(0);
				}	
			}
		});
		
		
		conexion = con;
		
		setMinimumSize(new Dimension(800, 400));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 995, 731);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setBorder(new LineBorder(new Color(192, 192, 192)));
		mnArchivo.setFont(new Font("Arial", Font.PLAIN, 14));
		menuBar.add(mnArchivo);
		
		JMenu mnConfiguración = new JMenu("Configuración");
		mnConfiguración.setFont(new Font("Arial", Font.BOLD, 14));
		mnArchivo.add(mnConfiguración);
		
		JMenuItem mntmTramites = new JMenuItem("Tramites");
		mntmTramites.setFont(new Font("Arial", Font.BOLD, 14));
		mnConfiguración.add(mntmTramites);
		
		JMenuItem mntmModelos = new JMenuItem("Modelos");
		mntmModelos.setFont(new Font("Arial", Font.BOLD, 14));
		mnConfiguración.add(mntmModelos);
		
		JMenuItem mntmEntidades = new JMenuItem("Entidades");
		mntmEntidades.setFont(new Font("Arial", Font.BOLD, 14));
		mnConfiguración.add(mntmEntidades);
		
		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.setFont(new Font("Arial", Font.BOLD, 14));
		mnConfiguración.add(mntmUsuarios);
		
		JMenuItem mntmCambClave = new JMenuItem("Clave");
		mntmCambClave.setFont(new Font("Arial", Font.BOLD, 14));
		mnConfiguración.add(mntmCambClave);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarDesLogeo(conexion);
				conexion.cerrarConexin();
				System.exit(0);
			}
		});
		
		JMenuItem mnitem_nuevoExp = new JMenuItem("Crear Expte");
		mnitem_nuevoExp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearExpte dialog = new CrearExpte();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
		mnitem_nuevoExp.setFont(new Font("Arial", Font.BOLD, 14));
		mnArchivo.add(mnitem_nuevoExp);
		mntmSalir.setFont(new Font("Arial", Font.BOLD, 14));
		mnArchivo.add(mntmSalir);
		
		JMenu mnHerramientas = new JMenu("Herramientas");
		mnHerramientas.setBorder(new LineBorder(new Color(192, 192, 192)));
		mnHerramientas.setFont(new Font("Arial", Font.PLAIN, 14));
		menuBar.add(mnHerramientas);
		
		JMenu mnListados = new JMenu("Listados");
		mnListados.setFont(new Font("Arial", Font.BOLD, 14));
		mnHerramientas.add(mnListados);
		
		JMenuItem mntmCombinados = new JMenuItem("Combinados");
		mntmCombinados.setFont(new Font("Arial", Font.BOLD, 14));
		mnListados.add(mntmCombinados);
		
		JMenuItem mntmAuditoria = new JMenuItem("Auditoria");
		mntmAuditoria.setFont(new Font("Arial", Font.BOLD, 14));
		mnListados.add(mntmAuditoria);
		
		JMenuItem mntmActAutoma = new JMenuItem("Act. Automáticas");
		mntmActAutoma.setFont(new Font("Arial", Font.BOLD, 14));
		mnHerramientas.add(mntmActAutoma);
		
		JMenuItem mntmGestion = new JMenuItem("Gestión");
		mntmGestion.setFont(new Font("Arial", Font.BOLD, 14));
		mnHerramientas.add(mntmGestion);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel tabBuscar = new JPanel();
		tabBuscar.setBorder(null);
		tabBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		tabbedPane.addTab("Buscar", null, tabBuscar, null);
		tabBuscar.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBuscar = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBuscar.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelBuscar.setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
		panelBuscar.setPreferredSize(new Dimension(10, 40));
		panelBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		tabBuscar.add(panelBuscar, BorderLayout.NORTH);
		
		JLabel lblActor = new JLabel("Actor:");
		lblActor.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(lblActor);
		
		textFieldActor = new JTextField();
		textFieldActor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarTabla();
				buscar(textFieldActor.getText(),textFieldDem.getText(),textFieldExpte.getText());
			}
		});
		textFieldActor.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(textFieldActor);
		textFieldActor.setColumns(15);
		
		JLabel lblDemandado = new JLabel("  Demandado:");
		lblDemandado.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(lblDemandado);
		
		textFieldDem = new JTextField();
		textFieldDem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarTabla();
				buscar(textFieldActor.getText(),textFieldDem.getText(),textFieldExpte.getText());
			}
		});
		textFieldDem.setFont(new Font("Arial", Font.BOLD, 12));
		textFieldDem.setColumns(15);
		panelBuscar.add(textFieldDem);
		
		JLabel lblExpte = new JLabel("  Expte:");
		lblExpte.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(lblExpte);
		
		textFieldExpte = new JTextField();
		textFieldExpte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarTabla();
				buscar(textFieldActor.getText(),textFieldDem.getText(),textFieldExpte.getText());
			}
		});
		textFieldExpte.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(textFieldExpte);
		textFieldExpte.setColumns(6);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  
				borrarTabla();
				buscar(textFieldActor.getText(),textFieldDem.getText(),textFieldExpte.getText());
			}
		});
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(btnBuscar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarTabla();
				textFieldExpte.setText("");
				textFieldDem.setText("");
				textFieldActor.setText("");
			}
		});
		btnBorrar.setFont(new Font("Arial", Font.BOLD, 12));
		panelBuscar.add(btnBorrar);
		
		JPanel panelLisBusqueda = new JPanel();
		panelLisBusqueda.setBorder(null);
		tabBuscar.add(panelLisBusqueda, BorderLayout.CENTER);
		panelLisBusqueda.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelLisBusqueda.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				abrirExpte(e);
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirExpte(e);
			}
		});
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		model = new ModeloTablaBuscar(new String[] {"ID","EXPTE","ACTOR","DEMANDADO","PROCESO"},0);
		table.setModel(model);
		TableColumn column = table.getColumnModel().getColumn(0);
		table.getColumnModel().removeColumn(column);
		table.getColumnModel().getColumn(0).setMaxWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);		
		JTableHeader encabezado = table.getTableHeader();
		TableColumnModel columnModel = encabezado.getColumnModel();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) encabezado.getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column1 = columnModel.getColumn(i);
            column1.setHeaderRenderer(renderer);
        }
		
		scrollPane.setViewportView(table);
		
		JPanel tabTablero = new JPanel();
		tabTablero.setFont(new Font("Arial", Font.BOLD, 12));
		tabbedPane.addTab("Tablero", null, tabTablero, null);
		tabTablero.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFiltroTablero = new JPanel();        //****************************************** Panel filtro tablero
		FlowLayout flowLayout_1 = (FlowLayout) panelFiltroTablero.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelFiltroTablero.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panelFiltroTablero.setPreferredSize(new Dimension(10, 40));
		panelFiltroTablero.setFont(new Font("Arial", Font.BOLD, 12));
		
		tabTablero.add(panelFiltroTablero, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		panelFiltroTablero.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Renzo Roco", "Florencia Bermudez", "Fredy Bermudez", "Julieta Roco"}));
		comboBox.setFont(new Font("Arial", Font.BOLD, 12));
		panelFiltroTablero.add(comboBox);
		
		JPanel panelTablero = new JPanel();
		panelTablero.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		tabTablero.add(panelTablero, BorderLayout.CENTER);
	}
	
	private void buscar(String actor, String demandado, String expt) {
		String query="";
		
		if(actor.isEmpty() && demandado.isEmpty() && expt.isEmpty()) {
			return;
		}
		
		if(actor.isEmpty() && demandado.isEmpty() && !expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `expedientes`.`expediente` LIKE '%" + expt + "%';";
		}
		
		if(actor.isEmpty() && !demandado.isEmpty() && expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `partes_demandado`.`nombre` LIKE '%"+ demandado +"%';";
		}
		
		if(actor.isEmpty() && !demandado.isEmpty() && !expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `partes_demandado`.`nombre` LIKE '%"+ demandado +"%' AND `expedientes`.`expediente` LIKE '%" + expt + "%';";
		}
		
		if(!actor.isEmpty() && demandado.isEmpty() && expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `partes_actora`.`nombre` LIKE '%"+ actor +"%';";
		}
		
		if(!actor.isEmpty() && demandado.isEmpty() && !expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `partes_actora`.`nombre` LIKE '%"+ actor +"%' AND `expedientes`.`expediente` LIKE '%" + expt + "%';";
		}
		
		if(!actor.isEmpty() && !demandado.isEmpty() && expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `partes_demandado`.`nombre` LIKE '%"+ demandado +"%' AND `partes_actora`.`nombre` LIKE '%" + actor + "%';";
		}

		if(!actor.isEmpty() && !demandado.isEmpty() && !expt.isEmpty()) {
			query = "SELECT `expedientes`.`id`,`expedientes`.`expediente`,`expedientes`.`tipo_proceso`,`partes_demandado`.`nombre` AS `demandado` ,`partes_actora`.`nombre` AS `actor` FROM `expedientes` \r\n"
					+ "	LEFT JOIN `partes_demandado` ON `expedientes`.`demandado`=`partes_demandado`.`id` \r\n"
					+ "    LEFT JOIN `partes_actora` ON `expedientes`.`actor`=`partes_actora`.`id` \r\n"
					+ "    WHERE `partes_demandado`.`nombre` LIKE '%"+ demandado +"%' AND `expedientes`.`expediente` LIKE '%" + expt + "%' AND `partes_actora`.`nombre` LIKE '%"+ actor +"%';";
		}
		
		conexion.sendQueryExecute(query);

		try {
			while(conexion.resul.next()) {
				String expte = conexion.resul.getString("expediente");
				String actorr = conexion.resul.getString("actor").toUpperCase();
				String demandadoo = conexion.resul.getString("demandado");
				int id = conexion.resul.getInt("id");
				String proceso = conexion.resul.getString("tipo_proceso");
				model.addRow(new Object[] {id,expte,actorr,demandadoo,proceso});
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	private void registrarDesLogeo(ConexionBd con) {
		LocalDateTime fechaHora= LocalDateTime.now();
		FormateadorHora formateador = new FormateadorHora(fechaHora);
		String query = String.format("INSERT INTO `logeos` (`id`, `usuarioLogeado`, `hora`, `tipo`) VALUES (NULL, '%s', '%s', 'out')", con.getUser() ,formateador.getFormatoSQL());
		con.sendQuery(query);
	}
	
	private void borrarTabla() {
		model.setRowCount(0);
	}
	
	private void abrirExpte(MouseEvent e) {
		if(e.getClickCount()==2) {
			int filaSeleccionada = table.getSelectedRow();
			if(filaSeleccionada != -1) {
			int id = (int) model.getValueAt(filaSeleccionada, 0);	
			VentanaExpediente ve = new VentanaExpediente(id,conexion);
			ve.setExtendedState(MAXIMIZED_BOTH);
			ve.setVisible(true);
			}
		}
	}
	
	private void abrirExpte(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			int filaSeleccionada = table.getSelectedRow();
			if(filaSeleccionada != -1) {
				int id = (int) model.getValueAt(filaSeleccionada, 0);
				VentanaExpediente ve = new VentanaExpediente(id,conexion);
				ve.setExtendedState(MAXIMIZED_BOTH);
				ve.setVisible(true);
			}
		}
	}
	
}
