package miPaquete;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Logeo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPassw;
	private ConexionBd conectar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Logeo frame = new Logeo();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Logeo() {
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

		setTitle("Ingreso al sistema");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 301);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblusuario = new JLabel("USUARIO");
		lblusuario.setFont(new Font("Consolas", Font.BOLD, 20));
		lblusuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblusuario.setBounds(177, 21, 147, 24);
		contentPane.add(lblusuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBackground(new Color(235, 238, 237));
		txtUsuario.setFont(new Font("Consolas", Font.BOLD, 24));
		txtUsuario.setBounds(55, 41, 427, 39);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblcontra = new JLabel("CONTRASEÑA");
		lblcontra.setHorizontalAlignment(SwingConstants.CENTER);
		lblcontra.setFont(new Font("Consolas", Font.BOLD, 20));
		lblcontra.setBounds(177, 121, 147, 24);
		contentPane.add(lblcontra);
		
		txtPassw = new JPasswordField();
		txtPassw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(logear()) {
					VentanaPrincipal1 vp = new VentanaPrincipal1(conectar);
					vp.setExtendedState(MAXIMIZED_BOTH);
					vp.setVisible(true);
					dispose();
				};
			}
		});
		txtPassw.setBackground(new Color(235, 238, 237));
		txtPassw.setFont(new Font("Consolas", Font.BOLD, 24));
		txtPassw.setBounds(55, 142, 427, 41);
		txtPassw.setEchoChar('*');
		contentPane.add(txtPassw);
		
		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(logear()) {
					VentanaPrincipal1 vp = new VentanaPrincipal1(conectar);
					vp.setExtendedState(MAXIMIZED_BOTH);
					vp.setVisible(true);
					dispose();
				};				
			}
		});
		
		btnEntrar.setBorder(null);
		btnEntrar.setMnemonic(KeyEvent.VK_NUMPAD1);
		btnEntrar.setBackground(new Color(128, 128, 128));
		btnEntrar.setFont(new Font("Consolas", Font.BOLD, 18));
		btnEntrar.setBounds(198, 199, 102, 39);
		contentPane.add(btnEntrar);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnNewToggleButton.isSelected()) {
					txtPassw.setEchoChar((char)0);
				}else {
					txtPassw.setEchoChar('*');
				}
			}
		});
		tglbtnNewToggleButton.setSelectedIcon(new ImageIcon(Logeo.class.getResource("/resources/imagenes/icons8-ojo-64.png")));
		tglbtnNewToggleButton.setIcon(new ImageIcon(Logeo.class.getResource("/resources/imagenes/ojo- tachado.png")));
		tglbtnNewToggleButton.setBounds(492, 142, 45, 41);
		contentPane.add(tglbtnNewToggleButton);
	}
	
	private boolean logear() {
		
		conectar=new ConexionBd(txtUsuario.getText(),new String(txtPassw.getPassword())); // creo conexión con bd; inicializo "conectar"
		if(conectar.keyOk) {
			Usuario usuario = new Usuario(txtUsuario.getText()); //no hace conexión con base de datos
			registrarLogeo(usuario);
			return true;	
		}
		return false;
	}
	
	private void registrarLogeo(Usuario u) {
		LocalDateTime fechaHora= LocalDateTime.now();
		FormateadorHora formateador = new FormateadorHora(fechaHora);
		String query = String.format("INSERT INTO `logeos` (`id`, `usuarioLogeado`, `hora`, `tipo`) VALUES (NULL, '%s', '%s', 'in')", u.getUser() ,formateador.getFormatoSQL());
		conectar.sendQuery(query);
	}
}
