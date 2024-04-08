package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Login implements WindowListener, ActionListener
{
	
	// Atributos, Componentes, Campos, ...
	// Diseño de una ventana
	Frame ventana = new Frame("Login"); // Crear ventana
	
	Label lblUsuario = new Label("Usuario");
	Label lblClave = new Label("Clave    ");
	
	static TextField txtUsuario = new TextField(15);
	TextField txtClave = new TextField(15);
	
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXX");
	
	// Constructor
	public Login() 
	{
		// Listener
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		// Pantalla
		ventana.setSize(220, 130); // En pixeles Width, Height. Ir probando y cambiando anchura y altura
		ventana.setResizable(false); // No Permitir redimensionar

		ventana.setLayout(new FlowLayout()); // Fijar la distribucion de labels and text fields
		
		ventana.setBackground(new Color(208, 170, 232));
		ventana.add(lblUsuario);
		ventana.add(txtUsuario);
		ventana.add(lblClave);
		txtClave.setEchoChar('*'); // Para que en vez de mostrar la contraseña se sustituyan los caracteres por *
		ventana.add(txtClave);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);
		
		ventana.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
	}
	
	public static void main(String[] args)
	{
		new Login();
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(btnCancelar))
		{
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		}
		else if(ae.getSource().equals(btnAceptar))
		{
			// Coger los textos de la ventana: usuario y clave
			String usuario = txtUsuario.getText();
			String clave = txtClave.getText();
			// Conectar BD
			BaseDatos bd = new BaseDatos();
			bd.conectar();
			// Hacer un SELECT
			int resultado = bd.consultar("SELECT * FROM usuarios WHERE nombreUsuario = '"+usuario+
					"' AND claveUsuario = SHA2('"+clave+"', 256);");
			// Caso negativo (-1): Mostrar Mensaje Error
			if(resultado==-1)
			{
				dlgMensaje.setSize(180,75);
				dlgMensaje.setLayout(new FlowLayout());
				dlgMensaje.setResizable(false);
				lblMensaje.setText("Credenciales incorrectas");
				dlgMensaje.add(lblMensaje);
				dlgMensaje.setLocationRelativeTo(null);
				dlgMensaje.setVisible(true);
			}
			else
			{
				// Caso afirmativo (0-1): Mostrar Menú Principal
				new MenuPrincipal(resultado);
				ventana.setVisible(false);	
				
				// Registro Escritura
				bd.registroEscritura("["+usuario+"]"+"[LOGIN]");
			}
			// Desconectar BD
			bd.desconectar();
		}
	}
	
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		else
		{
			System.exit(0);
		}
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

}
