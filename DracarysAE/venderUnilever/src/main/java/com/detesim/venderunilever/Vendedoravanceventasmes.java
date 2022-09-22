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

public class Vendedoravanceventasmes extends Activity 
{
	LinearLayout llVendedorAvanceVentasMes;
	Utilidades theUtilidades = new Utilidades();	
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<AvanceVenta> listadoAvanceVentaMes;
	
	TextView tvProveedor;
	TextView tvPresupuesto;
	TextView tvAvance;
	TextView tvTendencia;
	TextView tvCobertura;
	ListView lvAvances;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoravanceventasmes);
		
		llVendedorAvanceVentasMes  = (LinearLayout)findViewById(R.id.VendedorAvanceVentaMesCategoriaLinearLayout);
		tvProveedor = (TextView)findViewById(R.id.tvAvanceVentaMesVendedor);
		tvPresupuesto = (TextView)findViewById(R.id.tvAvanceVentaMesPresupuesto);
		tvAvance = (TextView)findViewById(R.id.tvAvanceVentaMesAvance);
		tvTendencia = (TextView)findViewById(R.id.tvAvanceVentaMesTendencia);
		tvCobertura = (TextView)findViewById(R.id.tvAvanceVentaMesCobertura);
		
		llVendedorAvanceVentasMes.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
		
		tvProveedor.setVisibility(visibility);
		tvPresupuesto.setVisibility(visibility);
		tvAvance.setVisibility(visibility);
		tvTendencia.setVisibility(visibility);
		tvCobertura.setVisibility(visibility);
	}
	
	private void ObtenerAvancesVentaMesForDisplay()
	{
	    listadoAvanceVentaMes = null;
	    try
	    {
	    	listadoAvanceVentaMes = new AvanceVentaBLL().ObtenerAvanceVentaPorTipoAvanceVentaYRol("MES","Vendedor");
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por mes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesVenta por mes: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoAvanceVentaMes == null)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen avances de venta en el mes.", 1);
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
	    ListView localListView = (ListView)findViewById(R.id.lvAvanceVentaMesAvanceVentas);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoAvanceVentaMes.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<AvanceVenta>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_supervisoravancevendedordia,listadoAvanceVentaMes);
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
			
			AvanceVenta localAvanceVenta = (AvanceVenta)listadoAvanceVentaMes.get(position);
			
			TextView tvVendedor = (TextView)localView.findViewById(R.id.tvVendedorAvanceVentasCatgoriaTitulo);
			TextView tvProveedor = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasProveedor);
			TextView tvPresupuesto = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPresupuesto);
			TextView tvAvance = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasPreventas);
			TextView tvTendencia = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasAvance);
			TextView tvCobertura = (TextView)localView.findViewById(R.id.tvSupervisorAvanceVentasEficiencia);
			
			tvProveedor.setText("");
			tvVendedor.setText(localAvanceVenta.get_nombreProveedor());
			tvPresupuesto.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_presupuesto()).setScale(2,RoundingMode.HALF_UP)));
			tvAvance.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_avance()).setScale(2,RoundingMode.HALF_UP)));
			tvTendencia.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_tendencia()).setScale(2,RoundingMode.HALF_UP)));
			tvCobertura.setText(String.valueOf(new BigDecimal(localAvanceVenta.get_coberturaPorcentaje()).setScale(2,RoundingMode.HALF_UP)));			
			
			return localView;
		}
	}
}
