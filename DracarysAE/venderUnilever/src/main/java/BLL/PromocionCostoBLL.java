package BLL;

import java.util.ArrayList;
import Clases.PromocionCosto;
import Clases.PromocionCostoWSResult;
import DAL.PromocionCostoDAL;
import Utilidades.Utilidades;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PromocionCostoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	  
	public boolean BorrarPromocionesCosto() throws Exception
	{
	    try
	    {
	    	boolean bool = new PromocionCostoDAL().BorrarPromocionesCosto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public boolean BorrarPromocionCostoPor(long rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new PromocionCostoDAL().BorrarPomocionCostoPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion costo por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion costo por rowId: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public long InsertarPromocionCosto(ArrayList<PromocionCostoWSResult> promocionesCosto) throws Exception
	{
		try
	    {
			long l = new PromocionCostoDAL().InsertarPromocionCosto(promocionesCosto);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los costos de la promocion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los costos de la promocion: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	  
	public ArrayList<PromocionCosto> ObtenerPromocionesCosto() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<PromocionCosto> listadoPromocionCosto = null;
		try
	    {
			localCursor = new PromocionCostoDAL().ObtenerPromocionesCosto();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones costo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones costo: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null && localCursor.getCount()>0)
		{
			listadoPromocionCosto = new ArrayList<PromocionCosto>();
			do
	        {
				PromocionCosto localPromocionCosto = new PromocionCosto(localCursor.getInt(1),localCursor.getInt(2));
				
				listadoPromocionCosto.add(localPromocionCosto);
	        }
			while(localCursor.moveToNext());
		}
		
		return listadoPromocionCosto;
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
}
