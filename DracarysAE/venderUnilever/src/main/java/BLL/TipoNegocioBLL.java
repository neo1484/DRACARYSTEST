package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.TipoNegocio;
import Clases.TipoNegocioWSResult;
import DAL.TipoNegocioDAL;

public class TipoNegocioBLL 
{
	MyLogBLL myLog = new MyLogBLL();	

	public boolean BorrarTiposNegocio()throws Exception
	{
		try
		{
		    boolean bool = new TipoNegocioDAL().BorrarTiposNegocio();
		    return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos de negocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos de negocio: " + localException.getMessage());
			}
		    throw localException;
		}
	}
		  
	public long InsertarTipoNegocio(ArrayList<TipoNegocioWSResult> tiposNegocio) throws Exception
	{
		try
		{
			long l = new TipoNegocioDAL().InsertarTipoNegocio(tiposNegocio);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio: " + localException.getMessage());
			}
			throw localException;
		}
	}
		  
	public TipoNegocio ObtenerTipoNegocioPor(int tipoNegocioId) throws Exception
	{
		Cursor localCursor=null;
		TipoNegocio localTipoNegocio = null;
		
		try
		{
			localCursor = new TipoNegocioDAL().ObtenerTipoNegocioPor(tipoNegocioId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener tipos de negocio por tipoNegocioId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener tipos de negocio por tipoNegocioId: " + localException.getMessage());
			}
		     throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			localTipoNegocio = new TipoNegocio(localCursor.getInt(1), localCursor.getString(2));
		}
		return localTipoNegocio;		
	}
		
	public ArrayList<TipoNegocio> ObtenerTiposNegocio() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<TipoNegocio> listadotemporal = null;
		ArrayList<TipoNegocio> listadoTipoNegocio = null;
		
		try
		{
			localCursor = new TipoNegocioDAL().ObtenerTiposNegocio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipode de negocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipode de negocio: " + localException.getMessage());
			}
			return null;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadotemporal = new ArrayList<TipoNegocio>();
			do
			{
				TipoNegocio tipoNegocio = new TipoNegocio(localCursor.getInt(1), localCursor.getString(2));
				
				listadotemporal.add(tipoNegocio);
			}
			while(localCursor.moveToNext());
			
			listadoTipoNegocio = new ArrayList<TipoNegocio>();
			
			listadoTipoNegocio.add(new TipoNegocio(0,"Seleccione un tipo de negocio"));
			
			for(TipoNegocio item : listadotemporal)
			{
				listadoTipoNegocio.add(item);
			}
		}
		
		return listadoTipoNegocio;
	}
}
