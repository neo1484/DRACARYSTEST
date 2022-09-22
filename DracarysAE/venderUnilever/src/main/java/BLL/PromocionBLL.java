package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Producto;
import Clases.Promocion;
import Clases.PromocionProducto;
import Clases.PromocionWSResult;
import DAL.PromocionDAL;
import DAL.PromocionProductoDAL;

public class PromocionBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPromociones() throws Exception
	{
		try
	    {
			boolean bool = new PromocionDAL().BorrarPromociones();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarPromocion(ArrayList<PromocionWSResult> promociones) throws Exception
	{
	    try
	    {
	    	long l = new PromocionDAL().InsertarPromocion(promociones);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las promociones: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las promociones: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public Promocion ObtenerPromocionPor(int promocionId) throws Exception
	{
		Cursor localCursor;
		Promocion localPromocion = null;
		try
	    {
			localCursor = new PromocionDAL().ObtenerPromocionPor(promocionId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
		
		if (localCursor.getCount() > 0) 
		{
			localPromocion = new Promocion(localCursor.getInt(1),localCursor.getString(2),
					localCursor.getString(3),localCursor.getString(4).equals("1")?true:false,
					localCursor.getInt(5));
		}
		
		return localPromocion;
	  }
	  
	public ArrayList<Promocion> ObtenerPromociones() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Promocion> listadoPromocion = null;
		  try
		  {
			  localCursor = new PromocionDAL().ObtenerPromociones();
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoPromocion = new ArrayList<Promocion>();
			  do
			  {
		          Promocion promocion = new Promocion(localCursor.getInt(1),localCursor.getString(2),
		        		  	localCursor.getString(3),localCursor.getString(4).equals("1")?true:false,
        		  			localCursor.getInt(5));
				  
		          listadoPromocion.add(promocion);
		      } 
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocion;
	  }

	public ArrayList<Promocion> ObtenerPromocionesNoEnPreventaProductoTemp(int clienteId,int empleadoId,int proveedorId,int precioListaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Promocion> listadoPromocion = new ArrayList<Promocion>();
		  listadoPromocion.add(new Promocion(0,"Seleccione una promocion ...","",true,0));
		  try
		  {
			  localCursor = new PromocionDAL().ObtenerPromocionesNoEnPreventaProductoTemp(clienteId,empleadoId,proveedorId,precioListaId);
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones clasificadas por clienteId y empleadoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones clasificadas por clienteId y empleadoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {    
			  do
			  {
		          Promocion promocion = new Promocion(localCursor.getInt(1),localCursor.getString(2),
		        		  localCursor.getString(3),localCursor.getString(4).equals("1")?true:false,
		        		  localCursor.getInt(5));
				  
		          listadoPromocion.add(promocion);
		      } 
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocion;
	  }
	
	public ArrayList<Promocion> ObtenerPromocionesNoEnAlmacenDevolucionProductoTemp(int clienteId,int empleadoId,int proveedorId,int precioListaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Promocion> listadoPromocion = new ArrayList<Promocion>();
		  listadoPromocion.add(new Promocion(0,"Seleccione una promocion ...","",true,0));
		  try
		  {
			  localCursor = new PromocionDAL().ObtenerPromocionesNoEnAlmacenDevolucionProductoTemp(clienteId,empleadoId,proveedorId,precioListaId);
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones del almacen devolcuion clasificadas por clienteId y empleadoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones del almacen devolcuion clasificadas por clienteId y empleadoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {    
			  do
			  {
		          Promocion promocion = new Promocion(localCursor.getInt(1),localCursor.getString(2),
		        		localCursor.getString(3),localCursor.getString(4).equals("1")?true:false,
		        		localCursor.getInt(5));
				  
		          listadoPromocion.add(promocion);
		      } 
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocion;
	  }
	
	public ArrayList<Promocion> ObtenerPromocionesNoEnVentaDirectaProductoTemp(int clienteId,int empleadoId,int proveedorId,int precioListaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Promocion> listadoPromocion = new ArrayList<Promocion>();
		  listadoPromocion.add(new Promocion(0,"Seleccione una promocion ...","",true,0));
		  try
		  {
			  localCursor = new PromocionDAL().ObtenerPromocionesNoEnVentaDirectaProductoTemp(clienteId,empleadoId,proveedorId,precioListaId);
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones de la venta directa clasificadas por clienteId y empleadoId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones de la venta directa clasificadas por clienteId y empleadoId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {    
			  do
			  {
		          Promocion promocion = new Promocion(localCursor.getInt(1),localCursor.getString(2),
		        		localCursor.getString(3),localCursor.getString(4).equals("1")?true:false,
		        		localCursor.getInt(5));
				  
		          listadoPromocion.add(promocion);
		      } 
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocion;
	  }
	
	public boolean ExistePromocionesConSaldoEnAlmacenProducto(int promocionId, int cantidad) throws Exception
	{
		ArrayList<PromocionProducto> listadoPromocionProducto;
		Producto localProducto;
		Cursor localCursor;
		
		localCursor = null;
		try
		{
			localCursor = new PromocionProductoDAL().ObtenerPromocionesProductoPor(promocionId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocionesProducto por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocionesProducto por promocionId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor == null)
		{
			return false;
		}
		
		listadoPromocionProducto = new ArrayList<PromocionProducto>();
	
		do
		{
			listadoPromocionProducto.add(new PromocionProducto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),
															localCursor.getInt(4),localCursor.getFloat(5),localCursor.getFloat(6),
															localCursor.getInt(7),localCursor.getInt(8),localCursor.getFloat(9),
															localCursor.getFloat(10)));
		}
		while(localCursor.moveToNext());
		
		
		for(PromocionProducto item : listadoPromocionProducto)
		{
			localProducto = null;
			try
			{
				localProducto = new ProductoBLL().ObtenerProductoPor(item.get_productoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los productos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los productos: " + localException.getMessage());
				} 
				throw localException;
			}
			
			if(localProducto == null)
			{
				return false;
			}
			
			localCursor = null;
			try
			{
				localCursor = new PromocionDAL().ObtenerPromocionesConSaldoEnAlmacenProducto(localProducto.get_conversion(),
												cantidad,localProducto.get_productoId(), promocionId);
			}
			catch (Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones con saldo en almacenProducto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones con saldo en almacenProducto: " + localException.getMessage());
				} 
				throw localException;
			}
			
			if(localCursor==null || localCursor.getCount()<=0)
			{
				return false;
			}
		}
				  
		return true;
	  }
	
	public boolean ExistePromocionesConSaldoEnDevolucionDistribuidorProducto(int promocionId, int cantidad) throws Exception
	{
		ArrayList<PromocionProducto> listadoPromocionProducto;
		Producto localProducto;
		Cursor localCursor;
		
		localCursor = null;
		try
		{
			localCursor = new PromocionProductoDAL().ObtenerPromocionesProductoPor(promocionId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocionesProducto por promocionId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocionesProducto por promocionId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor == null)
		{
			return false;
		}
		
		listadoPromocionProducto = new ArrayList<PromocionProducto>();
	
		do
		{
			listadoPromocionProducto.add(new PromocionProducto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),
															localCursor.getInt(4),localCursor.getFloat(5),localCursor.getFloat(6),
															localCursor.getInt(7),localCursor.getInt(8),localCursor.getFloat(9),
															localCursor.getFloat(10)));
		}
		while(localCursor.moveToNext());
		
		
		for(PromocionProducto item : listadoPromocionProducto)
		{
			localProducto = null;
			try
			{
				localProducto = new ProductoBLL().ObtenerProductoPor(item.get_productoId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los productos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de los productos: " + localException.getMessage());
				} 
				throw localException;
			}
			
			if(localProducto == null)
			{
				return false;
			}
			
			localCursor = null;
			try
			{
				localCursor = new PromocionDAL().ObtenerPromocionesConSaldoEnDevolucionDistribuidorProducto(localProducto.get_conversion(),
												cantidad,localProducto.get_productoId(), promocionId);
			}
			catch (Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones con saldo en almacenProducto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones con saldo en almacenProducto: " + localException.getMessage());
				} 
				throw localException;
			}
			
			if(localCursor.getCount()<=0 || localCursor==null)
			{
				return false;
			}
		}
				  
		return true;
	  }
	
	public ArrayList<Promocion> ObtenerPromocionesNoEnPreventaProducto(int preventaId,int proveedorId,int precioListaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Promocion> listadoPromocion = new ArrayList<Promocion>();
		  listadoPromocion.add(new Promocion(0,"Seleccione una promocion ...","",true,0));
		  try
		  {
			  localCursor = new PromocionDAL().ObtenerPromocionesNoEnPreventaProducto(preventaId, proveedorId, precioListaId);
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones clasificadas por preventaId y proveedorId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promociones clasificadas por preventaId y proveedorId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {    
			  do
			  {
		          Promocion promocion = new Promocion(localCursor.getInt(1),localCursor.getString(2),
		        		  localCursor.getString(3),localCursor.getString(4).equals("1")?true:false,
		        		  localCursor.getInt(5));
				  
		          listadoPromocion.add(promocion);
		      } 
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocion;
	  }
}
