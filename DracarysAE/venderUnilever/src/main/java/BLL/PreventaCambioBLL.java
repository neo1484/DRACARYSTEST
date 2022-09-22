package BLL;

import java.util.ArrayList;
import Clases.PreventaCambio;
import Clases.PreventaCambioWSResult;
import DAL.PreventaCambioDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PreventaCambioBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreventasCambio()throws Exception
	{
		try
		{
			boolean bool = new PreventaCambioDAL().BorrarPreventasCambio();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas cambio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarPreventaCambio(ArrayList<PreventaCambioWSResult>  preventasCambio) throws Exception
	{
		try
		{
			long l = new PreventaCambioDAL().InsertarPreventaCambio(preventasCambio);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas cambio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public PreventaCambio ObtenerPreventaCambioPor(int preventaId) throws Exception
	{
		PreventaCambio localPreventaCambio = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new PreventaCambioDAL().ObtenerPreventaCambioPor(preventaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio por preventaId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localPreventaCambio = new PreventaCambio(localCursor.getInt(1));
	    }
	    
		return localPreventaCambio;
    }
		  
	public ArrayList<PreventaCambio> ObtenerPreventasCambio() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<PreventaCambio> listadoPreventaCambio = null;
		try
		{
			localCursor = new PreventaCambioDAL().ObtenerPreventasCambio();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoPreventaCambio = new ArrayList<PreventaCambio>();
			do
			{
				PreventaCambio localPreventaCambio = new PreventaCambio(localCursor.getInt(1));
				
				listadoPreventaCambio.add(localPreventaCambio);
	        }
			while (localCursor.moveToNext());
		}

        return listadoPreventaCambio;
	}

}
