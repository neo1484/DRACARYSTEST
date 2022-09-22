package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.TipoCalleWSResult;
import Utilidades.AdministradorDB;

public class TipoCalleDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarTiposCalle() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarTiposCalle();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos calle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos calle: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarTipoCalle(ArrayList<TipoCalleWSResult> tiposCalle)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarTipoCalle(tiposCalle);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de calle: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de calle: " + localException.getMessage());
				} 
	      		throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerTiposCalle()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerTiposCalle();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos de calle: vacio");
			  }
			  else
			  {
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos de calle: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
