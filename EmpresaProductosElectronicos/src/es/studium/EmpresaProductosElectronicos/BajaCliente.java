package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BajaCliente implements WindowListener, ActionListener
{
	// Componentes
	Frame ventana = new Frame("Baja del Cliente");
	Label lblCabecera = new Label("Elegir el Cliente a Borrar");
	Choice choClientes = new Choice();
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
	public BajaCliente()
	{
		// Listener
		ventana.addWindowListener(this);
		btnBorrar.addActionListener(this);
		
		// Pantalla
		ventana.setSize(800, 150);
		ventana.setResizable(false);

		ventana.setLayout(new FlowLayout());
		ventana.setBackground(new Color(230, 206, 245));
		ventana.add(lblCabecera);
		// Rellenar Choice
		choClientes.add("Elegir Cliente...");
		// Conectar
		bd.conectar();
		// Sacar los datos de la tabla Clientes
		rs=bd.selectCliente();
		// Registro a registro, meterlos en el Choice
		try
		{
			while(rs.next())
			{
				choClientes.add(rs.getInt("idCliente") + "-" + rs.getString("nombreCliente") + " "
						+ rs.getString("primerApellidoCliente") + " " + rs.getString("segundoApellidoCliente") + "-"
						+ rs.getString("telefonoCliente") + "-" + rs.getString("correoElectronicoCliente") + "-"
						+ rs.getString("domicilioCliente") + "-" + rs.getString("poblacionCliente") + "-"
						+ rs.getString("provinciaCliente") + "-" + rs.getString("codigoPostalCliente"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		// Desconectar
		bd.desconectar();
		
		ventana.add(choClientes);
		ventana.add(btnBorrar);
		
		ventana.setLocationRelativeTo(null); //	fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
	}
	
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		if(dlgConfirmacion.isActive())
		{
			dlgConfirmacion.setVisible(false);
		}
		else
		{
			ventana.setVisible(false);
		}
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnBorrar))
		{
			// Mostrar el diálogo de confirmación
			// Listener
			dlgConfirmacion.addWindowListener(this);
			btnSi.addActionListener(this);
			btnNo.addActionListener(this);

			// Pantalla
			dlgConfirmacion.setSize(840, 105);
			dlgConfirmacion.setResizable(false);

			dlgConfirmacion.setLayout(new FlowLayout());
			dlgConfirmacion.setBackground(new Color(230, 206, 245));
			lblConfirmacion.setText("¿Seguro de Borrar el cliente "+
			choClientes.getSelectedItem()+"?");
			dlgConfirmacion.add(lblConfirmacion);
			dlgConfirmacion.add(btnSi);
			dlgConfirmacion.add(btnNo);

			dlgConfirmacion.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
			dlgConfirmacion.setVisible(true); // Mostrarla
		}
		else if(evento.getSource().equals(btnNo))
		{
			dlgConfirmacion.setVisible(false);
		}
		else if(evento.getSource().equals(btnSi))
		{
			// Conectar
			bd.conectar();
			// Hacer el DELETE
			// Se crea un array que contiene todos los valores indicados en el choice, separados por -
			String[] array = choClientes.getSelectedItem().split("-");
			
			// Selecionamos el id de cliente, valor contenido en el array[0]
			int resultado = bd.borrarCliente(Integer.parseInt(array[0]));
			
			if(resultado==0)
			{
				lblMensaje.setText("Borrado con éxito");
				
				String usuario;
				usuario=Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[DELETE FROM clientes]");
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
