package BLL;

import java.util.ArrayList;

import Clases.Factura;
import DAL.FacturaDAL;
import android.database.Cursor;

public class FacturaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public FacturaBLL(){}
	  
	public boolean BorrarFacturas() throws Exception
	{
	    try
	    {
	    	boolean bool = new FacturaDAL().BorrarFacturas();
	    	return bool;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturass: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las facturas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	  
	public long InsertarFactura(String numero, String nombre, String nit, int fechaDia,int fechaMes, int fechaAnio,
								int fechaHora, int fechaMinuto,int fechaLimiteEmisionDia, int fechaLimiteEmisionMes,
								int fechaLimiteEmisionAnio, int fechaLimiteEmisionHora,int fechaLimiteEmisionMinuto, 
								float montoTotal, float descuento,float montoFinal, String montoPalabras,
								String codigoAutorizacion, String codigoControl,String facturaTipoId, int almacenId, 
								boolean anulada,int anulacionUsuarioId, String anulacionMotivo,int anulacionFechaDia, 
								int anulacionFechaMes,int anulacionFechaAnio, int dosificacionId, int empleadoId,
								long qrTamano, String qrExtension, String qrBinario,String qrMimeType, int clienteId, 
								int numeroItems,boolean sincronizacion,boolean impreso,int ventaId,int serverVentaId,
								boolean nitNuevo,int noFactura,String tipoNit)throws Exception
	{
		try
		{
			long l = new FacturaDAL().InsertarFactura(numero, nombre, nit, fechaDia, fechaMes, fechaAnio, fechaHora, 
													fechaMinuto, fechaLimiteEmisionDia, fechaLimiteEmisionMes, 
													fechaLimiteEmisionAnio, fechaLimiteEmisionHora, fechaLimiteEmisionMinuto,
													montoTotal, descuento, montoFinal, montoPalabras, codigoAutorizacion,
													codigoControl, facturaTipoId, almacenId, anulada, anulacionUsuarioId,
													anulacionMotivo, anulacionFechaDia, anulacionFechaMes, anulacionFechaAnio,
													dosificacionId, empleadoId, qrTamano, qrExtension, qrBinario, qrMimeType, 
													clienteId, numeroItems, sincronizacion, impreso, ventaId, serverVentaId,
													nitNuevo,noFactura,tipoNit);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar la factura: " + localException.getMessage());
			} 
			throw localException;
		}
	}
	   
	public ArrayList<Factura> ObtenerFacturas() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<Factura> localListadoFactura = null;
	    
	    try
	    {
	    	localCursor = new FacturaDAL().obtenerFacturas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localListadoFactura = new ArrayList<Factura>();
	        	do
	        	{
	        		Factura localFactura = new Factura(localCursor.getInt(0),
	        			localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getInt(4),
	        			localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        			localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
	        			localCursor.getInt(13),localCursor.getFloat(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        			localCursor.getString(17),localCursor.getString(18),localCursor.getString(19),localCursor.getString(20),
	        			localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,localCursor.getInt(23),
	        			localCursor.getString(24),localCursor.getInt(25),localCursor.getInt(26),localCursor.getInt(27),
	        			localCursor.getInt(28),localCursor.getInt(29),localCursor.getLong(30),localCursor.getString(31),
	        			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	        			localCursor.getString(36).equals("1")?true:false,localCursor.getString(37).equals("1")?true:false,
	        			localCursor.getInt(38),localCursor.getInt(39),localCursor.getString(40).equals("1")?true:false,
	        			localCursor.getInt(41),localCursor.getString(42));
	        		
	        		localListadoFactura.add(localFactura);
	        	}
	        	while(localCursor.moveToNext());
	        }
	    }
	    
	    return localListadoFactura;
	}
	
	public ArrayList<Factura> ObtenerFacturasNoSincronizadas() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<Factura> localListadoFactura = null;
	    
	    try
	    {
	    	localCursor = new FacturaDAL().obtenerFacturasNoSincronizadas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localListadoFactura = new ArrayList<Factura>();
	        	do
	        	{
	        		Factura localFactura = new Factura(localCursor.getInt(0),
	        			localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getInt(4),
	        			localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        			localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
	        			localCursor.getInt(13),localCursor.getFloat(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        			localCursor.getString(17),localCursor.getString(18),localCursor.getString(19),localCursor.getString(20),
	        			localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,localCursor.getInt(23),
	        			localCursor.getString(24),localCursor.getInt(25),localCursor.getInt(26),localCursor.getInt(27),
	        			localCursor.getInt(28),localCursor.getInt(29),localCursor.getLong(30),localCursor.getString(31),
	        			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	        			localCursor.getString(36).equals("1")?true:false,localCursor.getString(37).equals("1")?true:false,
	        			localCursor.getInt(38),localCursor.getInt(39),localCursor.getString(40).equals("1")?true:false,
	    	        	localCursor.getInt(41),localCursor.getString(42));
	        		
	        		localListadoFactura.add(localFactura);
	        	}
	        	while(localCursor.moveToNext());
	        }
	    }
	    
	    return localListadoFactura;
	}
	
	public ArrayList<Factura> ObtenerFacturasNoImpresas() throws Exception
	{
	    Cursor localCursor = null;
	    ArrayList<Factura> localListadoFactura = null;
	    
	    try
	    {
	    	localCursor = new FacturaDAL().obtenerFacturasNoImpresas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no impresas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no impresas: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localListadoFactura = new ArrayList<Factura>();
	        	do
	        	{
	        		Factura localFactura = new Factura(localCursor.getInt(0),
	        			localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getInt(4),
	        			localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        			localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
	        			localCursor.getInt(13),localCursor.getFloat(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        			localCursor.getString(17),localCursor.getString(18),localCursor.getString(19),localCursor.getString(20),
	        			localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,localCursor.getInt(23),
	        			localCursor.getString(24),localCursor.getInt(25),localCursor.getInt(26),localCursor.getInt(27),
	        			localCursor.getInt(28),localCursor.getInt(29),localCursor.getLong(30),localCursor.getString(31),
	        			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	        			localCursor.getString(36).equals("1")?true:false,localCursor.getString(37).equals("1")?true:false,
	        			localCursor.getInt(38),localCursor.getInt(39),localCursor.getString(40).equals("1")?true:false,
	    	        	localCursor.getInt(41),localCursor.getString(42));
	        		
	        		localListadoFactura.add(localFactura);
	        	}
	        	while(localCursor.moveToNext());
	        }
	    }
	    
	    return localListadoFactura;
	}

	public Factura ObtenerFacturaPorVentaId(int ventaId) throws Exception
	{
	    Cursor localCursor = null;
	    Factura localFactura = null;
	    
	    try
	    {
	    	localCursor = new FacturaDAL().obtenerFacturaPorVentaId(ventaId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por ventaId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localFactura = new Factura(localCursor.getInt(0),
	        			localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getInt(4),
	        			localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        			localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
	        			localCursor.getInt(13),localCursor.getFloat(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        			localCursor.getString(17),localCursor.getString(18),localCursor.getString(19),localCursor.getString(20),
	        			localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,localCursor.getInt(23),
	        			localCursor.getString(24),localCursor.getInt(25),localCursor.getInt(26),localCursor.getInt(27),
	        			localCursor.getInt(28),localCursor.getInt(29),localCursor.getLong(30),localCursor.getString(31),
	        			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	        			localCursor.getString(36).equals("1")?true:false,localCursor.getString(37).equals("1")?true:false,
	        			localCursor.getInt(38),localCursor.getInt(39),localCursor.getString(40).equals("1")?true:false,
	    	        	localCursor.getInt(41),localCursor.getString(42));
	        }
	    }
	    
	    return localFactura;
	}
	
	public Factura ObtenerFacturaPorRowId(int rowId) throws Exception
	{
	    Cursor localCursor = null;
	    Factura localFactura = null;
	    
	    try
	    {
	    	localCursor = new FacturaDAL().obtenerFacturaPorRowId(rowId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la factura por rowId: " + localException.getMessage());
	    	} 
	      	throw localException;
	    }
	    
	    if(localCursor != null)
	    {
	        if (localCursor.getCount() > 0)
	        {
	        	localFactura = new Factura(localCursor.getInt(0),
	        			localCursor.getString(1),localCursor.getString(2),localCursor.getString(3),localCursor.getInt(4),
	        			localCursor.getInt(5),localCursor.getInt(6),localCursor.getInt(7),localCursor.getInt(8),
	        			localCursor.getInt(9),localCursor.getInt(10),localCursor.getInt(11),localCursor.getInt(12),
	        			localCursor.getInt(13),localCursor.getFloat(14),localCursor.getFloat(15),localCursor.getFloat(16),
	        			localCursor.getString(17),localCursor.getString(18),localCursor.getString(19),localCursor.getString(20),
	        			localCursor.getInt(21),localCursor.getString(22).equals("1")?true:false,localCursor.getInt(23),
	        			localCursor.getString(24),localCursor.getInt(25),localCursor.getInt(26),localCursor.getInt(27),
	        			localCursor.getInt(28),localCursor.getInt(29),localCursor.getLong(30),localCursor.getString(31),
	        			localCursor.getString(32),localCursor.getString(33),localCursor.getInt(34),localCursor.getInt(35),
	        			localCursor.getString(36).equals("1")?true:false,localCursor.getString(37).equals("1")?true:false,
	        			localCursor.getInt(38),localCursor.getInt(39),localCursor.getString(40).equals("1")?true:false,
	    	        	localCursor.getInt(41),localCursor.getString(42));
	        }
	    }
	    
	    return localFactura;
	}

	public int ModificarEstadoImpresion(int ventaId, boolean estado) throws Exception
	{
		try
	    {
			int i = new FacturaDAL().ModificarEstadoImpresion(ventaId, estado);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado impresion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estado impresion: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public int ModificarSincronizacionFactura(int rowId, boolean estado) throws Exception
	{
		try
	    {
			int i = new FacturaDAL().ModificarFacturaSincronizacion(rowId, estado);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la factura: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion de la factura: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public int ModificarFacturaServerVentaIdPorVentaId(int ventaId,int serverVentaId) throws Exception
	{
		try
	    {
			int i = new FacturaDAL().ModificarFacturaServerVentaIdPorVentaId(ventaId, serverVentaId);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar serverVentaId por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar serverVentaId por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
	
	public int ModificarFacturaDatosFacturaPorVentaId(int ventaId,String nombreFactura,String nit,boolean nitNuevo) throws Exception
	{
		try
	    {
			int i = new FacturaDAL().ModificarFacturaDatosFacturaPorVentaId(ventaId, nombreFactura, nit, nitNuevo);
			return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar nombreFactura y nit por ventaId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar nombreFactura y nit por ventaId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
