package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.VentaProductoTempNoEntrega;
import DAL.VentaProductoTempNoEntregaDAL;

public class VentaProductoTempNoEntregaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasProductoTempNoEntrega() throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempNoEntregaDAL().BorrarVentasProductoTempNoEntrega();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas productos temporales no entregadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas productos temporales no entregadas: " + localException.getMessage());
			}
		    throw localException;
	    }
	}
		  
	public long InsertarVentaProductoTempNoEntrega(int ventaId, int productoId, int promocionId, int cantidad, 
													int cantidadPaquete, float monto, float descuento, float montoFinal, 
													int motivoId, boolean estadoSincronizacion) throws Exception
	{
		try
		{
			long l = new VentaProductoTempNoEntregaDAL().InsertarVentaProductoTempNoEntrega(ventaId,productoId,promocionId,
											cantidad,cantidadPaquete,monto,descuento,montoFinal,motivoId,estadoSincronizacion);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ventas productos temporales no entregadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ventas productos temporales no entregadas: " + localException.getMessage());
			}
			throw localException;
	    }
	}
		  
	public VentaProductoTempNoEntrega ObtenerVentaProductoTempNoEntregaPor(int paramInt) throws Exception
	{
		Cursor localCursor = null;
		VentaProductoTempNoEntrega localVentaProductoTempNoEntrega = null;
		try
		{
			localCursor = new VentaProductoTempNoEntregaDAL().ObtenerVentaProductoTempNoEntregaPor(paramInt);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas prodcutos temporales no entregadas por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas prodcutos temporales no entregadas por rowId: " + localException.getMessage());
			}
			throw localException;
	    }
		
		if(localCursor.getCount()>0);
		{
			localVentaProductoTempNoEntrega = new VentaProductoTempNoEntrega(localCursor.getInt(0), localCursor.getInt(1), 
														localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
														localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7),
														localCursor.getFloat(8), localCursor.getInt(9),
														localCursor.getString(10).equals("1")?true:false);
	    }
	    return localVentaProductoTempNoEntrega;
	}
	
		  
	public ArrayList<VentaProductoTempNoEntrega> ObtenerVentasProductoTempNoEntrega() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<VentaProductoTempNoEntrega> listadoVentaProductoTempNoEntrega = null;
		
		try
		{
			localCursor = new VentaProductoTempNoEntregaDAL().ObtenerVentasProductoTempNoEntrega();
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas productos temporales no entregadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas productos temporales no entregadas: " + localException.getMessage());
	    	} 
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoTempNoEntrega = new ArrayList<VentaProductoTempNoEntrega>();
			
			do
	        {
				VentaProductoTempNoEntrega localVentaProductoTempNoEntrega = new VentaProductoTempNoEntrega(localCursor.getInt(0), 
															localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
															localCursor.getInt(4), localCursor.getInt(5), localCursor.getFloat(6), 
															localCursor.getFloat(7), localCursor.getFloat(8), localCursor.getInt(9),
															localCursor.getString(10).equals("1")?true:false);
				
				listadoVentaProductoTempNoEntrega.add(localVentaProductoTempNoEntrega);
	        } 
			while(localCursor.moveToNext());
		}
	    
		return listadoVentaProductoTempNoEntrega;
	}
}
