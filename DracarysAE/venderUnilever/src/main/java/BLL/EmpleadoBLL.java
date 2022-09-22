package BLL;

import java.util.ArrayList;
import Clases.Empleado;
import Clases.EmpleadoWSResult;
import DAL.EmpleadoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class EmpleadoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarEmpleados() throws Exception
	{
		try
	    {
			boolean bool = new EmpleadoDAL().BorrarEmpleados();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los empleados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los empleados: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarEmpleado(ArrayList<EmpleadoWSResult> empleados) throws Exception
	{
	    try
	    {
	    	long l = new EmpleadoDAL().InsertarEmpleado(empleados);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los empleados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los empleados: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	    
	public ArrayList<Empleado> ObtenerEmpleados() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Empleado> listadoEmpleado = null;
		  try
		  {
			  localCursor = new EmpleadoDAL().ObtenerEmpleados();
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los empleados: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los empleados: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoEmpleado = new ArrayList<Empleado>();
			  do
			  {
				  Empleado empleado = new Empleado(localCursor.getInt(1),localCursor.getString(2));
				  
				  listadoEmpleado.add(empleado);
			  }
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoEmpleado;
	  }

	public Empleado ObtenerEmpleadoPor(int empleadoId) throws Exception
	  {
		  Cursor localCursor;
		  Empleado localEmpleado = null;
		  try
		  {
			  localCursor = new EmpleadoDAL().ObtenerEmpleadosPor(empleadoId);
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el empleado por empleadoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el empleados por empleadoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  localEmpleado = new Empleado(localCursor.getInt(1),localCursor.getString(2));
		  }
		  
		  return localEmpleado;
	  }
}
