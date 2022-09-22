package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.PreventaProductoPOPWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PreventaProductoPOPDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarPreventaProductoPOPPor(long id) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarPreventaProductoPOPPor(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoPOP por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoPOP por Id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarPreventasProductoPOP() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarPreventasProductoPOP();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventasProductoPOP por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventasProductoPOP por Id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarPreventaProductoPOP(ArrayList<PreventaProductoPOPWSResult> preventasProductoPOP) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarPreventaProductoPOP(preventasProductoPOP);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long InsertarPreventaProductoPOP(int preventaPOPId,int preventaPOPIdServer,int productoPOPId,int cantidad,int clienteId,
											boolean syncro,String observacion,int motivoPopId) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarPreventaProductoPOP(preventaPOPId,preventaPOPIdServer,productoPOPId,cantidad,clienteId,syncro,observacion,motivoPopId);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoPOP: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerpreventasProductoPOP() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoPOP();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductosPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductosPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerpreventasProductoPOPPorClienteId(int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoPOPPorClienteId(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerpreventasProductoPOPPorPreventaPOPId(int preventaPOPId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoPOPPorPreventaPOPId(preventaPOPId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP por preventaPOPId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP por preventaPOPId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerpreventasProductoPOPPorPreventaPOPIdServer(int preventaPOPIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoPOPPorPreventaPOPIdServer(preventaPOPIdServer);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP por preventaPOPIdServer: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP por preventaPOPIdServer: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerpreventasProductoPOPNoSincronizadas(int preventaPOPId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoPOPNoSincronizadasPor(preventaPOPId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP no sincronizadas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerpreventasProductoPOPNoSincronizadas() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoPOPNoSincronizadas();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProductoPOP no sincronizadas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarPreventaProductoPOPNoSincronizado(int id, int preventaPOPIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      int i = db.modificarPreventaProductoPOPNoSincronizado(id, preventaPOPIdServer);;
	      return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa producto POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa producto POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarPreventaProductosPOPNoSincronizado(int preventaPOPId, int preventaPOPIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      int i = db.modificarPreventaProductosPOPNoSincronizado(preventaPOPId, preventaPOPIdServer);
	      return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa producto POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa producto POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long ModificarPreventaProductosPOP(int id,String observacion) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      int i = db.modificarPreventaProductosPOPPor(id,observacion);
	      return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la preventa producto POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la preventa producto POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

}
