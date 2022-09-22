package com.detesim.venderunilever;

import java.util.ArrayList;

import BLL.MyLogBLL;
import Clases.LoginEmpleado;
import Clases.MyLog;
import Utilidades.Utilidades;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Log extends Activity 
{
	LinearLayout llLog;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	ListView lvLog;
	
	ArrayList<MyLog> listadoLog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		
		llLog = (LinearLayout)findViewById(R.id.LogLinearLayout);
		lvLog = (ListView)findViewById(R.id.lvLogLogs);
		
		llLog.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	    
	    ObtenerLogsForDisplay();
	}
	
	private void ObtenerLogsForDisplay()
	{
		listadoLog = null;
	    try
	    {
	    	listadoLog = new MyLogBLL().ObtenerLogs();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los logs: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los logs: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoLog == null) 
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"No existen logs que visualizar.", 1);
	    	return;
	    }
	    else
	    {
	    	LlenarLogListView();	    	
	    }
	}
	
	private void LlenarLogListView()
	{
		MiAdaptadorLog localMiAdaptadorPreventa = new MiAdaptadorLog();
		ListView localListView = lvLog;
		if(listadoLog == null)
		{
			localListView.setAdapter(null);
		}
		else
		{
			ViewGroup.LayoutParams localLayoutParams = localListView.getLayoutParams();
	    	localLayoutParams.height = ((int)(115*getApplicationContext().getResources().getDisplayMetrics().density) * listadoLog.size());
	    	localListView.setLayoutParams(localLayoutParams);
			localListView.setAdapter(localMiAdaptadorPreventa);
		}
	}
	
	class MiAdaptadorLog extends ArrayAdapter<MyLog>
	{
		public MiAdaptadorLog()
		{
			super(getApplicationContext(),R.layout.disenio_log,listadoLog);
		}
    
		public View getView(int position, View view, ViewGroup viewGroup)
		{
			View localView = view;
			
			if (localView == null) 
			{
				localView = getLayoutInflater().inflate(R.layout.disenio_log, viewGroup, false);
			}
			
			TextView tvApp = (TextView)localView.findViewById(R.id.tvLogAplicacion);
			TextView tvFecha = (TextView)localView.findViewById(R.id.tvLogFecha);
			TextView tvActividad = (TextView)localView.findViewById(R.id.tvLogActividad);
			TextView tvTipo = (TextView)localView.findViewById(R.id.tvLogTipoLog);
			TextView tvLog = (TextView)localView.findViewById(R.id.tvVendedorVentaDirectaPromocionProveedor);
			
			MyLog localMyLog = null;
			
			if(listadoLog.size() > 0)
			{
				localMyLog = (MyLog)listadoLog.get(position);
			}
			
			tvApp.setText(localMyLog.get_aplicacion());
			tvFecha.setText(localMyLog.get_fecha());
			tvActividad.setText(localMyLog.get_actividad());
			tvTipo.setText(localMyLog.get_tipoLog());
			tvLog.setText(localMyLog.get_Log());
			
			return localView;
		}
	}
	
}
