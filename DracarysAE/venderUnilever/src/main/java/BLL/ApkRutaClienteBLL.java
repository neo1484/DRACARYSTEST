package BLL;

import java.util.ArrayList;
import Clases.ApkRutaCliente;
import Clases.ApkRutaClienteWSResult;
import DAL.ApkRutaClienteDAL;
import DAL.RutaDAL;

import android.database.Cursor;

public class ApkRutaClienteBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarApksRutaCliente()throws Exception
	{
		try
		{
			boolean bool = new ApkRutaClienteDAL().BorrarApksRutaCliente();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apksRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los apksRutaCliente: " + localException.getMessage());
			} 
			throw localException;
	    }
	}

	public long insertarApkRutaCliente(ArrayList<ApkRutaClienteWSResult> apkRutas)throws Exception
	{
		try
		{
			long l = new ApkRutaClienteDAL().insertarApkRutaCliente(apkRutas);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las apk rutas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las apk rutas: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public long InsertarApkRutaCliente(int rutaId,int diaId,String rutaNombre,String diaNombre, boolean bloquearPreventaDistancia,boolean rutaCompleta) throws Exception
	{
		try
		{
			long l = new ApkRutaClienteDAL().InsertarApkRutaCliente(rutaId, diaId, rutaNombre, diaNombre, bloquearPreventaDistancia, rutaCompleta);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la apkRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la apkRutaCliente: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ApkRutaCliente ObtenerApkRutaClientePor(int rutaId) throws Exception
	{
		ApkRutaCliente localApkRutaCliente = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ApkRutaClienteDAL().ObtenerApkRutaClientePor(rutaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los apksRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los apksRutaCliente: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localApkRutaCliente = new ApkRutaCliente(localCursor.getInt(1), localCursor.getInt(2), localCursor.getString(3),
													localCursor.getString(4),localCursor.getString(5).equals("1")?true:false,
													localCursor.getString(6).equals("1")?true:false);
	    }
	    
		return localApkRutaCliente;
    }
		  
	public ArrayList<ApkRutaCliente> ObtenerApksRutaCliente() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<ApkRutaCliente> listadoApkRutaCliente = null;
		try
		{
			localCursor = new ApkRutaClienteDAL().ObtenerApksRutaCliente();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las apksRutaCliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las apksRutaCliente: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoApkRutaCliente = new ArrayList<ApkRutaCliente>();
			do
			{
				ApkRutaCliente localApkRutaCliente = new ApkRutaCliente(localCursor.getInt(1), localCursor.getInt(2), 
																		localCursor.getString(3),localCursor.getString(4),localCursor.getString(5).equals("1")?true:false,
																				localCursor.getString(6).equals("1")?true:false);
				
				listadoApkRutaCliente.add(localApkRutaCliente);
	        }
			while (localCursor.moveToNext());
		}

        return listadoApkRutaCliente;
	}
}
