package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ZonaMercadoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ZonaMercadoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarZonasMercado() throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarZonasMercado();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonasMercado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonasMercado: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long InsertarZonaMercado(ArrayList<ZonaMercadoWSResult> zonasMercado)throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarZonaMercado(zonasMercado);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonasMercado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonasMercado: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerZonaMercadoPor(int zonaMercadoId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerZonaMercadoPor(zonaMercadoId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zonaMercado por zonaMercadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zonaMercado por zonaMercadoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerZonasMercado()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerZonasMercado();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonasMercado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonasMercado: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
