package BLL;

import java.util.ArrayList;

import Clases.Ciudad;
import Clases.CiudadWSResult;
import DAL.CiudadDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CiudadBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarCiudades() throws Exception
	{
		try
		{
			boolean bool = new CiudadDAL().BorrarCiudades();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ciudades: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ciudades: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarCiudad(ArrayList<CiudadWSResult> ciudades) throws Exception
	{
		try
		{
			long l = new CiudadDAL().InsertarCiudad(ciudades);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ciudades: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ciudades: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Ciudad ObtenerCiudadPorCiudadId(int ciudadId)throws Exception
	{
		Cursor localCursor =null;
		Ciudad localCiudad = null;
		try
		{
			localCursor = new CiudadDAL().ObtenerCiudadPor(ciudadId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ciudad por ciudadId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ciudad por ciudadId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localCiudad = new Ciudad(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localCiudad;
	}
		  
	public ArrayList<Ciudad> ObtenerCiudades() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Ciudad> listadoCiudad = null;
		
		try
		{
			localCursor = new CiudadDAL().ObtenerCiudades();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ciudades: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ciudades: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoCiudad = new ArrayList<Ciudad>();
			listadoCiudad.add(new Ciudad(0,"[Seleccione una ciudad ...]"));
			do
			{
				Ciudad localCiudad = new Ciudad(localCursor.getInt(1),localCursor.getString(2));
				listadoCiudad.add(localCiudad);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoCiudad;
	}
}
