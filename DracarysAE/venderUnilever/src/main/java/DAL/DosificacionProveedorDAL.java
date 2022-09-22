package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.DosificacionProveedorWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class DosificacionProveedorDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDosifiacionesProveedor() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarDosificacionesProveedor();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarDosificacionProveedor(ArrayList<DosificacionProveedorWSResult> dosificacionesProveedor) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarDosificacionProveedor(dosificacionesProveedor);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones proveedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDosificacionProveedorPor(int dosificacionId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDosificacionProveedorPor(dosificacionId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por dosificacionId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por dosificacionId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerDosificacionProveedorPorProveedorId(int proveedorId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDosificacionProveedorPorProveedorId(proveedorId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion por proveedorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion por proveedorId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDosificacionesProveedor() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDosificacionesProveedor();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public Cursor ObtenerDosificacionesProveedorVendedor() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDosificacionesProveedorVendedor();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por vendedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

}
