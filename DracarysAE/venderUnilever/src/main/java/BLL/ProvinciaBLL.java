package BLL;

import java.util.ArrayList;

import Clases.Provincia;
import Clases.ProvinciaWSResult;
import DAL.ProvinciaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProvinciaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProvincias() throws Exception
	{
		try
		{
			boolean bool = new ProvinciaDAL().BorrarProvincias();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las provincias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las provincias: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarProvincia(ArrayList<ProvinciaWSResult> provincias) throws Exception
	{
		try
		{
			long l = new ProvinciaDAL().InsertarProvincia(provincias);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las provincias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las provincias: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Provincia ObtenerProvinciaPorProvinciaId(int provinciaId)throws Exception
	{
		Cursor localCursor =null;
		Provincia localProvincia = null;
		try
		{
			localCursor = new ProvinciaDAL().ObtenerProvinciaPor(provinciaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la provincia por provinciaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la provincia por provinciaId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localProvincia = new Provincia(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));			
		}
		
		return localProvincia;
	}
	
	public ArrayList<Provincia> ObtenerProvinciasPorCiudadId(int ciudadId) throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Provincia> listadoProvincia = null;
		
		try
		{
			localCursor = new ProvinciaDAL().ObtenerProvinciasPorCiudadID(ciudadId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las provincias por ciudadId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las provincias por ciudadId: " + localException.getMessage());
			}
			throw localException;
		}
		
		listadoProvincia = new ArrayList<Provincia>();
		listadoProvincia.add(new Provincia(0,"[Seleccione una provincia ...]",0));
		
		if(localCursor.getCount() > 0)
		{
			do
			{
				Provincia localProvincia = new Provincia(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));
				listadoProvincia.add(localProvincia);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoProvincia;
	}
		  
	public ArrayList<Provincia> ObtenerProvincias() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Provincia> listadoProvincia = null;
		
		try
		{
			localCursor = new ProvinciaDAL().ObtenerProvincias();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las provincias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las provincias: " + localException.getMessage());
			}
			throw localException;
		}
		
		listadoProvincia = new ArrayList<Provincia>();
		listadoProvincia.add(new Provincia(0,"[Seleccione una provincia ...]",0));
		
		if(localCursor.getCount() > 0)
		{
			do
			{
				Provincia localProvincia = new Provincia(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3));
				listadoProvincia.add(localProvincia);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoProvincia;
	}
}