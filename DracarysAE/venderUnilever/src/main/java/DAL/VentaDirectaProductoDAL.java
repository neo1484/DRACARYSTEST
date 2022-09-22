package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class VentaDirectaProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasDirectasProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasDirectasProducto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVentaDirectaProducto(int ventaDirectaIdServer, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
									float monto, float descuento, float montoFinal, int motivoId, int costoId,int precioId,
									int noVentaDirecta, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVentaDirectaProducto(ventaDirectaIdServer,productoId,promocionId,cantidad,cantidadPaquete,monto,descuento,
	    			montoFinal,motivoId,costoId,precioId,noVentaDirecta, descuentoCanal, descuentoAjuste, canalPrecioRutaId);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	   
	public Cursor ObtenerVentaDirectaProductoPorRowId(int rowId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentaDirectaProductoPorRowId(rowId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaDirectaProducto por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaDirectaProducto por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasDirectasProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentasDirectasProducto();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasDirectasProductoPorVentaId(int ventaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoPorVentaId(ventaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasDirectasProductoPorNoVentaDirecta(int noVentaDirecta) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoPorNoVentaDirecta(noVentaDirecta);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa por noVentaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasDirectasProductoNoSincronizadasPorVentaDirectaId(int ventaDirectaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoNoSincronizadasPorVentaDirectaId(ventaDirectaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados por ventaDirectaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados por ventaDirectaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarVentaDirectaProductoIdServerPor(int noVentaDirecta,int ventaDirectaIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      int i = db.modificarVentaDirectaProductoIdServerPor(noVentaDirecta, ventaDirectaIdServer);
	      return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta directa producto IdServer por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta directa producto IdServer por noVentaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasDirectasProductoNoSincronizadasPorNoVentaDirecta(int noVentaDirecta) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoNoSincronizadasPorNoVentaDirecta(noVentaDirecta);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados por noventaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
