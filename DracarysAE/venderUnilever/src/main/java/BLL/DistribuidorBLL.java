package BLL;

import java.util.ArrayList;
import Clases.Distribuidor;
import Clases.DistribuidorWSResult;
import DAL.DistribuidorDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class DistribuidorBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDistribuidores() throws Exception
	{
	    try
	    {
	    	boolean bool = new DistribuidorDAL().BorrarDistribuidores();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarDistribuidorPor(int rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new DistribuidorDAL().BorrarDistribuidorPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores por rowId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarDistribuidor(ArrayList<DistribuidorWSResult> distribuidores) throws Exception
	{
		try
		{
			long l = new DistribuidorDAL().InsertarDistribuidor(distribuidores);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los distribuidores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los distribuidores: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public Distribuidor ObtenerDistribuidorPor(int distribuidorId) throws Exception
	{
	    Cursor localCursor = null;
	    Distribuidor distribuidor = null;
	    
	    try
	    {
	    	localCursor = new DistribuidorDAL().ObtenerDistribuidorPor(distribuidorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener el distribuidor por distribuidorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el distribuidor por distribuidorId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        		distribuidor = new Distribuidor(localCursor.getInt(1),localCursor.getString(2));
	        }
	    }
	    
	    return distribuidor;
	}
	
	public ArrayList<Distribuidor> ObtenerDistribuidores() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<Distribuidor> listadoDistribuidor = null;
	    
	    try
	    {
	    	localCursor = new DistribuidorDAL().ObtenerDistribuidores();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los distribuidores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los distribuidores: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoDistribuidor = new ArrayList<Distribuidor>();
	        	do
	        	{
	        		Distribuidor localDistribuidor = new Distribuidor(localCursor.getInt(1),localCursor.getString(2));
	        		
	        		listadoDistribuidor.add(localDistribuidor);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoDistribuidor;
	}

}
