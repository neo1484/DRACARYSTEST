package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.AlmacenPOPWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AlmacenPOPDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAlmacenesPOP() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarAlmacenesPOP();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarAlmacenPOP(AlmacenPOPWSResult almacenPOP) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarAlmacenPOP(almacenPOP);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerAlmacenesPOP() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerAlmacenesPOP();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
