package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.DiaSemana;
import Clases.DiaSemanaWSResult;
import DAL.DiaSemanaDAL;

public class DiaSemanaBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarDiasSemana() throws Exception
	{
		try
		{
			boolean bool = new DiaSemanaDAL().BorrarDiasSemana();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los dias semana: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los dias semana: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarDiaSemana(ArrayList<DiaSemanaWSResult> diasSemana) throws Exception
	{
		try
	    {
			long l = new DiaSemanaDAL().InsertarDiaSemana(diasSemana);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los dias de la semana: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los dias de la semana: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<DiaSemana> ObtenerDiasSemana() throws Exception
	{
		ArrayList<DiaSemana> listadoTemporal = null;
		ArrayList<DiaSemana> listadoDiaSemana = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new DiaSemanaDAL().ObtenerDiasSemana();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: " + localException.getMessage());
			} 
			return listadoDiaSemana;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoTemporal = new ArrayList<DiaSemana>();
			
			do
			{
				DiaSemana diaSemana = new DiaSemana(localCursor.getInt(0),localCursor.getInt(1),localCursor.getString(2));
				
				listadoTemporal.add(diaSemana);
			}
			while(localCursor.moveToNext());
			
			listadoDiaSemana = new ArrayList<DiaSemana>();
			
			listadoDiaSemana.add(new DiaSemana(0,0,"Seleccione un dia de ruta"));
			
			for(DiaSemana item : listadoTemporal)
			{
				listadoDiaSemana.add(item);
			}
			
		}
		return listadoDiaSemana;
	}

}
