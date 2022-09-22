package BLL;

import java.util.ArrayList;
import Clases.PreventaProductoPOP;
import Clases.PreventaProductoPOPWSResult;
import DAL.PreventaProductoPOPDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class PreventaProductoPOPBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarPreventaProductoPOPPor(long id) throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoPOPDAL().BorrarPreventaProductoPOPPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoPOP por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasPorudctoPOP por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarPreventasProductoPOP() throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoPOPDAL().BorrarPreventasProductoPOP();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasPorudctoPOP: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarPreventaProductoPOP(ArrayList<PreventaProductoPOPWSResult> preventasProductoPOP) throws Exception
	{
		try
	    {
			long l = new PreventaProductoPOPDAL().InsertarPreventaProductoPOP(preventasProductoPOP);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public long InsertarPreventaProductoPOP(int preventaPOPId,int preventaPOPIdServer,int productoPOPId,int cantidad,int clienteId,boolean syncro,
											String observacion,int motivoPopId) throws Exception
	{
		try
		{
			long l = new PreventaProductoPOPDAL().InsertarPreventaProductoPOP(preventaPOPId,preventaPOPIdServer,productoPOPId,cantidad,
					clienteId,syncro,observacion,motivoPopId);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventaProductoPOP: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public ArrayList<PreventaProductoPOP> ObtenerPreventasPorductoPOP() throws Exception
	{
		ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoPOPDAL().ObtenerpreventasProductoPOP();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventaProductoPOP: " + localException.getMessage());
			} 
			return listadoPreventaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoPOP = new ArrayList<PreventaProductoPOP>();
			
			do
			{
				PreventaProductoPOP preventaProductoPOP = new PreventaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				
				listadoPreventaProductoPOP.add(preventaProductoPOP);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoPreventaProductoPOP;
	}
	
	public ArrayList<PreventaProductoPOP> ObtenerPreventasPorductoPOPPorClienteId(int clienteId) throws Exception
	{
		ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoPOPDAL().ObtenerpreventasProductoPOPPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP por clienteId: " + localException.getMessage());
			} 
			return listadoPreventaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoPOP = new ArrayList<PreventaProductoPOP>();
			
			do
			{
				PreventaProductoPOP preventaProductoPOP = new PreventaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoPOP.add(preventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoPOP;
	}
	
	public ArrayList<PreventaProductoPOP> ObtenerPreventasPorductoPOPPorPreventaPOPId(int preventaPOPId) throws Exception
	{
		ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoPOPDAL().ObtenerpreventasProductoPOPPorPreventaPOPId(preventaPOPId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP por preventaPOPId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP por preventaPOPId: " + localException.getMessage());
			} 
			return listadoPreventaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoPOP = new ArrayList<PreventaProductoPOP>();
			
			do
			{
				PreventaProductoPOP preventaProductoPOP = new PreventaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoPOP.add(preventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoPOP;
	}
	
	public ArrayList<PreventaProductoPOP> ObtenerPreventasPorductoPOPPorPreventaPOPIdServer(int preventaPOPIdServer) throws Exception
	{
		ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoPOPDAL().ObtenerpreventasProductoPOPPorPreventaPOPIdServer(preventaPOPIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP por preventaPOPIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP por preventaPOPIdServer: " + localException.getMessage());
			} 
			return listadoPreventaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoPOP = new ArrayList<PreventaProductoPOP>();
			
			do
			{
				PreventaProductoPOP preventaProductoPOP = new PreventaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoPOP.add(preventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoPOP;
	}

	public ArrayList<PreventaProductoPOP> ObtenerPreventasPorductoPOPNoSincronizadasPor(int preventaPOPId) throws Exception
	{
		ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoPOPDAL().ObtenerpreventasProductoPOPNoSincronizadas(preventaPOPId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP no sincronizadas: " + localException.getMessage());
			} 
			return listadoPreventaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoPOP = new ArrayList<PreventaProductoPOP>();
			
			do
			{
				PreventaProductoPOP preventaProductoPOP = new PreventaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoPOP.add(preventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoPOP;
	}
	
	public ArrayList<PreventaProductoPOP> ObtenerPreventasPorductoPOPNoSincronizadas() throws Exception
	{
		ArrayList<PreventaProductoPOP> listadoPreventaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new PreventaProductoPOPDAL().ObtenerpreventasProductoPOPNoSincronizadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProductoPOP no sincronizadas: " + localException.getMessage());
			} 
			return listadoPreventaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoPOP = new ArrayList<PreventaProductoPOP>();
			
			do
			{
				PreventaProductoPOP preventaProductoPOP = new PreventaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoPreventaProductoPOP.add(preventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoPreventaProductoPOP;
	}
	
	public long ModificarPreventaProductoPOPNoSincronizadaPor(int id, int preventaPOPIdServer) throws Exception
	{
		try
	    {
			long i = new PreventaProductoPOPDAL().ModificarPreventaProductoPOPNoSincronizado(id, preventaPOPIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarPreventaProductosPOPNoSincronizadaPor(int preventaPOPId, int preventaPOPIdServer) throws Exception
	{
		try
	    {
			long i = new PreventaProductoPOPDAL().ModificarPreventaProductosPOPNoSincronizado(preventaPOPId, preventaPOPIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la preventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public long ModificarPreventaProductosPOPor(int id,String observacion) throws Exception
	{
		try
	    {
			long i = new PreventaProductoPOPDAL().ModificarPreventaProductosPOP(id, observacion);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la preventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la preventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

}
