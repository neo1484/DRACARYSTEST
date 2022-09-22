package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteFotoBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import Clases.ClienteFoto;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class Censistasincronizacionclientefoto extends Activity 
{	
	LinearLayout llCensistaSincronizacionClienteFoto;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String Origen;
	ParametroGeneral parametroGeneral;
	
	ArrayList<ClienteFoto> listadoClienteFotoNoSincronizados;
	
	ListView lvFotosNoSincronizadas;
	ProgressDialog pdSincronizarFoto;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistasincronizacionclientefoto);
		
		llCensistaSincronizacionClienteFoto = (LinearLayout)findViewById(R.id.CensistaSincronizacionClienteFotoLinearLayout);
		lvFotosNoSincronizadas = (ListView)findViewById(R.id.lvSincronizacionClienteFotoFotosNoSincronizadas);
		
		llCensistaSincronizacionClienteFoto.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		Origen = "";
		Origen = getIntent().getExtras().getString("Origen");
		if (Origen == "") 
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el origen de la peticion.", 1);
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
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el login empleado: vacio");
    		}
    		else
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al obtener el login empleado: " + localException.getMessage());
    		} 
	    }
	      
	    if(loginEmpleado != null)
	    {
	        if(theUtilidades.ValidarFecha(loginEmpleado.get_dia(),loginEmpleado.get_mes(),loginEmpleado.get_anio())) 
	        {
	        	ObtenerFotosNoSincronizadas();
	        }
	        else
	        {
	        	theUtilidades.MostrarMensaje(getApplicationContext(), "La fecha del dispositivo no concuerda con la del servidos.", 1);
		    	return;
	        }
	    }
	    else
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	    	return;
	    }    
	}
	    
	private void ObtenerFotosNoSincronizadas()
	{
		listadoClienteFotoNoSincronizados = null;
		try
		{
			listadoClienteFotoNoSincronizados = new ClienteFotoBLL().ObtenerClientesFotoNoSincronizados();
		}
    	catch (Exception localException)
		{
    		if(localException.getMessage() == null || localException.getMessage().isEmpty())
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos no sincronizadas: vacio");
    		}
    		else
    		{
    			myLog.InsertarLog("App",this.toString(),1,"Error al obtener las fotos no sincronizadas: " + localException.getMessage());
    		} 
        }
	        
		if(listadoClienteFotoNoSincronizados == null)
		{
			lvFotosNoSincronizadas.setAdapter(null);
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existen fotos para sincronizar.", 1);
			return;
        }
		else
		{
			LlenarListViewFotosNoSincronizadas();
			SincronizarItemListViewFotosNoSincronizadas();	
        } 
	}
	
	private void LlenarListViewFotosNoSincronizadas()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = lvFotosNoSincronizadas;
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(110*getApplicationContext().getResources().getDisplayMetrics().density) * listadoClienteFotoNoSincronizados.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<ClienteFoto>
	{
		private Context _context;
		
	    public MiAdaptadorLista(Context context)
	    {
	    	super(context,R.layout.disenio_censistasincronizacionclientefoto,listadoClienteFotoNoSincronizados);
	    	this._context = context;
	    }
	    
	    public View getView(int position, View convertView, ViewGroup parent)
	    {
	    	View localView = convertView;
	    	
	    	if (localView == null) 
	    	{
	    		LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_censistasincronizacionclientefoto, parent, false);
	    	}
	    	
	      ClienteFoto localClienteFoto = (ClienteFoto)listadoClienteFotoNoSincronizados.get(position);
	      
	      TextView codigo = (TextView)localView.findViewById(R.id.tvSupervisorSincronizacionClienteFotoCodigo);
	      ImageView foto = (ImageView)localView.findViewById(R.id.ivSupervisorSincronizacionClienteFotoFoto);
	      
	      codigo.setText("Id: " + String.valueOf(localClienteFoto.get_id()));
	      foto.setImageBitmap(ConvertirVectorDeBytesABitmap(localClienteFoto.get_foto()));
	      
	      return localView;
	    }
	}
	
	public Bitmap ConvertirVectorDeBytesABitmap(byte[] paramArrayOfByte)
	{
	    return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
	}
	
	private void SincronizarItemListViewFotosNoSincronizadas()
	{
	    ((ListView)findViewById(R.id.lvSincronizacionClienteFotoFotosNoSincronizadas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		ClienteFoto localClienteFoto = (ClienteFoto)listadoClienteFotoNoSincronizados.get(position);
	    		
	    		if(ObtenerParametroGeneral())
	    		{
	    			if(parametroGeneral.is_sincronizarWifi())
	    			{
	    				if(theUtilidades.VerificarConexionInternetWifi(getApplicationContext()))
			    		{
			    			InsertarFoto(localClienteFoto.get_foto(), localClienteFoto.get_id(), localClienteFoto.get_clienteIdServer(),localClienteFoto.get_fotoCategoriaId());
			    		}
			    		else
			    		{
			    			theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a una red WiFi para sincronizar las fotos.", 1);
			    			return;
			    		}
	    			}	    			
	    			else
	    			{
	    				if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
	    				{
	    					InsertarFoto(localClienteFoto.get_foto(), localClienteFoto.get_id(), localClienteFoto.get_clienteIdServer(),localClienteFoto.get_fotoCategoriaId());
	    				}
	    				else
	    				{
	    					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encuentra conectado a internet, intentelo mas tarde..", 1);
	    				}
	    			}
	    		}
	    		else
	    		{
	    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el parametro general.", 1);
	    		}
	      }
	    });
	}
	
	private void InsertarFoto(byte[] paramArrayOfByte, int paramInt1, int paramInt2,int categoriaId)
	{
	    pdSincronizarFoto = new ProgressDialog(this);
	    pdSincronizarFoto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pdSincronizarFoto.setMessage("Sincronizando imagen ...");
	    pdSincronizarFoto.setCancelable(false);
	    pdSincronizarFoto.setCanceledOnTouchOutside(false);
	    
	    WSInsertarFoto localWSInsertarFoto = new WSInsertarFoto(paramArrayOfByte, paramInt1, paramInt2,categoriaId);
	    try
	    {
	      localWSInsertarFoto.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	  		{
	    	  myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertFotoCliente: vacio");
	  		}
	  		else
	  		{
	  			myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertFotoCliente: " + localException.getMessage());
	  		}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertFotoCliente.", 1);
	    }
	}
	
	private class WSInsertarFoto extends AsyncTask<Void, Integer, Boolean>
	{
		String INSERTFOTO_METHOD_NAME = "InsertFotoCliente";
		String INSERTFOTO_SOAP_ACTION = NAMESPACE + INSERTFOTO_METHOD_NAME;
		
		boolean WSInsertarFoto = false;
		private int _clienteId;
		private byte[] _foto;
		private int _rowId;
		private int _categoriaId;
		int intResultado;
		String stringResultado;
    
		public WSInsertarFoto(byte[] foto, int id, int clienteId, int categoriaId)
		{
			this._foto = foto;
			this._rowId = id;
			this._clienteId = clienteId;
			this._categoriaId = categoriaId;
		}
    
		protected void onPreExecute()
	    {
			pdSincronizarFoto.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarFoto = false;

			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,INSERTFOTO_METHOD_NAME);
			localSoapObject.addProperty("clienteId", Integer.valueOf(this._clienteId));
			localSoapObject.addProperty("imagen", this._foto);
			localSoapObject.addProperty("fileSize", Long.valueOf(this._foto.length));
			localSoapObject.addProperty("categoriaId",this._categoriaId);
      
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			new MarshalBase64().register(localSoapSerializationEnvelope);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

			try
			{
				localHttpTransportSE.call(this.INSERTFOTO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				SoapObject localSoapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				if(localSoapObjects!=null)
				{
					intResultado = Integer.parseInt(localSoapObjects.getPropertyAsString("Id"));
					stringResultado = localSoapObjects.getPropertyAsString("Resultado");
				}
				
				if(stringResultado.equals("OK") && this.intResultado > 0) 
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
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertFotoCliente: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSInsertFotoCliente: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdSincronizarFoto.isShowing())
			{
				pdSincronizarFoto.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarFoto)
				{
					int i=0;
					try
					{
						i = new ClienteFotoBLL().ModificarSincronizacionClienteFoto(_rowId, true);
					}
					catch (Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPermisosByEmpleado: " + localException.getMessage());
						} 
					}
            
					if (i == 0) 
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado de sincronizacion de la foto cliente.", 1);
						return;
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "Foto insertada en el servidor.", 1);
						if(listadoClienteFotoNoSincronizados != null)
				        {
							ObtenerFotosNoSincronizadas();
				        }
						else
						{
							MostrarPantallaMenuSupervisor();
						}
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),stringResultado, 1);
			        return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el webservice WSInsertarFoto.", 1);
				return;
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
	
	private void MostrarPantallaMenuSupervisor()
	  {
		if(Origen.equals("Menuvendedor"))
		{
			Intent intent = new Intent(this,Menuvendedor.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(this,Menucensista.class);
			intent.putExtra("Origen", Origen);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	  }
  
	@Override
	public void onBackPressed() 
	{
		if(Origen.equals("Menuvendedor"))
		{
			Intent intent = new Intent(this,Menuvendedor.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(this,Menucensista.class);
			intent.putExtra("Origen", Origen);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
}
