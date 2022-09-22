package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.EncuestaListaBLL;
import BLL.EncuestaRespuestaBLL;
import BLL.MyLogBLL;
import Clases.EncuestaLista;
import Clases.EncuestaRespuesta;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Vendedorencuestapdv extends Activity implements OnClickListener
{
	LinearLayout llVendedorEncuestaPdv;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	String especificacion;
	String respuesta;
	int clienteId;
	Fecha theFecha;
	String codigoBarra;
	ArrayList<EncuestaLista> listadoProductos;
	ArrayList<EncuestaLista> listadoDistribuidores;
	ArrayList<EncuestaRespuesta> listadoEncuestaRespuestaNoSincro;
	ArrayList<EncuestaRespuesta> listadoEncuestaRespuesta;
	boolean sincronizar;
	
	Spinner spnProducto;
	EditText etCantidad;
	EditText etPrecioCompra;
	EditText etPrecioVenta;
	Spinner spnDistribuidor;
	EditText etDistribuidor;
	Button btnRegistrarProducto;
	ListView lvProductos;
	Button btnEnviarProductos;
	ProgressDialog pdInsertarEncuesta;
	ProgressDialog pdGrabarRespuesta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorencuestapdv);
		
		llVendedorEncuestaPdv = (LinearLayout)findViewById(R.id.llVendedorEncuestaPdv);
		spnProducto = (Spinner)findViewById(R.id.spnVenEncPdvProducto);
		etCantidad = (EditText)findViewById(R.id.etVenEncPdvCantidad);
		etPrecioCompra = (EditText)findViewById(R.id.etVenEncPdvPrecioCompra);
		etPrecioVenta = (EditText)findViewById(R.id.etVenEncPdvPrecioVenta);
		spnDistribuidor = (Spinner)findViewById(R.id.spnVenEncPdvDistribuidor);
		etDistribuidor = (EditText)findViewById(R.id.etVenEncPdvNombreDistribuidor);
		btnRegistrarProducto = (Button)findViewById(R.id.btnVenEncPdvRegistrarProducto);
		btnRegistrarProducto.setOnClickListener(this);
		lvProductos = (ListView)findViewById(R.id.lvVenEncPdvProductos);
		btnEnviarProductos = (Button)findViewById(R.id.btnVenEncPdvEnviarProductos);
		btnEnviarProductos.setOnClickListener(this);
		
		llVendedorEncuestaPdv.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    
	    MostrarControles(false);
	    lvProductos.setVisibility(View.INVISIBLE);
		btnEnviarProductos.setVisibility(View.INVISIBLE);
	    
	    if(loginEmpleado == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	        return;
	    }
	    else
	    {
	    	ObtenerPreguntas();
	    	ObtenerDetallesNoSincro();
	    }
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.btnVenEncPdvRegistrarProducto:
				InsertarProducto();
				break;
			case R.id.btnVenEncPdvEnviarProductos:
				if(theUtilidades.VerificarConexionInternet(this))
				{
					sincronizar = true;
					ObtenerDetallesNoSincro();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Debe estar conectado a internet para realizar la encuesta. Intentelo mas tarde.", 1);
					MostrarPantallaPreventaNits();
				}
				break;
		}
	}
	
	private void MostrarControles(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		
		spnProducto.setVisibility(visibility);
		etCantidad.setVisibility(visibility);
		etPrecioCompra.setVisibility(visibility);
		etPrecioVenta.setVisibility(visibility);
		spnDistribuidor.setVisibility(visibility);
		etDistribuidor.setVisibility(visibility);
		btnRegistrarProducto.setVisibility(visibility);
	}
	
	private void ObtenerPreguntas()
	{
		listadoProductos = null; 
		try
		{
			listadoProductos = new EncuestaListaBLL().ObtenerEncuestaListaPor(2);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la lista de productos de la encuesta PDV: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la lista de productos de la encuesta PDV: " + localException.getMessage());
	    	} 
		}
		
		if(listadoProductos == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los productos de la encuesta PDV.", 1);
		}
		else
		{
			listadoDistribuidores = null; 
			try
			{
				listadoDistribuidores = new EncuestaListaBLL().ObtenerEncuestaListaPor(6);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la lista de distribuidores de la encuesta PDV: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la lista de distribuidores de la encuesta PDV: " + localException.getMessage());
		    	} 
			}
			
			if(listadoDistribuidores == null)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los distribuidores de la encuesta PDV.", 1);
			}
			else
			{
				ArrayAdapter<EncuestaLista> localArrayAdapterProductos = new ArrayAdapter<EncuestaLista>(getApplicationContext(), R.layout.disenio_spinner, listadoProductos);
			    spnProducto.setAdapter(localArrayAdapterProductos);
			    
			    ArrayAdapter<EncuestaLista> localArrayAdapterDistribuidores = new ArrayAdapter<EncuestaLista>(getApplicationContext(), R.layout.disenio_spinner, listadoDistribuidores);
			    spnDistribuidor.setAdapter(localArrayAdapterDistribuidores);
				
			    MostrarControles(true);
			}
		}
	}

	private void InsertarProducto()
	{
		if(etCantidad.getText().length()<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La cantidad es requerida.", 1);
			return;
		}
		else if(etPrecioVenta.getText().length()<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "La precio de venta es requerido.", 1);
			return;
		}
		else if(etDistribuidor.getText().length()<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El distribuidor / mercado es requerido.", 1);
			return;
		}
		else
		{
			theFecha = theUtilidades.ObtenerFecha(); 
			 
			long l1 = 0,l2 = 0,l3 = 0,l4 = 0,l5 = 0,l6 = 0;
			try
			{
				l1 = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, 2, clienteId, ((EncuestaLista)spnProducto.getSelectedItem()).get_valor(), "0", 
						"", loginEmpleado.get_empleadoId(), theFecha.get_dia(),theFecha.get_mes(),theFecha.get_anio());
				
				l2 = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, 3, clienteId, etCantidad.getText().toString(), "0", 
						"", loginEmpleado.get_empleadoId(), theFecha.get_dia(), theFecha.get_mes(),theFecha.get_anio());
				
				l3 = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, 4, clienteId, etPrecioCompra.getText().toString(), "0", 
						"", loginEmpleado.get_empleadoId(), theFecha.get_dia(), theFecha.get_mes(),theFecha.get_anio());
				
				l4 = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, 5, clienteId, etPrecioVenta.getText().toString(), "0", 
						"", loginEmpleado.get_empleadoId(), theFecha.get_dia(),	theFecha.get_mes(),theFecha.get_anio());
				
				l5 = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, 6, clienteId, ((EncuestaLista)spnDistribuidor.getSelectedItem()).get_valor(), "0", 
						"", loginEmpleado.get_empleadoId(), theFecha.get_dia(),	theFecha.get_mes(),theFecha.get_anio());
				
				l6 = new EncuestaRespuestaBLL().InsertarEncuestaRespuesta(0, 7, clienteId, etDistribuidor.getText().toString(), "0", 
						"", loginEmpleado.get_empleadoId(), theFecha.get_dia(), theFecha.get_mes(),theFecha.get_anio());
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
			
			if(l1 == 0 || l2 == 0 || l3==0 || l4==0 || l5==0 || l6==0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la respuesta, intentelo mas tarde.", 1);
			}
			else
			{
				etCantidad.setText("");
				etPrecioCompra.setText("");
				etPrecioVenta.setText("");
				etDistribuidor.setText("");
				ObtenerListadoRespuesta();
			}
		}
	}
	
	private void ObtenerListadoRespuesta()
	{
		listadoEncuestaRespuesta = null;
		try
		{
			listadoEncuestaRespuesta = new EncuestaRespuestaBLL().ObtenerEncuestaRespuestaRangoDetalle(clienteId, 2, 7);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las respuestas de la encuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las respeustas de la encuesta: " + localException.getMessage());
	    	}
		}
		
		if(listadoEncuestaRespuesta != null && listadoEncuestaRespuesta.size() > 0)
		{
			lvProductos.setVisibility(View.VISIBLE);
			btnEnviarProductos.setVisibility(View.VISIBLE);
			
			LlenarRespuestasListView();
		}
	}
	
	private void LlenarRespuestasListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoEncuestaRespuesta == null)
	    {
	    	lvProductos.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvProductos.getLayoutParams();
		    localLayoutParams.height = ((int)(25*getApplicationContext().getResources().getDisplayMetrics().density) * listadoEncuestaRespuesta.size());
		    lvProductos.setLayoutParams(localLayoutParams);
		    lvProductos.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<EncuestaRespuesta>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_vendedorsincronizacionpreventapreventaproducto,listadoEncuestaRespuesta);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_vendedorsincronizacionpreventa, parent, false);
			}
			
			EncuestaRespuesta localEncuestaRespuesta = (EncuestaRespuesta)listadoEncuestaRespuesta.get(position);
			
			TextView producto = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			switch(localEncuestaRespuesta.get_detalleId())
			{
			case 2:
				if(localEncuestaRespuesta.get_especificacion().equals("0"))
				{
					producto.setText("Producto en el PDV : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Contenido);	
				}
				else
				{
					producto.setText("Producto en el PDV : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Etiqueta);
				}
				break;
			case 3:
				if(localEncuestaRespuesta.get_especificacion().equals("0"))
				{
					producto.setText("Cantidad : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Contenido);	
				}
				else
				{
					producto.setText("Cantidad : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Etiqueta);
				}
				break;
			case 4:
				if(localEncuestaRespuesta.get_especificacion().equals("0"))
				{
					producto.setText("Precio compra : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Contenido);	
				}
				else
				{
					producto.setText("Precio compra : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Etiqueta);
				}
				break;
			case 5:
				if(localEncuestaRespuesta.get_especificacion().equals("0"))
				{
					producto.setText("Precio venta : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Contenido);	
				}
				else
				{
					producto.setText("Precio venta : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Etiqueta);
				}
				break;
			case 6:
				if(localEncuestaRespuesta.get_especificacion().equals("0"))
				{
					producto.setText("Tipo Proveedor : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Contenido);	
				}
				else
				{
					producto.setText("Tipo Proveedor : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Etiqueta);
				}
				break;
			case 7:
				if(localEncuestaRespuesta.get_especificacion().equals("0"))
				{
					producto.setText("Nombre Proveedor : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Contenido);	
				}
				else
				{
					producto.setText("Nombre Proveedor : " + localEncuestaRespuesta.get_respuesta());
					producto.setTextAppearance(_context, R.style.Etiqueta);
				}
				break;
			}
						
			return localView;
		}
	}
	
	private void ObtenerDetallesNoSincro()
	{
		listadoEncuestaRespuestaNoSincro = null;
		try
		{
			listadoEncuestaRespuestaNoSincro = new EncuestaRespuestaBLL().ObtenerEncuestaRespuestaRangoDetalleNoSincro(clienteId, 2, 7);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las respuestas no sincro de la encuesta: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las respuestas no sincro de la encuesta: " + localException.getMessage());
	    	}
		}
		
		if(listadoEncuestaRespuestaNoSincro != null && listadoEncuestaRespuestaNoSincro.size() > 0)
		{
			EnviarRespuesta(listadoEncuestaRespuestaNoSincro.get(0));
		}
		else
		{
			if(sincronizar)
			{
				MostrarPantallaPreventaNits();
			}
			else
			{
				ObtenerListadoRespuesta();
			}
		}
	}
	
 	private void EnviarRespuesta(EncuestaRespuesta encuestaRespuesta)
	{
		pdGrabarRespuesta = new ProgressDialog(this);
		pdGrabarRespuesta.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdGrabarRespuesta.setMessage("Grabando respuesta ...");
		pdGrabarRespuesta.setCancelable(false);
		pdGrabarRespuesta.setCanceledOnTouchOutside(false);
		 	 
		WSGrabarRespuesta localWSGrabarRespuesta = new WSGrabarRespuesta(encuestaRespuesta);
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
		EncuestaRespuesta _encuestaRespuesta;
		
		public WSGrabarRespuesta(EncuestaRespuesta encuestaRespuesta)
		{
			this._encuestaRespuesta = encuestaRespuesta;
		}
		 
		protected void onPreExecute()
		 {
			 pdGrabarRespuesta.show();
		 }
	    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSGrabarRespuesta = false;
			 
			SoapObject localSoapObject = new SoapObject(NAMESPACE,GRABARRESPUESTA_METHOD_NAME);
			localSoapObject.addProperty("detalleId", Integer.valueOf(_encuestaRespuesta.get_detalleId()));
			localSoapObject.addProperty("clienteId", Integer.valueOf(_encuestaRespuesta.get_clienteId()));
			localSoapObject.addProperty("respuesta", String.valueOf(_encuestaRespuesta.get_respuesta()));
			localSoapObject.addProperty("especificacion", String.valueOf("0"));
			localSoapObject.addProperty("observacion", String.valueOf(""));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(_encuestaRespuesta.get_empleadoId()));
			localSoapObject.addProperty("dia", Integer.valueOf(_encuestaRespuesta.get_dia()));
			localSoapObject.addProperty("mes", Integer.valueOf(_encuestaRespuesta.get_mes()));
			localSoapObject.addProperty("anio", Integer.valueOf(_encuestaRespuesta.get_anio()));
			
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
					 if(ModificarProductoSincro(this._encuestaRespuesta.get_id(),intResultado))
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Respuesta almacenada.", 1);
						 ObtenerDetallesNoSincro();
					 }
					 else
					 {
						 theUtilidades.MostrarMensaje(getApplicationContext(), "Error al modificar la sincro de la respuesta de la encuesta", 1);
						 return;
					 }
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
	
	private boolean ModificarProductoSincro(int encuestaRespuestaId,int intResultado)
	{
		long l = 0;
		try
		{
			l = new EncuestaRespuestaBLL().ModificarEncuestaRespuestaSincro(encuestaRespuestaId, intResultado);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro de la encuesta respuesta: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro de la encuesta respuesta: " + localException.getMessage());
			}
		}
		
		if(l<=0)
		{
			return false;
		}
		else
		{
			return true;
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
