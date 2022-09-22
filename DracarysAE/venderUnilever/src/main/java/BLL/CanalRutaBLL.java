package BLL;

import java.util.ArrayList;

import Clases.CanalRuta;
import Clases.CanalRutaWSResult;
import DAL.CanalRutaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CanalRutaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarCanalesRuta()throws Exception
	{
		try
		{
			boolean bool = new CanalRutaDAL().BorrarCanalesRuta();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canalesRuta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canalesRuta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarCanalRuta(ArrayList<CanalRutaWSResult> canalesRuta) throws Exception
	{
		try
		{
			long l = new CanalRutaDAL().InsertarCanalRuta(canalesRuta);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los canales de ruta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los canales de ruta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public CanalRuta ObtenerCanalRutaPor(int canalRutaId) throws Exception
	{
		CanalRuta localCanalRuta = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new CanalRutaDAL().ObtenerCanalRutaPor(canalRutaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta por canalRutaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta por canalRutaId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localCanalRuta = new CanalRuta(localCursor.getInt(1), localCursor.getString(2));
	    }
	    
		return localCanalRuta;
    }
	
	public ArrayList<CanalRuta> ObtenerCanalesRuta() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<CanalRuta> listadoCanalRuta = null;
		try
		{
			localCursor = new CanalRutaDAL().ObtenerCanalesRuta();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoCanalRuta = new ArrayList<CanalRuta>();
			do
			{
				CanalRuta localCanalRuta = new CanalRuta(localCursor.getInt(1), localCursor.getString(2));
				
				listadoCanalRuta.add(localCanalRuta);
	        }
			while (localCursor.moveToNext());
		}

        return listadoCanalRuta;
	}
}
