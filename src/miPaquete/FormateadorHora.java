package miPaquete;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormateadorHora {
	LocalDateTime tiempo;
	public FormateadorHora(LocalDateTime h) {
		tiempo = h;
	}
	
	public String getFormatoSQL() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDate = tiempo.format(formatter);
		return formattedDate;
	}
	
	public String getFormatSoloFechaSQL() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = tiempo.format(formatter);
		return formattedDate;
	}
	
	public String getFormatSoloFechaEstandar() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = tiempo.format(formatter);
		return formattedDate;
	}
}
