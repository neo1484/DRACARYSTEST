package com.detesim.venderunilever;

import BLL.MyLogBLL;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Vendedorventadirecta extends Activity 
{
	LinearLayout llVendedorVentaDirecta;
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	
	ListView lvPreventa;
	Dialog dialogPreventa;
	ProgressDialog pdDeletePreventa;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedorventadirecta);
	}
}
