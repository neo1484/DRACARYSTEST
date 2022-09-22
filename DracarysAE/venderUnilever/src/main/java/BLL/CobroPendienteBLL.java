package BLL;

import java.util.ArrayList;
import Clases.CobroPendiente;
import Clases.CobroPendienteWSResult;
import DAL.CobroPendienteDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class CobroPendienteBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarCobrosPendientes()throws Exception
	{
		try
		{
			boolean bool = new CobroPendienteDAL().BorrarCobrosPendientes();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cobros pendientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cobros pendientes: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarCobroPendiente(ArrayList<CobroPendienteWSResult> cobrosPendiente) throws Exception
	{
		try
		{
			long l = new CobroPendienteDAL().InsertarCobroPendiente(cobrosPendiente);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los cobros pendientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los cobros pendientes: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public CobroPendiente ObtenerCobroPendientePor(int clienteId) throws Exception
	{
		CobroPendiente localCobroPendiente = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new CobroPendienteDAL().ObtenerCobrosPendientesPor(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cobro pendiente por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cobro pendiente por clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localCobroPendiente = new CobroPendiente(localCursor.getInt(1),localCursor.getInt(2),localCursor.getString(3),
													localCursor.getString(4),localCursor.getFloat(5),localCursor.getString(6),
													localCursor.getInt(7),localCursor.getFloat(8));
	    }
	    
		return localCobroPendiente;
    }
		  
	public ArrayList<CobroPendiente> ObtenerCobrosPendientes() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<CobroPendiente> listadoCobroPendiente = null;
		try
		{
			localCursor = new CobroPendienteDAL().ObtenerCobrosPendientes();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cobros pendientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cobros pendientes: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoCobroPendiente = new ArrayList<CobroPendiente>();
			do
			{
				CobroPendiente localCobroPendiente = new CobroPendiente(localCursor.getInt(1),localCursor.getInt(2),
																		localCursor.getString(3),localCursor.getString(4),
																		localCursor.getFloat(5),localCursor.getString(6),
																		localCursor.getInt(7),localCursor.getFloat(8));
				
				listadoCobroPendiente.add(localCobroPendiente);
	        }
			while (localCursor.moveToNext());
		}

        return listadoCobroPendiente;
	}
}
