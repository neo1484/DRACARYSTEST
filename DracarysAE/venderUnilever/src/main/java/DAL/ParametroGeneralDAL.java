package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ParametroGeneralWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ParametroGeneralDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarParametrosGenerales()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarParametrosGeneral();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	
	public boolean BorrarParametroGeneralPor(long rowId)throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarParametroGeneralPor(rowId);
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales por rowId: "+localException.getMessage());
			}
			throw localException;
		 }
		 finally
		 {
			 db.CloseDB();
		 }
	}
	 
	public long InsertarParametroGeneral(ParametroGeneralWSResult parametroGeneral) throws Exception
	{
		db.OpenDB();
	    try
	    {
	      long l = db.insertarParametroGeneral(parametroGeneral);
	      return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el parametro general: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el parametro general: "+localException.getMessage());
			}
	      throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerParametrosGenerales() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerParametrosGenerales();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{					
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: "+localException.getMessage());
			}	
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
}
