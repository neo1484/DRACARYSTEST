package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import BLL.CobroPendienteBLL;
import BLL.MyLogBLL;
import Clases.CobroPendiente;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Vendedorcobrospendientes extends Activity 
{
	LinearLayout llVendedorCobrosPendientes;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	TextView tvCliente;
	TextView tvFecha;
	TextView tvMonto;
	TextView tvVencimiento;
	TextView tvDiasMora;
	ListView lvCobrosPendientes;
	
	ArrayList<CobroPendiente> listadoCobrosPendientes;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorcobrospendientes);
		
		llVendedorCobrosPendientes = (LinearLayout)findViewById(R.id.llCobrosPendientes);
		tvCliente = (TextView)findViewById(R.id.tvCobroPendienteClienteTxt);
		tvFecha = (TextView)findViewById(R.id.tvCobroPendienteFecha);
		tvMonto = (TextView)findViewById(R.id.tvCobroPendienteMontoTxt);
		tvVencimiento = (TextView)findViewById(R.id.tvCobroPendienteSaldoTxt);
		tvDiasMora = (TextView)findViewById(R.id.tvCobroPendienteMora);
		lvCobrosPendientes = (ListView)findViewById(R.id.lvCobrosPendientesCobros);
		
		llVendedorCobrosPendientes.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	mostrarControles(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 1);
	        return;
	    }
	    	    
	    ObtenerCobrosPendientesForDisplay();
	}
	
	private void mostrarControles(boolean estado)
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
		
		tvCliente.setVisibility(visibility);
		tvFecha.setVisibility(visibility);
		tvMonto.setVisibility(visibility);
		tvVencimiento.setVisibility(visibility);
		tvDiasMora.setVisibility(visibility);
	}
	
	private void ObtenerCobrosPendientesForDisplay()
	{
		listadoCobrosPendientes = null;
	    try
	    {
	    	listadoCobrosPendientes = new CobroPendienteBLL().ObtenerCobrosPendientes();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cobros pendientes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los cobros pendientes: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoCobrosPendientes == null) 
	    {
	    	mostrarControles(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen cobros pendientes que visualizar.", 1);
	    	return;
	    }
	    else
	    {
	    	LlenarCobrosPendientesListView();	    	
	    }
	}
	
	private void LlenarCobrosPendientesListView()
	{
		MiAdaptadorCobro localMiAdaptadorCobro = new MiAdaptadorCobro();
		ListView localListView = lvCobrosPendientes;
		if(listadoCobrosPendientes == null)
		{
			mostrarControles(false);
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(100*getApplicationContext().getResources().getDisplayMetrics().density) * listadoCobrosPendientes.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorCobro);
			mostrarControles(true);
		}
	}
	
	class MiAdaptadorCobro extends ArrayAdapter<CobroPendiente>
	{
		public MiAdaptadorCobro()
		{
			super(getApplicationContext(),R.layout.disenio_vendedorsincronizacionpreventa,listadoCobrosPendientes);
		}
    
		public View getView(final int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_cobropendiente, viewGroup, false);
			}
			
			TextView tvCliente = (TextView)localView.findViewById(R.id.tvCobroPendienteCliente);
			TextView tvFechaVenta = (TextView)localView.findViewById(R.id.tvCobroPendienteFechaVenta);
			TextView tvMonto = (TextView)localView.findViewById(R.id.tvCobroPendienteMontos);
			//TextView tvFechaVencimiento = (TextView)localView.findViewById(R.id.tvCobroPendienteFechaVencimiento);
			TextView tvSaldo = (TextView)localView.findViewById(R.id.tvCobroPendienteSaldo);
			TextView tvDiasMora = (TextView)localView.findViewById(R.id.tvCobroPendienteDiasMora);
			
			CobroPendiente cobro = listadoCobrosPendientes.get(position);
			
			tvCliente.setText(cobro.get_clienteNombre());
			tvFechaVenta.setText(cobro.get_fechaVenta());
			tvMonto.setText(String.valueOf(new BigDecimal(cobro.get_monto()).setScale(2, RoundingMode.HALF_UP)));
			//tvFechaVencimiento.setText(cobro.get_fechaVencimiento());
			tvSaldo.setText(String.valueOf(new BigDecimal(cobro.get_saldo()).setScale(2, RoundingMode.HALF_UP)));
			tvDiasMora.setText(String.valueOf(cobro.get_diasMora()));
			
			if(cobro.get_diasMora()>0)
			{	
				tvFechaVenta.setTextAppearance(getApplicationContext(), R.style.EtiquetaRequerida);
				tvMonto.setTextAppearance(getApplicationContext(), R.style.EtiquetaRequerida);
				//tvFechaVencimiento.setTextAppearance(getApplicationContext(), R.style.EtiquetaRequerida);
				tvSaldo.setTextAppearance(getApplicationContext(), R.style.EtiquetaRequerida);
				tvDiasMora.setTextAppearance(getApplicationContext(), R.style.EtiquetaRequerida);
			}
			
			return localView;
		}
	}
}
