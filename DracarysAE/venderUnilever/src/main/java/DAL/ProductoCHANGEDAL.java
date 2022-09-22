package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class ProductoCHANGEDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProductosCHANGE() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProductosCHANGE();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarProductoCHANGEPor(long id) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProductoCHANGEPor(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productoCHANGE por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productoCHANGE por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarProductoCHANGE(int productoId,String descripcion25,int categoriaId,String codigoBarra) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarProductoCHANGE(productoId, descripcion25, categoriaId, codigoBarra);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerProductosCHANGE() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerProductosCHANGE();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(int proveedorId,int categoriaId,int preventaPOPId)throws Exception
	{
		db.OpenDB();
		try
		{
			  Cursor localCursor = db.obtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(proveedorId, categoriaId, preventaPOPId);
			  return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId y categoriaIdCHANGE : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId y categoriaIdCHANGE: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerProductoCHANGEPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			  Cursor localCursor = db.obtenerProductoCHANGEPorProductoId(productoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGE por productoId : vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGE por ProductoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	}
}
