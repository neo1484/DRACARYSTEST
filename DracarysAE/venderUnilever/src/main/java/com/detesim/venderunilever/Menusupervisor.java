package com.detesim.venderunilever;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.AvanceDistribucionBLL;
import BLL.AvanceVentaBLL;
import BLL.MyLogBLL;
import BLL.SincronizacionDatosBLL;
import Clases.AES;
import Clases.AvanceVendedorDiaWSResult;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.VendedorFechaWS;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Menusupervisor extends Activity implements OnClickListener 
{
	LinearLayout llMenuSupervisor;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	TextView tvUsuario;
	ImageView ivSincronizarDaots;
	ImageView ivVentasDia;
	ImageView ivVentasMes;
	ImageView ivDistribucionDia;
	ImageView ivDistribucionMes;
	
	ProgressDialog pdEsperaObtenerInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menusupervisor);
		
		llMenuSupervisor = (LinearLayout)findViewById(R.id.MenuSupervisorLinearLayout);
		tvUsuario = (TextView)findViewById(R.id.tvMenuSupervisorUsuario);
		ivSincronizarDaots = (ImageView)findViewById(R.id.imgbtnMenuSupervisorSincronizarDatoss);
		ivSincronizarDaots.setOnClickListener(this);
		ivVentasDia = (ImageView)findViewById(R.id.imgbtnMenuSupervisorVentasDia);
		ivVentasDia.setOnClickListener(this);
		ivVentasMes = (ImageView)findViewById(R.id.imgbtnMenuSupervisorVentasMes);
		ivVentasMes.setOnClickListener(this);
		ivDistribucionDia = (ImageView)findViewById(R.id.imgbtnMenuSupervisorDistribucionDia);
		ivDistribucionDia.setOnClickListener(this);
		ivDistribucionMes = (ImageView)findViewById(R.id.imgbtnMenuSupervisorDistribucionMes);
		ivDistribucionMes.setOnClickListener(this);
		
		llMenuSupervisor.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado supervisor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado supervisor: " + localException.getMessage());
	    	}	
	    }
	    
	    if(loginEmpleado == null)
	    {
	    	MostrarControles(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El supervisor no se logeo correctamente.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarControles(true);
	    	tvUsuario.setText(loginEmpleado.get_empleadoNombre());
	    }
	    
	    if(!theUtilidades.ValidarFecha(loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio()))
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "La fecha del celular, no concuerda con la del servidor.", 1);
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.imgbtnMenuSupervisorSincronizarDatoss:
			SincronizarDatos();
			break;
		case R.id.imgbtnMenuSupervisorVentasDia:
			MostrarPantallaSupervisorVentasDia();
			break;
		case R.id.imgbtnMenuSupervisorVentasMes:
			MostrarPantallaSupervisorVentasMes();
			break;
		case R.id.imgbtnMenuSupervisorDistribucionDia:
			MostrarPantallaSupervisorDistribucionDia();
			break;
		case R.id.imgbtnMenuSupervisorDistribucionMes:
			MostrarPantallaSupervisorDistribucionMes();
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
		
		ivSincronizarDaots.setVisibility(visibility);
		ivVentasDia.setVisibility(visibility);
		ivVentasMes.setVisibility(visibility);
		ivDistribucionDia.setVisibility(visibility);
		ivDistribucionMes.setVisibility(visibility);
	}
	
	public void SincronizarDatos()
	{
		if(theUtilidades.VerificarConexionInternet(this))
		{
			MostrarControles(false);
			ObtenerAvancesDiaByDistribuidor();
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existe conexion a internet, intentelo mas tarde.", 1);
		}
	}
	
	private void ObtenerAvancesDiaByDistribuidor()
	{
		pdEsperaObtenerInfo = new ProgressDialog(this);
		pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerInfo.setMessage("Obteniendo ventas por dia ...");
		pdEsperaObtenerInfo.setCancelable(false);
		pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);
	    
	    WSGetAvancesBySupervisorDia localWSGetAvancesBySupervisorDia = new WSGetAvancesBySupervisorDia();
	    
	    try
	    {
	    	localWSGetAvancesBySupervisorDia.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesBySupervisorDia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesBySupervisorDia: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesBySupervisorDia.", 1);
	    }    
	}
	
	private class WSGetAvancesBySupervisorDia  extends AsyncTask<Void, Integer, Boolean>
	{
		String AVANCESDIA_METHOD_NAME = "GetAvancesBySupervisorDia";
		String AVANCESDIA_SOAP_ACTION = NAMESPACE + AVANCESDIA_METHOD_NAME;

		boolean WSObtenerAvancesDia;
		ArrayList<AvanceVendedorDiaWSResult> avanceVendedorDiaWSResults;
		String error;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAvancesDia = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESDIA_METHOD_NAME);
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
				localHttpTransportSE.call(AVANCESDIA_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<AvanceVendedorDiaWSResult>>(){ }.getType();
					avanceVendedorDiaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerAvancesDia = (avanceVendedorDiaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAvancesDia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesBySupervisorDia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesBySupervisorDia: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerInfo.isShowing())
			{
				pdEsperaObtenerInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesBySupervisorDia no se ejecutó correctamente. ", 1);
				return;
			}

			if(!WSObtenerAvancesDia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de las ventas del dia que descargar. " + error, 1);
				ObtenerAvancesMesByDistribuidor();
				return;
			}

			if(!BorrarAvancesVentaDia())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los avances de las ventas del día.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new AvanceVentaBLL().InsertarAvanceVenta( avanceVendedorDiaWSResults, loginEmpleado.get_dia() );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesVenta por dia: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesVenta por dia: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los avances de las ventas del día.", 1);
				return;
			}

			ObtenerAvancesMesByDistribuidor();
		}
	}
    
	private boolean BorrarAvancesVentaDia()
	{	        
		try
		{
        	return new AvanceVentaBLL().BorrarAvancesVentaPorTipoAvanceVentaYRol("DIA","Supervisor");
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por dia: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por dia: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerAvancesMesByDistribuidor()
	{
		pdEsperaObtenerInfo = new ProgressDialog(this);
		pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerInfo.setMessage("Obteniendo ventas por mes ...");
		pdEsperaObtenerInfo.setCancelable(false);
		pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);
	    
	    WSGetAvancesBySupervisorMes localWSGetAvancesBySupervisorMes = new WSGetAvancesBySupervisorMes();
	    
	    try
	    {
	    	localWSGetAvancesBySupervisorMes.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesBySupervisorMes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesBySupervisorMes: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesBySupervisorMes.", 1);
	    }    
	}
	
	private class WSGetAvancesBySupervisorMes  extends AsyncTask<Void, Integer, Boolean>
	{
		String AVANCESMES_METHOD_NAME = "GetAvancesBySupervisorMes";
		String AVANCESMES_SOAP_ACTION = NAMESPACE + AVANCESMES_METHOD_NAME;

		boolean WSObtenerAvancesMes;
		ArrayList<AvanceVendedorDiaWSResult> avanceVendedorDiaWSResults;
		String error;

		protected void onPreExecute()
	    {
			pdEsperaObtenerInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAvancesMes = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESMES_METHOD_NAME);
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
				localHttpTransportSE.call(AVANCESMES_SOAP_ACTION, localSoapSerializationEnvelope);
				String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
				String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
				if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
				{
					error = cadenaDesencriptada;
				}
				else
				{
					Type objectType = new TypeToken<List<AvanceVendedorDiaWSResult>>(){ }.getType();
					avanceVendedorDiaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
					WSObtenerAvancesMes = (avanceVendedorDiaWSResults.size() > 0);
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAvancesMes = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesBySupervisorMes: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesBySupervisorMes: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerInfo.isShowing())
			{
				pdEsperaObtenerInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesBySupervisorMes no se ejecutó correctamente. ", 1);
				return;
			}

			if(!WSObtenerAvancesMes)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de las ventas del mes que descargar. " + error, 1);
				ObtenerAvanceDistribucionDia();
				return;
			}

			if(!BorrarAvancesVentaMes()) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los avances de las ventas del mes.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new AvanceVentaBLL().InsertarAvanceVenta( avanceVendedorDiaWSResults , loginEmpleado.get_dia());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesVenta por mes: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesVenta por mes: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los avances de las ventas del mes.", 1);
				return;
			}

			ObtenerAvanceDistribucionDia();

		}
	}
    
	private boolean BorrarAvancesVentaMes()
	{	        
		try
		{
			return new AvanceVentaBLL().BorrarAvancesVentaPorTipoAvanceVentaYRol("MES","Supervisor");
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por mes: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por mes: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerAvanceDistribucionDia()
	{
		pdEsperaObtenerInfo = new ProgressDialog(this);
		pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerInfo.setMessage("Obteniendo distribucion por dia ...");
		pdEsperaObtenerInfo.setCancelable(false);
		pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);
	    
	    WSGetAvancesDistribuidorBySupervisorDia localWSGetAvancesDistribuidorBySupervisorDia = new WSGetAvancesDistribuidorBySupervisorDia();
	    
	    try
	    {
	    	localWSGetAvancesDistribuidorBySupervisorDia.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesDistribuidorBySupervisorDia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesDistribuidorBySupervisorDia: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesDistribuidorBySupervisorDia.", 1);
	    }    
	}
	
	private class WSGetAvancesDistribuidorBySupervisorDia  extends AsyncTask<Void, Integer, Boolean>
	{
		String AVANCESDIA_METHOD_NAME = "GetAvancesDistribuidorBySupervisorDia";
		String AVANCESDIA_SOAP_ACTION = NAMESPACE + AVANCESDIA_METHOD_NAME;
		boolean WSObtenerAvancesDia;
		SoapObject soapAvancesDia;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAvancesDia = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESDIA_METHOD_NAME);
			localSoapObject.addProperty("supervisorId", loginEmpleado.get_empleadoId());
			localSoapObject.addProperty("dia", loginEmpleado.get_dia());
			localSoapObject.addProperty("mes", loginEmpleado.get_mes());
			localSoapObject.addProperty("anio", loginEmpleado.get_anio());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(AVANCESDIA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapAvancesDia = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetAvancesDistribuidorBySupervisorDiaResult"));
				if(soapAvancesDia != null && soapAvancesDia.getPropertyCount() >0)
				{
					WSObtenerAvancesDia = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAvancesDia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesDistribuidorBySupervisor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesDistribuidorBySupervisor: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerInfo.isShowing())
			{
				pdEsperaObtenerInfo.dismiss();
			}
			
			if(!ejecutado) {
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesDistribuidorBySupervisor no se ejecutó correctamente. ", 1);
				return;
			}

			if(!WSObtenerAvancesDia)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de distribución del dia que descargar. ", 1);
				ObtenerAvanceDistribucionMes();
				return;
			}

			if(!BorrarAvancesDistribucionDia())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los avances de la distribución por día.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new AvanceDistribucionBLL().InsertarAvanceDistribucion( soapAvancesDia );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances de la distribucion por día: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avances de la distribucion por día: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los avances de la distribución por día.", 1);
				return;
			}

			ObtenerAvanceDistribucionMes();

		}
	}
    
	private boolean BorrarAvancesDistribucionDia()
	{	        
		try
		{
        	return new AvanceDistribucionBLL().BorrarAvancesDistribucionPorTipoAvanceDistribucionYRol("DIA","Supervisor");
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion por dia: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDiatribucion por dia: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerAvanceDistribucionMes()
	{
		pdEsperaObtenerInfo = new ProgressDialog(this);
		pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerInfo.setMessage("Obteniendo distribucion por mes ...");
		pdEsperaObtenerInfo.setCancelable(false);
		pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);
	    
	    WSGetAvancesDistribuidorBySupervisorMes localWSGetAvancesDistribuidorBySupervisorMes = new WSGetAvancesDistribuidorBySupervisorMes();
	    
	    try
	    {
	    	localWSGetAvancesDistribuidorBySupervisorMes.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesDistribuidorBySupervisorMes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesDistribuidorBySupervisorMes: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesDistribuidorBySupervisorMes.", 1);
	    }    
	}
	
	private class WSGetAvancesDistribuidorBySupervisorMes  extends AsyncTask<Void, Integer, Boolean>
	{
		String AVANCESMES_METHOD_NAME = "GetAvancesDistribuidorBySupervisorMes";
		String AVANCESMES_SOAP_ACTION = NAMESPACE + AVANCESMES_METHOD_NAME;
		boolean WSObtenerAvancesMes;
		SoapObject soapAvancesMes;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerInfo.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAvancesMes = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESMES_METHOD_NAME);
			localSoapObject.addProperty("supervisorId", loginEmpleado.get_empleadoId());
			localSoapObject.addProperty("mes", loginEmpleado.get_mes());
			localSoapObject.addProperty("anio", loginEmpleado.get_anio());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(AVANCESMES_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapAvancesMes = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetAvancesDistribuidorBySupervisorMesResult"));
				if(soapAvancesMes != null && soapAvancesMes.getPropertyCount() > 0)
				{
					WSObtenerAvancesMes = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAvancesMes = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesDistribuidorBySupervisorMes: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesDistribuidorBySupervisorMes: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerInfo.isShowing())
			{
				pdEsperaObtenerInfo.dismiss();
			}
			
			if(!ejecutado)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesDistribuidorBySupervisor no se ejecutó correctamente. ", 1);
				return;
			}

			if(!WSObtenerAvancesMes)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de distribución del dia que descargar. ", 1);
				RegistrarSincronizacionSupervisor();
				return;
			}
			if(!BorrarAvancesDistribucionMes())
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los avances de la distribución por día.", 1);
				return;
			}

			long l = 0;
			try
			{
				l = new AvanceDistribucionBLL().InsertarAvanceDistribucion( soapAvancesMes );
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesDistribucion por mes: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesDistribucion por mes: " + localException.getMessage());
				}
			}

			if(l<=0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los avances de la distribución por mes.", 1);
				return;
			}

			RegistrarSincronizacionSupervisor();

		}
	}
    
	private boolean BorrarAvancesDistribucionMes()
	{	        
		try
		{
        	return new AvanceDistribucionBLL().BorrarAvancesDistribucionPorTipoAvanceDistribucionYRol("MES","Supervisor");
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDistribucion por mes: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesDiatribucion por mes: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	public void RegistrarSincronizacionSupervisor()
	{
    	long l = 0;
	    try
	    {
	    	l = new SincronizacionDatosBLL().InsertarSincronizacionDatos(
	    			loginEmpleado.get_empleadoId(),
	    			loginEmpleado.get_dia(),
	    			loginEmpleado.get_mes(),
	    			loginEmpleado.get_anio(),
	    			1);//Rol 1 = Supervisor
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionDatos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionDatos: " + localException.getMessage());
	    	} 
	    }
	    
	    if(l<=0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los datosSincronizacion.", 1);
	    }
	    else
	    {
	    	MostrarControles(true);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Sincronizacion de datos exitosa.", 1);
	    }
    }

	public void MostrarPantallaSupervisorVentasDia()
	{
		Intent intent = new Intent(this,Supervisorventaavancedia.class);
		startActivity(intent);
	}
	
	public void MostrarPantallaSupervisorVentasMes()
	{
		Intent intent = new Intent(this,Supervisorventaavancemes.class);
		startActivity(intent);
	}
	
	public void MostrarPantallaSupervisorDistribucionDia()
	{
		Intent intent = new Intent(this,Supervisordistribucionravancedia.class);
		startActivity(intent);
	}
	
	public void MostrarPantallaSupervisorDistribucionMes()
	{
		Intent intent = new Intent(this,Supervisordistribucionavancemes.class);
		startActivity(intent);
	}
}
