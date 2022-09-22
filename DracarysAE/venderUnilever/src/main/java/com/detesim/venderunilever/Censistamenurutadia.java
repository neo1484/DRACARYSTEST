package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BLL.ApkDistRutaClienteBLL;
import BLL.ApkRutaClienteBLL;
import BLL.AsignacionRutaBLL;
import BLL.CanalRutaBLL;
import BLL.CiudadBLL;
import BLL.ClienteBLL;
import BLL.ClienteFotoBLL;
import BLL.DiaSemanaBLL;
import BLL.ExpedidoBLL;
import BLL.FotoCategoriaBLL;
import BLL.MercadoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaNombreBLL;
import BLL.ProvinciaBLL;
import BLL.RelevamientoBLL;
import BLL.RutaBLL;
import BLL.SincronizacionDatosBLL;
import BLL.TipoCalleBLL;
import BLL.TipoNegocioBLL;
import BLL.TipoNitBLL;
import BLL.VendedorZonaVentaBLL;
import BLL.ZonaBLL;
import BLL.ZonaMercadoBLL;
import BLL.ZonaVentaBLL;
import Clases.AES;
import Clases.ApkDistRutaClienteWSResult;
import Clases.ApkRutaClienteWSResult;
import Clases.AsignacionRutaWSResult;
import Clases.CanalRutaWSResult;
import Clases.CiudadWSResult;
import Clases.ClienteWSResult;
import Clases.DiaSemana;
import Clases.DiaSemanaWSResult;
import Clases.ExpedidoWSResult;
import Clases.Fecha;
import Clases.FotoCategoriaWSResult;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.MercadoWSResult;
import Clases.ParametroGeneral;
import Clases.ParametroGeneralWSResult;
import Clases.PrecioListaNombreWSResult;
import Clases.ProductoCambioWSResult;
import Clases.ProvinciaWSResult;
import Clases.Relevamiento;
import Clases.Ruta;
import Clases.RutaWSResult;
import Clases.SingleId;
import Clases.TipoCalleWSResult;
import Clases.TipoNegocioWSResult;
import Clases.TipoNitWSResult;
import Clases.VendedorDiaRutaWS;
import Clases.VendedorDiaWS;
import Clases.VendedorZonaVentaWSResult;
import Clases.ZonaMercadoWSResult;
import Clases.ZonaVentaWSResult;
import Clases.ZonaWSResult;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Censistamenurutadia extends Activity implements OnClickListener
{
	LinearLayout llCensistaMenuRutaDia;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	String URLUNILEVER = theUtilidades.get_URLUNILEVER();
	String URLENROQUE = theUtilidades.get_URLENROQUE();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String Origen;/**/
	boolean rolVendedor = false;
	boolean rolDistribuidor = false;
	boolean rolVendedorProvincia = false;
	boolean rolDistribuidorProvincia = false;

	ArrayList<Ruta> listadoRuta;
	ArrayList<DiaSemana> listadoDiaSemana;
	ParametroGeneral parametroGeneral;

	TextView tvRuta;
	TextView tvDia;
	Spinner spnRuta;
	Spinner spnDia;
	Button btnSincronizar;
	ProgressDialog pdEsperaInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistamenurutadia);

		llCensistaMenuRutaDia = (LinearLayout)findViewById(R.id.CensistaMenuRutaDiaLinearLayout);
		tvRuta = (TextView)findViewById(R.id.tvRestauracionPreventaTitulo);
		tvDia = (TextView)findViewById(R.id.tvVendedorVentaDirectaPromocionClienteContenido);
		spnRuta = (Spinner)findViewById(R.id.spnSupervisorMenuRutaDiaRuta);
		spnDia = (Spinner)findViewById(R.id.spnSupervisorMenuRutaDiaDia);
		btnSincronizar = (Button)findViewById(R.id.btnSupervisorMenuRutaDiaSincronizarDatos);
		btnSincronizar.setOnClickListener(this);

		llCensistaMenuRutaDia.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));

		Origen = "";
	    Origen = getIntent().getExtras().getString("Origen");

	    if(Origen.equals(""))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de llamada del menu censista.", 1);
	    	return;
	    }

		loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado: " + localException.getMessage());
	    	}
	    }

	    if (loginEmpleado == null)
	    {
	    	MostrarControles(false);
	    	theUtilidades.MostrarMensaje(this, "El supervisor no se logeo correctamente.", 2);
	    }
	    else
	    {
	    	MostrarControles(true);

	    	try
			{
				rolVendedor = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(),"Vendedor");
			}
			catch (Exception localException)
			{
				if (localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol vendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol vendedor: " + localException.getMessage());
				}
			}

	    	try
			{
				rolVendedorProvincia = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(),"VendedorProvincia");
			}
			catch (Exception localException)
			{
				if (localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol vendedorProvincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol vendedorProvincia: " + localException.getMessage());
				}
			}

	    	try
			{
				rolDistribuidor = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(),"Distribuidor");
			}
			catch (Exception localException)
			{
				if (localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol distribuidor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol distribuidor: " + localException.getMessage());
				}
			}

	    	try
			{
				rolDistribuidorProvincia = theUtilidades.VerificarExistenciaRolEnUsuario(loginEmpleado.get_empleadoId(),"DistribuidorProvincia");
			}
			catch (Exception localException)
			{
				if (localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol distribuidor provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(), 1, "Error al verificar el rol distribuidor provincia: " + localException.getMessage());
				}
			}

	    	if(rolVendedor || rolVendedorProvincia || rolDistribuidor || rolDistribuidorProvincia)
	    	{
	    		MostrarControles(false);
	    	}

	    	ObtenerApkRutas();
	    }
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btnSupervisorMenuRutaDiaSincronizarDatos:
			SincronizarDatos();
			break;
		}
	}

	private void MostrarControles(boolean estado)
	{
		int visibility = View.VISIBLE;
		if(!estado)
		{
			visibility = View.INVISIBLE;
		}

		tvRuta.setVisibility(visibility);
		tvDia.setVisibility(visibility);
		spnRuta.setVisibility(visibility);
		spnDia.setVisibility(visibility);
		btnSincronizar.setVisibility(visibility);
	}

	private void ObtenerApkRutas()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando rutas ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

		WSObtenerApkRutas obtenerApkRutas = new WSObtenerApkRutas();
		try
		{
			obtenerApkRutas.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutas: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerRutas.", 1);
		}
	}

	private class WSObtenerApkRutas extends AsyncTask<Void, Integer, Boolean>
	{
		String APKRUTA_METHOD_NAME = "GetApkRutas";
		String APKRUTA_SOAP_ACTION = NAMESPACE + APKRUTA_METHOD_NAME;

		boolean WSObtenerApkRuta = false;
		ArrayList<ApkRutaClienteWSResult> apkRutaClienteWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaInfo.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerApkRuta = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,APKRUTA_METHOD_NAME);
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
				localHttpTransportSE.call(APKRUTA_SOAP_ACTION,localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ApkRutaClienteWSResult>>(){ }.getType();
					apkRutaClienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerApkRuta = (apkRutaClienteWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutas: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el WSGetRutas",2);
				return;
			}

			if(!WSObtenerApkRuta)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No existen rutas que descargar. " + error,2);
				return;
			}

			if(!BorrarApkRutas())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No existen rutas que descargar.",2);
				return;
			}

			long l = 0;
			try
			{
				l = new ApkRutaClienteBLL().insertarApkRutaCliente( apkRutaClienteWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar las rutas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar las rutas: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la ruta.",2);
			}

			ObtenerDiasSemana();
		}
	}

	private boolean BorrarApkRutas()
	{
		try
		{
			return new ApkRutaClienteBLL().BorrarApksRutaCliente();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apk rutas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apk rutas: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerDiasSemana()
	{
	    pdEsperaInfo = new ProgressDialog(this);
	    pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdEsperaInfo.setMessage("Descargando dias semana ...");
	    pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

	    WSObtenerDiasSemana wsObtenerDiasSemana = new WSObtenerDiasSemana();

	    try
	    {
	    	wsObtenerDiasSemana.execute();
		}
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerDiaSemana: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerDiaSemana: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerDiaSemana.", 1);
	    }
	}

	private class WSObtenerDiasSemana extends AsyncTask<Void, Integer, Boolean>
	{
		String DIASEMANA_METHOD_NAME = "GetDiasSemana";
		String DIASEMANA_SOAP_ACTION = NAMESPACE + this.DIASEMANA_METHOD_NAME;

		boolean WSObtenerDiaSemana = false;
		ArrayList<DiaSemanaWSResult> diaSemanaWSResults;
		String error;

		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerDiaSemana = false;

			SoapObject localSoapObject1 = new SoapObject(NAMESPACE,DIASEMANA_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject1.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(DIASEMANA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<DiaSemanaWSResult>>(){ }.getType();
					diaSemanaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerDiaSemana = (diaSemanaWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDiasSemana: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDiasSemana: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if (!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDiasSemana.", 2);
				return;
			}

			if(!WSObtenerDiaSemana)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron dias semana que descargar. " + error, 2);
				return;
			}

			if(!BorrarDiasSemana())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los dias de la semana.", 2);
				return;
			}

			long l=0;
			try
			{
				l = new DiaSemanaBLL().InsertarDiaSemana( diaSemanaWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los dias de la semana: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los dias de la semana: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el diaSemana", 2);
				return;
			}

			//si es rol vendedor o distribuidor
			if(rolVendedor || rolVendedorProvincia || rolDistribuidor || rolDistribuidorProvincia)
			{
				ObtenerTipoNegocio();
			}
			else
			{
				CargarInformacion();
			}
		}
	}

	private boolean BorrarDiasSemana()
	{
		try
		{
			return new DiaSemanaBLL().BorrarDiasSemana();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los diasSemana: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los diasSemana: " + localException.getMessage());
			}
			return false    ;
		}
	}

	private void CargarInformacion()
	{
		 CargarRutas();
		 CargarDiasSemana();
	}

	private void CargarRutas()
	{
		ArrayList<Ruta> listadoRuta = new ArrayList<Ruta>();
		try
	    {
			listadoRuta = new RutaBLL().ObtenerRutas();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las rutas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las rutas: " + localException.getMessage());
	    	}
	    }

		if (listadoRuta.size() > 0)
	    {
	        ArrayAdapter<Ruta> localArrayAdapter = new ArrayAdapter<Ruta>(this,R.layout.disenio_spinner,listadoRuta);
	        spnRuta.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron rutas que desplegar.", 1);
		}
	}

	private void CargarDiasSemana()
	{
		ArrayList<DiaSemana> listadoDiaSemana = new ArrayList<DiaSemana>();
		try
	    {
			listadoDiaSemana = new DiaSemanaBLL().ObtenerDiasSemana();
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los dias semana: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener los dias semana: " + localException.getMessage());
	    	}
	    }

		if (listadoDiaSemana.size() > 0)
	    {
	        ArrayAdapter<DiaSemana> localArrayAdapter = new ArrayAdapter<DiaSemana>(this,R.layout.disenio_spinner,listadoDiaSemana);
	        spnDia.setAdapter(localArrayAdapter);
	    }
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los dias semana que desplegar.", 1);
		}
	}

	private void SincronizarDatos()
	{
		if(((Ruta)spnRuta.getSelectedItem()).get_rutaId()==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La ruta ruta es requerida.", 1);
		}
		else if(((DiaSemana)spnDia.getSelectedItem()).get_id() == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El dia es requerido.", 1);
		}
		else
		{
			MostrarControles(false);
			ObtenerClientes();
		}
	}

	private void ObtenerClientes()
	{
	    pdEsperaInfo = new ProgressDialog(this);
	    pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdEsperaInfo.setMessage("Descargando clientes ...");
	    pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

	    WSObtenerClientes wsObtenerClientes = new WSObtenerClientes();

	    try
	    {
	    	wsObtenerClientes.execute();

	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByRutaYDia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByRutaYDia: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesByRutaYDia.", 1);
	    }
	}

	private class WSObtenerClientes extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTE_METHOD_NAME = "GetClientesByRutaYDia";
		String CLIENTE_SOAP_ACTION = NAMESPACE + CLIENTE_METHOD_NAME;

		boolean WSObtenerCliente = false;
		ArrayList<ClienteWSResult> clienteWSResults;
		String error;

		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerCliente = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTE_METHOD_NAME);
			VendedorDiaRutaWS vendedorDiaRutaWs = new VendedorDiaRutaWS(0, ((DiaSemana)spnDia.getSelectedItem()).get_diaSemanaId(), ((Ruta)spnRuta.getSelectedItem()).get_rutaId(),
					loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaRutaWSJson = new JSonParser().GenerarVendedorDiaRutaJson(vendedorDiaRutaWs);
			String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaRutaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerClientes);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.CLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteWSResult>>(){ }.getType();
					clienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerCliente = (clienteWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerCliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerCliente: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSObtenerClientes.", 2);
				return;
			}

			if(!WSObtenerCliente)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron clientes que descargar. " + error, 2);
				ObtenerTipoNegocio();
				return;
			}

			if(!BorrarClientes())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes.", 2);
				return;
			}

			Fecha theFecha = theUtilidades.ObtenerFecha();

			long l=0;
			try
			{
				l = new ClienteBLL().InsertarCliente( clienteWSResults, theFecha );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: " + localException.getMessage());
				}
			}
			if(l <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los clientes.", 2);
				return;
			}

			ObtenerTipoNegocio();
		}
	}

	private boolean BorrarClientes()
	{
        try
        {
			return new ClienteBLL().BorrarClientes();
        }
        catch(Exception localException)
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: " + localException.getMessage());
        	}
        	return false;
        }
	}

	private void ObtenerTipoNegocio()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setMessage("Descargando tipos de negocio ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

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
			pdEsperaInfo.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerTipoNegocio = false;

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
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerTipoNegocio no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerTipoNegocio )
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
				i = new TipoNegocioBLL().InsertarTipoNegocio( tipoNegocioWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Tipo Negocio: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Tipo Negocio: " + localException.getMessage());
				}
			}

			if(i<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los tipos de negocio.", 2);
				return;
			}

			ObtenerVendedorZonaVenta();
		}
	}

	private boolean borrarTiposNegocio()
	{
		try
		{
			return new TipoNegocioBLL().BorrarTiposNegocio();
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

	private void ObtenerVendedorZonaVenta()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo rutas cliente ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

		WSGetVendedorZonaVenta localWSGetVendedorZonaVenta = new WSGetVendedorZonaVenta();

	    try
	    {
	    	localWSGetVendedorZonaVenta.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetZonaVentaVendedorByVendedorAndDiaVisita: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetZonaVentaVendedorByVendedorAndDiaVisita: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice GetZonaVentaVendedorByVendedorAndDiaVisita.", 1);
	    }
	}

	private class WSGetVendedorZonaVenta extends AsyncTask<Void, Integer, Boolean>
	{
		String VENZONVEN_METHOD_NAME = "UNI_GetZonaVentaVendedorByVendedorAndDiaVisita";
		String VENZONVEN_SOAP_ACTION = NAMESPACE + VENZONVEN_METHOD_NAME;

		boolean WSObtenerVenZonVen;
		ArrayList<VendedorZonaVentaWSResult> vendedorZonaVentaWSResults;
		String error;

		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerVenZonVen = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,VENZONVEN_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(VENZONVEN_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<VendedorZonaVentaWSResult>>(){ }.getType();
					vendedorZonaVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerVenZonVen = (vendedorZonaVentaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerVenZonVen = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonaVentaVendedorByVendedorAndDiaVisita: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonaVentaVendedorByVendedorAndDiaVisita: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice GetZonaVentaVendedorByVendedorAndDiaVisita no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSObtenerVenZonVen)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron rutas que descargar. " + error, 1);
				return;
			}

			if(!BorrarVendedorZonaVenta())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar zonas de venta del vendedor.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new VendedorZonaVentaBLL().InsertarVendedorZonaVenta( vendedorZonaVentaWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el vendedorZonaVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el vendedorZonaVenta: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las rutas del vendedor.", 1);
				return;
			}

			ObtenerAsignacionesVendedor();
		}
	}

	private boolean BorrarVendedorZonaVenta()
	{
		try
		{
			return new VendedorZonaVentaBLL().BorrarVendedoresZonaVenta();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas del vendedor: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas del vendedor: " + localException.getMessage());
        	}
			return false;
        }
	}

	private void ObtenerAsignacionesVendedor()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo asignaciones vendedor ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

		WSGetAsignacionesVendedor localWSGetAsignacionesVendedor = new WSGetAsignacionesVendedor();

	    try
	    {
	    	localWSGetAsignacionesVendedor.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetAsignacionByVendedorYDia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetAsignacionByVendedorYDia: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice GetAsignacionByVendedorYDia.", 1);
	    }
	}

	private class WSGetAsignacionesVendedor extends AsyncTask<Void, Integer, Boolean>
	{
		String ASIVEN_METHOD_NAME = "UNI_GetAsignacionByVendedorYDia";
		String ASIVEN_SOAP_ACTION = NAMESPACE + ASIVEN_METHOD_NAME;

		boolean WSObtenerAsiVen;
		ArrayList<AsignacionRutaWSResult> vendedorZonaVentaWSResults;
		String error;

		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAsiVen = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,ASIVEN_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerAsignaciones = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerAsignaciones);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(ASIVEN_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<AsignacionRutaWSResult>>(){ }.getType();
					vendedorZonaVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerAsiVen = (vendedorZonaVentaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAsiVen = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice UNI_GetAsignacionByVendedorYDia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice UNI_GetAsignacionByVendedorYDia: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAsignacionesVendedor no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerAsiVen)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Get Asignaciones Vendedor que descargar. " + error, 1);
				return;
			}

			if (!BorrarAsiVen())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Get Asignaciones Vendedor.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new AsignacionRutaBLL().InsertarAsignacionRuta( vendedorZonaVentaWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Get Asignaciones Vendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Get Asignaciones Vendedor: " + localException.getMessage());
				}
			}

			if(i<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las asignaciones del vendedor.", 2);
				return;
			}

			ObtenerFotosCategoria();
		}
	}

	private boolean BorrarAsiVen()
	{
		try
		{
			return new AsignacionRutaBLL().BorrarAsignacionesRuta();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las asignaciones rutas del vendedor: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las asignaciones rutas del vendedor: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	/*
	private void ObtenerRutasVendedor()
	{
		pdEsperaObtenerRutaVendedor = new ProgressDialog(this);
		pdEsperaObtenerRutaVendedor.setProgressStyle(0);
		pdEsperaObtenerRutaVendedor.setTitle("Sincronizacion");
		pdEsperaObtenerRutaVendedor.setMessage("Descargando rutas vendedor ...");
		pdEsperaObtenerRutaVendedor.setCancelable(false);
		pdEsperaObtenerRutaVendedor.setMax(100);
		pdEsperaObtenerRutaVendedor.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() 
		{
			  @Override
			  public void onClick(DialogInterface dialog, int which) 
			  {
				  if(pdEsperaObtenerRutaVendedor.isShowing())
				  {
					  pdEsperaObtenerRutaVendedor.dismiss();
				  }
			  }
		});
		  
		WSObtenerRutasVendedor wsObtenerRutasVendedor = new WSObtenerRutasVendedor();
		  
		try
		{
			wsObtenerRutasVendedor.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerRutasVendedor: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerRutasVendedor: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerRutasVendedor.", 1);
		    return;
		}
	}
	
	private class WSObtenerRutasVendedor extends AsyncTask<Void, Integer, Boolean>
	{
		String RUTAVENDEDOR_METHOD_NAME = "GetRutaVendedorByVendedorAndDiaVisita";
		String RUTAVENDEDOR_SOAP_ACTION = NAMESPACE + RUTAVENDEDOR_METHOD_NAME;
		boolean WSObtenerRutaVendedor = false; 
		SoapObject soapObjects;
		int totalItems;
    
		protected void onPreExecute()
	    {
			pdEsperaObtenerRutaVendedor.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerRutaVendedor = false;
      
			SoapObject localSoapObject1 = new SoapObject(NAMESPACE,RUTAVENDEDOR_METHOD_NAME);
			localSoapObject1.addProperty("vendedorId", loginEmpleado.get_empleadoId());
			localSoapObject1.addProperty("diaVisitaId", theUtilidades.ObtenerDiaId());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.RUTAVENDEDOR_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetRutaVendedorByVendedorAndDiaVisitaResult");
				if(soapObjects!=null)
				{
					totalItems = soapObjects.getPropertyCount();
				}
				
				if(totalItems>0)
				{
					WSObtenerRutaVendedor = true;		
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutaVendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutaVendedor: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerRutaVendedor.isShowing())
			{
				pdEsperaObtenerRutaVendedor.dismiss();
			}
			
			if(ejecutado)
			{
				if (WSObtenerRutaVendedor)
				{
					if(borrarRutasVendedor())
					{
						for(int i=0;i<totalItems;i++)
						{
							SoapObject soapObject = (SoapObject)soapObjects.getProperty(i);
							
							long l=0;
							try
							{
								l = new RutaVendedorBLL().InsertarRutaVendedor(
										Integer.parseInt(soapObject.getPropertyAsString("VendedorId")),
										Integer.parseInt(soapObject.getPropertyAsString("RutaId")),
										Integer.parseInt(soapObject.getPropertyAsString("DiaId")),
										Integer.parseInt(soapObject.getPropertyAsString("DiaVisitaId")),
										soapObject.getPropertyAsString("Descripcion"));
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ruta vendedor: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ruta vendedor: " + localException.getMessage());
								}
								return;
							}
							
							if(l<=0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la ruta vendedor.", 2);
								return;								
							}
						}
					}
					ObtenerFotosCategoria();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No existen rutas vendedor que descargar.", 2);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El WebService ObtenerRutasVendedor no se ejecuto correctamente. ", 1);
				return;
			}
		}
	}
	
	private boolean borrarRutasVendedor()
	{
		RutaVendedorBLL theBLL = new RutaVendedorBLL();
		boolean borrado = false;
		try
		{
			borrado = theBLL.BorrarRutasVendedor();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutasVendedor: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutasVendedor: " + localException.getMessage());
			}
			return false;
		}	
	}
	*/

	private void ObtenerFotosCategoria()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando categorias de la fotos ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

	    WSObtenerFotosCategoria wsObtenerFotosCategoria = new WSObtenerFotosCategoria();

	    try
	    {
	    	wsObtenerFotosCategoria.execute();

	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategorias: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategorias: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClienteFotoCategoria.", 1);
	    }
	}

	private class WSObtenerFotosCategoria extends AsyncTask<Void, Integer, Boolean>
	{
		String FOTOCATEGORIA_METHOD_NAME = "GetClienteFotoCategorias";
		String FOTOCATEGORIA_SOAP_ACTION = NAMESPACE + FOTOCATEGORIA_METHOD_NAME;

		boolean WSObtenerFotoCategoria = false;
		ArrayList<FotoCategoriaWSResult> fotoCategoriaWSResults;
		String error;

		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerFotoCategoria = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,FOTOCATEGORIA_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerFotos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerFotos);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.FOTOCATEGORIA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<FotoCategoriaWSResult>>(){ }.getType();
					fotoCategoriaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerFotoCategoria = (fotoCategoriaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategoria: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategoria: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerFotosCategoria no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerFotoCategoria )
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Fotos Categoria que descargar. " + error, 1);
				return;
			}

			if (!BorrarFotosCategoria())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Fotos Categoria.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new FotoCategoriaBLL().InsertarFotoCategoria( fotoCategoriaWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Fotos Categoria: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Fotos Categoria: " + localException.getMessage());
				}
			}

			if(i<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las fotos categoria.", 2);
				return;
			}

			ObtenerExpedidos();
		}
	}

	private boolean BorrarFotosCategoria()
	{
        try
        {
        	return new FotoCategoriaBLL().BorrarFotosCategoria();
        }
        catch(Exception localException)
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotosCategoria: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotosCategoria: " + localException.getMessage());
        	}
        	return false;
        }
	}

 	private void ObtenerExpedidos()
	{
	    pdEsperaInfo = new ProgressDialog(this);
	    pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdEsperaInfo.setMessage("Descargando expedidos ...");
	    pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

	    WSObtenerExpedidos wsObtenerExpedidos = new WSObtenerExpedidos();

	    try
	    {
	    	wsObtenerExpedidos.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	           	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerExpedidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerExpedidos: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerExpedidos.", 1);
	    }
	}

 	private class WSObtenerExpedidos extends AsyncTask<Void, Integer, Boolean>
 	{
 		String EXPEDIDO_METHOD_NAME = "GetExpedidos";
 		String EXPEDIDO_SOAP_ACTION = NAMESPACE + EXPEDIDO_METHOD_NAME;

 		boolean WSObtenerExpedido = false;
		ArrayList<ExpedidoWSResult> expedidoWSResults;
		String error;

 		protected void onPreExecute()
 	    {
 			pdEsperaInfo.show();
 	    }

 		protected Boolean doInBackground(Void... paramVarArgs)
 		{
 			WSObtenerExpedido = false;

 			SoapObject localSoapObject = new SoapObject(NAMESPACE,EXPEDIDO_METHOD_NAME);
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
 				localHttpTransportSE.call(EXPEDIDO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ExpedidoWSResult>>(){ }.getType();
					expedidoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerExpedido = (expedidoWSResults != null);
				}
 				return true;
 			}
 			catch (Exception localException)
 			{
 				if(localException.getMessage() == null || localException.getMessage().isEmpty())
 				{
 			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetExpedidos: vacio");
 				}
 				else
 				{
 					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetExpedidos: " + localException.getMessage());
 				}
 				return true;
 			}
 		}

 		protected void onPostExecute(Boolean ejecucion)
 		{
 			if(pdEsperaInfo.isShowing())
 			{
 				pdEsperaInfo.dismiss();
 			}

 			if(!ejecucion)
 			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El WSGetExpedidos no se ejecuto correctamente.",2);
				return;
			}

 			if(!WSObtenerExpedido)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No existen expedidos que descargar. " + error,2);
				return;
			}

			if(!BorrarExpedidos())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los expedidos.",2);
				return;
			}

			long l=0;
			try
			{
				l = new ExpedidoBLL().InsertarExpedido( expedidoWSResults );
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
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el expedido.",2);
				return;
			}

			ObtenerTiposCalle();
		}
 	}

 	private boolean BorrarExpedidos()
 	{
 		try
 		{
			return new ExpedidoBLL().BorrarExpedidos();
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

	private void ObtenerTiposCalle()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando tipos calle ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

		WSObtenerTiposCalle wsObtenerTiposCallenew = new WSObtenerTiposCalle();

		try
		{
			  wsObtenerTiposCallenew.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerTiposCalle: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerTiposCalle: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerTiposCalle.", 1);
		}
	}

	private class WSObtenerTiposCalle extends AsyncTask<Void, Integer, Boolean>
	{
		String TIPOCALLE_METHOD_NAME = "GetTiposCalle";
		String TIPOCALLE_SOAP_ACTION = NAMESPACE + TIPOCALLE_METHOD_NAME;

		boolean WSObtenerTipoCalle = false;
		ArrayList<TipoCalleWSResult> tipoCalleWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaInfo.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerTipoCalle = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,TIPOCALLE_METHOD_NAME);
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
				localHttpTransportSE.call(TIPOCALLE_SOAP_ACTION,localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<TipoCalleWSResult>>(){ }.getType();
					tipoCalleWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerTipoCalle = (tipoCalleWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTiposCalle: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTiposCalle: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSGetTiposCalle.", 2);
				return;
			}

			if(!WSObtenerTipoCalle)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existen tipos de calle que descargar. "  + error, 2);
				return;
			}

			if(!BorrarTiposCalle())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los tiposCalle.", 2);
				return;
			}

			long l =0;
			try
			{
				l = new TipoCalleBLL().InsertarTipoCalle( tipoCalleWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar tipoCalle: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar tipoCalle: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el tipo calle.", 2);
				return;
			}

			ObtenerCiudades();
        }
	}

	private boolean BorrarTiposCalle()
	{
		try
		{
			return new TipoCalleBLL().BorrarTiposCalle();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos de calle: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos de calle: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerCiudades()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando ciudades ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

		WSObtenerCiudades WSObtenerCiudades = new WSObtenerCiudades();

		try
		{
			  WSObtenerCiudades.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCiudades.", 1);
		}
	}

	private class WSObtenerCiudades extends AsyncTask<Void, Integer, Boolean>
	{
		String CIUDADES_METHOD_NAME = "GetCiudades";
		String CIUDADES_SOAP_ACTION = NAMESPACE + CIUDADES_METHOD_NAME;

		boolean WSObtenerCiudades = false;
		ArrayList<CiudadWSResult> ciudadWSResults;
		String error;

		protected void onPreExecute()
		{
			pdEsperaInfo.show();
		}

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerCiudades = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,CIUDADES_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerCiudades = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerCiudades);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(CIUDADES_SOAP_ACTION,localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<CiudadWSResult>>(){ }.getType();
					ciudadWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerCiudades = (ciudadWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerCiudades no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerCiudades)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Ciudades que descargar. " + error, 1);
				return;
			}

			if (!BorrarCiudades())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Ciudades.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new CiudadBLL().InsertarCiudad( ciudadWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Ciudades: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Ciudades: " + localException.getMessage());
				}
			}

			if(i<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las ciudades.", 2);
				return;
			}

			ObtenerCanalesRuta();
        }
	}

	private boolean BorrarCiudades()
	{
		try
		{
			return new CiudadBLL().BorrarCiudades();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ciudades: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ciudades: " + localException.getMessage());
			}
			return false;
		}
	}

	private void ObtenerCanalesRuta()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo canales ruta ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

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
			pdEsperaInfo.show();
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
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCanalesRuta no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetCanalesRuta )
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron canales de ruta que descargar. " + error, 1);
				return;
			}

			if (!BorrarCanalesRuta())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los Canales Ruta.", 1);
				return;
			}
			long i = 0;
			try
			{
				i = new CanalRutaBLL().InsertarCanalRuta( canalRutaWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Canales Ruta: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Canales Ruta: " + localException.getMessage());
				}
			}
			if (i <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los canales de ruta.", 1);
				return;
			}
			ObtenerTiposNit();

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

	private void ObtenerTiposNit()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo tipos NIT ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

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
			pdEsperaInfo.show();
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
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetTiposNit no se ejecuto correctamente. ", 1);
				return;
			}

			if (!GetTiposNit)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Get Tipos Nit que descargar. " + error, 1);
				return;
			}

			if (!BorrarTiposNit())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Get Tipos Nit.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new TipoNitBLL().InsertarTipoNit(tipoNitWSResults);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el tipo de nit: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el tipo de nit: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el tipo de NIT.", 1);
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
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo parametros generales ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);

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
			pdEsperaInfo.show();
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
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerParametrosGenerales no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerParametrosGenerales )
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
				i = new ParametroGeneralBLL().InsertarParametroGeneral( parametroGeneralWSResult );
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

			if(BorrarClienteFoto())
			{
				if(ObtenerParametroGeneral())
				{
					if(Origen.equals("Menuvendedor"))
					{
						ObtenerClientesVendedor();
					}

					if(Origen.equals("Menudistribuidor"))
					{
						ObtenerClientesDistribuidor();
					}

					if(Origen.equals("Menuprincipal"))
					{
						RegistrarSincronizacionCensista();
					}
				}
			}
			else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las fotos de los clientes.", 1);
			}
		}
	}

	private boolean BorrarClienteFoto()
	{
		try
		{
			return new ClienteFotoBLL().BorrarClientesFoto();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clienteFoto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clienteFoto: " + localException.getMessage());
			}
		}
		return false;
	}
	
	private boolean BorrarParametrosGenerales()
	{
		try
		{
			return new ParametroGeneralBLL().BorrarParametrosGenerales(); }
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
	
	private void ObtenerClientesVendedor()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando clientes ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
	    WSObtenerClientesVendedor wsObtenerClientesVendedor = new WSObtenerClientesVendedor();
	    
	    try
	    {
	    	wsObtenerClientesVendedor.execute();
	    		    	
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesByVendedorYDiaVisita.", 1);
	    }
	    
	}
	
	private class WSObtenerClientesVendedor extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTEDIAVISITA_METHOD_NAME = "UNI_GetClientesByVendedorYDiaVisita";
		String CLIENTEDIAVISITA_SOAP_ACTION = NAMESPACE + CLIENTEDIAVISITA_METHOD_NAME;
    
		boolean WSObtenerClienteDiaVisita = false;
		ArrayList<ClienteWSResult> clienteWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClienteDiaVisita = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEDIAVISITA_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerClientes);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.CLIENTEDIAVISITA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteWSResult>>(){ }.getType();
					clienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClienteDiaVisita = (clienteWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}
			
			if(!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSGetClientesByVendedorYDiaVisita.", 2);
				return;
			}

			if(!WSObtenerClienteDiaVisita)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron clientes que descargar. " + error, 2);
				return;
			}

			if(!BorrarClientesDiaVisita())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes.", 2);
				return;
			}
			Fecha theFecha = theUtilidades.ObtenerFecha();
			long l=0;
			try
			{
				l = new ClienteBLL().InsertarCliente( clienteWSResults, theFecha );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el cliente: " + localException.getMessage());
				}
			}
			if(l <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el cliente.",1);
				return;
			}

			ObtenerZonasMercado();
		}
	}            

	private boolean BorrarClientesDiaVisita()
	{   
        try
        {
        	return new ClienteBLL().BorrarClientes();
        }
        catch(Exception localException)
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: " + localException.getMessage());
        	}
        	return false;
        }
	}
		
	/*private void ObtenerApkRutaCliente()
	{
		pdEsperaObtenerApkRutas = new ProgressDialog(this);
		pdEsperaObtenerApkRutas.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerApkRutas.setTitle("Sincronizacion vendedor");
		pdEsperaObtenerApkRutas.setMessage("Obteniendo rutas cliente ...");
		pdEsperaObtenerApkRutas.setCancelable(false);
		pdEsperaObtenerApkRutas.setMax(100);
		pdEsperaObtenerApkRutas.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar",new DialogInterface.OnClickListener() 
	    {	
	    	@Override
	    	public void onClick(DialogInterface dialog, int which) 
	    	{
	    		if(pdEsperaObtenerApkRutas.isShowing())
	    		{
	    			pdEsperaObtenerApkRutas.dismiss();
	    		}
	    	}
	    });
	    
		WSGetApkRutasByVendedorYDiaVisita localWSGetApkRutasByVendedorYDiaVisita = new WSGetApkRutasByVendedorYDiaVisita();
	    
	    try
	    {
	    	localWSGetApkRutasByVendedorYDiaVisita.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutasByVendedorYDiaVisita: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutasByVendedorYDiaVisita: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetApkRutasByVendedorYDiaVisita.", 1);
		    return;
	    }    
	}
	
	private class WSGetApkRutasByVendedorYDiaVisita extends AsyncTask<Void, Integer, Boolean>
	{
		String APKRUTAS_METHOD_NAME = "GetApkRutasByVendedorYDiaVisita";
		String APKRUTAS_SOAP_ACTION = NAMESPACE + APKRUTAS_METHOD_NAME;
		boolean WSObtenerApkRutas;
		SoapObject soapApksRutas;
		int totalItems;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerApkRutas.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerApkRutas = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,APKRUTAS_METHOD_NAME);
			localSoapObject.addProperty("vendedorId",loginEmpleado.get_empleadoId());
			localSoapObject.addProperty("diaVisitaId",theUtilidades.ObtenerDiaId());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{				
				localHttpTransportSE.call(APKRUTAS_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapApksRutas = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetApkRutasByVendedorYDiaVisitaResult"));
				
				if(soapApksRutas!=null)
				{
					totalItems = soapApksRutas.getPropertyCount();
				}		
				
				if(totalItems > 0) 
				{
					WSObtenerApkRutas = true;
				}

				return true;
			}
			catch (Exception localException)
			{
				WSObtenerApkRutas = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetApkRutasByVendedorYDiaVisita: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetApkRutasByVendedorYDiaVisita: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerApkRutas.isShowing())
			{
				pdEsperaObtenerApkRutas.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSObtenerApkRutas)
				{
					if(BorrarApkRutasCliente())
					{							
						for(int i=0;i<totalItems;i++)
						{
							SoapObject soapObject = (SoapObject)this.soapApksRutas.getProperty(i);
							
							long l = 0;
							try
							{
								l = new ApkRutaClienteBLL().InsertarApkRutaCliente(
										Integer.parseInt(soapObject.getPropertyAsString("RutaId")),
										Integer.parseInt(soapObject.getPropertyAsString("DiaId")),
										soapObject.getPropertyAsString("RutaNombre"),
										soapObject.getPropertyAsString("DiaNombre"),
										soapObject.getPropertyAsString("BloquearPreventaDistancia").equals("true")?true:false,
										soapObject.getPropertyAsString("RutaCompleta").equals("true")?true:false);
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la ApkRutaCliente: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al insertar la APKRutaCliente: " + localException.getMessage());
								}
							}
							
							if(l<=0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las rutas de la apkRutaCliente.", 1);
								return;
							}
						}
						
						ObtenerZonasMercado();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las rutas de la apkRutaCliente.", 1);
			            return;	
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron las rutas de la apkRutaCliente que descargar.", 1);
					return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetApkRutasByVendedorYDiaVisita no se ejecuto correctamente. ", 1);
			} 
		}
	}
    
	private boolean BorrarApkRutasCliente()
	{	        
		try
		{
			boolean borrado = new ApkRutaClienteBLL().BorrarApksRutaCliente();
        	return borrado;
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apkRutascliente: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apkRutaCliente: " + localException.getMessage());
        	}
			return false;
        }
	}*/
	
	private void ObtenerClientesDistribuidor()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando clientes ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
	    WSObtenerClientesDistribuidor wsObtenerClientesDistribuidor = new WSObtenerClientesDistribuidor();
	    
	    try
	    {
	    	wsObtenerClientesDistribuidor.execute();
	    		    	
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByDistribuidorYDiaVisita: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByDistribuidorYDiaVisita: " + localException.getMessage());
	    	} 
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesByDistribuidorYDiaVisita.", 1);
	    }
	}
	
	private class WSObtenerClientesDistribuidor extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTEDISTDIAVISITA_METHOD_NAME = "GetClientesByDistribuidorYDiaVisita";
		String CLIENTEDISTDIAVISITA_SOAP_ACTION = NAMESPACE + CLIENTEDISTDIAVISITA_METHOD_NAME;
    
		boolean WSObtenerClienteDistDiaVisita = false;
		ArrayList<ClienteWSResult> clienteWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClienteDistDiaVisita = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEDISTDIAVISITA_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerClientes);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.CLIENTEDISTDIAVISITA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteWSResult>>(){ }.getType();
					clienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClienteDistDiaVisita = (clienteWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByDistribuidorYDiaVisita: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByDistribuidorYDiaVisita: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecucion) {
			if (pdEsperaInfo.isShowing()) {
				pdEsperaInfo.dismiss();
			}

			if (!ejecucion) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSGetClientesByDistribuidorYDiaVisita.", 2);
				return;
			}

			if (!WSObtenerClienteDistDiaVisita) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron clientes que descargar. " + error, 2);
				return;
			}

			if (!BorrarClientesDistDiaVisita()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes.", 2);
				return;
			}

			Fecha theFecha = theUtilidades.ObtenerFecha();

			long l = 0;
			try
			{
				l = new ClienteBLL().InsertarCliente(clienteWSResults, theFecha);
			} catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar el cliente: vacio");
				} else {
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar el cliente: " + localException.getMessage());
				}
			}

			if (l <= 0) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente.", 2);
				return;
			}

			ObtenerApkDistRutaCliente();
		}
	}            

	private boolean BorrarClientesDistDiaVisita()
	{   
        try
        {
        	return new ClienteBLL().BorrarClientes();
        }
        catch(Exception localException)
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: " + localException.getMessage());
        	}
        	return false;
        }
	}
	
	private void ObtenerApkDistRutaCliente()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo rutas cliente ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
		WSGetApkRutasByDistribuidorYDiaVisita localWSGetApkRutasByDistribuidorYDiaVisita = new WSGetApkRutasByDistribuidorYDiaVisita();
	    
	    try
	    {
	    	localWSGetApkRutasByDistribuidorYDiaVisita.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutasByDistribuidorYDiaVisita: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutasByDistribuidorYDiaVisita: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetApkRutasByDistribuidorYDiaVisita.", 1);
	    }    
	}
	
	private class WSGetApkRutasByDistribuidorYDiaVisita extends AsyncTask<Void, Integer, Boolean>
	{
		String APKDISTRUTAS_METHOD_NAME = "GetApkRutasByDistribuidorYDiaVisita";
		String APKDISTRUTAS_SOAP_ACTION = NAMESPACE + APKDISTRUTAS_METHOD_NAME;

		boolean WSObtenerApkDistRutas;
		ArrayList<ApkDistRutaClienteWSResult> apkDistRutaClienteWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerApkDistRutas = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,APKDISTRUTAS_METHOD_NAME);
			VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
			String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{				
				localHttpTransportSE.call(APKDISTRUTAS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ApkDistRutaClienteWSResult>>(){ }.getType();
					apkDistRutaClienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerApkDistRutas = (apkDistRutaClienteWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerApkDistRutas = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetApkRutasByDistribuidorYDiaVisita: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetApkRutasByDistribuidorYDiaVisita: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado) {
			if (pdEsperaInfo.isShowing()) {
				pdEsperaInfo.dismiss();
			}

			if (!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetApkRutasByVendedorYDiaVisita no se ejecuto correctamente. ", 1);
				return;
			}

			if (!WSObtenerApkDistRutas) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron las rutas de la apkDistRutaCliente que descargar. "  + error, 1);
				return;
			}

			if (!BorrarApkDistRutasCliente()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las rutas de la apkDistRutaCliente.", 1);
				return;
			}

			long l = 0;
			try {
				l = new ApkDistRutaClienteBLL().InsertarApkDistRutaCliente( apkDistRutaClienteWSResults );
			} catch (Exception localException) {
				if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar la ApkDistRutaCliente: vacio");
				} else {
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar la APKDistRutaCliente: " + localException.getMessage());
				}
			}

			if (l <= 0) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las rutas de la apkDistRutaCliente.", 1);
				return;
			}

			ObtenerZonasMercado();
		}
	}
    
	private boolean BorrarApkDistRutasCliente()
	{	        
		try
		{
			return new ApkDistRutaClienteBLL().BorrarApksDistRutaCliente();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apkDistRutascliente: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apkDistRutaCliente: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	//Verificamos configuraciones parametrizadas
	
	public boolean ObtenerParametroGeneral()
	{
		parametroGeneral = null;
		try
		{
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametro general: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametro general: " + localException.getMessage());
        	}
		}
		
		if(parametroGeneral == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private void ObtenerZonasVenta()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo zonas venta ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
		WSGetZonasVenta localWSGetZonasVenta = new WSGetZonasVenta();
	    
	    try
	    {
	    	localWSGetZonasVenta.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonasVenta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonasVenta: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetZonasVenta.", 1);
	    }    
	}
	
	private class WSGetZonasVenta extends AsyncTask<Void, Integer, Boolean>
	{
		String ZONASVENTA_METHOD_NAME = "GetZonaVenta";
		String ZONASVENTA_SOAP_ACTION = NAMESPACE + ZONASVENTA_METHOD_NAME;

		boolean WSZonasVenta;
		ArrayList<ZonaVentaWSResult> zonaVentaWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSZonasVenta = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,ZONASVENTA_METHOD_NAME);
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
				localHttpTransportSE.call(ZONASVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ZonaVentaWSResult>>(){ }.getType();
					zonaVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSZonasVenta = (zonaVentaWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSZonasVenta = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSZonasVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSZonasVenta: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}
			
			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetZonasVenta no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSZonasVenta)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron zonas venta que descargar. " + error, 1);
				return;
			}

			if(!BorrarZonasVenta()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las zonas venta.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new ZonaVentaBLL().InsertarZonaVenta( zonaVentaWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la zonaVenta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la zonaVenta: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la zona venta.", 1);
				return;
			}

			ObtenerZonasMercado();
		}
	}
    	
	private boolean BorrarZonasVenta()
	{	        
		try
		{
			return new ZonaVentaBLL().BorrarZonasVenta();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas venta: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas venta: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerZonasMercado()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo zonas mercado ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
		WSGetZonasMercado localWSGetZonasMercado = new WSGetZonasMercado();
	    
	    try
	    {
	    	localWSGetZonasMercado.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonasMercado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonasMercado: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetZonasMercado.", 1);
	    }    
	}
	
	private class WSGetZonasMercado extends AsyncTask<Void, Integer, Boolean>
	{
		String ZONASMERCADO_METHOD_NAME = "GetZonaMercados";
		String ZONASMERCADO_SOAP_ACTION = NAMESPACE + ZONASMERCADO_METHOD_NAME;

		boolean WSZonasMercado;
		ArrayList<ZonaMercadoWSResult> zonaMercadoWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSZonasMercado = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,ZONASMERCADO_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerZonas = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerZonas);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{				
				localHttpTransportSE.call(ZONASMERCADO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ZonaMercadoWSResult>>(){ }.getType();
					zonaMercadoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSZonasMercado = (zonaMercadoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSZonasMercado = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonasMercado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonasMercado: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetZonasMercado no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSZonasMercado)
			{
				if(parametroGeneral != null && parametroGeneral.is_zonaMercadoRequerida())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron zonas mercado que descargar. " + error, 1);
				}
				else
				{
					ObtenerZonas();
				}
				return;
			}

			if(!BorrarZonasMercado()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la zona mercado.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new ZonaMercadoBLL().InsertarZonaMercado( zonaMercadoWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Get Zonas Mercado: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Get Zonas Mercado: " + localException.getMessage());
				}
			}

			if (i <= 0) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las zonas de mercado.", 1);
				return;
			}

			ObtenerZonas();
		}
	}
    	
	private boolean BorrarZonasMercado()
	{	        
		try
		{
			return new ZonaMercadoBLL().BorrarZonasMercado();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas mercado: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas mercado: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerZonas()
	{
		pdEsperaInfo = new ProgressDialog(this);
	    pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdEsperaInfo.setMessage("Descargando zonas ...");
	    pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
	    WSObtenerZonas wsObtenerZonas = new WSObtenerZonas();
	    
	    try
	    {
	    	wsObtenerZonas.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerZonas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerZonas: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerZonas.", 1);
	    }
	}
	
	private class WSObtenerZonas extends AsyncTask<Void, Integer, Boolean>
	{
		String ZONA_METHOD_NAME = "GetZonas";
		String ZONA_SOAP_ACTION = NAMESPACE + ZONA_METHOD_NAME;
		
		boolean WSObtenerZona = false;
		ArrayList<ZonaWSResult> zonaWSResults;
		String error;
		
		protected void onPreExecute()
	    {
	      pdEsperaInfo.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerZona = false;
      
			SoapObject localSoapObject1 = new SoapObject(NAMESPACE,ZONA_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerProveedores = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject1.addProperty("givendata", encriptedObtenerProveedores);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(this.ZONA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ZonaWSResult>>(){ }.getType();
					zonaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerZona = (zonaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonas: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonas: " + localException.getMessage());
				}
				return true;
			} 
		}
    
		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el WSGetZonas.",2);
				return;
			}

			if(!WSObtenerZona)
			{
				if(parametroGeneral.is_zonaRequerida())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No existen zonas que descargar. " + error,2);
					return;
				}
				else
				{
					ObtenerMercados();
				}
				return;
			}

			if(!BorrarZonas())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar las zonas.",2);
				return;
			}

			long i = 0;
			try
			{
				i = new ZonaBLL().InsertarZona( zonaWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Zonas: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Zonas: " + localException.getMessage());
				}
			}

			if (i <= 0) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las zonas.", 1);
				return;
			}

			ObtenerMercados();
		}

    }
	
	private boolean BorrarZonas()
	{
		try
		{
			return new ZonaBLL().BorrarZonas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas: " + localException.getMessage());
			}
			return false;
		}
	}	
	
	private void ObtenerMercados()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo mercados ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
		WSGetMercados localWSGetMercados = new WSGetMercados();
	    
	    try
	    {
	    	localWSGetMercados.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMercados: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMercados: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMercados.", 1);
	    }    
	}
	
	private class WSGetMercados extends AsyncTask<Void, Integer, Boolean>
	{
		String MERCADOS_METHOD_NAME = "GetMercados";
		String MERCADOS_SOAP_ACTION = NAMESPACE + MERCADOS_METHOD_NAME;
		boolean WSObtenerMercados;
		ArrayList<MercadoWSResult> mercadoWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerMercados = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,MERCADOS_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerMercados = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerMercados);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{				
				localHttpTransportSE.call(MERCADOS_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<MercadoWSResult>>(){ }.getType();
					mercadoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerMercados = (mercadoWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerMercados = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMercados: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMercados: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetMercados no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSObtenerMercados)
			{
				if(parametroGeneral.is_mercadoRequerido())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron mercados que descargar. " + error, 1);
					return;
				}
				else
				{
					ObtenerPreciosLista();
				}
				return;
			}

			if(!BorrarMercados()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los mercados.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new MercadoBLL().InsertarMercado( mercadoWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Get Mercados: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Get Mercados: " + localException.getMessage());
				}
			}

			if(i <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el mercado.", 1);
				return;
			}

			ObtenerPreciosLista();
		}
	}
    
	private boolean BorrarMercados()
	{	        
		try
		{
			return new MercadoBLL().BorrarMercados();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los mercados: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los mercados: " + localException.getMessage());
        	}
			return false;
        }
	}
		
	private void ObtenerPreciosLista()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo listas de precio ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
		WSGetPreciosLista localWSGetPreciosLista = new WSGetPreciosLista();
	    
	    try
	    {
	    	localWSGetPreciosLista.execute();
	    }
	    catch(Exception localException)
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
	
	private class WSGetPreciosLista extends AsyncTask<Void, Integer, Boolean>
	{
		String PRECIOLISTA_METHOD_NAME = "GetPreciosLista";
		String PRECIOLISTA_SOAP_ACTION = NAMESPACE + PRECIOLISTA_METHOD_NAME;

		boolean WSPrecioLista;
		ArrayList<PrecioListaNombreWSResult> precioListaNombreWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSPrecioLista = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,PRECIOLISTA_METHOD_NAME);
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
				localHttpTransportSE.call(PRECIOLISTA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<PrecioListaNombreWSResult>>(){ }.getType();
					precioListaNombreWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSPrecioLista = (precioListaNombreWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSPrecioLista = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosLista: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosLista: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPreciosLista no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSPrecioLista)
			{
				if(parametroGeneral != null && parametroGeneral.is_mostrarListaPrecio())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron listas de precio que descargar. " + error, 1);
				}
				else
				{
					ObtenerProvincias();
				}
				return;
			}

			if(!BorrarPreciosLista()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los precios de lista.", 1);
				return;
			}

			long i = 0;
			try
			{
				i = new PrecioListaNombreBLL().InsetarPrecioListaNombre( precioListaNombreWSResults );
			}
			catch (Exception localException)
			{
				if (localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Get Precios Lista: vacio");
				}
				else
				{
					myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Get Precios Lista: " + localException.getMessage());
				}
			}

			if(i<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los precios de lista.", 2);
				return;
			}

			ObtenerProvincias();
		}
	}
    	
	private boolean BorrarPreciosLista()
	{	        
		try
		{
			return new PrecioListaNombreBLL().BorrarPreciosListaNombre();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios de lista: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios de lista: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerProvincias()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Descargando provincias ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
		  
		WSObtenerProvincias WSObtenerProvincias = new WSObtenerProvincias();
		  
		try
		{
			  WSObtenerProvincias.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProvincias.", 1);
		}
	}
	
	private class WSObtenerProvincias extends AsyncTask<Void, Integer, Boolean>
	{
		String PROVINCIAS_METHOD_NAME = "GetProvincias";
		String PROVINCIAS_SOAP_ACTION = NAMESPACE + PROVINCIAS_METHOD_NAME;
		
		boolean WSObtenerProvincias = false;
		ArrayList<ProvinciaWSResult> provinciaWSResults;
		String error;
    
		protected void onPreExecute()
		{
			pdEsperaInfo.show();
		}
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerProvincias = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,PROVINCIAS_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerProvincias= new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerProvincias);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(PROVINCIAS_SOAP_ACTION,localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ProvinciaWSResult>>(){ }.getType();
					provinciaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerProvincias = (provinciaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}

			if(!ejecucion) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSGetProvincias.", 2);
				return;
			}

			if(!WSObtenerProvincias)
			{
				if(parametroGeneral.is_provinciaRequerida())
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No existen provincias que descargar. " + error, 2);
					return;
				}
				else
				{
					ObtenerRelevamientos();
				}
				return;
			}

			if(!BorrarProvincias())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las provincias.", 2);
				return;
			}


			long l =0;
			try
			{
				l = new ProvinciaBLL().InsertarProvincia( provinciaWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la provincia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar la provincia: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la provincia.", 2);
				return;
			}

			ObtenerRelevamientos();
        }
	}
	
	private boolean BorrarProvincias()
	{
		try
		{
			return new ProvinciaBLL().BorrarProvincias();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las provincias: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las provincias: " + localException.getMessage());
			}
			return false;
		}
	}
	
	//Verificamos opciones de Relevamientos UNILEVER
	
	private void ObtenerRelevamientos()
	{
		pdEsperaInfo = new ProgressDialog(this);
		pdEsperaInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaInfo.setMessage("Obteniendo relevamientos ...");
		pdEsperaInfo.setCancelable(false);
		pdEsperaInfo.setCanceledOnTouchOutside(false);
	    
		WSGetRelevamientos localWSGetRelevamientos = new WSGetRelevamientos();
	    
	    try
	    {
	    	localWSGetRelevamientos.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRelevamientosActivos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRelevamientosActivos: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetRelevamientosActivos.", 1);
	    }
	}
	
	private class WSGetRelevamientos extends AsyncTask<Void, Integer, Boolean>
	{
		String RELEVAMIENTO_METHOD_NAME = "GetRelevamientosActivos";
		String RELEVAMIENTO_SOAP_ACTION = NAMESPACE + RELEVAMIENTO_METHOD_NAME;
		boolean WSRelevamiento;
		ArrayList<Relevamiento> listadoResulRelevamiento;
		String error;
				
		protected void onPreExecute()
	    {
			pdEsperaInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSRelevamiento = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,RELEVAMIENTO_METHOD_NAME);
			SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerInfo);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLENROQUE);
			
			try
			{				
				localHttpTransportSE.call(RELEVAMIENTO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<Relevamiento>>(){ }.getType();
					listadoResulRelevamiento = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSRelevamiento = (listadoResulRelevamiento.size()> 0 );
				}
				return true;
			}
			catch (Exception localException)
			{
				WSRelevamiento = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRelevamientosActivos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRelevamientosActivos: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaInfo.isShowing())
			{
				pdEsperaInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetRelevamientosActivos no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSRelevamiento)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron relevamientos que descargar. " + error, 1);
				RegistrarSincronizacionCensista();
				return;
			}

			if(!BorrarRelevamientos()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los relevamientos.", 1);
				return;
			}
			long l = 0;
			try
			{
				l = new RelevamientoBLL().InsertarRelevamiento( listadoResulRelevamiento );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el relevamiento: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el relevamiento: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el relevamiento.", 1);
				return;
			}

			RegistrarSincronizacionCensista();
		}
	}
    	
	private boolean BorrarRelevamientos()
	{	        
		try
		{
			return new RelevamientoBLL().BorrarRelevaminetos();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los relevamientos: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	//--------------------------------------------

	public void RegistrarSincronizacionCensista()
	{	    
	    long l=0;
	    
	    try
	    {
	    	l = new SincronizacionDatosBLL().InsertarSincronizacionDatos(loginEmpleado.get_empleadoId(),
	    			loginEmpleado.get_dia(),
	    			loginEmpleado.get_mes(),
	    			loginEmpleado.get_anio(),
	    			2);//Rol 2=Censista
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion de datos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacion de datos: " + localException.getMessage());
	    	} 
	    }
	    
	    if(l<=0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los datos de sincronizacion", 2);
	    }
	    else
	    {
	    	MostrarControles(true);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Sincronizacion exitosa.", 2);
	    	MostrarPantalaMenuCensista();
	    }
	  }
	
	private void MostrarPantalaMenuCensista()
	{
		Intent intent = new Intent(this, Menucensista.class);
		intent.putExtra("Origen", Origen);
		startActivity(intent);
	}
}
