package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import Utilidades.AdministradorDB;
import Utilidades.Utilidades;

public class LogDAL
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	Utilidades theUtilidades = new Utilidades();
	String fecha = theUtilidades.ObtenerFechaString();
	  
	public boolean BorrarLogs() throws Exception
	{
		db.OpenDB();
	    try
	    {
	      db.borrarLogs();
	      return true;
	    }
	    catch (Exception localException)
	    {
		    if(localException.getMessage().isEmpty())
		    {
			    InsertarLog("App",this.toString(),fecha,"1","Error al borrar los logs: vacio");
		    }
		    else
		    {
			    InsertarLog("App",this.toString(),fecha,"1","Error al borrar los logs: "+localException.getMessage());
		    }
	        throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public long InsertarLog(String aplicacion, String actividad, String fecha, 
							String tipoLog, String log) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarLog(aplicacion,actividad,fecha,tipoLog,log);
	    	return l;
	    }
	    catch (Exception localException)
	    {
		    if(localException.getMessage().isEmpty())
		    {
			    InsertarLog("App",this.toString(),fecha,"1","Error al insertar los logs: vacio");
		    }
		    else
		    {
			    InsertarLog("App",this.toString(),fecha,"1","Error al insertar los logs: "+localException.getMessage());
		    }
		    throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerLogs() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerLogs();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
		    {
			    InsertarLog("App",this.toString(),fecha,"1","Error al obtener los logs: vacio");
		    }
		    else
		    {
			    InsertarLog("App",this.toString(),fecha,"1","Error al obtener los logs: "+localException.getMessage());
		    }
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
