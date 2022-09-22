package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.EncuestaBLL;
import BLL.EncuestaDetalleBLL;
import BLL.EncuestaListaBLL;
import BLL.EncuestaRespuestaBLL;
import BLL.MyLogBLL;
import Clases.Encuesta;
import Clases.EncuestaDetalle;
import Clases.EncuestaLista;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Vendedorencuesta extends Activity implements OnClickListener
{
	LinearLayout llVendedorEncuesta;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	int detalleId=0;
	String especificacion;
	String respuesta;
	int clienteId;
	Fecha theFecha;
	String codigoBarra;
	ArrayList<EncuestaLista> listadoEncuestaLista;
	
	TextView tvPregunta;
	RadioButton rbVerdadero;
	RadioButton rbFalso;
	TextView tvEspecificar;
	TextView tvCodigoBarra;
	EditText etCodigoBarra;
	ListView lvOpciones;
	ProgressDialog pdInsertarEncuesta;
	ProgressDialog pdGrabarRespuesta;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorencuesta);
		
		llVendedorEncuesta = (LinearLayout)findViewById(R.id.llVendedorEncuesta);
		tvPregunta = (TextView)findViewById(R.id.tvEncuestaPregunta);
		rbVerdadero = (RadioButton)findViewById(R.id.rbtEncuestaVerdadero);
		rbVerdadero.setOnClickListener(this);
		rbFalso = (RadioButton)findViewById(R.id.rbtEncuestaFalse);
		rbFalso.setOnClickListener(this);
		tvEspecificar = (TextView)findViewById(R.id.tvEncuestaEspecificar);
		tvEspecificar.setText("");
		tvCodigoBarra = (TextView)findViewById(R.id.tvEncuestaCodigoBarra);
		etCodigoBarra = (EditText)findViewById(R.id.etEncuetaCodigoBarra);
		lvOpciones = (ListView)findViewById(R.id.lvEncuestaOpciones);
		
		llVendedorEncuesta.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
	    clienteId = getIntent().getExtras().getInt("ClienteId");
		
		loginEmpleado = null;	    
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado vendedor: " + localException.getMessage());
	    	} 
	    	return;
	    }
	      
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	        return;
	    }
	    else
	    {
	    	tvCodigoBarra.setVisibility(View.INVISIBLE);
	    	etCodigoBarra.setVisibility(View.INVISIBLE);
	    	DesplegarPreguntas();
	    }
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.rbtEncuestaVerdadero:
			MostrarListado();
			break;
		case R.id.rbtEncuestaFalse:
			GrabarRespuesta(false);
			break;
		}
	}
	
	private void DesplegarPreguntas()
	{
		Encuesta encuesta = null;
		try
		{
			encuesta = new EncuestaBLL().ObtenerEncuesta(1);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la encuesta: " + localException.getMessage());
	    	} 
		}
		
		if(encuesta == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron encuestas activas", 1);
		}
		else
		{	
			EncuestaDetalle encuestaDetalle = null;
			try
			{
				encuestaDetalle = new EncuestaDetalleBLL().ObtenerEncuestaDetalle(encuesta.get_encuestaId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la encuesta exhibidor: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles de la encuesta exhibidor: " + localException.getMessage());
		    	} 
			}
			if(encuestaDetalle == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron preguntas, para la encuesta exhibidor.", 1);
			}
			else
			{
				tvPregunta.setText(encuestaDetalle.get_pregunta());
				detalleId = encuestaDetalle.get_detalleId();
			}
		}
	}

	private void MostrarListado()
	{
		listadoEncuestaLista = null; 
		try
		{
			listadoEncuestaLista = new EncuestaListaBLL().ObtenerEncuestaListaPor(1);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la lista de la encuesta exhibidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la lista de la encuesta exhibidor: " + localException.getMessage());
	    	} 
		}
		
		if(listadoEncuestaLista == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron items del listado de la encuesta exhibidor.", 1);
		}
		else
		{
			tvEspecificar.setText("Ingrese el codigo de barra del exhibidor y seleccione su opcion:");
			tvCodigoBarra.setVisibility(View.VISIBLE);
			etCodigoBarra.setVisibility(View.VISIBLE);
			
			LlenarOpcionestView();
			OpcionesOnClick();
		}
	}
	
	private void LlenarOpcionestView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoEncuestaLista == null)
	    {
	    	lvOpciones.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvOpciones.getLayoutParams();
		    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoEncuestaLista.size());
		    lvOpciones.setLayoutParams(localLayoutParams);
		    lvOpciones.setAdapter(localMiAdaptadorLista);
	    }
	}
	
	class MiAdaptadorLista extends ArrayAdapter<EncuestaLista>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorpreventanits,listadoEncuestaLista);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_vendedorpreventanits, parent, false);
			}
				
			String opcion = listadoEncuestaLista.get(position).get_valor();
			
			TextView nombre = (TextView)localView.findViewById(R.id.tvDisenioNitNombreFactura);
			TextView nombre2 = (TextView)localView.findViewById(R.id.tvDisenioNitNit);
			TextView nombre3 = (TextView)localView.findViewById(R.id.tvDisenioNitTipo);
			
			nombre.setText(opcion);
			nombre2.setText("");
			nombre3.setText("");
  	
			return localView;
		}
	}

	private void OpcionesOnClick()
	{
	    ((ListView)findViewById(R.id.lvEncuestaOpciones)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		if(etCodigoBarra.getText().length()<=0)
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe especificar el codigo de barra del exhibidor", 1);
	    		}
	    		else
	    		{
	    			codigoBarra = etCodigoBarra.getText().toString();
	    			especificacion = listadoEncuestaLista.get(position).get_valor();	 
		    		GrabarRespuesta(true);
	    		}
	    		
	        }
	    });
	}
	
	private void GrabarRespuesta(boolean respuestaTemp)
	{
		 if(respuestaTemp)
			{
				respuesta = "SI";
			}
			else
			{
				respuesta = "NO";
			}
		 
		theFecha = theUtilidades.ObtenerFecha(); 
		 
		long l = 0;
		try
		{
			l = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, detalleId, clienteId, respuesta, especificacion, 
																	codigoBarra, loginEmpleado.get_empleadoId(), theFecha.get_dia(),
																	theFecha.get_mes(),theFecha.get_anio());
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al insertar la respuesta de la encuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al insertar la respuesta de la encuesta: " + localException.getMessage());
	    	}
		}
		
		if(l==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la respuesta, intentelo mas tarde.", 1);
		}
		else
		{
			if(theUtilidades.VerificarConexionInternet(this))
			{
				EnviarRespuesta();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe estar conectado a internet para realizar la encuesta. Intentelo mas tarde.", 1);
				MostrarPantallaPreventaNits();
			}
		}
	}
	
	private void EnviarRespuesta()
	{
		pdGrabarRespuesta = new ProgressDialog(this);
		pdGrabarRespuesta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdGrabarRespuesta.setMessage("Grabando respuesta ...");
		pdGrabarRespuesta.setCancelable(false);
		pdGrabarRespuesta.setCanceledOnTouchOutside(false);
		 	 
		WSGrabarRespuesta localWSGrabarRespuesta = new WSGrabarRespuesta();
		try
		{
			localWSGrabarRespuesta.execute();
		}
		catch (Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGrabarRespuesta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGrabarRespuesta: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGrabarRespuesta.", 1);
		}
	}
	 
	private class WSGrabarRespuesta extends AsyncTask<Void, Integer, Boolean>
	{
		String GRABARRESPUESTA_METHOD_NAME = "InsertEncuestaRespuesta";
		String GRABARRESPUESTA_SOAP_ACTION = NAMESPACE + GRABARRESPUESTA_METHOD_NAME;
		
		boolean WSGrabarRespuesta = false;
		int intResultado;
		SoapObject soapResultado;
		String stringResultado;
		 
		protected void onPreExecute()
		 {
			 pdGrabarRespuesta.show();
		 }
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSGrabarRespuesta = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,GRABARRESPUESTA_METHOD_NAME);
			localSoapObject.addProperty("detalleId", Integer.valueOf(detalleId));
			localSoapObject.addProperty("clienteId", Integer.valueOf(clienteId));
			localSoapObject.addProperty("respuesta", String.valueOf(respuesta));
			localSoapObject.addProperty("especificacion", String.valueOf(especificacion));
			localSoapObject.addProperty("observacion", String.valueOf(codigoBarra));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("dia", Integer.valueOf(theFecha.get_dia()));
			localSoapObject.addProperty("mes", Integer.valueOf(theFecha.get_mes()));
			localSoapObject.addProperty("anio", Integer.valueOf(theFecha.get_anio()));
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(GRABARRESPUESTA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapResultado = ((SoapObject)localSoapSerializationEnvelope.getResponse());
				if(soapResultado!=null)
				{
					intResultado = Integer.parseInt(soapResultado.getPropertyAsString("Id"));
			        stringResultado = soapResultado.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && intResultado > 0)
				{
					WSGrabarRespuesta = true;
				}
	            return true;
			}
			catch (Exception localException)
			{
				WSGrabarRespuesta = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGrabarRespuesta: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGrabarRespuesta: " + localException.getMessage());
				}
				return true;
			}
		 }
		 
		 protected void onPostExecute(Boolean ejecutado)
		 {
			 if(pdGrabarRespuesta.isShowing())
			 {
				 pdGrabarRespuesta.dismiss();
			 }
			 
			 if(ejecutado)
			 {
				 if(WSGrabarRespuesta) 
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(), "Respuesta almacenada.", 1);
					 MostrarPantallaPreventaNits();
				 }
				 else
				 {
					 theUtilidades.MostrarMensaje(getApplicationContext(), stringResultado, 1);
					 MostrarPantallaPreventaNits();
				 }
			 }
			 else
			 {
				 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertPreventa.", 1);
				 return;
			 }
		 }
	 }
	 
	private void MostrarPantallaPreventaNits()
	{
	    Intent localIntent = null;
	    localIntent = new Intent(this, Vendedorpreventanits.class);
	    localIntent.putExtra("clienteId", clienteId);
	    localIntent.putExtra("origenSolicitud", "Preventa");
	    startActivity(localIntent);
	}
	
}
