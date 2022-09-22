package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class MotivoCHANGEDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	 
	 public boolean BorrarMotivosCHANGE()throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 db.borrarMotivosCHANGE();
			 return true;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos CHANGE: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos CHANGE: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
 	}
	  
	 public long InsertarMotivoCHANGE(int motivoCHANGEId, String descripcion) throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 long l = db.insertarMotivoCHANGE(motivoCHANGEId,descripcion);
			 return l;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar motivo CHANGE: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar motivo CHANGE: " + localException.getMessage());
			 } 
			 throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerMotivoCHANGEPor(int motivoPopId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMotivoCHANGEPor(motivoPopId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE por motivoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE por motivoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerMotivosCHANGE() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMotivosCHANGE();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
