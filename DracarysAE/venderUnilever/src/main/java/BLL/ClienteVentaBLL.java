package BLL;

import java.util.ArrayList;

import android.database.Cursor;

import org.ksoap2.serialization.SoapObject;

import Clases.ClienteVenta;
import Clases.ClienteVentaWSResult;
import DAL.ClienteVentaDAL;

public class ClienteVentaBLL 
{
	MyLogBLL myLog = new MyLogBLL();
	
	public boolean BorrarClientesVenta() throws Exception
	{
		try
		{
			boolean bool = new ClienteVentaDAL().BorrarClientesVenta();
			return bool;
    	}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}
		  
	public long InsertarClienteVenta(ArrayList<ClienteVentaWSResult> clientesVenta) throws Exception
	{
		try
		{
			long l = new ClienteVentaDAL().InsertarClienteVenta(clientesVenta);
			return l;
	    }
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes venta: " + localException.getMessage());
			} 
			throw localException;
	    }
	}

	public long InsertarClienteVenta(int clienteId, String codigo, int clienteTipoNegocioId,
									 String nombreCompleto, double latitud, double longitud, String direccion, String telefono, String celular,
									 int precioListaId, float descuento, int tipoPagoId, int diasPago, float topeCredito, int rutaId,
									 String rutaDescripcion, String razonSocial, boolean autoventa, boolean estadoAtendido,boolean clientePunteoSincronizado,
									 boolean estadoSincronizacion,String nombreFactura,String nit,int diaId,float montoPendienteCredito,int provinciaId,
									 int canalRutaId, String observacion, String pedidoExternoId) throws Exception
	{
		try
		{
			long l = new ClienteVentaDAL().InsertarClienteVenta(clienteId,codigo,clienteTipoNegocioId,
					nombreCompleto,latitud,longitud,direccion,telefono,celular,precioListaId,descuento,tipoPagoId,diasPago,
					topeCredito,rutaId,rutaDescripcion,razonSocial,autoventa,estadoAtendido,clientePunteoSincronizado,
					estadoSincronizacion,nombreFactura,nit,diaId,montoPendienteCredito,provinciaId, canalRutaId, observacion,
					pedidoExternoId);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente venta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente venta: " + localException.getMessage());
			}
			throw localException;
		}
	}
		  
	public ClienteVenta ObtenerClienteVentaPor(int clienteId) throws Exception
	{
		ClienteVenta localClienteVenta = null;
		Cursor localCursor = null;

		try
		{
			localCursor = new ClienteVentaDAL().ObtenerClienteVentaPor(clienteId);
	    }
	    catch (Exception localException)
		{
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente venta por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente venta por clienteId: " + localException.getMessage());
	    	}
	    	throw localException;
		}
		
		if (localCursor.getCount() > 0) 
		{
			localClienteVenta = new ClienteVenta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
												localCursor.getInt(3), localCursor.getString(4), 
												localCursor.getDouble(5), localCursor.getDouble(6), localCursor.getString(7), 
												localCursor.getString(8), localCursor.getString(9), localCursor.getInt(10), 
												localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13), 
												localCursor.getFloat(14), localCursor.getInt(15), localCursor.getString(16),
												localCursor.getString(17), localCursor.getString(18).equals("1")?true:false, 
												localCursor.getString(19).equals("1")?true:false,
												localCursor.getString(20).equals("1")?true:false,
												localCursor.getString(21).equals("1")?true:false,
												localCursor.getString(22),localCursor.getString(23),localCursor.getInt(24),
												localCursor.getFloat(25),localCursor.getInt(26), localCursor.getInt(27),
												localCursor.getString(28), localCursor.getString(29));
		}
		return localClienteVenta;
	}
		  
	public ArrayList<ClienteVenta> ObtenerClientesVenta() throws Exception
	{
		ArrayList<ClienteVenta> listadoClienteVenta = null;
		Cursor localCursor = null;
		try
		{
			localCursor = new ClienteVentaDAL().ObtenerClientesVenta();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes venta: " + localException.getMessage());
	    	}
	    	throw localException;
		}
		
		if(localCursor.getCount()>0)
		{
			listadoClienteVenta = new ArrayList<ClienteVenta>();
			do
			{
				ClienteVenta localClienteVenta = new ClienteVenta(localCursor.getInt(0), localCursor.getInt(1), localCursor.getInt(2), 
						localCursor.getInt(3), localCursor.getString(4), 
						localCursor.getDouble(5), localCursor.getDouble(6), localCursor.getString(7), 
						localCursor.getString(8), localCursor.getString(9), localCursor.getInt(10), 
						localCursor.getFloat(11), localCursor.getInt(12), localCursor.getInt(13), 
						localCursor.getFloat(14), localCursor.getInt(15), localCursor.getString(16),
						localCursor.getString(17), localCursor.getString(18).equals("1")?true:false, 
						localCursor.getString(19).equals("1")?true:false,
						localCursor.getString(20).equals("1")?true:false,
						localCursor.getString(21).equals("1")?true:false,
						localCursor.getString(22),localCursor.getString(23),localCursor.getInt(24),
						localCursor.getFloat(25),localCursor.getInt(26), localCursor.getInt(27),
						localCursor.getString(28), localCursor.getString(29));
				
				listadoClienteVenta.add(localClienteVenta);
			}
			while(localCursor.moveToNext());
		}
		
		return listadoClienteVenta;
	}
	
	public int ModificarClienteVentaEstadoAtendido(int clienteId,boolean estadoAtendido) throws Exception
	{
	    try
	    {
	    	int i = new ClienteVentaDAL().ModificarClienteVentaEstadoAtendido(clienteId, estadoAtendido);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtendido del clienteVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtendido del clienteVenta: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarClienteVentaDatos(int clienteId, String nombres,String paterno,String materno,
					String casada,String direccion,double latitud,double longitud,int tipoNegocioId,String telefono,String celular) throws Exception
	{
	    try
	    {
	    	int i = new ClienteVentaDAL().ModificarClienteVentaDatos(clienteId, nombres, paterno, materno, 
	    															casada, direccion, latitud, longitud,
	    															tipoNegocioId,telefono,celular);
	    	return i;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente venta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente venta: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	
	public int ModificarClienteVentaMontoCredito(int clienteId,float montoFinal) throws Exception
	{
		try
		{
			int i = new ClienteVentaDAL().ModificarClienteVentaMontoCredito(clienteId, montoFinal);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto credito del clienteVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto credito del clienteVenta: " + localException.getMessage());
			}
			throw localException;
		}
	}
	
	public long ModificarClienteVentaSincronizacionDesdeDistribuidor(int id,int clienteId, boolean clientePunteoSincronizado) throws Exception
	{
	    try
	    {
	    	long l = new ClienteVentaDAL().ModificarClienteVentaSincronizacionDesdeDistribuidor(id, clienteId, clientePunteoSincronizado);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteVenta desde el distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del clienteVenta desde el distribuidor: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	}
}
