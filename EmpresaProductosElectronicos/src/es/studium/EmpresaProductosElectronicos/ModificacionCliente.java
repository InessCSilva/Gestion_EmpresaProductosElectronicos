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

public class ModificacionCliente implements WindowListener, ActionListener 
{
	// Componentes
	Frame ventana = new Frame("Modificación de Cliente");
	Label lblCabecera = new Label("Elegir el Cliente a Modificar:");
	Choice choClientes = new Choice();
	Button btnEditar = new Button("Editar");
	
	Dialog dlgEditar = new Dialog(ventana, "Edición Cliente", true);
	Label lblCabecera2 = new Label("Editando el cliente nº ");
	
	Label lblId = new Label("Nº Cliente:");
	Label lblNombre = new Label("Nombre:");
	Label lblPrimerApellido = new Label("Primer Apellido:");
	Label lblSegundoApellido = new Label("Segundo Apellido:");
	Label lblTelefono = new Label("Teléfono:");
	Label lblCorreoElectronico = new Label("Correo Electrónico:");
	Label lblDni = new Label("DNI:");
	Label lblDomicilio = new Label("Domicilio:");
	Label lblPoblacion = new Label("Población:");
	Label lblProvincia = new Label("Provincia:");
	Label lblCodigoPostal = new Label("Código Postal:");
	
	TextField txtId = new TextField(15);
	TextField txtNombre = new TextField(15);
	TextField txtPrimerApellido = new TextField(15);
	TextField txtSegundoApellido = new TextField(15);
	TextField txtTelefono = new TextField(15);
	TextField txtCorreoElectronico = new TextField(15);
	TextField txtDni = new TextField(15);
	TextField txtDomicilio = new TextField(15);
	TextField txtPoblacion = new TextField(15);
	TextField txtProvincia = new TextField(15);
	TextField txtCodigoPostal = new TextField(15);
	
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;

	// Constructor
	public ModificacionCliente()
	{
		// Listener
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnModificar.addActionListener(this);
		btnCancelar.addActionListener(this);
		dlgEditar.addWindowListener(this);
		dlgMensaje.addWindowListener(this);

		// Pantalla
		ventana.setSize(850, 140); // En pixeles Width, Height. Ir probando y cambiando anchura y altura
		ventana.setResizable(false); // No Permitir redimensionar

		ventana.setLayout(new FlowLayout());
		ventana.setBackground(new Color(230, 206, 245));
		ventana.add(lblCabecera);

		rellenarChoiceClientes();
		
		ventana.add(choClientes);
		ventana.add(btnEditar);

		ventana.setLocationRelativeTo(null); //fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
	}

	private void rellenarChoiceClientes()
	{
		choClientes.removeAll();
		// Rellenar Choice
		choClientes.add("Elegir Cliente...");
		// Conectar BD
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
						+ rs.getString("dniCliente") + "-" +  rs.getString("domicilioCliente") + "-" 
						+ rs.getString("poblacionCliente") + "-" + rs.getString("provinciaCliente") + "-" 
						+ rs.getString("codigoPostalCliente"));
			}
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Desconectar BD
		bd.desconectar();
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		else if(dlgEditar.isActive())
		{
			dlgEditar.setVisible(false);
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
		if(evento.getSource().equals(btnEditar))
		{
			if(!choClientes.getSelectedItem().equals("Elegir Cliente..."))
			{
				String[] seleccionado = choClientes.getSelectedItem().split("-");
				// Conectar BD y sacar los datos del cliente seleccionado
				bd.conectar();
				rs = bd.consultarCliente(seleccionado[0]);
				try
				{
					rs.next();
					txtNombre.setText(rs.getString("nombreCliente"));
					txtPrimerApellido.setText(rs.getString("primerApellidoCliente"));
					txtSegundoApellido.setText(rs.getString("segundoApellidoCliente"));
					txtTelefono.setText(rs.getString("telefonoCliente"));
					txtCorreoElectronico.setText(rs.getString("correoElectronicoCliente"));
					txtDni.setText(rs.getString("dniCliente"));
					txtDomicilio.setText(rs.getString("domicilioCliente"));
					txtPoblacion.setText(rs.getString("poblacionCliente"));
					txtProvincia.setText(rs.getString("provinciaCliente"));
					txtCodigoPostal.setText(rs.getString("codigoPostalCliente"));
					
				}	
				catch(SQLException sqle) {}
				bd.desconectar();
				// Muestro los datos del cliente elegido
				// en la pantalla de edición
				dlgEditar.setSize(175, 730); // En pixeles Width, Height. Ir probando y cambiando anchura y altura
				dlgEditar.setResizable(false); // No Permitir redimensionar

				dlgEditar.setLayout(new FlowLayout());
				dlgEditar.add(lblCabecera2);
				dlgEditar.add(lblId);
				txtId.setEnabled(false);
				txtId.setText(seleccionado[0]); // rs.getInt("idCliente");
				dlgEditar.add(txtId);
				
				dlgEditar.add(lblNombre);				
				dlgEditar.add(txtNombre);
				
				dlgEditar.add(lblPrimerApellido);				
				dlgEditar.add(txtPrimerApellido);	
				
				dlgEditar.add(lblSegundoApellido);				
				dlgEditar.add(txtSegundoApellido);
				
				dlgEditar.add(lblTelefono);				
				dlgEditar.add(txtTelefono);
				
				dlgEditar.add(lblCorreoElectronico);				
				dlgEditar.add(txtCorreoElectronico);	
				
				dlgEditar.add(lblDni);				
				dlgEditar.add(txtDni);
				
				dlgEditar.add(lblDomicilio);			
				dlgEditar.add(txtDomicilio);
				
				dlgEditar.add(lblPoblacion);			
				dlgEditar.add(txtPoblacion);
				
				dlgEditar.add(lblProvincia);			
				dlgEditar.add(txtProvincia);
				
				dlgEditar.add(lblCodigoPostal);			
				dlgEditar.add(txtCodigoPostal);
				
				dlgEditar.add(btnModificar);
				dlgEditar.add(btnCancelar);
				
				dlgEditar.setBackground(new Color(230, 206, 245));
				dlgEditar.setLocationRelativeTo(null);
				dlgEditar.setVisible(true);
			}
		}
		else if(evento.getSource().equals(btnModificar))
		{
			// Conectar
			bd.conectar();
			int resultado = bd.actualizarCliente(txtId.getText(), txtNombre.getText(), 
					txtPrimerApellido.getText(), txtSegundoApellido.getText(), txtTelefono.getText(),
					txtCorreoElectronico.getText(), txtDni.getText(),
					txtDomicilio.getText(), txtPoblacion.getText(),
					txtProvincia.getText(), txtCodigoPostal.getText());
			// Desconectar
			bd.desconectar();
			rellenarChoiceClientes();			

			if(resultado == 0)
			{
				lblMensaje.setText("Modificación Correcta");
				
				String usuario;
				usuario=Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[UPDATE clientes]");
			}
			else
			{
				lblMensaje.setText("Error en Modificación");
			}
			dlgMensaje.setBackground(new Color(230, 206, 245));
			dlgMensaje.setSize(180,75);
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setResizable(false);
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
			else if (evento.getSource().equals(btnCancelar))
			{
				txtNombre.setText("");
				txtPrimerApellido.setText("");
				txtSegundoApellido.setText("");
				txtTelefono.setText("");
				txtCorreoElectronico.setText("");
				txtDni.setText("");
				txtDomicilio.setText("");
				txtPoblacion.setText("");
				txtProvincia.setText("");
				txtCodigoPostal.setText("");
				
				txtNombre.requestFocus();
				dlgMensaje.setVisible(false);
			}
		}
	}
