package BLL;

import java.util.ArrayList;

import Clases.MotivoCHANGE;
import DAL.MotivoCHANGEDAL;
import android.database.Cursor;

public class MotivoCHANGEBLL 
{
MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMotivosCHANGE()throws Exception
	{
		try
		{
			boolean bool = new MotivoCHANGEDAL().BorrarMotivosCHANGE();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos CHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos CHANGE: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarMotivoCHANGE(int motivoCHANGEId, String descripcion) throws Exception
	{
		try
		{
			long l = new MotivoCHANGEDAL().InsertarMotivoCHANGE(motivoCHANGEId, descripcion);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el motivo CHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el motivo CHANGE: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public MotivoCHANGE ObtenerMotivoCHANGEPor(int motivoCHANGEId) throws Exception
	{
		MotivoCHANGE localMotivoCHANGE = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new MotivoCHANGEDAL().ObtenerMotivoCHANGEPor(motivoCHANGEId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop por motivo CHANGEId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop por motivo CHANGEId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localMotivoCHANGE = new MotivoCHANGE(localCursor.getInt(1), localCursor.getString(2));
	    }
	    
		return localMotivoCHANGE;
    }
		  
	public ArrayList<MotivoCHANGE> ObtenerMotivosCHANGE() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<MotivoCHANGE> listadoMotivoCHANGE = null;
		try
		{
			localCursor = new MotivoCHANGEDAL().ObtenerMotivosCHANGE();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos CHANGE: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoMotivoCHANGE = new ArrayList<MotivoCHANGE>();
			listadoMotivoCHANGE.add(new MotivoCHANGE(0,"Seleccione una opcion ..."));
			do
			{
				MotivoCHANGE localMotivoCHANGE = new MotivoCHANGE(localCursor.getInt(1), localCursor.getString(2));
				
				listadoMotivoCHANGE.add(localMotivoCHANGE);
	        }
			while (localCursor.moveToNext());
		}

        return listadoMotivoCHANGE;
	}
}
