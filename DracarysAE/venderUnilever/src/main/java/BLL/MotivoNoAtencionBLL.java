package BLL;

import java.util.ArrayList;
import Clases.MotivoNoAtencion;
import Clases.MotivoNoAtencionWSResult;
import DAL.MotivoNoAtencionDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class MotivoNoAtencionBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMotivosNoAtencion()throws Exception
	{
		try
		{
			boolean bool = new MotivoNoAtencionDAL().BorrarMotivosNoAtencion();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos de no atencion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos de no atencion: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarMotivoNoAtencion(ArrayList<MotivoNoAtencionWSResult> motivosNoAtencion) throws Exception
	{
		try
		{
			long l = new MotivoNoAtencionDAL().InsertarMotivoNoAtencion(motivosNoAtencion);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no atencion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no atencion: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public MotivoNoAtencion ObtenerMotivoNoAtencionPor(int motivoId) throws Exception
	{
		MotivoNoAtencion localMotivoNoAtencion = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new MotivoNoAtencionDAL().ObtenerMotivoNoAtencionPor(motivoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no atencion por motivoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no atencion por motivoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localMotivoNoAtencion = new MotivoNoAtencion(localCursor.getInt(0), localCursor.getInt(1), localCursor.getString(2));
	    }
	    
		return localMotivoNoAtencion;
    }
		  
	public ArrayList<MotivoNoAtencion> ObtenerMotivosNoAtencion() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<MotivoNoAtencion> listadoMotivoNoAtencion = null;
		try
		{
			localCursor = new MotivoNoAtencionDAL().ObtenerMotivosNoAtencion();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no atencion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no atencion: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoMotivoNoAtencion = new ArrayList<MotivoNoAtencion>();
			listadoMotivoNoAtencion.add(new MotivoNoAtencion(0,0,"Seleccione una opcion ..."));
			do
			{
				MotivoNoAtencion localMotivoNoAtencion = new MotivoNoAtencion(localCursor.getInt(0), localCursor.getInt(1), localCursor.getString(2));
				
				listadoMotivoNoAtencion.add(localMotivoNoAtencion);
	        }
			while (localCursor.moveToNext());
		}

        return listadoMotivoNoAtencion;
	}
}
