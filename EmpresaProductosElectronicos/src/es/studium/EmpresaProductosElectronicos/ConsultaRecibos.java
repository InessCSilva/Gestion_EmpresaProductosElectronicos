package es.studium.EmpresaProductosElectronicos;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.ResultSet;

public class ConsultaRecibos extends Frame implements WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	// Componentes
	Frame ventana = new Frame("Listado de Recibos");
	TextArea txtListado = new TextArea(5,35);
	Label lblCabecera = new Label("Elegir Recibo");
	Button btnPdf = new Button("Exportar PDF");
	
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null ;
	
	// Constructor
	public ConsultaRecibos() {
		// Listener
		ventana.addWindowListener(this);
		btnPdf.addActionListener(this);
		
		// Pantalla
		ventana.setSize(320, 210); 
		ventana.setResizable(false);
		
		ventana.setLayout(new FlowLayout());
		ventana.setBackground(new Color(230, 206, 245));
		ventana.add(lblCabecera);	
		// Rellenar TextArea con BD
		// Conectar
		bd.conectar();
		
		// Sacar la información y meterla en el TextArea
		txtListado.setText(bd.consultarRecibos());
		bd.EscrituraTablaPdfRecibo(txtListado.getText());
		
		// Desconectar BD
		bd.desconectar();
		
		String usuario;
		usuario = Login.txtUsuario.getText();
		bd.registroEscritura("[" + usuario + "]" + "[SELECT * FROM recibos]");
		
		ventana.add(txtListado);		
		ventana.add(btnPdf);
		
		ventana.setLocationRelativeTo(null); // fijar que la ventana salga siempre en el medio
		ventana.setVisible(true); // Mostrarla
	}
	
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we) {
			ventana.setVisible(false);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

	@Override
	public void actionPerformed(ActionEvent evento) {	
		if(evento.getSource().equals(btnPdf)) {
			try {
				new pdfRecibos("ConsultaRecibos.pdf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


