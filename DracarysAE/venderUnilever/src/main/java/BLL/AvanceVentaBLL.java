package BLL;

import java.util.ArrayList;

import Clases.AvanceVendedorDiaWSResult;
import Clases.AvanceVenta;
import DAL.AvanceVentaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AvanceVentaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAvancesVenta() throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceVentaDAL().BorrarAvancesVenta();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarAvancesVentaPor(int vendedorId) throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceVentaDAL().BorrarAvanceVentaPor(vendedorId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por vendedorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por vendedorId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public boolean BorrarAvancesVentaPorTipoAvanceVentaYRol(String tipoAvanceVenta,String rol) throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceVentaDAL().BorrarAvanceVentaPorTipoAvanceVentaYRol(tipoAvanceVenta,rol);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por tipoAvanceVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por tipoAvanceVenta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarAvanceVenta(ArrayList<AvanceVendedorDiaWSResult> avancesVenta, int mes) throws Exception
	{
		try
		{
			long l = new AvanceVentaDAL().InsertarAvanceVenta(avancesVenta, mes);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta: " + localException.getMessage());
			} 
			throw localException;
		}
	}

	public long InsertarAvanceVenta(int vendedorId,int dia,int mes,int anio,String nombreVendedor,float presupuesto,float avance,
									float tendencia,float cobertura,String tipoAvanceVenta,String rol,String nombreProveedor,
									int noPreventas,float coberturaPorcentaje) throws Exception
	{
		try
		{
			long l = new AvanceVentaDAL().InsertarAvanceVenta(vendedorId, dia, mes, anio, nombreVendedor, presupuesto, avance,
					tendencia, cobertura, tipoAvanceVenta,rol,nombreProveedor,
					noPreventas,coberturaPorcentaje);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el avanceVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el avanceVenta: " + localException.getMessage());
			}
			throw localException;
		}
	}
	   
	public ArrayList<AvanceVenta> ObtenerAvancesVentaPor(int vendedorId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<AvanceVenta> listadoAvanceVenta = null;
	    
	    try
	    {
	    	localCursor = new AvanceVentaDAL().ObtenerAvancesVentaPor(vendedorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoAvanceVenta = new ArrayList<AvanceVenta>();
	        	do
	        	{
	        		AvanceVenta localAvanceVenta = new AvanceVenta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
	        				localCursor.getInt(3),localCursor.getInt(4),localCursor.getString(5),localCursor.getFloat(6),
	        				localCursor.getFloat(7),localCursor.getFloat(8),localCursor.getFloat(9),localCursor.getString(10),
	        				localCursor.getString(11),localCursor.getString(12),localCursor.getInt(13),localCursor.getFloat(14));
	        		
	        		listadoAvanceVenta.add(localAvanceVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoAvanceVenta;
	}
	
	public ArrayList<AvanceVenta> ObtenerAvanceVentaPorTipoAvanceVentaYRol(String tipoAvanceVenta,String rol) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<AvanceVenta> listadoAvanceVenta = null;
	    
	    try
	    {
	    	localCursor = new AvanceVentaDAL().ObtenerAvancesVentaPorTipoAvanceVentaYRol(tipoAvanceVenta,rol);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por tipoAvanceVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por tipoAvanceVenta: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoAvanceVenta = new ArrayList<AvanceVenta>();
	        	do
	        	{
	        		AvanceVenta localAvanceVenta = new AvanceVenta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),
	        				localCursor.getInt(3),localCursor.getInt(4),localCursor.getString(5),localCursor.getFloat(6),
	        				localCursor.getFloat(7),localCursor.getFloat(8),localCursor.getFloat(9),localCursor.getString(10),
	        				localCursor.getString(11),localCursor.getString(12),localCursor.getInt(13),localCursor.getFloat(14));
	        		
	        		listadoAvanceVenta.add(localAvanceVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoAvanceVenta;
	}
}
