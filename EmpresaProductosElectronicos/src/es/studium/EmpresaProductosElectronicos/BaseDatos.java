package es.studium.EmpresaProductosElectronicos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDatos
{
	String driver = "com.mysql.cj.jdbc.Driver"; 
	String url = "jdbc:mysql://localhost:3306/empresa_productos_electronicos_pr"; 
	String login = "electronicos"; 	// Usuario MySQl
	String password = "Studium2022;"; // Su clave correspondiente
	String sentencia = ""; 
	Connection connection = null; 
	Statement statement = null;
	
	// Constructor
	public BaseDatos()
	{}
	
	// Conectar a la BD
	public void conectar()
	{
		try
		{
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{} 
		catch (SQLException sqle)
		{} 	
	}
	
	// Consulta tipo de Usuario, usado en Clase Login
	public int consultar(String sentencia)
	{
		int resultado = -1;
		ResultSet rs = null;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			if(rs.next()) // Si hay, al menos uno
			{
				resultado = rs.getInt("tipoUsuario");
			}
		}
		catch (SQLException sqle)
		{}
		return resultado;
	}
	
		
	// TABLA CLIENTES
	
	// Consultamos todos los datos de la tabla clientes
	public ResultSet selectCliente()
	{
		ResultSet rs = null;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sequencia SQL
			rs = statement.executeQuery("SELECT * FROM clientes");
		}
		catch (SQLException sqle)
		{}	
		return (rs);
	}
	
	// Consulta la tabla Clientes para poder mostrarlo en el TextArea usado en Clase ConsultaClientes
	public String consultarClientes()
	{
		String contenido = " ";
		ResultSet rs = null;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// Y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM clientes");
			while(rs.next()) // Si hay, al menos uno
			{
				contenido = contenido + rs.getInt("idCliente") +
						"-" + rs.getString("nombreCliente") +
						"-" + rs.getString("primerApellidoCliente") +
						"-" + rs.getString("segundoApellidoCliente") + 
						"-" + rs.getInt("telefonoCliente") +
						"-" + rs.getString("correoElectronicoCliente") +
						"-" + rs.getString("dniCliente") +
						"-" + rs.getString("domicilioCliente") +
						"-" + rs.getString("poblacionCliente") +
						"-" + rs.getString("provinciaCliente") +
						"-" + rs.getInt("codigoPostalCliente") + "\n";
			}
		}
		catch (SQLException sqle)
		{}	
		return(contenido);
	}
	
	//Comprobar alta tabla Clientes, usado en Clase AltaClientes
	public int insertarCliente(String sentencia)
	{
		int resultado = 0; //Éxito
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutar el INSERT
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //Error
		}	
		return(resultado);
	}
		
	//Comprueba si el borrado del Cliente se ha realizado correctamente o no
	public int borrarCliente(int idCliente)
	{
		int resultado = 0;
		//	Devolvemos un 0 --> Borrado éxito
		//	Devolvemos un -1 --> Borrado error
		try
		{
			//	Crear una sentencia
			statement = connection.createStatement();
			//	Ejecutar el DELETE
			String sentencia ="DELETE FROM clientes WHERE idCliente="+idCliente;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; //	Error
		}	
		return(resultado);
	}
	
	//	Consulta la tabla Clientes para poder rellenar el Choice usado en la Clase ModificacionClientes
	public ResultSet consultarCliente(String idCliente)
	{
		ResultSet rs = null;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//	Crear un objeto ResultSet para guardar lo obtenido
			//	Y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM clientes WHERE idCliente = " + idCliente);
		}
		catch (SQLException sqle)
		{}	
		return(rs);
	}
	
	//	Hace el UPDATE y comprueba si se ha realizado correctamente o no en la Clase ModificacionCliente
	public int actualizarCliente(String idCliente, String nombreNuevo, 
			String primerApellidoNuevo, String segundoApellidoNuevo, 
			String TelefonoNuevo, String CorreoElectronicoNuevo, 
			String DniNuevo, String domicilioNuevo, String poblacionNuevo, 
			String provinciaNuevo, String codigoPostalNuevo)
	{
		int resultado = 0;
		String sentencia = "UPDATE clientes SET nombreCliente = '"+nombreNuevo+"', primerApellidoCliente = '"+primerApellidoNuevo+"', "
				+ "segundoApellidoCliente = '"+segundoApellidoNuevo+"', telefonoCliente = '"+TelefonoNuevo+"',"
				+ " correoElectronicoCliente = '"+CorreoElectronicoNuevo+"', dniCliente = '"+DniNuevo+"', domicilioCliente = '"+domicilioNuevo+"', "
				+ "poblacionCliente = '"+poblacionNuevo+"', provinciaCliente = '"+provinciaNuevo+"',"
				+ " codigoPostalCliente = '"+codigoPostalNuevo+"' WHERE idCliente = " + idCliente;         
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Ejecutar el UPDATE
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; // Error
		}
		// Devolvemos un 0 --> Modificación con éxito
		// Devolvemos un -1 --> Modificación error
		return (resultado);		
	}
	
	
	
	// TABLA RECIBOS

	
	// Consulta la tabla Recibos
	public ResultSet selectRecibo() 
	{
		ResultSet rs = null;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Ejecutar el SELECT
			rs = statement.executeQuery("SELECT * FROM recibos");
		}
		catch (SQLException sqle)
		{}	
		return (rs);
	}

	// Consulta la tabla Recibos para poder mostrar en el TextArea de la Clase ConsultaRecibo
	public String consultarRecibos()
	{
		String contenido = "";
		ResultSet rs = null;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sequencia SQL
			rs = statement.executeQuery("SELECT idRecibo AS 'Nº Recibo', nombreCliente AS 'Nombre', "
					+ "primerApellidoCliente AS 'Primer Apellido', segundoApellidoCliente AS 'Segundo Apellido', "
					+ "dniCliente AS 'Dni' FROM clientes JOIN recibos ON idCliente = idClienteFK");
			while(rs.next())
			{
				contenido = contenido + rs.getInt("Nº Recibo") + "-" + rs.getString("Nombre") + 
				"-" + rs.getString("Primer Apellido")+ "-" + rs.getString("Segundo Apellido") + "-" + rs.getString("Dni") + "\n";
			}	
		}
		catch (SQLException sqle)
		{}	
		return (contenido);
	}	
	
	// Comprueba si el DELETE se ha realizado correctamente o no en la Clase BajaCliente
	public int borrarRecibo(int idRecibo)
	{
		int resultado = 0;
		// Devolvemos un 0 --> Borrado éxito
		// Devolvemos un -1 --> Borrado error
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Ejecutar el DELETE
			statement.executeUpdate("DELETE FROM recibos WHERE idRecibo="+idRecibo);
		}
		catch (SQLException sqle)
		{
			resultado = -1; // Error
		}	
		return(resultado);
	}
	
	// Insert de la tabla Recibos para AltaRecibo
	public int insertarRecibo(String fecha, String subTotal, String clienteFK)
	{
		int resultado = 0; // Éxito
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			String sentencia = "INSERT INTO recibos VALUES(null, " + "'" + fecha + "', '" + subTotal + "', '" + clienteFK + "');";
			//	Ejecutar el INSERT
			statement.execute(sentencia);
		}
		catch (SQLException sqle)
		{
			resultado = -1; // Error
		}	
		return(resultado);
	}
	
	// Consulta la tabla Recibos para poder rellenar el Choice usado en la Clase ModificacionRecibo
	public ResultSet consultarRecibo(String idRecibo)
	{
		ResultSet rs = null;
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sequencia SQL
			rs = statement.executeQuery("SELECT * FROM recibos WHERE idRecibo = "+idRecibo);	
		}
		catch (SQLException sqle)
		{}	
		return (rs);
	}
	
	// Hace el UPDATE y comprueba si se ha realizado correctamente o no en la Clase ModificacionRecibo
	public int actualizarRecibo(String idRecibo, String fechaReciboNuevo, String subTotalReciboNuevo,
			String idClienteFKNuevo)
	{
		int resultado = 0;
		String sentencia = "UPDATE recibos SET fechaRecibo = '" + fechaReciboNuevo + "', subTotalRecibo = '"
				+ subTotalReciboNuevo + "'," + " idClienteFK = '" + idClienteFKNuevo + "' WHERE idRecibo = " + idRecibo;
		// Devolvemos un 0 --> Modificación con éxito
		// Devolvemos un -1 --> Modificación error
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Ejecutar el UPDATE
			statement.executeUpdate(sentencia);
		} 
		catch (SQLException sqle)
		{
			resultado = -1; // Error
		}
		return (resultado);
	}	
	

	
	// Tercer Trimestre
	
	
	// Fecha y Hora
	public String consultarFecha() 
	{
		Date fechaHora;
		SimpleDateFormat calendarioHora;
		fechaHora = new Date();
		calendarioHora = new SimpleDateFormat("dd/MM/yyyy H:mm");
		System.out.println(calendarioHora.format(fechaHora));
		return calendarioHora.format(fechaHora);
	}

	// Genera el FicheroEPE, donde se guardan todos los registros
	public String registroEscritura(String resultado)
	{
		String contenido = "";
		try
		{
			// Destino de los datos
			FileWriter fw = new FileWriter("FicheroEPE.txt", true);
			// Buffer de escritura
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto para la escritura
			PrintWriter salida = new PrintWriter(bw);
			String calendarioHora;
			calendarioHora = consultarFecha();
			contenido= "["+calendarioHora+"]" + resultado; 
			salida.println(contenido);
			// Cerrar el objeto salida, el objeto bw y el fw
			salida.close();
			bw.close();
			fw.close();
			System.out.println("Información guardada en fichero");
		} catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		return (contenido);
	}
	
	// Genera FicheroConsultasClientes, aquí se guardan los datos de todos los clientes para poder generar el PDF
	public String EscrituraTablaPdf(String resultado)
	{
		String contenido = "";
		try
		{
			// Destino de los datos
			FileWriter fw = new FileWriter("FicheroConsultasClientes.txt", false);
			// Buffer de escritura
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto para la escritura
			PrintWriter salida = new PrintWriter(bw);
			contenido = resultado;
			salida.println(contenido);
			// Cerrar el objeto salida, el objeto bw y el fw
			salida.close();
			bw.close();
			fw.close();
			System.out.println("Información guardada en fichero");
		} catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		return (contenido);
	}
	
	// Genera FicheroConsultasRecibos, aquí se guardan los datos de todos los recibos para poder generar el PDF
	public String EscrituraTablaPdfRecibo(String resultado)
	{
		String contenido = "";
		try
		{
			// Destino de los datos
			FileWriter fw = new FileWriter("FicheroConsultasRecibos.txt", false);
			// Buffer de escritura
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto para la escritura
			PrintWriter salida = new PrintWriter(bw);
			contenido = resultado;
			salida.println(contenido);
			// Cerrar el objeto salida, el objeto bw y el fw
			salida.close();
			bw.close();
			fw.close();
			System.out.println("Información guardada en fichero");
		} catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		return (contenido);
	}
	
	
	// TABLA PRODUCTOS
	
	//Comprobar alta tabla Productos, usado en Clase AltaClientes
		public int insertarProducto(String sentencia)
		{
			int resultado = 0; //Éxito
			try
			{
				//Crear una sentencia
				statement = connection.createStatement();
				//Ejecutar el INSERT
				statement.executeUpdate(sentencia);
			}
			catch (SQLException sqle)
			{
				resultado = -1; //Error
			}	
			return(resultado);
		}
		
		
		// Consulta la tabla Productos para poder mostrarlo en el TextArea usado en Clase ConsultaProductos
		public String consultarProductos()
		{
			String contenido = " ";
			ResultSet rs = null;
			try
			{
				// Crear una sentencia
				statement = connection.createStatement();
				// Crear un objeto ResultSet para guardar lo obtenido
				// Y ejecutar la sentencia SQL
				rs = statement.executeQuery("SELECT * FROM productos");
				while(rs.next()) // Si hay, al menos uno
				{
					contenido = contenido + rs.getInt("idProducto") +
							"-" + rs.getString("codigoProducto") +
							"-" + rs.getInt("IVAProducto") +
							"-" + rs.getString("descripcionProducto") +
							"-" + rs.getInt("cantidadAlmacenProducto") + 
							"-" + rs.getInt("descuentoProducto") +
							"-" + rs.getDouble("precioVentaProducto") +
							"-" + rs.getDouble("precioCompraProducto") +
							"-" + rs.getString("medidasProducto") + "\n";
				}
			}
			catch (SQLException sqle)
			{}	
			return(contenido);
		}
		
			
		
		// Consultamos todos los datos de la tabla productos
		public ResultSet selectProducto()
		{
			ResultSet rs = null;
			try
			{
				// Crear una sentencia
				statement = connection.createStatement();
				// Crear un objeto ResultSet para guardar lo obtenido
				// y ejecutar la sequencia SQL
				rs = statement.executeQuery("SELECT * FROM productos");
			}
			catch (SQLException sqle)
			{}	
			return (rs);
		}
		
		//	Consulta la tabla productos para poder rellenar el Choice usado en la Clase ModificacionProducto
		public ResultSet consultarProducto(String idProducto)
		{
			ResultSet rs = null;
			try
			{
				//Crear una sentencia
				statement = connection.createStatement();
				//	Crear un objeto ResultSet para guardar lo obtenido
				//	Y ejecutar la sentencia SQL
				rs = statement.executeQuery("SELECT * FROM productos WHERE idProducto = " + idProducto);
			}
			catch (SQLException sqle)
			{}	
			return(rs);
		}
		

		//	Hace el UPDATE y comprueba si se ha realizado correctamente o no en la Clase ModificacionProducto
		public int actualizarProducto(String idProducto, String codigoNuevo, 
				String IVANuevo, String descripcionNuevo, 
				String cantidadAlmacenNuevo, String descuentoNuevo, 
				String precioVentaNuevo, String precioCompraNuevo, String medidasNuevo)
		{
			int resultado = 0;
			String sentencia = "UPDATE productos SET codigoProducto = '"+codigoNuevo+"', IVAProducto = '"+IVANuevo+"', "
					+ "descripcionProducto = '"+descripcionNuevo+"', cantidadAlmacenProducto = '"+cantidadAlmacenNuevo+"',"
					+ "descuentoProducto = '"+descuentoNuevo+"', precioVentaProducto = '"+precioVentaNuevo+"', "
					+ "precioCompraProducto = '"+precioCompraNuevo+"', "+ "medidasProducto = '"+medidasNuevo+"' WHERE idProducto = " + idProducto;         
			try
			{
				// Crear una sentencia
				statement = connection.createStatement();
				// Ejecutar el UPDATE
				statement.executeUpdate(sentencia);
			}
			catch (SQLException sqle)
			{
				resultado = -1; // Error
			}
			// Devolvemos un 0 --> Modificación con éxito
			// Devolvemos un -1 --> Modificación error
			return (resultado);		
		}
		
		// Genera FicheroConsultasClientes, aquí se guardan los datos de todos los productos para poder generar el PDF
		public String EscrituraTablaPdfProductos(String resultado)
		{
			String contenido = "";
			try
			{
				// Destino de los datos
				FileWriter fw = new FileWriter("FicheroConsultasProductos.txt", true);
				// Buffer de escritura
				BufferedWriter bw = new BufferedWriter(fw);
				// Objeto para la escritura
				PrintWriter salida = new PrintWriter(bw);
				contenido = resultado;
				salida.println(contenido);
				// Cerrar el objeto salida, el objeto bw y el fw
				salida.close();
				bw.close();
				fw.close();
				System.out.println("Información guardada en fichero");
			} catch (IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
			return (contenido);
		}
		
		// Desconectar a la BD
		public void desconectar()
		{
			try
			{
				if(connection!=null)
				{
					connection.close();
				}
			}
			catch (SQLException e)
			{}
		}
		
}
	