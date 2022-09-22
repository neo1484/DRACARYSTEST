package BLL;

import java.util.ArrayList;
import Clases.Mercado;
import Clases.MercadoWSResult;
import DAL.MercadoDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class MercadoBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMercados() throws Exception
	{
		try
		{
			boolean bool = new MercadoDAL().BorrarMercados();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los mercados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los mercados: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarMercado(ArrayList<MercadoWSResult> mercados) throws Exception
	{
		try
		{
			long l = new MercadoDAL().InsertarMercado(mercados);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los mercados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los mercados: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Mercado ObtenerMercadoPorMercadoId(int mercadoId)throws Exception
	{
		Cursor localCursor =null;
		Mercado localMercado = null;
		try
		{
			localCursor = new MercadoDAL().ObtenerMercadoPor(mercadoId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el mercado por mercadoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el mercado por mercadoId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localMercado = new Mercado(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localMercado;
	}
		  
	public ArrayList<Mercado> ObtenerMercados() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Mercado> listadoTemporal = null;
		ArrayList<Mercado> listadoMercado = null;
		
		try
		{
			localCursor = new MercadoDAL().ObtenerMercados();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los mercados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los mercados: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoTemporal = new ArrayList<Mercado>();
			do
			{
				Mercado localMercado = new Mercado(localCursor.getInt(1),localCursor.getString(2));
				
				listadoTemporal.add(localMercado);
			}
			while(localCursor.moveToNext());
			
			listadoMercado = new ArrayList<Mercado>();
			
			listadoMercado.add(new Mercado(0,"Seleccione un mercado"));
			
			for(Mercado item : listadoTemporal)
			{
				listadoMercado.add(item);
			}
		}
		else
		{
			listadoMercado = new ArrayList<Mercado>();
			listadoMercado.add(new Mercado(0,"Seleccione un mercado"));
		}
		
		return listadoMercado;
	}
}
