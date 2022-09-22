package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Cliente;
import Clases.ClienteWSResult;
import Clases.Fecha;
import DAL.ClienteDAL;

public class ClienteBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientes()throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	    	boolean bool = localClienteDAL.BorrarClientes();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: " + localException.getMessage());
	    	}
	    	return false;
	    }
	}
	  
	public boolean BorrarClientesPor(int id)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	    	boolean bool = localClienteDAL.BorrarClientePor(id);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por rowId: " + localException.getMessage());
	    	}
	    	return false;
	    }
	}
	  
	public boolean BorrarClientesPorClienteId(int clienteId)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	      boolean bool = localClienteDAL.BorrarClientePorClienteId(clienteId);
	      return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por clienteId: " + localException.getMessage());
	    	}
	    	return false;
	    }
	}
	  
	public long InsertarCliente(ArrayList<ClienteWSResult> clientes, Fecha fecha) throws Exception
	{
	    try
	    {
	    	long lng = new ClienteDAL().InsertarCliente(clientes, fecha);
	    	return lng;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	}

	public long InsertarCliente(int clienteId, String codigo, String nombres, String paterno, String materno,
								String apellidoCasada, String nombreCompleto, boolean tieneCi, String ci, String expedidoId, boolean tieneCelular,
								String celular, int tipoCalleId, String direccion, String numero, String referencia, int entreTipoCalle1Id,
								String calle1, int entreTipoCalle2Id, String calle2, String edificio, String edificioPiso, String edificioNumero,
								String manzano, String uv, String nombreFactura, String nit, String razonSocial, String contacto, boolean tieneTelefono,
								String telefono, String paginaWeb, String email, int tipoNegocioId, int zonaId,
								double latitud, double longitud, int creadorId, double latitudCreador, double longitudCreador, int tipoPagoId,
								int diasPago, float topeCredito, int dia, int mes, int anio, int hora, int minuto, boolean verificado,
								boolean completo, boolean sincronizacion,int rutaId,int diaRutaId,int mercadoId,boolean activo,boolean editado,
								int tatId,String tipoNit,boolean clienteVisita,int zonaMercadoId,int a,int b,int c,int d,int e,int f,
								int g,int h,int i,int j,int k,int l,int m,int n,int o,int p,int q,int r,float secuenciaPreventa,float secuenciaVenta,
								int provinciaId,int precioListaNombreId,String ruta,String tipoVisita,int zonaVentaId, int canalRutaId) throws Exception
	{
		try
		{
			long lng = new ClienteDAL().InsertarCliente(clienteId,codigo,nombres,paterno,materno,apellidoCasada,nombreCompleto,
					tieneCi,ci,expedidoId,tieneCelular,celular,tipoCalleId,direccion,numero,referencia,entreTipoCalle1Id,
					calle1,entreTipoCalle2Id,calle2,edificio,edificioPiso,edificioNumero,manzano,uv,nombreFactura,
					nit,razonSocial,contacto,tieneTelefono,telefono,paginaWeb,email,tipoNegocioId,zonaId,
					latitud,longitud,creadorId,latitudCreador,longitudCreador,tipoPagoId,diasPago,topeCredito,dia,mes,anio,
					hora,minuto,verificado,completo,sincronizacion,rutaId,diaRutaId,mercadoId,activo,editado,tatId,tipoNit,
					clienteVisita,zonaMercadoId,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,secuenciaPreventa,secuenciaVenta,
					provinciaId,precioListaNombreId,ruta,tipoVisita,zonaVentaId,canalRutaId);
			return lng;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: " + localException.getMessage());
			}
			return 0;
		}
	}
	  
	public long ModificarSincronizacionCliente(int id, int clienteId, boolean sincronizacion)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	    	long l = localClienteDAL.ModificarSincronizacionCliente(id,clienteId,sincronizacion);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	}
	
	public long ModificarSincronizacionClienteEditadoPorClienteId(int clienteId, boolean sincronizacion,
															boolean editado)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	    	long l = localClienteDAL.ModificarSincronizacionClienteEditadoPorClienteId(clienteId,sincronizacion,editado);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	}
	  
	public Cliente ObtenerClientePor(int clienteId)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    Cursor localCursor;
	    Cliente localCliente=null;
	    try
	    {
	    	localCursor = localClienteDAL.ObtenerClientePor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por clienteId: " + localException.getMessage());
	    	}
	    	return null;
	    }
	    
	    if(localCursor != null)
	    {
	    	localCliente = new Cliente(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2), 
	    			localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),localCursor.getString(6),
	    			localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9),
	    			localCursor.getString(10),localCursor.getString(11).equals("1")?true:false,localCursor.getString(12),
	    			localCursor.getInt(13),localCursor.getString(14),localCursor.getString(15),localCursor.getString(16),
	    			localCursor.getInt(17),localCursor.getString(18),localCursor.getInt(19),localCursor.getString(20),
	    			localCursor.getString(21),localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),
	    			localCursor.getString(25),localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),
	    			localCursor.getString(29),localCursor.getString(30).equals("1")?true:false,localCursor.getString(31),
	    			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	    			localCursor.getDouble(36),localCursor.getDouble(37),localCursor.getInt(38),localCursor.getDouble(39),
	    			localCursor.getDouble(40),localCursor.getInt(41),localCursor.getInt(42),localCursor.getFloat(43),
	    			localCursor.getInt(44),localCursor.getInt(45),localCursor.getInt(46),localCursor.getInt(47),
	    			localCursor.getInt(48),localCursor.getString(49).equals("1")?true:false,localCursor.getString(50).equals("1")?true:false,
	    			localCursor.getString(51).equals("1")?true:false,localCursor.getInt(52),localCursor.getInt(53),
	    			localCursor.getInt(54),localCursor.getString(55).equals("1")?true:false,localCursor.getString(56).equals("1")?true:false,
	    			localCursor.getInt(57),localCursor.getString(58),localCursor.getString(59).equals("1")?true:false,
	    			localCursor.getInt(60),localCursor.getInt(61),localCursor.getInt(62),localCursor.getInt(63),localCursor.getInt(64),
	    			localCursor.getInt(65),localCursor.getInt(66),localCursor.getInt(67),localCursor.getInt(68),localCursor.getInt(69),localCursor.getInt(70),
	    			localCursor.getInt(71),localCursor.getInt(72),localCursor.getInt(73),localCursor.getInt(74),localCursor.getInt(75),localCursor.getInt(76),
	    			localCursor.getInt(77),localCursor.getInt(78),localCursor.getInt(79),localCursor.getInt(80),localCursor.getInt(81),localCursor.getInt(82),
	    			localCursor.getString(83),localCursor.getString(84),localCursor.getInt(85),localCursor.getInt(86));
	    }
	   
	    return localCliente;
	  }
	  
	public Cliente ObtenerClientePorRowId(int id)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    Cursor localCursor = null;
	    Cliente localCliente = null;
	    
	    try
	    {
	    	localCursor = localClienteDAL.ObtenerClientePorRowId(id);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por rowId: " + localException.getMessage());
	    	}
	    }
	    
	    if(localCursor != null)
	    {
	    	localCliente = new Cliente(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2), 
	    			localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),localCursor.getString(6),
	    			localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9),
	    			localCursor.getString(10),localCursor.getString(11).equals("1")?true:false,localCursor.getString(12),
	    			localCursor.getInt(13),localCursor.getString(14),localCursor.getString(15),localCursor.getString(16),
	    			localCursor.getInt(17),localCursor.getString(18),localCursor.getInt(19),localCursor.getString(20),
	    			localCursor.getString(21),localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),
	    			localCursor.getString(25),localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),
	    			localCursor.getString(29),localCursor.getString(30).equals("1")?true:false,localCursor.getString(31),
	    			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	    			localCursor.getDouble(36),localCursor.getDouble(37),
	    			localCursor.getInt(38),localCursor.getDouble(39),localCursor.getDouble(40),localCursor.getInt(41),
	    			localCursor.getInt(42),localCursor.getFloat(43),localCursor.getInt(44),localCursor.getInt(45),
	    			localCursor.getInt(46),localCursor.getInt(47),localCursor.getInt(48),
	    			localCursor.getString(49).equals("1")?true:false,localCursor.getString(50).equals("1")?true:false,
	    			localCursor.getString(51).equals("1")?true:false,localCursor.getInt(52),localCursor.getInt(53),
	    			localCursor.getInt(54),localCursor.getString(55).equals("1")?true:false,
	    	    	localCursor.getString(56).equals("1")?true:false,localCursor.getInt(57),localCursor.getString(58),
	    	    	localCursor.getString(59).equals("1")?true:false,localCursor.getInt(60),localCursor.getInt(61),
	    	    	localCursor.getInt(62),localCursor.getInt(63),localCursor.getInt(64),localCursor.getInt(65),
	    	    	localCursor.getInt(66),localCursor.getInt(67),localCursor.getInt(68),localCursor.getInt(69),localCursor.getInt(70),
	    			localCursor.getInt(71),localCursor.getInt(72),localCursor.getInt(73),localCursor.getInt(74),localCursor.getInt(75),localCursor.getInt(76),
	    			localCursor.getInt(77),localCursor.getInt(78),localCursor.getInt(79),localCursor.getInt(80),localCursor.getInt(81),localCursor.getInt(82),
	    			localCursor.getString(83),localCursor.getString(84),localCursor.getInt(85),localCursor.getInt(86));
	    }
	    
	    return localCliente;
	}
	  
	public ArrayList<Cliente> ObtenerClientes()throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    Cursor localCursor =null;
	    Cliente localCliente = null;
	    ArrayList<Cliente> listadoClientes = null;
	    try
	    {
	    	localCursor = localClienteDAL.ObtenerClientes();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes: " + localException.getMessage());
	    	}
	    	return null;
	    }
	    
	    if(localCursor.getCount()>0)
	    {
	    	listadoClientes = new ArrayList<Cliente>();
	    	do
	        {
	          localCliente = new Cliente(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2), 
		    			localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),localCursor.getString(6),
		    			localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9),
		    			localCursor.getString(10),localCursor.getString(11).equals("1")?true:false,localCursor.getString(12),
		    			localCursor.getInt(13),localCursor.getString(14),localCursor.getString(15),localCursor.getString(16),
		    			localCursor.getInt(17),localCursor.getString(18),localCursor.getInt(19),localCursor.getString(20),
		    			localCursor.getString(21),localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),
		    			localCursor.getString(25),localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),
		    			localCursor.getString(29),localCursor.getString(30).equals("1")?true:false,localCursor.getString(31),
		    			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
		    			localCursor.getDouble(36),localCursor.getDouble(37),
		    			localCursor.getInt(38),localCursor.getDouble(39),localCursor.getDouble(40),localCursor.getInt(41),
		    			localCursor.getInt(42),localCursor.getFloat(43),localCursor.getInt(44),localCursor.getInt(45),
		    			localCursor.getInt(46),localCursor.getInt(47),localCursor.getInt(48),
		    			localCursor.getString(49).equals("1")?true:false,localCursor.getString(50).equals("1")?true:false,
		    			localCursor.getString(51).equals("1")?true:false,localCursor.getInt(52),localCursor.getInt(53),
		    			localCursor.getInt(54),localCursor.getString(55).equals("1")?true:false,
		    	    	localCursor.getString(56).equals("1")?true:false,localCursor.getInt(57),localCursor.getString(58),
		    	    	localCursor.getString(59).equals("1")?true:false,localCursor.getInt(60),localCursor.getInt(61),
		    	    	localCursor.getInt(62),localCursor.getInt(63),localCursor.getInt(64),localCursor.getInt(65),
		    	    	localCursor.getInt(66),localCursor.getInt(67),localCursor.getInt(68),localCursor.getInt(69),localCursor.getInt(70),
		    			localCursor.getInt(71),localCursor.getInt(72),localCursor.getInt(73),localCursor.getInt(74),localCursor.getInt(75),localCursor.getInt(76),
		    			localCursor.getInt(77),localCursor.getInt(78),localCursor.getInt(79),localCursor.getInt(80),localCursor.getInt(81),localCursor.getInt(82),
		    			localCursor.getString(83),localCursor.getString(84),localCursor.getInt(85),localCursor.getInt(86));
	          
	          listadoClientes.add(localCliente);
	        } 
	    	while (localCursor.moveToNext());
	    }
	    
	    return listadoClientes;
	  }
	  
	public ArrayList<Cliente> ObtenerClientesNoSincronizados()throws Exception
	{
		ClienteDAL localClienteDAL = new ClienteDAL();
	    Cursor localCursor = null;
	    Cliente localCliente = null;
	    ArrayList<Cliente> listadoClientes = null;

	    try
	    {
	    	localCursor = localClienteDAL.ObtenerClientesNoSincronizados(); 
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: " + localException.getMessage());
	    	}
	    }
	    
	    if(localCursor.getCount()>0)
	    {
	    	listadoClientes = new ArrayList<Cliente>();
	    	do
	    	{
	    		localCliente = new Cliente(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2), 
		    			localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),localCursor.getString(6),
		    			localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9),
		    			localCursor.getString(10),localCursor.getString(11).equals("1")?true:false,localCursor.getString(12),
		    			localCursor.getInt(13),localCursor.getString(14),localCursor.getString(15),localCursor.getString(16),
		    			localCursor.getInt(17),localCursor.getString(18),localCursor.getInt(19),localCursor.getString(20),
		    			localCursor.getString(21),localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),
		    			localCursor.getString(25),localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),
		    			localCursor.getString(29),localCursor.getString(30).equals("1")?true:false,localCursor.getString(31),
		    			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
		    			localCursor.getDouble(36),localCursor.getDouble(37),
		    			localCursor.getInt(38),localCursor.getDouble(39),localCursor.getDouble(40),localCursor.getInt(41),
		    			localCursor.getInt(42),localCursor.getFloat(43),localCursor.getInt(44),localCursor.getInt(45),
		    			localCursor.getInt(46),localCursor.getInt(47),localCursor.getInt(48),
		    			localCursor.getString(49).equals("1")?true:false,localCursor.getString(50).equals("1")?true:false,
		    			localCursor.getString(51).equals("1")?true:false,localCursor.getInt(52),localCursor.getInt(53),
		    			localCursor.getInt(54),localCursor.getString(55).equals("1")?true:false,
		    	    	localCursor.getString(56).equals("1")?true:false,localCursor.getInt(57),localCursor.getString(58),
		    	    	localCursor.getString(59).equals("1")?true:false,localCursor.getInt(60),localCursor.getInt(61),
		    	    	localCursor.getInt(62),localCursor.getInt(63),localCursor.getInt(64),localCursor.getInt(65),
		    	    	localCursor.getInt(66),localCursor.getInt(67),localCursor.getInt(68),localCursor.getInt(69),localCursor.getInt(70),
		    			localCursor.getInt(71),localCursor.getInt(72),localCursor.getInt(73),localCursor.getInt(74),localCursor.getInt(75),localCursor.getInt(76),
		    			localCursor.getInt(77),localCursor.getInt(78),localCursor.getInt(79),localCursor.getInt(80),localCursor.getInt(81),localCursor.getInt(82),
		    			localCursor.getString(83),localCursor.getString(84),localCursor.getInt(85),localCursor.getInt(86));
	    		
	    		listadoClientes.add(localCliente);
	    	}
	    	while(localCursor.moveToNext());
	    }
		
	    return listadoClientes;
	}
	
	public ArrayList<Cliente> ObtenerClientesEditadosNoSincronizados()throws Exception
	{
		ClienteDAL localClienteDAL = new ClienteDAL();
	    Cursor localCursor = null;
	    Cliente localCliente = null;
	    ArrayList<Cliente> listadoClientes = null;

	    try
	    {
	    	localCursor = localClienteDAL.ObtenerClientesEditadosNoSincronizados(); 
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: " + localException.getMessage());
	    	}
	    }
	    
	    if(localCursor.getCount()>0)
	    {
	    	listadoClientes = new ArrayList<Cliente>();
	    	do
	    	{
	    		localCliente = new Cliente(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2), 
		    			localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),localCursor.getString(6),
		    			localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9),
		    			localCursor.getString(10),localCursor.getString(11).equals("1")?true:false,localCursor.getString(12),
		    			localCursor.getInt(13),localCursor.getString(14),localCursor.getString(15),localCursor.getString(16),
		    			localCursor.getInt(17),localCursor.getString(18),localCursor.getInt(19),localCursor.getString(20),
		    			localCursor.getString(21),localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),
		    			localCursor.getString(25),localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),
		    			localCursor.getString(29),localCursor.getString(30).equals("1")?true:false,localCursor.getString(31),
		    			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
		    			localCursor.getDouble(36),localCursor.getDouble(37),
		    			localCursor.getInt(38),localCursor.getDouble(39),localCursor.getDouble(40),localCursor.getInt(41),
		    			localCursor.getInt(42),localCursor.getFloat(43),localCursor.getInt(44),localCursor.getInt(45),
		    			localCursor.getInt(46),localCursor.getInt(47),localCursor.getInt(48),
		    			localCursor.getString(49).equals("1")?true:false,localCursor.getString(50).equals("1")?true:false,
		    			localCursor.getString(51).equals("1")?true:false,localCursor.getInt(52),localCursor.getInt(53),
		    			localCursor.getInt(54),localCursor.getString(55).equals("1")?true:false,
		    	    	localCursor.getString(56).equals("1")?true:false,localCursor.getInt(57),localCursor.getString(58),
		    	    	localCursor.getString(59).equals("1")?true:false,localCursor.getInt(60),localCursor.getInt(61),
		    	    	localCursor.getInt(62),localCursor.getInt(63),localCursor.getInt(64),localCursor.getInt(65),
		    	    	localCursor.getInt(66),localCursor.getInt(67),localCursor.getInt(68),localCursor.getInt(69),localCursor.getInt(70),
		    			localCursor.getInt(71),localCursor.getInt(72),localCursor.getInt(73),localCursor.getInt(74),localCursor.getInt(75),localCursor.getInt(76),
		    			localCursor.getInt(77),localCursor.getInt(78),localCursor.getInt(79),localCursor.getInt(80),localCursor.getInt(81),localCursor.getInt(82),
		    			localCursor.getString(83),localCursor.getString(84),localCursor.getInt(85),localCursor.getInt(86));
	    		
	    		listadoClientes.add(localCliente);
	    	}
	    	while(localCursor.moveToNext());
	    }
		
	    return listadoClientes;
	}

	public long ModificarClienteNombrePorClienteId(int clienteId, String nombres,String paterno,String materno,
													String apellidoCasada,String direccion,String referencia)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	    	long l = localClienteDAL.ModificarClienteNombrePorClienteId(clienteId,nombres,paterno,materno,apellidoCasada,
	    																direccion,referencia);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por clienteId: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	  }
	
	public long ModificarClienteNombrePorId(int Id, String nombres,String paterno,String materno,String apellidoCasada,
											String direccion,String referencia)throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    try
	    {
	    	long l = localClienteDAL.ModificarClienteNombrePorId(Id,nombres,paterno,materno,apellidoCasada,direccion,referencia);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	}
	
	public long ModificarClienteDatosPorClienteId(int clienteId, String nombres,String paterno,String materno,String apellidoCasada,
			String telefono,String celular,String direccion,String referencia,double latitud,double longitud,boolean activo,int tipoNegocioId,
			String ci,int provinciaId,int zonaId,int mercadoId,int zonaMercadoId,int tipoPagoId,int precioListaId,String tipoNit,
			String nombreFactura,String nit,int a,int b,int c,int d,int e,int f,int g,int h,int i,int j,int k,int l,
			int m,int n,int o,int p,int q,int r,float secPreventa,float secVenta)throws Exception
	{
		ClienteDAL localClienteDAL = new ClienteDAL();
		try
		{
			long lng = localClienteDAL.ModificarClienteDatosPorClienteId(clienteId,nombres,paterno,materno,apellidoCasada,telefono,celular,direccion,referencia,
														latitud,longitud,activo,tipoNegocioId,ci,provinciaId,zonaId,mercadoId,zonaMercadoId,tipoPagoId,precioListaId,tipoNit,
														nombreFactura,nit,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,secPreventa,secVenta);
			return lng;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: " + localException.getMessage());
			}
			return 0;
		}
	}
	
	public Cliente ObtenerPrimerCliente()throws Exception
	{
	    ClienteDAL localClienteDAL = new ClienteDAL();
	    Cursor localCursor =null;
	    Cliente localCliente = null;

	    try
	    {
	    	localCursor = localClienteDAL.ObtenerPrimerCliente();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el primer cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el primer cliente: " + localException.getMessage());
	    	}
	    	return null;
	    }
	    
	    if(localCursor.getCount()>0)
	    {
	          localCliente = new Cliente(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2), 
		    			localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),localCursor.getString(6),
		    			localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9),
		    			localCursor.getString(10),localCursor.getString(11).equals("1")?true:false,localCursor.getString(12),
		    			localCursor.getInt(13),localCursor.getString(14),localCursor.getString(15),localCursor.getString(16),
		    			localCursor.getInt(17),localCursor.getString(18),localCursor.getInt(19),localCursor.getString(20),
		    			localCursor.getString(21),localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),
		    			localCursor.getString(25),localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),
		    			localCursor.getString(29),localCursor.getString(30).equals("1")?true:false,localCursor.getString(31),
		    			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
		    			localCursor.getDouble(36),localCursor.getDouble(37),
		    			localCursor.getInt(38),localCursor.getDouble(39),localCursor.getDouble(40),localCursor.getInt(41),
		    			localCursor.getInt(42),localCursor.getFloat(43),localCursor.getInt(44),localCursor.getInt(45),
		    			localCursor.getInt(46),localCursor.getInt(47),localCursor.getInt(48),
		    			localCursor.getString(49).equals("1")?true:false,localCursor.getString(50).equals("1")?true:false,
		    			localCursor.getString(51).equals("1")?true:false,localCursor.getInt(52),localCursor.getInt(53),
		    			localCursor.getInt(54),localCursor.getString(55).equals("1")?true:false,
		    			localCursor.getString(56).equals("1")?true:false,localCursor.getInt(57),localCursor.getString(58),
		    	    	localCursor.getString(59).equals("1")?true:false,localCursor.getInt(60),localCursor.getInt(61),
		    	    	localCursor.getInt(62),localCursor.getInt(63),localCursor.getInt(64),localCursor.getInt(65),
		    	    	localCursor.getInt(66),localCursor.getInt(67),localCursor.getInt(68),localCursor.getInt(69),localCursor.getInt(70),
		    			localCursor.getInt(71),localCursor.getInt(72),localCursor.getInt(73),localCursor.getInt(74),localCursor.getInt(75),localCursor.getInt(76),
		    			localCursor.getInt(77),localCursor.getInt(78),localCursor.getInt(79),localCursor.getInt(80),localCursor.getInt(81),localCursor.getInt(82),
		    			localCursor.getString(83),localCursor.getString(84),localCursor.getInt(85),localCursor.getInt(86));
	    }
	    
	    return localCliente;
	}
}
