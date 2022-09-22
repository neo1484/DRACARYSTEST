package com.detesim.venderunilever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BLL.ClienteCensoBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PreventaBLL;
import BLL.TipoNegocioBLL;
import BLL.UltimaCoordenadaBLL;
import Clases.ClienteCenso;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.Preventa;
import Clases.TipoNegocio;
import Clases.UltimaCoordenada;
import Utilidades.Utilidades;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class Censistaunificacionclientesmapa extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	LoginEmpleado loginEmpleado;
	ParametroGeneral parametroGeneral;
	GoogleMap mapa;

	CheckBox cbClienteCenso;
	CheckBox cbClienteVender;
	RadioButton rbtClienteMatch;

	ArrayList<ClientePreventa> listadoClienteVender;
	ArrayList<ClienteCenso> listadoClienteCenso;

	int clienteCensoId;
	int clienteVenderId;
	int clienteUltimaCoordenada;
	double ultimaLatitudCenso;
	double ultimaLongitudCenso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_censistaunificacionclientesmapa);

		cbClienteCenso = (CheckBox) findViewById(R.id.cbCensistaMapaClienteCenso);
		cbClienteCenso.setVisibility(View.INVISIBLE);
		cbClienteVender = (CheckBox) findViewById(R.id.cbCensistaMapaClienteVender);
		cbClienteVender.setVisibility(View.INVISIBLE);
		rbtClienteMatch = (RadioButton) findViewById(R.id.rbtCensistaMapaClienteMatch);
		rbtClienteMatch.setVisibility(View.INVISIBLE);
		;

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.censistamapaunificarcliente);
		mapFragment.getMapAsync(this);

		loginEmpleado = null;

		try {
			loginEmpleado = this.theUtilidades.ObtenerLoginEmpleado();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del vendedor: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el LoginEmpleado del vendedor: " + localException.getMessage());
			}
		}

		if (loginEmpleado == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "El vendedor no se logeo correctamente.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			ObtenerParametroGeneral();

			if (parametroGeneral.is_habilitarMatcheo()) {
				cbClienteCenso.setVisibility(View.VISIBLE);
				cbClienteVender.setVisibility(View.VISIBLE);
				rbtClienteMatch.setVisibility(View.VISIBLE);

				rbtClienteMatch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						MostrarPantallaUnificacion();
					}
				});

				if (ObtenerClientesVendedor()) {
					if (ObtenerClientesCenso()) {
						if (InicializarMapa()) {
							DesplegarClientesVendedor();

							theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoClienteVender.size()), 1);
						} else {
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
						}
					} else {
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron clientes del censo.", 1);
					}
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los clientes.", 1);
				}
			} else {
				if (ObtenerClientesVendedor()) {
					if (InicializarMapa()) {
						DesplegarClientesVendedor();

						theUtilidades.MostrarMensaje(getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoClienteVender.size()), 1);
					} else {
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
					}
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes para el vendedor.", 1);
				}
			}
		} else {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo inicializar el mapa.", 1);
		}
	}

	private void ObtenerParametroGeneral() {
		parametroGeneral = null;
		try {
			parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los parametros generales: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los parametros generales: " + localException.getMessage());
			}
		}

		if (parametroGeneral == null) {
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
		}
	}

	private boolean ObtenerClientesCenso() {
		listadoClienteCenso = null;
		try {
			listadoClienteCenso = new ClienteCensoBLL().ObtenerClientesCenso();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes censo: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes censo: " + localException.getMessage());
			}
		}

		return listadoClienteCenso != null;
	}

	private boolean ObtenerClientesVendedor() {
		listadoClienteVender = null;
		try {
			listadoClienteVender = new ClientePreventaBLL().ObtenerClientesPreventa();
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes vender: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener los clientes vender: " + localException.getMessage());
			}
		}

		return listadoClienteVender != null;
	}

	private void DesplegarClientesVendedor() {
		String nombreNegocio;

		for (ClientePreventa item : listadoClienteVender) {
			MarkerOptions localMarkerOptions = new MarkerOptions();

			localMarkerOptions.title(String.valueOf(item.get_nombreCompleto()));
			localMarkerOptions.snippet(String.valueOf(item.get_clienteId()));
			localMarkerOptions.position(new LatLng(item.get_latitud(), item.get_longitud()));

			nombreNegocio = ObtenerNombreNegocio(item.get_tipoNegocioId());

			if (nombreNegocio.equals("Almacen/Pulperias")) {
				localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_noatendido));
			} else if (nombreNegocio.equals("Tipo A")) {
				localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_noatendida));
			} else if (nombreNegocio.equals("Tipo B")) {
				localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_noatendida));
			} else if (nombreNegocio.equals("Tipo C")) {
				localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_noatendida));
			} else if (nombreNegocio.equals("Micromercado")) {
				localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_noatendido));
			} else {
				localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_no_atendido));
			}
			mapa.addMarker(localMarkerOptions);
		}

		if (parametroGeneral.is_habilitarMatcheo()) {
			if (listadoClienteCenso != null & listadoClienteCenso.size() > 0) {
				for (ClienteCenso item : listadoClienteCenso) {
					MarkerOptions localMarkerOptions = new MarkerOptions();

					localMarkerOptions.title("CC-" + item.get_codigo() + "-" + item.get_referencia() + "-" + item.get_contacto());
					localMarkerOptions.snippet(String.valueOf(item.get_id()));
					localMarkerOptions.position(new LatLng(item.get_latitud(), item.get_longitud()));
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_censo));

					this.mapa.addMarker(localMarkerOptions);
				}

				if (ultimaLatitudCenso != 0 && ultimaLongitudCenso != 0) {
					MarkerOptions localMarkerOptions = new MarkerOptions();
					localMarkerOptions.position(new LatLng(ultimaLatitudCenso, ultimaLongitudCenso));
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_censo));
					this.mapa.addMarker(localMarkerOptions);
				}
			}
		}
	}

	private String ObtenerNombreNegocio(int tipoNegocioId) {
		TipoNegocio theNegocio = null;
		try {
			theNegocio = new TipoNegocioBLL().ObtenerTipoNegocioPor(tipoNegocioId);
		} catch (Exception localException) {
			if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el tipoNegocio por tipoNegocioId: vacio");
			} else {
				myLog.InsertarLog("App", this.toString(), 1, "Error al obtener el tipoNegocio por tipoNegocioId: " + localException.getMessage());
			}
		}
		if (theNegocio == null) {
			return "";
		} else {
			return theNegocio.get_descripcion();
		}
	}


	private boolean InicializarMapa() {

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}
		mapa.setMyLocationEnabled(true);
		mapa.getUiSettings().setZoomControlsEnabled(true);
		LatLng ubicacionInicial=null;

		if(listadoClienteVender != null && listadoClienteVender.size()>0)
		{
			if(parametroGeneral.is_habilitarMatcheo())
			{
				if(ObtenerUltimaCoordenadaCenso())
				{
					ubicacionInicial = new LatLng(ultimaLatitudCenso,ultimaLongitudCenso);
				}
				else
				{
					ubicacionInicial = new LatLng(listadoClienteCenso.get(0).get_latitud(),listadoClienteCenso.get(0).get_longitud());
				}
			}
			else
			{
				ubicacionInicial = new LatLng(listadoClienteVender.get(0).get_latitud(),listadoClienteVender.get(0).get_longitud());
			}

		}
		else
		{
			ubicacionInicial = new LatLng(0,0);
		}
		CameraPosition localCameraPosition = new CameraPosition.Builder().target(ubicacionInicial).zoom(15).build();
		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));

		mapa.setInfoWindowAdapter(new InfoWindowAdapter()
		{
			@Override
			public View getInfoWindow(Marker marker)
			{
				View view = getLayoutInflater().inflate(R.layout.disenio_mapanombrecliente, null);

				TextView tvNombre = (TextView)view.findViewById(R.id.tvMapaNombreClienteNombre);
				TextView tvCodigo = (TextView)view.findViewById(R.id.tvMapaNombreClienteCodigo);
				TextView tvNroPreventas = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroPreventas);
				TextView tvPromedioMensualVenta = (TextView)view.findViewById(R.id.tvMapaNombreClientePromedioVentaMensual);
				TextView tvInformacionComplementaria = (TextView)view.findViewById(R.id.tvMapaNombreClienteDatosComplementarios);

				tvNombre.setText(marker.getTitle());
				tvCodigo.setText("Codigo: " +marker.getSnippet());
				tvNroPreventas.setText("No. Preventas: " + ObtenerNroPreventasPorCliente(Integer.valueOf(marker.getSnippet())));
				tvPromedioMensualVenta.setText("Promedio venta Mensual: " + ObtenerPromedioVentaMensual(Integer.valueOf(marker.getSnippet())));
				tvInformacionComplementaria.setText("Info. Adicional: " + ObtenerInfoAdicional(Integer.valueOf(marker.getSnippet())));

				return view;
			}

			@Override
			public View getInfoContents(Marker marker)
			{
				return null;
			}
		});

		mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
		{
			@Override
			public void onInfoWindowClick(Marker marker)
			{
				if(parametroGeneral.is_habilitarMatcheo())
				{
					if (marker != null)
					{
						if (marker.getSnippet() != "")
						{
							if(marker.getTitle().substring(0,3).equals("CC-"))
							{
								clienteCensoId = Integer.valueOf(marker.getSnippet());
								cbClienteCenso.setChecked(true);
								theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente Censo Establecido", 1);
							}
							else
							{
								if(cbClienteCenso.isChecked())
								{
									clienteVenderId = Integer.valueOf(marker.getSnippet());
									cbClienteVender.setChecked(true);
									theUtilidades.MostrarMensaje(getApplicationContext(), "Cliente Vender Establecido", 1);
								}
								else
								{

									if (marker.getSnippet() != "")
									{

									}
									else
									{
										theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
									}
								}
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
							return;
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
					}
				}
				else
				{
					if (marker != null)
					{

					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
					}
				}
			}
		});

		return true;
	}

	private String ObtenerNroPreventasPorCliente(int clienteId)
	{
		ArrayList<Preventa> listadoPreventa = null;
		try
		{
			listadoPreventa = new PreventaBLL().ObtenerNroPreventasPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(listadoPreventa == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(listadoPreventa.size());
		}
	}
	
	private String ObtenerPromedioVentaMensual(int clienteId)
	{
		ClientePreventa theClientePreventa = null;
		try
		{
			theClientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los detalles del cliente por clienteId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(theClientePreventa == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(BigDecimal.valueOf(theClientePreventa.get_promedioVenta()).setScale(2,RoundingMode.HALF_UP));
		}
	}
	
	private String ObtenerInfoAdicional(int clienteId)
	{
		ClientePreventa clientePreventa = null;
		try
		{
			clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas por clienteId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(clientePreventa == null)
		{
			return "";
		}
		else
		{
			return String.valueOf(clientePreventa.get_codigo() + " - " + clientePreventa.get_referencia() + " - " + clientePreventa.get_contacto());
		}
	}
	
	private boolean ObtenerUltimaCoordenadaCenso()
	{
		UltimaCoordenada localUltimaCordenada = null;
		
		try
		{
			localUltimaCordenada = new UltimaCoordenadaBLL().ObtenerUltimaCoordenadaPor(clienteCensoId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ultimaCoordenada por clienteCensoId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la ultimaCoordenada por clienteCensoId: " + localException.getMessage());
	    	} 
		}
		
		if(localUltimaCordenada == null)
		{
			return false;
		}
		else
		{
			ultimaLatitudCenso = localUltimaCordenada.get_latitud();
			ultimaLongitudCenso = localUltimaCordenada.get_longitud();
			return true;
		}
	}
	
	private void MostrarPantallaUnificacion()
	{
		if(clienteCensoId==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe establecer al menos la coordenada del cliente censo.", 1);
		}
		else
		{
			Intent intent = new Intent(this,Censistaunificacionclientes.class);
			intent.putExtra("ClienteCensoId", clienteCensoId);
			intent.putExtra("ClienteVenderId", clienteVenderId);
			intent.putExtra("Origen", "Censo");
			startActivity(intent);
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this,Menucensista.class);
		intent.putExtra("Origen", "Menuvendedor");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
