package BLL;

import java.util.ArrayList;
import Clases.PromocionTipoNegocio;
import Clases.PromocionTipoNegocioWSResult;
import DAL.PromocionTipoNegocioDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PromocionTipoNegocioBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPromocionesTipoNegocio()throws Exception
	{
		try
		{
			boolean bool = new PromocionTipoNegocioDAL().BorrarPromocionesTipoNegocio();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarPromocionTipoNegocio(ArrayList<PromocionTipoNegocioWSResult> promocionesTipoNegocio) throws Exception
	{
		try
		{
			long l = new PromocionTipoNegocioDAL().InsertarPromocionTipoNegocio(promocionesTipoNegocio);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio de la promocion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio de la promocion: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ArrayList<PromocionTipoNegocio> ObtenerPromocionTipoNegocioPor(int promocionId) throws Exception
	{
		ArrayList<PromocionTipoNegocio> listadoPromocionTipoNegocio = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new PromocionTipoNegocioDAL().ObtenerPromocionTipoNegocioPor(promocionId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones tipo negocio por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones tipo negocio por promocionId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			listadoPromocionTipoNegocio = new ArrayList<PromocionTipoNegocio>();
			do
			{
				PromocionTipoNegocio promocionTipoNegocio = new PromocionTipoNegocio(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3));
				listadoPromocionTipoNegocio.add(promocionTipoNegocio);
			}
			while(localCursor.moveToNext());
	    }
	    
		return listadoPromocionTipoNegocio;
    }
		  
	public ArrayList<PromocionTipoNegocio> ObtenerPromocionTipoNegocio() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<PromocionTipoNegocio> listadoPromocionTipoNegocio = null;
		try
		{
			localCursor = new PromocionTipoNegocioDAL().ObtenerPromocionesTipoNegocio();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones tipo negocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones tipo negocio: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoPromocionTipoNegocio = new ArrayList<PromocionTipoNegocio>();
			do
			{
				PromocionTipoNegocio localPromocionTipoNegocio = new PromocionTipoNegocio(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3));
				
				listadoPromocionTipoNegocio.add(localPromocionTipoNegocio);
	        }
			while (localCursor.moveToNext());
		}

        return listadoPromocionTipoNegocio;
	}
}
