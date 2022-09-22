package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.MotivoPopWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class MotivoPopDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	 
	 public boolean BorrarMotivosPop()throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 db.borrarMotivosPop();
			 return true;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos pop: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos pop: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
 	}
	  
	 public long InsertarMotivoPop(ArrayList<MotivoPopWSResult> motivosPOP) throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 long l = db.insertarMotivoPop(motivosPOP);
			 return l;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos POP: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos POP: " + localException.getMessage());
			 } 
			 throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerMotivoPopPor(int motivoPopId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMotivoPopPor(motivoPopId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop por motivoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop por motivoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerMotivosPop() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMotivosPop();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
