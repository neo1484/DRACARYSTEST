package com.detesim.venderunilever;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteBLL;
import BLL.ClienteFotoBLL;
import BLL.FotoCategoriaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import Clases.Cliente;
import Clases.ClienteFoto;
import Clases.FotoCategoria;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Censistafotocliente extends Activity implements android.view.View.OnClickListener
{
	static final int REQUEST_IMAGE_CAPTURE = 1;
	LinearLayout llCensistaFotoCliente;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	
	LoginEmpleado loginEmpleado;
	ArrayList<ClienteFoto> listadoFotos;
	Cliente theCliente;
	int androidRowId;
	int categoriaId;
	String Origen;
	ParametroGeneral parametroGeneral;
	ArrayList<FotoCategoria> listadoFotoCategoria;
	
	Button btnSincronizar;
	Spinner spnFotoCategoria;
	Button btnTomarFoto;
	ListView lvFotos;
	ProgressDialog pdInsertarFoto;
	TextView tvClienteFotoNombre;
	TextView tvClienteFotoCi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistafotocliente);
		
		llCensistaFotoCliente = (LinearLayout)findViewById(R.id.CensistaFotoClienteLayout);
		tvClienteFotoNombre = ((TextView)findViewById(R.id.tvClienteFotoNombre));
	    tvClienteFotoCi = ((TextView)findViewById(R.id.tvClienteFotoCi));
	    spnFotoCategoria = (Spinner)findViewById(R.id.spnClienteFotoCategoriaFoto);
	    btnTomarFoto = ((Button)findViewById(R.id.btnClienteFotoTomarFoto));
	    btnTomarFoto.setOnClickListener(this);
	    lvFotos = ((ListView)findViewById(R.id.lvClienteFotoFotos));
	    btnSincronizar = ((Button)findViewById(R.id.btnClienteFotoInsertarFotos));
	    btnSincronizar.setOnClickListener(this);
	    
	    llCensistaFotoCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    androidRowId = 0;
	    Origen ="";
	    
	    Bundle localBundle = getIntent().getExtras();
	    androidRowId = Integer.parseInt(localBundle.getString("RowId"));
	    Origen = localBundle.getString("Origen");
	    
	    if (androidRowId == 0) 
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el id del cliente.", 1);
	    	return;
	    }
	    
	    if (Origen == "") 
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de la peticion.", 1);
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
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener loginEmpleado supervisor: vacio");
	        } 
	    	else 
	    	{
	            myLog.InsertarLog("App", toString(), 1, "Error al obtener loginEmpleado supervisor: " + localException.getMessage());
	        }
	    }
	    
	    if(loginEmpleado != null)
	    {
	    	if(CargarClasificacionesFoto())
	    	{
	    		DesplegarInformacion();
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las clasificaciones de las fotos.", 1);
	    	}
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(this, "El usuario no se logeo correctamente.", 1);
	    }	    
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnClienteFotoTomarFoto:
			if(categoriaId > 0)
			{
				TomarFoto();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una categoria para clasificar la foto.", 1);
			}
			break;
		case R.id.btnClienteFotoInsertarFotos:
			SincronizarFotos();
			break;
		}
	}
	
	private boolean CargarClasificacionesFoto()
	{
		listadoFotoCategoria = null;
		
		try
		{
			listadoFotoCategoria = new FotoCategoriaBLL().ObtenerFotosCategoria();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las cotegorias de las fotos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las categorias de las fotos: " + localException.getMessage());
	    	}  
		}
		
		if(listadoFotoCategoria == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias de las fotos.", 1);
			return false;
		}
		else
		{				
			ArrayAdapter<FotoCategoria> localArrayAdapter = new ArrayAdapter<FotoCategoria>(this,R.layout.disenio_spinner,listadoFotoCategoria);
			spnFotoCategoria.setAdapter(localArrayAdapter);
	        	        
			spnFotoCategoria.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					categoriaId = ((FotoCategoria)spnFotoCategoria.getSelectedItem()).get_categoriaId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});	
	        
	        return true;
		}
	}
	
	private void DesplegarInformacion()
	{
		theCliente = null;
		try
	    {
			theCliente = new ClienteBLL().ObtenerClientePorRowId(androidRowId);
	    }
		catch (Exception localException)
	    {
			if (localException.getMessage().isEmpty()) 
			{
				myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del cliente por rowId: vacio");
	        } 
			else 
			{
				myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del cliente por rowId: " + localException.getMessage());
	        }
	    }
		
		if(theCliente == null)
	    {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
	        return;
	    }
		
		tvClienteFotoNombre.setText(theCliente.get_nombres().toString() + " " + theCliente.get_paterno().toString());
		if(theCliente.get_ci() == null)
		{
			tvClienteFotoCi.setText("-----");
		}
		else
		{
			tvClienteFotoCi.setText(theCliente.get_ci().toString());
		}
	    
	    ObtenerFotosCliente();    
	}

	private void ObtenerFotosCliente()
	{
	    listadoFotos = null;
	    try
	    {
	    	listadoFotos = new ClienteFotoBLL().ObtenerClientesFotoPorClienteIdAndroid(androidRowId);
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty()) 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las fotos de los clientes: vacio");
	        } 
	    	else 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las fotos de los clientes: " + localException.getMessage());
	        }
	    }
	    
	    if (listadoFotos == null)
    	{
    		btnSincronizar.setVisibility(View.INVISIBLE);
    	}
	    else
	    {
	    	btnSincronizar.setVisibility(View.VISIBLE);
	    	if (this.listadoFotos.size() >= 3) 
		    {
		    	btnTomarFoto.setVisibility(View.INVISIBLE);
		    }
		    else
		    {
		    	btnTomarFoto.setVisibility(View.VISIBLE);
		    }
	    }
	    
	    LlenarListViewFotos();
	    EliminarItemListViewFotos();
	}
	
	private void LlenarListViewFotos()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvClienteFotoFotos);
	    if(listadoFotos == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
		    localLayoutParams.height = ((int)(110*getApplicationContext().getResources().getDisplayMetrics().density) * listadoFotos.size());
		    localListView.setLayoutParams(localLayoutParams);
		    localListView.setAdapter(localMiAdaptadorLista);	    	
	    }
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClienteFoto>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistaclientefoto,listadoFotos);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_censistaclientefoto,parent,false);
			}
			
			ClienteFoto localClienteFoto = (ClienteFoto)listadoFotos.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvDisenioClienteFotoNombre);
			ImageView imageView = (ImageView)localView.findViewById(R.id.ivDisenioClienteFotoFoto);
			
			textView.setText(theCliente.get_nombres()+ " " + theCliente.get_paterno() + "_" + String.valueOf(position+1));
			imageView.setImageBitmap(ConvertirVectorDeBytesABitmap(localClienteFoto.get_foto()));
			
			return localView;
		}
	}
		
	public Bitmap ConvertirVectorDeBytesABitmap(byte[] foto)
	{
		return BitmapFactory.decodeByteArray(foto, 0, foto.length);
	}	
	
	private void EliminarItemListViewFotos()
	{
		((ListView)findViewById(R.id.lvClienteFotoFotos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				ClienteFoto localClienteFoto = (ClienteFoto)listadoFotos.get(position);
				boolean bool=false;
				try
				{
					bool = new ClienteFotoBLL().BorrarClientesFotoPor(localClienteFoto.get_id());
				}
				catch (Exception localException)
				{
					if (localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App", toString(), 1, "Error al borrar la foto del cliente por fotoClienteId: vacio");
					}
					else
					{
						myLog.InsertarLog("App", toString(), 1, "Error al borrar la foto del cliente por fotoClienteId: " + localException.getMessage());	
	              }
	            }
				
				if(bool)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Foto eliminada.", 1);
					ObtenerFotosCliente();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar la foto del dispositivo.", 1);				
				}
			}
		});
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
	    byte[] foto=null;
	    
	    if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
	    {
	    	foto = ConvertirBitmapAVectorDeBytes((Bitmap)data.getExtras().get("data"));
	    }
	    
	    long l=0;
	    try
	    {
	       l = new ClienteFotoBLL().InsertarClienteFoto(androidRowId, theCliente.get_clienteId(), foto, false, categoriaId, 0);
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty())
	        {
	    		myLog.InsertarLog("App", toString(), 1, "Error al insertar la foto del cliente: vacio");
	        }
	        else
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al insertar la foto del cliente: " + localException.getMessage());
	        }
	    }
	        
	    if (l <= 0)
	    {
	      this.theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto del cliente.", 1);
	      return;
	    }
	    else
	    {
	      ObtenerFotosCliente();
	    }
	}
	
	public static byte[] ConvertirBitmapAVectorDeBytes(Bitmap bitmap)
	{
	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
	    return localByteArrayOutputStream.toByteArray();
	}
	
	private void SincronizarFotos()
	{
		if(ObtenerParametroGeneral())
		{
			if(parametroGeneral.is_sincronizarWifi())
			{
				if (theUtilidades.VerificarConexionInternetWifi(this))
			    {
					int i = 0;
					for(ClienteFoto item : listadoFotos)
					{
						if(item.get_clienteIdServer() != 0)
						{
							i++;
							InsertarFoto(item.get_foto(),item.get_id(),i,listadoFotos.size(),item.get_fotoCategoriaId());
						}
						else
				        {
							theUtilidades.MostrarMensaje(getApplicationContext(),"Primero debe sincronizar el cliente.", 1);
							MostrarPantallaMenuCensista();
				        }	
					}
			    }
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a una red WiFi para sincronizar las fotos.", 1);
					MostrarPantallaMenuCensista();
				}
			}
			else
			{
				if(theUtilidades.VerificarConexionInternet(this))
				{
					int i = 0;
					for(ClienteFoto item : listadoFotos)
					{
						if(item.get_clienteIdServer() != 0)
						{
							i++;
							InsertarFoto(item.get_foto(),item.get_id(),i,listadoFotos.size(),item.get_fotoCategoriaId());
						}
						else
				        {
							theUtilidades.MostrarMensaje(getApplicationContext(),"Primero debe sincronizar el cliente.", 1);
							MostrarPantallaMenuCensista();
				        }	
					}					
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encuentra conectado a internet, intentelo mas tarde..", 1);
				}
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el parametro general.",1);
		}
	}
	  
	private void InsertarFoto(byte[] foto, int rowId, int posicionActual, int posicionFinal, int fotoClasificacionId)
	{
	    pdInsertarFoto = new ProgressDialog(this);
	    pdInsertarFoto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarFoto.setMessage("Insertando imagen ...");
	    pdInsertarFoto.setCancelable(false);
		pdInsertarFoto.setCanceledOnTouchOutside(false);
	    
	    WSInsertarFoto wsInsertarFoto = new WSInsertarFoto(foto,rowId,posicionActual,posicionFinal,fotoClasificacionId);
	    try
	    {
	    	wsInsertarFoto.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSInsertarFoto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSInsertarFoto: " + localException.getMessage());
			}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertarFoto.", 1);
	    }
	}
	
	private class WSInsertarFoto extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTFOTO_METHOD_NAME = "InsertFotoCliente";
		String INSERTFOTO_SOAP_ACTION = NAMESPACE + INSERTFOTO_METHOD_NAME;
		
		boolean WSInsertarFoto = false;
		private byte[] _foto;
		private int _posicionActual;
		private int _posicionFinal;
		private int _rowId;
		private int _fotoClasificacionId;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
    
		public WSInsertarFoto(byte[] foto, int rowId, int posicionActual, int posicionFinal, int fotoClasificacionId)
		{
			this._foto = foto;
			this._rowId = rowId;
			this._posicionActual = posicionActual;
			this._posicionFinal = posicionFinal;
			this._fotoClasificacionId = fotoClasificacionId;
		}
    
		protected void onPreExecute()
	    {
			pdInsertarFoto.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarFoto = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTFOTO_METHOD_NAME);
			localSoapObject.addProperty("clienteId",theCliente.get_clienteId());
			localSoapObject.addProperty("imagen", this._foto);
			localSoapObject.addProperty("fileSize", this._foto.length);
			localSoapObject.addProperty("categoriaId", this._fotoClasificacionId);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			MarshalBase64 marshalBase64 = new MarshalBase64();
			marshalBase64.register(localSoapSerializationEnvelope);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(INSERTFOTO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObjects != null)
				{
					intResultado = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					stringResultado = soapObjects.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && (intResultado > 0)) 
				{
					WSInsertarFoto = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertarFoto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertFoto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertFoto: " + localException.getMessage());
				}
				return true;
			}		
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertarFoto.isShowing())
			{
				pdInsertarFoto.dismiss();
			}
			
			if (ejecutado)
			{
				if(WSInsertarFoto)
				{
					int i = 0;
					try
					{
						i = new ClienteFotoBLL().ModificarSincronizacionClienteFoto(this._rowId, true);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion cliente foto: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion cliente foto: " + localException.getMessage());
						}
					}
					if (i == 0) 
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo modificar el estado de sincronizacion de la foto cliente.", 1);
						return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Foto_" + String.valueOf(this._posicionActual) + " insertada en el servidor.", 1);
					}
          
					if (this._posicionActual == this._posicionFinal)
					{
						MostrarPantallaMenuCensista();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto en el servidor.", 1);
			        MostrarPantallaMenuCensista();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el WSInsertFoto.", 1);
			}
		}
	}
    
	private boolean ObtenerParametroGeneral()
	{
		boolean obtenido = false;
		
		parametroGeneral = null;
		try
		{
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		}
		catch(Exception localException)
		{
			if (localException.getMessage().isEmpty()) 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener el parametro general: vacio");
	        } 
	    	else 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener el parametro general: " + localException.getMessage());
	        }
			return obtenido;
		}
		
		if(parametroGeneral != null)
		{
			obtenido = true;
		}
		
		return obtenido;
	}
	
	private void MostrarPantallaMenuCensista()
	{
	    Intent localIntent = new Intent(this,Menucensista.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    localIntent.putExtra("Origen", Origen);
	    startActivity(localIntent);
	}
	
	@Override
	public void onBackPressed() 
	{
	    if (listadoFotos == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "Debe tomar al menos una fotografia al cliente.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarPantallaMenuCensista();
	    }
	}
	
}
