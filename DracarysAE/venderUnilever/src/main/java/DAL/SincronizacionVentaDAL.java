package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class SincronizacionVentaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarSincronizacionesVenta() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarSincronizacionesVenta();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public boolean BorrarSincronizacionesVentaPor(int preventaId,int clienteId,int productoId) throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarSincronizacionesVentaPor(preventaId, clienteId, productoId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta por preventaId, clienteId, productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta por preventaId, clienteId, productoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }	
	
	  public boolean BorrarSincronizacionesVentaNoConfirmadas() throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarSincronizacionesVentaNoConfirmadas();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVentaNoConfirmadas.: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVentaNoConfirmadas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarSincronizacionVenta(int ventaProductoTempRow,int preventaId,int clienteId,int distribuidorId,
			  	int productoId,int promocionId,int cantidad,int cantidadPaquete,float monto,float descuento,
			  	float montoFinal,int tipoPagoId,int cantidadNueva,int cantidadPaqueteNueva,float montoNuevo,
			  	float descuentoNuevo,float montoFinalNuevo,int numeroDeItems,int motivoId,int dia,int mes,int anio,
				double latitud,double longitud,boolean confirmado,int tipoSincronizacion,boolean estadoSincronizacion,
				int hora,int minuto,boolean autoventa,String nombreFactura,String nit,boolean nitNuevo,float costo,
				int ventaIdServer,int ventaId,int costoId,String observacion,int precioId,boolean aplicarBonificacion,
				int noAutoventa,int dosificacionId,String tipoNit, float descuentoCanal, float descuentoAjuste, 
				int canalPrecioRutaId, float descuentoProntoPago, int prontoPagoId, float descuentoObjetivo,
				int formaPagoId, String codTransferencia, boolean fromShopping) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarSincronizacionVenta(ventaProductoTempRow,preventaId, clienteId, distribuidorId, productoId, 
					  								promocionId, cantidad, cantidadPaquete, monto, descuento,montoFinal, 
					  								tipoPagoId, cantidadNueva,cantidadPaqueteNueva,montoNuevo, descuentoNuevo,
					  								montoFinalNuevo, numeroDeItems,	motivoId, dia,mes, anio, latitud, longitud,
					  								confirmado,tipoSincronizacion,estadoSincronizacion,hora,minuto,autoventa,
					  								nombreFactura,nit,nitNuevo,costo,ventaIdServer,ventaId,costoId,observacion,
					  								precioId,aplicarBonificacion,noAutoventa,dosificacionId,tipoNit, descuentoCanal, 
					  								descuentoAjuste, canalPrecioRutaId, descuentoProntoPago, prontoPagoId, descuentoObjetivo,
					  								formaPagoId, codTransferencia, fromShopping);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionVentaPor(int preventaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaPor(preventaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionVentaNoConfirmadaPorVentaProductoTempRowId(int ventaProductoTempRowId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaNoConfirmadaPorVentaProductoTempRowId(ventaProductoTempRowId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no confirmada por ventaProductoTempRowId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no confirmada por ventaProductoTempRowId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(int preventaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaNoSincronizadasPorPreventaId(preventaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaNoSincronizadasPorVentaIdServer(int ventaIdServer) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaNoSincronizadasPorVentaIdServer(ventaIdServer);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por ventaIdServer: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por ventaIdServer: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaNoSincronizadaPorVentaId(int ventaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaNoSincronizadaPorVentaId(ventaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por ventaId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por ventaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaVentaNoEntregadaPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaVentaNoEntregadaPor(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta no entregada por clienteId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta no entregada por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	   
	  public Cursor ObtenerSincronizacionesVentasModificadasPor(int preventaId,int productoId,int promocionId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentasModificadasPor(preventaId, productoId, promocionId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta modificadas: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta modificadas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaNoSincronizadasAgrupadas() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaNoSincronizadasAgrupadas();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no sincronizadas agrupadas: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no sincronizadas agrupadas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesAutoVentaNoSincronizadasAgrupadas() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionAutoVentaNoSincronizadasAgrupadas();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no sincronizadas agrupadas: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no sincronizadas agrupadas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaModificada(int ventaProductoTempRowId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaModificada(ventaProductoTempRowId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta modificadas por rowId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta modificadas por rowId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	  public int ModificarSincronizacionVentaConfirmacionYSincronizacion(int preventaId, int tipoSincronizacion,boolean confirmado,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaConfirmacionYSincronizacion(preventaId,tipoSincronizacion,confirmado,sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaSincronizacion(int rowId,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaSincronizacion(rowId, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaVentaIdPorPreventaId(int preventaId,int ventaId)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaVentaIdPorPreventaId(preventaId, ventaId);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId por preventaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId por preventaId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaVentaIdServerPorVentaId(int ventaId,int ventaIdServer)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaVentaIdServerPorVentaId(ventaId, ventaIdServer);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer por ventaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer por ventaId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionAutoventaSincronizacionPorVentaId(int ventaId,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionAutoventaSincronizacionPorVentaId(ventaId, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionAutoventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionAutoventa: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaSincronizacionPorVentaIdServer(int ventaIdServer,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaSincronizacionPorVentaIdServer(ventaIdServer, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta por ventaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta por ventaId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaSincronizacionPorVentaProductoTempRowId(int ventaProductoTempRowId,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaSincronizacionPorVentaProductoTempRowId(ventaProductoTempRowId, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta por ventaProductoTempRowId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta por ventaProductoTempRowId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaVentaIdServerPor(int preventaId,int clienteId,int ventaIdServer)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaVentaIdServerPor(preventaId, clienteId, ventaIdServer);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por preventaId y clienteId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por preventaId y clienteId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaVentaIdServerPorProducto(int clienteId,int preventaId,int productoId,int ventaIdServer)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaVentaIdServerPorProducto(clienteId, preventaId, productoId, ventaIdServer);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y productoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y productoId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaVentaIdServerPorPromocion(int clienteId,int preventaId,int promocionId,int ventaIdServer)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaVentaIdServerPorPromocion(clienteId, preventaId, promocionId, ventaIdServer);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y promocionId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la sincronizacionVenta por clienteId, preventaId y promocionId: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionVentaPorClienteId(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaPorClienteId(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por clienteId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la SincronizacionVenta por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaClienteId(int Id,int clienteId)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaClienteId(Id, clienteId);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la sincronizacionVenta: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la sincronizacionVenta: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionVentaConfirmacionYSincronizacionPorId(int rowId, int tipoSincronizacion,
			  						boolean confirmado,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionVentaConfirmacionYSincronizacionPorId(rowId, tipoSincronizacion, 
					  												confirmado, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionVenta: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerSincronizacionesVentaAutoVentaNoEntregadaPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerSincronizacionVentaAutoVentaNoEntregadaPor(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener la autoventa no entregada por clienteId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la autoventa no entregada por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarSincronizacionAutoventaSincronizacionPorClienteId(int clienteId,boolean sincronizacion)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarSincronizacionAutoventaSincronizacionPorClienteId(clienteId, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionAutoventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la sincronizacionAutoventa: " + localException.getMessage());
			  }
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
