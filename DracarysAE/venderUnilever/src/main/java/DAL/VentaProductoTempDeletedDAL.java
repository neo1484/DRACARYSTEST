package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class VentaProductoTempDeletedDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
		  
	public boolean BorrarVentasProductoTempDeleted() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasProductoTempDeleted();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp deleted: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp deleted: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarVentasProductoTempDeletedPor(int id) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentaProductoTempDeletedPor(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por id: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarVentasProductoTempDeletedPorRowId(int rowId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentaProductoTempDeletedPorRowId(rowId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por rowId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVentaProductoTempDeleted(int rowId,int tempId, int empleadoId, int clienteId, int motivoId, 
												boolean estadoSincronizacion) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVentaProductoTempDeleted(rowId,tempId,empleadoId,clienteId,motivoId,estadoSincronizacion);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temp deleted: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temp deleted: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public Cursor ObtenerVentaProductoTempDeletedPor(int id) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentaProductoTempDeletedPor(id);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp deleted por id: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp deleted por id: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerVentaProductoTempDeletedPorRowId(int rowId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentaProductoTempDeletedPorRowId(rowId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaTempDeleted por rowId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaTempDeleted por rowId: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public Cursor ObtenerVentaProductoTempDeletedPorTempId(int tempId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentaProductoTempDeletedPor(tempId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaTempDeleted por tempId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la ventaTempDeleted por tempId: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerVentasProductoTempDeleted() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentasProductoTempDeleted();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"rror al obtener los productos de la venta temp deleted: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"rror al obtener los productos de la venta temp deleted: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
