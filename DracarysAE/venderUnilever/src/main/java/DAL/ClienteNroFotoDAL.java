package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ClienteNroFotoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ClienteNroFotoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesNroFoto() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesNroFoto();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNroFoto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNroFoto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	    
	public long InsertarClienteNroFoto(ArrayList<ClienteNroFotoWSResult> clientesNroFoto) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarClienteNroFoto(clientesNroFoto);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNroFoto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNroFoto: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  	  
	public Cursor ObtenerClienteNroFotoPor(int clienteId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerClienteNroFotoPor(clienteId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteNroFoto por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteNroFoto por clienteId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerClientesNroFoto() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerClientesNroFoto();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clientesNroFoto por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clientesNroFoto por clienteId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}