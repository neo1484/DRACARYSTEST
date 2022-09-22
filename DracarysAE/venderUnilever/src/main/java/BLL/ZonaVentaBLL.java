package BLL;

import java.util.ArrayList;

import Clases.ZonaVenta;
import Clases.ZonaVentaWSResult;
import DAL.ZonaVentaDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ZonaVentaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarZonasVenta() throws Exception
	{
		try
		{
			boolean bool = new ZonaVentaDAL().BorrarZonasVenta();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonasVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonasVenta: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarZonaVenta(ArrayList<ZonaVentaWSResult> zonasVenta) throws Exception
	{
		try
		{
			long l = new ZonaVentaDAL().InsertarZonaVenta(zonasVenta);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonas de venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las zonas de venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ZonaVenta ObtenerZonaVentaPor(int zonaVentaId)throws Exception
	{
		Cursor localCursor =null;
		ZonaVenta localZonaVenta = null;
		try
		{
			localCursor = new ZonaVentaDAL().ObtenerZonaVentaPor(zonaVentaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zonaVenta por zonaVentaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la zonaVenta por zonaVentaId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localZonaVenta = new ZonaVenta(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localZonaVenta;
	}
		  
	public ArrayList<ZonaVenta> ObtenerZonasVenta() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<ZonaVenta> listadoZonaVenta = null;
		
		try
		{
			localCursor = new ZonaVentaDAL().ObtenerZonasVenta();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonasVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las zonasVenta: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoZonaVenta = new ArrayList<ZonaVenta>();
			
			do
			{
				ZonaVenta localZonaVenta = new ZonaVenta(localCursor.getInt(1),localCursor.getString(2));
				listadoZonaVenta.add(localZonaVenta);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoZonaVenta;
	}
}
