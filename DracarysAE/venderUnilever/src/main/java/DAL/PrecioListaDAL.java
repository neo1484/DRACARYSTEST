package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.PrecioWSResult;
import Utilidades.AdministradorDB;

public class PrecioListaDAL
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarPreciosLista() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarPreciosLista();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios lista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios lista: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarPrecioLista(ArrayList<PrecioWSResult> precios)	throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarPrecioLista(precios);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios lista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios lista: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerPrecioListaPor(int precioListaId, int productoId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreciosListaPor(precioListaId,productoId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerPrecioListaPor(int precioListaId, int productoId,int precioId) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerPreciosListaPor(precioListaId,productoId,precioId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
