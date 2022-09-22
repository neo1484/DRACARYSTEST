package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ApkRutaClienteWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import java.util.ArrayList;

public class ApkRutaClienteDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarApksRutaCliente() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarApksRutaCliente();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apksRutaCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apksRutaCliente: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long insertarApkRutaCliente(ArrayList<ApkRutaClienteWSResult> apkRutas)throws Exception
	{
		db.OpenDB();
		try
		{
			long l = this.db.insertarApkRutaCliente(apkRutas);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar laa apk rutas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las apk rutas: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long InsertarApkRutaCliente(int rutaId,int diaId,String rutaNombre,String diaNombre,boolean bloquearPreventaDistancia,boolean rutaCompleta) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarApkRutaCliente(rutaId, diaId, rutaNombre, diaNombre, bloquearPreventaDistancia, rutaCompleta);
	    	
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ApkRutaCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ApkRutaCliente: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerApksRutaCliente() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerApksRutaCliente();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ApksRutaCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ApksRutaCliente: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerApkRutaClientePor(int rutaId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerApkRutaClientePor(rutaId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ApkRutaCliente por rutaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el ApkRutaCliente por rutaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
