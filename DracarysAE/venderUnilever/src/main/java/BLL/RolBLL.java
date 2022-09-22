package BLL;

import java.util.ArrayList;
import Clases.Rol;
import DAL.RolDAL;
import android.database.Cursor;

public class RolBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarRoles() throws Exception
	{
	    RolDAL localRolDAL = new RolDAL();
	    try
	    {
	    	boolean bool = localRolDAL.BorrarRoles();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los roles: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los roles: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarRolesPor(int empleadoId) throws Exception
	{
	    RolDAL localRolDAL = new RolDAL();
	    try
	    {
	    	boolean bool = localRolDAL.BorrarRolesPor(empleadoId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los roles por empleadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los roles por empleadoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarRol(int empleadoId, String rol) throws Exception
	{
		try
		{
			long l = new RolDAL().InsertarRol(empleadoId,rol);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el rol: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el rol: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<Rol> ObtenerRolesPor(int empleadoId) throws Exception
	{
	    RolDAL localRolDAL = new RolDAL();
	    Cursor localCursor = null;
	    ArrayList<Rol> listadoRol = null;
	    
	    try
	    {
	    	localCursor = localRolDAL.ObtenerRolesPor(empleadoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los roles: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los roles: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoRol = new ArrayList<Rol>();
	        	do
	        	{
	        		Rol localRol = new Rol(localCursor.getInt(1), localCursor.getString(2));
	        		
	        		listadoRol.add(localRol);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoRol;
	}
}
