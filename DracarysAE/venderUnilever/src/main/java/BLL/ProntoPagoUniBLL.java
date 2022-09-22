package BLL;

import java.util.ArrayList;

import Clases.ProntoPagoUni;
import Clases.ProntoPagoUniWSResult;
import DAL.ProntoPagoUniDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProntoPagoUniBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProntosPagoUni()throws Exception
	{
		try
		{
			boolean bool = new ProntoPagoUniDAL().BorrarProntosPagoUni();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontosPago unilever: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontosPago unilever: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarProntoPagoUni(ProntoPagoUniWSResult prontosPagoUni) throws Exception
	{
		try
		{
			long l = new ProntoPagoUniDAL().InsertarProntoPagoUni(prontosPagoUni);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago unilever: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago unilever: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ProntoPagoUni ObtenerProntoPagoUniPor(int prontoPagoId) throws Exception
	{
		ProntoPagoUni localProntoPagoUni = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoUniDAL().ObtenerProntoPagoUniPor(prontoPagoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por prontoPagoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por prontoPagoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localProntoPagoUni = new ProntoPagoUni(localCursor.getInt(1),localCursor.getString(2));
	    }
	    
		return localProntoPagoUni;
    }
		  
	public ArrayList<ProntoPagoUni> ObtenerProntosPagoUni() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<ProntoPagoUni> listadoProntoPagoUni = null;
		try
		{
			localCursor = new ProntoPagoUniDAL().ObtenerProntosPagoUni();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago unilever: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago unilever: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProntoPagoUni = new ArrayList<ProntoPagoUni>();
			do
			{
				ProntoPagoUni localProntoPagoUni = new ProntoPagoUni(localCursor.getInt(1),localCursor.getString(2));
				
				listadoProntoPagoUni.add(localProntoPagoUni);
	        }
			while (localCursor.moveToNext());
		}

        return listadoProntoPagoUni;
	}
}
