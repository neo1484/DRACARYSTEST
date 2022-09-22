package BLL;

import java.util.ArrayList;

import Clases.CanalRutaPrecio;
import Clases.CanalRutaPrecioWSResult;
import DAL.CanalRutaPrecioDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CanalRutaPrecioBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarCanalesRutaPrecio()throws Exception
	{
		try
		{
			boolean bool = new CanalRutaPrecioDAL().BorrarCanalesRutaPrecio();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canalesRutaPrecio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canalesRutaPrecio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarCanalRutaPrecio(ArrayList<CanalRutaPrecioWSResult> canalesRutaPrecio) throws Exception
	{
		try
		{
			long l = new CanalRutaPrecioDAL().InsertarCanalRutaPrecio(canalesRutaPrecio);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los canales ruta precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los canales ruta precio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public CanalRutaPrecio ObtenerCanalRutaPrecioPor(int canalPrecioRutaId) throws Exception
	{
		CanalRutaPrecio localCanalRutaPrecio = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new CanalRutaPrecioDAL().ObtenerCanalRutaPreciorPor(canalPrecioRutaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta precio por canalPrecioRutaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta precio por canalPrecioRutaId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localCanalRutaPrecio = new CanalRutaPrecio(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), localCursor.getFloat(4), 
														localCursor.getFloat(5), localCursor.getFloat(6));
	    }
	    
		return localCanalRutaPrecio;
    }
		  
	public CanalRutaPrecio ObtenerCanalRutaPrecioPorCanalRutaIdYProductoId(int canalRutaId,int productoId) throws Exception
	{
		CanalRutaPrecio localCanalRutaPrecio = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new CanalRutaPrecioDAL().obtenerCanalRutaPrecioPorCanalRutaIdYProductoId(canalRutaId, productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta precio por canalRutaId y productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta precio por canalRutaId y productoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localCanalRutaPrecio = new CanalRutaPrecio(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), localCursor.getFloat(4), 
														localCursor.getFloat(5), localCursor.getFloat(6));
	    }
	    
		return localCanalRutaPrecio;
    }
	
	public ArrayList<CanalRutaPrecio> ObtenerCanalesrutaPrecio() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<CanalRutaPrecio> listadoCanalRutaPrecio = null;
		try
		{
			localCursor = new CanalRutaPrecioDAL().ObtenerCanalesRutaPrecio();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los canales ruta precio: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoCanalRutaPrecio = new ArrayList<CanalRutaPrecio>();
			do
			{
				CanalRutaPrecio localCanlrutaPrecio = new CanalRutaPrecio(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), localCursor.getFloat(4), 
																		localCursor.getFloat(5), localCursor.getFloat(6));
				
				listadoCanalRutaPrecio.add(localCanlrutaPrecio);
	        }
			while (localCursor.moveToNext());
		}

        return listadoCanalRutaPrecio;
	}
}
