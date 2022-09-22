package BLL;

import java.util.ArrayList;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.TipoCalle;
import Clases.TipoCalleWSResult;
import DAL.TipoCalleDAL;

public class TipoCalleBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarTiposCalle() throws Exception
	{
		try
		{
		      boolean bool = new TipoCalleDAL().BorrarTiposCalle();
		      return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipo de calle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipo de calle: " + localException.getMessage());
			} 
		    throw localException;
		}
	}
		  
	public long InsertarTipoCalle(ArrayList<TipoCalleWSResult> tiposCalle)throws Exception
	{
		try
		{
			long l = new TipoCalleDAL().InsertarTipoCalle(tiposCalle);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de calle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de calle: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		 
	public ArrayList<TipoCalle> ObtenerTiposCalle()
	{
		Cursor localCursor = null;
		ArrayList<TipoCalle> listadoTemporal = null;
		ArrayList<TipoCalle> listadoTipoCalle = null;
		
		try
		{
			localCursor = new TipoCalleDAL().ObtenerTiposCalle();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos de calle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos de calle: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoTemporal = new ArrayList<TipoCalle>();
			do
			{
				TipoCalle tipoCalle = new TipoCalle(localCursor.getInt(1),localCursor.getString(2));
				
				listadoTemporal.add(tipoCalle);
			}
			while(localCursor.moveToNext());
			
			listadoTipoCalle = new ArrayList<TipoCalle>();
			
			listadoTipoCalle.add(new TipoCalle(0,"[Sel]"));
			
			for(TipoCalle item : listadoTemporal)
			{
				listadoTipoCalle.add(item);
			}
		}
	
		return listadoTipoCalle;
	}
}
