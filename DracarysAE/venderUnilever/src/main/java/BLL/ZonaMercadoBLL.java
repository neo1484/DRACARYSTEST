package BLL;

import java.util.ArrayList;

import Clases.ZonaMercado;
import Clases.ZonaMercadoWSResult;
import DAL.ZonaMercadoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ZonaMercadoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarZonasMercado() throws Exception
	{
		try
		{
			boolean bool = new ZonaMercadoDAL().BorrarZonasMercado();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonasMercado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonasMercado: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarZonaMercado(ArrayList<ZonaMercadoWSResult> zonasMercado) throws Exception
	{
		try
		{
			long l = new ZonaMercadoDAL().InsertarZonaMercado(zonasMercado);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonasMercado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonasMercado: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ZonaMercado ObtenerZonaMercadoPor(int zonaMercadoId)throws Exception
	{
		Cursor localCursor =null;
		ZonaMercado localZonaMercado = null;
		try
		{
			localCursor = new ZonaMercadoDAL().ObtenerZonaMercadoPor(zonaMercadoId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zonaMercado por zonaMercadoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zonaMercado por zonaMercadoId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localZonaMercado = new ZonaMercado(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localZonaMercado;
	}
		  
	public ArrayList<ZonaMercado> ObtenerZonasMercado() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<ZonaMercado> listadoZonaMercado = null;
		
		try
		{
			localCursor = new ZonaMercadoDAL().ObtenerZonasMercado();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonasMercado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonasMercado: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoZonaMercado = new ArrayList<ZonaMercado>();
			listadoZonaMercado.add(new ZonaMercado(0,"Seleccione una zona mercado"));
			do
			{
				ZonaMercado localZonaMercado = new ZonaMercado(localCursor.getInt(1),localCursor.getString(2));
				listadoZonaMercado.add(localZonaMercado);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoZonaMercado;
	}
}
