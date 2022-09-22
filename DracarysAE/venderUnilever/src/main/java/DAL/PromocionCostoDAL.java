package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.PromocionCostoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PromocionCostoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPromocionesCosto()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarPromocionesCosto();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	
	public boolean BorrarPomocionCostoPor(long rowId)throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarPromocionCostoPor(rowId);
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion costo por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la promocion costo por rowId: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	 
	public long InsertarPromocionCosto(ArrayList<PromocionCostoWSResult> promocionesCosto) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      long l = db.insertarPromocionCosto(promocionesCosto);
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
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los costos de la promocion: "+localException.getMessage());
			}
	      throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPromocionesCosto() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerPromocionesCosto();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promoicones costo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones costo: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerPromocionCostoPorPromocionId(int promocionId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerPromocionCostoPorPromocionId(promocionId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones costo por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones costo por promocionId: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
