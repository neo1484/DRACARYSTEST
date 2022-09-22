package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.PromocionTipoNegocioWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class PromocionTipoNegocioDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	 
	 public boolean BorrarPromocionesTipoNegocio()throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 db.borrarPromocionesTipoNegocio();
			 return true;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
 	}
	  
	 public long InsertarPromocionTipoNegocio(ArrayList<PromocionTipoNegocioWSResult> promocionesTipoNegocio) throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 long l = db.insertarPromocionTipoNegocio(promocionesTipoNegocio);
			 return l;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio de la promocion: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio de la promocion: " + localException.getMessage());
			 } 
			 throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerPromocionTipoNegocioPor(int promocionId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPromocionTipoNegocioPor(promocionId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion tipo negocio por promocionId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion tipo negocio por promocionId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPromocionesTipoNegocio() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPromocionesTipoNegocio();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones tipo negocio: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones tipo negocio: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
