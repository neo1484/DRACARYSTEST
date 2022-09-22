package BLL;

import java.util.ArrayList;

import Clases.ClienteNroFoto;
import Clases.ClienteNroFotoWSResult;
import DAL.ClienteNroFotoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ClienteNroFotoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesNroFoto() throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteNroFotoDAL().BorrarClientesNroFoto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNroFoto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNroFoto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  	  
	public long InsertarClienteNroFoto(ArrayList<ClienteNroFotoWSResult>  clientesNroFoto) throws Exception
	{
		try
		{
			long l = new ClienteNroFotoDAL().InsertarClienteNroFoto(clientesNroFoto);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNroFoto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNroFoto: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ClienteNroFoto ObtenerClienteNroFotoPor(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    ClienteNroFoto clienteNroFoto = null;
	    
	    try
	    {
	    	localCursor = new ClienteNroFotoDAL().ObtenerClienteNroFotoPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNroFoto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNroFoto: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null && localCursor.getCount() > 0)
	    {
	        clienteNroFoto = new ClienteNroFoto(localCursor.getInt(1),localCursor.getInt(2));
	    }
	    
	    return clienteNroFoto;
	}
	
	public ArrayList<ClienteNroFoto> ObtenerClientesNroFoto() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<ClienteNroFoto> listadoClienteNroFoto = null;
	    
	    try
	    {
	    	localCursor = new ClienteNroFotoDAL().ObtenerClientesNroFoto();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos de los nits del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos de los nits del cliente: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null && localCursor.getCount() > 0)
	    {
        	listadoClienteNroFoto = new ArrayList<ClienteNroFoto>();
        	do
        	{
        		ClienteNroFoto localClienteNroFoto = new ClienteNroFoto(localCursor.getInt(1),localCursor.getInt(2));
        		
        		listadoClienteNroFoto.add(localClienteNroFoto);
        	} 
        	while (localCursor.moveToNext());
	        
	    }
	    
	    return listadoClienteNroFoto;
	}
}