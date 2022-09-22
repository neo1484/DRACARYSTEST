package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class CierreDistribuidorDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarCierresDistribuidor()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarCierresDistribuidor();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres distribuidor: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	
	public boolean BorrarCierreDistribuidorPor(long rowId)throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarCierreDistribuidorPor(rowId);
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres distribuidor por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres distribuidor por rowId: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	 
	public long InsertarCierreDistribuidor(int empleadoId,int anio,int mes,int dia) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      long l = db.insertarCierreDistribuidor(empleadoId, anio, mes, dia);
	      return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre distribuidor: "+localException.getMessage());
			}
	      throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerCierresDistribuidor() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerCierresDistribuidor();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres distribuidor: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerCierreDistribuidorPorEmpleadoIdFecha(int empleadoId,int anio,int mes,int dia) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerCierreDistribuidorPorEmpleadoIdFecha(empleadoId, anio, mes, dia);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre distribuidor: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

}
