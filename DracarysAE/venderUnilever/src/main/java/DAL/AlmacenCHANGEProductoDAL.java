package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class AlmacenCHANGEProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAlmacenesCHANGEProducto() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarAlmacenesCHANGEProducto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesCHANGEProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesCHANGEProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarAlmacenCHANGEProducto(int almacenId,int productoId,int saldo) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarAlmacenCHANGEProducto(almacenId,productoId,saldo);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenCHANGEProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenCHANGEProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerAlmacenesCHANGEProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerAlmacenesCHANGEProducto();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesCHANGEProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesCHANGEProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerAlmacenCHANGEProductoPorProductoId(int productoId)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerAlmacenCHANGEProductoPorProductoId(productoId);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacenCHANGEProducto por productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacenCHANGEProducto por productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ValidarExistenciasAlmacenCHANGEDispositivo(int productoId,int cantidad)
		    throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.ValidarExistenciasAlmacenCHANGEDispositivo(productoId,cantidad);
			return localCursor;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del productoCHANGE, en el almacenCHANGEProducto: vacio");
	    	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del productoCHANGE, en el almacenCHANGEProducto: " + localException.getMessage());
	    	} 
			throw localException;
	    }
		finally
		{
			db.CloseDB();
	    }
  	}
}