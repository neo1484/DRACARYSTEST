package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import BLL.AvanceVentaBLL;
import BLL.MyLogBLL;
import Clases.AvanceVenta;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Supervisorventaavancedia extends Activity 
{
	LinearLayout llSupervisorAvancesDia;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<AvanceVenta> listadoAvanceVentaDia;
	
	ListView lvAvanceVenta;
	TextView tvVendedor;
	TextView tvProveedor;
	TextView tvPresupuesto;
	TextView tvNoPreventas;
	TextView tvAvance;
	TextView tvEficiencia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supervisoravanceventasdia);
		
		llSupervisorAvancesDia = (LinearLayout)findViewById(R.id.SupervisorAvancesDiaLinearLayout);
		lvAvanceVenta = (ListView)findViewById(R.id.lvSupervisorVentasDiaAvanceVentas);
		tvVendedor = (TextView)findViewById(R.id.tvSupervisorVentasDiaVendedor);
		tvProveedor = (TextView)findViewById(R.id.tvSupervisorVentasDiaProveedor);
		tvPresupuesto = (TextView)findViewById(R.id.tvSupervisorVentasDiaPresupuesto);
		tvNoPreventas = (TextView)findViewById(R.id.tvSupervisorVentasDiaPreventas);
		tvAvance = (TextView)findViewById(R.id.tvSupervisorVentasDiaAvances);
		tvEficiencia = (TextView)findViewById(R.id.tvSupervisorVentasDiaEficiencia);
		
		llSupervisorAvancesDia.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
		
		lvAvanceVenta.setVisibility(visibility);
		tvVendedor.setVisibility(visibility);
		tvProveedor.setVisibility(visibility);
		tvPresupuesto.setVisibility(visibility);
		tvNoPreventas.setVisibility(visibility);
		tvAvance.setVisibility(visibility);
		tvEficiencia.setVisibility(visibility);
	}
	
	private void ObtenerAvancesVentaDiaForDisplay()
	{
	    listadoAvanceVentaDia = null;
	    try
	    {
	    	listadoAvanceVentaDia = new AvanceVentaBLL().ObtenerAvanceVentaPorTipoAvanceVentaYRol("DIA","Supervisor");
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por dia: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por dia: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoAvanceVentaDia == null)
	    {
	    	lvAvanceVenta.setAdapter(null);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen avances de venta de los vendedores en el dia.", 1);
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
	    ListView localListView = (ListView)findViewById(R.id.lvSupervisorVentasDiaAvanceVentas);
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
			
			tvVendedor.setText(localAvanceVenta.get_nombreVendedor());
			tvProveedor.setText(localAvanceVenta.get_nombreProveedor());
			tvPresupuesto.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_presupuesto()).setScale(2,RoundingMode.HALF_UP)));
			tvNoPreventas.setText(String.valueOf(localAvanceVenta.get_noPreventas()));
			tvAvance.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_avance()).setScale(2, RoundingMode.HALF_UP)));
			tvEficiencia.setText(String.valueOf(new BigDecimal((localAvanceVenta.get_avance()/localAvanceVenta.get_presupuesto())*100).setScale(2,RoundingMode.HALF_UP)));			
			
			return localView;
		}
	}
}
