package com.detesim.venderunilever;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.AvanceVentaBLL;
import BLL.ClienteNoAtendidoBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import Clases.AES;
import Clases.AvanceVendedorDiaWSResult;
import Clases.AvanceVenta;
import Clases.ClienteNoAtendido;
import Clases.ClientePreventa;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.Preventa;
import Clases.VendedorFechaWS;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Vendedoravanceventasdia extends Activity implements OnClickListener
{
	LinearLayout llVendedorAvanceVentasDia;
	Utilidades theUtilidades = new Utilidades();	
	MyLogBLL myLog = new MyLogBLL();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<AvanceVenta> listadoAvanceVentaDia;
	
	TextView tvProveedor;
	TextView tvPresupuesto;
	TextView tvAvance;
	TextView tvNoPreventas;
	TextView tvEficiencia;
	ListView lvAvances;
	Button btnAvancesDia;
	ProgressDialog pdEsperaObtenerAvanceVentasDiaByVendedor;
	
	VentasDia ventasdia;
	
	TextView tvVenta;
	TextView tvPreventa;
	TextView tvTotales;
	TextView tvVentaTotalBs;
	TextView tvPreventaTotalBs;	
	
	LinearLayout llVentaProgramada;
	TextView tvVentaProgramada;
	LinearLayout llVentaEfectivos;
	TextView tvVentaEfectivos;
	LinearLayout llVentaNoEfectivos;
	TextView tvVentaNoEfectivos;
	LinearLayout llVentaVisitados;
	TextView tvVentaVisitados;
	LinearLayout llVentaEfectividadVisita;
	TextView tvVentaEfectividadVisita;
	LinearLayout llVentaNoVisitados;
	TextView tvVentaNoVisitados;
	LinearLayout llVentaEfectividadRuta;
	TextView tvVentaEfectividadRuta;
	
	TextView tvProgramados;
	TextView tvEfectivos;
	TextView tvNoEfectivos;
	TextView tvVisitados;
	TextView tvEfectividadVisita;
	TextView tvNoVisitados;
	TextView tvEfectividadRuta;
	
	LinearLayout llPreventaProgramada;
	TextView tvPreventaProgramada;
	LinearLayout llPreventaEfectivos;
	TextView tvPreventaEfectivos;
	LinearLayout llPreventaNoEfectivos;
	TextView tvPreventaNoEfectivos;
	LinearLayout llPreventaVisitados;
	TextView tvPreventaVisitados;
	LinearLayout llPreventaEfectividadVisita;
	TextView tvPreventaEfectividadVisita;
	LinearLayout llPreventaNoVisitados;
	TextView tvPreventaNoVisitados;
	LinearLayout llPreventaEfectividadRuta;
	TextView tvPreventaEfectividadRuta;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoravanceventasdia);
		
		llVendedorAvanceVentasDia = (LinearLayout)findViewById(R.id.VendedorAvanceVentasDiaLinearLayout);
		tvProveedor = (TextView)findViewById(R.id.tvVendedorAvancesVentaDiaProveedor);
		tvPresupuesto = (TextView)findViewById(R.id.tvVendedorAvancesVentaDiaPresupuesto);
		tvAvance = (TextView)findViewById(R.id.tvVendedorAvancesVentaDiaAvance);
		tvNoPreventas = (TextView)findViewById(R.id.tvVendedorAvancesVentaDiaNoPreventas);
		tvEficiencia = (TextView)findViewById(R.id.tvVendedorAvancesVentaDiaEficiencia);
		lvAvances = (ListView)findViewById(R.id.lvVendedorAvancesVentaDiaAvances);
		btnAvancesDia = (Button)findViewById(R.id.btnVendedorAvancesVentaDiaActualizarDatos);
		btnAvancesDia.setOnClickListener(this);
		
		llVentaProgramada = (LinearLayout)findViewById(R.id.llAvaVenVentaProgramados);
		tvVentaProgramada = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaProgramada);
		tvProgramados = (TextView)findViewById(R.id.tvVenAvaVenDiaProgramados);
		llPreventaProgramada = (LinearLayout)findViewById(R.id.llAvaVenPreVentaProgramados);
		tvPreventaProgramada = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaProgramada);		
		
		llVentaEfectivos = (LinearLayout)findViewById(R.id.llAvaVenVentaRealizado);
		tvVentaEfectivos = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaEfectivos);
		tvEfectivos = (TextView)findViewById(R.id.tvVenAvaVenDiaEfectivos);
		llPreventaEfectivos = (LinearLayout)findViewById(R.id.llAvaVenPreVentaRealizado);
		tvPreventaEfectivos = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaEfectivos);
		
		llVentaNoEfectivos = (LinearLayout)findViewById(R.id.llAvaVenVentaNoEfectivos);
		tvVentaNoEfectivos = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaNoEfectivos);
		tvNoEfectivos = (TextView)findViewById(R.id.tvVenAvaVenDiaNoEfectivos);
		llPreventaNoEfectivos = (LinearLayout)findViewById(R.id.llAvaVenPreVentaNoEfectivos);
		tvPreventaNoEfectivos = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaNoEfectivos);
		
		llVentaVisitados = (LinearLayout)findViewById(R.id.llAvaVenVentaVisitados);
		tvVentaVisitados = (TextView)findViewById(R.id.tvVenAvaVenDiaVisitados);
		tvVisitados = (TextView)findViewById(R.id.tvVenAvaVenDiaVisitados);
		llPreventaVisitados = (LinearLayout)findViewById(R.id.llAvaVenPreVentaVisitados);
		tvPreventaVisitados = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaVisitados);
		
		llVentaEfectividadVisita = (LinearLayout)findViewById(R.id.llAvaVenVentaPorcentaje);
		tvVentaEfectividadVisita = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaEfectividadVisita);
		tvEfectividadVisita = (TextView)findViewById(R.id.tvVenAvaVenDiaEfectividadVisita);
		llPreventaEfectividadVisita = (LinearLayout)findViewById(R.id.llAvaVenPreVentaPorcentaje);
		tvPreventaEfectividadVisita = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaEfectividadVisita);
		
		llVentaNoVisitados = (LinearLayout)findViewById(R.id.llAvaVenVentaNoVisitados);
		tvVentaNoVisitados = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaNoVisitados);
		tvNoVisitados = (TextView)findViewById(R.id.tvVenAvaVenDiaNoVisitados);
		llPreventaNoVisitados = (LinearLayout)findViewById(R.id.llAvaVenPreVentaNoVisitados);
		tvPreventaNoVisitados = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaNoVisitados);
		
		llVentaEfectividadRuta = (LinearLayout)findViewById(R.id.llAvaVenVentaPorcentajeRuta);
		tvVentaEfectividadRuta = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaEfectividadRuta);
		tvEfectividadRuta = (TextView)findViewById(R.id.tvVenAvaVenDiaEfectividadRuta);
		llPreventaEfectividadRuta = (LinearLayout)findViewById(R.id.llAvaVenPreVentaPorcentajeRuta);
		tvPreventaEfectividadRuta = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaEfectividadRuta);
		
		tvVenta = (TextView)findViewById(R.id.tvVenAvaVenDiaVenta);
		tvPreventa = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventa);
		tvTotales = (TextView)findViewById(R.id.tvVenAvaVenDiaTotales);
		tvVentaTotalBs = (TextView)findViewById(R.id.tvVenAvaVenDiaVentaTotalBs);
		tvPreventaTotalBs = (TextView)findViewById(R.id.tvVenAvaVenDiaPreventaTotalBs);
		
		llVendedorAvanceVentasDia.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		MostrarControlesVenta(false);
		loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el loginEmpleado: " + localException.getMessage());
	    	} 
	      
	    }
	    
    	if(loginEmpleado == null)
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
    		MostrarControles(false);
    		return;
    	}
    	else
    	{
    		MostrarControles(true);
    		ObtenerAvancesVentaDiaForDisplay();
    		MostrarEstadisticos();
    	}
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnVendedorAvancesVentaDiaActualizarDatos:
			ObtenerAvancesDiaByVendedor();
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
		
		tvProveedor.setVisibility(visibility);
		tvPresupuesto.setVisibility(visibility);
		tvNoPreventas.setVisibility(visibility);
		tvAvance.setVisibility(visibility);
		tvEficiencia.setVisibility(visibility);
		lvAvances.setVisibility(visibility);
	}
	
	private void ObtenerAvancesVentaDiaForDisplay()
	{
		listadoAvanceVentaDia= null;
	    try
	    {
	    	listadoAvanceVentaDia = new AvanceVentaBLL().ObtenerAvanceVentaPorTipoAvanceVentaYRol("DIA","Vendedor");
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta vendedor por dia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta vendedor por dia: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoAvanceVentaDia == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen avances de venta en el dia.", 1);
	        return;
	    }
	    else
	    {
	    	LlenarListViewAvancesVenta();
	    }
	}
	
	private void LlenarListViewAvancesVenta()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvVendedorAvancesVentaDiaAvances);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoAvanceVentaDia.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<AvanceVenta>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_supervisoravancevendedordia,listadoAvanceVentaDia);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_supervisoravancevendedordia,parent,false);
			}
			
			AvanceVenta localAvanceVenta = (AvanceVenta)listadoAvanceVentaDia.get(position);
			
			TextView tvVendedor = (TextView)localView.findViewById(R.id.tvVendedorAvanceVentasCatgoriaTitulo);
			TextView tvProveedor = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasProveedor);
			TextView tvPresupuesto = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPresupuesto);
			TextView tvNoPreventas = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPreventas);
			TextView tvAvance = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasAvance);
			TextView tvEficiencia = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasEficiencia);
			
			tvVendedor.setText(localAvanceVenta.get_nombreProveedor());
			tvProveedor.setText("");
			tvPresupuesto.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_presupuesto()).setScale(2,RoundingMode.HALF_UP)));
			tvNoPreventas.setText(String.valueOf(localAvanceVenta.get_noPreventas()));
			tvAvance.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_avance()).setScale(2, RoundingMode.HALF_UP)));
			tvEficiencia.setText(String.valueOf(new BigDecimal((localAvanceVenta.get_avance()/localAvanceVenta.get_presupuesto())*100).setScale(2,RoundingMode.HALF_UP)));			
			
			return localView;
		}
	}

	private void ObtenerAvancesDiaByVendedor()
	{
		pdEsperaObtenerAvanceVentasDiaByVendedor = new ProgressDialog(this);
		pdEsperaObtenerAvanceVentasDiaByVendedor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerAvanceVentasDiaByVendedor.setMessage("Obteniendo avance ventas por dia ...");
		pdEsperaObtenerAvanceVentasDiaByVendedor.setCancelable(false);
		pdEsperaObtenerAvanceVentasDiaByVendedor.setCanceledOnTouchOutside(false);
	    
	    WSGetAvancesByVendedorDia localWSGetAvancesByVendedorDia = new WSGetAvancesByVendedorDia();
	    
	    try
	    {
	    	localWSGetAvancesByVendedorDia.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesByVendedorDia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesByVendedorDia: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesByVendedorDia.", 1);
	    }
	}
	
	private class WSGetAvancesByVendedorDia  extends AsyncTask<Void, Integer, Boolean>
	{
		String AVANCESDIA_METHOD_NAME = "GetAvanceByVendedorDia";
		String AVANCESDIA_SOAP_ACTION = NAMESPACE + AVANCESDIA_METHOD_NAME;

		boolean WSObtenerAvancesDia;
		ArrayList<AvanceVendedorDiaWSResult> avanceVendedorDiaWSResults;
		String error;
	
	 protected void onPreExecute()
    {
		pdEsperaObtenerAvanceVentasDiaByVendedor.show();
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
		        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesByVendedorDia: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesByVendedorDia: " + localException.getMessage());
			}
			return true;
		}
	}

	 protected void onPostExecute(Boolean ejecutado)
	 {
		 if(pdEsperaObtenerAvanceVentasDiaByVendedor.isShowing())
		 {
			 pdEsperaObtenerAvanceVentasDiaByVendedor.dismiss();
		 }
		
		 if(!ejecutado)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesByVendedorDia no se ejecuto correctamente. ", 1);
			 return;
		 }

		 if(!WSObtenerAvancesDia)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de las ventas del vendedor por dia que descargar.", 1);
			 ObtenerAvancesVentaDiaByVendedor();
			 return;
		 }

		 if(!BorrarAvancesVentaDia())
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo borrar los avances de las ventas del dia por vendedor.", 1);
			 return;
		 }

		 long i = 0;
		 try
		 {
			 i = new AvanceVentaBLL().InsertarAvanceVenta( avanceVendedorDiaWSResults, 0);
		 }
		 catch (Exception localException)
		 {
			 if (localException.getMessage() == null || localException.getMessage().isEmpty())
			 {
				 myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances del vendedor dia: vacio");
			 }
			 else
			 {
				 myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances del vendedor dia: " + localException.getMessage());
			 }
		 }
		 if (i == 0)
		 {
			 theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener avances del vendedor dia.", 1);
			 return;
		 }
				 ObtenerAvancesVentaDiaByVendedor();

		 }
	 }

	private boolean BorrarAvancesVentaDia()
	{	        
		try
		{
			return new AvanceVentaBLL().BorrarAvancesVentaPorTipoAvanceVentaYRol("DIA","Vendedor");
        }
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por dia del vendedor: vacio");
        	}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por dia del vendedor: " + localException.getMessage());
        	}
			return false;
        }
	}
	
	private void ObtenerAvancesVentaDiaByVendedor()
	{
		pdEsperaObtenerAvanceVentasDiaByVendedor = new ProgressDialog(this);
		pdEsperaObtenerAvanceVentasDiaByVendedor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerAvanceVentasDiaByVendedor.setMessage("Obteniendo avance ventas por dia ...");
		pdEsperaObtenerAvanceVentasDiaByVendedor.setCancelable(false);
		pdEsperaObtenerAvanceVentasDiaByVendedor.setCanceledOnTouchOutside(false);
	    
		WSGetApkNumeroVentasVendedor localWSGetApkNumeroVentasVendedor = new WSGetApkNumeroVentasVendedor();
	    
	    try
	    {
	    	localWSGetApkNumeroVentasVendedor.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkNumeroVentasVendedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkNumeroVentasVendedor: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetApkNumeroVentasVendedor.", 1);
		    return;
	    }    
	}
	
	private class WSGetApkNumeroVentasVendedor  extends AsyncTask<Void, Integer, Boolean>
	{
		String VENTASDIA_METHOD_NAME = "GetApkNumeroVentasVendedor";
		String VENTASDIA_SOAP_ACTION = NAMESPACE + VENTASDIA_METHOD_NAME;
		boolean WSObtenerVentasDia;
		SoapObject soapVentasDia;
		int totalItems;
	
		protected void onPreExecute()
    {
		pdEsperaObtenerAvanceVentasDiaByVendedor.show();
    }

		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerVentasDia = false;
  
			SoapObject localSoapObject = new SoapObject(NAMESPACE,VENTASDIA_METHOD_NAME);
			localSoapObject.addProperty("vendedorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("dia", Integer.valueOf(loginEmpleado.get_dia()));
					
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
		
			try
			{
				localHttpTransportSE.call(VENTASDIA_SOAP_ACTION, localSoapSerializationEnvelope);
			
				soapVentasDia = ((SoapObject)localSoapSerializationEnvelope.getResponse());
			
				if(soapVentasDia!=null)
				{
					totalItems = soapVentasDia.getPropertyCount();
				}
			
				if(totalItems > 0) 
				{
					WSObtenerVentasDia = true;
				}

				return true;
			}
			catch (Exception localException)
			{
				WSObtenerVentasDia = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetApkNumeroVentasVendedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetApkNumeroVentasVendedor: " + localException.getMessage());
				}
				return true;
			}
		}

		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerAvanceVentasDiaByVendedor.isShowing())
			{
				pdEsperaObtenerAvanceVentasDiaByVendedor.dismiss();
			}
		
			if(ejecutado)
			{
				if(WSObtenerVentasDia)
				{
					ventasdia = new VentasDia(Integer.parseInt(soapVentasDia.getPropertyAsString("Programadas")),
											Integer.parseInt(soapVentasDia.getPropertyAsString("Efectivas")));
					ObtenerAvancesVentaDiaForDisplay();
					MostrarVentasDia();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de las ventas del vendedor por dia que descargar.", 1);
					ObtenerAvancesVentaDiaForDisplay();
					MostrarVentasDia();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesByVendedorDia no se ejecuto correctamente. ", 1);
			} 
		}
	}
	
	private class VentasDia
	{
		private int _programadas;
		private int _efectivas;
		
		public VentasDia(int programadas,int efectivas)
		{
			this._programadas = programadas;
			this._efectivas = efectivas;
		}

		public int get_programadas() {
			return _programadas;
		}

		public int get_efectivas() {
			return _efectivas;
		}
	}

	private void MostrarVentasDia()
	{
		MostrarControlesVenta(true);
		
		if(ventasdia != null && ventasdia.get_programadas() > 0)
		{
			tvVentaProgramada.setText(String.valueOf(ventasdia.get_programadas()));
			if(ventasdia.get_efectivas()>0)
			{
				tvVentaEfectivos.setText(String.valueOf(ventasdia.get_efectivas()));
				tvVentaEfectividadRuta.setText(String.valueOf(new BigDecimal(((float)ventasdia.get_efectivas()/(float)ventasdia.get_programadas())*100).setScale(2,RoundingMode.HALF_UP)) + " %");
			}
			else
			{
				tvVentaEfectivos.setText("0");
				tvVentaEfectividadRuta.setText("0.00 %");
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron ventas en el dia", 1);
			tvVentaProgramada.setText("0");
			tvVentaEfectivos.setText("0");
			tvVentaEfectividadRuta.setText("0.00 %");
		}
	}
	
	private void MostrarEstadisticos()
	{
		int nroProgramados = 0;
		int nroVisitados = 0;
		int nroEfectivos = 0;
		int nroNoAtendidos = 0;
		
		ArrayList<ClientePreventa> listadoClientePreventa = null;
		try
		{
			listadoClientePreventa = new ClientePreventaBLL().ObtenerClientesPreventa();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes: " + localException.getMessage());
			}
		}
		
		if(listadoClientePreventa != null && listadoClientePreventa.size()>0)
		{
			nroProgramados = listadoClientePreventa.size();
			tvPreventaProgramada.setText(String.valueOf(nroProgramados));
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes programados.", 1);
			return;
		}
		
		try
		{
			nroEfectivos = new PreventaBLL().obtenerNroPreventas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes con preventa: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de clientes con preventa: " + localException.getMessage());
			}
		}
		
		if(nroEfectivos > 0)
		{
			tvPreventaEfectivos.setText(String.valueOf(nroEfectivos));
			
			ArrayList<ClienteNoAtendido> listadoClienteNoAtendido = null;
			try
			{
				listadoClienteNoAtendido = new ClienteNoAtendidoBLL().ObtenerClientesNoAtendidos();
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no atendidos: " + localException.getMessage());
				}
			}
			
			if(listadoClienteNoAtendido != null && listadoClienteNoAtendido.size() > 0)
			{
				nroNoAtendidos = listadoClienteNoAtendido.size();
				tvPreventaNoEfectivos.setText(String.valueOf(nroNoAtendidos));
				nroVisitados = nroEfectivos + nroNoAtendidos;
				tvPreventaVisitados.setText(String.valueOf(nroVisitados));
				tvPreventaNoVisitados.setText(String.valueOf(nroProgramados-nroVisitados));
				float efVisita = ((float)nroVisitados/(float)nroProgramados)*100;
				tvPreventaEfectividadVisita.setText(String.valueOf(new BigDecimal(efVisita).setScale(2,RoundingMode.HALF_UP)) + " %");
				float efRuta = ((float)nroEfectivos/(float)nroProgramados)*100;
				tvPreventaEfectividadRuta.setText(String.valueOf(new BigDecimal(efRuta).setScale(2,RoundingMode.HALF_UP)) + " %");
								
				ArrayList<Preventa> listadoPreventa = null;
				try
				{
					listadoPreventa = new PreventaBLL().ObtenerPreventas();
				}
				catch(Exception localException)
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
				
				if(listadoPreventa != null && listadoPreventa.size() > 0)
				{
					float montoFinal = 0;
					for(Preventa item : listadoPreventa)
					{
						montoFinal += item.get_montoFinal();
					}
					tvPreventaTotalBs.setText(String.valueOf(new BigDecimal(montoFinal).setScale(2,RoundingMode.HALF_UP)) + " Bs.");
				}
			}
			else
			{
				nroNoAtendidos = 0;
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron pre ventas en el dia", 1);
		}
	}
	
	private void MostrarControlesVenta(boolean estado)
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
		
		tvVenta.setVisibility(visibility);
		tvPreventa.setVisibility(visibility);
		tvTotales.setVisibility(visibility);
		tvVentaTotalBs.setVisibility(visibility);
		tvPreventaTotalBs.setVisibility(visibility);
		
		llVentaProgramada.setVisibility(visibility);
		tvVentaProgramada.setVisibility(visibility);
		tvProgramados.setVisibility(visibility);
		llPreventaProgramada.setVisibility(visibility);
		tvPreventaProgramada.setVisibility(visibility);		
		
		llVentaEfectivos.setVisibility(visibility);
		tvVentaEfectivos.setVisibility(visibility);
		tvEfectivos.setVisibility(visibility);
		llPreventaEfectivos.setVisibility(visibility);
		tvPreventaEfectivos.setVisibility(visibility);
		
		llVentaNoEfectivos.setVisibility(visibility);
		tvVentaNoEfectivos.setVisibility(visibility);
		tvNoEfectivos.setVisibility(visibility);
		llPreventaNoEfectivos.setVisibility(visibility);
		tvPreventaNoEfectivos.setVisibility(visibility);
		
		llVentaVisitados.setVisibility(visibility);
		tvVentaVisitados.setVisibility(visibility);
		tvVisitados.setVisibility(visibility);
		llPreventaVisitados.setVisibility(visibility);
		tvPreventaVisitados.setVisibility(visibility);
		
		llVentaEfectividadVisita.setVisibility(visibility);
		tvVentaEfectividadVisita.setVisibility(visibility);
		tvEfectividadVisita.setVisibility(visibility);
		llPreventaEfectividadVisita.setVisibility(visibility);
		tvPreventaEfectividadVisita.setVisibility(visibility);
		
		llVentaNoVisitados.setVisibility(visibility);
		tvVentaNoVisitados.setVisibility(visibility);
		tvNoVisitados.setVisibility(visibility);
		llPreventaNoVisitados.setVisibility(visibility);
		tvPreventaNoVisitados.setVisibility(visibility);
		
		llVentaEfectividadRuta.setVisibility(visibility);
		tvVentaEfectividadRuta.setVisibility(visibility);
		tvEfectividadRuta.setVisibility(visibility);
		llPreventaEfectividadRuta.setVisibility(visibility);
		tvPreventaEfectividadRuta.setVisibility(visibility);
	}
}
