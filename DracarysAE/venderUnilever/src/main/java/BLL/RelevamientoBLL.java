package BLL;

import java.util.ArrayList;
import java.util.List;

import Clases.Relevamiento;
import DAL.RelevamientoDAL;
import android.database.Cursor;

public class RelevamientoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarRelevaminetos() throws Exception
	{
		try
		{
			boolean bool = new RelevamientoDAL().BorrarRelevamientos();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarRelevamiento(List<Relevamiento> relevamiento) throws Exception
	{
		try
	    {
			long l = new RelevamientoDAL().InsertarRelevamiento(relevamiento);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los relevamientos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los relevamientos: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<Relevamiento> ObtenerRelevamientos() throws Exception
	{
		ArrayList<Relevamiento> listadoRelevamiento = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new RelevamientoDAL().ObtenerRelevamientos();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtenrer los relevamientos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos: " + localException.getMessage());
			} 
			return null;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoRelevamiento = new ArrayList<Relevamiento>();
			
			do
			{
				Relevamiento relevamiento = new Relevamiento(localCursor.getInt(1), localCursor.getString(2), localCursor.getString(3),
						localCursor.getInt(4), localCursor.getString(5), localCursor.getString(6).equals("1")?true:false);
				
				listadoRelevamiento.add(relevamiento);
			}
			while(localCursor.moveToNext());
		}
		return listadoRelevamiento;
	}
}
