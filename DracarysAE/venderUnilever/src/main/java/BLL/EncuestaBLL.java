package BLL;

import java.util.ArrayList;
import Clases.Encuesta;
import Clases.EncuestaWSResult;
import DAL.EncuestaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class EncuestaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestas() throws Exception
	{
	    try
	    {
	    	boolean bool = new EncuestaDAL().BorrarEncuestas();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarEncuesta(ArrayList<EncuestaWSResult> encuestas)
				throws Exception
	{
		try
		{
			long l = new EncuestaDAL().InsertarEncuesta(encuestas);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las encuestas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las encuestas: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<Encuesta> ObtenerEncuestas() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<Encuesta> listadoEncuesta = null;
	    
	    try
	    {
	    	localCursor = new EncuestaDAL().ObtenerEncuestas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoEncuesta = new ArrayList<Encuesta>();
	        	do
	        	{
	        		Encuesta localEncuesta = new Encuesta(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3).equals("1")?true:false);
	        		
	        		listadoEncuesta.add(localEncuesta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoEncuesta;
	}
	
	public Encuesta ObtenerEncuesta(int encuestaId) throws Exception
	{
	    Cursor localCursor = null;
	    Encuesta localEncuesta = null;
	    
	    try
	    {
	    	localCursor = new EncuestaDAL().ObtenerEncuestaPor(encuestaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta por encuestaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta por encuestaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
        		localEncuesta = new Encuesta(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3).equals("1")?true:false);
	        }
	    }
	    
	    return localEncuesta;
	}
}
