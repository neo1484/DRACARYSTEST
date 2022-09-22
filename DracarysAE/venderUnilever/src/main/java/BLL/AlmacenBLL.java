package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Almacen;
import Clases.AlmacenTempWSResult;
import DAL.AlmacenDAL;

public class AlmacenBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAlmacenes() throws Exception
	{
	    AlmacenDAL localAlmacenDAL = new AlmacenDAL();
	    try
	    {
	    	boolean bool = localAlmacenDAL.BorrarAlmacenes();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenes: " + localException.getMessage());
	    	}    
	    	throw localException;
	    }
	}
	  
	public long InsertarAlmacen(AlmacenTempWSResult almacen) throws Exception
	{
		AlmacenDAL localAlmacenDAL = new AlmacenDAL();
	    try
	    {
	    	long l = localAlmacenDAL.InsertarAlmacen(almacen);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public Almacen ObtenerAlmacenPor(int almacenId)throws Exception
	{
	    AlmacenDAL localAlmacenDAL = new AlmacenDAL();
	    Cursor localCursor;
	    try
	    {
	    	localCursor = localAlmacenDAL.ObtenerAlmacenPor(almacenId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen por almacenId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacen por almacenId: " + localException.getMessage());
	    	} 
	    	throw localException;  
	    }
	    
	    Almacen localAlmacen=null;
	    
	    if (localCursor.getCount() > 0) 
	    {
	        localAlmacen = new Almacen(localCursor.getInt(1), localCursor.getString(2));
	    }
	      
	    return localAlmacen;
	}
	  
	public ArrayList<Almacen> ObtenerAlmacenes() throws Exception
	{
		Cursor localCursor;
		ArrayList<Almacen> listadoAlmacen = null;
	    try
	    {
	    	localCursor = new AlmacenDAL().ObtenerAlmacenes();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenes: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }

	    if (localCursor.getCount() > 0)
	    {
	    	listadoAlmacen = new ArrayList<Almacen>();
	        do
	        {
	        	Almacen almacen = new Almacen(localCursor.getInt(1), localCursor.getString(2));
	        	
	        	listadoAlmacen.add(almacen);
	        } 
	        while (localCursor.moveToNext());
	    }
	    
	    return listadoAlmacen;
	  }
}
