package BLL;

import java.util.ArrayList;
import Clases.EncuestaLista;
import Clases.EncuestaListaWSResult;
import DAL.EncuestaListaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class EncuestaListaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestasLista() throws Exception
	{
	    try
	    {
	    	boolean bool = new EncuestaListaDAL().BorrarEncuestaLista();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasLista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasLista: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarEncuestaLista(ArrayList<EncuestaListaWSResult> encuestasLista) throws Exception
	{
		try
		{
			long l = new EncuestaListaDAL().InsertarEncuestaLista(encuestasLista);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las listas de las encuestas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las listas de las encuestas: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<EncuestaLista> ObtenerEncuestasLista() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<EncuestaLista> listadoEncuestaLista = null;
	    
	    try
	    {
	    	localCursor = new EncuestaListaDAL().ObtenerEncuestasLista();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestasLista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestasLista: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoEncuestaLista = new ArrayList<EncuestaLista>();
	        	do
	        	{
	        		EncuestaLista localEncuestaLista = new EncuestaLista(localCursor.getInt(1),localCursor.getString(2));
	        		
	        		listadoEncuestaLista.add(localEncuestaLista);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoEncuestaLista;
	}
	
	public  ArrayList<EncuestaLista> ObtenerEncuestaListaPor(int detalleId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<EncuestaLista> listadoEncuestaLista = null;
	    
	    try
	    {
	    	localCursor = new EncuestaListaDAL().ObtenerEncuestaListaPor(detalleId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestaLista por detalleId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestaLista por detalleId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null && localCursor.getCount() > 0)
	    {
	    	listadoEncuestaLista = new ArrayList<EncuestaLista>();
	    	
	    	do
        	{
        		EncuestaLista localEncuestaLista = new EncuestaLista(localCursor.getInt(1),localCursor.getString(2));
        		
        		listadoEncuestaLista.add(localEncuestaLista);
        	} 
        	while (localCursor.moveToNext());	        
	    }
	    
	    return listadoEncuestaLista;
	}
}
