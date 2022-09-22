package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.TipoNegocioWSResult;
import Utilidades.AdministradorDB;

public class TipoNegocioDAL 
{
	  AdministradorDB db = new AdministradorDB(Login.getContexto());
	  MyLogBLL myLog = new MyLogBLL();
	  
	  public boolean BorrarTiposNegocio()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  db.borrarTiposNegocio();
			  return true;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos de negocio: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos de negocio: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long InsertarTipoNegocio(ArrayList<TipoNegocioWSResult> tiposNegocio)throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarTipoNegocio(tiposNegocio);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de negocio: " + localException.getMessage());
				}
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerTipoNegocioPor(int tipoNegocioId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerTipoNegocioPor(tipoNegocioId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener tipos de negocio por clienteTipoNegocioId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener tipos de negocio por clienteTipoNegocioId: " + localException.getMessage());
				}
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerTiposNegocio() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerTiposNegocio();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos de negocio: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos de negocio: " + localException.getMessage());
				}
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
