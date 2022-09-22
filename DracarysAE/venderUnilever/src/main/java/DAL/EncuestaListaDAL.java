package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.EncuestaListaWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class EncuestaListaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestaLista() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarEncuestasLista();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuentasLista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasLista: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarEncuestaLista(ArrayList<EncuestaListaWSResult> encuestasLista) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarEncuestaLista(encuestasLista);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las listas de las encuestas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las listas de las encuestas: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerEncuestasLista() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestasLista();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasLista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasLista: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerEncuestaListaPor(int detalleId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestaListaPor(detalleId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta lista por detalleId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta lista por detalleId: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
}
