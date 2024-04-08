package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MenuPrincipal implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Menú Principal");
	MenuBar barraMenu = new MenuBar();
	Menu menuProductos = new Menu("Productos");
	MenuItem menuAltaProducto = new MenuItem("Nuevo Producto");
	MenuItem menuConsultaProductos = new MenuItem("Listados de Productos");
	MenuItem menuBajaProducto = new MenuItem("Eliminar Producto");
	MenuItem menuModificacionProducto = new MenuItem("Modificar Producto");
	
	Menu menuClientes = new Menu("Clientes");
	MenuItem menuAltaCliente = new MenuItem("Nuevo Cliente");
	MenuItem menuConsultaClientes = new MenuItem("Listados de Clientes");
	MenuItem menuBajaCliente = new MenuItem("Eliminar Cliente");
	MenuItem menuModificacionCliente = new MenuItem("Modificar Cliente");
	
	Menu menuRecibos = new Menu("Recibos");
	MenuItem menuAltaRecibo = new MenuItem("Nuevo Recibo");
	MenuItem menuConsultaRecibos = new MenuItem("Listados de Recibos");
	MenuItem menuBajaRecibo = new MenuItem("Eliminar Recibo");
	MenuItem menuModificacionRecibo = new MenuItem("Modificar Recibo");
	
	Menu menuAyuda = new Menu("Ayuda");
	MenuItem menuComoUsar = new MenuItem("Cómo Usar");
	
	Label lblUsuario = new Label();
	
	public MenuPrincipal(int tipoUsuario)
	{
				//	Listener
				ventana.addWindowListener(this);
				
				// Damos función al CRUD de Clientes
				menuConsultaClientes.addActionListener(this);
				menuAltaCliente.addActionListener(this);
				menuBajaCliente.addActionListener(this);
				menuModificacionCliente.addActionListener(this);
				
				// Damos función al CRUD de Recibos
				menuBajaRecibo.addActionListener(this);
				menuConsultaRecibos.addActionListener(this);
				menuAltaRecibo.addActionListener(this);
				menuModificacionRecibo.addActionListener(this);
						
				// Damos función al CRUD de Productos
				menuAltaProducto.addActionListener(this);
				menuConsultaProductos.addActionListener(this);
				menuBajaProducto.addActionListener(this);
				menuModificacionProducto.addActionListener(this);
				
				// Damos función a la ayuda
				menuComoUsar.addActionListener(this);
				
				//	Pantalla
				ventana.setSize(280, 180); // En pixeles Width, Height. Ir probando y cambiando anchura y altura
				ventana.setResizable(false); // No Permitir redimensionar

				ventana.setLayout(new FlowLayout()); // Fijar la distribucion de labels and text fields
				menuRecibos.add(menuAltaRecibo);
				menuRecibos.add(menuConsultaRecibos);
				lblUsuario = new Label("Usuario: Básico");
				if(tipoUsuario==1)
				{
					menuRecibos.add(menuBajaRecibo);
					menuRecibos.add(menuModificacionRecibo);
					lblUsuario = new Label("Usuario: Administrador");
				}
				barraMenu.add(menuRecibos);
				
				menuProductos.add(menuAltaProducto);
				menuProductos.add(menuConsultaProductos);
				if(tipoUsuario==1)
				{
					menuProductos.add(menuBajaProducto);
					menuProductos.add(menuModificacionProducto);
				}
				barraMenu.add(menuProductos);
				
				menuClientes.add(menuAltaCliente);
				menuClientes.add(menuConsultaClientes);
				if(tipoUsuario==1)
				{
					menuClientes.add(menuBajaCliente);
					menuClientes.add(menuModificacionCliente);
				}
				barraMenu.add(menuClientes);
				
				ventana.setMenuBar(barraMenu);
				
				menuAyuda.add(menuComoUsar);
				barraMenu.add(menuAyuda);
				
				ventana.add(lblUsuario);
				
				ventana.setBackground(new Color(208, 170, 232));
				ventana.setLocationRelativeTo(null); //	fijar que la ventana salga siempre en el medio
				ventana.setVisible(true); // Mostrarla
	}
	
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
			System.exit(0);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(menuConsultaClientes))
		{
			new ConsultaClientes();
		}
		else if(evento.getSource().equals(menuAltaCliente))
		{
			new AltaCliente();
		}	
		else if(evento.getSource().equals(menuBajaCliente))
		{
			new BajaCliente();
		}
		else if(evento.getSource().equals(menuModificacionCliente))
		{
			new ModificacionCliente();
		}
		else if(evento.getSource().equals(menuBajaRecibo))
		{
			new BajaRecibo();
		}
		else if(evento.getSource().equals(menuConsultaRecibos))
		{
			new ConsultaRecibos();
		}
		else if(evento.getSource().equals(menuAltaRecibo))
		{
			new AltaRecibo();
		}
		else if(evento.getSource().equals(menuModificacionRecibo))
		{
			new ModificacionRecibo();
		}
		else if(evento.getSource().equals(menuComoUsar))
		{
			try 
			{
				Runtime.getRuntime().exec("hh.exe Ayuda.chm");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}	
		}
		else if(evento.getSource().equals(menuAltaProducto))
		{
			new AltaProducto();
		}
		else if(evento.getSource().equals(menuConsultaProductos))
		{
			new ConsultaProductos();
		}
		else if(evento.getSource().equals(menuBajaProducto))
		{
			new BajaProducto();
		}
		else if(evento.getSource().equals(menuModificacionProducto))
		{
			new ModificacionProducto();
		}
	}
}
