package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.EncuestaWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class EncuestaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestas() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarEncuestas();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuentas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarEncuesta(ArrayList<EncuestaWSResult> encuestas) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarEncuesta(encuestas);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las encuestas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las encuestas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerEncuestas() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestas();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestas: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerEncuestaPor(int encuestaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestaPor(encuestaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta por encuestaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta por encuestaId: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
}
