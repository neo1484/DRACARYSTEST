package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.DosificacionWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class DosificacionDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDosificacion() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarDosificacion();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public long InsertarDosificacion(ArrayList<DosificacionWSResult> dosificaciones) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarDosificacion(dosificaciones);
			
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor obtenerDosificacion() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerDosificacion();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public int ModificarDosificacionNumeroFactura(int dosificacionId,int numeroActual) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarDosificacionNumeroFactura(dosificacionId, numeroActual);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el numero de factura de la dosificacion: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el numero de factura de la dosificacion: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
