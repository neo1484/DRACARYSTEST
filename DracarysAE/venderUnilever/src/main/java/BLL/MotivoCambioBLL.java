package BLL;

import java.util.ArrayList;

import Clases.MotivoCambio;
import Clases.MotivoCambioWSResult;
import DAL.MotivoCambioDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class MotivoCambioBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMotivosCambio()throws Exception
	{
		try
		{
			boolean bool = new MotivoCambioDAL().BorrarMotivosCambio();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos cambio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarMotivoCambio(ArrayList<MotivoCambioWSResult> motivosCambio) throws Exception
	{
		try
		{
			long l = new MotivoCambioDAL().InsertarMotivoCambio(motivosCambio);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivso cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos cambio: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public MotivoCambio ObtenerMotivoCambioPor(int motivoCambioId) throws Exception
	{
		MotivoCambio localMotivoCambio = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new MotivoCambioDAL().ObtenerMotivoCambioPor(motivoCambioId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos cambio por motivoCambioId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos cambio por motivoCambioId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localMotivoCambio = new MotivoCambio(localCursor.getInt(1), localCursor.getString(2));
	    }
	    
		return localMotivoCambio;
    }
		  
	public ArrayList<MotivoCambio> ObtenerMotivosCambio() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<MotivoCambio> listadoMotivoCambio = null;
		try
		{
			localCursor = new MotivoCambioDAL().ObtenerMotivosCambio();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos cambio: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoMotivoCambio = new ArrayList<MotivoCambio>();
			listadoMotivoCambio.add(new MotivoCambio(0,"Seleccione una opcion ..."));
			do
			{
				MotivoCambio localMotivoCambio = new MotivoCambio(localCursor.getInt(1), localCursor.getString(2));
				
				listadoMotivoCambio.add(localMotivoCambio);
	        }
			while (localCursor.moveToNext());
		}

        return listadoMotivoCambio;
	}

}
