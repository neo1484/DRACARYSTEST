package BLL;

import java.util.ArrayList;
import Clases.PreventaBonificacion;
import Clases.PreventaBonificacionWSResult;
import DAL.PreventaBonificacionDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PreventaBonificacionBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarPreventasBonificacion() throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaBonificacionDAL().BorrarPreventasBonificacion();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasBonificcion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasBonificacion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarPreventaBonificacionPor(int rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaBonificacionDAL().BorrarPreventaBonificacionPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasBonificacion por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasBonificacion por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarPreventaBonificacion(ArrayList<PreventaBonificacionWSResult> preventasBonificacion) throws Exception
	{
		try
		{
			long l = new PreventaBonificacionDAL().InsertarPreventaBonificacion(preventasBonificacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas bonificadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas bonificadas: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public PreventaBonificacion ObtenerPreventaBonificacionPor(int preventaId) throws Exception
	{
	    Cursor localCursor = null;
	    PreventaBonificacion preventaBonificacion = null;
	    
	    try
	    {
	    	localCursor = new PreventaBonificacionDAL().ObtenerPreventaBonificacionPorPreventaId(preventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventaBonificacion por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventaBonificacion por preventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	preventaBonificacion = new PreventaBonificacion(localCursor.getInt(1),localCursor.getInt(2));
	        }
	    }
	    
	    return preventaBonificacion;
	}
	
	public ArrayList<PreventaBonificacion> ObtenerPreventasBonificacion() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<PreventaBonificacion> listadoPreventaBonificacion = null;
	    
	    try
	    {
	    	localCursor = new PreventaBonificacionDAL().ObtenerPreventasBonificacion();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasBonificacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasBonificacion: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoPreventaBonificacion = new ArrayList<PreventaBonificacion>();
	        	do
	        	{
	        		PreventaBonificacion localPreventaBonificacion = new PreventaBonificacion(localCursor.getInt(1),localCursor.getInt(2));
	        		
	        		listadoPreventaBonificacion.add(localPreventaBonificacion);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoPreventaBonificacion;
	}
}
