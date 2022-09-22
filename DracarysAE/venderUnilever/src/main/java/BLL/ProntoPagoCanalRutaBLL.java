package BLL;

import java.util.ArrayList;
import Clases.ProntoPagoCanalRuta;
import Clases.ProntoPagoCanalRutaWSResult;
import DAL.ProntoPagoCanalRutaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProntoPagoCanalRutaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProntosPagoCanalRuta()throws Exception
	{
		try
		{
			boolean bool = new ProntoPagoCanalRutaDAL().BorrarProntosPagoCanalRuta();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago canal ruta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago canal ruta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarProntoPagoCanalRuta(ArrayList<ProntoPagoCanalRutaWSResult> prontosPagoCanalRuta) throws Exception
	{
		try
		{
			long l = new ProntoPagoCanalRutaDAL().InsertarProntoPagoCanalRuta(prontosPagoCanalRuta);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago canal ruta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago canal ruta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ProntoPagoCanalRuta ObtenerProntoPagoCanalRutaPor(int canalRutaId) throws Exception
	{
		ProntoPagoCanalRuta localProntoPagoCanalRuta = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoCanalRutaDAL().ObtenerProntoPagoCanalRutaPor(canalRutaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago canal ruta por canalRutaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago canal ruta por canalRutaId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localProntoPagoCanalRuta = new ProntoPagoCanalRuta(localCursor.getInt(1),localCursor.getInt(2));
	    }
	    
		return localProntoPagoCanalRuta;
    }
		  
	public ArrayList<ProntoPagoCanalRuta> ObtenerProntosPagoCanalRuta() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<ProntoPagoCanalRuta> listadoProntoPagoCanalRuta = null;
		try
		{
			localCursor = new ProntoPagoCanalRutaDAL().ObtenerProntosPagoCanalRuta();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago canal ruta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago canal ruta: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProntoPagoCanalRuta = new ArrayList<ProntoPagoCanalRuta>();
			do
			{
				ProntoPagoCanalRuta localProntoPagoCanalRuta = new ProntoPagoCanalRuta(localCursor.getInt(1),localCursor.getInt(2));
				
				listadoProntoPagoCanalRuta.add(localProntoPagoCanalRuta);
	        }
			while (localCursor.moveToNext());
		}

        return listadoProntoPagoCanalRuta;
	}
}