package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ClienteCobranzaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesCobranza() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesCobranza();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes cobranza: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes cobranza: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarClienteCobranza(SoapObject clienteCobranza)
			  		throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarClienteCobranza(clienteCobranza);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes de cobranza: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes de cobranza: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClientesCobranzaPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesCobranzaPor(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza por clienteId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	  public Cursor ObtenerClientesCobranza() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  return db.obtenerClientesCobranza();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes cobranza: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClienteCobranzaServerId(int rowId,int serverId,float saldo) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      int i = db.ModificarClienteCobranzaServerId(rowId, serverId, saldo);
		      return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del cliente cobranza: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del cliente cobranza: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
