package BLL;

import Clases.Dosificacion;
import Clases.DosificacionWSResult;
import DAL.DosificacionDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class DosificacionBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDosificacion() throws Exception
	{
	    try
	    {
	    	boolean bool = new DosificacionDAL().BorrarDosificacion();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarDosificacion(ArrayList<DosificacionWSResult> dosificaciones)throws Exception
	{
		try
		{
			long l = new DosificacionDAL().InsertarDosificacion(dosificaciones);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public Dosificacion ObtenerDosificacion() throws Exception
	{
	    Cursor localCursor = null;
	    Dosificacion localDosificacion = null;
	    
	    try
	    {
	    	localCursor = new DosificacionDAL().obtenerDosificacion();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localDosificacion = new Dosificacion(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),
	        							localCursor.getInt(4),localCursor.getString(5),localCursor.getInt(6),localCursor.getString(7),
	        							localCursor.getString(8),localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),
	        							localCursor.getString(12),localCursor.getInt(13),localCursor.getString(14).equals("1")?true:false,
	        							localCursor.getString(15).equals("1")?true:false,localCursor.getString(16),localCursor.getInt(17),
	        							localCursor.getString(18),localCursor.getString(19),localCursor.getString(20),
	        							localCursor.getString(21),localCursor.getString(22),localCursor.getString(23));
	        }
	    }
	    
	    return localDosificacion;
	}
	
	public long ModificarDosificacionNumeroFactura(int dosificacionId,int numeroActual) throws Exception
	{
	    try
	    {
	    	int i = new DosificacionDAL().ModificarDosificacionNumeroFactura(dosificacionId, numeroActual);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el numero de factura de la dosificacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el numero de factura de la dosificacion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
