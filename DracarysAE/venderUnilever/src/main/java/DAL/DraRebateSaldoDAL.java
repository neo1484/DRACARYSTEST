package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.DraRebateSaldoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class DraRebateSaldoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDraRebatesSaldo() throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarDraRebatesSaldo();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los saldos rebate dracaris : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los saldos rebate dracaris: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long InsertarDraRebateSaldo(ArrayList<DraRebateSaldoWSResult> drasRebateSaldo)throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarDraRebateSaldo(drasRebateSaldo);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los saldos del rebate dracaris: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los saldos del rebate dracaris: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerDraRebateSaldoPor(int clienteId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDraRebateSaldoPor(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate dracaris: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el saldo del rebate dracaris: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerDraRebatesSaldo()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerDraRebatesSaldo();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los saldos del rebate dracaris: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los saldos del rebate dracaris: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public long ModificarSaldoDraRebateSaldo(int clienteId, float saldo)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	long l = db.modificarSaldoDraRebateSaldo(clienteId, saldo);
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
	    	
	    	throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	
	public int AbonarSaldoDraRebateSaldo(int clienteId,float saldo) throws Exception
    {
        db.OpenDB();
        try
        {
            int m = db.abonarSaldoDraRebateSaldo(clienteId, saldo);
            return m;
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al abonar el saldo del cliente: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al abonar el saldo del cliente: " + localException.getMessage());
            }
            throw localException;
        }
        finally
        {
            db.CloseDB();
        }
    }
}