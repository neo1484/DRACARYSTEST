package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ClienteNitWSResult;
import Clases.LoginEmpleado;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ClienteNitDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesNit() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesNit();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNit: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesNit: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public boolean BorrarClientenitPor(int clienteId) throws Exception
	  {
	    db.OpenDB();
	    try
	    {
	    	db.borrarClienteNitPorClienteId(clienteId);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al borrar el clienteNit por clienteId.: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el clienteNit por clienteId.: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }

	  public long InsertarClienteNit(ArrayList<ClienteNitWSResult> clientesNit, LoginEmpleado loginEmpleado) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarClienteNit(clientesNit, loginEmpleado);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los nits de los clientes: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los nits de los clientes: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	public long InsertarClienteNit(int clienteId, String nombreFactura,String nit,int empleadoId,int dia,int mes,
								   int anio,boolean sincronizacion,String tipoNit) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarClienteNit(clienteId, nombreFactura, nit, empleadoId, dia, mes, anio, sincronizacion, tipoNit);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteNit: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	  public int ModificarClienteNit(int clienteId,String nit,String tipoNit) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarClienteNit(clienteId, nit, tipoNit);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nit del cliente: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la nit del cliente: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarClienteNitSincro(int clienteId,int newClienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarClienteNitSincro(clienteId, newClienteId);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId del cliente: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId del cliente: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClienteNitPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClienteNitPor(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteNit por clienteId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clienteNit por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClienteNitsNoSincronizados() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClienteNitNoSincronizados();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
           			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los nits del cliente no sincronizados: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los nits del cliente no sincronizados: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
