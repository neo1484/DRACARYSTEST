package BLL;

import java.util.ArrayList;

import Clases.VendedorZonaVenta;
import Clases.VendedorZonaVentaWSResult;
import DAL.VendedorZonaVentaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class VendedorZonaVentaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVendedoresZonaVenta()throws Exception
	{
		try
		{
			boolean bool = new VendedorZonaVentaDAL().BorrarVendedoresZonaVenta();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los vendedoresZonaVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los vendedoresZonaVenta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVendedorZonaVenta(ArrayList<VendedorZonaVentaWSResult> vendedoresZonaVenta) throws Exception
	{
		try
		{
			long l = new VendedorZonaVentaDAL().InsertarVendedorZonaVenta(vendedoresZonaVenta);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las vendedorZonaVentas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las vendedorZonaVentas: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public VendedorZonaVenta ObtenerVendedorZonaVentaPor(int rutaId) throws Exception
	{
		VendedorZonaVenta localVendedorZonaVenta = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new VendedorZonaVentaDAL().ObtenerVendedorZonaVentaPor(rutaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el vendedorZonaVenta por rutaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el vendedorZonaVenta por rutaId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localVendedorZonaVenta = new VendedorZonaVenta(localCursor.getInt(1),localCursor.getString(2));
	    }
	    
		return localVendedorZonaVenta;
    }
		  
	public ArrayList<VendedorZonaVenta> ObtenerVendedoresZonaVenta() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<VendedorZonaVenta> listadoVendedorZonaVenta = null;
		try
		{
			localCursor = new VendedorZonaVentaDAL().ObtenerVendedoresZonaVenta();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los vendedoresZonaVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los vendedoresZonaVenta: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVendedorZonaVenta = new ArrayList<VendedorZonaVenta>();
			do
			{
				VendedorZonaVenta localVendedorZonaVenta = new VendedorZonaVenta(localCursor.getInt(1),localCursor.getString(2));
				
				listadoVendedorZonaVenta.add(localVendedorZonaVenta);
	        }
			while (localCursor.moveToNext());
		}

        return listadoVendedorZonaVenta;
	}
}
