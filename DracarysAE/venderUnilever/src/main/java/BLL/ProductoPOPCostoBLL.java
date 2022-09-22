package BLL;

import java.util.ArrayList;
import Clases.ProductoPOPCosto;
import Clases.ProductoPOPCostoWSResult;
import DAL.ProductoPOPCostoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProductoPOPCostoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarProductosPOPCosto() throws Exception
	{
		try
		{
			boolean bool = new ProductoPOPCostoDAL().BorrarProductosPOPCosto();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosPOPCosto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosPOPCosto: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarProductoPOPCostoPor(long id) throws Exception
	{
		try
		{
			boolean bool = new ProductoPOPCostoDAL().BorrarProductoPOPCostoPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosPOPCosto por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosPOPCosto por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarProductoPOPCosto(ArrayList<ProductoPOPCostoWSResult> productosPOPCosto) throws Exception
	{
		try
	    {
			long l = new ProductoPOPCostoDAL().InsertarProductoPOPCosto(productosPOPCosto);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los costos del producto POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los costos del producto POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<ProductoPOPCosto> ObtenerProductosPOPCosto() throws Exception
	{
		ArrayList<ProductoPOPCosto> listadoProductoPOPCosto = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new ProductoPOPCostoDAL().ObtenerProductosPOPCosto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOPCosto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOPCosto: " + localException.getMessage());
			} 
			return listadoProductoPOPCosto;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoProductoPOPCosto = new ArrayList<ProductoPOPCosto>();
			
			do
			{
				ProductoPOPCosto ProductoPOPCosto = new ProductoPOPCosto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getFloat(3));
				
				listadoProductoPOPCosto.add(ProductoPOPCosto);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoProductoPOPCosto;
	}
	
	public ProductoPOPCosto ObtenerProductoPOPCostoPorProductoId(int productoId) throws Exception
	{
		Cursor localCursor;
		ProductoPOPCosto localProductoPOPCosto = new ProductoPOPCosto();

		try
		{
			localCursor = new ProductoPOPCostoDAL().ObtenerProductoPOPCostoPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoPOPCosto por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoPOPCosto por productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			localProductoPOPCosto = new ProductoPOPCosto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getFloat(3));
		}

		return localProductoPOPCosto;
	}
}
