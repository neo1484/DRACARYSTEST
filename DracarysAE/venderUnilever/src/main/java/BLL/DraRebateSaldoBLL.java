package BLL;

import java.util.ArrayList;
import Clases.DraRebateSaldo;
import Clases.DraRebateSaldoWSResult;
import DAL.DraRebateSaldoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class DraRebateSaldoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarDraRebatesSaldo()throws Exception
	{
		try
		{
			boolean bool = new DraRebateSaldoDAL().BorrarDraRebatesSaldo();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los saldos del rabate dracaris: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los saldos del rebate dracaris: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarDraRebateSaldo(ArrayList<DraRebateSaldoWSResult> drasRebateSaldo) throws Exception
	{
		try
		{
			long l = new DraRebateSaldoDAL().InsertarDraRebateSaldo(drasRebateSaldo);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los saldos del rebate dracaris: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los saldos del rebate dracaris: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public DraRebateSaldo ObtenerDraRebateSaldoPor(int clienteId) throws Exception
	{
		DraRebateSaldo localDraRebateSaldo = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new DraRebateSaldoDAL().ObtenerDraRebateSaldoPor(clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate dracaris: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate dracaris: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localDraRebateSaldo = new DraRebateSaldo(localCursor.getInt(1), localCursor.getFloat(2), localCursor.getFloat(3));
	    }
	    
		return localDraRebateSaldo;
    }
		  
	public ArrayList<DraRebateSaldo> ObtenerDraRebatesSaldo() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<DraRebateSaldo> listadoDraRebateSaldo = null;
		try
		{
			localCursor = new DraRebateSaldoDAL().ObtenerDraRebatesSaldo();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los saldos del rebate dracaris: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los saldos del rebate dracaris: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoDraRebateSaldo = new ArrayList<DraRebateSaldo>();
			do
			{
				DraRebateSaldo localDraRebateSaldo = new DraRebateSaldo(localCursor.getInt(1), localCursor.getFloat(2), localCursor.getFloat(3));
				
				listadoDraRebateSaldo.add(localDraRebateSaldo);
	        }
			while (localCursor.moveToNext());
		}

        return listadoDraRebateSaldo;
	}
	
	public long ModificarSaldoDraRebateSaldo(int clienteId, float saldo)throws Exception
	{
	    try
	    {
	    	long l = new DraRebateSaldoDAL().ModificarSaldoDraRebateSaldo(clienteId, saldo);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el saldo del rebate dracaris: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el saldo del rebate dracaris: " + localException.getMessage());
	    	}
	    	return 0;
	    }
	}
	
	public int AbonarSaldoDraRebateSaldo(int clienteId, float saldo) throws Exception
    {
        int m = 0;
        try
        {
            m = new DraRebateSaldoDAL().AbonarSaldoDraRebateSaldo(clienteId, saldo);
            m = 1;
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al abonar el saldo del cliente: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al abonar el saldo del cliente: " + localException.getMessage());
            }
            throw localException;
        }

        return m;
    }
}