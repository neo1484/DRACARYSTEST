package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.VentaBonificacionWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class VentaBonificacionDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarVentasBonificacion() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarVentasBonificacion();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificacion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public boolean BorrarVentaBonificacionPor(int rowId) throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarVentaBonificacionPor(rowId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar la ventaBonificacion por rowId.: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la ventaBonificacion por rowId.: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarVentaBonificacion(ArrayList<VentaBonificacionWSResult> ventasBonificacion) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarVentaBonificacion(ventasBonificacion);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ventas bonificadas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ventas bonificadas: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerVentaBonificacionPorVentaId(int ventaId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentaBonificacionPorVentaId(ventaId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasBonificacion por ventaId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasBonificacion por ventaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerVentasBonificacion() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerVentasBonificacion();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasBonificacion: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasBonificacion: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
