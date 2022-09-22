package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;

public class LoginEmpleadoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarLoginsEmpleados()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarLoginsEmpleado();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleado: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	 
	public long InsertarLoginEmpleado(int empleadoId, String empleadoNombre, String empleadoUsuario, int dia, int mes, 
										int anio, int estado, String mensaje, int almacenId,int vendedorRutaId,
									  	boolean modificarClienteApk, String token) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      long l = db.insertarLoginEmpleado(empleadoId,empleadoNombre,empleadoUsuario,dia,mes,anio,
											estado, mensaje, almacenId, vendedorRutaId, modificarClienteApk,
				  							token);
	      return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el loginEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el loginEmpleado: "+localException.getMessage());
			}
	      throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	  }
	  
	  public Cursor ObtenerLoginEmpleadoDetallePor(int dia, int mes, int anio) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerLoginEmpleadoDetallePor(dia,mes,anio);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {					
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleadoDetallePorFecha: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleadoDetallePorFecha: "+localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerLoginEmpleadoDetallePorEmpleadoUsuario(String empleadoUsuario,int dia,int mes,int anio) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerLoginEmpleadoDetallePorEmpleadoUsuario(empleadoUsuario,dia,mes,anio);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {					
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleadoDetallePorEmpleadoUsuario: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleadoDetallePorEmpleadoUsuario: "+localException.getMessage());
			  }
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarLoginEmpleadoFechaPor(int empleadoId,int dia,int mes,int anio) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      int i = db.ModificarLoginEmpleadoFechaPor(empleadoId, dia, mes, anio);
		      return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la fecha del login empleado: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la fecha del login empleado: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
