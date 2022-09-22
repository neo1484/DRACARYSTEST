package BLL;

import java.util.ArrayList;

import Clases.AlmacenCHANGEProducto;
import DAL.AlmacenCHANGEProductoDAL;
import android.database.Cursor;

public class AlmacenCHANGEProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarAlmacenesCHANGEProducto() throws Exception
	{
		try
		{
			boolean bool = new AlmacenCHANGEProductoDAL().BorrarAlmacenesCHANGEProducto();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesCHANGEProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesCHANGEProducto: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarAlmacenCHANGEProducto(int almacenId,int productoId,int saldo) throws Exception
	{
		try
	    {
			long l = new AlmacenCHANGEProductoDAL().InsertarAlmacenCHANGEProducto(almacenId,productoId,saldo);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenCHANGEProducto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenCHANGEProducto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<AlmacenCHANGEProducto> ObtenerAlmacenesCHANGEProducto() throws Exception
	{
		ArrayList<AlmacenCHANGEProducto> listadoAlmacenCHANGEProducto = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new AlmacenCHANGEProductoDAL().ObtenerAlmacenesCHANGEProducto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesCHANGEProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesCHANGEProducto: " + localException.getMessage());
			} 
			return listadoAlmacenCHANGEProducto;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoAlmacenCHANGEProducto = new ArrayList<AlmacenCHANGEProducto>();
			
			do
			{
				AlmacenCHANGEProducto almacenCHANGEProducto = new AlmacenCHANGEProducto(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3));
				
				listadoAlmacenCHANGEProducto.add(almacenCHANGEProducto);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoAlmacenCHANGEProducto;
	}
	
	public AlmacenCHANGEProducto ObtenerAlmacenCHANGEProductoPorProductoId(int productoId)throws Exception
	  {
		  Cursor localCursor=null;
		  AlmacenCHANGEProducto localAlmacenCHANGEProducto =null;
		  try
		  {
			  localCursor = new AlmacenCHANGEProductoDAL().ObtenerAlmacenCHANGEProductoPorProductoId(productoId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGE del almacenCHANGE, por productoId: vacio");
			  }
			  else
			  {
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoCHANGE del almacenCHANGE, por productoId: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  localAlmacenCHANGEProducto = new AlmacenCHANGEProducto(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3));
		  }
	      
		  return localAlmacenCHANGEProducto;
	  }
	
	public AlmacenCHANGEProducto ValidarExistenciasAlmacenCHANGEDispositivo(int productoId,int cantidad) throws Exception
	{		  
		  Cursor localCursor;
		  AlmacenCHANGEProducto almacenCHANGEProducto =null;
		  try
		  {
			  localCursor = new AlmacenCHANGEProductoDAL().ValidarExistenciasAlmacenCHANGEDispositivo(productoId,cantidad);
		  }
		  catch(Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenCHANGEProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenCHANGEProducto: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  almacenCHANGEProducto = new AlmacenCHANGEProducto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
					  											localCursor.getInt(3));
		  }
		  
		  return almacenCHANGEProducto;
	  }
}
