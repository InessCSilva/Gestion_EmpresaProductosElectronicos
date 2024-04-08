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

public class ModificacionRecibo implements WindowListener, ActionListener
{

	// Componentes
	Frame ventana = new Frame("Modificación de Recibo");
	Label lblCabecera = new Label("Elegir el Recibo a Modificar:");
	Choice choRecibos = new Choice();
	Button btnEditar = new Button("Editar");
	
	Dialog dlgEditar = new Dialog(ventana, "Edición Recibo", true);
	Label lblCabecera2 =  new Label("Editando el recibo nº ");
	Label lblId = new Label("Nº Recibo:");
	Label lblFecha = new Label("Fecha:");
	Label lblSubTotal = new Label("SubTotal:");
	Label lblClienteFK = new Label("Cliente:");

	TextField txtId = new TextField(15);
	TextField txtFecha = new TextField(15);
	TextField txtSubTotal = new TextField(15);
	TextField txtClienteFK = new TextField(15);

	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;

	// Constructor
	public ModificacionRecibo()
		{
			// Listener
			ventana.addWindowListener(this);
			btnEditar.addActionListener(this);
			btnModificar.addActionListener(this);
			btnCancelar.addActionListener(this);
			dlgEditar.addWindowListener(this);
			dlgMensaje.addWindowListener(this);

			// Pantalla
			ventana.setSize(185, 140);
			ventana.setResizable(false);

			ventana.setLayout(new FlowLayout());
			ventana.setBackground(new Color(230, 206, 245));
			ventana.add(lblCabecera);
			// Rellenar Choice
			choRecibos.add("Elegir Recibo...");
			// Conectar
			bd.conectar();
			// Sacar los datos de la tabla Recibo
			rs = bd.selectRecibo();
			// Registro a registro, meterlos en el Choice
			try
			{
				while (rs.next())
				{
					choRecibos.add(rs.getInt("idRecibo") + "-" + rs.getString("fechaRecibo") + " "
							+ rs.getDouble("subTotalRecibo") + " " + rs.getInt("idClienteFK"));
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			// Desconectar
			bd.desconectar();

			ventana.add(choRecibos);
			ventana.add(btnEditar);

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
		if (evento.getSource().equals(btnEditar))
		{
			if(!choRecibos.getSelectedItem().equals("Elegir Recibo ..."))
				{
					String[] seleccionado = choRecibos.getSelectedItem().split("-");
					// Conectar BD
					bd.conectar();
					rs = bd.consultarRecibo(seleccionado[0]);
					try
					{
						rs.next();
						txtFecha.setText(rs.getString("fechaRecibo"));
						txtSubTotal.setText(rs.getString("subTotalRecibo"));
						txtClienteFK.setText(rs.getString("idClienteFK"));
					}
					catch (SQLException sqle){}
					// Desconectar BD
					bd.desconectar();
					// Muestro los datos del Recibo elegido
					// en la pantalla de edición
					dlgEditar.setSize(200, 350);
					dlgEditar.setResizable(false);

					dlgEditar.setLayout(new FlowLayout());
					dlgEditar.add(lblCabecera2);
					
					dlgEditar.add(lblId);
					txtId.setEnabled(false);
					txtId.setText(seleccionado[0]);
					dlgEditar.add(txtId);
					
					dlgEditar.add(lblFecha);
					dlgEditar.add(txtFecha);
					
					dlgEditar.add(lblSubTotal);
					dlgEditar.add(txtSubTotal);
					
					dlgEditar.add(lblClienteFK);
					dlgEditar.add(txtClienteFK);
					
					dlgEditar.add(btnModificar);
					dlgEditar.add(btnCancelar);
					
					dlgEditar.setBackground(new Color(230, 206, 245));
					dlgEditar.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
					dlgEditar.setVisible(true); // Mostrarla			
		}
	}
	else if (evento.getSource().equals(btnModificar))
	{
		bd.conectar();
		int resultado = bd.actualizarRecibo(txtId.getText(), txtFecha.getText(), txtSubTotal.getText(),
				txtClienteFK.getText());
		bd.desconectar();
		if (resultado == 0)
			{
				lblMensaje.setText("Modificación Correcta");
				
				String usuario;
				usuario=Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[UPDATE recibos]");
			} 
			else 
			{
				lblMensaje.setText("Error en Modificación");
			}
			dlgMensaje.setBackground(new Color(230, 206, 245));
			dlgMensaje.setSize(180, 100);
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setResizable(false);
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
		else if (evento.getSource().equals(btnCancelar))
		{
			txtFecha.setText("");
			txtSubTotal.setText("");
			txtClienteFK.setText("");

			txtId.requestFocus();
			dlgMensaje.setVisible(false);
		}
	}
}