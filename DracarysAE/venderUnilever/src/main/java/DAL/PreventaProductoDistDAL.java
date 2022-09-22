package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.PreventaProductoDistWSResult;
import Utilidades.AdministradorDB;

public class PreventaProductoDistDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarPreventasProductoDist() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarPreventasProductoDist();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas producto distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas producto distribuidor: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarPreventaProductoDist(ArrayList<PreventaProductoDistWSResult> preventasProductoDist) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarPreventaProductoDist(preventasProductoDist);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las prevetas del distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventa del distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPreventaProductoDistPor(int preventaProductoId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerPreventaProductoDistPor(preventaProductoId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventa detalle distribuidor por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventa detalle distribuidor por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPreventasProductoDist() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoDist();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas detalle del distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas detalle del distribuidor: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPreventasProductoDistPor(int preventaId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoDistPor(preventaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas detalle distribuidor por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas detalle distribuidor por preventaId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public int UpdatePreventaProductoDist(int preventaProductoId, int preventaId, int productoId, int cantidad, 
			  							int cantidadPaquete, float monto, float descuento, float montoFinal, 
			  							boolean estadoSincronizacion,float costo,int costoId,int precioId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarRegistroPreventaProductoDistPor(preventaProductoId,preventaId,productoId,cantidad,
					  								cantidadPaquete,monto,descuento,montoFinal,estadoSincronizacion,
					  								costo,costoId,precioId);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa detalle distribuidor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa detalle distribuidor: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventasProductoDistPor(int preventaId,int productoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventasProductoDistPor(preventaId,productoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas detalle distribuidor por preventaId y productoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas detalle distribuidor por preventaId y productoId: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
