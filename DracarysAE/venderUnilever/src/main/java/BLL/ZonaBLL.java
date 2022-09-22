package BLL;

import java.util.ArrayList;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Zona;
import Clases.ZonaWSResult;
import DAL.ZonaDAL;

public class ZonaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarZonas() throws Exception
	{
		try
		{
			boolean bool = new ZonaDAL().BorrarZonas();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarZona(ArrayList<ZonaWSResult> zonas) throws Exception
	{
		try
		{
			long l = new ZonaDAL().InsertarZona(zonas);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonas: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Zona ObtenerZonaPor(int zonaId)throws Exception
	{
		Cursor localCursor =null;
		Zona localZona = null;
		try
		{
			localCursor = new ZonaDAL().ObtenerZonaPor(zonaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zona por zonaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zona por zonaId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localZona = new Zona(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localZona;
	}
		  
	public ArrayList<Zona> ObtenerZonas() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Zona> listadoTemporal = null;
		ArrayList<Zona> listadoZonas = null;
		
		try
		{
			localCursor = new ZonaDAL().ObtenerZonas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonas: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoTemporal = new ArrayList<Zona>();
			do
			{
				Zona localZona = new Zona(localCursor.getInt(1),localCursor.getString(2));
				
				listadoTemporal.add(localZona);
			}
			while(localCursor.moveToNext());
			
			listadoZonas = new ArrayList<Zona>();
			
			listadoZonas.add(new Zona(0,"Seleccione una zona"));
			
			for(Zona item : listadoTemporal)
			{
				listadoZonas.add(item);
			}
		}
		
		return listadoZonas;
	}
}
