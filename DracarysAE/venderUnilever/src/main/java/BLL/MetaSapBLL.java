package BLL;

import java.util.ArrayList;

import Clases.MetaSap;
import DAL.MetaSapDAL;
import android.database.Cursor;

public class MetaSapBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMetasSap() throws Exception
	{
		try
		{
			boolean bool = new MetaSapDAL().BorrarMetasSap();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las metas SAP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las metas SAP: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarMetaSap(String categoriaVendedor,String nivelVendedor,String tipo,String nivel,String objeto,float porcentaje,float monto,
			int cobertura,float montoVenta,int coberturaVenta,float avanceMonto,float avanceCobertura) throws Exception
	{
		try
		{
			long l = new MetaSapDAL().InsertarMetaSap(categoriaVendedor,nivelVendedor,tipo,nivel,objeto,porcentaje,monto,
												cobertura,montoVenta,coberturaVenta,avanceMonto,avanceCobertura);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la meta SAP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la meta SAP: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public MetaSap ObtenerMetaSapPor(String tipo)throws Exception
	{
		Cursor localCursor =null;
		MetaSap localMetaSap = null;
		try
		{
			localCursor = new MetaSapDAL().ObtenerMetaSapPor(tipo);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la meta SAP por tipo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la meta SAP por tipo: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localMetaSap = new MetaSap(localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),
										localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getInt(8),localCursor.getFloat(9),localCursor.getInt(10),
										localCursor.getFloat(11),localCursor.getFloat(12));			
		}
		
		return localMetaSap;
	}
		  
	public ArrayList<MetaSap> ObtenerMetasSap() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<MetaSap> listadoMetaSap = null;
		
		try
		{
			localCursor = new MetaSapDAL().ObtenerMetasSap();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las metas SAP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las metas SAP: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoMetaSap = new ArrayList<MetaSap>();
			do
			{
				MetaSap localMetaSap = new MetaSap(localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getString(4),localCursor.getString(5),
													localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getInt(8),localCursor.getFloat(9),localCursor.getInt(10),
													localCursor.getFloat(11),localCursor.getFloat(12));
				listadoMetaSap.add(localMetaSap);
			}
			while(localCursor.moveToNext());		
		}
		
		return listadoMetaSap;
	}
}
