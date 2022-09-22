package BLL;

import java.util.ArrayList;

import Clases.Descuento2;
import Clases.MotivoNoEntrega;
import DAL.Descuento2DAL;
import DAL.MotivoNoEntregaDAL;
import android.database.Cursor;

public class Descuento2BLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarDescuentos2()throws Exception
	{
		try
		{
			boolean bool = new Descuento2DAL().BorrarDescuentos2();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los descuento2: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los descuento2: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarDescuentos(int distribuidorId,float monto) throws Exception
	{
		try
		{
			long l = new Descuento2DAL().InsertarDescuento2(distribuidorId, monto);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento2: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el descuento2: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Descuento2 ObtenerDescuento2Por(int distribuidorId) throws Exception
	{
		Descuento2 localDescuento2 = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new Descuento2DAL().ObtenerDescuento2Por(distribuidorId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el descuento2 por distribuidorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el descuento2 por distribuidorId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
	        localDescuento2 = new Descuento2(localCursor.getInt(1), localCursor.getFloat(2));
	    }
	    
		return localDescuento2;
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
