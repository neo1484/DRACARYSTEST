package BLL;

import java.util.ArrayList;
import Clases.TipoNit;
import Clases.TipoNitWSResult;
import DAL.TipoNitDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class TipoNitBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarTiposNit() throws Exception
	{
		try
		{
			boolean bool = new TipoNitDAL().BorrarTiposNit();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos nit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos nit: " + localException.getMessage());
			} 
		    throw localException;
    	}
	}
		  
	public long InsertarTipoNit(ArrayList<TipoNitWSResult>  tiposNit)throws Exception
	{
		try
		{
			long l = new TipoNitDAL().InsertarTipoNit(tiposNit);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de nit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los tipos de nit: " + localException.getMessage());
			} 
			throw localException;
    	}
	}
		  
	public ArrayList<TipoNit> ObtenerTiposNit() throws Exception
	{
		ArrayList<TipoNit> listadoTemporal = null;
		ArrayList<TipoNit> listadoTipoNit = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new TipoNitDAL().ObtenerTiposNit();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos nit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los tipos nit: " + localException.getMessage());
			} 
			return listadoTipoNit;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoTemporal = new ArrayList<TipoNit>();
			do
			{
				TipoNit localTipoNit = new TipoNit(localCursor.getString(1));
				
				listadoTemporal.add(localTipoNit);				
			}
			while(localCursor.moveToNext());
			
			listadoTipoNit = new ArrayList<TipoNit>();
			
			listadoTipoNit.add(new TipoNit("Seleccione un tipo de NIT"));
			
			for(TipoNit item : listadoTemporal)
			{
				listadoTipoNit.add(item);
			}
		}
		
		return listadoTipoNit;
	}

}
