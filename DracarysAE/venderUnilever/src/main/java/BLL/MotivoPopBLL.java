package BLL;

import java.util.ArrayList;

import Clases.MotivoPop;
import Clases.MotivoPopWSResult;
import DAL.MotivoPopDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class MotivoPopBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMotivosPop()throws Exception
	{
		try
		{
			boolean bool = new MotivoPopDAL().BorrarMotivosPop();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos pop: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos pop: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarMotivoPop(ArrayList<MotivoPopWSResult> motivosPOP) throws Exception
	{
		try
		{
			long l = new MotivoPopDAL().InsertarMotivoPop(motivosPOP);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos POP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos POP: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public MotivoPop ObtenerMotivoPopPor(int motivoPopId) throws Exception
	{
		MotivoPop localMotivoPop = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new MotivoPopDAL().ObtenerMotivoPopPor(motivoPopId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop por motivoPopId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop por motivoPopId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localMotivoPop = new MotivoPop(localCursor.getInt(1), localCursor.getString(2));
	    }
	    
		return localMotivoPop;
    }
		  
	public ArrayList<MotivoPop> ObtenerMotivosPop() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<MotivoPop> listadoMotivoPop = null;
		try
		{
			localCursor = new MotivoPopDAL().ObtenerMotivosPop();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos pop: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoMotivoPop = new ArrayList<MotivoPop>();
			listadoMotivoPop.add(new MotivoPop(0,"Seleccione una opcion ..."));
			do
			{
				MotivoPop localMotivoPop = new MotivoPop(localCursor.getInt(1), localCursor.getString(2));
				
				listadoMotivoPop.add(localMotivoPop);
	        }
			while (localCursor.moveToNext());
		}

        return listadoMotivoPop;
	}
}