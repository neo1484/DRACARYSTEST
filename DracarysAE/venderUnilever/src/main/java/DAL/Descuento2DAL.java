package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class Descuento2DAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	 
	public boolean BorrarDescuentos2()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarDescuentos2();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los descuentos2: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los descuentos2: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
 	}
	  
	public long InsertarDescuento2(int distribuidorId,float monto) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarDescuento2(distribuidorId, monto);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento: " + localException.getMessage());
			} 
			throw localException;
		}
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDescuento2Por(int distribuidorId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerDescuentos2Por(distribuidorId);
			return localCursor;
		}
		catch (Exception localException)
		{
			  
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el monto descuento2 por distribuidorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el monto descuento2 por distribuidorId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerDescuentos2() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerDescuentos2();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el monto descuento2: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el monto descuento2: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
