package com.detesim.venderunilever;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteFotoBLL;
import BLL.FotoCategoriaBLL;
import BLL.MyLogBLL;
import Clases.ClienteFoto;
import Clases.FotoCategoria;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Vendedoradminfotosclientes extends Activity implements OnClickListener
{
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	static final int PICK_IMAGE = 100;
	Uri imageUri;
	
	LinearLayout llVenAdmFotCli;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	int clienteId;
	LoginEmpleado loginEmpleado;
	ArrayList<ClienteFoto> listadoClienteFoto;
	ArrayList<FotoCategoria> listadoFotoCategoria;
	int categoriaId;
	byte[] fotoActual;
	
	ProgressDialog pdEsperaObtenerFotosCliente;
	ListView lvFotos;
	CheckBox cbTomarFoto;
	TextView tvCategoria;
	Spinner spnCategorias;
	TextView tvOrigenFoto;
	ImageView ivCamara;
	ImageView ivFile;
	Dialog dialog;
	ProgressDialog pdInsertarFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoradminfotosclientes);
		
		llVenAdmFotCli = (LinearLayout)findViewById(R.id.llVenAdmFotCliente);
		lvFotos = (ListView)findViewById(R.id.lvVenAdmFotCliFotos);
		cbTomarFoto = (CheckBox)findViewById(R.id.cbVenAdmFotCliAdicionarFoto);
		cbTomarFoto.setOnClickListener(this);
		tvCategoria = (TextView)findViewById(R.id.tvVenAdmFotCliCategoria);
		spnCategorias = (Spinner)findViewById(R.id.spnVenAdmFotCliCategorias);
		tvOrigenFoto = (TextView)findViewById(R.id.tvVenAdmFotCliOrigenFoto);
		ivCamara = (ImageView)findViewById(R.id.ivVenAdmFotCliCamara);
		ivCamara.setOnClickListener(this);
		ivFile = (ImageView)findViewById(R.id.ivVenAdmFotCliFile);
		ivFile.setOnClickListener(this);
		
		llVenAdmFotCli.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("clienteId");
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el clienteId.", 1);
	    	return;
	    }
	    
		loginEmpleado = null;
		
		try
		{
			loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado vendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado vendedor: " + localException.getMessage());
	    	} 
		}
		
		if(loginEmpleado == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se encuentra logueado.", 1);
		}
		else
		{
			MostrarControlesFoto(false);
			if(ObtenerFotoCategorias())
			{
				ObtenerFotosClienteWS();
			}
		}
	}
	
	@Override
	public void onClick(View view) 
	{
		switch (view.getId()) 
		{
			case R.id.cbVenAdmFotCliAdicionarFoto:
				if(cbTomarFoto.isChecked())
				{
					MostrarControlesFoto(true);
				}
				else
				{
					MostrarControlesFoto(false);
				}
				break;
			case R.id.ivVenAdmFotCliCamara:
				TomarFoto();			
				break;
			case R.id.ivVenAdmFotCliFile:
				AbrirGaleria();
				break;
		}
	}
	
	private void MostrarControlesFoto(boolean estado)
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
		
		tvCategoria.setVisibility(visibility);
		spnCategorias.setVisibility(visibility);
		tvOrigenFoto.setVisibility(visibility);
		ivCamara.setVisibility(visibility);
		ivFile.setVisibility(visibility);
	}
	
	private boolean ObtenerFotoCategorias()
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
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categorias de las fotosr: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las categorias de las fotos: " + localException.getMessage());
	    	} 
		}
		
		if(listadoFotoCategoria == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron categorias de las fotos.", 1);
			return false;
		}
		else
		{
			ArrayAdapter<FotoCategoria> localArrayAdapter = new ArrayAdapter<FotoCategoria>(getApplicationContext(), R.layout.disenio_spinner, listadoFotoCategoria);
			spnCategorias.setAdapter(localArrayAdapter);
			
			spnCategorias.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {	    	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
				{
					categoriaId = ((FotoCategoria)spnCategorias.getSelectedItem()).get_categoriaId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent){}
		    	
			});
		    
			return true;
		}
	}
	
	private void ObtenerFotosClienteWS()
	 {
		 pdEsperaObtenerFotosCliente = new ProgressDialog(this);
		 pdEsperaObtenerFotosCliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	     pdEsperaObtenerFotosCliente.setMessage("Obteniendo fotografias cliente ...");
	     pdEsperaObtenerFotosCliente.setCancelable(false);
	     pdEsperaObtenerFotosCliente.setCanceledOnTouchOutside(false);
	         
	    WSObtenerFotosCliente localWSObtenerFotosCliente = new WSObtenerFotosCliente();
	    try
	    {
	    	localWSObtenerFotosCliente.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetFotosCliente: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetFotosCliente: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetFotosCliente.", 1);
	    	return;
	    }
	 }
	 
	private class WSObtenerFotosCliente extends AsyncTask<Void, Integer, Boolean>
	 {
	    String FOTOCLIENTE_METHOD_NAME = "GetFotosByCliente";
	    String FOTOCLIENTE_SOAP_ACTION = NAMESPACE + FOTOCLIENTE_METHOD_NAME;
	    
	    boolean WSObtenerFotosCliente = false;
	    SoapObject soapFotosCliente;
	    int totalItems;
	    
	    protected void onPreExecute()
	    {
	    	pdEsperaObtenerFotosCliente.show();
	    }
	    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSObtenerFotosCliente = false;
	      
	    	SoapObject localSoapObject = new SoapObject(NAMESPACE,FOTOCLIENTE_METHOD_NAME);
	    	localSoapObject.addProperty("clienteId", clienteId);
	    	SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
	    	localSoapSerializationEnvelope.dotNet = true;
	    	localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
	    	HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
	      
	    	try
	    	{
	    		localHttpTransportSE.call(FOTOCLIENTE_SOAP_ACTION,localSoapSerializationEnvelope);
	        
	    		soapFotosCliente = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetFotosByClienteResult"));
	    		if(soapFotosCliente != null)
	    		{
	    			totalItems = soapFotosCliente.getPropertyCount();
   			}
	    		
	    		if(totalItems > 0) 
	    		{
	    			WSObtenerFotosCliente = true;
   			}
	    		return true;
	      }
	      catch (Exception localException)
	      {
	    	  WSObtenerFotosCliente = false;
	    	  
	    	  if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetFotosByCliente: vacio");
	    	  }
	    	  else
	    	  {
	    		  myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetFotosByCliente: " + localException.getMessage());
	    	  } 
	    	  
	    	  return true;
	      }
	    }
	    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdEsperaObtenerFotosCliente.isShowing())
	    	{
	    		pdEsperaObtenerFotosCliente.dismiss();
	    	}
	    	
	    	if(ejecutado)
	    	{
	    		if(WSObtenerFotosCliente)
	    		{
	    			if(BorrarFotosCliente())
	    			{
	    				for(int i=0; i<totalItems; i++)
	    				{
	    					long l = 0;
	    					
	    					SoapObject localSoapObject = (SoapObject)soapFotosCliente.getProperty(i);
	    					
	    					try
	    					{
	    						l = new ClienteFotoBLL().InsertarClienteFoto(0,
	    								Integer.parseInt(localSoapObject.getPropertyAsString("ClienteId")), 
	    								Base64.decode(localSoapObject.getPropertyAsString("FileBinary").toString()),
	    								true,
	    								Integer.parseInt(localSoapObject.getPropertyAsString("CategoriaId")),
	    								Integer.parseInt(localSoapObject.getPropertyAsString("ClienteFotoId")));
	    						
	    					}
	    					catch(Exception localException)
	    					{
	    						if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    						{
	    					        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la foto del cliente: vacio");
	    						}
	    						else
	    						{
	    							myLog.InsertarLog("App",this.toString(),1,"Error al insertar la foto del cliente: " + localException.getMessage());
	    						} 	
	    					}
	    					
	    					if(l==0)
	    					{
	    						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto del cliente. ", 1);
	    						return;
	    					}
	    				}
	    				
	    				theUtilidades.MostrarMensaje(getApplicationContext(), String.valueOf(totalItems) + " fotos encontradas.", 1);
	    				ObtenerFotosCliente();	    				   			
	    			}
	   				else
	   				{
	   					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar las fotos del cliente.", 1);
	   			        return;
	   				}
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron fotos que descargar.", 1);
   				}
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "El WSGetFotosByCliente no se ejecuto correctamente.", 1);
	    	}
	    }
	}
	 
	private boolean BorrarFotosCliente() 
	{
		try
		{
			boolean bool = new ClienteFotoBLL().BorrarClienteFotos(clienteId);
			return bool;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotos del cliente : vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotos del cliente : " + localException.getMessage());
			} 
		}
		return false;
	}
	
	private void ObtenerFotosCliente()
	{
	    listadoClienteFoto = null;
	    try
	    {
	    	listadoClienteFoto = new ClienteFotoBLL().ObtenerClientesFotoServer(clienteId);
	    }
	    catch (Exception localException)
	    {
	    	if (localException.getMessage().isEmpty()) 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las fotos del server del cliente. " + localException.getMessage());
	        } 
	    	else 
	    	{
	    		myLog.InsertarLog("App", toString(), 1, "Error al obtener las fotos del server del cliente. " + localException.getMessage());
	        }
	    }
	    	    
	    if (listadoClienteFoto == null)
	    {
	    	lvFotos.setVisibility(View.INVISIBLE);
	    }
	    else
	    {
	    	lvFotos.setVisibility(View.VISIBLE);
	    }
	    	
	    LlenarListViewFotos();
	    EliminarItemListViewFotos();
	}
	
	private void LlenarListViewFotos()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvVenAdmFotCliFotos);
	    
	    if(listadoClienteFoto == null)
	    {
	    	localListView.setAdapter(null);
	    }
	    else
	    {
	    	ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(110*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClienteFoto.size());
	    	localListView.setLayoutParams(localLayoutParams);
	    	localListView.setAdapter(localMiAdaptadorLista);
	    }
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClienteFoto>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_censistaclientefoto,listadoClienteFoto);
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
			
			ClienteFoto localClienteFoto = (ClienteFoto)listadoClienteFoto.get(position);
			
			TextView textView = (TextView)localView.findViewById(R.id.tvDisenioClienteFotoNombre);
			ImageView imageView = (ImageView)localView.findViewById(R.id.ivDisenioClienteFotoFoto);
			
			FotoCategoria theFotoCategoria = null;
			
			try
			{
				theFotoCategoria = new FotoCategoriaBLL().ObtenerFotoCategoriaPor(localClienteFoto.get_fotoCategoriaId());
				
			}catch(Exception localException)
			{
				if (localException.getMessage().isEmpty())
	    		  {
	    			  myLog.InsertarLog("App", toString(), 1, "Error al obtener la categoria de la foto por categoriaId: vacio");
	    		  }
	    		  else
	    		  {
	    			  myLog.InsertarLog("App", toString(), 1, "Error al obtener la categoria de la foto por categoriaId: " + localException.getMessage());
	    		  }
			}
			if(theFotoCategoria == null)
			{
				textView.setText("Foto sin categoria");
			}
			else
			{
				textView.setText(theFotoCategoria.get_descripcion());
			}
					
			imageView.setImageBitmap(ConvertirVectorDeBytesABitmap(localClienteFoto.get_foto()));
			
			return localView;
		}
	}
	
	private void EliminarItemListViewFotos()
	{
		((ListView)findViewById(R.id.lvVenAdmFotCliFotos)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	      {
	    	  final ClienteFoto localClienteFoto = (ClienteFoto)listadoClienteFoto.get(position);
	    	  dialog = new Dialog(Vendedoradminfotosclientes.this);
	    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	  dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
	    	  dialog.setTitle("Eliminando item");
	    	  dialog.setCancelable(false);
			  dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
			
	    	  TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
	    	  tvMensaje.setText("Esta seguro de eliminar la foto?");
			
	    	  Button btnAceptar = (Button)dialog.findViewById(R.id.btnDialogoAceptar);
	    	  btnAceptar.setOnClickListener(new OnClickListener() 
	    	  {		
	    		  @Override
	    		  public void onClick(View v) 
	    		  {
	    			  switch(v.getId())
	    			  {
	    			  	case R.id.btnDialogoAceptar:
						
						if(dialog.isShowing())
						{
							dialog.dismiss();
						}
						
						DeleteClienteFoto(localClienteFoto);
						
						break;
					}	
				}
			});
			
			Button btnCancelar = (Button)dialog.findViewById(R.id.btnDialogoCancelar);
			btnCancelar.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
						case R.id.btnDialogoCancelar:
							if(dialog.isShowing())
							{
								dialog.dismiss();
							}
						}							
					}
				});
			
			dialog.show();
	      }
	    });
	  }
	
	public Bitmap ConvertirVectorDeBytesABitmap(byte[] paramArrayOfByte)
	{
	    return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
	}
	
	private void TomarFoto()
	{
		if(categoriaId == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una categoria de la foto.", 1);
			return;
		}
		else
		{
			Intent localIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		    if (localIntent.resolveActivity(getPackageManager()) != null) 
		    {
		      startActivityForResult(localIntent, REQUEST_IMAGE_CAPTURE);
		    }
		}
	}
	
	private void AbrirGaleria()
	{
		if(categoriaId == 0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe seleccionar una categoria de la foto.", 1);
			return;
		}
		else
		{
			Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			startActivityForResult(gallery,PICK_IMAGE);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    fotoActual = null;
	    
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
	    {
	    	fotoActual = ConvertirBitmapAVectorDeBytes((Bitmap)data.getExtras().get("data"));
	    }
	    
	    if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK)
	    {
	    	try 
	    	{
				InputStream inputStream = getContentResolver().openInputStream(data.getData());
				fotoActual = getBytes(inputStream);
			} 
	    	catch (FileNotFoundException e) 
	    	{
				e.printStackTrace();
			} 
	    	catch (IOException e) 
	    	{ 
				e.printStackTrace();
			}
	    }
	  
	    ClienteFoto localClienteFoto = new ClienteFoto(0,clienteId,clienteId,fotoActual,false,categoriaId,0);

	    InsertarFoto(localClienteFoto);
	}
	
	public static byte[] ConvertirBitmapAVectorDeBytes(Bitmap paramBitmap)
	{
	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	    paramBitmap.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
	    return localByteArrayOutputStream.toByteArray();
	}

	private byte[] getBytes(InputStream iStream) throws IOException
	{
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		
		int len = 0;
		while((len = iStream.read(buffer)) != -1)
		{
			byteBuffer.write(buffer,0,len);
		}
		return byteBuffer.toByteArray();
	}
	
 	private void InsertarFoto(ClienteFoto theClienteFoto)
	{
	    pdInsertarFoto = new ProgressDialog(this);
	    pdInsertarFoto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarFoto.setMessage("Insertando foto ...");
	    pdInsertarFoto.setCancelable(false);
	    pdInsertarFoto.setCanceledOnTouchOutside(false);
	    
	    WSInsertarFoto wsInsertarFoto = new WSInsertarFoto(theClienteFoto);
	    
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
		    return;
	    }
	}
	
	private class WSInsertarFoto extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTFOTO_METHOD_NAME = "InsertFotoCliente";
		String INSERTFOTO_SOAP_ACTION = NAMESPACE + INSERTFOTO_METHOD_NAME;
		
		private ClienteFoto _clienteFoto;
		boolean WSInsertarFoto = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
    
		public WSInsertarFoto(ClienteFoto theClienteFoto)
		{
			this._clienteFoto = theClienteFoto;
		}
    
		protected void onPreExecute()
	    {
			pdInsertarFoto.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarFoto = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTFOTO_METHOD_NAME);
			localSoapObject.addProperty("clienteId",_clienteFoto.get_clienteIdServer());
			localSoapObject.addProperty("imagen", _clienteFoto.get_foto());
			localSoapObject.addProperty("fileSize", _clienteFoto.get_foto().length);
			localSoapObject.addProperty("categoriaId", _clienteFoto.get_fotoCategoriaId());
			
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
					long l=0;
				    try
				    {
				       l = new ClienteFotoBLL().InsertarClienteFoto(_clienteFoto.get_clienteIdServer(),_clienteFoto.get_clienteIdServer(),_clienteFoto.get_foto(),
				    		   										true,_clienteFoto.get_fotoCategoriaId(),intResultado);
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
				    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto del cliente.", 1);
				    	return;
				    }
				    else
				    {
				    	ObtenerFotosCliente();
				    }
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar la foto en el servidor.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el WSInsertFoto.", 1);
			}
		}
	}
	
	private void DeleteClienteFoto(ClienteFoto clienteFoto)
	{
	    pdInsertarFoto = new ProgressDialog(this);
	    pdInsertarFoto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdInsertarFoto.setMessage("Eliminando foto ...");
	    pdInsertarFoto.setCancelable(false);
	    pdInsertarFoto.setCanceledOnTouchOutside(false);
	    
	    WSDeleteClienteFoto wsDeleteClienteFoto = new WSDeleteClienteFoto(clienteFoto);
	    
	    try
	    {
	    	wsDeleteClienteFoto.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSDeleteClienteFoto: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSDeleteClienteFoto: " + localException.getMessage());
			}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSDeleteClienteFoto.", 1);
		    return;
	    }
	}
	
	private class WSDeleteClienteFoto extends AsyncTask<Void, Integer, Boolean>
	{
		String DELETEFOTO_METHOD_NAME = "DeleteClienteFoto";
		String DELETEFOTO_SOAP_ACTION = NAMESPACE + DELETEFOTO_METHOD_NAME;
		
		private ClienteFoto _clienteFoto;
		boolean WSDeleteFoto = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;
    
		public WSDeleteClienteFoto(ClienteFoto clienteFoto)
		{
			this._clienteFoto = clienteFoto;
		}
    
		protected void onPreExecute()
	    {
			pdInsertarFoto.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSDeleteFoto = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,DELETEFOTO_METHOD_NAME);
			localSoapObject.addProperty("fotoId",_clienteFoto.get_fotoIdServer());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(DELETEFOTO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(soapObjects != null)
				{
					intResultado = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					stringResultado = soapObjects.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && (intResultado > 0)) 
				{
					WSDeleteFoto = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSDeleteFoto = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSDeleteclienteFoto: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSDeleteClienteFoto: " + localException.getMessage());
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
				if(WSDeleteFoto)
				{
					boolean b=false;
				    try
				    {
				       b = new ClienteFotoBLL().BorrarClientesFotoPor(_clienteFoto.get_id());
				    }
				    catch (Exception localException)
				    {
				    	if (localException.getMessage().isEmpty())
				        {
				    		myLog.InsertarLog("App", toString(), 1, "Error al borrar la foto del cliente por rowId: vacio");
				        }
				        else
				        {
				        	myLog.InsertarLog("App", toString(), 1, "Error al borrar la foto del cliente por rowId: " + localException.getMessage());
				        }
				    }
				    if (!b)
				    {
				    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar la foto del cliente.", 1);
				    	return;
				    }
				    else
				    {
				    	ObtenerFotosCliente();
				    }
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar la foto en el servidor.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el WSDeleteClienteFoto.", 1);
			}
		}
	}
}