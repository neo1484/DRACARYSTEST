package BLL;

import java.util.ArrayList;

import Clases.ProntoPagoJerarquia;
import Clases.ProntoPagoJerarquiaWSResult;
import DAL.ProntoPagoJerarquiaDAL;
import android.database.Cursor;

public class ProntoPagoJerarquiaBLL
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarProntosPagoJerarquia()throws Exception
	{
		try
		{
			boolean bool = new ProntoPagoJerarquiaDAL().BorrarProntosPagoJerarquia();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago jerarquia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago jerarquia: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarProntoPagoJerarquia(ArrayList<ProntoPagoJerarquiaWSResult> prontosPagoJerarquia) throws Exception
	{
		try
		{
			long l = new ProntoPagoJerarquiaDAL().InsertarProntoPagoJerarquia(prontosPagoJerarquia);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontos pago jerarquia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los prontso pago jerarquia: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public ProntoPagoJerarquia ObtenerProntoPagoJerarquiaPor(int prontoPagoId) throws Exception
	{
		ProntoPagoJerarquia localProntoPagoJerarquia2 = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquiaPor(prontoPagoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por prontoPagoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por prontoPagoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			localProntoPagoJerarquia2 = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3),localCursor.getFloat(4));
	    }
	    
		return localProntoPagoJerarquia2;
    }
	
	public int ObtenerProntoPagoJerarquia1IdPorProductoId(int productoId) throws Exception
	{
		int jerarquia1Id = 0;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia1IdPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: " + localException.getMessage());
			} 
			throw localException;
	    }
				
		if (localCursor.getCount()>0) 
		{
			jerarquia1Id = localCursor.getInt(0);
	    }
	    
		return jerarquia1Id;
    }

	public int ObtenerProntoPagoJerarquia2IdPorProductoId(int productoId) throws Exception
	{
		int jerarquia2Id = 0;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia2IdPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			jerarquia2Id = localCursor.getInt(0);
		}

		return jerarquia2Id;
	}

	public int ObtenerProntoPagoJerarquia3IdPorProductoId(int productoId) throws Exception
	{
		int jerarquia3Id = 0;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia3IdPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			jerarquia3Id = localCursor.getInt(0);
		}

		return jerarquia3Id;
	}

	public int ObtenerProntoPagoJerarquia5IdPorProductoId(int productoId) throws Exception
	{
		int jerarquia5Id = 0;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia5IdPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			jerarquia5Id = localCursor.getInt(0);
		}

		return jerarquia5Id;
	}

	public int ObtenerProntoPagoCategoriaIdPorProductoId(int productoId) throws Exception
	{
		int categoriaId = 0;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoCategoriaIdPorProductoId(productoId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por productoId: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			categoriaId = localCursor.getInt(0);
		}

		return categoriaId;
	}

	public ProntoPagoJerarquia ObtenerProntoPagoJerarquia1Por(int jerarquia1Id) throws Exception
	{
		ProntoPagoJerarquia localProntoPagoJerarquia = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia1Por(jerarquia1Id);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia1Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia1Id: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			localProntoPagoJerarquia = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3),localCursor.getFloat(4));
		}

		return localProntoPagoJerarquia;
	}

	public ProntoPagoJerarquia ObtenerProntoPagoJerarquia2Por(int jerarquia2Id) throws Exception
	{
		ProntoPagoJerarquia localProntoPagoJerarquia = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia2Por(jerarquia2Id);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia2Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia2Id: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			localProntoPagoJerarquia = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3),localCursor.getFloat(4));
		}

		return localProntoPagoJerarquia;
	}

	public ProntoPagoJerarquia ObtenerProntoPagoJerarquia3Por(int jerarquia3Id) throws Exception
	{
		ProntoPagoJerarquia localProntoPagoJerarquia = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia3Por(jerarquia3Id);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia3Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia3Id: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			localProntoPagoJerarquia = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3),localCursor.getFloat(4));
		}

		return localProntoPagoJerarquia;
	}

	public ProntoPagoJerarquia ObtenerProntoPagoJerarquia5Por(int jerarquia5Id) throws Exception
	{
		ProntoPagoJerarquia localProntoPagoJerarquia = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoJerarquia5Por(jerarquia5Id);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia5Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por jerarquia5Id: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			localProntoPagoJerarquia = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3),localCursor.getFloat(4));
		}

		return localProntoPagoJerarquia;
	}

	public ProntoPagoJerarquia ObtenerProntoPagoCategoriaIdPor(int categoriaId) throws Exception
	{
		ProntoPagoJerarquia localProntoPagoJerarquia = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntoPagoCategoriaIdPor(categoriaId);
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por categoriaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago por categoriaId: " + localException.getMessage());
			}
			throw localException;
		}

		if (localCursor.getCount()>0)
		{
			localProntoPagoJerarquia = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3),localCursor.getFloat(4));
		}

		return localProntoPagoJerarquia;
	}
		  
	public ArrayList<ProntoPagoJerarquia> ObtenerProntosPagoJerarquia() throws Exception
	{
		Cursor localCursor=null;
		ArrayList<ProntoPagoJerarquia> listadoProntoPagoJerarquia = null;
		try
		{
			localCursor = new ProntoPagoJerarquiaDAL().ObtenerProntosPagoJerarquia();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago jerarquia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los prontos pago jerarquia: " + localException.getMessage());
			} 
		}
		
		if(localCursor.getCount()>0)
		{
			listadoProntoPagoJerarquia = new ArrayList<ProntoPagoJerarquia>();
			do
			{
				ProntoPagoJerarquia localProntoPagoJerarquia = new ProntoPagoJerarquia(localCursor.getInt(1), localCursor.getString(2), localCursor.getInt(3), localCursor.getFloat(4));
				
				listadoProntoPagoJerarquia.add(localProntoPagoJerarquia);
	        }
			while (localCursor.moveToNext());
		}

        return listadoProntoPagoJerarquia;
	}
}
