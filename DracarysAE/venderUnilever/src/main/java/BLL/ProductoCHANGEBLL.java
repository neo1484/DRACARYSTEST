package BLL;

import java.util.ArrayList;

import Clases.ProductoCHANGE;
import DAL.ProductoCHANGEDAL;
import android.database.Cursor;

public class ProductoCHANGEBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarProductosCHANGE() throws Exception
	{
		try
		{
			boolean bool = new ProductoCHANGEDAL().BorrarProductosCHANGE();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productosCHANGE: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarProductoCHANGEPor(long id) throws Exception
	{
		try
		{
			boolean bool = new ProductoCHANGEDAL().BorrarProductoCHANGEPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosCHANGE por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productosCHANGE por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarProductoCHANGE(int productoId,String descripcion25,int categoriaId,String codigoBarra) throws Exception
	{
		try
	    {
			long l = new ProductoCHANGEDAL().InsertarProductoCHANGE(productoId, descripcion25, categoriaId, codigoBarra);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el productoCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<ProductoCHANGE> ObtenerProductosCHANGE() throws Exception
	{
		ArrayList<ProductoCHANGE> listadoProductoCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new ProductoCHANGEDAL().ObtenerProductosCHANGE();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productosCHANGE: " + localException.getMessage());
			} 
			return listadoProductoCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoProductoCHANGE = new ArrayList<ProductoCHANGE>();
			
			do
			{
				ProductoCHANGE ProductoCHANGE = new ProductoCHANGE(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3),
						localCursor.getString(4));
				
				listadoProductoCHANGE.add(ProductoCHANGE);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoProductoCHANGE;
	}
	
	public ArrayList<ProductoCHANGE> ObtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(int proveedorId,int categoriaId,
																						int preventaPOPId) throws Exception
	{
		Cursor localCursor;
		ArrayList<ProductoCHANGE> localListadoProductoCHANGE = new ArrayList<ProductoCHANGE>();
		localListadoProductoCHANGE.add(new ProductoCHANGE(0,"Seleccione un producto ...",0,""));

		try
		{
			localCursor = new ProductoCHANGEDAL().ObtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(proveedorId, categoriaId, preventaPOPId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos CHANGE clasificados por proveedorId, categoriaId y preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos CHANGE clasificados por proveedorId, categoriaId y preventaId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			do
			{
				ProductoCHANGE localProductoCHANGE = new ProductoCHANGE(localCursor.getInt(0),localCursor.getString(1), localCursor.getInt(2),
															localCursor.getString(3));

				localListadoProductoCHANGE.add(localProductoCHANGE);
			}
			while(localCursor.moveToNext());
		}

		return localListadoProductoCHANGE;
	}
	
	public ProductoCHANGE ObtenerProductoCHANGEPorProductoId(int productoId) throws Exception
	{
		Cursor localCursor;
		ProductoCHANGE localProductoCHANGE = new ProductoCHANGE();
		localProductoCHANGE = new ProductoCHANGE(0,"Seleccione un producto ...",0,"");

		try
		{
			localCursor = new ProductoCHANGEDAL().ObtenerProductoCHANGEPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGE por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGE por productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			localProductoCHANGE = new ProductoCHANGE(localCursor.getInt(1),localCursor.getString(2), localCursor.getInt(3),
													localCursor.getString(4));
		}

		return localProductoCHANGE;
	}
}