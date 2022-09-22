package BLL;

import java.util.ArrayList;
import Clases.ClienteNit;
import Clases.ClienteNitWSResult;
import Clases.LoginEmpleado;
import DAL.ClienteNitDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ClienteNitBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesNit() throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteNitDAL().BorrarClientesNit();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNit: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNit: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarClientesNitPor(int clienteId) throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteNitDAL().BorrarClientesNit();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNit por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNit por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarClienteNit(ArrayList<ClienteNitWSResult> clientesNit, LoginEmpleado loginEmpleado) throws Exception
	{
		try
		{
			long l = new ClienteNitDAL().InsertarClienteNit(clientesNit, loginEmpleado);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los nits de los clientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los nits de los clientes: " + localException.getMessage());
			} 
			throw localException;
		}
	}

	public long InsertarClienteNit(int clienteId, String nombreFactura, String nit, int empleadoId, int dia, int mes,
								   int anio,boolean sincronizacion,String tipoNit) throws Exception
	{
		try
		{
			long l = new ClienteNitDAL().InsertarClienteNit(clienteId, nombreFactura, nit, empleadoId, dia, mes, anio,sincronizacion,tipoNit);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNit: " + localException.getMessage());
			}
			throw localException;
		}
	}
	
	public long ModificarClienteNit(int clienteId,String nit,String tipoNit) throws Exception
	{
	    try
	    {
	    	long i = new ClienteNitDAL().ModificarClienteNit(clienteId, nit, tipoNit);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nit del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nit del cliente: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarClienteNitSincro(int clienteId,int newClienteId) throws Exception
	{
	    try
	    {
	    	long i = new ClienteNitDAL().ModificarClienteNitSincro(clienteId, newClienteId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId del cliente: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	   
	public ArrayList<ClienteNit> ObtenerClienteNitPor(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteNit> listadoClienteNit = null;
	    
	    try
	    {
	    	localCursor = new ClienteNitDAL().ObtenerClienteNitPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNit: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNit: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoClienteNit = new ArrayList<ClienteNit>();
	        	do
	        	{
	        		ClienteNit localClienteNit = new ClienteNit(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2),
	        				localCursor.getString(3),localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),
	        				localCursor.getString(8).equals("1")?true:false,localCursor.getString(9));
	        		
	        		listadoClienteNit.add(localClienteNit);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoClienteNit;
	}
	
	public ArrayList<ClienteNit> ObtenerClienteNitsNoSincronizados() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteNit> listadoClienteNit = null;
	    
	    try
	    {
	    	localCursor = new ClienteNitDAL().ObtenerClienteNitsNoSincronizados();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los nits del cliente no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los nits del cliente no sincronizados: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoClienteNit = new ArrayList<ClienteNit>();
	        	do
	        	{
	        		ClienteNit localClienteNit = new ClienteNit(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2),
	        				localCursor.getString(3),localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),
	        				localCursor.getInt(7),localCursor.getString(8).equals("1")?true:false,localCursor.getString(9));
	        		
	        		listadoClienteNit.add(localClienteNit);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoClienteNit;
	}
}
