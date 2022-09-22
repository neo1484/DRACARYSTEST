package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.VentaProducto;
import DAL.VentaProductoDAL;

public class VentaProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasProducto() throws Exception
	{
		try
		{
			boolean bool = new VentaProductoDAL().BorrarVentasProducto();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVentaProducto(int ventaId, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
										float monto, float descuento, float montoFinal, int motivoId, 
										boolean estadoSincronizacion,float costo,int costoId, int precioId, 
										float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId,
										float descuentoProntoPago) throws Exception
	{
		try
		{
			long l = new VentaProductoDAL().InsertarVentaProducto(ventaId,productoId,promocionId,cantidad,cantidadPaquete,
															monto,descuento,montoFinal,motivoId,estadoSincronizacion,costo,
															costoId,precioId, descuentoCanal, descuentoAjuste, canalPrecioRutaId,
															descuentoProntoPago);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public int ModificarVentaProducto(int rowId,boolean estadoSincronizacion) throws Exception
	{
		try
		{
			int i = new VentaProductoDAL().ModificarVentaProducto(rowId,estadoSincronizacion);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta producto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta producto: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public VentaProducto ObtenerVentaProductoPor(int id) throws Exception
	{
		VentaProducto localVentaProducto = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaProductoDAL().ObtenerVentaProductoPor(id);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVentaProducto = new VentaProducto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
													localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
													localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
													localCursor.getInt(9), localCursor.getString(10).equals("1")?true:false,
													localCursor.getFloat(11),localCursor.getInt(12),localCursor.getInt(13),
													localCursor.getFloat(14), localCursor.getFloat(15), localCursor.getInt(16),
													localCursor.getFloat(17));
		}

		return localVentaProducto;
	}
		  
	public ArrayList<VentaProducto> ObtenerVentasProducto() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaProducto> listadoVentaProducto = null;
		
		try
		{
			localCursor = new VentaProductoDAL().ObtenerVentasProducto();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaProducto = new ArrayList<VentaProducto>();
			do
	        {
				VentaProducto localVentaProducto = new VentaProducto(localCursor.getInt(0), localCursor.getInt(1), 
												localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
												localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
												localCursor.getFloat(8), localCursor.getInt(9), 
												localCursor.getString(10).equals("1")?true:false,localCursor.getFloat(11),
												localCursor.getInt(12),localCursor.getInt(13),
												localCursor.getFloat(14), localCursor.getFloat(15), localCursor.getInt(16),
												localCursor.getFloat(17));
				listadoVentaProducto.add(localVentaProducto);
	        }
			while (localCursor.moveToNext());
		}
		
		return listadoVentaProducto;
	}
		  
	public ArrayList<VentaProducto> ObtenerVentasProductoPor(int ventaId) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaProducto> listadoVentaProducto = null;
		
		try
		{
		      localCursor = new VentaProductoDAL().ObtenerVentasProductoPor(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasProducto por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasProducto por ventaId: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaProducto = new ArrayList<VentaProducto>();
			do
	        {
				VentaProducto localVentaProducto =new VentaProducto(localCursor.getInt(0), localCursor.getInt(1), 
	        		  							localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  							localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
	        		  							localCursor.getFloat(8), localCursor.getInt(9), 
	        		  							localCursor.getString(10).equals("1")?true:false,localCursor.getFloat(11),
	        		  							localCursor.getInt(12),localCursor.getInt(13),
												localCursor.getFloat(14), localCursor.getFloat(15), localCursor.getInt(16),
												localCursor.getFloat(17));
				listadoVentaProducto.add(localVentaProducto);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaProducto;
	}
	
	public ArrayList<VentaProducto> ObtenerVentasProductoNoSincronizados(int ventaId) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaProducto> listadoVentaProducto = null;
		
		try
		{
		      localCursor = new VentaProductoDAL().ObtenerVentasProductoNoSincronizados(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasProducto no sincronizados por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasProducto no sincronizados por ventaId: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaProducto = new ArrayList<VentaProducto>();
			do
	        {
				VentaProducto localVentaProducto =new VentaProducto(localCursor.getInt(0), localCursor.getInt(1), 
	        		  							localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  							localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
	        		  							localCursor.getFloat(8), localCursor.getInt(9), 
	        		  							localCursor.getString(10).equals("1")?true:false,localCursor.getFloat(11),
	        		  							localCursor.getInt(12),localCursor.getInt(13),
												localCursor.getFloat(14), localCursor.getFloat(15), localCursor.getInt(16),
												localCursor.getFloat(17));
				listadoVentaProducto.add(localVentaProducto);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaProducto;
	}
}
