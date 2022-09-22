package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class VentaDirectaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarVentasDirectas() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarVentasDirectas();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas directas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas directas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarVentaDirecta(int ventaDirectaIdServer, int dia, int mes, int anio, int clienteId, float monto,
							float descuento, float montoFinal, int tipoPagoId, double latitud, double longitud, 
							int hora, int minuto, String observacion,int empleadoId,String factura,String nit,
							boolean nitNuevo,int noVentaDirecta,boolean estado,boolean aplicarBonificacion,String tipoNit,
							String ruta,String tipoVisita,int zonaVentaId,int prontoPagoId, float descuentoCanal, 
							float descuentoAjuste, float descuentoProntoPago, float descuentoObjetivo, int formaPagoId,
							String codTransferencia, float descuentoIncentivo) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarVentaDirecta(ventaDirectaIdServer,dia,mes,anio,clienteId,monto,descuento,montoFinal,
	    									tipoPagoId,latitud,longitud,hora,minuto,observacion,empleadoId,
	    									factura,nit,nitNuevo,noVentaDirecta,estado,aplicarBonificacion,tipoNit,
	    									ruta,tipoVisita,zonaVentaId,prontoPagoId, descuentoCanal, descuentoAjuste,
	    									descuentoProntoPago, descuentoObjetivo, formaPagoId, codTransferencia,
											descuentoIncentivo);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta directas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta directas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public int ModificarVentaDirectaMontosYDescuentos(int rowId, float monto, float descuento, float montoFinal) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaDirectaMontosYDescuentos(rowId, monto, descuento, montoFinal);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos de la venta directa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos de la venta directa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public int ModificarVentaDirectaDescuentoIncentivo(int ventaIdServer, float montoFinal, float descuentoIncentivo) throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarVentaDirectaDescuentoIncentivo(ventaIdServer, montoFinal, descuentoIncentivo);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto final y descuento incentivo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto final y descuento incentivo: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerVentaDirectaPor(int rowId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentaDirectaPor(rowId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerVentaDirectaPorVentaIdServer(int ventaIdServer) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentaDirectaPorVentaIdServer(ventaIdServer);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por ventaIdServer: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por ventaIdServer: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerVentasDirectas() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentasDirectas();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public int ModificarVentaDirectaPorVentaIdServer(int rowId,int ventaIdServer) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaDirectaPorVentaIdServer(rowId, ventaIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public Cursor ObtenerVentaDirectaPorClienteId(int clienteId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerVentaDirectaPorClienteId(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public int ModificarVentaDirectaNoSincronizadaPor(int rowId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarVentaDirectaNoSincronizadaPor(rowId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer de la venta directa por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer de la venta directa por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

}
