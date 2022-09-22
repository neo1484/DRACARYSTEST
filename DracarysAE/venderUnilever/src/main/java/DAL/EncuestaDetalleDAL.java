package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.EncuestaDetalleWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class EncuestaDetalleDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestasDetalle() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarEncuestasDetalle();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuentasDetalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasDetalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarEncuestaDetalle(ArrayList<EncuestaDetalleWSResult> encuestasDetalle) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarEncuestaDetalle(encuestasDetalle);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los detalles de las encuestas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los detalles de las encuestas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerEncuestasDetalle() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestasDetalle();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasDetalle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasDetalle: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerEncuestaDetallePor(int encuestaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestaDetallePor(encuestaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta detalle por encuestaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta detalle por encuestaId: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
}
