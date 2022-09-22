package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.DevolucionDistribuidorProductoWSResult;
import Utilidades.AdministradorDB;

public class DevolucionDistribuidorProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDevolucionesDistribuidorProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarDevolucionesDistribuidorProducto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacen distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacen distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarDevolucionDistribuidorProducto(ArrayList<DevolucionDistribuidorProductoWSResult> devolucionesDistribuidorProducto)
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarDevolucionDistribuidorProducto(devolucionesDistribuidorProducto);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la devolucion del distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la devolucion del distribuidor: " + localException.getMessage());
	    	} 
	    	return 0;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long InsertarDevolucionDistribuidorProducto(int almacenDevolucionId, int productoId, int cantidad,
													   int cantidadPaquete, boolean estadoSincronizacion)
	{
		db.OpenDB();
		try
		{
			long l = db.insertarDevolucionDistribuidorProducto(almacenDevolucionId,productoId,cantidad,cantidadPaquete,
					estadoSincronizacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar al almacen producto del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar al almacen producto del distribuidor: " + localException.getMessage());
			}
			return 0;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long ModificarCantidadDevolucionesDistribuidorProducto(int id, int productoId, int cantidad) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	int i = db.modificarCantidadDevolucionDistribuidorProducto(id,productoId,cantidad);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la devolucion distribuidor producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la devolucion distribuidor producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long ModificarCantidadesDevolucionesDistribuidorProducto(int almacenDevolucionId, int productoId, int cantidad, int cantidadPaquete) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	int i = db.modificarCantidadesDevolucionDistribuidorProducto(almacenDevolucionId, productoId, cantidad, cantidadPaquete);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades de la devolucion distribuidor producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"EError al modificar las cantidades de la devolucion distribuidor producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long ModificarDevolucionesDistribuidorProductoPorAlmacenYProducto(int almacenDevolucionId, int productoId, int cantidad, int cantidadPaquete, boolean estado) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarDevolucionDistribuidorProductoPorAlmacenYProducto(almacenDevolucionId,productoId,cantidad,cantidadPaquete,estado);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la devolucion distribuidor producto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al modificar la devolucion distribuidor producto: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerDevolucionesDistribuidorProducto() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionesDistribuidorProducto();
			  return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones distribuidor producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones distribuidor producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public Cursor ObtenerDevolucionesDistribuidorProductoPor(int almacenDevolucionId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionesDistribuidorProductoPor(almacenDevolucionId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor producto por almacenDevolucionProductoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor producto por almacenDevolucionProductoId: " + localException.getMessage());
				} 
		  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	public Cursor ObtenerDevolucionesDistribuidorProductoPorProductoId(int almacenDevolucionId, int productoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoPorProductoId(almacenDevolucionId, productoId);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen devoluciones producto por almacenDevolucionProductoId y productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen devoluciones producto por almacenDevolucionProductoId y productoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor getCantidadUnitariaTotal(int productoId, int almacenDevolucionId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.DevolucionDistribuidorProductoGetCantidadUnitariaTotal(productoId,almacenDevolucionId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la cantidad unitaria total por productoId y almacenDevolucionId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la cantidad unitaria total por productoId y almacenDevolucionId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public Cursor ObtenerExistenciaDevolucionDistribuidorProducto(int productoId,int conversionProducto,int cantidadSolicitadaEnUnidades)
		    throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerExistenciaDevolucionDistribuidorProducto(productoId, conversionProducto, cantidadSolicitadaEnUnidades);
			return localCursor;
    	}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en la devolucionDistribuidorProducto: vacio");
	    	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en la devolucionDistribuidorProducto: " + localException.getMessage());
    		} 
	    	throw localException;
	    }
		finally
		{
			db.CloseDB();
	    }
  	}

	public Cursor ObtenerDevolucionesDistribuidorProductoPorProductoId(int productoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDevolucionDistribuidorProductoPorProductoId(productoId);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen devoluciones producto por almacenDevolucionProductoId y productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen devoluciones producto por almacenDevolucionProductoId y productoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarCantidadesDevolucionesDistribuidorProducto(int productoId, int cantidad, int cantidadPaquete) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	int i = db.modificarCantidadesDevolucionDistribuidorProducto(productoId, cantidad, cantidadPaquete);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades de la devolucion distribuidor producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"EError al modificar las cantidades de la devolucion distribuidor producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
