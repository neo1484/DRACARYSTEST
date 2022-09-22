package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.PromocionProducto;
import Clases.PromocionProductoWSResult;
import DAL.PromocionProductoDAL;

public class PromocionProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPromocionesProducto() throws Exception
	{
	    try
	    {
	    	boolean bool = new PromocionProductoDAL().BorrarPromocionesProducto();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarPromocionProducto(ArrayList<PromocionProductoWSResult> promocionesProducto) throws Exception
	{
	    try
	    {
	    	long l = new PromocionProductoDAL().InsertarPromocionProducto(promocionesProducto);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la promocion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los productos de la promocion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public PromocionProducto ObtenerPromocionProductoPor(int id) throws Exception
	{
		Cursor localCursor;
		PromocionProducto promocionProducto = null;
	    try
	    {
	    	localCursor = new PromocionProductoDAL().ObtenerPromocionProductoPor(id);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion producto por Id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la promocion producto por id: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    
	    if (localCursor.getCount() > 0) 
	    {
    		promocionProducto  = new PromocionProducto(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3), 
    						localCursor.getInt(4),localCursor.getFloat(5),localCursor.getFloat(6),localCursor.getInt(7),
    						localCursor.getInt(8),localCursor.getFloat(9),localCursor.getFloat(10));
    	}
    	return promocionProducto;
	  }
	  
	public ArrayList<PromocionProducto> ObtenerPromocionesProducto() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PromocionProducto> listadoPromocionProducto = null;
		  
		  try
		  {
			  localCursor = new PromocionProductoDAL().ObtenerPromocionesProducto();
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocione producto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las promocione producto: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoPromocionProducto = new ArrayList<PromocionProducto>();
			  do
			  {
				  PromocionProducto promocionProducto = new PromocionProducto(localCursor.getInt(1),localCursor.getInt(2), 
						  								localCursor.getInt(3),localCursor.getInt(4),localCursor.getFloat(5),
						  								localCursor.getFloat(6),localCursor.getInt(7),localCursor.getInt(8),
						  								localCursor.getFloat(9),localCursor.getFloat(10));
				  
				  listadoPromocionProducto.add(promocionProducto);
			  }
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoPromocionProducto;	    
	  }
	  
	public ArrayList<PromocionProducto> ObtenerPromocionesProductoPor(int promocionId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PromocionProducto> listadoPromocionProducto = null;
		  try
		  {
			  localCursor = new PromocionProductoDAL().ObtenerPromocionesProductoPor(promocionId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de productosPromocion: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de productosPromocion: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoPromocionProducto = new ArrayList<PromocionProducto>();
			  do
			  {
		          PromocionProducto promocionProducto = new PromocionProducto(localCursor.getInt(1),localCursor.getInt(2), 
		        		  								localCursor.getInt(3),localCursor.getInt(4),localCursor.getFloat(5),
		        		  								localCursor.getFloat(6),localCursor.getInt(7),localCursor.getInt(8),
		        		  								localCursor.getFloat(9),localCursor.getFloat(10));
		          
		          listadoPromocionProducto.add(promocionProducto);
			  } 
			  while (localCursor.moveToNext());
		  }
		  
		  return listadoPromocionProducto;
	  }
	
	public ArrayList<PromocionProducto> ObtenerPromocionesProductoPorPromocionIdPrecioListaId(int promocionId,int precioListaId) throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<PromocionProducto> listadoPromocionProducto = null;
		  try
		  {
			  localCursor = new PromocionProductoDAL().ObtenerPromocionesProductoPorPromocionIdPrecioListaId(promocionId, precioListaId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de productosPromocion por pormocionId y precioListaId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de productosPromocion por pormocionId y precioListaId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoPromocionProducto = new ArrayList<PromocionProducto>();
			  do
			  {
		          PromocionProducto promocionProducto = new PromocionProducto(localCursor.getInt(1),localCursor.getInt(2), 
		        		  								localCursor.getInt(3),localCursor.getInt(4),localCursor.getFloat(5),
		        		  								localCursor.getFloat(6),localCursor.getInt(7),localCursor.getInt(8),
		        		  								localCursor.getFloat(9),localCursor.getFloat(10));
		          
		          listadoPromocionProducto.add(promocionProducto);
			  } 
			  while (localCursor.moveToNext());
		  }
		  
		  return listadoPromocionProducto;
	  }
}
