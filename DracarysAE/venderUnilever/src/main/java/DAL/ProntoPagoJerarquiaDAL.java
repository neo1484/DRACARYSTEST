package DAL;

import com.detesim.venderunilever.Login;

import BLL.MyLogBLL;
import Clases.ProntoPagoJerarquiaWSResult;
import Utilidades.AdministradorDB;
import android.database.Cursor;

import java.util.ArrayList;

public class ProntoPagoJerarquiaDAL
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarProntosPagoJerarquia() throws Exception
	{
		db.OpenDB();
		try
		{
			db.borrarProntosPagoJerarquia();
			return true;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago jerarquia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago jerarquia: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long InsertarProntoPagoJerarquia(ArrayList<ProntoPagoJerarquiaWSResult> prontosPagoJerarquia)throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarProntoPagoJerarquia(prontosPagoJerarquia);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago jerarquia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago jerarquia: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerProntoPagoJerarquiaPor(int prontoPagoId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerProntoPagoJerarquiaPor(prontoPagoId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por prontoPagoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por prontoPagoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerProntoPagoJerarquia1IdPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerProntoPagoJerarquia1IdPorProductoId(productoId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia1 por productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia1 por productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public Cursor ObtenerProntoPagoJerarquia2IdPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia2IdPorProductoId(productoId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia2 por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia2 por productoId: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoJerarquia3IdPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia3IdPorProductoId(productoId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia3 por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia3 por productoId: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoJerarquia5IdPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia5IdPorProductoId(productoId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia5 por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia5 por productoId: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoCategoriaIdPorProductoId(int productoId)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoCategoriaIdPorProductoId(productoId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago categoriaId por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago categoriaId por productoId: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoJerarquia1Por(int jerarquia1Id)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia1Por(jerarquia1Id);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia1Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia1Id: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoJerarquia2Por(int jerarquia2Id)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia2Por(jerarquia2Id);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia2Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia2Id: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoJerarquia3Por(int jerarquia3Id)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia3Por(jerarquia3Id);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia3Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia3Id: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoJerarquia5Por(int jerarquia5Id)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoJerarquia5Por(jerarquia5Id);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia5Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por jerarquia5Id: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}

	public Cursor ObtenerProntoPagoCategoriaIdPor(int categoriaId)throws Exception
	{
		db.OpenDB();
		try
		{
			Cursor localCursor = db.obtenerProntoPagoCategoriaIdPor(categoriaId);
			return localCursor;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por categoriaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el pronto pago jerarquia por categoriaId: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerProntosPagoJerarquia()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerProntosPagoJerarquia();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago jerarquia unilever: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago jerarquia unilever: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
}