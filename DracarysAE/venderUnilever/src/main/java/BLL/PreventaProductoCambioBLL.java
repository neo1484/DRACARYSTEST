package BLL;

import java.util.ArrayList;

import Clases.PreventaProductoCambio;
import Clases.PreventaProductoCambioWSResult;
import DAL.PreventaProductoCambioDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PreventaProductoCambioBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarPreventaProductoCambioPor(long id) throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoCambioDAL().BorrarPreventaProductoCambioPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas producto cambio por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas porudcto cambio por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarPreventasProductoCambio() throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoCambioDAL().BorrarPreventasProductoCambio();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoCambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasPorudctoCambio: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarPreventaProductoCambio(ArrayList<PreventaProductoCambioWSResult> preventasProductoCambio) throws Exception
	{
		try
	    {
			long l = new PreventaProductoCambioDAL().InsertarPreventaProductoCambio(preventasProductoCambio);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas cambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas cambio: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public long InsertarPreventaProductoCambio(int preventaId,int preventaIdServer,int productoId,int cantidad,int clienteId,boolean sincro,int motivoCambioId) throws Exception
	{
		try
		{
			long l = new PreventaProductoCambioDAL().InsertarPreventaProductoCambio(preventaId, preventaIdServer, productoId, cantidad, clienteId, sincro, motivoCambioId);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventa producto cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventa producto cambio: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public ArrayList<PreventaProductoCambio> ObtenerPreventasProductoCambio() throws Exception
	{
		ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCambioDAL().ObtenerpreventasProductoCambio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio: " + localException.getMessage());
			} 
			return listadoPreventaProductoCambio;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCambio = new ArrayList<PreventaProductoCambio>();
			
			do
			{
				PreventaProductoCambio preventaProductoCambio = new PreventaProductoCambio(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getInt(7));
				
				listadoPreventaProductoCambio.add(preventaProductoCambio);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoPreventaProductoCambio;
	}
	
	public ArrayList<PreventaProductoCambio> ObtenerPreventasPorductoCambioPorPreventaId(int preventaId) throws Exception
	{
		ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCambioDAL().ObtenerpreventasProductoCambioPorPreventaId(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio por preventaId: " + localException.getMessage());
			} 
			return listadoPreventaProductoCambio;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCambio = new ArrayList<PreventaProductoCambio>();
			
			do
			{
				PreventaProductoCambio preventaProductoCambio = new PreventaProductoCambio(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getInt(7));
				listadoPreventaProductoCambio.add(preventaProductoCambio);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCambio;
	}
	
	public ArrayList<PreventaProductoCambio> ObtenerPreventasProductoCambioPorPreventaIdServer(int preventaIdServer) throws Exception
	{
		ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCambioDAL().ObtenerpreventasProductoCambioPorPreventaIdServer(preventaIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio por preventaIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio por preventaIdServer: " + localException.getMessage());
			} 
			return listadoPreventaProductoCambio;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCambio = new ArrayList<PreventaProductoCambio>();
			
			do
			{
				PreventaProductoCambio preventaProductoCambio = new PreventaProductoCambio(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getInt(7));
				listadoPreventaProductoCambio.add(preventaProductoCambio);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCambio;
	}
	
	public ArrayList<PreventaProductoCambio> ObtenerPreventasProductoCambioNoSincronizadasPor(int preventaId) throws Exception
	{
		ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCambioDAL().ObtenerpreventasProductoCambioNoSincronizadasPor(preventaId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio no sincronizadas por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio no sincronizadas por preventaId: " + localException.getMessage());
			} 
			return listadoPreventaProductoCambio;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCambio = new ArrayList<PreventaProductoCambio>();
			
			do
			{
				PreventaProductoCambio preventaProductoCambio = new PreventaProductoCambio(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getInt(7));
				listadoPreventaProductoCambio.add(preventaProductoCambio);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCambio;
	}
	
	public ArrayList<PreventaProductoCambio> ObtenerPreventasProductoCambioNoSincronizadas() throws Exception
	{
		ArrayList<PreventaProductoCambio> listadoPreventaProductoCambio = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCambioDAL().ObtenerpreventasProductoCambioNoSincronizadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas producto cambio no sincronizadas: " + localException.getMessage());
			} 
			return listadoPreventaProductoCambio;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCambio = new ArrayList<PreventaProductoCambio>();
			
			do
			{
				PreventaProductoCambio preventaProductoCambio = new PreventaProductoCambio(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getInt(7));
				listadoPreventaProductoCambio.add(preventaProductoCambio);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCambio;
	}
	
	public long ModificarPreventaProductoCambioNoSincronizadaPor(int id, int preventaIdServer) throws Exception
	{
		try
	    {
			long i = new PreventaProductoCambioDAL().ModificarPreventaProductoCambioNoSincronizado(id, preventaIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa producto cambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventa producto cambio: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarPreventaProductoCambioPreventaIdServer(int id, int preventaIdServer) throws Exception
	{
		try
	    {
			long i = new PreventaProductoCambioDAL().ModificarPreventaProductoCambioPreventaIdServer(id, preventaIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa producto cambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa producto cambio: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
