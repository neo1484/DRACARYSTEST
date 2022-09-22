package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.Expedido;
import Clases.ExpedidoWSResult;
import DAL.ExpedidoDAL;

public class ExpedidoBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarExpedidos() throws Exception
	{
		try
		{
			boolean bool = new ExpedidoDAL().BorrarExpedidos();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los expedidos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los expedidos: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarExpedido(ArrayList<ExpedidoWSResult> expedidos) throws Exception
	{
		try
		{
			long l = new ExpedidoDAL().InsertarExpedido(expedidos);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los expedidos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los expedidos: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ArrayList<Expedido> ObtenerExpedidos() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Expedido> listadoExpedido = null;
	
	    try
	    {
	    	localCursor = new ExpedidoDAL().ObtenerExpedidos();
		}
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los expedidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los expedidos: " + localException.getMessage());
	    	} 
	    	return listadoExpedido;
	    }
	    
	    if(localCursor.getCount() > 0)
	    {
	    	listadoExpedido = new ArrayList<Expedido>();

	        do
	        {
	        	Expedido expedido = new Expedido(localCursor.getInt(0), localCursor.getString(1));
	        	
	        	listadoExpedido.add(expedido);
	        } 
	        while (localCursor.moveToNext());
	    }
	        
	    return listadoExpedido;
	}


}
