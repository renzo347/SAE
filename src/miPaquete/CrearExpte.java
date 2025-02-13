package miPaquete;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.Window;

public class CrearExpte extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tf_dni_actor;
	private JTextField tf_dni_Dem;
	private ConexionBd con;
	private String datos_actor, datos_demandado;
	private JLabel lblDatos_actor;
	private JLabel lblDatos_demandado;
	private JButton btnModificarActor;
	private JButton btnModificar_Dem;
	private int id_Actor=-1, id_Demandado=-1;

	public CrearExpte(ConexionBd con) {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				tf_dni_actor.setText("");
				tf_dni_Dem.setText("");
				Window source = e.getOppositeWindow();
				if(source instanceof JDialog) {
					if(((JDialog)source).getTitle().equals("MODIFICAR PARTE")) {
						buscar_actor(id_Actor);
						buscar_demandado(id_Demandado);					
					}
				}
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		
		setTitle("CREAR EXPEDIENTE");
		this.con = con;
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 979, 663);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		setLocationRelativeTo(null); // para centrar el JDialog
		
		{
			JPanel panel_actor = new JPanel();
			panel_actor.setBorder(new TitledBorder(null, "ACTOR", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			contentPanel.add(panel_actor);
			panel_actor.setLayout(null);
			
			JLabel lbldni_actor = new JLabel("DNI/CUIT/CUIL:");
			lbldni_actor.setFont(new Font("Arial", Font.BOLD, 14));
			lbldni_actor.setBounds(10, 27, 106, 20);
			panel_actor.add(lbldni_actor);
			
			tf_dni_actor = new JTextField();
			tf_dni_actor.setFont(new Font("Arial", Font.BOLD, 14));
			tf_dni_actor.setBounds(114, 22, 150, 25);
			panel_actor.add(tf_dni_actor);
			tf_dni_actor.setColumns(10);
			
			JButton btnNewButton = new JButton("BUSCAR");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscar_actor(tf_dni_actor.getText());
				}
			});
			btnNewButton.setBounds(274, 24, 83, 23);
			panel_actor.add(btnNewButton);
			
			JButton btnAgregarActor = new JButton("AGREGAR");
			btnAgregarActor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AgregarParte dialog = new AgregarParte(con,"Actor");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnAgregarActor.setBounds(369, 24, 89, 23);
			panel_actor.add(btnAgregarActor);
			
			lblDatos_actor = new JLabel(datos_actor);
			lblDatos_actor.setBorder(new LineBorder(new Color(128, 128, 128)));
			lblDatos_actor.setFont(new Font("Arial", Font.PLAIN, 14));
			lblDatos_actor.setBounds(10, 72, 825, 72);
			panel_actor.add(lblDatos_actor);
			
			btnModificarActor = new JButton("MODIFICAR");
			btnModificarActor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Modificar_Parte dialog = new Modificar_Parte(id_Actor, con, "Actor");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);					
				}
			});
			btnModificarActor.setEnabled(false);
			btnModificarActor.setBounds(837, 96, 106, 23);
			panel_actor.add(btnModificarActor);
		}
		{
			JPanel panel_demandado = new JPanel();
			panel_demandado.setBorder(new TitledBorder(null, "DEMANDADO", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			contentPanel.add(panel_demandado);
			panel_demandado.setLayout(null);
			
			JLabel lblDniDem = new JLabel("DNI/CUIT/CUIL:");
			lblDniDem.setFont(new Font("Arial", Font.BOLD, 14));
			lblDniDem.setBounds(10, 27, 106, 20);
			panel_demandado.add(lblDniDem);
			
			tf_dni_Dem = new JTextField();
			tf_dni_Dem.setFont(new Font("Arial", Font.BOLD, 14));
			tf_dni_Dem.setColumns(10);
			tf_dni_Dem.setBounds(114, 22, 150, 25);
			panel_demandado.add(tf_dni_Dem);
			
			JButton btnNewButton = new JButton("BUSCAR");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscar_demandado(tf_dni_Dem.getText());
				}
			});
			btnNewButton.setBounds(274, 24, 83, 23);
			panel_demandado.add(btnNewButton);
			
			JButton btnAgregarDem = new JButton("AGREGAR");
			btnAgregarDem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AgregarParte dialog = new AgregarParte(con,"Demandado");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnAgregarDem.setBounds(368, 24, 89, 23);
			panel_demandado.add(btnAgregarDem);
			
			lblDatos_demandado = new JLabel((String) null);
			lblDatos_demandado.setBorder(new LineBorder(new Color(128, 128, 128)));
			lblDatos_demandado.setFont(new Font("Arial", Font.PLAIN, 14));
			lblDatos_demandado.setBounds(10, 72, 825, 72);
			panel_demandado.add(lblDatos_demandado);
			
			btnModificar_Dem = new JButton("MODIFICAR");
			btnModificar_Dem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Modificar_Parte dialog = new Modificar_Parte(id_Demandado, con, "Demandado");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnModificar_Dem.setEnabled(false);
			btnModificar_Dem.setBounds(837, 96, 106, 23);
			panel_demandado.add(btnModificar_Dem);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void buscar_actor(String dni) {
		if(!dni.isEmpty()) {
			String query = "SELECT * FROM `partes_actora` WHERE `partes_actora`.`dni` = '" + dni + "';";
			con.sendQueryExecute(query);
			
			try {
				if(con.resul.next()) {
					id_Actor = con.resul.getInt("id");
					datos_actor = "<html> Nombre: <b>" + con.resul.getString("nombre") + "</b>, DNI: <b>" + con.resul.getString("dni") + "</b> <br>DIRECCIÓN: <b>" + con.resul.getString("direccion") + ", "
					+ con.resul.getString("localidad") + ", " + con.resul.getString("provincia") + "</b><br>"
					+ "MAIL: <b>" + con.resul.getString("mail") + "</b>, TELEFONO: <b>" + con.resul.getString("telefono") +"</b>, DOMICILIO DIGITAL: <b>" + con.resul.getString("dom_digital") +"</b></html>";
					lblDatos_actor.setText(datos_actor);
					btnModificarActor.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(null, "No se encontró Parte Actora");
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			//System.out.println(datos_actor);
			}else {
			return;
		}
	}
	
	protected void buscar_actor(int id) {
		if(id != -1) {
			String query = "SELECT * FROM `partes_actora` WHERE `partes_actora`.`id` = '" + id +"';";
			con.sendQueryExecute(query);
			try {
				if(con.resul.next()) {
					datos_actor = "<html> Nombre: <b>" + con.resul.getString("nombre") + "</b>, DNI: <b>" + con.resul.getString("dni") + "</b> <br>DIRECCIÓN: <b>" + con.resul.getString("direccion") + ", "
					+ con.resul.getString("localidad") + ", " + con.resul.getString("provincia") + "</b><br>"
					+ "MAIL: <b>" + con.resul.getString("mail") + "</b>, TELEFONO: <b>" + con.resul.getString("telefono") +"</b>, DOMICILIO DIGITAL: <b>" + con.resul.getString("dom_digital") +"</b></html>";
					lblDatos_actor.setText(datos_actor);
					btnModificarActor.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(null, "no se encontro parte por id");
				}
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
	protected void buscar_demandado(String dni) {
		if(!dni.isEmpty()) {
			String query = "SELECT * FROM `partes_demandado` WHERE `partes_demandado`.`dni` = '" + dni + "';";
			con.sendQueryExecute(query);
			
			try {
				if(con.resul.next()) {
					id_Demandado = con.resul.getInt("id");
					datos_demandado = "<html> Nombre: <b>" + con.resul.getString("nombre") + "</b>, DNI: <b>" + con.resul.getString("dni") + "</b> <br>DIRECCIÓN: <b>" + con.resul.getString("direccion") + ", "
							+ con.resul.getString("localidad") + ", " + con.resul.getString("provincia") + "</b><br>"
							+ "MAIL: <b>" + con.resul.getString("mail") + "</b>, TELEFONO: <b>" + con.resul.getString("telefono") +"</b>, DOMICILIO DIGITAL: <b>" + con.resul.getString("dom_digital") +"</b></html>";
					lblDatos_demandado.setText(datos_demandado);
					btnModificar_Dem.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(null, "No se encontró Parte Demandado");
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(datos_actor);
			}else {
			return;
		}
	}
	
	protected void buscar_demandado(int id) {
		if(id != -1) {
			String query = "SELECT * FROM `partes_demandado` WHERE `partes_demandado`.`id` = '" + id +"';";
			con.sendQueryExecute(query);
				try {
					if(con.resul.next()) {
						datos_demandado = "<html> Nombre: <b>" + con.resul.getString("nombre") + "</b>, DNI: <b>" + con.resul.getString("dni") + "</b> <br>DIRECCIÓN: <b>" + con.resul.getString("direccion") + ", "
						+ con.resul.getString("localidad") + ", " + con.resul.getString("provincia") + "</b><br>"
						+ "MAIL: <b>" + con.resul.getString("mail") + "</b>, TELEFONO: <b>" + con.resul.getString("telefono") +"</b>, DOMICILIO DIGITAL: <b>" + con.resul.getString("dom_digital") +"</b></html>";
						lblDatos_demandado.setText(datos_demandado);
						btnModificarActor.setEnabled(true);
					}else {
							JOptionPane.showMessageDialog(null, "no se encontro parte por id");
					}
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
		}
		
	}
}
