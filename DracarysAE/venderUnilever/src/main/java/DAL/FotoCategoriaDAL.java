package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.FotoCategoriaWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class FotoCategoriaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarFotosCategoria() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarFotosCategoria();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias de las fotos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias de las fotos: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	    
	public long InsertarFotoCategoria(ArrayList<FotoCategoriaWSResult> fotosCategoria) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarFotoCategoria(fotosCategoria);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias de las fotos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las categorias de las fotos: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  	  
	public Cursor ObtenerFotoCategoriaPor(int categoriaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFotoCategoriaPor(categoriaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la fotoCategoria por categoriaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la fotoCategoria por categoriaId: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerFotosCategoria() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerFotosCategoria();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotosCategoria: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotosCategoria: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
