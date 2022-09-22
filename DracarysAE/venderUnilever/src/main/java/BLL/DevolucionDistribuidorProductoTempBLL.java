package BLL;

import java.util.ArrayList;
import Clases.DevolucionDistribuidorProductoTemp;
import DAL.DevolucionDistribuidorProductoTempDAL;
import android.database.Cursor;

public class DevolucionDistribuidorProductoTempBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarDevolucionDistribuidorProductoTemp() throws Exception
	{
		try
		{
			boolean bool = new DevolucionDistribuidorProductoTempDAL().BorrarDevolucionDistribuidorProductoTemp();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public boolean BorrarDevolucionDistribuidorProductoTempPorEmpleadoIdYClienteId(int empleadoId, int clienteId) throws Exception
	{
		try
		{
			boolean bool = new DevolucionDistribuidorProductoTempDAL().BorrarDevolucionDistribuidorProductoTempPor(empleadoId,clienteId);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por empleadoId y clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolcuionPRoductoTemppor empleadoId y clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public boolean BorrarDevolucionDistribuidorProductoTempPorId(long Id) throws Exception
	{
		try
		{
			boolean bool = new DevolucionDistribuidorProductoTempDAL().BorrarDevolucionDistribuidorProductoTempPorId(Id);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por Id: " + localException.getMessage());
			}
			throw localException;
	    }
	}
	
	public boolean BorrarDevolucionDistribuidorProductoTempPorTempId(int tempId) throws Exception
	{
		try
		{
			boolean bool = new DevolucionDistribuidorProductoTempDAL().BorrarDevolucionDistribuidorProductoTempPorTempId(tempId);
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por tempId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos del almacenDevolucionProductoTemp por tempId: " + localException.getMessage());
			}
			throw localException;
	    }
	}
		  
	public long InsertarDevolucionDistribuidorProductoTemp(int tempId, int productoId, int promocionId, int cantidad, 
											int cantidadPaquete, float monto, float descuento, float montoFinal, 
											int empleadoId, int clienteId,float costo,int costoId,int precioId,
											int noAutoventa, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId,
											float descuentoProntoPago) throws Exception
  		{
		try
		{
			long l = new DevolucionDistribuidorProductoTempDAL().InsertarDevolucionDistribuidorProductoTemp(tempId,productoId,promocionId,
					cantidad,cantidadPaquete,monto,descuento,montoFinal,empleadoId,clienteId,costo,costoId,precioId,noAutoventa,
					descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago);
			return l;
	    }
    	catch (Exception localException)
		{
    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenDevolucionProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenDevolucionProductoTemp: " + localException.getMessage());
	    	} 
    		throw localException;
	    }
  	}
		  
	public ArrayList<DevolucionDistribuidorProductoTemp> ObtenerDevolucionDistribuidorProductoTempPor(int clienteId) throws Exception
	{
		ArrayList<DevolucionDistribuidorProductoTemp> listadoAlmacenDevolucionProductoTemp = null;
		Cursor localCursor = null;
		    
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempPor(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos del almacenDevolucionProductoTemp por clienteId: " + localException.getMessage());
			} 
		}

		if(localCursor.getCount() > 0)
		{
			listadoAlmacenDevolucionProductoTemp = new ArrayList<DevolucionDistribuidorProductoTemp>();
			
			do
	        {
	          DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(
	        		  									localCursor.getInt(0), localCursor.getInt(1), 
	        		  									localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  									localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
	        		  									localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),
	        		  									localCursor.getFloat(11),localCursor.getInt(12),localCursor.getInt(13),
	        		  									localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
	        		  									localCursor.getInt(17), localCursor.getFloat(18));
	          
	          listadoAlmacenDevolucionProductoTemp.add(localAlmacenDevolucionProductoTemp);
	        } 
			while(localCursor.moveToNext());
			
		}
		
		return listadoAlmacenDevolucionProductoTemp;
	}
		  
	public ArrayList<DevolucionDistribuidorProductoTemp> ObtenerDevolucionDistribuidorProductoTempPorClienteYDistribuidor(int clienteId, int empleadoId) throws Exception
	{
		ArrayList<DevolucionDistribuidorProductoTemp> listadoAlmacenDevolucionProductoTemp = null;
		Cursor localCursor  = null;
	    try
	    {
	    	localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempPorClienteYDistribuidor(clienteId,empleadoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacenDevolucionProductoTemp por clienteId y empleadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacenDevolucionProductoTemp por clienteId y empleadoId: " + localException.getMessage());
	    	} 
    	}

	    if(localCursor.getCount()>0)
	    {
	    	listadoAlmacenDevolucionProductoTemp = new ArrayList<DevolucionDistribuidorProductoTemp>();
	    	
	    	do
	        {
	          DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
						localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),localCursor.getFloat(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), 
						localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
	          
	          listadoAlmacenDevolucionProductoTemp.add(localAlmacenDevolucionProductoTemp);
	        } 
	    	while (localCursor.moveToNext());
	    }
	    
	    return listadoAlmacenDevolucionProductoTemp;	      
 	}
		  
	public DevolucionDistribuidorProductoTemp ObtenerDevolucionDistribuidorProductoTempPorRowId(int id) throws Exception
	{
		Cursor localCursor = null;
		DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = null;
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempPorRowId(id);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos del almacenDevolucionProductoTemp por rowId: " + localException.getMessage());
			} 
		}
			
		if(localCursor.getCount()>0)
		{
			localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
					localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
					localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
					localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),localCursor.getFloat(11),
					localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), 
					localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
		}
		return localAlmacenDevolucionProductoTemp;
	}
	
	public DevolucionDistribuidorProductoTemp ObtenerDevolucionDistribuidorProductoTempPorProductoPromocion(int productoId,int promocionId) throws Exception
	{
		Cursor localCursor = null;
		DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = null;
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempPorProductoPromocion(productoId, promocionId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por productoId y promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por productoId y promocionId: " + localException.getMessage());
			} 
		}
			
		if(localCursor.getCount()>0)
		{
			localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
					localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
					localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
					localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),localCursor.getFloat(11),
					localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), 
					localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
		}
		return localAlmacenDevolucionProductoTemp;
	}
		  
	public ArrayList<DevolucionDistribuidorProductoTemp> ObtenerDevolucionDistribuidorProductoTemp() throws Exception
	{
		ArrayList<DevolucionDistribuidorProductoTemp> listadoAlmacenDevolucionProductoTemp = null;
		Cursor localCursor=null;
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTemp();
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp: " + localException.getMessage());
	    	} 
		}
		 
		if(localCursor.getCount()>0)
		{
			listadoAlmacenDevolucionProductoTemp = new ArrayList<DevolucionDistribuidorProductoTemp>();
			
			do
	        {
				DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(
						localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
						localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),localCursor.getFloat(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), 
						localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoAlmacenDevolucionProductoTemp.add(localAlmacenDevolucionProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoAlmacenDevolucionProductoTemp;
	}
	
	public ArrayList<DevolucionDistribuidorProductoTemp> ObtenerDevolucionDistribuidorProductoTempNoConfirmadas(int clienteId,int empleadoId) throws Exception
	{
		ArrayList<DevolucionDistribuidorProductoTemp> listadoAlmacenDevolucionProductoTemp = null;
		Cursor localCursor=null;
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempNoConfirmadas(clienteId, empleadoId);
		}
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp no confirmadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp no confirmadas: " + localException.getMessage());
	    	} 
		}
		 
		if(localCursor.getCount()>0)
		{
			listadoAlmacenDevolucionProductoTemp = new ArrayList<DevolucionDistribuidorProductoTemp>();
			
			do
	        {
				DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
						localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),localCursor.getFloat(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), 
						localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoAlmacenDevolucionProductoTemp.add(localAlmacenDevolucionProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoAlmacenDevolucionProductoTemp;
	}

	public ArrayList<DevolucionDistribuidorProductoTemp> ObtenerDevolucionDistribuidorProductoTempNoSincronizadasPor(int clienteId) throws Exception
	{
		ArrayList<DevolucionDistribuidorProductoTemp> listadoDevolucionDistribuidorProductoTemp = null;
		Cursor localCursor=null;
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempNoSincronizadosPor(clienteId);
		}
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp no sincronizados: " + localException.getMessage());
	    	} 
		}
		 
		if(localCursor.getCount()>0)
		{
			listadoDevolucionDistribuidorProductoTemp = new ArrayList<DevolucionDistribuidorProductoTemp>();
			
			do
	        {
				DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(localCursor.getInt(0), localCursor.getInt(1), 
						localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
						localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
						localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),localCursor.getFloat(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14), localCursor.getFloat(15), 
						localCursor.getFloat(16), localCursor.getInt(17), localCursor.getFloat(18));
				
				listadoDevolucionDistribuidorProductoTemp.add(localAlmacenDevolucionProductoTemp);
	        } 
			while (localCursor.moveToNext());
		}
		return listadoDevolucionDistribuidorProductoTemp;
	}
	
	public ArrayList<DevolucionDistribuidorProductoTemp> ObtenerDevolucionDistribuidorProductoTempPorClienteIdNoAutoventa(int clienteId,int noAutoventa) throws Exception
	{
		ArrayList<DevolucionDistribuidorProductoTemp> listadoAlmacenDevolucionProductoTemp = null;
		Cursor localCursor = null;
		    
		try
		{
			localCursor = new DevolucionDistribuidorProductoTempDAL().ObtenerDevolucionDistribuidorProductoTempPorClienteIdNoAutoventa(clienteId, noAutoventa);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos del almacenDevolucionProductoTemp por clienteId y noAutoventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prodcutos del almacenDevolucionProductoTemp por clienteId y noAutoventa: " + localException.getMessage());
			} 
		}

		if(localCursor.getCount() > 0)
		{
			listadoAlmacenDevolucionProductoTemp = new ArrayList<DevolucionDistribuidorProductoTemp>();
			
			do
	        {
	          DevolucionDistribuidorProductoTemp localAlmacenDevolucionProductoTemp = new DevolucionDistribuidorProductoTemp(
	        		  									localCursor.getInt(0), localCursor.getInt(1), 
	        		  									localCursor.getInt(2), localCursor.getInt(3), localCursor.getInt(4), 
	        		  									localCursor.getInt(5), localCursor.getFloat(6), localCursor.getFloat(7), 
	        		  									localCursor.getFloat(8),localCursor.getInt(9), localCursor.getInt(10),
	        		  									localCursor.getFloat(11),localCursor.getInt(12),localCursor.getInt(13),
	        		  									localCursor.getInt(14), localCursor.getFloat(15), localCursor.getFloat(16), 
	        		  									localCursor.getInt(17), localCursor.getFloat(18));
	          
	          listadoAlmacenDevolucionProductoTemp.add(localAlmacenDevolucionProductoTemp);
	        } 
			while(localCursor.moveToNext());
			
		}
		
		return listadoAlmacenDevolucionProductoTemp;
	}
}
