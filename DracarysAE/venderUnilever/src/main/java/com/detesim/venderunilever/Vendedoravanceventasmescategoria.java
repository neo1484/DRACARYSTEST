package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.AvanceVentaVendedorBLL;
import BLL.MyLogBLL;
import BLL.ProveedorBLL;
import Clases.AvanceVentaVendedor;
import Clases.LoginEmpleado;
import Clases.Proveedor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Vendedoravanceventasmescategoria extends Activity 
{
	LinearLayout llAvanceVentasMesVendedorCategoria;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = this.theUtilidades.get_URL();	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<AvanceVentaVendedor> listadoAvanceVentaVendedorMes;
	ArrayList<AvanceVentaVendedor> listadoAvanceVentaVendedorMesCategoria;
	
	TextView tvProveedor;
	TextView tvPresupuesto;
	TextView tvAvance;
	TextView tvTendencia;
	TextView tvCobertura;
	ListView lvAvances;
	
	TextView tvCategoria;
	TextView tvPresupuestoCat;
	TextView tvAvanceCat;
	TextView tvTendenciaCat;
	TextView tvCoberturaCat;
	ListView lvAvancesCat;
	
	Spinner spnProveedor;
	ProgressDialog pdEsperaObtenerAvanceVentasrMesCategoria;
	int proveedorId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoravanceventasmescategoria);
		
		llAvanceVentasMesVendedorCategoria  = (LinearLayout)findViewById(R.id.llAvanceVentasMesVendedor);
		
		tvProveedor = (TextView)findViewById(R.id.tvAvanceVentaMesVendedorProveedor);
		tvPresupuesto = (TextView)findViewById(R.id.tvAvancesVentaMesVendedorPresupuesto);
		tvAvance = (TextView)findViewById(R.id.tvAvancesVentaMesVendedorAvance);
		tvTendencia = (TextView)findViewById(R.id.tvAvancesVentaMesVenedorTendencia);
		tvCobertura = (TextView)findViewById(R.id.tvVendedorAvancesVentaMesCobertura);
		lvAvances = (ListView)findViewById(R.id.lvAvancesVentaMesVendedorProveedores);
		
		tvCategoria = (TextView)findViewById(R.id.tvAvancesVentaMesVendedorCategoria);
		tvPresupuestoCat = (TextView)findViewById(R.id.tvAvancesVentaMesVendedorPresupuestoCat);
		tvAvanceCat = (TextView)findViewById(R.id.tvAvancesVentaMesVendedorAvanceCat);
		tvTendenciaCat = (TextView)findViewById(R.id.tvAvancesVentaMesVenedorTendenciaCat);
		tvCoberturaCat = (TextView)findViewById(R.id.tvAvancesVentaMesVendedorCoberturaCat);
		lvAvancesCat = (ListView)findViewById(R.id.lvAvancesVentaMesVendedorCategorias);
		
		spnProveedor = (Spinner)findViewById(R.id.spnAvancesVentaMesVendedorProveedor);
		
		llAvanceVentasMesVendedorCategoria.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
    		CargarProveedores();
    		ObtenerAvancesVentaMesForDisplay();
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
		
		tvCategoria.setVisibility(visibility);
		tvPresupuestoCat.setVisibility(visibility);
		tvAvanceCat.setVisibility(visibility);
		tvTendenciaCat.setVisibility(visibility);
		tvCoberturaCat.setVisibility(visibility);
	}
	
	private void ObtenerAvancesVentaMesForDisplay()
	{
		listadoAvanceVentaVendedorMes = null;
	    try
	    {
	    	listadoAvanceVentaVendedorMes = new AvanceVentaVendedorBLL().ObtenerAvancesVentaVendedor();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVentaVendedor por mes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVentaVendedor por mes: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoAvanceVentaVendedorMes == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen avances de venta del vendedor en el mes.", 1);
	        return;
	    }
	    else
	    {
	    	LlenarListViewAvancesVentaVendedor();
	    }
	}
	
	private void LlenarListViewAvancesVentaVendedor()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvAvancesVentaMesVendedorProveedores);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoAvanceVentaVendedorMes.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<AvanceVentaVendedor>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_supervisoravancevendedordia,listadoAvanceVentaVendedorMes);
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
			
			AvanceVentaVendedor localAvanceVentaVendedor = (AvanceVentaVendedor)listadoAvanceVentaVendedorMes.get(position);
			
			TextView tvVendedor = (TextView)localView.findViewById(R.id.tvVendedorAvanceVentasCatgoriaTitulo);
			TextView tvProveedor = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasProveedor);
			TextView tvPresupuesto = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPresupuesto);
			TextView tvAvance = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPreventas);
			TextView tvTendencia = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasAvance);
			TextView tvCobertura = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasEficiencia);
			
			tvVendedor.setText(localAvanceVentaVendedor.get_objeto());
			tvProveedor.setText("");
			tvPresupuesto.setText(String.valueOf(new BigDecimal(localAvanceVentaVendedor.get_presupuesto()).setScale(2,RoundingMode.HALF_UP)));
			tvAvance.setText(String.valueOf(new BigDecimal(localAvanceVentaVendedor.get_avance()).setScale(2,RoundingMode.HALF_UP)));
			tvTendencia.setText(String.valueOf(new BigDecimal(localAvanceVentaVendedor.get_tendencia()).setScale(2,RoundingMode.HALF_UP)));
			tvCobertura.setText(String.valueOf(new BigDecimal(localAvanceVentaVendedor.get_coberturaPorcentaje()).setScale(2,RoundingMode.HALF_UP)));			
			
			return localView;
		}
	}
	
	public void CargarProveedores()
	{
		ArrayList<Proveedor> listadoProvedor = null;
		
	    try
	    {
	    	listadoProvedor = new ProveedorBLL().ObtenerProveedores();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los proveedores: " + localException.getMessage());
	    	} 
	    }
	    
	    if (listadoProvedor == null)
        {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cargar los proveedores.", 1);
	    	return;
        }
	    
	    ArrayAdapter<Proveedor> localArrayAdapter = new ArrayAdapter<Proveedor>(this,R.layout.disenio_spinner,listadoProvedor);
	    spnProveedor.setAdapter(localArrayAdapter);

	    spnProveedor.setOnItemSelectedListener(new OnItemSelectedListener() 
	    {	    	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
			{
				Proveedor proveedor = (Proveedor)spnProveedor.getSelectedItem();
				proveedorId = proveedor.get_proveedorId();
				
				if(theUtilidades.VerificarConexionInternet(getApplicationContext()))
				{
					ObtenerAvancesVentaVendedorMes();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No esta conectado a internet, intentelo mas tarde.", 1);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}
	    	
		});
	    
	}
	
	private void ObtenerAvancesVentaVendedorMes()
	{
		pdEsperaObtenerAvanceVentasrMesCategoria = new ProgressDialog(this);
		pdEsperaObtenerAvanceVentasrMesCategoria.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperaObtenerAvanceVentasrMesCategoria.setMessage("Obteniendo avances ventas por categoria ...");
		pdEsperaObtenerAvanceVentasrMesCategoria.setCancelable(false);
		pdEsperaObtenerAvanceVentasrMesCategoria.setCanceledOnTouchOutside(false);
	    
		WSGetAvanceByVendedorAndMesAndCategoriasProveedor localWSGetAvanceByVendedorAndMesAndCategoriasProveedor = new WSGetAvanceByVendedorAndMesAndCategoriasProveedor();
	    
	    try
	    {
	    	localWSGetAvanceByVendedorAndMesAndCategoriasProveedor.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvanceByVendedorAndMesAndCategoriasProveedor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvanceByVendedorAndMesAndCategoriasProveedor: " + localException.getMessage());
	    	} 	
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvanceByVendedorAndMesAndCategoriasProveedor.", 1);
		    return;
	    }    
	}
	
	private class WSGetAvanceByVendedorAndMesAndCategoriasProveedor extends AsyncTask<Void, Integer, Boolean>
	{
		String AVANCESVENTAMESCATEGORIA_METHOD_NAME = "GetAvanceByVendedorAndMesAndCategoriasProveedor";
		String AVANCESVENTAMESCATEGORIA_SOAP_ACTION = NAMESPACE + AVANCESVENTAMESCATEGORIA_METHOD_NAME;
		boolean WSObtenerAvanceVentaMesCategoria;
		SoapObject soapAvancesVentaMesCategoria;
		int totalItems;
		
		protected void onPreExecute()
	    {
			pdEsperaObtenerAvanceVentasrMesCategoria.show();
	    }
    
		protected Boolean doInBackground(Void... paramVarArgs)
		{
			WSObtenerAvanceVentaMesCategoria = false;
      
			SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESVENTAMESCATEGORIA_METHOD_NAME);
			localSoapObject.addProperty("vendedorId", Integer.valueOf(loginEmpleado.get_empleadoId()));
			localSoapObject.addProperty("anio", Integer.valueOf(loginEmpleado.get_anio()));
			localSoapObject.addProperty("mes", Integer.valueOf(loginEmpleado.get_mes()));
			localSoapObject.addProperty("proveedorId", Integer.valueOf(proveedorId));
						
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			
			try
			{
				localHttpTransportSE.call(AVANCESVENTAMESCATEGORIA_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapAvancesVentaMesCategoria = ((SoapObject)((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("GetAvanceByVendedorAndMesAndCategoriasProveedorResult"));
				
				if(soapAvancesVentaMesCategoria!=null)
				{
					totalItems = soapAvancesVentaMesCategoria.getPropertyCount();
				}
				
				if(totalItems > 0) 
				{
					WSObtenerAvanceVentaMesCategoria = true;
				}
				
				return true;
			}
			catch (Exception localException)
			{
				WSObtenerAvanceVentaMesCategoria = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error en el webservice GetAvanceByVendedorAndMesAndCategoriasProveedor: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error en el webservice GetAvanceByVendedorAndMesAndCategoriasProveedor: " + localException.getMessage());
				}
				return true;
			}
		}
    
		protected void onPostExecute(Boolean ejecutado)
		{
			if(pdEsperaObtenerAvanceVentasrMesCategoria.isShowing())
			{
				pdEsperaObtenerAvanceVentasrMesCategoria.dismiss();
			}
			
			if(ejecutado)
			{
				if(WSObtenerAvanceVentaMesCategoria)
				{
					listadoAvanceVentaVendedorMesCategoria = new ArrayList<AvanceVentaVendedor>();
					
					for(int i=0;i<totalItems;i++)
					{
						SoapObject soapObject = (SoapObject)this.soapAvancesVentaMesCategoria.getProperty(i);
						
						try
						{
							listadoAvanceVentaVendedorMesCategoria.add(new AvanceVentaVendedor(
									Integer.parseInt(soapObject.getPropertyAsString("VendedorId")),
									loginEmpleado.get_dia(),
									Integer.parseInt(soapObject.getPropertyAsString("Mes")),
									Integer.parseInt(soapObject.getPropertyAsString("Anio")),
									soapObject.getPropertyAsString("NombreVendedor"),
									Float.parseFloat(soapObject.getPropertyAsString("Presupuesto")),
									Float.parseFloat(soapObject.getPropertyAsString("Avance")),
									Float.parseFloat(soapObject.getPropertyAsString("Tendencia")),
									Integer.parseInt(soapObject.getPropertyAsString("Cobertura")),
									soapObject.getPropertyAsString("Objeto"),
									Float.parseFloat(soapObject.getPropertyAsString("CoberturaPorcentaje"))));
						}
						catch(Exception localException)
						{
							if(localException.getMessage() == null || localException.getMessage().isEmpty())
							{
						        myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesVentaVendedor del vendedor por mes: vacio");
							}
							else
							{
								myLog.InsertarLog("App",this.toString(),1,"Error al insertar los avancesVentaVendedor del vendedor por mes: " + localException.getMessage());
							}
						}
					}
					
					LlenarListViewAvancesVentaProveedor();

				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances de las ventas del proveedor que descargar.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesByVendedorMes no se ejecuto correctamente. ", 1);
			} 
		}
	}

	private void LlenarListViewAvancesVentaProveedor()
	{
		MiAdaptadorListaProveedor localMiAdaptadorListaProveedor = new MiAdaptadorListaProveedor(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvAvancesVentaMesVendedorCategorias);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoAvanceVentaVendedorMesCategoria.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorListaProveedor);
	}
	
	class MiAdaptadorListaProveedor extends ArrayAdapter<AvanceVentaVendedor>
	{
		private Context _context;
		
		public MiAdaptadorListaProveedor(Context context)
		{
			super(context,R.layout.disenio_supervisoravancevendedordia,listadoAvanceVentaVendedorMesCategoria);
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
			
			AvanceVentaVendedor localAvanceVentaProveedor = (AvanceVentaVendedor)listadoAvanceVentaVendedorMesCategoria.get(position);
			
			TextView tvVendedor = (TextView)localView.findViewById(R.id.tvVendedorAvanceVentasCatgoriaTitulo);
			TextView tvProveedor = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasProveedor);
			TextView tvPresupuesto = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPresupuesto);
			TextView tvAvance = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPreventas);
			TextView tvTendencia = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasAvance);
			TextView tvCobertura = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasEficiencia);
			
			tvVendedor.setText(localAvanceVentaProveedor.get_objeto());
			tvProveedor.setText("");
			tvPresupuesto.setText(String.valueOf(new BigDecimal(localAvanceVentaProveedor.get_presupuesto()).setScale(2,RoundingMode.HALF_UP)));
			tvAvance.setText(String.valueOf(new BigDecimal(localAvanceVentaProveedor.get_avance()).setScale(2,RoundingMode.HALF_UP)));
			tvTendencia.setText(String.valueOf(new BigDecimal(localAvanceVentaProveedor.get_tendencia()).setScale(2,RoundingMode.HALF_UP)));
			tvCobertura.setText(String.valueOf(new BigDecimal(localAvanceVentaProveedor.get_coberturaPorcentaje()).setScale(2,RoundingMode.HALF_UP)));			
			
			return localView;
		}
	}
	
}
