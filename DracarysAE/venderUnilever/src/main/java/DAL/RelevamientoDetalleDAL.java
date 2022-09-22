package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class RelevamientoDetalleDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarRelevamientosDetalle() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarRelevamientosDetalle();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos detalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarRelevamientoDetalle(int relevamientoId, String nombre, int tipoNegocioId, String categoriaId, int fotoSize, 
								byte[] fotoBinary, double latitud, double longitud, int sincro) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarRelevamientoDetalle(relevamientoId, nombre, tipoNegocioId, categoriaId, fotoSize, fotoBinary, latitud, longitud, sincro);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el relevamiento detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el relevamiento detalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerRelevamientosDetalle() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerRelevamientosDetalle();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerRelevamientosDetalleNoSincro() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerRelevamientosDetalleNoSincro();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle no sincro: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle no sincro: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarRelevamientoDetalleSincro(int rowId, int sincro) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      int i = db.ModificarRelevamientoDetalleSincro(rowId, sincro);
		      return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del relevamiento detalle: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del relevamiento detalle: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
