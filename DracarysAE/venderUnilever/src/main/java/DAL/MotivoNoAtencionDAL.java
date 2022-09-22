package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.MotivoNoAtencionWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class MotivoNoAtencionDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	 
	public boolean BorrarMotivosNoAtencion()throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarMotivosNoAtencion();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivo de no atencion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivo de no atencion: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
	 	}
 	}
	  
	public long InsertarMotivoNoAtencion(ArrayList<MotivoNoAtencionWSResult> motivosNoAtencion) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarMotivoNoAtencion(motivosNoAtencion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no atencion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no atencion: " + localException.getMessage());
			} 
			throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerMotivoNoAtencionPor(int motivoId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerMotivoNoAtencionPor(motivoId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no atencion por motivoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos no atencion por motivoId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerMotivosNoAtencion() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerMotivosNoAtencion();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos de no atencion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos de no atencion: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

}
