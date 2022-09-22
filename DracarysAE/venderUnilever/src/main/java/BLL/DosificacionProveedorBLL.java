package BLL;

import java.util.ArrayList;

import Clases.DosificacionProveedor;
import Clases.DosificacionProveedorWSResult;
import DAL.DosificacionProveedorDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class DosificacionProveedorBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarDosifiacionesProveedor() throws Exception
	{
	    DosificacionProveedorDAL localDosificacionProveedorDAL = new DosificacionProveedorDAL();
	    try
	    {
	    	boolean bool = localDosificacionProveedorDAL.BorrarDosifiacionesProveedor();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: " + localException.getMessage());
	    	}    
	    	throw localException;
	    }
	}
	  
	public long InsertarDosificacionProveedor(ArrayList<DosificacionProveedorWSResult> dosificacionesProvedor) throws Exception
	{
		DosificacionProveedorDAL localDosificacionProveedorDAL = new DosificacionProveedorDAL();
	    try
	    {
	    	long l = localDosificacionProveedorDAL.InsertarDosificacionProveedor(dosificacionesProvedor);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar las dosificaciones proveedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public ArrayList<DosificacionProveedor> ObtenerDosificacionesProveedorPor(int dosificacionId)throws Exception
	{
	    DosificacionProveedorDAL localDosificacionProveedorDAL = new DosificacionProveedorDAL();
	    Cursor localCursor;
	    try
	    {
	    	localCursor = localDosificacionProveedorDAL.ObtenerDosificacionProveedorPor(dosificacionId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por dosificacionId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por dosificacionId: " + localException.getMessage());
	    	} 
	    	throw localException;  
	    }
	    
	    ArrayList<DosificacionProveedor> listadoDosificacionProveedor= new ArrayList<DosificacionProveedor>();
	    
	    if (localCursor.getCount() > 0) 
	    {	
	    	do
	    	{
	    		DosificacionProveedor dosificacionProveedor = new DosificacionProveedor(localCursor.getInt(1), localCursor.getInt(2),
	    																				localCursor.getString(3).equals("1")?true:false,
	    																				localCursor.getString(4));
	    		listadoDosificacionProveedor.add(dosificacionProveedor);
	    	}
	    	while(localCursor.moveToNext());
	    }
	      
	    return listadoDosificacionProveedor;
	}
	
	public DosificacionProveedor ObtenerDosificacionesProveedorPorProveedorId(int proveedorId)throws Exception
	{
	    DosificacionProveedorDAL localDosificacionProveedorDAL = new DosificacionProveedorDAL();
	    DosificacionProveedor dosificacionProveedor = null;
	    Cursor localCursor;
	    try
	    {
	    	localCursor = localDosificacionProveedorDAL.ObtenerDosificacionProveedorPorProveedorId(proveedorId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion por proveedorId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la dosificacion por proveedorId: " + localException.getMessage());
	    	} 
	    	throw localException;  
	    }
	    
	    if (localCursor.getCount() > 0) 
	    {	
	    	dosificacionProveedor = new DosificacionProveedor(localCursor.getInt(1), localCursor.getInt(2),
	    													localCursor.getString(3).equals("1")?true:false,
	    													localCursor.getString(4));
	    }
	      
	    return dosificacionProveedor;
	}
	  
	public ArrayList<DosificacionProveedor> ObtenerDosificacionesProveedor() throws Exception
	{
		Cursor localCursor;
		ArrayList<DosificacionProveedor> listadoDosificacionProveedor = null;
	    try
	    {
	    	localCursor = new DosificacionProveedorDAL().ObtenerDosificacionesProveedor();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }

	    if (localCursor.getCount() > 0)
	    {
	    	listadoDosificacionProveedor = new ArrayList<DosificacionProveedor>();
	    	String descripcionProveedor="";
	        do
	        {
	        	DosificacionProveedor dosificacionProveedor = new DosificacionProveedor(localCursor.getInt(1), localCursor.getInt(2),
	        																			localCursor.getString(3).equals("1")?true:false,
	    																				localCursor.getString(4));
	        	if(listadoDosificacionProveedor.size()>0)
	        	{
	        		for(int i=0;i<listadoDosificacionProveedor.size();i++)
		        	{
		        		if(!dosificacionProveedor.get_descripcion().equals(descripcionProveedor))
		        		{
		        			descripcionProveedor = dosificacionProveedor.get_descripcion();
		        			listadoDosificacionProveedor.add(dosificacionProveedor);
		        		}
		        	}
	        	}
	        	else
	        	{
	        		descripcionProveedor = dosificacionProveedor.get_descripcion();
	        		listadoDosificacionProveedor.add(dosificacionProveedor);
	        	}
	        } 
	        while (localCursor.moveToNext());
	    }
	    
	    return listadoDosificacionProveedor;
	  }
	
	public ArrayList<DosificacionProveedor> ObtenerDosificacionesProveedorVendedor() throws Exception
	{
		Cursor localCursor;
		ArrayList<DosificacionProveedor> listadoDosificacionProveedor = null;
	    try
	    {
	    	localCursor = new DosificacionProveedorDAL().ObtenerDosificacionesProveedorVendedor();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las dosificaciones proveedor por vendedor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }

	    if (localCursor.getCount() > 0)
	    {
	    	listadoDosificacionProveedor = new ArrayList<DosificacionProveedor>();
	    	String descripcionProveedor="";
	        do
	        {
	        	DosificacionProveedor dosificacionProveedor = new DosificacionProveedor(localCursor.getInt(1), localCursor.getInt(2),
	        																			localCursor.getString(3).equals("1")?true:false,
	    																				localCursor.getString(4));
	        	if(listadoDosificacionProveedor.size()>0)
	        	{
	        		for(int i=0;i<listadoDosificacionProveedor.size();i++)
		        	{
		        		if(!dosificacionProveedor.get_descripcion().equals(descripcionProveedor))
		        		{
		        			descripcionProveedor = dosificacionProveedor.get_descripcion();
		        			listadoDosificacionProveedor.add(dosificacionProveedor);
		        		}
		        	}
	        	}
	        	else
	        	{
	        		descripcionProveedor = dosificacionProveedor.get_descripcion();
	        		listadoDosificacionProveedor.add(dosificacionProveedor);
	        	}
	        } 
	        while (localCursor.moveToNext());
	    }
	    
	    return listadoDosificacionProveedor;
	  }
}
