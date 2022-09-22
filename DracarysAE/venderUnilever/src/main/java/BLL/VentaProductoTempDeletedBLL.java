package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.VentaProductoTempDeleted;
import DAL.VentaProductoTempDeletedDAL;

public class VentaProductoTempDeletedBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentaProductoTempDeletedPor(int id) throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempDeletedDAL().BorrarVentasProductoTempDeletedPor(id);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por id: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public boolean BorrarVentaProductoTempDeletedPorRowId(int rowId) throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempDeletedDAL().BorrarVentasProductoTempDeletedPorRowId(rowId);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la VentaProductoTempDeleted por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public boolean BorrarVentasProductoTempDeleted() throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempDeletedDAL().BorrarVentasProductoTempDeleted();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal deleted: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal deleted: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVentaProductoTempDeleted(int rowId,int tempId, int empleadoId, int clienteId, int motivoId,
											boolean estadoSincronizacion) throws Exception
	{
		try
		{
			long l = new VentaProductoTempDeletedDAL().InsertarVentaProductoTempDeleted(rowId,tempId,empleadoId,clienteId,
																					motivoId,estadoSincronizacion);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temporal deleted: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temporal deleted: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public VentaProductoTempDeleted ObtenerVentaProductoTempDeletedPor(int id) throws Exception
	{
		Cursor localCursor =null;
		VentaProductoTempDeleted localVentaProductoTempDeleted = null;
		try
		{
			localCursor = new VentaProductoTempDeletedDAL().ObtenerVentaProductoTempDeletedPor(id);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temporal deleted por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temporal deleted por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
		if(localCursor.getCount()<0)
		{
			localVentaProductoTempDeleted = new VentaProductoTempDeleted(localCursor.getInt(0),localCursor.getInt(1),
																		localCursor.getInt(2),localCursor.getInt(3),
																		localCursor.getInt(4),localCursor.getInt(5), 
																		localCursor.getString(6).equals("1")?true:false);
		}
		
		return localVentaProductoTempDeleted;
	}
		
	public VentaProductoTempDeleted ObtenerVentaProductoTempDeletedPorRowId(int rowId) throws Exception
	{
		Cursor localCursor = null;
		VentaProductoTempDeleted localVentaProductoTempDeleted = null;
		
		try
		{
			localCursor = new VentaProductoTempDeletedDAL().ObtenerVentaProductoTempDeletedPorRowId(rowId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la ventaTemporalDeleted por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la ventaTemporalDeleted por rowId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			localVentaProductoTempDeleted = new VentaProductoTempDeleted(localCursor.getInt(0),localCursor.getInt(1),
																		localCursor.getInt(2),localCursor.getInt(3),
																		localCursor.getInt(4),localCursor.getInt(5), 
																		localCursor.getString(6).equals("1")?true:false);
		}
		
		return localVentaProductoTempDeleted;
	}
	
	public VentaProductoTempDeleted ObtenerVentaProductoTempDeletedPorTempId(int tempId) throws Exception
	{
		Cursor localCursor = null;
		VentaProductoTempDeleted localVentaProductoTempDeleted = null;
		
		try
		{
			localCursor = new VentaProductoTempDeletedDAL().ObtenerVentaProductoTempDeletedPorTempId(tempId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la ventaTemporalDeleted por tempId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la ventaTemporalDeleted por tempId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			localVentaProductoTempDeleted = new VentaProductoTempDeleted(localCursor.getInt(0),localCursor.getInt(1),
																		localCursor.getInt(2),localCursor.getInt(3),
																		localCursor.getInt(4),localCursor.getInt(5), 
																		localCursor.getString(6).equals("1")?true:false);
		}
		
		return localVentaProductoTempDeleted;
	}
		  
	public ArrayList<VentaProductoTempDeleted> ObtenerVentasDetalleTempDeleted() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaProductoTempDeleted> listadoVentaProductoTempDeleted = null;
		try
		{
			localCursor = new VentaProductoTempDeletedDAL().ObtenerVentasProductoTempDeleted();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temporal deleted: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temporal deleted: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaProductoTempDeleted = new ArrayList<VentaProductoTempDeleted>();
			
			do
	        {
				VentaProductoTempDeleted localVentaProductoTempDeleted = new VentaProductoTempDeleted(
																		localCursor.getInt(0),localCursor.getInt(1),
																		localCursor.getInt(2),localCursor.getInt(3),
																		localCursor.getInt(4),localCursor.getInt(5), 
																		localCursor.getString(6).equals("1")?true:false);
				
				listadoVentaProductoTempDeleted.add(localVentaProductoTempDeleted);
	        } 
			while(localCursor.moveToNext());
		}
		
		return listadoVentaProductoTempDeleted;
	  }
}
