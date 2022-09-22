package BLL;

import android.database.Cursor;

import java.util.ArrayList;

import Clases.SincronizacionDatos;
import DAL.SincronizacionDatosDAL;

public class SincronizacionDatosBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarSincronizacionesDatos() throws Exception
	{
		SincronizacionDatosDAL localSincronizacionDatosDAL = new SincronizacionDatosDAL();
	
		try
		{
			boolean bool = localSincronizacionDatosDAL.BorrarSincronizacionesDatos();
		    return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizaciones datos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizaciones datos: " + localException.getMessage());
			} 
		    throw localException;
		}
	}
		  
	public long InsertarSincronizacionDatos(int empleadoId, int dia, int mes, int anio,int rol) throws Exception
	{
		SincronizacionDatosDAL localSincronizacionDatosDAL = new SincronizacionDatosDAL();
		try
		{
			long l = localSincronizacionDatosDAL.InsertarSincronizacionDatos(empleadoId,dia,mes,anio,rol);
		    return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al insertar registro sincronizacion datos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar registro sincronizacion datos: " + localException.getMessage());
			} 
		    throw localException;
		}
	}
	
	public SincronizacionDatos ObtenerDetalleSincronizacionDatosPor(int empleadoId,int dia,int mes,int anio,int rol)throws Exception
	{
		SincronizacionDatosDAL localSincronizacionDatosDAL = new SincronizacionDatosDAL();
		Cursor localCursor = null;
		SincronizacionDatos localSincronizacionDatos = null;
		
		try
		{
			localCursor = localSincronizacionDatosDAL.ObtenerSincronizacionDatosDetallePor(empleadoId,dia,mes,anio,rol);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el registro de sincronizacion datos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el registro de sincronizacion datos: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			localSincronizacionDatos = new SincronizacionDatos(localCursor.getInt(1), localCursor.getInt(2), 
															localCursor.getInt(3), localCursor.getInt(4),localCursor.getInt(5));
		}

		return localSincronizacionDatos;
	}

	public long ModificarFechaSinconizacionDatos(int empleadoId,int dia,int mes,int anio) throws Exception
	{
	    try
	    {
	    	long l = new SincronizacionDatosDAL().ModificarFechaSincronizacionDatos(empleadoId, dia, mes, anio);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionDatos por empleadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionDatos por empleadoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public ArrayList<SincronizacionDatos> ObtenerDetalleSincronizacionesDatos()throws Exception
	{
		SincronizacionDatosDAL localSincronizacionDatosDAL = new SincronizacionDatosDAL();
		ArrayList<SincronizacionDatos> listadoSincronizacionDatos = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = localSincronizacionDatosDAL.ObtenerSincronizacionesDatos();
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizaciones datos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizaciones datos: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoSincronizacionDatos = new ArrayList<SincronizacionDatos>();
			do
			{
				listadoSincronizacionDatos.add(new SincronizacionDatos(localCursor.getInt(1), localCursor.getInt(2), 
						localCursor.getInt(3), localCursor.getInt(4),localCursor.getInt(5)));
			}
			while(localCursor.moveToNext());
		}

		return listadoSincronizacionDatos;
	}
}
