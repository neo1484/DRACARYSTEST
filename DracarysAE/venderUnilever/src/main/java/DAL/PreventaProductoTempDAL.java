package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class PreventaProductoTempDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarPreventaProductoTemp() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarPreventaProductoTemp();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarPreventaProductoTempPorId(long id) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarPreventaProductoTempPorId(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarPreventaProductoTempPorClienteIdEmpleadoId(int clienteId,int empleadoId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarPreventaProductoTempPorClienteIdEmpleadoId(clienteId,empleadoId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por clienteId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarPreventaProductoTempPorClienteIdEmpleadoIdNoPreventa(int clienteId,int empleadoId,int noPreventa) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarPreventaProductoTempPorClienteIdEmpleadoIdNoPreventa(clienteId,empleadoId,noPreventa);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por clienteId, empleadoId y noPreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por clienteId, empleadoId y noPreventa: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarPreventaProductoTemp(int tempId, int clienteId, int productoId, int cantidad, int cantidadPaquete,
						float monto, float descuento, float montoFinal, int empleadoId, int promocionId, float costo,
						int costoId, int noPreventa, int precioId, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId,
						float descuentoProntoPago) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarPreventaProductoTemp(tempId, clienteId, productoId, cantidad, cantidadPaquete, monto, descuento,
	    			montoFinal, empleadoId, promocionId, costo, costoId, noPreventa, precioId, descuentoCanal, descuentoAjuste,
	    			canalPrecioRutaId, descuentoProntoPago);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long ModificarPreventaProductoTemp(int id, int tempId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      int i = db.modificarPreventaProductoTemp(id,tempId);
	      return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa producto temp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa producto temp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPreventasProductoTemp() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoTemp();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPreventasProductoTempNoSincronizadaPor(int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventaProductoTempNoSincronizadasPor(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp no sincronizadas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPreventasProductoTempPor(int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoTempPor(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerPreventasProductoTempPor(int clienteId,int noPreventa) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreventasProductoTempPor(clienteId,noPreventa);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp por clienteId y noPreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp por clienteId y noPreventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
