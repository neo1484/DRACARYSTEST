package DAL;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import com.detesim.venderunilever.Login;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.AlmacenProductoWSResult;
import Utilidades.AdministradorDB;

public class AlmacenProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAlmacenProductos() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarAlmacenProductos();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesProductoo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long InsertarAlmacenProducto(ArrayList<AlmacenProductoWSResult> almacenProductos) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.InsertarAlmacenProducto(almacenProductos);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacen: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public long InsertarAlmacenProducto(int almacenId, int productoId, int saldoUnitario, int saldoPaquete,
										float costoUnitario, float costoPaquete) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarAlmacenProducto(almacenId,productoId,saldoUnitario,saldoPaquete,costoUnitario,costoPaquete);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el los productos del almacen: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerAlmacenProductoPor(int almacenId, int productoId)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerAlmacenProductoPor(almacenId,productoId);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacenProducto, por almacenId y productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacenProducto, por almacenId y productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerAlmacenProductosPor(int almacenId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerAlmacenProductosPor(almacenId);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener almacenProducto por almacenId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener almacenProducto por almacenIdd: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerAlmacenesProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerAlmacenesProducto();
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
  }
	
	public Cursor ConsolidarProductosLote() throws Exception
	{
		db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.consolidarProductosLote();
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos lote: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos lote: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor deleteProductosLote()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.deleteProductosLote();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacen: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacen : " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public Cursor ObtenerExistenciaProductoAlmacenProducto(int almacenId, int productoId,int conversionProducto,int cantidadSolicitadaEnUnidades)
	    throws Exception
  	{
	    db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerExistenciaProductoAlmacenProducto(almacenId,productoId,conversionProducto,cantidadSolicitadaEnUnidades);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
  	}
	
	public Cursor ObtenerExistenciaProductoEnAlmacenPor(int almacenId, int productoId) throws Exception
  	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.ObtenerExistenciaProductoEnAlmacenPor(almacenId,productoId);
			return localCursor;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto en el almacenProducto: vacio");
	    	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del product  en el almacenProducto: " + localException.getMessage());
	    	} 
			throw localException;
	    }
		finally
		{
			db.CloseDB();
    	}
  	}

	public Cursor ObtenerInventarioAlmacenProductoPor(int proveedorId,int categoriaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.ObtenerInventarioAlmacenProductoPor(proveedorId, categoriaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario del almacenProducto: " + localException.getMessage());
			} 
			throw localException;
	  	}
		finally
		{
			db.CloseDB();
		}
	}
}
