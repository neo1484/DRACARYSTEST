package BLL;

import Clases.ParametroGeneral;
import Clases.ParametroGeneralWSResult;
import DAL.ParametroGeneralDAL;
import Utilidades.Utilidades;
import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

public class ParametroGeneralBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	  
	public boolean BorrarParametrosGenerales() throws Exception
	{
	    try
	    {
	    	boolean bool = new ParametroGeneralDAL().BorrarParametrosGenerales();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public boolean BorrarParametroGeneralPor(long rowId) throws Exception
	{
	    try
	    {
	    	boolean bool = new ParametroGeneralDAL().BorrarParametroGeneralPor(rowId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales por rowId: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	
	public long InsertarParametroGeneral(ParametroGeneralWSResult parametroGeneral) throws Exception
	{
		try
	    {
			long l = new ParametroGeneralDAL().InsertarParametroGeneral(parametroGeneral);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los parametros generales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los parametros generales: " + localException.getMessage());
			}
	    	throw localException;
	    }
	}
	  
	public ParametroGeneral ObtenerParamatroGeneral() throws Exception
	{
	    Cursor localCursor = null;
	    ParametroGeneral localParametroGeneral = null;
	    
		try
	    {
			localCursor = new ParametroGeneralDAL().ObtenerParametrosGenerales();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
			{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametro general: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametro general: " + localException.getMessage());
			}
	    	throw localException;
	    }
		
		if (localCursor != null)
		{
			int i = localCursor.getCount();
	        if (i > 0) 
	        {
	          localParametroGeneral = new ParametroGeneral(localCursor.getFloat(1),localCursor.getFloat(2),
	        		  localCursor.getString(3).equals("1")?true:false,localCursor.getString(4),localCursor.getString(5),
	        		  localCursor.getString(6),localCursor.getInt(7),localCursor.getInt(8),localCursor.getString(9).equals("1")?true:false,
	        		  localCursor.getString(10).equals("1")?true:false,localCursor.getString(11).equals("1")?true:false,
	        		  localCursor.getString(12),localCursor.getString(13).equals("1")?true:false,localCursor.getFloat(14),
    				  localCursor.getFloat(15),localCursor.getString(16).equals("1")?true:false,localCursor.getString(17).equals("1")?true:false,
					  localCursor.getString(18).equals("1")?true:false,localCursor.getFloat(19),localCursor.getString(20).equals("1")?true:false,
					  localCursor.getString(21).equals("1")?true:false,localCursor.getString(22).equals("1")?true:false,
					  localCursor.getString(23).equals("1")?true:false,localCursor.getString(24).equals("1")?true:false,
					  localCursor.getFloat(25),localCursor.getString(26).equals("1")?true:false,localCursor.getString(27),
					  localCursor.getString(28).equals("1")?true:false,localCursor.getString(29).equals("1")?true:false,
					  localCursor.getString(30).equals("1")?true:false,localCursor.getString(31).equals("1")?true:false,
					  localCursor.getString(32).equals("1")?true:false,localCursor.getString(33).equals("1")?true:false,
					  localCursor.getString(34).equals("1")?true:false,localCursor.getString(35).equals("1")?true:false,
					  localCursor.getString(36).equals("1")?true:false,localCursor.getFloat(37),localCursor.getString(38).equals("1")?true:false,
					  localCursor.getString(39).equals("1")?true:false,localCursor.getString(40).equals("1")?true:false,
					  localCursor.getString(41).equals("1")?true:false,localCursor.getString(42).equals("1")?true:false);
	        }
		}
		
		return localParametroGeneral;
	} 
}
