package BLL;

import Clases.ProveedorPrecioListaMargen;
import Clases.ProveedorPrecioListaMargenWSResult;
import DAL.ProveedorPrecioListaMargenDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ProveedorPrecioListaMargenBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarProveedorPrecioListaMargen() throws Exception
	{
	    try
	    {
	    	boolean bool = new ProveedorPrecioListaMargenDAL().BorrarProveedorPrecioListaMargen();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los margenes del proveedorPrecioListaMargen: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los margenes del proveedorPrecioListaMargen: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarProveedorPrecioListaMargen(ArrayList<ProveedorPrecioListaMargenWSResult> proveedoresPrecioListaMargen)
				throws Exception
	{
		try
		{
			long l = new ProveedorPrecioListaMargenDAL().InsertarProveedorPrecioListaMargen(proveedoresPrecioListaMargen);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los margenes del proveedor precio lista margen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los margenes del proveedor precio lista margen: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ProveedorPrecioListaMargen ObtenerMargenProveedorPrecioListaMargenPor(int proveedorId,int precioListaId) throws Exception
	{
	    Cursor localCursor = null;
	    ProveedorPrecioListaMargen localProveedorPrecioListaMargen = null;
	    
	    try
	    {
	    	localCursor = new ProveedorPrecioListaMargenDAL().obtenerMargenProveedorPrecioListaMargenPor(proveedorId, precioListaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el margen del proveedorPrecioListaMargen: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localProveedorPrecioListaMargen = new ProveedorPrecioListaMargen(localCursor.getInt(1),localCursor.getInt(2),
	        																	localCursor.getFloat(3));
	        }
	    }
	    
	    return localProveedorPrecioListaMargen;
	}
}
