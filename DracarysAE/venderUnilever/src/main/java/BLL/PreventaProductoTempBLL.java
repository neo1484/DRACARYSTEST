package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.PreventaProductoTemp;
import DAL.PreventaProductoTempDAL;

public class PreventaProductoTempBLL
{
	MyLogBLL myLog = new MyLogBLL();
		
	public boolean BorrarPreventaProductoTempPorId(long id) throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaProductoTempDAL().BorrarPreventaProductoTempPorId(id);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventaProductoTemp por Id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarPreventaProductoTempPorClienteIdEmpleadoId(int clienteId,int empleadoId) throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaProductoTempDAL().BorrarPreventaProductoTempPorClienteIdEmpleadoId(clienteId,empleadoId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp por clienteId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public boolean BorrarPreventaProductoTempPorClienteIdEmpleadoIdNoPreventa(int clienteId,int empleadoId,int noPreventa) throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaProductoTempDAL().BorrarPreventaProductoTempPorClienteIdEmpleadoIdNoPreventa(clienteId,empleadoId,noPreventa);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp por clienteId, empleadoId, noPreventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoTemp por clienteId, empleadoId, noPreventa: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarPreventaProductoTemp(int tempId, int clienteId, int productoId, int cantidad, int cantidadPaquete,
			float monto, float descuento, float montoFinal, int empleadoId, int promocionId,float costo,int costoId,
			int noPreventa,int precioId, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId,
			float descuentoProntoPago) throws Exception
	{
		try
    	{
			long l = new PreventaProductoTempDAL().InsertarPreventaProductoTemp(tempId, clienteId, productoId, cantidad,
																				cantidadPaquete, monto, descuento, montoFinal,
																				empleadoId, promocionId, costo, costoId,
																				noPreventa, precioId, descuentoCanal, descuentoAjuste,
																				canalPrecioRutaId, descuentoProntoPago);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long ModificarPreventaProductoTemp(int id, int tempId) throws Exception
	{
	    try
	    {
	    	long l = new PreventaProductoTempDAL().ModificarPreventaProductoTemp(id,tempId);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaProductoTemp: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public ArrayList<PreventaProductoTemp> ObtenerPreventasProductoTemp() throws Exception
	{
		Cursor localCursor;
		ArrayList<PreventaProductoTemp> listadoPreventasProductoTemp = null;
	    try
	    {
	      localCursor = new PreventaProductoTempDAL().ObtenerPreventasProductoTemp();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoTempo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    
	    if(localCursor.getCount() > 0)
	    {
	    	listadoPreventasProductoTemp = new ArrayList<PreventaProductoTemp>();
	    	
	    	do
	    	{
	    		PreventaProductoTemp preventaProductoTemp = new PreventaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
	    								localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
	    								localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getInt(9), 
	    								localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13),
	    								localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16),localCursor.getInt(17),
	    								localCursor.getFloat(18));
	    		
	    		listadoPreventasProductoTemp.add(preventaProductoTemp);
	    	}
	    	while(localCursor.moveToNext());
	    }
	    
	    return listadoPreventasProductoTemp;
	  }
	  
	public ArrayList<PreventaProductoTemp> ObtenerPreventasProductoTempNoSincronizadasPor(int clienteId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PreventaProductoTemp> listadoPreventaProductoTemp = null;
		  
		  try
		  {
			  localCursor = new PreventaProductoTempDAL().ObtenerPreventasProductoTempNoSincronizadaPor(clienteId);
		  }
	      catch (Exception localException)
		  {
	    	  if(localException.getMessage().isEmpty())
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProductosTemp no sincronizadas: vacio");
	    	  }
	    	  else
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProductosTemp no sincronizadas: " + localException.getMessage());
	    	  } 
	    	  throw localException;
		  }
		  
		  if(localCursor.getCount() > 0)
		  {
			  listadoPreventaProductoTemp = new ArrayList<PreventaProductoTemp>();
			  
			  do
			  {
				  PreventaProductoTemp preventaProductoTemp = new PreventaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
						  					localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
						  					localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getInt(9), 
						  					localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13),
		    								localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16),localCursor.getInt(17),
		    								localCursor.getFloat(18));
				  
				  listadoPreventaProductoTemp.add(preventaProductoTemp);
			  }
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPreventaProductoTemp;
	  }
	  
	public ArrayList<PreventaProductoTemp> ObtenerPreventasProductoTempPor(int clienteId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PreventaProductoTemp> listadoPreventaProductoTemp = null;
		  try
		  {
			  localCursor = new PreventaProductoTempDAL().ObtenerPreventasProductoTempPor(clienteId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProductoTemp por clienteId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProductoTemp por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount() > 0)
		  {
			  listadoPreventaProductoTemp = new ArrayList<PreventaProductoTemp>();
			  
			  do
			  {
				  PreventaProductoTemp preventaProductoTemp = new PreventaProductoTemp(localCursor.getInt(0), 
						  					localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
						  					localCursor.getInt(4), localCursor.getInt(5), localCursor.getFloat(6), 
						  					localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getInt(9), 
						  					localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12),
						  					localCursor.getInt(13), localCursor.getInt(14), localCursor.getFloat(15), 
						  					localCursor.getFloat(16),localCursor.getInt(17), localCursor.getFloat(18));
				  
				  listadoPreventaProductoTemp.add(preventaProductoTemp);
			  }
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPreventaProductoTemp;
	  }
	
	public ArrayList<PreventaProductoTemp> ObtenerPreventasProductoTempPor(int clienteId,int noPreventa) throws Exception
	{
		Cursor localCursor;
		ArrayList<PreventaProductoTemp> listadoPreventaProductoTemp = null;
		try
		{
			localCursor = new PreventaProductoTempDAL().ObtenerPreventasProductoTempPor(clienteId,noPreventa);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProductoTemp por clienteId y noPreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProductoTemp por clienteId y noPreventa: " + localException.getMessage());
			} 
			throw localException;
		}
		  
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoTemp = new ArrayList<PreventaProductoTemp>();
			  
			do
			{
				PreventaProductoTemp preventaProductoTemp = new PreventaProductoTemp(localCursor.getInt(0), 
						  					localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
						  					localCursor.getInt(4), localCursor.getInt(5), localCursor.getFloat(6), 
						  					localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getInt(9), 
						  					localCursor.getInt(10), localCursor.getFloat(11), localCursor.getInt(12),
						  					localCursor.getInt(13), localCursor.getInt(14), localCursor.getFloat(15), 
						  					localCursor.getFloat(16),localCursor.getInt(17), localCursor.getFloat(18));
				  
				listadoPreventaProductoTemp.add(preventaProductoTemp);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoTemp;
	}
}
