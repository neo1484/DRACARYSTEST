package BLL;

import java.util.ArrayList;

import Clases.ClienteEncuestado;
import Clases.ClienteEncuestadoWSResult;
import DAL.ClienteEncuestadoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ClienteEncuestadoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesEncuestado() throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteEncuestadoDAL().BorrarClienteEncuestado();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes encuestados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes encuestados: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarClienteEncuestado(ArrayList<ClienteEncuestadoWSResult> clientesEncuestado) throws Exception
	{
		try
		{
			long l = new ClienteEncuestadoDAL().InsertarClienteEncuestado(clientesEncuestado);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes encuestados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes encuestados: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<ClienteEncuestado> ObtenerClientesEncuestado() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteEncuestado> listadoClienteEncuestado = null;
	    
	    try
	    {
	    	localCursor = new ClienteEncuestadoDAL().ObtenerClientesEncuestado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes encuestados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes encuestados: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoClienteEncuestado = new ArrayList<ClienteEncuestado>();
	        	do
	        	{
	        		ClienteEncuestado localClienteEncuestado = new ClienteEncuestado(localCursor.getInt(1),localCursor.getInt(2));
	        		
	        		listadoClienteEncuestado.add(localClienteEncuestado);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoClienteEncuestado;
	}
	
	public ArrayList<ClienteEncuestado> ObtenerClienteEncuestadoPor(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteEncuestado> listadoClienteEncuestado = null;
	    
	    try
	    {
	    	localCursor = new ClienteEncuestadoDAL().ObtenerClienteEncuestadoPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente encuestado por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente encuestado por clienteId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null && localCursor.getCount() > 0)
	    {
	    	listadoClienteEncuestado = new ArrayList<ClienteEncuestado>();
	    	do
	    	{
	    		ClienteEncuestado clienteEncuestado = new ClienteEncuestado(localCursor.getInt(1),localCursor.getInt(2));
	    		listadoClienteEncuestado.add(clienteEncuestado);
	    	}
	    	while(localCursor.moveToNext());
	    }
	    
	    return listadoClienteEncuestado;
	}
}
