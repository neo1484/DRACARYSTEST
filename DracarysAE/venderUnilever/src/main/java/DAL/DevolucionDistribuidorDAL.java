package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import BLL.MyLogBLL;
import Clases.DevolucionDistribuidorWSResult;
import Utilidades.AdministradorDB;

public class DevolucionDistribuidorDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDevolucionesDistribuidor() throws Exception
	{
		db.OpenDB();
	    try
	    {
	      db.borrarDevolucionesDistribuidor();
	      return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar el almacen distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el almacen distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	public long InsertarDevolucionDistribuidor(DevolucionDistribuidorWSResult devolucionDistribuidor)
	{
	    db.OpenDB();
	    try
	    {
	    	long l = db.insertarDevolucionDistribuidor(devolucionDistribuidor);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las devoluciones del distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las devoluciones del distribuidor: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long InsertarDevolucionDistribuidor(int almacenDevolucionId, int distribuidorId, int anio,
											   int mes, int dia, int estadoId, boolean estadoSincronizacion)
	{
		db.OpenDB();
		try
		{
			long l = db.insertarDevolucionDistribuidor(almacenDevolucionId,distribuidorId,anio,mes,dia,estadoId,
					estadoSincronizacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la devolución del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la devolución del distribuidor: " + localException.getMessage());
			}
			return 0;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public long ModificarDevolucionDistribuidorId(long id) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      int i = db.modificarDevolucionDistribuidorId(id);
		      return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el almacenDevolucionId por rowId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el almacenDevolucionId por rowId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	public Cursor ObtenerDevolucionDistribuidorPorDistirbuidor(int distribuidorId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDevolucionDistribuidorPorDistribuidor(distribuidorId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor por distribuidorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor por distribuidorId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDevolucionDistribuidorPorDistribuidorYFecha(int distribuidorId, int dia, int mes, int anio) throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDevolucionDistribuidorPorDistribuidorYFecha(distribuidorId,dia,mes,anio);
	      return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor por distribuidorId y fecha: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor por distribuidorId y fecha: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDevolucionesDistribuidor() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDevolucionesDistribuidor();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenes distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenes distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDevolucionesDistribuidorPor(int almacenDevolucionId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDevolucionDistribuidorPor(almacenDevolucionId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor por almacenDevolucionId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen distribuidor por almacenDevolucionId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
