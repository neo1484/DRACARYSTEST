package BLL;

import java.util.ArrayList;
import Clases.VentaDirectaProductoTemp;
import DAL.VentaDirectaProductoTempDAL;
import android.database.Cursor;

public class VentaDirectaProductoTempBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasDirectasProductoTemp() throws Exception
	{
		try
		{
			boolean bool = new VentaDirectaProductoTempDAL().BorrarVentasDirectasProductoTemp();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa temp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta directa temp: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVentaDirectaProductoTemp(int ventaId, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
										float monto, float descuento, float montoFinal, int costoId,int precioId,int noVentaDirecta,
										int clienteId,int tempId,int empleadoId, float descuentoCanal, float descuentoAjuste,
										int canalPrecioRutaId, float descuentoProntoPago) throws Exception
	{
		try
		{
			long l = new VentaDirectaProductoTempDAL().InsertarVentaDirectaProductoTemp(ventaId,productoId,promocionId,cantidad,cantidadPaquete,
														monto,descuento,montoFinal,costoId,precioId,noVentaDirecta,clienteId,tempId,
														empleadoId, descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa temp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta directa temp: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public VentaDirectaProductoTemp ObtenerVentaDirectaProductoTempPorRowId(int rowId) throws Exception
	{
		VentaDirectaProductoTemp localVentaDirectaProductoTemp = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new VentaDirectaProductoTempDAL().ObtenerVentaDirectaProductoTempPorRowId(rowId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta directa temp por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta directa temp por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVentaDirectaProductoTemp = new VentaDirectaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
													localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
													localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
													localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),
													localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
													localCursor.getFloat(15), localCursor.getFloat(16), localCursor.getInt(17),
													localCursor.getFloat(18));
		}

		return localVentaDirectaProductoTemp;
	}
		  
	public ArrayList<VentaDirectaProductoTemp> ObtenerVentasDirectasProductoTemp() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp = null;
		
		try
		{
			localCursor = new VentaDirectaProductoTempDAL().ObtenerVentasDirectasProductoTemp();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProductoTemp = new ArrayList<VentaDirectaProductoTemp>();
			do
	        {
				VentaDirectaProductoTemp localVentaDirectaProductoTemp = new VentaDirectaProductoTemp(localCursor.getInt(0),
						localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5),localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
						localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
						localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoVentaDirectaProductoTemp.add(localVentaDirectaProductoTemp);
	        }
			while (localCursor.moveToNext());
		}
		
		return listadoVentaDirectaProductoTemp;
	}
		  
	public ArrayList<VentaDirectaProductoTemp> ObtenerVentasDirectasProductoTempPorVentaId(int ventaId) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp = null;
		
		try
		{
		      localCursor = new VentaDirectaProductoTempDAL().ObtenerVentasDirectasProductoTempPorVentaId(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProductoTemp por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProductoTemp por ventaId: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProductoTemp = new ArrayList<VentaDirectaProductoTemp>();
			do
	        {
				VentaDirectaProductoTemp localVentaDirectaProductoTemp =new VentaDirectaProductoTemp(localCursor.getInt(0), 
						localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5),localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
						localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
						localCursor.getInt(17) ,localCursor.getFloat(18));
				
				listadoVentaDirectaProductoTemp.add(localVentaDirectaProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaDirectaProductoTemp;
	}
	
	public ArrayList<VentaDirectaProductoTemp> ObtenerVentasDirectasProductoTempPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor= null;
		ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp = null;
		
		try
		{
		      localCursor = new VentaDirectaProductoTempDAL().ObtenerVentasDirectasProductoTempPorClienteId(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProductoTemp por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventasDirectasProductoTemp por clienteId: " + localException.getMessage());
	    	} 
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProductoTemp = new ArrayList<VentaDirectaProductoTemp>();
			do
	        {
				VentaDirectaProductoTemp localVentaDirectaProductoTemp =new VentaDirectaProductoTemp(localCursor.getInt(0), 
						localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5),localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
						localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
						localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoVentaDirectaProductoTemp.add(localVentaDirectaProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaDirectaProductoTemp;
	}
	
	public long ModificarVentaDirectaProductoTempNoSincronizadaPor(int rowId,int ventaDirectaIdServer) throws Exception
	{
	    try
	    {
	    	long l = new VentaDirectaProductoTempDAL().ModificarVentaDirectaProductoTempNoSincronizadaPor(rowId,ventaDirectaIdServer);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaDirectaProductoTemp por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaDirectaProductoTemp por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public boolean BorrarVentaDirectaProductoTempPorClienteIdEmpleadoIdNoVentaDirecta(int clienteId,int empleadoId,int noVentaDirecta) throws Exception
	{
		try
		{
			boolean bool = new VentaDirectaProductoTempDAL().BorrarVentaDirectaProductoTempPorClienteIdEmpleadoIdNoVentaDirecta(clienteId, empleadoId, noVentaDirecta);
			
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la venta directa temp por clienteId, empleadoId, noVentaDirecta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la venta directa temp por clienteId, empleadoId, noVentaDirecta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public ArrayList<VentaDirectaProductoTemp> ObtenerVentasDirectasProductoTempNoSincronizadasPorClienteIdNoVentaDirecta(int clienteId,int noVentaDirecta) throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp = null;
		
		try
		{
			localCursor = new VentaDirectaProductoTempDAL().ObtenerVentasDirectasProductoTempNoSincronizadasPorClienteIdNoVentaDirecta(clienteId, noVentaDirecta);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados de la venta directa temp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos no sincronizados de la venta directa temp: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProductoTemp = new ArrayList<VentaDirectaProductoTemp>();
			do
	        {
				VentaDirectaProductoTemp localVentaDirectaProductoTemp = new VentaDirectaProductoTemp(localCursor.getInt(0),
						localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5),localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
						localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
						localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoVentaDirectaProductoTemp.add(localVentaDirectaProductoTemp);
	        }
			while (localCursor.moveToNext());
		}
		
		return listadoVentaDirectaProductoTemp;
	}
	
	public boolean BorrarVentaDirectaProductoTempPor(int rowId) throws Exception
	{
		try
		{
			boolean bool = new VentaDirectaProductoTempDAL().BorrarVentaDirectaProductoTempPor(rowId);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la ventaDirectaProductoTemp por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la ventaDirectaProductoTemp por rowId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public ArrayList<VentaDirectaProductoTemp> ObtenerVentasDirectasProductoTemporClienteIdNoVentaDirecta(int clienteId,int noVentaDirecta) throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaDirectaProductoTemp> listadoVentaDirectaProductoTemp = null;
		
		try
		{
			localCursor = new VentaDirectaProductoTempDAL().ObtenerVentasDirectasProductoTempPorClienteIdNoVentaDirecta(clienteId, noVentaDirecta);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp por clienteId y noVentaDirecta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta directa temp por clienteId y noVentaDirecta: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirectaProductoTemp = new ArrayList<VentaDirectaProductoTemp>();
			do
	        {
				VentaDirectaProductoTemp localVentaDirectaProductoTemp = new VentaDirectaProductoTemp(localCursor.getInt(0),
						localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5),localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
						localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
						localCursor.getInt(17),	localCursor.getFloat(18));
				
				listadoVentaDirectaProductoTemp.add(localVentaDirectaProductoTemp);
	        }
			while (localCursor.moveToNext());
		}
		
		return listadoVentaDirectaProductoTemp;
	}
}
