package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.ClienteFoto;
import DAL.ClienteFotoDAL;

public class ClienteFotoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
		
	public boolean BorrarClientesFoto() throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteFotoDAL().BorrarClientesFoto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public boolean BorrarClienteFotos(int clienteId) throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteFotoDAL().BorrarClienteFotos(clienteId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotos del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotos del cliente: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public boolean BorrarClientesFotoPor(int rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new ClienteFotoDAL().BorrarClientesFotoPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes foto por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes foto por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarClienteFoto(int clienteIdAndroid, int clienteIdServer, byte[] foto, boolean sincronizacion,int fotoCategoriaId, int fotoIdServer) throws Exception
	{
	    try
	    {
	    	long l = new ClienteFotoDAL().InsertarClienteFoto(clienteIdAndroid, clienteIdServer, foto, sincronizacion, fotoCategoriaId, fotoIdServer);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar cliente foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar cliente foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public int ModificarClienteIdClienteFoto(int clienteIdAndroid, int clienteIdServer) throws Exception
	{
		try
	    {
			int i = new ClienteFotoDAL().ModificarClienteIdClienteFoto(clienteIdAndroid, clienteIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar cliente foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar cliente foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public int ModificarSincronizacionClienteFoto(int id, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new ClienteFotoDAL().ModificarSincronizacionClienteFoto(id,sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public ClienteFoto ObtenerClienteFotoPor(int id) throws Exception
	{
		Cursor localCursor;
		try
	    {
			localCursor = new ClienteFotoDAL().ObtenerClienteFotoPor(id);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener clientes foto por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener clientes foto por id: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	
		ClienteFoto localClienteFoto = null;
	    if (localCursor.getCount() > 0) 
	    {
	    	localClienteFoto = new ClienteFoto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
	    						localCursor.getBlob(3), localCursor.getString(4).equals("1")?true:false, localCursor.getInt(5), 
	    						localCursor.getInt(6));
	    }
	    
	    return localClienteFoto;
	  }
	  
	public ArrayList<ClienteFoto> ObtenerClientesFoto() throws Exception
	  {
		  ArrayList<ClienteFoto> listadoClienteFoto = null;
		  Cursor localCursor;
		  try
		  {
			  localCursor = new ClienteFotoDAL().ObtenerClientesFoto();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cliente foto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cliente foto: " + localException.getMessage());
				}
			  throw localException;
		  }
	      
	      
		  if (localCursor.getCount() > 0) 
		  {		     
			  listadoClienteFoto = new ArrayList<ClienteFoto>();
		        do
		        {
		        	ClienteFoto clienteFoto = new ClienteFoto(localCursor.getInt(0), localCursor.getInt(1), 
		        			localCursor.getInt(2), localCursor.getBlob(3),localCursor.getString(4).equals("1")?true:false, localCursor.getInt(5), 
		    						localCursor.getInt(6));
	          
		        	listadoClienteFoto.add(clienteFoto);
		        } 
		        while (localCursor.moveToNext());
	      }
	     
		  return listadoClienteFoto;
	  }
	  
	public ArrayList<ClienteFoto> ObtenerClientesFotoNoSincronizados() throws Exception
	  {
		  ArrayList<ClienteFoto> listadoClienteFotoNoSincronizado = null;
		  Cursor localCursor;
		  try
		  {
			  localCursor = new ClienteFotoDAL().ObtenerClientesFotoNoSincronizados();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cliente foto no sincronizados: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cliente foto no sincronizados: " + localException.getMessage());
				}
			  throw localException;
		  }
	      
		  if (localCursor.getCount() > 0) 
		  {		     
			  listadoClienteFotoNoSincronizado = new ArrayList<ClienteFoto>();
		        do
		        {
		        	ClienteFoto clienteFoto = new ClienteFoto(localCursor.getInt(0), localCursor.getInt(1), 
		        			localCursor.getInt(2), localCursor.getBlob(3),localCursor.getString(4).equals("1")?true:false, localCursor.getInt(5), 
		    						localCursor.getInt(6));
	          
		        	listadoClienteFotoNoSincronizado.add(clienteFoto);
		        } 
		        while (localCursor.moveToNext());
	      }
	     
		  return listadoClienteFotoNoSincronizado;
	  }
	  
	public ArrayList<ClienteFoto> ObtenerClientesFotoPorClienteIdAndroid(int paramInt) throws Exception
	  {
		  ArrayList<ClienteFoto> listadoClienteFoto = null;
		  Cursor localCursor;
		  try
		  {
			  localCursor = new ClienteFotoDAL().ObtenerClienteFotoPorClienteIdAndroid(paramInt);
	      
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente foto por clienteIdAndroid: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente foto por clienteIdAndroid: " + localException.getMessage());
			  }
	    	throw localException;
		  }
		
		  if(localCursor.getCount() > 0)
		  {
			  listadoClienteFoto = new ArrayList<ClienteFoto>();
			  do
			  {
				  ClienteFoto clienteFoto = new ClienteFoto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2),
						  localCursor.getBlob(3),localCursor.getString(4).equals("1")?true:false, localCursor.getInt(5), 
		    						localCursor.getInt(6));
	          
				  listadoClienteFoto.add(clienteFoto);
	  	 	  } 
			  while (localCursor.moveToNext());
		  }
	      
		return listadoClienteFoto;
	  }
	
	public ArrayList<ClienteFoto> ObtenerClientesFotoServer(int clienteId) throws Exception
	{
		ArrayList<ClienteFoto> listadoClienteFoto = null;
		Cursor localCursor;
		try
		{
			localCursor = new ClienteFotoDAL().ObtenerClienteFotoServer(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos del server del cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos del server del cliente: " + localException.getMessage());
			}
	    	throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoClienteFoto = new ArrayList<ClienteFoto>();
			do
			{
				ClienteFoto clienteFoto = new ClienteFoto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2),
						localCursor.getBlob(3),localCursor.getString(4).equals("1")?true:false, localCursor.getInt(5), 
	    						localCursor.getInt(6));
	          
				listadoClienteFoto.add(clienteFoto);
  	 	  	} 
			while (localCursor.moveToNext());
		}
	      
		return listadoClienteFoto;
	}
}
