package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.PreventaProductoDist;
import Clases.PreventaProductoDistWSResult;
import DAL.PreventaProductoDistDAL;

public class PreventaProductoDistBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreventasProductoDist() throws Exception
	{
		try
		{
			boolean bool = new PreventaProductoDistDAL().BorrarPreventasProductoDist();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar preventas detalle del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar preventas detalle del distribuidor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarPreventaProductoDist(ArrayList<PreventaProductoDistWSResult> preventasProductoDist)throws Exception
	{
		try
		{
			long l = new PreventaProductoDistDAL().InsertarPreventaProductoDist(preventasProductoDist);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de las preventas del distribuidor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public PreventaProductoDist ObtenerPreventaProductoDistPor(int preventaProductoId) throws Exception
	{
		Cursor localCursor;
		PreventaProductoDist localPreventaProductoDist = null;
		try
		{
			localCursor = new PreventaProductoDistDAL().ObtenerPreventaProductoDistPor(preventaProductoId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProducto del distribuidor por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventasProducto del distribuidor por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if (localCursor.getCount() > 0)
		{
			localPreventaProductoDist = new PreventaProductoDist(localCursor.getInt(0), localCursor.getInt(1), 
										localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
										localCursor.getInt(5), localCursor.getInt(6), localCursor.getFloat(7), 
										localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getInt(10), 
										localCursor.getString(11).equals("1")?true:false,localCursor.getFloat(12),
										localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15),
										localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
		}
		
		return localPreventaProductoDist;
	}
		  
	public ArrayList<PreventaProductoDist> ObtenerPreventasProductoDist() throws Exception
	{
		Cursor localCursor;
		ArrayList<PreventaProductoDist> listadoPreventaProductoDist = null;
		
		try
		{
			localCursor = new PreventaProductoDistDAL().ObtenerPreventasProductoDist();
    	}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoPreventaProductoDist = new ArrayList<PreventaProductoDist>();
			
			do
	        {
				PreventaProductoDist preventaProductoDist = new PreventaProductoDist(localCursor.getInt(0), localCursor.getInt(1), 
												localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
												localCursor.getInt(5), localCursor.getInt(6), localCursor.getFloat(7), 
												localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getInt(10), 
												localCursor.getString(11).equals("1")?true:false,localCursor.getFloat(12),
												localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15),
												localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoPreventaProductoDist.add(preventaProductoDist);
	        } 
			while (localCursor.moveToNext());
		}
		
		return listadoPreventaProductoDist;
	}
		  
	public ArrayList<PreventaProductoDist> ObtenerPreventasProductoDistPor(int preventaId) throws Exception
	{
		Cursor localCursor;
		ArrayList<PreventaProductoDist> listadoPreventaProductoDist = null;
		
		try
		{
			localCursor = new PreventaProductoDistDAL().ObtenerPreventasProductoDistPor(preventaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor por preventaId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoPreventaProductoDist = new ArrayList<PreventaProductoDist>();
			do
	        {
				PreventaProductoDist preventaProductoDist = new PreventaProductoDist(localCursor.getInt(0), 
										localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
										localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6), 
										localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getFloat(9), 
										localCursor.getInt(10), localCursor.getString(11).equals("1")?true:false,
										localCursor.getFloat(12),localCursor.getInt(13),localCursor.getInt(14), 
										localCursor.getFloat(15), localCursor.getFloat(16), localCursor.getInt(17), 
										localCursor.getFloat(18));
				
				listadoPreventaProductoDist.add(preventaProductoDist);
	        } 
			while(localCursor.moveToNext());			
		
		}
		return listadoPreventaProductoDist;
	}
		  
	public int UpdatePreventaProductoDist(int preventaProductoId, int preventaId, int productoId, int cantidad, 
											int cantidadPaquete, float monto, float descuento, float montoFinal,
											boolean estadoSincronizacion,float costo,int costoId,int precioId)throws Exception
	{
		try
		{
			int i = new PreventaProductoDistDAL().UpdatePreventaProductoDist(preventaProductoId,preventaId,productoId,
													cantidad,cantidadPaquete,monto,descuento,montoFinal,
													estadoSincronizacion,costo,costoId,precioId);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor por preventaId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public PreventaProductoDist ObtenerPreventasProductoDistPor(int preventaId,int productoId) throws Exception
	{
		Cursor localCursor;
		PreventaProductoDist preventaProductoDist = null;
		try
		{
			localCursor = new PreventaProductoDistDAL().ObtenerPreventasProductoDistPor(preventaId,productoId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventasProducto del distribuidor por preventaId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			preventaProductoDist = new PreventaProductoDist(localCursor.getInt(0), 
										localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
										localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6), 
										localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getFloat(9), 
										localCursor.getInt(10), localCursor.getString(11).equals("1")?true:false,
										localCursor.getFloat(12),localCursor.getInt(13),localCursor.getInt(14), 
										localCursor.getFloat(15), localCursor.getFloat(16), localCursor.getInt(17), 
										localCursor.getFloat(18));			
		
		}
		return preventaProductoDist;
	}

}
