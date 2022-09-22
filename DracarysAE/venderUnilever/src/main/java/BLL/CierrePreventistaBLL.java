package BLL;

import Clases.CierrePreventista;
import DAL.CierrePreventistaDAL;
import Utilidades.Utilidades;
import android.database.Cursor;

public class CierrePreventistaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	  
	public boolean BorrarCierresPreventistas() throws Exception
	{
	    try
	    {
	    	boolean bool = new CierrePreventistaDAL().BorrarCierresPreventista();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres preventistas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cierres preventistas: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public boolean BorrarCierrePreventistaPor(long rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new CierrePreventistaDAL().BorrarCierrePreventistaPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cierre preventista por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cierre preventista por rowId: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public long InsertarCierrePreventista(int empleadoId,int anio,int mes,int dia) throws Exception
	{
		try
	    {
			long l = new CierrePreventistaDAL().InsertarCierrePreventista(empleadoId, anio, mes, dia);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cierre preventista: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	  
	public CierrePreventista ObtenerCierresPreventista() throws Exception
	{
	    Cursor localCursor = null;
	    CierrePreventista localCierrePreventista = null;
	    
		try
	    {
			localCursor = new CierrePreventistaDAL().ObtenerCierresPreventistas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cierres preventista: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null)
		{
			int i = localCursor.getCount();
	        if (i > 0) 
	        {
	          localCierrePreventista = new CierrePreventista(localCursor.getInt(0),localCursor.getInt(1),
	        		  localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4));
	        }
		}
		
		return localCierrePreventista;
	}
	
	public boolean VerificarCierrePreventista(int empleadoId,int anio,int mes,int dia) throws Exception
	{
	    Cursor localCursor = null;
	    boolean verificado = false; 
		try
	    {
			localCursor = new CierrePreventistaDAL().ObtenerCierrePreventistaPorEmpleadoIdFecha(empleadoId, anio, mes, dia);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre preventista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cierre preventista: " + localException.getMessage());
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
