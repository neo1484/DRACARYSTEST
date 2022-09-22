package BLL;

import java.util.ArrayList;
import Clases.AvanceDistribucion;
import DAL.AvanceDistribucionDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AvanceDistribucionBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAvancesDistribucion() throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceDistribucionDAL().BorrarAvancesDistribucion();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarAvancesDistribucionPor(int distribuidorId) throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceDistribucionDAL().BorrarAvanceDistribucionPor(distribuidorId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion por distribuidorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion por distribuidorId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public boolean BorrarAvancesDistribucionPorTipoAvanceDistribucionYRol(String tipoAvanceDistribucion,String rol) throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceDistribucionDAL().BorrarAvanceDistribucionPorTipoAvanceDistribuidorYRol(tipoAvanceDistribucion,rol);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion por tipoAvanceDistribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion por tipoAvanceDistribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarAvanceDistribucion(SoapObject avanceDistribucion) throws Exception
	{
		try
		{
			long l = new AvanceDistribucionDAL().InsertarAvanceDistribucion(avanceDistribucion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances de la distribucion : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances de la distribucion: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<AvanceDistribucion> ObtenerAvancesDistribucionPor(int distribuidorId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<AvanceDistribucion> listadoAvanceDistribucion = null;
	    
	    try
	    {
	    	localCursor = new AvanceDistribucionDAL().ObtenerAvancesDistribuidorPor(distribuidorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesDistribucion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesDistribucion: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoAvanceDistribucion = new ArrayList<AvanceDistribucion>();
	        	do
	        	{
	        		AvanceDistribucion localAvanceDistribucion = new AvanceDistribucion(localCursor.getInt(1),localCursor.getInt(2),
	        				localCursor.getInt(3),localCursor.getInt(4),localCursor.getString(5),localCursor.getInt(6),
	        				localCursor.getFloat(7),localCursor.getInt(8),localCursor.getFloat(9),localCursor.getString(10),
	        				localCursor.getString(11));
	        		
	        		listadoAvanceDistribucion.add(localAvanceDistribucion);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoAvanceDistribucion;
	}
	
	public ArrayList<AvanceDistribucion> ObtenerAvanceDistribucionPorTipoAvanceDistribuidorYRol(String tipoAvanceDistribucion,String rol) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<AvanceDistribucion> listadoAvanceDistribucion = null;
	    
	    try
	    {
	    	localCursor = new AvanceDistribucionDAL().ObtenerAvancesDistribucionPorTipoAvanceDistribuidorYRol(tipoAvanceDistribucion,rol);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesDistribucion por tipoAvanceDistribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesDistribucion por tipoAvanceDistribuidor: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoAvanceDistribucion = new ArrayList<AvanceDistribucion>();
	        	do
	        	{
	        		AvanceDistribucion localAvanceDistribucion = new AvanceDistribucion(localCursor.getInt(1),localCursor.getInt(2),
	        				localCursor.getInt(3),localCursor.getInt(4),localCursor.getString(5),localCursor.getInt(6),
	        				localCursor.getFloat(7),localCursor.getInt(8),localCursor.getFloat(9),localCursor.getString(10),
	        				localCursor.getString(11));
	        		
	        		listadoAvanceDistribucion.add(localAvanceDistribucion);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoAvanceDistribucion;
	}
}
