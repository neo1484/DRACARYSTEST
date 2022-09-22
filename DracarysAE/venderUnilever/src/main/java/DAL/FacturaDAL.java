package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class FacturaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarFacturas() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarFacturas();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public long InsertarFactura(String numero, String nombre, String nit, int fechaDia,int fechaMes, int fechaAnio,
								int fechaHora, int fechaMinuto,int fechaLimiteEmisionDia, int fechaLimiteEmisionMes,
								int fechaLimiteEmisionAnio, int fechaLimiteEmisionHora,int fechaLimiteEmisionMinuto, 
								float montoTotal, float descuento,float montoFinal, String montoPalabras,
								String codigoAutorizacion, String codigoControl,String facturaTipoId, int almacenId, 
								boolean anulada,int anulacionUsuarioId, String anulacionMotivo,int anulacionFechaDia, 
								int anulacionFechaMes,int anulacionFechaAnio, int dosificacionId, int empleadoId,
								long qrTamano, String qrExtension, String qrBinario,String qrMimeType, int clienteId, 
								int numeroItems,boolean sincronizacion,boolean impreso,int ventaId,int serverVentaId,
								boolean nitNuevo,int noFactura,String tipoNit) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarFactura(numero, nombre, nit, fechaDia, fechaMes, fechaAnio, fechaHora, fechaMinuto, 
										fechaLimiteEmisionDia, fechaLimiteEmisionMes, fechaLimiteEmisionAnio, 
										fechaLimiteEmisionHora, fechaLimiteEmisionMinuto, montoTotal, descuento, montoFinal,
										montoPalabras, codigoAutorizacion, codigoControl, facturaTipoId, almacenId, anulada,
										anulacionUsuarioId, anulacionMotivo, anulacionFechaDia, anulacionFechaMes,
										anulacionFechaAnio, dosificacionId, empleadoId, qrTamano, qrExtension, qrBinario,
										qrMimeType, clienteId, numeroItems, sincronizacion, impreso, ventaId, serverVentaId,
										nitNuevo,noFactura,tipoNit);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor obtenerFacturas() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturas();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor obtenerFacturasNoSincronizadas() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasNoSincronizadas();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor obtenerFacturasNoImpresas() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasNoImpresas();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no impresas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no impresas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor obtenerFacturaPorVentaId(int ventaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasPorVentaId(ventaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por ventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por ventaId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor obtenerFacturaPorRowId(int rowId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasPorRowId(rowId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por rowId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public int ModificarEstadoImpresion(int ventaId,boolean estado) throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarEstadoImpresionPorVentaId(ventaId, estado);
			return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado impresion: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado impresion: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public int ModificarFacturaSincronizacion(int rowId,boolean estado) throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarFacturasincronizacion(rowId, estado);
			return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la factura: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la factura: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public int ModificarFacturaServerVentaIdPorVentaId(int ventaId,int serverVentaId) throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarFacturaServerVentaIdPorVentaId(ventaId, serverVentaId);
			return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar serverVentaId por ventaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar serverVentaId por ventaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public int ModificarFacturaDatosFacturaPorVentaId(int ventaId,String nombreFactura,String nit,boolean nitNuevo) throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarFacturaDatosFacturaPorVentaId(ventaId, nombreFactura, nit, nitNuevo);
			return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar nombreFactura y nit por ventaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar nombreFactura y nit por ventaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
