package com.detesim.venderunilever;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BLL.MyLogBLL;
import BLL.RelevamientoBLL;
import BLL.RelevamientoDetalleBLL;
import Clases.LoginEmpleado;
import Clases.Relevamiento;
import Clases.RelevamientoCategoria;
import Clases.RelevamientoDetalle;
import Clases.RelevamientoDetalleWS;
import Clases.RelevamientoTipoNegocio;
import Clases.ResultadoEnroque;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Censistarelevamiento extends Activity implements OnClickListener
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();	
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URLENROQUE = this.theUtilidades.get_URLENROQUE();
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	LoginEmpleado loginEmpleado;
	Dialog dialog;
	ProgressDialog pdInsertRelevamiento;
	String Origen;
	Double latitud;
	Double longitud;
	int tipoNegocioId;
	int relevamientoId;
	String categoria;
	Relevamiento relevamiento;
	RelevamientoDetalle relevamientoDetalle;
	ArrayList<RelevamientoDetalle> listadoRelevamientoDetalle;
	ArrayList<RelevamientoDetalle> listadoRelevamientoDetalleNoSincro;
	byte[] foto;
	
	LinearLayout llCenRel;
	EditText etNombre;
	Spinner spnTipoNegocio;
	Spinner spnCategoria;
	ImageView ivCamara;
	Button btnRegistrar;
	ListView lvRelevamientos;
	Button btnEnviar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistarelevamiento);
		
		llCenRel = (LinearLayout)findViewById(R.id.llCenRelRelevamiento);
		etNombre = (EditText)findViewById(R.id.etCenRelNombre);
		spnTipoNegocio = (Spinner)findViewById(R.id.spnCenRelTipoNegocio);
		spnCategoria = (Spinner)findViewById(R.id.spnCenRelCategoria);
		ivCamara = (ImageView)findViewById(R.id.ivCenRelTomarFoto);
		ivCamara.setOnClickListener(this);
		btnRegistrar = (Button)findViewById(R.id.btnCenRelRegistrarRelevamiento);
		btnRegistrar.setOnClickListener(this);
		lvRelevamientos = (ListView)findViewById(R.id.lvCenRelRelevamientos);
		btnEnviar = (Button)findViewById(R.id.btnCenRelEnviarDatos);
		btnEnviar.setOnClickListener(this);
		
		llCenRel.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));

		Origen = getIntent().getExtras().getString("Origen");
		
		latitud = 0.0;
		latitud = Double.parseDouble(getIntent().getExtras().getString("Latitud"));
		
		longitud = 0.0;
		longitud = Double.parseDouble(getIntent().getExtras().getString("Longitud"));
		
	    if(latitud == 0 || longitud == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las coordenadas del relevamiento.", 1);
	    	return;
	    }
		
		loginEmpleado = null;
	    
	    try
	    {
	    	loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el LoginEmpleado del cobrador: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el LoginEmpleado del cobrador: " + localException.getMessage());
	    	}	      
	    }
	    
	    if (loginEmpleado == null)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext(), "El vendedor no se logeo correctamente.", 2);
	    }
	    else
	    {
	    	CargarInfo();
	    	ObtenerRelevamientosDetalles();
	    	LlenarListViewFotos();
	    }
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.ivCenRelTomarFoto:
				TomarFoto();
				break;
			case R.id.btnCenRelRegistrarRelevamiento:
				GuardarDatos();
				break;
			case R.id.btnCenRelEnviarDatos:
				ObtenerRelevamientosDetallesNoSincro();
				break;
		}
	}
	
	private void CargarInfo()
	{
		relevamiento = null;
		try
		{
			relevamiento = new RelevamientoBLL().ObtenerRelevamientos().get(0);
		}
		catch(Exception localException)
		{
			if (localException.getMessage().isEmpty())
	        {
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener el relevamiento: vacio");
	        }
	        else
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al obtener el relevamiento: " + localException.getMessage());
	        }
		}
		
		if(relevamiento == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el relevamiento activo.", 1);
		}
		else
		{
			ArrayList<RelevamientoTipoNegocio> listadoRelTipoNegocio = new ArrayList<RelevamientoTipoNegocio>();
			listadoRelTipoNegocio.add(new RelevamientoTipoNegocio(1, "Peluqueria"));
			
			ArrayAdapter<RelevamientoTipoNegocio> localArrayAdapter = new ArrayAdapter<RelevamientoTipoNegocio>(this,R.layout.disenio_spinner,listadoRelTipoNegocio);
	        spnTipoNegocio.setAdapter(localArrayAdapter);
	        	        
	        spnTipoNegocio.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					tipoNegocioId = ((RelevamientoTipoNegocio)spnTipoNegocio.getSelectedItem()).get_tipoNegocioId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
			});
	        
	        ArrayList<RelevamientoCategoria> listadoRelCategoria = new ArrayList<RelevamientoCategoria>();
			listadoRelCategoria.add(new RelevamientoCategoria(1, "A"));
			listadoRelCategoria.add(new RelevamientoCategoria(2, "B"));
			listadoRelCategoria.add(new RelevamientoCategoria(3, "C"));
			
			ArrayAdapter<RelevamientoCategoria> localRelCategoria = new ArrayAdapter<RelevamientoCategoria>(this,R.layout.disenio_spinner,listadoRelCategoria);
	        spnCategoria.setAdapter(localRelCategoria);
	        
	        spnCategoria.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					categoria = ((RelevamientoCategoria)spnCategoria.getSelectedItem()).get_categoria();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
			});
		}
	}
	
	private void ObtenerRelevamientosDetalles()
	{
		listadoRelevamientoDetalle = null;
		try
		{
			listadoRelevamientoDetalle = new RelevamientoDetalleBLL().ObtenerRelevamientosDetalle();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle: " + localException.getMessage());
	    	}	
		}
		
		if(listadoRelevamientoDetalle == null)
		{
			btnEnviar.setVisibility(View.INVISIBLE);
		}
		else
		{
			btnEnviar.setVisibility(View.VISIBLE);
		}
	}
	
	private void TomarFoto()
	{
	    Intent localIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	    if (localIntent.resolveActivity(getPackageManager()) != null) 
	    {
	    	startActivityForResult(localIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    
	    if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
	    {
	    	foto = ConvertirBitmapAVectorDeBytes((Bitmap)data.getExtras().get("data"));
	    }
	    
	    LlenarListViewFotos();
	}
	
	public static byte[] ConvertirBitmapAVectorDeBytes(Bitmap bitmap)
	{
	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
	    return localByteArrayOutputStream.toByteArray();
	}
	
	private void LlenarListViewFotos()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvCenRelRelevamientos);
	    if(listadoRelevamientoDetalle == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
		    localLayoutParams.height = ((int)(110*getApplicationContext().getResources().getDisplayMetrics().density) * listadoRelevamientoDetalle.size());
		    localListView.setLayoutParams(localLayoutParams);
		    localListView.setAdapter(localMiAdaptadorLista);	    	
	    }
	}
	
	class MiAdaptadorLista extends ArrayAdapter<RelevamientoDetalle>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistarelevamiento,listadoRelevamientoDetalle);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_censistarelevamiento,parent,false);
			}
			
			RelevamientoDetalle localRelevamientoDetalle = listadoRelevamientoDetalle.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvDisenioClienteFotoNombre);
			ImageView imageView = (ImageView)localView.findViewById(R.id.ivDisenioClienteFotoFoto);
			
			if(localRelevamientoDetalle.getSincro() == 0)
			{
				textView.setText(localRelevamientoDetalle.getNombre() + " - " + localRelevamientoDetalle.getCategoriaId());
				textView.setTextAppearance(_context, R.style.Contenido);
			}
			else
			{
				textView.setText(localRelevamientoDetalle.getNombre() + " - " + localRelevamientoDetalle.getCategoriaId());
				textView.setTextAppearance(_context, R.style.Etiqueta);
			}
			
			if(localRelevamientoDetalle.getFotoBinary() != null)
			{
				imageView.setImageBitmap(ConvertirVectorDeBytesABitmap(localRelevamientoDetalle.getFotoBinary()));
			}
			
			return localView;
		}
	}
		
	public Bitmap ConvertirVectorDeBytesABitmap(byte[] foto)
	{
		return BitmapFactory.decodeByteArray(foto, 0, foto.length);
	}	
	
	private void GuardarDatos()
	{
		int fotoSize = 0;
		byte[] fotoActual = "".getBytes();
		
		if(foto != null)
		{
			fotoSize = foto.length;
			fotoActual = foto;
		}
			
		relevamientoDetalle = new RelevamientoDetalle(0,relevamiento.getRelevamientoId(), etNombre.getText().toString(), tipoNegocioId, categoria, 
				fotoSize, fotoActual, latitud, longitud, 0);
    
	    long l=0;
	    try
	    {
	       l = new RelevamientoDetalleBLL().InsertarRelevamientoDetalle(relevamientoDetalle.getRelevamientoId(), relevamientoDetalle.getNombre(),
	    		   relevamientoDetalle.getTipoNegocioId(), relevamientoDetalle.getCategoriaId(), relevamientoDetalle.getFotoSize(),
	    		   relevamientoDetalle.getFotoBinary(), relevamientoDetalle.getLatitud(), relevamientoDetalle.getLongitud(),
	    		   relevamientoDetalle.getSincro());
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	        {
	    		myLog.InsertarLog("App", toString(), 1, "Error al insertar el relevamiento: vacio");
	        }
	        else
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al insertar el relevamiento: " + localException.getMessage());
	        }
	    }
	        
	    if (l <= 0)
	    {
	      this.theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el relevamiento.", 1);
	      return;
	    }
	    else
	    {
	    	foto = "".getBytes();
	    	etNombre.setText("");
	    	ObtenerRelevamientosDetalles();
	    	LlenarListViewFotos();
	    }
	}
	
	private void ObtenerRelevamientosDetallesNoSincro()
	{
		listadoRelevamientoDetalleNoSincro = null;
		try
		{
			listadoRelevamientoDetalleNoSincro = new RelevamientoDetalleBLL().ObtenerRelevamientosDetalleNoSincro();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle no sincro: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los relevamientos detalle no sincro: " + localException.getMessage());
	    	}	
		}
		
		if(listadoRelevamientoDetalleNoSincro != null)
		{
			InsertarRelevamiento(listadoRelevamientoDetalleNoSincro.get(0));
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron datos que enviar.", 1);
			MostrarPantallaAltaClienteMapa();
		}
	}
		
	private void InsertarRelevamiento(RelevamientoDetalle theRelDet)
	{
		pdInsertRelevamiento = new ProgressDialog(this);
		pdInsertRelevamiento.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertRelevamiento.setMessage("Registrando relevamiento ...");
		pdInsertRelevamiento.setCancelable(false);
		pdInsertRelevamiento.setCanceledOnTouchOutside(false);
	    
	    WSInsertRelevamientoDet localWSInsertRelevamientoDet = new WSInsertRelevamientoDet(theRelDet);
	    
	    try
	    {
	    	localWSInsertRelevamientoDet.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertRelevamientoDet: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertRelevamientoDet: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertRelevamientoDet.", 1);
		    return;
	    }    
	}
	
	private class WSInsertRelevamientoDet extends AsyncTask<Void, Integer, Boolean>
	{
		String RELDET_METHOD_NAME = "InsertRelevamientoDetalle";
		String RELDET_SOAP_ACTION = NAMESPACE + RELDET_METHOD_NAME;
		
		boolean WSRelDet;
		RelevamientoDetalle _theRelDet;
		RelevamientoDetalleWS _theRelDetWS;
		String gsonValue;
		ResultadoEnroque resultadoDetalle;
		
		public WSInsertRelevamientoDet(RelevamientoDetalle relDet)
		{
			this._theRelDet = relDet;
			_theRelDetWS = new RelevamientoDetalleWS(_theRelDet.getRelevamientoId(), _theRelDet.getNombre(), _theRelDet.getTipoNegocioId(), _theRelDet.getCategoriaId(),
					_theRelDet.getFotoSize(), _theRelDet.getLatitud(), _theRelDet.getLongitud());
		}
		
		protected void onPreExecute()
	    {
			pdInsertRelevamiento.show();
			gsonValue= new Gson().toJson(this._theRelDetWS);
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSRelDet = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,RELDET_METHOD_NAME);
			localSoapObject.addProperty("relevamientoDetalle", gsonValue);
			localSoapObject.addProperty("imagen", _theRelDet.getFotoBinary());
						
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			MarshalBase64 marshalBase64 = new MarshalBase64();
			marshalBase64.register(localSoapSerializationEnvelope);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLENROQUE);
			
			try
			{
				localHttpTransportSE.call(RELDET_SOAP_ACTION, localSoapSerializationEnvelope);
				
				String cadena = ((SoapPrimitive)localSoapSerializationEnvelope.getResponse()).toString();
				Type type = new TypeToken<ResultadoEnroque>(){}.getType();
				resultadoDetalle = new Gson().fromJson(cadena, type);
				if(resultadoDetalle.getId() > 0 && resultadoDetalle.getMensaje().equals("OK"))
				{
					WSRelDet = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSRelDet = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSInsertRelevamientoDetalle: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSInsertRelevamientoDetalle: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertRelevamiento.isShowing())
			{
				pdInsertRelevamiento.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSRelDet)
				{						
					long l = 0;
					try
					{
						l = new RelevamientoDetalleBLL().ModificarRelevamientoDetalleSincro(this._theRelDet.getRelevamientoDetalleId(), 1);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del relevamiento detalle: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del relevamiento detalle: " + localException.getMessage());
						}
					}
					
					if(l <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el relevamiento detalle.", 1);
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Relevamiento registrado.", 1);
					ObtenerRelevamientosDetallesNoSincro();
				}
				else
				{
					if(resultadoDetalle.getMensaje().isEmpty())
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo registrar el relevamiento detalle.", 1);
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), resultadoDetalle.getMensaje(), 1);
					}
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertRelevamientoDetalle no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
	private void MostrarPantallaAltaClienteMapa()
	{
    	Intent intent = new Intent(this, Censistaaltaclientemapa.class);
    	intent.putExtra("Origen", Origen);
    	startActivity(intent);
	}
}
