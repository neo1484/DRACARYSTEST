package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.PreventaDisWSResult;
import Clases.PreventaDist;
import DAL.PreventaDistDAL;

public class PreventaDistBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreventasDist() throws Exception
	{
		try
		{
			boolean bool = new PreventaDistDAL().BorrarPreventasDist();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas del distribuidor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarPreventaDist(ArrayList<PreventaDisWSResult> preventasDist) throws Exception
	{
		try
		{
			long l = new PreventaDistDAL().InsertarPreventaDist(preventasDist);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas del ditribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar las preventas del ditribuidor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public int ModificarPreventaDistEstadoEntrega(int preventaId, int estadoEntrega) throws Exception
	{
		try
		{
			int i = new PreventaDistDAL().ModificarPreventaDetalleDist(preventaId,estadoEntrega);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoEntrega en la preventa distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoEntrega en la preventa distribuidor: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ArrayList<PreventaDist> ObtenerPreventasDist() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<PreventaDist> listadoPreventaDist = null;
		try
		{
			localCursor = new PreventaDistDAL().ObtenerPreventasDist();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventas del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventas del distribuidor: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoPreventaDist = new ArrayList<PreventaDist>();
			do
	        {
				PreventaDist preventaDist = new PreventaDist(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
															localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
															localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
															localCursor.getInt(9), localCursor.getString(10), localCursor.getString(11),
															localCursor.getString(12), localCursor.getString(13), localCursor.getInt(14), 
															localCursor.getString(15).equals("1")?true:false, localCursor.getInt(16),
															localCursor.getString(17), localCursor.getString(18),localCursor.getString(19),
															localCursor.getFloat(20),localCursor.getInt(21), localCursor.getFloat(22), 
															localCursor.getFloat(23), localCursor.getFloat(24), localCursor.getFloat(25),
															localCursor.getInt(26), localCursor.getString(27), 
															localCursor.getString(28).equals("1")?true:false,
															localCursor.getString(29).equals("1")?true:false,
															localCursor.getFloat(30), localCursor.getFloat(31));
	          
				listadoPreventaDist.add(preventaDist);
	        } 
			while (localCursor.moveToNext());
		}
		
		return listadoPreventaDist;
	}
		  
	public PreventaDist ObtenerPreventaDistPor(int preventaId) throws Exception
	{
		Cursor localCursor = null;
		PreventaDist preventaDist= null;
		try
		{
			localCursor = new PreventaDistDAL().ObtenerPreventaDistPor(preventaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor por preventaId: " + localException.getMessage());
			}
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			preventaDist = new PreventaDist(localCursor.getInt(0), localCursor.getInt(1), 
											localCursor.getInt(2), localCursor.getInt(3), 
											localCursor.getInt(4), localCursor.getInt(5), 
											localCursor.getFloat(6), localCursor.getFloat(7),
											localCursor.getFloat(8), localCursor.getInt(9),
											localCursor.getString(10), localCursor.getString(11),
											localCursor.getString(12), localCursor.getString(13),
											localCursor.getInt(14), localCursor.getString(15).equals("1")?true:false,
											localCursor.getInt(16),localCursor.getString(17),localCursor.getString(18),
											localCursor.getString(19),localCursor.getFloat(20),localCursor.getInt(21),
											localCursor.getFloat(22), localCursor.getFloat(23),
											localCursor.getFloat(24), localCursor.getFloat(25),
											localCursor.getInt(26), localCursor.getString(27), 
											localCursor.getString(28).equals("1")?true:false,
											localCursor.getString(29).equals("1")?true:false,
											localCursor.getFloat(30), localCursor.getFloat(31));
		}
		
		return preventaDist;
	}
		  
	public PreventaDist ObtenerPreventaDistPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor = null;
		PreventaDist preventaDist = null;
		
		try
		{
			localCursor = new PreventaDistDAL().ObtenerPreventaDistPorClienteId(clienteId);
		}
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor por clienteId: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			preventaDist = new PreventaDist(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
											localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
											localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
											localCursor.getInt(9), localCursor.getString(10), localCursor.getString(11), 
											localCursor.getString(12), localCursor.getString(13), localCursor.getInt(14), 
											localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),
											localCursor.getString(17),localCursor.getString(18),localCursor.getString(19),
											localCursor.getFloat(20),localCursor.getInt(21),
											localCursor.getFloat(22), localCursor.getFloat(23),
											localCursor.getFloat(24), localCursor.getFloat(25),
											localCursor.getInt(26), localCursor.getString(27), 
											localCursor.getString(28).equals("1")?true:false,
											localCursor.getString(29).equals("1")?true:false,
											localCursor.getFloat(30), localCursor.getFloat(31));
		}
	      
		return preventaDist;
	}
	
	public ArrayList<PreventaDist> ObtenerPreventaDistNoEntregadaPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor = null;
		ArrayList<PreventaDist> listadoPreventaDist = null;
		
		try
		{
			localCursor = new PreventaDistDAL().ObtenerPreventaDistNoEntregadaPorClienteId(clienteId);
		}
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor no entregada por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa del distribuidor no entregada por clienteId: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			listadoPreventaDist = new ArrayList<PreventaDist>();
			do
			{
				PreventaDist preventaDist = new PreventaDist(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
						localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
						localCursor.getFloat(6), localCursor.getFloat(7), localCursor.getFloat(8), 
						localCursor.getInt(9), localCursor.getString(10), localCursor.getString(11), 
						localCursor.getString(12), localCursor.getString(13), localCursor.getInt(14), 
						localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),localCursor.getString(17),
						localCursor.getString(18),localCursor.getString(19),localCursor.getFloat(20),localCursor.getInt(21),
						localCursor.getFloat(22), localCursor.getFloat(23), localCursor.getFloat(24), localCursor.getFloat(25),
						localCursor.getInt(26), localCursor.getString(27), localCursor.getString(28).equals("1")?true:false,
						localCursor.getString(29).equals("1")?true:false, localCursor.getFloat(30), localCursor.getFloat(31));
				
				listadoPreventaDist.add(preventaDist);
			}
			while(localCursor.moveToNext());
		}
	      
		return listadoPreventaDist;
	}
}
