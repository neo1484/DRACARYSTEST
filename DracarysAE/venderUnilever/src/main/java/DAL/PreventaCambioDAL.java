package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.PreventaCambioWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PreventaCambioDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	 
	 public boolean BorrarPreventasCambio()throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 db.borrarPreventasCambio();
			 return true;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas cambio: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas cambio: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
 	}
	  
	 public long InsertarPreventaCambio(ArrayList<PreventaCambioWSResult> preventasCambio) throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 long l = db.insertarPreventaCambio(preventasCambio);
			 return l;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas cambio: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas cambio: " + localException.getMessage());
			 } 
			 throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerPreventaCambioPor(int preventaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaCambioPor(preventaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa cambio: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa cambio: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventasCambio() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventasCambio();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas cambio: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
