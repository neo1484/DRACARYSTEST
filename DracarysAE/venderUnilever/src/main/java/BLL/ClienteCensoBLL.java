package BLL;

import java.util.ArrayList;

import Clases.ClienteCenso;
import Clases.ClienteCensoWSResult;
import DAL.ClienteCensoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ClienteCensoBLL 
{
MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarClientesCenso() throws Exception
	{
		try
		{
			boolean bool = new ClienteCensoDAL().BorrarClientesCenso();
			return bool;
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
	}
		  
	public long InsertarClienteCenso(ArrayList<ClienteCensoWSResult> clientesCenso) throws Exception
	{
		try
		{
			long l = new ClienteCensoDAL().InsertarClienteCenso(clientesCenso);
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
	}
		  
	public ClienteCenso ObtenerClienteCensoPor(String codigo) throws Exception
	{
		ClienteCenso localClienteCenso = null;
		Cursor localCursor = null;

		try
		{
			localCursor = new ClienteCensoDAL().ObtenerClienteCensoPor(codigo);
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente censo por codigo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente censo por codigo: " + localException.getMessage());
	    	}
	    	throw localException;
		}
		
		if (localCursor.getCount() > 0) 
		{
			localClienteCenso = new ClienteCenso(localCursor.getInt(0), localCursor.getString(1), localCursor.getString(2), 
												localCursor.getInt(3), localCursor.getString(4), localCursor.getString(5),
												localCursor.getDouble(6), localCursor.getDouble(7), localCursor.getString(8), 
												localCursor.getString(9), localCursor.getInt(10), localCursor.getDouble(11), 
												localCursor.getDouble(12), localCursor.getInt(13), localCursor.getInt(14), 
												localCursor.getInt(15), localCursor.getInt(16), localCursor.getInt(17),
												localCursor.getInt(18), localCursor.getInt(19),localCursor.getInt(20),
												localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,
												localCursor.getInt(23));
		}
		return localClienteCenso;
	}
	
	public ClienteCenso ObtenerClienteCensoPor(int id) throws Exception
	{
		ClienteCenso localClienteCenso = null;
		Cursor localCursor = null;

		try
		{
			localCursor = new ClienteCensoDAL().ObtenerClienteCensoPor(id);
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente censo por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente censo por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
		}
		
		if (localCursor.getCount() > 0) 
		{
			localClienteCenso = new ClienteCenso(localCursor.getInt(0), localCursor.getString(1), localCursor.getString(2), 
												localCursor.getInt(3), localCursor.getString(4), localCursor.getString(5),
												localCursor.getDouble(6), localCursor.getDouble(7), localCursor.getString(8), 
												localCursor.getString(9), localCursor.getInt(10), localCursor.getDouble(11), 
												localCursor.getDouble(12), localCursor.getInt(13), localCursor.getInt(14), 
												localCursor.getInt(15), localCursor.getInt(16), localCursor.getInt(17),
												localCursor.getInt(18), localCursor.getInt(19),localCursor.getInt(20),
												localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,
												localCursor.getInt(23));
		}
		return localClienteCenso;
	}
		  
	public ArrayList<ClienteCenso> ObtenerClientesCenso() throws Exception
	{
		ArrayList<ClienteCenso> listadoClienteCenso = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ClienteCensoDAL().ObtenerClientesCenso();
		}
		catch(Exception localException)
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
		
		if(localCursor.getCount()>0)
		{
			listadoClienteCenso = new ArrayList<ClienteCenso>();
			do
			{
				ClienteCenso localClienteCenso = new ClienteCenso(localCursor.getInt(0), localCursor.getString(1), localCursor.getString(2), 
						localCursor.getInt(3), localCursor.getString(4), localCursor.getString(5),
						localCursor.getDouble(6), localCursor.getDouble(7), localCursor.getString(8), 
						localCursor.getString(9), localCursor.getInt(10), localCursor.getDouble(11), 
						localCursor.getDouble(12), localCursor.getInt(13), localCursor.getInt(14), 
						localCursor.getInt(15), localCursor.getInt(16), localCursor.getInt(17),
						localCursor.getInt(18), localCursor.getInt(19),localCursor.getInt(20),
						localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,
						localCursor.getInt(23));
				
				listadoClienteCenso.add(localClienteCenso);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoClienteCenso;
	}
	
	public int ModificarClienteCenso(int id,String codigo,String referencia,int tipoNegocioIdVender,String tipoNegocio, String contacto, 
			  double latitud, double longitud,String nombres,String paterno,int creadorId,double latitudCreador,
			  double longitudCreador,int zonaId,int rutaId,int diaId,int mercadoId,int diaCreacion,
			  int mesCreacion,int anioCreacion,int estado,int clienteId,boolean sincro) throws Exception
	{
	    try
	    {
	    	int i = new ClienteCensoDAL().ModificarClienteCenso(id,codigo, referencia, tipoNegocioIdVender,
	    								tipoNegocio, contacto,latitud, longitud, nombres, paterno, creadorId, 
	    								latitudCreador,longitudCreador, zonaId, rutaId, diaId, mercadoId, 
	    								diaCreacion,mesCreacion, anioCreacion, estado, clienteId, sincro);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente censo: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarClienteCensoEstado(int id,int estado,String codigo,String referencia,String contacto,int motivoElimnacionId) throws Exception
	{
	    try
	    {
	    	int i = new ClienteCensoDAL().ModificarClienteCensoEstado(id, estado, codigo, referencia, contacto, motivoElimnacionId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del estado del cliente censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del estado del cliente censo: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public long ModificarClienteCensoincronizacion(int id,boolean sincro) throws Exception
	{
	    try
	    {
	    	long l = new ClienteCensoDAL().ModificarClienteCensoSincronizacion(id,sincro);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente censo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente censo: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public ArrayList<ClienteCenso> ObtenerClientesCensoNoSincronizados() throws Exception
	{
		ArrayList<ClienteCenso> listadoClienteCenso = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ClienteCensoDAL().ObtenerClientesCensoNoSincronizados();
		}
		catch(Exception localException)
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
		
		if(localCursor.getCount()>0)
		{
			listadoClienteCenso = new ArrayList<ClienteCenso>();
			do
			{
				ClienteCenso localClienteCenso = new ClienteCenso(localCursor.getInt(0), localCursor.getString(1), localCursor.getString(2), 
						localCursor.getInt(3), localCursor.getString(4), localCursor.getString(5),
						localCursor.getDouble(6), localCursor.getDouble(7), localCursor.getString(8), 
						localCursor.getString(9), localCursor.getInt(10), localCursor.getDouble(11), 
						localCursor.getDouble(12), localCursor.getInt(13), localCursor.getInt(14), 
						localCursor.getInt(15), localCursor.getInt(16), localCursor.getInt(17),
						localCursor.getInt(18), localCursor.getInt(19),localCursor.getInt(20),
						localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,
						localCursor.getInt(23));
				
				listadoClienteCenso.add(localClienteCenso);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoClienteCenso;
	}
}
