package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.AlmacenProductoBLL;
import BLL.CanalRutaBLL;
import BLL.CanalRutaPrecioBLL;
import BLL.CategoriaBLL;
import BLL.ClienteNitBLL;
import BLL.ClienteVentaBLL;
import BLL.CondicionTributariaBLL;
import BLL.DatosFacturaBLL;
import BLL.DevolucionDistribuidorBLL;
import BLL.DevolucionDistribuidorProductoBLL;
import BLL.DosificacionBLL;
import BLL.DosificacionProveedorBLL;
import BLL.EmpleadoBLL;
import BLL.ImpresoraBLL;
import BLL.MotivoNoEntregaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PreventaBonificacionBLL;
import BLL.PreventaCambioBLL;
import BLL.PreventaDistBLL;
import BLL.PreventaProductoCambioBLL;
import BLL.PreventaProductoDistBLL;
import BLL.PreventaProductoPOPBLL;
import BLL.ProductoBLL;
import BLL.ProductoCambioBLL;
import BLL.ProductoCostoBLL;
import BLL.ProductoPOPBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionProductoBLL;
import BLL.PromocionTipoNegocioBLL;
import BLL.ProveedorBLL;
import BLL.RolBLL;
import BLL.SincronizacionDatosBLL;
import BLL.TipoNegocioBLL;
import BLL.TipoNitBLL;
import BLL.VentaBLL;
import BLL.VentaBonificacionBLL;
import Clases.AES;
import Clases.AlmacenProductoWSResult;
import Clases.CanalRutaPrecioWSResult;
import Clases.CanalRutaWSResult;
import Clases.CategoriaWSResult;
import Clases.ClienteNitWSResult;
import Clases.ClienteVentaWSResult;
import Clases.CondicionTributariaWSResult;
import Clases.CostoWSResult;
import Clases.DatosFacturaWSResult;
import Clases.DevolucionDistribuidorProductoWSResult;
import Clases.DevolucionDistribuidorWSResult;
import Clases.DistribuidorAlmacenFechaWS;
import Clases.DosificacionProveedorWSResult;
import Clases.DosificacionWSResult;
import Clases.EmpleadoWSResult;
import Clases.Fecha;
import Clases.ImpresoraWSResult;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.MotivoNoEntregaWSResult;
import Clases.ParametroGeneralWSResult;
import Clases.PrecioWSResult;
import Clases.PreventaBonificacionWSResult;
import Clases.PreventaCambioWSResult;
import Clases.PreventaDisWSResult;
import Clases.PreventaProductoCambioWSResult;
import Clases.PreventaProductoDistWSResult;
import Clases.PreventaProductoPOPWSResult;
import Clases.ProductoCambioWSResult;
import Clases.ProductoPOPWSResult;
import Clases.ProductoWSResult;
import Clases.PromocionCostoWSResult;
import Clases.PromocionPrecioListWSResult;
import Clases.PromocionPrecioWSResult;
import Clases.PromocionProductoWSResult;
import Clases.PromocionTipoNegocioWSResult;
import Clases.PromocionWSResult;
import Clases.ProveedorWSResult;
import Clases.Rol;
import Clases.SincronizacionDatos;
import Clases.SingleId;
import Clases.TipoNegocioWSResult;
import Clases.TipoNitWSResult;
import Clases.VendedorDiaWS;
import Clases.VendedorFechaWS;
import Clases.Venta;
import Clases.VentaBonificacionWSResult;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Menudistribuidor extends Activity implements OnClickListener
{
	LinearLayout llMenuDistribuidor;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = this.theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();

	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();

	TextView tvUsuario;
	ImageView ivDescargarPreventas;
	ImageView ivVerEntregas;
	ImageView ivSincronizarEntregas;
	ImageView ivCerrarEntregas;
	ImageView ivInventarioAutoventa;
	ImageView ivVerLogs;
	ImageView ivImpresionFacturas;
	ImageView ivSincronizarFacturas;
	ImageView ivSincronizarAutoventas;
	ImageView ivVisualizadorVentas;
	ImageView ivTestImpresora;
	ImageView ivMenuCensista;

	ProgressDialog pdEsperaObtenerDatos;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menudistribuidor);

		llMenuDistribuidor = (LinearLayout)findViewById(R.id.MenuDistribuidorLinearLayout);
		tvUsuario = (TextView)findViewById(R.id.tvMenuDistribuidorUsuario);
		ivDescargarPreventas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorDescargarPreventas);
		ivDescargarPreventas.setOnClickListener(this);
		ivVerEntregas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorVerEntregas);
		ivVerEntregas.setOnClickListener(this);
		ivSincronizarEntregas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorSincronizarEntregas);
		ivSincronizarEntregas.setOnClickListener(this);
		ivCerrarEntregas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorCerrarEntregas);
		ivCerrarEntregas.setOnClickListener(this);
		ivInventarioAutoventa = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorInventarioAutoventa);
		ivInventarioAutoventa.setOnClickListener(this);
		ivVerLogs = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorVerLogs);
		ivVerLogs.setOnClickListener(this);
		ivImpresionFacturas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorImpresionFactura);
		ivImpresionFacturas.setOnClickListener(this);
		ivSincronizarFacturas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorSincronizarFacturas);
		ivSincronizarFacturas.setOnClickListener(this);
		ivSincronizarAutoventas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorSincronizarAutoventas);
		ivSincronizarAutoventas.setOnClickListener(this);
		ivVisualizadorVentas = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorVisualizarVentas);
		ivVisualizadorVentas.setOnClickListener(this);
		ivTestImpresora = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorTestImpresora);
		ivTestImpresora.setOnClickListener(this);
		ivMenuCensista = (ImageView)findViewById(R.id.imgbtnMenuDistribuidorMenuCensista);
		ivMenuCensista.setOnClickListener(this);

		llMenuDistribuidor.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));

		loginEmpleado = null;
		try
		{
			loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado distribuidor: " + localException.getMessage());
			}
		}

		if(loginEmpleado == null)
		{
			MostrarControles(false);
			theUtilidades.MostrarMensaje(getApplicationContext(), "El distribuidor no se logeo correctamente.", 1);
			return;
		}
		else
		{
			MostrarControles(true);
			tvUsuario.setText(loginEmpleado.get_empleadoNombre());
		}

		MostrarControlesEspeciales();
	}

	private void MostrarControlesEspeciales()
	{
		if(!loginEmpleado.is_modificarClienteApk())
		{
			ivMenuCensista.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.imgbtnMenuDistribuidorDescargarPreventas:
				DescargarPreventas();
				break;
			case R.id.imgbtnMenuDistribuidorVerEntregas:
				MostrarPantallaMapa();
				break;
			case R.id.imgbtnMenuDistribuidorSincronizarEntregas:
				MostrarPantallaSincronizacion();
				break;
			case R.id.imgbtnMenuDistribuidorSincronizarAutoventas:
				MostrarPantallaSincronizacionAutoventa();
				break;
			case R.id.imgbtnMenuDistribuidorSincronizarFacturas:
				if(VerificarVentasNoSincronizadas())
				{
					MostrarPantallaSincronizacionFacturas();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Primero debe sincronizar las entregas/autoventas.", 1);
				}
				break;
			case R.id.imgbtnMenuDistribuidorInventarioAutoventa:
				MostrarPantallaInventarioAutoventa();
				break;
			case R.id.imgbtnMenuDistribuidorVisualizarVentas:
				MostrarPantallaVisualizarVentas();
				break;
			case R.id.imgbtnMenuDistribuidorImpresionFactura:
				MostrarPantallaImpresionFacturas();
				break;
			case R.id.imgbtnMenuDistribuidorCerrarEntregas:
				MostrarPantallaCierreEntregas();
				break;
			case R.id.imgbtnMenuDistribuidorVerLogs:
				MostrarPantallaLogs();
				break;
			case R.id.imgbtnMenuDistribuidorTestImpresora:
				MostrarPantallaTestImpresoras();
				break;
			case R.id.imgbtnMenuDistribuidorMenuCensista:
				MostrarPantallaMenuCensista();
				break;
		}
	}

	private void MostrarControles(boolean estado)
	{
		int visibility =0;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}

		ivDescargarPreventas.setVisibility(visibility);
		ivVerEntregas.setVisibility(visibility);
		ivSincronizarEntregas.setVisibility(visibility);
		ivCerrarEntregas.setVisibility(visibility);
		ivInventarioAutoventa.setVisibility(visibility);
		ivVerLogs.setVisibility(visibility);
		ivImpresionFacturas.setVisibility(visibility);
		ivSincronizarFacturas.setVisibility(visibility);
		ivSincronizarAutoventas.setVisibility(visibility);
		ivTestImpresora.setVisibility(visibility);
		ivMenuCensista.setVisibility(visibility);
		ivVisualizadorVentas.setVisibility(visibility);
	}

	private void DescargarPreventas()
	{
		if(VerificarDescargaPreventas())
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La descarga de preventas ya fue realizada.", 1);
			return;
		}

		if(theUtilidades.ValidarFecha(loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio()))
		{
			if(theUtilidades.VerificarConexionInternet(this))
			{
				MostrarControles(false);

				if(ObtenerRolEmpleado() == 4)
				{
					ObtenerClientesVentas();
				}
				else if(ObtenerRolEmpleado() == 6)
				{
					ObtenerClientesVentasProvincia();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existe conexion a internet, intentelo mas tarde. ", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La fecha del dispositivo no concuerda con la del servidor.", 1);
		}
	}

	private boolean VerificarDescargaPreventas()
	{
		boolean verificado=false;

		ArrayList<Rol> listadoRol = null;
		int rol = 0;

		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: " + localException.getMessage());
			}
			return false;
		}

		if(listadoRol != null)
		{
			for(Rol item :listadoRol)
			{
				if(item.get_rol().equals("Distribuidor"))
				{
					rol = 4;//Rol 4 = Distribuidor
				}

				if(item.get_rol().equals("DistribuidorProvincia"))
				{
					rol = 6;//Rol 6 = DistribuidorProvincia
				}
			}

			SincronizacionDatos localSincronizacionDatos = null;
			try
			{
				localSincronizacionDatos = new SincronizacionDatosBLL().ObtenerDetalleSincronizacionDatosPor(loginEmpleado.get_empleadoId(),
						loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),rol);
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de sincronizacionDatos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de sincronizacionDatos: " + localException.getMessage());
				}
			}

			if(localSincronizacionDatos != null)
			{
				verificado = true;
			}
		}

		return verificado;
	}

	private int ObtenerRolEmpleado()
	{
		int rol = 0;
		ArrayList<Rol> listadoRol = null;

		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: " + localException.getMessage());
			}
		}

		if(listadoRol != null)
		{
			for(Rol item :listadoRol)
			{
				if(item.get_rol().equals("Distribuidor"))
				{
					rol = 4;
				}

				if(item.get_rol().equals("DistribuidorProvincia"))
				{
					rol = 6;
				}
			}
		}

		return rol;
	}

	private void ObtenerClientesVentas()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo clientes ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerClientesVentas localWSObtenerClientesVentas = new WSObtenerClientesVentas();
		try
		{
			localWSObtenerClientesVentas.execute();
		}
		catch (Exception localException)
		{
			if ((localException.getMessage() == null) || (localException.getMessage().isEmpty()))
			{
				myLog.InsertarLog("App", toString(), 1, "Error al ejecutar el webservice WSObtenerClientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App", toString(), 1, "Error al ejecutar el webservice WSObtenerClientes: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el webservice WSObtenerClientes", 1);
		}
	}

	private class WSObtenerClientesVentas extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTEENTREGA_METHOD_NAME = "UNI_GetClientesByDistribuidor";
		String CLIENTEENTREGA_SOAP_ACTION = NAMESPACE + CLIENTEENTREGA_METHOD_NAME;

		boolean WSObtenerClienteVentas = false;
		ArrayList<ClienteVentaWSResult> clienteVentaWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClienteVentas = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEENTREGA_METHOD_NAME);
			VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
					loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
			String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAvances);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(CLIENTEENTREGA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteVentaWSResult>>(){ }.getType();
					clienteVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClienteVentas = (clienteVentaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClienteVentas = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerClientesVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerClientesVenta: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerClientesVentas no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerClienteVentas)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Clientes Ventas que descargar. " + error, 1);
				return;
			}

			if (!BorrarClientesVentas())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Clientes Ventas.", 1);
				return;
			}

			long i = 0;

			try
			{
				i = new ClienteVentaBLL().InsertarClienteVenta(clienteVentaWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Clientes Ventas: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Clientes Ventas: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Clientes Ventas.", 1);
				return;
			}
			ObtenerProveedores();
		}
	}

	private void ObtenerClientesVentasProvincia()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo clientes provincia ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerClientesVentasProvincia localWSObtenerClientesVentasProvincia = new WSObtenerClientesVentasProvincia();
		try
		{
			localWSObtenerClientesVentasProvincia.execute();
		}
		catch (Exception localException)
		{
			if ((localException.getMessage() == null) || (localException.getMessage().isEmpty()))
			{
				myLog.InsertarLog("App", toString(), 1, "Error al ejecutar el webservice WSObtenerClientesProvincia: vacio");
			}
			else
			{
				myLog.InsertarLog("App", toString(), 1, "Error al ejecutar el webservice WSObtenerClientesProvincia: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el webservice WSObtenerClientesProvincia", 1);
		}
	}

	private class WSObtenerClientesVentasProvincia extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTEENTREGAPROVINCIA_METHOD_NAME = "GetClientesByDistribuidorProvincia";
		String CLIENTEENTREGAPROVINCIA_SOAP_ACTION = NAMESPACE + CLIENTEENTREGAPROVINCIA_METHOD_NAME;

		boolean WSObtenerClienteVentasProvincia = false;
		ArrayList<ClienteVentaWSResult> clienteVentaWSResults;
		String error;

		private WSObtenerClientesVentasProvincia() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClienteVentasProvincia = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEENTREGAPROVINCIA_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(CLIENTEENTREGAPROVINCIA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteVentaWSResult>>(){ }.getType();
					clienteVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClienteVentasProvincia = (clienteVentaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClienteVentasProvincia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerClientesVentaProvincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerClientesVentaProvincia: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}
			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerClientesVentasProvincia no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerClienteVentasProvincia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Clientes Ventas Provincia que descargar. " + error, 1);
				return;
			}

			if (!BorrarClientesVentas())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Clientes Ventas Provincia.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ClienteVentaBLL().InsertarClienteVenta(clienteVentaWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Clientes Ventas Provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Clientes Ventas Provincia: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Clientes Ventas Provincia.", 1);
				return;
			}
			ObtenerProveedores();

		}
	}

	private boolean BorrarClientesVentas()
	{
		try
		{
			return new ClienteVentaBLL().BorrarClientesVenta();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesVenta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesVenta: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerProveedores()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo proveedores ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerProveedores localWSObtenerProveedores = new WSObtenerProveedores();
		try
		{
			localWSObtenerProveedores.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar webservice WSObtenerProveedores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar webservice WSObtenerProveedores: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerProveedores.", 1);
		}
	}

	private class WSObtenerProveedores extends AsyncTask<Void, Integer, Boolean>
	{
		String PROVEEDOR_METHOD_NAME = "GetProveedores";
		String PROVEEDOR_SOAP_ACTION = NAMESPACE + PROVEEDOR_METHOD_NAME;

		boolean WSObtenerProveedores = false;
		ArrayList<ProveedorWSResult> proveedorWSResults;
		String error;

		private WSObtenerProveedores() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProveedores = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE, PROVEEDOR_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PROVEEDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ProveedorWSResult>>(){ }.getType();
					proveedorWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerProveedores = (proveedorWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerProveedores = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ajecutar el webservice WSObtenerProveedores: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ajecutar el webservice WSObtenerProveedores: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProveedores no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerProveedores)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Proveedores que descargar. " + error, 1);
				return;
			}

			if (!BorrarProveedores())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Proveedores.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ProveedorBLL().InsertarProveedor(proveedorWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Proveedores: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Proveedores: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Proveedores.", 1);
				return;
			}
			ObtenerCostos();

		}
	}

	private boolean BorrarProveedores()
	{
		boolean borrado = false;
		try
		{
			borrado = new ProveedorBLL().BorrarProveedores();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedores: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedores: " + localException.getMessage());
			}
		}
		return borrado;
	}

	private void ObtenerCostos()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo costos ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerCostos localWSObtenerCostos = new WSObtenerCostos();
		try
		{
			localWSObtenerCostos.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProdustosActuales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosActuales: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreciosLista.", 1);
		}
	}

	private class WSObtenerCostos extends AsyncTask<Void, Integer, Boolean>
	{
		String COSTOS_METHOD_NAME = "GetProductosActualesByAlmacen";
		String COSTOS_SOAP_ACTION = NAMESPACE + COSTOS_METHOD_NAME;

		boolean WSObtenerListadoCostos = false;
		ArrayList<CostoWSResult> costoWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerListadoCostos = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,COSTOS_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerCostos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerCostos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(COSTOS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<CostoWSResult>>(){ }.getType();
					costoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerListadoCostos = (costoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerListadoCostos = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosActuales: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosActuales: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerCostos no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerListadoCostos)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Costos que descargar. "  + error, 1);
				return;
			}

			if (!BorrarCostos())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los Costos.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ProductoCostoBLL().InsertarProductoCosto(costoWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Costos: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Costos: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Costos.", 1);
				return;
			}
			ObtenerPrecios();
		}
	}

	private boolean BorrarCostos()
	{
		try
		{
			return new ProductoCostoBLL().BorrarProductosCosto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPrecios()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo precios ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPrecios localWSObtenerPrecios = new WSObtenerPrecios();
		try
		{
			localWSObtenerPrecios.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosLista: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosLista: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreciosLista.", 1);
		}
	}

	private class WSObtenerPrecios extends AsyncTask<Void, Integer, Boolean>
	{
		String PRECIOLISTA_METHOD_NAME = "GetPreciosListaByAlmacen";
		String PRECIOLISTA_SOAP_ACTION = NAMESPACE + PRECIOLISTA_METHOD_NAME;

		boolean WSObtenerListadosPrecio = false;
		ArrayList<PrecioWSResult> precioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerListadosPrecio = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PRECIOLISTA_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerAlmacenes = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAlmacenes);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PRECIOLISTA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PrecioWSResult>>(){ }.getType();
					precioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerListadosPrecio = (precioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerListadosPrecio = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPrecioLista: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPrecioLista: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPrecios no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerListadosPrecio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Precios que descargar. " + error, 1);
				return;
			}

			if (!BorrarPrecios())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Precios.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PrecioListaBLL().InsertarPrecioLista(precioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Precios: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Precios: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Precios.", 1);
				return;
			}
			ObtenerProductos();
		}
	}

	private boolean BorrarPrecios()
	{
		try
		{
			return new PrecioListaBLL().BorrarPreciosLista();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerProductos()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo productos ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerProductos localWSObtenerProductos = new WSObtenerProductos();

		try
		{
			localWSObtenerProductos.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductos: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductos.", 1);
		}
	}

	private class WSObtenerProductos extends AsyncTask<Void, Integer, Boolean>
	{
		String PRODUCTO_METHOD_NAME = "GetProductosByDistribuidor";
		String PRODUCTO_SOAP_ACTION = NAMESPACE + PRODUCTO_METHOD_NAME;
		boolean WSObtenerProductos = false;
		ArrayList<ProductoWSResult> productoWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProductos = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTO_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ProductoWSResult>>(){ }.getType();
					productoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerProductos = (productoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerProductos = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductos: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductos no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerProductos)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Productos que descargar. "  + error, 1);
				return;
			}

			if (!BorrarProductos())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ProductoBLL().InsertarProducto(productoWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos.", 1);
				return;
			}
			ObtenerPromocionesTipoNegocios();
		}
	}

	public boolean BorrarProductos()
	{
		try
		{
			return new ProductoBLL().BorrarProductos();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPromocionesTipoNegocios()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo promociones tipos de negocio ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetPromocionesTipoNegocio localWSGetPromocionesTipoNegocio = new WSGetPromocionesTipoNegocio();

		try
		{
			localWSGetPromocionesTipoNegocio.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocioByPromocionesByAlmacen: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocioByPromocionesByAlmacen: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetTipoNegocioByPromocionesByAlmacen.", 1);
		}
	}

	private class WSGetPromocionesTipoNegocio extends AsyncTask<Void, Integer, Boolean>
	{
		String GETPROMOCIONESTIPONEGOCIO_METHOD_NAME = "GetTipoNegocioByPromocionesByAlmacen";
		String GETPROMOCIONESTIPONEGOCIO_SOAP_ACTION = NAMESPACE + GETPROMOCIONESTIPONEGOCIO_METHOD_NAME;

		boolean GetPromocionesTipoNegocio;
		ArrayList<PromocionTipoNegocioWSResult> promocionTipoNegocioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetPromocionesTipoNegocio = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPROMOCIONESTIPONEGOCIO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETPROMOCIONESTIPONEGOCIO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PromocionTipoNegocioWSResult>>(){ }.getType();
					promocionTipoNegocioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetPromocionesTipoNegocio = (promocionTipoNegocioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetPromocionesTipoNegocio = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTipoNegocioByPromocionesByAlmacen: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTipoNegocioByPromocionesByAlmacen: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPromocionesTipoNegocio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetPromocionesTipoNegocio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron promociones tipo negocio que descargar. " + error, 1);
				ObtenerPromocionesByDistribuidor();
				return;
			}

			if (!BorrarPromocionesTipoNegocio())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las promociones tipo negocio.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PromocionTipoNegocioBLL().InsertarPromocionTipoNegocio(promocionTipoNegocioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las promociones tipo negocio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las promociones tipo negocio: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las promociones tipo negocio.", 1);
				return;
			}
			ObtenerPromocionesByDistribuidor();
		}
	}

	private boolean BorrarPromocionesTipoNegocio()
	{
		try
		{
			return new PromocionTipoNegocioBLL().BorrarPromocionesTipoNegocio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPromocionesByDistribuidor()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo promociones ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPromocionesByDistribuidor localWSObtenerPromocionesByDistribuidor = new WSObtenerPromocionesByDistribuidor();
		try
		{
			localWSObtenerPromocionesByDistribuidor.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromociones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromociones: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromociones.", 1);
		}
	}

	private class WSObtenerPromocionesByDistribuidor extends AsyncTask<Void, Integer, Boolean>
	{
		String PROMOCION_METHOD_NAME = "GetPromocionesByDistribuidorByAlmacen";
		String PROMOCION_SOAP_ACTION = NAMESPACE + PROMOCION_METHOD_NAME;

		boolean WSObtenerPromociones;
		ArrayList<PromocionWSResult> promocionWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPromociones = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PROMOCION_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PROMOCION_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PromocionWSResult>>(){ }.getType();
					promocionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPromociones = (promocionWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPromociones = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionesByDistribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionesByDistribuidor: " + localException.getMessage());
				}
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerPromocionesByDistribuidor.", 1);
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPromocionesByDistribuidor no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPromociones)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Promociones Del Distribuidor que descargar. " + error, 1);
				ObtenerPromocionProductos();
				return;
			}

			if (!BorrarPromociones())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Promociones Del Distribuidor.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PromocionBLL().InsertarPromocion(promocionWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Promociones Del Distribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Promociones Del Distribuidor: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Promociones Del Distribuidor.", 1);
				return;
			}
			ObtenerPromocionProductos();
		}
	}

	private boolean BorrarPromociones()
	{
		try
		{
			return new PromocionBLL().BorrarPromociones();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPromocionProductos()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo productos de la promocion ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPromocionProductos localWSObtenerPromocionProductos = new WSObtenerPromocionProductos();

		try
		{
			localWSObtenerPromocionProductos.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProductos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProductos: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromocionesProducto.", 1);
		}
	}

	private class WSObtenerPromocionProductos extends AsyncTask<Void, Integer, Boolean>
	{
		String PROMOCIONPRODUCTO_METHOD_NAME = "GetPromocionProductosByAlmacen";
		String PROMOCIONPRODUCTO_SOAP_ACTION = NAMESPACE + PROMOCIONPRODUCTO_METHOD_NAME;

		boolean WSObtenerPromocionesProducto;
		ArrayList<PromocionProductoWSResult> promocionProductoWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPromocionesProducto = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PROMOCIONPRODUCTO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerProductos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerProductos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PROMOCIONPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PromocionProductoWSResult>>(){ }.getType();
					promocionProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPromocionesProducto = (promocionProductoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPromocionesProducto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPromocionProductos no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPromocionesProducto)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Promocion Productos que descargar. " + error, 1);
				if(ObtenerRolEmpleado() == 4)
				{
					ObtenerPreventasDist();
				}
				else if(ObtenerRolEmpleado() == 6)
				{
					ObtenerPreventasDistProvincia();
				}
				return;
			}

			if (!BorrarPromocionProductos())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Promocion Productos.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PromocionProductoBLL().InsertarPromocionProducto(promocionProductoWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Promocion Productos: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Promocion Productos: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Promocion Productos.", 1);
				return;
			}
			if(ObtenerRolEmpleado() == 4)
			{
				ObtenerPreventasDist();
			}
			else if(ObtenerRolEmpleado() == 6)
			{
				ObtenerPreventasDistProvincia();
			}
		}
	}

	private boolean BorrarPromocionProductos()
	{
		try
		{
			return new PromocionProductoBLL().BorrarPromocionesProducto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones producto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones producto: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreventasDist()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo preventas ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventaDist localWSObtenerPreventa = new WSObtenerPreventaDist();
		try
		{
			localWSObtenerPreventa.execute();
		}
		catch (Exception localException)
		{
			if ((localException.getMessage() == null) || (localException.getMessage().isEmpty()))
			{
				myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el webservice WSObtenerPreventas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el webservice WSOobtenerPreventas: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el webservice WSOobtenerPreventas.", 1);
		}
	}

	private class WSObtenerPreventaDist extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTACABECERA_METHOD_NAME = "GetPreVentasByDistribuidor";
		String PREVENTACABECERA_SOAP_ACTION = NAMESPACE + PREVENTACABECERA_METHOD_NAME;

		boolean WSObtenerPreventa;
		ArrayList<PreventaDisWSResult> preventaDisWSResults;
		String error;

		private WSObtenerPreventaDist() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventa = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTACABECERA_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL,120000);
			try
			{
				localHttpTransportSE.call(this.PREVENTACABECERA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaDisWSResult>>(){ }.getType();
					preventaDisWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventa = (preventaDisWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreventa = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventas: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventaDist no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventa)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventa Dist que descargar. " + error, 1);
				ObtenerPreventasProductoDist();
				return;
			}

			if (!BorrarPreventasDist())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventa Dist.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaDistBLL().InsertarPreventaDist(preventaDisWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventa Dist: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventa Dist: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventa Dist.", 1);
				return;
			}
			ObtenerPreventasProductoDist();
		}
	}

	private void ObtenerPreventasDistProvincia()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo preventas provincia ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventaDistProvincia localWSObtenerPreventaProvincia = new WSObtenerPreventaDistProvincia();
		try
		{
			localWSObtenerPreventaProvincia.execute();
		}
		catch (Exception localException)
		{
			if ((localException.getMessage() == null) || (localException.getMessage().isEmpty()))
			{
				myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el webservice WSObtenerPreventasProvincia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el webservice WSOobtenerPreventasProvincia: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el webservice WSOobtenerPreventasProvincia.", 1);
		}
	}

	private class WSObtenerPreventaDistProvincia extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTACABECERAPROVINCIA_METHOD_NAME = "GetPreVentasByDistribuidorProvincia";
		String PREVENTACABECERAPROVINCIA_SOAP_ACTION = NAMESPACE + PREVENTACABECERAPROVINCIA_METHOD_NAME;

		boolean WSObtenerPreventaProvincia;
		ArrayList<PreventaDisWSResult> preventaDisWSResults;
		String error;

		private WSObtenerPreventaDistProvincia() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventaProvincia = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTACABECERAPROVINCIA_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL,300000);
			try
			{
				localHttpTransportSE.call(this.PREVENTACABECERAPROVINCIA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaDisWSResult>>(){ }.getType();
					preventaDisWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventaProvincia = (preventaDisWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreventaProvincia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventasProvincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventasProvincia: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventaDistProvincia no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventaProvincia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventa Dist Provincia que descargar. " + error, 1);
				if(ObtenerRolEmpleado() == 4)
				{
					ObtenerPreventasProductoDist();
				}
				else if(ObtenerRolEmpleado() == 6)
				{
					ObtenerPreventasProductoDistProvincia();
				}
				return;
			}

			if (!BorrarPreventasDist())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventa Dist Provincia.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaDistBLL().InsertarPreventaDist(preventaDisWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventa Dist Provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventa Dist Provincia: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventa Dist Provincia.", 1);
				return;
			}
			if(ObtenerRolEmpleado() == 4)
			{
				ObtenerPreventasProductoDist();
			}
			else if(ObtenerRolEmpleado() == 6)
			{
				ObtenerPreventasProductoDistProvincia();
			}

		}
	}

	private boolean BorrarPreventasDist()
	{
		try
		{
			return new PreventaDistBLL().BorrarPreventasDist();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasDistibuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasDistibuidor: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreventasProductoDist()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo productos de la preventa ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventaProductoDist localWSObtenerPreventaProducto = new WSObtenerPreventaProductoDist();
		try
		{
			localWSObtenerPreventaProducto.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProducto: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerPreventasProducto.", 1);
		}
	}

	private class WSObtenerPreventaProductoDist extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTAPRODUCTO_METHOD_NAME = "GetPreVentasProductosByDistribuidor";
		String PREVENTAPRODUCTO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTO_METHOD_NAME;

		boolean WSObtenerPreventaProducto;
		ArrayList<PreventaProductoDistWSResult> preventaProductoDistWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventaProducto = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTAPRODUCTO_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(this.PREVENTAPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaProductoDistWSResult>>(){ }.getType();
					preventaProductoDistWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventaProducto = (preventaProductoDistWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreventaProducto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProducto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProducto: " + localException.getMessage());
				}
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerPreventasProducto.", 1);
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventaProductoDist no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventaProducto)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventa Producto Dist que descargar. " + error, 1);
				ObtenerMotivoNoEntrega();
				return;
			}

			if (!BorrarPreventasProductoDist())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventa Producto Dist.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaProductoDistBLL().InsertarPreventaProductoDist(preventaProductoDistWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventa Producto Dist: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventa Producto Dist: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventa Producto Dist.", 1);
				return;
			}
			ObtenerMotivoNoEntrega();
		}
	}

	private void ObtenerPreventasProductoDistProvincia()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo productos de la preventa provincia...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventaProductoDistProvincia localWSObtenerPreventaProductoProvincia = new WSObtenerPreventaProductoDistProvincia();
		try
		{
			localWSObtenerPreventaProductoProvincia.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProductoProvincia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProductoProvincia: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerPreventasProductoProvincia.", 1);
		}
	}

	private class WSObtenerPreventaProductoDistProvincia extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTAPRODUCTOPROVINCIA_METHOD_NAME = "GetPreVentasProductosByDistribuidorProvincia";
		String PREVENTAPRODUCTOPROVINCIA_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOPROVINCIA_METHOD_NAME;

		boolean WSObtenerPreventaProductoProvincia;
		ArrayList<PreventaProductoDistWSResult> preventaProductoDistWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventaProductoProvincia = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTAPRODUCTOPROVINCIA_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(this.PREVENTAPRODUCTOPROVINCIA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaProductoDistWSResult>>(){ }.getType();
					preventaProductoDistWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventaProductoProvincia = (preventaProductoDistWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreventaProductoProvincia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProductoProvincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerPreventaProductoProvincia: " + localException.getMessage());
				}
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerPreventasProductoProvincia.", 1);
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventaProductoDistProvincia no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventaProductoProvincia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventa Producto Dist Provincia que descargar. " + error, 1);
				ObtenerMotivoNoEntrega();
				return;
			}

			if (!BorrarPreventasProductoDist())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventa Producto Dist Provincia.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaProductoDistBLL().InsertarPreventaProductoDist(preventaProductoDistWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventa Producto Dist Provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventa Producto Dist Provincia: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventa Producto Dist Provincia.", 1);
				return;
			}
			ObtenerMotivoNoEntrega();
		}
	}

	private boolean BorrarPreventasProductoDist()
	{
		try
		{
			return new PreventaProductoDistBLL().BorrarPreventasProductoDist();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas producto del distribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventas producto del distribuidor: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerMotivoNoEntrega()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo motivos de no entrega ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerMotivoNoEntrega localWSObtenerMotivoNoEntrega = new WSObtenerMotivoNoEntrega();
		try
		{
			localWSObtenerMotivoNoEntrega.execute();
		}
		catch (Exception localException)
		{
			if ((localException.getMessage() == null) || (localException.getMessage().isEmpty()))
			{
				myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el webservice WSObtenerMotivosNoEntrega: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(), 1, "Error al ejecutar el webservice WSObtenerMotivosNoEntrega: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerMotivosNoEntrega.", 1);
		}
	}

	private class WSObtenerMotivoNoEntrega extends AsyncTask<Void, Integer, Boolean>
	{
		String MOTIVONOENTREGA_METHOD_NAME = "GetMotivosNoEntregaApk";
		String MOTIVONOENTREGA_SOAP_ACTION = NAMESPACE + MOTIVONOENTREGA_METHOD_NAME;

		boolean WSObtenerMotivoNoEntrega = false;
		ArrayList<MotivoNoEntregaWSResult> motivoNoEntregaWSResults;
		String error;

		private WSObtenerMotivoNoEntrega() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerMotivoNoEntrega = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,MOTIVONOENTREGA_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(MOTIVONOENTREGA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<MotivoNoEntregaWSResult>>(){ }.getType();
					motivoNoEntregaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerMotivoNoEntrega = (motivoNoEntregaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerMotivoNoEntrega = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerMotivosNoEntrega: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerMotivosNoEntrega: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerMotivoNoEntrega no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerMotivoNoEntrega)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Motivo No Entrega que descargar. " + error, 1);
				return;
			}

			if (!BorrarMotivosNoEntrega())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Motivo No Entrega.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new MotivoNoEntregaBLL().InsertarMotivoNoEntrega(motivoNoEntregaWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Motivo No Entrega: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Motivo No Entrega: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Motivo No Entrega.", 1);
				return;
			}
			ObtenerAlmacenProducto();
		}
	}

	private boolean BorrarMotivosNoEntrega()
	{
		boolean borrado = false;
		try
		{
			borrado = new MotivoNoEntregaBLL().BorrarMotivosNoEntrega();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivosNoEntrega: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los motivosNoEntrega: " + localException.getMessage());
			}
		}

		return borrado;
	}

	private void ObtenerAlmacenProducto()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo almacen producto ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerAlmacenProducto localWSObtenerAlmacenProducto = new WSObtenerAlmacenProducto();
		try
		{
			localWSObtenerAlmacenProducto.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerAlmacenProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerAlmacenProducto: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerAlmacenProducto.", 1);
		}
	}

	private class WSObtenerAlmacenProducto extends AsyncTask<Void, Integer, Boolean>
	{
		String ALMACENPRODUCTO_METHOD_NAME = "GetProductosByAlmacenTemp";
		String ALMACENPRODUCTO_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTO_METHOD_NAME;

		boolean WSObtenerAlmacenProducto = false;
		ArrayList<AlmacenProductoWSResult> almacenProductoWSResults;
		String error;

		private WSObtenerAlmacenProducto() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAlmacenProducto = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerProductos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerProductos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(this.ALMACENPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<AlmacenProductoWSResult>>(){ }.getType();
					almacenProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerAlmacenProducto = (almacenProductoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAlmacenProducto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerAlmacenProducto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerAlmacenProducto: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerAlmacenProducto no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerAlmacenProducto)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Almacen Producto que descargar. " + error, 1);
				return;
			}

			if (!BorrarAlmacenProductos())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Almacen Producto.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new AlmacenProductoBLL().InsertarAlmacenProducto( almacenProductoWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Almacen Producto: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Almacen Producto: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Almacen Producto.", 1);
				return;
			}
			ObtenerDevolucionDistribuidor();
		}
	}

	private boolean BorrarAlmacenProductos()
	{
		try
		{
			return new AlmacenProductoBLL().BorrarAlmacenProductos();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenProductos: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenProductos: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerDevolucionDistribuidor()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo almacen distribuidor ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerDevolucionDistribuidor localWSObtenerDevolucionDistribuidor = new WSObtenerDevolucionDistribuidor();
		try
		{
			localWSObtenerDevolucionDistribuidor.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidor: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerDevolucionDistribuidor", 1);
		}
	}

	private class WSObtenerDevolucionDistribuidor extends AsyncTask<Void, Integer, Boolean>
	{
		String DEVOLUCIONDISTRIBUIDOR_METHOD_NAME = "GetDevolucionDistribuidor";
		String DEVOLUCIONDISTRIBUIDOR_SOAP_ACTION = NAMESPACE + DEVOLUCIONDISTRIBUIDOR_METHOD_NAME;

		boolean WSObtenerDevolucionDistribuidor = false;
		DevolucionDistribuidorWSResult devolucionDistribuidorWSResult;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerDevolucionDistribuidor = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,DEVOLUCIONDISTRIBUIDOR_METHOD_NAME);
			VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
					loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
			String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAvances);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(DEVOLUCIONDISTRIBUIDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<DevolucionDistribuidorWSResult>(){ }.getType();
					devolucionDistribuidorWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerDevolucionDistribuidor = (devolucionDistribuidorWSResult != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerDevolucionDistribuidor = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidor: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerDevolucionDistribuidor no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerDevolucionDistribuidor)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Devolucion Distribuidor que descargar. " + error, 1);
				ObtenerDevolucionesDistribuidorProducto();
				return;
			}

			if (!BorrarDevolucionesDistribuidor())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Devolucion Distribuidor.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new DevolucionDistribuidorBLL().InsertarDevolucionDistribuidor(devolucionDistribuidorWSResult);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Devolucion Distribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Devolucion Distribuidor: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la devolucion distribuidor.", 1);
				return;
			}
			ObtenerDevolucionesDistribuidorProducto();
		}
	}

	private boolean BorrarDevolucionesDistribuidor()
	{
		try
		{
			return new DevolucionDistribuidorBLL().BorrarDevolucionesDistribuidor();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devolucionesDistribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devolucionesDistribuidor: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerDevolucionesDistribuidorProducto()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo productos almacen distribuidor ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerDevolucionDistribuidorProducto localWSObtenerDevolucionDistribuidorProducto = new WSObtenerDevolucionDistribuidorProducto();
		try
		{
			localWSObtenerDevolucionDistribuidorProducto.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidorProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidorProducto: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el webservice WSObtenerDevolucionDistribuidorProducto.", 1);
		}
	}

	private class WSObtenerDevolucionDistribuidorProducto extends AsyncTask<Void, Integer, Boolean>
	{
		String DEVOLUCIONDISTRIBUIDORPRODUCTO_METHOD_NAME = "GetDevolucionDistribuidorProductoApk";
		String DEVOLUCIONDISTRIBUIDORPRODUCTO_SOAP_ACTION = NAMESPACE + DEVOLUCIONDISTRIBUIDORPRODUCTO_METHOD_NAME;

		boolean WSObtenerDevolucionDistribuidorProducto = false;
		ArrayList<DevolucionDistribuidorProductoWSResult> devolucionDistribuidorProductoWSResults;
		String error;

		private WSObtenerDevolucionDistribuidorProducto() {}

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerDevolucionDistribuidorProducto = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,DEVOLUCIONDISTRIBUIDORPRODUCTO_METHOD_NAME);
			VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
					loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
			String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAvances);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(this.DEVOLUCIONDISTRIBUIDORPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<DevolucionDistribuidorProductoWSResult>>(){ }.getType();
					devolucionDistribuidorProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerDevolucionDistribuidorProducto = (devolucionDistribuidorProductoWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerDevolucionDistribuidorProducto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidorProducto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerDevolucionDistribuidorProducto: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerDevolucionDistribuidorProducto no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerDevolucionDistribuidorProducto)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Devolucion Distribuidor Producto que descargar. "  + error, 1);
				ObtenerCanalesRuta();
				return;
			}

			if (!BorrarDevolucionesDistribuidorProducto())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Devolucion Distribuidor Producto.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new DevolucionDistribuidorProductoBLL().InsertarDevolucionDistribuidorProducto(devolucionDistribuidorProductoWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Devolucion Distribuidor Producto: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Devolucion Distribuidor Producto: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Devolucion Distribuidor Producto.", 1);
				return;
			}
			ObtenerCanalesRuta();
		}
	}

	private boolean BorrarDevolucionesDistribuidorProducto()
	{
		try
		{
			return new DevolucionDistribuidorProductoBLL().BorrarDevolucionesDistribuidorProducto();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devolucionesDistribuidorProducto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las devolucionesDistribuidorProducto: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerCanalesRuta()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo canales ruta ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetCanalesRuta localWSGetCanalesRuta = new WSGetCanalesRuta();

		try
		{
			localWSGetCanalesRuta.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRuta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRuta: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCanalesRuta.", 1);
		}
	}

	private class WSGetCanalesRuta extends AsyncTask<Void, Integer, Boolean>
	{
		String CANALRUTA_METHOD_NAME = "GetCanalesRuta";
		String CANALRUTA_SOAP_ACTION = NAMESPACE + CANALRUTA_METHOD_NAME;

		boolean GetCanalesRuta;
		ArrayList<CanalRutaWSResult> canalRutaWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetCanalesRuta = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CANALRUTA_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerCanales = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerCanales);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(CANALRUTA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<CanalRutaWSResult>>(){ }.getType();
					canalRutaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetCanalesRuta = (canalRutaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetCanalesRuta = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCanalesRuta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCanalesRuta: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCanalesRuta no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetCanalesRuta)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron canales ruta que descargar. " + error, 1);
				ObtenerCanalesRutaPrecio();
				return;
			}

			if (!BorrarCanalesRuta())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los canales de ruta.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new CanalRutaBLL().InsertarCanalRuta(canalRutaWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales de ruta: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales de ruta: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los canales de ruta.", 1);
				return;
			}
			ObtenerCanalesRutaPrecio();
		}
	}

	private boolean BorrarCanalesRuta()
	{
		try
		{
			return new CanalRutaBLL().BorrarCanalesRuta();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerCanalesRutaPrecio()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo canales ruta precio ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetCanalesRutaPrecio localWSGetCanalesRutaPrecio = new WSGetCanalesRutaPrecio();

		try
		{
			localWSGetCanalesRutaPrecio.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRutaPrecio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRutaPrecio: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCanalesRutaPrecio.", 1);
		}
	}

	private class WSGetCanalesRutaPrecio extends AsyncTask<Void, Integer, Boolean>
	{
		String GETRUTAPRECIO_METHOD_NAME = "GetRutasPrecioByAlmacen";
		String GETRUTAPRECIO_SOAP_ACTION = NAMESPACE + GETRUTAPRECIO_METHOD_NAME;

		boolean GetRutaPrecio;
		ArrayList<CanalRutaPrecioWSResult> canalRutaPrecioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetRutaPrecio= false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETRUTAPRECIO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerPrecios = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPrecios);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETRUTAPRECIO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<CanalRutaPrecioWSResult>>(){ }.getType();
					canalRutaPrecioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetRutaPrecio = (canalRutaPrecioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetRutaPrecio = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRutasPrecioByAlmacen: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRutasPrecioByAlmacen: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCanalesRutaPrecio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetRutaPrecio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron canales de ruta precio que descargar. " + error, 1);
				ObtenerTiposNit();
				return;
			}

			if (!BorrarRutasPrecios())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los canales ruta precio.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new CanalRutaPrecioBLL().InsertarCanalRutaPrecio(canalRutaPrecioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales ruta precio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales ruta precio: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los canales ruta precio.", 1);
				return;
			}
			ObtenerTiposNit();
		}
	}

	private boolean BorrarRutasPrecios()
	{
		try
		{
			return new CanalRutaPrecioBLL().BorrarCanalesRutaPrecio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta precio: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerTiposNit()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo tipos NIT ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetTiposNit localWSGetTiposNit = new WSGetTiposNit();

		try
		{
			localWSGetTiposNit.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTiposNit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTiposNit: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetTiposNit.", 1);
		}
	}

	private class WSGetTiposNit extends AsyncTask<Void, Integer, Boolean>
	{
		String GETTIPOSNIT_METHOD_NAME = "GetTiposNit";
		String GETTIPOSNIT_SOAP_ACTION = NAMESPACE + GETTIPOSNIT_METHOD_NAME;

		boolean GetTiposNit;
		ArrayList<TipoNitWSResult> tipoNitWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetTiposNit= false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETTIPOSNIT_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerTipos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerTipos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETTIPOSNIT_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<TipoNitWSResult>>(){ }.getType();
					tipoNitWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetTiposNit = (tipoNitWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetTiposNit = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTiposVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTiposVenta: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetTiposNit no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetTiposNit)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de nit que descargar. " + error, 1);
				return;
			}

			if (!BorrarTiposNit())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los tipos de nit.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new TipoNitBLL().InsertarTipoNit(tipoNitWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los tipos de nit: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los tipos de nit: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los tipos nit.", 1);
				return;
			}
			ObtenerParametrosGenerales();
		}
	}

	private boolean BorrarTiposNit()
	{
		try
		{
			return new TipoNitBLL().BorrarTiposNit();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos nit: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos nit: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerParametrosGenerales()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo parametros generales ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerParametrosGenerales localWSObtenerParametrosGenerales = new WSObtenerParametrosGenerales();

		try
		{
			localWSObtenerParametrosGenerales.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerParametrosGenerales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerParametrosGenerales: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerParametrosGenerales.", 1);
		}
	}

	private class WSObtenerParametrosGenerales extends AsyncTask<Void, Integer, Boolean>
	{
		String PARAMETROGENERAL_METHOD_NAME = "GetParametroGeneral";
		String PARAMETROGENERAL_SOAP_ACTION = NAMESPACE + PARAMETROGENERAL_METHOD_NAME;

		boolean WSObtenerParametrosGenerales;
		ParametroGeneralWSResult parametroGeneralWSResult;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerParametrosGenerales = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PARAMETROGENERAL_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerParametros = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerParametros);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PARAMETROGENERAL_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<ParametroGeneralWSResult>(){ }.getType();
					parametroGeneralWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerParametrosGenerales = (parametroGeneralWSResult != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerParametrosGenerales = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerParametrosGenerales no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerParametrosGenerales)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Parametros Generales que descargar. " + error, 1);
				return;
			}

			if (!BorrarParametrosGenerales())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Parametros Generales.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ParametroGeneralBLL().InsertarParametroGeneral(parametroGeneralWSResult);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Parametros Generales: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Parametros Generales: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Parametros Generales.", 1);
				return;
			}
			ObtenerCategorias();
		}
	}

	private boolean BorrarParametrosGenerales()
	{
		try
		{
			return new ParametroGeneralBLL().BorrarParametrosGenerales();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerCategorias()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo categorias ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetCategorias localWSGetCategorias = new WSGetCategorias();

		try
		{
			localWSGetCategorias.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategorias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategorias: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCategorias.", 1);
		}
	}

	private class WSGetCategorias  extends AsyncTask<Void, Integer, Boolean>
	{
		String CATEGORIAS_METHOD_NAME = "GetCategorias";
		String CATEGORIAS_SOAP_ACTION = NAMESPACE + CATEGORIAS_METHOD_NAME;

		boolean WSObtenerCategorias;
		ArrayList<CategoriaWSResult> categoriaWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerCategorias = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CATEGORIAS_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerCategorias = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerCategorias);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(CATEGORIAS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<CategoriaWSResult>>(){ }.getType();
					categoriaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerCategorias = (categoriaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerCategorias = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCategorias: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCategorias: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCategorias no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerCategorias)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron categorias que descargar. " + error, 1);
				return;
			}

			if (!BorrarCategorias())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las categorias.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new CategoriaBLL().InsertarCategoria(categoriaWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las categorias: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las categorias: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias.", 1);
				return;
			}
			if(ObtenerRolEmpleado() == 4)
			{
				ObtenerClienteNits();
			}
			else if(ObtenerRolEmpleado() == 6)
			{
				ObtenerClienteNitsProvincia();
			}
		}
	}

	private boolean BorrarCategorias()
	{
		try
		{
			return new CategoriaBLL().BorrarCategorias();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerClienteNits()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo nits de clientes ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetNitsByClientesApk localWSGetNitsByClientesApk = new WSGetNitsByClientesApk();

		try
		{
			localWSGetNitsByClientesApk.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesApk: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesApk: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetNitsByClientesApk.", 1);
		}
	}

	private class WSGetNitsByClientesApk  extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTENITS_METHOD_NAME = "GetNitsByClientesDistribuidorApk";
		String CLIENTENITS_SOAP_ACTION = NAMESPACE + CLIENTENITS_METHOD_NAME;

		boolean WSObtenerClienteNits;
		ArrayList<ClienteNitWSResult> clienteNitWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClienteNits = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTENITS_METHOD_NAME);
			VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
					loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
			String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAvances);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(CLIENTENITS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteNitWSResult>>(){ }.getType();
					clienteNitWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClienteNits = (clienteNitWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClienteNits = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsByClientesApk: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsByClientesApk: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitsByClientesApk no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerClienteNits)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron nts del clientes que descargar. " + error, 1);
				ObtenerDosificaciones();
				return;
			}

			if (!BorrarClienteNits())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los nits del cliente.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ClienteNitBLL().InsertarClienteNit(clienteNitWSResults, loginEmpleado);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nits del cliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nits del cliente: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los nits del cliente.", 1);
				return;
			}
			ObtenerDosificaciones();
		}
	}

	private void ObtenerClienteNitsProvincia()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo nits de clientes de provincia ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetNitsByClientesProvinciaApk localWSGetNitsByClientesProvinciaApk = new WSGetNitsByClientesProvinciaApk();

		try
		{
			localWSGetNitsByClientesProvinciaApk.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesProvinciaApk: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesProvinciaApk: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetNitsByClientesProvinciaApk.", 1);
		}
	}

	private class WSGetNitsByClientesProvinciaApk  extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTENITSPROVINCIA_METHOD_NAME = "GetNitsByClientesDistribuidorProvinciaApk";
		String CLIENTENITSPROVINCIA_SOAP_ACTION = NAMESPACE + CLIENTENITSPROVINCIA_METHOD_NAME;

		boolean WSObtenerClienteNitsProvincia;
		ArrayList<ClienteNitWSResult> clienteNitWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClienteNitsProvincia = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTENITSPROVINCIA_METHOD_NAME);
			VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
					loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
			String encriptedObtenerNits = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerNits);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(CLIENTENITSPROVINCIA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteNitWSResult>>(){ }.getType();
					clienteNitWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClienteNitsProvincia = (clienteNitWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClienteNitsProvincia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsByClientesProvinciaApk: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsByClientesProvinciaApk: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitsByClientesProvinciaApk no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerClienteNitsProvincia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron nits del clientes provincia que descargar. " + error, 1);
				ObtenerDosificaciones();
				return;
			}

			if (!BorrarClienteNits())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los nits del clientes provincia.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ClienteNitBLL().InsertarClienteNit(clienteNitWSResults, loginEmpleado);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nits del cliente provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nits del clientes provincia: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los nits del clientes provincia.", 1);
				return;
			}
			ObtenerDosificaciones();
		}
	}

	private boolean BorrarClienteNits()
	{
		try
		{
			return new ClienteNitBLL().BorrarClientesNit();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clienteNits: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clienteNits: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerDosificaciones()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo dosificaciones ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetDosificacionByEmpleado localWSGetDosificacionByEmpleado = new WSGetDosificacionByEmpleado();

		try
		{
			localWSGetDosificacionByEmpleado.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionByEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionByEmpleado: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDosificacionByEmpleado.", 1);
		}
	}

	private class WSGetDosificacionByEmpleado  extends AsyncTask<Void, Integer, Boolean>
	{
		String DOSIFICACION_METHOD_NAME = "GetDosificacionByEmpleado";
		String DOSIFICACION_SOAP_ACTION = NAMESPACE + DOSIFICACION_METHOD_NAME;

		boolean WSObtenerDosificaciones;
		ArrayList<DosificacionWSResult> dosificacionWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerDosificaciones = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,DOSIFICACION_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(DOSIFICACION_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<DosificacionWSResult>>(){ }.getType();
					dosificacionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerDosificaciones = (dosificacionWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerDosificaciones = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetDosificacionByEmpleado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetDosificacionByEmpleado: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetDosificacionByEmpleado no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerDosificaciones)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron dosificacion del empleado que descargar. " + error, 1);
				ObtenerPreciosByPromociones();
				return;
			}

			if (!BorrarDosificaciones())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar dosificacion del empleado.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new DosificacionBLL().InsertarDosificacion(dosificacionWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las dosificaciones del empleado: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las dosificacion del empleado: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las dosificacion del empleado.", 1);
				return;
			}
			ObtenerPreciosByPromociones();

		}
	}

	private boolean BorrarDosificaciones()
	{
		try
		{
			return new DosificacionBLL().BorrarDosificacion();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreciosByPromociones()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo precios promociones ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetPreciosByPromociones localWSGetPreciosByPromociones = new WSGetPreciosByPromociones();

		try
		{
			localWSGetPreciosByPromociones.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosByPromociones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosByPromociones: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreciosByPromociones.", 1);
		}
	}

	private class WSGetPreciosByPromociones  extends AsyncTask<Void, Integer, Boolean>
	{
		String PRECIOSPROMOCIONES_METHOD_NAME = "GetPreciosByPromocionesByAlmacen";
		String PRECIOSPROMOCIONES_SOAP_ACTION = NAMESPACE + PRECIOSPROMOCIONES_METHOD_NAME;

		boolean WSObtenerPreciosPromociones;
		ArrayList<PromocionPrecioListWSResult> promocionPrecioListWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreciosPromociones = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PRECIOSPROMOCIONES_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(PRECIOSPROMOCIONES_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PromocionPrecioListWSResult>>(){ }.getType();
					promocionPrecioListWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreciosPromociones = (promocionPrecioListWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreciosPromociones = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosByPromociones: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosByPromociones: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPreciosByPromociones no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreciosPromociones)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron precios de las promociones que descargar. " + error, 1);
				ObtenerDatosFactura();
				return;
			}

			if (!BorrarPreciosPromociones())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los precios de las promociones.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PromocionPrecioListaBLL().InsertarPromocionPrecioLista(promocionPrecioListWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de las promociones: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de las promociones: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los precios de las promociones.", 1);
				return;
			}
			ObtenerDatosFactura();
		}
	}

	private boolean BorrarPreciosPromociones()
	{
		try
		{
			return new PromocionPrecioListaBLL().BorrarPromocionesPrecioLista();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerDatosFactura()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo datos factura ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetDatosFactura localWSGetDatosFactura = new WSGetDatosFactura();

		try
		{
			localWSGetDatosFactura.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDatosFactura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDatosFactura: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDatosFactura.", 1);
		}
	}

	private class WSGetDatosFactura  extends AsyncTask<Void, Integer, Boolean>
	{
		String DATOSFACTURA_METHOD_NAME = "GetDatosFactura";
		String DATOSFACTURA_SOAP_ACTION = NAMESPACE + DATOSFACTURA_METHOD_NAME;
		boolean WSObtenerDatosFactura;
		DatosFacturaWSResult datosFacturaWSResult;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerDatosFactura = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,DATOSFACTURA_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(DATOSFACTURA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<DatosFacturaWSResult>(){ }.getType();
					datosFacturaWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerDatosFactura = (datosFacturaWSResult != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerDatosFactura = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetDatosFactura: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetDatosFactura: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetDatosFactura no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerDatosFactura)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron datos factura que descargar. " + error, 1);
				ObtenerEmpleados();
				return;
			}

			if (!BorrarDatosFactura())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los datos de la factura.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new DatosFacturaBLL().InsertarDatosFactura(datosFacturaWSResult);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los datos de la factura: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los datos de la factura: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener datos de la factura.", 1);
				return;
			}
			ObtenerEmpleados();
		}
	}

	private boolean BorrarDatosFactura()
	{
		try
		{
			return new DatosFacturaBLL().BorrarDatosFactura();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datos de la factura: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datos de la factura: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerEmpleados()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo empleados ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetEmpleados localWSGetEmpleados = new WSGetEmpleados();

		try
		{
			localWSGetEmpleados.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesApk: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEmpleados: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetEmpleados.", 1);
		}
	}

	private class WSGetEmpleados  extends AsyncTask<Void, Integer, Boolean>
	{
		String EMPLEADOS_METHOD_NAME = "GetEmpleados";
		String EMPLEADOS_SOAP_ACTION = NAMESPACE + EMPLEADOS_METHOD_NAME;

		boolean WSObtenerEmpleados;
		ArrayList<EmpleadoWSResult> empleadoWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerEmpleados = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,EMPLEADOS_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(EMPLEADOS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<EmpleadoWSResult>>(){ }.getType();
					empleadoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerEmpleados = (empleadoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerEmpleados = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEmpleados: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEmpleados: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetEmpleados no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerEmpleados)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron empleados que descargar. " + error, 1);
				ObtenerImpresoras();
				return;
			}

			if (!BorrarEmpleados())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los empleados.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new EmpleadoBLL().InsertarEmpleado(empleadoWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los empleados: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los empleados: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los empleados.", 1);
				return;
			}
			ObtenerImpresoras();
		}
	}

	private boolean BorrarEmpleados()
	{
		try
		{
			return new EmpleadoBLL().BorrarEmpleados();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los empleados: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los empleados: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerImpresoras()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo impresoras ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetImpresoras localWSGetImpresoras = new WSGetImpresoras();

		try
		{
			localWSGetImpresoras.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetImpresorasByEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetImpresorasByEmpleados: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetImpresorasByEmpleado.", 1);
		}
	}

	private class WSGetImpresoras extends AsyncTask<Void, Integer, Boolean>
	{
		String IMPRESORAS_METHOD_NAME = "GetImpresorasByDistribuidor";
		String IMPRESORAS_SOAP_ACTION = NAMESPACE + IMPRESORAS_METHOD_NAME;

		boolean WSObtenerImpresoras;
		ArrayList<ImpresoraWSResult> impresoraWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerImpresoras = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,IMPRESORAS_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(IMPRESORAS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ImpresoraWSResult>>(){ }.getType();
					impresoraWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerImpresoras = (impresoraWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerImpresoras = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetImpresorasByDistribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetImpresorasByDistribuidor: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetImpresoras no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerImpresoras)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron impresoras que descargar. " + error, 1);
				ObtenerPromocionesCosto();
				return;
			}

			if (!BorrarImpresoras())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las impresoras.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ImpresoraBLL().InsertarImpresora(impresoraWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las impresoras: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los las impresoras: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las impresoras.", 1);
				return;
			}
			ObtenerPromocionesCosto();
		}
	}

	private boolean BorrarImpresoras()
	{
		try
		{
			return new ImpresoraBLL().BorrarImpresoras();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las impresoras: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las impresoras: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPromocionesCosto()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo costos promocion ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetPromocionesCostosActuales localWSGetPromocionesCostosActuales = new WSGetPromocionesCostosActuales();

		try
		{
			localWSGetPromocionesCostosActuales.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesCostosActuales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesCostosActuales: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromocionesCostosActuales.", 1);
		}
	}

	private class WSGetPromocionesCostosActuales extends AsyncTask<Void, Integer, Boolean>
	{
		String GETPROMOCIONESCOSTOS_METHOD_NAME = "GetPromocionCostosActualesByAlmacen";
		String GETPROMOCIONESCOSTOS_SOAP_ACTION = NAMESPACE + GETPROMOCIONESCOSTOS_METHOD_NAME;

		boolean GetPromocionesCostos;
		ArrayList<PromocionCostoWSResult> promocionCostoWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetPromocionesCostos = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPROMOCIONESCOSTOS_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerCostos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerCostos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETPROMOCIONESCOSTOS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PromocionCostoWSResult>>(){ }.getType();
					promocionCostoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetPromocionesCostos = (promocionCostoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetPromocionesCostos = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesCostosActuales: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesCostosActuales: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPromocionesCostosActuales no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetPromocionesCostos)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Promociones Costos Actuales que descargar. " + error, 1);
				ObtenerPromocionesPrecio();
				return;
			}

			if (!BorrarPromocionesCosto())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Promociones Costos Actuales.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PromocionCostoBLL().InsertarPromocionCosto(promocionCostoWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los costos de las promociones: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los costos de las promociones: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los costos de las promociones.", 1);
				return;
			}
			ObtenerPromocionesPrecio();
		}
	}

	private boolean BorrarPromocionesCosto()
	{
		try
		{
			return new PromocionCostoBLL().BorrarPromocionesCosto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPromocionesPrecio()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo precios promocion ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetPromocionesPreciosActuales localWSGetPromocionesPreciosActuales = new WSGetPromocionesPreciosActuales();

		try
		{
			localWSGetPromocionesPreciosActuales.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesPreciosActuales: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesPreciosActuales: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromocionesPreciosActuales.", 1);
		}
	}

	private class WSGetPromocionesPreciosActuales extends AsyncTask<Void, Integer, Boolean>
	{
		String GETPROMOCIONESPRECIOS_METHOD_NAME = "GetPromocionPreciosActualesByAlmacen";
		String GETPROMOCIONESPRECIOS_SOAP_ACTION = NAMESPACE + GETPROMOCIONESPRECIOS_METHOD_NAME;

		boolean GetPromocionesPrecios;
		ArrayList<PromocionPrecioWSResult> promocionPrecioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetPromocionesPrecios = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPROMOCIONESPRECIOS_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETPROMOCIONESPRECIOS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PromocionPrecioWSResult>>(){ }.getType();
					promocionPrecioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetPromocionesPrecios = (promocionPrecioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetPromocionesPrecios = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesPreciosActuales: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesPreciosActuales: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPromocionesPreciosActuales no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetPromocionesPrecios)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Promociones Precios Actuales que descargar. "  + error, 1);

				if(ObtenerRolEmpleado() == 4)
				{
					ObtenerPreventasBonificacion();
				}
				else if(ObtenerRolEmpleado() == 6)
				{
					ObtenerPreventasBonificacionProvincia();
				}
				return;
			}

			if (!BorrarPromocionesPrecio())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Promociones Precios Actuales.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PromocionPrecioBLL().InsertarPromocionPrecio(promocionPrecioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Promociones Precios Actuales: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Promociones Precios Actuales: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Promociones Precios Actuales.", 1);
				return;
			}

			if(ObtenerRolEmpleado() == 4)
			{
				ObtenerPreventasBonificacion();
			}
			else if(ObtenerRolEmpleado() == 6)
			{
				ObtenerPreventasBonificacionProvincia();
			}
		}
	}

	private boolean BorrarPromocionesPrecio()
	{
		try
		{
			return new PromocionPrecioBLL().BorrarPromocionesPrecio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreventasBonificacion()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo preventas bonificacion ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetPreventasBonificacion localWSGetPreventasBonificacion = new WSGetPreventasBonificacion();

		try
		{
			localWSGetPreventasBonificacion.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasBonificacion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasBonificacion: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreventasBonificacion.", 1);
		}
	}

	private class WSGetPreventasBonificacion  extends AsyncTask<Void, Integer, Boolean>
	{
		String GETPREVENTASBONIFICACION_METHOD_NAME = "GetPreVentasBonificacion";
		String GETPREVENTASBONIFICACION_SOAP_ACTION = NAMESPACE + GETPREVENTASBONIFICACION_METHOD_NAME;

		boolean WSObtenerPreventasBonificacion;
		ArrayList<PreventaBonificacionWSResult> preventaBonificacionWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventasBonificacion = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPREVENTASBONIFICACION_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETPREVENTASBONIFICACION_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaBonificacionWSResult>>(){ }.getType();
					preventaBonificacionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventasBonificacion = (preventaBonificacionWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreventasBonificacion = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreventasBonificacion: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreventasBonificacion: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPreventasBonificacion no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventasBonificacion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventas Bonificacion que descargar. " + error, 1);

				ObtenerVentasBonificacion();
				return;
			}

			if (!BorrarPreventasBonificacion())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventas Bonificacion.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaBonificacionBLL().InsertarPreventaBonificacion(preventaBonificacionWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Bonificacion: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Bonificacion: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventas Bonificacion.", 1);
				return;
			}
			ObtenerVentasBonificacion();
		}
	}

	private void ObtenerPreventasBonificacionProvincia()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo preventas bonificacion provincia ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetPreventasBonificacionProvincia localWSGetPreventasBonificacionProvincia = new WSGetPreventasBonificacionProvincia();

		try
		{
			localWSGetPreventasBonificacionProvincia.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasBonificacionProvincia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasBonificacionProvincia: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreventasBonificacionProvincia.", 1);
		}
	}

	private class WSGetPreventasBonificacionProvincia extends AsyncTask<Void, Integer, Boolean>
	{
		String GETPREVENTASBONIFICACIONPROV_METHOD_NAME = "GetPreVentasBonificacionProvincia";
		String GETPREVENTASBONIFICACIONPROV_SOAP_ACTION = NAMESPACE + GETPREVENTASBONIFICACIONPROV_METHOD_NAME;

		boolean WSObtenerPreventasBonificacionProv;
		ArrayList<PreventaBonificacionWSResult> preventaBonificacionWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventasBonificacionProv = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPREVENTASBONIFICACIONPROV_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETPREVENTASBONIFICACIONPROV_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaBonificacionWSResult>>(){ }.getType();
					preventaBonificacionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventasBonificacionProv = (preventaBonificacionWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerPreventasBonificacionProv = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreventasBonificacionProvincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreventasBonificacionProvincia: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPreventasBonificacionProvincia no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventasBonificacionProv)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventas Bonificacion Provincia que descargar. " + error, 1);
				ObtenerVentasBonificacion();
				return;
			}

			if (!BorrarPreventasBonificacion())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventas Bonificacion Provincia.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaBonificacionBLL().InsertarPreventaBonificacion(preventaBonificacionWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Bonificacion Provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Bonificacion Provincia: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventas Bonificacion Provincia.", 1);
				return;
			}
			ObtenerVentasBonificacion();
		}
	}

	private boolean BorrarPreventasBonificacion()
	{
		try
		{
			return new PreventaBonificacionBLL().BorrarPreventasBonificacion();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasBonificacion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los preventasBonificacion: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerVentasBonificacion()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo ventas bonificacion ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetVentasBonificacion localWSGetVentasBonificacion = new WSGetVentasBonificacion();

		try
		{
			localWSGetVentasBonificacion.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetVentasBonificacion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetVentasBonificacion: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetVentasBonificacion.", 1);
		}
	}

	private class WSGetVentasBonificacion  extends AsyncTask<Void, Integer, Boolean>
	{
		String GETVENTASBONIFICACION_METHOD_NAME = "GetVentasBonificacion";
		String GETVENTASBONIFICACION_SOAP_ACTION = NAMESPACE + GETVENTASBONIFICACION_METHOD_NAME;

		boolean WSObtenerVentasBonificacion;
		ArrayList<VentaBonificacionWSResult> ventaBonificacionWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerVentasBonificacion = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETVENTASBONIFICACION_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETVENTASBONIFICACION_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<VentaBonificacionWSResult>>(){ }.getType();
					ventaBonificacionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerVentasBonificacion = (ventaBonificacionWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerVentasBonificacion = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetVentasBonificacion: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetVentasBonificacion: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetVentasBonificacion no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerVentasBonificacion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Ventas Bonificacion que descargar. " + error, 1);
				ObtenerTipoNegocio();
				return;
			}

			if (!BorrarVentasBonificacion())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Ventas Bonificacion.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new VentaBonificacionBLL().InsertarVentaBonificacion(ventaBonificacionWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Ventas Bonificacion: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Ventas Bonificacion: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Ventas Bonificacion.", 1);
				return;
			}
			ObtenerTipoNegocio();
		}
	}

	private boolean BorrarVentasBonificacion()
	{
		try
		{
			return new VentaBonificacionBLL().BorrarVentasBonificacion();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ventasBonificacion: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los ventasBonificacion: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerTipoNegocio()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargando tipos de negocio ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerTipoNegocio wsObtenerTipoNegocio = new WSObtenerTipoNegocio();

		try
		{
			wsObtenerTipoNegocio.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerTipoNegocio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerTipoNegocio: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerTipoNegocio.", 1);
		}
	}

	private class WSObtenerTipoNegocio extends AsyncTask<Void, Integer, Boolean>
	{
		String TIPONEGOCIO_METHOD_NAME = "GetTiposNegocio";
		String TIPONEGOCIO_SOAP_ACTION = NAMESPACE + TIPONEGOCIO_METHOD_NAME;

		boolean WSObtenerTipoNegocio = false;
		ArrayList<TipoNegocioWSResult> tipoNegocioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerTipoNegocio = false;

			Fecha fecha = new Fecha();

			SoapObject localSoapObject1 = new SoapObject(NAMESPACE,TIPONEGOCIO_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerTipos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject1.addProperty("givendata", encriptedObtenerTipos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.TIPONEGOCIO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<TipoNegocioWSResult>>(){ }.getType();
					tipoNegocioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerTipoNegocio = (tipoNegocioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocio: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocio: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerTipoNegocio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerTipoNegocio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Tipo Negocio que descargar. " + error, 1);
				return;
			}

			if (!borrarTiposNegocio())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Tipo Negocio.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new TipoNegocioBLL().InsertarTipoNegocio(tipoNegocioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Tipo Negocio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Tipo Negocio: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Tipo Negocio.", 1);
				return;
			}
			ObtenerPreventasProductoPOP();

		}
	}

	private boolean borrarTiposNegocio()
	{
		TipoNegocioBLL theBLL = new TipoNegocioBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarTiposNegocio();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreventasProductoPOP()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargando productos POP ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventasProductoPOP wsObtenerPreventasProductoPOP = new WSObtenerPreventasProductoPOP();

		try
		{
			wsObtenerPreventasProductoPOP.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductosPopByDistribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductosPopByDistribuidor: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSPreventasProductoPopByDistribuidor.", 1);
		}
	}

	private class WSObtenerPreventasProductoPOP extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTAPRODUCTOPOP_METHOD_NAME = "GetPreVentasProductosPopByDistribuidor";
		String PREVENTAPRODUCTOPOP_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOPOP_METHOD_NAME;

		boolean WSObtenerPreventaProductoPOP = false;
		ArrayList<PreventaProductoPOPWSResult> preventaProductoPOPWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventaProductoPOP = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTAPRODUCTOPOP_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.PREVENTAPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaProductoPOPWSResult>>(){ }.getType();
					preventaProductoPOPWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventaProductoPOP = (preventaProductoPOPWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductosPopByDistribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductoPopByDistribuidor: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventasProductoPOP no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventaProductoPOP)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventas Producto POP que descargar. " + error, 1);
				ObtenerProductosPOPByDistribuidor();
				return;
			}

			if (!borrarPreventasProductoPOP())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventas Producto POP.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaProductoPOPBLL().InsertarPreventaProductoPOP(preventaProductoPOPWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Producto POP: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventas Producto POP: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventas Producto POP.", 1);
				return;
			}
			ObtenerProductosPOPByDistribuidor();
		}
	}

	private boolean borrarPreventasProductoPOP()
	{
		PreventaProductoPOPBLL theBLL = new PreventaProductoPOPBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarPreventasProductoPOP();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoPOP: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoPOP: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerProductosPOPByDistribuidor()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargando productos POP ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerProductosPOP wsObtenerProductosPOP = new WSObtenerProductosPOP();

		try
		{
			wsObtenerProductosPOP.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerGetProductosPOPByVendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerGetProductosPOPByVendedor: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosPOPByVendedor.", 1);
		}
	}

	private class WSObtenerProductosPOP extends AsyncTask<Void, Integer, Boolean>
	{
		String PRODUCTOSPOP_METHOD_NAME = "GetProductosPopByDistribuidor";
		String PRODUCTOSPOP_SOAP_ACTION = NAMESPACE + PRODUCTOSPOP_METHOD_NAME;

		boolean WSObtenerProductosPOP = false;
		ArrayList<ProductoPOPWSResult> productoPOPWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProductosPOP = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTOSPOP_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.PRODUCTOSPOP_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ProductoPOPWSResult>>(){ }.getType();
					productoPOPWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerProductosPOP = (productoPOPWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGeProductosPopByVendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopByVendedor: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosPOP no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerProductosPOP)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Productos POP que descargar. " + error, 1);
				ObtenerPreventasCambio();
				return;
			}

			if (!borrarProductosPOP())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos POP.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ProductoPOPBLL().InsertarProductoPOP(productoPOPWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos POP: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos POP: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos POP.", 1);
				return;
			}
			ObtenerPreventasCambio();
		}
	}

	private boolean borrarProductosPOP()
	{
		ProductoPOPBLL theBLL = new ProductoPOPBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarProductosPOP();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopByVendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopByVendedor: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreventasCambio()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargando productos para cambio ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventasCambio wsObtenerPreventasCambio = new WSObtenerPreventasCambio();

		try
		{
			wsObtenerPreventasCambio.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSOGetPreventasCambioByDistribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSOGetPreventasCambioByDistribuidor: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreventasCambioByDistribuidor.", 1);
		}
	}

	private class WSObtenerPreventasCambio extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTASCAMBIO_METHOD_NAME = "GetPreVentasCambio";
		String PREVENTASCAMBIO_SOAP_ACTION = NAMESPACE + PREVENTASCAMBIO_METHOD_NAME;

		boolean WSObtenerPreventasCambio = false;
		ArrayList<PreventaCambioWSResult> preventaCambioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventasCambio = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTASCAMBIO_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.PREVENTASCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaCambioWSResult>>(){ }.getType();
					preventaCambioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventasCambio = (preventaCambioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasCambio: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasCambio: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventasCambio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventasCambio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventas Cambio que descargar. "  + error, 1);
				ObtenerPreventasProductoCambio();
				return;
			}

			if (!borrarPreventasCambio())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventas Cambio.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaCambioBLL().InsertarPreventaCambio(preventaCambioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Cambio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventas Cambio: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventas Cambio.", 1);
				return;
			}
			ObtenerPreventasProductoCambio();
		}
	}

	private boolean borrarPreventasCambio()
	{
		PreventaCambioBLL theBLL = new PreventaCambioBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarPreventasCambio();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasByDistribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreventasByDistribuidor: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerPreventasProductoCambio()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargando proeventas con cambio ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerPreventasProductoCambio wsObtenerPreventasProductoCambio = new WSObtenerPreventasProductoCambio();

		try
		{
			wsObtenerPreventasProductoCambio.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductosCambioByDistribuidor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductosCambioByDistribuidor: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSPreventasProductoCambioByDistribuidor.", 1);
		}
	}

	private class WSObtenerPreventasProductoCambio extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTAPRODUCTOCAMBIO_METHOD_NAME = "GetPreVentasProductosCambioByDistribuidor";
		String PREVENTAPRODUCTOCAMBIO_SOAP_ACTION = NAMESPACE + PREVENTAPRODUCTOCAMBIO_METHOD_NAME;

		boolean WSObtenerPreventaProductoCambio = false;
		ArrayList<PreventaProductoCambioWSResult> preventaProductoCambioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerPreventaProductoCambio = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTAPRODUCTOCAMBIO_METHOD_NAME);
			DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS = new DistribuidorAlmacenFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_almacenId(),
					loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String distribuidorAlmacenFechaJson = new JSonParser().GenerarDistribuidorAlmacenFechaJson(distribuidorAlmacenFechaWS);
			String encriptedObtenerPreventas = new AES().Encriptar(distribuidorAlmacenFechaJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerPreventas);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.PREVENTAPRODUCTOCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PreventaProductoCambioWSResult>>(){ }.getType();
					preventaProductoCambioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerPreventaProductoCambio = (preventaProductoCambioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductosCambioByDistribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreVentasProductoCambioByDistribuidor: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}
			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPreventasProductoCambio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerPreventaProductoCambio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Preventas Producto Cambio que descargar. " + error, 1);
				ObtenerProductosCambio();
				return;
			}

			if (!borrarPreventasProductoCambio())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Preventas Producto Cambio.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new PreventaProductoCambioBLL().InsertarPreventaProductoCambio(preventaProductoCambioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Preventas Producto Cambio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Preventas Producto Cambio: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Preventas Producto Cambio.", 1);
				return;
			}
			ObtenerProductosCambio();
		}
	}

	private boolean borrarPreventasProductoCambio()
	{
		boolean borrado = false;
		try
		{
			return new PreventaProductoCambioBLL().BorrarPreventasProductoCambio();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoCambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las preventasProductoCambio: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerProductosCambio()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargandolos productos para cambio ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerProductosParaCambio wsObtenerProductosParaCambio = new WSObtenerProductosParaCambio();

		try
		{
			wsObtenerProductosParaCambio.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosParaCambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosParaCambio: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosParaCambio.", 1);
		}
	}

	private class WSObtenerProductosParaCambio extends AsyncTask<Void, Integer, Boolean>
	{
		String PRODUCTOCAMBIO_METHOD_NAME = "GetProductosParaCambio";
		String PRODUCTOCAMBIO_SOAP_ACTION = NAMESPACE + PRODUCTOCAMBIO_METHOD_NAME;

		boolean WSObtenerProductosCambio = false;
		ArrayList<ProductoCambioWSResult> productoCambioWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProductosCambio = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTOCAMBIO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.PRODUCTOCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ProductoCambioWSResult>>(){ }.getType();
					productoCambioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerProductosCambio = (productoCambioWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosCambio: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosCambio: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosParaCambio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerProductosCambio)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Productos Para Cambio que descargar. " + error, 1);
				ObtenerDosificacionProveedor();
				return;
			}

			if (!borrarProductosCambio())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos Para Cambio.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new ProductoCambioBLL().InsertarProductoCambio(productoCambioWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos Para Cambio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos Para Cambio: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos Para Cambio.", 1);
				return;
			}
			ObtenerDosificacionProveedor();
		}
	}

	private boolean borrarProductosCambio()
	{
		boolean borrado = false;
		try
		{
			return new ProductoCambioBLL().BorrarProductosCambios();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos para cambio: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos para cambio: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerDosificacionProveedor()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(0);
		pdEsperaObtenerDatos.setMessage("Descargando dosificaciones de los proveedores ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSObtenerDosificacionProveedor wsObtenerDosificacionProveedor = new WSObtenerDosificacionProveedor();

		try
		{
			wsObtenerDosificacionProveedor.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDosificacionesActivas.", 1);
		}
	}

	private class WSObtenerDosificacionProveedor extends AsyncTask<Void, Integer, Boolean>
	{
		String DOSIFICACIONPROVEEDOR_METHOD_NAME = "GetRegistrosActivos";
		String DOSIFICACIONPROVEEDOR_SOAP_ACTION = NAMESPACE + DOSIFICACIONPROVEEDOR_METHOD_NAME;

		boolean WSDosificacionProveedor = false;
		ArrayList<DosificacionProveedorWSResult> dosificacionProveedorWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSDosificacionProveedor = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,DOSIFICACIONPROVEEDOR_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerRegistros = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerRegistros);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.DOSIFICACIONPROVEEDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<DosificacionProveedorWSResult>>(){ }.getType();
					dosificacionProveedorWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSDosificacionProveedor = (dosificacionProveedorWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerDosificacionProveedor no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSDosificacionProveedor)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Dosificacion Proveedor que descargar. " + error, 1);
				return;
			}

			if (!borrarDosificacionesProveedor())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Dosificacion Proveedor.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new DosificacionProveedorBLL().InsertarDosificacionProveedor(dosificacionProveedorWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Dosificacion Proveedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Dosificacion Proveedor: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Dosificacion Proveedor.", 1);
				return;
			}
			ObtenerCondicionesTributarias();
		}
	}

	private boolean borrarDosificacionesProveedor()
	{
		try
		{
			return new DosificacionProveedorBLL().BorrarDosifiacionesProveedor();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerCondicionesTributarias()
	{
		pdEsperaObtenerDatos = new ProgressDialog(this);
		pdEsperaObtenerDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerDatos.setMessage("Obteniendo condiciones tributarias ...");
		pdEsperaObtenerDatos.setCancelable(false);
		pdEsperaObtenerDatos.setCanceledOnTouchOutside(false);

		WSGetNitsMonto localWSGetNitsMonto = new WSGetNitsMonto();

		try
		{
			localWSGetNitsMonto.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsMonto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsMonto: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetNitsMonto.", 1);
		}
	}

	private class WSGetNitsMonto extends AsyncTask<Void, Integer, Boolean>
	{
		String GETNITSMONTO_METHOD_NAME = "GetNitsMontosByDistribuidor";
		String GETNITSMONTO_SOAP_ACTION = NAMESPACE + GETNITSMONTO_METHOD_NAME;

		boolean GetNitsMonto;
		ArrayList<CondicionTributariaWSResult> condicionTributariaWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaObtenerDatos.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			GetNitsMonto = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETNITSMONTO_METHOD_NAME);
			VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
					loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
			String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAvances);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(GETNITSMONTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<CondicionTributariaWSResult>>(){ }.getType();
					condicionTributariaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					GetNitsMonto = (condicionTributariaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				GetNitsMonto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsMonto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsMonto: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerDatos.isShowing())
			{
				pdEsperaObtenerDatos.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitsMonto no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetNitsMonto)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los montos de los nits que descargar. " + error, 1);
				RegistrarSincronizacionDistribuidor();
				return;
			}

			if (!BorrarNitsMonto())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los montos de los nits.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new CondicionTributariaBLL().InsertarCondicionTributaria(condicionTributariaWSResults);
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Nits Monto: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Nits Monto: " + localException.getMessage());
				}
			}
			if (i == 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Nits Monto.", 1);
				return;
			}
			RegistrarSincronizacionDistribuidor();
		}
	}

	private boolean BorrarNitsMonto()
	{
		try
		{
			return new CondicionTributariaBLL().BorrarCondicionesTributarias();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las condiciones tributarias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los condiciones tributarias: " + localException.getMessage());
			}
			return false;
		}
	}

	private void RegistrarSincronizacionDistribuidor()
	{
		ArrayList<Rol> listadoRol = null;

		try
		{
			listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado por empleadoId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado por empleadoId: " + localException.getMessage());
			}
		}

		if(listadoRol == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el rol del usuario por empleadoId", 1);
		}
		else
		{
			int rol = 0;
			for(Rol item : listadoRol)
			{
				if(item.get_rol().equals("Distribuidor"))
				{
					rol =4;//Rol 4 = Distribuidor
				}

				if(item.get_rol().equals("DistribuidorProvincia"))
				{
					rol = 6;//Rol 6 = DistribuidorProvincia
				}
			}

			if(rol !=0)
			{
				long l = 0;
				try
				{
					l = new SincronizacionDatosBLL().InsertarSincronizacionDatos(
							loginEmpleado.get_empleadoId(),
							loginEmpleado.get_dia(),
							loginEmpleado.get_mes(),
							loginEmpleado.get_anio(),rol);
				}
				catch (Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionDatos distribuidor: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionDatos distribuidor: " + localException.getMessage());
					}
					theUtilidades.MostrarMensaje(getApplicationContext(), "Error al insertar la sincronizacion de datos del distribuidor.", 1);
					return;
				}

				if (l == 0)
				{
					this.theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la sincronizacion de datos del distribuidor.", 1);
				}
				else
				{
					MostrarControles(true);
					MostrarControlesEspeciales();
					theUtilidades.MostrarMensaje(getApplicationContext(), "Datos sincronizados exitosamente.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo determinar el rol del usuario.", 1);
			}
		}
	}

	private boolean VerificarVentasNoSincronizadas()
	{
		ArrayList<Venta> listadoVenta = null;

		try
		{
			listadoVenta = new VentaBLL().ObtenerVentasNoSincronizadas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas no sincronizadas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas no sincronizadas: " + localException.getMessage());
			}
		}

		if(listadoVenta == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void MostrarPantallaMapa()
	{
		if (VerificarDescargaPreventas())
		{
			startActivity(new Intent(this, Distribuidormapaentregas.class));
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe descargar las preventas para ver las entregas.", 1);
		}
	}

	private void MostrarPantallaSincronizacion()
	{
		startActivity(new Intent(this, Distribuidorsincronizacionventas.class));
	}

	private void MostrarPantallaSincronizacionAutoventa()
	{
		startActivity(new Intent(this, Distribuidorsincronizacionautoventa.class));
	}

	private void MostrarPantallaSincronizacionFacturas()
	{
		startActivity(new Intent(this, Distribuidorsincronizacionfacturas.class));
	}

	private void MostrarPantallaInventarioAutoventa()
	{
		startActivity(new Intent(this, Distribuidorinventarioautoventa.class));
	}

	private void MostrarPantallaVisualizarVentas()
	{
		startActivity(new Intent(this, Distribuidorvisualizarventas.class));
	}

	private void MostrarPantallaImpresionFacturas()
	{
		startActivity(new Intent(this, Distribuidorimpresionfacturas.class));
	}

	private void MostrarPantallaCierreEntregas()
	{
		startActivity(new Intent(this, Distribuidorcierreentregas.class));
	}

	private void MostrarPantallaLogs()
	{
		startActivity(new Intent(this, Log.class));
	}

	private void MostrarPantallaTestImpresoras()
	{
		startActivity(new Intent(this, Distribuidortestimpresora.class));
	}

	private void MostrarPantallaMenuCensista()
	{
		Intent intent = new Intent(this,Menucensista.class);
		intent.putExtra("Origen", "Menudistribuidor");
		startActivity(intent);
	}
}