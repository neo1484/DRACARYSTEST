package BLL;

import java.util.ArrayList;
import Clases.ProductoPOP;
import Clases.ProductoPOPWSResult;
import DAL.ProductoPOPDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProductoPOPBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarProductosPOP() throws Exception
	{
		try
		{
			boolean bool = new ProductoPOPDAL().BorrarProductosPOP();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosPOP: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarProductoPOPPor(long id) throws Exception
	{
		try
		{
			boolean bool = new ProductoPOPDAL().BorrarProductoPOPPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosPOP por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosPOP por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarProductoPOP(ArrayList<ProductoPOPWSResult> productosPOP) throws Exception
	{
		try
	    {
			long l = new ProductoPOPDAL().InsertarProductoPOP(productosPOP);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<ProductoPOP> ObtenerProductosPOP() throws Exception
	{
		ArrayList<ProductoPOP> listadoProductoPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new ProductoPOPDAL().ObtenerProductosPOP();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosPOP: " + localException.getMessage());
			} 
			return listadoProductoPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoProductoPOP = new ArrayList<ProductoPOP>();
			
			do
			{
				ProductoPOP ProductoPOP = new ProductoPOP(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3),
						localCursor.getString(4));
				
				listadoProductoPOP.add(ProductoPOP);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoProductoPOP;
	}
	
	public ArrayList<ProductoPOP> ObtenerProductosPOPPorProveedorNoEnPreventaProductoPOP(int proveedorId,int categoriaId,
																						int preventaPOPId) throws Exception
	{
		Cursor localCursor;
		ArrayList<ProductoPOP> localListadoProductoPOP = new ArrayList<ProductoPOP>();
		localListadoProductoPOP.add(new ProductoPOP(0,"Seleccione un producto ...",0,""));

		try
		{
			localCursor = new ProductoPOPDAL().ObtenerProductosPorProveedorNoEnPreventaProductoPOP(proveedorId, categoriaId, preventaPOPId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId, categoriaId y preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId, categoriaId y preventaId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			do
			{
				ProductoPOP localProductoPOP = new ProductoPOP(localCursor.getInt(0),localCursor.getString(1), localCursor.getInt(2),
															localCursor.getString(3));

				localListadoProductoPOP.add(localProductoPOP);
			}
			while(localCursor.moveToNext());
		}

		return localListadoProductoPOP;
	}
	
	public ProductoPOP ObtenerProductoPOPPorProductoId(int productoId) throws Exception
	{
		Cursor localCursor;
		ProductoPOP localProductoPOP = new ProductoPOP();
		localProductoPOP = new ProductoPOP(0,"Seleccione un producto ...",0,"");

		try
		{
			localCursor = new ProductoPOPDAL().ObtenerProductoPOPPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoPOP por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoPOP por productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			localProductoPOP = new ProductoPOP(localCursor.getInt(1),localCursor.getString(2), localCursor.getInt(3),
													localCursor.getString(4));
		}

		return localProductoPOP;
	}
}
