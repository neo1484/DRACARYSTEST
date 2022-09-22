package BLL;

import java.util.ArrayList;
import Clases.EncuestaDetalle;
import Clases.EncuestaDetalleWSResult;
import DAL.EncuestaDetalleDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class EncuestaDetalleBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestasDetalle() throws Exception
	{
	    try
	    {
	    	boolean bool = new EncuestaDetalleDAL().BorrarEncuestasDetalle();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasDetalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasDetalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarEncuestaDetalle(ArrayList<EncuestaDetalleWSResult>  encuestasDetalle) throws Exception
	{
		try
		{
			long l = new EncuestaDetalleDAL().InsertarEncuestaDetalle(encuestasDetalle);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los detalles de la encuesta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los detalles de la encuesta: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<EncuestaDetalle> ObtenerEncuestaDetalle() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<EncuestaDetalle> listadoEncuestaDetalle = null;
	    
	    try
	    {
	    	localCursor = new EncuestaDetalleDAL().ObtenerEncuestasDetalle();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas detalle: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoEncuestaDetalle = new ArrayList<EncuestaDetalle>();
	        	do
	        	{
	        		EncuestaDetalle localEncuestaDetalle = new EncuestaDetalle(localCursor.getInt(1),localCursor.getInt(2),localCursor.getString(3),localCursor.getString(4),
	        																localCursor.getString(5),localCursor.getString(6),localCursor.getString(7).equals("1")?true:false,
	        																localCursor.getInt(8),localCursor.getString(9));
	        		
	        		listadoEncuestaDetalle.add(localEncuestaDetalle);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoEncuestaDetalle;
	}
	
	public EncuestaDetalle ObtenerEncuestaDetalle(int encuestaId) throws Exception
	{
	    Cursor localCursor = null;
	    EncuestaDetalle localEncuestaDetalle = null;
	    
	    try
	    {
	    	localCursor = new EncuestaDetalleDAL().ObtenerEncuestaDetallePor(encuestaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestaDetalle por encuestaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestaDetalle por encuestaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
        		localEncuestaDetalle = new EncuestaDetalle(localCursor.getInt(1),localCursor.getInt(2),localCursor.getString(3),localCursor.getString(4),
						localCursor.getString(5),localCursor.getString(6),localCursor.getString(7).equals("1")?true:false,
						localCursor.getInt(8),localCursor.getString(9));
	        }
	    }
	    
	    return localEncuestaDetalle;
	}

}
