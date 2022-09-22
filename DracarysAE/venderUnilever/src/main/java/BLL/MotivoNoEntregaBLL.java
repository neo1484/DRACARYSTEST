package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.MotivoNoEntrega;
import Clases.MotivoNoEntregaWSResult;
import DAL.MotivoNoEntregaDAL;

public class MotivoNoEntregaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	public boolean BorrarMotivosNoEntrega()throws Exception
	{
		try
		{
			boolean bool = new MotivoNoEntregaDAL().BorrarMotivosNoEntrega();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos de no entrega: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos de no entrega: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarMotivoNoEntrega(ArrayList<MotivoNoEntregaWSResult> motivosNoEntrega) throws Exception
	{
		try
		{
			long l = new MotivoNoEntregaDAL().InsertarMotivoNoEntrega(motivosNoEntrega);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no entrega: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos no entrega: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public MotivoNoEntrega ObtenerMotivoNoEntregaPor(int motivoId) throws Exception
	{
		MotivoNoEntrega localMotivoNoEntrega = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new MotivoNoEntregaDAL().ObtenerMotivoNoEntregaPor(motivoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no entrega por motivoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no entrega por motivoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localMotivoNoEntrega = new MotivoNoEntrega(localCursor.getInt(0), localCursor.getInt(1), localCursor.getString(2));
	    }
	    
		return localMotivoNoEntrega;
    }
		  
	public ArrayList<MotivoNoEntrega> ObtenerMotivosNoEntrega() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<MotivoNoEntrega> listadoMotivoNoEntrega = null;
		try
		{
			localCursor = new MotivoNoEntregaDAL().ObtenerMotivosNoEntrega();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no entrega: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener motivos no entrega: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoMotivoNoEntrega = new ArrayList<MotivoNoEntrega>();
			listadoMotivoNoEntrega.add(new MotivoNoEntrega(0,0,"Seleccione una opcion ..."));
			do
			{
				MotivoNoEntrega localMotivoNoEntrega = new MotivoNoEntrega(localCursor.getInt(0), localCursor.getInt(1), localCursor.getString(2));
				
				listadoMotivoNoEntrega.add(localMotivoNoEntrega);
	        }
			while (localCursor.moveToNext());
		}

        return listadoMotivoNoEntrega;
	}
}
