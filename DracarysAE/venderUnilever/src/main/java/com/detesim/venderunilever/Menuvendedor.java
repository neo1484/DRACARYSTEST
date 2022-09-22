package com.detesim.venderunilever;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import BLL.AlmacenBLL;
import BLL.AlmacenPOPBLL;
import BLL.AlmacenPOPProductoBLL;
import BLL.AlmacenProductoBLL;
import BLL.ApkRutaClienteBLL;
import BLL.AsignacionRutaBLL;
import BLL.AvanceVentaBLL;
import BLL.AvanceVentaVendedorBLL;
import BLL.CanalRutaBLL;
import BLL.CanalRutaPrecioBLL;
import BLL.CategoriaBLL;
import BLL.CategoriaPOPBLL;
import BLL.CierrePreventistaBLL;
import BLL.CiudadBLL;
import BLL.ClienteBLL;
import BLL.ClienteCensoBLL;
import BLL.ClienteDatosVentaBLL;
import BLL.ClienteEncuestadoBLL;
import BLL.ClienteNitBLL;
import BLL.ClienteNroFotoBLL;
import BLL.ClientePreventaBLL;
import BLL.CobroPendienteBLL;
import BLL.CondicionTributariaBLL;
import BLL.DistribuidorBLL;
import BLL.DosificacionProveedorBLL;
import BLL.DraRebateSaldoBLL;
import BLL.EncuestaBLL;
import BLL.EncuestaDetalleBLL;
import BLL.EncuestaListaBLL;
import BLL.FotoCategoriaBLL;
import BLL.MercadoBLL;
import BLL.MotivoCambioBLL;
import BLL.MotivoEliminacionMatchBLL;
import BLL.MotivoNoAtencionBLL;
import BLL.MotivoPopBLL;
import BLL.MyLogBLL;
import BLL.ParametroGeneralBLL;
import BLL.PrecioListaBLL;
import BLL.PrecioListaNombreBLL;
import BLL.PreventaBLL;
import BLL.ProductoBLL;
import BLL.ProductoCambioBLL;
import BLL.ProductoCostoBLL;
import BLL.ProductoPOPBLL;
import BLL.ProductoPOPCostoBLL;
import BLL.PromocionBLL;
import BLL.PromocionCostoBLL;
import BLL.PromocionPrecioBLL;
import BLL.PromocionPrecioListaBLL;
import BLL.PromocionProductoBLL;
import BLL.PromocionTipoNegocioBLL;
import BLL.ProntoPagoCanalRutaBLL;
import BLL.ProntoPagoClienteBLL;
import BLL.ProntoPagoJerarquiaBLL;
import BLL.ProntoPagoUniBLL;
import BLL.ProveedorBLL;
import BLL.ProveedorPrecioListaMargenBLL;
import BLL.ProvinciaBLL;
import BLL.RolBLL;
import BLL.SincronizacionDatosBLL;
import BLL.TipoNegocioBLL;
import BLL.TipoNitBLL;
import BLL.VendedorZonaVentaBLL;
import BLL.ZonaBLL;
import BLL.ZonaMercadoBLL;
import Clases.AES;
import Clases.AlmacenPOPProductoWSResult;
import Clases.AlmacenPOPWSResult;
import Clases.AlmacenProductoWSResult;
import Clases.AlmacenTempWSResult;
import Clases.ApkClienteWS;
import Clases.ApkRutaClienteWSResult;
import Clases.AsignacionRutaWSResult;
import Clases.AvanceVendedorDiaWSResult;
import Clases.AvanceVentaVendedorWSResult;
import Clases.CanalRutaPrecioWSResult;
import Clases.CanalRutaWSResult;
import Clases.CategoriaPOPWSResult;
import Clases.CategoriaWSResult;
import Clases.CiudadWSResult;
import Clases.Cliente;
import Clases.ClienteCensoWSResult;
import Clases.ClienteDatosVentaJS;
import Clases.ClienteDatosVentaWS;
import Clases.ClienteEncuestadoWSResult;
import Clases.ClienteNitWSResult;
import Clases.ClienteNroFotoWSResult;
import Clases.ClienteWSResult;
import Clases.CobroPendienteWSResult;
import Clases.CondicionTributariaWSResult;
import Clases.CostoWSResult;
import Clases.DistribuidorWSResult;
import Clases.DosificacionProveedorWSResult;
import Clases.DraRebateSaldoWSResult;
import Clases.EncuestaDetalleWSResult;
import Clases.EncuestaListaWSResult;
import Clases.EncuestaWSResult;
import Clases.Fecha;
import Clases.FotoCategoriaWSResult;
import Clases.JSonParser;
import Clases.LoginEmpleado;
import Clases.MercadoWSResult;
import Clases.MotivoCambioWSResult;
import Clases.MotivoEliminacionMatchWSResult;
import Clases.MotivoNoAtencionWSResult;
import Clases.MotivoPopWSResult;
import Clases.ParametroGeneral;
import Clases.ParametroGeneralWSResult;
import Clases.ParseJSon;
import Clases.PrecioListaNombreWSResult;
import Clases.PrecioWSResult;
import Clases.Preventa;
import Clases.ProductoCambioWSResult;
import Clases.ProductoPOPCostoWSResult;
import Clases.ProductoPOPWSResult;
import Clases.ProductoWSResult;
import Clases.PromocionCostoWSResult;
import Clases.PromocionPrecioListWSResult;
import Clases.PromocionPrecioWSResult;
import Clases.PromocionProductoWSResult;
import Clases.PromocionTipoNegocioWSResult;
import Clases.PromocionWSResult;
import Clases.ProntoPagoCanalRutaWSResult;
import Clases.ProntoPagoClienteWSResult;
import Clases.ProntoPagoJerarquiaWSResult;
import Clases.ProntoPagoUniWSResult;
import Clases.ProveedorPrecioListaMargenWSResult;
import Clases.ProveedorWSResult;
import Clases.ProvinciaWSResult;
import Clases.Rol;
import Clases.SincronizacionDatos;
import Clases.SingleFechaWS;
import Clases.SingleId;
import Clases.TipoNegocioWSResult;
import Clases.TipoNitWSResult;
import Clases.Ubicacion;
import Clases.VendedorDiaRutaWS;
import Clases.VendedorDiaWS;
import Clases.VendedorFechaWS;
import Clases.VendedorZonaVentaWSResult;
import Clases.ZonaMercadoWSResult;
import Clases.ZonaWSResult;
import Utilidades.Utilidades;

public class Menuvendedor  extends Activity implements View.OnClickListener
{
    Utilidades theUtilidades = new Utilidades();
    String NAMESPACE = theUtilidades.get_NAMESPACE();
    String URLUNILEVER = theUtilidades.get_URLUNILEVER();
    String URL = this.theUtilidades.get_URL();
    String URLJSON = this.theUtilidades.get_URLJSON();
    MyLogBLL myLog = new MyLogBLL();

    LinearLayout llMenuVendedor;
    TextView tvUsuario;
    ImageView ivVendedorSincronizarDatos;
    ImageView ivVendedorRegistrarPreventa;
    ImageView ivVendedorSincronizarPreventa;
    ImageView ivVendedorCierrePreventa;
    ImageView ivVendedorAltaCliente;
    ImageView ivVendedorVerLogs;
    ImageView ivVendedorAvanceVentasDia;
    ImageView ivVendedorAvanceVentasMes;
    ImageView ivVendedorAvanceVentasMesCategoria;
    ImageView ivVendedorMetasSap;
    ImageView ivVendedorAvanceVentaVendedorMes;
    ImageView ivVendedorSincronizarNombres;
    ImageView ivVendedorSincronizarNoAtendidos;
    ImageView ivVendedorVisualizarPreventas;
    ImageView ivVendedorRestaurarPreventas;
    ImageView ivVendedorInventarioProductos;
    ImageView ivVendedorEdicionPreventas;
    ImageView ivVendedorVentaDirecta;
    ImageView ivVendedorVisorVentasDirectas;
    ImageView ivVendedorCobroPendiente;
    ImageView ivVendedorSincroEdClientes;
    ImageView ivVendedorSincroFotos;
    ImageView ivVendedorSincronizarPOP;
    ImageView ivVendedorSincronizarCambios;
    ImageView ivVendedorSincronizarMatcheo;
    ImageView ivVendedorSincronizarEdiClientes;
    ProgressDialog pdEsperaObtenerInfo;

    LoginEmpleado loginEmpleado;
    SincronizacionDatos sincronizacionDatos;
    ParametroGeneral parametroGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuvendedor);

        llMenuVendedor = (LinearLayout)findViewById(R.id.MenuVendedorLayout);
        tvUsuario = (TextView)findViewById(R.id.tvMenuVendedorUsuarios);
        ivVendedorSincronizarDatos = ((ImageView)findViewById(R.id.ivMenuVendedorSincronizarDatos));
        ivVendedorSincronizarDatos.setOnClickListener(this);
        ivVendedorRegistrarPreventa = ((ImageView)findViewById(R.id.imgbtnMenuVendedorRegistrarPreventas));
        ivVendedorRegistrarPreventa.setOnClickListener(this);
        ivVendedorSincronizarPreventa = ((ImageView)findViewById(R.id.imgbtnMenuVendedorSincronizarPreventas));
        ivVendedorSincronizarPreventa.setOnClickListener(this);
        ivVendedorCierrePreventa = ((ImageView)findViewById(R.id.imgbtnMenuVendedorCerrarPreventas));
        ivVendedorCierrePreventa.setOnClickListener(this);
        ivVendedorSincronizarEdiClientes = ((ImageView)findViewById(R.id.imgbtnMenuVendedorSincroEdiClientes));
        ivVendedorSincronizarEdiClientes.setOnClickListener(this);
        ivVendedorAltaCliente = (ImageView)findViewById(R.id.imgbtnMenuVendedorAltaCliente);
        ivVendedorAltaCliente.setOnClickListener(this);
        ivVendedorVerLogs = ((ImageView)findViewById(R.id.imgbtnMenuVendedorVerLogs));
        ivVendedorVerLogs.setOnClickListener(this);
        ivVendedorAvanceVentasDia = (ImageView)findViewById(R.id.imgbtnMenuVendedorAvanceVentasDia);
        ivVendedorAvanceVentasDia.setOnClickListener(this);
        ivVendedorAvanceVentasMes = (ImageView)findViewById(R.id.imgbtnMenuVendedorAvanceVentasMes);
        ivVendedorAvanceVentasMes.setOnClickListener(this);
        ivVendedorAvanceVentasMesCategoria = (ImageView)findViewById(R.id.imgbtnMenuVendedorAvanceVentaMesVendedor);
        ivVendedorAvanceVentasMesCategoria.setOnClickListener(this);
        ivVendedorMetasSap = (ImageView)findViewById(R.id.imgBtnMenuVendedorMetasSap);
        ivVendedorMetasSap.setOnClickListener(this);
        ivVendedorSincronizarNombres = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincronizarNombres);
        ivVendedorSincronizarNombres.setOnClickListener(this);
        ivVendedorSincronizarNoAtendidos = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincronizarNoAtendidos);
        ivVendedorSincronizarNoAtendidos.setOnClickListener(this);
        ivVendedorVisualizarPreventas = (ImageView)findViewById(R.id.imgbtnMenuVendedorVisualizarPreventas);
        ivVendedorVisualizarPreventas.setOnClickListener(this);
        ivVendedorRestaurarPreventas = (ImageView)findViewById(R.id.imgbtnMenuVendedorRestaurarPreventas);
        ivVendedorRestaurarPreventas.setOnClickListener(this);
        ivVendedorInventarioProductos = (ImageView)findViewById(R.id.imgbtnMenuVendedorInventarioProductos);
        ivVendedorInventarioProductos.setOnClickListener(this);
        ivVendedorEdicionPreventas = (ImageView)findViewById(R.id.imgbtnMenuVendedorEdicionPreventas);
        ivVendedorEdicionPreventas.setOnClickListener(this);
        ivVendedorVentaDirecta = (ImageView)findViewById(R.id.imgbtnMenuVendedorVentaDirecta);
        ivVendedorVentaDirecta.setOnClickListener(this);
        ivVendedorSincroFotos = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincroFotos);
        ivVendedorSincroFotos.setOnClickListener(this);
        ivVendedorSincroEdClientes = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincroEdiClientes);
        ivVendedorSincroEdClientes.setOnClickListener(this);
        ivVendedorVisorVentasDirectas = (ImageView)findViewById(R.id.imgbtnMenuVendedorVisualizarVentasDirectas);
        ivVendedorVisorVentasDirectas.setOnClickListener(this);
        ivVendedorCobroPendiente = (ImageView)findViewById(R.id.imgbtnMenuVendedorCobrosPendientes);
        ivVendedorCobroPendiente.setOnClickListener(this);
        ivVendedorSincronizarPOP = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincronizarPOP);
        ivVendedorSincronizarPOP.setOnClickListener(this);
        ivVendedorSincronizarCambios = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincronizarCambios);
        ivVendedorSincronizarCambios.setOnClickListener(this);
        ivVendedorSincronizarMatcheo = (ImageView)findViewById(R.id.imgbtnMenuVendedorSincronizarMatcheo);
        ivVendedorSincronizarMatcheo.setOnClickListener(this);

        llMenuVendedor.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));

        loginEmpleado = null;
        try
        {
            loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado preventista: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado preventista: " + localException.getMessage());
            }
        }

        if(loginEmpleado == null)
        {
            theUtilidades.MostrarMensaje(getApplicationContext(), "El preventista no se logeo correctamente.", 1);
            MostrarControles(false);
            return;
        }
        else
        {
            tvUsuario.setText(loginEmpleado.get_empleadoNombre());
            ObtenerParametroGeneral();
            MostrarControles(true);
        }

        MostrarControlesEspeciales();

        if(!theUtilidades.ValidarFecha(loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio()))
        {
            theUtilidades.MostrarMensaje(getApplicationContext(), "La fecha del celular, no concuerda con la del servidor.", 1);
            MostrarControles(false);
            return;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.ivMenuVendedorSincronizarDatos:
                if(VerificarSincronizacionClientes())
                {
                    if(VerificarSincronizacionPreventas())
                    {
                        SincronizarDatos();
                    }
                    else
                    {
                        theUtilidades.MostrarMensaje(getApplicationContext(),"Sincronice las preventas no sincronizadas.",1);
                        return;
                    }
                }
                else
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(),"Sincronice los clientes no sincronizados.",1);
                    return;
                }

                break;
            case R.id.imgbtnMenuVendedorRegistrarPreventas:
                if(ValidarIngreso())
                {
                    MostrarPantallaMapaPreventas();
                }
                break;
            case R.id.imgbtnMenuVendedorSincronizarPreventas:
                MostrarPantallaSincronizarPreventas();
                break;
            case R.id.imgbtnMenuVendedorCerrarPreventas:
                MostrarPantallaCerrarPreventas();
                break;
            case R.id.imgbtnMenuVendedorSincroEdiClientes:
                MostrarPantallaSincroEdiClientes();
                break;
            case R.id.imgbtnMenuVendedorAltaCliente:
                MostrarPantallaSincroEdiClientes();
                MostrarMenuCensista();
                break;
            case R.id.imgbtnMenuVendedorVerLogs:
                MostrarPantallaVerLogs();
                break;
            case R.id.imgbtnMenuVendedorAvanceVentasDia:
                MostrarPantallaAvancesVentaDia();
                break;
            case R.id.imgbtnMenuVendedorAvanceVentasMes:
                MostrarPantallaAvancesVentaMes();
                break;
            case R.id.imgbtnMenuVendedorAvanceVentaMesVendedor:
                MostrarPantallaAvancesVentaMesVendedor();
                break;
            case R.id.imgBtnMenuVendedorMetasSap:
                MostrarPantallaMetasSap();
                break;
            case R.id.imgbtnMenuVendedorSincronizarNombres:
                if(VerificarExistenciaClientesNoSincronizados())
                {
                    MostrarPantallaSincronizacionNombreCliente();
                }
                else
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(), "Primero debe sincronizar los clientes del men? censista.", 1);
                }
                break;
            case R.id.imgbtnMenuVendedorSincronizarNoAtendidos:
                MostrarPantallaSincronizacionClienteNoAtendidos();
                break;
            case R.id.imgbtnMenuVendedorVisualizarPreventas:
                MostrarPantallaVisualizarPreventas();
                break;
            case R.id.imgbtnMenuVendedorRestaurarPreventas:
                MostrarPantallaRestauracionPreventa();
                break;
            case R.id.imgbtnMenuVendedorVisualizarVentasDirectas:
                MostrarPantallaVisorVentasDirectas();
                break;
            case R.id.imgbtnMenuVendedorInventarioProductos:
                MostrarPantallaInventarioProductos();
                break;
            case R.id.imgbtnMenuVendedorEdicionPreventas:
                MostrarPantallaEdicionPreventas();
                break;
            case R.id.imgbtnMenuVendedorVentaDirecta:
                MostrarPantallaVentaDirecta();
                break;
            case R.id.imgbtnMenuVendedorCobrosPendientes:
                MostrarPantallaCobroPendiente();
                break;
            case R.id.imgbtnMenuVendedorSincronizarPOP:
                MostrarPantallaSincronizarPOP();
                break;
            case R.id.imgbtnMenuVendedorSincronizarCambios:
                MostrarPantallaSincronizarCambios();
                break;
            case R.id.imgbtnMenuVendedorSincroFotos:
                MostrarPantallaSincronizarFotos();
                break;
            case R.id.imgbtnMenuVendedorSincronizarMatcheo:
                MostrarPantallaSincronizarMatcheo();
                break;
        }
    }

    private void ObtenerParametroGeneral()
    {
        parametroGeneral = null;
        try
        {
            parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametro general: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener el parametro general: " + localException.getMessage());
            }
        }
    }

    private boolean VerificarSincronizacionClientes()
    {
        boolean verificado = true;
        ArrayList<Cliente> listadoCliente=null;

        try
        {
            listadoCliente = new ClienteBLL().ObtenerClientesNoSincronizados();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: " + localException.getMessage());
            }
        }

        if(listadoCliente != null)
        {
            verificado = false;
        }
        return verificado;
    }

    private boolean VerificarSincronizacionPreventas()
    {
        boolean verificado = true;
        ArrayList<Preventa> listadoPreventa=null;

        try
        {
            listadoPreventa = new PreventaBLL().ObtenerPreventasNoSincronizadas();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener las preventas no sincronizadas: " + localException.getMessage());
            }
        }

        if(listadoPreventa != null)
        {
            verificado = false;
        }
        return verificado;
    }

    public boolean ValidarIngreso()
    {
        try
        {
            new Ubicacion(this);
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al inicilizar la ubicacionGPS: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error el inicilizar la ubicacionGPS: " + localException.getMessage());
            }
            return false;
        }
        if(VerificarSincronizacionDatos())
        {
            if(VerificarCierrePreventista())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El cierre del d?a ya fue registrado, no puede ingresar m?s preventas.", 1);
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            theUtilidades.MostrarMensaje(getApplicationContext(), "Debe sincronizar los datos, para ingresar a la preventa.", 1);
            return false;
        }
    }

    private boolean VerificarCierrePreventista()
    {
        boolean verificado = false;
        try
        {
            verificado = new CierrePreventistaBLL().VerificarCierrePreventista(loginEmpleado.get_empleadoId(),
                    loginEmpleado.get_anio(),loginEmpleado.get_mes(),loginEmpleado.get_dia());
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al verificar el cierrePreventista: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error el verificar el cierrePreventista: " + localException.getMessage());
            }
        }
        return verificado;
    }

    public void SincronizarDatos()
    {
        if(theUtilidades.VerificarConexionInternet(this))
        {
            MostrarControles(false);
            //Si el rol es vendedor
            if(loginEmpleado.get_vendedorRutaId()>0)
            {
                if(VerificarSincronizacionClientesRolCensista())
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(), "Existen clientes sin sincronizar, int?ntelo luego de sincronizarlos.", 1);
                    return;
                }
                else
                {
                    ObtenerListadoClientesVentasByVendedorYRuta();
                }
            }
            else
            {
                ObtenerListadoClientesVentasByVendedorYRuta();
            }
        }
        else
        {
            theUtilidades.MostrarMensaje(getApplicationContext(), "No existe conexi?n a internet, int?ntelo m?s tarde.", 1);
            return;
        }
    }

    private boolean VerificarSincronizacionClientesRolCensista()
    {
        ArrayList<Cliente> listadoCliente = null;
        boolean verificado = false;
        try
        {
            listadoCliente = new ClienteBLL().ObtenerClientesNoSincronizados();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientesNoSincronizados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar los clientesNoSincronizados: " + localException.getMessage());
            }
        }

        if(listadoCliente != null)
        {
            verificado = true;
        }
        return verificado;
    }

    //Descargamos las opciones REQUERIDAS

    private void ObtenerListadoClientesVentasByVendedorYRuta()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo clientes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerClientesApkByVendedorYDia localWSObtenerClientesVentasByVendedorYDia = new WSObtenerClientesApkByVendedorYDia();

        try
        {
            localWSObtenerClientesVentasByVendedorYDia.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerClientesVentasByVendedorYDia: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerClientesVentasByVendedorYDia: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerClientesVentasByVendedorYDia.", 1);
        }
    }

    private class WSObtenerClientesApkByVendedorYDia  extends AsyncTask<Void, Integer, Boolean>
    {
        String CLIENTEAPK_METHOD_NAME = "UNI_GetClientesApkByVendedorYDia";
        String CLIENTEAPK_SOAP_ACTION = NAMESPACE + CLIENTEAPK_METHOD_NAME;
        boolean WSObtenerClientesVentasByVendedorYDia;
        ArrayList<ApkClienteWS> apkClientes;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerClientesVentasByVendedorYDia = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEAPK_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(CLIENTEAPK_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ApkClienteWS>>(){ }.getType();
                    apkClientes = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerClientesVentasByVendedorYDia = (apkClientes.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerClientesVentasByVendedorYDia = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice ObtenerClientesVentasByVendedorYDia: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice ObtenerClientesVentasByVendedorYDia: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerClientesApkByVendedorYDia no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerClientesVentasByVendedorYDia)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes que descargar. " + error, 1);
                return;
            }

            if (!BorrarClientesSinPreventa())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new ClientePreventaBLL().InsertarClientePreventa( apkClientes );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar el cliente: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar lel cliente: " + localException.getMessage());
                }
            }

            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Clientes Apk Del Vendedor Y Dia.", 1);
                return;
            }

            ObtenerProveedoresByVendedor();
        }
    }

    private boolean BorrarClientesSinPreventa()
    {
        try
        {
            return new ClientePreventaBLL().BorrarClientesSinPreventa();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesSinPreventa: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesSinPreventa: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProveedoresByVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo proveedores ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProveedoresByVendedor localWSObtenerProveedores = new WSObtenerProveedoresByVendedor();

        try
        {
            localWSObtenerProveedores.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerProveedores: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerProveedores: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSOntenerProveedores.", 1);
        }
    }

    private class WSObtenerProveedoresByVendedor extends AsyncTask<Void, Integer, Boolean>
    {
        String PROVEEDOR_METHOD_NAME = "GetProveedoresByVendedor";
        String PROVEEDOR_SOAP_ACTION = NAMESPACE + PROVEEDOR_METHOD_NAME;
        boolean WSObtenerProveedores = false;
        ArrayList<ProveedorWSResult> proveedorWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProveedores = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PROVEEDOR_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerProveedores = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerProveedores);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PROVEEDOR_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProveedorWSResult>>(){ }.getType();
                    proveedorWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProveedores = (proveedorWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerProveedores = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProveedores: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProveedores: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProveedoresByVendedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProveedores )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron proveedores que descargar. " + error, 1);
                return;
            }

            if (!BorrarProveedores())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los proveedores.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProveedorBLL().InsertarProveedor( proveedorWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Proveedores Del Vendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Proveedores Del Vendedor: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Proveedores Del Vendedor.", 1);
                return;
            }

            ObtenerProductosByVendedor();
        }

    }

    private boolean BorrarProveedores()
    {
        try
        {
            return new ProveedorBLL().BorrarProveedores();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedores: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los proveedores: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProductosByVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo productos ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProductosByVendedor localWSObtenerProductosByVendedor = new WSObtenerProductosByVendedor();

        try
        {
            localWSObtenerProductosByVendedor.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerProductosByVendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerProductosByVendedor: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerProductosByVendedor.", 1);
        }
    }

    private class WSObtenerProductosByVendedor extends AsyncTask<Void, Integer, Boolean>
    {
        String PRODUCTO_METHOD_NAME = "GetProductosByVendedor";
        String PRODUCTO_SOAP_ACTION = NAMESPACE + PRODUCTO_METHOD_NAME;
        boolean WSObtenerProductos = false;
        ArrayList<ProductoWSResult> productoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProductos = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTO_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerProductos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerProductos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(PRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProductoWSResult>>(){ }.getType();
                    productoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProductos = (productoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerProductos = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductos: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductos: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosByVendedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProductos )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos que descargar. " + error, 1);
                return;
            }

            if (!BorrarProductos())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos Del Vendedor.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProductoBLL().InsertarProducto( productoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos Del Vendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos Del Vendedor: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos Del Vendedor.", 1);
                return;
            }
            ObtenerPrecios();

        }
    }

    public boolean BorrarProductos()
    {
        try
        {
            return new ProductoBLL().BorrarProductos();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPrecios()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo precios ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);


        WSObtenerPrecios localWSObtenerPrecios = new WSObtenerPrecios();
        try
        {
            localWSObtenerPrecios.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosLista: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosLista: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreciosLista.", 1);
        }
    }

    private class WSObtenerPrecios extends AsyncTask<Void, Integer, Boolean>
    {
        String PRECIOLISTA_METHOD_NAME = "GetPreciosListaByAlmacen";
        String PRECIOLISTA_SOAP_ACTION = NAMESPACE + PRECIOLISTA_METHOD_NAME;
        boolean WSObtenerListadosPrecio = false;
        ArrayList<PrecioWSResult> precioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerListadosPrecio = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRECIOLISTA_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerAlmacenes = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerAlmacenes);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(PRECIOLISTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PrecioWSResult>>(){ }.getType();
                    precioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerListadosPrecio = (precioWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerListadosPrecio = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPrecioLista: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPrecioLista: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPrecios no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerListadosPrecio)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron precios que descargar. " + error, 1);
                return;
            }

            if (!BorrarPrecios())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Precios.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PrecioListaBLL().InsertarPrecioLista( precioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Precios: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Precios: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Precios.", 1);
                return;
            }
            ObtenerCostos();

        }
    }

    private boolean BorrarPrecios()
    {
        try
        {
            return new PrecioListaBLL().BorrarPreciosLista();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCostos()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo costos ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);


        WSObtenerCostos localWSObtenerCostos = new WSObtenerCostos();
        try
        {
            localWSObtenerCostos.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProdustosActuales: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosActuales: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreciosLista.", 1);
        }
    }

    private class WSObtenerCostos extends AsyncTask<Void, Integer, Boolean>
    {
        String COSTOS_METHOD_NAME = "GetProductosActualesByAlmacen";
        String COSTOS_SOAP_ACTION = NAMESPACE + COSTOS_METHOD_NAME;
        boolean WSObtenerListadoCostos = false;
        ArrayList<CostoWSResult> costoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerListadoCostos = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,COSTOS_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerCostos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerCostos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(COSTOS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CostoWSResult>>(){ }.getType();
                    costoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerListadoCostos = (costoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerListadoCostos = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosActuales: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosActuales: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerCostos no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerListadoCostos )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron costos que descargar. " + error, 1);
                return;
            }

            if (!BorrarCostos())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Costos.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProductoCostoBLL().InsertarProductoCosto( costoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Costos: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Costos: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Costos.", 1);
                return;
            }
            ObtenerAlmacenes();

        }
    }

    private boolean BorrarCostos()
    {
        try
        {
            return new ProductoCostoBLL().BorrarProductosCosto();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los costos: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAlmacenes()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo almacenes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerAlmacenes localWSObtenerAlmacenes = new WSObtenerAlmacenes();
        try
        {
            localWSObtenerAlmacenes.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar webservice WSGeAlmacenes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar webservice WSGeAlmacenes: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAlmacenes.", 1);
        }
    }

    private class WSObtenerAlmacenes extends AsyncTask<Void, Integer, Boolean>
    {
        String ALMACEN_METHOD_NAME = "GetAlmacenTemp";
        String ALMACEN_SOAP_ACTION = NAMESPACE + ALMACEN_METHOD_NAME;
        boolean WSObtenerAlmacen = false;
        String error;
        AlmacenTempWSResult almacenTempWSResult;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAlmacen = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACEN_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerAlmacenes = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerAlmacenes);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(ALMACEN_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<AlmacenTempWSResult>(){ }.getType();
                    almacenTempWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAlmacen = (almacenTempWSResult != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerAlmacen = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetAlmacenTemp: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetAlmacenTemp: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerAlmacenes no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerAlmacen )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron almacenes que descargar. " + error, 1);
                return;
            }

            if (!BorrarAlmacenes())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Almacenes.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new AlmacenBLL().InsertarAlmacen( almacenTempWSResult );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Almacenes: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Almacenes: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Almacenes.", 1);
                return;
            }
            ObtenerAlmacenProducto();
        }
    }

    private boolean BorrarAlmacenes()
    {
        try
        {
            return new AlmacenBLL().BorrarAlmacenes();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenes: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAlmacenProducto()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo productos del almacen ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerAlmacenProducto localWSObtenerAlmacenProducto = new WSObtenerAlmacenProducto();
        try
        {
            localWSObtenerAlmacenProducto.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetProductosByAlmacenTemp: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetProductosByAlmacenTemp: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosByAlmacenTemp.", 1);
        }
    }

    private class WSObtenerAlmacenProducto extends AsyncTask<Void, Integer, Boolean>
    {
        String ALMACENPRODUCTO_METHOD_NAME = "GetProductosByAlmacenTemp";
        String ALMACENPRODUCTO_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTO_METHOD_NAME;
        boolean WSObtenerAlmacenProducto = false;
        ArrayList<AlmacenProductoWSResult> almacenProductoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAlmacenProducto = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTO_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerProductos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerProductos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(ALMACENPRODUCTO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<AlmacenProductoWSResult>>(){ }.getType();
                    almacenProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAlmacenProducto = (almacenProductoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerAlmacenProducto = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetProductosByAlmacenTemp: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetProductosByAlmacenTemp: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProductosByalmacenTemp no se ejecuto correctamente. ", 1);
                return;
            }

            if(!WSObtenerAlmacenProducto) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos en el almacen que descargar. " + error, 1);
                return;
            }

            if(!BorrarAlmacenesProducto()) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los productos del almacen.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new AlmacenProductoBLL().InsertarAlmacenProducto( almacenProductoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Almacen Producto: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Almacen Producto: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Almacen Producto.", 1);
                return;
            }
            ObtenerParametrosGenerales();
        }
    }

    private boolean BorrarAlmacenesProducto()
    {
        try
        {
            return new AlmacenProductoBLL().BorrarAlmacenProductos();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenes producto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los almacenes producto: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerParametrosGenerales()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo parametros generales ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerParametrosGenerales localWSObtenerParametrosGenerales = new WSObtenerParametrosGenerales();

        try
        {
            localWSObtenerParametrosGenerales.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerParametrosGenerales: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSObtenerParametrosGenerales: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerParametrosGenerales.", 1);
        }
    }

    private class WSObtenerParametrosGenerales extends AsyncTask<Void, Integer, Boolean>
    {
        String PARAMETROGENERAL_METHOD_NAME = "GetParametroGeneral";
        String PARAMETROGENERAL_SOAP_ACTION = NAMESPACE + PARAMETROGENERAL_METHOD_NAME;

        boolean WSObtenerParametrosGenerales;
        ParametroGeneralWSResult parametroGeneralWSResult;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerParametrosGenerales = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PARAMETROGENERAL_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerParametros = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerParametros);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(PARAMETROGENERAL_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<ParametroGeneralWSResult>(){ }.getType();
                    parametroGeneralWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerParametrosGenerales = (parametroGeneralWSResult != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerParametrosGenerales = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerParametrosGenerales no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerParametrosGenerales )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron parametros generales que descargar. " + error, 1);
                return;
            }

            if (!BorrarParametrosGenerales())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Parametros Generales.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ParametroGeneralBLL().InsertarParametroGeneral( parametroGeneralWSResult );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Parametros Generales: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Parametros Generales: " + localException.getMessage());
                }
            }
            parametroGeneral = null;
            try
            {
                parametroGeneral = new ParametroGeneralBLL().ObtenerParamatroGeneral();
            }
            catch(Exception localException)
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

            if(parametroGeneral == null)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el parametro general.", 1);
            }
            else
            {
                ObtenerVendedorZonaVenta();
            }
        }
    }

    private boolean BorrarParametrosGenerales()
    {
        try
        {
            return new ParametroGeneralBLL().BorrarParametrosGenerales();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los parametros generales: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerVendedorZonaVenta()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo rutas cliente ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetVendedorZonaVenta localWSGetVendedorZonaVenta = new WSGetVendedorZonaVenta();

        try
        {
            localWSGetVendedorZonaVenta.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetZonaVentaVendedorByVendedorAndDiaVisita: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetZonaVentaVendedorByVendedorAndDiaVisita: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice GetZonaVentaVendedorByVendedorAndDiaVisita.", 1);
        }
    }

    private class WSGetVendedorZonaVenta extends AsyncTask<Void, Integer, Boolean>
    {
        String VENZONVEN_METHOD_NAME = "UNI_GetZonaVentaVendedorByVendedorAndDiaVisita";
        String VENZONVEN_SOAP_ACTION = NAMESPACE + VENZONVEN_METHOD_NAME;

        boolean WSObtenerVenZonVen;
        ArrayList<VendedorZonaVentaWSResult> vendedorZonaVentaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerVenZonVen = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,VENZONVEN_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(VENZONVEN_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<VendedorZonaVentaWSResult>>(){ }.getType();
                    vendedorZonaVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerVenZonVen = (vendedorZonaVentaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerVenZonVen = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonaVentaVendedorByVendedorAndDiaVisita: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonaVentaVendedorByVendedorAndDiaVisita: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetVendedorZonaVenta no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerVenZonVen )
            {
                Fecha fecha = theUtilidades.ObtenerFecha();
                if(fecha.get_diaSemana() == 1)
                {
                    ObtenerAsignacionesVendedor();
                }
                else
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron rutas que descargar. " + error, 1);
                    return;
                }
                return;
            }

            if (!BorrarVendedorZonaVenta())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las zonas del venta del vendedor.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new VendedorZonaVentaBLL().InsertarVendedorZonaVenta( vendedorZonaVentaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar la zona de venta del vendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar la zona de venta del vendedor: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las zonas de venta del vendedor.", 1);
                return;
            }
            ObtenerAsignacionesVendedor();
        }
    }

    private boolean BorrarVendedorZonaVenta()
    {
        try
        {
            return new VendedorZonaVentaBLL().BorrarVendedoresZonaVenta();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas del vendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las rutas del vendedor: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAsignacionesVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo asignaciones vendedor ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetAsignacionesVendedor localWSGetAsignacionesVendedor = new WSGetAsignacionesVendedor();

        try
        {
            localWSGetAsignacionesVendedor.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetAsignacionByVendedorYDia: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el GetAsignacionByVendedorYDia: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice GetAsignacionByVendedorYDia.", 1);
        }
    }

    private class WSGetAsignacionesVendedor extends AsyncTask<Void, Integer, Boolean>
    {
        String ASIVEN_METHOD_NAME = "UNI_GetAsignacionByVendedorYDia";
        String ASIVEN_SOAP_ACTION = NAMESPACE + ASIVEN_METHOD_NAME;

        boolean WSObtenerAsiVen;
        ArrayList<AsignacionRutaWSResult> vendedorZonaVentaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAsiVen = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ASIVEN_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerAsignaciones = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerAsignaciones);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(ASIVEN_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<AsignacionRutaWSResult>>(){ }.getType();
                    vendedorZonaVentaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAsiVen = (vendedorZonaVentaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerAsiVen = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice UNI_GetAsignacionByVendedorYDia: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice UNI_GetAsignacionByVendedorYDia: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAsignacionesVendedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerAsiVen)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron asignaciones de ruta que descargar. " + error, 1);
                return;
            }

            if (!BorrarAsiVen())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las asignaciones del vendedor.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new AsignacionRutaBLL().InsertarAsignacionRuta( vendedorZonaVentaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las asignaciones del vendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las asignaciones del vendedor: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las asignaciones del vendedor.", 1);
                return;
            }
            ObtenerCategorias();
        }
    }

    private boolean BorrarAsiVen()
    {
        try
        {
            return new AsignacionRutaBLL().BorrarAsignacionesRuta();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las asignaciones rutas del vendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las asignaciones rutas del vendedor: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCategorias()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo categorias ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetCategorias localWSGetCategorias = new WSGetCategorias();

        try
        {
            localWSGetCategorias.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategorias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategorias: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCategorias.", 1);
        }
    }

    private class WSGetCategorias  extends AsyncTask<Void, Integer, Boolean>
    {
        String CATEGORIAS_METHOD_NAME = "GetCategorias";
        String CATEGORIAS_SOAP_ACTION = NAMESPACE + CATEGORIAS_METHOD_NAME;

        boolean WSObtenerCategorias;
        ArrayList<CategoriaWSResult> categoriaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerCategorias = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CATEGORIAS_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerCategorias = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerCategorias);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(CATEGORIAS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CategoriaWSResult>>(){ }.getType();
                    categoriaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerCategorias = (categoriaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerCategorias = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCategorias: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCategorias: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCategorias no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerCategorias)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron categorias que descargar. " + error, 1);
                return;
            }

            if (!BorrarCategorias())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las categorias.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new CategoriaBLL().InsertarCategoria( categoriaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las ategorias: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los categorias: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las categorias.", 1);
                return;
            }
            ObtenerMotivosNoAtencion();

        }
    }

    private boolean BorrarCategorias()
    {
        try
        {
            return new CategoriaBLL().BorrarCategorias();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las categorias: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerMotivosNoAtencion()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo motivos no atencion ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetMotivosNoAtencionApk localWSGetMotivosNoAtencionApk = new WSGetMotivosNoAtencionApk();

        try
        {
            localWSGetMotivosNoAtencionApk.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosNoAtencionApk: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosNoAtencionApk: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMotivosNoAtencionApk.", 1);
        }
    }

    private class WSGetMotivosNoAtencionApk  extends AsyncTask<Void, Integer, Boolean>
    {
        String MOTIVOSNOATENCION_METHOD_NAME = "GetMotivosNoAtencionApk";
        String MOTIVOSNOATENCION_SOAP_ACTION = NAMESPACE + MOTIVOSNOATENCION_METHOD_NAME;

        boolean WSObtenerMotivosNoAtencion;
        ArrayList<MotivoNoAtencionWSResult> motivoNoAtencionWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerMotivosNoAtencion = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,MOTIVOSNOATENCION_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerMotivos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerMotivos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(MOTIVOSNOATENCION_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<MotivoNoAtencionWSResult>>(){ }.getType();
                    motivoNoAtencionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerMotivosNoAtencion = (motivoNoAtencionWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerMotivosNoAtencion = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMotivosNoAtencionApk: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMotivosNoAtencionApk: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetMotivosNoAtencionApk no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerMotivosNoAtencion )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron motivos de no atencion que descargar. " + error, 1);
                return;
            }

            if (!BorrarMotivosNoAtencion())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los motivos de no atencion.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new MotivoNoAtencionBLL().InsertarMotivoNoAtencion( motivoNoAtencionWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los motivos de no atencion: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar losmotivos de no atencion: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los motivos de no atencion.", 1);
                return;
            }
            ObtenerTipoNegocio();
        }
    }

    private boolean BorrarMotivosNoAtencion()
    {
        try
        {
            return new MotivoNoAtencionBLL().BorrarMotivosNoAtencion();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos no atencion: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos no atencion: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerTipoNegocio()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando tipos de negocio ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerTipoNegocio wsObtenerTipoNegocio = new WSObtenerTipoNegocio();

        try
        {
            wsObtenerTipoNegocio.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerTipoNegocio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerTipoNegocio: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerTipoNegocio.", 1);
        }
    }

    private class WSObtenerTipoNegocio extends AsyncTask<Void, Integer, Boolean>
    {
        String TIPONEGOCIO_METHOD_NAME = "GetTiposNegocio";
        String TIPONEGOCIO_SOAP_ACTION = NAMESPACE + TIPONEGOCIO_METHOD_NAME;

        boolean WSObtenerTipoNegocio = false;
        ArrayList<TipoNegocioWSResult> tipoNegocioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerTipoNegocio = false;

            SoapObject localSoapObject1 = new SoapObject(NAMESPACE,TIPONEGOCIO_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerTipos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject1.addProperty("givendata", encriptedObtenerTipos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.TIPONEGOCIO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<TipoNegocioWSResult>>(){ }.getType();
                    tipoNegocioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerTipoNegocio = (tipoNegocioWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocio: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerTipoNegocio no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerTipoNegocio )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No existen tipos de negocio que descargar. " + error, 1);
                return;
            }

            if (!borrarTiposNegocio())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Tipo Negocio.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new TipoNegocioBLL().InsertarTipoNegocio( tipoNegocioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Tipo Negocio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Tipo Negocio: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Tipo Negocio.", 1);
                return;
            }
            ObtenerDosificacionProveedor();

        }
    }

    private boolean borrarTiposNegocio()
    {
        TipoNegocioBLL theBLL = new TipoNegocioBLL();
        boolean borrado = false;
        try
        {
            borrado = theBLL.BorrarTiposNegocio();
            return borrado;
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPermisosByEmpleado: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerDosificacionProveedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando dosificaciones de los proveedores ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerDosificacionProveedor wsObtenerDosificacionProveedor = new WSObtenerDosificacionProveedor();

        try
        {
            wsObtenerDosificacionProveedor.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDosificacionesActivas.", 1);
        }
    }

    private class WSObtenerDosificacionProveedor extends AsyncTask<Void, Integer, Boolean>
    {
        String DOSIFICACIONPROVEEDOR_METHOD_NAME = "GetRegistrosActivos";
        String DOSIFICACIONPROVEEDOR_SOAP_ACTION = NAMESPACE + DOSIFICACIONPROVEEDOR_METHOD_NAME;

        boolean WSDosificacionProveedor = false;
        ArrayList<DosificacionProveedorWSResult> dosificacionProveedorWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSDosificacionProveedor = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,DOSIFICACIONPROVEEDOR_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerRegistros = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerRegistros);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.DOSIFICACIONPROVEEDOR_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<DosificacionProveedorWSResult>>(){ }.getType();
                    dosificacionProveedorWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSDosificacionProveedor = (dosificacionProveedorWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDosificacionesActivas: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerDosificacionProveedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSDosificacionProveedor)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No existe ni una dosificacion de los proveedores. " + error, 1);
                return;
            }

            if (!borrarDosificacionesProveedor())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar la dosificacion  del proveedor.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new DosificacionProveedorBLL().InsertarDosificacionProveedor( dosificacionProveedorWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Dosificacion Proveedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Dosificacion Proveedor: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Dosificacion Proveedor.", 1);
                return;
            }
            ObtenerCanalesRuta();
        }
    }

    private boolean borrarDosificacionesProveedor()
    {
        DosificacionProveedorBLL theBLL = new DosificacionProveedorBLL();
        boolean borrado = false;
        try
        {
            borrado = theBLL.BorrarDosifiacionesProveedor();
            return borrado;
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones proveedor: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCanalesRuta()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo canales ruta ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetCanalesRuta localWSGetCanalesRuta = new WSGetCanalesRuta();

        try
        {
            localWSGetCanalesRuta.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRuta: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRuta: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCanalesRuta.", 1);
        }
    }

    private class WSGetCanalesRuta extends AsyncTask<Void, Integer, Boolean>
    {
        String CANALRUTA_METHOD_NAME = "GetCanalesRuta";
        String CANALRUTA_SOAP_ACTION = NAMESPACE + CANALRUTA_METHOD_NAME;

        boolean GetCanalesRuta;
        ArrayList<CanalRutaWSResult> canalRutaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetCanalesRuta = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CANALRUTA_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerCanales = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerCanales);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(CANALRUTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CanalRutaWSResult>>(){ }.getType();
                    canalRutaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetCanalesRuta = (canalRutaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetCanalesRuta = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCanalesRuta: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetCanalesRuta: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCanalesRuta no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetCanalesRuta )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron canales de ruta que descargar. " + error, 1);
                return;
            }

            if (!BorrarCanalesRuta())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los Canales Ruta.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new CanalRutaBLL().InsertarCanalRuta( canalRutaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales de ruta: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales de ruta: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los canales de ruta.", 1);
                return;
            }
            ObtenerApkRutas();
        }
    }

    private boolean BorrarCanalesRuta()
    {
        try
        {
            return new CanalRutaBLL().BorrarCanalesRuta();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerApkRutas()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando rutas ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerApkRutas wsObtenerRutas = new WSObtenerApkRutas();
        try
        {
            wsObtenerRutas.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetApkRutas: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerRutas.", 1);
        }
    }

    private class WSObtenerApkRutas extends AsyncTask<Void, Integer, Boolean>
    {
        String APKRUTA_METHOD_NAME = "GetApkRutas";
        String APKRUTA_SOAP_ACTION = NAMESPACE + APKRUTA_METHOD_NAME;

        boolean WSObtenerApkRuta = false;
        ArrayList<ApkRutaClienteWSResult> apkRutaClienteWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerApkRuta = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,APKRUTA_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(APKRUTA_SOAP_ACTION,localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ApkRutaClienteWSResult>>(){ }.getType();
                    apkRutaClienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerApkRuta = (apkRutaClienteWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRutas: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecucion)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecucion)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo ejecutar el WSGetApkRutas",2);
                return;
            }

            if(!WSObtenerApkRuta)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No existen rutas que descargar. " + error,2);
                return;
            }

            if(!BorrarApkRutas())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No existen rutas que descargar.",2);
                return;
            }

            long l = 0;
            try
            {
                l = new ApkRutaClienteBLL().insertarApkRutaCliente( apkRutaClienteWSResults );
            }
            catch(Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al insertar las rutas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al insertar las rutas: " + localException.getMessage());
                }
            }

            if(l<=0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo insertar la ruta.",2);
            }

            ObtenerTiposNit();
        }
    }

    private boolean BorrarApkRutas()
    {
        try
        {
            return new ApkRutaClienteBLL().BorrarApksRutaCliente();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apk rutas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las apk rutas: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerTiposNit()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo tipos NIT ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetTiposNit localWSGetTiposNit = new WSGetTiposNit();

        try
        {
            localWSGetTiposNit.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTiposNit: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTiposNit: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetTiposNit.", 1);
        }
    }

    private class WSGetTiposNit extends AsyncTask<Void, Integer, Boolean>
    {
        String GETTIPOSNIT_METHOD_NAME = "GetTiposNit";
        String GETTIPOSNIT_SOAP_ACTION = NAMESPACE + GETTIPOSNIT_METHOD_NAME;

        boolean GetTiposNit;
        ArrayList<TipoNitWSResult> tipoNitWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetTiposNit= false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETTIPOSNIT_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerTipos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerTipos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETTIPOSNIT_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<TipoNitWSResult>>(){ }.getType();
                    tipoNitWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetTiposNit = (tipoNitWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetTiposNit = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTiposVenta: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTiposVenta: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetTiposNit no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetTiposNit)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de nit que descargar. " + error, 1);
                return;
            }

            if (!BorrarTiposNit())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los tipos de nit.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new TipoNitBLL().InsertarTipoNit( tipoNitWSResults );
            }
            catch (Exception localException) {
                if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los tipos de nit: vacio");
                } else {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los tipos de nit: " + localException.getMessage());
                }
            }

            if (i <= 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los tipos de nit.", 1);
                return;
            }

            if(parametroGeneral.is_edicionCliente())
            {
                ObtenerClientesVendedor();
            }
            else
            {
                ObtenerClientesNroFoto();
            }
        }
    }

    private boolean BorrarTiposNit()
    {
        try
        {
            return new TipoNitBLL().BorrarTiposNit();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos nit: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los tipos nit: " + localException.getMessage());
            }
            return false;
        }
    }

    //Parametros Edicion de clientes

    private void ObtenerClientesVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando clientes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerClientesVendedor wsObtenerClientesVendedor = new WSObtenerClientesVendedor();

        try
        {
            wsObtenerClientesVendedor.execute();

        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesByVendedorYDiaVisita.", 1);
        }
    }

    private class WSObtenerClientesVendedor extends AsyncTask<Void, Integer, Boolean>
    {
        String CLIENTEDIAVISITA_METHOD_NAME = "UNI_GetClientesByVendedorYDiaVisita";
        String CLIENTEDIAVISITA_SOAP_ACTION = NAMESPACE + CLIENTEDIAVISITA_METHOD_NAME;

        boolean WSObtenerClienteDiaVisita = false;
        ArrayList<ClienteWSResult> clienteWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerClienteDiaVisita = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTEDIAVISITA_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerClientes);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.CLIENTEDIAVISITA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ClienteWSResult>>(){ }.getType();
                    clienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerClienteDiaVisita = (clienteWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesByVendedorYDiaVisita: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if (pdEsperaObtenerInfo.isShowing()) {
                pdEsperaObtenerInfo.dismiss();
            }

            if (!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerClientesVendedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerClienteDiaVisita) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron clientes que descargar. " + error, 1);
                return;
            }

            if (!BorrarClientesDiaVisita()) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Clientes Vendedor.", 1);
                return;
            }

            Fecha fecha = theUtilidades.ObtenerFecha();
            long i = 0;
            try {
                i = new ClienteBLL().InsertarCliente( clienteWSResults, fecha );
            } catch (Exception localException) {
                if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Clientes Vendedor: vacio");
                } else {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Clientes Vendedor: " + localException.getMessage());
                }
            }

            if (i <= 0) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Clientes Vendedor.", 1);
                return;
            }

            ObtenerFotosCategoria();
        }
    }

    private boolean BorrarClientesDiaVisita()
    {
        try
        {
            return new ClienteBLL().BorrarClientes();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerFotosCategoria()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando categorias de la fotos ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerFotosCategoria wsObtenerFotosCategoria = new WSObtenerFotosCategoria();

        try
        {
            wsObtenerFotosCategoria.execute();

        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategorias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategorias: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClienteFotoCategoria.", 1);
        }
    }

    private class WSObtenerFotosCategoria extends AsyncTask<Void, Integer, Boolean>
    {
        String FOTOCATEGORIA_METHOD_NAME = "GetClienteFotoCategorias";
        String FOTOCATEGORIA_SOAP_ACTION = NAMESPACE + FOTOCATEGORIA_METHOD_NAME;

        boolean WSObtenerFotoCategoria = false;
        ArrayList<FotoCategoriaWSResult> fotoCategoriaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerFotoCategoria = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,FOTOCATEGORIA_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerFotos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerFotos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.FOTOCATEGORIA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<FotoCategoriaWSResult>>(){ }.getType();
                    fotoCategoriaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerFotoCategoria = (fotoCategoriaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategoria: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClienteFotoCategoria: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerFotosCategoria no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerFotoCategoria )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se obtuvieron categorias de las fotos que descargar. " + error, 1);
                return;
            }

            if (!BorrarFotosCategoria())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Fotos Categoria.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new FotoCategoriaBLL().InsertarFotoCategoria( fotoCategoriaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Fotos Categoria: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Fotos Categoria: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Fotos Categoria.", 1);
                return;
            }
            ObtenerZonas();
        }
    }

    private boolean BorrarFotosCategoria()
    {
        try
        {
            return new FotoCategoriaBLL().BorrarFotosCategoria();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotosCategoria: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las fotosCategoria: " + localException.getMessage());
            }
            return false;
        }
    }

    //Descargamos las opciones configuradas

    private void ObtenerZonas()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando zonas ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerZonas wsObtenerZonas = new WSObtenerZonas();

        try
        {
            wsObtenerZonas.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerZonas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerZonas: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerZonas.", 1);
        }
    }

    private class WSObtenerZonas extends AsyncTask<Void, Integer, Boolean>
    {
        String ZONA_METHOD_NAME = "GetZonas";
        String ZONA_SOAP_ACTION = NAMESPACE + ZONA_METHOD_NAME;

        boolean WSObtenerZona = false;
        ArrayList<ZonaWSResult> zonaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerZona = false;

            SoapObject localSoapObject1 = new SoapObject(NAMESPACE,ZONA_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerZonas = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject1.addProperty("givendata", encriptedObtenerZonas);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(this.ZONA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ZonaWSResult>>(){ }.getType();
                    zonaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerZona = (zonaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonas: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerZonas no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerZona)
            {
                if(parametroGeneral.is_zonaRequerida())
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(),"No existen zonas que descargar. " + error,2);
                    return;
                }
                else
                {
                    ObtenerMercados();
                }
                return;
            }

            if (!BorrarZonas())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Zonas.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ZonaBLL().InsertarZona( zonaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Zonas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Zonas: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Zonas.", 1);
                return;
            }
            ObtenerMercados();
        }
    }

    private boolean BorrarZonas()
    {
        try
        {
            return new ZonaBLL().BorrarZonas();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerMercados()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo mercados ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetMercados localWSGetMercados = new WSGetMercados();

        try
        {
            localWSGetMercados.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMercados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMercados: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMercados.", 1);
        }
    }

    private class WSGetMercados extends AsyncTask<Void, Integer, Boolean>
    {
        String MERCADOS_METHOD_NAME = "GetMercados";
        String MERCADOS_SOAP_ACTION = NAMESPACE + MERCADOS_METHOD_NAME;
        boolean WSObtenerMercados;
        ArrayList<MercadoWSResult> mercadoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerMercados = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,MERCADOS_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerMercados = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerMercados);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(MERCADOS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<MercadoWSResult>>(){ }.getType();
                    mercadoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerMercados = (mercadoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerMercados = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMercados: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMercados: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetMercados no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerMercados)
            {
                if(parametroGeneral.is_mercadoRequerido())
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron mercados que descargar. " + error, 1);
                    return;
                }
                else
                {
                    ObtenerZonasMercado();
                }
                return;
            }

            if (!BorrarMercados())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los mercados.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new MercadoBLL().InsertarMercado( mercadoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los mercados: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los mercados: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los mercados.", 1);
                return;
            }
            ObtenerZonasMercado();

        }
    }

    private boolean BorrarMercados()
    {
        try
        {
            return new MercadoBLL().BorrarMercados();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los mercados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los mercados: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerZonasMercado()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo zonas mercado ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetZonasMercado localWSGetZonasMercado = new WSGetZonasMercado();

        try
        {
            localWSGetZonasMercado.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonasMercado: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetZonasMercado: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetZonasMercado.", 1);
        }
    }

    private class WSGetZonasMercado extends AsyncTask<Void, Integer, Boolean>
    {
        String ZONASMERCADO_METHOD_NAME = "GetZonaMercados";
        String ZONASMERCADO_SOAP_ACTION = NAMESPACE + ZONASMERCADO_METHOD_NAME;

        boolean WSZonasMercado;
        ArrayList<ZonaMercadoWSResult> zonaMercadoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSZonasMercado = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ZONASMERCADO_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerZonas = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerZonas);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(ZONASMERCADO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ZonaMercadoWSResult>>(){ }.getType();
                    zonaMercadoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSZonasMercado = (zonaMercadoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSZonasMercado = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonasMercado: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetZonasMercado: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetZonasMercado no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSZonasMercado )
            {
                if(parametroGeneral != null && parametroGeneral.is_zonaMercadoRequerida())
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron zonas mercado que descargar. " + error, 1);
                }
                else
                {
                    ObtenerCiudades();
                }
                return;
            }

            if (!BorrarZonasMercado())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las zoonas de mercado.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new ZonaMercadoBLL().InsertarZonaMercado( zonaMercadoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las zonas de mercado: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las zonas de mercado: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las zoonas de mercado.", 1);
                return;
            }
            ObtenerCiudades();
        }
    }

    private boolean BorrarZonasMercado()
    {
        try
        {
            return new ZonaMercadoBLL().BorrarZonasMercado();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas mercado: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las zonas mercado: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCiudades()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando ciudades ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerCiudades WSObtenerCiudades = new WSObtenerCiudades();

        try
        {
            WSObtenerCiudades.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCiudades.", 1);
        }
    }

    private class WSObtenerCiudades extends AsyncTask<Void, Integer, Boolean>
    {
        String CIUDADES_METHOD_NAME = "GetCiudades";
        String CIUDADES_SOAP_ACTION = NAMESPACE + CIUDADES_METHOD_NAME;

        boolean WSObtenerCiudades = false;
        ArrayList<CiudadWSResult> ciudadWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerCiudades = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CIUDADES_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerCiudades = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerCiudades);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(CIUDADES_SOAP_ACTION,localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CiudadWSResult>>(){ }.getType();
                    ciudadWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerCiudades = (ciudadWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCiudades: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerCiudades no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerCiudades)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No existen ciudades que descargar. " + error, 1);
                return;
            }

            if (!BorrarCiudades())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Ciudades.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new CiudadBLL().InsertarCiudad( ciudadWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Ciudades: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Ciudades: " + localException.getMessage());
                }
            }

            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Ciudades.", 1);
                return;
            }

            ObtenerProvincias();
        }
    }

    private boolean BorrarCiudades()
    {
        try
        {
            return new CiudadBLL().BorrarCiudades();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ciudades: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las ciudades: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProvincias()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando provincias ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProvincias WSObtenerProvincias = new WSObtenerProvincias();

        try
        {
            WSObtenerProvincias.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProvincias.", 1);
        }
    }

    private class WSObtenerProvincias extends AsyncTask<Void, Integer, Boolean>
    {
        String PROVINCIAS_METHOD_NAME = "GetProvincias";
        String PROVINCIAS_SOAP_ACTION = NAMESPACE + PROVINCIAS_METHOD_NAME;

        boolean WSObtenerProvincias = false;
        ArrayList<ProvinciaWSResult> provinciaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProvincias = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PROVINCIAS_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerProvincias= new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerProvincias);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(PROVINCIAS_SOAP_ACTION,localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProvinciaWSResult>>(){ }.getType();
                    provinciaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProvincias = (provinciaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProvincias: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProvincias no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProvincias)
            {
                if(parametroGeneral.is_provinciaRequerida())
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(), "No existen provincias que descargar. " + error, 2);
                    return;
                }
                else
                {
                    ObtenerPreciosLista();
                }
                return;
            }

            if (!BorrarProvincias())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Provincias.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new ProvinciaBLL().InsertarProvincia( provinciaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Provincias: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Provincias: " + localException.getMessage());
                }
            }

            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Provincias.", 1);
                return;
            }

            ObtenerPreciosLista();
        }
    }

    private boolean BorrarProvincias()
    {
        try
        {
            return new ProvinciaBLL().BorrarProvincias();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las provincias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las provincias: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPreciosLista()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo listas de precio ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetPreciosLista localWSGetPreciosLista = new WSGetPreciosLista();

        try
        {
            localWSGetPreciosLista.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosLista: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosLista: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPreciosLista.", 1);
        }
    }

    private class WSGetPreciosLista extends AsyncTask<Void, Integer, Boolean>
    {
        String PRECIOLISTA_METHOD_NAME = "GetPreciosLista";
        String PRECIOLISTA_SOAP_ACTION = NAMESPACE + PRECIOLISTA_METHOD_NAME;

        boolean WSPrecioLista;
        ArrayList<PrecioListaNombreWSResult> precioListaNombreWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSPrecioLista = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRECIOLISTA_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerPrecios = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerPrecios);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PRECIOLISTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PrecioListaNombreWSResult>>(){ }.getType();
                    precioListaNombreWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSPrecioLista = (precioListaNombreWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSPrecioLista = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosLista: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosLista: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPreciosLista no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSPrecioLista)
            {
                if(parametroGeneral != null && parametroGeneral.is_mostrarListaPrecio())
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron precios de lista que descargar. " + error, 1);
                }
                else
                {
                    ObtenerClientesNroFoto();
                }
                return;
            }

            if (!BorrarPreciosLista())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los precios de lista.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PrecioListaNombreBLL().InsetarPrecioListaNombre( precioListaNombreWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de lista: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de lista: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los precios de lista.", 1);
                return;
            }
            ObtenerClientesNroFoto();
        }
    }

    private boolean BorrarPreciosLista()
    {
        try
        {
            return new PrecioListaNombreBLL().BorrarPreciosListaNombre();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios de lista: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los precios de lista: " + localException.getMessage());
            }
            return false;
        }
    }

    //Descargamos las opciones NO requeridas

    private void ObtenerClientesNroFoto()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo clientes nro foto ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetClientesNroFoto localWSGetClientesNroFoto = new WSGetClientesNroFoto();

        try
        {
            localWSGetClientesNroFoto.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesNroFoto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesNroFoto: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesNroFoto.", 1);
        }
    }

    private class WSGetClientesNroFoto extends AsyncTask<Void, Integer, Boolean>
    {
        String GETNROFOTO_METHOD_NAME = "GetNumeroFotosByClientesApk";
        String GETNROFOTO_SOAP_ACTION = NAMESPACE + GETNROFOTO_METHOD_NAME;

        boolean GetNroFoto;
        ArrayList<ClienteNroFotoWSResult> clienteNroFotoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetNroFoto= false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETNROFOTO_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerNumeros = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerNumeros);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETNROFOTO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ClienteNroFotoWSResult>>(){ }.getType();
                    clienteNroFotoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetNroFoto = (clienteNroFotoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetNroFoto = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNroFoto: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNroFoto: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetClientesNroFoto no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetNroFoto )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron nros de foto que descargar. " + error, 1);
                ObtenerCanalesRutaPrecio();
                return;
            }

            if (!BorrarClientesNroFoto())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los nros de foto de los clientes.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ClienteNroFotoBLL().InsertarClienteNroFoto( clienteNroFotoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nros de foto de los clientes: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nros de foto de los clientes: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los nros de foto de los clientes.", 1);
                return;
            }
            ObtenerCanalesRutaPrecio();
        }
    }

    private boolean BorrarClientesNroFoto()
    {
        try
        {
            return new ClienteNroFotoBLL().BorrarClientesNroFoto();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes nro foto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes nro foto: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCanalesRutaPrecio()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo canales ruta precio ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetCanalesRutaPrecio localWSGetCanalesRutaPrecio = new WSGetCanalesRutaPrecio();

        try
        {
            localWSGetCanalesRutaPrecio.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRutaPrecio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCanalesRutaPrecio: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetCanalesRutaPrecio.", 1);
        }
    }

    private class WSGetCanalesRutaPrecio extends AsyncTask<Void, Integer, Boolean>
    {
        String GETRUTAPRECIO_METHOD_NAME = "GetRutasPrecioByAlmacen";
        String GETRUTAPRECIO_SOAP_ACTION = NAMESPACE + GETRUTAPRECIO_METHOD_NAME;

        boolean GetRutaPrecio;
        ArrayList<CanalRutaPrecioWSResult> canalRutaPrecioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetRutaPrecio= false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETRUTAPRECIO_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerPrecios = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerPrecios);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETRUTAPRECIO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CanalRutaPrecioWSResult>>(){ }.getType();
                    canalRutaPrecioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetRutaPrecio = (canalRutaPrecioWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetRutaPrecio = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRutasPrecioByAlmacen: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRutasPrecioByAlmacen: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetCanalesRutaPrecio no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetRutaPrecio)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No se encontraron canales ruta precio que descargar. " + error, 1);
                ObtenerPromociones();
                return;
            }

            if (!BorrarRutasPrecios())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los canales de ruta de precio.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new CanalRutaPrecioBLL().InsertarCanalRutaPrecio( canalRutaPrecioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales de ruta de precio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales de ruta de precio: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los canales de ruta de precio.", 1);
                return;
            }
            ObtenerPromociones();
        }
    }

    private boolean BorrarRutasPrecios()
    {
        try
        {
            return new CanalRutaPrecioBLL().BorrarCanalesRutaPrecio();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta precio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los canales ruta precio: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPromociones()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo promociones ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerPromociones localWSObtenerPromociones = new WSObtenerPromociones();
        try
        {
            localWSObtenerPromociones.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromociones: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromociones: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromociones.", 1);
        }
    }

    private class WSObtenerPromociones extends AsyncTask<Void, Integer, Boolean>
    {
        String PROMOCION_METHOD_NAME = "GetPromocionesActivasYCerradasByAlmacen";
        String PROMOCION_SOAP_ACTION = NAMESPACE + PROMOCION_METHOD_NAME;

        boolean WSObtenerPromociones;
        ArrayList<PromocionWSResult> promocionWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerPromociones = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PROMOCION_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(PROMOCION_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PromocionWSResult>>(){ }.getType();
                    promocionWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerPromociones = (promocionWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerPromociones = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromociones: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPromociones no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerPromociones )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron promociones que descargar. " + error, 1);
                ObtenerPromocionProductos();
                return;
            }

            if (!BorrarPromociones())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Promociones.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new PromocionBLL().InsertarPromocion( promocionWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Promociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Promociones: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Promociones.", 1);
                return;
            }
            ObtenerPromocionProductos();
        }
    }

    private boolean BorrarPromociones()
    {
        try
        {
            return new PromocionBLL().BorrarPromociones();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPromocionProductos()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo productos de la promocion ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerPromocionProductos localWSObtenerPromocionProductos = new WSObtenerPromocionProductos();

        try
        {
            localWSObtenerPromocionProductos.execute();
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProductos: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProductos: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromocionesProducto.", 1);
        }
    }

    private class WSObtenerPromocionProductos extends AsyncTask<Void, Integer, Boolean>
    {
        String PROMOCIONDETALLE_METHOD_NAME = "GetPromocionProductosByAlmacen";
        String PROMOCIONDETALLE_SOAP_ACTION = NAMESPACE + PROMOCIONDETALLE_METHOD_NAME;

        boolean WSObtenerPromocionesDetalle;
        ArrayList<PromocionProductoWSResult> promocionProductoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerPromocionesDetalle = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PROMOCIONDETALLE_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerProductos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerProductos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
            try
            {
                localHttpTransportSE.call(PROMOCIONDETALLE_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PromocionProductoWSResult>>(){ }.getType();
                    promocionProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerPromocionesDetalle = (promocionProductoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerPromocionesDetalle = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el webservice WSGetPromocionProdcutos: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerPromocionProductos no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerPromocionesDetalle)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos de la promocion que descargar. " + error, 1);
                ObtenerAvancesDiaByVendedor();
                return;
            }

            if (!BorrarPromocionProductos())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Promocion Productos.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PromocionProductoBLL().InsertarPromocionProducto( promocionProductoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Promocion Productos: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Promocion Productos: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Promocion Productos.", 1);
                return;
            }

            ObtenerAvancesDiaByVendedor();
        }
    }

    private boolean BorrarPromocionProductos()
    {
        try
        {
            return new PromocionProductoBLL().BorrarPromocionesProducto();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones producto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones producto: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAvancesDiaByVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo avance ventas por dia ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetAvancesByVendedorDia localWSGetAvancesByVendedorDia = new WSGetAvancesByVendedorDia();

        try
        {
            localWSGetAvancesByVendedorDia.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesByVendedorDia: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesByVendedorDia: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesByVendedorDia.", 1);
        }
    }

    private class WSGetAvancesByVendedorDia  extends AsyncTask<Void, Integer, Boolean>
    {
        String AVANCESDIA_METHOD_NAME = "GetAvanceByVendedorDia";
        String AVANCESDIA_SOAP_ACTION = NAMESPACE + AVANCESDIA_METHOD_NAME;

        boolean WSObtenerAvancesDia;
        ArrayList<AvanceVendedorDiaWSResult> avanceVendedorDiaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAvancesDia = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESDIA_METHOD_NAME);
            VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
                                                        loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
            String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerAvances);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(AVANCESDIA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<AvanceVendedorDiaWSResult>>(){ }.getType();
                    avanceVendedorDiaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAvancesDia = (avanceVendedorDiaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerAvancesDia = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesByVendedorDia: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesByVendedorDia: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesByVendedorDia no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerAvancesDia)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances del vendedor por dia que descargar. " + error, 1);
                ObtenerAvancesMesByVendedor();
                return;
            }

            if (!BorrarAvancesVentaDia())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los avances del vendedor dia.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new AvanceVentaBLL().InsertarAvanceVenta( avanceVendedorDiaWSResults, 0);
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances del vendedor dia: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances del vendedor dia: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener avances del vendedor dia.", 1);
                return;
            }
            ObtenerAvancesMesByVendedor();
        }
    }

    private boolean BorrarAvancesVentaDia()
    {
        try
        {
            return new AvanceVentaBLL().BorrarAvancesVentaPorTipoAvanceVentaYRol("DIA","Vendedor");
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por dia del vendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta por dia del vendedor: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAvancesMesByVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo ventas por mes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetAvancesVentaByVendedorMes localWSGetAvancesVentaByVendedorMes = new WSGetAvancesVentaByVendedorMes();

        try
        {
            localWSGetAvancesVentaByVendedorMes.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesVentasByVendedorMes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesVentaByVendedorMes: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesByVendedorMes.", 1);
        }
    }

    private class WSGetAvancesVentaByVendedorMes  extends AsyncTask<Void, Integer, Boolean>
    {
        String AVANCESMES_METHOD_NAME = "GetAvanceByVendedorMes";
        String AVANCESMES_SOAP_ACTION = NAMESPACE + AVANCESMES_METHOD_NAME;

        boolean WSObtenerAvancesMes;
        ArrayList<AvanceVendedorDiaWSResult> avanceVendedorDiaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAvancesMes = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESMES_METHOD_NAME);
            VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
                    loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
            String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerAvances);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(AVANCESMES_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<AvanceVendedorDiaWSResult>>(){ }.getType();
                    avanceVendedorDiaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAvancesMes = (avanceVendedorDiaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerAvancesMes = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesByVendedorMes: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetAvancesByVendedorMes: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesVentaByVendedorMes no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerAvancesMes)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances venta del vendedor mes que descargar. " + error, 1);
                ObtenerAvancesVentaVendedorMes();
                return;
            }

            if (!BorrarAvancesVentaMes())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar avances venta del vendedor mes.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new AvanceVentaBLL().InsertarAvanceVenta( avanceVendedorDiaWSResults, loginEmpleado.get_dia() );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances venta del vendedor mes: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances venta del vendedor mes: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "Error al insertar los avances venta del vendedor mes .", 1);
                return;
            }
            ObtenerAvancesVentaVendedorMes();

        }
    }

    private boolean BorrarAvancesVentaMes()
    {
        try
        {
            return new AvanceVentaBLL().BorrarAvancesVentaPorTipoAvanceVentaYRol("MES","Vendedor");
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta del vendedor por mes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVenta del vendedor por mes: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAvancesVentaVendedorMes()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo avances ventas por mes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetAvancesVentaVendedorMes localWSGetAvancesVentaVendedorMes = new WSGetAvancesVentaVendedorMes();

        try
        {
            localWSGetAvancesVentaVendedorMes.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesVentasVendedorMes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAvancesVentaVendedorMes: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAvancesVentaVendedorMes.", 1);
        }
    }

    private class WSGetAvancesVentaVendedorMes  extends AsyncTask<Void, Integer, Boolean>
    {
        String AVANCESVENTAMES_METHOD_NAME = "GetAvanceByVendedorAndMesAndProveedores";
        String AVANCESVENTAMES_SOAP_ACTION = NAMESPACE + AVANCESVENTAMES_METHOD_NAME;

        boolean WSObtenerAvanceVentaMes;
        ArrayList<AvanceVentaVendedorWSResult> avanceVentaVendedorWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAvanceVentaMes = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,AVANCESVENTAMES_METHOD_NAME);
            VendedorFechaWS vendedorFechaWs = new VendedorFechaWS(loginEmpleado.get_empleadoId(), loginEmpleado.get_dia(), loginEmpleado.get_mes(),
                    loginEmpleado.get_anio(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorFechaWSJson = new JSonParser().GenerarVendedorFechaJson(vendedorFechaWs);
            String encriptedObtenerAvances = new AES().Encriptar(vendedorFechaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerAvances);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(AVANCESVENTAMES_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<AvanceVentaVendedorWSResult>>(){ }.getType();
                    avanceVentaVendedorWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAvanceVentaMes = (avanceVentaVendedorWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerAvanceVentaMes = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice GetAvanceByVendedorAndMesAndProveedores: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice GetAvanceByVendedorAndMesAndProveedores: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetAvancesVentaVendedorMes no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerAvanceVentaMes)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron avances venta vendedor mes que descargar. " + error, 1);
                ObtenerClienteNits();
                return;
            }

            if (!BorrarAvancesVentaVendedorMes())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los avances venta del vendedor mes.", 1);
                return;
            }
            Fecha fecha = theUtilidades.ObtenerFecha();
            long i = 0;
            try
            {
                i = new AvanceVentaVendedorBLL().InsertarAvanceVentaVendedor( avanceVentaVendedorWSResults, fecha.get_dia());
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances venta del vendedor mes: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los avances venta del vendedor mes: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los avances venta vendedor mes.", 1);
                return;
            }
            ObtenerClienteNits();
        }
    }

    private boolean BorrarAvancesVentaVendedorMes()
    {
        try
        {
            return new AvanceVentaVendedorBLL().BorrarAvancesVentaVendedor();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVentaVendedor del vendedor por mes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los avancesVentaVendedor del vendedor por mes: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerClienteNits()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo nits de clientes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetNitsByClientesApk localWSGetNitsByClientesApk = new WSGetNitsByClientesApk();
        try
        {
            localWSGetNitsByClientesApk.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesApk: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsByClientesApk: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetNitsByClientesApk.", 1);
        }
    }

    private class WSGetNitsByClientesApk  extends AsyncTask<Void, Integer, Boolean>
    {
        String CLIENTENITS_METHOD_NAME = "GetNitsByClientesApk";
        String CLIENTENITS_SOAP_ACTION = NAMESPACE + CLIENTENITS_METHOD_NAME;

        boolean WSObtenerClienteNits;
        ArrayList<ClienteNitWSResult> clienteNitWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerClienteNits = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTENITS_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerNits = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerNits);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(CLIENTENITS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ClienteNitWSResult>>(){ }.getType();
                    clienteNitWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerClienteNits = (clienteNitWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerClienteNits = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsByClientesApk: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsByClientesApk: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitsByClientesApk no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerClienteNits )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron nits de clientes Apk que descargar. " + error, 1);
                ObtenerDatosVentaCliente();
                return;
            }

            if (!BorrarClienteNits())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los nits del cliente.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ClienteNitBLL().InsertarClienteNit( clienteNitWSResults, loginEmpleado );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nits del cliente: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los nits del cliente: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los nits del clientes.", 1);
                return;
            }
            ObtenerDatosVentaCliente();

        }
    }

    private boolean BorrarClienteNits()
    {
        try
        {
            return new ClienteNitBLL().BorrarClientesNit();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clienteNits: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clienteNits: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerDatosVentaCliente()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setMessage("Obteniendo datos venta cliente ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        ClienteDatosVentaJS clienteDatosVentaJS = new ClienteDatosVentaJS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId());
        String stringClienteDatosJS = new ParseJSon().GenerarDatosVentaClienteJSString(clienteDatosVentaJS);

        String encriptado="";
        try{
            encriptado = new AES().Encrypt(stringClienteDatosJS, theUtilidades.get_KEY());}
        catch(Exception localException){
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al encriptar el parametro de WSGetDatosVentaClienteByVendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al encriptar el parametro de WSGetDatosVentaClienteByVendedor: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDatosVentaClienteByVendedor.", 1);
        }

        WSGetDatosVentaClienteByVendedor localWSGetDatosVentaClienteByVendedor = new WSGetDatosVentaClienteByVendedor(encriptado);

        try
        {
            localWSGetDatosVentaClienteByVendedor.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDatosVentaClienteByVendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDatosVentaClienteByVendedor: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDatosVentaClienteByVendedor.", 1);
        }
    }

    private class WSGetDatosVentaClienteByVendedor  extends AsyncTask<Void, Integer, Boolean>
    {
        String DATOSVENTA_METHOD_NAME = "GetDatosVentaClienteByVendedor";
        String DATOSVENTA_SOAP_ACTION = NAMESPACE + DATOSVENTA_METHOD_NAME;

        String _clienteDatosVentaJS;
        boolean WSObtenerDatosVenta;
        List<ClienteDatosVentaWS> listaClienteDatosVenta;

        public WSGetDatosVentaClienteByVendedor(String clienteDatosVentaJS)
        {
            this._clienteDatosVentaJS = clienteDatosVentaJS;
        }

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerDatosVenta= false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,DATOSVENTA_METHOD_NAME);
            localSoapObject.addProperty("givendata", this._clienteDatosVentaJS);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLJSON);

            try
            {
                localHttpTransportSE.call(DATOSVENTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                Type listType = new TypeToken<List<ClienteDatosVentaWS>>() {}.getType();
                listaClienteDatosVenta = new Gson().fromJson(cadenaDesencriptada, listType);

                if (listaClienteDatosVenta != null) {
                    WSObtenerDatosVenta = true;
                }

                return true;
            }
            catch (Exception localException)
            {
                WSObtenerDatosVenta = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice GetDatosVentaClienteByVendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice GetDatosVentaClienteByVendedor: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetDatosVentaClienteByVendedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerDatosVenta)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron datos venta del cliente que descargar. ", 1);
                ObtenerMargenes();
                return;
            }

            if (!BorrarClienteDatosVenta())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los datos de venta del cliente.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ClienteDatosVentaBLL().InsertarClienteDatosVenta( listaClienteDatosVenta );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty()) {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los datos de venta del cliente: vacio");
                } else {
                myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los datos de venta del cliente: " + localException.getMessage()); }
            }

            if (i <= 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los datos de venta del cliente.", 1);
                return;
            }

            ObtenerMargenes();
        }
    }

    private boolean BorrarClienteDatosVenta()
    {
        try
        {
            return new ClienteDatosVentaBLL().BorrarClienteDatosVentas();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datos de la venta del cliente: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los datos de la venta del cliente: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerMargenes()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo margenes productos ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetMargenesByProveedores localWSGetMargenesByProveedores = new WSGetMargenesByProveedores();

        try
        {
            localWSGetMargenesByProveedores.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMargenesByProveedores: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMargenesByProveedores: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMargenesByProveedores.", 1);
        }
    }

    private class WSGetMargenesByProveedores  extends AsyncTask<Void, Integer, Boolean>
    {
        String GETMARGENESBYPROVEEDORES_METHOD_NAME = "GetMargenesByProveedores";
        String GETMARGENESBYPROVEEDORES_SOAP_ACTION = NAMESPACE + GETMARGENESBYPROVEEDORES_METHOD_NAME;

        boolean WSGetMargenesByProveedores;
        ArrayList<ProveedorPrecioListaMargenWSResult> proveedorPrecioListaMargenWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSGetMargenesByProveedores = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETMARGENESBYPROVEEDORES_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETMARGENESBYPROVEEDORES_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProveedorPrecioListaMargenWSResult>>(){ }.getType();
                    proveedorPrecioListaMargenWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSGetMargenesByProveedores = (proveedorPrecioListaMargenWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSGetMargenesByProveedores = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMargenesByProveedores: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetMargenesByProveedores: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetMargenesByProveedores no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSGetMargenesByProveedores )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron margenes de los proveedores que descargar. " + error, 1);
                ObtenerPreciosByPromociones();
                return;
            }

            if (!BorrarMargenesByProveedor())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los margenes de los proveedores.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProveedorPrecioListaMargenBLL().InsertarProveedorPrecioListaMargen( proveedorPrecioListaMargenWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los margenes de los proveedores: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los margenes de los proveedores: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los margenes de los proveedores.", 1);
                return;
            }
            ObtenerPreciosByPromociones();

        }
    }

    private boolean BorrarMargenesByProveedor()
    {
        try
        {
            return new ProveedorPrecioListaMargenBLL().BorrarProveedorPrecioListaMargen();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los margenes del proveedorPrecioListaMargen: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los margenes del proveedorPrecioListaMargen: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPreciosByPromociones()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo precios promociones ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetPreciosByPromociones localWSGetPreciosByPromociones = new WSGetPreciosByPromociones();

        try
        {
            localWSGetPreciosByPromociones.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosByPromociones: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPreciosByPromociones: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDosificacionByEmpleado.", 1);
        }
    }

    private class WSGetPreciosByPromociones  extends AsyncTask<Void, Integer, Boolean>
    {
        String PRECIOSPROMOCIONES_METHOD_NAME = "GetPreciosByPromocionesByAlmacen";
        String PRECIOSPROMOCIONES_SOAP_ACTION = NAMESPACE + PRECIOSPROMOCIONES_METHOD_NAME;

        boolean WSObtenerPreciosPromociones;
        ArrayList<PromocionPrecioListWSResult> promocionPrecioListWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerPreciosPromociones = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRECIOSPROMOCIONES_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PRECIOSPROMOCIONES_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PromocionPrecioListWSResult>>(){ }.getType();
                    promocionPrecioListWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerPreciosPromociones = (promocionPrecioListWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerPreciosPromociones = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosByPromociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPreciosByPromociones: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPreciosByPromociones no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerPreciosPromociones)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron precios de las promociones que descargar. " + error, 1);
                ObtenerPromocionesCosto();
                return;
            }

            if (!BorrarPreciosPromociones())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los precios de las promociones.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PromocionPrecioListaBLL().InsertarPromocionPrecioLista( promocionPrecioListWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de las promociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de las promociones: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los precios de las promociones.", 1);
                return;
            }
            ObtenerPromocionesCosto();
        }
    }

    private boolean BorrarPreciosPromociones()
    {
        try
        {
            return new PromocionPrecioListaBLL().BorrarPromocionesPrecioLista();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las dosificaciones: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPromocionesCosto()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo costos promocion ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetPromocionesCostosActuales localWSGetPromocionesCostosActuales = new WSGetPromocionesCostosActuales();

        try
        {
            localWSGetPromocionesCostosActuales.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesCostosActuales: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesCostosActuales: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromocionesCostosActuales.", 1);
        }
    }

    private class WSGetPromocionesCostosActuales extends AsyncTask<Void, Integer, Boolean>
    {
        String GETPROMOCIONESCOSTOS_METHOD_NAME = "GetPromocionCostosActualesByAlmacen";
        String GETPROMOCIONESCOSTOS_SOAP_ACTION = NAMESPACE + GETPROMOCIONESCOSTOS_METHOD_NAME;

        boolean GetPromocionesCostos;
        ArrayList<PromocionCostoWSResult> promocionCostoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetPromocionesCostos = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPROMOCIONESCOSTOS_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerCostos = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerCostos);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETPROMOCIONESCOSTOS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PromocionCostoWSResult>>(){ }.getType();
                    promocionCostoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetPromocionesCostos = (promocionCostoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetPromocionesCostos = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesCostosActuales: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesCostosActuales: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPromocionesCostosActuales no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetPromocionesCostos )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron costos de las promociones actuales que descargar. " + error, 1);
                ObtenerPromocionesPrecio();
                return;
            }

            if (!BorrarPromocionesCosto())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los costos de las promociones.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PromocionCostoBLL().InsertarPromocionCosto( promocionCostoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los costos de las promociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los costos de las promociones: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los costos de las promociones.", 1);
                return;
            }
            ObtenerPromocionesPrecio();

        }
    }

    private boolean BorrarPromocionesCosto()
    {
        try
        {
            return new PromocionCostoBLL().BorrarPromocionesCosto();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones costo: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPromocionesPrecio()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo precios promocion ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetPromocionesPreciosActuales localWSGetPromocionesPreciosActuales = new WSGetPromocionesPreciosActuales();

        try
        {
            localWSGetPromocionesPreciosActuales.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesPreciosActuales: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetPromocionesPreciosActuales: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetPromocionesPreciosActuales.", 1);
        }
    }

    private class WSGetPromocionesPreciosActuales extends AsyncTask<Void, Integer, Boolean>
    {
        String GETPROMOCIONESPRECIOS_METHOD_NAME = "GetPromocionPreciosActualesByAlmacen";
        String GETPROMOCIONESPRECIOS_SOAP_ACTION = NAMESPACE + GETPROMOCIONESPRECIOS_METHOD_NAME;

        boolean GetPromocionesPrecios;
        ArrayList<PromocionPrecioWSResult> promocionPrecioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetPromocionesPrecios = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPROMOCIONESPRECIOS_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETPROMOCIONESPRECIOS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PromocionPrecioWSResult>>(){ }.getType();
                    promocionPrecioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetPromocionesPrecios = (promocionPrecioWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetPromocionesPrecios = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesPreciosActuales: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetPromocionesPreciosActuales: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPromocionesPreciosActuales no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetPromocionesPrecios)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron precios de las promociones actuales que descargar. " + error, 1);
                ObtenerPromocionesTipoNegocios();
                return;
            }

            if (!BorrarPromocionesPrecio())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los precios de las promociones.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PromocionPrecioBLL().InsertarPromocionPrecio( promocionPrecioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de las promociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los precios de las promociones: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los precios de las promociones.", 1);
                return;
            }
            ObtenerPromocionesTipoNegocios();

        }
    }

    private boolean BorrarPromocionesPrecio()
    {
        try
        {

            return new PromocionPrecioBLL().BorrarPromocionesPrecio();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones precio: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerPromocionesTipoNegocios()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo promociones tipos de negocio ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetPromocionesTipoNegocio localWSGetPromocionesTipoNegocio = new WSGetPromocionesTipoNegocio();

        try
        {
            localWSGetPromocionesTipoNegocio.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocioByPromocionesByAlmacen: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetTipoNegocioByPromocionesByAlmacen: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetTipoNegocioByPromocionesByAlmacen.", 1);
        }
    }

    private class WSGetPromocionesTipoNegocio extends AsyncTask<Void, Integer, Boolean>
    {
        String GETPROMOCIONESTIPONEGOCIO_METHOD_NAME = "GetTipoNegocioByPromocionesByAlmacen";
        String GETPROMOCIONESTIPONEGOCIO_SOAP_ACTION = NAMESPACE + GETPROMOCIONESTIPONEGOCIO_METHOD_NAME;

        boolean GetPromocionesTipoNegocio;
        ArrayList<PromocionTipoNegocioWSResult> promocionTipoNegocioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetPromocionesTipoNegocio = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETPROMOCIONESTIPONEGOCIO_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETPROMOCIONESTIPONEGOCIO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<PromocionTipoNegocioWSResult>>(){ }.getType();
                    promocionTipoNegocioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetPromocionesTipoNegocio = (promocionTipoNegocioWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetPromocionesTipoNegocio = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTipoNegocioByPromocionesByAlmacen: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetTipoNegocioByPromocionesByAlmacen: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetPromocionesTipoNegocio no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetPromocionesTipoNegocio)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron tipos de negocio de promociones que descargar. " + error, 1);
                ObtenerCobrosPendientes();
                return;
            }

            if (!BorrarPromocionesTipoNegocio())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los tipos de negocio de las promociones.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new PromocionTipoNegocioBLL().InsertarPromocionTipoNegocio( promocionTipoNegocioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los tipos negocio de las promociones: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los tipos negocio de las promociones: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los tipos de negocio de las promociones.", 1);
                return;
            }
            ObtenerCobrosPendientes();

        }
    }

    private boolean BorrarPromocionesTipoNegocio()
    {
        try
        {
            return new PromocionTipoNegocioBLL().BorrarPromocionesTipoNegocio();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las promociones tipo negocio: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCobrosPendientes()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo cobros pendientes ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetVentasACreditoByVendedor localWSGetVentasACreditoByVendedor = new WSGetVentasACreditoByVendedor();

        try
        {
            localWSGetVentasACreditoByVendedor.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetVentasACreditoByVendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetVentasACreditoByVendedor: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetVentasACreditoByVendedor.", 1);
        }
    }

    private class WSGetVentasACreditoByVendedor extends AsyncTask<Void, Integer, Boolean>
    {
        String GETVENTASACREDITO_METHOD_NAME = "GetVentasACreditoByVendedorAndRuta";
        String GETVENTASACREDITO_SOAP_ACTION = NAMESPACE + GETVENTASACREDITO_METHOD_NAME;

        boolean GetVentasACredito;
        ArrayList<CobroPendienteWSResult> cobroPendienteWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetVentasACredito = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETVENTASACREDITO_METHOD_NAME);
            VendedorDiaRutaWS vendedorDiaRutaWs = new VendedorDiaRutaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_vendedorRutaId(),
                    loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaRutaJson(vendedorDiaRutaWs);
            String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETVENTASACREDITO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CobroPendienteWSResult>>(){ }.getType();
                    cobroPendienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetVentasACredito = (cobroPendienteWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetVentasACredito = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetVentasACreditoByVendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetVentasACreditoByVendedor: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetVentasACreditoByVendedor no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetVentasACredito)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron las ventas a credito del vendedor que descargar. " + error, 1);
                ObtenerProntosPagoUni();
                return;
            }

            if (!BorrarCobrosPendientes())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las ventas a credito del vendedor.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new CobroPendienteBLL().InsertarCobroPendiente( cobroPendienteWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las ventas a credito del vendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los ventas a credito del vendedor: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las ventas a credito del vendedor.", 1);
                return;
            }
            ObtenerProntosPagoUni();

        }
    }

    private boolean BorrarCobrosPendientes()
    {
        try
        {
            return new CobroPendienteBLL().BorrarCobrosPendientes();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cobros pendientes: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los cobros pendientes: " + localException.getMessage());
            }
            return false;
        }
    }

    //Obtenemos configuraciones pronto Pago Unilever y Dracaris

    private void ObtenerProntosPagoUni()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo pronto pago ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetProntosPagoUni localWSGetProntosPagoUni = new WSGetProntosPagoUni();

        try
        {
            localWSGetProntosPagoUni.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntoPagoActivo: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntosPagoActivo: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProntoPagoActivo.", 1);
        }
    }

    private class WSGetProntosPagoUni extends AsyncTask<Void, Integer, Boolean>
    {
        String PRONTOPAGO_METHOD_NAME = "GetProntoPagoActivo";
        String PRONTOPAGO_SOAP_ACTION = NAMESPACE + PRONTOPAGO_METHOD_NAME;

        boolean prontoPago;
        ProntoPagoUniWSResult prontoPagoUniWSResult;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            prontoPago = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRONTOPAGO_METHOD_NAME);
            SingleFechaWS singleFecha = new SingleFechaWS(loginEmpleado.get_dia(), loginEmpleado.get_mes(), loginEmpleado.get_anio(),
                                                        loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleFechaJson = new JSonParser().GenerarSingleFechaJson(singleFecha);
            String encriptedObtenerInfo = new AES().Encriptar(singleFechaJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PRONTOPAGO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<ProntoPagoUniWSResult>(){ }.getType();
                    prontoPagoUniWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
                    prontoPago = (prontoPagoUniWSResult != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                prontoPago = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoActivo: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoActivo: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProntosPagoUni no se ejecuto correctamente. ", 1);
                return;
            }

            if (!prontoPago )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron prontos pago que descargar. " + error, 1);
                ObtenerDraRebatesSaldo();
                return;
            }

            if (!BorrarProntosPagoUni())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los prontos pago.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new ProntoPagoUniBLL().InsertarProntoPagoUni( prontoPagoUniWSResult );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los prontos pago: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los prontos pago: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los prontos pago.", 1);
                return;
            }
            ObtenerProntosPagoJerarquias( prontoPagoUniWSResult.getProntoPagoId());

        }
    }

    private boolean BorrarProntosPagoUni()
    {
        try
        {
            return new ProntoPagoUniBLL().BorrarProntosPagoUni();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago unilever: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago unilever: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProntosPagoJerarquias(int prontoPagoId)
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo pronto pago jerarquias ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetProntosPagoJerarquias localWSGetProntosPagoJerarquias = new WSGetProntosPagoJerarquias(prontoPagoId);

        try
        {
            localWSGetProntosPagoJerarquias.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntoPagoJerarquias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntosPagoJerarquias: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProntoPagoJerarquias.", 1);
        }
    }

    private class WSGetProntosPagoJerarquias extends AsyncTask<Void, Integer, Boolean>
    {
        String PRONTOPAGOJERARQUIAS_METHOD_NAME = "GetJerarquiasByProntoPagoActivo";
        String PRONTOPAGOJERARQUIAS_SOAP_ACTION = NAMESPACE + PRONTOPAGOJERARQUIAS_METHOD_NAME;

        boolean prontoPagoJerarquias;
        ArrayList<ProntoPagoJerarquiaWSResult> prontoPagoJerarquiaWSResults;
        String error;
        int _prontoPagoId;

        public WSGetProntosPagoJerarquias(int prontoPagoId)
        {
            this._prontoPagoId = prontoPagoId;
        }

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            prontoPagoJerarquias = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRONTOPAGOJERARQUIAS_METHOD_NAME);
            SingleId singleId = new SingleId(_prontoPagoId, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PRONTOPAGOJERARQUIAS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProntoPagoJerarquiaWSResult>>(){ }.getType();
                    prontoPagoJerarquiaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    prontoPagoJerarquias = (prontoPagoJerarquiaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                prontoPagoJerarquias = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoJararquias: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoJerarquias: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProntosPagoJerarquias no se ejecuto correctamente. ", 1);
                return;
            }

            if (!prontoPagoJerarquias )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron jerarquias prontos pago que descargar. " + error, 1);
                ObtenerProntosPagoCanalRuta();
                return;
            }

            if (!BorrarProntosPagoJerarquias())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las jerarquias pronto pago.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProntoPagoJerarquiaBLL().InsertarProntoPagoJerarquia( prontoPagoJerarquiaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las jerarquias pronto Pago: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las jerarquias prontos pago: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las jerarquias prontos pago.", 1);
                return;
            }
            ObtenerProntosPagoCanalRuta();

        }
    }

    private boolean BorrarProntosPagoJerarquias()
    {
        try
        {
            return new ProntoPagoJerarquiaBLL().BorrarProntosPagoJerarquia();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago jerarquias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago jerarquias: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProntosPagoCanalRuta()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo pronto pago canal ruta ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetProntosPagoCanalRuta localWSGetProntosPagoCanalRuta = new WSGetProntosPagoCanalRuta();

        try
        {
            localWSGetProntosPagoCanalRuta.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntoPagoCanalRuta: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntosPagoCanalRuta: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProntoPagoCanalRuta.", 1);
        }
    }

    private class WSGetProntosPagoCanalRuta extends AsyncTask<Void, Integer, Boolean>
    {
        String PRONTOPAGOCANALRUTA_METHOD_NAME = "GetCanalesProntoPagoByVendedorYDia";
        String PRONTOPAGOCANALRUTA_SOAP_ACTION = NAMESPACE + PRONTOPAGOCANALRUTA_METHOD_NAME;

        boolean prontoPagoCanalRuta;
        ArrayList<ProntoPagoCanalRutaWSResult> prontoPagoCanalRutaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            prontoPagoCanalRuta = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRONTOPAGOCANALRUTA_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerCanales = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerCanales);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PRONTOPAGOCANALRUTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProntoPagoCanalRutaWSResult>>(){ }.getType();
                    prontoPagoCanalRutaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    prontoPagoCanalRuta = (prontoPagoCanalRutaWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                prontoPagoCanalRuta = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoCanalRuta: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoCanalRuta: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProntosPagoCanalRuta no se ejecuto correctamente. ", 1);
                return;
            }

            if (!prontoPagoCanalRuta)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron prontos pago canal ruta que descargar. " + error, 1);
                ObtenerProntosPagoCliente();
                return;
            }

            if (!BorrarProntoPagoCanalesRuta())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los canales ruta pronto pago.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProntoPagoCanalRutaBLL().InsertarProntoPagoCanalRuta( prontoPagoCanalRutaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales ruta pronto pago: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los canales ruta pronto pago: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los canales ruta pronto pago.", 1);
                return;
            }
            ObtenerProntosPagoCliente();

        }
    }

    private boolean BorrarProntoPagoCanalesRuta()
    {
        try
        {
            return new ProntoPagoCanalRutaBLL().BorrarProntosPagoCanalRuta();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago canalRuta: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago canalRuta: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProntosPagoCliente()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo prontos pago cliente ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetProntosPagoCliente localWSGetProntosPagoCliente = new WSGetProntosPagoCliente();

        try
        {
            localWSGetProntosPagoCliente.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntoPagoCliente: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProntoPagoCliente: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProntoCliente.", 1);
        }
    }

    private class WSGetProntosPagoCliente extends AsyncTask<Void, Integer, Boolean>
    {
        String PRONTOPAGOCLIENTE_METHOD_NAME = "GetClientesProntoPagoByVendedorYDia";
        String PRONTOPAGOCLIENTE_SOAP_ACTION = NAMESPACE + PRONTOPAGOCLIENTE_METHOD_NAME;

        boolean prontoPagoCliente;
        ArrayList<ProntoPagoClienteWSResult> prontoPagoClienteWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            prontoPagoCliente = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRONTOPAGOCLIENTE_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerClientes);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(PRONTOPAGOCLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProntoPagoClienteWSResult>>(){ }.getType();
                    prontoPagoClienteWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    prontoPagoCliente = (prontoPagoClienteWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                prontoPagoCliente = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoCliente: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetProntoPagoCliente: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetProntosPagoCliente no se ejecuto correctamente. ", 1);
                return;
            }

            if (!prontoPagoCliente)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron prontos pago cliente que descargar. " + error, 1);
                ObtenerDraRebatesSaldo();
                return;
            }

            if (!BorrarProntoPagoClientes())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes prontos pago.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProntoPagoClienteBLL().InsertarProntoPagoCliente( prontoPagoClienteWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los clientes pronto pago: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los clientes pronto pago: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los clientes pronto pago.", 1);
                return;
            }
            ObtenerDraRebatesSaldo();

        }
    }

    private boolean BorrarProntoPagoClientes()
    {
        try
        {
            return new ProntoPagoClienteBLL().BorrarProntosPagoCliente();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago cliente: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los prontos pago cliente: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerDraRebatesSaldo()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo saldos de rebate ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetRebateSaldo localWSGetRebateSaldo = new WSGetRebateSaldo();

        try
        {
            localWSGetRebateSaldo.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRebateSaldo: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetRebateSaldo: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetRebateSaldo.", 1);
        }
    }

    private class WSGetRebateSaldo extends AsyncTask<Void, Integer, Boolean>
    {
        String REBATESALDO_METHOD_NAME = "GetClientesRebateSaldoVendedorYDia";
        String REBATESALDO_SOAP_ACTION = NAMESPACE + REBATESALDO_METHOD_NAME;

        boolean rebateSaldo;
        ArrayList<DraRebateSaldoWSResult> draRebateSaldoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            rebateSaldo = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,REBATESALDO_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerClientes);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(REBATESALDO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<DraRebateSaldoWSResult>>(){ }.getType();
                    draRebateSaldoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    rebateSaldo = (draRebateSaldoWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                rebateSaldo = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRebateSaldo: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetRebateSaldo: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetRebateSaldo no se ejecuto correctamente. ", 1);
                return;
            }

            if (!rebateSaldo)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los rebates saldo que descargar. " + error, 1);
                ObtenerCondicionesTributarias();
                return;
            }

            if (!BorrarRebatesSaldo())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los rebates saldo.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new DraRebateSaldoBLL().InsertarDraRebateSaldo( draRebateSaldoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los rebate saldo: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los rebate saldo: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los rebates saldo.", 1);
                return;
            }
            ObtenerCondicionesTributarias();

        }
    }

    private boolean BorrarRebatesSaldo()
    {
        try
        {
            return new DraRebateSaldoBLL().BorrarDraRebatesSaldo();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los saldos del rebate dracaris: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los saldos del rebate dracaris: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerCondicionesTributarias()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo condiciones tributarias ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetNitsMonto localWSGetNitsMonto = new WSGetNitsMonto();

        try
        {
            localWSGetNitsMonto.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsMonto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetNitsMonto: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetNitsMonto.", 1);
        }
    }

    private class WSGetNitsMonto extends AsyncTask<Void, Integer, Boolean>
    {
        String GETNITSMONTO_METHOD_NAME = "UNI_GetNitsMontosByVendedor";
        String GETNITSMONTO_SOAP_ACTION = NAMESPACE + GETNITSMONTO_METHOD_NAME;

        boolean GetNitsMonto;
        ArrayList<CondicionTributariaWSResult> condicionTributariaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            GetNitsMonto = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETNITSMONTO_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETNITSMONTO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CondicionTributariaWSResult>>(){ }.getType();
                    condicionTributariaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    GetNitsMonto = (condicionTributariaWSResults.size()> 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                GetNitsMonto = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsMonto: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetNitsMonto: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetNitsMonto no se ejecuto correctamente. ", 1);
                return;
            }

            if (!GetNitsMonto )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron montos de los nits que descargar. " + error, 1);
                VerificarOpcionVentaDirecta();
                return;
            }

            if (!BorrarNitsMonto())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los montos de los nits.", 1);
                return;
            }

            long i = 0;
            try
            {
                i = new CondicionTributariaBLL().InsertarCondicionTributaria( condicionTributariaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los montos de los nits: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los montos de los nits: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los montos de los nits.", 1);
                return;
            }
            VerificarOpcionVentaDirecta();

        }
    }

    private boolean BorrarNitsMonto()
    {
        try
        {
            return new CondicionTributariaBLL().BorrarCondicionesTributarias();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las condiciones tributarias: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los condiciones tributarias: " + localException.getMessage());
            }
            return false;
        }
    }

    //Verificamos configuracion Venta Directa

    public void VerificarOpcionVentaDirecta()
    {
        if(parametroGeneral != null && parametroGeneral.is_habilitarVentaDirecta())
        {
            ObtenerDistribuidores();
        }
        else
        {
            VerificarOpcionMaterialPOP();
        }
    }

    private void ObtenerDistribuidores()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo distribuidores ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);
        WSGetDistribuidores localWSGetDistribuidores = new WSGetDistribuidores();

        try
        {
            localWSGetDistribuidores.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDistribuidores: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetDistribuidores: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetDistribuidores.", 1);
        }
    }

    private class WSGetDistribuidores  extends AsyncTask<Void, Integer, Boolean>
    {
        String GETDISTRIBUIDORES_METHOD_NAME = "GetDistribuidores";
        String GETDISTRIBUIDORES_SOAP_ACTION = NAMESPACE + GETDISTRIBUIDORES_METHOD_NAME;

        boolean WSObtenerDistribuidores;
        ArrayList<DistribuidorWSResult> distribuidorWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerDistribuidores = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,GETDISTRIBUIDORES_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(GETDISTRIBUIDORES_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<DistribuidorWSResult>>(){ }.getType();
                    distribuidorWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerDistribuidores = (distribuidorWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerDistribuidores = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetDistribuidores: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetDistribuidores: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetDistribuidores no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerDistribuidores)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron distribuidores que descargar. " + error, 1);
                return;
            }

            if (!BorrarDistribuidores())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los distribuidores.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new DistribuidorBLL().InsertarDistribuidor( distribuidorWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los distribuidores: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los distribuidores: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los distribuidores.", 1);
                return;
            }
            VerificarOpcionMaterialPOP();
        }
    }

    private boolean BorrarDistribuidores()
    {
        try
        {
            return new DistribuidorBLL().BorrarDistribuidores();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los distribuidores: " + localException.getMessage());
            }
            return false;
        }
    }

    //Verificamos configuracion material POP

    public void VerificarOpcionMaterialPOP()
    {
        if(parametroGeneral != null && parametroGeneral.is_habilitarPop())
        {
            ObtenerCategoriasPOP();
        }
        else
        {
            VerificarOpcionCambio();
        }
    }

    private void ObtenerCategoriasPOP()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando categorias POP ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerCategoriasPOP wsObtenerCategoriasPOP = new WSObtenerCategoriasPOP();

        try
        {
            wsObtenerCategoriasPOP.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerCategoriasPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerCategoriasPOP: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSObtenerCategoriasPOP.", 1);
        }
    }

    private class WSObtenerCategoriasPOP extends AsyncTask<Void, Integer, Boolean>
    {
        String CATEGORIAPOP_METHOD_NAME = "GetCategoriasPop";
        String CATEGORIAPOP_SOAP_ACTION = NAMESPACE + CATEGORIAPOP_METHOD_NAME;

        boolean WSObtenerCategoriasPOP = false;
        ArrayList<CategoriaPOPWSResult> categoriaPOPWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerCategoriasPOP = false;

            SoapObject localSoapObject1 = new SoapObject(NAMESPACE,CATEGORIAPOP_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject1.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.CATEGORIAPOP_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<CategoriaPOPWSResult>>(){ }.getType();
                    categoriaPOPWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerCategoriasPOP = (categoriaPOPWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategoriasPOP: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategoriasPOP: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerCategoriasPOP no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerCategoriasPOP )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Categorias POP que descargar. " + error, 1);
                return;
            }

            if (!borrarCategoriasPOP())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Categorias P O P.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new CategoriaPOPBLL().InsertarCategoriaPOP( categoriaPOPWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Categorias P O P: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Categorias P O P: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Categorias P O P.", 1);
                return;
            }
            ObtenerProductosPOPByVendedor();

        }
    }

    private boolean borrarCategoriasPOP()
    {
        try
        {
            return new CategoriaPOPBLL().BorrarCategoriasPOP();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategoriasPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetCategoriasPOP: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProductosPOPByVendedor()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando productos POP ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProductosPOP wsObtenerProductosPOP = new WSObtenerProductosPOP();

        try
        {
            wsObtenerProductosPOP.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerGetProductosPOPByVendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSObtenerGetProductosPOPByVendedor: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosPOPByVendedor.", 1);
        }
    }

    private class WSObtenerProductosPOP extends AsyncTask<Void, Integer, Boolean>
    {
        String PRODUCTOSPOP_METHOD_NAME = "GetProductosPopByVendedor";
        String PRODUCTOSPOP_SOAP_ACTION = NAMESPACE + PRODUCTOSPOP_METHOD_NAME;

        boolean WSObtenerProductosPOP = false;
        ArrayList<ProductoPOPWSResult> productoPOPWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProductosPOP = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTOSPOP_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_empleadoId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.PRODUCTOSPOP_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProductoPOPWSResult>>(){ }.getType();
                    productoPOPWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProductosPOP = (productoPOPWSResults.size() > 0);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGeProductosPopByVendedor: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopByVendedor: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosPOP no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProductosPOP)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Productos POP que descargar. " + error, 1);
                return;
            }

            if (!borrarProductosPOP())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos P O P.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProductoPOPBLL().InsertarProductoPOP( productoPOPWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos P O P: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos P O P: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos P O P.", 1);
                return;
            }
            ObtenerAlmacenPOP();

        }
    }

    private boolean borrarProductosPOP()
    {
        try
        {
            return new ProductoPOPBLL().BorrarProductosPOP();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopByVendedor: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopByVendedor: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerAlmacenPOP()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando almacen POP ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerAlmacenPOP wsObtenerAlmacenPOP = new WSObtenerAlmacenPOP();

        try
        {
            wsObtenerAlmacenPOP.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAlmacenPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAlmacenPOP: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetAlamcenPOP.", 1);
        }
    }

    private class WSObtenerAlmacenPOP extends AsyncTask<Void, Integer, Boolean>
    {
        String ALMACENPOP_METHOD_NAME = "GetAlmacenPop";
        String ALMACENPOP_SOAP_ACTION = NAMESPACE + ALMACENPOP_METHOD_NAME;

        boolean WSObtenerAlmacenPOP = false;
        AlmacenPOPWSResult almacenPOPWSResult;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerAlmacenPOP = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPOP_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.ALMACENPOP_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<AlmacenPOPWSResult>(){ }.getType();
                    almacenPOPWSResult = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerAlmacenPOP = (almacenPOPWSResult != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAlmacenPop: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAlmacenPop: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerAlmacenPOP no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerAlmacenPOP)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Almacen P O P que descargar. " + error, 1);
                return;
            }

            if (!borrarAlmacenesPOP())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Almacen P O P.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new AlmacenPOPBLL().InsertarAlmacenPOP( almacenPOPWSResult );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Almacen P O P: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Almacen P O P: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Almacen P O P.", 1);
                return;
            }
            ObtenerProductosAlmacenPOP();

        }
    }

    private boolean borrarAlmacenesPOP()
    {
        try
        {
            return new AlmacenPOPBLL().BorrarAlmacenesPOP();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAlmacenPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetAlmacenPOP: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProductosAlmacenPOP()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando almacen de productos POP ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProductosAlmacenPOP wsObtenerProductosAlmacenPOP = new WSObtenerProductosAlmacenPOP();

        try
        {
            wsObtenerProductosAlmacenPOP.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPOP: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosByAlmacenPOP.", 1);
        }
    }

    private class WSObtenerProductosAlmacenPOP extends AsyncTask<Void, Integer, Boolean>
    {
        String ALMACENPRODUCTOPOP_METHOD_NAME = "GetProductosByAlmacenPop";
        String ALMACENPRODUCTOPOP_SOAP_ACTION = NAMESPACE + ALMACENPRODUCTOPOP_METHOD_NAME;

        boolean WSObtenerProductosAlmacenPOP = false;
        ArrayList<AlmacenPOPProductoWSResult> almacenPOPProductoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProductosAlmacenPOP = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ALMACENPRODUCTOPOP_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.ALMACENPRODUCTOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<AlmacenPOPProductoWSResult>>(){ }.getType();
                    almacenPOPProductoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProductosAlmacenPOP = (almacenPOPProductoWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPop: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosByAlmacenPop: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosAlmacenPOP no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProductosAlmacenPOP )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(),"No existen productos del almacen POP que descargar. " + error, 2);
                ObtenerProductosPOPCosto();
                return;
            }

            if (!borrarProductosAlmacenPOP())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos Almacen P O P.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new AlmacenPOPProductoBLL().InsertarAlmacenPOPProducto( almacenPOPProductoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos Almacen P O P: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos Almacen P O P: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos Almacen P O P.", 1);
                return;
            }
            ObtenerProductosPOPCosto();

        }
    }

    private boolean borrarProductosAlmacenPOP()
    {
        try
        {
            return new AlmacenPOPProductoBLL().BorrarAlmacenesPOPProducto();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosAlmacenPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosAlmacenPOP: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerProductosPOPCosto()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando costos de los productos POP ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProductosPOPCosto wsObtenerProductosPOPCosto = new WSObtenerProductosPOPCosto();

        try
        {
            wsObtenerProductosPOPCosto.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPOPCosto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPOPCosto: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosPOPCosto.", 1);
        }
    }

    private class WSObtenerProductosPOPCosto extends AsyncTask<Void, Integer, Boolean>
    {
        String PRODUCTOPOPCOSTO_METHOD_NAME = "GetProductosPopActualesByAlmacen";
        String PRODUCTOPOPCOSTO_SOAP_ACTION = NAMESPACE + PRODUCTOPOPCOSTO_METHOD_NAME;

        boolean WSObtenerProductosPOPCosto = false;
        ArrayList<ProductoPOPCostoWSResult> productoPOPCostoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProductosPOPCosto = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTOPOPCOSTO_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.PRODUCTOPOPCOSTO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProductoPOPCostoWSResult>>(){ }.getType();
                    productoPOPCostoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProductosPOPCosto = (productoPOPCostoWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopCosto: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPopCosto: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosPOPCosto no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProductosPOPCosto )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron costos de lo productos POP que descargar. " + error, 1);
                return;
            }

            if (!borrarProductosPOPCosto())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos P O P Costo.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProductoPOPCostoBLL().InsertarProductoPOPCosto( productoPOPCostoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos P O P Costo: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos P O P Costo: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos P O P Costo.", 1);
                return;
            }
            ObtenerMotivodPOP();

        }
    }

    private boolean borrarProductosPOPCosto()
    {
        try
        {
            return new ProductoPOPCostoBLL().BorrarProductosPOPCosto();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPOPCosto: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosPOPCosto: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerMotivodPOP()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando motivos POP ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerMotivosPOP wsObtenerMotivosPOP = new WSObtenerMotivosPOP();

        try
        {
            wsObtenerMotivosPOP.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosPOP: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosPOP: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMotivosPOP.", 1);
        }
    }

    private class WSObtenerMotivosPOP extends AsyncTask<Void, Integer, Boolean>
    {
        String MOTIVOPOP_METHOD_NAME = "GetMotivosPop";
        String MOTIVOPOP_SOAP_ACTION = NAMESPACE + MOTIVOPOP_METHOD_NAME;

        boolean WSObtenerMotivosPOP = false;
        ArrayList<MotivoPopWSResult> motivoPopWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerMotivosPOP = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,MOTIVOPOP_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.MOTIVOPOP_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<MotivoPopWSResult>>(){ }.getType();
                    motivoPopWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerMotivosPOP = (motivoPopWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosPop: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosPop: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerMotivosPOP no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerMotivosPOP)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron motivos POP que descargar. " + error, 1);
                return;
            }

            if (!borrarMotivosPOP())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Motivos P O P.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new MotivoPopBLL().InsertarMotivoPop( motivoPopWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Motivos P O P: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Motivos P O P: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Motivos P O P.", 1);
                return;
            }
            VerificarOpcionCambio();

        }
    }

    private boolean borrarMotivosPOP()
    {
        try
        {
            return new MotivoPopBLL().BorrarMotivosPop();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosPop: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosPop: " + localException.getMessage());
            }
            return false;
        }
    }

    //Verificamos configuracion Cambios

    public void VerificarOpcionCambio()
    {
        if(parametroGeneral != null && parametroGeneral.is_habilitarCambio())
        {
            ObtenerProductosCambio();
        }
        else
        {
            VerificarOpcionMatcheoClientesUnilever();
        }
    }

    private void ObtenerProductosCambio()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargandolos productos para cambio ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerProductosParaCambio wsObtenerProductosParaCambio = new WSObtenerProductosParaCambio();

        try
        {
            wsObtenerProductosParaCambio.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosParaCambio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosParaCambio: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetProductosParaCambio.", 1);

        }
    }

    private class WSObtenerProductosParaCambio extends AsyncTask<Void, Integer, Boolean>
    {
        String PRODUCTOCAMBIO_METHOD_NAME = "GetProductosParaCambio";
        String PRODUCTOCAMBIO_SOAP_ACTION = NAMESPACE + PRODUCTOCAMBIO_METHOD_NAME;

        boolean WSObtenerProductosCambio = false;
        ArrayList<ProductoCambioWSResult> productoCambioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerProductosCambio = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,PRODUCTOCAMBIO_METHOD_NAME);
            SingleId singleId = new SingleId(loginEmpleado.get_almacenId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.PRODUCTOCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProductoCambioWSResult>>(){ }.getType();
                    productoCambioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerProductosCambio = (productoCambioWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosCambio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetProductosCambio: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerProductosParaCambio no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerProductosCambio )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron productos para cambio que descargar. " + error, 1);
                return;
            }

            if (!borrarProductosCambio())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Productos Para Cambio.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ProductoCambioBLL().InsertarProductoCambio( productoCambioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Productos Para Cambio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Productos Para Cambio: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Productos Para Cambio.", 1);
                return;
            }
            ObtenerMotivosCambio();

        }
    }

    private boolean borrarProductosCambio()
    {
        try
        {
            return new ProductoCambioBLL().BorrarProductosCambios();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos para cambio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los productos para cambio: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerMotivosCambio()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando motivos de cambio ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerMotivosCambio wsObtenerMotivosCambio = new WSObtenerMotivosCambio();

        try
        {
            wsObtenerMotivosCambio.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosCambio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosCambio: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMotivosCambio.", 1);
        }
    }

    private class WSObtenerMotivosCambio extends AsyncTask<Void, Integer, Boolean>
    {
        String MOTIVOCAMBIO_METHOD_NAME = "GetMotivosCambio";
        String MOTIVOCAMBIO_SOAP_ACTION = NAMESPACE + MOTIVOCAMBIO_METHOD_NAME;

        boolean WSObtenerMotivosCambio = false;
        ArrayList<MotivoCambioWSResult> motivoCambioWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerMotivosCambio = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,MOTIVOCAMBIO_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(this.MOTIVOCAMBIO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ProductoCambioWSResult>>(){ }.getType();
                    motivoCambioWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerMotivosCambio = (motivoCambioWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosCambio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosCambio: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerMotivosCambio no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerMotivosCambio )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron motivos cambio que descargar. " + error, 1);
                return;
            }

            if (!borrarMotivosCambio())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Motivos Cambio.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new MotivoCambioBLL().InsertarMotivoCambio( motivoCambioWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Motivos Cambio: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Motivos Cambio: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Motivos Cambio.", 1);
                return;
            }
            VerificarOpcionMatcheoClientesUnilever();

        }
    }

    private boolean borrarMotivosCambio()
    {
        try
        {
            return new MotivoCambioBLL().BorrarMotivosCambio();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos de cambio: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos de cambio: " + localException.getMessage());
            }
            return false;
        }
    }

    //Verificamos configuracion Matcheo Clientes Censo Unilever

    public void VerificarOpcionMatcheoClientesUnilever()
    {
        if(parametroGeneral != null && parametroGeneral.is_habilitarMatcheo())
        {
            ObtenerClientesCenso();
        }
        else
        {
            ObtenerEncuestasActivas();
        }
    }

    private void ObtenerClientesCenso()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo clientes censo ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetClientesCenso localWSGetClientesCenso = new WSGetClientesCenso();

        try
        {
            localWSGetClientesCenso.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesCenso: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesCenso: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesCenso.", 1);
        }
    }

    private class WSGetClientesCenso extends AsyncTask<Void, Integer, Boolean>
    {
        String CLIENTECENSO_METHOD_NAME = "GetClientesUnileverByRutaYDia";
        String CLIENTECENSO_SOAP_ACTION = NAMESPACE + CLIENTECENSO_METHOD_NAME;

        boolean WSObtenerClientesCenso;
        ArrayList<ClienteCensoWSResult> clienteCensoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerClientesCenso = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTECENSO_METHOD_NAME);
            VendedorDiaRutaWS vendedorDiaRutaWs = new VendedorDiaRutaWS(0, theUtilidades.ObtenerDiaId(), loginEmpleado.get_vendedorRutaId(),
                    loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaRutaWSJson = new JSonParser().GenerarVendedorDiaRutaJson(vendedorDiaRutaWs);
            String encriptedObtenerClientes = new AES().Encriptar(vendedorDiaRutaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerClientes);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);

            try
            {
                localHttpTransportSE.call(CLIENTECENSO_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ClienteCensoWSResult>>(){ }.getType();
                    clienteCensoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerClientesCenso = (clienteCensoWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerClientesCenso = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetClientesUnileverByRutaYDIa: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetClientesUnileverByRutaYDIa: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetClientesCenso no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerClientesCenso )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron clientes censo que descargar. " + error, 1);
                ObtenerMotivosEliminacion();
                return;
            }

            if (!BorrarClientesCenso())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes censo.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ClienteCensoBLL().InsertarClienteCenso( clienteCensoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los clientes censo: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los clientes censo: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los clientes censo.", 1);
                return;
            }
            ObtenerMotivosEliminacion();

        }
    }

    private boolean BorrarClientesCenso()
    {
        try
        {
            return new ClienteCensoBLL().BorrarClientesCenso();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesCenso: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientesCenso: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerMotivosEliminacion()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Descargando motivos eliminacion ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSObtenerMotivosEliminacion wsObtenerMotivosEliminacion = new WSObtenerMotivosEliminacion();

        try
        {
            wsObtenerMotivosEliminacion.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosEliminacion: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivosEliminacion: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetMotivosEliminacion.", 1);
        }
    }

    private class WSObtenerMotivosEliminacion extends AsyncTask<Void, Integer, Boolean>
    {
        String MOTIVOSELIMINACION_METHOD_NAME = "GetMotivoseliminacionMatcheo";
        String MOTIVOSELIMINACION_SOAP_ACTION = NAMESPACE + MOTIVOSELIMINACION_METHOD_NAME;

        boolean WSObtenerMotivosEliminacion = false;
        ArrayList<MotivoEliminacionMatchWSResult> motivoEliminacionMatchWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerMotivosEliminacion = false;

            SoapObject localSoapObject1 = new SoapObject(NAMESPACE,MOTIVOSELIMINACION_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject1.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject1);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URLUNILEVER);
            try
            {
                localHttpTransportSE.call(this.MOTIVOSELIMINACION_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<MotivoEliminacionMatchWSResult>>(){ }.getType();
                    motivoEliminacionMatchWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerMotivosEliminacion = (motivoEliminacionMatchWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivoseliminacionMatcheo: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetMotivoseliminacionMatcheo: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSObtenerMotivosEliminacion no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerMotivosEliminacion)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron Motivos Eliminacion que descargar. " + error, 1);
                return;
            }

            if (!BorrarMotivosEliminacion())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar Motivos Eliminacion.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new MotivoEliminacionMatchBLL().InsertarMotivoEliminacionMatch( motivoEliminacionMatchWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar Motivos Eliminacion: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los Motivos Eliminacion: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener Motivos Eliminacion.", 1);
                return;
            }
            ObtenerEncuestasActivas();

        }

    }

    private boolean BorrarMotivosEliminacion()
    {
        try
        {
            return new MotivoEliminacionMatchBLL().BorrarMotivosEliminacionMatch();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los motivos eliminacion match: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las motivos de eliminacion match: " + localException.getMessage());
            }
            return false;
        }
    }

    //Verificamos configuracion Encuestas

    private void ObtenerEncuestasActivas()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo encuestas ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetEncuestasActivas localWSGetEncuestasActivas = new WSGetEncuestasActivas();

        try
        {
            localWSGetEncuestasActivas.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEncuestasActivas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEncuestasActivas: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetEncuestasActivas.", 1);
        }
    }

    private class WSGetEncuestasActivas extends AsyncTask<Void, Integer, Boolean>
    {
        String ENCUESTA_METHOD_NAME = "GetEncuestasActivas";
        String ENCUESTA_SOAP_ACTION = NAMESPACE +ENCUESTA_METHOD_NAME;

        boolean WSObtenerEncuesta;
        ArrayList<EncuestaWSResult> encuestaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerEncuesta = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ENCUESTA_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(ENCUESTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<EncuestaWSResult>>(){ }.getType();
                    encuestaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerEncuesta = (encuestaWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerEncuesta = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEncuestasActivas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEncuestasActivas: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }
            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetEncuestasActivas no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerEncuesta)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron encuestas activas que descargar. " + error, 1);
                RegistrarSincronizacionVendedor();
                return;
            }

            if (!BorrarEncuestas())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las encuestas activas.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new EncuestaBLL().InsertarEncuesta( encuestaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las encuestas activas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las encuestas activas: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las encuestas activas.", 1);
                return;
            }
            ObtenerEncuestaDetalleActivas();

        }
    }

    private boolean BorrarEncuestas()
    {
        try
        {
            return new EncuestaBLL().BorrarEncuestas();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encustas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los encuesta: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerEncuestaDetalleActivas()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo encuestas detalle ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetEncuestaDetalleActivas localWSGetEncuestaDetalleActivas = new WSGetEncuestaDetalleActivas();

        try
        {
            localWSGetEncuestaDetalleActivas.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEncuestaDetalleActivas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEncuestaDetalleActivas: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetEncuestaDetalleActivas.", 1);
        }
    }

    private class WSGetEncuestaDetalleActivas extends AsyncTask<Void, Integer, Boolean>
    {
        String ENCUESTADETALLE_METHOD_NAME = "GetEncuestaDetalleActivas";
        String ENCUESTADETALLE_SOAP_ACTION = NAMESPACE +ENCUESTADETALLE_METHOD_NAME;

        boolean WSObtenerEncuestaDetalle;
        ArrayList<EncuestaDetalleWSResult> encuestaDetalleWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerEncuestaDetalle = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ENCUESTADETALLE_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(ENCUESTADETALLE_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<EncuestaDetalleWSResult>>(){ }.getType();
                    encuestaDetalleWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerEncuestaDetalle = (encuestaDetalleWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerEncuestaDetalle = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEncuestaDetalleActivas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEncuestaDetalleActivas: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetEncuestaDetalleActivas no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerEncuestaDetalle )
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron encuestas detalle activas que descargar. " + error, 1);
                return;
            }

            if (!BorrarEncuestaDetalle())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las encuestas detalle Activas.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new EncuestaDetalleBLL().InsertarEncuestaDetalle( encuestaDetalleWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las encuestas detalle activas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los encuestas detalle activas: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las encuestas detalle activas.", 1);
                return;
            }
            ObtenerEncuestaDetalleListaActivas();

        }
    }

    private boolean BorrarEncuestaDetalle()
    {
        try
        {
            return new EncuestaDetalleBLL().BorrarEncuestasDetalle();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestas detalle: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los encuestas detalle: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerEncuestaDetalleListaActivas()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo encuesta lista ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetEncuestaDetalleListaActivas localWSGetEncuestaDetalleListaActivas = new WSGetEncuestaDetalleListaActivas();

        try
        {
            localWSGetEncuestaDetalleListaActivas.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEncuestaDetalleListaActivas: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetEncuestaDetalleListaActivas: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetEncuestaDetalleListaActivas.", 1);
        }
    }

    private class WSGetEncuestaDetalleListaActivas extends AsyncTask<Void, Integer, Boolean>
    {
        String ENCUESTADETALLELISTA_METHOD_NAME = "GetEncuestaDetalleListaActivas";
        String ENCUESTADETALLELISTA_SOAP_ACTION = NAMESPACE +ENCUESTADETALLELISTA_METHOD_NAME;

        boolean WSObtenerEncuestaDetalleLista;
        ArrayList<EncuestaListaWSResult> encuestaListaWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSObtenerEncuestaDetalleLista = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,ENCUESTADETALLELISTA_METHOD_NAME);
            SingleId singleId = new SingleId(0, loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String singleIdJson = new JSonParser().GenerarSingleIdJson(singleId);
            String encriptedObtenerInfo = new AES().Encriptar(singleIdJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerInfo);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(ENCUESTADETALLELISTA_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<EncuestaListaWSResult>>(){ }.getType();
                    encuestaListaWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSObtenerEncuestaDetalleLista = (encuestaListaWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSObtenerEncuestaDetalleLista = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEncuestaDetalleListaActivas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetEncuestaDetalleListaActivas: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetEncuestaDetalleListaActivas no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSObtenerEncuestaDetalleLista)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron encuestas detalle lista activas que descargar. " + error, 1);
                ObtenerClientesEncuestados();
                return;
            }

            if (!BorrarEncuestaDetalleLista())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar las encuestas detalle lista activas.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new EncuestaListaBLL().InsertarEncuestaLista( encuestaListaWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar las encuestas detalle lista activas: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los encuestas detalle lista activas: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las encuestas detalle lista activas.", 1);
                return;
            }
            ObtenerClientesEncuestados();

        }
    }

    private boolean BorrarEncuestaDetalleLista()
    {
        try
        {
            return new EncuestaListaBLL().BorrarEncuestasLista();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar las encuestas lista: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los encuestas lista: " + localException.getMessage());
            }
            return false;
        }
    }

    private void ObtenerClientesEncuestados()
    {
        pdEsperaObtenerInfo = new ProgressDialog(this);
        pdEsperaObtenerInfo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdEsperaObtenerInfo.setMessage("Obteniendo clientes encuestados ...");
        pdEsperaObtenerInfo.setCancelable(false);
        pdEsperaObtenerInfo.setCanceledOnTouchOutside(false);

        WSGetClientesEncuestados localWSGetClientesEncuestados = new WSGetClientesEncuestados();

        try
        {
            localWSGetClientesEncuestados.execute();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesEncuestados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSGetClientesEncuestados: " + localException.getMessage());
            }
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSGetClientesEncuestados.", 1);
        }
    }

    private class WSGetClientesEncuestados extends AsyncTask<Void, Integer, Boolean>
    {
        String CLIENTESENCUESTADOS_METHOD_NAME = "GetClientesEncuestadosApkByVendedorYDia";
        String CLIENTESENCUESTADOS_SOAP_ACTION = NAMESPACE + CLIENTESENCUESTADOS_METHOD_NAME;

        boolean WSClientesEncuestados;
        ArrayList<ClienteEncuestadoWSResult> clienteEncuestadoWSResults;
        String error;

        protected void onPreExecute()
        {
            pdEsperaObtenerInfo.show();
        }

        protected Boolean doInBackground(Void... paramVarArgs)
        {
            WSClientesEncuestados = false;

            SoapObject localSoapObject = new SoapObject(NAMESPACE,CLIENTESENCUESTADOS_METHOD_NAME);
            VendedorDiaWS vendedorDiaWs = new VendedorDiaWS(loginEmpleado.get_empleadoId(), theUtilidades.ObtenerDiaId(), loginEmpleado.get_empleadoUsuario(), loginEmpleado.get_token());
            String vendedorDiaWSJson = new JSonParser().GenerarVendedorDiaJson(vendedorDiaWs);
            String encriptedObtenerZonasVenta = new AES().Encriptar(vendedorDiaWSJson, theUtilidades.get_KEY());
            localSoapObject.addProperty("givendata", encriptedObtenerZonasVenta);

            SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            localSoapSerializationEnvelope.dotNet = true;
            localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
            HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);

            try
            {
                localHttpTransportSE.call(CLIENTESENCUESTADOS_SOAP_ACTION, localSoapSerializationEnvelope);
                String cadena = ((SoapPrimitive) localSoapSerializationEnvelope.getResponse()).toString();
                String cadenaDesencriptada = new AES().Decrypt(cadena,theUtilidades.get_KEY());
                if(cadenaDesencriptada.toUpperCase().contains("ERROR"))
                {
                    error = cadenaDesencriptada;
                }
                else
                {
                    Type objectType = new TypeToken<List<ClienteEncuestadoWSResult>>(){ }.getType();
                    clienteEncuestadoWSResults = new Gson().fromJson(cadenaDesencriptada, objectType);
                    WSClientesEncuestados = (clienteEncuestadoWSResults != null);
                }
                return true;
            }
            catch (Exception localException)
            {
                WSClientesEncuestados = false;
                if(localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetClientesEncuestadosByVendedorYDia: vacio");
                }
                else
                {
                    myLog.InsertarLog("App",this.toString(),1,"Error en el webservice WSGetClientesEncuestadosByVendedorYDia: " + localException.getMessage());
                }
                return true;
            }
        }

        protected void onPostExecute(Boolean ejecutado)
        {
            if(pdEsperaObtenerInfo.isShowing())
            {
                pdEsperaObtenerInfo.dismiss();
            }

            if(!ejecutado) {
                theUtilidades.MostrarMensaje(getApplicationContext(), "El webservice WSGetClientesEncuestados no se ejecuto correctamente. ", 1);
                return;
            }

            if (!WSClientesEncuestados)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se encontraron los clientes encuestados que descargar. " + error, 1);
                RegistrarSincronizacionVendedor();
                return;
            }

            if (!BorrarClientesEncuestados())
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo borrar los clientes encuestados.", 1);
                return;
            }
            long i = 0;
            try
            {
                i = new ClienteEncuestadoBLL().InsertarClienteEncuestado( clienteEncuestadoWSResults );
            }
            catch (Exception localException)
            {
                if (localException.getMessage() == null || localException.getMessage().isEmpty())
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los clientes encuestados: vacio");
                }
                else
                {
                    myLog.InsertarLog("App", this.toString(), 1, "Error al insertar los clientes encuestados: " + localException.getMessage());
                }
            }
            if (i == 0)
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los clientes encuestados.", 1);
                return;
            }
            RegistrarSincronizacionVendedor();

        }
    }

    private boolean BorrarClientesEncuestados()
    {
        try
        {
            return new ClienteEncuestadoBLL().BorrarClientesEncuestado();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes encuestados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar los clientes encuestados: " + localException.getMessage());
            }
            return false;
        }
    }

    //Termino la sincronizacion

    public void RegistrarSincronizacionVendedor()
    {
        ArrayList<Rol> listadoRol = null;

        try
        {
            listadoRol = new RolBLL().ObtenerRolesPor(loginEmpleado.get_empleadoId());
        }
        catch (Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado por empleadoId: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener roles del empleado por empleadoId: " + localException.getMessage());
            }
        }

        if(listadoRol == null)
        {
            theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el rol del usuario por empleadoId", 1);
        }
        else
        {
            int rol = 0;
            for(Rol item : listadoRol)
            {
                if(item.get_rol().equals("Vendedor"))
                {
                    rol =3;//Rol 3 = Vendedor
                }

                if(item.get_rol().equals("VendedorProvincia"))
                {
                    rol = 5;//Rol 5 = VendedorProvincia
                }
            }

            if(rol !=0)
            {
                long l = 0;
                try
                {
                    l = new SincronizacionDatosBLL().InsertarSincronizacionDatos(
                            loginEmpleado.get_empleadoId(),
                            loginEmpleado.get_dia(),
                            loginEmpleado.get_mes(),
                            loginEmpleado.get_anio(),
                            rol);
                }
                catch(Exception localException)
                {
                    if(localException.getMessage() == null || localException.getMessage().isEmpty())
                    {
                        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionDatos: vacio");
                    }
                    else
                    {
                        myLog.InsertarLog("App",this.toString(),1,"Error al insertar la sincronizacionDatos: " + localException.getMessage());
                    }
                }

                if(l<=0)
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo insertar los datosSincronizacion.", 1);
                }
                else
                {
                    theUtilidades.MostrarMensaje(getApplicationContext(), "Sincronizacion de datos exitosa.", 1);
                    MostrarControles(true);
                    MostrarControlesEspeciales();
                }
            }
            else
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo determinar el rol del usuario.", 1);
            }
        }
    }

    private void MostrarControles(boolean estado)
    {
        int visibility = 0 ;
        if(estado)
        {
            visibility = View.VISIBLE;
        }
        else
        {
            visibility = View.INVISIBLE;
        }

        ivVendedorSincronizarDatos.setVisibility(visibility);
        ivVendedorRegistrarPreventa.setVisibility(visibility);
        ivVendedorCierrePreventa.setVisibility(visibility);
        ivVendedorSincronizarPreventa.setVisibility(visibility);
        ivVendedorSincronizarNombres.setVisibility(visibility);
        ivVendedorSincronizarNoAtendidos.setVisibility(visibility);
        ivVendedorVisualizarPreventas.setVisibility(visibility);
        ivVendedorRestaurarPreventas.setVisibility(visibility);
        ivVendedorEdicionPreventas.setVisibility(visibility);
        ivVendedorInventarioProductos.setVisibility(visibility);
        ivVendedorAvanceVentasDia.setVisibility(visibility);
        ivVendedorAvanceVentasMes.setVisibility(visibility);
        ivVendedorAvanceVentasMesCategoria.setVisibility(visibility);
        ivVendedorMetasSap.setVisibility(visibility);
        ivVendedorCobroPendiente.setVisibility(visibility);
        ivVendedorVerLogs.setVisibility(visibility);
        ivVendedorAltaCliente.setVisibility(visibility);
        ivVendedorVentaDirecta.setVisibility(visibility);
        ivVendedorVisorVentasDirectas.setVisibility(visibility);
        ivVendedorSincronizarPOP.setVisibility(visibility);
        ivVendedorSincronizarCambios.setVisibility(visibility);
        ivVendedorSincronizarMatcheo.setVisibility(visibility);
        ivVendedorSincroEdClientes.setVisibility(visibility);
        ivVendedorSincroFotos.setVisibility(visibility);
    }

    private void MostrarControlesEspeciales()
    {
        //Alta de Clientes
        if(loginEmpleado.is_modificarClienteApk())
        {
            ivVendedorAltaCliente.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorAltaCliente.setVisibility(View.INVISIBLE);
        }

        if(parametroGeneral==null)
        {
            //Al no encontrar la configuracion de los controles especiales
            //Ocultamos todos
            ivVendedorVentaDirecta.setVisibility(View.INVISIBLE);
            ivVendedorVisorVentasDirectas.setVisibility(View.INVISIBLE);
            ivVendedorSincronizarPOP.setVisibility(View.INVISIBLE);
            ivVendedorSincronizarCambios.setVisibility(View.INVISIBLE);
            ivVendedorSincronizarMatcheo.setVisibility(View.INVISIBLE);
            ivVendedorSincroEdClientes.setVisibility(View.INVISIBLE);
            ivVendedorSincroFotos.setVisibility(View.INVISIBLE);
            return;
        }

        //VentaDirecta
        if(parametroGeneral.is_habilitarVentaDirecta())
        {
            ivVendedorVisorVentasDirectas.setVisibility(View.VISIBLE);
            ivVendedorVentaDirecta.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorVisorVentasDirectas.setVisibility(View.INVISIBLE);
            ivVendedorVentaDirecta.setVisibility(View.INVISIBLE);
        }

        //Sincro Edicion Clientes
        if(parametroGeneral.is_edicionCliente())
        {
            ivVendedorSincroEdClientes.setVisibility(View.VISIBLE);
            ivVendedorSincroFotos.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorSincroEdClientes.setVisibility(View.INVISIBLE);
            ivVendedorSincroFotos.setVisibility(View.INVISIBLE);
        }

        //ProductoPOP
        if(parametroGeneral.is_habilitarPop())
        {
            ivVendedorSincronizarPOP.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorSincronizarPOP.setVisibility(View.INVISIBLE);
        }

        //ProductoCambios
        if(parametroGeneral.is_habilitarCambio())
        {
            ivVendedorSincronizarCambios.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorSincronizarCambios.setVisibility(View.INVISIBLE);
        }

        //EdicionPreventas
        if(parametroGeneral.is_editarPreventas())
        {
            ivVendedorEdicionPreventas.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorEdicionPreventas.setVisibility(View.INVISIBLE);
        }

        //MatcheoCenso
        if(parametroGeneral.is_habilitarMatcheo())
        {
            ivVendedorSincronizarMatcheo.setVisibility(View.VISIBLE);
        }
        else
        {
            ivVendedorSincronizarMatcheo.setVisibility(View.INVISIBLE);
        }
    }

    private boolean VerificarExistenciaClientesNoSincronizados()
    {
        boolean verificado = false;
        ArrayList<Cliente> listadoCliente = null;
        try
        {
            listadoCliente = new ClienteBLL().ObtenerClientesNoSincronizados();
        }
        catch(Exception localException)
        {
            if(localException.getMessage() == null || localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener los clientes no sincronizados: " + localException.getMessage());
            }
        }
        if(listadoCliente == null)
        {
            verificado = true;;
        }
        return verificado;
    }

    public void MostrarPantallaMapaPreventas()
    {
        startActivity(new Intent(this, Vendedormapaclientes.class));
    }

    public boolean VerificarSincronizacionDatos()
    {
        if(loginEmpleado != null)
        {
            ArrayList<Rol> listadoRol = null;
            int rol = 0;

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
                return false;
            }

            if(listadoRol != null)
            {
                for(Rol item :listadoRol)
                {
                    if(item.get_rol().equals("Vendedor"))
                    {
                        rol = 3;
                    }

                    if(item.get_rol().equals("VendedorProvincia"))
                    {
                        rol = 5;
                    }
                }

                sincronizacionDatos = null;
                try
                {
                    sincronizacionDatos = new SincronizacionDatosBLL().ObtenerDetalleSincronizacionDatosPor(
                            loginEmpleado.get_empleadoId(),
                            loginEmpleado.get_dia(),
                            loginEmpleado.get_mes(),
                            loginEmpleado.get_anio(),
                            rol);
                }
                catch (Exception localException)
                {
                    if(localException.getMessage() == null || localException.getMessage().isEmpty())
                    {
                        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos de sincronizacion: vacio");
                    }
                    else
                    {
                        myLog.InsertarLog("App",this.toString(),1,"Error al obtener los datos de sincronizacion: " + localException.getMessage());
                    }
                }

                return sincronizacionDatos != null;
            }
            else
            {
                theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo establecer el rol del usuario.", 1);
                return false;
            }
        }
        else
        {
            theUtilidades.MostrarMensaje(getApplicationContext(), "El vendedor no se logeo correctamente.", 1);
            return false;
        }
    }

    public void MostrarMenuCensista()
    {
        Intent intent = new Intent(this,Menucensista.class);
        intent.putExtra("Origen", "Menuvendedor");
        startActivity(intent);
    }

    public void MostrarPantallaSincronizarPreventas()
    {
        startActivity(new Intent(this, Vendedorsincronizarpreventas.class));
    }

    public void MostrarPantallaCerrarPreventas()
    {
        startActivity(new Intent(this, Vendedorcierrepreventa.class));
    }

    public void MostrarPantallaVerLogs()
    {
        startActivity(new Intent(this, Log.class));
    }

    public void MostrarPantallaAvancesVentaDia()
    {
        startActivity(new Intent(this, Vendedoravanceventasdia.class));
    }

    public void MostrarPantallaAvancesVentaMes()
    {
        startActivity(new Intent(this, Vendedoravanceventasmes.class));
    }

    public void MostrarPantallaAvancesVentaMesVendedor()
    {
        startActivity(new Intent(this, Vendedoravanceventasmescategoria.class));
    }

    public void MostrarPantallaMetasSap()
    {
        startActivity(new Intent(this, Vendedormetassap.class));
    }

    public void MostrarPantallaSincronizacionNombreCliente()
    {
        startActivity(new Intent(this, Vendedorsincronizacionnombrecliente.class));
    }

    public void MostrarPantallaSincronizacionClienteNoAtendidos()
    {
        startActivity(new Intent(this, Vendedorsincronizacionclientenoatendido.class));
    }

    public void MostrarPantallaVisualizarPreventas()
    {
        startActivity(new Intent(this, Vendedorvisualizarpreventas.class));
    }

    public void MostrarPantallaInventarioProductos()
    {
        startActivity(new Intent(this, Vendedorinventarioproductos.class));
    }

    public void MostrarPantallaEdicionPreventas()
    {
        startActivity(new Intent(this, Vendedoredicionpreventas.class));
    }

    public void MostrarPantallaVentaDirecta()
    {
        startActivity(new Intent(this, Vendedorventadirectamapa.class));
    }

    public void MostrarPantallaVisorVentasDirectas()
    {
        startActivity(new Intent(this, Vendedorvisorventasdirectas.class));
    }

    public void MostrarPantallaSincroEdiClientes()
    {
        Intent intent = new Intent(this,Censistasincronizacionedicionclientes.class);
        intent.putExtra("Origen", "Menuvendedor");
        startActivity(intent);
    }

    public void MostrarPantallaSincronizarFotos()
    {
        Intent intent = new Intent(this,Censistasincronizacionclientefoto.class);
        intent.putExtra("Origen", "Menuvendedor");
        startActivity(intent);
    }

    public void MostrarPantallaCobroPendiente()
    {
        startActivity(new Intent(this, Vendedorcobrospendientes.class));
    }

    public void MostrarPantallaSincronizarPOP()
    {
        startActivity(new Intent(this, Vendedorsincronizarpop.class));
    }

    public void MostrarPantallaSincronizarCambios()
    {
        startActivity(new Intent(this, Vendedorsincronizacionpreventacambios.class));
    }

    public void MostrarPantallaSincronizarMatcheo()
    {
        startActivity(new Intent(this, Censistaunificacionclientessincronizacion.class));
    }

    public void MostrarPantallaRestauracionPreventa()
    {
        startActivity(new Intent(this, Vendedorrestauracionpreventa.class));
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,Menuprincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
