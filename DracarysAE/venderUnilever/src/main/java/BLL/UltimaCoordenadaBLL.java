package BLL;

import java.util.ArrayList;
import Clases.UltimaCoordenada;
import DAL.UltimaCoordenadaDAL;
import android.database.Cursor;

public class UltimaCoordenadaBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarUltimasCoordenada() throws Exception
	{
		try
		{
			boolean bool = new UltimaCoordenadaDAL().BorrarUltimasCoordenada();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ultimas coordenadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ultimas coordenadas: " + localException.getMessage());
			} 
		    throw localException;
    	}
	}
		  
	public long InsertarUltimaCoordenada(int clienteId,double latitud,double longitud)throws Exception
	{
		try
		{
			long l = new UltimaCoordenadaDAL().InsertarUltimaCoordenada(clienteId, latitud, longitud);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ultima coordenada: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ultima coordenada: " + localException.getMessage());
			} 
			throw localException;
    	}
	}
		  
	public UltimaCoordenada ObtenerUltimaCoordenadaPor(int clienteId) throws Exception
	{
		Cursor localCursor =null;
		UltimaCoordenada localUltimaCoordenada = null;
		
		try
		{
			localCursor = new UltimaCoordenadaDAL().ObtenerUltimaCoordenadaPor(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ultima coordenada por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ultima coordenada por clienteId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount() > 0)
		{
			 localUltimaCoordenada = new UltimaCoordenada(localCursor.getInt(1), localCursor.getDouble(2), localCursor.getDouble(3));
		}
		
	    return localUltimaCoordenada;
	}
		  
	public ArrayList<UltimaCoordenada> ObtenerUltimassCoordenadas() throws Exception
	{
		ArrayList<UltimaCoordenada> listadoUltimaCoordenada = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new UltimaCoordenadaDAL().ObtenerUltimasCoordenadas();
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
		}
		
		if(localCursor != null && localCursor.getCount() > 0)
		{
			listadoUltimaCoordenada = new ArrayList<UltimaCoordenada>();
			do
			{
				UltimaCoordenada localUltimaCoordenada = new UltimaCoordenada(localCursor.getInt(1), localCursor.getDouble(2), localCursor.getDouble(3));
				
				listadoUltimaCoordenada.add(localUltimaCoordenada);				
			}
			while(localCursor.moveToNext());
		}
		
		return listadoUltimaCoordenada;
	}
}
