package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class VentaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarVentas() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentas();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVenta(int ventaIdServer, int dia, int mes, int anio, int clienteId, int distribuidorId, float monto,
							float descuento, float montoFinal, int preventaId, int tipoPagoId, double latitud, 
							double longitud, boolean diferencia, boolean estadoSincronizacion,int hora,int minuto,
							String observacion,boolean aplicarBonificacion,int dosificacionId,String tipoNit, 
							float descuentoCanal, float descuentoAjuste, int prontoPagoId, float descuentoProntoPago,
							float descuentoObjetivo, int formaPagoId, String codTransferencia, boolean fromApk, boolean fromShopp) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVenta(ventaIdServer,dia,mes,anio,clienteId,distribuidorId,monto,descuento,montoFinal,preventaId,
	    								tipoPagoId,latitud,longitud,diferencia,estadoSincronizacion,hora,minuto,observacion,
	    								aplicarBonificacion,dosificacionId,tipoNit, descuentoCanal, descuentoAjuste, prontoPagoId,
	    								descuentoProntoPago, descuentoObjetivo, formaPagoId, codTransferencia, fromApk, fromShopp);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public int ModificarVenta(int id, int ventaId, boolean estadoSincronizacion) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVenta(id,ventaId,estadoSincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public int ModificarVentaMontosYDescuentos(int id, float monto, float descuento, float montoFinal) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaMontosYDescuentos(id, monto, descuento, montoFinal);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentaPor(int id) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentaPor(id);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentaPorVentaId(int ventaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentaPorVentaId(ventaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentas() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentas();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentasNoSincronizadas() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentasNoSincronizadas();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas no sincronizadas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public int ModificarVentaIdServer(int rowId,int ventaIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaIdServer(rowId, ventaIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaRowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaRowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public int ModificarSincronizacionVenta(int preventaId,int ventaId,boolean estadoSincronizacion) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarSincronizacionVenta(preventaId, ventaId, estadoSincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public int ModificarVentaSincronizacion(int ventaId,boolean estadoSincronizacion) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaSincronizacion(ventaId, estadoSincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public Cursor ObtenerVentaPorClienteId(int clienteId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentaPorClienteId(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public int ModificarVentaClienteIdPorIdYClienteId(int Id,int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaClienteIdPorIdYClienteId(Id, clienteId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
