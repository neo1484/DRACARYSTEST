package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.VentaProductoTemp;
import DAL.VentaProductoTempDAL;

public class VentaProductoTempBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasProductoTemp() throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempDAL().BorrarVentasProductoTemp();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public boolean BorrarVentasProductoTempPorEmpleadoIdYClienteId(int empleadoId, int clienteId) throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempDAL().BorrarVentasProductoTempPorTempId(empleadoId,clienteId);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal por empleadoId y clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal por empleadoId y clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public boolean BorrarVentasProductoTempPorTempId(int tempId) throws Exception
	{
		try
		{
			boolean bool = new VentaProductoTempDAL().BorrarVentasProductoTempPorTempId(tempId);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal por tempId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos de la venta temporal por tempId: " + localException.getMessage());
			}
			throw localException;
	    }
	}
		  
	public long InsertarVentaProductoTemp(int tempId, int productoId, int promocionId, int cantidad, int cantidadNueva, 
											int cantidadPaquete, int cantidadPaqueteNueva, float monto, float montoNuevo,
											float descuento, float descuentoNuevo, float montoFinal, float montoFinalNuevo, 
											int empleadoId, int clienteId, int motivoId,float costo,int costoId,int precioId,
											float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId, float descuentoProntoPago) throws Exception
  		{
		try
		{
			long l = new VentaProductoTempDAL().InsertarVentaProductoTemp(tempId,productoId,promocionId,cantidad,cantidadNueva,
					cantidadPaquete,cantidadPaqueteNueva,monto,montoNuevo,descuento,descuentoNuevo,montoFinal,montoFinalNuevo,
					empleadoId,clienteId,motivoId,costo,costoId,precioId, descuentoCanal, descuentoAjuste, canalPrecioRutaId,
					descuentoProntoPago);
			return l;
	    }
    	catch (Exception localException)
		{
    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temporal: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la venta temporal: " + localException.getMessage());
	    	} 
    		throw localException;
	    }
  	}
		  
	public ArrayList<VentaProductoTemp> ObtenerListadoVentaProductoTempPor(int clienteId) throws Exception
	{
		ArrayList<VentaProductoTemp> listadoVentaProductoTemp = null;
		Cursor localCursor = null;
		    
		try
		{
			localCursor = new VentaProductoTempDAL().ObtenerListadoVentaProductoTempPor(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta temporal por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta temporal por clienteId: " + localException.getMessage());
			} 
		}

		if(localCursor.getCount() > 0)
		{
			listadoVentaProductoTemp = new ArrayList<VentaProductoTemp>();
			
			do
	        {
	          VentaProductoTemp localVentaProductoTemp = new VentaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
	        		  									localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  									localCursor.getInt(5), localCursor.getInt(6), localCursor.getInt(7), 
	        		  									localCursor.getFloat(8), localCursor.getFloat(9), 
	        		  									localCursor.getFloat(10), localCursor.getFloat(11), 
	        		  									localCursor.getFloat(12), localCursor.getFloat(13), 
	        		  									localCursor.getInt(14), localCursor.getInt(15), localCursor.getInt(16),
														localCursor.getFloat(17), localCursor.getInt(18), localCursor.getInt(19),
														localCursor.getFloat(20), localCursor.getFloat(21), localCursor.getInt(22), 
														localCursor.getFloat(23));
	          
	          listadoVentaProductoTemp.add(localVentaProductoTemp);
	        } 
			while(localCursor.moveToNext());
			
		}
		
		return listadoVentaProductoTemp;
	}
		  
	public ArrayList<VentaProductoTemp> ObtenerListadoVentaProductoTempPorClienteYDistribuidor(int clienteId, int empleadoId) throws Exception
	{
		ArrayList<VentaProductoTemp> listadoVentaProductoTemp = null;
		Cursor localCursor  = null;
	    try
	    {
	    	localCursor = new VentaProductoTempDAL().ObtenerListadoVentaProductoTempPorClienteYDistribuidor(clienteId,empleadoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temporal por clienteId y empleadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos de la venta temporal por clienteId y empleadoId: " + localException.getMessage());
	    	} 
    	}

	    if(localCursor.getCount()>0)
	    {
	    	listadoVentaProductoTemp = new ArrayList<VentaProductoTemp>();
	    	
	    	do
	        {
	          VentaProductoTemp localVentaProductoTemp = new VentaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
	        		  									localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  									localCursor.getInt(5), localCursor.getInt(6), localCursor.getInt(7), 
	        		  									localCursor.getFloat(8), localCursor.getFloat(9), 
	        		  									localCursor.getFloat(10), localCursor.getFloat(11), 
	        		  									localCursor.getFloat(12), localCursor.getFloat(13), 
	        		  									localCursor.getInt(14), localCursor.getInt(15), localCursor.getInt(16),
														localCursor.getFloat(17),localCursor.getInt(18), localCursor.getInt(19),
														localCursor.getFloat(20), localCursor.getFloat(21), localCursor.getInt(22), 
														localCursor.getFloat(23));
	          
	          listadoVentaProductoTemp.add(localVentaProductoTemp);
	        } 
	    	while (localCursor.moveToNext());
	    }
	    
	    return listadoVentaProductoTemp;	      
 	}
		  
	public VentaProductoTemp ObtenerVentaProductoTempPor(int id) throws Exception
	{
		Cursor localCursor = null;
		VentaProductoTemp localVentaProductoTemp = null;
		try
		{
			localCursor = new VentaProductoTempDAL().ObtenerVentaProductoTempPor(id);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta temporal por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta temporal por rowId: " + localException.getMessage());
			} 
		}
			
		if(localCursor.getCount()>0)
		{
			localVentaProductoTemp = new VentaProductoTemp(localCursor.getInt(0), localCursor.getInt(1),localCursor.getInt(2), 
															localCursor.getInt(3), localCursor.getInt(4),localCursor.getInt(5), 
															localCursor.getInt(6), localCursor.getInt(7),localCursor.getFloat(8),
															localCursor.getFloat(9), localCursor.getFloat(10),localCursor.getFloat(11), 
															localCursor.getFloat(12), localCursor.getFloat(13),localCursor.getInt(14), 
															localCursor.getInt(15), localCursor.getInt(16), localCursor.getFloat(17),
															localCursor.getInt(18), localCursor.getInt(19),
															localCursor.getFloat(20), localCursor.getFloat(21), localCursor.getInt(22), 
															localCursor.getFloat(23));
		}
		return localVentaProductoTemp;
	}
	
	public VentaProductoTemp ObtenerVentaProductoTempPorProductoPromocion(int productoId,int promocionId) throws Exception
	{
		Cursor localCursor = null;
		VentaProductoTemp localVentaProductoTemp = null;
		try
		{
			localCursor = new VentaProductoTempDAL().ObtenerVentaProductoTempPorProductoPromocion(productoId, promocionId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta temporal por productoId y promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos de la venta temporal por productoId y promocionId: " + localException.getMessage());
			} 
		}
			
		if(localCursor.getCount()>0)
		{
			localVentaProductoTemp = new VentaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
															localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
															localCursor.getInt(6), localCursor.getInt(7), localCursor.getFloat(8),
															localCursor.getFloat(9), localCursor.getFloat(10), localCursor.getFloat(11), 
															localCursor.getFloat(12), localCursor.getFloat(13), localCursor.getInt(14), 
															localCursor.getInt(15), localCursor.getInt(16), localCursor.getFloat(17),
															localCursor.getInt(18), localCursor.getInt(19),
															localCursor.getFloat(20), localCursor.getFloat(21), localCursor.getInt(22), 
															localCursor.getFloat(23));
		}
		return localVentaProductoTemp;
	}
		  
	public ArrayList<VentaProductoTemp> ObtenerVentasProductoTemp() throws Exception
	{
		ArrayList<VentaProductoTemp> listadoVentaProductoTemp = null;
		Cursor localCursor=null;
		try
		{
			localCursor = new VentaProductoTempDAL().ObtenerVentasProductoTemp();
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoTemp: " + localException.getMessage());
	    	} 
		}
		 
		if(localCursor.getCount()>0)
		{
			listadoVentaProductoTemp = new ArrayList<VentaProductoTemp>();
			
			do
	        {
				VentaProductoTemp localVentaProductoTemp = new VentaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
	        		  									localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  									localCursor.getInt(5), localCursor.getInt(6), localCursor.getInt(7), 
	        		  									localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getFloat(10), 
	        		  									localCursor.getFloat(11), localCursor.getFloat(12), localCursor.getFloat(13), 
	        		  									localCursor.getInt(14), localCursor.getInt(15), localCursor.getInt(16),
														localCursor.getFloat(17), localCursor.getInt(18), localCursor.getInt(19),
														localCursor.getFloat(20), localCursor.getFloat(21), localCursor.getInt(22), 
														localCursor.getFloat(23));
				
				listadoVentaProductoTemp.add(localVentaProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaProductoTemp;
	}
	
	public ArrayList<VentaProductoTemp> ObtenerVentasProductoTempNoConfirmadas(int clienteId,int empleadoId) throws Exception
	{
		ArrayList<VentaProductoTemp> listadoVentaProductoTemp = null;
		Cursor localCursor=null;
		try
		{
			localCursor = new VentaProductoTempDAL().ObtenerVentasProductoTempNoConfirmadas(clienteId, empleadoId);
		}
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoTemp no confirmadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasProductoTemp no confirmadas: " + localException.getMessage());
	    	} 
		}
		 
		if(localCursor.getCount()>0)
		{
			listadoVentaProductoTemp = new ArrayList<VentaProductoTemp>();
			
			do
	        {
				VentaProductoTemp localVentaProductoTemp = new VentaProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
	        		  									localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  									localCursor.getInt(5), localCursor.getInt(6), localCursor.getInt(7), 
	        		  									localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getFloat(10), 
	        		  									localCursor.getFloat(11), localCursor.getFloat(12), localCursor.getFloat(13), 
	        		  									localCursor.getInt(14), localCursor.getInt(15), localCursor.getInt(16),
														localCursor.getFloat(17), localCursor.getInt(18), localCursor.getInt(19),
														localCursor.getFloat(20), localCursor.getFloat(21), localCursor.getInt(22), 
														localCursor.getFloat(23));
				
				listadoVentaProductoTemp.add(localVentaProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoVentaProductoTemp;
	}
}
