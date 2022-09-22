package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Producto;
import Clases.ProductoWSResult;
import DAL.ProductoDAL;

public class ProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarProductos() throws Exception
	{
		try
	    {
			boolean bool = new ProductoDAL().BorrarProductos();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarProducto(ArrayList<ProductoWSResult> productos) throws Exception
	{
	    try
	    {
	    	long l = new ProductoDAL().InsertarProducto(productos);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }
	  
	public int ObtenerConversionPor(int productoId) throws Exception
	  {
		  try
		  {
			  Cursor localCursor = new ProductoDAL().getProductoConversion(productoId);
			  return localCursor.getInt(5);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la conversion del producto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener la conversion del producto: " + localException.getMessage());
				} 
			  throw localException;
		  }
	  }
	  
	public Producto ObtenerProductoPor(int productoId) throws Exception
	  {
		  Cursor localCursor;
		  Producto localProducto =null;
		  try
		  {
			  localCursor = new ProductoDAL().ObtenerProductoPor(productoId);
		  }
		  catch (Exception localException)
		  {
			  	if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener detalle producto por productoId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener detalle producto por productoId: " + localException.getMessage());
				} 
			    throw localException;
		  }
		  
		  if (localCursor.getCount() > 0) 
		  {
			  localProducto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
					  									localCursor.getInt(4), localCursor.getInt(5),localCursor.getInt(6),
					  									localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,
					  									localCursor.getInt(9));
		  }
	      
		  return localProducto;
	  }
	
	public ArrayList<Producto> ObtenerProductoPorProveedorNoEnPreventaProductoTemp(int proveedorId,int categoriaId,
																				int empleadoId,int clienteId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Producto> localListadoProducto = new ArrayList<Producto>();
		  localListadoProducto.add(new Producto(0,"","Seleccione un producto ...",0,0,0,"",false,0));
		  
		  try
		  {
			  localCursor = new ProductoDAL().ObtenerProductoPorProveedorNoEnPreventaProductoTemp(proveedorId,categoriaId,
					  																				empleadoId, clienteId);
		  }
		  catch (Exception localException)
		  {
			  	if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId, empleadoId y clienteId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados por proveedorId, empleadoId y clienteId: " + localException.getMessage());
				} 
			    throw localException;
		  }
		  
		  if (localCursor.getCount() > 0) 
		  {
			  do
			  {
				  Producto localProducto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
														localCursor.getInt(4), localCursor.getInt(5),localCursor.getInt(6),
														localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));
				  
				  localListadoProducto.add(localProducto);
			  }
			  while(localCursor.moveToNext());
		  }
	      
		  return localListadoProducto;
	  }
	
		public ArrayList<Producto> ObtenerProductoPorProveedorNoEnVentaDirectaProductoTemp(int proveedorId,int categoriaId,
													int empleadoId,int clienteId) throws Exception
		{
			Cursor localCursor;
			ArrayList<Producto> localListadoProducto = new ArrayList<Producto>();
			localListadoProducto.add(new Producto(0,"","Seleccione un producto ...",0,0,0,"",false,0));

			try
			{
				localCursor = new ProductoDAL().ObtenerProductoPorProveedorNoEnVentaDirectaProductoTemp(proveedorId, categoriaId, 
																									empleadoId, clienteId);
			}
			catch (Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados de venta directa por proveedorId, empleadoId y clienteId: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados de venta directa por proveedorId, empleadoId y clienteId: " + localException.getMessage());
				} 
				throw localException;
			}

			if (localCursor.getCount() > 0) 
			{
				do
				{
					Producto localProducto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
							localCursor.getInt(4), localCursor.getInt(5),localCursor.getInt(6),localCursor.getString(7),
							localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));

					localListadoProducto.add(localProducto);
				}
				while(localCursor.moveToNext());
			}

			return localListadoProducto;
		}

	public ArrayList<Producto> ObtenerProductoPorProveedorNoEnVentaProductoTemp(int proveedorId,int categoriaId,
																	int empleadoId,int clienteId) throws Exception
	{
		Cursor localCursor;
		ArrayList<Producto> localListadoProducto = new ArrayList<Producto>();
		localListadoProducto.add(new Producto(0,"","Seleccione un producto ...",0,0,0,"",false,0));

		try
		{
			localCursor = new ProductoDAL().ObtenerProductoPorProveedorNoEnPreventaProductoTemp(proveedorId,categoriaId,
									empleadoId, clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados de preventa por proveedorId, empleadoId y clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados de preventa por proveedorId, empleadoId y clienteId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			do
			{
				Producto localProducto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
						localCursor.getInt(4), localCursor.getInt(5),localCursor.getInt(6),localCursor.getString(7),
						localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));

				localListadoProducto.add(localProducto);
			}
			while(localCursor.moveToNext());
		}

		return localListadoProducto;
	}
	
	public ArrayList<Producto> ObtenerProductoPorProveedorNoEnAlmacenDevolucionProductoTemp(int proveedorId,int categoriaId,
			int empleadoId,int clienteId) throws Exception
	{
		Cursor localCursor;
		ArrayList<Producto> localListadoProducto = new ArrayList<Producto>();
		localListadoProducto.add(new Producto(0,"","Seleccione un producto ...",0,0,0,"",false,0));

		try
		{
			localCursor = new ProductoDAL().ObtenerProductoPorProveedorNoEnAlmacenDevolucionProductoTemp(proveedorId,categoriaId,
											empleadoId, clienteId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados de autoventa por proveedorId, empleadoId y clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos clasificados de autoventa por proveedorId, empleadoId y clienteId: " + localException.getMessage());
			} 
			throw localException;
		}

		if (localCursor.getCount() > 0) 
		{
			do
			{
				Producto localProducto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
														localCursor.getInt(4), localCursor.getInt(5),localCursor.getInt(6),
														localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));

				localListadoProducto.add(localProducto);
			}
			while(localCursor.moveToNext());
		}

		return localListadoProducto;
	}
	  
	public ArrayList<Producto> ObtenerProductos() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Producto> listadoProducto =null;
		  try
		  {
			  localCursor = new ProductoDAL().ObtenerProductos();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoProducto = new ArrayList<Producto>();
			  do
			  {
				  Producto producto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3), 
						  							localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6),
						  							localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));
				  
				  listadoProducto.add(producto);
			  }
			  while(localCursor.moveToNext());
		  }
	    	  
	      return listadoProducto;
	  }
	  
	public ArrayList<Producto> ObtenerProdutosPor(int proveedorId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Producto> listadoProducto = null;
		  
		  try
		  {
			  localCursor = new ProductoDAL().ObtenerProductosPorProveedor(proveedorId);
		  }
	      catch (Exception localException)
		  {
	    	  if(localException.getMessage().isEmpty())
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos por proveedor: vacio");
	    	  }
	    	  else
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los productos por proveedor: " + localException.getMessage());
	    	  }
	    	  throw localException;
		  } 
	    
		  if (localCursor.getCount() > 0) 
		  {
			  listadoProducto = new ArrayList<Producto>();
		      
			  do
		      {
				  Producto producto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3), 
						  							localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6),
						  							localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));
				  
				  listadoProducto.add(producto);  
		      } 
			  while (localCursor.moveToNext());
		  }
		  
		  return listadoProducto;
	  }
	
	public ArrayList<Producto> ObtenerProductoPorProveedorNoEnPreventaProducto(int proveedorId,int categoriaId,
																				int preventaId) throws Exception
																				{
		Cursor localCursor;
		ArrayList<Producto> localListadoProducto = new ArrayList<Producto>();
		localListadoProducto.add(new Producto(0,"","Seleccione un producto ...",0,0,0,"",false,0));

		try
		{
			localCursor = new ProductoDAL().ObtenerProductoPorProveedorNoEnPreventaProducto(proveedorId,categoriaId,
																							preventaId);
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
				Producto localProducto = new Producto(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
													localCursor.getInt(4), localCursor.getInt(5),localCursor.getInt(6),
													localCursor.getString(7),localCursor.getString(8).equals("1")?true:false,localCursor.getInt(9));

				localListadoProducto.add(localProducto);
			}
			while(localCursor.moveToNext());
		}

		return localListadoProducto;
	}
}
