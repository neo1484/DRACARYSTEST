package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.PreventaDisWSResult;
import Utilidades.AdministradorDB;

public class PreventaDistDAL 
{
	  AdministradorDB db = new AdministradorDB(Login.getContexto());
	  MyLogBLL myLog = new MyLogBLL();
	  
	  public boolean BorrarPreventasDist() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  db.borrarPreventasDist();
			  return true;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas del distribuidor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas del distribuidor: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long InsertarPreventaDist(ArrayList<PreventaDisWSResult> preventasDist) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarPreventaDist(preventasDist);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas del distribuidor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas del distribuidor: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarPreventaDetalleDist(int preventaId, int estadoEntrega) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarPreventaDistEstadoEntrega(preventaId,estadoEntrega);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado entrega en la preventa distribuidor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado entrega en la preventa distribuidor: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventaDistPor(int preventaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaDistPor(preventaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas distribuidor por preventaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas distribuidor por preventaId: " + localException.getMessage());
			  } 
	      	  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventaDistPorClienteId(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaDistPorClienteId(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas distribuidor por clienteId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas distribuidor por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventaDistNoEntregadaPorClienteId(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventaDistNoEntregadaPorClienteId(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas distribuidor no entregadas por clienteId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas distribuidor no entregadas por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerPreventasDist() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerPreventasDist();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas del distribuidor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas del distribuidor: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

}
