package BLL;

import java.util.ArrayList;

import Clases.AlmacenPOP;
import Clases.AlmacenPOPWSResult;
import DAL.AlmacenPOPDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class AlmacenPOPBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarAlmacenesPOP() throws Exception
	{
		try
		{
			boolean bool = new AlmacenPOPDAL().BorrarAlmacenesPOP();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenesPOP: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarAlmacenPOP(AlmacenPOPWSResult almacenPOP) throws Exception
	{
		try
	    {
			long l = new AlmacenPOPDAL().InsertarAlmacenPOP(almacenPOP);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los almacenes POP: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<AlmacenPOP> ObtenerAlmacenesPOP() throws Exception
	{
		ArrayList<AlmacenPOP> listadoAlmacenPOP = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new AlmacenPOPDAL().ObtenerAlmacenesPOP();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los almacenesPOP: " + localException.getMessage());
			} 
			return listadoAlmacenPOP;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoAlmacenPOP = new ArrayList<AlmacenPOP>();
			
			do
			{
				AlmacenPOP almacenPOP = new AlmacenPOP(localCursor.getInt(1),localCursor.getString(2));
				
				listadoAlmacenPOP.add(almacenPOP);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoAlmacenPOP;
	}
}
