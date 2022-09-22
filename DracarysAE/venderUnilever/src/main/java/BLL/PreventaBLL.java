package BLL;

import java.util.ArrayList;
import android.database.Cursor;
import Clases.Preventa;
import DAL.PreventaDAL;

public class PreventaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarPreventas()throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaDAL().BorrarPreventas();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas: " + localException.getMessage());
	    	}
	    	return false;
	    }
	}
	
	public long InsertarPreventa(int preventaIdServer, int empleadoId, int clienteId, float monto, float descuento, float montoFinal,
			int tipoPagoId, double latitud, double longitud, boolean estado, int dia, int mes, int anio, int hora, int minuto,
			String factura, String nit, boolean nitNuevo, int noPreventa, String observacion, boolean aplicarBonificacion,
			int diaEntrega, int mesEntrega, int anioEntrega, int dosificacionId, String tipoNit, String ruta, String tipoVisita,
			int zonaVentaId, int prontoPagoId, float descuentoCanal, float descuentoAjuste, float descuentoProntoPago,
			float descuentoObjetivo, int formaPagoId, String codTransferencia, boolean fromApk, boolean fromShopp, float descuentoIncentivo) throws Exception
	{
		try
		{
			long l = new PreventaDAL().InsertarPreventa(preventaIdServer, empleadoId, clienteId, monto, descuento, montoFinal,
										tipoPagoId, latitud, longitud, estado, dia, mes, anio, hora, minuto, factura, nit, nitNuevo,
										noPreventa, observacion, aplicarBonificacion, diaEntrega, mesEntrega, anioEntrega,
										dosificacionId, tipoNit, ruta, tipoVisita, zonaVentaId, prontoPagoId, descuentoCanal,
										descuentoAjuste, descuentoProntoPago, descuentoObjetivo, formaPagoId, codTransferencia, fromApk, 
										fromShopp, descuentoIncentivo);
			return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventa: vacio");
    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la preventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
  	}
	
	public boolean BorrarPreventasPorId(long id)throws Exception
	{
	    try
	    {
	    	boolean bool = new PreventaDAL().BorrarPreventasPorId(id);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa por rowId: " + localException.getMessage());
	    	}
	    	return false;
	    }
	}
		  
	public long ModificarPreventaNoSincronizada(int id, int preventaId) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaNoSincronizada(id,preventaId);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long ModificarPreventaNoSincronizadaPor(int id,float monto,float descuento,float montoFinal) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaNoSincronizadaPor(id,monto,descuento,montoFinal);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por Id: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public long ModificarPreventaNoSincronizadaPor(int id,boolean estado) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaNoSincronizadaPor(id, estado);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por Id y estado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventa no sincronizada por Id y estado: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public Preventa ObtenerPreventaPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor;
		Preventa preventa = null;
		try
		{
			localCursor = new PreventaDAL().ObtenerPreventaPorClienteId(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: " + localException.getMessage());
			} 
		    throw localException;
		}
		
		if (localCursor.getCount()>0)
		{
			preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
									localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
									localCursor.getString(10).equals("1")?true:false, localCursor.getInt(11),
									localCursor.getInt(12), localCursor.getInt(13), localCursor.getInt(14),
									localCursor.getInt(15), localCursor.getString(16), localCursor.getString(17),
									localCursor.getString(18).equals("1")?true:false, localCursor.getInt(19),
									localCursor.getString(20), localCursor.getString(21).equals("1")?true:false,
									localCursor.getInt(22), localCursor.getInt(23), localCursor.getInt(24),localCursor.getInt(25),
									localCursor.getString(26), localCursor.getString(27), localCursor.getString(28), localCursor.getInt(29),
									localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
									localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
									localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
		}
		return preventa;
	}
	
	public ArrayList<Preventa> ObtenerNroPreventasPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor;
		ArrayList<Preventa> listadoPreventa = null;
		try
		{
			localCursor = new PreventaDAL().ObtenerNroPreventasPorClienteId(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: " + localException.getMessage());
			} 
		    throw localException;
		}
		
		if (localCursor.getCount()>0)
		{
			listadoPreventa = new ArrayList<Preventa>();
			
			do
			{
				Preventa preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
						localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
						localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
						localCursor.getString(10).equals("1")?true:false,localCursor.getInt(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
						localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
						localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
						localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
						localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),localCursor.getString(26),
						localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
						localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
						localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
						localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
				
				listadoPreventa.add(preventa);
			}
			while(localCursor.moveToNext());
			
		}
		return listadoPreventa;
	}
	
	public Preventa ObtenerPreventaPorId(int rowId) throws Exception
	{
		Cursor localCursor;
		Preventa preventa = null;
		try
		{
			localCursor = new PreventaDAL().ObtenerPreventaPorId(rowId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por rowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por rowId: " + localException.getMessage());
			} 
		    throw localException;
		}
		
		if (localCursor.getCount()>0)
		{
			preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
									localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
									localCursor.getString(10).equals("1")?true:false,localCursor.getInt(11),
									localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
									localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
									localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
									localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
									localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),
									localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
									localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
									localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
									localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
		}
		return preventa;
	}
		  
	public ArrayList<Preventa> ObtenerPreventas() throws Exception
	{
		Cursor localCursor;
		ArrayList<Preventa> listadoPreventa = null;
		
		try
		{
			localCursor = new PreventaDAL().ObtenerPreventas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventas: " + localException.getMessage());
			}
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoPreventa = new ArrayList<Preventa>();
			do
			{
				Preventa preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
						localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
						localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
						localCursor.getString(10).equals("1")?true:false,localCursor.getInt(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
						localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
						localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
						localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
						localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),
						localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
						localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
						localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
						localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
				
				listadoPreventa.add(preventa);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoPreventa;
	}
		    
	public ArrayList<Preventa> ObtenerPreventasNoSincronizadas() throws Exception
	 {
		 Cursor localCursor;
		 ArrayList<Preventa> listadoPreventa = null;
		 try
		 {
			 localCursor = new PreventaDAL().ObtenerPreventasNoSincronizadas();
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 
		 if(localCursor.getCount()>0)
		 {
			 listadoPreventa = new ArrayList<Preventa>();
			 do
			 {
				 Preventa preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
							localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
							localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
							localCursor.getString(10).equals("1")?true:false,localCursor.getInt(11),
							localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
							localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
							localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
							localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
							localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),
							localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
							localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
							localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
							localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
				 
				 listadoPreventa.add(preventa);
			 }
			 while(localCursor.moveToNext());
		 }
		 return listadoPreventa;		 
	 }
	
	public ArrayList<Preventa> ObtenerPreventasNoSincronizadasPorRowId(int rowId) throws Exception
	 {
		 Cursor localCursor;
		 ArrayList<Preventa> listadoPreventa = null;
		 try
		 {
			 localCursor = new PreventaDAL().ObtenerPreventasNoSincronizadasPorRowId(rowId);
		 }
		 catch(Exception localException)
		 {
			 if(localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas por rowId: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas por rowId: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 
		 if(localCursor.getCount()>0)
		 {
			 listadoPreventa = new ArrayList<Preventa>();
			 do
			 {
				 Preventa preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
							localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
							localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
							localCursor.getString(10).equals("1")?true:false,localCursor.getInt(11),
							localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
							localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
							localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
							localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
							localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),
							localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
							localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
							localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
							localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
				 
				 listadoPreventa.add(preventa);
			 }
			 while(localCursor.moveToNext());
		 }
		 return listadoPreventa;		 
	 }

	public Preventa OntenerPreventaPor(int id) throws Exception
	 {
		 Cursor localCursor;
		 Preventa preventa = null;
		 try
		 {
			 localCursor = new PreventaDAL().ObtenerPreventaPor(id);
		 }
		 catch (Exception localException)
		 {
			 if(localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas por id: vacio");
			 }
			 else
			 {
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener preventas por id: " + localException.getMessage());
			 } 
			 throw localException;
		 }
		 
	      if(localCursor.getCount() > 0)
	      {
	    	  preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
	    			  		localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), localCursor.getInt(7), 
	    			  		localCursor.getDouble(8), localCursor.getDouble(9), localCursor.getString(10).equals("1")?true:false,
	    			  		localCursor.getInt(11),localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
							localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
							localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
							localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
							localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),
							localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
							localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
							localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
							localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
	    	  
	      }
	      return preventa;
	  }
	
	public long ModificarPreventaClienteIdPorIdyClienteId(int id, int clienteId) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaClienteIdPorIdYClienteId(id, clienteId);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la preventa por id y clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la preventa por id y clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public long ModificarPreventaIdServer(int id,int preventaIdServer) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaIdServer(id, preventaIdServer);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la preventaIdServer de la preventa: " + localException.getMessage());
			} 
			throw localException;
	    }
	}

	public long ModificarPreventaClienteIncentivo(int preventaIdServer, float montoFinal, float descuentoIncentivo) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaClienteIncentivo(preventaIdServer, montoFinal, descuentoIncentivo);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el descuento incentivo de la preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el descuento incentivo de la preventa: " + localException.getMessage());
			}
			throw localException;
		}
	}
	
	public Preventa ObtenerPreventaPorPreventaIdServer(int preventaIdServer) throws Exception
	{
		Cursor localCursor;
		Preventa preventa = null;
		try
		{
			localCursor = new PreventaDAL().ObtenerPreventaPorPreventaIdServer(preventaIdServer);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaIdServer: " + localException.getMessage());
			} 
		    throw localException;
		}
		
		if (localCursor.getCount()>0)
		{
			preventa = new Preventa(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getFloat(4), localCursor.getFloat(5), localCursor.getFloat(6), 
									localCursor.getInt(7), localCursor.getDouble(8), localCursor.getDouble(9), 
									localCursor.getString(10).equals("1")?true:false,localCursor.getInt(11),
									localCursor.getInt(12),localCursor.getInt(13),localCursor.getInt(14),
									localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
									localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
									localCursor.getString(20),localCursor.getString(21).equals("1")?true:false,
									localCursor.getInt(22),localCursor.getInt(23),localCursor.getInt(24),localCursor.getInt(25),
									localCursor.getString(26),localCursor.getString(27),localCursor.getString(28),localCursor.getInt(29),
									localCursor.getInt(30), localCursor.getFloat(31), localCursor.getFloat(32), localCursor.getFloat(33), 
									localCursor.getFloat(34), localCursor.getInt(35), localCursor.getString(36), localCursor.getString(37).equals("1")?true:false,
									localCursor.getString(38).equals("1")?true:false, localCursor.getFloat(39));
		}
		return preventa;
	}
	
	public long ModificarPreventaMontosPorPreventaId(int preventaId, float monto, float descuento, float montoFinal, float descuentoCanal, 
													float descuentoAjuste, float descuentoProntoPago, float descuentoObjetivo) throws Exception
	{
		try
		{
			int i = new PreventaDAL().ModificarPreventaMontosPorPreventaId(preventaId, monto, descuento, montoFinal, descuentoCanal, descuentoAjuste, descuentoProntoPago, descuentoObjetivo);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos de la preventa por preventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos de la preventa por preventaId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public int obtenerNroPreventas() throws Exception
	{
		Cursor localCursor;
		int nroPreventas = 0;
		try
		{
			localCursor = new PreventaDAL().ObtenerNroPreventas();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el numero de preventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el numero de preventas: " + localException.getMessage());
			} 
		    throw localException;
		}
		
		if (localCursor.getCount()>0)
		{
			nroPreventas = localCursor.getInt(0);
		}
		return nroPreventas;
	}
}

