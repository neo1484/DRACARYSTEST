package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class CierrePreventistaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarCierresPreventista()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarCierresPreventista();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres preventista: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	
	public boolean BorrarCierrePreventistaPor(long rowId)throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarCierrePreventistaPor(rowId);
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres prevetista por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres preventista por rowId: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	 
	public long InsertarCierrePreventista(int empleadoId,int anio,int mes,int dia) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      long l = db.insertarCierrePreventista(empleadoId, anio, mes, dia);
	      return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre preventista: "+localException.getMessage());
			}
	      throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerCierresPreventistas() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerCierresPreventista();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres preventistas: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerCierrePreventistaPorEmpleadoIdFecha(int empleadoId,int anio,int mes,int dia) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerCierrePreventistaPorEmpleadoIdFecha(empleadoId, anio, mes, dia);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre preventista: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
