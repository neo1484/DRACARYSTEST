package BLL;

import java.util.ArrayList;

import android.database.Cursor;
import Clases.Venta;
import DAL.VentaDAL;

public class VentaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentas() throws Exception
	{
		try
		{
			boolean bool = new VentaDAL().BorrarVentas();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVenta(int ventaIdServer, int dia, int mes, int anio, int clienteId, int distribuidorId, float monto,
			float descuento, float montoFinal, int preventaId, int tipoPagoId, double latitud, 
			double longitud, boolean diferencia, boolean estadoSincronizacion,int hora,int minuto,
			String observacion,boolean aplicarBonificacion,int dosificacionId,String tipoNit,float descuentoCanal, 
			float descuentoAjuste, int prontoPagoId, float descuentoProntoPago, float descuentoObjetivo, int formaPagoId,
			String codTransferencia, boolean fromApk, boolean fromShopp) throws Exception
	{
		try
		{
			long l = new VentaDAL().InsertarVenta(ventaIdServer,dia,mes,anio,clienteId,distribuidorId,monto,descuento,montoFinal,
												preventaId,tipoPagoId,latitud,longitud,diferencia,estadoSincronizacion,
												hora,minuto,observacion,aplicarBonificacion,dosificacionId,tipoNit, descuentoCanal,
												descuentoAjuste, prontoPagoId, descuentoProntoPago, descuentoObjetivo, formaPagoId,
												codTransferencia, fromApk, fromShopp);
			return l;
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
		  
	public int ModificarVenta(int id, int ventaId, boolean estadoSincronizacion) throws Exception
	{
		try
		{
			int i = new VentaDAL().ModificarVenta(id,ventaId,estadoSincronizacion);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public int ModificarVentaMontosYDescuentos(int id, float monto, float descuento, float montoFinal) throws Exception
	{
		try
		{
			int i = new VentaDAL().ModificarVentaMontosYDescuentos(id, monto, descuento, montoFinal);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public Venta ObtenerVentaPor(int id) throws Exception
	{
		Cursor localCursor =null;
		Venta localVenta = null;
		try
		{
			localCursor = new VentaDAL().ObtenerVentaPor(id);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVenta = new Venta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6), localCursor.getFloat(7), 
									localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getInt(10), localCursor.getInt(11), 
									localCursor.getDouble(12), localCursor.getDouble(13), Boolean.parseBoolean(localCursor.getString(14)), 
									localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),localCursor.getInt(17),
									localCursor.getString(18),localCursor.getString(19).equals("1")?true:false,localCursor.getInt(20),
									localCursor.getString(21), localCursor.getFloat(22), localCursor.getFloat(23), localCursor.getInt(24), 
									localCursor.getFloat(25), localCursor.getFloat(26), localCursor.getInt(27), localCursor.getString(28),
									localCursor.getString(29).equals("1")?true:false, localCursor.getString(30).equals("1")?true:false);
		}
		
		return localVenta;
	}
	
	public Venta ObtenerVentaPorVentaId(int ventaId) throws Exception
	{
		Cursor localCursor =null;
		Venta localVenta = null;
		try
		{
			localCursor = new VentaDAL().ObtenerVentaPorVentaId(ventaId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por ventaId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por ventaId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVenta = new Venta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6), localCursor.getFloat(7), 
									localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getInt(10), localCursor.getInt(11), 
									localCursor.getDouble(12), localCursor.getDouble(13), Boolean.parseBoolean(localCursor.getString(14)), 
									localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),localCursor.getInt(17),
									localCursor.getString(18),localCursor.getString(19).equals("1")?true:false,localCursor.getInt(20),
									localCursor.getString(21), localCursor.getFloat(22), localCursor.getFloat(23), localCursor.getInt(24), 
									localCursor.getFloat(25), localCursor.getFloat(26), localCursor.getInt(27), localCursor.getString(28),
									localCursor.getString(29).equals("1")?true:false, localCursor.getString(30).equals("1")?true:false);
		}
		
		return localVenta;
	}
		  
	public ArrayList<Venta> ObtenerVentas() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Venta> listadoVenta=null;
		try
		{
			localCursor = new VentaDAL().ObtenerVentas();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al eobtener las ventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al eobtener las ventas: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVenta = new ArrayList<Venta>();
			do
	        {
				Venta localVenta = new Venta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
											localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
											localCursor.getInt(6), localCursor.getFloat(7), localCursor.getFloat(8), 
											localCursor.getFloat(9), localCursor.getInt(10), localCursor.getInt(11), 
											localCursor.getDouble(12), localCursor.getDouble(13),localCursor.getString(14).equals("1")?true:false, 
											localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),localCursor.getInt(17),
											localCursor.getString(18),localCursor.getString(19).equals("1")?true:false,localCursor.getInt(20),
											localCursor.getString(21), localCursor.getFloat(22), localCursor.getFloat(23), localCursor.getInt(24), 
											localCursor.getFloat(25), localCursor.getFloat(26), localCursor.getInt(27), localCursor.getString(28),
											localCursor.getString(29).equals("1")?true:false, localCursor.getString(30).equals("1")?true:false);
				listadoVenta.add(localVenta);
	        } 
			while(localCursor.moveToNext());
		}
		
		return listadoVenta;
	}
	
	public ArrayList<Venta> ObtenerVentasNoSincronizadas() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<Venta> listadoVenta=null;
		try
		{
			localCursor = new VentaDAL().ObtenerVentasNoSincronizadas();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al eobtener las ventas no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al eobtener las ventas no sincronizadas: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVenta = new ArrayList<Venta>();
			do
	        {
				Venta localVenta = new Venta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
											localCursor.getInt(3), localCursor.getInt(4), localCursor.getInt(5), 
											localCursor.getInt(6), localCursor.getFloat(7), localCursor.getFloat(8), 
											localCursor.getFloat(9), localCursor.getInt(10), localCursor.getInt(11), 
											localCursor.getDouble(12), localCursor.getDouble(13),localCursor.getString(14).equals("1")?true:false, 
											localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),localCursor.getInt(17),
											localCursor.getString(18),localCursor.getString(19).equals("1")?true:false,localCursor.getInt(20),
											localCursor.getString(21), localCursor.getFloat(22), localCursor.getFloat(23), localCursor.getInt(24), 
											localCursor.getFloat(25), localCursor.getFloat(26), localCursor.getInt(27), localCursor.getString(28),
											localCursor.getString(29).equals("1")?true:false, localCursor.getString(30).equals("1")?true:false);
				listadoVenta.add(localVenta);
	        } 
			while(localCursor.moveToNext());
		}
		
		return listadoVenta;
	}

	public int ModificarVentaIdServer(int rowId,int ventaIdServer) throws Exception
	{
		try
		{
			int i = new VentaDAL().ModificarVentaIdServer(rowId, ventaIdServer);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaRowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaRowId: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public int ModificarSincronizacionVenta(int preventaId,int ventaId,boolean estadoSincronizacion) throws Exception
	{
		try
		{
			int i = new VentaDAL().ModificarSincronizacionVenta(preventaId,ventaId,estadoSincronizacion);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
	
	public int ModificarVentaSincronizacion(int ventaId, boolean estadoSincronizacion) throws Exception
	{
		try
		{
			int i = new VentaDAL().ModificarVentaSincronizacion(ventaId, estadoSincronizacion);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}

	public Venta ObtenerVentaPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor =null;
		Venta localVenta = null;
		try
		{
			localCursor = new VentaDAL().ObtenerVentaPorClienteId(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta por clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVenta = new Venta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), localCursor.getInt(3), 
									localCursor.getInt(4), localCursor.getInt(5), localCursor.getInt(6), localCursor.getFloat(7), 
									localCursor.getFloat(8), localCursor.getFloat(9), localCursor.getInt(10), localCursor.getInt(11), 
									localCursor.getDouble(12), localCursor.getDouble(13), Boolean.parseBoolean(localCursor.getString(14)), 
									localCursor.getString(15).equals("1")?true:false,localCursor.getInt(16),localCursor.getInt(17),
									localCursor.getString(18),localCursor.getString(19).equals("1")?true:false,localCursor.getInt(20),
									localCursor.getString(21), localCursor.getFloat(22), localCursor.getFloat(23), localCursor.getInt(24), 
									localCursor.getFloat(25), localCursor.getFloat(26), localCursor.getInt(27), localCursor.getString(28),
									localCursor.getString(29).equals("1")?true:false, localCursor.getString(30).equals("1")?true:false);
		}
		
		return localVenta;
	}
	
	public int ModificarVentaClienteIdPorIdYClienteId(int Id,int clienteId) throws Exception
	{
		try
		{
			int i = new VentaDAL().ModificarVentaClienteIdPorIdYClienteId(Id, clienteId);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
}
