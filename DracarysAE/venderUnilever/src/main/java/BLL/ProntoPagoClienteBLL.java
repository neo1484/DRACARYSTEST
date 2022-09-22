package BLL;

import java.util.ArrayList;

import Clases.ProntoPagoCliente;
import Clases.ProntoPagoClienteWSResult;
import DAL.ProntoPagoClienteDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProntoPagoClienteBLL 
{
MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProntosPagoCliente()throws Exception
	{
		try
		{
			boolean bool = new ProntoPagoClienteDAL().BorrarProntosPagoCliente();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago cliente: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarProntoPagoCliente(ArrayList<ProntoPagoClienteWSResult> prontosPagocliente) throws Exception
	{
		try
		{
			long l = new ProntoPagoClienteDAL().InsertarProntoPagoCliente(prontosPagocliente);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago cliente: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ProntoPagoCliente ObtenerProntoPagoClientePor(int clienteId) throws Exception
	{
		ProntoPagoCliente localProntoPagoCliente = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoClienteDAL().ObtenerProntoPagoClientePor(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago cliente por prontoPagoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago cliente por prontoPagoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localProntoPagoCliente = new ProntoPagoCliente(localCursor.getInt(1),localCursor.getInt(2));
	    }
	    
		return localProntoPagoCliente;
    }
		  
	public ArrayList<ProntoPagoCliente> ObtenerProntosPagoCliente() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<ProntoPagoCliente> listadoProntoPagoCliente = null;
		try
		{
			localCursor = new ProntoPagoClienteDAL().ObtenerProntosPagoCliente();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago cliente: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProntoPagoCliente = new ArrayList<ProntoPagoCliente>();
			do
			{
				ProntoPagoCliente localProntoPagoCliente = new ProntoPagoCliente(localCursor.getInt(1),localCursor.getInt(2));
				
				listadoProntoPagoCliente.add(localProntoPagoCliente);
	        }
			while (localCursor.moveToNext());
		}

        return listadoProntoPagoCliente;
	}
}
