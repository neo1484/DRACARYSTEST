package BLL;

import java.util.ArrayList;
import Clases.ProductoCambio;
import Clases.ProductoCambioWSResult;
import DAL.ProductoCambioDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ProductoCambioBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarProductosCambios() throws Exception
	{
		try
		{
			boolean bool = new ProductoCambioDAL().BorrarProductosCambio();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos cambio: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
	
	public boolean BorrarProductoCambioPor(long id) throws Exception
	{
		try
		{
			boolean bool = new ProductoCambioDAL().BorrarProductoCambioPor(id);
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productos cambio por id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el productos cambio por id: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarProductoCambio(ArrayList<ProductoCambioWSResult> productosCambio) throws Exception
	{
		try
	    {
			long l = new ProductoCambioDAL().InsertarProductoCambio(productosCambio);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto cambio: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el producto cambio: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}

	public ArrayList<ProductoCambio> ObtenerProductosCambio() throws Exception
	{
		ArrayList<ProductoCambio> listadoProductoCambio = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new ProductoCambioDAL().ObtenerProductosCambio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio: " + localException.getMessage());
			} 
			return listadoProductoCambio;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoProductoCambio = new ArrayList<ProductoCambio>();
			
			do
			{
				ProductoCambio ProductoCambio = new ProductoCambio(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3),
						localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),localCursor.getFloat(7),localCursor.getInt(8));
				
				listadoProductoCambio.add(ProductoCambio);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoProductoCambio;
	}
	
	public ArrayList<ProductoCambio> ObtenerProductosCambiosPorProvedorIdNoEnPreventaProductoCambioPor(int proveedorId,int categoriaId,int clienteId) throws Exception
	{
		Cursor localCursor;
		ArrayList<ProductoCambio> localListadoProductoCambio = new ArrayList<ProductoCambio>();
		localListadoProductoCambio.add(new ProductoCambio(0,"Seleccione un producto ...",0,0,0,0,0,0));

		try
		{
			localCursor = new ProductoCambioDAL().ObtenerProductosCambioPorProveedorIdNoEnPreventaProductoCambioPor(proveedorId,categoriaId,clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio por productoId y clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos cambio por productoId u clienteId " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			do
			{
				ProductoCambio localProductoCambio = new ProductoCambio(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3),
						localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),localCursor.getFloat(7),localCursor.getInt(8));

				localListadoProductoCambio.add(localProductoCambio);
			}
			while(localCursor.moveToNext());
		}

		return localListadoProductoCambio;
	}
	
	public ProductoCambio ObtenerProductoCambioPorProductoId(int productoId) throws Exception
	{
		Cursor localCursor;
		ProductoCambio localProductoCambio = new ProductoCambio();
		localProductoCambio = new ProductoCambio(0,"Seleccione un producto ...",0,0,0,0,0,0);

		try
		{
			localCursor = new ProductoCambioDAL().ObtenerProductoCambioPor(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto cambio por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto cambio por productoId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			localProductoCambio = new ProductoCambio(localCursor.getInt(1),localCursor.getString(2),localCursor.getInt(3),
												localCursor.getInt(4),localCursor.getInt(5),localCursor.getInt(6),
												localCursor.getFloat(7),localCursor.getInt(8));
		}

		return localProductoCambio;
	}

}
