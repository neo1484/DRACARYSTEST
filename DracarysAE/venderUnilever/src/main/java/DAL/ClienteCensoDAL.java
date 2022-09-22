package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ClienteCensoWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ClienteCensoDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesCenso() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarClientesCenso();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes censo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarClienteCenso(ArrayList<ClienteCensoWSResult> clientesCenso) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarClienteCenso(clientesCenso);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes censo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClienteCensoPor(String codigo) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesCensoPor(codigo);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente censo por codigo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente censo por codigo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	 
	public Cursor ObtenerClienteCensoPor(int id) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesCensoPor(id);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente censo por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente censo por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerClientesCenso()throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesCenso();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public int ModificarClienteCenso(int id,String codigo,String referencia,int tipoNegocioIdVender,String tipoNegocio, 
				String contacto,double latitud, double longitud,String nombres,String paterno,int creadorId,
				double latitudCreador,double longitudCreador,int zonaId,int rutaId,int diaId,int mercadoId,int diaCreacion,
				int mesCreacion,int anioCreacion,int estado,int clienteId,boolean sincro)throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarClienteCenso(id, codigo, referencia, tipoNegocioIdVender, tipoNegocio, contacto, latitud, longitud, 
					nombres, paterno, creadorId, latitudCreador, longitudCreador, zonaId, rutaId, diaId, mercadoId, 
					diaCreacion, mesCreacion, anioCreacion, estado, clienteId, sincro);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente censo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente censo: " + localException.getMessage());
			}
		throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public int ModificarClienteCensoEstado(int id,int estado,String codigo, String referencia, String contacto,int motivoEliminacionId)throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarClienteCensoEstado(id, estado, codigo, referencia, contacto, motivoEliminacionId);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado del cliente censo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado del cliente censo: " + localException.getMessage());
			}
		throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public long ModificarClienteCensoSincronizacion(int id,boolean sincro) throws Exception
	{
		db.OpenDB();
		try
		{
			long i = db.modificarClienteCensoSincronizacion(id, sincro);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar cliente censo por codigo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar cliente censo por codigo: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerClientesCensoNoSincronizados()throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesCensoNoSincronizados();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo no sincronizados: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}
