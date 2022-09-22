package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ProductoPOPWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ProductoPOPDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProductosPOP() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProductosPOP();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarProductoPOPPor(long id) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProductoPOPPor(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productoPOP por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productoPOP por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarProductoPOP(ArrayList<ProductoPOPWSResult> productosPOP) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarProductoPOP(productosPOP);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerProductosPOP() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerProductosPOP();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerProductosPorProveedorNoEnPreventaProductoPOP(int proveedorId,int categoriaId,int preventaPOPId)throws Exception
	{
		db.OpenDB();
		try
		{
			  Cursor localCursor = db.obtenerProductosPOPPorProveedorNoEnPreventaProductoPOP(proveedorId, categoriaId, preventaPOPId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId y categoriaIdPOP : vacio");
	  }
	  else
	  {
		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId y categoriaIdPOP: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public Cursor ObtenerProductoPOPPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			  Cursor localCursor = db.obtenerProductoPOPPorProductoId(productoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto por productoId : vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto por ProductoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
