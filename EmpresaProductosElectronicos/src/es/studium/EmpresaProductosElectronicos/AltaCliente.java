package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AltaCliente implements WindowListener, ActionListener
{
	// Componentes
	Frame ventana = new Frame("Alta de Cliente");
	
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
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();
	
	// Constructor
	public AltaCliente()
	{
		// Listener
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		// Pantalla
		ventana.setSize(175, 640);
		ventana.setResizable(false);

		ventana.setLayout(new FlowLayout());
		ventana.setBackground(new Color(230, 206, 245));
		
		ventana.add(lblNombre);
		ventana.add(txtNombre);
		
		ventana.add(lblPrimerApellido);
		ventana.add(txtPrimerApellido);
		
		ventana.add(lblSegundoApellido);
		ventana.add(txtSegundoApellido);
		
		ventana.add(lblTelefono);
		ventana.add(txtTelefono);
		
		ventana.add(lblCorreoElectronico);
		ventana.add(txtCorreoElectronico);
		
		ventana.add(lblDni);
		ventana.add(txtDni);
		
		ventana.add(lblDomicilio);
		ventana.add(txtDomicilio);
		
		ventana.add(lblPoblacion);
		ventana.add(txtPoblacion);
		
		ventana.add(lblProvincia);
		ventana.add(txtProvincia);
		
		ventana.add(lblCodigoPostal);
		ventana.add(txtCodigoPostal);
		
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		
		ventana.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
	}
	
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
			limpiar();
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
		// Aceptar
		if(evento.getSource().equals(btnAceptar))
		{
			// Conectar
			bd.conectar();
			// Coger los datos del formulario
			String nombre = txtNombre.getText();
			String primerApellido = txtPrimerApellido.getText();
			String segundoApellido = txtSegundoApellido.getText();
			String telefono = txtTelefono.getText();
			String correoElectronico = txtCorreoElectronico.getText();
			String dni = txtDni.getText();
			String domicilio = txtDomicilio.getText();
			String poblacion = txtPoblacion.getText();
			String provincia = txtProvincia.getText();
			String codigoPostal = txtCodigoPostal.getText();
			
			// Hacer el INSERT con esos datos
			if(nombre.length()!=0)
			{
			String sentencia = "INSERT INTO clientes VALUES(null,'" +
			nombre+"','"+primerApellido+"','"+segundoApellido+
			"','"+telefono+"','"+correoElectronico+"','"+dni+
			"','"+domicilio+"','"+poblacion+"','"+provincia+"','"+
			codigoPostal+"');";
			int resultado = bd.insertarCliente(sentencia);
			if(resultado== 0)
			{
				lblMensaje.setText("Alta Correcta");
				String usuario;
				usuario=Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[INSERT INTO clientes]");			
			}
			else
			{
				lblMensaje.setText("Error en Alta");
			}
		}
			else
			{
				lblMensaje.setText("El nombre no puede estar vacío");
			}
			
			// Desconectar
			bd.desconectar();
			dlgMensaje.setBackground(new Color(230, 206, 245));
			dlgMensaje.setSize(210, 85);
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setResizable(false);
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
		// Limpiar
		else if(evento.getSource().equals(btnLimpiar))
		{
			limpiar();
		}		
	}

	private void limpiar()
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
	}	
}
