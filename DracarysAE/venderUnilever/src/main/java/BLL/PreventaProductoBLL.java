package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.PreventaProducto;
import DAL.PreventaProductoDAL;

public class PreventaProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreventaProductoPorId(long id) throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaProductoDAL().BorrarPreventaProductoPorId(id);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto por Id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public boolean BorrarPreventasProducto() throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaProductoDAL().BorrarPreventasProducto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarPreventaProducto(int preventaId, int productoId, int cantidad, int cantidadPaquete, 
										float monto, float descuento, float montoFinal, int empleadoId,
										int promocionId, boolean estado, float costo, int costoId, int noPreventa, 
										int precioId, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId,
										float descuentoProntoPago) throws Exception
	{
	    try
	    {
	    	long l = new PreventaProductoDAL().InsertarPreventaProducto(preventaId, productoId, cantidad, cantidadPaquete,
						monto, descuento, montoFinal, empleadoId, promocionId, estado, costo, costoId, noPreventa, precioId,
						descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProdcuto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProdcuto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long ModificarPreventaProductoNoSincronizadaPor(int id) throws Exception
	{
	    try
	    {
	    	int i = new PreventaProductoDAL().ModificarPreventaProductoNoSincronizadaPor(id);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaProducto no sincronizada: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaProducto no sincronizada: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarPreventaProductoNoSincronizadaPor(int preventaId,boolean estado) throws Exception
	{
	    try
	    {
	    	int i = new PreventaProductoDAL().ModificarPreventaProductoNoSincronizadaPor(preventaId, estado);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProducto por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProducto por preventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long ModificarPreventaProductoNoSincronizadaPor(int id, int preventaId) throws Exception
	{
		try
	    {
			int i = new PreventaProductoDAL().ModificarPreventaProductoNoSincronizadaPor(id,preventaId);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaProducto no sincronizada: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaProducto no sincronizada: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public ArrayList<PreventaProducto> ObtenerPreventasProducto() throws Exception
	{
		Cursor localCursor;
		ArrayList<PreventaProducto> listadoPreventaProducto = null;
		try
    	{
			localCursor = new PreventaProductoDAL().ObtenerPreventasProducto();	      
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener listado preventaProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener listado preventaProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProducto = new ArrayList<PreventaProducto>();
			
			do
			{
				PreventaProducto preventaProducto = new PreventaProducto(localCursor.getInt(0),
													localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
													localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6), 
													localCursor.getFloat(7), localCursor.getInt(8), localCursor.getInt(9), 
													localCursor.getString(10).equals("1")?true:false,
													localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13),
													localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16),
													localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoPreventaProducto.add(preventaProducto);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoPreventaProducto;	  
	}
	  
	public ArrayList<PreventaProducto> ObtenerPreventasProductoNoSincronizadas() throws Exception
	{
		Cursor localCursor;
		ArrayList<PreventaProducto> listadoPreventaProducto = null;
	    try
	    {
	    	localCursor = new PreventaProductoDAL().ObtenerPreventasProductoNoSincronizadas(); 
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    
	    if(localCursor.getCount()>0)
	    {
	    	listadoPreventaProducto = new ArrayList<PreventaProducto>();
	    	do
	    	{
	    		PreventaProducto preventaProducto = new PreventaProducto(localCursor.getInt(0),
						localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
						localCursor.getInt(4), localCursor.getFloat(5),localCursor.getFloat(6), 
						localCursor.getFloat(7), localCursor.getInt(8), localCursor.getInt(9), 
						localCursor.getString(10).equals("1")?true:false, localCursor.getFloat(11),
						localCursor.getInt(12), localCursor.getInt(13), localCursor.getInt(14), 
						localCursor.getFloat(15), localCursor.getFloat(16),	localCursor.getInt(17), 
						localCursor.getFloat(18));
	    		
	    		listadoPreventaProducto.add(preventaProducto);
	    	}
	    	while(localCursor.moveToNext());
	    }
	    
	    return listadoPreventaProducto;
	  }
	  
	public ArrayList<PreventaProducto> ObtenerPreventasProductoNoSincronizadasPor(int preventaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PreventaProducto> listadoPreventaProducto = null;
		  try
		  {
			  localCursor = new PreventaProductoDAL().ObtenerPreventasProductoNoSincronizadasPor(preventaId);
		  }
		  catch(Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProducto no sincronizada: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProducto no sincronizada: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount() > 0)
		  {
			  listadoPreventaProducto = new ArrayList<PreventaProducto>();
			  
			  do
			  {
				  PreventaProducto preventaProducto = new PreventaProducto(localCursor.getInt(0),
							localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
							localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6), 
							localCursor.getFloat(7), localCursor.getInt(8), localCursor.getInt(9), 
							localCursor.getString(10).equals("1")?true:false, localCursor.getFloat(11),
							localCursor.getInt(12), localCursor.getInt(13), localCursor.getInt(14),
							localCursor.getFloat(15), localCursor.getFloat(16),	localCursor.getInt(17), 
							localCursor.getFloat(18));
				  
				  listadoPreventaProducto.add(preventaProducto);
			  }
			  while(localCursor.moveToNext());
		  }
		  return listadoPreventaProducto;
	  }
	  
	public ArrayList<PreventaProducto> ObtenerPreventasProductoPor(int preventaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PreventaProducto> listadoPreventaProducto = null;
		  
		  try
		  {
			  localCursor = new PreventaProductoDAL().ObtenerPreventasProductoPor(preventaId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto por preventaId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto por preventaId: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoPreventaProducto = new ArrayList<PreventaProducto>();
			  
			  do
			  {
				  PreventaProducto preventaProducto = new PreventaProducto(localCursor.getInt(0),
							localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
							localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6), 
							localCursor.getFloat(7), localCursor.getInt(8), localCursor.getInt(9), 
							localCursor.getString(10).equals("1")?true:false, localCursor.getFloat(11),
							localCursor.getInt(12), localCursor.getInt(13), localCursor.getInt(14), 
							localCursor.getFloat(15), localCursor.getFloat(16), localCursor.getInt(17), 
							localCursor.getFloat(18));
				  
				  listadoPreventaProducto.add(preventaProducto);
			  }
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPreventaProducto;
	  }
	
	public boolean BorrarPreventaProductoPorPreventaId(long preventaId) throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaProductoDAL().BorrarPreventaProductoPorPreventaId(preventaId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProducto por preventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public PreventaProducto ObtenerPreventasProductoPorRowId(int rowId) throws Exception
	{
		Cursor localCursor;
		PreventaProducto preventaProducto = null;
		try
    	{
			localCursor = new PreventaProductoDAL().ObtenerPreventasProductoPorRowId(rowId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProducto por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventaProducto por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if(localCursor.getCount() > 0)
		{
			preventaProducto = new PreventaProducto(localCursor.getInt(0),
									localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6), 
									localCursor.getFloat(7), localCursor.getInt(8), localCursor.getInt(9), 
									localCursor.getString(10).equals("1")?true:false,
									localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13),
									localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16),
									localCursor.getInt(17), localCursor.getFloat(18));
		}
		
		return preventaProducto;	  
	}
}
