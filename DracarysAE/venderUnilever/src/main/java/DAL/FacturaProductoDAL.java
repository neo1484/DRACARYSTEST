package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class FacturaProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarFacturasProducto() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarFacturasProducto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public long InsertarFacturaProducto(long facturaId,int productoId, int promocionId, int cantidad,int cantidadPaquete,
										float monto, float descuento,	float montoFinal, int clienteId, int empleadoId,
										boolean sincronizacion,int precioId,int noFactura) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarFacturaProducto(facturaId,productoId, promocionId, cantidad, cantidadPaquete, monto, descuento, 
												montoFinal,clienteId, empleadoId, sincronizacion, precioId, noFactura);
			
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura producto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura producto: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor obtenerFacturasProducto() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasProducto();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor obtenerFacturasProductoNoSincronizadasPorFacturaId(int facturaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasProductoNoSincronizadasPorFacturaId(facturaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto no sincronizadas por facturaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto no sincronizadas por facturaId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor obtenerFacturasProductoPorFacturaId(int facturaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFacturasProductoPorFacturaId(facturaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto por facturaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto por facturaId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public int ModificarFacturaProductoSincronizacion(int rowId,boolean sincronizacion) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarFacturaProductoSincronizacion(rowId, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la facturaProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la facturaProducto: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
