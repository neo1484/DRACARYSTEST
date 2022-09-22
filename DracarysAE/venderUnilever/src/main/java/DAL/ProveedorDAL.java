package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.ProveedorWSResult;
import Utilidades.AdministradorDB;

public class ProveedorDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarProveedores() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProveedores();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedore: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedore: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarProveedor(ArrayList<ProveedorWSResult> proveedores)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarProveedor(proveedores);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los proveedores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los proveedores: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public Cursor ObtenerProveedores() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProveedores();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
  	}
	
	public Cursor ObtenerProveedorPor(int proveedorId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProveedorPor(proveedorId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedor por proveedorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedor por proveedorId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
  	}

	public Cursor ObtenerProveedoresPor(String proveedoresId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProveedoresPor(proveedoresId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedores por proveedoresId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
  	}

}
