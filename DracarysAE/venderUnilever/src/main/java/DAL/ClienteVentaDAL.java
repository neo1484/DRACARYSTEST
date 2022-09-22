package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.ClienteVentaWSResult;
import Utilidades.AdministradorDB;

public class ClienteVentaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesVenta() throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	db.borrarClientesVenta();
	    	return true;
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
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarClienteVenta(ArrayList<ClienteVentaWSResult> clientesVenta) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarClienteVenta(clientesVenta);
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
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long InsertarClienteVenta(int clienteId, String codigo, int clienteTipoNegocioId,
									 String nombreCompleto, double latitud, double longitud, String direccion, String telefono, String celular,
									 int precioListaId, float descuento, int tipoPagoId, int diasPago, float topeCredito, int rutaId,
									 String rutaDescripcion, String razonSocial, boolean autoventa, boolean estadoAtendido,
									 boolean clientePunteoSincronizado,boolean estadoSincronizacion,String nombreFactura,String nit,
									 int diaId,float montoPendienteCredito,int provinciaId,int canalRutaId, String observacion,
									 String pedidoExternoId) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarClienteVenta(clienteId,codigo,clienteTipoNegocioId,nombreCompleto,latitud,
					longitud,direccion,telefono,celular,precioListaId,descuento,tipoPagoId,diasPago,topeCredito,rutaId,
					rutaDescripcion,razonSocial,autoventa,estadoAtendido,clientePunteoSincronizado,estadoSincronizacion,
					nombreFactura,nit,diaId,montoPendienteCredito,provinciaId, canalRutaId, observacion, pedidoExternoId);
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
		finally
		{
			db.CloseDB();
		}
	}
	  
	public Cursor ObtenerClienteVentaPor(int clienteId) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesVentaPor(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente venta por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente venta por clienteId: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClientesVenta()throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesVenta();
	    	return localCursor;
	    }
	    catch (Exception localException)
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
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public int ModificarClienteVentaEstadoAtendido(int clienteId, boolean estadoAtendido)throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarClienteVentaEstadoAtendido(clienteId, estadoAtendido);
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
		finally
		{
			db.CloseDB();
		}
	}
	
	public int ModificarClienteVentaDatos(int clienteId, String nombres,String paterno,String materno,
			String casada,String direccion,double latitud,double longitud,int tipoNegocioId,String telefono,String celular)throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarClienteVentaDatos(clienteId, nombres, paterno, materno, casada, direccion, latitud, longitud,
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
		finally
		{
			db.CloseDB();
		}
	}
	
	public int ModificarClienteVentaMontoCredito(int clienteId,float montoFinal)throws Exception
	{
		db.OpenDB();
		try
		{
			int i = db.modificarClienteVentaMontoCredito(clienteId, montoFinal);
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
		finally
		{
			db.CloseDB();
		}
	}
	
	public long ModificarClienteVentaSincronizacionDesdeDistribuidor(int id,int clienteId, boolean clientePunteoSincronizado) throws Exception
	{
		db.OpenDB();
		try
		{
			long i = db.modificarClienteVentaSincronizacionDesdeDistribuidor(id, clienteId, clientePunteoSincronizado);
			return i;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionPunteo del clienteVenta desde el distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionPunteo del clienteVenta desde el distribuidor: " + localException.getMessage());
			} 
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
}
