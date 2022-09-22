package BLL;

import java.util.ArrayList;
import Clases.ApkDistRutaCliente;
import Clases.ApkDistRutaClienteWSResult;
import DAL.ApkDistRutaClienteDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ApkDistRutaClienteBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarApksDistRutaCliente()throws Exception
	{
		try
		{
			boolean bool = new ApkDistRutaClienteDAL().BorrarApksDistRutaCliente();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apksDistRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los apksDistRutaCliente: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarApkDistRutaCliente(ArrayList<ApkDistRutaClienteWSResult> apksDistRutaCliente) throws Exception
	{
		try
		{
			long l = new ApkDistRutaClienteDAL().InsertarApkDistRutaCliente(apksDistRutaCliente);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las rutas del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las rutas del distribuidor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ApkDistRutaCliente ObtenerApkDistRutaClientePor(int rutaId) throws Exception
	{
		ApkDistRutaCliente localApkDistRutaCliente = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ApkDistRutaClienteDAL().ObtenerApkDistRutaClientePor(rutaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los apksDistRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los apksDistRutaCliente: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localApkDistRutaCliente = new ApkDistRutaCliente(localCursor.getInt(1), localCursor.getInt(2), localCursor.getString(3),
													localCursor.getString(4));
	    }
	    
		return localApkDistRutaCliente;
    }
		  
	public ArrayList<ApkDistRutaCliente> ObtenerApksDistRutaCliente() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<ApkDistRutaCliente> listadoApkDistRutaCliente = null;
		try
		{
			localCursor = new ApkDistRutaClienteDAL().ObtenerApksDistRutaCliente();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las apksDistRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las apksDistRutaCliente: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoApkDistRutaCliente = new ArrayList<ApkDistRutaCliente>();
			do
			{
				ApkDistRutaCliente localApkDistRutaCliente = new ApkDistRutaCliente(localCursor.getInt(1), localCursor.getInt(2), 
																		localCursor.getString(3),localCursor.getString(4));
				
				listadoApkDistRutaCliente.add(localApkDistRutaCliente);
	        }
			while (localCursor.moveToNext());
		}

        return listadoApkDistRutaCliente;
	}

}
