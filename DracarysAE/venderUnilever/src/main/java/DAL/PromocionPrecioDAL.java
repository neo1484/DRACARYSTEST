package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.PromocionPrecioWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PromocionPrecioDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPromocionesPrecio()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarPromocionesPrecio();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	
	public boolean BorrarPomocionPrecioPor(long rowId)throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarPromocionPrecioPor(rowId);
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion precio por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion precio por rowId: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	 
	public long InsertarPromocionPrecio(ArrayList<PromocionPrecioWSResult> promocionesPrecio) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      long l = db.insertarPromocionPrecio(promocionesPrecio);
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
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios de la promocion: "+localException.getMessage());
			}
	      throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPromocionesPrecio() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerPromocionesPrecio();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promoicones precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones precio: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerPromocionPrecioPorPromocionId(int promocionId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerPromocionPrecioPorPromocionId(promocionId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones precio por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones precio por promocionId: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerPromocionPrecioPor(int promocionId,int precioListaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerPromocionPrecioPor(promocionId, precioListaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio promocion por promocionId y precioListaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio promocion por promocionId y precioListaId: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
