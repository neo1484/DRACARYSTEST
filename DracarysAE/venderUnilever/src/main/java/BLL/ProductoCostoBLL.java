package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.CostoWSResult;
import Clases.ProductoCosto;
import DAL.ProductoCostoDAL;

public class ProductoCostoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProductoCostoPorProductoId(long productoId) throws Exception
	{
	    try
	    {
	    	boolean bool = new ProductoCostoDAL().BorrarProductoCostoPorProductoId(productoId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos de los productos por productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos de los productos por productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public boolean BorrarProductosCosto() throws Exception
	{
	    try
	    {
	    	boolean bool = new ProductoCostoDAL().BorrarProductosCosto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos de los productos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos de los productos: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarProductoCosto(ArrayList<CostoWSResult> costos) throws Exception
	{
	    try
	    {
	    	long l = new ProductoCostoDAL().InsertarProductoCosto(costos);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el costos del productoo: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el costos del producto: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public ProductoCosto ObtenerProductosCosto(int productoId) throws Exception
	{
		Cursor localCursor;
		ProductoCosto localProductoCosto=null;
		
    	try
    	{
    		localCursor = new ProductoCostoDAL().ObtenerProductoCostoPor(productoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los costos del producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los costos del producto: " + localException.getMessage());
	    	}
	    	throw localException;
    	}
	    if(localCursor.getCount()>0)
	    {
	    	localProductoCosto = new ProductoCosto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
	    											localCursor.getFloat(3),localCursor.getFloat(4),localCursor.getFloat(5));
	    }
	    	return localProductoCosto;
	  }
	  
	public ArrayList<ProductoCosto> ObtenerProductosCosto() throws Exception
	  {
		  Cursor localCursor = null;
		  ArrayList<ProductoCosto> listadoProductoCosto = null;
		  
		  try
		  {
			  localCursor = new ProductoCostoDAL().ObtenerProductosCosto();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los costos de los productos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los costos de los productos: " + localException.getMessage());
				}
		  }
	      
		  if(localCursor.getCount()>0)
		  {
			  listadoProductoCosto = new ArrayList<ProductoCosto>();
		  	  do
		  	  {
		          ProductoCosto productoCosto = new ProductoCosto(localCursor.getInt(0), localCursor.getInt(1), 
		        		  											localCursor.getInt(2),localCursor.getFloat(3),
		        		  											localCursor.getFloat(4),localCursor.getFloat(5));
		          listadoProductoCosto.add(productoCosto);
		  	  } 
		  	  while(localCursor.moveToNext());
		  }
		  
		  return listadoProductoCosto;  
	  }
}

