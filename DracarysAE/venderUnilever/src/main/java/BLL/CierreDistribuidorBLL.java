package BLL;

import Clases.CierreDistribuidor;
import DAL.CierreDistribuidorDAL;
import Utilidades.Utilidades;
import android.database.Cursor;

public class CierreDistribuidorBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	  
	public boolean BorrarCierresDistribuidor() throws Exception
	{
	    try
	    {
	    	boolean bool = new CierreDistribuidorDAL().BorrarCierresDistribuidor();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres distribuidor: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public boolean BorrarCierreDistribuidorPor(long rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new CierreDistribuidorDAL().BorrarCierreDistribuidorPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cierre distribuidor por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cierre distribuidor por rowId: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public long InsertarCierreDistribuidor(int empleadoId,int anio,int mes,int dia) throws Exception
	{
		try
	    {
			long l = new CierreDistribuidorDAL().InsertarCierreDistribuidor(empleadoId, anio, mes, dia);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre distribuidor: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	  
	public CierreDistribuidor ObtenerCierresDistribuidor() throws Exception
	{
	    Cursor localCursor = null;
	    CierreDistribuidor localCierreDistribuidor = null;
	    
		try
	    {
			localCursor = new CierreDistribuidorDAL().ObtenerCierresDistribuidor();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres distribuidor: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null)
		{
			int i = localCursor.getCount();
	        if (i > 0) 
	        {
	          localCierreDistribuidor = new CierreDistribuidor(localCursor.getInt(0),localCursor.getInt(1),
	        		  localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4));
	        }
		}
		
		return localCierreDistribuidor;
	}
	
	public boolean VerificarCierreDistribuidor(int empleadoId,int anio,int mes,int dia) throws Exception
	{
	    Cursor localCursor = null;
	    boolean verificado = false; 
		try
	    {
			localCursor = new CierreDistribuidorDAL().ObtenerCierreDistribuidorPorEmpleadoIdFecha(empleadoId, anio, mes, dia);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre distribuidor: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null && localCursor.getCount()>0)
		{
			verificado = true;
		}
		
		return verificado;
	}
}
