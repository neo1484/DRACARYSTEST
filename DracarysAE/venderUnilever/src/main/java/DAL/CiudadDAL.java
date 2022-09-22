package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.CiudadWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class CiudadDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarCiudades() throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarCiudades();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los ciudades: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los ciudades: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long InsertarCiudad(ArrayList<CiudadWSResult> ciudades)throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarCiudad(ciudades);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ciudades: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ciudades: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerCiudadPor(int ciudadId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerCiudadPorCiudadId(ciudadId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ciudad por ciudadId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ciudad por ciudadId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerCiudades()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerCiudades();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ciudades: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ciudades: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
