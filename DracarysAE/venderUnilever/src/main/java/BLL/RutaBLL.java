package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.ApkRutaClienteWSResult;
import Clases.Ruta;
import Clases.RutaWSResult;
import DAL.RutaDAL;

public class RutaBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarRutas() throws Exception
	{
		try
		{
			boolean bool = new RutaDAL().BorrarRutas();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas: " + localException.getMessage());
			} 
		    throw localException;
    	}
	}
		  
	public Ruta ObtenerRutaPor(int rutaId) throws Exception
	{
		Cursor localCursor =null;
		Ruta localRuta = null;
		
		try
		{
			localCursor = new RutaDAL().ObtenerRutaPor(rutaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta por rutaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta por rutaId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount() > 0)
		{
			 localRuta = new Ruta(localCursor.getInt(1), localCursor.getString(2));
		}
		
	    return localRuta;
	}
		  
	public ArrayList<Ruta> ObtenerRutas() throws Exception
	{
		ArrayList<Ruta> listadoTemporal = null;
		ArrayList<Ruta> listadoRuta = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new RutaDAL().ObtenerRutas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las rutas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las rutas: " + localException.getMessage());
			} 
			return listadoRuta;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoTemporal = new ArrayList<Ruta>();
			do
			{
				Ruta localRuta = new Ruta(localCursor.getInt(1),localCursor.getString(2));
				
				listadoTemporal.add(localRuta);				
			}
			while(localCursor.moveToNext());
			
			listadoRuta = new ArrayList<Ruta>();
			
			listadoRuta.add(new Ruta(0,"Seleccione una ruta"));
			
			for(Ruta item : listadoTemporal)
			{
				listadoRuta.add(item);
			}
		}
		
		return listadoRuta;
	}
}
