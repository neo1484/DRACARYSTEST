package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class DevolucionDistribuidorProductoTempDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDevolucionDistribuidorProductoTemp() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarDevolucionDistribuidorProductoTemp();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarDevolucionDistribuidorProductoTempPorId(long Id)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarDevolucionDistribuidorProductoTempPorId(Id);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolcuionProductoTemp por Id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarDevolucionDistribuidorProductoTempPorTempId(int tempId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarDevolucionDistribuidorProductoTempPorTempId(tempId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por tempId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolcuionProductoTemp por tempId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public boolean BorrarDevolucionDistribuidorProductoTempPor(int empleadoId, int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarDevolucionDistribuidorProductoTempPorEmpleadoIdYClienteId(empleadoId,clienteId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por empleadoId y clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por empleadoId y clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarDevolucionDistribuidorProductoTemp(int tempId, int productoId, int promocionId, int cantidad, 
											int cantidadPaquete, float monto, float descuento, float montoFinal, 
											int empleadoId, int clienteId,float costo,int costoId,int precioId,
											int noAutoventa, float descuentoCanal, float descuentoAjuste, 
											int canalPrecioRutaId, float descuentoProntoPago) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarDevolucionDistribuidorProdutoTemp(tempId, productoId, promocionId, cantidad, 
	    			cantidadPaquete, monto, descuento, montoFinal, empleadoId, clienteId, costo, costoId,precioId,
	    			noAutoventa, descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacenDevolucionProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacenDevolucionProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public Cursor ObtenerDevolucionDistribuidorProductoTempPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTempPor(clienteId);
			  return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public Cursor ObtenerDevolucionDistribuidorProductoTempPorClienteYDistribuidor(int clienteId, int empleadoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = this.db.obtenerDevolucionDistribuidorProductoTempPorClienteYDistribuidor(clienteId,empleadoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId y empleadoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId y empleadoId: " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerDevolucionDistribuidorProductoTempPorRowId(long id) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTempPorRowId(id);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por id: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolcuionProductoTemp por id: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
		  
	public Cursor ObtenerDevolucionDistribuidorProductoTempPorProductoPromocion(int productoId,int promocionId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTempPorProductoPromocion(productoId, promocionId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por productoId y promocionId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por productoId y promocionId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerDevolucionDistribuidorProductoTemp() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTemp();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerDevolucionDistribuidorProductoTempNoConfirmadas(int clienteId,int empleadoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTempNoConfirmadas(clienteId, empleadoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp no confirmadas: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp no confirmadas: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	public Cursor ObtenerDevolucionDistribuidorProductoTempNoSincronizadosPor(int clienteId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTempNoSincronizadasPor(clienteId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devolucionesDistribuidorProductoTemp no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devolucionesDistribuidorProductoTemp no sincronizadas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerDevolucionDistribuidorProductoTempPorClienteIdNoAutoventa(int clienteId,int noAutoventa) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoTempPorClienteIdNoAutoventa(clienteId, noAutoventa);
			  return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId y noAutoventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId y noAutoventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
}
