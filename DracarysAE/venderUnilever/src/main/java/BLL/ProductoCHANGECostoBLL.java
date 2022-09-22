package BLL;

import java.util.ArrayList;

import Clases.ProductoCHANGECosto;
import DAL.ProductoCHANGECostoDAL;
import android.database.Cursor;

public class ProductoCHANGECostoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarProductosCHANGECosto() throws Exception
	{
		try
		{
			boolean bool = new ProductoCHANGECostoDAL().BorrarProductosCHANGECosto();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGECosto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGECosto: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarProductoCHANGECostoPor(long id) throws Exception
	{
		try
		{
			boolean bool = new ProductoCHANGECostoDAL().BorrarProductoCHANGECostoPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosCHANGECosto por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosCHANGECosto por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarProductoCHANGECosto(int costoId,int productoId,float costo) throws Exception
	{
		try
	    {
			long l = new ProductoCHANGECostoDAL().InsertarProductoCHANGECosto(costoId,productoId,costo);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGECosto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGECosto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<ProductoCHANGECosto> ObtenerProductosCHANGECosto() throws Exception
	{
		ArrayList<ProductoCHANGECosto> listadoProductoCHANGECosto = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new ProductoCHANGECostoDAL().ObtenerProductosCHANGECosto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGECosto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGECosto: " + localException.getMessage());
			} 
			return listadoProductoCHANGECosto;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoProductoCHANGECosto = new ArrayList<ProductoCHANGECosto>();
			
			do
			{
				ProductoCHANGECosto ProductoCHANGECosto = new ProductoCHANGECosto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getFloat(3));
				
				listadoProductoCHANGECosto.add(ProductoCHANGECosto);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoProductoCHANGECosto;
	}
	
	public ProductoCHANGECosto ObtenerProductoCHANGECostoPorProductoId(int productoId) throws Exception
	{
		Cursor localCursor;
		ProductoCHANGECosto localProductoCHANGECosto = new ProductoCHANGECosto();

		try
		{
			localCursor = new ProductoCHANGECostoDAL().ObtenerProductoCHANGECostoPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGECosto por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGECosto por productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			localProductoCHANGECosto = new ProductoCHANGECosto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getFloat(3));
		}

		return localProductoCHANGECosto;
	}
}