package BLL;

import java.util.ArrayList;

import Clases.RelevamientoDetalle;
import DAL.RelevamientoDetalleDAL;
import android.database.Cursor;

public class RelevamientoDetalleBLL 
{
	MyLogBLL myLog = new MyLogBLL();

	public boolean BorrarRelevamientosDetalle() throws Exception
	{
		try
		{
			boolean bool = new RelevamientoDetalleDAL().BorrarRelevamientosDetalle();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos detalle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos detalle: " + localException.getMessage());
			} 
		    throw localException;
	    }
	}
		  
	public long InsertarRelevamientoDetalle(int relevamientoId, String nombre, int tipoNegocioId, String categoriaId, int fotoSize, byte[] fotoBinary, 
										double latitud, double longitud, int sincro) throws Exception
	{
		try
	    {
			long l = new RelevamientoDetalleDAL().InsertarRelevamientoDetalle(relevamientoId, nombre, tipoNegocioId, categoriaId, fotoSize, fotoBinary, latitud, longitud, sincro);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los relevamientos detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los relevamientos detalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	  }

	public ArrayList<RelevamientoDetalle> ObtenerRelevamientosDetalle() throws Exception
	{
		ArrayList<RelevamientoDetalle> listadoRelevamientoDetalle = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new RelevamientoDetalleDAL().ObtenerRelevamientosDetalle();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: " + localException.getMessage());
			} 
			return null;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoRelevamientoDetalle = new ArrayList<RelevamientoDetalle>();
			
			do
			{
				RelevamientoDetalle relevamientoDetalle = new RelevamientoDetalle(localCursor.getInt(0), localCursor.getInt(1), localCursor.getString(2), 
						localCursor.getInt(3), localCursor.getString(4), localCursor.getInt(5), localCursor.getBlob(6), 
						localCursor.getDouble(7), localCursor.getDouble(8), localCursor.getInt(9));
				
				listadoRelevamientoDetalle.add(relevamientoDetalle);
			}
			while(localCursor.moveToNext());
		}
		return listadoRelevamientoDetalle;
	}
	
	public ArrayList<RelevamientoDetalle> ObtenerRelevamientosDetalleNoSincro() throws Exception
	{
		ArrayList<RelevamientoDetalle> listadoRelevamientoDetalle = null;
		Cursor localCursor = null;
		
		try
		{
			localCursor = new RelevamientoDetalleDAL().ObtenerRelevamientosDetalleNoSincro();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
	        	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: " + localException.getMessage());
			} 
			return null;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoRelevamientoDetalle = new ArrayList<RelevamientoDetalle>();
			
			do
			{
				RelevamientoDetalle relevamientoDetalle = new RelevamientoDetalle(localCursor.getInt(0), localCursor.getInt(1), localCursor.getString(2), 
						localCursor.getInt(3), localCursor.getString(4), localCursor.getInt(5), localCursor.getBlob(6), 
						localCursor.getDouble(7), localCursor.getDouble(8), localCursor.getInt(9));
				
				listadoRelevamientoDetalle.add(relevamientoDetalle);
			}
			while(localCursor.moveToNext());
		}
		return listadoRelevamientoDetalle;
	}
	
	public long ModificarRelevamientoDetalleSincro(int rowId,int sincro) throws Exception
	{
	    try
	    {
	    	long l = new RelevamientoDetalleDAL().ModificarRelevamientoDetalleSincro(rowId, sincro);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del relevamiento detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sinro del relevamiento detalle: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
