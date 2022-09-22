package BLL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import android.database.Cursor;
import Clases.MyLog;
import DAL.LogDAL;
import android.util.Log;

public class MyLogBLL
{	
	
	public boolean BorrarLogs() throws Exception
	{
	  LogDAL localLogDAL = new LogDAL();
	  try
	  {
	    boolean bool = localLogDAL.BorrarLogs();
	    return bool;
	  }
	  catch (Exception localException)
	  {
		  if(localException.getMessage().isEmpty())
		  {
			  Log.e("App", "Error al borrar los logs: vacio");
		  }
		  else
		  {
			  Log.e("App","Error al borrar los logs: " + localException.getMessage());
		  }
		  throw localException;
	  }
	}
		  
	public long InsertarLog(String aplicacion,String actividad,int tipoLog,String log)
	{
		LogDAL localLogDAL = new LogDAL();
		
		Date localDate = new GregorianCalendar().getTime();
	    String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(localDate);
	    
	    long l=0;
	    String tipoDeLog = "";
	    
	    switch (tipoLog)
	    {
	    	case 1: tipoDeLog = "Error";
	    		break;
	    	case 2: tipoDeLog = "Debug";
	    		break;
	    }
	    if(!tipoDeLog.equals(""))
	    {
	    	try
		    {
		      l = localLogDAL.InsertarLog(aplicacion,actividad,fecha,tipoDeLog,log);
		      return l;
		    }
		    catch (Exception localException)
		    {
		    	if(localException.getMessage().isEmpty())
		    	{
		    		Log.e("App","Error al insertar el log: vacio");
		    	}
		    	else
		    	{
		    		Log.e("App","Error al insertar el log:" + localException.getMessage());
		    	}
		    }
	    }
		return l;
	}
	
	public ArrayList<MyLog> ObtenerLogs() throws Exception
	{
	   LogDAL localLogDAL = new LogDAL();
	   Cursor localCursor = null;
	   ArrayList<MyLog> listadoLogs = null;
	   MyLog myLog = null;
	   
	   try
	   {
		   localCursor = localLogDAL.ObtenerLogs();
	   }
	   catch (Exception localException)
	   {
		   if(localException.getMessage().isEmpty())
		   {
			   Log.e("App", "Error al obtener los logs: vacio");
		   }
		   else
		   {
			   Log.e("App","Error al obtener los logs:" + localException.getMessage());
		   }
		   throw localException;
	   }
	   
	   int count = localCursor.getCount();
	   
	   if(count>0)
	   {
		   listadoLogs = new ArrayList<MyLog>();
		   
		   do
		   {
			   myLog = new MyLog(localCursor.getInt(0),localCursor.getString(1),localCursor.getString(2),
					   localCursor.getString(3),localCursor.getString(4),localCursor.getString(5));
			   
			   listadoLogs.add(myLog);			   
		   }
		   while(localCursor.moveToNext());
	   }
	   
	   return listadoLogs;
	}
}
