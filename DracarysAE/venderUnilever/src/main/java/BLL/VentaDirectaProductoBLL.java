package BLL;

import java.util.ArrayList;
import Clases.VentaDirectaProducto;
import DAL.VentaDirectaProductoDAL;
import android.database.Cursor;

public class VentaDirectaProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasDirectasProducto() throws Exception
	{
		try
		{
			boolean bool = new VentaDirectaProductoDAL().BorrarVentasDirectasProducto();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVentaDirectaProducto(int ventaDirectaIdServer, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
										float monto, float descuento, float montoFinal, int motivoId, 
										int costoId,int precioId,int noVentaDirecta, float descuentoCanal, float descuentoAjuste, 
										int canalPrecioRutaId) throws Exception
	{
		try
		{
			long l = new VentaDirectaProductoDAL().InsertarVentaDirectaProducto(ventaDirectaIdServer,productoId,promocionId,cantidad,cantidadPaquete,
															monto,descuento,montoFinal,motivoId,costoId,precioId,noVentaDirecta, descuentoCanal, 
															descuentoAjuste, canalPrecioRutaId);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public VentaDirectaProducto ObtenerVentaDirectaProductoPorRowId(int rowId) throws Exception
	{
		VentaDirectaProducto localVentaDirectaProducto = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaDirectaProductoDAL().ObtenerVentaDirectaProductoPorRowId(rowId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta directa por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta directa por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVentaDirectaProducto = new VentaDirectaProducto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
													localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
													localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
													localCursor.getInt(9), localCursor.getInt(10),localCursor.getInt(11),
													localCursor.getInt(12), localCursor.getFloat(13), localCursor.getFloat(14),
													localCursor.getInt(15));
		}

		return localVentaDirectaProducto;
	}
		  
	public ArrayList<VentaDirectaProducto> ObtenerVentasDirectasProducto() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaDirectaProducto> listadoVentaDirectaProducto = null;
		
		try
		{
			localCursor = new VentaDirectaProductoDAL().ObtenerVentasDirectasProducto();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProducto = new ArrayList<VentaDirectaProducto>();
			do
	        {
				VentaDirectaProducto localVentaDirectaProducto = new VentaDirectaProducto(localCursor.getInt(0), 
						localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), localCursor.getInt(4),
						localCursor.getInt(5),localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9), localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
						localCursor.getFloat(13), localCursor.getFloat(14), localCursor.getInt(15));
				listadoVentaDirectaProducto.add(localVentaDirectaProducto);
	        }
			while (localCursor.moveToNext());
		}
		
		return listadoVentaDirectaProducto;
	}
		  
	public ArrayList<VentaDirectaProducto> ObtenerVentasDirectasProductoPorVentaId(int ventaId) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaDirectaProducto> listadoVentaDirectaProducto = null;
		
		try
		{
		      localCursor = new VentaDirectaProductoDAL().ObtenerVentasDirectasProductoPorVentaId(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto por ventaId: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProducto = new ArrayList<VentaDirectaProducto>();
			do
	        {
				VentaDirectaProducto localVentaDirectaProducto =new VentaDirectaProducto(localCursor.getInt(0), 
											localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), 
											localCursor.getInt(4), localCursor.getInt(5),localCursor.getFloat(6),
											localCursor.getFloat(7), localCursor.getFloat(8),localCursor.getInt(9), 
											localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12), 
											localCursor.getFloat(13), localCursor.getFloat(14), localCursor.getInt(15));
				
				listadoVentaDirectaProducto.add(localVentaDirectaProducto);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaDirectaProducto;
	}
	
	public ArrayList<VentaDirectaProducto> ObtenerVentasDirectasProductoPorNoVentaDirecta(int noVentaDirecta) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaDirectaProducto> listadoVentaDirectaProducto = null;
		
		try
		{
		      localCursor = new VentaDirectaProductoDAL().ObtenerVentasDirectasProductoPorNoVentaDirecta(noVentaDirecta);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto por noVentaDirecta: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProducto = new ArrayList<VentaDirectaProducto>();
			do
	        {
				VentaDirectaProducto localVentaDirectaProducto =new VentaDirectaProducto(localCursor.getInt(0), 
											localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), 
											localCursor.getInt(4), localCursor.getInt(5),localCursor.getFloat(6),
											localCursor.getFloat(7), localCursor.getFloat(8),localCursor.getInt(9), 
											localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
											localCursor.getFloat(13), localCursor.getFloat(14), localCursor.getInt(15));
				
				listadoVentaDirectaProducto.add(localVentaDirectaProducto);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaDirectaProducto;
	}
	
	public ArrayList<VentaDirectaProducto> ObtenerVentasDirectasProductoNoSincronizadasPorVentaDirectaId(int ventaDirectaId) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaDirectaProducto> listadoVentaDirectaProducto = null;
		
		try
		{
		      localCursor = new VentaDirectaProductoDAL().ObtenerVentasDirectasProductoNoSincronizadasPorVentaDirectaId(ventaDirectaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto no sincronizadas por ventaDirectaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto no sincronizadas por ventaDirectaId: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProducto = new ArrayList<VentaDirectaProducto>();
			do
	        {
				VentaDirectaProducto localVentaDirectaProducto =new VentaDirectaProducto(localCursor.getInt(0), 
											localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), 
											localCursor.getInt(4), localCursor.getInt(5),localCursor.getFloat(6),
											localCursor.getFloat(7), localCursor.getFloat(8),localCursor.getInt(9), 
											localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
											localCursor.getFloat(13), localCursor.getFloat(14), localCursor.getInt(15));
				
				listadoVentaDirectaProducto.add(localVentaDirectaProducto);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaDirectaProducto;
	}
	
	public long ModificarVentaDirectaProductoIdServerPor(int noVentaDirecta,int ventaDirectaIdServer) throws Exception
	{
	    try
	    {
	    	long l = new VentaDirectaProductoDAL().ModificarVentaDirectaProductoIdServerPor(noVentaDirecta, ventaDirectaIdServer);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaDirectaProducto IdServer por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaDirectaProducto IdServer por noVentaDirecta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public ArrayList<VentaDirectaProducto> ObtenerVentasDirectasProductoNoSincronizadasPorNoVentaDirecta(int noVentaDirecta) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaDirectaProducto> listadoVentaDirectaProducto = null;
		
		try
		{
		      localCursor = new VentaDirectaProductoDAL().ObtenerVentasDirectasProductoNoSincronizadasPorNoVentaDirecta(noVentaDirecta);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto no sincronizadas por noVentaDirecta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProducto no sincronizadas por noVentaDirecta: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProducto = new ArrayList<VentaDirectaProducto>();
			do
	        {
				VentaDirectaProducto localVentaDirectaProducto =new VentaDirectaProducto(localCursor.getInt(0), 
											localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), 
											localCursor.getInt(4), localCursor.getInt(5),localCursor.getFloat(6),
											localCursor.getFloat(7), localCursor.getFloat(8),localCursor.getInt(9), 
											localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
											localCursor.getFloat(13), localCursor.getFloat(14), localCursor.getInt(15));
				
				listadoVentaDirectaProducto.add(localVentaDirectaProducto);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaDirectaProducto;
	}
}
