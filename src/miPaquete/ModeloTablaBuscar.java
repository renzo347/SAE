package miPaquete;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaBuscar extends DefaultTableModel {

	public ModeloTablaBuscar() {
		// TODO Auto-generated constructor stub
	}

	public ModeloTablaBuscar(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public ModeloTablaBuscar(Vector<?> columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public ModeloTablaBuscar(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public ModeloTablaBuscar(Vector<? extends Vector> data, Vector<?> columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public ModeloTablaBuscar(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
