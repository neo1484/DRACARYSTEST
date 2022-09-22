package BLL;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import Clases.PrecioLista;
import Clases.PrecioWSResult;
import DAL.PrecioListaDAL;

public class PrecioListaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreciosLista() throws Exception
	{
		try
		{
			boolean bool = new PrecioListaDAL().BorrarPreciosLista();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios lista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios lista: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarPrecioLista(ArrayList<PrecioWSResult> precios) throws Exception
	{
		try
		{
			long l = new PrecioListaDAL().InsertarPrecioLista(precios);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios lista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los precios lista: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
		  
	public PrecioLista ObtenerPrecioListaPor(int precioListaId, int productoId) throws Exception
	{
		Cursor localCursor;
		PrecioLista precioLista =null;
		try
		{
			localCursor = new PrecioListaDAL().ObtenerPrecioListaPor(precioListaId,productoId);
    	}
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precio del producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if (localCursor.getCount() > 0) 
		{
			precioLista = new PrecioLista(localCursor.getInt(1), localCursor.getInt(2), localCursor.getFloat(3), 
					localCursor.getFloat(4),localCursor.getFloat(5),localCursor.getFloat(6),localCursor.getFloat(7),
					localCursor.getFloat(8),localCursor.getInt(9),localCursor.getString(10).equals("1")?true:false);
		}
		return precioLista;
	}
	
	public PrecioLista ObtenerPrecioListaPor(int precioListaId, int productoId,int precioId) throws Exception
	{
		Cursor localCursor;
		PrecioLista precioLista =null;
		try
		{
			localCursor = new PrecioListaDAL().ObtenerPrecioListaPor(precioListaId,productoId,precioId);
    	}
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el precioLista del producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if (localCursor.getCount() > 0) 
		{
			precioLista = new PrecioLista(localCursor.getInt(1), localCursor.getInt(2), localCursor.getFloat(3), 
					localCursor.getFloat(4),localCursor.getFloat(5),localCursor.getFloat(6),localCursor.getFloat(7),
					localCursor.getFloat(8),localCursor.getInt(9),localCursor.getString(10).equals("1")?true:false);
		}
		return precioLista;
	}
}
