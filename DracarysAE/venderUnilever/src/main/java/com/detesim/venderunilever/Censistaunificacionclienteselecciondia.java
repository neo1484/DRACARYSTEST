package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteCensoBLL;
import BLL.ClientePreventaBLL;
import BLL.DiaSemanaBLL;
import BLL.MotivoEliminacionMatchBLL;
import BLL.MyLogBLL;
import Clases.AES;
import Clases.ApkClienteWS;
import Clases.ClienteCensoWSResult;
import Clases.ClientePreventa;
import Clases.DiaSemana;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.MotivoEliminacionMatchWSResult;
import Clases.SincronizacionDatos;
import Clases.SingleId;
import Clases.VendedorDiaRutaWS;
import Clases.VendedorDiaWS;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Censistaunificacionclienteselecciondia extends Activity implements OnClickListener 
{
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URLUNILEVER = theUtilidades.get_URLUNILEVER();
	String URL = this.theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	SincronizacionDatos sincronizacionDatos;
	int diaId=0;
	
	LinearLayout llSeleccionDia;
	Spinner spnDiaSemana;
	Button btnObtenerClientes;
	RadioButton rbtObtenerClientes;
	RadioButton rbtVerMapa;
	ProgressDialog pdEsperaObtenerClientesPreventasByVendedorYDia;
	ProgressDialog pdEsperaObtenerClientesCenso;
	ProgressDialog pdEsperaObtenerMotivosEliminacion;
	
	ArrayList<DiaSemana> listadoDiaSemana;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistaunificacionclienteselecciondia);
		
		llSeleccionDia = (LinearLayout)findViewById(R.id.llCensistaUnificacionClienteSeleccionDia);
		rbtObtenerClientes = (RadioButton)findViewById(R.id.rbtCensistaUnificacionClienteObtenerClientes);
		rbtObtenerClientes.setOnClickListener(this);
		rbtVerMapa = (RadioButton)findViewById(R.id.rbtCensistaUnificacionClienteVerMapa);
		rbtVerMapa.setOnClickListener(this);
		spnDiaSemana = (Spinner)findViewById(R.id.spnCensistaUnificacionClienteDia);
		btnObtenerClientes = ((Button)findViewById(R.id.btnCencistaUnificacionClienteSeleccionDia));
		btnObtenerClientes.setOnClickListener(this);
		
		llSeleccionDia.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado preventista: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado preventista: " + localException.getMessage());
	    	}	
	    }
	    
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El preventista no se logeo correctamente.", 1);
	    }
	    else
	    {
	    	if(CargarDiasSemana())
	    	{
	    		ArrayAdapter<DiaSemana> localArrayAdapter = new ArrayAdapter<DiaSemana>(this,R.layout.disenio_spinner,listadoDiaSemana);
		        spnDiaSemana.setAdapter(localArrayAdapter);
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los dias de la semana que desplegar.", 1);
			    btnObtenerClientes.setVisibility(View.INVISIBLE);
	    	}
	    }
	}

	@Override
	public void onClick(View arg0) 
	{
		switch(arg0.getId())
		{
		case R.id.rbtCensistaUnificacionClienteVerMapa:
			MostrarCensistaUnificacionClienteMapa();
			break;
		case R.id.btnCencistaUnificacionClienteSeleccionDia:
			diaId = ((DiaSemana)spnDiaSemana.getSelectedItem()).get_diaSemanaId(); 
			if(diaId !=0)
			{
				ObtenerClientes();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar un dia.", 1);
			}
			break;
		}
	}
	
	private boolean CargarDiasSemana()
	{
		listadoDiaSemana = new ArrayList<DiaSemana>();
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

		return listadoDiaSemana != null && listadoDiaSemana.size() > 0;
	}
	
	private void ObtenerClientes()
	{
		if(theUtilidades.VerificarConexionInternet(this))
		{
			ObtenerListadoClientesVentasByVendedorYRuta();
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a intenet, intentelo mas tarde.", 1);
		}
	}
	
	private void ObtenerListadoClientesVentasByVendedorYRuta()
	{
		pdEsperaObtenerClientesPreventasByVendedorYDia = new ProgressDialog(this);
		pdEsperaObtenerClientesPreventasByVendedorYDia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerClientesPreventasByVendedorYDia.setMessage("Obteniendo clientes ...");
		pdEsperaObtenerClientesPreventasByVendedorYDia.setCancelable(false);
		pdEsperaObtenerClientesPreventasByVendedorYDia.setCanceledOnTouchOutside(false);
	    
	    WSObtenerClientesApkByVendedorYDia localWSObtenerClientesVentasByVendedorYDia = new WSObtenerClientesApkByVendedorYDia();
	    
	    try
	    {
	    	localWSObtenerClientesVentasByVendedorYDia.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerClientesVentasByVendedorYDia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerClientesVentasByVendedorYDia: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerClientesVentasByVendedorYDia.", 1);
	    }    
	}
	
	private class WSObtenerClientesApkByVendedorYDia  extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTEAPK_METHOD_NAME = "UNI_GetClientesApkByVendedorYDia";
		String CLIENTEAPK_SOAP_ACTION = NAMESPACE + CLIENTEAPK_METHOD_NAME;

		boolean WSObtenerClientesVentasByVendedorYDia;
		ArrayList<ApkClienteWS> apkClientes;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerClientesPreventasByVendedorYDia.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClientesVentasByVendedorYDia = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEAPK_METHOD_NAME);
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
				localHttpTransportSE.call(CLIENTEAPK_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ApkClienteWS>>(){ }.getType();
					apkClientes = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClientesVentasByVendedorYDia = (apkClientes.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClientesVentasByVendedorYDia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice ObtenerClientesVentasByVendedorYDia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice ObtenerClientesVentasByVendedorYDia: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerClientesPreventasByVendedorYDia.isShowing())
			{
				pdEsperaObtenerClientesPreventasByVendedorYDia.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService ObtenerClientesApkByVendedorYDia no se ejecuto correctamente. ", 1);
				return;
			}
			if(!WSObtenerClientesVentasByVendedorYDia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes que descargar.", 1);
				return;
			}

			if(!BorrarClientesSinPreventa())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los datos de los clientesSinPreventas.", 1);
				return;
			}

			if(!VerificarExistenciaClientePreventa(apkClientes.get(0).getClienteId()))
			{
				long l = 0;
				try
				{
					l = new ClientePreventaBLL().InsertarClientePreventa( apkClientes );
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes peventa: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al insertar los clientes preventa: " + localException.getMessage());
					}
				}

				if(l<=0)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los clientes de la preventa.", 1);
					return;
				}
			}

			ObtenerClientesCenso();

		}
	}
	
	private boolean VerificarExistenciaClientePreventa(int clienteId)
	{
		boolean verificado = false;
		ClientePreventa localClientePreventa = null;
		try
		{
			localClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientePreventa por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientePreventa por ClienteId: " + localException.getMessage());
			}
		}
		
		if(localClientePreventa != null)
		{
			verificado = true;
		}
		
		return verificado;
	}
    
	private boolean BorrarClientesSinPreventa()
	{	        
		try
		{
        	return new ClientePreventaBLL().BorrarClientesSinPreventa();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesSinPreventa: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesSinPreventa: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerClientesCenso()
	{
		pdEsperaObtenerClientesCenso = new ProgressDialog(this);
		pdEsperaObtenerClientesCenso.setMessage("Obteniendo clientes censo ...");
		pdEsperaObtenerClientesCenso.setCancelable(false);
		pdEsperaObtenerClientesCenso.setCanceledOnTouchOutside(false);

		WSGetClientesCenso localWSGetClientesCenso = new WSGetClientesCenso();
	    
	    try
	    {
	    	localWSGetClientesCenso.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesCenso: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesCenso: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesCenso.", 1);
	    }    
	}
	
	private class WSGetClientesCenso extends AsyncTask<Void, Integer, Boolean>
	{
		String CLIENTECENSO_METHOD_NAME = "GetClientesUnileverByRutaYDia";
		String CLIENTECENSO_SOAP_ACTION = NAMESPACE + CLIENTECENSO_METHOD_NAME;

		boolean WSObtenerClientesCenso;
		ArrayList<ClienteCensoWSResult> clienteCensoWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerClientesCenso.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerClientesCenso = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTECENSO_METHOD_NAME);
			VendedorDiaRutaWS vendedorDiaRutaWs = new VendedorDiaRutaWS(0, theUtilidades.ObtenerDiaId(), loginEmpleado.get_vendedorRutaId(),
					loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String vendedorDiaRutaWSJson = new JSonParser().GenerarVendedorDiaRutaJson(vendedorDiaRutaWs);
			String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaRutaWSJson, theUtilidades.get_KEY());
			localSoapObject.addProperty("givendata", encriptedObtenerClientes);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			
			try
			{				
				localHttpTransportSE.call(CLIENTECENSO_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<ClienteCensoWSResult>>(){ }.getType();
					clienteCensoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerClientesCenso = (clienteCensoWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerClientesCenso = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetClientesUnileverByRutaYDIa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetClientesUnileverByRutaYDIa: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerClientesCenso.isShowing())
			{
				pdEsperaObtenerClientesCenso.dismiss();
			}
			
			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetClientesUnileverByRutaYDia no se ejecuto correctamente. ", 1);
				return;
			}

			if(!WSObtenerClientesCenso)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron los clientes censo que descargar.", 1);
				return;
			}

			if(!BorrarClientesCenso()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes del censo.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new ClienteCensoBLL().InsertarClienteCenso( clienteCensoWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteCenso: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el clienteCenso: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el clinete censo.", 1);
				return;
			}

			ObtenerMotivosEliminacion();

		}
	}
    
	private boolean BorrarClientesCenso()
	{	        
		try
		{
        	return new ClienteCensoBLL().BorrarClientesCenso();
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesCenso: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesCenso: " + localException.getMessage());
        	}
			return false;
        }
	}

	private void ObtenerMotivosEliminacion()
	{
		pdEsperaObtenerMotivosEliminacion = new ProgressDialog(this);
		pdEsperaObtenerMotivosEliminacion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerMotivosEliminacion.setMessage("Descargando motivos eliminacion ...");
		pdEsperaObtenerMotivosEliminacion.setCancelable(false);
		pdEsperaObtenerMotivosEliminacion.setCanceledOnTouchOutside(false);
	    
	    WSObtenerMotivosEliminacion wsObtenerMotivosEliminacion = new WSObtenerMotivosEliminacion();
	    
	    try
	    {
	    	wsObtenerMotivosEliminacion.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosEliminacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosEliminacion: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMotivosEliminacion.", 1);
	    }
	}
	
	private class WSObtenerMotivosEliminacion extends AsyncTask<Void, Integer, Boolean>
	{
		String MOTIVOSELIMINACION_METHOD_NAME = "GetMotivoseliminacionMatcheo";
		String MOTIVOSELIMINACION_SOAP_ACTION = NAMESPACE + MOTIVOSELIMINACION_METHOD_NAME;
		
		boolean WSObtenerMotivosEliminacion = false;
		ArrayList<MotivoEliminacionMatchWSResult> motivoEliminacionMatchWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerMotivosEliminacion.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerMotivosEliminacion = false;
      
			SoapObject localSoapObject1 = new SoapObject(NAMESPACE,MOTIVOSELIMINACION_METHOD_NAME);
			SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
			String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
			String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
			localSoapObject1.addProperty("givendata", encriptedObtenerInfo);

			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
			try
			{
				localHttpTransportSE.call(this.MOTIVOSELIMINACION_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<MotivoEliminacionMatchWSResult>>(){ }.getType();
					motivoEliminacionMatchWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerMotivosEliminacion = (motivoEliminacionMatchWSResults != null);
				}
				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivoseliminacionMatcheo: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivoseliminacionMatcheo: " + localException.getMessage());
				}
				return true;
			} 
		}
    
		protected void onPostExecute(Boolean ejecucion)
		{
			if(pdEsperaObtenerMotivosEliminacion.isShowing())
			{
				pdEsperaObtenerMotivosEliminacion.dismiss();
			}
			
			if(!ejecucion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el WSGetMotivoseliminacionMatcheo.",2);
				return;
			}
			if(!WSObtenerMotivosEliminacion)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No existen motivos eliminacion match que descargar.",2);
				return;
			}

			if(!BorrarMotivosEliminacion())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los motivos eliminacion match.",2);
				return;
			}

			long l = 0;
			try
			{
				l = new MotivoEliminacionMatchBLL().InsertarMotivoEliminacionMatch( motivoEliminacionMatchWSResults );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el motivo de eliminacion match: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar el motivo de eliminacion match: " + localException.getMessage());
				}
			}

			if(l <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar el motivo eliminacion match.",2);
				return;
			}

			MostrarCensistaUnificacionClienteMapa();

		}
    }
	
	private boolean BorrarMotivosEliminacion()
	{
		try
		{
			return new MotivoEliminacionMatchBLL().BorrarMotivosEliminacionMatch();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos eliminacion match: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las motivos de eliminacion match: " + localException.getMessage());
			}
			return false;
		}
	}
	
	private void MostrarCensistaUnificacionClienteMapa()
	{
		startActivity(new Intent(this, Censistaunificacionclientesmapa.class));
	}
}
