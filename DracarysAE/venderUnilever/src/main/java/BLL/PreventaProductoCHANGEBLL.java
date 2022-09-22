package BLL;

import java.util.ArrayList;

import Clases.PreventaProductoCHANGE;
import DAL.PreventaProductoCHANGEDAL;
import android.database.Cursor;

public class PreventaProductoCHANGEBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarPreventaProductoCHANGEPor(long id) throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoCHANGEDAL().BorrarPreventaProductoCHANGEPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoCHANGE por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasPorudctoCHANGE por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarPreventasProductoCHANGE() throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoCHANGEDAL().BorrarPreventasProductoCHANGE();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasPorudctoCHANGE: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarPreventaProductoCHANGE(int preventaCHANGEId,int preventaCHANGEIdServer,int productoCHANGEId,int cantidad,int clienteId,boolean syncro,
											String observacion,int motivoCHANGEId) throws Exception
	{
		try
	    {
			long l = new PreventaProductoCHANGEDAL().InsertarPreventaProductoCHANGE(preventaCHANGEId,preventaCHANGEIdServer,productoCHANGEId,cantidad,
													clienteId,syncro,observacion,motivoCHANGEId);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<PreventaProductoCHANGE> ObtenerPreventasPorductoCHANGE() throws Exception
	{
		ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCHANGEDAL().ObtenerpreventasProductoCHANGE();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoCHANGE: " + localException.getMessage());
			} 
			return listadoPreventaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCHANGE = new ArrayList<PreventaProductoCHANGE>();
			
			do
			{
				PreventaProductoCHANGE preventaProductoCHANGE = new PreventaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				
				listadoPreventaProductoCHANGE.add(preventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoPreventaProductoCHANGE;
	}
	
	public ArrayList<PreventaProductoCHANGE> ObtenerPreventasPorductoCHANGEPorClienteId(int clienteId) throws Exception
	{
		ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCHANGEDAL().ObtenerpreventasProductoCHANGEPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE por clienteId: " + localException.getMessage());
			} 
			return listadoPreventaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCHANGE = new ArrayList<PreventaProductoCHANGE>();
			
			do
			{
				PreventaProductoCHANGE preventaProductoCHANGE = new PreventaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoCHANGE.add(preventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCHANGE;
	}
	
	public ArrayList<PreventaProductoCHANGE> ObtenerPreventasProductoCHANGEPorPreventaCHANGEId(int preventaCHANGEId) throws Exception
	{
		ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCHANGEDAL().ObtenerpreventasProductoCHANGEPorPreventaCHANGEId(preventaCHANGEId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE por preventaCHANGEId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE por preventaCHANGEId: " + localException.getMessage());
			} 
			return listadoPreventaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCHANGE = new ArrayList<PreventaProductoCHANGE>();
			
			do
			{
				PreventaProductoCHANGE preventaProductoCHANGE = new PreventaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoCHANGE.add(preventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCHANGE;
	}
	
	public ArrayList<PreventaProductoCHANGE> ObtenerPreventasPorductoCHANGEPorPreventaCHANGEIdServer(int preventaCHANGEIdServer) throws Exception
	{
		ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCHANGEDAL().ObtenerpreventasProductoCHANGEPorPreventaCHANGEIdServer(preventaCHANGEIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE por preventaCHANGEIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE por preventaCHANGEIdServer: " + localException.getMessage());
			} 
			return listadoPreventaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCHANGE = new ArrayList<PreventaProductoCHANGE>();
			
			do
			{
				PreventaProductoCHANGE preventaProductoCHANGE = new PreventaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoCHANGE.add(preventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCHANGE;
	}

	public ArrayList<PreventaProductoCHANGE> ObtenerPreventasPorductoCHANGENoSincronizadasPor(int preventaCHANGEId) throws Exception
	{
		ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCHANGEDAL().ObtenerpreventasProductoCHANGENoSincronizadas(preventaCHANGEId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE no sincronizadas: " + localException.getMessage());
			} 
			return listadoPreventaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCHANGE = new ArrayList<PreventaProductoCHANGE>();
			
			do
			{
				PreventaProductoCHANGE preventaProductoCHANGE = new PreventaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoCHANGE.add(preventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCHANGE;
	}
	
	public ArrayList<PreventaProductoCHANGE> ObtenerPreventasPorductoCHANGENoSincronizadas() throws Exception
	{
		ArrayList<PreventaProductoCHANGE> listadoPreventaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoCHANGEDAL().ObtenerpreventasProductoCHANGENoSincronizadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoCHANGE no sincronizadas: " + localException.getMessage());
			} 
			return listadoPreventaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoCHANGE = new ArrayList<PreventaProductoCHANGE>();
			
			do
			{
				PreventaProductoCHANGE preventaProductoCHANGE = new PreventaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoCHANGE.add(preventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoCHANGE;
	}
	
	public long ModificarPreventaProductoCHANGENoSincronizadaPor(int id, int preventaCHANGEIdServer) throws Exception
	{
		try
	    {
			long i = new PreventaProductoCHANGEDAL().ModificarPreventaProductoCHANGENoSincronizado(id, preventaCHANGEIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarPreventaProductosCHANGENoSincronizadaPor(int preventaCHANGEId, int preventaCHANGEIdServer) throws Exception
	{
		try
	    {
			long i = new PreventaProductoCHANGEDAL().ModificarPreventaProductosCHANGENoSincronizado(preventaCHANGEId, preventaCHANGEIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public long ModificarPreventaProductosCHANGEPor(int id,String observacion) throws Exception
	{
		try
	    {
			long i = new PreventaProductoCHANGEDAL().ModificarPreventaProductosCHANGE(id, observacion);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la preventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la preventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}