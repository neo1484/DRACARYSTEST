package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class VentaProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasProducto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVentaProducto(int ventaId, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
									float monto, float descuento, float montoFinal, int motivoId, boolean estadoSincronizacion,
									float costo,int costoId,int precioId, float descuentoCanal, float descuentoAjuste,
									int canalPrecioRutaId, float descuentoProntoPago) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVentaProducto(ventaId,productoId,promocionId,cantidad,cantidadPaquete,monto,descuento,
	    			montoFinal,motivoId,estadoSincronizacion,costo,costoId, precioId, descuentoCanal, descuentoAjuste,
	    			canalPrecioRutaId, descuentoProntoPago);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public int ModificarVentaProducto(int rowId, boolean estadoSincronizacion) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaProducto(rowId,estadoSincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentaProductoPor(int id) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentaProductoPor(id);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProducto por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaProducto por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentasProducto();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasProductoPor(int ventaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasProductoPor(ventaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los venta productos por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los venta productos por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasProductoNoSincronizados(int ventaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasProductoNoSincronizados(ventaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los venta productos no sincronizados por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los venta productos no sincronizados por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
