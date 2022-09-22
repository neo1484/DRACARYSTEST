package com.detesim.venderunilever;

import java.util.ArrayList;
import BLL.FacturaBLL;
import BLL.MyLogBLL;
import BLL.PreventaBLL;
import BLL.RolBLL;
import BLL.SincronizacionDatosBLL;
import BLL.VentaBLL;
import Clases.AdminSessions;
import Clases.Factura;
import Clases.LoginEmpleado;
import Clases.Preventa;
import Clases.Rol;
import Clases.SincronizacionDatos;
import Clases.Venta;
import Utilidades.ControladorDB;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menuprincipal extends Activity implements OnClickListener
{
	Utilidades theUtilidades = new Utilidades();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	Dialog dialog;
	ArrayList<Rol> listadoRol;

	LinearLayout llMenuPrincipal;
	TextView tvUsuario;
	ImageView ivPrincipalMenuSupervisor;
	ImageView ivPrincipalMenuCensista;
	ImageView ivPrincipalMenuVendedor;
	ImageView ivPrincipalMenuDistribuidor;
	ImageView ivPrincipalMenuCobrador;
	ImageView ivPrincipalSalirSistema;
	ImageView ivPrincipalCerrarSesion;

	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
	    case R.id.salir:
	    	CerrarSistema();
	    	break;
	    }

	    return true;
	} 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menuprincipal);
		
		llMenuPrincipal = (LinearLayout)findViewById(R.id.MenuPrincipalLinearLayout);
		tvUsuario = (TextView)findViewById(R.id.tvMenUsuario);
		ivPrincipalMenuSupervisor = (ImageView)findViewById(R.id.ivMenSupervisor);
	    ivPrincipalMenuSupervisor.setOnClickListener(this);
	    ivPrincipalMenuCensista = (ImageView)findViewById(R.id.ivMenCensista);
	    ivPrincipalMenuCensista.setOnClickListener(this);
	    ivPrincipalMenuVendedor = (ImageView)findViewById(R.id.ivMenVendedor);
	    ivPrincipalMenuVendedor.setOnClickListener(this);
	    ivPrincipalMenuDistribuidor = (ImageView)findViewById(R.id.ivMenDistribuidor);
	    ivPrincipalMenuDistribuidor.setOnClickListener(this);
	    ivPrincipalMenuCobrador = (ImageView)findViewById(R.id.ivMenCobrador);
	    ivPrincipalMenuCobrador.setOnClickListener(this);
	    ivPrincipalSalirSistema = (ImageView)findViewById(R.id.ivMenSalirSistema);
	    ivPrincipalSalirSistema.setOnClickListener(this);
		ivPrincipalCerrarSesion = (ImageView)findViewById(R.id.ivMenCerrarSesion);
		ivPrincipalCerrarSesion.setOnClickListener(this);
	    
	    ivPrincipalMenuSupervisor.setVisibility(View.INVISIBLE);
	    ivPrincipalMenuCensista.setVisibility(View.INVISIBLE);
	    ivPrincipalMenuVendedor.setVisibility(View.INVISIBLE);
	    ivPrincipalMenuDistribuidor.setVisibility(View.INVISIBLE);
	    ivPrincipalMenuCobrador.setVisibility(View.INVISIBLE);
	    
	    llMenuPrincipal.setBackground(getResources().getDrawable(theUtilidades.get_fondoMenu()));
	    
        loginEmpleado = null;
        try
        {
            loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
        }
        catch (Exception localException)
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado: " + localException.getMessage());
			} 
        }
    	
    	if(loginEmpleado != null)
    	{
    		if(ObtenerRolesEmpleado())
    		{
    			tvUsuario.setText(loginEmpleado.get_empleadoNombre());
    		}
    		else
    		{
    			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los roles del empleado.", 2);
    		}
    	}
    	else
    	{
    		theUtilidades.MostrarMensaje(getApplicationContext(), "El usuario no se logeo correctamente.", 2);
    	}		  
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
	    {

			case R.id.ivMenSupervisor:
				MostrarPantallaMenuSupervisor();
				break;
			case R.id.ivMenCensista:
				MostrarPantallaMenuCensista();
				break;
			case R.id.ivMenVendedor:
				MostrarPantallaMenuVendedor();
				break;
			case R.id.ivMenDistribuidor:
				MostrarPantallaMenuDistribuidor();
				break;
			case R.id.ivMenCobrador:
				MostrarPantallaMenuCobrador();
				break;
			case R.id.ivMenSalirSistema:
				CerrarSistema();
				break;
			case R.id.ivMenCerrarSesion:
				CerrarSesion();
				break;
	    }
	}
	
	private boolean ObtenerRolesEmpleado()
	{
		listadoRol = null;
		
	    try
	    {
	    	listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoRol == null) 
	    {
	    	return false;
		}
	    else
	    {
	    	for(Rol rolEmpleado : listadoRol)
	    	{
	    		if (rolEmpleado.get_rol().equals("Supervisor")) 
	    		{
	  	          	ivPrincipalMenuSupervisor.setVisibility(View.VISIBLE);
	  	        } 
	    		else if (rolEmpleado.get_rol().equals("Censista")) 
	    		{
	  	          	ivPrincipalMenuCensista.setVisibility(View.VISIBLE);
	  	        } 
	    		else if (rolEmpleado.get_rol().equals("Vendedor")) 
	    		{
	    			if(!VerificarExistenciaPreventasAnteriores())
	    			{
	    				ivPrincipalMenuVendedor.setVisibility(View.VISIBLE);
	    			}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Existen preventas de dias anteriores, salga del sistema para borrar esa informacion.", 1);
	    				return false;
	    			}
	  	        } 
	    		else if(rolEmpleado.get_rol().equals("Distribuidor"))
	    		{
	    			if(!VerificarExistenciaVentasAnteriores())
	    			{
	    				ivPrincipalMenuDistribuidor.setVisibility(View.VISIBLE);
	    			}
	    			else
	    			{
	    				theUtilidades.MostrarMensaje(getApplicationContext(), "Existen ventas de dias anteriores, salga del sistema para borrar esa informacion.", 1);
	    				return false;
	    			}
	  	        }
	    		else if (rolEmpleado.get_rol().equals("VendedorProvincia"))
	    		{
	    			if(VerificarExistenciaPreventasAnteriores())
	    			{
	    				if(!BorrarDatosSincronizacion())
	    				{
	    					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los datos de sincronizacion.", 1);
	    					return false;
	    				}
	    			}
	    			
	    			ivPrincipalMenuVendedor.setVisibility(View.VISIBLE);
	    		}
	    		else if(rolEmpleado.get_rol().equals("DistribuidorProvincia"))
	    		{
	    			if(VerificarExistenciaSincronizacionDatos())
	    			{
		    			if(ActualizarFechaSincronizacionDatos())
		    			{
		    				ivPrincipalMenuDistribuidor.setVisibility(View.VISIBLE);
		    			}
		    			else
		    			{
		    				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo actualizar la nueva fecha del distribuidor provincia.", 1);
		    				return false;
		    			}
	    			}
	    			else
	    			{
	    				ivPrincipalMenuDistribuidor.setVisibility(View.VISIBLE);
	    			}
	  	        }
	    		else if(rolEmpleado.get_rol().toUpperCase().equals("COBRADOR"))
	    		{
	    			ivPrincipalMenuCobrador.setVisibility(View.VISIBLE);
	    		}
	    	}	
	    }
	    return true;
	}
	
	private boolean VerificarExistenciaPreventasAnteriores()
	{
		boolean existencia = false;
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
		
		if(listadoPreventa == null)
		{
			return existencia;
		}
		else
		{
			for(Preventa thePreventa : listadoPreventa)
			{
				if(thePreventa.get_anio() < loginEmpleado.get_anio()
				|| thePreventa.get_mes() < loginEmpleado.get_mes()
				|| thePreventa.get_dia() < loginEmpleado.get_dia())
				{
					return true;
				}
			}
			
			return existencia;
		}
	}
	
	private boolean VerificarExistenciaVentasAnteriores()
	{
		boolean existencia = false;
		ArrayList<Venta> listadoVenta = null;
		
		try
		{
			listadoVenta = new VentaBLL().ObtenerVentas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las ventas: " + localException.getMessage());
	    	} 
		}
		
		if(listadoVenta == null)
		{
			return existencia;
		}
		else
		{
			for(Venta theVenta : listadoVenta)
			{
				if(theVenta.get_anio() < loginEmpleado.get_anio()
				|| theVenta.get_mes() < loginEmpleado.get_mes()
				|| theVenta.get_dia() < loginEmpleado.get_dia())
				{
					return true;
				}
			}
			
			return existencia;
		}
	}
	
	private boolean BorrarDatosSincronizacion()
	{
		boolean bool = false;
	    try
	    {
	    	bool = new SincronizacionDatosBLL().BorrarSincronizacionesDatos();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datosSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datosSincronizacion: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    
	    if(!bool)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los datosSincronizacion.", 1);
	    	return false;
	    }
	    
	    return true;
	}
	
	private boolean ActualizarFechaSincronizacionDatos()
	{
		long l = 0;
	    try
	    {
	    	l = new SincronizacionDatosBLL().ModificarFechaSinconizacionDatos(loginEmpleado.get_empleadoId(),loginEmpleado.get_dia(),
	    																	loginEmpleado.get_mes(),loginEmpleado.get_anio());
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datosSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datosSincronizacion: " + localException.getMessage());
	    	}
	    	return false;
	    }
	    
	    if(l == 0)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	private boolean VerificarExistenciaSincronizacionDatos()
	{
		ArrayList<SincronizacionDatos> theSincro = null;
		try
		{
			theSincro = new SincronizacionDatosBLL().ObtenerDetalleSincronizacionesDatos();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datosSincronizacion: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datosSincronizacion: " + localException.getMessage());
	    	}
		}
		
		if(theSincro==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void CerrarSesion()
	{
		AdminSessions adminSessions =  new AdminSessions(Menuprincipal.this);
		adminSessions.RemoveSession();
		MostrarPantallaLogin();
	}
	
	public void CerrarSistema()
	{
		dialog = new Dialog(Menuprincipal.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.diseniodialogo_vendedorcierredia);
		dialog.setTitle("Cierre Sistema");
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBluePale));
		
		TextView tvMensaje = (TextView)dialog.findViewById(R.id.tvDialogoMensaje);
		tvMensaje.setText("Esta seguro que desea salir del sistema, borrando toda la informacion de su dispositivo?");
		
		Button btnAceptar = (Button)dialog.findViewById(R.id.btnDialogoAceptar);
		btnAceptar.setOnClickListener(new OnClickListener() 
			{		
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
					case R.id.btnDialogoAceptar:
						if(dialog.isShowing())
						{
							dialog.dismiss();
						}
						
						for(Rol item : listadoRol)
						{
							if(item.get_rol().equals("Distribuidor"))
							{
								ArrayList<Factura> listadoFactura = null;
								try
								{
									listadoFactura = new FacturaBLL().ObtenerFacturasNoSincronizadas();
								}
								catch(Exception localException)
								{
									if(localException.getMessage() == null || localException.getMessage().isEmpty())
							    	{
							    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: vacio");
							    	}
							    	else
							    	{
							    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las facturas no sincronizadas: " + localException.getMessage());
							    	} 
								}
								
								if(listadoFactura == null)
								{				
									BorrarTablasSistema();
								}
								else
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "Debe sincronizar las facturas antes de salir del sistema.", 1);
								}
							}
							else
							{
								BorrarTablasSistema();
							}
						}
						break;
					}	
				}
			});
		
		Button btnCancelar = (Button)dialog.findViewById(R.id.btnDialogoCancelar);
		btnCancelar.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					switch(v.getId())
					{
					case R.id.btnDialogoCancelar:
						if(dialog.isShowing())
						{
							dialog.dismiss();
						}
					}							
				}
			});
		
		dialog.show();
	}
	
	public void BorrarTablasSistema()
	{
		ControladorDB controlador = new ControladorDB(getApplicationContext());
		if(controlador.DeleteDB(getApplicationContext()))
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "El sistema se cerro correctamente.", 1);
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cerrar el sistema.", 1);
		}
		MostrarPantallaLogin();
	}
		
	public void MostrarPantallaMenuSupervisor()
	{
	    startActivity(new Intent(this, Menusupervisor.class));
	}
	
	public void MostrarPantallaMenuCensista()
	{
		Intent intent  = new Intent(this,Menucensista.class);
		intent.putExtra("Origen", "Menuprincipal");
		startActivity(intent);
	}
	  
	public void MostrarPantallaMenuVendedor()
	{
	    startActivity(new Intent(this, Menuvendedor.class));
	}
	
	public void MostrarPantallaMenuDistribuidor()
	{
	    startActivity(new Intent(this, Menudistribuidor.class));
	}
	
	public void MostrarPantallaMenuCobrador()
	{
	    startActivity(new Intent(this, Menucobrador.class));
	}
	
	public void MostrarPantallaLogin()
	{
		Intent intent = new Intent(this, Login.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Login.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
}
