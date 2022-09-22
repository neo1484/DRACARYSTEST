package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class ProductoCHANGECostoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProductosCHANGECosto() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProductosCHANGECosto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGECosto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGECosto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarProductoCHANGECostoPor(long id) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProductoCHANGECostoPor(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productoCHANGECosto por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productoCHANGECosto por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarProductoCHANGECosto(int costoId,int productoId,float costo) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarProductoCHANGECosto(costoId, productoId, costo);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGECosto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGECosto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerProductosCHANGECosto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerProductosCHANGECosto();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGECosto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGECosto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerProductoCHANGECostoPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			  Cursor localCursor = db.obtenerProductoCHANGECostoPor(productoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGECosto por productoId : vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGECosto por ProductoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}