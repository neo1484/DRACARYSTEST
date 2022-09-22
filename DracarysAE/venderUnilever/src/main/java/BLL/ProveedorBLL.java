package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Proveedor;
import Clases.ProveedorWSResult;
import DAL.ProveedorDAL;

public class ProveedorBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProveedores() throws Exception
	{
		try
		{
			boolean bool = new ProveedorDAL().BorrarProveedores();
		    return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedores: " + localException.getMessage());
			} 
		    throw localException;
    	}
	}
		  
	public long InsertarProveedor(ArrayList<ProveedorWSResult> proveedores) throws Exception
	{
		try
		{
			long l = new ProveedorDAL().InsertarProveedor(proveedores);
			return l;
    	}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los proveedores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los proveedores: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public ArrayList<Proveedor> ObtenerProveedores() throws Exception
	{
		Cursor localCursor;
		ArrayList<Proveedor> listadoProveedor = null;
		try
		{ 
			localCursor = new ProveedorDAL().ObtenerProveedores();  
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProveedor = new ArrayList<Proveedor>();
			
			do
			{
				Proveedor proveedor = new Proveedor(localCursor.getInt(1), localCursor.getString(2));
				
				listadoProveedor.add(proveedor);
			}
			while(localCursor.moveToNext());
		}
		return listadoProveedor;
	}

	public Proveedor ObtenerProveedorPor(int proveedorId) throws Exception
	{
		Cursor localCursor = null;
		Proveedor localProveedor = null;
		
		try
		{
			localCursor = new ProveedorDAL().ObtenerProveedorPor(proveedorId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedor por proveedorId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el proveedor por proveedorId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			localProveedor = new Proveedor(localCursor.getInt(1),localCursor.getString(2));
		}
		
		return localProveedor;
		
	}

	public ArrayList<Proveedor> ObtenerProveedoresPor(String proveedoresId) throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Proveedor> listadoProveedor = null;
		
		try
		{
			localCursor = new ProveedorDAL().ObtenerProveedoresPor(proveedoresId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProveedor = new ArrayList<Proveedor>();
			do
			{
				Proveedor localProveedor = new Proveedor(localCursor.getInt(1),localCursor.getString(2));
				listadoProveedor.add(localProveedor);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoProveedor;
		
	}
	
	public ArrayList<Proveedor> ObtenerProveedoresTodosPor(String proveedoresId) throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Proveedor> listadoProveedor = null;
		
		try
		{
			localCursor = new ProveedorDAL().ObtenerProveedoresPor(proveedoresId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores por proveedoresId: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProveedor = new ArrayList<Proveedor>();
			listadoProveedor.add(new Proveedor(0,"[Todos]"));
			do
			{
				Proveedor localProveedor = new Proveedor(localCursor.getInt(1),localCursor.getString(2));
				listadoProveedor.add(localProveedor);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoProveedor;
		
	}

}
