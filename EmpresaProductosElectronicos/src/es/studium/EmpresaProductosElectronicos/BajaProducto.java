package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BajaProducto implements WindowListener, ActionListener {
	// Componentes
	Frame ventana = new Frame("Baja de Producto");
	Label lblCabecera = new Label("Elegir el Producto a Borrar");
	Choice choProductos = new Choice();
	Button btnBorrar = new Button("Borrar");

	Dialog dlgConfirmacion = new Dialog(ventana, "Confirmación", true);
	Label lblConfirmacion = new Label("XXXXXXXXXXXXXXXXXXX");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");

	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;

	// Constructor
	public BajaProducto()
		{
			// Listener
			ventana.addWindowListener(this);
			btnBorrar.addActionListener(this);
			
			// Pantalla
			ventana.setSize(450, 140);
			ventana.setResizable(false);

			ventana.setLayout(new FlowLayout());
			ventana.setBackground(new Color(230, 206, 245));
			ventana.add(lblCabecera);
			// Rellenar Choice
			choProductos.add("Elegir Producto...");
			// Conectar
			bd.conectar();
			// Sacar los datos de la tabla Productos
			rs=bd.selectProducto();
			// Registro a registro, meterlos en el Choice
			try
			{
				while(rs.next())
				{
					choProductos.add(rs.getInt("idProducto") + "-" + rs.getString("codigoProducto") + " "
							+ rs.getInt("IVAProducto") + " " + rs.getString("descripcionProducto") + "-"
							+ rs.getInt("cantidadAlmacenProducto") + "-" + rs.getInt("descuentoProducto") + "-"
							+ rs.getDouble("precioVentaProducto") + "-" + rs.getDouble("precioCompraProducto") + "-"
							+ rs.getString("medidasProducto"));
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			// Desconectar
			bd.desconectar();
			
			ventana.add(choProductos);
			ventana.add(btnBorrar);
			
			ventana.setLocationRelativeTo(null); //	fijar que la ventana salga siempre en el medio
			ventana.setVisible(true); // Mostrarla
		}

	public void windowActivated(WindowEvent we) {
	}

	public void windowClosed(WindowEvent we) {
	}

	public void windowClosing(WindowEvent we) {
		if (dlgMensaje.isActive()) {
			dlgMensaje.setVisible(false);
		}
		if (dlgConfirmacion.isActive()) {
			dlgConfirmacion.setVisible(false);
		} else {
			ventana.setVisible(false);
		}
	}

	public void windowDeactivated(WindowEvent we) {
	}

	public void windowDeiconified(WindowEvent we) {
	}

	public void windowIconified(WindowEvent we) {
	}

	public void windowOpened(WindowEvent we) {
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource().equals(btnBorrar)) {
			// Mostrar el diálogo de confirmación
			// Listener
			dlgConfirmacion.addWindowListener(this);
			btnSi.addActionListener(this);
			btnNo.addActionListener(this);

			// Pantalla
			dlgConfirmacion.setSize(460, 105);
			dlgConfirmacion.setResizable(false);

			dlgConfirmacion.setLayout(new FlowLayout());
			dlgConfirmacion.setBackground(new Color(230, 206, 245));
			lblConfirmacion.setText("¿Seguro de Borrar el producto " + choProductos.getSelectedItem() + "?");
			dlgConfirmacion.add(lblConfirmacion);
			dlgConfirmacion.add(btnSi);
			dlgConfirmacion.add(btnNo);

			dlgConfirmacion.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
			dlgConfirmacion.setVisible(true); // Mostrarla
		} else if (evento.getSource().equals(btnNo)) {
			dlgConfirmacion.setVisible(false);
		} else if (evento.getSource().equals(btnSi)) {
			// Conectar
			bd.conectar();
			// Hacer el DELETE
			// Se crea un array que contiene todos los valores indicados en el choice,
			// separados por -
			String[] array = choProductos.getSelectedItem().split("-");

			// Selecionamos el id de producto, valor contenido en el array[0]
			int resultado = bd.borrarCliente(Integer.parseInt(array[0]));

			if (resultado == 0) {
				lblMensaje.setText("Borrado con éxito");
				
				String usuario;
				usuario=Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[DELETE FROM productos]");
			} 
			else 
			{
				lblMensaje.setText("Error en Borrado");
			}
			// Desconectar
			bd.desconectar();

			dlgMensaje.addWindowListener(this);

			// Pantalla
			dlgMensaje.setBackground(new Color(230, 206, 245));
			dlgMensaje.setSize(180, 100);
			dlgMensaje.setResizable(false);

			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.add(lblMensaje);

			dlgMensaje.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
			dlgMensaje.setVisible(true);

			dlgConfirmacion.setVisible(false);
		}
	}
}
