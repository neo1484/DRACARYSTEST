package BLL;

import java.util.ArrayList;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.DevolucionDistribuidor;
import Clases.DevolucionDistribuidorWSResult;
import DAL.DevolucionDistribuidorDAL;

public class DevolucionDistribuidorBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarDevolucionesDistribuidor() throws Exception
	{
		try
		{
			boolean bool = new DevolucionDistribuidorDAL().BorrarDevolucionesDistribuidor();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devoluciones distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devoluciones distribuidor: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	  
	public long InsertarDevolucionDistribuidor(DevolucionDistribuidorWSResult devolucionDistribuidor) throws Exception
	{
		long l = 0;
		try
    	{
			l = new DevolucionDistribuidorDAL().InsertarDevolucionDistribuidor(devolucionDistribuidor);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las devoluciones del distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las devoluciones del distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public long InsertarDevolucionDistribuidor(int almacenDevolucionId, int distribuidorId, int anio, int mes, int dia,
											   int estadoId, boolean estadoSincronizacion) throws Exception
	{
		long l = 0;
		try
		{
			l = new DevolucionDistribuidorDAL().InsertarDevolucionDistribuidor(almacenDevolucionId,distribuidorId,anio,mes,dia,estadoId,estadoSincronizacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacen distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacen distribuidor: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public long ModificarDevolucionDistribuidorId(long id) throws Exception
	{
	    try
	    {
	    	long l = new DevolucionDistribuidorDAL().ModificarDevolucionDistribuidorId(id);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el almacenDevolucionId por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el almacenDevolucionId por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public DevolucionDistribuidor ObtenerDevolucionDistribuidorPor(int almacenDevolucionId) throws Exception
	{
		Cursor localCursor;
		DevolucionDistribuidor localDevolucionDistribuidor = null;
		try
    	{
			localCursor = new DevolucionDistribuidorDAL().ObtenerDevolucionesDistribuidorPor(almacenDevolucionId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones del distribuidor por almacenDevolcuionId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones del distribuidor por almacenDevolcuionId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localDevolucionDistribuidor = new DevolucionDistribuidor(localCursor.getInt(0), 
    		  		localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
    		  		localCursor.getInt(5), localCursor.getInt(6), localCursor.getString(7).equals("1")?true:false);
		}
	      return localDevolucionDistribuidor;
	}
	  
	public DevolucionDistribuidor ObtenerDevolucionDistribuidorPorDistribuidor(int distribuidorId) throws Exception
	{
		Cursor localCursor;
		DevolucionDistribuidor localDevolucionDistribuidor=null;
	    try
	    {
	    	localCursor = new DevolucionDistribuidorDAL().ObtenerDevolucionDistribuidorPorDistirbuidor(distribuidorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones del distribuidor por distribuidorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones del distribuidor por distribuidorId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    
	    if(localCursor.getCount() > 0)
	    {
			localDevolucionDistribuidor = new DevolucionDistribuidor(localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
						localCursor.getInt(6), localCursor.getString(7).equals("1")?true:false);
	    }
	      
	    return localDevolucionDistribuidor;
	}
	  
	public DevolucionDistribuidor ObtenerDevolucionDistribuidorPorDistribuidorYFecha(int distribuidorId, int dia, 
			int mes, int anio) throws Exception
	{
		Cursor localCursor = null;
		DevolucionDistribuidor localDevolucionDistribuidor = null;
		
		try
    	{
			localCursor = new DevolucionDistribuidorDAL().ObtenerDevolucionDistribuidorPorDistribuidorYFecha(
																						distribuidorId,dia,mes,anio);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones del distribuidor por distribuidorId y fecha: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones del distribuidor por distribuidorId y fecha: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if (localCursor.getCount() > 0)
		{
	        localDevolucionDistribuidor = new DevolucionDistribuidor(localCursor.getInt(0), localCursor.getInt(1), 
	        			localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
	        			localCursor.getInt(6), localCursor.getString(7).equals("1")?true:false);
		}
		
		return localDevolucionDistribuidor;
	  }
	  
	public ArrayList<DevolucionDistribuidor> ObtenerDevolucionesDistribuidor() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<DevolucionDistribuidor> listadoDevolucionDistribuidor = null;
	    
		  try
		  {
			  localCursor = new DevolucionDistribuidorDAL().ObtenerDevolucionesDistribuidor();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones distribuidor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las devoluciones distribuidor: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if (localCursor.getCount() > 0) 
		  {
		        listadoDevolucionDistribuidor = new ArrayList<DevolucionDistribuidor>();
		        do
		        {
		        	DevolucionDistribuidor devolucionDistribuidor = new DevolucionDistribuidor(localCursor.getInt(0), 
		        			localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
		        			localCursor.getInt(5), localCursor.getInt(6), localCursor.getString(7).equals("1")?true:false);
		        
		        	listadoDevolucionDistribuidor.add(devolucionDistribuidor);
		        }
		        while(localCursor.moveToNext());
		  }
		  
		  return listadoDevolucionDistribuidor;
	  }
}
