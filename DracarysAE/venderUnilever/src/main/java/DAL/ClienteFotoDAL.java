package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class ClienteFotoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
  	public boolean BorrarClientesFoto() throws Exception
	{
  		db.OpenDB();
	    try
	    {
	      db.borrarClientesFoto();
	      return true;
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
	    finally
	    {
	      this.db.CloseDB();
	    }
	}
  	
  	public boolean BorrarClienteFotos(int clienteId) throws Exception
	{
  		db.OpenDB();
	    try
	    {
	      db.borrarClienteFotos(clienteId);
	      return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotos del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotos dle cliente: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	      this.db.CloseDB();
	    }
	}
	  
	public boolean BorrarClientesFotoPor(int rowId) throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	      db.borrarClienteFotoPor(rowId);
	      return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la foto dle cliente por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la foto dle cliente por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public long InsertarClienteFoto(int clienteIdAndroid, int clienteIdServer, byte[] foto, boolean sincronizacion, int fotoCategoriaId,int fotoIdServer)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = this.db.insertarClienteFoto(clienteIdAndroid,clienteIdServer,foto,sincronizacion,fotoCategoriaId,fotoIdServer);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public int ModificarClienteIdClienteFoto(int clienteIdAndroid, int clienteIdServer)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarClienteIdClienteFoto(clienteIdAndroid, clienteIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de los clientes foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de los clientes foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public int ModificarSincronizacionClienteFoto(int id, boolean sincronizacion)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	int i = db.modificarSincronizacionClienteFoto(id,sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClienteFotoPor(int id)throws Exception
  	{
	    db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerClientesFotoPor(id);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente foto por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente foto por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
  	}
	  
	public Cursor ObtenerClienteFotoPorClienteIdAndroid(int clienteIdAndroid) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesFotoPorClienteIdAndroid(clienteIdAndroid);
	    	return localCursor;
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
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerClienteFotoServer(int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesFotoServer(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos del cliente: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	//db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClientesFoto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesFoto();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes foto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes foto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClientesFotoNoSincronizados()throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerClientesFotoNoSincronizados();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente foto no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente foto no sincronizados: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

}
