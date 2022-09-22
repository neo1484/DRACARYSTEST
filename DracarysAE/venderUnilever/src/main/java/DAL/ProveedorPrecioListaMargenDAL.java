package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ProveedorPrecioListaMargenWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ProveedorPrecioListaMargenDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarProveedorPrecioListaMargen() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarProveedorPrecioListaMargen();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los margenes del ProveedorPrecioListaMargen: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los margenes del proveedorPrecioListaMargen: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarProveedorPrecioListaMargen(ArrayList<ProveedorPrecioListaMargenWSResult> proveedoresPrecioListaMargen)
			  		throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarProveedorPrecioListaMargen(proveedoresPrecioListaMargen);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los margenes del proveedor precio lista margen: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los margenes del proveedor precio lista margen: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor obtenerMargenProveedorPrecioListaMargenPor(int proveedorId,int precioListaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerMargenProveedorPrecioListaMargenPor(proveedorId, precioListaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
	       			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
}
