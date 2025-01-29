package miPaquete;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AgregarParte extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tf_nombre;
	private JTextField tf_dni;
	private JTextField tf_direccion;
	private JTextField tf_localidad;
	private JTextField tf_provincia;
	private JTextField tf_mail;
	private JTextField tf_tel;
	private JTextField tf_dom_dig;

	public AgregarParte(ConexionBd con, String parte) {
		setModal(true);
		setResizable(false);
		setTitle("AGREGAR PARTE");
		setBounds(100, 100, 622, 342);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null); // para centrar el JDialog
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NOMBRE: ");
		lblNewLabel.setBounds(10, 21, 63, 21);
		contentPanel.add(lblNewLabel);
		
		tf_nombre = new JTextField();
		tf_nombre.setFont(new Font("Arial", Font.BOLD, 14));
		tf_nombre.setBounds(76, 18, 335, 25);
		contentPanel.add(tf_nombre);
		tf_nombre.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI: ");
		lblDni.setBounds(421, 21, 30, 21);
		contentPanel.add(lblDni);
		
		tf_dni = new JTextField();
		tf_dni.setFont(new Font("Arial", Font.BOLD, 14));
		tf_dni.setBounds(451, 18, 138, 25);
		contentPanel.add(tf_dni);
		tf_dni.setColumns(10);
		
		JLabel lblDireccion = new JLabel("DIRECCION: ");
		lblDireccion.setBounds(10, 59, 77, 21);
		contentPanel.add(lblDireccion);
		
		tf_direccion = new JTextField();
		tf_direccion.setFont(new Font("Arial", Font.BOLD, 14));
		tf_direccion.setBounds(85, 54, 290, 25);
		contentPanel.add(tf_direccion);
		tf_direccion.setColumns(10);
		
		JLabel lblLocalidad = new JLabel("LOCALIDAD: ");
		lblLocalidad.setBounds(10, 101, 77, 21);
		contentPanel.add(lblLocalidad);
		
		tf_localidad = new JTextField();
		tf_localidad.setFont(new Font("Arial", Font.BOLD, 14));
		tf_localidad.setColumns(10);
		tf_localidad.setBounds(85, 96, 290, 25);
		contentPanel.add(tf_localidad);
		
		JLabel lblLocalidad_1 = new JLabel("PROVINCIA: ");
		lblLocalidad_1.setBounds(10, 145, 71, 21);
		contentPanel.add(lblLocalidad_1);
		
		tf_provincia = new JTextField();
		tf_provincia.setFont(new Font("Arial", Font.BOLD, 14));
		tf_provincia.setColumns(10);
		tf_provincia.setBounds(85, 140, 290, 25);
		contentPanel.add(tf_provincia);
		
		JLabel lblLocalidad_1_1 = new JLabel("MAIL: ");
		lblLocalidad_1_1.setBounds(10, 190, 38, 21);
		contentPanel.add(lblLocalidad_1_1);
		
		tf_mail = new JTextField();
		tf_mail.setFont(new Font("Arial", Font.BOLD, 14));
		tf_mail.setColumns(10);
		tf_mail.setBounds(50, 185, 315, 25);
		contentPanel.add(tf_mail);
		
		JLabel lblDni_1 = new JLabel("TEL:");
		lblDni_1.setBounds(387, 190, 30, 21);
		contentPanel.add(lblDni_1);
		
		tf_tel = new JTextField();
		tf_tel.setFont(new Font("Arial", Font.BOLD, 14));
		tf_tel.setColumns(10);
		tf_tel.setBounds(417, 185, 178, 25);
		contentPanel.add(tf_tel);
		
		JLabel lblLocalidad_1_1_1 = new JLabel("DOM. DIGITAL: ");
		lblLocalidad_1_1_1.setBounds(10, 232, 87, 21);
		contentPanel.add(lblLocalidad_1_1_1);
		
		tf_dom_dig = new JTextField();
		tf_dom_dig.setFont(new Font("Arial", Font.BOLD, 14));
		tf_dom_dig.setColumns(10);
		tf_dom_dig.setBounds(95, 228, 193, 25);
		contentPanel.add(tf_dom_dig);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						crearParte(con, parte);
						dispose();
					}
				});
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
	
	private void crearParte(ConexionBd con, String parte) {
		String query;
		if(parte.equals("Actor")){
			query = "INSERT INTO `partes_actora` (`id`, `nombre`, `dni`, `direccion`, `localidad`, `provincia`, `mail`, `telefono`, `dom_digital`)"
					+ "VALUES (NULL, '" + tf_nombre.getText() + "', '" + tf_dni.getText() + "', '" + tf_direccion.getText() +"',"
					+ " '" + tf_localidad.getText() + "', '" + tf_provincia.getText() + "', '" + tf_mail.getText() + "', '"
					+ tf_tel.getText() + "', '" + tf_dom_dig.getText() +"');";
		}else {
			query = "INSERT INTO `partes_demandado` (`id`, `nombre`, `dni`, `direccion`, `localidad`, `provincia`, `mail`, `telefono`, `dom_digital`)"
					+ "VALUES (NULL, '" + tf_nombre.getText() + "', '" + tf_dni.getText() + "', '" + tf_direccion.getText() +"',"
					+ " '" + tf_localidad.getText() + "', '" + tf_provincia.getText() + "', '" + tf_mail.getText() + "', '"
					+ tf_tel.getText() + "', '" + tf_dom_dig.getText() +"');";
		}
		
		con.sendQuery(query);
		
	}
}
