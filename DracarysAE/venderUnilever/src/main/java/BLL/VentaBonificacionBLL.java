package BLL;

import java.util.ArrayList;
import Clases.VentaBonificacion;
import Clases.VentaBonificacionWSResult;
import DAL.VentaBonificacionDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class VentaBonificacionBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarVentasBonificacion() throws Exception
	{
	    try
	    {
	    	boolean bool = new VentaBonificacionDAL().BorrarVentasBonificacion();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificcion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificacion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarVentaBonificacionPor(int rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new VentaBonificacionDAL().BorrarVentaBonificacionPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificacion por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificacion por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarVentaBonificacion(ArrayList<VentaBonificacionWSResult> ventasBonificacion) throws Exception
	{
		try
		{
			long l = new VentaBonificacionDAL().InsertarVentaBonificacion(ventasBonificacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ventas bonificadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las ventas bonificadas: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public VentaBonificacion ObtenerVentaBonificacionPor(int ventaId) throws Exception
	{
	    Cursor localCursor = null;
	    VentaBonificacion ventaBonificacion = null;
	    
	    try
	    {
	    	localCursor = new VentaBonificacionDAL().ObtenerVentaBonificacionPorVentaId(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventaBonificacion por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener ventaBonificacion por ventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	ventaBonificacion = new VentaBonificacion(localCursor.getInt(1),localCursor.getInt(2));
	        }
	    }
	    
	    return ventaBonificacion;
	}
	
	public ArrayList<VentaBonificacion> ObtenerVentasBonificacion() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<VentaBonificacion> listadoVentaBonificacion = null;
	    
	    try
	    {
	    	localCursor = new VentaBonificacionDAL().ObtenerVentasBonificacion();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasBonificacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventasBonificacion: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoVentaBonificacion = new ArrayList<VentaBonificacion>();
	        	do
	        	{
	        		VentaBonificacion localVentaBonificacion = new VentaBonificacion(localCursor.getInt(1),localCursor.getInt(2));
	        		
	        		listadoVentaBonificacion.add(localVentaBonificacion);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoVentaBonificacion;
	}
}
