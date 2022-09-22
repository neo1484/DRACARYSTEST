package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.LoginEmpleadoBLL;
import BLL.MyLogBLL;
import BLL.RolBLL;
import BLL.SincronizacionDatosBLL;
import Clases.AES;
import Clases.AdminSessions;
import Clases.Fecha;
import Clases.JSonParser;
import Clases.LogimEmpleadoWS;
import Clases.LoginEmpleado;
import Clases.LoginEmpleadoWSResult;
import Clases.Rol;
import Utilidades.Utilidades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Login extends Activity implements OnClickListener {

	static Context contexto;
	public static Context getContexto() {
		return contexto;
	}
	public void setContexto(Context context) {
		contexto = context;
	}

	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado = null;

	LinearLayout llLogin;
	EditText etLoginUsuario;
	EditText etLoginPassword;
	TextView tvLoginIniciarSesion;
	ImageView ivConfiguracion;
	TextView tvVersion;

	ArrayList<Rol> listadoRol;

	ProgressDialog pdLoginEmpleado;
	ProgressDialog pdGetRolesByEmpleado;
	Dialog dlConfiguraciones;

	MyLogBLL myLog = new MyLogBLL();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setContexto(getApplicationContext());

		llLogin = (LinearLayout) findViewById(R.id.llLogin);
		etLoginUsuario = ((EditText) findViewById(R.id.etLogUsuario));
		etLoginPassword = ((EditText) findViewById(R.id.etLogPassword));
		tvLoginIniciarSesion = ((TextView) findViewById(R.id.tvLogIngresar));
		tvLoginIniciarSesion.setOnClickListener(this);
		ivConfiguracion = (ImageView) findViewById(R.id.ivLogConfiguracion);
		ivConfiguracion.setOnClickListener(this);
		tvVersion = (TextView) findViewById(R.id.tvLogVersion);

		llLogin.setBackground(getResources().getDrawable(theUtilidades.get_fondoLogin()));

		String version = "Version: " + String.valueOf(theUtilidades.get_versionMayor()) + "." + String.valueOf(theUtilidades.get_versionMenor());
		tvVersion.setText(version);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			VerifyPermission();
		}

		try {
			loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener loginEmpleado: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener loginEmpleado: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "Ingrese sus credenciales.", 1);
		} else {
			if(checkAndroidSession() > 0)
			{
				VerificarRoles();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvLogIngresar:
				IniciarSesion();
				break;
			case R.id.ivLogConfiguracion:
				MostrarConfiguraciones();
				break;
		}
	}

	@SuppressLint("HardwareIds")
	private void MostrarConfiguraciones()
	{
		dlConfiguraciones = new Dialog(Login.this);
		dlConfiguraciones.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlConfiguraciones.setContentView(R.layout.diseniodialogo_configuraciones);
		dlConfiguraciones.setCancelable(false);
		dlConfiguraciones.setCanceledOnTouchOutside(false);
		Objects.requireNonNull(dlConfiguraciones.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));

		final TextView tvImei = dlConfiguraciones.findViewById(R.id.tvDisDiaConImei);
		final TextView tvDeviceId = dlConfiguraciones.findViewById(R.id.tvDisDiaConDeviceId);
		Button btnAceptar = dlConfiguraciones.findViewById(R.id.btnDisDiaConAceptar);
		btnAceptar.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (v.getId() == R.id.btnDisDiaConAceptar) {
					if (dlConfiguraciones.isShowing()) {
						dlConfiguraciones.dismiss();
					}
				}
			}
		});

		String imei = "";
		String deviceId = "";

		try {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			deviceId = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el imei o deviceId del telefono: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el imei o deviceId del telefono: " + localException.getMessage());
			}
		}

		if(imei == null)
		{
			tvImei.setText("No disponible.");
		}
		else
		{
			tvImei.setText(imei);
		}

		tvDeviceId.setText(deviceId);

		dlConfiguraciones.show();
	}

	private int checkAndroidSession()
	{
		AdminSessions adminSessions = new AdminSessions(Login.this);
		return adminSessions.GetSession();
	}

	public void IniciarSesion() {
		if ((etLoginUsuario.getText().toString().isEmpty()) || (etLoginPassword.getText().toString().isEmpty())) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe ingresar su usuario y contrasenia.", 2);
			return;
		}

		String usuario = etLoginUsuario.getText().toString();

		loginEmpleado = null;

		try {
			loginEmpleado = new LoginEmpleadoBLL().ObtenerLoginEmpleadoDetallePorEmpleadoUsuario(usuario);
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener loginEmpleadoPorUsuario: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener loginEmpleadoPorUsuario: " + localException.getMessage());
			}
		}

		if (theUtilidades.VerificarConexionInternet(this)) {
			EjecutarLoginEmpleado();
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existe conexion a internet, intentelo mas tarde.", 1);
		}

		/*if (loginEmpleado == null) {
			if (theUtilidades.VerificarConexionInternet(this)) {
				EjecutarLoginEmpleado();
			} else {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No existe conexion a internet, intentelo mas tarde.", 1);
			}
		} else {
			AdminSessions adminSessions = new AdminSessions(Login.this);
			adminSessions.SaveSession(loginEmpleado);
			VerificarRoles();
		}*/
	}

	private void EjecutarLoginEmpleado() {
		pdLoginEmpleado = new ProgressDialog(this);
		pdLoginEmpleado.setMessage("Verificando credenciales ...");
		pdLoginEmpleado.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdLoginEmpleado.setCancelable(false);
		pdLoginEmpleado.setCanceledOnTouchOutside(false);

		WSLoginEmpleado localWSLoginEmpleado = new WSLoginEmpleado();
		try {
			localWSLoginEmpleado.execute();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al ejecutar el webservice WSLoginEMpleado: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al ejecutar wsLoginEMpleado: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSLoginEmpleado.", 1);
		}
	}

	private class WSLoginEmpleado extends AsyncTask<Void, Integer, Boolean>
	{
		String LOGINEMPLEADO_METHOD_NAME = "LoginEmpleado";
		String LOGINEMPLEADO_SOAP_ACTION = NAMESPACE + LOGINEMPLEADO_METHOD_NAME;

		boolean WSLoginEmpleado;
		LoginEmpleadoWSResult loginEmpleadoWSResult;
		LogimEmpleadoWS loginEmpleadoWS;
		String loginEmpleadoWSJson;

		protected void onPreExecute() {
			Fecha fecha = theUtilidades.ObtenerFecha();
			if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			String imei = String.valueOf(((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
			if(imei.equals("null"))
			{
				imei = String.valueOf(android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
			}

			loginEmpleado = new LoginEmpleado();
			loginEmpleado.set_empleadoUsuario(etLoginUsuario.getText().toString());
			loginEmpleado.set_password(etLoginPassword.getText().toString());
			loginEmpleado.set_dia(fecha.get_dia());
			loginEmpleado.set_mes(fecha.get_mes());
			loginEmpleado.set_anio(fecha.get_anio());
			loginEmpleado.set_imei(imei);

			loginEmpleadoWS = new LogimEmpleadoWS(loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_password(),
												loginEmpleado.get_anio(), loginEmpleado.get_mes(), loginEmpleado.get_dia(), loginEmpleado.get_imei(),
												theUtilidades.get_versionMayor(), theUtilidades.get_versionMenor());

			loginEmpleadoWSJson = new JSonParser().GenerarLoginJson(loginEmpleadoWS);

			pdLoginEmpleado.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSLoginEmpleado = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,LOGINEMPLEADO_METHOD_NAME);
			String encriptedLogin = new AES().Encriptar(loginEmpleadoWSJson ,theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedLogin);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(LOGINEMPLEADO_SOAP_ACTION,localSoapSerializationEnvelope);

				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				Type objectType = new TypeToken<LoginEmpleadoWSResult>(){ }.getType();
				loginEmpleadoWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
				WSLoginEmpleado = (loginEmpleadoWSResult.getMensaje().equals("OK"));
				return true;
			}
			catch (Exception localException)
			{
				WSLoginEmpleado = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
	        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSLoginEmpleado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSLoginEmpleado: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdLoginEmpleado.isShowing())
			{
				pdLoginEmpleado.dismiss();
			}

			if(ejecucion)
			{
				if(WSLoginEmpleado)
				{
					loginEmpleado = new LoginEmpleado(loginEmpleadoWSResult.getEmpleadoId(),
							loginEmpleadoWSResult.getEmpleadoNombre(),
							loginEmpleadoWSResult.getEmpleadoUsuario(),
							loginEmpleadoWSResult.getDia(),
							loginEmpleadoWSResult.getMes(),
							loginEmpleadoWSResult.getAnio(),
							loginEmpleadoWSResult.getEstado(),
							loginEmpleadoWSResult.getMensaje(),
							loginEmpleadoWSResult.getAlmacenId(),
							loginEmpleadoWSResult.getRutaId(),
							"","",
							loginEmpleadoWSResult.isModificarClienteApk(),
							loginEmpleadoWSResult.getToken());

					InsertarLoginEmpleado();
				}
				else
				{
					if(loginEmpleadoWSResult.getMensaje() == null || loginEmpleadoWSResult.getMensaje().length() == 0)
					{
						theUtilidades.MostrarMensaje(getContexto(), "El webservice loginEmpleado no se ejecuto correctamente.", 1);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), loginEmpleadoWSResult.getMensaje(),1);
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El webService loginEmpleado no se ejecuto correctamente.",1);
			}
        }
	}

	public void InsertarLoginEmpleado()
	{
	    if (loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"El usuario no se logeo correctamente.",1);
	    	return;
	    }

	    boolean borrado = false;

	    try
	    {
	    	borrado = new LoginEmpleadoBLL().BorrarLoginsEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los loginsEmpleado: " + localException.getMessage());
			}
	    }

	    if(!borrado)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los loginsEmpleado.", 2);
	    	return;
	    }

	    long l=0;
	    try
	    {
	    	l = new LoginEmpleadoBLL().InsertarLoginEmpleado(loginEmpleado.get_empleadoId(),loginEmpleado.get_empleadoNombre(),
	    			loginEmpleado.get_empleadoUsuario(),loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio(),
	    			loginEmpleado.get_estado(),loginEmpleado.get_mensaje(),loginEmpleado.get_almacenId(),loginEmpleado.get_vendedorRutaId(),
	    			loginEmpleado.is_modificarClienteApk(), loginEmpleado.get_token());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el loginEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al insertar el loginEmpleado: " + localException.getMessage());
			}
	    }

	    if(l==0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el loginEmpleado.",2);
	    }
	    else
	    {
			AdminSessions adminSessions = new AdminSessions(Login.this);
			adminSessions.SaveSession(loginEmpleado);

	    	VerificarRoles();
	    }
	}

	private void VerificarRoles()
	{
		if(ObtenerRolesEmpleado())
		{
			MostrarMenuPrincipal();
		}
    	else
    	{
    		EjecutarWSGetRolesByEmpleado();
    	}
	}

	private boolean ObtenerRolesEmpleado()
	{
		listadoRol = null;

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

	    if(listadoRol == null)
	    {
	    	return false;
		}
	    else
	    {
	    	return true;
	    }
	}

	private void EjecutarWSGetRolesByEmpleado()
	{
	    pdGetRolesByEmpleado = new ProgressDialog(this);
	    pdGetRolesByEmpleado.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdGetRolesByEmpleado.setMessage("Obteniendo roles ...");
	    pdGetRolesByEmpleado.setCancelable(false);
		pdGetRolesByEmpleado.setCanceledOnTouchOutside(false);

	    WSGetRolesByEmpleado localWSGetRolesByEmpleado = new WSGetRolesByEmpleado();
	    try
	    {
	    	localWSGetRolesByEmpleado.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRolesByEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRolesByEmpleado: " + localException.getMessage());
			}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetRolesByEmpleado.", 1);
	    }
	}

	private class WSGetRolesByEmpleado extends AsyncTask<Void, Integer, Boolean>
	{
		String GETROLESBYEMPLEADO_METHOD_NAME = "GetRolesByEmpleado";
		String GETROLESBYEMPLEADO_SOAP_ACTION = NAMESPACE + GETROLESBYEMPLEADO_METHOD_NAME;

		boolean WSGetRolesByEmpleado;
		SoapObject soapRoles;
		int totalItems;

		protected void onPreExecute()
	    {
			pdGetRolesByEmpleado.show();
	    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSGetRolesByEmpleado = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,GETROLESBYEMPLEADO_METHOD_NAME);
			localSoapObject.addProperty("empleadoId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(GETROLESBYEMPLEADO_SOAP_ACTION,localSoapSerializationEnvelope);

				soapRoles = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetRolesByEmpleadoResult"));

				if(soapRoles!=null)
				{
					totalItems = soapRoles.getPropertyCount();
				}

				if (totalItems > 0)
				{
					WSGetRolesByEmpleado = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSGetRolesByEmpleado = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRolesByEmpleado: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRolesByEmpleado: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdGetRolesByEmpleado.isShowing())
			{
				pdGetRolesByEmpleado.dismiss();
			}

			if (ejecucion)
			{
				if (WSGetRolesByEmpleado)
				{
					if (BorrarRolesEmpleados(loginEmpleado.get_empleadoId()))
					{
						for(int i=0;i<totalItems;i++)
						{
							SoapObject soapObject = (SoapObject)soapRoles.getProperty(i);
							if (soapObject.getPropertyAsString("RoleName").toUpperCase().equals("SUPERVISOR"))
				            {
								InsertarRolEmpleado("Supervisor");
				            }
							else if(soapObject.getPropertyAsString("RoleName").toUpperCase().equals("CENSISTA"))
							{
			                	InsertarRolEmpleado("Censista");
							}
							else if(soapObject.getPropertyAsString("RoleName").toUpperCase().equals("VENDEDOR"))
							{
			                	InsertarRolEmpleado("Vendedor");
							}
							else if (soapObject.getPropertyAsString("RoleName").toUpperCase().equals("DISTRIBUIDOR"))
			              	{
			                	InsertarRolEmpleado("Distribuidor");
			              	}
							else if(soapObject.getPropertyAsString("RoleName").toUpperCase().equals("VENDEDORPROVINCIA"))
							{
								InsertarRolEmpleado("VendedorProvincia");
							}
							else if(soapObject.getPropertyAsString("RoleName").toUpperCase().equals("DISTRIBUIDORPROVINCIA"))
							{
								InsertarRolEmpleado("DistribuidorProvincia");
							}
							else if(soapObject.getPropertyAsString("RoleName").toUpperCase().equals("COBRADOR"))
							{
								InsertarRolEmpleado("Cobrador");
							}
						}

		    			if(BorrarDatosSincronizacion())
		    			{
		    				MostrarMenuPrincipal();
		    			}
		    			else
		    	    	{
		    				theUtilidades.MostrarMensaje(getContexto(), "No se pudo borrar los datos de sincronizacion.", 1);
		    	    	}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo eliminar los roles de los empleados.", 2);
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No existen roles que descaragr.", 1);
				}
			}
			else
			{
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el WSGetRolesByEmpleado.", 1);
			}
		}
	}

	private boolean BorrarRolesEmpleados(int empleadoId)
	{
	    try
	    {
	      return new RolBLL().BorrarRolesPor(empleadoId);
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los rolesEmpleado por empleadoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los rolesEmpleado por empleadoId: " + localException.getMessage());
	    	}
	    	return false;
	    }
	}

	private boolean InsertarRolEmpleado(String rol)
	{
	    long l = 0;

	    try
	    {
	    	l = new RolBLL().InsertarRol(loginEmpleado.get_empleadoId(), rol);
	    	return true;
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el rol del empleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar el rol del empleado: " + localException.getMessage());
	    	}
	    }
	    if (l == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el rol del empleado.", 1);
	    	return false;
	    }
	    return true;
	}

	private boolean BorrarDatosSincronizacion()
	{
		boolean bool = false;
	    try
	    {
	    	bool = new SincronizacionDatosBLL().BorrarSincronizacionesDatos();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datosSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datosSincronizacion: " + localException.getMessage());
	    	}
	    	return false;
	    }

	    if(!bool)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los datosSincronizacion.", 1);
	    	return false;
	    }

	    return true;
	}

	private void VerifyPermission() {
		int permsRequestCode = 100;
		String[] perms = {
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.CAMERA,
				Manifest.permission.READ_PHONE_STATE,
				Manifest.permission.ACCESS_NETWORK_STATE,
				Manifest.permission.BLUETOOTH,
				Manifest.permission.BLUETOOTH_ADMIN,
				Manifest.permission.USE_FINGERPRINT
		};

		int accessReadExternalStorage = checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
		int accessWriteExternalStorage = checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int accessFinePermission = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			accessFinePermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
		}

		int accessCoarsePermission = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			accessCoarsePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
		}

		int cameraPermission = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			cameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
		}

		int readPhoneState = checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE);
		int accessNetworkState = checkCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
		int bluethooth = checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH);
		int bluethoothAdmin = checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH_ADMIN);
		int fingerprint = checkCallingOrSelfPermission(Manifest.permission.USE_FINGERPRINT);

		if (accessReadExternalStorage == PackageManager.PERMISSION_GRANTED
				&& accessWriteExternalStorage == PackageManager.PERMISSION_GRANTED
				&& cameraPermission == PackageManager.PERMISSION_GRANTED
				&& accessFinePermission == PackageManager.PERMISSION_GRANTED
				&& accessCoarsePermission == PackageManager.PERMISSION_GRANTED
				&& readPhoneState == PackageManager.PERMISSION_GRANTED
				&& accessNetworkState == PackageManager.PERMISSION_GRANTED
				&& bluethooth == PackageManager.PERMISSION_GRANTED
				&& bluethoothAdmin == PackageManager.PERMISSION_GRANTED
				&& fingerprint == PackageManager.PERMISSION_GRANTED) {
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(perms, permsRequestCode);
			}
		}
	}

	public void MostrarMenuPrincipal()
	{
	    Intent localIntent = new Intent(this, Menuprincipal.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(localIntent);
	}

	@Override
 	public void onBackPressed()
	{
		theUtilidades.MostrarMensaje(getApplicationContext(),"Ingrese sus credenciales.", 1);
	}
}
