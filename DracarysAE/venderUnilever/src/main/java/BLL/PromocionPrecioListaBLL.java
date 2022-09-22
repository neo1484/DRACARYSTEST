package BLL;

import java.util.ArrayList;

import Clases.PromocionPrecioListWSResult;
import Clases.PromocionPrecioLista;
import DAL.PromocionPrecioListaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PromocionPrecioListaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPromocionesPrecioLista() throws Exception
	{
		try
	    {
			boolean bool = new PromocionPrecioListaDAL().BorrarPromocionesPrecioLista();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promocionesPrecioLista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promocionesPrecioLista: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarPromocionPrecioLista(ArrayList<PromocionPrecioListWSResult> promocionesPrecioLista) throws Exception
	{
	    try
	    {
	    	long l = new PromocionPrecioListaDAL().InsertarPromocionPrecioLista(promocionesPrecioLista);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios lista de las promociones: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios lista de las promociones: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public PromocionPrecioLista ObtenerPromocionPrecioListaPor(int promocionId) throws Exception
	{
		Cursor localCursor;
		PromocionPrecioLista localPromocionPrecioLista = null;
		try
	    {
			localCursor = new PromocionPrecioListaDAL().ObtenerPromocionesPrecioListaPor(promocionId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocionPrecioLista por promocionId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocionPrecioLista por promocionId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if (localCursor.getCount() > 0) 
		{
			localPromocionPrecioLista = new PromocionPrecioLista(localCursor.getInt(1),localCursor.getInt(2));
		}
		
		return localPromocionPrecioLista;
	}
	  
	public ArrayList<PromocionPrecioLista> ObtenerPromocionesPRecioLista() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PromocionPrecioLista> listadoPromocionPrecioLista = null;
		  try
		  {
			  localCursor = new PromocionPrecioListaDAL().ObtenerPromocionesPrecioLista();
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocionesPrecioLista: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocionesPrecioLista: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoPromocionPrecioLista = new ArrayList<PromocionPrecioLista>();
			  do
			  {
		          PromocionPrecioLista promocionPrecioLista = new PromocionPrecioLista(localCursor.getInt(1),localCursor.getInt(2));
				  
		          listadoPromocionPrecioLista.add(promocionPrecioLista);
		      } 
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocionPrecioLista;
	  }
}
