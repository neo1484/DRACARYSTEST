package com.detesim.venderunilever;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClienteCobranzaBLL;
import BLL.MyLogBLL;
import Clases.ClienteCobranza;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Cobradorcobranza extends Activity 
{
	MyLogBLL myLog = new MyLogBLL();
	Utilidades theUtilidades = new Utilidades();
	
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();
	
	ArrayList<ClienteCobranza> listadoCobranzas;
	int clienteId;
	
	LoginEmpleado loginEmpleado;
	LinearLayout llCobranzas;
	ListView lvCobranzas;
	Dialog dialog;
	ProgressDialog pdInsertPago;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cobradorcobranza);
		
		llCobranzas = (LinearLayout)findViewById(R.id.llCobradorCobranza);
		lvCobranzas = (ListView)findViewById(R.id.lvCobCliCobranzas);
		
		llCobranzas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		clienteId = 0;
	    clienteId = getIntent().getExtras().getInt("ClienteId");
	    if(clienteId <= 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el codigo de cliente", 1);
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
	    	ObtenerCobranzasPorCliente();
	    }
	}
	
	private void ObtenerCobranzasPorCliente()
	{
		listadoCobranzas = null;
		try
		{
			listadoCobranzas = new ClienteCobranzaBLL().ObtenerClienteCobranzaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las cobranzas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las cobranzas por clienteId: " + localException.getMessage());
	    	} 
		}
		
		if(listadoCobranzas != null)
		{
			LlenarClientesCobranzaListView();
	    	ClienteCobranzaItemOnClick();
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron cobranzas asociadas al cliente.", 1);
		}
	}
	
	private void LlenarClientesCobranzaListView()
	{
	    MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    	    
	    if(listadoCobranzas == null)
	    {
	    	lvCobranzas.setAdapter(null);
	    }
	    else
	    {
		    ViewGroup.LayoutParams localLayoutParams = lvCobranzas.getLayoutParams();
		    localLayoutParams.height = ((int)(30*getApplicationContext().getResources().getDisplayMetrics().density) * listadoCobranzas.size());
		    lvCobranzas.setLayoutParams(localLayoutParams);
		    lvCobranzas.setAdapter(localMiAdaptadorLista);
	    }
	}

	class MiAdaptadorLista extends ArrayAdapter<ClienteCobranza>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_cobradorcobranza,listadoCobranzas);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_cobradorcobranza, parent, false);
			}
				
			ClienteCobranza localClienteCobranza = (ClienteCobranza)listadoCobranzas.get(position);
			
			TextView nombre = (TextView)localView.findViewById(R.id.tvDisCobCobNombre);
			TextView fecha = (TextView)localView.findViewById(R.id.tvDisCobCobFecha);
			TextView monto = (TextView)localView.findViewById(R.id.tvDisCobCobMonto);
			TextView saldo = (TextView)localView.findViewById(R.id.tvDisCobCobSaldo);
			TextView nroFactura = (TextView)localView.findViewById(R.id.tvDisCobCobNroFactura);
			
			nombre.setText(localClienteCobranza.get_nombre());
			fecha.setText(localClienteCobranza.get_fecha());
			monto.setText(String.valueOf(localClienteCobranza.get_monto()));
			saldo.setText(String.valueOf(localClienteCobranza.get_saldo()));
			nroFactura.setText(localClienteCobranza.get_nroFactura().isEmpty()?"---":localClienteCobranza.get_nroFactura());
  	
			return localView;
		}
	}
	
	private void ClienteCobranzaItemOnClick()
	{
	    ((ListView)findViewById(R.id.lvCobCliCobranzas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	    	ClienteCobranza localCobranza = null;
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	{
	    		localCobranza = ((ClienteCobranza)listadoCobranzas.get(position));
	    		
	    		dialog = new Dialog(Cobradorcobranza.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.disenio_cobradorpagocobranza);
				dialog.setTitle("");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
				
				final TextView tvMontoCredito = (TextView)dialog.findViewById(R.id.tvDisCobPagCobMontoCredito);
				final EditText etNumeroCheque = (EditText)dialog.findViewById(R.id.etDisCobPagCobMontoCheque);
				final EditText etRecibo = (EditText)dialog.findViewById(R.id.etDisCobPagCobRecibo);
				final EditText etMontoPago = (EditText)dialog.findViewById(R.id.etDisCobPagCobMontoPago);
				
				tvMontoCredito.setText(String.valueOf(localCobranza.get_saldo()));
				
				Button btnPagar = (Button)dialog.findViewById(R.id.btnDisCobPagCobPagar);
				btnPagar.setOnClickListener(new OnClickListener() 
					{		
						@Override
						public void onClick(View v) 
						{
							switch(v.getId())
							{
								case R.id.btnDisCobPagCobPagar:
									if(etMontoPago.getText().length()<=0)
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "Debe especificar un monto de pago", 1);
										return;
									}
									
									if(Float.parseFloat(etMontoPago.getText().toString()) > localCobranza.get_saldo())
									{
										theUtilidades.MostrarMensaje(getApplicationContext(), "El monto que desea pagar es mayor al saldo adeudado.", 1);
										return;
									}
									
									InsertPago(localCobranza,etNumeroCheque.getText().toString(),etRecibo.getText().toString(),etMontoPago.getText().toString());
									break;
								
							}	
						}
					});
				
				Button btnCancelar = (Button)dialog.findViewById(R.id.btnDisCobPagCobCancelar);
				btnCancelar.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
						{
							switch(v.getId())
							{
							case R.id.btnDisCobPagCobCancelar:
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
	
	private void InsertPago(ClienteCobranza clienteCobranza,String nroCheque,String nroRecibo,String montoPago)
	{
		pdInsertPago = new ProgressDialog(this);
		pdInsertPago.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdInsertPago.setMessage("Regitrando pago ...");
		pdInsertPago.setCancelable(false);
		pdInsertPago.setCanceledOnTouchOutside(false);
	    
	    WSInsertPago localWSInsertPAgo = new WSInsertPago(clienteCobranza,nroCheque,nroRecibo,montoPago);
	    try
	    {
	    	localWSInsertPAgo.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertPagoFromApk: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSInsertPagoFromApk: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSInsertPagoFromApk.", 1);
	    }    
	}
	
	private class WSInsertPago extends AsyncTask<Void, Integer, Boolean>
	{
		String PAGO_METHOD_NAME = "InsertarCobranza";
		String PAGO_SOAP_ACTION = NAMESPACE + PAGO_METHOD_NAME;
		
		private ClienteCobranza _clienteCobranza;
		private String _nroCheque;
		private String _nroRecibo;
		private String _montoPago;
		
		boolean WSInsertarPago;
		SoapObject soapPago;
		String stringRespuesta;
		int intRespuesta;
		
		public WSInsertPago(ClienteCobranza clienteCobranza,String nroCheque,String nroRecibo,String montoPago)
		{
			this._clienteCobranza = clienteCobranza;
			this._nroCheque = nroCheque;
			this._nroRecibo = nroRecibo;
			this._montoPago = montoPago;
		}
		
		protected void onPreExecute()
	    {
			pdInsertPago.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSInsertarPago = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,PAGO_METHOD_NAME);
			localSoapObject.addProperty("cobradorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("monto", _montoPago);
			localSoapObject.addProperty("nroCheque", _nroCheque);
			localSoapObject.addProperty("nroRecibo", _nroRecibo);
			localSoapObject.addProperty("ventaId", String.valueOf(_clienteCobranza.get_ventaId()));
						
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(PAGO_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapPago = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("InsertarCobranzaResult"));
				if(soapPago != null)
				{
					stringRespuesta = soapPago.getPropertyAsString("Resultado");
					intRespuesta = Integer.parseInt(soapPago.getPropertyAsString("Id"));
				}				
				
				if (intRespuesta > 0) 
				{
					WSInsertarPago = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSInsertarPago = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice InsertPagoFromApk: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice InsertPagoFromApk: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdInsertPago.isShowing())
			{
				pdInsertPago.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSInsertarPago)
				{						
					long l = 0;
					try
					{
						l = new ClienteCobranzaBLL().ModificarClienteCobranzaServerId(_clienteCobranza.get_rowId(), intRespuesta, Float.parseFloat(stringRespuesta));
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del cliente cobranza: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincro del cliente cobranza: " + localException.getMessage());
						}
					}
					
					if(l <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar el cliente cobranza.", 1);
					}
				
					if(dialog.isShowing())
					{
						dialog.dismiss();
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "Cobranza registrada", 1);
					MostrarPantallaMapaCobrador();
				
				}
				else
				{
					if(dialog.isShowing())
					{
						dialog.dismiss();
					}
					
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo registrar la cobranza del cliente.", 1);
			        return;
				}
			}
			else
			{
				if(dialog.isShowing())
				{
					dialog.dismiss();
				}
				theUtilidades.MostrarMensaje(getApplicationContext(), "El WebService InsertPAgoFromApk no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
	private void MostrarPantallaMapaCobrador()
	{
		startActivity(new Intent(this,Cobradormapaclientes.class));
	}
}
