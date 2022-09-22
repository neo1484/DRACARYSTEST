package BLL;

import java.util.ArrayList;
import Clases.VentaProductoPOP;
import DAL.VentaProductoPOPDAL;
import android.database.Cursor;

public class VentaProductoPOPBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarVentaProductoPOPPor(long id) throws Exception
	{
		try
		{
			boolean bool = new VentaProductoPOPDAL().BorrarVentaProductoPOPPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoPOP por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasPorudctoPOP por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarVentasProductoPOP() throws Exception
	{
		try
		{
			boolean bool = new VentaProductoPOPDAL().BorrarVentasProductoPOP();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasPorudctoPOP: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarVentaProductoPOP(int ventaPOPId,int ventaPOPIdServer,int productoPOPId,int cantidad,int clienteId,boolean syncro,
											String observacion,int motivoPopId) throws Exception
	{
		try
	    {
			long l = new VentaProductoPOPDAL().InsertarVentaProductoPOP(ventaPOPId,ventaPOPIdServer,productoPOPId,cantidad,
													clienteId,syncro,observacion,motivoPopId);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public ArrayList<VentaProductoPOP> ObtenerVentasPorductoPOP() throws Exception
	{
		ArrayList<VentaProductoPOP> listadoVentaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoPOPDAL().ObtenerVentasProductoPOP();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventaProductoPOP: " + localException.getMessage());
			} 
			return listadoVentaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoPOP = new ArrayList<VentaProductoPOP>();
			
			do
			{
				VentaProductoPOP ventaProductoPOP = new VentaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																		localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																		localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																		localCursor.getInt(8));
				
				listadoVentaProductoPOP.add(ventaProductoPOP);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoVentaProductoPOP;
	}
	
	public ArrayList<VentaProductoPOP> ObtenerVentasPorductoPOPPorClienteId(int clienteId) throws Exception
	{
		ArrayList<VentaProductoPOP> listadoVentaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoPOPDAL().ObtenerVentasProductoPOPPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP por clienteId: " + localException.getMessage());
			} 
			return listadoVentaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoPOP = new ArrayList<VentaProductoPOP>();
			
			do
			{
				VentaProductoPOP ventaProductoPOP = new VentaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoPOP.add(ventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoPOP;
	}
	
	public ArrayList<VentaProductoPOP> ObtenerVentasPorductoPOPPorVentaPOPId(int ventaPOPId) throws Exception
	{
		ArrayList<VentaProductoPOP> listadoVentaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoPOPDAL().ObtenerVentasProductoPOPPorVentaPOPId(ventaPOPId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP por ventaPOPId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP por ventaPOPId: " + localException.getMessage());
			} 
			return listadoVentaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoPOP = new ArrayList<VentaProductoPOP>();
			
			do
			{
				VentaProductoPOP ventaProductoPOP = new VentaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoPOP.add(ventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoPOP;
	}
	
	public ArrayList<VentaProductoPOP> ObtenerVentasProductoPOPPorVentaPOPIdServer(int ventaPOPIdServer) throws Exception
	{
		ArrayList<VentaProductoPOP> listadoVentaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoPOPDAL().ObtenerVentasProductoPOPPorVentaPOPIdServer(ventaPOPIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP por ventaPOPIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP por ventaPOPIdServer: " + localException.getMessage());
			} 
			return listadoVentaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoPOP = new ArrayList<VentaProductoPOP>();
			
			do
			{
				VentaProductoPOP ventaProductoPOP = new VentaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoPOP.add(ventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoPOP;
	}

	public ArrayList<VentaProductoPOP> ObtenerVentasPorductoPOPNoSincronizadasPor(int ventaPOPId) throws Exception
	{
		ArrayList<VentaProductoPOP> listadoVentaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoPOPDAL().ObtenerVentasProductoPOPNoSincronizadas(ventaPOPId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP no sincronizadas: " + localException.getMessage());
			} 
			return listadoVentaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoPOP = new ArrayList<VentaProductoPOP>();
			
			do
			{
				VentaProductoPOP ventaProductoPOP = new VentaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoPOP.add(ventaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoPOP;
	}
	
	public ArrayList<VentaProductoPOP> ObtenerVentasPorductoPOPNoSincronizadas() throws Exception
	{
		ArrayList<VentaProductoPOP> listadoVentaProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoPOPDAL().ObtenerVentasProductoPOPNoSincronizadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoPOP no sincronizadas: " + localException.getMessage());
			} 
			return listadoVentaProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoPOP = new ArrayList<VentaProductoPOP>();
			
			do
			{
				VentaProductoPOP VentaProductoPOP = new VentaProductoPOP(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
																				localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
																				localCursor.getString(6).equals("1")?true:false,localCursor.getString(7),
																				localCursor.getInt(8));
				listadoVentaProductoPOP.add(VentaProductoPOP);
			}
			while(localCursor.moveToNext());
		}
		return listadoVentaProductoPOP;
	}
	
	public long ModificarVentaProductoPOPNoSincronizadaPor(int id, int ventaPOPIdServer) throws Exception
	{
		try
	    {
			long i = new VentaProductoPOPDAL().ModificarVentaProductoPOPNoSincronizado(id,ventaPOPIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public long ModificarVentaProductosPOPNoSincronizadaPor(int ventaPOPId, int ventaPOPIdServer) throws Exception
	{
		try
	    {
			long i = new VentaProductoPOPDAL().ModificarVentaProductosPOPNoSincronizado(ventaPOPId,ventaPOPIdServer);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public long ModificarVentaProductosPOPor(int id,String observacion) throws Exception
	{
		try
	    {
			long i = new VentaProductoPOPDAL().ModificarVentaProductosPOP(id, observacion);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la ventaProductoPOP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la observacion de la ventaProductoPOP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
