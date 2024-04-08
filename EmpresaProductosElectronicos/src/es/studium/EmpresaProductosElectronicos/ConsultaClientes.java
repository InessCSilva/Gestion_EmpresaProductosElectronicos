package es.studium.EmpresaProductosElectronicos;

import java.awt.*;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ConsultaClientes extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	// Componentes
	Frame ventana = new Frame("Listado de Clientes");
	TextArea txtListado = new TextArea(5, 110);
	Button btnPdf = new Button("Exportar PDF");
	BaseDatos bd = new BaseDatos();

	// Constructor
	public ConsultaClientes()
	{
		// Listener
		ventana.addWindowListener(this);

		// Pantalla
		ventana.setSize(850, 180);
		ventana.setResizable(false);

		ventana.setLayout(new FlowLayout());
		ventana.setBackground(new Color(230, 206, 245));
		// Rellenar TextArea con la informaci�n de la BD
		// Conectar
		bd.conectar();
		// Sacar la informaci�n y meterla en el TextArea
		txtListado.setText(bd.consultarClientes());
		bd.EscrituraTablaPdf(txtListado.getText());
		// Desconectar
		bd.desconectar();
		
		String usuario;
		usuario=Login.txtUsuario.getText();
		bd.registroEscritura("["+usuario+"]"+"[INSERT INTO clientes]");

		addWindowListener(this);
		btnPdf.addActionListener(this);

		ventana.add(txtListado);
		ventana.add(btnPdf);

		ventana.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
	}

	public void windowActivated(WindowEvent we)
	{
	}

	public void windowClosed(WindowEvent we)
	{
	}

	public void windowClosing(WindowEvent we)
	{
		ventana.setVisible(false);
	}

	public void windowDeactivated(WindowEvent we)
	{
	}

	public void windowDeiconified(WindowEvent we)
	{
	}

	public void windowIconified(WindowEvent we)
	{
	}

	public void windowOpened(WindowEvent we)
	{
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnPdf))
		{
				try {
					new pdfClientes("ConsultaClientes.pdf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
