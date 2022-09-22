package BLL;

import java.util.ArrayList;

import Clases.ClienteCobranza;
import DAL.ClienteCobranzaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ClienteCobranzaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarClientesCobranza() throws Exception
	{
		try
		{
			boolean bool = new ClienteCobranzaDAL().BorrarClientesCobranza();
		    return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes cobranza: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes cobranza: " + localException.getMessage());
			} 
		    throw localException;
    	}
	}
		  
	public long InsertarClienteCobranza(SoapObject clienteCobranza) throws Exception
	{
		try
		{
			long l = new ClienteCobranzaDAL().InsertarClienteCobranza(clienteCobranza);
			return l;
    	}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes de cobranza: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes de cobranza: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public ArrayList<ClienteCobranza> ObtenerClienteCobranzaPor(int clienteId) throws Exception
	{
		Cursor localCursor;
		ArrayList<ClienteCobranza> listadoClienteCobranza = null;
		try
		{ 
			localCursor = new ClienteCobranzaDAL().ObtenerClientesCobranzaPor(clienteId);  
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza por clienteId: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoClienteCobranza = new ArrayList<ClienteCobranza>();
			
			do
			{
				ClienteCobranza clienteCobranza = new ClienteCobranza(localCursor.getInt(0),localCursor.getInt(1), localCursor.getString(2),localCursor.getString(3), 
																	localCursor.getFloat(4),localCursor.getFloat(5),localCursor.getString(6),localCursor.getInt(7),
																	localCursor.getDouble(8),localCursor.getDouble(9),localCursor.getInt(10));
				
				listadoClienteCobranza.add(clienteCobranza);
			}
			while(localCursor.moveToNext());
		}
		return listadoClienteCobranza;
	}

	public ArrayList<ClienteCobranza> ObtenerClientesCobranza() throws Exception
	{
		Cursor localCursor;
		ArrayList<ClienteCobranza> listadoClienteCobranza = null;
		try
		{ 
			localCursor = new ClienteCobranzaDAL().ObtenerClientesCobranza();  
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoClienteCobranza = new ArrayList<ClienteCobranza>();
			
			do
			{
				ClienteCobranza clienteCobranza = new ClienteCobranza(localCursor.getInt(0),localCursor.getInt(1), localCursor.getString(2),localCursor.getString(3), 
																	localCursor.getFloat(4),localCursor.getFloat(5),localCursor.getString(6),localCursor.getInt(7),
																	localCursor.getDouble(8),localCursor.getDouble(9),localCursor.getInt(10));
				
				listadoClienteCobranza.add(clienteCobranza);
			}
			while(localCursor.moveToNext());
		}
		return listadoClienteCobranza;
	}
	
	public long ModificarClienteCobranzaServerId(int rowId,int serverId,float saldo) throws Exception
	{
	    try
	    {
	    	long l = new ClienteCobranzaDAL().ModificarClienteCobranzaServerId(rowId, serverId, saldo);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del cliente cobranza: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del cliente cobranza: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
