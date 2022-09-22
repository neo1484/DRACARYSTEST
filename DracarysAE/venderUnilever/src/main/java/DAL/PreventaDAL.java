package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class PreventaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreventas()throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	db.borrarPreventas();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarPreventa(int preventaIdServer, int empleadoId, int clienteId, float monto, float descuento, 
			float montoFinal, int tipoPagoId, double latitud, double longitud, boolean estado, int dia, int mes,
			int anio, int hora, int minuto, String factura, String nit, boolean nitNuevo, int noPreventa, String observacion,
			boolean aplicarBonificacion, int diaEntrega, int mesEntrega, int anioEntrega, int dosificacionId, String tipoNit,
			String ruta, String tipoVisita, int zonaVentaId, int prontoPagoId, float descuentoCanal, float descuentoAjuste,
			float descuentoProntoPago, float descuentoObjetivo, int formaPagoId, String codTransferencia, boolean fromApk, 
			boolean fromShopp, float descuentoIncentivo) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarPreventa(preventaIdServer, empleadoId, clienteId, monto, descuento, montoFinal, tipoPagoId, latitud,
	    								longitud, estado, dia, mes, anio, hora, minuto, factura, nit, nitNuevo, noPreventa,
	    								observacion, aplicarBonificacion, diaEntrega, mesEntrega, anioEntrega, dosificacionId, tipoNit,
	    								ruta, tipoVisita, zonaVentaId, prontoPagoId, descuentoCanal, descuentoAjuste, descuentoProntoPago,
	    								descuentoObjetivo, formaPagoId, codTransferencia, fromApk, fromShopp, descuentoIncentivo);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventa: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarPreventasPorId(long id)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	db.borrarPreventaPor(id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventa por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventas por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public int ModificarPreventaNoSincronizada(int id, int preventaId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	int i = db.modificarPreventaNoSincronizadaPor(id,preventaId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por preventaI: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	  public int ModificarPreventaNoSincronizadaPor(int id,float monto,float descuento,float montoFinal) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarPreventaNoSincronizadaPor(id,monto,descuento,montoFinal);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por Id: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por Id: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarPreventaNoSincronizadaPor(int id,boolean estado) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarPreventaNoSincronizadaPor(id, estado);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por id y estado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por id y estado: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventaPor(int id) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaPor(id);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por Id: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por Id: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	   
	  public Cursor ObtenerPreventaPorClienteId(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaPorClienteId(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerNroPreventasPorClienteId(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerNroPreventasPorClienteId(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventaPorId(int rowId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaPorId(rowId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por rowId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por rowId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventas() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventas();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventasNoSincronizadas() throws Exception
	  {
		  db.OpenDB();	
		  try
		  {
			  Cursor localCursor = db.obtenerPreventasNoSincronizadas();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventasNoSincronizadasPorRowId(int rowId) throws Exception
	  {
		  db.OpenDB();	
		  try
		  {
			  Cursor localCursor = db.obtenerPreventasNoSincronizadasPorRowId(rowId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas por rowId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas por rowId: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarPreventaClienteIdPorIdYClienteId(int id, int clienteId) throws Exception
		{
		    db.OpenDB();
		    try
		    {
		    	int i = db.modificarPreventaClienteIdPorIdYClienteId(id, clienteId);
		    	return i;
		    }
		    catch (Exception localException)
		    {
		    	if(localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la preventa por id y clienteId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la preventa por id y clienteId: " + localException.getMessage());
		    	} 
		    	throw localException;
		    }
		    finally
		    {
		    	db.CloseDB();
		    }
		}
	  
	  public int ModificarPreventaIdServer(int id,int preventaIdServer) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarPreventaIdServer(id, preventaIdServer);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	public int ModificarPreventaClienteIncentivo(int preventaIdServer,float montoFinal, float descuentoIncentivo) throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarPreventaClienteIncentivo(preventaIdServer, montoFinal, descuentoIncentivo);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el descuento incentivo de la preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la descuento incentivo de la preventa: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	  public Cursor ObtenerPreventaPorPreventaIdServer(int preventaIdServer) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaPorPreventaIdServer(preventaIdServer);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaIdServer: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaIdServer: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarPreventaMontosPorPreventaId(int preventaId, float monto, float descuento, float montoFinal, float descuentoCanal,
			  											float descuentoAjuste, float descuentoProntoPago, float descuentoObjetivo) throws Exception
		{
		    db.OpenDB();
		    try
		    {
		    	int i = db.modificarPreventaMontosPorPreventaId(preventaId, monto, descuento, montoFinal, descuentoCanal, descuentoAjuste, descuentoProntoPago, descuentoObjetivo);
		    	return i;
		    }
		    catch (Exception localException)
		    {
		    	if(localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos de la preventa por preventaId: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos de la preventa por preventaId: " + localException.getMessage());
		    	} 
		    	throw localException;
		    }
		    finally
		    {
		    	db.CloseDB();
		    }
		}
	  
	  public Cursor ObtenerNroPreventas() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerNroPreventas();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el numero de preventas: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el numero de preventas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
