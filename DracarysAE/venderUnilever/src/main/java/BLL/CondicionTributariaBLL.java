package BLL;

import java.util.ArrayList;

import Clases.CondicionTributaria;
import Clases.CondicionTributariaWSResult;
import Clases.Fecha;
import DAL.CondicionTributariaDAL;
import Utilidades.Utilidades;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CondicionTributariaBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarCondicionesTributarias() throws Exception
	{
		try
		{
			boolean bool = new CondicionTributariaDAL().BorrarCondicionesTributarias();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las condiciones tributarias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las condiciones tributarias: " + localException.getMessage());
			} 
		    throw localException;
    	}
	}
		  
	public long InsertarCondicionTributaria(ArrayList<CondicionTributariaWSResult> condicionesTributaria)throws Exception
	{
		try
		{
			long l = new CondicionTributariaDAL().InsertarCondicionTributaria(condicionesTributaria);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las condiciones tributarias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las condiciones tributarias: " + localException.getMessage());
			} 
			throw localException;
    	}
	}
		  
	public CondicionTributaria ObtenerCondicionTributariaPor(String nit) throws Exception
	{
		Fecha fecha = new Utilidades().ObtenerFecha();
		Cursor localCursor =null;
		CondicionTributaria localCondicionTributaria = null;
		
		try
		{
			localCursor = new CondicionTributariaDAL().ObtenerCondicionTributariaPor(nit,fecha.get_anio());
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria por nit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria por nit: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount() > 0)
		{
			 localCondicionTributaria = new CondicionTributaria(localCursor.getString(1), localCursor.getInt(2), localCursor.getFloat(3));
		}
		
	    return localCondicionTributaria;
	}
		  
	public ArrayList<CondicionTributaria> ObtenerCondicionesTributarias() throws Exception
	{
		ArrayList<CondicionTributaria> listadoCondicionTributaria = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new CondicionTributariaDAL().ObtenerCondicionesTributarias();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las condiciones tributarias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las condiciones tributarias: " + localException.getMessage());
			} 
		}
		
		if(localCursor != null && localCursor.getCount() > 0)
		{
			listadoCondicionTributaria = new ArrayList<CondicionTributaria>();
			do
			{
				CondicionTributaria localCondicionTributaria = new CondicionTributaria(localCursor.getString(1), localCursor.getInt(2), localCursor.getFloat(3));
				
				listadoCondicionTributaria.add(localCondicionTributaria);				
			}
			while(localCursor.moveToNext());
		}
		
		return listadoCondicionTributaria;
	}
}
