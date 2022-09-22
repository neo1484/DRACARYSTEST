package BLL;

import java.util.ArrayList;

import Clases.VentaProductoCHANGE;
import DAL.VentaProductoCHANGEDAL;
import android.database.Cursor;

public class VentaProductoCHANGEBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarVentaProductoCHANGEPor(long id) throws Exception
	{
		try
		{
			boolean bool = new VentaProductoCHANGEDAL().BorrarVentaProductoCHANGEPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoCHANGE por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasPorudctoCHANGE por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarVentasProductoCHANGE() throws Exception
	{
		try
		{
			boolean bool = new VentaProductoCHANGEDAL().BorrarVentasProductoCHANGE();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasPorudctoCHANGE: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarVentaProductoCHANGE(int ventaCHANGEId,int ventaCHANGEIdServer,int productoCHANGEId,int cantidad,int clienteId,boolean syncro,
											String observacion,int motivoCHANGEId) throws Exception
	{
		try
	    {
			long l = new VentaProductoCHANGEDAL().InsertarVentaProductoCHANGE(ventaCHANGEId,ventaCHANGEIdServer,productoCHANGEId,cantidad,
													clienteId,syncro,observacion,motivoCHANGEId);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public ArrayList<VentaProductoCHANGE> ObtenerVentasPorductoCHANGE() throws Exception
	{
		ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoCHANGEDAL().ObtenerVentasProductoCHANGE();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventaProductoCHANGE: " + localException.getMessage());
			} 
			return listadoVentaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoCHANGE = new ArrayList<VentaProductoCHANGE>();
			
			do
			{
				VentaProductoCHANGE ventaProductoCHANGE = new VentaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																		localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																		localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																		localCursor.getInt(8));
				
				listadoVentaProductoCHANGE.add(ventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoVentaProductoCHANGE;
	}
	
	public ArrayList<VentaProductoCHANGE> ObtenerVentasPorductoCHANGEPorClienteId(int clienteId) throws Exception
	{
		ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoCHANGEDAL().ObtenerVentasProductoCHANGEPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE por clienteId: " + localException.getMessage());
			} 
			return listadoVentaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoCHANGE = new ArrayList<VentaProductoCHANGE>();
			
			do
			{
				VentaProductoCHANGE ventaProductoCHANGE = new VentaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoCHANGE.add(ventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoCHANGE;
	}
	
	public ArrayList<VentaProductoCHANGE> ObtenerVentasPorductoCHANGEPorVentaCHANGEId(int ventaCHANGEId) throws Exception
	{
		ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoCHANGEDAL().ObtenerVentasProductoCHANGEPorVentaCHANGEId(ventaCHANGEId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE por ventaCHANGEId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE por ventaCHANGEId: " + localException.getMessage());
			} 
			return listadoVentaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoCHANGE = new ArrayList<VentaProductoCHANGE>();
			
			do
			{
				VentaProductoCHANGE ventaProductoCHANGE = new VentaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoCHANGE.add(ventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoCHANGE;
	}
	
	public ArrayList<VentaProductoCHANGE> ObtenerVentasProductoCHANGEPorVentaCHANGEIdServer(int ventaCHANGEIdServer) throws Exception
	{
		ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoCHANGEDAL().ObtenerVentasProductoCHANGEPorVentaCHANGEIdServer(ventaCHANGEIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE por ventaCHANGEIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE por ventaCHANGEIdServer: " + localException.getMessage());
			} 
			return listadoVentaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoCHANGE = new ArrayList<VentaProductoCHANGE>();
			
			do
			{
				VentaProductoCHANGE ventaProductoCHANGE = new VentaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoCHANGE.add(ventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoCHANGE;
	}

	public ArrayList<VentaProductoCHANGE> ObtenerVentasPorductoCHANGENoSincronizadasPor(int ventaCHANGEId) throws Exception
	{
		ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoCHANGEDAL().ObtenerVentasProductoCHANGENoSincronizadas(ventaCHANGEId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE no sincronizadas: " + localException.getMessage());
			} 
			return listadoVentaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoCHANGE = new ArrayList<VentaProductoCHANGE>();
			
			do
			{
				VentaProductoCHANGE ventaProductoCHANGE = new VentaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoCHANGE.add(ventaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoCHANGE;
	}
	
	public ArrayList<VentaProductoCHANGE> ObtenerVentasPorductoCHANGENoSincronizadas() throws Exception
	{
		ArrayList<VentaProductoCHANGE> listadoVentaProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoCHANGEDAL().ObtenerVentasProductoCHANGENoSincronizadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoCHANGE no sincronizadas: " + localException.getMessage());
			} 
			return listadoVentaProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoCHANGE = new ArrayList<VentaProductoCHANGE>();
			
			do
			{
				VentaProductoCHANGE VentaProductoCHANGE = new VentaProductoCHANGE(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoCHANGE.add(VentaProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoCHANGE;
	}
	
	public long ModificarVentaProductoCHANGENoSincronizadaPor(int id, int ventaCHANGEIdServer) throws Exception
	{
		try
	    {
			long i = new VentaProductoCHANGEDAL().ModificarVentaProductoCHANGENoSincronizado(id,ventaCHANGEIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarVentaProductosCHANGENoSincronizadaPor(int ventaCHANGEId, int ventaCHANGEIdServer) throws Exception
	{
		try
	    {
			long i = new VentaProductoCHANGEDAL().ModificarVentaProductosCHANGENoSincronizado(ventaCHANGEId,ventaCHANGEIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public long ModificarVentaProductosCHANGEor(int id,String observacion) throws Exception
	{
		try
	    {
			long i = new VentaProductoCHANGEDAL().ModificarVentaProductosCHANGE(id, observacion);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la ventaProductoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la ventaProductoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
