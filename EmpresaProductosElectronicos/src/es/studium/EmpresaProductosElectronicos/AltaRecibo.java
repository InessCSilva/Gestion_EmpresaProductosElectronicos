package es.studium.EmpresaProductosElectronicos;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AltaRecibo implements WindowListener, ActionListener
{
	//	Componentes
	Frame ventana = new Frame("Alta de Recibo");
	
	Label lblFecha = new Label("Fecha Recibo:");
	Label lblSubTotal = new Label("SubTotal Recibo:");
	Label lblClienteFK = new Label("Cliente:");
	
	TextField txtFecha = new TextField(15);
	TextField txtSubTotal = new TextField(15);
	TextField txtClienteFK = new TextField(15);
	
	Choice choClientes = new Choice();
	
	Button btnAceptar = new Button("Aceptar");
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	
	// Constructor
	public AltaRecibo()
	{
		// Listener
		ventana.addWindowListener(this);
		ventana.setBackground(new Color(230, 206, 245));
		btnAceptar.addActionListener(this);
		dlgMensaje.addWindowListener(this);

		// Pantalla
		ventana.setLayout(new FlowLayout());
		ventana.setSize(800, 130);
		ventana.setResizable(false);
		
		ventana.add(lblFecha);
		ventana.add(txtFecha);
		
		ventana.add(lblSubTotal);
		ventana.add(txtSubTotal);
		
		ventana.add(lblClienteFK);
		ventana.add(choClientes);
		
		ventana.add(btnAceptar);
		
		ventana.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
		
		rellenarChoiceRecibos();
	}

	private void rellenarChoiceRecibos()
	{
		// Rellenar Choice
		choClientes.removeAll();
		choClientes.add("Elegir Cliente...");
		// Conectar
		bd.conectar();
		// Sacar los datos de la tabla Clientes
		rs = bd.selectCliente();		
		// Registro a registro, meterlos en el Choice
		try
		{
			while (rs.next())
			{
				choClientes.add(rs.getInt("idCliente") + "-" + rs.getString("nombreCliente") + " "
						+ rs.getString("primerApellidoCliente") + " " + rs.getString("segundoApellidoCliente") + "-"
						+ rs.getString("telefonoCliente") + "-" + rs.getString("correoElectronicoCliente") + "-"
						+ rs.getString("domicilioCliente") + "-" + rs.getString("poblacionCliente") + "-"
						+ rs.getString("provinciaCliente") + "-" + rs.getString("codigoPostalCliente"));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		// Desconectar
		bd.desconectar();
	}
	
	@Override
	public void windowActivated(WindowEvent we) {}
	
	@Override
	public void windowClosed(WindowEvent we) {}
	
	@Override
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		else
		{
			ventana.setVisible(false);
		}
	}
	
	@Override
	public void windowDeactivated(WindowEvent we) {}
	
	@Override
	public void windowDeiconified(WindowEvent we) {}
	
	@Override
	public void windowIconified(WindowEvent we) {}
	
	@Override
	public void windowOpened(WindowEvent we) {}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		
		String[] seleccionado = choClientes.getSelectedItem().split("-");
		
		// Conectar
		bd.conectar();
		
		// Coger los datos de la BD
		String fecha = txtFecha.getText();
		String subTotal = txtSubTotal.getText();
		String clienteFK = seleccionado[0];
		
		// Hacer el INSERT
		int resultado = bd.insertarRecibo(fecha, subTotal, clienteFK);
		if (resultado ==0)
		{
			lblMensaje.setText("Alta Correcta");
			
			String usuario;
			usuario=Login.txtUsuario.getText();
			bd.registroEscritura("["+usuario+"]"+"[INSERT INTO recibos]");
		}
		else
		{
			lblMensaje.setText("Error en Alta");
		}
		//	Desconectar
		bd.desconectar();
		dlgMensaje.setBackground(new Color(230, 206, 245));
		dlgMensaje.setSize(180, 100);
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setResizable(false);
		dlgMensaje.add(lblMensaje);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.setVisible(true);		
	}
}
	
