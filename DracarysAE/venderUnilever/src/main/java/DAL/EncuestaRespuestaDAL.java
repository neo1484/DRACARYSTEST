package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class EncuestaRespuestaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarEncuestasRespuesta() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarEncuestasRespuesta();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuentasRespuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestasRespuesta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarEncuestaRespuesta(int encuestaRespuestaId,int detalleId,int clienteId,String respuesta,String especificacion,
										String observacion,int empleadoId,int dia,int mes,int anio) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarEncuestaRespuesta(encuestaRespuestaId, detalleId, clienteId, respuesta, especificacion, observacion, empleadoId, dia, mes, anio);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la encuestaRespuesta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la encuestaRespuesta: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerEncuestasRespuesta() throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestasRespuesta();
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasRespuesta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasRespuesta: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerEncuestaRespuestaPor(int encuestaRespuestaId) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestaRespuestaPor(encuestaRespuestaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta respuesta por encuestaRespuestaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta respuesta por encuestaRespuestaId: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerEncuestasRespuestaRangoDetalle(int clienteId, int detalleInicio,int detalleFin) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestasRespuestaRangoDetalle(clienteId, detalleInicio, detalleFin);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasRespuesta en un rango de detalles: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasRespuesta en un rango de detalles: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public Cursor ObtenerEncuestasRespuestaRangoDetalleNoSincro(int clienteId, int detalleInicio,int detalleFin) throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerEncuestasRespuestaRangoDetalleNoSincro(clienteId, detalleInicio, detalleFin);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasRespuesta en un rango de detalles no sincro: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuestasRespuesta en un rango de detalles no sincro: " + localException.getMessage());
			} 
			throw localException;
		}
	  	finally
	  	{
	  		db.CloseDB();
	  	}
	}
	
	public long ModificarEncuestaRespuestaSincro(int encuestaRespuestaId,int especificacion) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      long l = db.ModificarEncuestaRespuestaSincro(encuestaRespuestaId, especificacion);
		      return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro de la encuesta respuesta: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro de la encuesta respuesta: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
