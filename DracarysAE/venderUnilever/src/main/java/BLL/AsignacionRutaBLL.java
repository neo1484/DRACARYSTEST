package BLL;

import java.util.ArrayList;

import Clases.AsignacionRuta;
import Clases.AsignacionRutaWSResult;
import DAL.AsignacionRutaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AsignacionRutaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarAsignacionesRuta()throws Exception
	{
		try
		{
			boolean bool = new AsignacionRutaDAL().BorrarAsignacionesRuta();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las asignacionesRuta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las asignacionesRuta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarAsignacionRuta(ArrayList<AsignacionRutaWSResult> asignacionesRuta) throws Exception
	{
		try
		{
			long l = new AsignacionRutaDAL().InsertarAsignacionRuta(asignacionesRuta);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las asignacionesRuta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las asignacionesRuta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public AsignacionRuta ObtenerAsignacionRutaPor(int clienteId) throws Exception
	{
		AsignacionRuta localAsignacionRuta = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new AsignacionRutaDAL().ObtenerAsignacionesRutaPor(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la asignacionRuta por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la asignacionRuta por clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localAsignacionRuta = new AsignacionRuta();
	    }
	    
		return localAsignacionRuta;
    }
		  
	public ArrayList<AsignacionRuta> ObtenerAsignacionesRuta() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<AsignacionRuta> listadoAsignacionRuta = null;
		try
		{
			localCursor = new AsignacionRutaDAL().ObtenerAsignacionesRuta();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las asignacionesRuta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las asignacionesRuta: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoAsignacionRuta = new ArrayList<AsignacionRuta>();
			do
			{
				AsignacionRuta localAsignacionRuta = new AsignacionRuta();
				
				listadoAsignacionRuta.add(localAsignacionRuta);
	        }
			while (localCursor.moveToNext());
		}

        return listadoAsignacionRuta;
	}
}
