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
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearExpte extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tf_dni_actor;
	private JTextField tf_dni_Dem;

	/**
	 * Launch the application.
	*/
	public static void main(String[] args) {
		try {
			CrearExpte dialog = new CrearExpte();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CrearExpte() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 861, 663);
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
					buscar(tf_dni_actor.getText());
				}
			});
			btnNewButton.setBounds(274, 24, 83, 23);
			panel_actor.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("AGREGAR");
			btnNewButton_1.setBounds(369, 24, 89, 23);
			panel_actor.add(btnNewButton_1);
			
			JLabel lblNewLabel = new JLabel("Nombre:");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			lblNewLabel.setBounds(10, 72, 803, 20);
			panel_actor.add(lblNewLabel);
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
			btnNewButton.setBounds(274, 24, 83, 23);
			panel_demandado.add(btnNewButton);
			
			JButton btnNewButton_2 = new JButton("AGREGAR");
			btnNewButton_2.setBounds(368, 24, 89, 23);
			panel_demandado.add(btnNewButton_2);
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void buscar(String text) {
		
	}
}
