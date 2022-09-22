package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.AlmacenPOPProductoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class AlmacenPOPProductoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAlmacenesPOPProducto() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarAlmacenesPOPProducto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOPProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOPProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarAlmacenPOPProducto(ArrayList<AlmacenPOPProductoWSResult> almacenesPopProducto) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarAlmacenPOPProducto(almacenesPopProducto);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerAlmacenesPOPProducto() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerAlmacenesPOPProducto();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOPProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOPProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerAlmacenPOPProductoPorProductoId(int productoId)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	      Cursor localCursor = db.obtenerAlmacenPOPProductoPorProductoId(productoId);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacenPOPProducto por productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacenPOPProducto por productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ValidarExistenciasAlmacenPOPDispositivo(int productoId,int cantidad)
		    throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.ValidarExistenciasAlmacenPOPDispositivo(productoId,cantidad);
			return localCursor;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del productoPOP, en el almacenPOPProducto: vacio");
	    	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del productoPOP, en el almacenPOPProducto: " + localException.getMessage());
	    	} 
			throw localException;
	    }
		finally
		{
			db.CloseDB();
	    }
  	}
}
