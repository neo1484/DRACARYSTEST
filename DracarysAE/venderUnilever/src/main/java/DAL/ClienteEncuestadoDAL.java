package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ClienteEncuestadoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ClienteEncuestadoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClienteEncuestado() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesEncuestado();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes encuestados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes encuestados: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarClienteEncuestado(ArrayList<ClienteEncuestadoWSResult> clientesEncuestado) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarClienteEncuestado(clientesEncuestado);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes encuestados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes encuestados: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerClientesEncuestado() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerClientesEncuestado();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes encuestado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes encuestado: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerClienteEncuestadoPor(int clienteId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerClienteEncuestadoPor(clienteId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente encuestado por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente encuestado por clienteId: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
}
