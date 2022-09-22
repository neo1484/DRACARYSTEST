package BLL;

import java.util.ArrayList;
import Clases.EncuestaRespuesta;
import DAL.EncuestaRespuestaDAL;
import android.database.Cursor;

public class EncuestaRespuestaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestasRespuesta() throws Exception
	{
	    try
	    {
	    	boolean bool = new EncuestaRespuestaDAL().BorrarEncuestasRespuesta();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasRespuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasRespuesta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarEncuestaRespuesta(int encuestaRespuestaId,int detalleId,int clienteId,String respuesta,String especificacion,
										String observacion,int empleadoId,int dia,int mes,int anio) throws Exception
	{
		try
		{
			long l = new EncuestaRespuestaDAL().InsertarEncuestaRespuesta(encuestaRespuestaId, detalleId, clienteId, respuesta, especificacion, observacion,
																		empleadoId,dia,mes,anio);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la encuestaRespuesta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la encuestaRespuesta: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<EncuestaRespuesta> ObtenerEncuestaRespuestaPor(int encuestaRespuestaId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<EncuestaRespuesta> listadoEncuestaRespuesta = null;
	    
	    try
	    {
	    	localCursor = new EncuestaRespuestaDAL().ObtenerEncuestaRespuestaPor(encuestaRespuestaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuesta respuesta por encuestaRespuestaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuesta respuesta por encuestaRespuestaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoEncuestaRespuesta = new ArrayList<EncuestaRespuesta>();
	        	do
	        	{
	        		EncuestaRespuesta localEncuestaRespuesta = new EncuestaRespuesta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getString(4),
	        																localCursor.getString(5),localCursor.getString(6),localCursor.getInt(7),localCursor.getInt(8),
	        																localCursor.getInt(9),localCursor.getInt(10));
	        		
	        		listadoEncuestaRespuesta.add(localEncuestaRespuesta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoEncuestaRespuesta;
	}
	
	public EncuestaRespuesta ObtenerEncuestaRespuesta(int encuestaRespuestaId) throws Exception
	{
	    Cursor localCursor = null;
	    EncuestaRespuesta localEncuestaRespuesta = null;
	    
	    try
	    {
	    	localCursor = new EncuestaRespuestaDAL().ObtenerEncuestaRespuestaPor(encuestaRespuestaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestaRespuesta por encuestaRespuestaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestaRespuesta por encuestaRespuestaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
        		localEncuestaRespuesta = new EncuestaRespuesta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getString(4),
						localCursor.getString(5),localCursor.getString(6),localCursor.getInt(7),localCursor.getInt(8),
						localCursor.getInt(9),localCursor.getInt(10));
	        }
	    }
	    
	    return localEncuestaRespuesta;
	}
	
	public ArrayList<EncuestaRespuesta> ObtenerEncuestaRespuestaRangoDetalle(int clienteId, int detalleInicio,int detalleFin) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<EncuestaRespuesta> listadoEncuestaRespuesta = null;
	    
	    try
	    {
	    	localCursor = new EncuestaRespuestaDAL().ObtenerEncuestasRespuestaRangoDetalle(clienteId, detalleInicio, detalleFin);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuesta respuesta en un rango detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuesta respuesta en un rango detalle: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoEncuestaRespuesta = new ArrayList<EncuestaRespuesta>();
	        	do
	        	{
	        		EncuestaRespuesta localEncuestaRespuesta = new EncuestaRespuesta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getString(4),
	        																localCursor.getString(5),localCursor.getString(6),localCursor.getInt(7),localCursor.getInt(8),
	        																localCursor.getInt(9),localCursor.getInt(10));
	        		
	        		listadoEncuestaRespuesta.add(localEncuestaRespuesta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoEncuestaRespuesta;
	}
	
	public ArrayList<EncuestaRespuesta> ObtenerEncuestaRespuestaRangoDetalleNoSincro(int clienteId, int detalleInicio,int detalleFin) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<EncuestaRespuesta> listadoEncuestaRespuesta = null;
	    
	    try
	    {
	    	localCursor = new EncuestaRespuestaDAL().ObtenerEncuestasRespuestaRangoDetalleNoSincro(clienteId, detalleInicio, detalleFin);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuesta respuesta en un rango detalle no sincro: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuesta respuesta en un rango detalle no sincro: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoEncuestaRespuesta = new ArrayList<EncuestaRespuesta>();
	        	do
	        	{
	        		EncuestaRespuesta localEncuestaRespuesta = new EncuestaRespuesta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getString(4),
	        																localCursor.getString(5),localCursor.getString(6),localCursor.getInt(7),localCursor.getInt(8),
	        																localCursor.getInt(9),localCursor.getInt(10));
	        		
	        		listadoEncuestaRespuesta.add(localEncuestaRespuesta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoEncuestaRespuesta;
	}
	
	public long ModificarEncuestaRespuestaSincro(int encuestaRespuestaId,int especificacion) throws Exception
	{
	    try
	    {
	    	long l = new EncuestaRespuestaDAL().ModificarEncuestaRespuestaSincro(encuestaRespuestaId, especificacion);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro de la encuesta respuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro de la encuesta respuesta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
