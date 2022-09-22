package com.detesim.venderunilever;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.MetaSapBLL;
import BLL.MyLogBLL;
import Clases.Fecha;
import Clases.LoginEmpleado;
import Clases.MetaSap;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Vendedormetassap extends Activity 
{
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
		
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();
	
	LoginEmpleado loginEmpleado;
	
	ArrayList<MetaSap> listadoMetaSap;
	
	LinearLayout llMetasSap;
	TextView tvUsuario;
	TextView tvCatVendedor;
	TextView tvNivVendedor;
	TextView tvTipo;
	TextView tvObjeto;
	TextView tvMonto;
	TextView tvCobertura;
	TextView tvMontoVenta;
	TextView tvMontoCobertura;
	TextView tvAvanceVenta;
	TextView tvAvanceCobertura;
	ListView lvMetasSap;
		
	ProgressDialog pdEsperaObtenerMetas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedormetassap);
		
		llMetasSap = (LinearLayout) findViewById(R.id.llVenMetSap);
		tvCatVendedor = (TextView) findViewById(R.id.tvVenMetSapCatVendedor);
		tvNivVendedor = (TextView) findViewById(R.id.tvVenMetSapNivVendedor);
		tvTipo = (TextView) findViewById(R.id.tvVenMetSapTipo);
		tvObjeto = (TextView) findViewById(R.id.tvVenMetSapObjeto);
		tvMonto = (TextView) findViewById(R.id.tvVenMetSapMonto);
		tvCobertura = (TextView) findViewById(R.id.tvVenMetSapCobVenta);
		tvMontoVenta = (TextView) findViewById(R.id.tvVenMetSapMontoVenta);
		tvMontoCobertura = (TextView) findViewById(R.id.tvVenMetSapCobertura);
		tvAvanceVenta = (TextView) findViewById(R.id.tvVenMetSapAvanceMonto);
		tvAvanceCobertura = (TextView) findViewById(R.id.tvVenMetSapAvanceCobertura);
		lvMetasSap = (ListView)findViewById(R.id.lvVenMetSap);
				
		llMetasSap.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	    }
	    else
	    {
	    	if(theUtilidades.VerificarConexionInternet(this))
	    	{
	    		MostrarControlesMetasSap(false);
	    		ObtenerMetasSap();
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las metas, no se encuentra conectado a internet, intentelo mas tarde.", 1);
	    	}
	    }
	}
	
	private void MostrarControlesMetasSap(boolean estado)
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
		
		tvCatVendedor.setVisibility(visibility);
		tvNivVendedor.setVisibility(visibility);
		tvTipo.setVisibility(visibility);
		tvObjeto.setVisibility(visibility);
		tvMonto.setVisibility(visibility);
		tvCobertura.setVisibility(visibility);
		tvMontoVenta.setVisibility(visibility);
		tvMontoCobertura.setVisibility(visibility);
		tvAvanceVenta.setVisibility(visibility);
		tvAvanceCobertura.setVisibility(visibility);
		lvMetasSap.setVisibility(visibility);
	}
	
	private void ObtenerMetasSap()
	{
		pdEsperaObtenerMetas = new ProgressDialog(this);
		pdEsperaObtenerMetas.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerMetas.setMessage("Obteniendo las metas SAP ...");
		pdEsperaObtenerMetas.setCancelable(false);
		pdEsperaObtenerMetas.setCanceledOnTouchOutside(false);
	    
		WSGetAvanceMetasSapByVendedor localWSGetAvanceMetasSapByVendedor = new WSGetAvanceMetasSapByVendedor();
	    
	    try
	    {
	    	localWSGetAvanceMetasSapByVendedor.execute();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvanceMetasSapByVendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvanceMetasSapByVendedor: " + localException.getMessage());
	    	}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvanceMetasSapByVendedor.", 1);
	    }
	}
	
	private class WSGetAvanceMetasSapByVendedor extends AsyncTask<Void, Integer, Boolean>
	{
		String METADETALLE_METHOD_NAME = "GetAvanceMetasSapByVendedor";
		String METADETALLE_SOAP_ACTION = NAMESPACE + METADETALLE_METHOD_NAME;
		boolean WSObtenerMeta = false;
		SoapObject soapMeta;
		int totalItems;
		Fecha fecha;
    
		protected void onPreExecute()
	    {
			fecha = theUtilidades.ObtenerFecha();
			pdEsperaObtenerMetas.show();
	    }
		
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerMeta = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,METADETALLE_METHOD_NAME);
			localSoapObject.addProperty("vendedorId", loginEmpleado.get_empleadoId());
			localSoapObject.addProperty("anio", fecha.get_anio());
			localSoapObject.addProperty("mes", fecha.get_mes());
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(METADETALLE_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapMeta = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetAvanceMetasSapByVendedorResult"));
				if(soapMeta != null)
				{
					totalItems = soapMeta.getPropertyCount();
				}
				
				if(totalItems > 0) 
				{
					WSObtenerMeta = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerMeta = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvanceMetasSapByVendedorResult: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvanceMetasSapByVendedorResult: " + localException.getMessage());
				} 
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerMetas.isShowing())
			{
				pdEsperaObtenerMetas.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSObtenerMeta)
				{
					if(BorrarMetasSap())
					{
						for(int i=0;i<totalItems;i++)
						{
							SoapObject soapObject = (SoapObject)soapMeta.getProperty(i);
							
							long l =0;
							try
							{
								l = new MetaSapBLL().InsertarMetaSap(
										soapObject.getPropertyAsString("CategoriaVendedor"),
										soapObject.getPropertyAsString("NivelVendedor"),
										soapObject.getPropertyAsString("Tipo"),
										soapObject.getPropertyAsString("Nivel"),
										soapObject.getPropertyAsString("Objeto"),
										Float.parseFloat(soapObject.getPropertyAsString("Porcentaje")),
										Float.parseFloat(soapObject.getPropertyAsString("Monto")),
										Integer.parseInt(soapObject.getPropertyAsString("Cobertura")),
										Float.parseFloat(soapObject.getPropertyAsString("MontoVenta")),
										Integer.parseInt(soapObject.getPropertyAsString("CoberturaVenta")),
										Float.parseFloat(soapObject.getPropertyAsString("AvanceMonto")),
										Float.parseFloat(soapObject.getPropertyAsString("AvanceCobertura")));
							}
							catch(Exception localException)
							{
								if(localException.getMessage() == null || localException.getMessage().isEmpty())
								{
							        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la meta sap: vacio");
								}
								else
								{
									myLog.InsertarLog("App",this.toString(),1,"Error al insertar la meta sap: " + localException.getMessage());
								} 
							}
							
							if (l <= 0)
							{
								theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar las metas SAP. ", 1);
								return;
			                }
						}
						
						MostrarMetasSap();
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las metas sap. ", 1);
			            return;
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron metas sap que descargar.", 1);
			        return;
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webService WSGetAvanceMetasSapByVendedorResult no se ejecuto correctamente. ", 1);
				return;
			}
		}
	}
	  
	public boolean BorrarMetasSap() 
	{
		try
		{
			boolean borrado = new MetaSapBLL().BorrarMetasSap();
			return borrado;
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al borrar las metas sap: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar las metas sap: " + localException.getMessage());
			}
			return false;
		}
	}
	
	private void MostrarMetasSap()
	{
		listadoMetaSap = null;
		
	    try
	    {
	    	listadoMetaSap = new MetaSapBLL().ObtenerMetasSap();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las metas sap: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las metas sap: " + localException.getMessage());
	    	} 
	    }
	    
	    if (listadoMetaSap == null)
        {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron las meta sap.", 1);
        }
	    else
	    {    	
	    	LlenarMetasSapListView();
	    }
	}
	
	private void LlenarMetasSapListView()
	{
	    MiAdaptadorMetasSap localMiAdaptadorMetasSap = new MiAdaptadorMetasSap(getApplicationContext());
	    	        
	    if(listadoMetaSap == null)
	    {
	    	lvMetasSap.setAdapter(null);
	    }
	    else
	    {
	    	MostrarControlesMetasSap(true);
		    ViewGroup.LayoutParams localLayoutParams = lvMetasSap.getLayoutParams();
		    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoMetaSap.size());
		    lvMetasSap.setLayoutParams(localLayoutParams);
		    lvMetasSap.setAdapter(localMiAdaptadorMetasSap);
	    }
	}

	class MiAdaptadorMetasSap extends ArrayAdapter<MetaSap>
	{
		private Context _context;
		
		public MiAdaptadorMetasSap(Context context)
		{
			super(context,R.layout.disenio_metasap,listadoMetaSap);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_metasap, parent, false);
			}
			
			MetaSap localMetaSap = (MetaSap)listadoMetaSap.get(position);
			
			TextView catVendedor = (TextView)localView.findViewById(R.id.tvDisMetSapCatVendedor);
			TextView nivVendedor = (TextView)localView.findViewById(R.id.tvDisMetSapNivVendedor);
			TextView tipo = (TextView)localView.findViewById(R.id.tvDisMetSapTipo);
			TextView objeto = (TextView)localView.findViewById(R.id.tvDisMetSapObjeto);
			TextView monto = (TextView)localView.findViewById(R.id.tvDisMetSapMonto);
			TextView cobertura = (TextView)localView.findViewById(R.id.tvDisMetSapCobertura);
			TextView montoVenta = (TextView)localView.findViewById(R.id.tvDisMetSapMontoVenta);
			TextView coberturaVenta = (TextView)localView.findViewById(R.id.tvDisMetSapCoberVenta);
			TextView avanceMonto = (TextView)localView.findViewById(R.id.tvDisMetSapAvanceMonto);
			TextView avanceCobertura = (TextView)localView.findViewById(R.id.tvDisMetSapAvanceCober);			
      
			catVendedor.setText(localMetaSap.get_categoriaVendedor());
			nivVendedor.setText(localMetaSap.get_nivelVendedor());
			tipo.setText(String.valueOf(localMetaSap.get_tipo()));
			objeto.setText(String.valueOf(localMetaSap.get_objeto()));
			monto.setText(String.valueOf(localMetaSap.get_monto()));
			cobertura.setText(String.valueOf(localMetaSap.get_cobertura()));
			montoVenta.setText(String.valueOf(localMetaSap.get_montoVenta()));
			coberturaVenta.setText(String.valueOf(localMetaSap.get_coberturaVenta()));
			avanceMonto.setText(String.valueOf(localMetaSap.get_avanceMonto()));
			avanceCobertura.setText(String.valueOf(localMetaSap.get_avanceCobertura()));
			
			return localView;
		}
	}	
}
