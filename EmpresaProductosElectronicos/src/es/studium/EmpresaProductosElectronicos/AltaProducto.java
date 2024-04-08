package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AltaProducto implements WindowListener, ActionListener{

	// Componentes
	Frame ventana = new Frame("Alta de Producto");
	
	Label lblCodigoProducto = new Label("Código Producto:");
	Label lblIVAProducto = new Label("IVA Producto:");
	Label lblDescripcionProducto = new Label("Descripción Producto:");
	Label lblCantidadAlmacenProducto = new Label("Stock Producto:");
	Label lblDescuentoProducto = new Label("Descuento Producto:");
	Label lblPrecioVentaProducto = new Label("Precio Venta Producto:");
	Label lblPrecioCompraProducto = new Label("Precio Compra Producto:");
	Label lblMedidasProducto = new Label("Medidas Producto:");

	
	TextField txtCodigoProducto = new TextField(15);
	TextField txtIVAProducto = new TextField(15);
	TextField txtDescripcionProducto = new TextField(15);
	TextField txtCantidadAlmacenProducto = new TextField(15);
	TextField txtDescuentoProducto = new TextField(15);
	TextField txtPrecioVentaProducto = new TextField(15);
	TextField txtPrecioCompraProducto = new TextField(15);
	TextField txtMedidasProducto = new TextField(15);
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXX");
	
	BaseDatos bd = new BaseDatos();
	

	
	// Constructor
	public AltaProducto()
	{
		// Listener
		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		// Pantalla
		ventana.setSize(175, 530);
		ventana.setResizable(false);

		ventana.setLayout(new FlowLayout());
		ventana.setBackground(new Color(230, 206, 245));
		
		ventana.add(lblCodigoProducto);
		ventana.add(txtCodigoProducto);
		
		ventana.add(lblIVAProducto);
		ventana.add(txtIVAProducto);
		
		ventana.add(lblDescripcionProducto);
		ventana.add(txtDescripcionProducto);
		
		ventana.add(lblCantidadAlmacenProducto);
		ventana.add(txtCantidadAlmacenProducto);
		
		ventana.add(lblDescuentoProducto);
		ventana.add(txtDescuentoProducto);
		
		ventana.add(lblPrecioVentaProducto);
		ventana.add(txtPrecioVentaProducto);
		
		ventana.add(lblPrecioCompraProducto);
		ventana.add(txtPrecioCompraProducto);
		
		ventana.add(lblMedidasProducto);
		ventana.add(txtMedidasProducto);
		
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
			String codigo = txtCodigoProducto.getText();
			String IVA = txtIVAProducto.getText();
			String descripcion = txtDescripcionProducto.getText();
			String cantidadAlmacen = txtCantidadAlmacenProducto.getText();
			String descuento = txtDescuentoProducto.getText();
			String precioVenta = txtPrecioVentaProducto.getText();
			String precioCompra = txtPrecioCompraProducto.getText();
			String medidas = txtMedidasProducto.getText();
			
			// Hacer el INSERT con esos datos
			if(codigo.length()!=0)
			{
			String sentencia = "INSERT INTO productos VALUES(null,'" +
			codigo+"','"+IVA+"','"+descripcion+
			"','"+cantidadAlmacen+"','"+descuento+"','"+precioVenta+
			"','"+precioCompra+"','"+medidas+"');";
			int resultado = bd.insertarProducto(sentencia);
			if(resultado== 0)
			{
				lblMensaje.setText("Alta Correcta");
				String usuario;
				usuario=Login.txtUsuario.getText();
				bd.registroEscritura("["+usuario+"]"+"[INSERT INTO productos]");	
			}
			else
			{
				lblMensaje.setText("Error en Alta");
			}
		}
			else
			{
				lblMensaje.setText("El código del producto no puede estar vacío");
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
		txtCodigoProducto.setText("");
		txtIVAProducto.setText("");
		txtDescripcionProducto.setText("");
		txtCantidadAlmacenProducto.setText("");
		txtDescuentoProducto.setText("");
		txtPrecioVentaProducto.setText("");
		txtPrecioCompraProducto.setText("");
		txtMedidasProducto.setText("");
		txtCodigoProducto.requestFocus();
	}	
}


