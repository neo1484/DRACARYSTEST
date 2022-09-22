package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.MotivoNoEntregaWSResult;
import Utilidades.AdministradorDB;

public class MotivoNoEntregaDAL 
{
	 AdministradorDB db = new AdministradorDB(Login.getContexto());
	 MyLogBLL myLog = new MyLogBLL();
	 
	 public boolean BorrarMotivosNoEntrega()throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 db.borrarMotivosNoEntrega();
			 return true;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivo de no entrega: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivo de no entrega: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
  	}
	  
	 public long InsertarMotivoNoEntrega(ArrayList<MotivoNoEntregaWSResult> motivosNoEntrega) throws Exception
	 {
		 db.OpenDB();
		 try
		 {
			 long l = db.insertarMotivoNoEntrega(motivosNoEntrega);
			 return l;
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no entrega: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no entrega: " + localException.getMessage());
			 } 
			 throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerMotivoNoEntregaPor(int motivoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMotivoNoEntregaPor(motivoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no entrega por motivoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no entrega por motivoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerMotivosNoEntrega() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMotivosNoEntrega();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos de no entrega: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos de no entrega: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
