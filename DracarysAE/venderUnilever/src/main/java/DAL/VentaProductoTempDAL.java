package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class VentaProductoTempDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarVentasProductoTemp() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasProductoTemp();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarVentasProductoTempPorTempId(int tempId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasProductoTempPorTempId(tempId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp por tempId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp por tempId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarVentasProductoTempPorTempId(int empleadoId, int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasProductoTempPorEmpleadoIdYClienteId(empleadoId,clienteId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp por empleadoId y clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temp por empleadoId y clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVentaProductoTemp(int tempId, int productoId, int promocionId, int cantidad, int cantidadNueva, 
											int cantidadPaquete, int cantidadPaqueteNueva, float monto, float montoNuevo,
											float descuento, float descuentoNuevo, float montoFinal, float montoFinalNuevo, 
											int empleadoId, int clienteId, int motivoId,float costo,int costoId,int precioId,
											float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId, float descuentoProntoPago) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVentaProductoTemp(tempId,productoId,promocionId,cantidad,cantidadNueva,cantidadPaquete,
	    			cantidadPaqueteNueva,monto,montoNuevo,descuento,descuentoNuevo,montoFinal,montoFinalNuevo,empleadoId,
	    			clienteId,motivoId,costo,costoId,precioId, descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerListadoVentaProductoTempPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerListadoVentaProductoTempPor(clienteId);
			  return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerListadoVentaProductoTempPorClienteYDistribuidor(int clienteId, int empleadoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = this.db.obtenerListadoVentaProductoTempPorClienteYDistribuidor(clienteId,empleadoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por clienteId y empleadoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por clienteId y empleadoId: " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerVentaProductoTempPor(long id) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentaProductoTempPor(id);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por id: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp id: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
		  
	  public Cursor ObtenerVentaProductoTempPorProductoPromocion(int productoId,int promocionId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentaProductoTempPorProductoPromocion(productoId, promocionId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por productoId y promocionId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp por productoId y promocionId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerVentasProductoTemp() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentasProductoTemp();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerVentasProductoTempNoConfirmadas(int clienteId,int empleadoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentasProductoTempNoConfirmadas(clienteId, empleadoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp no confirmadas: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temp no confirmadas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
