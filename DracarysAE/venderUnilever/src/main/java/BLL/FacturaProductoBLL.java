package BLL;

import java.util.ArrayList;
import Clases.FacturaProducto;
import DAL.FacturaProductoDAL;
import android.database.Cursor;

public class FacturaProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarFacturasProducto() throws Exception
	{
	    try
	    {
	    	boolean bool = new FacturaProductoDAL().BorrarFacturasProducto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarFacturaProducto(long facturaId,int productoId, int promocionId, int cantidad,int cantidadPaquete,
										float monto, float descuento,	float montoFinal, int clienteId, int empleadoId,
										boolean sincronizacion,int precioId,int noFactura)	throws Exception
	{
		try
		{
			long l = new FacturaProductoDAL().InsertarFacturaProducto(facturaId,productoId, promocionId, cantidad, cantidadPaquete,
																		monto,descuento, montoFinal, clienteId, empleadoId, 
																		sincronizacion,precioId,noFactura);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura producto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura producto: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<FacturaProducto> ObtenerFacturasProducto() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<FacturaProducto> localListadoFacturaProducto = null;
	    
	    try
	    {
	    	localCursor = new FacturaProductoDAL().obtenerFacturasProducto();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas producto: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localListadoFacturaProducto = new ArrayList<FacturaProducto>();
	        	do
	        	{
	        		FacturaProducto localFacturaProducto = new FacturaProducto(localCursor.getInt(0),localCursor.getInt(1),
	        				localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
	        				localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8),localCursor.getInt(9),
	        				localCursor.getInt(10),localCursor.getString(11).equals("1")?true:false,localCursor.getInt(12),
	        				localCursor.getInt(13));
	        		
	        		localListadoFacturaProducto.add(localFacturaProducto);
	        	}
	        	while(localCursor.moveToNext());
	        }
	    }
	    
	    return localListadoFacturaProducto;
	}

	public ArrayList<FacturaProducto> ObtenerFacturasProductoNoSincronizadasPorFacturaId(int facturaId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<FacturaProducto> localListadoFacturaProducto = null;
	    
	    try
	    {
	    	localCursor = new FacturaProductoDAL().obtenerFacturasProductoNoSincronizadasPorFacturaId(facturaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto no sincronizadas por facturaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto no sincronizadas por facturaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localListadoFacturaProducto = new ArrayList<FacturaProducto>();
	        	do
	        	{
	        		FacturaProducto localFacturaProducto = new FacturaProducto(localCursor.getInt(0),localCursor.getInt(1),
	        				localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
	        				localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8),localCursor.getInt(9),
	        				localCursor.getInt(10),localCursor.getString(11).equals("1")?true:false,localCursor.getInt(12),
	    	        				localCursor.getInt(13));
	        		
	        		localListadoFacturaProducto.add(localFacturaProducto);
	        	}
	        	while(localCursor.moveToNext());
	        }
	    }
	    
	    return localListadoFacturaProducto;
	}

	public ArrayList<FacturaProducto> ObtenerFacturasProductoPorFacturaId(int facturaId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<FacturaProducto> localListadoFacturaProducto = null;
	    
	    try
	    {
	    	localCursor = new FacturaProductoDAL().obtenerFacturasProductoPorFacturaId(facturaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto por facturaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturasProducto por facturaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localListadoFacturaProducto = new ArrayList<FacturaProducto>();
	        	do
	        	{
	        		FacturaProducto localFacturaProducto = new FacturaProducto(localCursor.getInt(0),localCursor.getInt(1),
	        				localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5),
	        				localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8),localCursor.getInt(9),
	        				localCursor.getInt(10),localCursor.getString(11).equals("1")?true:false,localCursor.getInt(12),
	    	        		localCursor.getInt(13));
	        		
	        		localListadoFacturaProducto.add(localFacturaProducto);
	        	}
	        	while(localCursor.moveToNext());
	        }
	    }
	    
	    return localListadoFacturaProducto;
	}

	public int ModificarFacturaProducto(int rowId,boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new FacturaProductoDAL().ModificarFacturaProductoSincronizacion(rowId, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la facturaProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la facturaProducto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
}
