package BLL;

import java.util.ArrayList;

import Clases.PromocionCosto;
import Clases.PromocionPrecio;
import Clases.PromocionPrecioWSResult;
import DAL.PromocionCostoDAL;
import DAL.PromocionPrecioDAL;
import Utilidades.Utilidades;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PromocionPrecioBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	  
	public boolean BorrarPromocionesPrecio() throws Exception
	{
	    try
	    {
	    	boolean bool = new PromocionPrecioDAL().BorrarPromocionesPrecio();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public boolean BorrarPromocionPrecioPor(long rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new PromocionPrecioDAL().BorrarPomocionPrecioPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion precio por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion precio por rowId: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public long InsertarPromocionPrecio(ArrayList<PromocionPrecioWSResult> promocionesPrecio) throws Exception
	{
		try
	    {
			long l = new PromocionPrecioDAL().InsertarPromocionPrecio(promocionesPrecio);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios de la promocion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios de la promocion: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	  
	public ArrayList<PromocionPrecio> ObtenerPromocionesPrecio() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<PromocionPrecio> listadoPromocionPrecio = null;
		try
	    {
			localCursor = new PromocionPrecioDAL().ObtenerPromocionesPrecio();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones precio: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null && localCursor.getCount()>0)
		{
			listadoPromocionPrecio = new ArrayList<PromocionPrecio>();
			do
	        {
				PromocionPrecio localPromocionPrecio = new PromocionPrecio(localCursor.getInt(1),localCursor.getInt(2),
																		localCursor.getInt(3),localCursor.getFloat(4),
																		localCursor.getFloat(5));
				
				listadoPromocionPrecio.add(localPromocionPrecio);
	        }
			while(localCursor.moveToNext());
		}
		
		return listadoPromocionPrecio;
	}
	
	public PromocionCosto ObtenerPromocionCostoPorPromocionId(int promocionId) throws Exception
	{
	    Cursor localCursor = null;
	    PromocionCosto localPromocionCosto = null;
		try
	    {
			localCursor = new PromocionCostoDAL().ObtenerPromocionCostoPorPromocionId(promocionId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion costo por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion costo por promocionId: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null && localCursor.getCount()>0)
		{
			localPromocionCosto = new PromocionCosto(localCursor.getInt(1),localCursor.getInt(2));
		}
		
		return localPromocionCosto;
	}
	
	public PromocionPrecio ObtenerPromocionPrecioPor(int promocionId,int precioListaId) throws Exception
	{
	    Cursor localCursor = null;
	    PromocionPrecio localPromocionPrecio = null;
		try
	    {
			localCursor = new PromocionPrecioDAL().ObtenerPromocionPrecioPor(promocionId, precioListaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio promocion por promocionId y precioListaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio promocion por promocionId y precioListaId: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null && localCursor.getCount()>0)
		{
			localPromocionPrecio = new PromocionPrecio(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),
														localCursor.getFloat(4),localCursor.getFloat(5));
		}
		
		return localPromocionPrecio;
	}

}
