package BLL;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import android.database.Cursor;
import Clases.AlmacenProducto;
import Clases.AlmacenProductoWSResult;
import DAL.AlmacenProductoDAL;

public class AlmacenProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAlmacenProductos()throws Exception
	{
	    try
	    {
	    	boolean bool = new AlmacenProductoDAL().BorrarAlmacenProductos();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesProducto: " + localException.getMessage());
	    	} 
	        throw localException;
	    }
	  }

	public long InsertarAlmacenProducto(ArrayList<AlmacenProductoWSResult> almacenProductos) throws Exception
	{
		try
		{
			long l = new AlmacenProductoDAL().InsertarAlmacenProducto(almacenProductos);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenProducto: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public long InsertarAlmacenProducto(int almacenId, int productoId, int saldoUnitario, int saldoPaquete,
										float costoUnitario, float costoPAquete) throws Exception
	{
		try
		{
			long l = new AlmacenProductoDAL().InsertarAlmacenProducto(almacenId,productoId,saldoUnitario,
					saldoPaquete,costoUnitario,costoPAquete);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenProducto: " + localException.getMessage());
			}
			throw localException;
		}
	}
	  
	  public ArrayList<AlmacenProducto> ConsolidarProductosLote()throws Exception
	  {
		  Cursor localCursor=null;
		  ArrayList<AlmacenProducto> listAlmacenProducto =null;
		  try
		  {
			  localCursor = new AlmacenProductoDAL().ConsolidarProductosLote();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
			        myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos lote: vacio");
			  }
			  else
			  {
					myLog.InsertarLog("App",this.toString(),1,"Error al consolidar los productos lote: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listAlmacenProducto = new ArrayList<AlmacenProducto>();
			  do
			  {
				  AlmacenProducto localAlmacenProducto = new AlmacenProducto(localCursor.getInt(0), localCursor.getInt(1),localCursor.getInt(2),
							localCursor.getInt(3), localCursor.getFloat(4), localCursor.getFloat(5));
				  listAlmacenProducto.add(localAlmacenProducto);
			  }
			  while(localCursor.moveToNext());
		  }
	      
		  return listAlmacenProducto;
	  }
	  
	  public int deleteProductosLote() throws Exception
		{
			Cursor localCursor;
			
			try
			{
				localCursor = new AlmacenProductoDAL().deleteProductosLote();
			}
			catch(Exception localException)
			{
				if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: " + localException.getMessage());
				}
				throw localException;
			}
			
			return 1;
		}
	  
	  public AlmacenProducto ObtenerAlmacenProductoPor(int almacenId, int productoId)throws Exception
	  {
		  Cursor localCursor=null;
		  AlmacenProducto localAlmacenProducto =null;
		  try
		  {
			  localCursor = new AlmacenProductoDAL().ObtenerAlmacenProductoPor(almacenId,productoId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacen, por almacenId y productoId: vacio");
			  }
			  else
			  {
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el producto del almacen, por almacenId y productoId: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  localAlmacenProducto = new AlmacenProducto(localCursor.getInt(1), localCursor.getInt(2),localCursor.getInt(3),
	    														localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6));
		  }
	      
		  return localAlmacenProducto;
	  }
	  
	  public AlmacenProducto ObtenerAlmacenProductosPor(int almacenId) throws Exception
	  {
		  Cursor localCursor;
		  AlmacenProducto  almacenProducto = null;
		  try
		  {
			  localCursor = new AlmacenProductoDAL().ObtenerAlmacenProductosPor(almacenId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacenProducto por almacenId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el almacenProducto por almacenId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
	    
		  if (localCursor.getCount() > 0) 
		  {
		        almacenProducto = new AlmacenProducto(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
		        								localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6));
		  }
		  
		  return almacenProducto;  
	  }
	  
	  public ArrayList<AlmacenProducto> ObtenerAlmacenesProducto()throws Exception
	  {
		  Cursor localCursor = null;
		  ArrayList<AlmacenProducto> listadoAlmacenProducto = null;
		  try
		  {
		  		localCursor = new AlmacenProductoDAL().ObtenerAlmacenesProducto();
	      
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesProducto: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		
		  if (localCursor.getCount() > 0)
	      {
			  listadoAlmacenProducto = new ArrayList<AlmacenProducto>();
			  do
			  {
				  AlmacenProducto almacenProducto = new AlmacenProducto(localCursor.getInt(1), localCursor.getInt(2), 
						  localCursor.getInt(3), localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6));
				  
				  listadoAlmacenProducto.add(almacenProducto);
			  } 
			  while (localCursor.moveToNext());
	      }
	      
	      return listadoAlmacenProducto;
	  }
	  
	  public AlmacenProducto ObtenerExistenciaProductoAlmacenProducto(int almacenId, int productoId,int conversionProducto, int cantidadSolicitadaEnUnidades) throws Exception
	  {		  
		  Cursor localCursor;
		  AlmacenProducto almacenProducto =null;
		  try
		  {
			  localCursor = new AlmacenProductoDAL().ObtenerExistenciaProductoAlmacenProducto(almacenId, productoId, conversionProducto, cantidadSolicitadaEnUnidades);
		  }
		  catch(Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenProducto: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  
		  if(localCursor.getCount()>0)
		  {
			  almacenProducto = new AlmacenProducto(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
					  											localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6));
		  }
		  
		  return almacenProducto;
  	  }
	  
	  public int ObtenerExistenciaProductoEnAlmacenPor(int almacenId, int productoId,int conversionProducto) throws Exception
	  {		  
		  Cursor localCursor;
		  AlmacenProducto almacenProducto =null;
		  int saldoEnUnidades = 0;
		  try
		  {
			  localCursor = new AlmacenProductoDAL().ObtenerExistenciaProductoEnAlmacenPor(almacenId, productoId);
		  }
		  catch(Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto en el almacenProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto en el almacenProducto: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  
		  if(localCursor.getCount()>0)
		  {
			  almacenProducto = new AlmacenProducto(localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
		  											localCursor.getInt(4), localCursor.getFloat(5), localCursor.getFloat(6));
		  }
		  
		  saldoEnUnidades = almacenProducto.get_saldoPaquete() * conversionProducto + almacenProducto.get_saldoUnitario(); 
		  return saldoEnUnidades;
  	  }

	  public ArrayList<AlmacenProducto> ObtenerInventarioAlmacenProductoPor(int proveedorId,int categoriaId) throws Exception
	  {
		  ArrayList<AlmacenProducto> listadoAlmacenProducto = null;
		  Cursor localCursor=null;
		  try
		  {
			  localCursor = new AlmacenProductoDAL().ObtenerInventarioAlmacenProductoPor(proveedorId, categoriaId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage() == null || localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario almacenProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el inventario almacenProducto: " + localException.getMessage());
			  } 
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoAlmacenProducto = new ArrayList<AlmacenProducto>();
				
			  do
			  {				  
				  AlmacenProducto localAlmacenProducto = new AlmacenProducto(localCursor.getInt(1),localCursor.getInt(2), 
						  								localCursor.getInt(3), localCursor.getInt(4),localCursor.getFloat(5),
						  								localCursor.getFloat(6));
					
				  listadoAlmacenProducto.add(localAlmacenProducto);
			  } 
			  while (localCursor.moveToNext());
		  }
		  return listadoAlmacenProducto;
	  }
}
