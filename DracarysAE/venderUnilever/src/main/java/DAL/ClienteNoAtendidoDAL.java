package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Utilidades.AdministradorDB;
import android.database.Cursor;

public class ClienteNoAtendidoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesNoAtendidos() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesNoAtendidos();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes no atendidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes no atendidos: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }
	  
	  public long InsertarClienteNoAtendido(int empleadoId,int clienteId,int motivoId,int dia,int mes,int anio,int hora,
			  								int minuto,double latitud,double longitud,boolean sincronizacion) 
			  		throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long l = db.insertarClienteNoAtendido(empleadoId, clienteId, motivoId, dia, mes, anio, hora, minuto,
					  								latitud,longitud,sincronizacion);
			  return l;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente no atendido: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente no atendido: " + localException.getMessage());
				} 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClientesNoAtendidos() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesNoAtendidos();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
	       			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	
	  public Cursor ObtenerClientesNoAtendidosPor(int empleadoId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClienteNoAtendidoPor(empleadoId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
	       			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por empleadoId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClientesNoAtendidosPorClienteId(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClienteNoAtendidoPorClienteId(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
	       			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por clienteId: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	  public Cursor ObtenerClientesNoAtendidosNoSincronizados() throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesNoAtendidosNoSincronizados();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
	       			myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos no sincronizados: vacio");
			  }
			  else
			  {
				  	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos no sincronizados: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }

	  public int ModificarClienteNOAtendidoSincronizacion(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarClienteNoAtendidoSincronizacion(clienteId);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteNoAtendido: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteNoAtendido: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public int ModificarClienteNOAtendidoSincronizacionDesdeVendedor(int id,int clienteId,boolean sincronizacion) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  int i = db.modificarClienteNoAtendidoSincronizacionDesdeVendedor(id, clienteId, sincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteNoAtendido desde vendedor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteNoAtendido desde vendedor: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
