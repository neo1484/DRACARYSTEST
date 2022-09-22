package BLL;

import java.util.ArrayList;
import Clases.ClienteNoAtendido;
import DAL.ClienteNoAtendidoDAL;
import android.database.Cursor;

public class ClienteNoAtendidoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesNoAtendidos() throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteNoAtendidoDAL().BorrarClientesNoAtendidos();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes no atendidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes no atendidos: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarClienteNoAtendido(int empleadoId,int clienteId,int motivoId,int dia,int mes,int anio,int hora,
											int minuto,double latitud,double longitud,boolean sincronizacion) 
				throws Exception
	{
		try
		{
			long l = new ClienteNoAtendidoDAL().InsertarClienteNoAtendido(empleadoId, clienteId, motivoId, dia, mes, anio, 
																			hora, minuto, latitud, longitud, sincronizacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente no atendido: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente no atendido: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<ClienteNoAtendido> ObtenerClientesNoAtendidosPor(int empleadoId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteNoAtendido> listadoClienteNoAtendido = null;
	    
	    try
	    {
	    	localCursor = new ClienteNoAtendidoDAL().ObtenerClientesNoAtendidosPor(empleadoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por empleadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por empleadoId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoClienteNoAtendido = new ArrayList<ClienteNoAtendido>();
	        	do
	        	{
	        		ClienteNoAtendido localClienteNoAtendido = new ClienteNoAtendido(localCursor.getInt(0),localCursor.getInt(1),
	        												localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        												localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),
	        												localCursor.getInt(8),localCursor.getDouble(9),localCursor.getDouble(10),
	        												localCursor.getString(11).equals("1")?true:false);
	        		
	        		listadoClienteNoAtendido.add(localClienteNoAtendido);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoClienteNoAtendido;
	}
	
	public ClienteNoAtendido ObtenerClientesNoAtendidosPorClienteId(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    ClienteNoAtendido localClienteNoAtendido = null;
	    
	    try
	    {
	    	localCursor = new ClienteNoAtendidoDAL().ObtenerClientesNoAtendidosPorClienteId(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por clienteId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {

	        		localClienteNoAtendido = new ClienteNoAtendido(localCursor.getInt(0),localCursor.getInt(1),
											localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
											localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),
											localCursor.getInt(8),localCursor.getDouble(9),localCursor.getDouble(10),
											localCursor.getString(11).equals("1")?true:false);
	        }
	    }
	    
	    return localClienteNoAtendido;
	}
	
	public ArrayList<ClienteNoAtendido> ObtenerClientesNoAtendidos() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteNoAtendido> listadoClienteNoAtendido = null;
	    
	    try
	    {
	    	localCursor = new ClienteNoAtendidoDAL().ObtenerClientesNoAtendidos();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoClienteNoAtendido = new ArrayList<ClienteNoAtendido>();
	        	do
	        	{
	        		ClienteNoAtendido localClienteNoAtendido = new ClienteNoAtendido(localCursor.getInt(0),localCursor.getInt(1),
	        												localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        												localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),
	        												localCursor.getInt(8),localCursor.getDouble(9),localCursor.getDouble(10),
	        												localCursor.getString(11).equals("1")?true:false);
	        		
	        		listadoClienteNoAtendido.add(localClienteNoAtendido);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoClienteNoAtendido;
	}
	
	public ArrayList<ClienteNoAtendido> ObtenerClientesNoSincronizados() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteNoAtendido> listadoClienteNoAtendidoNoSincronizado = null;
	    
	    try
	    {
	    	localCursor = new ClienteNoAtendidoDAL().ObtenerClientesNoAtendidosNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos no sincronizados: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoClienteNoAtendidoNoSincronizado = new ArrayList<ClienteNoAtendido>();
	        	do
	        	{
	        		ClienteNoAtendido localClienteNoAtendido = new ClienteNoAtendido(localCursor.getInt(0),localCursor.getInt(1),
	        												localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        												localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),
	        												localCursor.getInt(8),localCursor.getDouble(9),localCursor.getDouble(10),
	        												localCursor.getString(11).equals("1")?true:false);
	        		
	        		listadoClienteNoAtendidoNoSincronizado.add(localClienteNoAtendido);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoClienteNoAtendidoNoSincronizado;
	}

	public long ModificarClienteNoAtendidoSincronizacion(int clienteId) throws Exception
	{
	    try
	    {
	    	long i = new ClienteNoAtendidoDAL().ModificarClienteNOAtendidoSincronizacion(clienteId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteNoAtendido no sincronizado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la clienteNoAtendido no sincronizado: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarClienteNoAtendidoSincronizacionDesdeVendedor(int id,int clienteId,boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	long i = new ClienteNoAtendidoDAL().ModificarClienteNOAtendidoSincronizacionDesdeVendedor(id, clienteId, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteNoAtendido no sincronizado desde vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la clienteNoAtendido no sincronizado desde vendedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
