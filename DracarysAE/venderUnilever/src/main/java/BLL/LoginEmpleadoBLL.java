package BLL;

import android.database.Cursor;
import Utilidades.Utilidades;
import Clases.Fecha;
import Clases.LoginEmpleado;
import DAL.LoginEmpleadoDAL;

public class LoginEmpleadoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	  
	public boolean BorrarLoginsEmpleado() throws Exception
	{
		LoginEmpleadoDAL localLoginEmpleadoDAL = new LoginEmpleadoDAL();
	    try
	    {
	    	boolean bool = localLoginEmpleadoDAL.BorrarLoginsEmpleados();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleados: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public long InsertarLoginEmpleado(int empleadoId,String empleadoNombre,String empleadoUsuario,int dia,int mes, 
										int anio, int estado, String mensaje, int almacenId, int vendedorRutaId,
									  	boolean modificarClienteApk, String token) throws Exception
	{
		try
	    {
	      long l = new LoginEmpleadoDAL().InsertarLoginEmpleado(empleadoId,empleadoNombre,empleadoUsuario,dia,
	    		  												mes,anio,estado,mensaje,almacenId,vendedorRutaId,
				  												modificarClienteApk, token);
	      return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleados: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	  
	public LoginEmpleado ObtenerLoginEmpleado() throws Exception
	{
		LoginEmpleadoDAL localLoginEmpleadoDAL = new LoginEmpleadoDAL();
	    Fecha fecha = theUtilidades.ObtenerFecha();
	    Cursor localCursor = null;
	    LoginEmpleado localLoginEmpleado = null;
	    
		try
	    {
			localCursor = localLoginEmpleadoDAL.ObtenerLoginEmpleadoDetallePor(fecha.get_dia(),
																	fecha.get_mes(),fecha.get_anio());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los loginsEmpleadoDetallePorFecha: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los loginsEmpleadoDetallePorFecha: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null)
		{
			int i = localCursor.getCount();
	        if (i > 0) 
	        {
	          localLoginEmpleado = new LoginEmpleado(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3),
	        		  localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7), 
	        		  localCursor.getString(8),localCursor.getInt(9),localCursor.getInt(10),"","",
					  localCursor.getString(11).equals("1"), localCursor.getString(12));
	        }
	      }
		
	      return localLoginEmpleado;
	  }
	  
	public LoginEmpleado ObtenerLoginEmpleadoDetallePor(int dia, int mes, int anio) throws Exception
	  {
	    LoginEmpleadoDAL localLoginEmpleadoDAL = new LoginEmpleadoDAL();
	    LoginEmpleado localLoginEmpleado = null;
	    Cursor localCursor = null;
	    
	    try
	    {
	      localCursor = localLoginEmpleadoDAL.ObtenerLoginEmpleadoDetallePor(dia,mes,anio);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los loginsEmpleadoDetallePorFecha: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los loginsEmpleadoDetallePorFecha: " + localException.getMessage());
			}
	      throw localException;
	    }
	    
	    if (localCursor != null)
	    {
	        int i = localCursor.getCount();
	        if (i > 0) 
	        {
	          localLoginEmpleado = new LoginEmpleado(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3),
	        		  localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7), 
	        		  localCursor.getString(8),localCursor.getInt(9),localCursor.getInt(10),"","",
					  localCursor.getString(11).equals("1"), localCursor.getString(12));
	        }
	    }
	    return localLoginEmpleado;
	  }
	  
	public LoginEmpleado ObtenerLoginEmpleadoDetallePorEmpleadoUsuario(String empleadoUsuario) throws Exception
	{
		  LoginEmpleadoDAL localLoginEmpleadoDAL = new LoginEmpleadoDAL();
		  LoginEmpleado localLoginEmpleado = null;
		  Cursor localCursor = null;
		  Fecha fecha = theUtilidades.ObtenerFecha();
		  
		  try
		  {
			  localCursor = localLoginEmpleadoDAL.ObtenerLoginEmpleadoDetallePorEmpleadoUsuario(empleadoUsuario,
					  fecha.get_dia(),fecha.get_mes(),fecha.get_anio());
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los loginsEmpleadoDetallePorEmpleadoUsuario: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los loginsEmpleadoDetallePorEmpleadoUsuario: " + localException.getMessage());
			  }
		      throw localException;
		  }
		  
		  if (localCursor != null)
		  {
			  if (localCursor.getCount() > 0) 
			  {
				  localLoginEmpleado = new LoginEmpleado(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3),
		        		  localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7), 
		        		  localCursor.getString(8),localCursor.getInt(9),localCursor.getInt(10),"","",
						  localCursor.getString(11).equals("1"), localCursor.getString(12));
			  }
		  }
		  
	      return localLoginEmpleado;	    
	}
	
	public long ModificarLoginEmpleadoFechaPor(int empleadoId,int dia,int mes,int anio) throws Exception
	{
	    try
	    {
	    	long l = new LoginEmpleadoDAL().ModificarLoginEmpleadoFechaPor(empleadoId, dia, mes, anio);
	    	return l;
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
	}
}
