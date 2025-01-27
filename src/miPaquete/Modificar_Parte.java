package miPaquete;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class Modificar_Parte extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			Modificar_Parte dialog = new Modificar_Parte();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Modificar_Parte(int id, ConexionBd con) {
		setModal(true);
		setResizable(false);
		setTitle("MODIFICAR PARTE");
		setBounds(100, 100, 622, 342);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null); // para centrar el JDialog
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NOMBRE: ");
		lblNewLabel.setBounds(10, 21, 63, 21);
		contentPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.BOLD, 14));
		textField.setBounds(76, 18, 335, 25);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI: ");
		lblDni.setBounds(421, 21, 30, 21);
		contentPanel.add(lblDni);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_1.setBounds(451, 18, 138, 25);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDireccion = new JLabel("DIRECCION: ");
		lblDireccion.setBounds(10, 59, 77, 21);
		contentPanel.add(lblDireccion);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_2.setBounds(85, 54, 290, 25);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblLocalidad = new JLabel("LOCALIDAD: ");
		lblLocalidad.setBounds(10, 101, 77, 21);
		contentPanel.add(lblLocalidad);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_3.setColumns(10);
		textField_3.setBounds(85, 96, 290, 25);
		contentPanel.add(textField_3);
		
		JLabel lblLocalidad_1 = new JLabel("PROVINCIA: ");
		lblLocalidad_1.setBounds(10, 145, 71, 21);
		contentPanel.add(lblLocalidad_1);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Arial", Font.BOLD, 14));
		textField_4.setColumns(10);
		textField_4.setBounds(85, 140, 290, 25);
		contentPanel.add(textField_4);
		
		JLabel lblLocalidad_1_1 = new JLabel("MAIL: ");
		lblLocalidad_1_1.setBounds(10, 190, 38, 21);
		contentPanel.add(lblLocalidad_1_1);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Arial", Font.BOLD, 14));
		textField_5.setColumns(10);
		textField_5.setBounds(50, 185, 315, 25);
		contentPanel.add(textField_5);
		
		JLabel lblDni_1 = new JLabel("TEL:");
		lblDni_1.setBounds(387, 190, 30, 21);
		contentPanel.add(lblDni_1);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Arial", Font.BOLD, 14));
		textField_6.setColumns(10);
		textField_6.setBounds(417, 185, 178, 25);
		contentPanel.add(textField_6);
		
		JLabel lblLocalidad_1_1_1 = new JLabel("DOM. DIGITAL: ");
		lblLocalidad_1_1_1.setBounds(10, 232, 87, 21);
		contentPanel.add(lblLocalidad_1_1_1);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Arial", Font.BOLD, 14));
		textField_7.setColumns(10);
		textField_7.setBounds(95, 228, 193, 25);
		contentPanel.add(textField_7);
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
