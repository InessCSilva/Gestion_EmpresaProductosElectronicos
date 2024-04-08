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

	public class ModificacionProducto implements WindowListener, ActionListener 
	{
		// Componentes
		Frame ventana = new Frame("Modificación de Producto");
		Label lblCabecera = new Label("Elegir el Producto a Modificar:");
		Choice choProductos = new Choice();
		Button btnEditar = new Button("Editar");
		
		Dialog dlgEditar = new Dialog(ventana, "Edición Producto", true);
		Label lblCabecera2 = new Label("Editando el producto nº ");
		
		Label lblId = new Label("Nº Producto:");
		Label lblCodigoProducto = new Label("Código Producto:");
		Label lblIVAProducto = new Label("IVA Producto:");
		Label lblDescripcionProducto = new Label("Descripción Producto:");
		Label lblCantidadAlmacenProducto = new Label("Stock Producto:");
		Label lblDescuentoProducto = new Label("Descuento Producto:");
		Label lblPrecioVentaProducto = new Label("Precio Venta Producto");
		Label lblPrecioCompraProducto = new Label("Precio Compra Producto:");
		Label lblMedidasProducto = new Label("Medidas Producto:");
		
		TextField txtId = new TextField(15);
		TextField txtCodigoProducto = new TextField(15);
		TextField txtIVAProducto = new TextField(15);
		TextField txtDescripcionProducto = new TextField(15);
		TextField txtCantidadAlmacenProducto = new TextField(15);
		TextField txtDescuentoProducto = new TextField(15);
		TextField txtPrecioVentaProducto = new TextField(15);
		TextField txtPrecioCompraProducto = new TextField(15);
		TextField txtMedidasProducto = new TextField(15);
		
		Button btnModificar = new Button("Modificar");
		Button btnCancelar = new Button("Cancelar");

		Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
		Label lblMensaje = new Label("XXXXXXXXXXXXXX");

		BaseDatos bd = new BaseDatos();
		ResultSet rs = null;

		// Constructor
		public ModificacionProducto()
		{
			// Listener
			ventana.addWindowListener(this);
			btnEditar.addActionListener(this);
			btnModificar.addActionListener(this);
			btnCancelar.addActionListener(this);
			dlgEditar.addWindowListener(this);
			dlgMensaje.addWindowListener(this);

			// Pantalla
			ventana.setSize(450, 130); 
			// No Permitir redimensionar
			ventana.setResizable(false); 

			ventana.setLayout(new FlowLayout());
			ventana.setBackground(new Color(230, 206, 245));
			ventana.add(lblCabecera);

			rellenarChoiceProductos();
			
			ventana.add(choProductos);
			ventana.add(btnEditar);

			// Fijar que la ventana salga siempre en el medio
			ventana.setLocationRelativeTo(null); 
			// Mostrarla
			ventana.setVisible(true); 
		}

		private void rellenarChoiceProductos()
		{
			choProductos.removeAll();
			// Rellenar Choice
			choProductos.add("Elegir Producto...");
			// Conectar BD
			bd.conectar();
			// Sacar los datos de la tabla Productos
			rs = bd.selectProducto();
			// Registro a registro, meterlos en el Choice
			try
			{
				while (rs.next())
				{
					choProductos.add(rs.getInt("idProducto") + "-" + rs.getString("codigoProducto") + " "
							+ rs.getString("IVAProducto") + " " + rs.getString("descripcionProducto") + "-"
							+ rs.getString("cantidadAlmacenProducto") + "-" + rs.getString("descuentoProducto") + "-" 
							+ rs.getString("precioVentaProducto") + "-" +  rs.getString("precioCompraProducto") + "-" 
							+ rs.getString("medidasProducto"));
				}
			} 
			catch (SQLException e)
			{
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
				if(!choProductos.getSelectedItem().equals("Elegir Producto..."))
				{
					String[] seleccionado = choProductos.getSelectedItem().split("-");
					// Conectar BD y sacar los datos del producto seleccionado
					bd.conectar();
					rs = bd.consultarProducto(seleccionado[0]);
					try
					{
						rs.next();
						txtCodigoProducto.setText(rs.getString("codigoProducto"));
						txtIVAProducto.setText(rs.getString("IVAProducto"));
						txtDescripcionProducto.setText(rs.getString("descripcionProducto"));
						txtCantidadAlmacenProducto.setText(rs.getString("cantidadAlmacenProducto"));
						txtDescuentoProducto.setText(rs.getString("descuentoProducto"));
						txtPrecioVentaProducto.setText(rs.getString("precioVentaProducto"));
						txtPrecioCompraProducto.setText(rs.getString("precioCompraProducto"));
						txtMedidasProducto.setText(rs.getString("medidasProducto"));		
					}	
					catch(SQLException sqle) {}
					bd.desconectar();
					// Muestro los datos del producto elegido
					// en la pantalla de edición
					dlgEditar.setSize(175, 610);
					 // No Permitir redimensionar
					dlgEditar.setResizable(false);

					dlgEditar.setLayout(new FlowLayout());
					dlgEditar.add(lblCabecera2);
					dlgEditar.add(lblId);
					txtId.setEnabled(false);
					// rs.getInt("idProducto");
					txtId.setText(seleccionado[0]);
					dlgEditar.add(txtId);
					
					dlgEditar.add(lblCodigoProducto);				
					dlgEditar.add(txtCodigoProducto);
					
					dlgEditar.add(lblIVAProducto);				
					dlgEditar.add(txtIVAProducto);	
					
					dlgEditar.add(lblDescripcionProducto);				
					dlgEditar.add(txtDescripcionProducto);
					
					dlgEditar.add(lblCantidadAlmacenProducto);				
					dlgEditar.add(txtCantidadAlmacenProducto);
					
					dlgEditar.add(lblDescuentoProducto);				
					dlgEditar.add(txtDescuentoProducto);	
					
					dlgEditar.add(lblPrecioVentaProducto);				
					dlgEditar.add(txtPrecioVentaProducto);
					
					dlgEditar.add(lblPrecioCompraProducto);			
					dlgEditar.add(txtPrecioCompraProducto);
					
					dlgEditar.add(lblMedidasProducto);			
					dlgEditar.add(txtMedidasProducto);
					
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
				int resultado = bd.actualizarProducto(txtId.getText(), txtCodigoProducto.getText(), 
						txtIVAProducto.getText(), txtDescripcionProducto.getText(), txtCantidadAlmacenProducto.getText(),
						txtDescuentoProducto.getText(), txtPrecioVentaProducto.getText(),
						txtPrecioCompraProducto.getText(), txtMedidasProducto.getText());
				// Desconectar
				bd.desconectar();
				rellenarChoiceProductos();			

				if(resultado == 0)
				{
					lblMensaje.setText("Modificación Correcta");
					String usuario;
					usuario=Login.txtUsuario.getText();
					bd.registroEscritura("["+usuario+"]"+"[UPDATE productos]");			
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
					txtCodigoProducto.setText("");
					txtIVAProducto.setText("");
					txtDescripcionProducto.setText("");
					txtCantidadAlmacenProducto.setText("");
					txtDescuentoProducto.setText("");
					txtPrecioVentaProducto.setText("");
					txtPrecioCompraProducto.setText("");
					txtMedidasProducto.setText("");
					
					txtCodigoProducto.requestFocus();
					dlgMensaje.setVisible(false);
				}
			}
		}

