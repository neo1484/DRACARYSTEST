package BLL;

import Clases.DatosFactura;
import Clases.DatosFacturaWSResult;
import DAL.DatosFacturaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class DatosFacturaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarDatosFactura() throws Exception
	{
		try
	    {
			boolean bool = new DatosFacturaDAL().BorrarDatosFactura();
			return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datos de la factura: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datos de la factura: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public long InsertarDatosFactura(DatosFacturaWSResult datosFactura) throws Exception
	{
	    try
	    {
	    	long l = new DatosFacturaDAL().InsertarDatosFactura(datosFactura);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos de la factura: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos de la factura: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	    
	public DatosFactura ObtenerDatosFactura() throws Exception
	  {
		  Cursor localCursor;
		  DatosFactura localDatosFactura = null;
		  try
		  {
			  localCursor = new DatosFacturaDAL().ObtenerDatosFactura();
	      }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos de la factura: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos de la factura: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  
		  if(localCursor.getCount()>0)
		  {
			  localDatosFactura = new DatosFactura(localCursor.getString(1),localCursor.getString(2));
		  }
		  
		  return localDatosFactura;
	  }
}
