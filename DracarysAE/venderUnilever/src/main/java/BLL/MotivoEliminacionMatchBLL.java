package BLL;

import java.util.ArrayList;

import Clases.Mercado;
import Clases.MotivoEliminacionMatch;
import Clases.MotivoEliminacionMatchWSResult;
import DAL.MotivoEliminacionMatchDAL;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class MotivoEliminacionMatchBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarMotivosEliminacionMatch() throws Exception
	{
		try
		{
			boolean bool = new MotivoEliminacionMatchDAL().BorrarMotivosEliminacionMatch();
			return bool;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos eliminacion match: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos eliminacion match: " + localException.getMessage());
			} 
			throw localException;
		}
	}
		  
	public long InsertarMotivoEliminacionMatch(ArrayList<MotivoEliminacionMatchWSResult>  motivosEliminacion) throws Exception
	{
		try
		{
			long l = new MotivoEliminacionMatchDAL().InsertarMotivoEliminacionMatch(motivosEliminacion);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos de elimincion del macth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los motivos de eliminacion del match: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Mercado ObtenerMotivoEliminacionMatchId(int motivoId)throws Exception
	{
		Cursor localCursor =null;
		Mercado localMercado = null;
		try
		{
			localCursor = new MotivoEliminacionMatchDAL().ObtenerMotivoEliminacionMatchPor(motivoId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el motivo eliminacion match por motivoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el motivo eliminacion match por motivoId: " + localException.getMessage());
			} 
		    throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localMercado = new Mercado(localCursor.getInt(1),localCursor.getString(2));			
		}
		
		return localMercado;
	}
		  
	public ArrayList<MotivoEliminacionMatch> ObtenerMotivoseliminacionMatch() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<MotivoEliminacionMatch> listadoMotivo = null;
		
		try
		{
			localCursor = new MotivoEliminacionMatchDAL().ObtenerMotivosEliminacionMatch();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos de eliminacion del match: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivos de eliminacion del match: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount() > 0)
		{
			listadoMotivo = new ArrayList<MotivoEliminacionMatch>();
			do
			{
				MotivoEliminacionMatch localMotivo = new MotivoEliminacionMatch(localCursor.getInt(1),localCursor.getString(2));
				
				listadoMotivo.add(localMotivo);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoMotivo;
	}
}
