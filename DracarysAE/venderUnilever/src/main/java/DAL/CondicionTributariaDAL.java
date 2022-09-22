package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.CondicionTributariaWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class CondicionTributariaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarCondicionesTributarias()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarCondicionesTrbutarias();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las condiciones tributarias: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las condiciones tributarias: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarCondicionTributaria(ArrayList<CondicionTributariaWSResult> condicionesTributaria)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = this.db.insertarCondicionTributaria(condicionesTributaria);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las condiciones tributaria: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las condiciones tributaria: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerCondicionTributariaPor(String nit,int anio)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerCondicionTributariaPor(nit,anio);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la condicion tributaria: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerCondicionesTributarias()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = this.db.obtenerCondicionesTributarias();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las condiciones tributarias: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las condiciones tributarias: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
