package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.DistribuidorWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class DistribuidorDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDistribuidores() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarDistribuidores();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public boolean BorrarDistribuidorPor(int rowId) throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarDistribuidorPor(rowId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar el distribuidor por rowId.: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el ditribuidor por rowId.: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarDistribuidor(ArrayList<DistribuidorWSResult> distribuidores) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarDistribuidor(distribuidores);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los distribuidores: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los distribuidores: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerDistribuidorPor(int distribuidorId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDistribuidorPorDistribuidorId(distribuidorId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el distribuidor por distribuidorId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el distribuidor por distribuidorId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerDistribuidores() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerDistribuidores();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los distribuidores: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los distribuidores: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
