package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.ApkClienteWS;
import Clases.ClientePreventa;
import Utilidades.AdministradorDB;

public class ClientePreventaDAL 
{
	AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientesPreventa() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesPreventa();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes preventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes preventas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public boolean BorrarClientesSinPreventa() throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	db.borrarClientesSinPreventa();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes sin preventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes sin preventas: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarClientePreventa(ArrayList<ApkClienteWS> clientesPreventa) throws Exception
	{
		db.OpenDB();
	    try
	    {
	    	long l = db.insertarClientePreventa(clientesPreventa);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes preventa: " + localException.getMessage());
	    	} 
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	  }

	public long InsertarClientePreventa(int clienteId, String codigo, int clienteTipoNegocioId, String nombreCompleto,
										double latitud, double longitud, String direccion, String telefono, String celular, int precioListaId,
										float descuento, int tipoPagoId, int diasPago, float topeCredito, int rutaId, String rutaDescripcion,
										String razonSocial, boolean autoventa, boolean estadoAtendido, boolean clientePunteoSincronizado,
										boolean sincronizacion,String nombres,String paterno,String materno,String apellidoCasada,int tipoNegocioId,
										int zonaId,String nombreFactura,String nit,int diaId,String referencia,float promedioVenta,String ci,
										String contacto,float montoPendienteCredito,int provinciaId,String ruta,String tipoVisita,int zonaVentaId,
										String zona, String mercado, int canalRutaId, String observacion, boolean verificadoFoto) throws Exception
	{
		db.OpenDB();
		try
		{
			long l = db.insertarClientePreventa(clienteId,codigo,clienteTipoNegocioId,nombreCompleto,latitud,longitud,
					direccion,telefono,celular,precioListaId,descuento,tipoPagoId,diasPago,topeCredito,rutaId,rutaDescripcion,
					razonSocial,autoventa,estadoAtendido,clientePunteoSincronizado,sincronizacion,nombres,paterno,materno,
					apellidoCasada,tipoNegocioId,zonaId,nombreFactura,nit,diaId,referencia,promedioVenta,ci,contacto,
					montoPendienteCredito,provinciaId,ruta,tipoVisita,zonaVentaId, zona, mercado, canalRutaId, observacion,
					verificadoFoto);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente preventa: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	  public Cursor ObtenerClientePreventaPor(int clienteId) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesPreventaPor(clienteId);
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente preventa por clienteIdo: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente preventa por clienteId: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClientesPreventa()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesPreventa();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa: " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor obtenerClientesPreventaNoAtendidos()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesPreventaNoAtendidos();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no atendidos: " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClientePreventaEstadoAtencion(int clienteId, boolean estadoAtencion) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      int i = db.modificarClientePreventaEstadoAtencion(clienteId, estadoAtencion);
		      return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el estadoAtencion del clientePreventa: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClientePreventaSincronizacionDesdeVendedor(int id,int clienteId, boolean clientePunteoSincronizado) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
		      long i = db.modificarClientePreventaSincronizacionDesdeVendedor(id, clienteId, clientePunteoSincronizado);
		      return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionPunteo del clientePreventa desde el vendedor: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacionPunteo del clientePreventa desde el vendedor: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClientePreventaNombre(int clienteId,String nombres,String paterno,String materno,
			  								String apellidoCasada,String direccion,String referencia) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long i = db.modificarClientePreventaNombre(clienteId,nombres,paterno,materno,apellidoCasada,direccion,referencia);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClientePreventaMontoCredito(int clienteId,float montoFinal) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long i = db.modificarClientePreventaMontoCredito(clienteId, montoFinal);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto credito del clientePreventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar el monto credito del clientePreventa: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClientePreventaDatos(int clienteId,String nombres,String paterno,String materno,
				String apellidoCasada,String direccion,String referencia,double latitud,double longitud,
				int tipoNegocioId,String telefono,String celular) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long i = db.modificarClientePreventaDatos(clienteId,nombres,paterno,materno,apellidoCasada,direccion,referencia,
					  									latitud,longitud,tipoNegocioId,telefono,celular);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del clientePreventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del clientePreventa: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public long ModificarClientePreventaNombreSincronizacion(int clienteId,boolean estadoSincronizacion) throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  long i = db.modificarClientePreventaNombreSincronizacion(clienteId,estadoSincronizacion);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del nombre del clientePreventa: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del nombre del clientePreventa: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor ObtenerClientesPreventaNoSincronizados()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerClientesPreventaNoSincronizados();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no sincronizados: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa no sincronizados: " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }	 
	  
	  public long ModificarClienteDatosCenso(int clienteId,String codigo,String referencia,int tipoNegocioId,String contacto,
				double latitud,double longitud) throws Exception
	  {	
		  db.OpenDB();
		  try
		  {
			  long i = db.modificarClienteDatosCenso(clienteId, codigo, referencia, tipoNegocioId, contacto, latitud, longitud);
			  return i;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente: vacio");
			  }
			  else
			  {
				  myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente: " + localException.getMessage());
			  } 
			  throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
	  
	  public Cursor obtenerNroClientesPreventa()throws Exception
	  {
		  db.OpenDB();
		  try
		  {
			  Cursor localCursor = db.obtenerNroClientesPreventa();
			  return localCursor;
		  }
		  catch (Exception localException)
		  {
			  if(localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes preventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes preventa: " + localException.getMessage());
				} 
			  	throw localException;
		  }
		  finally
		  {
			  db.CloseDB();
		  }
	  }
}
