package BLL;

import java.util.ArrayList;
import Clases.VentaDirecta;
import DAL.VentaDirectaDAL;
import android.database.Cursor;

public class VentaDirectaBLL 
{
MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarVentasDirectas() throws Exception
	{
		try
		{
			boolean bool = new VentaDirectaDAL().BorrarVentasDirectas();
			return bool;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventas directas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas directas: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarVentaDirecta(int ventaDirectaIdServer, int dia, int mes, int anio, int clienteId, float monto,
			float descuento, float montoFinal, int tipoPagoId, double latitud, double longitud, int hora,int minuto,
			String observacion,int empleadoId,String factura,String nit,boolean nitNuevo,int noVentaDirecta,boolean estado,
			boolean aplicarBonificacion,String tipoNit,String ruta,String tipoVisita,int zonaVentaId,int prontoPagoId,
			float descuentoCanal, float descuentoAjuste, float descuentoProntoPago, float descuentoObjetivo, int formaPagoId,
			String codTransferencia, float descuentoIncentivo) throws Exception
	{
		try
		{
			long l = new VentaDirectaDAL().InsertarVentaDirecta(ventaDirectaIdServer,dia,mes,anio,clienteId,monto,descuento,montoFinal,
											tipoPagoId,latitud,longitud,hora,minuto,observacion,empleadoId,factura,nit,nitNuevo,
											noVentaDirecta,estado,aplicarBonificacion,tipoNit,ruta,tipoVisita,zonaVentaId,prontoPagoId,
											descuentoCanal, descuentoAjuste, descuentoProntoPago, descuentoObjetivo, formaPagoId,
											codTransferencia, descuentoIncentivo);
			return l;
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta directa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la venta directa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
		  
	public int ModificarVentaDirectaMontosYDescuentos(int rowId, float monto, float descuento, float montoFinal) throws Exception
	{
		try
		{
			int i = new VentaDirectaDAL().ModificarVentaDirectaMontosYDescuentos(rowId, monto, descuento, montoFinal);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos de la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar los montos y descuentos de la venta directa: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public VentaDirecta ObtenerVentaDirectaPor(int rowId) throws Exception
	{
		Cursor localCursor =null;
		VentaDirecta localVentaDirecta = null;
		try
		{
			localCursor = new VentaDirectaDAL().ObtenerVentaDirectaPor(rowId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVentaDirecta = new VentaDirecta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2), 
											localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5), 
											localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8), 
											localCursor.getInt(9),localCursor.getDouble(10),localCursor.getDouble(11),
											localCursor.getInt(12),localCursor.getInt(13),localCursor.getString(14),
											localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
											localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
											localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
											localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getInt(25),
											localCursor.getInt(26), localCursor.getFloat(27), localCursor.getFloat(28),
											localCursor.getFloat(29), localCursor.getFloat(30), localCursor.getInt(31),
											localCursor.getString(32), localCursor.getFloat(33));
		}
		
		return localVentaDirecta;
	}
	
	public VentaDirecta ObtenerVentaDirectaPorVentaIdServer(int ventaIdServer) throws Exception
	{
		Cursor localCursor =null;
		VentaDirecta localVentaDirecta = null;
		try
		{
			localCursor = new VentaDirectaDAL().ObtenerVentaDirectaPorVentaIdServer(ventaIdServer);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por ventaIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por ventaIdServer: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVentaDirecta = new VentaDirecta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2), 
					localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5), 
					localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8), 
					localCursor.getInt(9),localCursor.getDouble(10),localCursor.getDouble(11),
					localCursor.getInt(12),localCursor.getInt(13),localCursor.getString(14),
					localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
					localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
					localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
					localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getInt(25),
					localCursor.getInt(26), localCursor.getFloat(27), localCursor.getFloat(28),
					localCursor.getFloat(29), localCursor.getFloat(30), localCursor.getInt(31),
					localCursor.getString(32), localCursor.getFloat(33));
		}
		
		return localVentaDirecta;
	}
		  
	public ArrayList<VentaDirecta> ObtenerVentasDirectas() throws Exception
	{
		Cursor localCursor = null;
		ArrayList<VentaDirecta> listadoVentaDirecta = null;
		try
		{
			localCursor = new VentaDirectaDAL().ObtenerVentasDirectas();
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al eobtener las ventas directas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al eobtener las ventas directas: " + localException.getMessage());
			} 
			throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoVentaDirecta = new ArrayList<VentaDirecta>();
			do
	        {
				VentaDirecta localVentaDirecta = new VentaDirecta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2), 
						localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5), 
						localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8), 
						localCursor.getInt(9),localCursor.getDouble(10),localCursor.getDouble(11),
						localCursor.getInt(12),localCursor.getInt(13),localCursor.getString(14),
						localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
						localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
						localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
						localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getInt(25),
						localCursor.getInt(26), localCursor.getFloat(27), localCursor.getFloat(28),
						localCursor.getFloat(29), localCursor.getFloat(30), localCursor.getInt(31),
						localCursor.getString(32), localCursor.getFloat(33));
				
				listadoVentaDirecta.add(localVentaDirecta);
	        } 
			while(localCursor.moveToNext());
		}
		
		return listadoVentaDirecta;
	}

	public int ModificarVentaDirectaPorVentaIdServer(int rowId,int ventaIdServer) throws Exception
	{
		try
		{
			int i = new VentaDirectaDAL().ModificarVentaDirectaPorVentaIdServer(rowId, ventaIdServer);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaId de la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaId de la venta directa: " + localException.getMessage());
			} 
			throw localException;
	    }
	}

	public int ModificarVentaDirectaDescuentoIncentivo(int ventaDiectaIdServer, float montoFinal, float descuentoIncentivo) throws Exception
	{
		try
		{
			int i = new VentaDirectaDAL().ModificarVentaDirectaDescuentoIncentivo(ventaDiectaIdServer, montoFinal, descuentoIncentivo);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto final y descunto incentivo por ventaDirectaIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto final y descunto incentivo por ventaDirectaIdServer: " + localException.getMessage());
			}
			throw localException;
		}
	}

	public VentaDirecta ObtenerVentaDirectaPorClienteId(int clienteId) throws Exception
	{
		Cursor localCursor =null;
		VentaDirecta localVentaDirecta = null;
		try
		{
			localCursor = new VentaDirectaDAL().ObtenerVentaDirectaPorClienteId(clienteId);
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta directa por clienteId: " + localException.getMessage());
			} 
			throw localException;
	    }
		
		if(localCursor.getCount()>0)
		{
			localVentaDirecta = new VentaDirecta(localCursor.getInt(0),localCursor.getInt(1),localCursor.getInt(2), 
					localCursor.getInt(3),localCursor.getInt(4),localCursor.getInt(5), 
					localCursor.getFloat(6),localCursor.getFloat(7),localCursor.getFloat(8), 
					localCursor.getInt(9),localCursor.getDouble(10),localCursor.getDouble(11),
					localCursor.getInt(12),localCursor.getInt(13),localCursor.getString(14),
					localCursor.getInt(15),localCursor.getString(16),localCursor.getString(17),
					localCursor.getString(18).equals("1")?true:false,localCursor.getInt(19),
					localCursor.getString(20).equals("1")?true:false,localCursor.getString(21).equals("1")?true:false,
					localCursor.getString(22),localCursor.getString(23),localCursor.getString(24),localCursor.getInt(25),
					localCursor.getInt(26), localCursor.getFloat(27), localCursor.getFloat(28),
					localCursor.getFloat(29), localCursor.getFloat(30), localCursor.getInt(31),
					localCursor.getString(32), localCursor.getFloat(33));
		}
		
		return localVentaDirecta;
	}
	
	public int ModificarVentaDirectaNoSincronizadaPor(int rowId) throws Exception
	{
		try
		{
			int i = new VentaDirectaDAL().ModificarVentaDirectaNoSincronizadaPor(rowId);
			return i;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaId, de la venta directa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar ventaIdServer por ventaId, de la venta directa: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
}
