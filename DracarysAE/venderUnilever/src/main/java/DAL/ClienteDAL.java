package DAL;

import android.database.Cursor;

import com.detesim.venderunilever.Login;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.ClienteWSResult;
import Clases.Fecha;
import Utilidades.AdministradorDB;

public class ClienteDAL 
{
	static AdministradorDB db = new AdministradorDB(Login.getContexto());
	MyLogBLL myLog = new MyLogBLL();
	  
	public boolean BorrarClientePor(int id) throws Exception
	{
		db.OpenDB();
	    
		try
	    {
			db.borrarClientePor(id);
			return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
  	}
	  
	public boolean BorrarClientePorClienteId(int clienteId) throws Exception
  	{
		db.OpenDB();
	    
		try
	    {
			db.borrarClientePorClienteId(clienteId);
			return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar el cliente por clienteId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
  	}
	  
	public boolean BorrarClientes()throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	db.borrarClientes();
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public long InsertarCliente(ArrayList<ClienteWSResult> clientes, Fecha fecha)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	long lng = db.insertarCliente(clientes, fecha);
	    	return lng;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long InsertarCliente(int clienteId, String codigo, String nombres, String paterno, String materno,
								String apellidoCasada, String nombreCompleto, boolean tieneCi, String ci, String expedidoId, boolean tieneCelular,
								String celular, int tipoCalleId, String direccion, String numero, String referencia, int entreTipoCalle1Id,
								String calle1, int entreTipoCalle2Id, String calle2, String edificio, String edificioPiso, String edificioNumero,
								String manzano, String uv, String nombreFactura, String nit, String razonSocial, String contacto, boolean tieneTelefono,
								String telefono, String paginaWeb, String email, int tipoNegocioId, int zonaId,double latitud, double longitud,
								int creadorId, double latitudCreador, double longitudCreador, int tipoPagoId,int diasPago, float topeCredito,
								int dia, int mes, int anio, int hora, int minuto, boolean verificado,boolean completo, boolean sincronizacion,
								int rutaId,int rutaDiaId,int mercadoId,boolean activo,boolean editado,int tatId,String tipoNit,boolean clienteVisita,
								int zonaMercadoId,int a,int b,int c,int d,int e,int f,int g,int h,int i,int j,int k,int l,int m,int n,int o,int p,int q,
								int r,float secuenciaPreventa,float secuenciaVenta,int provinciaId,int precioListaNombreId,String ruta,String tipoVisita,
								int zonaVentaId, int canalRutaId)throws Exception
	{
		db.OpenDB();

		try
		{
			long lng = db.insertarCliente(clienteId, codigo, nombres, paterno, materno, apellidoCasada, nombreCompleto, tieneCi, ci, expedidoId,
					tieneCelular, celular, tipoCalleId, direccion, numero, referencia, entreTipoCalle1Id, calle1, entreTipoCalle2Id,
					calle2, edificio, edificioPiso, edificioNumero, manzano, uv, nombreFactura, nit, razonSocial, contacto, tieneTelefono,
					telefono, paginaWeb, email, tipoNegocioId, zonaId, latitud, longitud, creadorId, latitudCreador, longitudCreador,
					tipoPagoId, diasPago, topeCredito, dia, mes, anio, hora, minuto, verificado, completo, sincronizacion, rutaId, rutaDiaId,
					mercadoId, activo, editado, tatId, tipoNit, clienteVisita, zonaMercadoId, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,
					q, r, secuenciaPreventa, secuenciaVenta, provinciaId, precioListaNombreId,ruta,tipoVisita,zonaVentaId,canalRutaId);
			return lng;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	  
	public long ModificarSincronizacionCliente(int id, int clienteId, boolean sincronizacion)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	long l = db.modificarSincronizacionCliente(id,clienteId,sincronizacion);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el cliente: " + localException.getMessage());
	    	}
	    	
	    	throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	
	public long ModificarSincronizacionClienteEditadoPorClienteId(int clienteId, boolean sincronizacion,boolean editado)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	long l = db.modificarSincronizacionClienteEditadoPorClienteId(clienteId,sincronizacion,editado);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del cliente: " + localException.getMessage());
	    	}
	    	
	    	throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClientePor(int clienteId)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesPor(clienteId);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente por clienteId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClientePorRowId(int id)throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesPorRowId(id);
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente por rowId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener cliente por rowId: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	  
	public Cursor ObtenerClientes()throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	Cursor localCursor = db.obtenerClientes();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	}
	  
	public Cursor ObtenerClientesNoSincronizados()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesNoSincronizados();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener clientes no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener clientes no sincronizados: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}
	
	public Cursor ObtenerClientesEditadosNoSincronizados()throws Exception
	{
	    db.OpenDB();
	    try
	    {
	    	Cursor localCursor = db.obtenerClientesEditadosNoSincronizados();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            	myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes editados no sincronizados: " + localException.getMessage());
	    	}
	    	throw localException;
	    }
	    finally
	    {
	    	db.CloseDB();
	    }
	}

	public long ModificarClienteNombrePorClienteId(int clienteId, String nombres,String paterno,String materno,
													String apellidoCasada,String direccion,String referencia)throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	long l = db.modificarClienteNombrePorClienteId(clienteId,nombres,paterno,materno,apellidoCasada,direccion,referencia);
	    	return l;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
            	myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por clienteId: " + localException.getMessage());
	    	}
	    	
	    	throw localException;
	    }
	    finally
	    {
	      db.CloseDB();
	    }
	  }
	
	public long ModificarClienteNombrePorId(int Id, String nombres,String paterno,String materno,
			String apellidoCasada,String direccion,String referencia)throws Exception
	{
		db.OpenDB();

		try
		{
			long l = db.modificarClienteNombrePorId(Id,nombres,paterno,materno,apellidoCasada,direccion,referencia);
			return l;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public long ModificarClienteDatosPorClienteId(int clienteId, String nombres,String paterno,String materno,
			String apellidoCasada,String telefono,String celular,String direccion,String referencia,double latitud,double longitud,
			boolean activo,int tipoNegocioId,String ci,int provinciaId,int zonaId,int mercadoId,int zonaMercadoId,int tipoPagoId,int precioListaId,String tipoNit,
			String nombreFactura,String nit,int a,int b,int c,int d,int e,int f,int g,int h,int i,int j,int k,int l,int m,int n,int o,int p,int q,int r,float secPreventa,
			float secVenta)throws Exception
	{
		db.OpenDB();

		try
		{
			long lng = db.modificarClienteDatosPorClienteId(clienteId,nombres,paterno,materno,apellidoCasada,telefono,celular,direccion,referencia,latitud,
											longitud,activo,tipoNegocioId,ci,provinciaId,zonaId,mercadoId,zonaMercadoId,tipoPagoId,precioListaId,tipoNit,
											nombreFactura,nit,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,secPreventa,secVenta);
			return lng;
		}
		catch (Exception localException)
		{
			if(localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente por Id: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar los datos del cliente por Id: " + localException.getMessage());
			}
			throw localException;
		}
		finally
		{
			db.CloseDB();
		}
	}
	
	public Cursor ObtenerPrimerCliente()throws Exception
	{
	    db.OpenDB();
	    
	    try
	    {
	    	Cursor localCursor = db.obtenerPrimerCliente();
	    	return localCursor;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el primer cliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el primer cliente: " + localException.getMessage());
	    	}
	    	throw localException;
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
}
