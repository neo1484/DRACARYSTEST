package BLL;

import java.util.ArrayList;
import Clases.SincronizacionVenta;
import DAL.SincronizacionVentaDAL;
import android.database.Cursor;

public class SincronizacionVentaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarSincronizacionesVentaPor(int preventaId,int clienteId,int productoId) throws Exception
	{
	    try
	    {
	    	boolean bool = new SincronizacionVentaDAL().BorrarSincronizacionesVentaPor(preventaId, clienteId, productoId);
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta por preventaId, clienteId, productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta por preventaId, clienteId, productoId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public boolean BorrarSincronizacionesVenta() throws Exception
	{
	    try
	    {
	    	boolean bool = new SincronizacionVentaDAL().BorrarSincronizacionesVenta();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVenta: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public boolean BorrarSincronizacionesVentaNoConfirmadas() throws Exception
	{
	    try
	    {
	    	boolean bool = new SincronizacionVentaDAL().BorrarSincronizacionesVentaNoConfirmadas();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVentaNoConfirmadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las sincronizacionesVentaNoConfirmadas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarSincronizacionVenta(int ventaProductoTempRowId,int preventaId,int clienteId,int distribuidorId,
			int productoId,int promocionId,int cantidad,int cantidadPaquete,float monto,float descuento,float montoFinal,
			int tipoPagoId,int cantidadNueva,int cantidadPaqueteNueva,float montoNuevo,float descuentoNuevo,
			float montoFinalNuevo,int numeroDeItems,int motivoId,int dia,int mes,int anio,
			double latitud,double longitud,boolean confirmado,int tipoSincronizacion,boolean estadoSincronizacion,
			int hora,int minuto,boolean autoventa,String nombreFactura,String nit,boolean nitNuevo,float costo,
			int ventaIdServer,int ventaId,int costoId,String observacion,int precioId,boolean aplicarBonificacion,
			int noAutoventa,int dosificacionId,String tipoNit, float descuentoCanal, float descuentoAjuste, 
			int canalPrecioRutaId, float descuentoProntoPago, int prontoPagoId, float descuentoObjetivo, int formaPagoId, 
			String codTransferencia, boolean fromShopping) throws Exception
	{
		try
		{
			long l = new SincronizacionVentaDAL().InsertarSincronizacionVenta(ventaProductoTempRowId,preventaId,clienteId, 
									distribuidorId,productoId,promocionId,cantidad,cantidadPaquete,monto,descuento,montoFinal,
									tipoPagoId,cantidadNueva,cantidadPaqueteNueva,montoNuevo, descuentoNuevo,montoFinalNuevo,
									numeroDeItems, motivoId, dia,mes, anio, latitud,longitud,confirmado,tipoSincronizacion, 
									estadoSincronizacion,hora,minuto,autoventa,nombreFactura,nit,nitNuevo,costo,ventaIdServer,
									ventaId,costoId,observacion,precioId,aplicarBonificacion,noAutoventa,dosificacionId,tipoNit,
									descuentoCanal, descuentoAjuste, canalPrecioRutaId, descuentoProntoPago, prontoPagoId,
									descuentoObjetivo, formaPagoId, codTransferencia, fromShopping);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionVenta: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<SincronizacionVenta> ObtenerSincronizacionVentaPor(int preventaId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionVentaPor(preventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoSincronizacionVenta = new ArrayList<SincronizacionVenta>();
	        	do
	        	{
	        		SincronizacionVenta localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	        				localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	        				localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        		
	        		listadoSincronizacionVenta.add(localSincronizacionVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoSincronizacionVenta;
	}
	
	public SincronizacionVenta ObtenerSincronizacionVentaNoConfirmadaPorVentaProductoTempRowId(int ventaProductoTempRowId) throws Exception
	{
	    Cursor localCursor = null;
	    SincronizacionVenta localSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionVentaNoConfirmadaPorVentaProductoTempRowId(ventaProductoTempRowId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no confirmadas por ventaProductoTempRowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no confirmadas por ventaProductoTempRowId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        			localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        			localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        			localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        			localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        			localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        			localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
						localCursor.getString(25).equals("1"),localCursor.getInt(26),
						localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
						localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
						localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
						localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
        				localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        }
	    }
	    
	    return localSincronizacionVenta;
	}
	
	public ArrayList<SincronizacionVenta> ObtenerSincronizacionesVentaNosincronizadasPorPreventaId(int preventaId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaNoSincronizadasPorPreventaId(preventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta por preventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoSincronizacionVenta = new ArrayList<SincronizacionVenta>();
	        	do
	        	{
	        		SincronizacionVenta localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        		
	        		listadoSincronizacionVenta.add(localSincronizacionVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoSincronizacionVenta;
	}
	
	public ArrayList<SincronizacionVenta> ObtenerSincronizacionesVentaNosincronizadasPorVentaIdServer(int ventaIdServer) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaNoSincronizadasPorVentaIdServer(ventaIdServer);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por ventaIdServer: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta por ventaIdServer: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoSincronizacionVenta = new ArrayList<SincronizacionVenta>();
	        	do
	        	{
	        		SincronizacionVenta localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        		
	        		listadoSincronizacionVenta.add(localSincronizacionVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoSincronizacionVenta;
	}
	
	public ArrayList<SincronizacionVenta> ObtenerSincronizacionesVentaNosincronizadaPorVentaId(int ventaId) throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaNoSincronizadaPorVentaId(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta por ventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoSincronizacionVenta = new ArrayList<SincronizacionVenta>();
	        	do
	        	{
	        		SincronizacionVenta localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        		
	        		listadoSincronizacionVenta.add(localSincronizacionVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoSincronizacionVenta;
	}
	
	public SincronizacionVenta ObtenerSincronizacionesVentaVentaNoEntregadaPor(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    SincronizacionVenta localSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaVentaNoEntregadaPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta no entregada por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la venta no entregada por clienteId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        		localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
    		        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        }
	    }
	    
	    return localSincronizacionVenta;
	}
	
	public SincronizacionVenta ObtenerSincronizacionesVentasModificadasPor(int preventaId,int productoId,int promocionId) throws Exception
	{
	    Cursor localCursor = null;
	    SincronizacionVenta sincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentasModificadasPor(preventaId, productoId, promocionId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta modificadas por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta modificadas por preventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	sincronizacionVenta =  new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        }
	    }
	    
	    return sincronizacionVenta;
	}
	
	public ArrayList<SincronizacionVenta> ObtenerSincronizacionesVentaNosincronizadasAgrupadas() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaNoSincronizadasAgrupadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta no sincronizadas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoSincronizacionVenta = new ArrayList<SincronizacionVenta>();
	        	do
	        	{
	        		SincronizacionVenta localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        		
	        		listadoSincronizacionVenta.add(localSincronizacionVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoSincronizacionVenta;
	}
	
	public ArrayList<SincronizacionVenta> ObtenerSincronizacionesAutoVentaNosincronizadasAgrupadas() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<SincronizacionVenta> listadoSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesAutoVentaNoSincronizadasAgrupadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta no sincronizadas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	listadoSincronizacionVenta = new ArrayList<SincronizacionVenta>();
	        	do
	        	{
	        		SincronizacionVenta localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
	    	        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        		
	        		listadoSincronizacionVenta.add(localSincronizacionVenta);
	        	} 
	        	while (localCursor.moveToNext());
	        }
	    }
	    
	    return listadoSincronizacionVenta;
	}
	
	public SincronizacionVenta ObtenerSincronizacionVentaModificada(int ventaProductoTempRowId) throws Exception
	{
	    Cursor localCursor = null;
	    SincronizacionVenta localSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaModificada(ventaProductoTempRowId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las sincronizacionesVenta modificadas por id: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los sincronizacionesVenta modificadas por id: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
						localCursor.getString(25).equals("1"),localCursor.getInt(26),
						localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
						localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
						localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
            	    	localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
						localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        }
	    }
	    
	    return localSincronizacionVenta;
	}

	public int ModificarSincronizacionVentaConfirmacionYSincronizacion(int preventaId,int tipoSincronizacion,boolean confirmado, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaConfirmacionYSincronizacion(preventaId, tipoSincronizacion, confirmado, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaSincronizacion(int rowId, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaSincronizacion(rowId, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaVentaIdPorPreventaId(int preventaId, int ventaId) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaVentaIdPorPreventaId(preventaId, ventaId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId por preventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId por preventaId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaVentaIdServerPorVentaId(int ventaId, int ventaIdServer) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaVentaIdServerPorVentaId(ventaId, ventaIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer por ventaId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionAutoventaSincronizacionPorVentaId(int ventaId, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionAutoventaSincronizacionPorVentaId(ventaId, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la autoventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la autoventaSincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaSincronizacionPorVentaIdServer(int ventaIdServer, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaSincronizacionPorVentaIdServer(ventaIdServer, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion por ventaIdServer: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion por ventaIdServer: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaSincronizacionPorVentaProductoTempRowId(int ventaProductoTempRowId, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaSincronizacionPorVentaProductoTempRowId(ventaProductoTempRowId, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion por ventaProductoTempRowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion por ventaProductoTempRowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaVentaIdServerPor(int preventaId,int clienteId,int ventaIdServer) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaVentaIdServerPor(preventaId, clienteId, ventaIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la ventaSincronizacion por preventaIdServer y clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la ventaSincronizacion por preventaIdServer y clienteId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaVentaIdServerPorProducto(int clienteId,int preventaId,int productoId,int ventaIdServer) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaVentaIdServerPorProducto(clienteId, preventaId, productoId, ventaIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer de la ventaSincronizacion por clienteId, preventaId y productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaIdServer de la ventaSincronizacion por clienteId, preventaId y productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaVentaIdServerPorPromocion(int clienteId,int preventaId,int promocionId,int ventaIdServer) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaVentaIdServerPorPromocion(clienteId, preventaId, promocionId, ventaIdServer);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la ventaSincronizacion por clienteId, preventaId y productoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la ventaId de la ventaSincronizacion por clienteId, preventaId y productoId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public SincronizacionVenta ObtenerSincronizacionVentaPorClienteId(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    SincronizacionVenta localSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionVentaPorClienteId(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la sincronizacionVenta por clienteId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        		localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
    		        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        }
	    }
	    
	    return localSincronizacionVenta;
	}
	
	public int ModificarSincronizacionVentaClienteId(int Id,int clienteId) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaClienteId(Id, clienteId);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la ventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el clienteId de la ventaSincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionVentaConfirmacionYSincronizacionPorId(int preventaId,int tipoSincronizacion,
															boolean confirmado, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionVentaConfirmacionYSincronizacionPorId(preventaId, 
	    																	tipoSincronizacion, confirmado, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la ventaSincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public SincronizacionVenta ObtenerSincronizacionesVentaAutoVentaNoEntregadaPor(int clienteId) throws Exception
	{
	    Cursor localCursor = null;
	    SincronizacionVenta localSincronizacionVenta = null;
	    
	    try
	    {
	    	localCursor = new SincronizacionVentaDAL().ObtenerSincronizacionesVentaAutoVentaNoEntregadaPor(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la autoventa no entregada por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la autoventa no entregada por clienteId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        		localSincronizacionVenta = new SincronizacionVenta(localCursor.getInt(0),
	        				localCursor.getInt(1),localCursor.getInt(2),localCursor.getInt(3),localCursor.getInt(4),
	        				localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        				localCursor.getFloat(9),localCursor.getFloat(10),localCursor.getFloat(11),localCursor.getInt(12),
	        				localCursor.getInt(13),localCursor.getInt(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        				localCursor.getFloat(17),localCursor.getInt(18),localCursor.getInt(19),localCursor.getInt(20),
	        				localCursor.getInt(21),localCursor.getInt(22),localCursor.getDouble(23),localCursor.getDouble(24),
							localCursor.getString(25).equals("1"),localCursor.getInt(26),
							localCursor.getString(27).equals("1"),localCursor.getInt(28),localCursor.getInt(29),
							localCursor.getString(30).equals("1"),localCursor.getString(31),localCursor.getString(32),
							localCursor.getString(33).equals("1"),localCursor.getFloat(34),localCursor.getInt(35),
    		        		localCursor.getInt(36),localCursor.getInt(37),localCursor.getString(38),localCursor.getInt(39),
							localCursor.getString(40).equals("1"),localCursor.getInt(41),localCursor.getInt(42),
	                		localCursor.getString(43), localCursor.getFloat(44), localCursor.getFloat(45), localCursor.getInt(46), 
	        				localCursor.getFloat(47), localCursor.getInt(48), localCursor.getFloat(49), localCursor.getInt(50), 
	        				localCursor.getString(51), localCursor.getString(52).equals("1"));
	        }
	    }
	    
	    return localSincronizacionVenta;
	}
	
	public int ModificarSincronizacionAutoventaSincronizacionPorClienteId(int clienteId, boolean sincronizacion) throws Exception
	{
	    try
	    {
	    	int i = new SincronizacionVentaDAL().ModificarSincronizacionAutoventaSincronizacionPorClienteId(clienteId, sincronizacion);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la autoventaSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la autoventaSincronizacion: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
}
