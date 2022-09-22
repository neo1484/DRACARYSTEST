package BLL;

import java.util.ArrayList;

import Clases.PrecioListaNombre;
import Clases.PrecioListaNombreWSResult;
import DAL.PrecioListaNombreDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PrecioListaNombreBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreciosListaNombre() throws Exception
	{
		try
		{
			boolean bool = new PrecioListaNombreDAL().BorrarPreciosListaNombre();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los preciosListaNombre: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los preciosListaNombre: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsetarPrecioListaNombre(ArrayList<PrecioListaNombreWSResult> preciosLista) throws Exception
	{
		try
		{
			long l = new PrecioListaNombreDAL().InsertarPrecioListaNombre(preciosLista);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los preciosLista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los preciosLista: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public PrecioListaNombre ObtenerPrecioListaNombrePorPrecioListaNombreId(int precioListaNombreId)throws Exception
	{
		Cursor localCursor =null;
		PrecioListaNombre localPrecioListaNombre = null;
		try
		{
			localCursor = new PrecioListaNombreDAL().ObtenerPrecioListaNombrePorPrecioListaNombreId(precioListaNombreId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioListaNombre por precioListaNombreId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioListaNombre por precioListaNombreId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localPrecioListaNombre = new PrecioListaNombre(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localPrecioListaNombre;
	}
		  
	public ArrayList<PrecioListaNombre> ObtenerPreciosListaNombre() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<PrecioListaNombre> listadoPrecioListaNombre = null;
		
		try
		{
			localCursor = new PrecioListaNombreDAL().ObtenerPreciosListaNombre();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los preciosListaNombre: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los preciosListaNombre: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPrecioListaNombre = new ArrayList<PrecioListaNombre>();
			listadoPrecioListaNombre.add(new PrecioListaNombre(0,"[Seleccione una lista de precios ...]"));
			do
			{
				PrecioListaNombre localPrecioListaNombre = new PrecioListaNombre(localCursor.getInt(1),localCursor.getString(2));
				listadoPrecioListaNombre.add(localPrecioListaNombre);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoPrecioListaNombre;
	}
}
