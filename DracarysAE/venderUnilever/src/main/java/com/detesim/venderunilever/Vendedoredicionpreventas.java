package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import BLL.ClientePreventaBLL;
import BLL.DraRebateSaldoBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import BLL.PreventaProductoBLL;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.Preventa;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Vendedoredicionpreventas extends Activity
{
	LinearLayout llVendedorEdicionPreventas;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	ListView lvPreventa;
	Dialog dialogPreventa;
	ProgressDialog pdDeletePreventa;
	
	ArrayList<Preventa> listadoPreventa;
	int clienteId;
	int preventaId;
	int preventaIdServer;
	String factura;
	String nit;
	String tipoNit;
	boolean esBonificada = false;
	
	ProgressDialog pdEsperaObtenerPreventaBonificada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoredicionpreventas);
		
		llVendedorEdicionPreventas = (LinearLayout)findViewById(R.id.llEdicionPreventas);
		lvPreventa = (ListView)findViewById(R.id.lvVendedorVentaDirectaPreventas);
		
		llVendedorEdicionPreventas.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	    
	    ObtenerPreventasForDisplay();
	}
	
	private void ObtenerPreventasForDisplay()
	{
		listadoPreventa = null;
	    try
	    {
	    	listadoPreventa = new PreventaBLL().ObtenerPreventas();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoPreventa == null) 
	    {
	    	MostrarControles(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen preventas que visualizar.", 1);
	    	return;
	    }
	    else
	    {
	    	MostrarControles(true);
	    	LlenarPreventaListView();	    	
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
		
		lvPreventa.setVisibility(visibility);
	}

	private void LlenarPreventaListView()
	{
		MiEdicionPreventaAdapter localMiEdicionPreventaAdapter = new MiEdicionPreventaAdapter(this,listadoPreventa);
		ListView localListView = lvPreventa;
		if(listadoPreventa == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoPreventa.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiEdicionPreventaAdapter);
		}
	}
	
	class MiEdicionPreventaAdapter extends BaseAdapter implements ListAdapter
	{		
		private Context _context;
		private ArrayList<Preventa> _listPreventa = new ArrayList<Preventa>();

		public MiEdicionPreventaAdapter(Context contexto,ArrayList<Preventa> listadoPreventas) 
		{
			this._context = contexto;
			this._listPreventa = listadoPreventas;		
		}
		
		@Override
		public int getCount()
		{
			return _listPreventa.size();
		}

		@Override
		public Object getItem(int position) 
		{
			return _listPreventa.get(position);
		}

		@Override
		public long getItemId(int position) 
		{
			return _listPreventa.get(position).get_Id();
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) 
		{
			View view = convertView;
			
			if(view == null)
			{
				LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.disenio_edicionpreventas,null);
			}
			
			TextView tvNombre = (TextView)view.findViewById(R.id.tvVendedorVentaDirectaCliente);
			TextView tvMonto = (TextView)view.findViewById(R.id.tvDisenioEdicionPreventaMonto);
			ImageView imgbtnDetalle = (ImageView)view.findViewById(R.id.imgbtnDisenioEdicionPreventaDetalles);
			ImageView imgbtnEliminar = (ImageView)view.findViewById(R.id.imgbtnDisenioEdicionPreventaEliminar);
			
			ClientePreventa cliente = null;
			try
			{
				cliente = new ClientePreventaBLL().ObtenerClientePreventaPor(_listPreventa.get(position).get_clienteId());
			}
			catch(Exception localException)
			{
				if(localException.getMessage().isEmpty())
		    	{
		            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clientePreventa: vacio");
		    	}
		    	else
		    	{
		    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el clientePreventa: " + localException.getMessage());
		    	} 
			}
			
			if(cliente == null)
			{
				return null;
			}
			
			if(_listPreventa.get(position).is_estado())
			{
				tvNombre.setText(cliente.get_nombreCompleto());
				tvMonto.setText(String.valueOf(new BigDecimal(_listPreventa.get(position).get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
			}
			else
			{
				tvNombre.setText(cliente.get_nombreCompleto());
				tvNombre.setTextAppearance(_context, R.style.Contenido);
				tvMonto.setText(String.valueOf(new BigDecimal(_listPreventa.get(position).get_montoFinal()).setScale(2,RoundingMode.HALF_UP)));
				tvMonto.setTextAppearance(_context, R.style.Contenido);
			}
			
			imgbtnDetalle.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					clienteId = _listPreventa.get(position).get_clienteId();
					preventaId = _listPreventa.get(position).get_Id();
					preventaIdServer = _listPreventa.get(position).get_preventaIdServer();
					factura = _listPreventa.get(position).get_factura();
					nit = _listPreventa.get(position).get_nit();
					tipoNit = _listPreventa.get(position).get_tipoNit();
					
					if(_listPreventa.get(position).is_aplicarBonificacion() == false)
					{
						if(preventaIdServer > 0)
						{
							if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
							{
								ObtenerPreventaBonificada();
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, para verficar si la preventa esta bonificada, intentelo mas tarde.", 1);
							}
						}
						else
						{
							MostrarPantallaEdicionProductos();
						}
						
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede editar la preventa, porque se encuentra bonificada.", 1);
						return;
					}
				}
			});
			
			imgbtnEliminar.setOnClickListener(new View.OnClickListener() 
			{	
				@Override
				public void onClick(View v)
				{
					clienteId = _listPreventa.get(position).get_clienteId();
					preventaId = _listPreventa.get(position).get_Id();
					preventaIdServer = _listPreventa.get(position).get_preventaIdServer();
					factura = _listPreventa.get(position).get_factura();
					nit = _listPreventa.get(position).get_nit();
					
					if(_listPreventa.get(position).is_estado())
					{
						BorrarPreventa(true,preventaIdServer);
					}
					else
					{
						BorrarPreventa(false,preventaId);
					}					
				}
			});

			return view;
		}
	}
	
	public void BorrarPreventa(final boolean server,final int preventaId)
	{
		dialogPreventa = new Dialog(Vendedoredicionpreventas.this);
		dialogPreventa.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogPreventa.setContentView(R.layout.diseniodialogo_vendedorcierredia);
		dialogPreventa.setTitle("Borrar Preventa");
		dialogPreventa.setCancelable(false);
		dialogPreventa.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
		
		TextView tvMensaje = (TextView)dialogPreventa.findViewById(R.id.tvDialogoMensaje);
		tvMensaje.setText("Esta seguro que desea eliminar definitivamente la preventa?");
		
		Button btnAceptar = (Button)dialogPreventa.findViewById(R.id.btnDialogoAceptar);
		btnAceptar.setOnClickListener(new OnClickListener() 
			{		
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
					case R.id.btnDialogoAceptar:
						if(dialogPreventa.isShowing())
						{
							dialogPreventa.dismiss();
						}
						
						if(server)
						{
							if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
							{
								DeletePreventaServer(preventaId);
							}
							else
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se encuentra conectado a internet, intentelo mas tarde.",1);
								return;
							}							
						}
						else
						{
							BorrarPreventaPorPreventaId(preventaId);
						}
						break;
					}	
				}
			});
		
		Button btnCancelar = (Button)dialogPreventa.findViewById(R.id.btnDialogoCancelar);
		btnCancelar.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
					case R.id.btnDialogoCancelar:
						if(dialogPreventa.isShowing())
						{
							dialogPreventa.dismiss();
						}
					}							
				}
			});
		
		dialogPreventa.show();
	}
	
	private void DeletePreventaServer(int preventaId)
	{
		pdDeletePreventa = new ProgressDialog(this);
		pdDeletePreventa.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdDeletePreventa.setMessage("Eliminando preventa ...");
		pdDeletePreventa.setCancelable(false);
		pdDeletePreventa.setCanceledOnTouchOutside(false);
		
		WSDeletePreventa localWSDeletePreventa = new WSDeletePreventa(preventaId);
		try
		{
			localWSDeletePreventa.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa WSDeletePreventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa WSDeletePreventa: " + localException.getMessage());
			}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar la preventa WSDeletePreventa", 1);
			return;
		}
	}
	
	private class WSDeletePreventa extends AsyncTask<Void, Integer, Boolean>
	{
		String DELETEPREVENTA_METHOD_NAME = "DeletePreVenta";
		String DELETEPREVENTA_SOAP_ACTION = NAMESPACE + DELETEPREVENTA_METHOD_NAME;

		private int _preventaIdServer;
		boolean WSDeletePreventa = false;
		SoapObject soapObjects;
		int resultadoInt;
		String resultadoString;
		
		public WSDeletePreventa(int preventaIdServer)
		{
			this._preventaIdServer = preventaIdServer;
		}
		
		protected void onPreExecute()
	    {
	    	pdDeletePreventa.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSDeletePreventa = false;

			SoapObject localSoapObject = new SoapObject(NAMESPACE,DELETEPREVENTA_METHOD_NAME);
			localSoapObject.addProperty("preVentaId", Integer.valueOf(_preventaIdServer));
			localSoapObject.addProperty("empleadoId", Integer.valueOf(loginEmpleado.get_empleadoId()));
    		
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
    			
			try
			{
				localHttpTransportSE.call(DELETEPREVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
					
				if(soapObjects != null)
				{
					resultadoInt = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					resultadoString = soapObjects.getPropertyAsString("Resultado"); 
				}
					
				if(resultadoInt >= 0 && resultadoString.equals("OK"))
				{
					WSDeletePreventa = true;
				}
				
				return true;
			}
			catch(Exception localException)
			{
				WSDeletePreventa = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSDeletePreventa: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdDeletePreventa.isShowing())
			{
				pdDeletePreventa.dismiss();
			}
    	
			if(ejecutado)
			{
				if(WSDeletePreventa) 
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"Preventa eliminada del servidor.", 1);
					ObtenerPreventaPorPreventaIdServer(_preventaIdServer);
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo eliminar la preventa del servidor, intentelo mas tarde.", 1);
				}
		
				MostrarPantallaMenuPrincipal();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSDeletePreventa no se ejecuto correctamente.", 1);
				return;
			}
		}
	}
	
	private void ObtenerPreventaPorPreventaIdServer(int preventaIdServer)
	{
		Preventa preventa = null;
		
		try
		{
			preventa = new PreventaBLL().ObtenerPreventaPorPreventaIdServer(preventaIdServer);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaIdServer: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por preventaIdServer: " + localException.getMessage());
			} 
		}
		
		if(preventa == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo obtener la preventa por preventaIdServer",1);
		}
		else
		{
			BorrarPreventaPorPreventaId(preventa.get_Id());
		}
	}
	
	private void BorrarPreventaPorPreventaId(int rowId)
	{		
		Preventa preventa = null;
		try
		{
			preventa = new PreventaBLL().OntenerPreventaPor(rowId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al abonar el saldo del rebate del cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al abonar el saldo del rebate del lciente: " + localException.getMessage());
			} 
		}
		
		if(preventa == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener la preventa por preventaId para ser eliminada", 1);
		}
		else
		{
			if(AbonarDraRebateSaldo(preventa.get_descuentoObjetivo()))
			{
				boolean eliminado = false;
				try
				{
					eliminado = new PreventaBLL().BorrarPreventasPorId(rowId);
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
				        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa por RowId: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa por RowId: " + localException.getMessage());
					} 
				}
				
				if(eliminado)
				{
					try
					{
						eliminado = new PreventaProductoBLL().BorrarPreventaProductoPorPreventaId(rowId);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa producto por preventaId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa producto por preventaId: " + localException.getMessage());
						} 
					}
					
					ModificarClientePreventaEstadoAtencion(false);
					theUtilidades.MostrarMensaje(getApplicationContext(), "Preventa eliminada del dispositivo.", 1);
					MostrarPantallaMenuPrincipal();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo eliminar la preventa del dispositivo.", 1);
				}

			}	
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo abonar el saldo del rebate del cliente, por lo que la preventa no fue eliminada del celular", 1);
			}
		}
	}
	
	private void ModificarClientePreventaEstadoAtencion(boolean estado)
	{
		long l = 0;
		try
		{
			l = new ClientePreventaBLL().ModificarClientePreventaEstadoAtencion(clienteId, estado);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa por RowId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar la preventa por RowId: " + localException.getMessage());
			} 
		}
		if(l==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el estado de atencion del cliente.", 1);
		}
	}
	
	private boolean AbonarDraRebateSaldo(float saldo)
	{
		long l=0;
		try
		{
			l = new DraRebateSaldoBLL().AbonarSaldoDraRebateSaldo(clienteId, saldo);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el saldo del rebate dracaris por clienteId: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar el saldo del rebate dracaris por clienteId: " + localException.getMessage());
			}
		}
		
		if(l > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void ObtenerPreventaBonificada()
	{
		pdEsperaObtenerPreventaBonificada = new ProgressDialog(this);
		pdEsperaObtenerPreventaBonificada.setProgressStyle(0);
		pdEsperaObtenerPreventaBonificada.setMessage("Verificando bonificacion  ...");
		pdEsperaObtenerPreventaBonificada.setCancelable(false);
		pdEsperaObtenerPreventaBonificada.setCanceledOnTouchOutside(false);
		  
		WSObtenerPreventaBonificada wsObtenerPreventaBonificada = new WSObtenerPreventaBonificada();
		try
		{
			wsObtenerPreventaBonificada.execute();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
		    {
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSPreVentaEstaBonificada: vacio");
		   	}
		   	else
		   	{
		   		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSPreVentaEstaBonificada: " + localException.getMessage());
		   	}
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSPreVentaEstaBonificada.", 1);
		}
	}
	
	private class WSObtenerPreventaBonificada extends AsyncTask<Void, Integer, Boolean>
	{
		String PREVENTABONIFICADA_METHOD_NAME = "PreVentaEstaBonificada";
		String PREVENTABONIFICADA_SOAP_ACTION = NAMESPACE + PREVENTABONIFICADA_METHOD_NAME;
		boolean WSPreventaBonificada = false; 
		SoapPrimitive soapObjects;
    
		protected void onPreExecute()
	    {
			pdEsperaObtenerPreventaBonificada.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSPreventaBonificada = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,PREVENTABONIFICADA_METHOD_NAME);
			localSoapObject.addProperty("preVentaId", Integer.valueOf(preventaIdServer));
						
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(this.PREVENTABONIFICADA_SOAP_ACTION, localSoapSerializationEnvelope);
				soapObjects = (SoapPrimitive)localSoapSerializationEnvelope.getResponse();
				if(soapObjects!=null)
				{
					WSPreventaBonificada = true;
				}

				return true;
			}
			catch (Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
		        	myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSPreVentaEstaBonificada: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSPreVentaEstaBonificada: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerPreventaBonificada.isShowing())
			{
				pdEsperaObtenerPreventaBonificada.dismiss();
			}
			
			if(ejecutado)
			{
				if (WSPreventaBonificada)
				{
					esBonificada = Boolean.parseBoolean((soapObjects.toString()));
					if(esBonificada)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se puede editar la preventa, porque se encuentra bonificada.", 1);
					}
					else
					{
						MostrarPantallaEdicionProductos();
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo saber si la preventa fue bonificada o no.", 2);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El WebService WSPreVentaEstaBonificada no se ejecuto correctamente. ", 1);
				return;
			}
		}
	}
		
	private void MostrarPantallaMenuPrincipal()
	{
		Intent localIntent = new Intent(this, Menuvendedor.class);
	    localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(localIntent);
	}

	private void MostrarPantallaEdicionProductos()
	{
		Intent localIntent = new Intent(this, Vendedoredicionpreventaproducto.class);
		localIntent.putExtra("preventaId", preventaId);
		localIntent.putExtra("preventaIdServer", preventaIdServer);
		localIntent.putExtra("clienteId", clienteId);
		localIntent.putExtra("factura", factura);
		localIntent.putExtra("nit", nit);
		localIntent.putExtra("tipoNit", tipoNit);
		startActivity(localIntent);
	}
}
