package BLL;

import java.util.ArrayList;
import Clases.Impresora;
import Clases.ImpresoraWSResult;
import DAL.ImpresoraDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ImpresoraBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarImpresoras() throws Exception
	{
		try
	    {
			boolean bool = new ImpresoraDAL().BorrarImpresoras();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las impresoras: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las impresoras: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarImpresora(ArrayList<ImpresoraWSResult> impresoras) throws Exception
	{
	    try
	    {
	    	long l = new ImpresoraDAL().InsertarImpresora(impresoras);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las impresoras: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las impresoras: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	    
	public ArrayList<Impresora> ObtenerImpresoras() throws Exception
	  {
		  Cursor localCursor;
		  ArrayList<Impresora> listadoImpresora = null;
		  try
		  {
			  localCursor = new ImpresoraDAL().ObtenerImpresoras();
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las impresoras: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener las impresoras: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  listadoImpresora = new ArrayList<Impresora>();
			  do
			  {
				  Impresora impresora = new Impresora(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3),
						  							localCursor.getString(4),localCursor.getString(5),localCursor.getString(6));
				  
				  listadoImpresora.add(impresora);
			  }
			  while(localCursor.moveToNext());
		  }
		  
		  return listadoImpresora;
	  }

		public Impresora ObtenerImpresoraPor(int impresoraId) throws Exception
	  {
		  Cursor localCursor;
		  Impresora localImpresora = null;
		  try
		  {
			  localCursor = new ImpresoraDAL().ObtenerImpresorasPor(impresoraId);
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la impresora por impresoraId: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener la impresora por impresoraId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  localImpresora = new Impresora(localCursor.getInt(1),localCursor.getString(2),localCursor.getString(3),
						localCursor.getString(4),localCursor.getString(5),localCursor.getString(6));
		  }
		  
		  return localImpresora;
	  }
}
