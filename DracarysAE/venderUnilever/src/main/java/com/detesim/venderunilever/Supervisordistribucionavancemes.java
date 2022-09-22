package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import BLL.AvanceDistribucionBLL;
import BLL.MyLogBLL;
import Clases.AvanceDistribucion;
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

public class Supervisordistribucionavancemes extends Activity 
{
	LinearLayout llSupervisorAvanceDistribucionMes;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ArrayList<AvanceDistribucion> listadoAvanceDistribucionMes;
	
	ListView lvAvanceDistribucionMes;
	TextView tvDistribuidor;
	TextView tvNoPreventas;
	TextView tvMontoPreventas;
	TextView tvNoVentas;
	TextView tvMontoVentas;
	TextView tvEficiencia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supervisoravancedistribucionmes);
		
		llSupervisorAvanceDistribucionMes = (LinearLayout)findViewById(R.id.llDisenioAvanceDistribuidorMes);
		lvAvanceDistribucionMes = (ListView)findViewById(R.id.lvAvanceDistribucionMesDistribuidores);
		tvDistribuidor = (TextView)findViewById(R.id.tvAvanceDistribucionMesDistribuidor);
		tvNoPreventas = (TextView)findViewById(R.id.tvAvanceDistribucionMesNoPreventas);
		tvMontoPreventas = (TextView)findViewById(R.id.tvAvanceDistribucionMesMontoPreventas);
		tvNoVentas = (TextView)findViewById(R.id.tvAvanceDistribucionMesNoVentas);
		tvMontoVentas = (TextView)findViewById(R.id.tvAvanceDistribucionMesMontoVentas);
		tvEficiencia = (TextView)findViewById(R.id.tvAvanceDistribucionMesEficiencia);
		
		llSupervisorAvanceDistribucionMes.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
    		ObtenerAvancesDistribucionDiaForDisplay();
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
		
		lvAvanceDistribucionMes.setVisibility(visibility);
		tvDistribuidor.setVisibility(visibility);
		tvNoPreventas.setVisibility(visibility);
		tvMontoPreventas.setVisibility(visibility);
		tvNoVentas.setVisibility(visibility);
		tvMontoVentas.setVisibility(visibility);
		tvEficiencia.setVisibility(visibility);
	}
	
	private void ObtenerAvancesDistribucionDiaForDisplay()
	{
	    listadoAvanceDistribucionMes = null;
	    try
	    {
	    	listadoAvanceDistribucionMes = new AvanceDistribucionBLL().ObtenerAvanceDistribucionPorTipoAvanceDistribuidorYRol("MES","Supervisor");
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesDistribucion por mes: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los avancesDistribucion por mes: " + localException.getMessage());
	    	} 
	    }
	      
	    if(listadoAvanceDistribucionMes == null)
	    {
	    	MostrarControles(false);
	    	lvAvanceDistribucionMes.setAdapter(null);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No existen avances de distribucion en el mes.", 1);
	        return;
	    }
	    else
	    {
	    	LlenarListViewAvanceDistribucion();
	    }
	}
	
	private void LlenarListViewAvanceDistribucion()
	{
		MiAdaptadorLista localMiAdaptadorLista = new MiAdaptadorLista(getApplicationContext());
	    ListView localListView = (ListView)findViewById(R.id.lvAvanceDistribucionMesDistribuidores);
	    ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    localLayoutParams.height = ((int)(50*getApplicationContext().getResources().getDisplayMetrics().density) * listadoAvanceDistribucionMes.size());
	    localListView.setLayoutParams(localLayoutParams);
	    localListView.setAdapter(localMiAdaptadorLista);
	}
	
	class MiAdaptadorLista extends ArrayAdapter<AvanceDistribucion>
	{
		private Context _context;
		
		public MiAdaptadorLista(Context context)
		{
			super(context,R.layout.disenio_supervisoravancedistribuidor,listadoAvanceDistribucionMes);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (convertView == null) 
			{
				LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				localView = layoutInflater.inflate(R.layout.disenio_supervisoravancedistribuidor,parent,false);
			}
			
			AvanceDistribucion localAvanceDistribucion = (AvanceDistribucion)listadoAvanceDistribucionMes.get(position);
			
			TextView tvDistribuidor = (TextView)localView.findViewById(R.id.tvDisenioAvanceDistribuidorDistribuidor);
			TextView tvNoPreventas = (TextView)localView.findViewById(R.id.tvDisenioAvanceDistribuidorNoPreventas);
			TextView tvMontoPreventas = (TextView)localView.findViewById(R.id.tvDisenioAvanceDistribuidorMontoPreventas);
			TextView tvNoVentas = (TextView)localView.findViewById(R.id.tvDisenioAvanceDistribuidorNoVentas);
			TextView tvMontoVentas = (TextView)localView.findViewById(R.id.tvDisenioAvanceDistribuidorMontoVentas);
			TextView tvEficiencia = (TextView)localView.findViewById(R.id.tvDisenioAvanceDistribuidorEficiencia);
			
			tvDistribuidor.setText(localAvanceDistribucion.get_nombreDistribuidor());
			tvNoPreventas.setText(String.valueOf(localAvanceDistribucion.get_noPreventas()));
			tvMontoPreventas.setText(String.valueOf(new BigDecimal(localAvanceDistribucion.get_montoPreventas()).setScale(2, RoundingMode.HALF_UP)));
			tvNoVentas.setText(String.valueOf(localAvanceDistribucion.get_noVentas()));
			tvMontoVentas.setText(String.valueOf(new BigDecimal(localAvanceDistribucion.get_montoVentas()).setScale(2,RoundingMode.HALF_UP)));
			tvEficiencia.setText(String.valueOf(new BigDecimal(localAvanceDistribucion.get_eficiencia()).setScale(2,RoundingMode.HALF_UP)));			
			
			return localView;
		}
	}
}
