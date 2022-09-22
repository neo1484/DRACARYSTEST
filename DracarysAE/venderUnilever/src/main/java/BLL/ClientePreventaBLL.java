package BLL;

import java.util.ArrayList;
import android.database.Cursor;
import Clases.ApkClienteWS;
import Clases.ClientePreventa;
import DAL.ClientePreventaDAL;

public class ClientePreventaBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarClientesPreventa() throws Exception
	{
		try
		{
			boolean bool = new ClientePreventaDAL().BorrarClientesPreventa();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes preventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public boolean BorrarClientesSinPreventa() throws Exception
	{
		try
		{
			boolean bool = new ClientePreventaDAL().BorrarClientesSinPreventa();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes sin preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes sin preventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
		  
	public long InsertarClientePreventa(ArrayList<ApkClienteWS> clientesPreventa) throws Exception
	{
		try
		{
			long l = new ClientePreventaDAL().InsertarClientePreventa(clientesPreventa);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes preventa: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}

	public long InsertarClientePreventa(int clienteId, String codigo, int clienteTipoNegocioId,
										String nombreCompleto,double latitud, double longitud, String direccion, String telefono, String celular,
										int precioListaId,float descuento, int tipoPagoId, int diasPago, float topeCredito, int rutaId,
										String rutaDescripcion,String razonSocial, boolean autoventa, boolean estadoAtendido,
										boolean clientePunteoSincronizado,boolean sincronizacion,String nombres,String paterno,String materno,
										String apellidoCasada,int tipoNegocioId,int zonaId,String nombreFactura,String nit,int diaId,String referencia,
										float promedioVenta,String ci,String contacto,float montoPendienteCredito,int provinciaId,int precioListaNombreId,
										String ruta,String tipoVisita,int zonaVentaId, String zona, String mercado, int canalRutaId, String observacion,
										boolean verificadoFoto) throws Exception
	{
		try
		{
			long l = new ClientePreventaDAL().InsertarClientePreventa(clienteId,codigo,clienteTipoNegocioId,nombreCompleto,
					latitud,longitud,direccion,telefono,celular,precioListaId,descuento,tipoPagoId,diasPago,topeCredito,rutaId,
					rutaDescripcion,razonSocial,autoventa,estadoAtendido,clientePunteoSincronizado,sincronizacion,nombres,paterno,
					materno,apellidoCasada,tipoNegocioId,zonaId,nombreFactura,nit,diaId,referencia,promedioVenta,ci,contacto,
					montoPendienteCredito,provinciaId,ruta,tipoVisita,zonaVentaId, zona, mercado, canalRutaId, observacion, verificadoFoto);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente preventa: " + localException.getMessage());
			}
			throw localException;
		}
	}
		  
	public ClientePreventa ObtenerClientePreventaPor(int clienteId) throws Exception
	{
		Cursor localCursor;
		ClientePreventa localClientePreventa = null;
		try
		{
			localCursor = new ClientePreventaDAL().ObtenerClientePreventaPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente preventa por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente preventa por clienteId: " + localException.getMessage());
	    	}
		    throw localException;
	    }
		
		if (localCursor.getCount() > 0) 
		{
			localClientePreventa = new ClientePreventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
					localCursor.getInt(3), localCursor.getString(4), localCursor.getDouble(5), 
					localCursor.getDouble(6), localCursor.getString(7), localCursor.getString(8), localCursor.getString(9), 
					localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13), 
					localCursor.getInt(14), localCursor.getInt(15), localCursor.getString(16), localCursor.getString(17), 
					localCursor.getString(18).equals("1")?true:false,localCursor.getString(19).equals("1")?true:false,
					localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
					localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getString(25),
					localCursor.getInt(26),localCursor.getInt(27),localCursor.getString(28),localCursor.getString(29),
					localCursor.getInt(30),localCursor.getString(31),localCursor.getFloat(32),localCursor.getString(33),
					localCursor.getString(34),localCursor.getFloat(35),localCursor.getInt(36),localCursor.getString(37),
					localCursor.getString(38),localCursor.getInt(39), localCursor.getString(40), localCursor.getString(41),
					localCursor.getInt(42), localCursor.getString(43), localCursor.getString(44).equals("1")?true:false);
		}
		return localClientePreventa;
	}
		  
	public ArrayList<ClientePreventa> ObtenerClientesPreventa() throws Exception
	{
		Cursor localCursor;
		ArrayList<ClientePreventa> listadoClientePreventa = null;
		try
		{
			localCursor = new ClientePreventaDAL().ObtenerClientesPreventa();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa por clienteId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoClientePreventa = new ArrayList<ClientePreventa>();
			do
			{
				ClientePreventa clientePreventa = new ClientePreventa(localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2),localCursor.getInt(3), localCursor.getString(4), localCursor.getDouble(5), 
						localCursor.getDouble(6), localCursor.getString(7), localCursor.getString(8), localCursor.getString(9), 
						localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13), 
						localCursor.getInt(14), localCursor.getInt(15), localCursor.getString(16), localCursor.getString(17), 
						localCursor.getString(18).equals("1")?true:false,localCursor.getString(19).equals("1")?true:false,
						localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
						localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getString(25),
						localCursor.getInt(26),localCursor.getInt(27),localCursor.getString(28),localCursor.getString(29),
						localCursor.getInt(30),localCursor.getString(31),localCursor.getFloat(32),localCursor.getString(33),
						localCursor.getString(34),localCursor.getFloat(35),localCursor.getInt(36),localCursor.getString(37),
						localCursor.getString(38),localCursor.getInt(39), localCursor.getString(40), localCursor.getString(41),
						localCursor.getInt(42), localCursor.getString(43), localCursor.getString(44).equals("1")?true:false);
				
				listadoClientePreventa.add(clientePreventa);
			}
			while(localCursor.moveToNext());
		}
		return listadoClientePreventa;
	}
	
	public int obtenerClientesPreventaNoAtendidos() throws Exception
	{
		Cursor localCursor;
		int clientesNoAtendidos = 0;
		
		try
		{
			localCursor = new ClientePreventaDAL().ObtenerClientesPreventa();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			clientesNoAtendidos = localCursor.getInt(0);
		}
		
		return clientesNoAtendidos;
	}

	public long ModificarClientePreventaEstadoAtencion(int clienteId, boolean estadoAtencion) throws Exception
	{
	    try
	    {
	    	long l = new ClientePreventaDAL().ModificarClientePreventaEstadoAtencion(clienteId, estadoAtencion);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarClientePreventaSincronizacionDesdeVendedor(int id,int clienteId, boolean clientePunteoSincronizado) throws Exception
	{
	    try
	    {
	    	long l = new ClientePreventaDAL().ModificarClientePreventaSincronizacionDesdeVendedor(id, clienteId, clientePunteoSincronizado);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa desde el vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clientePreventa desde el vendedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarClientePreventaNombre(int clienteId,String nombres,String paterno,String materno,
											String apellidoCasada,String direccion,String referencia) throws Exception
	{
	    try
	    {
	    	long l = new ClientePreventaDAL().ModificarClientePreventaNombre(clienteId, nombres, paterno, materno, 
	    															apellidoCasada,direccion,referencia);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarClientePreventaMontoCredito(int clienteId,float montoFinal) throws Exception
	{
		try
		{
			long l = new ClientePreventaDAL().ModificarClientePreventaMontoCredito(clienteId, montoFinal);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	
	public long ModificarClientePreventaDatos(int clienteId,String nombres,String paterno,String materno,
			String apellidoCasada,String direccion,String referencia,double latitud,double longitud,
			int tipoNegocioId,String telefono,String celular) throws Exception
	{
		try
		{
			long l = new ClientePreventaDAL().ModificarClientePreventaDatos(clienteId, nombres, paterno, materno, 
										apellidoCasada,direccion,referencia,latitud,longitud,tipoNegocioId,telefono,celular);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	
	public long ModificarClientePreventaNombreSincronizacion(int clienteId,boolean estadoSincronizacion) throws Exception
	{
	    try
	    {
	    	long l = new ClientePreventaDAL().ModificarClientePreventaNombreSincronizacion(clienteId, estadoSincronizacion);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del nombre del clientePreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del nombre del clientePreventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public ArrayList<ClientePreventa> ObtenerClientesPreventaNoSincronizados() throws Exception
	{
		Cursor localCursor;
		ArrayList<ClientePreventa> listadoClientePreventa = null;
		try
		{
			localCursor = new ClientePreventaDAL().ObtenerClientesPreventaNoSincronizados();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no sincronizados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no sincronizados: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoClientePreventa = new ArrayList<ClientePreventa>();
			do
			{
				ClientePreventa clientePreventa = new ClientePreventa(localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2),localCursor.getInt(3), localCursor.getString(4), localCursor.getDouble(5), 
						localCursor.getDouble(6), localCursor.getString(7), localCursor.getString(8), localCursor.getString(9), 
						localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13), 
						localCursor.getInt(14), localCursor.getInt(15), localCursor.getString(16), localCursor.getString(17), 
						localCursor.getString(18).equals("1")?true:false,localCursor.getString(19).equals("1")?true:false,
						localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
						localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getString(25),
						localCursor.getInt(26),localCursor.getInt(27),localCursor.getString(28),localCursor.getString(29),
						localCursor.getInt(30),localCursor.getString(31),localCursor.getFloat(32),localCursor.getString(33),
						localCursor.getString(34),localCursor.getFloat(35),localCursor.getInt(36),localCursor.getString(37),
						localCursor.getString(38),localCursor.getInt(39), localCursor.getString(40), localCursor.getString(41),
						localCursor.getInt(42), localCursor.getString(43) ,localCursor.getString(44).equals("1")?true:false);
				
				listadoClientePreventa.add(clientePreventa);
			}
			while(localCursor.moveToNext());
		}
		return listadoClientePreventa;
	}
	
	public long ModificarClienteDatosCenso(int clienteId,String codigo,String referencia,int tipoNegocioId,String contacto,
			double latitud,double longitud) throws Exception
	{
	    try
	    {
	    	long l = new ClientePreventaDAL().ModificarClienteDatosCenso(clienteId, codigo, referencia, tipoNegocioId, contacto, latitud, longitud);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del clientePreventa desde censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del clientePreventa desde censo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public int ObtenerNroClientesPreventa() throws Exception
	{
		Cursor localCursor;
		int nroClientes = 0;
		try
		{
			localCursor = new ClientePreventaDAL().obtenerNroClientesPreventa();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes preventa: " + localException.getMessage());
	    	}
		    throw localException;
	    }
		
		if (localCursor.getCount() > 0) 
		{
			nroClientes = localCursor.getInt(0);
		}
		return nroClientes;
	}
}
