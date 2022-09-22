package BLL;

import java.util.ArrayList;

import Clases.RutaVendedor;
import DAL.RutaVendedorDAL;
import android.database.Cursor;

public class RutaVendedorBLL 
{
MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarRutasVendedor() throws Exception
	{
		try
		{
			boolean bool = new RutaVendedorDAL().BorrarRutasVendedor();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas vendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas vendedor: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarRutaVendedor(int vendedorId,int rutaId,int diaId,int diaVisitaId,String descripcion) throws Exception
	{
		try
		{
			long l = new RutaVendedorDAL().InsertarRutaVendedor(vendedorId,rutaId,diaId,diaVisitaId,descripcion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ruta vendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ruta vendedor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public RutaVendedor ObtenerRutaVendedorPor(int diaVisitaId)throws Exception
	{
		Cursor localCursor =null;
		RutaVendedor localRutaVendedor = null;
		try
		{
			localCursor = new RutaVendedorDAL().ObtenerRutaVendedorPor(diaVisitaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta vendedor por diaVisitaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ruta vendedor por diaVisitaId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localRutaVendedor = new RutaVendedor(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
												localCursor.getString(5));			
		}
		
		return localRutaVendedor;
	}
		  
	public ArrayList<RutaVendedor> ObtenerRutasVendedor() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<RutaVendedor> listadoRutaVendedor = null;
		
		try
		{
			localCursor = new RutaVendedorDAL().ObtenerRutasVendedor();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las rutas vendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las rutas vendedor: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoRutaVendedor = new ArrayList<RutaVendedor>();
			//listadoRutaVendedor.add(new RutaVendedor(0,0,0,0,"[Seleccione una ruta ...]"));
			do
			{
				RutaVendedor localRutaVendedor = new RutaVendedor(localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
						localCursor.getString(5));
				listadoRutaVendedor.add(localRutaVendedor);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoRutaVendedor;
	}
}