package BLL;

import java.util.ArrayList;

import Clases.AlmacenCHANGE;
import DAL.AlmacenCHANGEDAL;
import android.database.Cursor;

public class AlmacenCHANGEBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarAlmacenesCHANGE() throws Exception
	{
		try
		{
			boolean bool = new AlmacenCHANGEDAL().BorrarAlmacenesCHANGE();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesCHANGE: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarAlmacenCHANGE(int almacenId, String descripcion) throws Exception
	{
		try
	    {
			long l = new AlmacenCHANGEDAL().InsertarAlmacenCHANGE(almacenId, descripcion);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenCHANGE: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el almacenCHANGE: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<AlmacenCHANGE> ObtenerAlmacenesCHANGE() throws Exception
	{
		ArrayList<AlmacenCHANGE> listadoAlmacenCHANGE = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new AlmacenCHANGEDAL().ObtenerAlmacenesCHANGE();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesCHANGE: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesCHANGE: " + localException.getMessage());
			} 
			return listadoAlmacenCHANGE;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoAlmacenCHANGE = new ArrayList<AlmacenCHANGE>();
			
			do
			{
				AlmacenCHANGE almacenCHANGE = new AlmacenCHANGE(localCursor.getInt(1),localCursor.getString(2));
				
				listadoAlmacenCHANGE.add(almacenCHANGE);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoAlmacenCHANGE;
	}
}