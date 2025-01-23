package miPaquete;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class ConexionBd {
	private final String url = "jdbc:mysql://186.182.243.188:3306/sae";
	private String user;
	private String password;
	private Connection conexion;
	private Statement stm;
	public ResultSet resul;
	public boolean keyOk=false;
	
	public ConexionBd(String u, String p) {
		user = u;
		password = p;
		try {
			conexion = DriverManager.getConnection(url, user, password);
			stm = conexion.createStatement();
			keyOk=true;
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error de credenciales");
		}
	}
	
	public void sendQuery(String query) {  // se usa para INSERT INTO... no obtengo valor, solo cargo datos bd
		try {
			stm.executeUpdate(query);
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectar con BD");
			e.printStackTrace();
		}
	}
	
	public void sendQueryExecute(String query) { // se usa con SELECT... obtengo un valor
		try {
			resul = stm.executeQuery(query);
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectar con BD");
			e.printStackTrace();
		}
	}
	
	public String getUser() {
		return user;
	}
/*	
	public PreparedStatement preparaQuery(String query) { // por ahora no se usara esta funcion, ver luego como se implemente bien
		PreparedStatement ps=null;
		try {
			ps = conexion.prepareStatement(query);
			return ps;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
		
	}
	*/
	public void cerrarConexin() {
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
