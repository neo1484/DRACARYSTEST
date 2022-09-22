package BLL;

import java.util.ArrayList;
import Clases.AlmacenPOPProducto;
import Clases.AlmacenPOPProductoWSResult;
import DAL.AlmacenPOPProductoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AlmacenPOPProductoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarAlmacenesPOPProducto() throws Exception
	{
		try
		{
			boolean bool = new AlmacenPOPProductoDAL().BorrarAlmacenesPOPProducto();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOPProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOPProducto: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarAlmacenPOPProducto(ArrayList<AlmacenPOPProductoWSResult> almacenesPopProducto) throws Exception
	{
		try
	    {
			long l = new AlmacenPOPProductoDAL().InsertarAlmacenPOPProducto(almacenesPopProducto);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP producto: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP producto: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<AlmacenPOPProducto> ObtenerAlmacenesPOPProducto() throws Exception
	{
		ArrayList<AlmacenPOPProducto> listadoAlmacenPOPProducto = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new AlmacenPOPProductoDAL().ObtenerAlmacenesPOPProducto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOPProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOPProducto: " + localException.getMessage());
			} 
			return listadoAlmacenPOPProducto;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoAlmacenPOPProducto = new ArrayList<AlmacenPOPProducto>();
			
			do
			{
				AlmacenPOPProducto almacenPOPProducto = new AlmacenPOPProducto(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3));
				
				listadoAlmacenPOPProducto.add(almacenPOPProducto);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoAlmacenPOPProducto;
	}
	
	public AlmacenPOPProducto ObtenerAlmacenPOPProductoPorProductoId(int productoId)throws Exception
	  {
		  Cursor localCursor=null;
		  AlmacenPOPProducto localAlmacenPOPProducto =null;
		  try
		  {
			  localCursor = new AlmacenPOPProductoDAL().ObtenerAlmacenPOPProductoPorProductoId(productoId);
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoPOP del almacenPOP, por productoId: vacio");
			  }
			  else
			  {
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el productoPOP del almacenPOP, por productoId: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  localAlmacenPOPProducto = new AlmacenPOPProducto(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3));
		  }
	      
		  return localAlmacenPOPProducto;
	  }
	
	public AlmacenPOPProducto ValidarExistenciasAlmacenPOPDispositivo(int productoId,int cantidad) throws Exception
	{		  
		  Cursor localCursor;
		  AlmacenPOPProducto almacenPOPProducto =null;
		  try
		  {
			  localCursor = new AlmacenPOPProductoDAL().ValidarExistenciasAlmacenPOPDispositivo(productoId,cantidad);
		  }
		  catch(Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenPOPProducto: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la existencia del producto, en el almacenPOPProducto: " + localException.getMessage());
			  }
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  almacenPOPProducto = new AlmacenPOPProducto(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
					  											localCursor.getInt(3));
		  }
		  
		  return almacenPOPProducto;
	  }
}
