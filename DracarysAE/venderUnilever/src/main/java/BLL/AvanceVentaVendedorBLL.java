package BLL;

import java.util.ArrayList;
import Clases.AvanceVentaVendedor;
import Clases.AvanceVentaVendedorWSResult;
import DAL.AvanceVentaVendedorDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AvanceVentaVendedorBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarAvancesVentaVendedor() throws Exception
	{
	    try
	    {
	    	boolean bool = new AvanceVentaVendedorDAL().BorrarAvancesVentaVendedor();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVentaVendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVentaVendedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarAvanceVentaVendedor(ArrayList<AvanceVentaVendedorWSResult>  avancesVentaVendedor, int dia) throws Exception
	{
		try
		{
			long l = new AvanceVentaVendedorDAL().InsertarAvanceVentaVendedor(avancesVentaVendedor, dia);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta vendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances venta vendedor: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<AvanceVentaVendedor> ObtenerAvancesVentaVendedor() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<AvanceVentaVendedor> listadoAvanceVentaVendedor = null;
	    
	    try
	    {
	    	localCursor = new AvanceVentaVendedorDAL().ObtenerAvancesVentaVendedor();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVentaVendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVentaVendedor: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoAvanceVentaVendedor = new ArrayList<AvanceVentaVendedor>();
	        	do
	        	{
	        		AvanceVentaVendedor localAvanceVentaVendedor = new AvanceVentaVendedor(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),
	        				localCursor.getInt(4),localCursor.getString(5),localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8),
	        				localCursor.getInt(9),localCursor.getString(10),localCursor.getFloat(11));
	        		
	        		listadoAvanceVentaVendedor.add(localAvanceVentaVendedor);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoAvanceVentaVendedor;
	}
}
