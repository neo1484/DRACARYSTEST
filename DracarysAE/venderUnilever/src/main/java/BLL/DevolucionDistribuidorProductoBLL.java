package BLL;

import java.util.ArrayList;
import Clases.DevolucionDistribuidorProducto;
import Clases.DevolucionDistribuidorProductoWSResult;
import Clases.Producto;
import DAL.DevolucionDistribuidorProductoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class DevolucionDistribuidorProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public int DevolucionDistribuidorProductoGetCantidadUnitariaTotal(int productoId, int almacenDevolucionId) throws Exception
	{
		Cursor localCursor=null;
		DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
		Producto producto;
		int cantidadUnitariaTotal = 0;
		
		try
		{
			localCursor = new DevolucionDistribuidorProductoDAL().getCantidadUnitariaTotal(productoId, almacenDevolucionId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount() > 0)
		{
			localDevolucionDistribuidorProducto = new DevolucionDistribuidorProducto(localCursor.getInt(0), localCursor.getInt(1), 
					localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), localCursor.getString(5).equals("1")?true:false);
			
			producto = null;
			try
			{
				producto = new ProductoBLL().ObtenerProductoPor(localDevolucionDistribuidorProducto.get_productoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: " + localException.getMessage());
				} 
				throw localException;
			}
			
			if(producto != null)
			{
				cantidadUnitariaTotal = localDevolucionDistribuidorProducto.get_cantidadPaquete() * producto.get_conversion() 
						+ localDevolucionDistribuidorProducto.get_cantidad(); 
			}
		}
		return cantidadUnitariaTotal;
	}
	  
	public boolean BorrarDevolucionesDistribuidorProducto() throws Exception
	{
		try
    	{
			boolean bool = new DevolucionDistribuidorProductoDAL().BorrarDevolucionesDistribuidorProducto();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la devolucion distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la devolucion distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarDevolucionDistribuidorProducto(ArrayList<DevolucionDistribuidorProductoWSResult> devolucionesDistribuidorProducto) throws Exception
	{
		try
    	{
			long l = new DevolucionDistribuidorProductoDAL().InsertarDevolucionDistribuidorProducto(devolucionesDistribuidorProducto);
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
	    	throw localException;
	    }
	}

	public long InsertarDevolucionDistribuidorProducto(int almacenDevolucionId, int productoId, int cantidad,
													   int cantidadPaquete, boolean estadoSincronizacion) throws Exception
	{
		try
		{
			long l = new DevolucionDistribuidorProductoDAL().InsertarDevolucionDistribuidorProducto(almacenDevolucionId,productoId,
					cantidad,cantidadPaquete,estadoSincronizacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacen distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos del almacen distribuidor: " + localException.getMessage());
			}
			throw localException;
		}
	}
	  
	public long ModificarCantidadDevolucionDistribuidorProducto(int paramInt1, int paramInt2, int paramInt3) throws Exception
	{
		try
    	{
			long l = new DevolucionDistribuidorProductoDAL().ModificarCantidadDevolucionesDistribuidorProducto(paramInt1, paramInt2, paramInt3);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los productos del almacen distribuidor distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los productos del almacen distribuidor distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long ModificarCantidadesDevolucionDistribuidorProducto(int almacenDevolucionId, int productoId, int cantidad, 
																int cantidadPaquete) throws Exception
	{	
	    try
	    {
	      long l = new DevolucionDistribuidorProductoDAL().ModificarCantidadesDevolucionesDistribuidorProducto(almacenDevolucionId,
	    		  productoId,cantidad,cantidadPaquete);
	      return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen distribuidor: " + localException.getMessage());
	    	}
	      throw localException;
	    }
	}
	  
	public long ModificarDevolucionDistribuidorProductoPorAlmacenYProducto(int almacenDevolucionId, int productoId, int cantidad, 
																			int cantidadPaquete, boolean estado) throws Exception
	{
		try
	    {
			long l = new DevolucionDistribuidorProductoDAL().ModificarDevolucionesDistribuidorProductoPorAlmacenYProducto(almacenDevolucionId,
													productoId,cantidad,cantidadPaquete,estado);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del producto del almacen distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del producto del almacen distribuidor: " + localException.getMessage());
	    	}
	      throw localException;
	    }
	}
	  
	public DevolucionDistribuidorProducto ObtenerDevolucionDistribuidorProductoPorProductoId(int almacenDevolucionId,
					int productoId) throws Exception
	{
		Cursor localCursor ;
		DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;
		
		try
		{
			localCursor = new DevolucionDistribuidorProductoDAL().ObtenerDevolucionesDistribuidorProductoPorProductoId(
					almacenDevolucionId,productoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolucionId y productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolucionId y productoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		 if(localCursor.getCount()>0)
		 {
			 localDevolucionDistribuidorProducto = new DevolucionDistribuidorProducto(localCursor.getInt(0), localCursor.getInt(1), 
					 localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4),localCursor.getString(5).equals("1")?true:false); 
		 }
	      
		 return localDevolucionDistribuidorProducto;
	  }
	  
	public ArrayList<DevolucionDistribuidorProducto>ObtenerDevolucionesDistribuidorProductoPor(int almacenDevolucionId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<DevolucionDistribuidorProducto> listadoDevolucionDistribuidorProducto = null;
		  try
		  {
			  localCursor = new DevolucionDistribuidorProductoDAL().ObtenerDevolucionesDistribuidorProductoPor(almacenDevolucionId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolcuionId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolcuionId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if (localCursor.getCount() > 0) 
		  {
			  listadoDevolucionDistribuidorProducto = new ArrayList<DevolucionDistribuidorProducto>();
			  do
			  {
				  DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = new DevolucionDistribuidorProducto(
						  localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
						  localCursor.getInt(3), localCursor.getInt(4), localCursor.getString(5).equals("1")?true:false);
		        
				  listadoDevolucionDistribuidorProducto.add(localDevolucionDistribuidorProducto);
			  } 
			  while (localCursor.moveToNext());
	      }
		  return listadoDevolucionDistribuidorProducto;
	  }
	  
	public ArrayList<DevolucionDistribuidorProducto> ObtenerDevolucionesDistribuidorProducto() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<DevolucionDistribuidorProducto> listadoDevolucionDistribuidorProducto = null;
		  try
		  {
			  localCursor = new DevolucionDistribuidorProductoDAL().ObtenerDevolucionesDistribuidorProducto();
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
		  
		  if(localCursor.getCount() > 0)
		  {
			  listadoDevolucionDistribuidorProducto = new ArrayList<DevolucionDistribuidorProducto>();
		       do
		       {
		    	   DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = new DevolucionDistribuidorProducto(
		    			   localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
		    			   localCursor.getInt(3), localCursor.getInt(4), localCursor.getString(5).equals("1")?true:false);
		    	   
		    	   listadoDevolucionDistribuidorProducto.add(localDevolucionDistribuidorProducto);
		       } 
		       while (localCursor.moveToNext());
		  }
	      return listadoDevolucionDistribuidorProducto;
	  }

	public DevolucionDistribuidorProducto ObtenerExistenciaDevolucionDistribuidorProducto(int productoId,
														int conversionProducto, int cantidadSolicitadaEnUnidades) throws Exception
	{		  
		Cursor localCursor;
		DevolucionDistribuidorProducto devolucionDistribuidorProducto =null;
		try
		{
			localCursor = new DevolucionDistribuidorProductoDAL().ObtenerExistenciaDevolucionDistribuidorProducto(
											productoId, conversionProducto, cantidadSolicitadaEnUnidades);
		}
		catch(Exception localException)
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
		  
		if(localCursor.getCount()>0)
		{
			devolucionDistribuidorProducto = new DevolucionDistribuidorProducto(localCursor.getInt(0), localCursor.getInt(1), 
			localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), localCursor.getString(5).equals("1")?true:false);
		  }
		  
		  return devolucionDistribuidorProducto;
	  }
	
	public DevolucionDistribuidorProducto ObtenerDevolucionDistribuidorProductoPorProductoId(int productoId) throws Exception
	{
		Cursor localCursor ;
		DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;

		try
		{
			localCursor = new DevolucionDistribuidorProductoDAL().ObtenerDevolucionesDistribuidorProductoPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolucionId y productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolucionId y productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if(localCursor.getCount()>0)
		{
			localDevolucionDistribuidorProducto = new DevolucionDistribuidorProducto(localCursor.getInt(0), localCursor.getInt(1), 
			 localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4),localCursor.getString(5).equals("1")?true:false); 
		}
  
		return localDevolucionDistribuidorProducto;
	}

	public long ModificarCantidadesDevolucionDistribuidorProducto(int productoId, int cantidad, 
			int cantidadPaquete) throws Exception
	{	
		try
		{
			long l = new DevolucionDistribuidorProductoDAL().ModificarCantidadesDevolucionesDistribuidorProducto(productoId,
									cantidad,cantidadPaquete);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar las cantidades del almacen distribuidor: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public int ObtenerDevolucionDistribuidorProductoSaldoPorProductoId(int productoId,int productoConversion) throws Exception
	{
		Cursor localCursor ;
		DevolucionDistribuidorProducto localDevolucionDistribuidorProducto = null;

		try
		{
			localCursor = new DevolucionDistribuidorProductoDAL().ObtenerDevolucionesDistribuidorProductoPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolucionId y productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacen del distribuidor por almacenDevolucionId y productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if(localCursor.getCount()>0)
		{
			localDevolucionDistribuidorProducto = new DevolucionDistribuidorProducto(localCursor.getInt(0), localCursor.getInt(1), 
			 localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4),localCursor.getString(5).equals("1")?true:false); 
		}
  
		return localDevolucionDistribuidorProducto.get_cantidadPaquete() * productoConversion + localDevolucionDistribuidorProducto.get_cantidad();
	}
}
