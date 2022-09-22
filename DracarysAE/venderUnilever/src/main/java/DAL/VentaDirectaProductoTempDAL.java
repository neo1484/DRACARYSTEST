package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class VentaDirectaProductoTempDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasDirectasProductoTemp() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasDirectasProductoTemp();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa temp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa temp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVentaDirectaProductoTemp(int ventaId, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
									float monto, float descuento, float montoFinal, int costoId,int precioId,int noVentaDirecta,
									int clienteId,int tempId,int empleadoId, float descuentoCanal, float descuentoAjuste,
									int canalPrecioRutaId, float descuentoProntoPago) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVentaDirectaProductoTemp(ventaId, productoId, promocionId, cantidad, cantidadPaquete, monto, descuento,
	    												montoFinal, costoId, precioId, noVentaDirecta, clienteId, tempId, empleadoId,
	    												descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa temp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa temp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	   
	public Cursor ObtenerVentaDirectaProductoTempPorRowId(int rowId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentaDirectaProductoTempPorRowId(rowId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaDirectaProductoTemp por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ventaDirectaProductoTemp por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasDirectasProductoTemp() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentasDirectasProductoTemp();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasDirectasProductoTempPorVentaId(int ventaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoTempPorVentaId(ventaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasDirectasProductoTempPorClienteId(int clienteId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoTempPorClienteId(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temp por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarVentaDirectaProductoTempNoSincronizadaPor(int rowId,int ventaDirectaIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      int i = db.modificarVentaDirectaProductoTempNoSincronizadaPor(rowId,ventaDirectaIdServer);
	      return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta directa producto temp por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta directa producto temp por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarVentaDirectaProductoTempPorClienteIdEmpleadoIdNoVentaDirecta(int clienteId,int empleadoId,int noVentaDirecta) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentaDirectaProductoTempPorClienteIdEmpleadoIdNoVentaDirecta(clienteId, empleadoId, noVentaDirecta);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la venta directa por clienteId, empleadoId, noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la venta directa por clienteId, empleadoId, noVentaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasDirectasProductoTempNoSincronizadasPorClienteIdNoVentaDirecta(int clienteId,int noVentaDirecta) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoTempNoSincronizadasPorClienteIdNoVentaDirecta(clienteId, noVentaDirecta);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temp no sincronizadas por clienteId y noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temp no sincronizadas por clienteId y noVentaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarVentaDirectaProductoTempPor(int rowId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentaDirectaProductoTempPor(rowId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la ventaDirectaProductoTemp por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la ventaDirectaProductoTemp por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasDirectasProductoTempPorClienteIdNoVentaDirecta(int clienteId,int noVentaDirecta) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerVentasDirectasProductoTempPorClienteIdNoVentaDirecta(clienteId, noVentaDirecta);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temp por clienteId y noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas temp por clienteId y noVentaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
