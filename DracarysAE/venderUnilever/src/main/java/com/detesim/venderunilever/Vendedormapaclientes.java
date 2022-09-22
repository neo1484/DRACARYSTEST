package com.detesim.venderunilever;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Locale;

import BLL.ClienteCensoBLL;
import BLL.ClienteDatosVentaBLL;
import BLL.ClienteEncuestadoBLL;
import BLL.ClienteNoAtendidoBLL;
import BLL.ClienteNroFotoBLL;
import BLL.ClientePreventaBLL;
import BLL.EncuestaBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PreventaBLL;
import BLL.TipoNegocioBLL;
import BLL.UltimaCoordenadaBLL;
import Clases.ClienteCenso;
import Clases.ClienteDatosVenta;
import Clases.ClienteEncuestado;
import Clases.ClienteNoAtendido;
import Clases.ClienteNroFoto;
import Clases.ClientePreventa;
import Clases.Encuesta;
import Clases.LoginEmpleado;
import Clases.ParametroGeneral;
import Clases.Preventa;
import Clases.TipoNegocio;
import Clases.UltimaCoordenada;
import Utilidades.Utilidades;

public class Vendedormapaclientes extends FragmentActivity implements OnMapReadyCallback {
	Utilidades theUtilidades = new Utilidades();
	MyLogBLL myLog = new MyLogBLL();

	LoginEmpleado loginEmpleado;
	ParametroGeneral parametroGeneral;

	GoogleMap mapa;

	CheckBox cbClienteCenso;
	CheckBox cbClienteVender;
	RadioButton rbtClienteMatch;
	CheckBox cbClienteEncuesta;
	Dialog dialog;

	ArrayList<ClientePreventa> listadoClientePreventa;
	ArrayList<ClienteCenso> listadoClienteCenso;
	ArrayList<Encuesta> listadoEncuestas;

	int clienteCensoId;
	int clienteVenderId;
	double ultimaLatitudCenso;
	double ultimaLongitudCenso;
	boolean encuestasActivas;
	String clienteId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedormapaclientes);

		cbClienteCenso = (CheckBox) findViewById(R.id.cbVendedorMapaClienteCenso);
		cbClienteCenso.setVisibility(View.INVISIBLE);
		cbClienteVender = (CheckBox) findViewById(R.id.cbVendedorMapaClienteVender);
		cbClienteVender.setVisibility(View.INVISIBLE);
		rbtClienteMatch = (RadioButton) findViewById(R.id.rbtnVendedorMapaClienteMatch);
		rbtClienteMatch.setVisibility(View.INVISIBLE);
		cbClienteEncuesta = (CheckBox) findViewById(R.id.cbVendedorMapaEncuesta);
		cbClienteEncuesta.setVisibility(View.INVISIBLE);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaVendedor);
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
			theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "El vendedor no se logeo correctamente.", 2);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		if (mapa != null) {
			ObtenerParametroGeneral();

			if (ExistenEncuestasActivas()) {
				cbClienteEncuesta.setVisibility(View.VISIBLE);
			}

			if (parametroGeneral.is_habilitarMatcheo()) {
				cbClienteCenso.setVisibility(View.VISIBLE);
				cbClienteVender.setVisibility(View.VISIBLE);
				rbtClienteMatch.setVisibility(View.VISIBLE);

				rbtClienteMatch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						MostrarPantallaUnificacion();
					}
				});

				if (ObtenerClientesVendedor()) {
					InicializarMapa();
					ObtenerClientesCenso();
					DesplegarClientesVendedor();
					theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoClientePreventa.size()), 1);
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "No se pudo obtener los clientes.", 1);
				}
			} else {
				if (ObtenerClientesVendedor()) {
					InicializarMapa();
					DesplegarClientesVendedor();
					theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "Clientes en ruta: " + String.valueOf(listadoClientePreventa.size()), 1);
				} else {
					theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "No se encontraron clientes para el vendedor.", 1);
				}
			}
		}
		else{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo inicializar el mapa.", 1);
		}
	}

	private void InicializarMapa()
	{
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		mapa.setMyLocationEnabled(true);
		mapa.getUiSettings().setZoomControlsEnabled(true);
		LatLng ubicacionInicial=null;

		if(listadoClientePreventa != null && listadoClientePreventa.size()>0)
		{
			if(parametroGeneral.is_habilitarMatcheo())
			{
				if(ObtenerUltimaCoordenadaCenso())
				{
					ubicacionInicial = new LatLng(ultimaLatitudCenso,ultimaLongitudCenso);
				}
				else
				{
					ubicacionInicial = new LatLng(listadoClientePreventa.get(0).get_latitud(),listadoClientePreventa.get(0).get_longitud());
				}
			}
			else
			{
				ubicacionInicial = new LatLng(listadoClientePreventa.get(0).get_latitud(),listadoClientePreventa.get(0).get_longitud());
			}
		}
		else
		{
			ubicacionInicial = new LatLng(0,0);
		}
		CameraPosition localCameraPosition = new CameraPosition.Builder().target(ubicacionInicial).zoom(15).build();
		mapa.animateCamera(CameraUpdateFactory.newCameraPosition(localCameraPosition));

		mapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
		{
			@Override
			public View getInfoWindow(Marker marker)
			{
				View view = getLayoutInflater().inflate(R.layout.disenio_mapanombreclienteextendido, null);

				TextView tvNombre = (TextView)view.findViewById(R.id.tvMapaNombreClienteNombre);
				TextView tvCodigo = (TextView)view.findViewById(R.id.tvMapaNombreClienteCodigo);
				TextView tvNroPreventas = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroPreventas);
				TextView tvCelular = (TextView)view.findViewById(R.id.tvDisMapNomCliCelular);

				TextView tvTipoNegocio = (TextView)view.findViewById(R.id.tvDisMapNomCliTipoNegocio);
				TextView tvTerritorio = (TextView)view.findViewById(R.id.tvMapaNombreClienteTerritorio);
				TextView tvMercado = (TextView)view.findViewById(R.id.tvMapaNombreClienteMercado);

				TextView tvPromedioMensualVenta = (TextView)view.findViewById(R.id.tvMapaNombreClientePromedioVentaMensual);
				TextView tvPromedioSemanalVenta = (TextView)view.findViewById(R.id.tvDisMapNomCliPremedioSemanal);
				TextView tvIntensidadVenta = (TextView)view.findViewById(R.id.tvDisMapNomCliIntensidadCompra);
				TextView tvUltimaCompra = (TextView)view.findViewById(R.id.tvDisMapNomCliUltimaCompra);
				TextView tvEscadoPreventa = (TextView)view.findViewById(R.id.tvDisMapNomCliEscadoPreventa);
				TextView tvEscadoVenta = (TextView)view.findViewById(R.id.tvDisMapNomCliEscadoVenta);
				TextView tvDeuda = (TextView)view.findViewById(R.id.tvDisMapNomCliDeuda);

				TextView tvInformacionComplementaria = (TextView)view.findViewById(R.id.tvMapaNombreClienteDatosComplementarios);
				TextView tvClienteCensado = (TextView)view.findViewById(R.id.tvMapaNombreClienteClienteEncuestado);
				TextView tvClienteNroFotos = (TextView)view.findViewById(R.id.tvMapaNombreClienteNroFotos);
				TextView tvClienteObservacion = (TextView)view.findViewById(R.id.tvMapaNombreClienteObservacion);

				ClienteDatosVenta clienteDatosVenta = ObtenerClienteDatosVenta(Integer.valueOf(marker.getSnippet()));
				ClientePreventa clientePreventa = null;

				try
				{
					clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(Integer.valueOf(marker.getSnippet()));
				}
				catch(Exception localException)
				{
					if(localException.getMessage() == null || localException.getMessage().isEmpty())
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por codigo: vacio");
					}
					else
					{
						myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente por codigo: " + localException.getMessage());
					}
				}

				if(clientePreventa != null)
				{
					tvNombre.setText(marker.getTitle());
					tvCodigo.setText(marker.getSnippet());
					tvCelular.setText(clientePreventa.get_celular());

					tvNroPreventas.setText(ObtenerNroPreventasPorCliente(Integer.parseInt(marker.getSnippet())));
					tvTipoNegocio.setText(ObtenerTipoNegocio(clientePreventa.get_clienteTipoNegocioId()));
					tvTerritorio.setText(clientePreventa.get_zona());
					tvMercado.setText(clientePreventa.get_mercado());

					tvPromedioMensualVenta.setText(String.valueOf(BigDecimal.valueOf(clientePreventa.get_promedioVenta()).setScale(2, RoundingMode.HALF_UP)));
					tvPromedioSemanalVenta.setText(String.valueOf(clienteDatosVenta!=null? BigDecimal.valueOf(clienteDatosVenta.getPromedioSemanal()).setScale(2,RoundingMode.HALF_UP):0.00));
					tvIntensidadVenta.setText(String.valueOf(clienteDatosVenta!=null?clienteDatosVenta.getIntensidadCompra():0));
					String montoUltCom = String.valueOf(clienteDatosVenta!=null? BigDecimal.valueOf(clienteDatosVenta.getUltimaCompraMonto()).setScale(2,RoundingMode.HALF_UP):"0.00");
					String fechaUltCom = String.valueOf(clienteDatosVenta!=null? clienteDatosVenta.getUltimaCompraFecha():"00/00/0000");
					tvUltimaCompra.setText(montoUltCom + " - " + fechaUltCom);
					tvEscadoPreventa.setText(String.valueOf(clienteDatosVenta!=null?clienteDatosVenta.getEscadoPreventa():""));
					tvEscadoVenta.setText(String.valueOf(clienteDatosVenta!=null?clienteDatosVenta.getEscadoVenta():""));
					tvDeuda.setText(String.valueOf(BigDecimal.valueOf(clientePreventa.get_montoPendienteCredito()).setScale(2, RoundingMode.HALF_UP)));

					tvInformacionComplementaria.setText(String.valueOf(clientePreventa.get_codigo() + " - " + clientePreventa.get_referencia() + " - " + clientePreventa.get_contacto()));
					tvClienteCensado.setText(ObtenerClienteEncuestadoPor(Integer.parseInt(marker.getSnippet())));
					tvClienteNroFotos.setText(ObtenerNroFotosPorCliente(Integer.parseInt(marker.getSnippet())));
					if(clientePreventa.get_observacion().equals(""))
					{
						tvClienteObservacion.setText("");
					}
					else
					{
						tvClienteObservacion.setText(clientePreventa.get_observacion());
					}
				}

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
						if (!marker.getSnippet().equals(""))
						{
							if(marker.getTitle().substring(0,3).equals("CC-"))
							{
								clienteCensoId = Integer.parseInt(marker.getSnippet());
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
									if(parametroGeneral.is_habilitarMultiplesPreventas())
									{
										if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
										{
											if (marker.getSnippet() != "")
											{
												if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
														marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
												{
													MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
												}
												else
												{
													if(cbClienteEncuesta.isChecked())
													{
														if (marker != null)
														{
															clienteId = marker.getSnippet();
															MostrarPantallaEncuesta();
														}
													}
													else
													{
														MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
													}
												}
											}
											else
											{
												theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
											}
										}
										else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
										{
											if (marker.getSnippet() != "")
											{
												if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
														marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
												{
													MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
												}
												else
												{
													if(cbClienteEncuesta.isChecked())
													{
														if (marker != null)
														{
															clienteId = marker.getSnippet();
															MostrarPantallaEncuesta();
														}
													}
													else
													{
														MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
													}
												}
											}
											else
											{
												theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
											}
										}
										else
										{
											if (marker.getSnippet() != "")
											{
												if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
														marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
												{
													MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
												}
												else
												{
													if(cbClienteEncuesta.isChecked())
													{
														if (marker != null)
														{
															clienteId = marker.getSnippet();
															MostrarPantallaEncuesta();
														}
													}
													else
													{
														MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
													}
												}
											}
											else
											{
												theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
											}
										}
									}
									else
									{
										if(VerificarExistenciaPreventa(Integer.valueOf(marker.getSnippet())))
										{
											theUtilidades.MostrarMensaje(getApplicationContext(), "El cliente ya tiene una preventa registrada.", 1);
										}
										else
										{
											if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
											{
												if (marker.getSnippet() != "")
												{
													if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
															marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
													{
														MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
													}
													else
													{
														if(cbClienteEncuesta.isChecked())
														{
															if (marker != null)
															{
																clienteId = marker.getSnippet();
																MostrarPantallaEncuesta();
															}
														}
														else
														{
															MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
														}
													}
												}
												else
												{
													theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
												}
											}
											else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
											{
												if (marker.getSnippet() != "")
												{
													if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
															marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
													{
														MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
													}
													else
													{
														if(cbClienteEncuesta.isChecked())
														{
															if (marker != null)
															{
																clienteId = marker.getSnippet();
																MostrarPantallaEncuesta();
															}
														}
														else
														{
															MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
														}
													}
												}
												else
												{
													theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
												}
											}
											else
											{
												if (marker.getSnippet() != "")
												{
													if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
															marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
													{
														MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
													}
													else
													{
														if(cbClienteEncuesta.isChecked())
														{
															if (marker != null)
															{
																clienteId = marker.getSnippet();
																MostrarPantallaEncuesta();
															}
														}
														else
														{
															MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
														}
													}
												}
												else
												{
													theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
												}
											}
										}
									}
								}
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
					}
				}
				else
				{
					if(cbClienteEncuesta.isChecked())
					{
						if (marker != null)
						{
							clienteId = marker.getSnippet();
							MostrarPantallaEncuesta();
						}
					}
					else
					{
						if (marker != null)
						{
							if(parametroGeneral.is_habilitarMultiplesPreventas())
							{
								if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
								{
									if (marker.getSnippet() != "")
									{
										if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
												marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
										{
											MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
										}
										else
										{
											MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
										}
									}
									else
									{
										theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
									}
								}
								else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
								{
									if (marker.getSnippet() != "")
									{
										if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
												marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
										{
											MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
										}
										else
										{
											MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
										}
									}
									else
									{
										theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
									}
								}
								else
								{
									if (marker.getSnippet() != "")
									{
										if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
												marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
										{
											MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
										}
										else
										{
											MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
										}
									}
									else
									{
										theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
									}
								}
							}
							else
							{
								if(VerificarExistenciaPreventa(Integer.valueOf(marker.getSnippet())))
								{
									theUtilidades.MostrarMensaje(getApplicationContext(), "El cliente ya tiene una preventa registrada.", 1);
								}
								else
								{
									if(parametroGeneral.get_tipoImpresionFactura().equals("Medio Oficio"))
									{
										if (marker.getSnippet() != "")
										{
											if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
													marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
											{
												MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
											}
											else
											{
												MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
											}
										}
										else
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
										}
									}
									else if(parametroGeneral.get_tipoImpresionFactura().equals("Oficio"))
									{
										if (marker.getSnippet() != "")
										{
											if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
													marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
											{
												MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
											}
											else
											{
												MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
											}
										}
										else
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
										}
									}
									else
									{
										if (marker.getSnippet() != "")
										{
											if(marker.getTitle().toLowerCase(Locale.US).contains("sin nombres") ||
													marker.getTitle().toLowerCase(Locale.US).contains("sin paterno"))
											{
												MostrarPantallaActualizarCliente(Integer.parseInt(marker.getSnippet()));
											}
											else
											{
												MostrarPantallaPreventaNits(Integer.parseInt(marker.getSnippet()));
											}
										}
										else
										{
											theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontro el Id Cliente.",1);
										}
									}
								}
							}
						}
						else
						{
							theUtilidades.MostrarMensaje(getApplicationContext(),"Operacion no definida.",1);
						}
					}
				}
			}
		});
	}

	private void ObtenerParametroGeneral()
	{
		parametroGeneral = null;
	    try
	    {
	    	parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los parametros generales: " + localException.getMessage());
	    	} 
	    }
	    
	    if (parametroGeneral == null)
	    {
	        theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "No se pudo obtener los parametros generales.", 1);
	    }
	}
	
	private boolean ExistenEncuestasActivas()
	{
		ArrayList<Encuesta> listadoEncuesta = null;
		try
		{
			listadoEncuesta = new EncuestaBLL().ObtenerEncuestas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas: " + localException.getMessage());
	    	} 
		}
		
		if(listadoEncuesta == null)
		{
			encuestasActivas = false;
		}
		else
		{
			encuestasActivas = true;
		}
		
		return encuestasActivas;
	}
	
	private void ObtenerClientesCenso()
	{		
		listadoClienteCenso = null;
	   try
	    {
	    	listadoClienteCenso = new ClienteCensoBLL().ObtenerClientesCenso();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	           myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo: vacio");
	   	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes censo: " + localException.getMessage());
	    	} 
	    }
	    
	    if(listadoClienteCenso == null) 
	    {
	      theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "No se pudo obtener los clientes del censo.", 1);
	    }
	}
	
	private boolean ObtenerClientesVendedor()
	{
		listadoClientePreventa = null;
	    try
	    {
	    	listadoClientePreventa = new ClientePreventaBLL().ObtenerClientesPreventa();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes preventa: " + localException.getMessage());
	    	} 
	    }
	    
	    if (listadoClientePreventa == null)
	    {
	        return false;
	    }
	    else
	    {
	    	return true;
	    }
	}

	private void DesplegarClientesVendedor()
	{
		//String nombreNegocio;
		
		for(ClientePreventa item : listadoClientePreventa)
		{
			MarkerOptions localMarkerOptions = new MarkerOptions();
			
		    localMarkerOptions.title(String.valueOf(item.get_nombreCompleto()));
		    localMarkerOptions.snippet(String.valueOf(item.get_clienteId()));
		    localMarkerOptions.position(new LatLng(item.get_latitud(),item.get_longitud()));
		    
		    //nombreNegocio = ObtenerNombreNegocio(item.get_clienteTipoNegocioId());
		    
		    if(VerificarExistenciaPreventa(item.get_clienteId()))
		    {
				//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
				if(item.get_clienteTipoNegocioId() == 2)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_atendido));
				}
				//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
				else if(item.get_clienteTipoNegocioId() == 18)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_atendida));
				}
				//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
				else if(item.get_clienteTipoNegocioId() == 19)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_atendida));
				}
				//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
				else if(item.get_clienteTipoNegocioId() == 20)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_atendida));
				}
				//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
				else if(item.get_clienteTipoNegocioId() == 14)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_atendido));
				}
				else
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_atendido));
				}
		    }
		    else
		    {
				//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
				if(item.get_clienteTipoNegocioId() == 2)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_noatendido));
				}
				//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
				else if(item.get_clienteTipoNegocioId() == 18)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_noatendida));
				}
				//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
				else if(item.get_clienteTipoNegocioId() == 19)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_noatendida));
				}
				//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
				else if(item.get_clienteTipoNegocioId() == 20)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_noatendida));
				}
				//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
				else if(item.get_clienteTipoNegocioId() == 14)
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_noatendido));
				}
				else
				{
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_no_atendido));
				}
		    }
		    
		    if(VerificarExistenciaClienteNoAtendido(item.get_clienteId()))
		    {
		    	//if(nombreNegocio.toUpperCase().equals("ALMACEN/PULPERIAS"))
		    	if(item.get_clienteTipoNegocioId() == 2)
    			{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.almacen_visitado));
    			}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO A"))
		    	else if(item.get_clienteTipoNegocioId() == 18)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendaa_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO B"))
		    	else if(item.get_clienteTipoNegocioId() == 19)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendab_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("TIPO C"))
		    	else if(item.get_clienteTipoNegocioId() == 20)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tiendac_visitada));
		    	}
		    	//else if(nombreNegocio.toUpperCase().equals("MICROMERCADO"))
		    	else if(item.get_clienteTipoNegocioId() == 14)
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.micromercado_visitado));
		    	}
		    	else
		    	{
		    		localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_visitado));
		    	}
		    }
		    mapa.addMarker(localMarkerOptions);
		}
		
		if(parametroGeneral.is_habilitarMatcheo())
		{
			if(listadoClienteCenso != null && listadoClienteCenso.size() > 0)
			{
				for(ClienteCenso item : listadoClienteCenso)
				{
					MarkerOptions localMarkerOptions = new MarkerOptions();
					
				    localMarkerOptions.title("CC-" + item.get_codigo()+ "-" + item.get_referencia() + "-" + item.get_contacto());
				    localMarkerOptions.snippet(String.valueOf(item.get_id()));
				    localMarkerOptions.position(new LatLng(item.get_latitud(),item.get_longitud()));
				    localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cliente_censo));
				    
			        this.mapa.addMarker(localMarkerOptions);
				}
				
				if(ultimaLatitudCenso !=0 && ultimaLongitudCenso !=0)
				{
					MarkerOptions localMarkerOptions = new MarkerOptions();
					localMarkerOptions.position(new LatLng(ultimaLatitudCenso,ultimaLongitudCenso));
					localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_censo));
					this.mapa.addMarker(localMarkerOptions);
				}
			}
		}
	}

	/*private String ObtenerNombreNegocio(int tipoNegocioId)
	{
		TipoNegocio theNegocio=null;
		try
		{
			theNegocio = new TipoNegocioBLL().ObtenerTipoNegocioPor(tipoNegocioId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el tipoNegocio por tipoNegocioId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el tipoNegocio por tipoNegocioId: " + localException.getMessage());
	    	}
		}
		if(theNegocio == null)
		{
			return "";
		}
		else
		{
			return theNegocio.get_descripcion();
		}
	}*/
	
	private boolean VerificarExistenciaClienteNoAtendido(int clienteId)
	{
		boolean verificado = false;
		ClienteNoAtendido localClienteNoAtendido = null;
		try
		{
			localClienteNoAtendido = new ClienteNoAtendidoBLL().ObtenerClientesNoAtendidosPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoAtendidos: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoAtendidos: " + localException.getMessage());
	    	} 
		}
		
		if(localClienteNoAtendido != null)
		{
			verificado = true;
		}
		return verificado;
	}

	private String ObtenerNroFotosPorCliente(int clienteId)
	{
		ClienteNroFoto theClienteNroFoto=null;
		try
		{
			theClienteNroFoto = new ClienteNroFotoBLL().ObtenerClienteNroFotoPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de fotos por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el nro de fotos por clienteId: " + localException.getMessage());
	    	}
		}
		if(theClienteNroFoto == null)
		{
			return "0";
		}
		else
		{
			return String.valueOf(theClienteNroFoto.get_numero());
		}
	}
	
	private ClienteDatosVenta ObtenerClienteDatosVenta(int clienteId)
	{
		ClienteDatosVenta clienteDatosVenta = null;
		try
		{
			clienteDatosVenta = new ClienteDatosVentaBLL().ObtenerClienteDatosVentaPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente preventa por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el cliente preventa por clienteId: " + localException.getMessage());
	    	} 
			return null;
		}
		
		if(clienteDatosVenta == null)
		{
			return null;
		}
		else
		{
			return clienteDatosVenta;
		}
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
	
	private String ObtenerTipoNegocio(int tipoNegocioId)
	{
		TipoNegocio tipoNegocio = null;
		try
		{
			tipoNegocio = new TipoNegocioBLL().ObtenerTipoNegocioPor(tipoNegocioId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener el tipo de negocio por tippNegocioId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener el tipo de negocio por tipoNegocioId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(tipoNegocio == null)
		{
			return "";
		}
		else
		{
			return tipoNegocio.get_descripcion();
		}
	}
			
	private String ObtenerClienteEncuestadoPor(int clienteId)
	{
		ArrayList<ClienteEncuestado> listadoClienteEncuestado = null;
		try
		{
			listadoClienteEncuestado = new ClienteEncuestadoBLL().ObtenerClienteEncuestadoPor(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas del cliente por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas del cliente por clienteId: " + localException.getMessage());
	    	} 
			return "";
		}
		
		if(listadoClienteEncuestado != null && listadoClienteEncuestado.size() > 0)
		{
			String encuestas = "";
			for(ClienteEncuestado item : listadoClienteEncuestado)
			{
				switch(item.get_encuestaId())
				{
				case 1:
					encuestas += "Exhibidor:SI, ";
					break;
				case 2:
					encuestas += "Astrix:SI, ";
					break;
				case 3:
					encuestas += "Food:SI, ";
					break;
				}
			}
			return encuestas;
		}
		else
		{
			return "Exhibidor:NO, Astrix:NO, Foods:NO";
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
	
	private boolean VerificarExistenciaPreventa(int clienteId)
	{
		Preventa preventa = null;
		try
		{
			preventa = new PreventaBLL().ObtenerPreventaPorClienteId(clienteId);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener la preventa por clienteId: " + localException.getMessage());
	    	}
		}
		
		if(preventa == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
 	private void MostrarPantallaPreventaNits(int clienteId)
	{
	    Intent localIntent = null;
	    localIntent = new Intent(this, Vendedorpreventanits.class);
	    localIntent.putExtra("clienteId", clienteId);
	    localIntent.putExtra("origenSolicitud", "Preventa");
	    startActivity(localIntent);
	}
	
	private void MostrarPantallaActualizarCliente(int clienteId)
	{
		Intent localIntent = new Intent(this, Vendedoractualizacioncliente.class);
		localIntent.putExtra("ClienteId", clienteId);
		startActivity(localIntent);
	}
	
	private void MostrarPantallaUnificacion()
	{
		if(clienteCensoId==0)
		{
			theUtilidades.MostrarMensaje(this, "Debe establecer al menos la coordenada del cliente censo.", 1);
		}
		else
		{
			Intent intent = new Intent(this, Censistaunificacionclientes.class);
			intent.putExtra("ClienteCensoId", clienteCensoId);
			intent.putExtra("ClienteVenderId", clienteVenderId);
			intent.putExtra("Origen", "Ventas");
			startActivity(intent);
		}
	}
	
	private void MostrarPantallaEncuesta()
	{
		listadoEncuestas = null;
		try
		{
			listadoEncuestas = new EncuestaBLL().ObtenerEncuestas();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener las encuestas: " + localException.getMessage());
	    	} 
		}
		
		if(listadoEncuestas == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext().getApplicationContext(), "No se encontraron encuestas que desplegar.", 1);
			return;
		}
		
		if(listadoEncuestas.size() == 1)
		{
			if(listadoEncuestas.get(0).get_encuestaId() == 1)
			{
				MostrarEncuestaMatcheo();
			}
			else if(listadoEncuestas.get(0).get_encuestaId() == 2)
			{
				MostrarEncuestaAstrix();
			}
			else if(listadoEncuestas.get(0).get_encuestaId() == 3)
			{
				MostrarEncuestaFoods();
			}
		}
		else
		{
			if(listadoEncuestas.size() > 1)
			{
				dialog = new Dialog(this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.diseniodialogo_vendedorencuestas);
				dialog.setTitle("Menu encuestas");
				dialog.setCancelable(false);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 102, 153)));
				
				ListView lvEncuestas = (ListView)dialog.findViewById(R.id.lvDisDiaVenEncEncuestas);
				
				Button btnCancelar = (Button)dialog.findViewById(R.id.btnDisDiaVenEncCancelar);
				btnCancelar.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						switch(v.getId())
						{
						case R.id.btnDisDiaVenEncCancelar:
							if(dialog.isShowing())
							{
								dialog.dismiss();
							}
							break;
						}
					}
				});
				
				MiAdaptadorEncuestaLista localMiAdaptadorEncuestaLista = new MiAdaptadorEncuestaLista(this);
	    	    
			    if(listadoEncuestas == null)
			    {
			    	lvEncuestas.setAdapter(null);
			    }
			    else
			    {
				    ViewGroup.LayoutParams localLayoutParams = lvEncuestas.getLayoutParams();
				    localLayoutParams.height = ((int)(35*getApplicationContext().getResources().getDisplayMetrics().density) * listadoEncuestas.size());
				    lvEncuestas.setLayoutParams(localLayoutParams);
				    lvEncuestas.setAdapter(localMiAdaptadorEncuestaLista);
			    }
			    
			    OnClickItemListView();				
				
				dialog.show();
			}
		}
	}

	class MiAdaptadorEncuestaLista extends ArrayAdapter<Encuesta>
	{
		private Context _context;
		
		public MiAdaptadorEncuestaLista(Context context)
		{
			super(context,R.layout.diseniodialogo_vendedorencuestas,listadoEncuestas);
			this._context = context;
		}
    
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View localView = convertView;
			
			if (localView == null) 
			{				
				LayoutInflater layoutInflater =	(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		localView = layoutInflater.inflate(R.layout.disenio_vendedorsincronizacionpreventa, parent, false);
			}
			
			TextView tvEncuesta = (TextView)localView.findViewById(R.id.tvVendedorSincronizacionPreventaNombre);
			
			Encuesta localEncuesta = (Encuesta)listadoEncuestas.get(position);
			
			tvEncuesta.setText(localEncuesta.get_nombre());
			
			return localView;
		}
	}
	
	private void OnClickItemListView()
	{
		((ListView)dialog.findViewById(R.id.lvDisDiaVenEncEncuestas)).setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id)
			{
				Encuesta localEncuesta = listadoEncuestas.get(position);
				int encuestaId = localEncuesta.get_encuestaId();
				switch(encuestaId)
				{
				case 1:
					MostrarEncuestaMatcheo();
					break;
				case 2:
					MostrarEncuestaAstrix();
					break;
				case 3:
					MostrarEncuestaFoods();
					break;
				}
			}
		});
	}
	
	private void MostrarEncuestaMatcheo()
	{
		Intent intent = new Intent(this ,Vendedorencuesta.class);
		intent.putExtra("ClienteId", Integer.valueOf(clienteId));
		startActivity(intent);
	} 
	
	private void MostrarEncuestaAstrix()
	{
		Intent intent = new Intent(this, Vendedorencuestapdv.class);
		intent.putExtra("ClienteId", Integer.valueOf(clienteId));
		startActivity(intent);
	}
	
	private void MostrarEncuestaFoods()
	{
		Intent intent = new Intent(this, Vendedorencuestafoods.class);
		intent.putExtra("ClienteId", Integer.valueOf(clienteId));
		startActivity(intent);
	}

	public void onBackPressed() 
	{
		Intent intent = new Intent(this, Menuvendedor.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
