package Utilidades;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import Clases.AlmacenPOPProductoWSResult;
import Clases.AlmacenPOPWSResult;
import Clases.AlmacenProductoWSResult;
import Clases.AlmacenTempWSResult;
import Clases.ApkClienteWS;
import Clases.ApkDistRutaClienteWSResult;
import Clases.ApkRutaClienteWSResult;
import Clases.AsignacionRutaWSResult;
import Clases.AvanceVendedorDiaWSResult;
import Clases.AvanceVentaVendedorWSResult;
import Clases.CanalRutaPrecioWSResult;
import Clases.CanalRutaWSResult;
import Clases.CategoriaPOPWSResult;
import Clases.CategoriaWSResult;
import Clases.CiudadWSResult;
import Clases.ClienteCensoWSResult;
import Clases.ClienteDatosVentaWS;
import Clases.ClienteEncuestadoWSResult;
import Clases.ClienteIncentivo;
import Clases.ClienteNitWSResult;
import Clases.ClienteNroFotoWSResult;
import Clases.ClienteVentaWSResult;
import Clases.ClienteWSResult;
import Clases.CobroPendienteWSResult;
import Clases.CondicionTributariaWSResult;
import Clases.CostoWSResult;
import Clases.DatosFacturaWSResult;
import Clases.DevolucionDistribuidorProductoWSResult;
import Clases.DevolucionDistribuidorWSResult;
import Clases.DiaSemanaWSResult;
import Clases.DistribuidorWSResult;
import Clases.DosificacionProveedorWSResult;
import Clases.DosificacionWSResult;
import Clases.DraRebateSaldoWSResult;
import Clases.EmpleadoWSResult;
import Clases.EncuestaDetalleWSResult;
import Clases.EncuestaListaWSResult;
import Clases.EncuestaWSResult;
import Clases.ExpedidoWSResult;
import Clases.Fecha;
import Clases.FotoCategoriaWSResult;
import Clases.ImpresoraWSResult;
import Clases.LoginEmpleado;
import Clases.MercadoWSResult;
import Clases.MotivoCambioWSResult;
import Clases.MotivoEliminacionMatchWSResult;
import Clases.MotivoNoAtencionWSResult;
import Clases.MotivoNoEntregaWSResult;
import Clases.MotivoPopWSResult;
import Clases.ParametroGeneralWSResult;
import Clases.PrecioListaNombreWSResult;
import Clases.PrecioWSResult;
import Clases.PreventaBonificacionWSResult;
import Clases.PreventaCambioWSResult;
import Clases.PreventaDisWSResult;
import Clases.PreventaProductoCambioWSResult;
import Clases.PreventaProductoDistWSResult;
import Clases.PreventaProductoPOPWSResult;
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
import Clases.Relevamiento;
import Clases.TipoCalleWSResult;
import Clases.TipoNegocioWSResult;
import Clases.TipoNitWSResult;
import Clases.VendedorZonaVentaWSResult;
import Clases.VentaBonificacionWSResult;
import Clases.ZonaMercadoWSResult;
import Clases.ZonaVentaWSResult;
import Clases.ZonaWSResult;

public class AdministradorDB 
{
	private ControladorDB controladorDB;
	private SQLiteDatabase db;
	
	public AdministradorDB(Context context)
	{
		controladorDB = new ControladorDB(context);
	}
	public AdministradorDB OpenDB()
	{
		db = controladorDB.getWritableDatabase();
		return this;
	}
	
	public void CloseDB()
	{
		db.close();
	}
	
	private String ObtenerFechaStringFromJson(String fechaJson) {
        String fechaFormato = "";
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        String jsonString = String.valueOf(fechaJson);
        Calendar calendar = Calendar.getInstance();

        String jsonStringFormatted = jsonString.replace("/Date(", "").replace(")/", "");
        Long timeInMillis = Long.valueOf(jsonStringFormatted);
        calendar.setTimeInMillis(timeInMillis);

        return fechaFormato = myFormat.format(calendar.getTime());
    }
	
	//"LOGINEMPLEADO"//
	public static final String KEY_LOGINEMPLEADO_ROW_ID = "_Id";
	public static final String KEY_LOGINEMPLEADO_EMPLEADOID = "_empleadoId";
	public static final String KEY_LOGINEMPLEADO_EMPLEADONOMBRE = "_empleadoNombre";
	public static final String KEY_LOGINEMPLEADO_EMPLEADOUSUARIO = "_empleadoUsuario";
	public static final String KEY_LOGINEMPLEADO_DIA = "_dia";
	public static final String KEY_LOGINEMPLEADO_MES = "_mes";
	public static final String KEY_LOGINEMPLEADO_ANIO = "_anio";
	public static final String KEY_LOGINEMPLEADO_ESTADO = "_estado";
	public static final String KEY_LOGINEMPLEADO_MENSAJE = "_mensaje";
	public static final String KEY_LOGINEMPLEADO_ALMACENID = "_almacenId";
	public static final String KEY_LOGINEMPLEADO_VENDEDORRUTAID = "_vendedorRutaId";
	public static final String KEY_LOGINEMPLEADO_MODIFICARCLIENTEAPK = "_modificarClienteAPK";
	public static final String KEY_LOGINEMPLEADO_TOKEN = "_token";
	
	public static final int COL_LOGINEMPLEADO_ROW_ID = 0;
	public static final int COL_LOGINEMPLEADO_EMPLEADOID = 1;
	public static final int COL_LOGINEMPLEADO_EMPLEADONOMBRE = 2;
	public static final int COL_LOGINEMPLEADO_EMPLEADOUSUARIO = 3;
	public static final int COL_LOGINEMPLEADO_DIA = 4;
	public static final int COL_LOGINEMPLEADO_MES = 5;
	public static final int COL_LOGINEMPLEADO_ANIO = 6;
	public static final int COL_LOGINEMPLEADO_ESTADO = 7;
	public static final int COL_LOGINEMPLEADO_MENSAJE = 8;
	public static final int COL_LOGINEMPLEADO_ALMACENID = 9;
	public static final int COL_LOGINEMPLEADO_VENDEDORRUTAID = 10;
	public static final int COL_LOGINEMPLEADO_MODIFICARCLIENTEAPK = 11;
	public static final int COL_LOGINEMPLEADO_TOKEN = 12;
	
	public static final String[] LOGINEMPLEADO_ALL_KEYS = {
		KEY_LOGINEMPLEADO_ROW_ID, KEY_LOGINEMPLEADO_EMPLEADOID, KEY_LOGINEMPLEADO_EMPLEADONOMBRE,
		KEY_LOGINEMPLEADO_EMPLEADOUSUARIO,KEY_LOGINEMPLEADO_DIA,KEY_LOGINEMPLEADO_MES,KEY_LOGINEMPLEADO_ANIO, 
		KEY_LOGINEMPLEADO_ESTADO, KEY_LOGINEMPLEADO_MENSAJE, KEY_LOGINEMPLEADO_ALMACENID,
		KEY_LOGINEMPLEADO_VENDEDORRUTAID, KEY_LOGINEMPLEADO_MODIFICARCLIENTEAPK, KEY_LOGINEMPLEADO_TOKEN};
	
	public static final String LOGINEMPLEADO_TABLE_NAME = "tbl_LoginEmpleado";
	
	public static final String LOGINEMPLEADO_TABLE_CREATE = "CREATE TABLE " + LOGINEMPLEADO_TABLE_NAME + "("
			+ KEY_LOGINEMPLEADO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
			+ KEY_LOGINEMPLEADO_EMPLEADOID + " integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_EMPLEADONOMBRE + " text NOT NULL, "
			+ KEY_LOGINEMPLEADO_EMPLEADOUSUARIO + " text NOT NULL, "
			+ KEY_LOGINEMPLEADO_DIA + " integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_MES + " integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_ANIO + " integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_ESTADO +" integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_MENSAJE + " text, "
			+ KEY_LOGINEMPLEADO_ALMACENID + " integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_VENDEDORRUTAID + " integer NOT NULL, "
			+ KEY_LOGINEMPLEADO_MODIFICARCLIENTEAPK + " boolean NOT NULL, "
			+ KEY_LOGINEMPLEADO_TOKEN + " text NOT NULL);";
	
	public boolean borrarLoginEmpleadoPor(long id)
	{
	    String str = "_Id=" + id;
	    return this.db.delete(LOGINEMPLEADO_TABLE_NAME,str, null) > 0;
	}
	  
	public void borrarLoginsEmpleado()
	{
	    Cursor localCursor = obtenerLoginsEmpleado();
	    long l = localCursor.getColumnIndexOrThrow("_Id");
	   if (localCursor.moveToFirst()) 
	   {
		   do
		   {
			   borrarLoginEmpleadoPor(localCursor.getLong((int)l));
		   } 
		   while (localCursor.moveToNext());
	   }
	   localCursor.close();
	}
	
	public long insertarLoginEmpleado(int empleadoId, String empleadoNombre, String EmpleadoUsuario, int dia, 
				int mes, int anio, int estado, String mensaje, int almacenId, int vendedorRutaId, boolean modificarClienteApk,
			 	String token)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_empleadoId", empleadoId);
	    localContentValues.put("_empleadoNombre", empleadoNombre);
	    localContentValues.put("_empleadoUsuario", EmpleadoUsuario);
	    localContentValues.put("_dia", dia);
	    localContentValues.put("_mes", mes);
	    localContentValues.put("_anio", anio);
	    localContentValues.put("_estado", estado);
	    localContentValues.put("_mensaje", mensaje);
	    localContentValues.put("_almacenId", almacenId);
	    localContentValues.put("_vendedorRutaId", vendedorRutaId);
	    localContentValues.put("_modificarClienteApk", modificarClienteApk);
		localContentValues.put("_token", token);
	    return this.db.insert("tbl_LoginEmpleado", null, localContentValues);
	}
	  
	public Cursor obtenerLoginEmpleadoDetallePor(int dia, int mes, int anio)
	{
		String str = "_dia=" + dia + " and " + "_mes" + "=" + mes + " and " + "_anio" + "=" + anio;
		Cursor localCursor = this.db.query(true, LOGINEMPLEADO_TABLE_NAME, LOGINEMPLEADO_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}
	  
	public Cursor obtenerLoginEmpleadoDetallePorEmpleadoUsuario(String empleadoUsuario, int dia, int mes, int anio)
	{
		String str = "_empleadoUsuario='" + empleadoUsuario + "' and _dia = " + dia + " and _mes =" + mes + " and _anio =" + anio;
		Cursor localCursor = this.db.query(true, LOGINEMPLEADO_TABLE_NAME, LOGINEMPLEADO_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}
	  
	public Cursor obtenerLoginsEmpleado()
	{
		Cursor localCursor = this.db.query(true, LOGINEMPLEADO_TABLE_NAME, LOGINEMPLEADO_ALL_KEYS, null, null, null, null, null, null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}
	
	public int ModificarLoginEmpleadoFechaPor(int empleadoId,int dia,int mes,int anio)
    {
      String str = "_empleadoId=" + empleadoId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_dia", dia);
      localContentValues.put("_mes", mes);
      localContentValues.put("_anio", anio);
      return this.db.update(LOGINEMPLEADO_TABLE_NAME, localContentValues, str, null);
    }
	
	//"SINCRONIZACIONDATOS"//	  
	public static final String KEY_SINCRONIZACIONDATOS_ROW_ID = "_Id";
	public static final String KEY_SINCRONIZACIONDATOS_EMPLEADOID = "_empleadoId";
	public static final String KEY_SINCRONIZACIONDATOS_DIA = "_dia";
	public static final String KEY_SINCRONIZACIONDATOS_MES = "_mes";
	public static final String KEY_SINCRONIZACIONDATOS_ANIO = "_anio";
	public static final String KEY_SINCRONIZACIONDATOS_ROL = "_rol";  
	  
	public static final int COL_SINCRONIZACIONDATOS_ROW_ID = 0;
	public static final int COL_SINCRONIZACIONDATOS_EMPLEADOID = 1;
	public static final int COL_SINCRONIZACIONDATOS_DIA = 2;
	public static final int COL_SINCRONIZACIONDATOS_MES = 3;
	public static final int COL_SINCRONIZACIONDATOS_ANIO = 4;
	public static final int COL_SINCRONIZACIONDATOS_ROL = 5;
	  
	public static final String[] SINCRONIZACIONDATOS_ALL_KEYS = { 
		KEY_SINCRONIZACIONDATOS_ROW_ID, KEY_SINCRONIZACIONDATOS_EMPLEADOID,
		KEY_SINCRONIZACIONDATOS_DIA, KEY_SINCRONIZACIONDATOS_MES,
		KEY_SINCRONIZACIONDATOS_ANIO,KEY_SINCRONIZACIONDATOS_ROL};
	
	public static final String SINCRONIZACIONDATOS_TABLE_NAME = "tbl_SincronizacionDatos";
	
	public static final String SINCRONIZACIONDATOS_TABLE_CREATE = "CREATE TABLE " + SINCRONIZACIONDATOS_TABLE_NAME + "("
			  + KEY_SINCRONIZACIONDATOS_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT," 
			  + KEY_SINCRONIZACIONDATOS_EMPLEADOID + " integer NOT NULL, "
			  + KEY_SINCRONIZACIONDATOS_DIA + " integer NOT NULL,"
			  + KEY_SINCRONIZACIONDATOS_MES + " integer NOT NULL,"
			  + KEY_SINCRONIZACIONDATOS_ANIO + " integer NOT NULL,"
			  + KEY_SINCRONIZACIONDATOS_ROL + " integer NOT NULL);";
	
	public boolean borrarSincronizacionDatosPor(long id)
	{
	  String str = "_Id=" + id;
	  return this.db.delete(SINCRONIZACIONDATOS_TABLE_NAME, str, null) > 0;
	}
	  
	public void borrarSincronizacionesDatos()
	{
	  Cursor localCursor = obtenerSincronizacionesDatos();
	  long l = localCursor.getColumnIndexOrThrow("_Id");
	  if (localCursor.moveToFirst()) 
	  {
	    do
	    {
	      borrarSincronizacionDatosPor(localCursor.getLong((int)l));
	    } 
	    while (localCursor.moveToNext());
	  }
	  localCursor.close();
	}
	  
	public long insertarSincronizacionDatos(int empleadoId, int dia, int mes, int anio,int rol)
	{
	  ContentValues localContentValues = new ContentValues();
	  localContentValues.put("_empleadoId", empleadoId);
	  localContentValues.put("_dia", dia);
	  localContentValues.put("_mes", mes);
	  localContentValues.put("_anio", anio);
	  localContentValues.put("_rol", rol);
	  return this.db.insert(SINCRONIZACIONDATOS_TABLE_NAME, null, localContentValues);
	}
	  
	public Cursor obtenerDetalleSincronizacionDatosPor(int empleadoId, int dia, int mes, int anio,int rol)
	{
	  String str = "_empleadoId=" + empleadoId + " and _dia=" + dia + " and _mes=" + mes + " and _anio=" + anio + " and _rol=" +rol;
	  Cursor localCursor = this.db.query(true,SINCRONIZACIONDATOS_TABLE_NAME, SINCRONIZACIONDATOS_ALL_KEYS, str, null, null, null, null, null);
	  if (localCursor != null) 
	  {
	    localCursor.moveToFirst();
	  }
	  return localCursor;
	}
	  
	public Cursor obtenerSincronizacionesDatos()
	{
	  Cursor localCursor = this.db.query(true,SINCRONIZACIONDATOS_TABLE_NAME, SINCRONIZACIONDATOS_ALL_KEYS, null, null, null, null, null, null);
	  if (localCursor != null) {
	    localCursor.moveToFirst();
	  }
	  return localCursor;
	}
	
	public int ModificarFechaSincronizacionDatos(int empleadoId,int dia,int mes,int anio)
    {
      String str = "_empleadoId=" + empleadoId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_dia", dia);
      localContentValues.put("_mes", mes);
      localContentValues.put("_anio", anio);
      return this.db.update(SINCRONIZACIONDATOS_TABLE_NAME, localContentValues, str, null);
    }
	  
	//CLIENTEPREVENTA//
	public static final String KEY_CLIENTEPREVENTA_ROW_ID = "_Id";
	public static final String KEY_CLIENTEPREVENTA_CLIENTEID = "_clienteId";
	public static final String KEY_CLIENTEPREVENTA_CODIGO = "_codigo";
	public static final String KEY_CLIENTEPREVENTA_CLIENTETIPONEGOCIOID = "_clienteTipoNegocioId";
	public static final String KEY_CLIENTEPREVENTA_NOMBRECOMPLETO = "_nombreCompleto";
	public static final String KEY_CLIENTEPREVENTA_LATITUD = "_latitud";
	public static final String KEY_CLIENTEPREVENTA_LONGITUD = "_longitud";
	public static final String KEY_CLIENTEPREVENTA_DIRECCION = "_direccion";
	public static final String KEY_CLIENTEPREVENTA_TELEFONO = "_telefono";
	public static final String KEY_CLIENTEPREVENTA_CELULAR = "_celular";
	public static final String KEY_CLIENTEPREVENTA_PRECIOLISTAID = "_precioListaId";
	public static final String KEY_CLIENTEPREVENTA_DESCUENTO = "_descuento";
	public static final String KEY_CLIENTEPREVENTA_TIPOPAGOID = "_tipoPagoId";
	public static final String KEY_CLIENTEPREVENTA_DIASPAGO = "_diasPago";
	public static final String KEY_CLIENTEPREVENTA_TOPECREDITO = "_topeCredito";
	public static final String KEY_CLIENTEPREVENTA_RUTAID = "_rutaId";
	public static final String KEY_CLIENTEPREVENTA_RUTADESCRIPCION = "_rutaDescripcion";
	public static final String KEY_CLIENTEPREVENTA_RAZONSOCIAL = "_razonSocial";
	public static final String KEY_CLIENTEPREVENTA_AUTOVENTA = "_autoventa";
	public static final String KEY_CLIENTEPREVENTA_ESTADOATENDIDO = "_estadoAtendido";
	public static final String KEY_CLIENTEPREVENTA_CLIENTEPUNTEOSINCRONIZADO = "_clientePunteoSincronizado";
	public static final String KEY_CLIENTEPREVENTA_NOMBRESINCRONIZACION = "_nombreSincronizacion";  
	public static final String KEY_CLIENTEPREVENTA_NOMBRES = "_nombres";
	public static final String KEY_CLIENTEPREVENTA_PATERNO = "_paterno";
	public static final String KEY_CLIENTEPREVENTA_MATERNO = "_materno";
	public static final String KEY_CLIENTEPREVENTA_APELLIDOCASADA = "_apellidoCasada";
	public static final String KEY_CLIENTEPREVENTA_TIPONEGOCIOID = "_tipoNegocioId";
	public static final String KEY_CLIENTEPREVENTA_ZONAID = "_zonaId";
	public static final String KEY_CLIENTEPREVENTA_NOMBREFACTURA = "_nombreFactura";
	public static final String KEY_CLIENTEPREVENTA_NIT = "_nit";
	public static final String KEY_CLIENTEPREVENTA_DIAID = "_diaId";
	public static final String KEY_CLIENTEPREVENTA_REFERENCIA = "_referencia";
	public static final String KEY_CLIENTEPREVENTA_PROMEDIOVENTA = "_promedioVenta";
	public static final String KEY_CLIENTEPREVENTA_CI = "_ci";
	public static final String KEY_CLIENTEPREVENTA_CONTACTO = "_contacto";
	public static final String KEY_CLIENTEPREVENTA_MONTOPENDIENTECREDITO = "_montoPendienteCredito";
	public static final String KEY_CLIENTEPREVENTA_PROVINCIAID = "_provinciaId";
	public static final String KEY_CLIENTEPREVENTA_RUTA = "_ruta";
	public static final String KEY_CLIENTEPREVENTA_TIPOVISITA = "_tipoVisita";
	public static final String KEY_CLIENTEPREVENTA_ZONAVENTAID = "_zonaVentaId";
	public static final String KEY_CLIENTEPREVENTA_ZONA = "_zona";
	public static final String KEY_CLIENTEPREVENTA_MERCADO = "_mercado";
	public static final String KEY_CLIENTEPREVENTA_CANALRUTAID = "_canalRutaId";
	public static final String KEY_CLIENTEPREVENTA_OBSERVACION = "_observacion";
	public static final String KEY_CLIENTEPREVENTA_VERIFICADOFOTO = "_verificadoFoto";
	
	public static final int COL_CLIENTEPREVENTA_ROW_ID = 0;
	public static final int COL_CLIENTEPREVENTA_CLIENTEID = 1;
	public static final int COL_CLIENTEPREVENTA_CODIGO = 2;
	public static final int COL_CLIENTEPREVENTA_CLIENTETIPONEGOCIOID = 3;
	public static final int COL_CLIENTEPREVENTA_NOMBRECOMPLETO = 4;
	public static final int COL_CLIENTEPREVENTA_LATITUD = 5;
	public static final int COL_CLIENTEPREVENTA_LONGITUD = 6;
	public static final int COL_CLIENTEPREVENTA_DIRECCION = 7;
	public static final int COL_CLIENTEPREVENTA_TELEFONO = 8;
	public static final int COL_CLIENTEPREVENTA_CELULAR = 9;
	public static final int COL_CLIENTEPREVENTA_PRECIOLISTAID = 10;
	public static final int COL_CLIENTEPREVENTA_DESCUENTO = 11;
	public static final int COL_CLIENTEPREVENTA_TIPOPAGOID = 12;
	public static final int COL_CLIENTEPREVENTA_DIASPAGO = 13;
	public static final int COL_CLIENTEPREVENTA_TOPECREDITO = 14;
	public static final int COL_CLIENTEPREVENTA_RUTAID = 15;
	public static final int COL_CLIENTEPREVENTA_RUTADESCRIPCION = 16;
	public static final int COL_CLIENTEPREVENTA_RAZONSOCIAL = 17;
	public static final int COL_CLIENTEPREVENTA_AUTOVENTA = 18;
	public static final int COL_CLIENTEPREVENTA_ESTADOATENDIDO = 19;
	public static final int COL_CLIENTEPREVENTA_CLIENTEPUNTEOSINCRONIZADO = 20;
	public static final int COL_CLIENTEPREVENTA_NOMBRESINCRONIZACION = 21;
	public static final int COL_CLIENTEPREVENTA_NOMBRES = 22;
	public static final int COL_CLIENTEPREVENTA_PATERNO = 23;
	public static final int COL_CLIENTEPREVENTA_MATERNO = 24;
	public static final int COL_CLIENTEPREVENTA_APELLIDOCASADA = 25;
	public static final int COL_CLIENTEPREVENTA_TIPONEGOCIOID = 26;
	public static final int COL_CLIENTEPREVENTA_ZONAID = 27;
	public static final int COL_CLIENTEPREVENTA_NOMBREFACTURA = 28;
	public static final int COL_CLIENTEPREVENTA_NIT = 29;
	public static final int COL_CLIENTEPREVENTA_DIAID = 30;
	public static final int COL_CLIENTEPREVENTA_REFERENCIA = 31;
	public static final int COL_CLIENTEPREVENTA_PROMEDIOVENTA = 32;
	public static final int COL_CLIENTEPREVENTA_CI = 33;
	public static final int COL_CLIENTEPREVENTA_CONTACTO = 34;
	public static final int COL_CLIENTEPREVENTA_MONTOPENDIENTECREDITO = 35;
	public static final int COL_CLIENTEPREVENTA_PROVINCIAID = 36;
	public static final int COL_CLIENTEPREVENTA_RUTA = 37;
	public static final int COL_CLIENTEPREVENTA_TIPOVISITA = 38;
	public static final int COL_CLIENTEPREVENTA_ZONAVENTAID = 39;
	public static final int COL_CLIENTEPREVENTA_ZONA = 40;
	public static final int COL_CLIENTEPREVENTA_MERCADO = 41;
	public static final int COL_CLIENTEPREVENTA_CANALRUATID = 42;
	public static final int COL_CLIENTEPREVENTA_OBSERVACION = 43;
	public static final int COL_CLIENTEPREVENTA_VERIFICADOFOTO = 44;
	
	public static final String CLIENTEPREVENTA_TABLE_NAME = "tbl_ClientePreventa";
	
    public static final String[] CLIENTEPREVENTA_ALL_KEYS = new String[] { 
    	KEY_CLIENTEPREVENTA_ROW_ID, KEY_CLIENTEPREVENTA_CLIENTEID, KEY_CLIENTEPREVENTA_CODIGO, 
    	KEY_CLIENTEPREVENTA_CLIENTETIPONEGOCIOID, KEY_CLIENTEPREVENTA_NOMBRECOMPLETO,
    	KEY_CLIENTEPREVENTA_LATITUD, KEY_CLIENTEPREVENTA_LONGITUD, KEY_CLIENTEPREVENTA_DIRECCION, KEY_CLIENTEPREVENTA_TELEFONO,
    	KEY_CLIENTEPREVENTA_CELULAR,KEY_CLIENTEPREVENTA_PRECIOLISTAID, KEY_CLIENTEPREVENTA_DESCUENTO, KEY_CLIENTEPREVENTA_TIPOPAGOID,
    	KEY_CLIENTEPREVENTA_DIASPAGO, KEY_CLIENTEPREVENTA_TOPECREDITO, KEY_CLIENTEPREVENTA_RUTAID, KEY_CLIENTEPREVENTA_RUTADESCRIPCION,
    	KEY_CLIENTEPREVENTA_RAZONSOCIAL, KEY_CLIENTEPREVENTA_AUTOVENTA, KEY_CLIENTEPREVENTA_ESTADOATENDIDO, 
    	KEY_CLIENTEPREVENTA_CLIENTEPUNTEOSINCRONIZADO,KEY_CLIENTEPREVENTA_NOMBRESINCRONIZACION,KEY_CLIENTEPREVENTA_NOMBRES,
    	KEY_CLIENTEPREVENTA_PATERNO,KEY_CLIENTEPREVENTA_MATERNO,KEY_CLIENTEPREVENTA_APELLIDOCASADA,
    	KEY_CLIENTEPREVENTA_TIPONEGOCIOID,KEY_CLIENTEPREVENTA_ZONAID,KEY_CLIENTEPREVENTA_NOMBREFACTURA,KEY_CLIENTEPREVENTA_NIT,
    	KEY_CLIENTEPREVENTA_DIAID,KEY_CLIENTEPREVENTA_REFERENCIA,KEY_CLIENTEPREVENTA_PROMEDIOVENTA,KEY_CLIENTEPREVENTA_CI,
    	KEY_CLIENTEPREVENTA_CONTACTO,KEY_CLIENTEPREVENTA_MONTOPENDIENTECREDITO,KEY_CLIENTEPREVENTA_PROVINCIAID,
    	KEY_CLIENTEPREVENTA_RUTA,KEY_CLIENTEPREVENTA_TIPOVISITA,KEY_CLIENTEPREVENTA_ZONAVENTAID,
    	KEY_CLIENTEPREVENTA_ZONA,KEY_CLIENTEPREVENTA_MERCADO, KEY_CLIENTEPREVENTA_CANALRUTAID,
    	KEY_CLIENTEPREVENTA_OBSERVACION, KEY_CLIENTEPREVENTA_VERIFICADOFOTO};
	
	public static final String CLIENTEPREVENTA_TABLE_CREATE = "CREATE TABLE "+ CLIENTEPREVENTA_TABLE_NAME + "("
			+ KEY_CLIENTEPREVENTA_ROW_ID +" integer PRIMARY KEY AUTOINCREMENT, "
			+ KEY_CLIENTEPREVENTA_CLIENTEID + " integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CODIGO + " text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CLIENTETIPONEGOCIOID + " integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_NOMBRECOMPLETO + " text NOT NULL,"
			+ KEY_CLIENTEPREVENTA_LATITUD + " double NOT NULL, "
			+ KEY_CLIENTEPREVENTA_LONGITUD + " double NOT NULL, "
			+ KEY_CLIENTEPREVENTA_DIRECCION + " text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_TELEFONO + " text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CELULAR + " text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_PRECIOLISTAID + " integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_DESCUENTO + " float NOT NULL, "
			+ KEY_CLIENTEPREVENTA_TIPOPAGOID + " integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_DIASPAGO + " integer NOT NULL,"
			+ KEY_CLIENTEPREVENTA_TOPECREDITO + " float NOT NULL, "
			+ KEY_CLIENTEPREVENTA_RUTAID + " integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_RUTADESCRIPCION + " text, "
			+ KEY_CLIENTEPREVENTA_RAZONSOCIAL + " text, "
			+ KEY_CLIENTEPREVENTA_AUTOVENTA + " boolean NOT NULL, "
			+ KEY_CLIENTEPREVENTA_ESTADOATENDIDO + " boolean NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CLIENTEPUNTEOSINCRONIZADO + " boolean NOT NULL, "
			+ KEY_CLIENTEPREVENTA_NOMBRESINCRONIZACION +" boolean NOT NULL,"
			+ KEY_CLIENTEPREVENTA_NOMBRES +" text NOT NULL,"
			+ KEY_CLIENTEPREVENTA_PATERNO +" text NOT NULL,"
			+ KEY_CLIENTEPREVENTA_MATERNO +" text NOT NULL,"
			+ KEY_CLIENTEPREVENTA_APELLIDOCASADA +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_TIPONEGOCIOID +" integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_ZONAID +" integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_NOMBREFACTURA +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_NIT +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_DIAID +" integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_REFERENCIA +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_PROMEDIOVENTA +" float NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CI +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CONTACTO +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_MONTOPENDIENTECREDITO +" float NOT NULL, "
			+ KEY_CLIENTEPREVENTA_PROVINCIAID +" integer NOT NULL,"
			+ KEY_CLIENTEPREVENTA_RUTA +" text NOT NULL,"
			+ KEY_CLIENTEPREVENTA_TIPOVISITA +" text NOT NULL,"
			+ KEY_CLIENTEPREVENTA_ZONAVENTAID +" integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_ZONA +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_MERCADO +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_CANALRUTAID +" integer NOT NULL, "
			+ KEY_CLIENTEPREVENTA_OBSERVACION +" text NOT NULL, "
			+ KEY_CLIENTEPREVENTA_VERIFICADOFOTO +" text NOT NULL);";
	
	public boolean borrarClientePreventaPor(long id)
	{
	  String str = "_Id=" + id;
	  return this.db.delete(CLIENTEPREVENTA_TABLE_NAME, str, null) > 0;
	}
	
	public void borrarClientesPreventa()
	{
	  Cursor localCursor = obtenerClientesPreventa();
	  long l = localCursor.getColumnIndexOrThrow("_Id");
	  if (localCursor.moveToFirst()) 
	  {
	    do
	    {
	      borrarClientePreventaPor(localCursor.getLong((int)l));
	    } 
	    while (localCursor.moveToNext());
	  }
	  localCursor.close();
	}
	
	public boolean borrarClientesSinPreventa()
	{
		String query = "_clienteId NOT IN (SELECT _clienteId FROM tbl_preventa) AND _clienteId NOT IN (SELECT _clienteId FROM tbl_ventaDirecta)";
		return this.db.delete(CLIENTEPREVENTA_TABLE_NAME, query,null) > 0;
	}
	
	public long insertarClientePreventa(ArrayList<ApkClienteWS> clientesPreventa)
	{
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClientePreventa(" +
					"_clienteId, _codigo, _clienteTipoNegocioId, _nombreCompleto, _latitud, _longitud, " +
					"_direccion, _telefono, _celular, _precioListaId, _descuento, _tipoPagoId, _diasPago, _topeCredito, " +
					"_rutaId, _rutaDescripcion, _razonSocial, _autoventa, _estadoAtendido, _clientePunteoSincronizado, " +
					"_nombreSincronizacion, _nombres, _paterno, _materno, _apellidoCasada, _tipoNegocioId, _zonaId, " +
					"_nombreFactura, _nit, _diaId, _referencia, _promedioVenta, _ci, _contacto, _montoPendienteCredito, " +
					"_provinciaId, _ruta, _tipoVisita, _zonaVentaId, _zona, _mercado, _canalRutaId, _observacion, _verificadoFoto)" +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ApkClienteWS item : clientesPreventa)
			{
				if(obtenerPreventaPorClienteId(item.getClienteId()).getCount() == 0 && obtenerVentaDirectaPorClienteId(item.getClienteId()).getCount() == 0)
				{
					stmt.bindLong(1,item.getClienteId());
					stmt.bindString(2,item.getCodigo());
					stmt.bindLong(3,item.getTipoNegocioId());
					stmt.bindString(4,item.getNombreCompleto());
					stmt.bindDouble(5,item.getLatitud());
					stmt.bindDouble(6,item.getLongitud());
					stmt.bindString(7,item.getDireccion());
					stmt.bindString(8,item.getTelefono());
					stmt.bindString(9,item.getCelular());
					stmt.bindLong(10,item.getPrecioListaId());
					stmt.bindDouble(11,item.getDescuento());
					stmt.bindLong(12,item.getTipoPagoId());
					stmt.bindLong(13,item.getDiasPago());
					stmt.bindDouble(14,item.getTopeCredito());
					stmt.bindLong(15,item.getRutaId());
					stmt.bindString(16,"");
					stmt.bindString(17,item.getRazonSocial());
					stmt.bindLong(18,item.isAutoVenta()?1:0);
					stmt.bindLong(19,0);
					stmt.bindLong(20,1);
					stmt.bindLong(21,1);
					stmt.bindString(22,item.getNombres());
					stmt.bindString(23,item.getPaterno());
					stmt.bindString(24,item.getMaterno());
					stmt.bindString(25,item.getApellidoCasada());
					stmt.bindLong(26,0);
					stmt.bindLong(27,0);
					stmt.bindString(28,"");
					stmt.bindString(29,"");
					stmt.bindLong(30,item.getDiaId());
					stmt.bindString(31,item.getReferencia());
					stmt.bindDouble(32,item.getPromedioVenta());
					stmt.bindString(33,item.getCi());
					stmt.bindString(34,"");
					stmt.bindDouble(35,item.getMontoPendienteCredito());
					stmt.bindLong(36,0);
					stmt.bindString(37,item.getRuta());
					stmt.bindString(38,item.getTipoVisita());
					stmt.bindLong(39,item.getZonaVentaId());
					stmt.bindString(40,item.getZona());
					stmt.bindString(41,item.getMercado());
					stmt.bindLong(42,item.getCanalRutaId());
					stmt.bindString(43,item.getObservacion());
					stmt.bindLong(44,item.isVerificadoFoto()?1:0);

					stmt.executeInsert();
					stmt.clearBindings();
				}
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
	}

	public long insertarClientePreventa(int clienteId, String codigo, int clienteTipoNegocioId,
										String nombreCompleto, double latitud, double longitud, String direccion, String telefono,
										String celular, int precioListaId, float descuento, int tipoPagoId, int diasPago, float topeCredito,
										int rutaId, String rutaDescripcion, String razonSocial, boolean autoventa, boolean estadoAtendido,
										boolean clientePunteoSincronizado,boolean nombreSincronizacion,String nombres,String paterno,String materno,
										String apellidoCasada,int tipoNegocioId,int zonaId,String nombreFactura,String nit,int diaId,String referencia,
										float promedioVenta,String ci,String contacto,float montoPendienteCredito,int provinciaId,
										String ruta,String tipoVisita,int zonaVentaId,String zona, String mercado, int canalRutaId,
										String observacion, boolean verificadoFoto)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_clienteId", clienteId);
		localContentValues.put("_codigo", codigo);
		localContentValues.put("_clienteTipoNegocioId", clienteTipoNegocioId);
		localContentValues.put("_nombreCompleto", nombreCompleto);
		localContentValues.put("_latitud", latitud);
		localContentValues.put("_longitud", longitud);
		localContentValues.put("_direccion", direccion);
		localContentValues.put("_telefono", telefono);
		localContentValues.put("_celular", celular);
		localContentValues.put("_precioListaId", precioListaId);
		localContentValues.put("_descuento", descuento);
		localContentValues.put("_tipoPagoId", tipoPagoId);
		localContentValues.put("_diasPago", diasPago);
		localContentValues.put("_topeCredito", topeCredito);
		localContentValues.put("_rutaId", rutaId);
		localContentValues.put("_rutaDescripcion", rutaDescripcion);
		localContentValues.put("_razonSocial", razonSocial);
		localContentValues.put("_autoventa", autoventa);
		localContentValues.put("_estadoAtendido", estadoAtendido);
		localContentValues.put("_nombreSincronizacion", nombreSincronizacion);
		localContentValues.put("_clientePunteoSincronizado", clientePunteoSincronizado);
		localContentValues.put("_nombres",nombres);
		localContentValues.put("_paterno",paterno);
		localContentValues.put("_materno",materno);
		localContentValues.put("_apellidoCasada",apellidoCasada);
		localContentValues.put("_tipoNegocioId",tipoNegocioId);
		localContentValues.put("_zonaId",zonaId);
		localContentValues.put("_nombreFactura",nombreFactura);
		localContentValues.put("_nit",nit);
		localContentValues.put("_diaId",diaId);
		localContentValues.put("_referencia",referencia);
		localContentValues.put("_promedioVenta",promedioVenta);
		localContentValues.put("_ci",ci);
		localContentValues.put("_contacto",contacto);
		localContentValues.put("_montoPendienteCredito",montoPendienteCredito);
		localContentValues.put("_provinciaId",provinciaId);
		localContentValues.put("_ruta",ruta);
		localContentValues.put("_tipoVisita",tipoVisita);
		localContentValues.put("_ZonaVentaId",zonaVentaId);
		localContentValues.put("_zona", zona);
		localContentValues.put("_mercado", mercado);
		localContentValues.put("_canalRutaId", canalRutaId);
		localContentValues.put("_observacion", observacion);
		localContentValues.put("_verificadoFoto", verificadoFoto);
		return this.db.insert(CLIENTEPREVENTA_TABLE_NAME, null, localContentValues);
	}
	
	public Cursor obtenerClientesPreventa()
	{
	  Cursor localCursor = this.db.query(true, CLIENTEPREVENTA_TABLE_NAME, CLIENTEPREVENTA_ALL_KEYS, null, null, null, null, null, null);
	  if (localCursor != null) 
	  {
	    localCursor.moveToFirst();
	  }
	  return localCursor;
	}
	  
	public Cursor obtenerClientesPreventaPor(int clienteId)
	{
	  String str = "_clienteId=" + clienteId;
	  Cursor localCursor = this.db.query(true, CLIENTEPREVENTA_TABLE_NAME, CLIENTEPREVENTA_ALL_KEYS, str, null, null, null, null, null);
	  if (localCursor != null) 
	  {
	    localCursor.moveToFirst();
	  }
	  return localCursor;
	}
	
	public Cursor obtenerClientesPreventaNoAtendidos()
	{
		String query = "select count(_clienteId) from tbl_ClientePreventa where _clienteId not in " 
						+ "(select _clienteId from tbl_Preventa) and _clienteId not in "
						+ "(select _clienteId from tbl_ClienteNoAtendido)";
		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}
	
	public int modificarClientePreventaEstadoAtencion(int clienteId, boolean estadoAtencion)
    {
      String str = "_clienteId=" + clienteId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_estadoAtendido", estadoAtencion);
      return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
    }
	
	public int modificarClientePreventaSincronizacionDesdeVendedor(int id,int clienteId,boolean clientePunteoSincronizado)
    {
      String str = "_clienteId=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_clienteId", clienteId);
      localContentValues.put("_clientePunteoSincronizado", clientePunteoSincronizado);
      localContentValues.put("_nombreSincronizacion", true);
      return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
    }
	
	public int modificarClientePreventaNombre(int clienteId,String nombres,String paterno,String materno,String apellidoCasada,
												String direccion,String referencia)
    {
      String str = "_clienteId=" + clienteId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_nombres", nombres);
      localContentValues.put("_paterno", paterno);
      localContentValues.put("_materno", materno);
      localContentValues.put("_apellidoCasada", apellidoCasada);
      localContentValues.put("_nombreCompleto", String.valueOf(nombres + " " + paterno + " " + materno + " " + apellidoCasada));
      localContentValues.put("_direccion", direccion);
      localContentValues.put("_referencia", referencia);
      return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
    }
	
	public int modificarClientePreventaMontoCredito(int clienteId, float montoFinal)
	{
		String str = "_clienteId=" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_montoPendienteCredito", montoFinal);
		return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
	}
	
	public int modificarClientePreventaDatos(int clienteId,String nombres,String paterno,String materno,String apellidoCasada,
			String direccion,String referencia,double latitud,double longitud,int tipoNegocioId,String telefono,String celular)
	{
		String str = "_clienteId=" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_nombres", nombres);
		localContentValues.put("_paterno", paterno);
		localContentValues.put("_materno", materno);
		localContentValues.put("_apellidoCasada", apellidoCasada);
		localContentValues.put("_nombreCompleto", String.valueOf(nombres + " " + paterno + " " + materno + " " + apellidoCasada));
		localContentValues.put("_direccion", direccion);
		localContentValues.put("_referencia", referencia);
		localContentValues.put("_latitud", latitud);
		localContentValues.put("_longitud", longitud);
		localContentValues.put("_clienteTipoNegocioId", tipoNegocioId);
		localContentValues.put("_telefono", telefono);
		localContentValues.put("_celular", celular);
		
		return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
	}
	
	public int modificarClientePreventaNombreSincronizacion(int clienteId,boolean sincronizacion)
    {
      String str = "_clienteId=" + clienteId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_nombreSincronizacion", sincronizacion);
      return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
    }
	
	public Cursor obtenerClientesPreventaNoSincronizados()
	{
		String str = "_nombreSincronizacion = 0";
		Cursor localCursor = this.db.query(true, CLIENTEPREVENTA_TABLE_NAME, CLIENTEPREVENTA_ALL_KEYS,str, null, null, null, null, null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}
	
	public int modificarClienteDatosCenso(int clienteId,String codigo,String referencia,int tipoNegocioId,String contacto,
			double latitud,double longitud)
	{
		String str = "_clienteId=" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_codigo", codigo);
		localContentValues.put("_referencia", referencia);
		localContentValues.put("_tipoNegocioId", tipoNegocioId);
		localContentValues.put("_contacto", contacto);
		localContentValues.put("_latitud", latitud);
		localContentValues.put("_longitud", longitud);
		return this.db.update(CLIENTEPREVENTA_TABLE_NAME, localContentValues, str, null);
	}
	
	public Cursor obtenerNroClientesPreventa()
	{
		String query = "select count(distinct _clienteId) from tbl_ClientePreventa";
		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}
	
	//CLIENTEVENTA//
	public static final String KEY_CLIENTEVENTA_ROW_ID = "_Id";
	public static final String KEY_CLIENTEVENTA_CLIENTEID = "_clienteId";
	public static final String KEY_CLIENTEVENTA_CODIGO = "_codigo";
	public static final String KEY_CLIENTEVENTA_CLIENTETIPONEGOCIOID = "_clienteTipoNegocioId";
	public static final String KEY_CLIENTEVENTA_NOMBRECOMPLETO = "_nombreCompleto";
	public static final String KEY_CLIENTEVENTA_LATITUD = "_latitud";
	public static final String KEY_CLIENTEVENTA_LONGITUD = "_longitud";
	public static final String KEY_CLIENTEVENTA_DIRECCION = "_direccion";
	public static final String KEY_CLIENTEVENTA_TELEFONO = "_telefono";
	public static final String KEY_CLIENTEVENTA_CELULAR = "_celular";
	public static final String KEY_CLIENTEVENTA_PRECIOLISTAID = "_precioListaId";
	public static final String KEY_CLIENTEVENTA_DESCUENTO = "_descuento";
	public static final String KEY_CLIENTEVENTA_TIPOPAGOID = "_tipoPagoId";
	public static final String KEY_CLIENTEVENTA_DIASPAGO = "_diasPago";
	public static final String KEY_CLIENTEVENTA_TOPECREDITO = "_topeCredito";  
	public static final String KEY_CLIENTEVENTA_RUTAID = "_rutaId";
	public static final String KEY_CLIENTEVENTA_RUTADESCRIPCION = "_rutaDescripcion";  
	public static final String KEY_CLIENTEVENTA_RAZONSOCIAL = "_razonSocial";
	public static final String KEY_CLIENTEVENTA_AUTOVENTA = "_autoventa";
	public static final String KEY_CLIENTEVENTA_ESTADOATENDIDO = "_estadoAtendido"; 
	public static final String KEY_CLIENTEVENTA_CLIENTEPUNTEOSINCRONIZADO = "_clientePunteoSincronizado";
	public static final String KEY_CLIENTEVENTA_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	public static final String KEY_CLIENTEVENTA_NOMBREFACTURA = "_nombreFactura";
	public static final String KEY_CLIENTEVENTA_NIT = "_nit";
	public static final String KEY_CLIENTEVENTA_DIAID = "_diaId";
	public static final String KEY_CLIENTEVENTA_MONTOPENDIENTECREDITO = "_montoPendienteCredito";
	public static final String KEY_CLIENTEVENTA_PROVINCIAID = "_provinciaId";
	public static final String KEY_CLIENTEVENTA_CANALRUTAID = "_canalRutaId";
	public static final String KEY_CLIENTEVENTA_OBSERVACION = "_observacion";
	public static final String KEY_CLIENTEVENTA_PEDIDOEXTERNOID = "_pedidoExternoId";
	      
	public static final int COL_CLIENTEVENTA_ROW_ID = 0;
	public static final int COL_CLIENTEVENTA_CLIENTEID = 1;
	public static final int COL_CLIENTEVENTA_CODIGO = 2;
	public static final int COL_CLIENTEVENTA_CLIENTETIPONEGOCIOID = 3;
	public static final int COL_CLIENTEVENTA_NOMBRECOMPLETO = 4;
	public static final int COL_CLIENTEVENTA_LATITUD = 5;
	public static final int COL_CLIENTEVENTA_LONGITUD = 6;
	public static final int COL_CLIENTEVENTA_DIRECCION = 7;
	public static final int COL_CLIENTEVENTA_TELEFONO = 8;
	public static final int COL_CLIENTEVENTA_CELULAR = 9;
	public static final int COL_CLIENTEVENTA_PRECIOLISTAID = 10;
	public static final int COL_CLIENTEVENTA_DESCUENTO = 11;
	public static final int COL_CLIENTEVENTA_TIPOPAGOID = 12;
	public static final int COL_CLIENTEVENTA_DIASPAGO = 13;
	public static final int COL_CLIENTEVENTA_TOPECREDITO = 14;
	public static final int COL_CLIENTEVENTA_RUTAID = 15;
	public static final int COL_CLIENTEVENTA_RUTADESCRIPCION = 16;
	public static final int COL_CLIENTEVENTA_RAZONSOCIAL = 17;
	public static final int COL_CLIENTEVENTA_AUTOVENTA = 18;
	public static final int COL_CLIENTEVENTA_ESTADOATENDIDO = 19;
	public static final int COL_CLIENTEVENTA_CLIENTEPUNTEOSINCRONIZADO = 20;
	public static final int COL_CLIENTEVENTA_ESTADOSINCRONIZACION = 21;
	public static final int COL_CLIENTEVENTA_NOMBREFACTURA = 22;
	public static final int COL_CLIENTEVENTA_NIT = 23;
	public static final int COL_CLIENTEVENTA_DIAID = 24;
	public static final int COL_CLIENTEVENTA_MONTOPENDIENTECREDITO = 25;
	public static final int COL_CLIENTEVENTA_PROVINCIAID = 26;
	public static final int COL_CLIENTEVENTA_CANALRUTAID = 27;
	public static final int COL_CLIENTEVENTA_OBSERVACION = 28;
	public static final int COL_CLIENTEVENTA_PEDIDOEXTERNOID = 29;
	  
	public static final String[] CLIENTEVENTA_ALL_KEYS = new String[] { 
		  KEY_CLIENTEVENTA_ROW_ID,KEY_CLIENTEVENTA_CLIENTEID,KEY_CLIENTEVENTA_CODIGO,
		  KEY_CLIENTEVENTA_CLIENTETIPONEGOCIOID,KEY_CLIENTEVENTA_NOMBRECOMPLETO,
		  KEY_CLIENTEVENTA_LATITUD,KEY_CLIENTEVENTA_LONGITUD,KEY_CLIENTEVENTA_DIRECCION,KEY_CLIENTEVENTA_TELEFONO,
		  KEY_CLIENTEVENTA_CELULAR,KEY_CLIENTEVENTA_PRECIOLISTAID,KEY_CLIENTEVENTA_DESCUENTO,KEY_CLIENTEVENTA_TIPOPAGOID,
		  KEY_CLIENTEVENTA_DIASPAGO,KEY_CLIENTEVENTA_TOPECREDITO,KEY_CLIENTEVENTA_RUTAID,KEY_CLIENTEVENTA_RUTADESCRIPCION,
		  KEY_CLIENTEVENTA_RAZONSOCIAL,KEY_CLIENTEVENTA_AUTOVENTA,KEY_CLIENTEVENTA_ESTADOATENDIDO,
		  KEY_CLIENTEVENTA_CLIENTEPUNTEOSINCRONIZADO,KEY_CLIENTEVENTA_ESTADOSINCRONIZACION,KEY_CLIENTEVENTA_NOMBREFACTURA,
		  KEY_CLIENTEVENTA_NIT,KEY_CLIENTEVENTA_DIAID,KEY_CLIENTEVENTA_MONTOPENDIENTECREDITO,
		  KEY_CLIENTEVENTA_PROVINCIAID, KEY_CLIENTEVENTA_CANALRUTAID, KEY_CLIENTEVENTA_OBSERVACION,
		  KEY_CLIENTEVENTA_PEDIDOEXTERNOID};
	    
	public static final String CLIENTEVENTA_TABLE_NAME = "tbl_ClienteVenta";
	  
	public static final String CLIENTEVENTA_TABLE_CREATE = "CREATE TABLE " + CLIENTEVENTA_TABLE_NAME + "("
	  		+ KEY_CLIENTEVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	  		+ KEY_CLIENTEVENTA_CLIENTEID + " integer NOT NULL, "
	  		+ KEY_CLIENTEVENTA_CODIGO + " text NOT NULL, "
	  		+ KEY_CLIENTEVENTA_CLIENTETIPONEGOCIOID + " integer NOT NULL, "
	  		+ KEY_CLIENTEVENTA_NOMBRECOMPLETO + " text NOT NULL, "
	  		+ KEY_CLIENTEVENTA_LATITUD + " double NOT NULL, "
	  		+ KEY_CLIENTEVENTA_LONGITUD + " double NOT NULL, "
	  		+ KEY_CLIENTEVENTA_DIRECCION + " text NOT NULL, "
	  		+ KEY_CLIENTEVENTA_TELEFONO + " text NOT NULL, "
	  		+ KEY_CLIENTEVENTA_CELULAR +" text NOT NULL, "
	  		+ KEY_CLIENTEVENTA_PRECIOLISTAID + " integer NOT NULL, "
	  		+ KEY_CLIENTEVENTA_DESCUENTO + " float NOT NULL, "
	  		+ KEY_CLIENTEVENTA_TIPOPAGOID + " integer NOT NULL, "
	  		+ KEY_CLIENTEVENTA_DIASPAGO + " integer NOT NULL, "
	  		+ KEY_CLIENTEVENTA_TOPECREDITO + " float NOT NULL, "
	  		+ KEY_CLIENTEVENTA_RUTAID + " integer NOT NULL, "
	  		+ KEY_CLIENTEVENTA_RUTADESCRIPCION + " text, "
	  		+ KEY_CLIENTEVENTA_RAZONSOCIAL + " text, "
	  		+ KEY_CLIENTEVENTA_AUTOVENTA + " boolean NOT NULL, "
	  		+ KEY_CLIENTEVENTA_ESTADOATENDIDO + " boolean NOT NULL, "
	  		+ KEY_CLIENTEVENTA_CLIENTEPUNTEOSINCRONIZADO + " boolean NOT NULL, "
	  		+ KEY_CLIENTEVENTA_ESTADOSINCRONIZACION + " boolean NOT NULL, "
	  		+ KEY_CLIENTEVENTA_NOMBREFACTURA + " text NOT NULL, "
	  		+ KEY_CLIENTEVENTA_NIT + " text NOT NULL, "
			+ KEY_CLIENTEVENTA_DIAID + " integer NOT NULL, "
			+ KEY_CLIENTEVENTA_MONTOPENDIENTECREDITO + " float NOT NULL, "
			+ KEY_CLIENTEVENTA_PROVINCIAID + " integer NOT NULL, "
			+ KEY_CLIENTEVENTA_CANALRUTAID + " integer NOT NULL, "
			+ KEY_CLIENTEVENTA_OBSERVACION + " text NOT NULL, "
			+ KEY_CLIENTEVENTA_PEDIDOEXTERNOID + " text NOT NULL);";
	  
	public boolean borrarClienteVentaPor(long id)
	  {
	    String str = "_Id=" + id;
	    return this.db.delete(CLIENTEVENTA_TABLE_NAME, str, null) > 0;
	  }
	  
	public void borrarClientesVenta()
	{
		Cursor localCursor = obtenerClientesVenta();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    if (localCursor.moveToFirst()) 
	    {
	      do
	      {
	        borrarClienteVentaPor(localCursor.getLong((int)l));
	      } 
	      while (localCursor.moveToNext());
	    }
	    localCursor.close();
	}
	  
	public long insertarClienteVenta(ArrayList<ClienteVentaWSResult> clientesVenta)
	{
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClienteVenta(_clienteId, _codigo, _clienteTipoNegocioId, _nombreCompleto, _latitud, " +
						"_longitud, _direccion, _telefono, _celular, _precioListaId, _descuento, _tipoPagoId, _diasPago, _topeCredito, " +
						"_rutaId, _rutaDescripcion, _razonSocial, _autoventa, _estadoAtendido, _clientePunteoSincronizado, " +
						"_estadoSincronizacion, _nombreFactura, _nit, _diaId, _montoPendienteCredito, _provinciaId, _canalRutaId, " +
						"_observacion, _pedidoExternoId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ClienteVentaWSResult item : clientesVenta) {

				stmt.bindLong(1, item.getClienteId());
				stmt.bindString(2, item.getCodigo());
				stmt.bindLong(3, item.getTipoNegocioId());
				stmt.bindString(4, item.getNombreCompleto());
				stmt.bindDouble(5, item.getLatitud());
				stmt.bindDouble(6, item.getLongitud());
				stmt.bindString(7, item.getDireccion());
				stmt.bindString(8, item.getTelefono());
				stmt.bindString(9, item.getCelular());
				stmt.bindLong(10, item.getPrecioListaId());
				stmt.bindDouble(11, item.getDescuento());
				stmt.bindLong(12, item.getTipoPagoId());
				stmt.bindLong(13, item.getDiasPago());
				stmt.bindDouble(14, item.getTopeCredito());
				stmt.bindLong(15, item.getRutaId());
				stmt.bindString(16,"");
				stmt.bindString(17, item.getRazonSocial());
				stmt.bindLong(18, item.isAutoventa()?1:0);
				stmt.bindLong(19,0);
				stmt.bindLong(20,1);
				stmt.bindLong(21,1);
				stmt.bindString(22, item.getNombreFactura());
				stmt.bindString(23, item.getNit());
				stmt.bindLong(24, item.getDiaId());
				stmt.bindDouble(25, item.getMontoPendienteCredito());
				stmt.bindLong(26,0);
				stmt.bindLong(27, item.getCanalRutaId());
				stmt.bindString(28, item.getObservacion());
				stmt.bindString(29, item.getPedidoExternoId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
	  }

	public long insertarClienteVenta(int clienteId, String codigo, int clienteTipoNegocioId, String nombreCompleto,
									 double latitud, double longitud, String direccion, String telefono, String celular,
									 int precioListaId, float descuento, int tipoPagoId, int diasPago, float topeCredito, int rutaId,
									 String rutaDescripcion, String razonSocial, boolean autoventa,boolean estadoAtendido,boolean clientePunteoSincronizado,
									 boolean estadoSincronizacion,String nombreFactura,String nit,int diaId,float montoPendienteCredito,int provinciaId,
									 int canalRutaId, String observacion, String pedidoExternoId)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_clienteId", Integer.valueOf(clienteId));
		localContentValues.put("_codigo", codigo);
		localContentValues.put("_clienteTipoNegocioId", Integer.valueOf(clienteTipoNegocioId));
		localContentValues.put("_nombreCompleto", nombreCompleto);
		localContentValues.put("_latitud", Double.valueOf(latitud));
		localContentValues.put("_longitud", Double.valueOf(longitud));
		localContentValues.put("_direccion", direccion);
		localContentValues.put("_telefono", telefono);
		localContentValues.put("_celular", celular);
		localContentValues.put("_precioListaId", Integer.valueOf(precioListaId));
		localContentValues.put("_descuento", Float.valueOf(descuento));
		localContentValues.put("_tipoPagoId", Integer.valueOf(tipoPagoId));
		localContentValues.put("_diasPago", Integer.valueOf(diasPago));
		localContentValues.put("_topeCredito", Float.valueOf(topeCredito));
		localContentValues.put("_rutaId", Integer.valueOf(rutaId));
		localContentValues.put("_rutaDescripcion", rutaDescripcion);
		localContentValues.put("_razonSocial", razonSocial);
		localContentValues.put("_autoventa", Boolean.valueOf(autoventa));
		localContentValues.put("_estadoAtendido", Boolean.valueOf(estadoAtendido));
		localContentValues.put("_clientePunteoSincronizado", Boolean.valueOf(clientePunteoSincronizado));
		localContentValues.put("_estadoSincronizacion", Boolean.valueOf(estadoSincronizacion));
		localContentValues.put("_nombreFactura", nombreFactura);
		localContentValues.put("_nit", nit);
		localContentValues.put("_diaId", diaId);
		localContentValues.put("_montoPendienteCredito", montoPendienteCredito);
		localContentValues.put("_provinciaId", provinciaId);
		localContentValues.put("_canalRutaId", canalRutaId);
		localContentValues.put("_observacion", observacion);
		localContentValues.put("_pedidoExternoId", pedidoExternoId);
		return this.db.insert(CLIENTEVENTA_TABLE_NAME, null, localContentValues);
	}
	  
	public Cursor obtenerClientesVenta()
	{
	    Cursor localCursor = this.db.query(true, CLIENTEVENTA_TABLE_NAME, CLIENTEVENTA_ALL_KEYS, null, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	}
	  
	public Cursor obtenerClientesVentaPor(int clienteId)
  	{
		String str = "_clienteId=" + clienteId;
	    Cursor localCursor = this.db.query(true, CLIENTEVENTA_TABLE_NAME, CLIENTEVENTA_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
  	}
	  
	public int modificarClienteVentaEstadoAtendido(int clienteId,boolean estadoAtendido)
	{
		String str = "_clienteId=" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_estadoAtendido", estadoAtendido);
		return this.db.update(CLIENTEVENTA_TABLE_NAME, localContentValues, str, null);
	}
	
	public int modificarClienteVentaMontoCredito(int clienteId, float montoFinal)
	{
		String str = "_clienteId=" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_montoPendienteCredito", montoFinal);
		return this.db.update(CLIENTEVENTA_TABLE_NAME, localContentValues, str, null);
	}
	
	public int modificarClienteVentaDatos(int clienteId,String nombres,String paterno,String materno,
											String casada,String direccion,double latitud,double longitud,
											int tipoNegocioId,String telefono,String celular)
	{
		String str = "_clienteId=" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_nombrecompleto", nombres + " " + paterno + " " + materno + " " + casada);
		localContentValues.put("_direccion", direccion);
		localContentValues.put("_latitud", latitud);
		localContentValues.put("_longitud", longitud);
		localContentValues.put("_clienteTipoNegocioId", tipoNegocioId);
		localContentValues.put("_telefono", telefono);
		localContentValues.put("_celular", celular);
		
		return this.db.update(CLIENTEVENTA_TABLE_NAME, localContentValues, str, null);
	}
	
	public int modificarClienteVentaSincronizacionDesdeDistribuidor(int id,int clienteId,boolean clientePunteoSincronizado)
    {
      String str = "_clienteId=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_clienteId", clienteId);
      localContentValues.put("_clientePunteoSincronizado", clientePunteoSincronizado);
      return this.db.update(CLIENTEVENTA_TABLE_NAME, localContentValues, str, null);
    }
	  
	  //PROVEEDOR//
	  public static final String KEY_PROVEEDOR_ROW_ID = "_Id";
	  public static final String KEY_PROVEEDOR_PROVEEDORID = "_proveedorId";
	  public static final String KEY_PROVEEDOR_NOMBRE = "_nombre";
	  
	  public static final int COL_PROVEEDOR_ROW_ID = 0;
	  public static final int COL_PROVEEDOR_PROVEEDORID = 1;
	  public static final int COL_PROVEEDOR_NOMBRE = 2;
	  
	  public static final String[] PROVEEDOR_ALL_KEYS = new String[] {
		  KEY_PROVEEDOR_ROW_ID,KEY_PROVEEDOR_PROVEEDORID,KEY_PROVEEDOR_NOMBRE};
	  
	  public static final String PROVEEDOR_TABLE_NAME = "tbl_Proveedor";
	  
	  public static final String PROVEEDOR_TABLE_CREATE = "CREATE TABLE " + PROVEEDOR_TABLE_NAME + "("
	  		+ KEY_PROVEEDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	  		+ KEY_PROVEEDOR_PROVEEDORID + " integer NOT NULL, "
	  		+ KEY_PROVEEDOR_NOMBRE + " text NOT NULL);";
	  
	  public boolean borrarProveedorPor(long id)
	  {
	    String str = "_Id=" + id;
	    return this.db.delete(PROVEEDOR_TABLE_NAME, str, null) > 0;
	  }
	  
	  public void borrarProveedores()
	  {
	    Cursor localCursor = obtenerProveedores();
	    long l = localCursor.getColumnIndexOrThrow("_Id");
	    if (localCursor.moveToFirst()) 
	    {
	      do
	      {
	        borrarProveedorPor(localCursor.getLong((int)l));
	      } 
	      while (localCursor.moveToNext());
	    }
	    localCursor.close();
	  }
	  
	  public long insertarProveedor(ArrayList<ProveedorWSResult> proveedores)
	  {
		  SQLiteStatement stmt = db.compileStatement(
				  "INSERT INTO tbl_proveedor(_proveedorId, _nombre) VALUES (?,?)"
		  );
		  try {
			  db.beginTransaction();
			  for (ProveedorWSResult item : proveedores) {

				  stmt.bindLong(1, item.getProveedorId());
				  stmt.bindString(2, item.getNombre());

				  stmt.executeInsert();
				  stmt.clearBindings();
			  }
			  db.setTransactionSuccessful();
			  db.endTransaction();
			  return 1;
		  }catch(Exception localException){
			  if(db.inTransaction()){
				  db.endTransaction();
			  }
			  return 0;
		  }
	  }
	  
	  public Cursor obtenerProveedores()
	  {
		  String orderBy = "_nombre";
		  Cursor localCursor = this.db.query(true,PROVEEDOR_TABLE_NAME, PROVEEDOR_ALL_KEYS, null, null, null, null, orderBy, null);
		  if (localCursor != null) 
		  {
			  localCursor.moveToFirst();
		  }
		  return localCursor;
	  }
	  
	  public Cursor obtenerProveedorPor(int proveedorId)
	  {
		  String str = "_proveedorId =" + proveedorId;
		  Cursor localCursor = this.db.query(true,PROVEEDOR_TABLE_NAME, PROVEEDOR_ALL_KEYS,str, null, null, null, null, null);
		  if (localCursor != null) 
		  {
			  localCursor.moveToFirst();
		  }
		  return localCursor;
	  }
	  
	  public Cursor obtenerProveedoresPor(String proveedoresId)
	  {
		  String str = "_proveedorId in (" + proveedoresId + ")";
		  Cursor localCursor = this.db.query(true,PROVEEDOR_TABLE_NAME, PROVEEDOR_ALL_KEYS,str, null, null, null, null, null);
		  if (localCursor != null) 
		  {
			  localCursor.moveToFirst();
		  }
		  return localCursor;
	  }
	  
	  //PRODUCTO//
	  public static final String KEY_PRODUCTO_ROW_ID = "_Id";
	  public static final String KEY_PRODUCTO_PRODUCTOID = "_productoId";
	  public static final String KEY_PRODUCTO_CODIGO = "_codigo";
	  public static final String KEY_PRODUCTO_DESCRIPCION25 = "_descripcion25";
	  public static final String KEY_PRODUCTO_PROVEEDORID = "_proveedorId";
	  public static final String KEY_PRODUCTO_CONVERSION = "_conversion";
	  public static final String KEY_PRODUCTO_CATEGORIAID = "_categoriaId";
	  public static final String KEY_PRODUCTO_DESCRIPCIONUNIDAD25 = "_descripcionUnidad25";
	  public static final String KEY_PRODUCTO_PRODUCTODEPROMOCION = "_productoDePromocion";
	  public static final String KEY_PRODUCTO_PRODUCTOREFERENCIAID = "_productoReferenciaId";
	  
	  public static final int COL_PRODUCTO_ROW_ID = 0;
	  public static final int COL_PRODUCTO_PRODUCTOID = 1;
	  public static final int COL_PRODUCTO_CODIGO = 2;
	  public static final int COL_PRODUCTO_DESCRIPCION25 = 3;
	  public static final int COL_PRODUCTO_PROVEEDORID = 4;
	  public static final int COL_PRODUCTO_CONVERSION = 5;
	  public static final int COL_PRODUCTO_CATEGORIAID = 6;
	  public static final int COL_PRODUCTO_DESCRIPCIONUNIDAD25 = 7;
	  public static final int COL_PRODUCTO_PRODUCTODEPROMOCION = 8;
	  public static final int COL_PRODUCTO_PRODUCTOREFERENCIAID = 9;
	  
	  public static final String[] PRODUCTO_ALL_KEYS = new String[] {
		  KEY_PRODUCTO_ROW_ID, KEY_PRODUCTO_PRODUCTOID, KEY_PRODUCTO_CODIGO, KEY_PRODUCTO_DESCRIPCION25,
		  KEY_PRODUCTO_PROVEEDORID,KEY_PRODUCTO_CONVERSION,KEY_PRODUCTO_CATEGORIAID,KEY_PRODUCTO_DESCRIPCIONUNIDAD25,
		  KEY_PRODUCTO_PRODUCTODEPROMOCION,KEY_PRODUCTO_PRODUCTOREFERENCIAID};
	  
	  public static final String PRODUCTO_TABLE_NAME = "tbl_Producto";
	  
	  public static final String PRODUCTO_TABLE_CREATE = "CREATE TABLE " + PRODUCTO_TABLE_NAME + "("
	  		+ KEY_PRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	  		+ KEY_PRODUCTO_PRODUCTOID + " integer NOT NULL, "
	  		+ KEY_PRODUCTO_CODIGO + " text NOT NULL, "
	  		+ KEY_PRODUCTO_DESCRIPCION25 + " text NOT NULL, "
	  		+ KEY_PRODUCTO_PROVEEDORID + " integer NOT NULL, "
	  		+ KEY_PRODUCTO_CONVERSION + " integer NOT NULL, "
	  		+ KEY_PRODUCTO_CATEGORIAID + " integer NOT NULL,"
	  		+ KEY_PRODUCTO_DESCRIPCIONUNIDAD25 + " text NOT NULL,"
	  		+ KEY_PRODUCTO_PRODUCTODEPROMOCION + " text NOT NULL, "
	  		+ KEY_PRODUCTO_PRODUCTOREFERENCIAID + " integer NOT NULL);";
	  
	  public boolean borrarProductoPor(long id)
	  {
	    String str = "_Id=" + id;
	    return this.db.delete(PRODUCTO_TABLE_NAME, str, null) > 0;
	  }
	  
	  public Cursor getConversionProducto(int productoId)
	  {
	    String str = "_productoId=" + productoId;
	    Cursor localCursor = this.db.query(true, PRODUCTO_TABLE_NAME, PRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public void borrarProductos()
	  {
	    Cursor localCursor = obtenerProductos();
	    long l = localCursor.getColumnIndexOrThrow("_Id");
	    if (localCursor.moveToFirst()) 
	    {
	      do
	      {
	    	  borrarProductoPor(localCursor.getLong((int)l));
	      } 
	      while (localCursor.moveToNext());
	    }
	    localCursor.close();
	  }
	  
	  public Cursor obtenerProductoPor(int productoId)
	  {
	    String str = "_productoId=" + productoId;
	    Cursor localCursor = this.db.query(true, PRODUCTO_TABLE_NAME, PRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public Cursor obtenerProductos()
	  {
	    Cursor localCursor = this.db.query(true, PRODUCTO_TABLE_NAME, PRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public Cursor obtenerProductoPorProveedor(int proveedorId)
	  {
	    String str = "_proveedorId=" + proveedorId;
	    Cursor localCursor = this.db.query(true, PRODUCTO_TABLE_NAME, PRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public Cursor obtenerProductosPorProveedorNoEnPreventaProductoTemp(int proveedorId,int categoriaId,int empleadoId,
			  																int clienteId)
	  {
		  String query = "SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
		  			"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " +
		  			"FROM tbl_producto AS P " +
		  			"JOIN tbl_almacenProducto AS AP " +
		  			"ON (P._productoId = AP._productoId) " +
		  			"WHERE (AP._saldoUnitario > 0 OR AP._saldoPaquete > 0) " +
		  			"AND P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " +
		  			"AND P._productoId NOT IN (SELECT PPT._productoId " +
		  			"FROM tbl_PreventaProductoTemp as PPT " +
		  			"WHERE _empleadoId = ? and _clienteId = ?) " +
		  			"UNION " + 
		  			"SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
		  			"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " + 
		  			"FROM tbl_producto AS P " +
		  			"WHERE _productoReferenciaId > 0 and P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " + 
		  			"AND P._productoId NOT IN (SELECT PPT._productoId " + 
		  			"FROM tbl_PreventaProductoTemp as PPT WHERE _empleadoId = ? and _clienteId = ?) " +
		  			"ORDER BY P._descripcion25";
		  Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(proveedorId),String.valueOf(categoriaId),
				  										String.valueOf(empleadoId),String.valueOf(clienteId),
				  										String.valueOf(proveedorId),String.valueOf(categoriaId),
				  										String.valueOf(empleadoId),String.valueOf(clienteId)});
		  if (localCursor != null) 
		    {
		      localCursor.moveToFirst();
		    }
		  return localCursor;
	  }
	  
	  public Cursor obtenerProductosPorProveedorNoEnVentaDirectaProductoTemp(int proveedorId,int categoriaId,int empleadoId,
				int clienteId)
	  {
		  	String query = "SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
  					"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " +
  					"FROM tbl_producto AS P " +
  					"JOIN tbl_almacenProducto AS AP " +
  					"ON (P._productoId = AP._productoId) " +
  					"WHERE (AP._saldoUnitario > 0 OR AP._saldoPaquete > 0) " +
  					"AND P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " +
  					"AND P._productoId NOT IN (SELECT VDPT._productoId " +
  					"FROM tbl_VentaDirectaProductoTemp as VDPT " +
  					"WHERE _empleadoId = ? and _clienteId = ?) " +
  					"UNION " + 
		  			"SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
		  			"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " + 
		  			"FROM tbl_producto AS P " +
		  			"WHERE _productoReferenciaId > 0 and P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " + 
		  			"AND P._productoId NOT IN (SELECT VDPT._productoId " + 
		  			"FROM tbl_VentaDirectaProductoTemp as VDPT " +
		  			"WHERE _empleadoId = ? and _clienteId = ?) " +
  					"ORDER BY P._descripcion25";
		  	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(proveedorId),String.valueOf(categoriaId),
		  									String.valueOf(empleadoId),String.valueOf(clienteId),
		  									String.valueOf(proveedorId),String.valueOf(categoriaId),
		  									String.valueOf(empleadoId),String.valueOf(clienteId)});
		  	if (localCursor != null) 
		  	{
		  		localCursor.moveToFirst();
		  	}
		  	return localCursor;
	  }
	  
	  public Cursor obtenerProductosPorProveedorNoEnAlmacenDevolucionProductoTemp(int proveedorId,int categoriaId,int empleadoId,
				int clienteId)
	  {
		  String query = "SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
				  "P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " +
				  "FROM tbl_producto AS P " +
				  "JOIN tbl_devolucionDistribuidorProducto AS DDP " +
				  "ON (P._productoId = DDP._productoId) " +
				  "WHERE (DDP._saldoUnitario > 0 OR DDP._saldoPaquete > 0) " +
				  "AND P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " +
				  "AND P._productoId NOT IN (SELECT DDPT._productoId " +
				  "FROM tbl_devolucionDistribuidorProductoTemp as DDPT " +
				  "WHERE _empleadoId = ? and _clienteId = ?) " +
				  "UNION " + 
				  "SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
				  "P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " + 
				  "FROM tbl_producto AS P " +
				  "WHERE _productoReferenciaId > 0 and P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " + 
				  "AND P._productoId NOT IN (SELECT DDPT._productoId " + 
				  "FROM tbl_devolucionDistribuidorProductoTemp as DDPT " +
				  "WHERE _empleadoId = ? and _clienteId = ?) " +
				  "ORDER BY P._descripcion25";
		  Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(proveedorId),String.valueOf(categoriaId),
				  							String.valueOf(empleadoId),String.valueOf(clienteId),
				  							String.valueOf(proveedorId),String.valueOf(categoriaId),
				  							String.valueOf(empleadoId),String.valueOf(clienteId)});
		  if (localCursor != null) 
		  {
			  localCursor.moveToFirst();
		  }
		  return localCursor;
	  }
	  
	  public Cursor obtenerProductosPorProveedorNoEnVentaProductoTemp(int proveedorId,int categoriaId,int empleadoId,
																			int clienteId)
	  {
		  String query = "SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
  				"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId  " +
  				"FROM tbl_producto AS P " +
  				"JOIN tbl_devolucionDistribuidorProducto AS DDP " +
  				"ON (P._productoId = DDP._productoId) " +
  				"WHERE (DDP._saldoUnitario > 0 OR AP._saldoPaquete > 0) " +
  				"AND P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " +
  				"AND P._productoId NOT IN (SELECT PPT._productoId " +
  				"FROM tbl_PreventaProductoTemp as PPT " +
  				"WHERE _empleadoId = ? and _clienteId = ?) " +
  				"UNION " + 
  				"SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
  				"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " + 
  				"FROM tbl_producto AS P " +
  				"WHERE _productoReferenciaId > 0 and P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " + 
  				"AND P._productoId NOT IN (SELECT PPT._productoId " + 
  				"FROM tbl_preventaProductoTemp as PPT " +
  				"WHERE _empleadoId = ? and _clienteId = ?) " +
  				"ORDER BY P._descripcion25";
		  Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(proveedorId),String.valueOf(categoriaId),
				  							String.valueOf(empleadoId),String.valueOf(clienteId),
				  							String.valueOf(proveedorId),String.valueOf(categoriaId),
				  							String.valueOf(empleadoId),String.valueOf(clienteId)});
		  if (localCursor != null) 
		  {
			  localCursor.moveToFirst();
		  }
		  return localCursor;
	  }
	  
	  public long insertarProducto(ArrayList<ProductoWSResult> productos)
	  {
		  SQLiteStatement stmt = db.compileStatement(
				  "INSERT INTO tbl_Producto(_productoId, _codigo, _descripcion25, _proveedorId, _conversion, " +
						  "_categoriaId, _descripcionUnidad25, _productoDePromocion, _productoReferenciaId) VALUES (?,?,?,?,?,?,?,?,?)"
		  );
		  try {
			  db.beginTransaction();
			  for (ProductoWSResult item: productos) {

				  stmt.bindLong(1, item.getProductoId());
				  stmt.bindString(2, item.getCodigo());
				  stmt.bindString(3, item.getDescripcion25());
				  stmt.bindLong(4, item.getProveedorId());
				  stmt.bindLong(5, item.getConversion());
				  stmt.bindLong(6, item.getCategoriaId());
				  stmt.bindString(7, item.getDescripcionUnidad25());
				  stmt.bindLong(8, item.isProductoDePromocion()?1:0);
				  stmt.bindLong(9, item.getProductoReferenciaId());

				  stmt.executeInsert();
				  stmt.clearBindings();
			  }
			  db.setTransactionSuccessful();
			  db.endTransaction();
			  return 1;
		  }catch(Exception localException){
			  if(db.inTransaction()){
				  db.endTransaction();
			  }
			  return 0;
		  }
	  }
	  
	  public Cursor obtenerProductosPorProveedorNoEnPreventaProducto(int proveedorId,int categoriaId,int preventaId)
	  {
		  String query = "SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
	  			"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId  " +
  				"FROM tbl_producto AS P " +
  				"JOIN tbl_almacenProducto AS AP " +
  				"ON (P._productoId = AP._productoId) " +
  				"WHERE (AP._saldoUnitario > 0 OR AP._saldoPaquete > 0) " +
  				"AND P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " +
  				"AND P._productoId NOT IN (SELECT PP._productoId " +
  				"FROM tbl_PreventaProducto as PP " +
  				"WHERE _preventaId = ?) " +
  				"UNION " + 
  				"SELECT P._id, P._productoId, P._codigo, P._descripcion25, P._proveedorId, P._conversion, " +
  				"P._categoriaId, P._descripcionUnidad25, P._productoDePromocion, P._productoReferenciaId " + 
  				"FROM tbl_producto AS P " +
  				"WHERE _productoReferenciaId > 0 and P._proveedorId = ? AND P._categoriaId = ? and _productoDePromocion = 0 " + 
  				"AND P._productoId NOT IN (SELECT PP._productoId " + 
  				"FROM tbl_preventaProducto as PP " +
  				"WHERE _preventaId = ?) " +
  				"ORDER BY P._descripcion25";
		  Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(proveedorId),String.valueOf(categoriaId),
				  							String.valueOf(preventaId),String.valueOf(proveedorId),String.valueOf(categoriaId),
				  							String.valueOf(preventaId)});
		  if (localCursor != null) 
		  {
			  localCursor.moveToFirst();
		  }
		  return localCursor;
	  }
	  
	  //PRECIOLISTA//
	  public static final String KEY_PRECIOLISTA_ROW_ID = "_Id";
	  public static final String KEY_PRECIOLISTA_PRECIOLISTAID = "_precioListaId";
	  public static final String KEY_PRECIOLISTA_PRODUCTOID = "_productoId";
	  public static final String KEY_PRECIOLISTA_PRECIO = "_precio";
	  public static final String KEY_PRECIOLISTA_PRECIOPAQUETE = "_precioPaquete";
	  public static final String KEY_PRECIOLISTA_DESCUENTOUNIDAD = "_descuentoUnidad";
	  public static final String KEY_PRECIOLISTA_DESCUENTOPAQUETE = "_descuentoPaquete";
	  public static final String KEY_PRECIOLISTA_MARGENUNIDAD = "_margenUnidad";
	  public static final String KEY_PRECIOLISTA_MARGENPAQUETE = "_margenPaquete";
	  public static final String KEY_PRECIOLISTA_PRECIOID = "_precioId";
	  public static final String KEY_PRECIOLISTA_ACTIVO = "_activo";
	  
	  public static final int COL_PRECIOLISTA_ROW_ID = 0;
	  public static final int COL_PRECIOLISTA_PRECIOLISTAID = 1;
	  public static final int COL_PRECIOLISTA_PRODUCTOID = 2;
	  public static final int COL_PRECIOLISTA_PRECIO = 3;
	  public static final int COL_PRECIOLISTA_PRECIOPAQUETE = 4;
	  public static final int COL_PRECIOLISTA_DESCUENTOUNIDAD = 5;
	  public static final int COL_PRECIOLISTA_DESCUENTOPAQUETE = 6;
	  public static final int COL_PRECIOLISTA_MARGENUNIDAD = 7;
	  public static final int COL_PRECIOLISTA_MARGENPAQUETE = 8;
	  public static final int COL_PRECIOLISTA_PRECIOID = 9;
	  public static final int COL_PRECIOLISTA_ACTIVO = 10;
	  
	  public static final String[] PRECIOLISTA_ALL_KEYS = new String[] {
		  KEY_PRECIOLISTA_ROW_ID,KEY_PRECIOLISTA_PRECIOLISTAID,KEY_PRECIOLISTA_PRODUCTOID,
		  KEY_PRECIOLISTA_PRECIO, KEY_PRECIOLISTA_PRECIOPAQUETE,KEY_PRECIOLISTA_DESCUENTOUNIDAD,
		  KEY_PRECIOLISTA_DESCUENTOPAQUETE,KEY_PRECIOLISTA_MARGENUNIDAD,KEY_PRECIOLISTA_MARGENPAQUETE,
		  KEY_PRECIOLISTA_PRECIOID,KEY_PRECIOLISTA_ACTIVO};
	  
	  public static final String PRECIOLISTA_TABLE_NAME = "tbl_PrecioLista";
	  
	  public static final String PRECIOLISTA_TABLE_CREATE = "CREATE TABLE " + PRECIOLISTA_TABLE_NAME + "("
	  		+ KEY_PRECIOLISTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	  		+ KEY_PRECIOLISTA_PRECIOLISTAID + " integer NOT NULL, "
	  		+ KEY_PRECIOLISTA_PRODUCTOID + " integer NOT NULL, "
	  		+ KEY_PRECIOLISTA_PRECIO + " float NOT NULL, "
	  		+ KEY_PRECIOLISTA_PRECIOPAQUETE + " float NOT NULL, "
	  		+ KEY_PRECIOLISTA_DESCUENTOUNIDAD + " float NOT NULL, "
	  		+ KEY_PRECIOLISTA_DESCUENTOPAQUETE + " float NOT NULL, "
	  		+ KEY_PRECIOLISTA_MARGENUNIDAD + " float NOT NULL, "
	  		+ KEY_PRECIOLISTA_MARGENPAQUETE + " float NOT NULL, "
	  		+ KEY_PRECIOLISTA_PRECIOID + " integer NOT NULL, "
	  		+ KEY_PRECIOLISTA_ACTIVO + " boolean NOT NULL);";
	  
	  
	  public boolean borrarPrecioListaPor(long id)
	  {
	    String str = "_Id=" + id;
	    return this.db.delete(PRECIOLISTA_TABLE_NAME, str, null) > 0;
	  }
	  
	  public void borrarPreciosLista()
	  {
	    Cursor localCursor = obtenerPreciosLista();
	    long l = localCursor.getColumnIndexOrThrow("_Id");
	    if (localCursor.moveToFirst()) 
	    {
	      do
	      {
	        borrarPrecioListaPor(localCursor.getLong((int)l));
	      } 
	      while (localCursor.moveToNext());
	    }
	    localCursor.close();
	  }
	  
	  public Cursor obtenerPreciosLista()
	  {
	    Cursor localCursor = this.db.query(true,PRECIOLISTA_TABLE_NAME, PRECIOLISTA_ALL_KEYS, null, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public Cursor obtenerPreciosListaPor(int precioListaId, int productoId)
	  {
	    String str = "_precioListaId = " + precioListaId + " and _productoId = " + productoId + " and _activo=1";
	    Cursor localCursor = this.db.query(true,PRECIOLISTA_TABLE_NAME, PRECIOLISTA_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public Cursor obtenerPreciosListaPor(int precioListaId, int productoId, int precioId)
	  {
	    String str = "_precioListaId = " + precioListaId + " and _productoId = " + productoId + " and _precioId=" + precioId;
	    Cursor localCursor = this.db.query(true,PRECIOLISTA_TABLE_NAME, PRECIOLISTA_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	  }
	  
	  public long insertarPrecioLista(ArrayList<PrecioWSResult> precios)
	  {
		  SQLiteStatement stmt = db.compileStatement(
				  "INSERT INTO tbl_PrecioLista(_precioListaId, _productoId, _precio, _precioPaquete, _descuentoUnidad, " +
						  "_descuentoPaquete, _margenUnidad, _margenPaquete, _precioId, _activo) VALUES (?,?,?,?,?,?,?,?,?,?)"
		  );
		  try {
			  db.beginTransaction();
			  for (PrecioWSResult item : precios) {

				  stmt.bindLong(1, item.getPrecioListaId());
				  stmt.bindLong(2, item.getProductoId());
				  stmt.bindDouble(3, item.getPrecio());
				  stmt.bindDouble(4, item.getPrecioPaquete());
				  stmt.bindDouble(5, item.getDescuentoUnidad());
				  stmt.bindDouble(6, item.getDescuentoPaquete());
				  stmt.bindDouble(7, item.getMargenUnidad());
				  stmt.bindDouble(8, item.getMargenPaquete());
				  stmt.bindLong(9, item.getPrecioId());
				  stmt.bindLong(10, item.isActivo()?1:0);

				  stmt.executeInsert();
				  stmt.clearBindings();
			  }
			  db.setTransactionSuccessful();
			  db.endTransaction();
			  return 1;
		  }catch(Exception localException){
			  if(db.inTransaction()){
				  db.endTransaction();
			  }
			  return 0;
		  }
	  }
	  
	  //PREVENTAPRODUCTOTEMP//
	  public static final String KEY_PREVENTAPRODUCTOTEMP_ROW_ID = "_Id";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_TEMPID = "_tempId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_CLIENTEID = "_clienteId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_PRODUCTOID = "_productoId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_CANTIDAD = "_cantidad";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_CANTIDADPAQUETE = "_cantidadPaquete";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_MONTO = "_monto";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_DESCUENTO = "_descuento";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_MONTOFINAL = "_montoFinal";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_EMPLEADOID = "_empleadoId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_PROMOCIONID = "_promocionId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_COSTO = "_costo";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_COSTOID = "_costoId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_NOPREVENTA = "_noPreventa";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_PRECIOID = "_precioId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_DESCUENTOCANAL = "_descuentoCanal";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_DESCUENTOAJUSTE = "_descuentoAjuste";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_CANALPRECIORUTAID = "_canalPrecioRutaId";
	  public static final String KEY_PREVENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	  
	  public static final int COL_PREVENTAPRODUCTOTEMP_ROW_ID = 0;
	  public static final int COL_PREVENTAPRODUCTOTEMP_TEMPID = 1;
	  public static final int COL_PREVENTAPRODUCTOTEMP_CLIENTEID = 2;
	  public static final int COL_PREVENTAPRODUCTOTEMP_PRODUCTOID = 3;
	  public static final int COL_PREVENTAPRODUCTOTEMP_CANTIDAD = 4;
	  public static final int COL_PREVENTAPRODUCTOTEMP_CANTIDADPAQUETE = 5;
	  public static final int COL_PREVENTAPRODUCTOTEMP_MONTO = 6;
	  public static final int COL_PREVENTAPRODUCTOTEMP_DESCUENTO = 7;
	  public static final int COL_PREVENTAPRODUCTOTEMP_MONTOFINAL = 8;
	  public static final int COL_PREVENTAPRODUCTOTEMP_EMPLEADOID = 9;
	  public static final int COL_PREVENTAPRODUCTOTEMP_PROMOCIONID = 10;
	  public static final int COL_PREVENTAPRODUCTOTEMP_COSTO = 11;
	  public static final int COL_PREVENTAPRODUCTOTEMP_COSTOID = 12;
	  public static final int COL_PREVENTAPRODUCTOTEMP_NOPREVENTA = 13;
	  public static final int COL_PREVENTAPRODUCTOTEMP_PRECIOID = 14;
	  public static final int COL_PREVENTAPRODUCTOTEMP_DESCUENTOCANAL = 15;
	  public static final int COL_PREVENTAPRODUCTOTEMP_DESCUENTOPRECIO = 16;
	  public static final int COL_PREVENTAPRODUCTOTEMP_CANALPRECIORUTAID = 17;
	  public static final int COL_PREVENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO = 18;
	  
	  public static final String[] PREVENTAPRODUCTOTEMP_ALL_KEYS = new String[] { 
		  KEY_PREVENTAPRODUCTOTEMP_ROW_ID, KEY_PREVENTAPRODUCTOTEMP_TEMPID, KEY_PREVENTAPRODUCTOTEMP_CLIENTEID,
		  KEY_PREVENTAPRODUCTOTEMP_PRODUCTOID, KEY_PREVENTAPRODUCTOTEMP_CANTIDAD, KEY_PREVENTAPRODUCTOTEMP_CANTIDADPAQUETE,
		  KEY_PREVENTAPRODUCTOTEMP_MONTO, KEY_PREVENTAPRODUCTOTEMP_DESCUENTO, KEY_PREVENTAPRODUCTOTEMP_MONTOFINAL,
		  KEY_PREVENTAPRODUCTOTEMP_EMPLEADOID, KEY_PREVENTAPRODUCTOTEMP_PROMOCIONID, KEY_PREVENTAPRODUCTOTEMP_COSTO,
		  KEY_PREVENTAPRODUCTOTEMP_COSTOID, KEY_PREVENTAPRODUCTOTEMP_NOPREVENTA, KEY_PREVENTAPRODUCTOTEMP_PRECIOID,
		  KEY_PREVENTAPRODUCTOTEMP_DESCUENTOCANAL, KEY_PREVENTAPRODUCTOTEMP_DESCUENTOAJUSTE, KEY_PREVENTAPRODUCTOTEMP_CANALPRECIORUTAID,
		  KEY_PREVENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO};
	  
	  public static final String PREVENTAPRODUCTOTEMP_TABLE_NAME = "tbl_PreventaProductoTemp";
	  
	  public static final String PREVENTAPRODUCTOTEMP_TABLE_CREATE = "CREATE TABLE " + PREVENTAPRODUCTOTEMP_TABLE_NAME + "("
	  		+ KEY_PREVENTAPRODUCTOTEMP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_TEMPID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_CLIENTEID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_PRODUCTOID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_CANTIDAD + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_CANTIDADPAQUETE+ " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_MONTO + " float NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_DESCUENTO + " float NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_MONTOFINAL + " float NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_EMPLEADOID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_PROMOCIONID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_COSTO + " float NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_COSTOID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_NOPREVENTA + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_PRECIOID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_DESCUENTOCANAL + " float NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_DESCUENTOAJUSTE + " float NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_CANALPRECIORUTAID + " integer NOT NULL, "
	  		+ KEY_PREVENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO + " float NOT NULL);";
	  	    
	  	public boolean borrarPreventaProductoTemp()
	    {
	       return this.db.delete(PREVENTAPRODUCTOTEMP_TABLE_NAME, null, null) > 0;
	    }
	  
	    public boolean borrarPreventaProductoTempPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(PREVENTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarPreventaProductoTempPorClienteIdEmpleadoId(int clienteId, int empleadoId)
	    {
	      String str = "_clienteId=" + clienteId + " and _empleadoId=" + empleadoId;
	      return this.db.delete(PREVENTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarPreventaProductoTempPorClienteIdEmpleadoIdNoPreventa(int clienteId, int empleadoId,int noPreventa)
	    {
	      String str = "_clienteId=" + clienteId + " and _empleadoId=" + empleadoId + " and _noPreventa =" + noPreventa;
	      return this.db.delete(PREVENTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarPreventaProductoTemp(int tempId, int clienteId, int productoId, int cantidad, int cantidadPaquete, 
	    		float monto, float descuento, float montoFinal, int empleadoId, int promocionId, float costo, int costoId,
	    		int noPreventa, int precioId, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId, 
	    		float descuentoProntoPago)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_tempId", tempId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_noPreventa", noPreventa);
	      localContentValues.put("_precioId", precioId);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      return this.db.insert(PREVENTAPRODUCTOTEMP_TABLE_NAME, null, localContentValues);
	    }
	    
	    public int modificarPreventaProductoTemp(int id, int tempId)
	    {
	      String str = "_Id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_tempId", tempId);
	      return this.db.update(PREVENTAPRODUCTOTEMP_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerPreventaProductoTempNoSincronizadasPor(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId + " and _tempId = 0";
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOTEMP_TABLE_NAME, PREVENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoTemp()
	    {
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOTEMP_TABLE_NAME, PREVENTAPRODUCTOTEMP_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoTempPor(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOTEMP_TABLE_NAME, PREVENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoTempPor(int clienteId,int noPreventa)
	    {
	      String str = "_clienteId=" + clienteId + " and _noPreventa =" + noPreventa;
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOTEMP_TABLE_NAME, PREVENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	  
	  //PREVENTA//
	    public static final String KEY_PREVENTA_ROW_ID = "_id";
	    public static final String KEY_PREVENTA_PREVENTAIDSERVER = "_preventaIdServer";
	    public static final String KEY_PREVENTA_EMPLEADOID = "_empleadoId";
	    public static final String KEY_PREVENTA_CLIENTEID = "_clienteId";
	    public static final String KEY_PREVENTA_MONTO = "_monto";
	    public static final String KEY_PREVENTA_DESCUENTO = "_descuento";
	    public static final String KEY_PREVENTA_MONTOFINAL = "_montoFinal";
	    public static final String KEY_PREVENTA_TIPOPAGOID = "_tipoPagoId";
	    public static final String KEY_PREVENTA_LATITUD = "_latitud";
	    public static final String KEY_PREVENTA_LONGITUD = "_longitud";
	    public static final String KEY_PREVENTA_ESTADO = "_estado";
	    public static final String KEY_PREVENTA_DIA = "_dia";
	    public static final String KEY_PREVENTA_MES = "_mes";
	    public static final String KEY_PREVENTA_ANIO = "_anio";
	    public static final String KEY_PREVENTA_HORA = "_hora";
	    public static final String KEY_PREVENTA_MINUTO = "_minuto";
	    public static final String KEY_PREVENTA_FACTURA = "_factura";
	    public static final String KEY_PREVENTA_NIT = "_nit";
	    public static final String KEY_PREVENTA_NITNUEVO = "_nitNuevo";
	    public static final String KEY_PREVENTA_NOPREVENTA = "_noPreventa";
	    public static final String KEY_PREVENTA_OBSERVACION = "_observacion";
	    public static final String KEY_PREVENTA_APLICARBONIFICACION = "_aplicarBonificacion";
	    public static final String KEY_PREVENTA_DIAENTREGA = "_diaEntrega";
	    public static final String KEY_PREVENTA_MESENTREGA = "_mesEntrega";
	    public static final String KEY_PREVENTA_ANIOENTREGA = "_anioEntrega";
	    public static final String KEY_PREVENTA_DOSIFICACIONID = "_dosificacionId";
	    public static final String KEY_PREVENTA_TIPONIT = "_tipoNit";
	    public static final String KEY_PREVENTA_RUTA = "_ruta";
	    public static final String KEY_PREVENTA_TIPOVISITA = "_tipoVisita";
	    public static final String KEY_PREVENTA_ZONAVENTAID = "_zonaVentaId";
	    public static final String KEY_PREVENTA_PRONTOPAGOID = "_prontoPagoId";
	    public static final String KEY_PREVENTA_DESCUENTOCANAL = "_descuentoCanal";
	    public static final String KEY_PREVENTA_DESCUENTOAJUSTE = "_descuentoAjuste";
	    public static final String KEY_PREVENTA_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    public static final String KEY_PREVENTA_DESCUENTOOBJETIVO = "_descuentoObjetivo";
	    public static final String KEY_PREVENTA_FORMAPAGOID = "_formaPagoId";
	    public static final String KEY_PREVENTA_CODTRANSFERENCIA = "_codTransferencia";
	    public static final String KEY_PREVENTA_FROMAPK = "_fromApk";
	    public static final String KEY_PREVENTA_FROMSHOPP = "_fromShopp";
		public static final String KEY_PREVENTA_DESCUENTOINCENTIVO = "_descuentoIncentivo";

	    public static final int COL_PREVENTA_ROW_ID = 0;
	    public static final int COL_PREVENTA_PREVENTAIDSERVER = 1;
	    public static final int COL_PREVENTA_EMPLEADOID = 2;
	    public static final int COL_PREVENTA_CLIENTEID = 3;
	    public static final int COL_PREVENTA_MONTO = 4;
	    public static final int COL_PREVENTA_DESCUENTO = 5;
	    public static final int COL_PREVENTA_MONTOFINAL = 6;
	    public static final int COL_PREVENTA_TIPOPAGOID = 7;
	    public static final int COL_PREVENTA_LATITUD = 8;
	    public static final int COL_PREVENTA_LONGITUD = 9;
	    public static final int COL_PREVENTA_ESTADO = 10;
	    public static final int COL_PREVENTA_DIA = 11;
	    public static final int COL_PREVENTA_MES = 12;
	    public static final int COL_PREVENTA_ANIO = 13;
	    public static final int COL_PREVENTA_HORA = 14;
	    public static final int COL_PREVENTA_MINUTO = 15;
	    public static final int COL_PREVENTA_FACTURA = 16;
	    public static final int COL_PREVENTA_NIT = 17;
	    public static final int COL_PREVENTA_NITNUEVO = 18;
	    public static final int COL_PREVENTA_NOPREVENTA = 19;
	    public static final int COL_PREVENTA_OBSERVACION = 20;
	    public static final int COL_PREVENTA_APLICARBONIFICACION = 21;
	    public static final int COL_PREVENTA_DIAENTREGA = 22;
	    public static final int COL_PREVENTA_MESENTREGA = 23;
	    public static final int COL_PREVENTA_ANIOENTREGA = 24;
	    public static final int COL_PREVENTA_DOSIFICACIONID = 25;
	    public static final int COL_PREVENTA_TIPONIT = 26;
	    public static final int COL_PREVENTA_RUTA = 27;
	    public static final int COL_PREVENTA_TIPOVISITA = 28;
	    public static final int COL_PREVENTA_ZONAVENTAID = 29;
	    public static final int COL_PREVENTA_PRONTOPAGOID = 30;
	    public static final int COL_PREVENTA_DESCUENTOCANAL = 31;
	    public static final int COL_PREVENTA_DESCUENTOAJUSTE = 32;
	    public static final int COL_PREVENTA_DESCUENTOPRONTOPAGO = 33;
	    public static final int COL_PREVENTA_DESCUENTOOBJETIVO = 34;
	    public static final int COL_PREVENTA_FORMAPAGOID = 35;
	    public static final int COL_PREVENTA_CODTRANSFERENCIA = 36;
	    public static final int COL_PREVENTA_FROMAPK = 37;
	    public static final int COL_PREVENTA_FROMSHOPP = 38;
		public static final int COL_PREVENTA_DESCUENTOINCENTIVO = 39;
	    
	    public static final String[] PREVENTA_ALL_KEYS = new String[] {
	    	KEY_PREVENTA_ROW_ID,KEY_PREVENTA_PREVENTAIDSERVER,KEY_PREVENTA_EMPLEADOID,
	    	KEY_PREVENTA_CLIENTEID,KEY_PREVENTA_MONTO,KEY_PREVENTA_DESCUENTO,
	    	KEY_PREVENTA_MONTOFINAL,KEY_PREVENTA_TIPOPAGOID,KEY_PREVENTA_LATITUD,
	    	KEY_PREVENTA_LONGITUD,KEY_PREVENTA_ESTADO,KEY_PREVENTA_DIA,KEY_PREVENTA_MES,
	    	KEY_PREVENTA_ANIO,KEY_PREVENTA_HORA,KEY_PREVENTA_MINUTO,KEY_PREVENTA_FACTURA,
	    	KEY_PREVENTA_NIT,KEY_PREVENTA_NITNUEVO,KEY_PREVENTA_NOPREVENTA,
	    	KEY_PREVENTA_OBSERVACION,KEY_PREVENTA_APLICARBONIFICACION,KEY_PREVENTA_DIAENTREGA,
	    	KEY_PREVENTA_MESENTREGA,KEY_PREVENTA_ANIOENTREGA,KEY_PREVENTA_DOSIFICACIONID,
	    	KEY_PREVENTA_TIPONIT,KEY_PREVENTA_RUTA,KEY_PREVENTA_TIPOVISITA,
	    	KEY_PREVENTA_ZONAVENTAID, KEY_PREVENTA_PRONTOPAGOID, KEY_PREVENTA_DESCUENTOCANAL,
	    	KEY_PREVENTA_DESCUENTOAJUSTE, KEY_PREVENTA_DESCUENTOPRONTOPAGO, KEY_PREVENTA_DESCUENTOOBJETIVO,
	    	KEY_PREVENTA_FORMAPAGOID, KEY_PREVENTA_CODTRANSFERENCIA, KEY_PREVENTA_FROMAPK,
	    	KEY_PREVENTA_FROMSHOPP, KEY_PREVENTA_DESCUENTOINCENTIVO};
	    
	    public static final String PREVENTA_TABLE_NAME = "tbl_Preventa";
	    
	    public static final String PREVENTA_TABLE_CREATE = "CREATE TABLE " + PREVENTA_TABLE_NAME + "("
	    		+ KEY_PREVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PREVENTA_PREVENTAIDSERVER + " integer NOT NULL, "
	    		+ KEY_PREVENTA_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_MONTO + " float NOT NULL, "
	    		+ KEY_PREVENTA_DESCUENTO+ " float NOT NULL, "
	    		+ KEY_PREVENTA_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_PREVENTA_TIPOPAGOID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_LATITUD + " double NOT NULL, "
	    		+ KEY_PREVENTA_LONGITUD + " double NOT NULL, "
	    		+ KEY_PREVENTA_ESTADO + " boolean NOT NULL, "
	    		+ KEY_PREVENTA_DIA + " integer NOT NULL, "
	    		+ KEY_PREVENTA_MES + " integer NOT NULL, "
	    		+ KEY_PREVENTA_ANIO + " integer NOT NULL, "
	    		+ KEY_PREVENTA_HORA + " integer NOT NULL, "
	    		+ KEY_PREVENTA_MINUTO + " int NOT NULL, "
	    		+ KEY_PREVENTA_FACTURA + " text NOT NULL, "
	    		+ KEY_PREVENTA_NIT + " text NOT NULL, "
	    		+ KEY_PREVENTA_NITNUEVO + " boolean NOT NULL, "
	    		+ KEY_PREVENTA_NOPREVENTA + " int NOT NULL, "
	    		+ KEY_PREVENTA_OBSERVACION + " text NOT NULL, "
	    		+ KEY_PREVENTA_APLICARBONIFICACION + " boolean NOT NULL, "
	    		+ KEY_PREVENTA_DIAENTREGA + " integer NOT NULL, "
	    		+ KEY_PREVENTA_MESENTREGA + " integer NOT NULL, "
	    		+ KEY_PREVENTA_ANIOENTREGA + " integer NOT NULL, "
	    		+ KEY_PREVENTA_DOSIFICACIONID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_TIPONIT + " text NOT NULL, "
	    		+ KEY_PREVENTA_RUTA + " text NOT NULL, "
	    		+ KEY_PREVENTA_TIPOVISITA + " text NOT NULL, "
	    		+ KEY_PREVENTA_ZONAVENTAID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_PRONTOPAGOID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_PREVENTA_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_PREVENTA_DESCUENTOPRONTOPAGO + " float NOT NULL, "
	    		+ KEY_PREVENTA_DESCUENTOOBJETIVO + " float NOT NULL, "
	    		+ KEY_PREVENTA_FORMAPAGOID + " integer NOT NULL, "
	    		+ KEY_PREVENTA_CODTRANSFERENCIA + " text NOT NULL, "
	    		+ KEY_PREVENTA_FROMAPK + " boolean NOT NULL, "
	    		+ KEY_PREVENTA_FROMSHOPP + " boolean NOT NULL, "
				+ KEY_PREVENTA_DESCUENTOINCENTIVO + " float NOT NULL);";
	    
	    public long insertarPreventa(int preventaIdServer, int empleadoId, int clienteId, float monto, float descuento, 
	    		float montoFinal, int tipoPagoId, double latitud, double longitud, boolean estado,int dia,int mes,
	    		int anio, int hora, int minuto, String factura, String nit, boolean nitNuevo, int noPreventa, String observacion,
	    		boolean aplicarBonificacion, int diaEntrega, int mesEntrega, int anioEntrega, int dosificacionId, String tipoNit,
	    		String ruta, String tipoVisita, int zonaVentaId, int prontoPagoId, float descuentoCanal, float descuentoAjuste,
	    		float descuentoProntoPago, float descuentoObjetivo, int formaPagoId, String codTransferencia, boolean fromApk,
	    		boolean fromShopp, float descuentoIncentivo)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaIdServer", preventaIdServer);
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_tipoPagoId", tipoPagoId);
	      localContentValues.put("_latitud", latitud);
	      localContentValues.put("_longitud", longitud);
	      localContentValues.put("_estado", estado);
	      localContentValues.put("_dia", dia);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_hora", hora);
	      localContentValues.put("_minuto", minuto);
	      localContentValues.put("_factura", factura);
	      localContentValues.put("_nit", nit);
	      localContentValues.put("_nitNuevo", nitNuevo);
	      localContentValues.put("_noPreventa", noPreventa);
	      localContentValues.put("_observacion", observacion);
	      localContentValues.put("_aplicarBonificacion", aplicarBonificacion);
	      localContentValues.put("_diaEntrega", diaEntrega);
	      localContentValues.put("_mesEntrega", mesEntrega);
	      localContentValues.put("_anioEntrega", anioEntrega);
	      localContentValues.put("_dosificacionId", dosificacionId);
	      localContentValues.put("_tipoNit", tipoNit);
	      localContentValues.put("_ruta", ruta);
	      localContentValues.put("_tipoVisita", tipoVisita);
	      localContentValues.put("_zonaVentaId", zonaVentaId);
	      localContentValues.put("_prontoPagoId", prontoPagoId);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      localContentValues.put("_descuentoObjetivo", descuentoObjetivo);
	      localContentValues.put("_formaPagoId", formaPagoId);
	      localContentValues.put("_codTransferencia", codTransferencia);
	      localContentValues.put("_fromApk", fromApk);
	      localContentValues.put("_fromShopp", fromShopp);
	      localContentValues.put("_descuentoIncentivo", descuentoIncentivo);
	      return this.db.insert(PREVENTA_TABLE_NAME, null, localContentValues);
	    }
	    
	    public boolean borrarPreventaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(PREVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPreventas()
	    {
	      Cursor localCursor = obtenerPreventas();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPreventaPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public Cursor obtenerPreventaPor(int id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventaPorClienteId(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerNroPreventasPorClienteId(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventaPorId(int rowId)
	    {
	      String str = "_id=" + rowId;
	      Cursor localCursor = this.db.query(true,PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventas()
	    {
	      Cursor localCursor = this.db.query(true, PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasNoSincronizadas()
	    {
	    	String str= "_estado= 0";
	    	Cursor localCursor = this.db.query(true, PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPreventasNoSincronizadasPorRowId(int rowId)
	    {
	    	String str= "_id = " + rowId + "and _estado= 0";
	    	Cursor localCursor = this.db.query(true, PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public int modificarPreventaNoSincronizadaPor(int Id,float monto,float descuento,float montoFinal)
	    {
	    	String str = "_id=" + Id;
	    	ContentValues localContentValues = new ContentValues();
	    	localContentValues.put("_estado", Boolean.TRUE);
	    	localContentValues.put("_monto", monto);
	    	localContentValues.put("_descuento", descuento);
	    	localContentValues.put("_montoFinal", montoFinal);
	    	return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarPreventaNoSincronizadaPor(int Id,boolean estado)
	    {
	      String str = "_id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estado", estado);
	      return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    
	    public int modificarPreventaNoSincronizadaPor(int Id, int preventaId)
	    {
	      String str = "_id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_estado", Boolean.TRUE);
	      return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarPreventaClienteIdPorIdYClienteId(int Id, int clienteId)
	    {
	      String str = "_clienteId=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteId", clienteId);
	      return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarPreventaIdServer(int Id,int preventaIdServer)
	    {
	      String str = "_id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaIdServer", preventaIdServer);
	      return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
	    }

		public int modificarPreventaClienteIncentivo(int preventaIdServer, float montoFinal, float descuentoIncentivo)
		{
			String str = "_preventaIdServer=" + preventaIdServer;
			ContentValues localContentValues = new ContentValues();
			localContentValues.put("_descuentoIncentivo", descuentoIncentivo);
			localContentValues.put("_montoFinal", montoFinal);
			return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
		}
	    
	    public Cursor obtenerPreventaPorPreventaIdServer(int preventaIdServer)
	    {
	      String str = "_preventaIdServer=" + preventaIdServer;
	      Cursor localCursor = this.db.query(true,PREVENTA_TABLE_NAME, PREVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public int modificarPreventaMontosPorPreventaId(int preventaId, float monto, float descuento, float montoFinal,
	    												float descuentoCanal, float descuentoAjuste, float descuentoProntoPago, float descuentoObjetivo)
	    {
	      String str = "_id=" + preventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      localContentValues.put("_descuentoObjetivo", descuentoObjetivo);
	      return this.db.update(PREVENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerNroPreventas()
		{
			String query = "select count(distinct _clienteId) from tbl_preventa";
			Cursor localCursor = db.rawQuery(query,null);
			if (localCursor != null) 
			{
				localCursor.moveToFirst();
			}
			return localCursor;
		}
	    
	    //PREVENTAPRODUCTO//
	    public static final String KEY_PREVENTAPRODUCTO_ROW_ID = "_Id";
	    public static final String KEY_PREVENTAPRODUCTO_PREVENTAID = "_preventaId";
	    public static final String KEY_PREVENTAPRODUCTO_PRODUCTOID = "_productoId";
	    public static final String KEY_PREVENTAPRODUCTO_CANTIDAD = "_cantidad";
	    public static final String KEY_PREVENTAPRODUCTO_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_PREVENTAPRODUCTO_MONTO = "_monto";
	    public static final String KEY_PREVENTAPRODUCTO_DESCUENTO = "_descuento";
	    public static final String KEY_PREVENTAPRODUCTO_MONTOFINAL = "_montoFinal";
	    public static final String KEY_PREVENTAPRODUCTO_PROMOCIONID = "_promocionId";
	    public static final String KEY_PREVENTAPRODUCTO_EMPLEADOID = "_empleadoId";
	    public static final String KEY_PREVENTAPRODUCTO_ESTADO = "_estado";
	    public static final String KEY_PREVENTAPRODUCTO_COSTO = "_costo";
	    public static final String KEY_PREVENTAPRODUCTO_COSTOID = "_costoId";
	    public static final String KEY_PREVENTAPRODUCTO_NOPREVENTA = "_noPreventa";
	    public static final String KEY_PREVENTAPRODUCTO_PRECIOID = "_precioId";
	    public static final String KEY_PREVENTAPRODUCTO_DESCUENTOCANAL = "_descuentoCanal";	    
	    public static final String KEY_PREVENTAPRODUCTO_DESCUENTOAJUSTE = "_descuentoAjuste";
	    public static final String KEY_PREVENTAPRODUCTO_CANALPRECIORUTAID = "_canalPrecioRutaId";
	    public static final String KEY_PREVENTAPRODUCTO_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    
	    public static final int COL_PREVENTAPRODUCTO_ROW_ID = 0;
	    public static final int COL_PREVENTAPRODUCTO_PREVENTAID = 1;
	    public static final int COL_PREVENTAPRODUCTO_PRODUCTOID = 2;
	    public static final int COL_PREVENTAPRODUCTO_CANTIDAD = 3;
	    public static final int COL_PREVENTAPRODUCTO_CANTIDADPAQUETE = 4;
	    public static final int COL_PREVENTAPRODUCTO_MONTO = 5;
	    public static final int COL_PREVENTAPRODUCTO_DESCUENTO = 6;
	    public static final int COL_PREVENTAPRODUCTO_MONTOFINAL = 7;
	    public static final int COL_PREVENTAPRODUCTO_EMPLEADOID = 8;
	    public static final int COL_PREVENTAPRODUCTO_PROMOCIONID = 9;
	    public static final int COL_PREVENTAPRODUCTO_ESTADO = 10;
	    public static final int COL_PREVENTAPRODUCTO_COSTO = 11;
	    public static final int COL_PREVENTAPRODUCTO_COSTOID = 12;
	    public static final int COL_PREVENTAPRODUCTO_NOPREVENTA = 13;
	    public static final int COL_PREVENTAPRODUCTO_PRECIOID = 14;
	    public static final int COL_PREVENTAPRODUCTO_DESCUENTOCANAL = 15;
	    public static final int COL_PREVENTAPRODUCTO_DESCUENTOAJUSTE = 16;
	    public static final int COL_PREVENTAPRODUCTO_CANALPRECIOLISTAID = 17;
	    public static final int COL_PREVENTAPRODUCTO_DESCUENTOPRONTOPAGO = 18;
	    
	    public static final String[] PREVENTAPRODUCTO_ALL_KEYS = new String[] { 
	    	KEY_PREVENTAPRODUCTO_ROW_ID,KEY_PREVENTAPRODUCTO_PREVENTAID,
	    	KEY_PREVENTAPRODUCTO_PRODUCTOID,KEY_PREVENTAPRODUCTO_CANTIDAD,KEY_PREVENTAPRODUCTO_CANTIDADPAQUETE,
	    	KEY_PREVENTAPRODUCTO_MONTO,KEY_PREVENTAPRODUCTO_DESCUENTO,KEY_PREVENTAPRODUCTO_MONTOFINAL,
	    	KEY_PREVENTAPRODUCTO_EMPLEADOID,KEY_PREVENTAPRODUCTO_PROMOCIONID,KEY_PREVENTAPRODUCTO_ESTADO,
	    	KEY_PREVENTAPRODUCTO_COSTO,KEY_PREVENTAPRODUCTO_COSTOID,KEY_PREVENTAPRODUCTO_NOPREVENTA,
	    	KEY_PREVENTAPRODUCTO_PRECIOID, KEY_PREVENTAPRODUCTO_DESCUENTOCANAL, KEY_PREVENTAPRODUCTO_DESCUENTOAJUSTE,
	    	KEY_PREVENTAPRODUCTO_CANALPRECIORUTAID, KEY_PREVENTAPRODUCTO_DESCUENTOPRONTOPAGO}; 
	    
	    public static final String PREVENTAPRODUCTO_TABLE_NAME = "tbl_PreventaProducto";
	    
	    public static final String PREVENTAPRODUCTO_TABLE_CREATE = "CREATE TABLE " + PREVENTAPRODUCTO_TABLE_NAME + "("
	    		+ KEY_PREVENTAPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PREVENTAPRODUCTO_PREVENTAID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_MONTO + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_DESCUENTO + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_ESTADO + " boolean NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_COSTO + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_COSTOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_NOPREVENTA + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_PRECIOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_CANALPRECIORUTAID + " int NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTO_DESCUENTOPRONTOPAGO + " float NOT NULL);";
	    
	    public boolean borrarPreventaProductoPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(PREVENTAPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPreventasProducto()
	    {
	      Cursor localCursor = obtenerPreventasProducto();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPreventaProductoPorId(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPreventaProducto(int preventaId, int productoId, int cantidad, int cantidadPaquete, 
	    		float monto, float descuento, float montoFinal, int empleadoId, int promocionId, boolean estado,
	    		float costo, int costoId, int noPreventa, int precioId, float descuentoCanal, float descuentoAjuste,
	    		int canalPrecioRutaId, float descuentoProntoPago)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_estado", estado);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_noPreventa", noPreventa);
	      localContentValues.put("_precioId", precioId);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      return this.db.insert(PREVENTAPRODUCTO_TABLE_NAME, null, localContentValues);
	    }
	    
	    public int modificarPreventaProducto(int Id, int preventaId)
	    {
	      String str = "_Id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaId", preventaId);
	      return this.db.update(PREVENTAPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarPreventaProductoNoSincronizadaPor(int Id)
	    {
	      String str = "_Id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estado", Boolean.TRUE);
	      return this.db.update(PREVENTAPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarPreventaProductoNoSincronizadaPor(int preventaId,boolean estado)
	    {
	      String str = "_preventaId=" + preventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estado", estado);
	      return this.db.update(PREVENTAPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarPreventaProductoNoSincronizadaPor(int Id, int preventaId)
	    {
	      String str = "_Id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_estado", Boolean.TRUE);
	      return this.db.update(PREVENTAPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerPreventasProducto()
	    {
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTO_TABLE_NAME, PREVENTAPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoNoSincronizadas()
	    {
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTO_TABLE_NAME, PREVENTAPRODUCTO_ALL_KEYS, "_estado= 0", null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoNoSincronizadasPor(int preventaId)
	    {
	      String str = "_preventaId=" + preventaId + " and _estado = 0";
	      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTO_TABLE_NAME, PREVENTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoPor(int preventaId)
	    {
	      String str = "_preventaId=" + preventaId;
	      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTO_TABLE_NAME, PREVENTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public boolean borrarPreventaProductoPorPreventaId(long preventaId)
	    {
	      String str = "_preventaId=" + preventaId;
	      return this.db.delete(PREVENTAPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public Cursor obtenerPreventaProductoPorRowId(int rowId)
	    {
	      String str = "_Id=" + rowId;
	      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTO_TABLE_NAME, PREVENTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //ALMACEN//
	    public static final String KEY_ALMACEN_ROW_ID = "_id";
	    public static final String KEY_ALMACEN_ALMACENID = "_almacenId";
	    public static final String KEY_ALMACEN_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_ALMACEN_ROW_ID = 0;
	    public static final int COL_ALMACEN_ALMACENID = 1;
	    public static final int COL_ALMACEN_DESCRIPCION = 2;
	    
	    public static final String[] ALMACEN_ALL_KEYS = new String[] { 
	    	KEY_ALMACEN_ROW_ID,KEY_ALMACEN_ALMACENID, KEY_ALMACEN_DESCRIPCION 
	    	};
	    
	    public static final String ALMACEN_TABLE_NAME = "tbl_Almacen";
	    
	    public static final String ALMACEN_TABLE_CREATE = "CREATE TABLE " + ALMACEN_TABLE_NAME + "("
	    		+ KEY_ALMACEN_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_ALMACEN_ALMACENID + " integer NOT NULL, "
	    		+ KEY_ALMACEN_DESCRIPCION + " text NOT NULL);";
	    
	    
	    public boolean borrarAlmacenPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(ALMACEN_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarAlmacenes()
	    {
	      Cursor localCursor = obtenerAlmacenes();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarAlmacenPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarAlmacen(AlmacenTempWSResult almacen)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_almacen(_almacenId, _descripcion) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				//for (int i = 0; i < almacen.getPropertyCount(); i++) {
				//	SoapObject soapObject = (SoapObject) almacen.getProperty(i);

					stmt.bindLong(1, almacen.getAlmacenId());
					stmt.bindString(2, almacen.getDescripcion());

					stmt.executeInsert();
					stmt.clearBindings();
				//}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerAlmacenPor(int almacenId)
	    {
	      String str = "_almacenId=" + almacenId;
	      Cursor localCursor = this.db.query(true,ALMACEN_TABLE_NAME, ALMACEN_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerAlmacenes()
	    {
	      Cursor localCursor = this.db.query(true, ALMACEN_TABLE_NAME, ALMACEN_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //ALMACENPRODUCTO//
	    public static final String KEY_ALMACENPRODUCTO_ROW_ID = "_id";
	    public static final String KEY_ALMACENPRODUCTO_ALMACENID = "_almacenId";
	    public static final String KEY_ALMACENPRODUCTO_PRODUCTOID = "_productoId";	    
	    public static final String KEY_ALMACENPRODUCTO_SALDOUNITARIO = "_saldoUnitario";
	    public static final String KEY_ALMACENPRODUCTO_SALDOPAQUETE = "_saldoPaquete";
	    public static final String KEY_ALMACENPRODUCTO_COSTOUNITARIO = "_costoUnitario";
	    public static final String KEY_ALMACENPRODUCTO_COSTOPAQUETE = "_costoPaquete";
	    
	    public static final int COL_ALMACENPRODUCTO_ROW_ID = 0;	    
	    public static final int COL_ALMACENRPODUCTO_ALMACENID = 1;
	    public static final int COL_ALMACENPRODUCTO_PRODUCTOID = 2;
	    public static final int COL_ALMACENPRODUCTO_SALDOUNITARIO = 3;
	    public static final int COL_ALMACENPRODUCTO_SALDOPAQUETE = 4;
	    public static final int COL_ALMACENPRODUCTO_COSTOUNITARIO = 5;
	    public static final int COL_ALMACENPRODUCTO_COSTOPAQUETE = 6;
	    
	    public static final String[] ALMACENPRODUCTO_ALL_KEYS = new String[] { 
	    	KEY_ALMACENPRODUCTO_ROW_ID, KEY_ALMACENPRODUCTO_ALMACENID, KEY_ALMACENPRODUCTO_PRODUCTOID, 
	    	KEY_ALMACENPRODUCTO_SALDOUNITARIO, KEY_ALMACENPRODUCTO_SALDOPAQUETE, KEY_ALMACENPRODUCTO_COSTOUNITARIO, 
	    	KEY_ALMACENPRODUCTO_COSTOPAQUETE 
	    	};
	    
	    public static final String ALMACENPRODUCTO_TABLE_NAME = "tbl_AlmacenProducto";
	    
	    public static final String ALMACENPRODUCTO_TABLE_CREATE = "CREATE TABLE " + ALMACENPRODUCTO_TABLE_NAME + "("
	    		+ KEY_ALMACENPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_ALMACENPRODUCTO_ALMACENID + " integer NOT NULL, "
	    		+ KEY_ALMACENPRODUCTO_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_ALMACENPRODUCTO_SALDOUNITARIO +  " integer NOT NULL,"
	    		+ KEY_ALMACENPRODUCTO_SALDOPAQUETE + " integer NOT NULL,"
	    		+ KEY_ALMACENPRODUCTO_COSTOUNITARIO + " float NOT NULL,"
	    		+ KEY_ALMACENPRODUCTO_COSTOPAQUETE + " float NOT NULL);";
	    
	    public boolean borrarAlmacenProductoPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(ALMACENPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarAlmacenProductos()
	    {
	      Cursor localCursor = obtenerAlmacenesProducto();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarAlmacenProductoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }

		public long InsertarAlmacenProducto(ArrayList<AlmacenProductoWSResult> almacenProductos)
		{
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_almacenProducto(_almacenId, _productoId, _saldoUnitario, _saldoPaquete, _costoUnitario, _costoPaquete) VALUES (?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (AlmacenProductoWSResult item : almacenProductos) {

					stmt.bindLong(1, item.getAlmacenId());
					stmt.bindLong(2, item.getProductoId());
					stmt.bindLong(3, item.getSaldoUnitario());
					stmt.bindLong(4, item.getSaldoPaquete());
					stmt.bindDouble(5, item.getCostoUnitario());
					stmt.bindDouble(6, item.getCostoPaquete());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
		}

	public long insertarAlmacenProducto(int almacenId, int productoId, int saldoUnitario, int saldoPaquete,
										float costoUnitario, float costoPaquete)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_almacenId", almacenId);
		localContentValues.put("_productoId", productoId);
		localContentValues.put("_saldoUnitario", saldoUnitario);
		localContentValues.put("_saldoPaquete", saldoPaquete);
		localContentValues.put("_costoUnitario", costoUnitario);
		localContentValues.put("_costoPaquete", costoPaquete);
		return this.db.insert(ALMACENPRODUCTO_TABLE_NAME, null, localContentValues);
	}


	    public Cursor consolidarProductosLote()
	    {
	    	String query = "select _almacenId, _productoId, SUM(_saldoUnitario), SUM(_saldoPaquete), _costoUnitario, _costoPaquete " + 
	    					"from tbl_AlmacenProducto " + 
	    					"group by _almacenId, _productoId, _costoUnitario, _costoPaquete";
			Cursor localCursor = db.rawQuery(query,null);
			if (localCursor != null) 
			{
				localCursor.moveToFirst();
			}
			return localCursor;
	    }
	    
	    public Cursor deleteProductosLote()
	    {
	    	String query = "delete from tbl_AlmacenProducto";
			Cursor localCursor = db.rawQuery(query,null);
			if (localCursor != null) 
			{
				localCursor.moveToFirst();
			}
			return localCursor;
	    }
	    
	    public Cursor obtenerAlmacenProductoPor(int almacenId, int productoId)
	    {
	      String str = "_almacenId=" + almacenId + " and " + "_productoId" + "=" + productoId;
	      Cursor localCursor = this.db.query(true, ALMACENPRODUCTO_TABLE_NAME, ALMACENPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerAlmacenProductosPor(int almacenId)
	    {
	      String str = "_almacenId=" + almacenId;
	      Cursor localCursor = this.db.query(true, ALMACENPRODUCTO_TABLE_NAME, ALMACENPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerAlmacenesProducto()
	    {
	      Cursor localCursor = this.db.query(true, ALMACENPRODUCTO_TABLE_NAME, ALMACENPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerExistenciaProductoAlmacenProducto(int almacenId, int productoId, int conversionProducto, int cantidadSolicitadaEnUnidades)
	    {
	      String str = "_almacenId=" + almacenId + " and _productoId =" + productoId + " and (_saldoPaquete * " + conversionProducto + ") + _saldoUnitario" + ">=" + cantidadSolicitadaEnUnidades;
	     
	      Cursor localCursor = this.db.query(true, ALMACENPRODUCTO_TABLE_NAME, ALMACENPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor ObtenerExistenciaProductoEnAlmacenPor(int almacenId, int productoId)
	    {
	      String str = "_almacenId=" + almacenId + " and _productoId =" + productoId;
	     
	      Cursor localCursor = this.db.query(true, ALMACENPRODUCTO_TABLE_NAME, ALMACENPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor ObtenerInventarioAlmacenProductoPor(int proveedorId,int categoriaId)
	    {
	    	String query =  "SELECT ap._id, ap._almacenId, ap._productoId, ap._saldoUnitario, ap._saldoPaquete, ap._costoUnitario, ap._costoPaquete "
		  			+ "FROM tbl_almacenProducto AS ap "
		  			+ "JOIN  tbl_producto AS p ON(p._productoId = ap._productoId) "
		  			+ "WHERE p._proveedorId = " + proveedorId + " and p._categoriaid = " + categoriaId;
    	
	    	Cursor localCursor = db.rawQuery(query,null);
		  	if (localCursor != null) 
	  		{
		  		localCursor.moveToFirst();
		    }
		  return localCursor; 
	    }
	    
	    
	    //MOTIVONOENTREGA//
	    public static final String KEY_MOTIVONOENTREGA_ROW_ID = "_Id";
	    public static final String KEY_MOTIVONOENTREGA_MOTIVOID = "_motivoId";
	    public static final String KEY_MOTIVONOENTREGA_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_MOTIVONOENTREGA_ROW_ID = 0;
	    public static final int COL_MOTIVONOENTREGA_MOTIVOID = 1;
	    public static final int COL_MOTIVONOENTREGA_DESCRIPCION = 2;
	    
	    public static final String[] MOTIVONOENTREGA_ALL_KEYS = new String[] {
	    	KEY_MOTIVONOENTREGA_ROW_ID,KEY_MOTIVONOENTREGA_MOTIVOID,KEY_MOTIVONOENTREGA_DESCRIPCION 
	    	};
	    
	    public static final String MOTIVONOENTREGA_TABLE_NAME = "tbl_MotivoNoEntrega";
	    
	    public static final String MOTIVONOENTREGA_TABLE_CREATE = "CREATE TABLE " + MOTIVONOENTREGA_TABLE_NAME + " ("
	    		+ KEY_MOTIVONOENTREGA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_MOTIVONOENTREGA_MOTIVOID + " integer NOT NULL,"
	    		+ KEY_MOTIVONOENTREGA_DESCRIPCION + " text NOT NULL);";
	    
	    public boolean borrarMotivoNoEntregaPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(MOTIVONOENTREGA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarMotivosNoEntrega()
	    {
	      Cursor localCursor = obtenerMotivosNoEntrega();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarMotivoNoEntregaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarMotivoNoEntrega(ArrayList<MotivoNoEntregaWSResult> motivosNoEntrega)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_MotivoNoEntrega(_motivoId, _descripcion) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (MotivoNoEntregaWSResult item : motivosNoEntrega) {

					stmt.bindLong(1, item.getMotivoId());
					stmt.bindString(2, item.getDescripcion());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerMotivoNoEntregaPor(int motivoId)
	    {
	      String str = "_motivoId=" + motivoId;
	      Cursor localCursor = this.db.query(true,MOTIVONOENTREGA_TABLE_NAME, MOTIVONOENTREGA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerMotivosNoEntrega()
	    {
	      Cursor localCursor = this.db.query(true,MOTIVONOENTREGA_TABLE_NAME, MOTIVONOENTREGA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //PREVENTADIST//
	    public static final String KEY_PREVENTADIST_ROW_ID = "_id";
	    public static final String KEY_PREVENTADIST_PREVENTAID = "_preventaId";
	    public static final String KEY_PREVENTADIST_DIA = "_dia";
	    public static final String KEY_PREVENTADIST_MES = "_mes";
	    public static final String KEY_PREVENTADIST_ANIO = "_anio";
	    public static final String KEY_PREVENTADIST_CLIENTEID = "_clienteId";
	    public static final String KEY_PREVENTADIST_MONTO = "_monto";
	    public static final String KEY_PREVENTADIST_DESCUENTO = "_descuento";
	    public static final String KEY_PREVENTADIST_MONTOFINAL = "_montoFinal";
	    public static final String KEY_PREVENTADIST_TIPOPAGOID = "_tipoPagoId";
	    public static final String KEY_PREVENTADIST_TIPOPAGOFD = "_tipoPagoFD";
	    public static final String KEY_PREVENTADIST_CLIENTEFD = "_clienteFD";
	    public static final String KEY_PREVENTADIST_FECHAFD = "_fechaFD";
	    public static final String KEY_PREVENTADIST_MONTOFD = "_montoFD";
	    public static final String KEY_PREVENTADIST_ESTADOENTREGA = "_estadoEntrega";
	    public static final String KEY_PREVENTADIST_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    public static final String KEY_PREVENTADIST_EMPLEADOID = "_empleadoId";
	    public static final String KEY_PREVENTADIST_NIT = "_nit";
	    public static final String KEY_PREVENTADIST_NOMBREFACTURA = "_nombreFactura";
	    public static final String KEY_PREVENTADIST_OBSERVACION = "_observacion";
	    public static final String KEY_PREVENTADIST_DESCUENTO2 = "_descuento2";
	    public static final String KEY_PREVENTADIST_DOSIFICACIONID = "_dosificacionId";
	    public static final String KEY_PREVENTADIST_DESCUENTOCANAL = "_descuentoCanal";
	    public static final String KEY_PREVENTADIST_DESCUENTOAJUSTE = "_descuentoAjuste";
	    public static final String KEY_PREVENTADIST_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    public static final String KEY_PREVENTADIST_DESCUENTOOBJETIVO = "_descuentoObjetivo";
	    public static final String KEY_PREVENTADIST_PRONTOPAGOID = "_prontoPagoId";
	    public static final String KEY_PREVENTADIST_CODTRANSFERENCIA = "_codTransferencia";
	    public static final String KEY_PREVENTADIST_FROMAPK = "_fromApk";
	    public static final String KEY_PREVENTADIST_FROMSHOPP = "_fromShopp";
		public static final String KEY_PREVENTADIST_DESCUENTOSOCIO = "_descuentoSocio";
		public static final String KEY_PREVENTADIST_DESCUENTOINCENTIVO = "_descuentoIncentivo";
	    
	    public static final int COL_PREVENTADIST_ROW_ID = 0;
	    public static final int COL_PREVENTADIST_PREVENTAID = 1;
	    public static final int COL_PREVENTADIST_DIA = 2;
	    public static final int COL_PREVENTADIST_MES = 3;
	    public static final int COL_PREVENTADIST_ANIO = 4;
	    public static final int COL_PREVENTADIST_CLIENTEID = 5;
	    public static final int COL_PREVENTADIST_MONTO = 6;
	    public static final int COL_PREVENTADIST_DESCUENTO = 7;
	    public static final int COL_PREVENTADIST_MONTOFINAL = 8;
	    public static final int COL_PREVENTADIST_TIPOPAGOID = 9;
	    public static final int COL_PREVENTADIST_TIPOPAGOFD = 10;
	    public static final int COL_PREVENTADIST_CLIENTEFD = 11;
	    public static final int COL_PREVENTADIST_FECHAFD = 12;
	    public static final int COL_PREVENTADIST_MONTOFD = 13;
	    public static final int COL_PREVENTADIST_ESTADOENTREGA = 14;
	    public static final int COL_PREVENTADIST_ESTADOSINCRONIZACION = 15;
	    public static final int COL_PREVENTADIST_EMPLEADOID = 16;
	    public static final int COL_PREVENTADIST_NIT = 17;
	    public static final int COL_PREVENTADIST_NOMBREFACTURA = 18;
	    public static final int COL_PREVENTADIST_OBSERVACION = 19;
	    public static final int COL_PREVENTADIST_DESCUENTO2 = 20;
	    public static final int COL_PREVENTADIST_DOSIFICACIONID = 21;
	    public static final int COL_PREVENTADIST_DESCUENTOCANAL = 22;
	    public static final int COL_PREVENTADIST_DESCUENTOAJUSTE = 23;
	    public static final int COL_PREVENTADIST_DESCUENTOPRONTOPAGO = 24;
	    public static final int COL_PREVENTADIST_DESCUENTOOBJETIVO = 25;
	    public static final int COL_PREVENTADIST_PRONTOPAGOID = 26;
	    public static final int COL_PREVENTADIST_CODTRANSFERENCIA = 27;
	    public static final int COL_PREVENTADIST_FROMAPK = 28;
	    public static final int COL_PREVENTADIST_FROMSHOPP = 29;
		public static final int COL_PREVENTADIST_DESCUENTOSOCIO = 30;
		public static final int COL_PREVENTADIST_DESCUENTOINCENTIVO = 31;
	    
	    public static final String[] PREVENTADIST_ALL_KEYS = new String[] {
	    	KEY_PREVENTADIST_ROW_ID,KEY_PREVENTADIST_PREVENTAID,KEY_PREVENTADIST_DIA,
	    	KEY_PREVENTADIST_MES,KEY_PREVENTADIST_ANIO,KEY_PREVENTADIST_CLIENTEID,
	    	KEY_PREVENTADIST_MONTO,KEY_PREVENTADIST_DESCUENTO,KEY_PREVENTADIST_MONTOFINAL,
	    	KEY_PREVENTADIST_TIPOPAGOID,KEY_PREVENTADIST_TIPOPAGOFD,KEY_PREVENTADIST_CLIENTEFD,
	    	KEY_PREVENTADIST_FECHAFD,KEY_PREVENTADIST_MONTOFD,KEY_PREVENTADIST_ESTADOENTREGA,
	    	KEY_PREVENTADIST_ESTADOSINCRONIZACION,KEY_PREVENTADIST_EMPLEADOID,KEY_PREVENTADIST_NIT,
	    	KEY_PREVENTADIST_NOMBREFACTURA,KEY_PREVENTADIST_OBSERVACION,KEY_PREVENTADIST_DESCUENTO2,
	    	KEY_PREVENTADIST_DOSIFICACIONID, KEY_PREVENTADIST_DESCUENTOCANAL, KEY_PREVENTADIST_DESCUENTOAJUSTE,
	    	KEY_PREVENTADIST_DESCUENTOPRONTOPAGO, KEY_PREVENTADIST_DESCUENTOOBJETIVO, KEY_PREVENTADIST_PRONTOPAGOID,
	    	KEY_PREVENTADIST_CODTRANSFERENCIA, KEY_PREVENTADIST_FROMAPK, KEY_PREVENTADIST_FROMSHOPP,
			KEY_PREVENTADIST_DESCUENTOSOCIO, KEY_PREVENTADIST_DESCUENTOINCENTIVO};
	    
	    public static final String PREVENTADIST_TABLE_NAME = "tbl_PreventaDist";
	    
	    public static final String PREVENTADIST_TABLE_CREATE = "CREATE TABLE " + PREVENTADIST_TABLE_NAME + "("
	    		+ KEY_PREVENTADIST_ROW_ID+ " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_PREVENTADIST_PREVENTAID + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_DIA + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_MES + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_ANIO + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_CLIENTEID + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_MONTO + " float NOT NULL,"
	    		+ KEY_PREVENTADIST_DESCUENTO + " float NOT NULL,"
	    		+ KEY_PREVENTADIST_MONTOFINAL + " float NOT NULL,"
	    		+ KEY_PREVENTADIST_TIPOPAGOID + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_TIPOPAGOFD + " text NOT NULL,"
	    		+ KEY_PREVENTADIST_CLIENTEFD + " text NOT NULL,"
	    		+ KEY_PREVENTADIST_FECHAFD+ " text NOT NULL,"
	    		+ KEY_PREVENTADIST_MONTOFD + " text NOT NULL,"
	    		+ KEY_PREVENTADIST_ESTADOENTREGA + " integer NOT NULL,"
	    		+ KEY_PREVENTADIST_ESTADOSINCRONIZACION + " boolean NOT NULL,"
	    		+ KEY_PREVENTADIST_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_PREVENTADIST_NIT + " text NOT NULL, "
	    		+ KEY_PREVENTADIST_NOMBREFACTURA + " text NOT NULL, "
	    		+ KEY_PREVENTADIST_OBSERVACION + " text NOT NULL, "
	    		+ KEY_PREVENTADIST_DESCUENTO2 + " float NOT NULL, "
	    		+ KEY_PREVENTADIST_DOSIFICACIONID + " integer NOT NULL, "
	    		+ KEY_PREVENTADIST_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_PREVENTADIST_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_PREVENTADIST_DESCUENTOPRONTOPAGO + " float NOT NULL, "
	    		+ KEY_PREVENTADIST_DESCUENTOOBJETIVO + " float NOT NULL, "
	    		+ KEY_PREVENTADIST_PRONTOPAGOID + " float NOT NULL, "
	    		+ KEY_PREVENTADIST_CODTRANSFERENCIA + " text NOT NULL, "
	    		+ KEY_PREVENTADIST_FROMAPK + " boolean NOT NULL, "
	    		+ KEY_PREVENTADIST_FROMSHOPP + " boolean NOT NULL, "
				+ KEY_PREVENTADIST_DESCUENTOSOCIO + " float NOT NULL,"
				+ KEY_PREVENTADIST_DESCUENTOINCENTIVO + " float NOT NULL);";
	    
	    public boolean borrarPreventaDistPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(PREVENTADIST_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPreventasDist()
	    {
	      Cursor localCursor = obtenerPreventasDist();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPreventaDistPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPreventaDist(ArrayList<PreventaDisWSResult> preventasDist)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_PreventaDist(_preventaId, _dia, _mes, _anio, _clienteId, _monto, _descuento, " +
							"_montoFinal, _tipoPagoId, _tipoPagoFD, _clienteFD, _fechaFD, _montoFD, _estadoEntrega, " +
							"_estadoSincronizacion, _empleadoId, _nit, _nombreFactura, _observacion, _descuento2, _dosificacionId, " +
							"_descuentoCanal, _descuentoAjuste, _descuentoProntoPago, _descuentoObjetivo, _prontoPagoId, " +
							"_codTransferencia, _fromApk, _fromShopp, _descuentoSocio, _descuentoIncentivo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (PreventaDisWSResult item : preventasDist) {

					stmt.bindLong(1, item.getPreVentaId());
					stmt.bindLong(2, item.getDia());
					stmt.bindLong(3, item.getMes());
					stmt.bindLong(4, item.getAnio());
					stmt.bindLong(5, item.getClienteId());
					stmt.bindDouble(6, item.getMonto());
					stmt.bindDouble(7, item.getDescuento());
					stmt.bindDouble(8, item.getMontoFinal());
					stmt.bindLong(9, item.getTipoPagoId());
					stmt.bindString(10, item.getTipoPagoFD());
					stmt.bindString(11, item.getClienteFD());
					stmt.bindString(12, item.getFechaFD());
					stmt.bindString(13, item.getMontoFD());
					stmt.bindLong(14,0);
					stmt.bindLong(15,1);
					stmt.bindLong(16, item.getEmpleadoId());
					stmt.bindString(17, item.getNit());
					stmt.bindString(18, item.getNombreFactura());
					stmt.bindString(19, item.getObservacion());
					stmt.bindDouble(20, item.getDescuento2());
					stmt.bindLong(21, item.getDosificacionId());
					stmt.bindDouble(22, item.getDescuentoCanal());
					stmt.bindDouble(23, item.getDescuentoAjuste());
					stmt.bindDouble(24, item.getDescuentoProntoPago());
					stmt.bindDouble(25, item.getDescuentoObjetivo());
					stmt.bindLong(26, item.getProntoPagoId());
					stmt.bindString(27, item.getCodTransferencia());
					stmt.bindLong(28, item.isFromApk()?1:0);
					stmt.bindLong(29, item.isFromShopping()?1:0);
					stmt.bindDouble(30, item.getDescuentoSocio());
					stmt.bindDouble(31, item.getDescuentoIncentivo());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public int modificarPreventaDistEstadoEntrega(int preventaId, int estadoEntrega)
	    {
	      String str = "_preventaId=" + preventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoEntrega", estadoEntrega);
	      return this.db.update(PREVENTADIST_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerPreventaDistPor(int preventaId)
	    {
	      String str = "_preventaId=" + preventaId;
	      Cursor localCursor = this.db.query(true,PREVENTADIST_TABLE_NAME, PREVENTADIST_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventaDistPorClienteId(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,PREVENTADIST_TABLE_NAME, PREVENTADIST_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventaDistNoEntregadaPorClienteId(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId + " and _estadoEntrega = 0";
	      Cursor localCursor = this.db.query(true,PREVENTADIST_TABLE_NAME, PREVENTADIST_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasDist()
	    {
	      Cursor localCursor = this.db.query(true,PREVENTADIST_TABLE_NAME, PREVENTADIST_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //PREVENTAPRODUCTODIST//
	    public static final String KEY_PREVENTAPRODUCTODIST_ROW_ID = "_id";
	    public static final String KEY_PREVENTAPRODUCTODIST_PREVENTAPRODUCTOID = "_preventaProductoId";
	    public static final String KEY_PREVENTAPRODUCTODIST_PREVENTAID = "_preventaId";
	    public static final String KEY_PREVENTAPRODUCTODIST_PRODUCTOID = "_productoId";
	    public static final String KEY_PREVENTAPRODUCTODIST_PROMOCIONID = "_promocionId";
	    public static final String KEY_PREVENTAPRODUCTODIST_CANTIDAD = "_cantidad";
	    public static final String KEY_PREVENTAPRODUCTODIST_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_PREVENTAPRODUCTODIST_MONTO = "_monto";
	    public static final String KEY_PREVENTAPRODUCTODIST_DESCUENTO = "_descuento";
	    public static final String KEY_PREVENTAPRODUCTODIST_MONTOFINAL = "_montoFinal";
	    public static final String KEY_PREVENTAPRODUCTODIST_ESTADOID = "_estadoId";
	    public static final String KEY_PREVENTAPRODUCTODIST_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    public static final String KEY_PREVENTAPRODUCTODIST_COSTO = "_costo";
	    public static final String KEY_PREVENTAPRODUCTODIST_COSTOID = "_costoId";
	    public static final String KEY_PREVENTAPRODUCTODIST_PRECIOID = "_precioId";
	    public static final String KEY_PREVENTAPRODUCTODIST_DESCUENTOCANAL = "_descuentoCanal";	    
	    public static final String KEY_PREVENTAPRODUCTODIST_DESCUENTOAJUSTE = "_descuentoAjuste";
	    public static final String KEY_PREVENTAPRODUCTODIST_CANALPRECIORUTAID = "_canalPrecioRutaId";
	    public static final String KEY_PREVENTAPRODUCTODIST_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    
	    public static final int COL_PREVENTAPRODUCTODIST_ROW_ID = 0;
	    public static final int COL_PREVENTAPRODUCTODIST_PREVENTAPRODUCTOID = 1;
	    public static final int COL_PREVENTAPRODUCTODIST_PREVENTAID = 2;
	    public static final int COL_PREVENTAPRODUCTODIST_PRODUCTOID = 3;
	    public static final int COL_PREVENTAPRODUCTODIST_PROMOCIONID = 4;
	    public static final int COL_PREVENTAPRODUCTODIST_CANTIDAD = 5;
	    public static final int COL_PREVENTAPRODUCTODIST_CANTIDADPAQUETE = 6;
	    public static final int COL_PREVENTAPRODUCTODIST_MONTO = 7;
	    public static final int COL_PREVENTAPRODUCTODIST_DESCUENTO = 8;
	    public static final int COL_PREVENTAPRODUCTODIST_MONTOFINAL = 9;
	    public static final int COL_PREVENTAPRODUCTODIST_ESTADOID = 10;
	    public static final int COL_PREVENTAPRODUCTODIST_ESTADOSINCRONIZACION = 11;
	    public static final int COL_PREVENTAPRODUCTODIST_COSTO = 12;
	    public static final int COL_PREVENTAPRODUCTODIST_COSTOID = 13;
	    public static final int COL_PREVENTAPRODUCTODIST_PRECIOID = 14;
	    public static final int COL_PREVENTAPRODUCTODIST_DESCUENTOCANAL = 15;
	    public static final int COL_PREVENTAPRODUCTODIST_DESCUENTOAJUSTE = 16;
	    public static final int COL_PREVENTAPRODUCTODIST_CANALPRECIORUTAID = 17;
	    public static final int COL_PREVENTAPRODUCTODIST_DESCUENTPRONTOPAGO = 18;
	    
	    public static final String[] PREVENTAPRODUCTODIST_ALL_KEYS = new String[] {
	    	KEY_PREVENTAPRODUCTODIST_ROW_ID,KEY_PREVENTAPRODUCTODIST_PREVENTAPRODUCTOID,KEY_PREVENTAPRODUCTODIST_PREVENTAID,
	    	KEY_PREVENTAPRODUCTODIST_PRODUCTOID,KEY_PREVENTAPRODUCTODIST_PROMOCIONID,KEY_PREVENTAPRODUCTODIST_CANTIDAD,
	    	KEY_PREVENTAPRODUCTODIST_CANTIDADPAQUETE,KEY_PREVENTAPRODUCTODIST_MONTO,KEY_PREVENTAPRODUCTODIST_DESCUENTO,
	    	KEY_PREVENTAPRODUCTODIST_MONTOFINAL,KEY_PREVENTAPRODUCTODIST_ESTADOID,KEY_PREVENTAPRODUCTODIST_ESTADOSINCRONIZACION,
	    	KEY_PREVENTAPRODUCTODIST_COSTO, KEY_PREVENTAPRODUCTODIST_COSTOID, KEY_PREVENTAPRODUCTODIST_PRECIOID,
	    	KEY_PREVENTAPRODUCTODIST_DESCUENTOCANAL, KEY_PREVENTAPRODUCTODIST_DESCUENTOAJUSTE, KEY_PREVENTAPRODUCTODIST_CANALPRECIORUTAID,
	    	KEY_PREVENTAPRODUCTODIST_DESCUENTOPRONTOPAGO};
	    
	    public static final String PREVENTAPRODUCTODIST_TABLE_NAME = "tbl_PreventaProductoDist";
	    
	    public static final String PREVENTAPRODUCTODIST_TABLE_CREATE = "CREATE TABLE " + PREVENTAPRODUCTODIST_TABLE_NAME + "("
	    		+ KEY_PREVENTAPRODUCTODIST_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_PREVENTAPRODUCTODIST_PREVENTAPRODUCTOID + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_PREVENTAID + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_PRODUCTOID + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_PROMOCIONID + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_CANTIDAD + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_CANTIDADPAQUETE + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_MONTO + " float NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_DESCUENTO + " float NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_MONTOFINAL + " float NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_ESTADOID + " integer NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_ESTADOSINCRONIZACION + " boolean NOT NULL,"
	    		+ KEY_PREVENTAPRODUCTODIST_COSTO + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTODIST_COSTOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTODIST_PRECIOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTODIST_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTODIST_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTODIST_CANALPRECIORUTAID + " integer NOT NULL, "
	    		+ KEY_PREVENTAPRODUCTODIST_DESCUENTOPRONTOPAGO + " float NOT NULL);";
	    
	    public boolean borrarPreventaProductoDistPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(PREVENTAPRODUCTODIST_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPreventasProductoDist()
	    {
	      Cursor localCursor = obtenerPreventasProductoDist();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPreventaProductoDistPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPreventaProductoDist(ArrayList<PreventaProductoDistWSResult> preventasProductoDist)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_PreventaProductoDist(_preventaProductoId, _preventaId, _productoId, " +
							"_promocionId, _cantidad, _cantidadPaquete, _monto, _descuento, _montoFinal, _estadoId, " +
							"_estadoSincronizacion, _costo, _costoId, _precioId, _descuentoCanal, _descuentoAjuste, " +
							"_canalPrecioRutaId, _descuentoProntoPago) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (PreventaProductoDistWSResult item : preventasProductoDist) {

					stmt.bindLong(1, item.getPreVentaProductoId());
					stmt.bindLong(2, item.getPreVentaId());
					stmt.bindLong(3, item.getProductoId());
					stmt.bindLong(4, item.getPromocionId());
					stmt.bindLong(5, item.getCantidad());
					stmt.bindLong(6, item.getCantidadPaquete());
					stmt.bindDouble(7, item.getMonto());
					stmt.bindDouble(8, item.getDescuento());
					stmt.bindDouble(9, item.getMontoFinal());
					stmt.bindLong(10, 0);
					stmt.bindLong(11,1);
					stmt.bindLong(12,0);
					stmt.bindLong(13, item.getCostoId());
					stmt.bindLong(14, item.getPrecioId());
					stmt.bindDouble(15, item.getDescuentoCanal());
					stmt.bindDouble(16, item.getDescuentoAjuste());
					stmt.bindLong(17, item.getCanalRutaPrecioId());
					stmt.bindDouble(18, item.getDescuentoProntoPago());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public int modificarRegistroPreventaProductoDistPor(int preventaProductoId, int preventaId, int productoId, 
	    													int cantidad, int cantidadPaquete, float monto, 
	    													float descuento, float montoFinal, 
	    													boolean estadoSincronizacion,float costo,int costoId,
	    													int precioId)
	    {
	      String str = "_preventaProductoId=" + preventaProductoId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_precioId", precioId);
	      return this.db.update("tbl_PreventaProductoDist", localContentValues, str, null);
	    }
	    
	    public Cursor obtenerPreventaProductoDistPor(long preventaProductoId)
	    {
	      String str = "_preventaProductoId=" + preventaProductoId;
	      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTODIST_TABLE_NAME, PREVENTAPRODUCTODIST_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoDist()
	    {
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTODIST_TABLE_NAME, PREVENTAPRODUCTODIST_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoDistPor(int preventaId)
	    {
	      String str = "_preventaId=" + preventaId;
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTODIST_TABLE_NAME, PREVENTAPRODUCTODIST_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasProductoDistPor(int preventaId,int productoId)
	    {
	      String str = "_preventaId=" + preventaId + " and _productoId = " + productoId;
	      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTODIST_TABLE_NAME, PREVENTAPRODUCTODIST_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //VENTA//
	    public static final String KEY_VENTA_ROW_ID = "_id";
	    public static final String KEY_VENTA_VENTAIDSERVER = "_ventaIdServer";
	    public static final String KEY_VENTA_DIA = "_dia";
	    public static final String KEY_VENTA_MES = "_mes";
	    public static final String KEY_VENTA_ANIO = "_anio";
	    public static final String KEY_VENTA_CLIENTEID = "_clienteId";
	    public static final String KEY_VENTA_DISTRIBUIDORID = "_distribuidorId";
	    public static final String KEY_VENTA_MONTO = "_monto";
	    public static final String KEY_VENTA_DESCUENTO = "_descuento";
	    public static final String KEY_VENTA_MONTOFINAL = "_montoFinal";
	    public static final String KEY_VENTA_PREVENTAID = "_preventaId";
	    public static final String KEY_VENTA_TIPOPAGOID = "_tipoPagoId";
	    public static final String KEY_VENTA_LATITUD = "_latitud";
	    public static final String KEY_VENTA_LONGITUD = "_longitud";	
	    public static final String KEY_VENTA_DIFERENCIA = "_diferencia";	    
	    public static final String KEY_VENTA_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    public static final String KEY_VENTA_HORA = "_hora";
	    public static final String KEY_VENTA_MINUTO = "_minuto";
	    public static final String KEY_VENTA_OBSERVACION = "_observacion";
	    public static final String KEY_VENTA_APLICARBONIFICACION = "_aplicarBonificacion";
	    public static final String KEY_VENTA_DOSIFICACIONID = "_dosificacionId";
	    public static final String KEY_VENTA_TIPONIT = "_tipoNit";
	    public static final String KEY_VENTA_DESCUENTOCANAL = "_descuentoCanal";
	    public static final String KEY_VENTA_DESCUENTOAJUSTE = "_descuentoAjuste";
	    public static final String KEY_VENTA_PRONTOPAGOID = "_prontoPagoId";
	    public static final String KEY_VENTA_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    public static final String KEY_VENTA_DESCUENTOOBJETIVO = "_descuentoObjetivo";
	    public static final String KEY_VENTA_FORMAPAGOID = "_formaPagoId";
	    public static final String KEY_VENTA_CODTRANSFERENCIA = "_codTransferencia";
	    public static final String KEY_VENTA_FROMAPK = "_fromApk";
	    public static final String KEY_VENTA_FROMSHOPP = "_fromShopp";
	    
	    public static final int COL_VENTA_ROW_ID = 0;
	    public static final int COL_VENTA_VENTAIDSERVER = 1;
	    public static final int COL_VENTA_DIA = 2;
	    public static final int COL_VENTA_MES = 3;
	    public static final int COL_VENTA_ANIO = 4;
	    public static final int COL_VENTA_CLIENTEID = 5;
	    public static final int COL_VENTA_DISTRIBUIDORID = 6;
	    public static final int COL_VENTA_MONTO = 7;
	    public static final int COL_VENTA_DESCUENTO = 8;
	    public static final int COL_VENTA_MONTOFINAL = 9;
	    public static final int COL_VENTA_PREVENTAID = 10;
	    public static final int COL_VENTA_TIPOPAGOID = 11;
	    public static final int COL_VENTA_LATITUD = 12;
	    public static final int COL_VENTA_LONGITUD = 13;
	    public static final int COL_VENTA_DIFERENCIA = 14;
	    public static final int COL_VENTA_ESTADOSINCRONIZACION = 15;
	    public static final int COL_VENTA_HORA = 16;
	    public static final int COL_VENTA_MINUTO = 17;
	    public static final int COL_VENTA_OBSERVACION = 18;
	    public static final int COL_VENTA_APLICARBONIFICACION = 19;
	    public static final int COL_VENTA_DOSIFICACIONID = 20;
	    public static final int COL_VENTA_TIPONIT = 21;
	    public static final int COL_VENTA_DESCUENTOCANAL = 22;
	    public static final int COL_VENTA_DESCUENTOAJUSTE = 23;
	    public static final int COL_VENTA_PRONTOPAGOID = 24;
	    public static final int COL_VENTA_DESCUENTOPRONTOPAGO = 25;
	    public static final int COL_VENTA_DESCUENTOOBJETIVO = 26;
	    public static final int COL_VENTA_FORMAPAGOID = 27;
	    public static final int COL_VENTA_CODTRANSFERENCIA = 28;
	    public static final int COL_VENTA_FROMAPK = 29;
	    public static final int COL_VENTA_FROMSHOPP = 30;
	    
	    
	    public static final String[] VENTA_ALL_KEYS = new String[] {
	    	KEY_VENTA_ROW_ID,KEY_VENTA_VENTAIDSERVER,KEY_VENTA_DIA,KEY_VENTA_MES,KEY_VENTA_ANIO,KEY_VENTA_CLIENTEID,
	    	KEY_VENTA_DISTRIBUIDORID,KEY_VENTA_MONTO,KEY_VENTA_DESCUENTO,KEY_VENTA_MONTOFINAL,KEY_VENTA_PREVENTAID,
	    	KEY_VENTA_TIPOPAGOID,KEY_VENTA_LATITUD,KEY_VENTA_LONGITUD,KEY_VENTA_DIFERENCIA,KEY_VENTA_ESTADOSINCRONIZACION,
	    	KEY_VENTA_HORA,KEY_VENTA_MINUTO,KEY_VENTA_OBSERVACION,KEY_VENTA_APLICARBONIFICACION,KEY_VENTA_DOSIFICACIONID,
	    	KEY_VENTA_TIPONIT, KEY_VENTA_DESCUENTOCANAL, KEY_VENTA_DESCUENTOAJUSTE,
	    	KEY_VENTA_PRONTOPAGOID, KEY_VENTA_DESCUENTOPRONTOPAGO, KEY_VENTA_DESCUENTOOBJETIVO,
	    	KEY_VENTA_FORMAPAGOID, KEY_VENTA_CODTRANSFERENCIA, KEY_VENTA_FROMAPK,
	    	KEY_VENTA_FROMSHOPP};
	    
	    public static final String VENTA_TABLE_NAME = "tbl_Venta";
	    
	    public static final String VENTA_TABLE_CREATE = "CREATE TABLE " + VENTA_TABLE_NAME + "("
	    		+ KEY_VENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_VENTA_VENTAIDSERVER + " integer NOT NULL,"
	    		+ KEY_VENTA_DIA + " integer NOT NULL,"
	    		+ KEY_VENTA_MES + " integer NOT NULL,"
	    		+ KEY_VENTA_ANIO + " integer NOT NULL,"
	    		+ KEY_VENTA_CLIENTEID + " integer NOT NULL,"
	    		+ KEY_VENTA_DISTRIBUIDORID + " integer NOT NULL,"
	    		+ KEY_VENTA_MONTO + " float NOT NULL,"
	    		+ KEY_VENTA_DESCUENTO + " float NOT NULL,"
	    		+ KEY_VENTA_MONTOFINAL + " float NOT NULL,"
	    		+ KEY_VENTA_PREVENTAID + " integer NOT NULL,"
	    		+ KEY_VENTA_TIPOPAGOID + " integer NOT NULL,"
	    		+ KEY_VENTA_LATITUD + " double NOT NULL,"
	    		+ KEY_VENTA_LONGITUD + " double NOT NULL,"
	    		+ KEY_VENTA_DIFERENCIA + " boolean NOT NULL,"
	    		+ KEY_VENTA_ESTADOSINCRONIZACION + " boolean NOT NULL,"
	    		+ KEY_VENTA_HORA + " integer NOT NULL,"
	    		+ KEY_VENTA_MINUTO + " integer NOT NULL, "
	    		+ KEY_VENTA_OBSERVACION + " text NOT NULL, "
	    		+ KEY_VENTA_APLICARBONIFICACION + " boolean NOT NULL, "
	    		+ KEY_VENTA_DOSIFICACIONID + " int NOT NULL , "
	    		+ KEY_VENTA_TIPONIT + " text NOT NULL, "
	    		+ KEY_VENTA_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_VENTA_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_VENTA_PRONTOPAGOID + " int NOT NULL , "
	    		+ KEY_VENTA_DESCUENTOPRONTOPAGO + " float NOT NULL, "
	    		+ KEY_VENTA_DESCUENTOOBJETIVO + " float NOT NULL, "
	    		+ KEY_VENTA_FORMAPAGOID + " integer NOT NULL, "
	    		+ KEY_VENTA_CODTRANSFERENCIA + " text NOT NULL,"
	    		+ KEY_VENTA_FROMAPK + " boolean NOT NULL,"
	    		+ KEY_VENTA_FROMSHOPP + " boolean NOT NULL);";
	    
	    public void borrarVentas()
	    {
	      Cursor localCursor = obtenerVentas();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarVentaPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public boolean borrarVentaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(VENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarVenta(int ventaIdServer, int dia, int mes, int anio, int clienteId, int distribuidorId, float monto, 
	    		float descuento, float montoFinal, int preventaId, int tipoPagoId, double latitud, double longitud, 
	    		boolean diferencia, boolean estadoSincronizacion,int hora,int minuto,String observacion,boolean aplicarBonificacion,
	    		int dosificacionId,String tipoNit, float descuentoCanal, float descuentoAjuste, int prontoPagoId, float descuentoProntoPago,
	    		float descuentoObjetivo, int formaPagoId, String codTransferencia, boolean fromApk, boolean fromShopp)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      localContentValues.put("_dia", dia);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_distribuidorId", distribuidorId);
	      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_tipoPagoId", tipoPagoId);
	      localContentValues.put("_latitud", latitud);
	      localContentValues.put("_longitud", longitud);
	      localContentValues.put("_diferencia", diferencia);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      localContentValues.put("_hora", hora);
	      localContentValues.put("_minuto", minuto);
	      localContentValues.put("_observacion", String.valueOf(observacion));
	      localContentValues.put("_aplicarBonificacion", aplicarBonificacion);
	      localContentValues.put("_dosificacionId", dosificacionId);
	      localContentValues.put("_tipoNit", String.valueOf(tipoNit));
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_prontoPagoId", prontoPagoId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      localContentValues.put("_descuentoObjetivo", descuentoObjetivo);
	      localContentValues.put("_formaPagoId", formaPagoId);
	      localContentValues.put("_codTransferencia", codTransferencia);
	      localContentValues.put("_fromApk", fromApk);
	      localContentValues.put("_fromShopp", fromShopp);
	      return this.db.insert(VENTA_TABLE_NAME, null, localContentValues);
	    }

	    public int modificarVenta(int id, int ventaId, boolean estadoSincronizacion)
	    {
	      String str = "_id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaId", ventaId);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.update(VENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarVentaMontosYDescuentos(int id,float monto,float descuento,float montoFinal)
	    {
	      String str = "_id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      return this.db.update(VENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerVentaPorVentaId(long ventaId)
	    {
	      String str = "_ventaId=" + ventaId;
	      Cursor localCursor = this.db.query(true,VENTA_TABLE_NAME, VENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentaPor(long id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,VENTA_TABLE_NAME, VENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentas()
	    {
	      Cursor localCursor = this.db.query(true,VENTA_TABLE_NAME, VENTA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasNoSincronizadas()
	    {
	    	String str = "_estadoSincronizacion = 0";
	    	Cursor localCursor = this.db.query(true,VENTA_TABLE_NAME, VENTA_ALL_KEYS,str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public int modificarVentaIdServer(int rowId, int ventaIdServer)
	    {
	      String str = "_id=" + rowId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      return this.db.update(VENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarSincronizacionVenta(int preventaId, int ventaIdServer, boolean estadoSincronizacion)
	    {
	      String str = "_preventaId=" + preventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.update(VENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarVentaSincronizacion(int ventaId, boolean estadoSincronizacion)
	    {
	      String str = "_id=" + ventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.update(VENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerVentaPorClienteId(long clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,VENTA_TABLE_NAME, VENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public int modificarVentaClienteIdPorIdYClienteId(int Id, int clienteId)
	    {
	      String str = "_clienteId=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteId", clienteId);
	      return this.db.update(VENTA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerVentasPorClienteId(long clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,VENTA_TABLE_NAME, VENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //VENTAPRODUCTO//
	    public static final String KEY_VENTAPRODUCTO_ROW_ID = "_id";
	    public static final String KEY_VENTAPRODUCTO_VENTAID = "_ventaId";
	    public static final String KEY_VENTAPRODUCTO_PRODUCTOID = "_productoId";
	    public static final String KEY_VENTAPRODUCTO_PROMOCIONID = "_promocionId";
	    public static final String KEY_VENTAPRODUCTO_CANTIDAD = "_cantidad";
	    public static final String KEY_VENTAPRODUCTO_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_VENTAPRODUCTO_MONTO = "_monto";
	    public static final String KEY_VENTAPRODUCTO_DESCUENTO = "_descuento";
	    public static final String KEY_VENTAPRODUCTO_MONTOFINAL = "_montoFinal";
	    public static final String KEY_VENTAPRODUCTO_MOTIVOID = "_motivoId";
	    public static final String KEY_VENTAPRODUCTO_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    public static final String KEY_VENTAPRODUCTO_COSTO = "_costo";
	    public static final String KEY_VENTAPRODUCTO_COSTOID = "_costoId";
	    public static final String KEY_VENTAPRODUCTO_PRECIOID = "_precioId";
	    public static final String KEY_VENTAPRODUCTO_DESCUENTOCANAL = "_descuentoCanal";
		public static final String KEY_VENTAPRODUCTO_DESCUENTOAJUSTE = "_descuentoAjuste";
		public static final String KEY_VENTAPRODUCTO_CANALPRECIORUTAID = "_canalPrecioRutaId";
		public static final String KEY_VENTAPRODUCTO_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    
	    public static final int COL_VENTAPRODUCTO_ROW_ID = 0;
	    public static final int COL_VENTAPRODUCTO_VENTAID = 1;
	    public static final int COL_VENTAPRODUCTO_PRODUCTOID = 2;
	    public static final int COL_VENTAPRODUCTO_PROMOCIONID = 3;
	    public static final int COL_VENTAPRODUCTO_CANTIDAD = 4;
	    public static final int COL_VENTAPRODUCTO_CANTIDADPAQUETE = 5;
	    public static final int COL_VENTAPRODUCTO_MONTO = 6;
	    public static final int COL_VENTAPRODUCTO_DESCUENTO = 7;
	    public static final int COL_VENTAPRODUCTO_MONTOFINAL = 8;
	    public static final int COL_VENTAPRODUCTO_MOTIVOID = 9;
	    public static final int COL_VENTAPRODUCTO_ESTADOSINCRONIZACION = 10;
	    public static final int COL_VENTAPRODUCTO_COSTO = 11;
	    public static final int COL_VENTAPRODUCTO_COSTOID = 12;
	    public static final int COL_VENTAPRODUCTO_PRECIOID = 13;
	    public static final int COL_VENTAPRODUCTO_DESCUENTOCANAL = 14;
	    public static final int COL_VENTAPRODUCTO_DESCUENTOAJUSTE = 15;
	    public static final int COL_VENTAPRODUCTO_CANALPRECIORUTAID = 16;
	    public static final int COL_VENTAPRODUCTO_DESCUENTOPRONTOPAGO = 17;
	    
	    public static final String[] VENTAPRODUCTO_ALL_KEYS = new String[] {
	    	KEY_VENTAPRODUCTO_ROW_ID,KEY_VENTAPRODUCTO_VENTAID,KEY_VENTAPRODUCTO_PRODUCTOID,
	    	KEY_VENTAPRODUCTO_PROMOCIONID,KEY_VENTAPRODUCTO_CANTIDAD,KEY_VENTAPRODUCTO_CANTIDADPAQUETE,
	    	KEY_VENTAPRODUCTO_MONTO,KEY_VENTAPRODUCTO_DESCUENTO,KEY_VENTAPRODUCTO_MONTOFINAL,
	    	KEY_VENTAPRODUCTO_MOTIVOID,KEY_VENTAPRODUCTO_ESTADOSINCRONIZACION,KEY_VENTAPRODUCTO_COSTO,
	    	KEY_VENTAPRODUCTO_COSTOID, KEY_VENTAPRODUCTO_PRECIOID, KEY_VENTAPRODUCTO_DESCUENTOCANAL,
	    	KEY_VENTAPRODUCTO_DESCUENTOAJUSTE, KEY_VENTAPRODUCTO_CANALPRECIORUTAID, KEY_VENTAPRODUCTO_DESCUENTOPRONTOPAGO};
	    
	    public static final String VENTAPRODUCTO_TABLE_NAME = "tbl_VentaProducto";
	    
	    public static final String VENTAPRODUCTO_TABLE_CREATE = "CREATE TABLE " + VENTAPRODUCTO_TABLE_NAME + "("
	    		+ KEY_VENTAPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_VENTAPRODUCTO_VENTAID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_MONTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_DESCUENTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_MOTIVOID + " int NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_ESTADOSINCRONIZACION + " boolean NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_COSTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_COSTOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_PRECIOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_CANALPRECIORUTAID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTO_DESCUENTOPRONTOPAGO + " float NOT NULL);";

	    public void borrarVentasProducto()
	    {
	      Cursor localCursor = obtenerVentasProducto();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarVentaProductoPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public boolean borrarVentaProductoPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(VENTAPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarVentaProducto(int ventaId, int productoId, int promocionId, int cantidad, 
	    		int cantidadPaquete, float monto, float descuento, float montoFinal, int motivoId, 
	    		boolean estadoSincronizacion,float costo,int costoId,int precioId, float descuentoCanal,
	    		float descuentoAjuste, int canalPrecioRutaId, float descuentoProntoPago)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaId", ventaId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_precioId", precioId);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      return this.db.insert(VENTAPRODUCTO_TABLE_NAME, null, localContentValues);
	    }
	    
	    public int modificarVentaProducto(int rowId, boolean estadoSincronizacion)
	    {
	      String str = "_id=" + rowId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.update(VENTAPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerVentaProductoPor(long id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true, VENTAPRODUCTO_TABLE_NAME, VENTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProducto()
	    {
	      Cursor localCursor = this.db.query(true, VENTAPRODUCTO_TABLE_NAME, VENTAPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProductoPor(int ventaId)
	    {
	      String str = "_ventaId=" + ventaId;
	      Cursor localCursor = this.db.query(true, VENTAPRODUCTO_TABLE_NAME, VENTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProductoNoSincronizados(int ventaId)
	    {
	      String str = "_ventaId=" + ventaId + " and _estadoSincronizacion = 0";
	      Cursor localCursor = this.db.query(true, VENTAPRODUCTO_TABLE_NAME, VENTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //VENTAPRODUCTOTEMP//
	    public static final String KEY_VENTAPRODUCTOTEMP_ROW_ID = "_id";
	    public static final String KEY_VENTAPRODUCTOTEMP_TEMPID = "_tempId";
	    public static final String KEY_VENTAPRODUCTOTEMP_PRODUCTOID = "_productoId";
	    public static final String KEY_VENTAPRODUCTOTEMP_PROMOCIONID = "_promocionId";
	    public static final String KEY_VENTAPRODUCTOTEMP_CANTIDAD = "_cantidad";
	    public static final String KEY_VENTAPRODUCTOTEMP_CANTIDADNUEVA = "_cantidadNueva";
	    public static final String KEY_VENTAPRODUCTOTEMP_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_VENTAPRODUCTOTEMP_CANTIDADPAQUETENUEVA = "_cantidadPaqueteNueva";
	    public static final String KEY_VENTAPRODUCTOTEMP_MONTO = "_monto";
	    public static final String KEY_VENTAPRODUCTOTEMP_MONTONUEVO = "_montoNuevo";
	    public static final String KEY_VENTAPRODUCTOTEMP_DESCUENTO = "_descuento";
	    public static final String KEY_VENTAPRODUCTOTEMP_DESCUENTONUEVO = "_descuentoNuevo";
	    public static final String KEY_VENTAPRODUCTOTEMP_MONTOFINAL = "_montoFinal";
	    public static final String KEY_VENTAPRODUCTOTEMP_MONTOFINALNUEVO = "_montoFinalNuevo";
	    public static final String KEY_VENTAPRODUCTOTEMP_EMPLEADOID = "_empleadoId";
	    public static final String KEY_VENTAPRODUCTOTEMP_CLIENTEID = "_clienteId";
	    public static final String KEY_VENTAPRODUCTOTEMP_MOTIVOID = "_motivoId";
	    public static final String KEY_VENTAPRODUCTOTEMP_COSTO = "_costo";
	    public static final String KEY_VENTAPRODUCTOTEMP_COSTOID = "_costoId";
	    public static final String KEY_VENTAPRODUCTOTEMP_PRECIOID = "_precioId";
	    public static final String KEY_VENTAPRODUCTOTEMP_DESCUENTOCANAL = "_descuentoCanal";
		public static final String KEY_VENTAPRODUCTOTEMP_DESCUENTOAJUSTE = "_descuentoAjuste";
		public static final String KEY_VENTAPRODUCTOTEMP_CANALPRECIORUTAID = "_canalPrecioRutaId";
		public static final String KEY_VENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
		    
	    public static final int COL_VENTAPRODUCTOTEMP_ROW_ID = 0;
	    public static final int COL_VENTAPRODUCTOTEMP_TEMPID = 1;
	    public static final int COL_VENTAPRODUCTOTEMP_PRODUCTOID = 2;
	    public static final int COL_VENTAPRODUCTOTEMP_PROMOCIONID = 3;
	    public static final int COL_VENTAPRODUCTOTEMP_CANTIDAD = 4;
	    public static final int COL_VENTAPRODUCTOTEMP_CANTIDADNUEVA = 5;
	    public static final int COL_VENTAPRODUCTOTEMP_CANTIDADPAQUETE = 6;
	    public static final int COL_VENTAPRODUCTOTEMP_CANTIDADPAQUETENUEVA = 7;
	    public static final int COL_VENTAPRODUCTOTEMP_MONTO = 8;
	    public static final int COL_VENTAPRODUCTOTEMP_MONTONUEVO = 9;
	    public static final int COL_VENTAPRODUCTOTEMP_DESCUENTO = 10;
	    public static final int COL_VENTAPRODUCTOTEMP_DESCUENTONUEVO = 11;
	    public static final int COL_VENTAPRODUCTOTEMP_MONTOFINAL = 12;
	    public static final int COL_VENTAPRODUCTOTEMP_MONTOFINALNUEVO = 13;
	    public static final int COL_VENTAPRODUCTOTEMP_EMPLEADOID = 14;	    
	    public static final int COL_VENTAPRODUCTOTEMP_CLIENTEID = 15;
	    public static final int COL_VENTAPRODUCTOTEMP_MOTIVOID = 16;
	    public static final int COL_VENTAPRODUCTOTEMP_COSTO = 17;
	    public static final int COL_VENTAPRODUCTOTEMP_COSTOID = 18;
	    public static final int COL_VENTAPRODUCTOTEMP_PRECIOID = 19;
	    public static final int COL_VENTAPRODUCTOTEMP_DESCUENTOCANAL = 20;
		public static final int COL_VENTAPRODUCTOTEMP_DESCUENTOAJUSTE = 21;
		public static final int COL_VENTAPRODUCTOTEMP_CANALPRECIORUTAID = 22;
		public static final int COL_VENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO = 23;
		
	    public static final String[] VENTAPRODUCTOTEMP_ALL_KEYS = new String[] { 
	    	KEY_VENTAPRODUCTOTEMP_ROW_ID,KEY_VENTAPRODUCTOTEMP_TEMPID,KEY_VENTAPRODUCTOTEMP_PRODUCTOID,
	    	KEY_VENTAPRODUCTOTEMP_PROMOCIONID,KEY_VENTAPRODUCTOTEMP_CANTIDAD,KEY_VENTAPRODUCTOTEMP_CANTIDADNUEVA,
	    	KEY_VENTAPRODUCTOTEMP_CANTIDADPAQUETE,KEY_VENTAPRODUCTOTEMP_CANTIDADPAQUETENUEVA,KEY_VENTAPRODUCTOTEMP_MONTO,
	    	KEY_VENTAPRODUCTOTEMP_MONTONUEVO,KEY_VENTAPRODUCTOTEMP_DESCUENTO,KEY_VENTAPRODUCTOTEMP_DESCUENTONUEVO,
	    	KEY_VENTAPRODUCTOTEMP_MONTOFINAL,KEY_VENTAPRODUCTOTEMP_MONTOFINALNUEVO,KEY_VENTAPRODUCTOTEMP_EMPLEADOID,
	    	KEY_VENTAPRODUCTOTEMP_CLIENTEID,KEY_VENTAPRODUCTOTEMP_MOTIVOID,KEY_VENTAPRODUCTOTEMP_COSTO,
	    	KEY_VENTAPRODUCTOTEMP_COSTOID,KEY_VENTAPRODUCTOTEMP_PRECIOID, KEY_VENTAPRODUCTOTEMP_DESCUENTOCANAL,
	    	KEY_VENTAPRODUCTOTEMP_DESCUENTOAJUSTE, KEY_VENTAPRODUCTOTEMP_CANALPRECIORUTAID, KEY_VENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO};
	    
	    public static final String VENTAPRODUCTOTEMP_TABLE_NAME = "tbl_VentaProductoTemp";
	    
	    public static final String VENTAPRODUCTOTEMP_TABLE_CREATE = "CREATE TABLE " + VENTAPRODUCTOTEMP_TABLE_NAME + "("
	    		+ KEY_VENTAPRODUCTOTEMP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_VENTAPRODUCTOTEMP_TEMPID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_CANTIDADNUEVA + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_CANTIDADPAQUETENUEVA + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_MONTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_MONTONUEVO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_DESCUENTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_DESCUENTONUEVO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_MONTOFINALNUEVO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_MOTIVOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_COSTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_COSTOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_PRECIOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_CANALPRECIORUTAID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO + " float NOT NULL);";
	    
	    public boolean borrarVentaProductoTempPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(VENTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarVentasProductoTemp()
	    {
	      Cursor localCursor = obtenerVentasProductoTemp();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarVentaProductoTempPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public boolean borrarVentasProductoTempPorEmpleadoIdYClienteId(int empleadoId, int clienteId)
	    {
	      String str = "_empleadoId=" + empleadoId + " and " + "_clienteId" + "=" + clienteId;
	      return this.db.delete(VENTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarVentasProductoTempPorTempId(int tempId)
	    {
	      String str = "_tempId=" + tempId;
	      return this.db.delete(VENTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarVentaProductoTemp(int tempId, int productoId, int promocionId, int cantidad, int cantidadNueva,
	    		int cantidadPaquete, int cantidadPaqueteNueva, float monto, float montoNuevo, float descuento, float descuentoNuevo, 
	    		float montoFinal, float montoFinalNuevo, int empleadoId, int clienteId, int motivoId,float costo,int costoId,
	    		int precioId, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId, float descuentoProntoPago)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_tempId", tempId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadNueva", cantidadNueva);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_cantidadPaqueteNueva", cantidadPaqueteNueva);
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_montoNuevo", montoNuevo);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_descuentoNuevo", descuentoNuevo);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_montoFinalNuevo", montoFinalNuevo);
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_precioId", precioId);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      return this.db.insert(VENTAPRODUCTOTEMP_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerListadoVentaProductoTempPor(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true, VENTAPRODUCTOTEMP_TABLE_NAME, VENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerListadoVentaProductoTempPorClienteYDistribuidor(int clienteId, int empleadoId)
	    {
	      String str = "_clienteId=" + clienteId + " and _empleadoId=" + empleadoId;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMP_TABLE_NAME, VENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentaProductoTempPor(long id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMP_TABLE_NAME, VENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentaProductoTempPorProductoPromocion(int productoId,int promocionId)
	    {
	      String str = "_productoId=" + productoId + " and _promocionId=" + promocionId;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMP_TABLE_NAME, VENTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProductoTemp()
	    {
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMP_TABLE_NAME, VENTAPRODUCTOTEMP_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProductoTempNoConfirmadas(int clienteId,int empleadoId)
	    {
	    	String query =  "SELECT * "
    		  			+ "FROM tbl_ventaProductoTemp AS VPT "
    		  			+ "WHERE VPT._clienteId = " + clienteId + " and VPT._empleadoId = " + empleadoId + " and VPT._id NOT IN("
    		  			+ "SELECT SV._ventaProductoTempRowId "
    		  			+ "FROM tbl_sincronizacionventa AS SV "
    		  			+ "WHERE SV._clienteId= " + clienteId + " and SV._distribuidorId =" + empleadoId + " and SV._confirmado = 0 and SV._tipoSincronizacion = 3)";
	    	
	    	Cursor localCursor = db.rawQuery(query,null);
			  	if (localCursor != null) 
		  		{
			  		localCursor.moveToFirst();
			    }
			  return localCursor;	
	    }
	    
	    //VENTAPRODUCTOTEMPDELETED//
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_ROW_ID = "_id";
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_ROWID = "_rowId";
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_TEMPID = "_tempId";
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_EMPLEADOID = "_empleadoId";
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_CLIENTEID = "_clienteId";
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_MOTIVOID = "_motivoId";
	    public static final String KEY_VENTAPRODUCTOTEMPDELETED_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_ROW_ID = 0;
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_ROWID = 1;
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_TEMPID = 2;
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_EMPLEADOID = 3;
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_CLIENTEID = 4;
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_MOTIVOID = 5;
	    public static final int COL_VENTAPRODUCTOTEMPDELETED_ESTADOSINCRONIZACION = 6;
	    
	    public static final String[] VENTAPRODUCTOTEMPDELETED_ALL_KEYS = new String[] { 
	    	KEY_VENTAPRODUCTOTEMPDELETED_ROW_ID,KEY_VENTAPRODUCTOTEMPDELETED_ROWID,KEY_VENTAPRODUCTOTEMPDELETED_TEMPID,
	    	KEY_VENTAPRODUCTOTEMPDELETED_EMPLEADOID,KEY_VENTAPRODUCTOTEMPDELETED_CLIENTEID,KEY_VENTAPRODUCTOTEMPDELETED_MOTIVOID,
	    	KEY_VENTAPRODUCTOTEMPDELETED_ESTADOSINCRONIZACION };
	    
	    public static final String VENTAPRODUCTOTEMPDELETED_TABLE_NAME = "tbl_VentaProductoTempDeleted";
	    
	    public static final String VENTAPRODUCTOTEMPDELETED_TABLE_CREATE = "CREATE TABLE " + VENTAPRODUCTOTEMPDELETED_TABLE_NAME + "("
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_ROWID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_TEMPID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_MOTIVOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPDELETED_ESTADOSINCRONIZACION + " boolean NOT NULL);";
	     
	    public boolean borrarVentaProductoTempDeletedPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(VENTAPRODUCTOTEMPDELETED_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarVentaProductoTempDeletedPorRowId(long rowId)
	    {
	      String str = "_rowId=" + rowId;
	      return this.db.delete(VENTAPRODUCTOTEMPDELETED_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarVentasProductoTempDeleted()
	    {
	      Cursor localCursor = obtenerVentasProductoTempDeleted();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarVentaProductoTempDeletedPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarVentaProductoTempDeleted(int rowId,int tempId, int empleadoId, int clienteId, int motivoId, 
	    											boolean estadoSincronizacion)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_rowId", rowId);
	      localContentValues.put("_tempId", tempId);
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.insert(VENTAPRODUCTOTEMPDELETED_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerVentaProductoTempDeletedPor(long tempId)
	    {
	      String str = "_tempId=" + tempId;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMPDELETED_TABLE_NAME, VENTAPRODUCTOTEMPDELETED_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentaProductoTempDeletedPorRowId(long rowId)
	    {
	      String str = "_rowId=" + rowId;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMPDELETED_TABLE_NAME, VENTAPRODUCTOTEMPDELETED_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentaProductoTempDeletedPorTempId(long tempId)
	    {
	      String str = "_tempId=" + tempId;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMPDELETED_TABLE_NAME, VENTAPRODUCTOTEMPDELETED_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProductoTempDeleted()
	    {
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMPDELETED_TABLE_NAME, VENTAPRODUCTOTEMPDELETED_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //PREVENTAENTREGA//
	    public static final String KEY_PREVENTAENTREGA_ROW_ID = "_id";
	    public static final String KEY_PREVENTAENTREGA_PREVENTAID = "_preventaId";
	    public static final String KEY_PREVENTAENTREGA_DIA = "_dia";
	    public static final String KEY_PREVENTAENTREGA_MES = "_mes";
	    public static final String KEY_PREVENTAENTREGA_ANIO = "_anio";
	    public static final String KEY_PREVENTAENTREGA_VENTAID = "_ventaId";
	    public static final String KEY_PREVENTAENTREGA_MOTIVOID = "_motivoId";
	    public static final String KEY_PREVENTAENTREGA_TIPOSINCRONIZACION = "_tipoSincronizacion";
	    public static final String KEY_PREVENTAENTREGA_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    
	    public static final int COL_PREVENTAENTREGA_ROW_ID = 0;
	    public static final int COL_PREVENTAENTREGA_PREVENTAID = 1;
	    public static final int COL_PREVENTAENTREGA_DIA = 2;
	    public static final int COL_PREVENTAENTREGA_MES = 3;
	    public static final int COL_PREVENTAENTREGA_ANIO = 4;
	    public static final int COL_PREVENTAENTREGA_VENTAID = 5;
	    public static final int COL_PREVENTAENTREGA_MOTIVOID = 6;
	    public static final int COL_PREVENTAENTREGA_TIPOSINCRONIZACION = 7;
	    public static final int COL_PREVENTAENTREGA_ESTADOSINCRONIZACION = 8;
	    
	    
	    public static final String[] PREVENTAENTREGA_ALL_KEYS = new String[] {
	    	KEY_PREVENTAENTREGA_ROW_ID,KEY_PREVENTAENTREGA_PREVENTAID,KEY_PREVENTAENTREGA_DIA,KEY_PREVENTAENTREGA_MES,
	    	KEY_PREVENTAENTREGA_ANIO, KEY_PREVENTAENTREGA_VENTAID,KEY_PREVENTAENTREGA_MOTIVOID,KEY_PREVENTAENTREGA_TIPOSINCRONIZACION, 
	    	KEY_PREVENTAENTREGA_ESTADOSINCRONIZACION
	    	};
	    
	    public static final String PREVENTAENTREGA_TABLE_NAME = "tbl_PreventaEntrega";
	    
	    public static final String PREVENTAENTREGA_TABLE_CREATE = "CREATE TABLE " + PREVENTAENTREGA_TABLE_NAME + "("
	    		+KEY_PREVENTAENTREGA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PREVENTAENTREGA_PREVENTAID + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_DIA + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_MES + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_ANIO + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_VENTAID + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_MOTIVOID + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_TIPOSINCRONIZACION + " integer NOT NULL, "
	    		+ KEY_PREVENTAENTREGA_ESTADOSINCRONIZACION + " boolean NOT NULL);";
	    
	    public boolean borrarPreventaEntregaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(PREVENTAENTREGA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPreventasEntrega()
	    {
	      Cursor localCursor = obtenerPreventasEntrega();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPreventaEntregaPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPreventaEntrega(int preventaId, int dia, int mes, int anio, int ventaId, int motivoId, 
	    		int tipoSincronizacion, boolean estadoSincronizacion)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_dia", dia);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_ventaId", ventaId);
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_tipoSincronizacion", tipoSincronizacion);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.insert("tbl_PreventaEntrega", null, localContentValues);
	    }
	    
	    public int modificarPreventaEntregaEstadoSincronizacion(int preventaId, boolean estadoSincronizacion)
	    {
	      String str = "_preventaId=" + preventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.update(PREVENTAENTREGA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerPreventaEntregaPor(long id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,PREVENTAENTREGA_TABLE_NAME, PREVENTAENTREGA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasEntrega()
	    {
	      Cursor localCursor = this.db.query(true,PREVENTAENTREGA_TABLE_NAME, PREVENTAENTREGA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPreventasEntregaPorSincronizacion(int estadoSincronizacion)
	    {
	      String str = "_estadoSincronizacion=" + estadoSincronizacion;
	      Cursor localCursor = this.db.query(true,PREVENTAENTREGA_TABLE_NAME, PREVENTAENTREGA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //VENTAPRODUCTOTEMPNOENTREGA//
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_ROW_ID = "_id";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_VENTAID = "_ventaId";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_PRODUCTOID = "_productoId";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_PROMOCIONID = "_promocionId";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_CANTIDAD = "_cantidad";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_MONTO = "_monto";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_DESCUENTO = "_descuento";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_MONTOFINAL = "_montoFinal";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_MOTIVOID = "_motivoId";
	    public static final String KEY_VENTAPRODUCTOTEMPNOENTREGA_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_ROW_ID = 0;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_VENTAID = 1;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_PRODUCTOID = 2;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_PROMOCIONID = 3;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_CANTIDAD = 4;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_CANTIDADPAQUETE = 5;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_MONTO = 6;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_DESCUENTO = 7;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_MONTOFINAL = 8;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_MOTIVOID = 9;
	    public static final int COL_VENTAPRODUCTOTEMPNOENTREGA_ESTADOSINCRONIZACION = 10;
	    
	    public static final String[] VENTAPRODUCTOTEMPNOENTREGA_ALL_KEYS = new String[] { 
	    	KEY_VENTAPRODUCTOTEMPNOENTREGA_ROW_ID,KEY_VENTAPRODUCTOTEMPNOENTREGA_VENTAID,KEY_VENTAPRODUCTOTEMPNOENTREGA_PRODUCTOID,
	    	KEY_VENTAPRODUCTOTEMPNOENTREGA_PROMOCIONID, KEY_VENTAPRODUCTOTEMPNOENTREGA_CANTIDAD,
	    	KEY_VENTAPRODUCTOTEMPNOENTREGA_CANTIDADPAQUETE,KEY_VENTAPRODUCTOTEMPNOENTREGA_MONTO,
	    	KEY_VENTAPRODUCTOTEMPNOENTREGA_DESCUENTO, KEY_VENTAPRODUCTOTEMPNOENTREGA_MONTOFINAL,
	    	KEY_VENTAPRODUCTOTEMPNOENTREGA_MOTIVOID, KEY_VENTAPRODUCTOTEMPNOENTREGA_ESTADOSINCRONIZACION 
	    	};
	    
	    public static final String VENTAPRODUCTOTEMPNOENTREGA_TABLE_NAME = "tbl_VentaProductoTempNoEntrega";
	    
	    public static final String VENTAPRODUCTOTEMPNOENTREGA_TABLE_CREATE = "CREATE TABLE " + VENTAPRODUCTOTEMPNOENTREGA_TABLE_NAME + "("
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_VENTAID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_MONTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_DESCUENTO + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_MOTIVOID + " integer NOT NULL, "
	    		+ KEY_VENTAPRODUCTOTEMPNOENTREGA_ESTADOSINCRONIZACION + " boolean NOT NULL);";
	    
	    public boolean borrarVentasProductoTempNoEntregaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(VENTAPRODUCTOTEMPNOENTREGA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarVentasProductoTempNoEntrega()
	    {
	      Cursor localCursor = obtenerVentasProductoTempNoEntrega();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	        	borrarVentasProductoTempNoEntregaPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarVentaProductoTempNoEntrega(int ventaId, int productoId, int promocionId, int cantidad, int cantidadPaquete, 
	    		float monto, float descuento, float montoFinal, int motivoId, boolean estadoSincronizacion)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaId", ventaId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      return this.db.insert(VENTAPRODUCTOTEMPNOENTREGA_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerVentaProductoTempNoEntregaPor(long id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMPNOENTREGA_TABLE_NAME, VENTAPRODUCTOTEMPNOENTREGA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerVentasProductoTempNoEntrega()
	    {
	      Cursor localCursor = this.db.query(true,VENTAPRODUCTOTEMPNOENTREGA_TABLE_NAME, VENTAPRODUCTOTEMPNOENTREGA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //DEVOLUCIONDISTRIBUIDOR//
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_ROW_ID = "_id";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_ALMACENDEVOLUCIONID = "_almacenDevolucionId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_DISTRIBUIDORID = "_distribuidorId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_ANIO = "_anio";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_MES = "_mes";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_DIA = "_dia";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_ESTADOID = "_estadoId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDOR_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	   
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_ROW_ID = 0;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_ALMACENDEVOLUCIONID = 1;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_DISTRIBUIDORID = 2;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_ANIO = 3;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_MES = 4;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_DIA = 5;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_ESTADOID = 6;
	    public static final int COL_DEVOLUCIONDISTRIBUIDOR_ESTADOSINCRONIZACION = 7;
	    
	    public static final String[] DEVOLUCIONDISTRIBUIDOR_ALL_KEYS = new String[] { 
	    	KEY_DEVOLUCIONDISTRIBUIDOR_ROW_ID,KEY_DEVOLUCIONDISTRIBUIDOR_ALMACENDEVOLUCIONID,
	    	KEY_DEVOLUCIONDISTRIBUIDOR_DISTRIBUIDORID,KEY_DEVOLUCIONDISTRIBUIDOR_ANIO,KEY_DEVOLUCIONDISTRIBUIDOR_MES,
	    	KEY_DEVOLUCIONDISTRIBUIDOR_DIA,KEY_DEVOLUCIONDISTRIBUIDOR_ESTADOID,KEY_DEVOLUCIONDISTRIBUIDOR_ESTADOSINCRONIZACION
	    	};
	    
	    public static final String DEVOLUCIONDISTRIBUIDOR_TABLE_NAME = "tbl_DevolucionDistribuidor";
	    
	    public static final String DEVOLUCIONDISTRIBUIDOR_TABLE_CREATE = "CREATE TABLE " + DEVOLUCIONDISTRIBUIDOR_TABLE_NAME + "("
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_ALMACENDEVOLUCIONID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_DISTRIBUIDORID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_ANIO + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_MES + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_DIA + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_ESTADOID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDOR_ESTADOSINCRONIZACION + " boolean NOT NULL);";

	    public boolean borrarDevolucionDistribuidorPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarDevolucionesDistribuidor()
	    {
	      Cursor localCursor = obtenerDevolucionesDistribuidor();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarDevolucionDistribuidorPor(localCursor.getLong((int)l));
	        } while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarDevolucionDistribuidor(DevolucionDistribuidorWSResult devolucionDistribuidorWSResult)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_DevolucionDistribuidor(_almacenDevolucionId, _distribuidorId, " +
							"_anio, _mes, _dia, _estadoId, _estadoSincronizacion) VALUES (?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				//for (int i = 0; i < devolucionDistribuidor.getPropertyCount(); i++) {
				//	SoapObject soapObject = (SoapObject) devolucionDistribuidor.getProperty(i);

					stmt.bindLong(1,1);
					stmt.bindLong(2, devolucionDistribuidorWSResult.getDistribuidorId());
					stmt.bindLong(3, devolucionDistribuidorWSResult.getAnio());
					stmt.bindLong(4, devolucionDistribuidorWSResult.getMes());
					stmt.bindLong(5, devolucionDistribuidorWSResult.getDia());
					stmt.bindLong(6, devolucionDistribuidorWSResult.getEstadoId());
					stmt.bindLong(7, 0);

					stmt.executeInsert();
					stmt.clearBindings();
				//}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }

	public long insertarDevolucionDistribuidor(int almacenDevolucionId, int distribuidorId, int anio, int mes,
											   int dia, int estadoId, boolean estadoSincronizacion)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_almacenDevolucionId", almacenDevolucionId);
		localContentValues.put("_distribuidorId", distribuidorId);
		localContentValues.put("_anio", anio);
		localContentValues.put("_mes", mes);
		localContentValues.put("_dia", dia);
		localContentValues.put("_estadoId", estadoId);
		localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
		return this.db.insert(DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, null, localContentValues);
	}
	    
	    public int modificarDevolucionDistribuidorId(long id)
	    {
	      String str = "_id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_almacenDevolucionId", id);
	      return this.db.update(DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorPor(int id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, DEVOLUCIONDISTRIBUIDOR_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorPorDistribuidor(int distribuidorId)
	    {
	      String str = "_distribuidorId=" + distribuidorId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, DEVOLUCIONDISTRIBUIDOR_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorPorDistribuidorYFecha(int distribuidorId, int dia, int mes, int anio)
	    {
	      String str = "_distribuidorId=" + distribuidorId + " and " + "_dia" + "=" + dia + " and " + "_mes" + "=" + mes + " and " + "_anio" + "=" + anio;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, DEVOLUCIONDISTRIBUIDOR_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionesDistribuidor()
	    {
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDOR_TABLE_NAME, DEVOLUCIONDISTRIBUIDOR_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //DEVOLUCIONDISTRIBUIDORPRODUCTO//
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ROW_ID = "_id";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ALMACENDEVOLUCIONID = "_almacenDevolucionId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_PRODUCTOID = "_productoId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOUNITARIO = "_saldoUnitario";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOPAQUETE = "_saldoPaquete";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	    
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTO_ROW_ID = 0;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTO_ALMACENDEVOLUCIONID = 1;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTO_PRODUCTOID = 2;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOUNITARIO = 3;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOPAQUETE = 4;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTO_ESTADOSINCRONIZACION = 5;
	  
	    public static final String[] DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS = new String[] {
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ROW_ID, KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ALMACENDEVOLUCIONID,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_PRODUCTOID,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOUNITARIO,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOPAQUETE,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ESTADOSINCRONIZACION};
	    
	    public static final String DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME = "tbl_DevolucionDistribuidorProducto";
	    
	    public static final String DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_CREATE = "CREATE TABLE " + DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME + "("
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ALMACENDEVOLUCIONID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOUNITARIO + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_SALDOPAQUETE + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTO_ESTADOSINCRONIZACION + " boolean NOT NULL);";
	   
	    public Cursor DevolucionDistribuidorProductoGetCantidadUnitariaTotal(int productoId, int almacenDevolucionId)
	    {
	      String str = "_productoId=" + productoId + " and " + "_almacenDevolucionId" + "=" + almacenDevolucionId;
	      Cursor localCursor = this.db.query(true, DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public boolean borrarDevolucionDistribuidorProductoPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarDevolucionesDistribuidorProducto()
	    {
	      Cursor localCursor = obtenerDevolucionesDistribuidorProducto();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarDevolucionDistribuidorProductoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarDevolucionDistribuidorProducto(ArrayList<DevolucionDistribuidorProductoWSResult> devolucionesDistribuidorProducto)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_DevolucionDistribuidorProducto(_almacenDevolucionId, _productoId, _saldoUnitario, " +
							"_saldoPaquete, _estadoSincronizacion) VALUES (?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (DevolucionDistribuidorProductoWSResult item : devolucionesDistribuidorProducto) {

					stmt.bindLong(1,1);
					stmt.bindLong(2, item.getProductoId());
					stmt.bindLong(3, item.getSaldoUnitario());
					stmt.bindLong(4, item.getSaldoPaquete());
					stmt.bindLong(5, 0);

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }

	public long insertarDevolucionDistribuidorProducto(int almacenDevolucionId, int productoId, int saldoUnitario,
													   int saldoPaquete, boolean estadoSincronizacion)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_almacenDevolucionId", almacenDevolucionId);
		localContentValues.put("_productoId", productoId);
		localContentValues.put("_saldoUnitario", saldoUnitario);
		localContentValues.put("_saldoPaquete", saldoPaquete);
		localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
		return this.db.insert("tbl_DevolucionDistribuidorProducto", null, localContentValues);
	}
	    
	    public int modificarCantidadDevolucionDistribuidorProducto(int id, int productoId, int saldoUnitario)
	    {
	      String str = "_id=" + id + " and " + "_productoId" + "=" + productoId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_saldoUnitario", saldoUnitario);
	      return this.db.update(DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarCantidadesDevolucionDistribuidorProducto(int almacenDevolucionId, int productoId, 
	    																int saldoUnitario, int saldoPaquete)
	    {
	      String str = "_almacenDevolucionId=" + almacenDevolucionId + " and _productoId =" + productoId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_saldoUnitario", saldoUnitario);
	      localContentValues.put("_saldoPaquete", saldoPaquete);
	      return this.db.update(DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarDevolucionDistribuidorProductoPorAlmacenYProducto(int almacenDevolucionId, int productoId, 
	    															int  saldoUnitario, int saldoPaquete, boolean estado)
	    {
	      String str = "_almacenDevolucionId=" + almacenDevolucionId + " and _productoId" + "=" + productoId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_cantidad", saldoUnitario);
	      localContentValues.put("_cantidadPaquete", saldoPaquete);
	      localContentValues.put("_estado", estado);
	      return this.db.update(DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerDevolucionesDistribuidorProducto()
	    {
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionesDistribuidorProductoPor(int almacenDevolucionId)
	    {
	      String str = "_almacenDevolucionId=" + almacenDevolucionId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoPorProductoId(int almacenDevolucionId,int productoId)
	    {
	      String str = "_almacenDevolucionId = " + almacenDevolucionId + " and _productoId= " + productoId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerExistenciaDevolucionDistribuidorProducto(int productoId, int conversionProducto, int cantidadSolicitadaEnUnidades)
	    {
	      String str = "_productoId =" + productoId + " and (_saldoPaquete * " + conversionProducto + ") + _saldoUnitario" + ">=" + cantidadSolicitadaEnUnidades;
	     
	      Cursor localCursor = this.db.query(true, DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoPorProductoId(int productoId)
	    {
	      String str = "_productoId= " + productoId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    	    
	    public int modificarCantidadesDevolucionDistribuidorProducto(int productoId, 
				int saldoUnitario, int saldoPaquete)
	    {
	    	String str = "_productoId =" + productoId;
	    	ContentValues localContentValues = new ContentValues();
	    	localContentValues.put("_saldoUnitario", saldoUnitario);
	    	localContentValues.put("_saldoPaquete", saldoPaquete);
	    	return this.db.update(DEVOLUCIONDISTRIBUIDORPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    //PROMOCION//
	    public static final String KEY_PROMOCION_ROW_ID = "_id";
	    public static final String KEY_PROMOCION_PROMOCIONID = "_promocionId";
	    public static final String KEY_PROMOCION_DESCRIPCION = "_descripcion";
	    public static final String KEY_PROMOCION_DESCRIPCION25 = "_descripcion25";
	    public static final String KEY_PROMOCION_ACTIVA = "_activa";
	    public static final String KEY_PROMOCION_PROVEEDORID = "_proveedorId";
	    
	    public static final int COL_PROMOCION_ROW_ID = 0;
	    public static final int COL_PROMOCION_PROMOCIONID = 1;
	    public static final int COL_PROMOCION_DESCRIPCION = 2;
	    public static final int COL_PROMOCION_DESCRIPCION25 = 3;
	    public static final int COL_PROMOCION_ACTIVA = 4;
	    public static final int COL_PROMOCION_PROVEEDORID= 5;
	    
	    public static final String[] PROMOCION_ALL_KEYS = new String[] {
	    	KEY_PROMOCION_ROW_ID,KEY_PROMOCION_PROMOCIONID,KEY_PROMOCION_DESCRIPCION,KEY_PROMOCION_DESCRIPCION25,
	    	KEY_PROMOCION_ACTIVA,KEY_PROMOCION_PROVEEDORID};
	    
	    public static final String PROMOCION_TABLE_NAME = "tbl_Promocion";
	    
	    public static final String PROMOCION_TABLE_CREATE = "CREATE TABLE " + PROMOCION_TABLE_NAME + "("
	    		+ KEY_PROMOCION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PROMOCION_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_PROMOCION_DESCRIPCION + " text NOT NULL, "
	    		+ KEY_PROMOCION_DESCRIPCION25 + " text NOT NULL, "
	    		+ KEY_PROMOCION_ACTIVA + " boolean NOT NULL, "
	    		+ KEY_PROMOCION_PROVEEDORID + " integer NOT NULL);";
	    
	    public boolean borrarPromocionPor(long promocionId)
	    {
	      String str = "_promocionId=" + promocionId;
	      return this.db.delete(PROMOCION_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPromociones()
	    {
	      Cursor localCursor = obtenerPromociones();
	      long l = localCursor.getColumnIndexOrThrow("_promocionId");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPromocionPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPromocion(ArrayList<PromocionWSResult> promociones)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_Promocion(_promocionId, _descripcion, _descripcion25, _activa, _proveedorId) VALUES (?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (PromocionWSResult item : promociones) {

					stmt.bindLong(1, item.getPromocionId());
					stmt.bindString(2, item.getDescripcion());
					stmt.bindString(3, item.getDescripcion25());
					stmt.bindLong(4, item.isActiva()?1:0);
					stmt.bindLong(5, item.getProveedorId());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerPromocionPor(int promocionId)
	    {
	      String str = "_promocionId=" + promocionId;
	      Cursor localCursor = this.db.query(true,PROMOCION_TABLE_NAME, PROMOCION_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromociones()
	    {
	      Cursor localCursor = this.db.query(true, PROMOCION_TABLE_NAME, PROMOCION_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesNoEnPreventaProductoTemp(int clienteId,int empleadoId,int proveedorId,int precioListaId)
	    {
	    	String query = 	"SELECT P._id, P._promocionId, P._descripcion, P._descripcion25, P._activa, P._proveedorId, PPL._precioListaId " +
	    					"FROM tbl_promocion AS P JOIN tbl_promocionPrecioLista AS PPL ON (P._promocionId = PPL._promocionId)" +
	    					"WHERE P._promocionId NOT IN (" +
	    					"SELECT PPT._promocionId FROM tbl_PreventaProductoTemp AS PPT " + 
	    					"WHERE PPT._clienteId = ? AND PPT._empleadoId = ?) AND P._proveedorId = ? AND PPL._precioListaId = ?";
	    	
	    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(clienteId),String.valueOf(empleadoId),
	    									String.valueOf(proveedorId), String.valueOf(precioListaId)});
	    	if (localCursor != null) 
	  		{
	    		localCursor.moveToFirst();
  		    }
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesNoEnAlmacenDevolucionProductoTemp(int clienteId,int empleadoId,int proveedorId,int precioListaId)
	    {
	    	String query = 	"SELECT P._id, P._promocionId, P._descripcion, P._descripcion25, P._activa, P._proveedorId, PPL._precioListaId " +
	    					"FROM tbl_promocion AS P JOIN tbl_promocionPrecioLista AS PPL ON (P._promocionId = PPL._promocionId)" +
	    					"WHERE P._promocionId NOT IN (" +
	    					"SELECT DDPT._promocionId FROM tbl_DevolucionDistribuidorProductoTemp AS DDPT " + 
	    					"WHERE DDPT._clienteId = ? AND DDPT._empleadoId = ?) AND P._proveedorId = ? AND PPL._precioListaId = ?";
	    	
	    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(clienteId),String.valueOf(empleadoId),
	    									String.valueOf(proveedorId), String.valueOf(precioListaId)});
	    	if (localCursor != null) 
	  		{
	    		localCursor.moveToFirst();
  		    }
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesNoEnVentaDirectaProductoTemp(int clienteId,int empleadoId,int proveedorId,int precioListaId)
	    {
	    	String query = 	"SELECT P._id, P._promocionId, P._descripcion, P._descripcion25, P._activa, P._proveedorId, PPL._precioListaId " +
	    					"FROM tbl_promocion AS P JOIN tbl_promocionPrecioLista AS PPL ON (P._promocionId = PPL._promocionId)" +
	    					"WHERE P._promocionId NOT IN (" +
	    					"SELECT VDPT._promocionId FROM tbl_VentaDirectaProductoTemp AS VDPT " + 
	    					"WHERE VDPT._clienteId = ? AND VDPT._empleadoId = ?) AND P._proveedorId = ? AND PPL._precioListaId = ?";
	    	
	    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(clienteId),String.valueOf(empleadoId),
	    									String.valueOf(proveedorId), String.valueOf(precioListaId)});
	    	if (localCursor != null) 
	  		{
	    		localCursor.moveToFirst();
  		    }
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesConSaldoEnAlmacenProducto(int conversion,int cantidad,int productoId,int promocionId)
	    {
	    	String query = 	"SELECT PP._promocionId, PP._productoId, PP._cantidad, PP._cantidadPaquete " +
	    					"FROM tbl_promocionproducto AS PP " +
	    					"JOIN tbl_almacenProducto AS AP ON (PP._productoId = AP._productoId) " +
	    					"WHERE ((AP._saldoPaquete*" + conversion + ")+AP._saldoUnitario >= " +
	    						"((PP._cantidadPaquete*"+ conversion +")+PP._cantidad)*"+ cantidad +") "
								+ "and PP._productoId = "+ productoId + " "
								+ "and PP._promocionId = " + promocionId;
	    	
	    	Cursor localCursor = db.rawQuery(query,new String[]{});
	    	if (localCursor != null) 
	  		{
	    		localCursor.moveToFirst();
  		    }
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesConSaldoEnDevolucionDistribuidorProducto(int conversion,int cantidad,int productoId,int promocionId)
	    {
	    	String query = 	"SELECT PP._promocionId, PP._productoId, PP._cantidad, PP._cantidadPaquete, PP._descuento " +
	    					"FROM tbl_promocionproducto AS PP " +
	    					"JOIN tbl_devolucionDistribuidorProducto AS DDP ON (PP._productoId = DDP._productoId) " +
	    					"WHERE ((DDP._saldoPaquete*" + conversion + ")+DDP._saldoUnitario >= " +
	    						"((PP._cantidadPaquete*"+ conversion +")+PP._cantidad)*"+ cantidad +") "
								+ "and PP._productoId = "+ productoId + " "
								+ "and PP._promocionId = " + promocionId;
	    	
	    	Cursor localCursor = db.rawQuery(query,new String[]{});
	    	if (localCursor != null) 
	  		{
	    		localCursor.moveToFirst();
  		    }
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesNoEnPreventaProducto(int preventaId,int proveedorId,int precioListaId)
	    {
	    	String query = "SELECT P._id, P._promocionId, P._descripcion, P._descripcion25, P._activa, P._proveedorId, PPL._precioListaId " +
	    			        "FROM tbl_promocion AS P JOIN tbl_promocionPrecioLista AS PPL ON (P._promocionId = PPL._promocionId) " +
	    			        "WHERE P._promocionId NOT IN (" +
	    			        "SELECT PP._promocionId FROM tbl_PreventaProducto AS PP " +
	    			        "WHERE PP._preventaId = ? and _promocionId > 0) AND P._proveedorId = ? AND PPL._precioListaId = ?";
	    	
	    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(preventaId),String.valueOf(proveedorId),
	    									String.valueOf(precioListaId)});
	    	if (localCursor != null) 
	  		{
	    		localCursor.moveToFirst();
  		    }
	    	return localCursor;
	    }
	    
	    //PROMOCIONPRODUCTO//
	    public static final String KEY_PROMOCIONPRODUCTO_ROW_ID = "_id";
	    public static final String KEY_PROMOCIONPRODUCTO_PROMOCIONID = "_promocionId";
	    public static final String KEY_PROMOCIONPRODUCTO_PRODUCTOID = "_productoId";
	    public static final String KEY_PROMOCIONPRODUCTO_CANTIDAD = "_cantidad";
	    public static final String KEY_PROMOCIONPRODUCTO_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_PROMOCIONPRODUCTO_DESCUENTO = "_descuento";
	    public static final String KEY_PROMOCIONPRODUCTO_DESCUENTOPAQUETE = "_descuentoPaquete";
	    public static final String KEY_PROMOCIONPRODUCTO_PRECIOLISTAID = "_precioListaId";
	    public static final String KEY_PROMOCIONPRODUCTO_COSTOID = "_costoId";
	    public static final String KEY_PROMOCIONPRODUCTO_PRECIO = "_precio";
	    public static final String KEY_PROMOCIONPRODUCTO_PRECIOPAQUETE = "_precioPaquete";
	    
	    public static final int COL_PROMOCIONPRODUCTO_ROW_ID = 0;
	    public static final int COL_PROMOCIONPRODUCTO_PROMOCIONID = 1;
	    public static final int COL_PROMOCIONPRODUCTO_PRODUCTOID = 2;
	    public static final int COL_PROMOCIONPRODUCTO_CANTIDAD = 3;
	    public static final int COL_PROMOCIONPRODUCTO_CANTIDADPAQUETE = 4;
	    public static final int COL_PROMOCIONPRODUCTO_DESCUENTO = 5;
	    public static final int COL_PROMOCIONPRODUCTO_DESCUENTOPAQUETE = 6;
	    public static final int COL_PROMOCIONPRODUCTO_PRECIOLISTAID = 7;
	    public static final int COL_PROMOCIONPRODUCTO_COSTOID = 8;
	    public static final int COL_PROMOCIONPRODUCTO_PRECIO = 9;
	    public static final int COL_PROMOCIONPRODUCTO_PRECIOPAQUETE = 10;
	    
	    public static final String[] PROMOCIONPRODUCTO_ALL_KEYS = new String[] { 
	    	KEY_PROMOCIONPRODUCTO_ROW_ID,KEY_PROMOCIONPRODUCTO_PROMOCIONID,
	    	KEY_PROMOCIONPRODUCTO_PRODUCTOID,KEY_PROMOCIONPRODUCTO_CANTIDAD,KEY_PROMOCIONPRODUCTO_CANTIDADPAQUETE,
	    	KEY_PROMOCIONPRODUCTO_DESCUENTO,KEY_PROMOCIONPRODUCTO_DESCUENTOPAQUETE,KEY_PROMOCIONPRODUCTO_PRECIOLISTAID,
	    	KEY_PROMOCIONPRODUCTO_COSTOID,KEY_PROMOCIONPRODUCTO_PRECIO,KEY_PROMOCIONPRODUCTO_PRECIOPAQUETE};
	    
	    public static final String PROMOCIONPRODUCTO_TABLE_NAME = "tbl_PromocionProducto";
	    
	    public static final String PROMOCIONPRODUCTO_TABLE_CREATE = "CREATE TABLE " + PROMOCIONPRODUCTO_TABLE_NAME +"("
	    		+ KEY_PROMOCIONPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PROMOCIONPRODUCTO_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_DESCUENTO + " float NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_DESCUENTOPAQUETE + " float NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_PRECIOLISTAID + " integer NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_COSTOID + " integer NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_PRECIO + " float NOT NULL, "
	    		+ KEY_PROMOCIONPRODUCTO_PRECIOPAQUETE + " float NOT NULL);";
	    
	    public boolean borrarPromocionProductoPor(long promocionProductoId)
	    {
	      String str = "_id=" + promocionProductoId;
	      return this.db.delete(PROMOCIONPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPromocionesProducto()
	    {
	      Cursor localCursor = obtenerPromocionesProducto();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarPromocionProductoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPromocionProducto(ArrayList<PromocionProductoWSResult> promocionesProducto)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_PromocionProducto(_promocionId, _productoId, _cantidad, _cantidadPaquete, _descuento, _descuentoPaquete, _precioListaId, _costoId, _precio, _precioPaquete) " +
							"VALUES (?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (PromocionProductoWSResult item : promocionesProducto) {

					stmt.bindLong(1, item.getPromocionId());
					stmt.bindLong(2, item.getProductoId());
					stmt.bindLong(3, item.getCantidad());
					stmt.bindLong(4, item.getCantidadPaquete());
					stmt.bindDouble(5, item.getDescuento());
					stmt.bindDouble(6, item.getDescuentoPaquete());
					stmt.bindLong(7, item.getPrecioListaId());
					stmt.bindLong(8, item.getCostoId());
					stmt.bindDouble(9, item.getPrecio());
					stmt.bindDouble(10, item.getPrecioPaquete());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerPromocionProductoPor(int id)
	    {
	      String str = "_id=" + id;
	      Cursor localCursor = this.db.query(true,PROMOCIONPRODUCTO_TABLE_NAME, PROMOCIONPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesProducto()
	    {
	      Cursor localCursor = this.db.query(true,PROMOCIONPRODUCTO_TABLE_NAME, PROMOCIONPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesProductoPor(int promocionId)
	    {
	      String str = "_promocionId=" + promocionId;
	      Cursor localCursor = this.db.query(true,PROMOCIONPRODUCTO_TABLE_NAME, PROMOCIONPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromocionesProductoPorPromocionIdPrecioListaId(int promocionId,int precioListaId)
	    {
	      String str = "_promocionId=" + promocionId + " and _precioListaId = " + precioListaId;
	      Cursor localCursor = this.db.query(true,PROMOCIONPRODUCTO_TABLE_NAME, PROMOCIONPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //CLIENTE//
	    public static final String KEY_CLIENTE_ROW_ID = "_Id";
	    public static final String KEY_CLIENTE_CLIENTEID = "_clienteId";
	    public static final String KEY_CLIENTE_CODIGO = "_codigo";
	    public static final String KEY_CLIENTE_NOMBRES = "_nombres";
	    public static final String KEY_CLIENTE_PATERNO = "_paterno";
	    public static final String KEY_CLIENTE_MATERNO = "_materno";
	    public static final String KEY_CLIENTE_APELLIDOCASADA = "_apellidoCasada";
	    public static final String KEY_CLIENTE_NOMBRECOMPLETO = "_nombreCompleto";
	    public static final String KEY_CLIENTE_TIENECI = "_tieneCi";
	    public static final String KEY_CLIENTE_CI = "_ci";
	    public static final String KEY_CLIENTE_EXPEDIDOID = "_expedidoId";
	    public static final String KEY_CLIENTE_TIENECELULAR = "_tieneCelular";
	    public static final String KEY_CLIENTE_CELULAR = "_celular";
	    public static final String KEY_CLIENTE_TIPOCALLEID = "_tipoCalleId";
	    public static final String KEY_CLIENTE_DIRECCION = "_direccion";
	    public static final String KEY_CLIENTE_NUMERO = "_numero";
	    public static final String KEY_CLIENTE_REFERENCIA = "_referencia";
	    public static final String KEY_CLIENTE_ENTRETIPOCALLE1ID = "_entreTipoCalle1Id";
	    public static final String KEY_CLIENTE_CALLE1 = "_calle1";
	    public static final String KEY_CLIENTE_ENTRETIPOCALLE2ID = "_entreTipoCalle2Id";
	    public static final String KEY_CLIENTE_CALLE2 = "_calle2";
	    public static final String KEY_CLIENTE_EDIFICIO = "_edificio";
	    public static final String KEY_CLIENTE_EDIFICIOPISO = "_edificioPiso";
	    public static final String KEY_CLIENTE_EDIFICIONUMERO = "_edificioNumero";
	    public static final String KEY_CLIENTE_MANZANO = "_manzano";
	    public static final String KEY_CLIENTE_UV = "_uv";
	    public static final String KEY_CLIENTE_NOMBREFACTURA = "_nombreFactura";
	    public static final String KEY_CLIENTE_NIT = "_nit";
	    public static final String KEY_CLIENTE_RAZONSOCIAL = "_razonSocial";
	    public static final String KEY_CLIENTE_CONTACTO = "_contacto";
	    public static final String KEY_CLIENTE_TIENETELEFONO = "_tieneTelefono";
	    public static final String KEY_CLIENTE_TELEFONO = "_telefono";   
	    public static final String KEY_CLIENTE_PAGINAWEB = "_paginaWeb";
	    public static final String KEY_CLIENTE_EMAIL = "_email";
	    public static final String KEY_CLIENTE_TIPONEGOCIOID = "_tipoNegocioId";
	    public static final String KEY_CLIENTE_ZONAID = "_zonaId";
	    public static final String KEY_CLIENTE_LATITUD = "_latitud";
	    public static final String KEY_CLIENTE_LONGITUD = "_longitud";
	    public static final String KEY_CLIENTE_CREADORID = "_creadorId";
	    public static final String KEY_CLIENTE_LATITUDCREADOR = "_latitudCreador";
	    public static final String KEY_CLIENTE_LONGITUDCREADOR = "_longitudCreador";
	    public static final String KEY_CLIENTE_TIPOPAGOID = "_tipoPagoId";
	    public static final String KEY_CLIENTE_DIASPAGO = "_diasPago";
	    public static final String KEY_CLIENTE_TOPECREDITO = "_topeCredito";
	    public static final String KEY_CLIENTE_DIA = "_dia";
	    public static final String KEY_CLIENTE_MES = "_mes";
	    public static final String KEY_CLIENTE_ANIO = "_anio";
	    public static final String KEY_CLIENTE_HORA = "_hora";	    
	    public static final String KEY_CLIENTE_MINUTO = "_minuto";
	    public static final String KEY_CLIENTE_VERIFICADO = "_verificado";
	    public static final String KEY_CLIENTE_COMPLETO = "_completo";
	    public static final String KEY_CLIENTE_SINCRONIZACION = "_sincronizacion";	    
	    public static final String KEY_CLIENTE_RUTAID = "_rutaId";
	    public static final String KEY_CLIENTE_RUTADIAID = "_rutaDiaId";
	    public static final String KEY_CLIENTE_MERCADOID = "_mercadoId";
	    public static final String KEY_CLIENTE_ACTIVO = "_activo";
	    public static final String KEY_CLIENTE_EDITADO = "_editado";
	    public static final String KEY_CLIENTE_TATID = "_tatId";
	    public static final String KEY_CLIENTE_TIPONIT = "_tipoNit";
	    public static final String KEY_CLIENTE_CLIENTEVISITA = "_clienteVisita";
	    public static final String KEY_CLIENTE_ZONAMERCADOID = "_zonaMercadoId";
	    public static final String KEY_CLIENTE_A = "_a";
	    public static final String KEY_CLIENTE_B = "_b";
	    public static final String KEY_CLIENTE_C = "_c";
	    public static final String KEY_CLIENTE_D = "_d";
	    public static final String KEY_CLIENTE_E = "_e";
	    public static final String KEY_CLIENTE_F = "_f";
	    public static final String KEY_CLIENTE_G = "_g";
	    public static final String KEY_CLIENTE_H = "_h";
	    public static final String KEY_CLIENTE_I = "_i";
	    public static final String KEY_CLIENTE_J = "_j";
	    public static final String KEY_CLIENTE_K = "_k";
	    public static final String KEY_CLIENTE_L = "_l";
	    public static final String KEY_CLIENTE_M = "_m";
	    public static final String KEY_CLIENTE_N = "_n";
	    public static final String KEY_CLIENTE_O = "_o";
	    public static final String KEY_CLIENTE_P = "_p";
	    public static final String KEY_CLIENTE_Q = "_q";
	    public static final String KEY_CLIENTE_R = "_r";
	    public static final String KEY_CLIENTE_SECUENCIAPREVENTA = "_secuenciaPreventa";
	    public static final String KEY_CLIENTE_SECUENCIAVENTA = "_secuenciaVenta";
	    public static final String KEY_CLIENTE_PROVINCIAID = "_provinciaId";
	    public static final String KEY_CLIENTE_PRECIOLISTANOMBREID = "_precioListaNombreId";
	    public static final String KEY_CLIENTE_RUTA = "_ruta";
		public static final String KEY_CLIENTE_TIPOVISITA = "_tipoVisita";
		public static final String KEY_CLIENTE_ZONAVENTAID = "_zonaVentaId";
		public static final String KEY_CLIENTE_CANALRUTAID = "_canalRutaId";
	    
	    public static final int COL_CLIENTE_ROW_ID = 0;
	    public static final int COL_CLIENTE_CLIENTEID = 1;
	    public static final int COL_CLIENTE_CODIGO = 2;
	    public static final int COL_CLIENTE_NOMBRES = 3;
	    public static final int COL_CLIENTE_PATERNO = 4;
	    public static final int COL_CLIENTE_MATERNO = 5;
	    public static final int COL_CLIENTE_APELLIDOCASADA = 6;
	    public static final int COL_CLIENTE_NOMBRECOMPLETO = 7;
	    public static final int COL_CLIENTE_TIENECI = 8;
	    public static final int COL_CLIENTE_CI = 9;
	    public static final int COL_CLIENTE_EXPEDIDOID = 10;
	    public static final int COL_CLIENTE_TIENECELULAR = 11;
	    public static final int COL_CLIENTE_CELULAR = 12;
	    public static final int COL_CLIENTE_TIPOCALLEID = 13;
	    public static final int COL_CLIENTE_DIRECCION = 14;
	    public static final int COL_CLIENTE_NUMERO = 15;
	    public static final int COL_CLIENTE_REFERENCIA = 16;
	    public static final int COL_CLIENTE_ENTRETIPOCALLE1ID = 17;
	    public static final int COL_CLIENTE_CALLE1 = 18;
	    public static final int COL_CLIENTE_ENTRETIPOCALLE2ID = 19;
	    public static final int COL_CLIENTE_CALLE2 = 20;
	    public static final int COL_CLIENTE_EDIFICIO = 21;
	    public static final int COL_CLIENTE_EDIFICIOPISO = 22;
	    public static final int COL_CLIENTE_EDIFICIONUMERO = 23;
	    public static final int COL_CLIENTE_MANZANO = 24;
	    public static final int COL_CLIENTE_UV = 25;
	    public static final int COL_CLIENTE_NOMBREFACTURA = 26;
	    public static final int COL_CLIENTE_NIT = 27;	    
	    public static final int COL_CLIENTE_RAZONSOCIAL = 28;
	    public static final int COL_CLIENTE_CONTACTO = 29;
	    public static final int COL_CLIENTE_TIENETELEFONO = 30;
	    public static final int COL_CLIENTE_TELEFONO = 31;
	    public static final int COL_CLIENTE_PAGINAWEB = 32;
	    public static final int COL_CLIENTE_EMAIL = 33;
	    public static final int COL_CLIENTE_TIPONEGOCIOID = 34;
	    public static final int COL_CLIENTE_ZONAID = 35;
	    public static final int COL_CLIENTE_LATITUD = 36;
	    public static final int COL_CLIENTE_LONGITUD = 37;
	    public static final int COL_CLIENTE_CREADORID = 38;
	    public static final int COL_CLIENTE_LALITUDCREADOR = 39;
	    public static final int COL_CLIENTE_LONGITUDCREADOR = 40;
	    public static final int COL_CLIENTE_TIPOPAGOID = 41;
	    public static final int COL_CLIENTE_DIASPAGO = 42;
	    public static final int COL_CLIENTE_TOPECREDITO = 43;
	    public static final int COL_CLIENTE_DIA = 44;
	    public static final int COL_CLIENTE_MES = 45;
	    public static final int COL_CLIENTE_ANIO = 46;
	    public static final int COL_CLIENTE_HORA = 47;
	    public static final int COL_CLIENTE_MINUTO = 48;
	    public static final int COL_CLIENTE_VERIFICADO = 49;
	    public static final int COL_CLIENTE_COMPLETO = 50;    
	    public static final int COL_CLIENTE_SINCRONIZACION = 51;
	    public static final int COL_CLIENTE_RUTAID = 52;
	    public static final int COL_CLIENTE_RUTADIAID = 53;
	    public static final int COL_CLIENTE_MERCADOID = 54;
	    public static final int COL_CLIENTE_ACTIVO = 55;
	    public static final int COL_CLIENTE_EDITADO = 56;
	    public static final int COL_CLIENTE_TATID = 57;
	    public static final int COL_CLIENTE_TIPONIT = 58;
	    public static final int COL_CLIENTE_CLIENTEVISITA = 59;
	    public static final int COL_CLIENTE_ZONAMERCADOID = 60;
	    public static final int COL_CLIENTE_A = 61;
	    public static final int COL_CLIENTE_B = 62;
	    public static final int COL_CLIENTE_C = 63;
	    public static final int COL_CLIENTE_D = 64;
	    public static final int COL_CLIENTE_E = 65;
	    public static final int COL_CLIENTE_F = 66;
	    public static final int COL_CLIENTE_G = 67;
	    public static final int COL_CLIENTE_H = 68;
	    public static final int COL_CLIENTE_I = 69;
	    public static final int COL_CLIENTE_J = 70;
	    public static final int COL_CLIENTE_K = 71;
	    public static final int COL_CLIENTE_L = 72;
	    public static final int COL_CLIENTE_M = 73;
	    public static final int COL_CLIENTE_N = 74;
	    public static final int COL_CLIENTE_O = 75;
	    public static final int COL_CLIENTE_P = 76;
	    public static final int COL_CLIENTE_Q = 77;
	    public static final int COL_CLIENTE_R = 78;
	    public static final int COL_CLIENTE_SECUENCIAPREVENTA = 79;
	    public static final int COL_CLIENTE_SECUENCIAVENTA = 80;
	    public static final int COL_CLIENTE_PROVINCIAID = 81;
	    public static final int COL_CLIENTE_PRECIOLISTANOMBREID = 82;
	    public static final int COL_CLIENTE_RUTA = 83;
	    public static final int COL_CLIENTE_TIPOVISITA = 84;
	    public static final int COL_CLIENTE_ZONAVENTAID = 85;
	    public static final int COL_CLIENTE_CANALRUTAID = 86;
	    
	    public static final String[] CLIENTE_ALL_KEYS = new String[] {
	    	KEY_CLIENTE_ROW_ID,KEY_CLIENTE_CLIENTEID,KEY_CLIENTE_CODIGO,KEY_CLIENTE_NOMBRES,
	    	KEY_CLIENTE_PATERNO,KEY_CLIENTE_MATERNO,KEY_CLIENTE_APELLIDOCASADA,KEY_CLIENTE_NOMBRECOMPLETO,
	    	KEY_CLIENTE_TIENECI,KEY_CLIENTE_CI,KEY_CLIENTE_EXPEDIDOID,KEY_CLIENTE_TIENECELULAR,KEY_CLIENTE_CELULAR,
	    	KEY_CLIENTE_TIPOCALLEID,KEY_CLIENTE_DIRECCION,KEY_CLIENTE_NUMERO,KEY_CLIENTE_REFERENCIA,
	    	KEY_CLIENTE_ENTRETIPOCALLE1ID,KEY_CLIENTE_CALLE1,KEY_CLIENTE_ENTRETIPOCALLE2ID,KEY_CLIENTE_CALLE2,
	    	KEY_CLIENTE_EDIFICIO,KEY_CLIENTE_EDIFICIOPISO,KEY_CLIENTE_EDIFICIONUMERO,KEY_CLIENTE_MANZANO,
	    	KEY_CLIENTE_UV,KEY_CLIENTE_NOMBREFACTURA,KEY_CLIENTE_NIT,KEY_CLIENTE_RAZONSOCIAL,KEY_CLIENTE_CONTACTO,
	    	KEY_CLIENTE_TIENETELEFONO,KEY_CLIENTE_TELEFONO,KEY_CLIENTE_PAGINAWEB,KEY_CLIENTE_EMAIL,
	    	KEY_CLIENTE_TIPONEGOCIOID,KEY_CLIENTE_ZONAID,KEY_CLIENTE_LATITUD,KEY_CLIENTE_LONGITUD,
	    	KEY_CLIENTE_CREADORID,KEY_CLIENTE_LATITUDCREADOR,KEY_CLIENTE_LONGITUDCREADOR,KEY_CLIENTE_TIPOPAGOID,
	    	KEY_CLIENTE_DIASPAGO,KEY_CLIENTE_TOPECREDITO,KEY_CLIENTE_DIA,KEY_CLIENTE_MES,KEY_CLIENTE_ANIO,
	    	KEY_CLIENTE_HORA,KEY_CLIENTE_MINUTO,KEY_CLIENTE_VERIFICADO,KEY_CLIENTE_COMPLETO,KEY_CLIENTE_SINCRONIZACION, 
	    	KEY_CLIENTE_RUTAID,KEY_CLIENTE_RUTADIAID,KEY_CLIENTE_MERCADOID,KEY_CLIENTE_ACTIVO,KEY_CLIENTE_EDITADO,
	    	KEY_CLIENTE_TATID,KEY_CLIENTE_TIPONIT,KEY_CLIENTE_CLIENTEVISITA,
	    	KEY_CLIENTE_ZONAMERCADOID,KEY_CLIENTE_A,KEY_CLIENTE_B,
	    	KEY_CLIENTE_C,KEY_CLIENTE_D,KEY_CLIENTE_E,
	    	KEY_CLIENTE_F,KEY_CLIENTE_G,KEY_CLIENTE_H,
	    	KEY_CLIENTE_I,KEY_CLIENTE_J,KEY_CLIENTE_K,
	    	KEY_CLIENTE_L,KEY_CLIENTE_M,KEY_CLIENTE_N,
	    	KEY_CLIENTE_O,KEY_CLIENTE_P,KEY_CLIENTE_Q,
	    	KEY_CLIENTE_R,KEY_CLIENTE_SECUENCIAPREVENTA,KEY_CLIENTE_SECUENCIAVENTA,
	    	KEY_CLIENTE_PROVINCIAID,KEY_CLIENTE_PRECIOLISTANOMBREID,KEY_CLIENTE_RUTA,
	    	KEY_CLIENTE_TIPOVISITA,KEY_CLIENTE_ZONAVENTAID,KEY_CLIENTE_CANALRUTAID};
	    
	    public static final String CLIENTE_TABLE_NAME = "tbl_Cliente";
	    
	    public static final String CLIENTE_TABLE_CREATE = "CREATE TABLE " + CLIENTE_TABLE_NAME + "("
	    		+ KEY_CLIENTE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_CLIENTE_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_CODIGO + " text , "
	    		+ KEY_CLIENTE_NOMBRES + " text NOT NULL, "
	    		+ KEY_CLIENTE_PATERNO + " text NOT NULL, "
	    		+ KEY_CLIENTE_MATERNO + " text NOT NULL, "
	    		+ KEY_CLIENTE_APELLIDOCASADA + " text , "
	    		+ KEY_CLIENTE_NOMBRECOMPLETO + " text, "
	    		+ KEY_CLIENTE_TIENECI + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_CI + " text , "
	    		+ KEY_CLIENTE_EXPEDIDOID + " text NOT NULL, "
	    		+ KEY_CLIENTE_TIENECELULAR + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_CELULAR + " text , "
	    		+ KEY_CLIENTE_TIPOCALLEID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_DIRECCION + " text , "
	    		+ KEY_CLIENTE_NUMERO + " text , "
	    		+ KEY_CLIENTE_REFERENCIA + " text , "
	    		+ KEY_CLIENTE_ENTRETIPOCALLE1ID + " integer NOT NULL , "
	    		+ KEY_CLIENTE_CALLE1 + " text , "
	    		+ KEY_CLIENTE_ENTRETIPOCALLE2ID + " integer NOT NULL , "
	    		+ KEY_CLIENTE_CALLE2 + " text , "
	    		+ KEY_CLIENTE_EDIFICIO + " text , "
	    		+ KEY_CLIENTE_EDIFICIOPISO + " text , "
	    		+ KEY_CLIENTE_EDIFICIONUMERO + " text , "
	    		+ KEY_CLIENTE_MANZANO + " text , "
	    		+ KEY_CLIENTE_UV + " text , "
	    		+ KEY_CLIENTE_NOMBREFACTURA + " text , "
	    		+ KEY_CLIENTE_NIT + " text , "
	    		+ KEY_CLIENTE_RAZONSOCIAL + " text , "
	    		+ KEY_CLIENTE_CONTACTO + " text , "
	    		+ KEY_CLIENTE_TIENETELEFONO + " boolean , "
	    		+ KEY_CLIENTE_TELEFONO + " text , "
	    		+ KEY_CLIENTE_PAGINAWEB + " text , "
	    		+ KEY_CLIENTE_EMAIL + " text , "
	    		+ KEY_CLIENTE_TIPONEGOCIOID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_ZONAID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_LATITUD + " double , "
	    		+ KEY_CLIENTE_LONGITUD + " double , "
	    		+ KEY_CLIENTE_CREADORID + " integer , "
	    		+ KEY_CLIENTE_LATITUDCREADOR + " double , "
	    		+ KEY_CLIENTE_LONGITUDCREADOR + " double , "
	    		+ KEY_CLIENTE_TIPOPAGOID + " integer  , "
	    		+ KEY_CLIENTE_DIASPAGO + " integer , "
	    		+ KEY_CLIENTE_TOPECREDITO + " float , "
	    		+ KEY_CLIENTE_DIA + " integer NOT NULL, "
	    		+ KEY_CLIENTE_MES + " integer NOT NULL, "
	    		+ KEY_CLIENTE_ANIO + " integer NOT NULL, "
	    		+ KEY_CLIENTE_HORA + " integer NOT NULL, "
	    		+ KEY_CLIENTE_MINUTO + " integer NOT NULL, "
	    		+ KEY_CLIENTE_VERIFICADO + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_COMPLETO + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_SINCRONIZACION + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_RUTAID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_RUTADIAID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_MERCADOID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_ACTIVO + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_EDITADO + " boolean NOT NULL,"
	    		+ KEY_CLIENTE_TATID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_TIPONIT + " text NOT NULL,"
	    		+ KEY_CLIENTE_CLIENTEVISITA + " boolean NOT NULL, "
	    		+ KEY_CLIENTE_ZONAMERCADOID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_A + " integer NOT NULL, "
	    		+ KEY_CLIENTE_B + " integer NOT NULL, "
	    		+ KEY_CLIENTE_C + " integer NOT NULL, "
	    		+ KEY_CLIENTE_D + " integer NOT NULL, "
	    		+ KEY_CLIENTE_E + " integer NOT NULL, "
	    		+ KEY_CLIENTE_F + " integer NOT NULL, "
	    		+ KEY_CLIENTE_G + " integer NOT NULL, "
	    		+ KEY_CLIENTE_H + " integer NOT NULL, "
	    		+ KEY_CLIENTE_I + " integer NOT NULL, "
	    		+ KEY_CLIENTE_J + " integer NOT NULL, "
	    		+ KEY_CLIENTE_K + " integer NOT NULL, "
	    		+ KEY_CLIENTE_L + " integer NOT NULL, "
	    		+ KEY_CLIENTE_M + " integer NOT NULL, "
	    		+ KEY_CLIENTE_N + " integer NOT NULL, "
	    		+ KEY_CLIENTE_O + " integer NOT NULL, "
	    		+ KEY_CLIENTE_P + " integer NOT NULL, "
	    		+ KEY_CLIENTE_Q + " integer NOT NULL, "
	    		+ KEY_CLIENTE_R + " integer NOT NULL, "
	    		+ KEY_CLIENTE_SECUENCIAPREVENTA + " float NOT NULL, "
	    		+ KEY_CLIENTE_SECUENCIAVENTA + " float NOT NULL ,"
	    		+ KEY_CLIENTE_PROVINCIAID + " integer NOT NULL ,"
	    		+ KEY_CLIENTE_PRECIOLISTANOMBREID + " integer NOT NULL ,"
	    		+ KEY_CLIENTE_RUTA + " text NOT NULL ,"
	    		+ KEY_CLIENTE_TIPOVISITA + " text NOT NULL ,"
	    		+ KEY_CLIENTE_ZONAVENTAID + " integer NOT NULL, "
	    		+ KEY_CLIENTE_CANALRUTAID + " integer NOT NULL);";
	    
	    public boolean borrarClientePor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(CLIENTE_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarClientePorClienteId(long clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      return this.db.delete(CLIENTE_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarClientes()
	    {
	      Cursor localCursor = obtenerClientes();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarClientePor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarCliente(ArrayList<ClienteWSResult> clientes, Fecha theFecha)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_cliente(_clienteId, _codigo, _nombres, _paterno, _materno, _apellidoCasada, _nombreCompleto, _tieneCi, _ci, _expedidoId, " +
							"_tieneCelular, _celular, _tipoCalleId, _direccion, _numero, _referencia, _entreTipoCalle1Id, _calle1, _entreTipoCalle2Id, _calle2, _edificio, " +
							"_edificioPiso, _edificioNumero, _manzano, _uv, _nombreFactura, _nit, _razonSocial, _contacto, _tieneTelefono, _telefono, _paginaWeb, _email, " +
							"_tipoNegocioId, _zonaId, _latitud, _longitud, _creadorId, _latitudCreador, _longitudCreador, _tipoPagoId, _diasPago, _topeCredito, _dia, _mes, " +
							"_anio, _hora, _minuto, _verificado, _completo, _sincronizacion, _rutaId, _rutaDiaId, _mercadoId, _activo, _editado, _tatId, _tipoNit, _clienteVisita, " +
							"_zonaMercadoId, _a, _b, _c, _d, _e, _f, _g, _h, _i, _j, _k, _l, _m, _n, _o, _p, _q, _r, _secuenciaPreventa, _secuenciaVenta, _provinciaId, " +
							"_precioListaNombreId, _ruta, _tipoVisita, _zonaVentaId, _canalRutaId) " +
							"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (ClienteWSResult item : clientes) {

					stmt.bindLong(1, item.getClienteId());
					stmt.bindString(2, item.getCodigo());
					stmt.bindString(3, item.getNombres());
					stmt.bindString(4, item.getPaterno());
					stmt.bindString(5, item.getMaterno());
					stmt.bindString(6, item.getApellidoCasada());
					stmt.bindString(7, item.getNombreCompleto());
					stmt.bindLong(8, item.isTieneCi()?1:0);
					stmt.bindString(9, item.getCi());
					stmt.bindString(10, item.getExpedidoId());
					stmt.bindLong(11, item.isTieneCelular()?1:0);
					stmt.bindString(12, item.getCelular());
					stmt.bindLong(13, item.getTipoCalleId());
					stmt.bindString(14, item.getDireccion());
					stmt.bindString(15, item.getNumero());
					stmt.bindString(16, item.getReferencia());
					stmt.bindLong(17, item.getEntreTipoCalle1Id());
					stmt.bindString(18, item.getCalle1());
					stmt.bindLong(19, item.getEntreTipoCalle2Id());
					stmt.bindString(20, item.getCalle2());
					stmt.bindString(21, item.getEdificio());
					stmt.bindString(22, item.getEdificioPiso());
					stmt.bindString(23, item.getEdificioNumero());
					stmt.bindString(24, item.getManzano());
					stmt.bindString(25, item.getUv());
					stmt.bindString(26, item.getNombreFactura());
					stmt.bindString(27, item.getNit());
					stmt.bindString(28, item.getRazonSocial());
					stmt.bindString(29, item.getContacto());
					stmt.bindLong(30, item.isTieneTelefono()?1:0);
					stmt.bindString(31, item.getTelefono());
					stmt.bindString(32, item.getPaginaWeb());
					stmt.bindString(33, item.getEmail());
					stmt.bindLong(34, item.getTipoNegocioId());
					stmt.bindLong(35, item.getZonaId());
					stmt.bindDouble(36, item.getLatitud());
					stmt.bindDouble(37, item.getLongitud());
					stmt.bindLong(38, item.getCreadorId());
					stmt.bindDouble(39, item.getLatitudCreador());
					stmt.bindDouble(40, item.getLongitudCreador());
					stmt.bindLong(41, item.getTipoPagoId());
					stmt.bindLong(42, item.getDiasPago());
					stmt.bindDouble(43, item.getTopeCredito());
					stmt.bindLong(44, theFecha.get_dia());
					stmt.bindLong(45, theFecha.get_mes());
					stmt.bindLong(46, theFecha.get_anio());
					stmt.bindLong(47, theFecha.get_hora());
					stmt.bindLong(48, theFecha.get_minuto());
					stmt.bindLong(49, item.isVerificado()?1:0);
					stmt.bindLong(50, item.isCompleto()?1:0);
					stmt.bindLong(51,1);
					stmt.bindLong(52,0);
					stmt.bindLong(53,0);
					stmt.bindLong(54, item.getMercadoId());
					stmt.bindLong(55,1);
					stmt.bindLong(56,0);
					stmt.bindLong(57, item.getTatId());
					stmt.bindString(58, item.getTipoNit()==null?"":item.getTipoNit());
					stmt.bindLong(59,0);
					stmt.bindLong(60, item.getZonaMercadoId());
					stmt.bindLong(61, item.isA()?1:0);
					stmt.bindLong(62, item.isB()?1:0);
					stmt.bindLong(63, item.isC()?1:0);
					stmt.bindLong(64, item.isD()?1:0);
					stmt.bindLong(65, item.isE()?1:0);
					stmt.bindLong(66, item.isF()?1:0);
					stmt.bindLong(67, item.isG()?1:0);
					stmt.bindLong(68, item.isH()?1:0);
					stmt.bindLong(69, item.isI()?1:0);
					stmt.bindLong(70, item.isJ()?1:0);
					stmt.bindLong(71, item.isK()?1:0);
					stmt.bindLong(72, item.isL()?1:0);
					stmt.bindLong(73, item.isM()?1:0);
					stmt.bindLong(74, item.isN()?1:0);
					stmt.bindLong(75, item.isO()?1:0);
					stmt.bindLong(76, item.isP()?1:0);
					stmt.bindLong(77, item.isQ()?1:0);
					stmt.bindLong(78, item.isR()?1:0);
					stmt.bindDouble(79, item.getSecuenciaPreventa());
					stmt.bindDouble(80, item.getSecuenciaVenta());
					stmt.bindLong(81, item.getProvinciaId());
					stmt.bindLong(82, item.getPrecioListaNombreId());
					stmt.bindString(83,"");
					stmt.bindString(84,"");
					stmt.bindLong(85,0);
					stmt.bindLong(86, item.getCanalRutaId());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }

	public long insertarCliente(int clienteId, String codigo, String nombres, String paterno,
								String materno, String apellidoCasada, String nombreCompleto, boolean tieneCi,
								String ci, String expedidoId, boolean tieneCelular, String celular,
								int tipoCalleId, String direccion, String numero, String referencia,
								int entreTipoCalle1Id, String calle1, int entreTipoCalle2Id, String calle2,
								String edificio, String edificioPiso, String edificioNumero, String manzano,
								String uv, String nombreFactura, String nit, String razonSocial,
								String contacto, boolean tieneTelefono, String telefono, String paginaWeb,
								String email, int tipoNegocioId, int zonaId,double latitud, double longitud,
								int creadorId, double latitudCreador,double longitudCreador, int tipoPagoId,
								int diasPago, float topeCredito, int dia,int mes, int anio, int hora, int minuto,
								boolean verificado,boolean completo, boolean sincronizacion,int rutaId,int rutaDiaId,
								int mercadoId,boolean activo,boolean editado,int tatId,String tipoNit,boolean clienteVisita,
								int zonaMercadoId,int a,int b,int c,int d,int e,int f,int g,int h,int i,int j,int k,int l,
								int m,int n,int o,int p,int q,int r,float secuenciaPreventa,float secuenciaVenta,int provinciaId,
								int precioListaNombreId,String ruta,String tipoVisita,int zonaVentaId, int canalRutaId)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_clienteId", clienteId);
		localContentValues.put("_codigo", codigo);
		localContentValues.put("_nombres", nombres);
		localContentValues.put("_paterno", paterno);
		localContentValues.put("_materno", materno);
		localContentValues.put("_apellidoCasada", apellidoCasada);
		localContentValues.put("_nombreCompleto", nombreCompleto);
		localContentValues.put("_tieneCi", tieneCi);
		localContentValues.put("_ci", ci);
		localContentValues.put("_expedidoId", expedidoId);
		localContentValues.put("_tieneCelular", tieneCelular);
		localContentValues.put("_celular", celular);
		localContentValues.put("_tipoCalleId", tipoCalleId);
		localContentValues.put("_direccion", direccion);
		localContentValues.put("_numero", numero);
		localContentValues.put("_referencia", referencia);
		localContentValues.put("_entreTipoCalle1Id", entreTipoCalle1Id);
		localContentValues.put("_calle1", calle1);
		localContentValues.put("_entreTipoCalle2Id", entreTipoCalle2Id);
		localContentValues.put("_calle2", calle2);
		localContentValues.put("_edificio", edificio);
		localContentValues.put("_edificioPiso", edificioPiso);
		localContentValues.put("_edificioNumero", edificioNumero);
		localContentValues.put("_manzano", manzano);
		localContentValues.put("_uv", uv);
		localContentValues.put("_nombreFactura", nombreFactura);
		localContentValues.put("_nit", nit);
		localContentValues.put("_razonSocial", razonSocial);
		localContentValues.put("_contacto", contacto);
		localContentValues.put("_tieneTelefono", tieneTelefono);
		localContentValues.put("_telefono", telefono);
		localContentValues.put("_paginaWeb", paginaWeb);
		localContentValues.put("_email", email);
		localContentValues.put("_tipoNegocioId", tipoNegocioId);
		localContentValues.put("_zonaId", zonaId);
		localContentValues.put("_latitud", latitud);
		localContentValues.put("_longitud", longitud);
		localContentValues.put("_creadorId", creadorId);
		localContentValues.put("_latitudCreador", latitudCreador);
		localContentValues.put("_longitudCreador", longitudCreador);
		localContentValues.put("_tipoPagoId", tipoPagoId);
		localContentValues.put("_diasPago", diasPago);
		localContentValues.put("_topeCredito", topeCredito);
		localContentValues.put("_dia", dia);
		localContentValues.put("_mes", mes);
		localContentValues.put("_anio", anio);
		localContentValues.put("_hora", hora);
		localContentValues.put("_minuto", minuto);
		localContentValues.put("_verificado", verificado);
		localContentValues.put("_completo", completo);
		localContentValues.put("_sincronizacion", sincronizacion);
		localContentValues.put("_rutaId", rutaId);
		localContentValues.put("_rutaDiaId", rutaDiaId);
		localContentValues.put("_mercadoId", mercadoId);
		localContentValues.put("_activo", activo);
		localContentValues.put("_editado", editado);
		localContentValues.put("_tatId", tatId);
		localContentValues.put("_tipoNit", String.valueOf(tipoNit));
		localContentValues.put("_clienteVisita", clienteVisita);
		localContentValues.put("_zonaMercadoId", zonaMercadoId);
		localContentValues.put("_a", a);
		localContentValues.put("_b", b);
		localContentValues.put("_c", c);
		localContentValues.put("_d", d);
		localContentValues.put("_e", e);
		localContentValues.put("_f", f);
		localContentValues.put("_g", g);
		localContentValues.put("_h", h);
		localContentValues.put("_i", i);
		localContentValues.put("_j", j);
		localContentValues.put("_k", k);
		localContentValues.put("_l", l);
		localContentValues.put("_m", m);
		localContentValues.put("_n", n);
		localContentValues.put("_o", o);
		localContentValues.put("_p", p);
		localContentValues.put("_q", q);
		localContentValues.put("_r", r);
		localContentValues.put("_secuenciaPreventa", secuenciaPreventa);
		localContentValues.put("_secuenciaVenta", secuenciaVenta);
		localContentValues.put("_provinciaId", provinciaId);
		localContentValues.put("_precioListaNombreId", precioListaNombreId);
		localContentValues.put("_ruta", ruta);
		localContentValues.put("_tipoVisita", tipoVisita);
		localContentValues.put("_zonaVentaId", zonaVentaId);
		localContentValues.put("_canalRutaId", canalRutaId);
		return this.db.insert(CLIENTE_TABLE_NAME, null, localContentValues);
	}
	    
	    public int modificarSincronizacionCliente(int id, int clienteId, boolean sincronizacion)
	    {
	      String str = "_Id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_sincronizacion", sincronizacion);
	      return this.db.update(CLIENTE_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarSincronizacionClienteEditadoPorClienteId(int clienteId, boolean sincronizacion, 
	    														boolean editado)
	    {
	      String str = "_clienteId=" + clienteId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_sincronizacion", sincronizacion);
	      localContentValues.put("_editado", editado);
	      return this.db.update(CLIENTE_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerClientes()
	    {
	      Cursor localCursor = this.db.query(true, CLIENTE_TABLE_NAME, CLIENTE_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesNoSincronizados()
	    {
	      Cursor localCursor = this.db.query(true, CLIENTE_TABLE_NAME, CLIENTE_ALL_KEYS, "_clienteId<= 0 and _sincronizacion= 0", null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesEditadosNoSincronizados()
	    {
	    	String query = "_sincronizacion = 0 and _editado = 1";
	    	Cursor localCursor = this.db.query(true, CLIENTE_TABLE_NAME, CLIENTE_ALL_KEYS, query, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerClientesPor(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,CLIENTE_TABLE_NAME, CLIENTE_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesPorRowId(int id)
	    {
	      String str = "_Id=" + id;
	      Cursor localCursor = this.db.query(true,CLIENTE_TABLE_NAME, CLIENTE_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public int modificarClienteNombrePorClienteId(int clienteId,String nombres,String paterno,String materno,String 
	    											apellidoCasada,String direccion,String referencia)
	    {
	      String str = "_clienteId=" + clienteId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_nombres", String.valueOf(nombres));
	      localContentValues.put("_paterno", String.valueOf(paterno));
	      localContentValues.put("_materno", String.valueOf(materno));
	      localContentValues.put("_apellidoCasada", String.valueOf(apellidoCasada));
	      localContentValues.put("_nombreCompleto",String.valueOf(String.valueOf(nombres) + " " + String.valueOf(paterno) + " " + String.valueOf(materno)));
	      localContentValues.put("_direccion", String.valueOf(direccion));
	      localContentValues.put("_referencia", String.valueOf(referencia));
	      return this.db.update(CLIENTE_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarClienteNombrePorId(int id, String nombres,String paterno,String materno,String apellidoCasada,
	    										String direccion,String referencia)
	    {
	      String str = "_Id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_nombres", String.valueOf(nombres));
	      localContentValues.put("_paterno", String.valueOf(paterno));
	      localContentValues.put("_materno", String.valueOf(materno));
	      localContentValues.put("_apellidoCasada", String.valueOf(apellidoCasada));
	      localContentValues.put("_nombreCompleto",String.valueOf(String.valueOf(nombres) + " " + String.valueOf(paterno) + 
	    		  													" " + String.valueOf(materno)));
	      localContentValues.put("_direccion", String.valueOf(direccion));
	      localContentValues.put("_referencia", String.valueOf(referencia));
	      return this.db.update(CLIENTE_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerPrimerCliente()
	    {
	      Cursor localCursor = this.db.query(true, CLIENTE_TABLE_NAME, CLIENTE_ALL_KEYS, null, null, null, null, null,String.valueOf(1));
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public int modificarClienteDatosPorClienteId(int clienteId, String nombres,String paterno,String materno,String apellidoCasada,
				String telefono,String celular,String direccion,String referencia,double latitud,double longitud,boolean activo,int tipoNegocioId,
				String ci,int provinciaId,int zonaId,int mercadoId,int zonaMercadoId,int tipoPagoId,int precioListaId,String tipoNit,
				String nombreFactura,String nit,int a,int b,int c,int d,int e,int f,int g,int h,int i,int j,int k,int l,
				int m,int n,int o,int p,int q,int r,float secPreventa,float secVenta)
		{
			String str = "_clienteId=" + clienteId;
			ContentValues localContentValues = new ContentValues();
			localContentValues.put("_nombres", String.valueOf(nombres));
			localContentValues.put("_paterno", String.valueOf(paterno));
			localContentValues.put("_materno", String.valueOf(materno));
			localContentValues.put("_apellidoCasada", String.valueOf(apellidoCasada));
			localContentValues.put("_nombreCompleto",String.valueOf(String.valueOf(nombres) + " " + String.valueOf(paterno) + " " + String.valueOf(materno)));
			localContentValues.put("_telefono", String.valueOf(telefono));
			localContentValues.put("_celular", String.valueOf(celular));
			localContentValues.put("_direccion", String.valueOf(direccion));
			localContentValues.put("_referencia", String.valueOf(referencia));
			localContentValues.put("_latitud", latitud);
			localContentValues.put("_longitud", longitud);
			localContentValues.put("_activo", activo);
			localContentValues.put("_tipoNegocioId", tipoNegocioId);
			localContentValues.put("_ci", String.valueOf(ci));
			localContentValues.put("_provinciaId", provinciaId);
			localContentValues.put("_zonaId", zonaId);
			localContentValues.put("_mercadoId", mercadoId);
			localContentValues.put("_zonaMercadoId", zonaMercadoId);
			localContentValues.put("_tipoPagoId", tipoPagoId);
			localContentValues.put("_precioListaNombreId", precioListaId);
			localContentValues.put("_tipoNit", String.valueOf(tipoNit));
			localContentValues.put("_nombreFactura", String.valueOf(nombreFactura));
			localContentValues.put("_nit", String.valueOf(nit));
			localContentValues.put("_a", a);
			localContentValues.put("_b", b);
			localContentValues.put("_c", c);
			localContentValues.put("_d", d);
			localContentValues.put("_e", e);
			localContentValues.put("_f", f);
			localContentValues.put("_g", g);
			localContentValues.put("_h", h);
			localContentValues.put("_i", i);
			localContentValues.put("_j", j);
			localContentValues.put("_k", k);
			localContentValues.put("_l", l);
			localContentValues.put("_m", m);
			localContentValues.put("_n", n);
			localContentValues.put("_o", o);
			localContentValues.put("_p", p);
			localContentValues.put("_q", q);
			localContentValues.put("_r", r);
			localContentValues.put("_secuenciaPreventa", secPreventa);
			localContentValues.put("_secuenciaVenta", secVenta);
			return this.db.update(CLIENTE_TABLE_NAME, localContentValues, str, null);
		}
	    
	    //TIPONEGOCIO//
	    public static final String KEY_TIPONEGOCIO_ROW_ID = "_Id";
	    public static final String KEY_TIPONEGOCIO_CLIENTETIPONEGOCIOID = "_clienteTipoNegocioId";
	    public static final String KEY_TIPONEGOCIO_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_TIPONEGOCIO_ROW_ID = 0;
	    public static final int COL_TIPONEGOCIO_CLIENTETIPONEGOCIOID = 1;
	    public static final int COL_TIPONEGOCIO_DESCRIPCION = 2;
	    
	    public static final String[] TIPONEGOCIO_ALL_KEYS = new String[] {
	    	KEY_TIPONEGOCIO_ROW_ID,KEY_TIPONEGOCIO_CLIENTETIPONEGOCIOID,KEY_TIPONEGOCIO_DESCRIPCION
	    	};
	    public static final String TIPONEGOCIO_TABLE_NAME = "tbl_TipoNegocio";
	    
	    public static final String TIPONEGOCIO_TABLE_CREATE = "CREATE TABLE " + TIPONEGOCIO_TABLE_NAME + "("
	    		+ KEY_TIPONEGOCIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_TIPONEGOCIO_CLIENTETIPONEGOCIOID + " integer NOT NULL, "
	    		+ KEY_TIPONEGOCIO_DESCRIPCION + " text NOT NULL);";
	    
	    public boolean borrarTipoNegocioPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(TIPONEGOCIO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarTiposNegocio()
	    {
	      Cursor localCursor = obtenerTiposNegocio();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarTipoNegocioPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarTipoNegocio(ArrayList<TipoNegocioWSResult> tiposNegocio)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_tipoNegocio(_clienteTipoNegocioId, _descripcion) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (TipoNegocioWSResult item : tiposNegocio) {

					stmt.bindLong(1, item.getTipoId());
					stmt.bindString(2, item.getDescripcion());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerTipoNegocioPor(int clienteTipoNegocioId)
	    {
	      String str = "_clienteTipoNegocioId=" + clienteTipoNegocioId;
	      Cursor localCursor = this.db.query(true,TIPONEGOCIO_TABLE_NAME, TIPONEGOCIO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerTiposNegocio()
	    {
	      Cursor localCursor = this.db.query(true,TIPONEGOCIO_TABLE_NAME, TIPONEGOCIO_ALL_KEYS, null, null, null, null, KEY_TIPONEGOCIO_DESCRIPCION, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //ZONA//
	    public static final String KEY_ZONA_ROW_ID = "_Id";
	    public static final String KEY_ZONA_ZONAID = "_zonaId";
	    public static final String KEY_ZONA_NOMBRE = "_nombre";

	    public static final int COL_ZONA_ROW_ID = 0;
	    public static final int COL_ZONA_ZONAID = 1;
	    public static final int COL_ZONA_NOMBRE = 2;
	    
	    public static final String[] ZONA_ALL_KEYS= new String[] { 
	    	KEY_ZONA_ROW_ID, KEY_ZONA_ZONAID, KEY_ZONA_NOMBRE 
	    	};
	    public static final String ZONA_TABLE_NAME = "tbl_Zona";
	    
	    public static final String ZONA_TABLE_CREATE = "CREATE TABLE " + ZONA_TABLE_NAME + "("
	    		+ KEY_ZONA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_ZONA_ZONAID + " integer NOT NULL, "
	    		+ KEY_ZONA_NOMBRE + " text NOT NULL);";

	    public boolean borrarZonaPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(ZONA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarZonas()
	    {
	      Cursor localCursor = obtenerZonas();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarZonaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarZona(ArrayList<ZonaWSResult> zonas)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_Zona(_zonaId, _nombre) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (ZonaWSResult item : zonas) {

					stmt.bindLong(1, item.getZonaId());
					stmt.bindString(2, item.getNombre());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerZonaPor(int zonaId)
	    {
	      String str = "_zonaId=" + zonaId;
	      Cursor localCursor = this.db.query(true,ZONA_TABLE_NAME, ZONA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerZonas()
	    {
	      Cursor localCursor = this.db.query(true,ZONA_TABLE_NAME, ZONA_ALL_KEYS, null, null, null, null,KEY_ZONA_NOMBRE, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //CLIENTEFOTO//
	    public static final String KEY_CLIENTEFOTO_ROW_ID = "_Id";
	    public static final String KEY_CLIENTEFOTO_CLIENTEIDANDROID = "_clienteIdAndroid";
	    public static final String KEY_CLIENTEFOTO_CLIENTEIDSERVER = "_clienteIdServer";
	    public static final String KEY_CLIENTEFOTO_FOTO = "_foto";
	    public static final String KEY_CLIENTEFOTO_SINCRONIZACION = "_sincronizacion";
	    public static final String KEY_CLIENTEFOTO_FOTOCATEGORIAID = "_fotoCategoriaId";
	    public static final String KEY_CLIENTEFOTO_FOTOIDSERVER = "_fotoIdServer";
	    
	    public static final int COL_CLIENTEFOTO_ROW_ID = 0;
	    public static final int COL_CLIENTEFOTO_CLIENTEIDANDROID = 1;
	    public static final int COL_CLIENTEFOTO_CLIENTEIDSERVER = 2;
	    public static final int COL_CLIENTEFOTO_FOTO = 3;
	    public static final int COL_CLIENTEFOTO_SINCRONIZACION = 4;
	    public static final int COL_CLIENTEFOTO_FOTOCATEGORIAID = 5;
	    public static final int COL_CLIENTEFOTO_FOTOIDSERVER = 6;
	    
	    public static final String[] CLIENTEFOTO_ALL_KEYS = new String[] {
	    	KEY_CLIENTEFOTO_ROW_ID,KEY_CLIENTEFOTO_CLIENTEIDANDROID,KEY_CLIENTEFOTO_CLIENTEIDSERVER,
	    	KEY_CLIENTEFOTO_FOTO, KEY_CLIENTEFOTO_SINCRONIZACION, KEY_CLIENTEFOTO_FOTOCATEGORIAID,
	    	KEY_CLIENTEFOTO_FOTOIDSERVER
	    	};
	    public static final String CLIENTEFOTO_TABLE_NAME = "tbl_ClienteFoto";
	    
	    public static final String CLIENTEFOTO_TABLE_CREATE = "CREATE TABLE " + CLIENTEFOTO_TABLE_NAME + "("
	    		+ KEY_CLIENTEFOTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_CLIENTEFOTO_CLIENTEIDANDROID + " integer NOT NULL, "
	    		+ KEY_CLIENTEFOTO_CLIENTEIDSERVER + " integer NOT NULL, "
	    		+ KEY_CLIENTEFOTO_FOTO + " blob NOT NULL, "
	    		+ KEY_CLIENTEFOTO_SINCRONIZACION + " boolean NOT NULL, "
	    		+ KEY_CLIENTEFOTO_FOTOCATEGORIAID + " integer NOT NULL,"
	    		+ KEY_CLIENTEFOTO_FOTOIDSERVER + " integer NOT NULL);";
	    
	    public boolean borrarClienteFotoPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(CLIENTEFOTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarClienteFotos(int clienteId)
	    {
	      String str = "_clienteIdServer=" + clienteId;
	      return this.db.delete(CLIENTEFOTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarClientesFoto()
	    {
	      Cursor localCursor = obtenerClientesFoto();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarClienteFotoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarClienteFoto(int clienteIdAndroid, int clienteIdServer, byte[] foto, boolean sincronizacion, int fotoCategoriaId, int fotoIdServer)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteIdAndroid", clienteIdAndroid);
	      localContentValues.put("_clienteIdServer", clienteIdServer);
	      localContentValues.put("_foto", foto);
	      localContentValues.put("_sincronizacion", sincronizacion);
	      localContentValues.put("_fotoCategoriaId", fotoCategoriaId);
	      localContentValues.put("_fotoIdServer", fotoIdServer);
	      return this.db.insert(CLIENTEFOTO_TABLE_NAME, null, localContentValues);
	    }
	    
	    public int modificarClienteIdClienteFoto(int clienteIdAndroid, int clienteIdServer)
	    {
	      String str = "_clienteIdAndroid=" + clienteIdAndroid;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteIdServer", clienteIdServer);
	      return this.db.update(CLIENTEFOTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarSincronizacionClienteFoto(int id, boolean _sincronizacion)
	    {
	      String str = "_Id=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_Id", id);
	      localContentValues.put("_sincronizacion", _sincronizacion);
	      return this.db.update(CLIENTEFOTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerClientesFoto()
	    {
	      Cursor localCursor = this.db.query(true,CLIENTEFOTO_TABLE_NAME, CLIENTEFOTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesFotoNoSincronizados()
	    {
	      Cursor localCursor = this.db.query(true,CLIENTEFOTO_TABLE_NAME, CLIENTEFOTO_ALL_KEYS, "_sincronizacion = 0 and _clienteIdServer <> 0", null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesFotoPor(int id)
	    {
	      String str = "_Id=" + id;
	      Cursor localCursor = this.db.query(true,CLIENTEFOTO_TABLE_NAME, CLIENTEFOTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesFotoPorClienteIdAndroid(int clienteIdAndroid)
	    {
	      String str = "_clienteIdAndroid=" + clienteIdAndroid;
	      Cursor localCursor = this.db.query(true,CLIENTEFOTO_TABLE_NAME, CLIENTEFOTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesFotoServer(int clienteId)
	    {
	      String str = "_clienteIdServer = " + clienteId;
	      Cursor localCursor = this.db.query(true,CLIENTEFOTO_TABLE_NAME, CLIENTEFOTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //RUTA//	    
	    public static final String KEY_RUTA_ROW_ID = "_Id";
	    public static final String KEY_RUTA_RUTAID = "_rutaId";
	    public static final String KEY_RUTA_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_RUTA_ROW_ID = 0;
	    public static final int COL_RUTA_RUTAID = 1;
	    public static final int COL_RUTA_DESCRIPCION = 2;
	    
	    public static final String[] RUTA_ALL_KEYS = new String[] {
	    	KEY_RUTA_ROW_ID,KEY_RUTA_RUTAID,KEY_RUTA_DESCRIPCION 
	    	};
	    public static final String RUTA_TABLE_NAME = "tbl_Ruta";
	    
	    public static final String RUTA_TABLE_CREATE = "CREATE TABLE " + RUTA_TABLE_NAME + "("
	    		+ KEY_RUTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_RUTA_RUTAID + " integer NOT NULL, "
	    		+ KEY_RUTA_DESCRIPCION + " text NOT NULL);";
	    
	    public boolean borrarRutaPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(RUTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarRutas()
	    {
	      Cursor localCursor = obtenerRutas();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarRutaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public Cursor obtenerRutaPor(int rutaId)
	    {
	      String str = "_rutaId=" + rutaId;
	      Cursor localCursor = this.db.query(true,RUTA_TABLE_NAME, RUTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerRutas()
	    {
	      Cursor localCursor = this.db.query(true,RUTA_TABLE_NAME, RUTA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //EXPEDIDO//
	    public static final String KEY_EXPEDIDO_ROW_ID = "_Id";
	    public static final String KEY_EXPEDIDO_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_EXPEDIDO_ROW_ID = 0;
	    public static final int COL_EXPEDIDO_DESCRIPCION = 1;
	    
	    public static final String[] EXPEDIDO_ALL_KEYS = new String[] {
	    	KEY_EXPEDIDO_ROW_ID, KEY_EXPEDIDO_DESCRIPCION };
	    
	    public static final String EXPEDIDO_TABLE_NAME = "tbl_Expedido";
	    
	    public static final String EXPEDIDO_TABLE_CREATE = "CREATE TABLE " + EXPEDIDO_TABLE_NAME + "("
	    		+ KEY_EXPEDIDO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_EXPEDIDO_DESCRIPCION + " text NOT NULL);";	    
	    
	    public boolean borrarExpedidoPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(EXPEDIDO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarExpedidos()
	    {
	      Cursor localCursor = obtenerExpedidos();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarExpedidoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarExpedido(ArrayList<ExpedidoWSResult> expedidos)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_Expedido(_descripcion) VALUES (?)"
			);
			try {
				db.beginTransaction();
				for (ExpedidoWSResult item : expedidos) {

					stmt.bindString(1, item.getExpedidoId());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerExpedidos()
	    {
	      Cursor localCursor = this.db.query(true,EXPEDIDO_TABLE_NAME, EXPEDIDO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //TIPOCALLE//
	    public static final String KEY_TIPOCALLE_ROW_ID = "_Id";
	    public static final String KEY_TIPOCALLE_TIPOCALLEID = "_tipoCalleId";
	    public static final String KEY_TIPOCALLE_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_TIPOCALLE_ROW_ID = 0;
	    public static final int COL_TIPOCALLE_TIPOCALLEID = 1;
	    public static final int COL_TIPOCALLE_DESCRIPCION = 2;
	    
	    public static final String[] TIPOCALLE_ALL_KEYS = new String[] {
	    	KEY_TIPOCALLE_ROW_ID,KEY_TIPOCALLE_TIPOCALLEID,KEY_TIPOCALLE_DESCRIPCION
	    	};
	    public static final String TIPOCALLE_TABLE_NAME = "tbl_TipoCalle";
	    
	    public static final String TIPOCALLE_TABLE_CREATE = "CREATE TABLE " + TIPOCALLE_TABLE_NAME + "("
	    		+ KEY_TIPOCALLE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_TIPOCALLE_TIPOCALLEID + " integer NOT NULL, "
	    		+ KEY_TIPOCALLE_DESCRIPCION + " text NOT NULL);";	    
	    
	    public boolean borrarTipoCallePor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(TIPOCALLE_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarTiposCalle()
	    {
	      Cursor localCursor = obtenerTiposCalle();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarTipoCallePor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarTipoCalle(ArrayList<TipoCalleWSResult> tiposCalle)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_TipoCalle(_tipoCalleId, _descripcion) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (TipoCalleWSResult item : tiposCalle) {

					stmt.bindLong(1, item.getTipoCalleId());
					stmt.bindString(2, item.getDescripcion());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerTiposCalle()
	    {
	      Cursor localCursor = this.db.query(true,TIPOCALLE_TABLE_NAME, TIPOCALLE_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //DIASEMANA//
	    public static final String KEY_DIASEMANA_ROW_ID = "_Id";
	    public static final String KEY_DIASEMANA_DIASEMANAID = "_diaSemanaId";
	    public static final String KEY_DIASEMANA_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_DIASEMANA_ROW_ID = 0;
	    public static final int COL_DIASEMANA_DIASEMANAID = 1;
	    public static final int COL_DIASEMANA_DESCRIPCION = 2;
	    
  
	    public static final String[] DIASEMANA_ALL_KEYS = new String[] {
	    	KEY_DIASEMANA_ROW_ID,KEY_DIASEMANA_DIASEMANAID ,KEY_DIASEMANA_DESCRIPCION
	    	};
	    public static final String DIASEMANA_TABLE_NAME = "tbl_DiaSemana";
	    
	    public static final String DIASEMANA_TABLE_CREATE = "CREATE TABLE " + DIASEMANA_TABLE_NAME + "("
	    		+ KEY_DIASEMANA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_DIASEMANA_DIASEMANAID + " integer NOT NULL, "
	    		+ KEY_DIASEMANA_DESCRIPCION + " text NOT NULL);";
	    
	    public boolean borrarDiaSemanaPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(DIASEMANA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarDiasSemana()
	    {
	      Cursor localCursor = obtenerDiasSemana();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarDiaSemanaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarDiaSemana(ArrayList<DiaSemanaWSResult> diasSemana)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_DiaSemana(_diaSemanaId, _descripcion) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (DiaSemanaWSResult item : diasSemana) {

					stmt.bindLong(1, item.getDiaId());
					stmt.bindString(2, item.getDescripcion());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerDiasSemana()
	    {
	      Cursor localCursor = this.db.query(true,DIASEMANA_TABLE_NAME, DIASEMANA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null)
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //LOG//
	    public static final String KEY_LOG_ROW_ID = "_Id";
	    public static final String KEY_LOG_APLICACION = "_aplicacion";
	    public static final String KEY_LOG_ACTIVIDAD = "_actividad";
	    public static final String KEY_LOG_FECHA = "_fecha";
	    public static final String KEY_LOG_TIPOLOG = "_tipoLog";
	    public static final String KEY_LOG_LOG = "_log";
	    
	    public static final int COL_LOG_ROW_ID = 0;
	    public static final int COL_LOG_ROW_APLICACION = 1;
	    public static final int COL_LOG_ROW_ACTIVIDAD = 2;
	    public static final int COL_LOG_ROW_FECHA = 3;
	    public static final int COL_LOG_ROW_TIPOLOG = 4;
	    public static final int COL_LOG_ROW_LOG = 5;
	    
	    public static final String[] LOG_ALL_KEYS = new String[] {
	    	KEY_LOG_ROW_ID,KEY_LOG_APLICACION,KEY_LOG_ACTIVIDAD,
	    	KEY_LOG_FECHA,KEY_LOG_TIPOLOG,KEY_LOG_LOG 
	    	};
	    public static final String LOG_TABLE_NAME = "tbl_Log";
	    
	    public static final String LOG_TABLE_CREATE = "CREATE TABLE " + LOG_TABLE_NAME + "("
	    		+ KEY_LOG_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_LOG_APLICACION + " text NOT NULL, "
	    		+ KEY_LOG_ACTIVIDAD + " text NOT NULL, "
	    		+ KEY_LOG_FECHA + " text NOT NULL, "
	    		+ KEY_LOG_TIPOLOG + " text NOT NULL, "
	    		+ KEY_LOG_LOG + " text NOT NULL);";
	    
	    public boolean borrarLogPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(LOG_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarLogs()
	    {
	      Cursor localCursor = obtenerLogs();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarLogPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarLog(String aplicacion, String actividad, String fecha, String tipoLog, String log)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_aplicacion", aplicacion);
	      localContentValues.put("_actividad", actividad);
	      localContentValues.put("_fecha", fecha);
	      localContentValues.put("_tipoLog", tipoLog);
	      localContentValues.put("_log", log);
	      return this.db.insert(LOG_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerLogs()
	    {
	      Cursor localCursor = this.db.query(true,LOG_TABLE_NAME, LOG_ALL_KEYS, null, null, null, null, "_Id desc", null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //PARAMETROGENERAL//
	    public static final String KEY_PARAMETROGENERAL_ROW_ID = "_Id";
	    public static final String KEY_PARAMETROGENERAL_MARGENMINIMO = "_margenMinimo";
	    public static final String KEY_PARAMETROGENERAL_MARGENMINIMOEMPLEADO = "_margenMinimoEmpleado";
	    public static final String KEY_PARAMETROGENERAL_DESCUENTOPROMOCION = "_descuentoPromocion";
	    public static final String KEY_PARAMETROGENERAL_NIT = "_nit";
	    public static final String KEY_PARAMETROGENERAL_NOMBREEMPRESAFACTURA = "_nombreEmpresaFactura";
	    public static final String KEY_PARAMETROGENERAL_DIRECCIONREPORTE = "_direccionReporte";
	    public static final String KEY_PARAMETROGENERAL_LISTAPRECIOID = "_listaPrecioId";
	    public static final String KEY_PARAMETROGENERAL_TIPOPAGOID = "_tipoPagoId";
	    public static final String KEY_PARAMETROGENERAL_HABILITARTIPOSPAGO = "_habilitarTiposPago";
	    public static final String KEY_PARAMETROGENERAL_FACTURARTODO = "_facturarTodo";
	    public static final String KEY_PARAMETROGENERAL_SINCRONIZARWIFI = "_sincronizarWifi";
	    public static final String KEY_PARAMETROGENERAL_TIPOIMPRESIONFACTURA = "_tipoImpresionFactura";
	    public static final String KEY_PARAMETROGENERAL_MERCADOREQUERIDO = "_mercadoRequerido";
	    public static final String KEY_PARAMETROGENERAL_MONTOCI = "_montoCi";
	    public static final String KEY_PARAMETROGENERAL_MONTOBANCARIZACION = "_montoBancarizacion";
	    public static final String KEY_PARAMETROGENERAL_HABILITARPOP = "_habilitarPop";
	    public static final String KEY_PARAMETROGENERAL_HABILITARVENTADIRECTA = "_habilitarVentaDirecta";
	    public static final String KEY_PARAMETROGENERAL_HABILITARFECHAENTREGA = "_habilitarFechaEntrega";
	    public static final String KEY_PARAMETROGENERAL_MONTONIT = "_montoNit";
	    public static final String KEY_PARAMETROGENERAL_HABILITARCAMBIO = "_habilitarCambio";
	    public static final String KEY_PARAMETROGENERAL_HABILITARMATCHEO = "_habilitarMatcheo";
	    public static final String KEY_PARAMETROGENERAL_EDITARPREVENTAS = "_editarPreventas";
	    public static final String KEY_PARAMETROGENERAL_HABILITARMULTIPLEPREVENTA = "_habilitarMultiplePreventa";
	    public static final String KEY_PARAMETROGENERAL_BLOQUEARCONDICIONTRIBUTARIA = "_bloquearCondicionTributaria";
	    public static final String KEY_PARAMETROGENERAL_MONTOCONDICIONTRIBUTARIA = "_montoCondicionTributaria";
	    public static final String KEY_PARAMETROGENERAL_CAMBIARNIT = "_cambiarNit";
	    public static final String KEY_PARAMETROGENERAL_TEXTOSINNOMBRE = "_textoSinNombre";
	    public static final String KEY_PARAMETROGENERAL_CREDITOSOBRECREDITO = "_creditoSobreCredito";
	    public static final String KEY_PARAMETROGENERAL_RESPETARTIPOPAGO = "_respetarTipoPago";
	    public static final String KEY_PARAMETROGENERAL_MOSTRARALERTAMORA = "_mostrarAlertaMora";
	    public static final String KEY_PARAMETROGENERAL_MODIFICARTIPONEGOCIO = "_modificarTipoNegocio";
	    public static final String KEY_PARAMETROGENERAL_ZONAREQUERIDA = "_zonaRequerida";
	    public static final String KEY_PARAMETROGENERAL_CLIENTEVISITAREQUERIDA = "_clienteVisitaRequerida";
	    public static final String KEY_PARAMETROGENERAL_ZONAMERCADOREQUERIDA = "_zonaMercadoRequerida";
	    public static final String KEY_PARAMETROGENERAL_EDITARCLIENTE = "_editarCliente";
	    public static final String KEY_PARAMETROGENERAL_ACTIVARGPS = "_activarGps";
	    public static final String KEY_PARAMETROGENERAL_DISTANCIACLIENTE = "_distanciaCliente";
	    public static final String KEY_PARAMETROGENERAL_PROVINCIAREQUERIDA = "_provinciaRequerida";
	    public static final String KEY_PARAMETROGENERAL_MOSTRARLISTAPRECIO = "_mostrarListaPrecio";
	    public static final String KEY_PARAMETROGENERAL_MOSTRARTIPOPAGO = "_mostrarTipoPago";
	    public static final String KEY_PARAMETROGENERAL_MOSTRARSECUENCIAVISITA = "_mostrarSecuenciaVisita";
	    public static final String KEY_PARAMETROGENERAL_MOSTRARADICIONARNIT = "_mostrarAdicionarNit";
	    	    	    	    
	    public static final int COL_PARAMETROGENERAL_ROW_ID = 0;
	    public static final int COL_PARAMETROGENERAL_MARGENMINIMO = 1;
	    public static final int COL_PARAMETROGENERAL_MARGENMINIMOEMPLEADO = 2;
	    public static final int COL_PARAMETROGENERAL_DESCUENTOPROMOCION = 3;
	    public static final int COL_PARAMETROGENERAL_NIT = 4;
	    public static final int COL_PARAMETROGENERAL_NOMBREEMPRESAFACTURA = 5;
	    public static final int COL_PARAMETROGENERAL_DIRECCIONREPORTE = 6;
	    public static final int COL_PARAMETROGENERAL_LISTAPRECIOID  = 7;
	    public static final int COL_PARAMETROGENERAL_TIPOPAGOID = 8;
	    public static final int COL_PARAMETROGENERAL_HABILITARTIPOSPAGO = 9;
	    public static final int COL_PARAMETROGENERAL_FACTURARTODO = 10;
	    public static final int COL_PARAMETROGENERAL_SINCRONIZARWIFI = 11;
	    public static final int COL_PARAMETROGENERAL_TIPOIMPRESIONFACTURA = 12;
	    public static final int COL_PARAMETROGENERAL_MERCADOREQUERIDO = 13;
	    public static final int COL_PARAMETROGENERAL_MONTOCI = 14;
	    public static final int COL_PARAMETROGENERAL_MONTOBANCARIZACION = 15;
	    public static final int COL_PARAMETROGENERAL_HABILITARPOP = 16;
	    public static final int COL_PARAMETROGENERAL_HABILITARVENTADIRECTA = 17;
	    public static final int COL_PARAMETROGENERAL_HABILITARFECHAENTREGA = 18;
	    public static final int COL_PARAMETROGENERAL_MONTONIT = 19;
	    public static final int COL_PARAMETROGENERAL_HABILITARCAMBIO = 20;
	    public static final int COL_PARAMETROGENERAL_HABILITARMATCHEO = 21;
	    public static final int COL_PARAMETROGENERAL_EDITARPREVENTAS = 22;
	    public static final int COL_PARAMETROGENERAL_HABILITARMULTIPLEPREVENTA = 23;
	    public static final int COL_PARAMETROGENERAL_BLOQUEARCONDICIONTRIBUTARIA = 24;
	    public static final int COL_PARAMETROGENERAL_MONTOCONDICIONTRIBUTARIA = 25;
	    public static final int COL_PARAMETROGENERAL_CAMBIARNIT = 26;
	    public static final int COL_PARAMETROGENERAL_TEXTOSINNOMBRE = 27;
	    public static final int COL_PARAMETROGENERAL_CREDITOSOBRECREDITO = 28;
	    public static final int COL_PARAMETROGENERAL_RESPETARTIPOPAGO = 29;
	    public static final int COL_PARAMETROGENERAL_MOSTRARALERTAMORA = 30;
	    public static final int COL_PARAMETROGENERAL_MODIFICARTIPONEGOCIO = 31;
	    public static final int COL_PARAMETROGENERAL_ZONAREQUERIDA = 32;
	    public static final int COL_PARAMETROGENERAL_CLIENTEVISITAREQUERIDA = 33;
	    public static final int COL_PARAMETROGENERAL_ZONAMERCADOREQUERIDA = 34;
	    public static final int COL_PARAMETROGENERAL_EDITARCLIENTE = 35;
	    public static final int COL_PARAMETROGENERAL_ACTIVARGPS = 36;
	    public static final int COL_PARAMETROGENERAL_DISTANCIACLIENTE = 37;
	    public static final int COL_PARAMETROGENERAL_PROVINCIAREQUERIDA = 38;
	    public static final int COL_PARAMETROGENERAL_MOSTRARLISTAPRECIO = 39;
	    public static final int COL_PARAMETROGENERAL_MOSTRARTIPOPAGO = 40;
	    public static final int COL_PARAMETROGENERAL_MOSTRARSECUENCIAVISITA = 41;
	    public static final int COL_PARAMETROGENERAL_MOSTRARADICIONARNIT = 42;
	    
	    public static final String[] PARAMETROGENERAL_ALL_KEYS = new String[] { 
	    	KEY_PARAMETROGENERAL_ROW_ID,KEY_PARAMETROGENERAL_MARGENMINIMO,KEY_PARAMETROGENERAL_MARGENMINIMOEMPLEADO,
	    	KEY_PARAMETROGENERAL_DESCUENTOPROMOCION,KEY_PARAMETROGENERAL_NIT,KEY_PARAMETROGENERAL_NOMBREEMPRESAFACTURA,
	    	KEY_PARAMETROGENERAL_DIRECCIONREPORTE,KEY_PARAMETROGENERAL_LISTAPRECIOID,KEY_PARAMETROGENERAL_TIPOPAGOID,
	    	KEY_PARAMETROGENERAL_HABILITARTIPOSPAGO,KEY_PARAMETROGENERAL_FACTURARTODO,KEY_PARAMETROGENERAL_SINCRONIZARWIFI,
	    	KEY_PARAMETROGENERAL_TIPOIMPRESIONFACTURA,KEY_PARAMETROGENERAL_MERCADOREQUERIDO,KEY_PARAMETROGENERAL_MONTOCI,
	    	KEY_PARAMETROGENERAL_MONTOBANCARIZACION,KEY_PARAMETROGENERAL_HABILITARPOP,KEY_PARAMETROGENERAL_HABILITARVENTADIRECTA,
	    	KEY_PARAMETROGENERAL_HABILITARFECHAENTREGA,KEY_PARAMETROGENERAL_MONTONIT,KEY_PARAMETROGENERAL_HABILITARCAMBIO,
	    	KEY_PARAMETROGENERAL_HABILITARMATCHEO,KEY_PARAMETROGENERAL_EDITARPREVENTAS,KEY_PARAMETROGENERAL_HABILITARMULTIPLEPREVENTA,
	    	KEY_PARAMETROGENERAL_BLOQUEARCONDICIONTRIBUTARIA,KEY_PARAMETROGENERAL_MONTOCONDICIONTRIBUTARIA,KEY_PARAMETROGENERAL_CAMBIARNIT,
	    	KEY_PARAMETROGENERAL_TEXTOSINNOMBRE,KEY_PARAMETROGENERAL_CREDITOSOBRECREDITO,KEY_PARAMETROGENERAL_RESPETARTIPOPAGO,
	    	KEY_PARAMETROGENERAL_MOSTRARALERTAMORA,KEY_PARAMETROGENERAL_MODIFICARTIPONEGOCIO,KEY_PARAMETROGENERAL_ZONAREQUERIDA,
	    	KEY_PARAMETROGENERAL_CLIENTEVISITAREQUERIDA,KEY_PARAMETROGENERAL_ZONAMERCADOREQUERIDA,KEY_PARAMETROGENERAL_EDITARCLIENTE,
	    	KEY_PARAMETROGENERAL_ACTIVARGPS,KEY_PARAMETROGENERAL_DISTANCIACLIENTE,KEY_PARAMETROGENERAL_PROVINCIAREQUERIDA,
	    	KEY_PARAMETROGENERAL_MOSTRARLISTAPRECIO,KEY_PARAMETROGENERAL_MOSTRARTIPOPAGO,KEY_PARAMETROGENERAL_MOSTRARSECUENCIAVISITA,
	    	KEY_PARAMETROGENERAL_MOSTRARADICIONARNIT};
	    
	    public static final String PARAMETROGENERAL_TABLE_NAME = "tbl_ParametroGeneral";
	    
	    public static final String PARAMETROGENERAL_TABLE_CREATE = "CREATE TABLE " + PARAMETROGENERAL_TABLE_NAME + "("
	    		+ KEY_PARAMETROGENERAL_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PARAMETROGENERAL_MARGENMINIMO + " float NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MARGENMINIMOEMPLEADO + " float NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_DESCUENTOPROMOCION + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_NIT + " text NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_NOMBREEMPRESAFACTURA + " text NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_DIRECCIONREPORTE + " text NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_LISTAPRECIOID + " integer NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_TIPOPAGOID + " integer NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARTIPOSPAGO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_FACTURARTODO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_SINCRONIZARWIFI + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_TIPOIMPRESIONFACTURA + " text NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MERCADOREQUERIDO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MONTOCI + " float NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MONTOBANCARIZACION + " float NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARPOP + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARVENTADIRECTA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARFECHAENTREGA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MONTONIT + " float NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARCAMBIO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARMATCHEO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_EDITARPREVENTAS + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_HABILITARMULTIPLEPREVENTA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_BLOQUEARCONDICIONTRIBUTARIA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MONTOCONDICIONTRIBUTARIA + " float NOT NULL , "
	    		+ KEY_PARAMETROGENERAL_CAMBIARNIT + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_TEXTOSINNOMBRE + " text NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_CREDITOSOBRECREDITO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_RESPETARTIPOPAGO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MOSTRARALERTAMORA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MODIFICARTIPONEGOCIO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_ZONAREQUERIDA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_CLIENTEVISITAREQUERIDA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_ZONAMERCADOREQUERIDA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_EDITARCLIENTE + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_ACTIVARGPS + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_DISTANCIACLIENTE + " float NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_PROVINCIAREQUERIDA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MOSTRARLISTAPRECIO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MOSTRARTIPOPAGO + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MOSTRARSECUENCIAVISITA + " boolean NOT NULL, "
	    		+ KEY_PARAMETROGENERAL_MOSTRARADICIONARNIT + " boolean NOT NULL);";
	    
	    public boolean borrarParametroGeneralPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(PARAMETROGENERAL_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarParametrosGeneral()
	    {
	      Cursor localCursor = obtenerParametrosGenerales();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) {
	        do
	        {
	          borrarParametroGeneralPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarParametroGeneral(ParametroGeneralWSResult parametroGeneral)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_ParametroGeneral(_margenMinimo, _margenMinimoEmpleado, _descuentoPromocion, _nit, " +
							"_nombreEmpresaFactura, _direccionReporte, _listaPrecioId, _tipoPagoId, _habilitarTiposPago, _facturarTodo, _sincronizarWifi, " +
							"_tipoImpresionFactura, _mercadoRequerido, _montoCi, _montoBancarizacion, _habilitarPop, _habilitarVentaDirecta, _habilitarFechaEntrega, " +
							"_montoNit, _habilitarCambio, _habilitarMatcheo, _editarPreventas, _habilitarMultiplePreventa, _bloquearCondicionTributaria, " +
							"_montoCondicionTributaria, _cambiarNit, _textoSinNombre, _creditoSobreCredito, _respetarTipoPago, _mostrarAlertaMora, " +
							"_modificarTipoNegocio, _zonaRequerida, _clienteVisitaRequerida, _zonaMercadoRequerida, _editarCliente, _activarGps, _distanciaCliente, " +
							"_provinciaRequerida, _mostrarListaPrecio, _mostrarTipoPago, _mostrarSecuenciaVisita, _mostrarAdicionarNit) " +
							"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				//for (int i = 0; i < parametroGeneral.getPropertyCount(); i++) {
				//	SoapObject soapObject = (SoapObject) parametroGeneral.getProperty(i);

					stmt.bindDouble(1, parametroGeneral.getMargenMinimo());
					stmt.bindDouble(2, parametroGeneral.getMargenMinimoEmpleado());
					stmt.bindLong(3, parametroGeneral.isDescuentoPromocion()?1:0);
					stmt.bindString(4, parametroGeneral.getNit());
					stmt.bindString(5, parametroGeneral.getNombreEmpresaFactura());
					stmt.bindString(6, parametroGeneral.getDireccionReporte());
					stmt.bindLong(7, parametroGeneral.getListaPrecioId());
					stmt.bindLong(8, parametroGeneral.getTipoPagoId());
					stmt.bindLong(9, parametroGeneral.isHabilitarTiposPago()?1:0);
					stmt.bindLong(10, parametroGeneral.isFacturarTodo()?1:0);
					stmt.bindLong(11, parametroGeneral.isSincronizarWifi()?1:0);
					stmt.bindString(12, parametroGeneral.getTipoImpresionFactura());
					stmt.bindLong(13, parametroGeneral.isMercadoRequerido()?1:0);
					stmt.bindDouble(14, parametroGeneral.getMontoCi());
					stmt.bindDouble(15, parametroGeneral.getMontoBancarizacion());
					stmt.bindLong(16, parametroGeneral.isHabilitarPop()?1:0);
					stmt.bindLong(17, parametroGeneral.isHabilitarVentaDirecta()?1:0);
					stmt.bindLong(18, parametroGeneral.isHabilitarFechaEntrega()?1:0);
					stmt.bindDouble(19, parametroGeneral.getMontoNit());
					stmt.bindLong(20, parametroGeneral.isHabilitarCambio()?1:0);
					stmt.bindLong(21, parametroGeneral.isHabilitarMatcheo()?1:0);
					stmt.bindLong(22, parametroGeneral.isEditarPreventas()?1:0);
					stmt.bindLong(23, parametroGeneral.isHabilitarMultiplePreventa()?1:0);
					stmt.bindLong(24, parametroGeneral.isBloquearCondicionTributaria()?1:0);
					stmt.bindDouble(25, parametroGeneral.getMontoCondicionTributaria());
					stmt.bindLong(26, parametroGeneral.isCambiarNit()?1:0);
					stmt.bindString(27, parametroGeneral.getTextoSinNombre());
					stmt.bindLong(28, parametroGeneral.isCreditoSobreCredito()?1:0);
					stmt.bindLong(29, parametroGeneral.isRespetarTipoPago()?1:0);
					stmt.bindLong(30, parametroGeneral.isMostrarAlertaMora()?1:0);
					stmt.bindLong(31, parametroGeneral.isModificarTipoNegocio()?1:0);
					stmt.bindLong(32, parametroGeneral.isZonaRequerida()?1:0);
					stmt.bindLong(33, parametroGeneral.isClienteVisita()?1:0);
					stmt.bindLong(34, parametroGeneral.isZonaMercadoRequerida()?1:0);
					stmt.bindLong(35, parametroGeneral.isHabilitarModificacionCliente()?1:0);
					stmt.bindLong(36, parametroGeneral.isActivarGps()?1:0);
					stmt.bindDouble(37, parametroGeneral.getDistanciaCliente());
					stmt.bindLong(38, parametroGeneral.isProvinciaRequerida()?1:0);
					stmt.bindLong(39,parametroGeneral.isMostrarListaPrecio()?1:0);
					stmt.bindLong(40,parametroGeneral.isMostrarTipoPago()?1:0);
					stmt.bindLong(41,parametroGeneral.isMostrarSecuenciaVisita()?1:0);
					stmt.bindLong(42,parametroGeneral.isMostrarAdicionarNit()?1:0);

					stmt.executeInsert();
					stmt.clearBindings();
				//}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerParametrosGenerales()
	    {
	      Cursor localCursor = this.db.query(true,PARAMETROGENERAL_TABLE_NAME, PARAMETROGENERAL_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	  //CIERREPREVENTISTA//
	    public static final String KEY_CIERREPREVENTISTA_ROW_ID = "_Id";
	    public static final String KEY_CIERREPREVENTISTA_EMPLEADOID = "_empleadoId";
	    public static final String KEY_CIERREPREVENTISTA_ANIO = "_anio";
	    public static final String KEY_CIERREPREVENTISTA_MES = "_mes";
	    public static final String KEY_CIERREPREVENTISTA_DIA = "_dia";

	    public static final int COL_CIERREPREVENTISTA_ROW_ID = 0;
	    public static final int COL_CIERREPREVENTISTA_EMPLEADOID = 1;
	    public static final int COL_CIERREPREVENTISTA_ANIO = 2;
	    public static final int COL_CIERREPREVENTISTA_MES = 3;
	    public static final int COL_CIERREPREVENTISTA_DIA = 4;	    
	    
	    public static final String[] CIERREPREVENTISTA_ALL_KEYS = new String[] { 
	    	KEY_CIERREPREVENTISTA_ROW_ID,KEY_CIERREPREVENTISTA_EMPLEADOID,KEY_CIERREPREVENTISTA_ANIO,
	    	KEY_CIERREPREVENTISTA_MES,KEY_CIERREPREVENTISTA_DIA};
	    
	    public static final String CIERREPREVENTISTA_TABLE_NAME = "tbl_CierrePreventista";
	    
	    public static final String CIERREPREVENTISTA_TABLE_CREATE = "CREATE TABLE " + CIERREPREVENTISTA_TABLE_NAME + "("
	    		+ KEY_CIERREPREVENTISTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_CIERREPREVENTISTA_EMPLEADOID + " int NOT NULL, "
	    		+ KEY_CIERREPREVENTISTA_ANIO + " int NOT NULL, "
	    		+ KEY_CIERREPREVENTISTA_MES + " int NOT NULL, "
	    		+ KEY_CIERREPREVENTISTA_DIA + " int NOT NULL);";
	    
	    public boolean borrarCierrePreventistaPor(long rowId)
	    {
	      String str = "_Id=" + rowId;
	      return this.db.delete(CIERREPREVENTISTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarCierresPreventista()
	    {
	      Cursor localCursor = obtenerCierresPreventista();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) {
	        do
	        {
	          borrarCierrePreventistaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarCierrePreventista(int empleadoId,int anio,int mes,int dia)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_dia", dia);
	      return this.db.insert(CIERREPREVENTISTA_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerCierresPreventista()
	    {
	      Cursor localCursor = this.db.query(true,CIERREPREVENTISTA_TABLE_NAME, CIERREPREVENTISTA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerCierrePreventistaPorEmpleadoIdFecha(int empleadoId,int anio,int mes,int dia)
	    {
	    	String str = "_empleadoId="+ empleadoId + " and _anio=" + anio + " and _mes=" + mes + " and _dia=" + dia;
	    	Cursor localCursor = this.db.query(true,CIERREPREVENTISTA_TABLE_NAME, CIERREPREVENTISTA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    //DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP//	   
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ROW_ID = "_Id";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TEMPID = "_tempId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRODUCTOID = "_productoId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PROMOCIONID = "_promocionId";	    
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDAD = "_cantidad";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTO = "_monto";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTO = "_descuento";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTOFINAL = "_montoFinal";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_EMPLEADOID = "_empleadoId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CLIENTEID = "_clienteId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTO = "_costo";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTOID = "_costoId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRECIOID = "_precioId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_NOAUTOVENTA = "_noAutoventa";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOCANAL = "_descuentoCanal";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOAJUSTE = "_descuentoAjuste";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANALPRECIORUTAID = "_canalPrecioRutaId";
	    public static final String KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	    
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ROW_ID = 0;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TEMPID = 1;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRODUCTOID = 2;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PROMOCIONID = 3;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDAD = 4;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDADPAQUETE = 5;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTO = 6;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTO = 7;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTOFINAL = 8;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_EMPLEADOID = 9;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CLIENTEID = 10;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTO = 11;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTOID = 12;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRECIOID = 13;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_NOAUTOVENTA = 14;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOCANAL = 15;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOAJUSTE = 16;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANALPRECIORUTAID = 17;
	    public static final int COL_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOPRONTOPAGO = 18;

	    public static final String[] DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS = new String[] { 
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ROW_ID, KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TEMPID,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRODUCTOID,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PROMOCIONID,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDAD,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDADPAQUETE,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTO,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTO,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTOFINAL,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_EMPLEADOID,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CLIENTEID,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTO,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTOID,KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRECIOID,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_NOAUTOVENTA, KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOCANAL,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOAJUSTE, KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANALPRECIORUTAID,
	    	KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOPRONTOPAGO};
	    
	    public static final String DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME = "tbl_DevolucionDistribuidorProductoTemp";
	    
	    public static final String DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_CREATE = "CREATE TABLE " + DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME + "("
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TEMPID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTO + " float NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTO + " float NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTO + " float NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_COSTOID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_PRECIOID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_NOAUTOVENTA + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOCANAL + " float NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOAJUSTE + " float NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_CANALPRECIORUTAID + " integer NOT NULL, "
	    		+ KEY_DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_DESCUENTOPRONTOPAGO + " float NOT NULL);";

	    public boolean borrarDevolucionDistribuidorProductoTempPorClienteId(long clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      return this.db.delete(DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarDevolucionDistribuidorProductoTempPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarDevolucionDistribuidorProductoTempPorTempId(long tempId)
	    {
	      String str = "_tempId=" + tempId;
	      return this.db.delete(DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarDevolucionDistribuidorProductoTempPorEmpleadoIdYClienteId(int empleadoId, int clienteId)
	    {
	      String str = "_empleadoId=" + empleadoId + " and _clienteId =" + clienteId;
	      return this.db.delete(DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarDevolucionDistribuidorProductoTemp()
	    {
	      Cursor localCursor = obtenerDevolucionDistribuidorProductoTemp();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarDevolucionDistribuidorProductoTempPorId(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarDevolucionDistribuidorProdutoTemp(int tempId, int productoId, int promocionId, int cantidad, 
	    		int cantidadPaquete, float monto, float descuento, float montoFinal, int empleadoId, int clienteId,
	    		float costo,int costoId,int precioId,int noAutoventa, float descuentoCanal, float descuentoAjuste, 
	    		int canalPrecioRutaId, float descuentoProntoPago)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_tempId", tempId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_precioId", precioId);
	      localContentValues.put("_noAutoventa", noAutoventa);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      return this.db.insert(DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, null, localContentValues);
	    }
	    
	    public int modificarDevolucionDistribuidorProductoTemp(int Id, int tempId)
	    {
	      String str = "_Id=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_tempId", tempId);
	      return this.db.update(DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempNoSincronizadasPor(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId + " and _tempId = 0";
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempPorRowId(long rowId)
	    {
	      String str = "_Id=" + rowId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempPor(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTemp()
	    {
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempPorClienteYDistribuidor(int clienteId, int empleadoId)
	    {
	      String str = "_clienteId=" + clienteId + " and _empleadoId=" + empleadoId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempPorProductoPromocion(int productoId,int promocionId)
	    {
	      String str = "_productoId=" + productoId + " and _promocionId=" + promocionId;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempNoConfirmadas(int clienteId,int empleadoId)
	    {
	    	String query =  "SELECT * "
    		  			+ "FROM tbl_devolucionDistribuidorProductoTemp AS DDPT "
    		  			+ "WHERE DDPT._clienteId = " + clienteId + " and DDPT._empleadoId = " + empleadoId + " and DDPT._id NOT IN("
    		  			+ "SELECT SV._ventaProductoTempRowId "
    		  			+ "FROM tbl_sincronizacionventa AS SV "
    		  			+ "WHERE SV._clienteId= " + clienteId + " and SV._distribuidorId =" + empleadoId + " and SV._confirmado = 0 and SV._tipoSincronizacion = 3)";
	    	
	    	Cursor localCursor = db.rawQuery(query,null);
			  	if (localCursor != null) 
		  		{
			  		localCursor.moveToFirst();
			    }
			  return localCursor;	
	    }
	    
	    public Cursor obtenerDevolucionDistribuidorProductoTempPorClienteIdNoAutoventa(int clienteId,int noAutoventa)
	    {
	      String str = "_clienteId=" + clienteId + " and _noAutoventa=" + noAutoventa;
	      Cursor localCursor = this.db.query(true,DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_TABLE_NAME, DEVOLUCIONDISTRIBUIDORPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //PRODUCTOCOSTO//
	    public static final String KEY_PRODUCTOCOSTO_ROW_ID = "_Id";
	    public static final String KEY_PRODUCTOCOSTO_COSTOID = "_costoId";
	    public static final String KEY_PRODUCTOCOSTO_PRODUCTOID = "_productoId";
	    public static final String KEY_PRODUCTOCOSTO_COSTOUNITARIO = "_costoUnitario";
	    public static final String KEY_PRODUCTOCOSTO_COSTOPAQUETE = "_costoPaquete";
	    public static final String KEY_PRODUCTOCOSTO_CPP = "_cpp";
	    
	    public static final int COL_PRODUCTOCOSTO_ROW_ID = 0;
	    public static final int COL_PRODUCTOCOSTO_COSTOID = 1;
	    public static final int COL_PRODUCTOCOSTO_PRODUCTOID = 2;
	    public static final int COL_PRODUCTOCOSTO_COSTOUNITARIO = 3;
	    public static final int COL_PRODUCTOCOSTO_COSTOPAQUETE = 4;
	    public static final int COL_PRODUCTOCOSTO_CPP = 5;
	    
	    public static final String[]PRODUCTOCOSTO_ALL_KEYS = new String[] { 
	    	KEY_PRODUCTOCOSTO_ROW_ID,KEY_PRODUCTOCOSTO_COSTOID,KEY_PRODUCTOCOSTO_PRODUCTOID,
	    	KEY_PRODUCTOCOSTO_COSTOUNITARIO,KEY_PRODUCTOCOSTO_COSTOPAQUETE,KEY_PRODUCTOCOSTO_CPP};
	    
	    public static final String PRODUCTOCOSTO_TABLE_NAME = "tbl_ProductoCosto";
	    
	    public static final String PRODUCTOCOSTO_TABLE_CREATE = "CREATE TABLE " + PRODUCTOCOSTO_TABLE_NAME + "("
	    		+ KEY_PRODUCTOCOSTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PRODUCTOCOSTO_COSTOID + " integer NOT NULL, "
	    		+ KEY_PRODUCTOCOSTO_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_PRODUCTOCOSTO_COSTOUNITARIO + " float NOT NULL, "
	    		+ KEY_PRODUCTOCOSTO_COSTOPAQUETE + " float NOT NULL, "
	    		+ KEY_PRODUCTOCOSTO_CPP + " float NOT NULL);";
	    
	    public boolean borrarProductoCostoPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(PRODUCTOCOSTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarProductoCostoPorProductoId(long productoId)
	    {
	      String str = "_productoId=" + productoId;
	      return this.db.delete(PRODUCTOCOSTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarProductosCosto()
	    {
	      Cursor localCursor = obtenerProductosCosto();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarProductoCostoPorId(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarProductoCosto(ArrayList<CostoWSResult> costos)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_ProductoCosto(_costoId, _productoId, _costoUnitario, _costoPaquete, _cpp) VALUES (?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (CostoWSResult item : costos) {

					stmt.bindLong(1, item.getCostoId());
					stmt.bindLong(2, item.getProductoId());
					stmt.bindDouble(3, item.getCosto());
					stmt.bindDouble(4, item.getCostoPaquete());
					stmt.bindDouble(5, item.getCpp());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerProductoCostoPor(int productoId)
	    {
	      String str = "_productoId=" + productoId;
	      Cursor localCursor = this.db.query(true,PRODUCTOCOSTO_TABLE_NAME, PRODUCTOCOSTO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerProductosCosto()
	    {
	      Cursor localCursor = this.db.query(true,PRODUCTOCOSTO_TABLE_NAME, PRODUCTOCOSTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }

	    //ROL//
	    public static final String KEY_ROL_ROW_ID = "_Id";
	    public static final String KEY_ROL_EMPLEADOID = "_empleadoId";
	    public static final String KEY_ROL_ROL = "_rol";
	    
	    public static final int COL_ROL_ROW_ID = 0;
	    public static final int COL_ROL_EMPLEADOID = 1;
	    public static final int COL_ROL_ROL = 2;
	    
	    public static final String[] ROL_ALL_KEYS = { 
	    	KEY_ROL_ROW_ID,KEY_ROL_EMPLEADOID,KEY_ROL_ROL 
	    	};
	    public static final String ROL_TABLE_NAME = "tbl_Rol";
	    
	    public static final String ROL_TABLE_CREATE = "CREATE TABLE " + ROL_TABLE_NAME + " ("
	    		+ KEY_ROL_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_ROL_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_ROL_ROL + " text NOT NULL);";
	    		
	    public void borrarRoles()
	    {
	    	Cursor localCursor = obtenerRoles();
	    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    	if (localCursor.moveToFirst()) 
	    	{
	    		do
	    		{
	    			borrarRolPorId(localCursor.getLong((int)l));
	    		} 
	    		while (localCursor.moveToNext());
	    	}
	    	localCursor.close();
	    }
	    
	    public boolean borrarRolesPorEmpleadoId(long empleadoId)
	    {
	    	String str = "_empleadoId=" + empleadoId;
	    	return this.db.delete(ROL_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarRolPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(ROL_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarRol(int empleadoId, String rol)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_rol", rol);
	      return this.db.insert(ROL_TABLE_NAME, null, localContentValues);
	    }
	    
	  public Cursor obtenerRoles()
	  {
	    	Cursor localCursor = this.db.query(true,ROL_TABLE_NAME,ROL_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	  }
	    
	  public Cursor obtenerRolPor(int empleadoId)
	  {
		  String str = "_empleadoId=" + empleadoId;
	      Cursor localCursor = this.db.query(true,ROL_TABLE_NAME,ROL_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	//AVANCEVENTA//
	  public static final String KEY_AVANCEVENTA_ROW_ID = "_Id";
	  public static final String KEY_AVANCEVENTA_VENDEDORID = "_vendedorId";
	  public static final String KEY_AVANCEVENTA_DIA = "_dia";
	  public static final String KEY_AVANCEVENTA_MES = "_mes";
	  public static final String KEY_AVANCEVENTA_ANIO = "_anio";
	  public static final String KEY_AVANCEVENTA_NOMBREVENDEDOR = "_nombreVendedor";
	  public static final String KEY_AVANCEVENTA_PRESUPUESTO = "_presupuesto";
	  public static final String KEY_AVANCEVENTA_AVANCE = "_avance";
	  public static final String KEY_AVANCEVENTA_TENDENCIA = "_tendencia";
	  public static final String KEY_AVANCEVENTA_COBERTURA = "_cobertura";
	  public static final String KEY_AVANCEVENTA_TIPOAVANCEVENTA = "_tipoAvanceVenta";
	  public static final String KEY_AVANCEVENTA_ROL = "_rol";
	  public static final String KEY_AVANCEVENTA_NOMBREPROVEEDOR = "_nombreProveedor";
	  public static final String KEY_AVANCEVENTA_NOPREVENTAS = "_noPreventas";
	  public static final String KEY_AVANCEVENTA_CORBERTURAPORCENTAJE = "_coberturaPorcentaje";
	  
	  public static final int COL_AVANCEVENTA_ROW_ID = 0;
	  public static final int COL_AVANCEVENTA_VENDEDORID = 1;
	  public static final int COL_AVANCEVENTA_DIA = 2;
	  public static final int COL_AVANCEVENTA_MES = 3;
	  public static final int COL_AVANCEVENTA_ANIO = 4;
	  public static final int COL_AVANCEVENTA_NOMBREVENDEDOR = 5;
	  public static final int COL_AVANCEVENTA_PRESUPUESTO = 6;
	  public static final int COL_AVANCEVENTA_AVANCE = 7;
	  public static final int COL_AVANCEVENTA_TENDENCIA = 8;
	  public static final int COL_AVANCEVENTA_COBERTURA = 9;
	  public static final int COL_AVANCEVENTA_TIPOAVANCEVENTA = 10;
	  public static final int COL_AVANCEVENTA_ROL = 11;
	  public static final int COL_AVANCEVENTA_NOMBREPROVEEDOR = 12;
	  public static final int COL_AVANCEVENTA_NOPREVENTAS = 13;
	  public static final int COL_AVANCEVENTA_COBERTURAPORCENTAJE = 14;
	  
	  public static final String[] AVANCEVENTA_ALL_KEYS = { 
		  KEY_AVANCEVENTA_ROW_ID,KEY_AVANCEVENTA_VENDEDORID,KEY_AVANCEVENTA_DIA,
		  KEY_AVANCEVENTA_MES,KEY_AVANCEVENTA_ANIO,KEY_AVANCEVENTA_NOMBREVENDEDOR,
		  KEY_AVANCEVENTA_PRESUPUESTO,KEY_AVANCEVENTA_AVANCE,KEY_AVANCEVENTA_TENDENCIA,
		  KEY_AVANCEVENTA_COBERTURA,KEY_AVANCEVENTA_TIPOAVANCEVENTA,KEY_AVANCEVENTA_ROL,
		  KEY_AVANCEVENTA_NOMBREPROVEEDOR,KEY_AVANCEVENTA_NOPREVENTAS,KEY_AVANCEVENTA_CORBERTURAPORCENTAJE};
	    
	  public static final String AVANCEVENTA_TABLE_NAME = "tbl_AvanceVenta";
	    
	  public static final String AVANCEVENTA_TABLE_CREATE = "CREATE TABLE " + AVANCEVENTA_TABLE_NAME + " ("
	    		+ KEY_AVANCEVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_AVANCEVENTA_VENDEDORID + " integer NOT NULL, "
	    		+ KEY_AVANCEVENTA_DIA + " integer NOT NULL, "
	    		+ KEY_AVANCEVENTA_MES + " integer NOT NULL, "
	    		+ KEY_AVANCEVENTA_ANIO + " integer NOT NULL, "
	    		+ KEY_AVANCEVENTA_NOMBREVENDEDOR + " String, "
	    		+ KEY_AVANCEVENTA_PRESUPUESTO + " float NOT NULL, "
	    		+ KEY_AVANCEVENTA_AVANCE + " float NOT NULL, "
	    		+ KEY_AVANCEVENTA_TENDENCIA + " float NOT NULL, "
	    		+ KEY_AVANCEVENTA_COBERTURA + " float NOT NULL, "
	    		+ KEY_AVANCEVENTA_TIPOAVANCEVENTA + " text NOT NULL, "
	    		+ KEY_AVANCEVENTA_ROL + " text NOT NULL, "
	    		+ KEY_AVANCEVENTA_NOMBREPROVEEDOR + " text NOT NULL, "
	    		+ KEY_AVANCEVENTA_NOPREVENTAS + " integer NOT NULL, "
	    		+ KEY_AVANCEVENTA_CORBERTURAPORCENTAJE + " float NOT NULL);";
	  
	    		
	    public void borrarAvancesVenta()
	    {
	    	Cursor localCursor = obtenerAvancesVenta();
	    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    	if (localCursor.moveToFirst()) 
	    	{
	    		do
	    		{
	    			borrarAvanceVentaPorId(localCursor.getLong((int)l));
	    		} 
	    		while (localCursor.moveToNext());
	    	}
	    	localCursor.close();
	    }
	    
	    public boolean borrarAvanceVentaPorVendedorId(long vendedorId)
	    {
	    	String str = "_vendedorId=" + vendedorId;
	    	return this.db.delete(AVANCEVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarAvanceVentaPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(AVANCEVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarAvanceVentaPorTipoAvanceVentaYRol(String tipoAvanceVenta,String rol)
	    {
	      String str = "_tipoAvanceVenta='" + tipoAvanceVenta + "' and _rol='" + rol + "'";
	      return this.db.delete(AVANCEVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarAvanceVenta(ArrayList<AvanceVendedorDiaWSResult> avancesVenta, int mes)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_AvanceVenta(_vendedorId, _dia, _mes, _anio, _nombreVendedor, _presupuesto, _avance, _tendencia, _cobertura, " +
							"_tipoAvanceVenta, _rol, _nombreProveedor, _noPreventas, _coberturaPorcentaje) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (AvanceVendedorDiaWSResult item : avancesVenta) {

					stmt.bindLong(1, item.getVendedorId());
					if(mes == 0)
					{
						stmt.bindLong(2, item.getDia());
					} else {
						stmt.bindLong(2,mes);
					}
					stmt.bindLong(3, item.getMes());
					stmt.bindLong(4, item.getAnio());
					stmt.bindString(5, item.getNombreVendedor());
					stmt.bindDouble(6, item.getPresupuesto());
					stmt.bindDouble(7, item.getAvance());
					stmt.bindDouble(8, item.getTendencia());
					stmt.bindDouble(9, item.getCobertura());
					if(mes == 0) {
						stmt.bindString(10,"DIA");
					} else {
						stmt.bindString(10,"MES");
					}
					stmt.bindString(11,"Vendedor");
					if(mes == 0) {
						stmt.bindString(12, item.getProveedor());
					} else {
						stmt.bindString(12, "");//Revisar que es Objeto??
					}
					if(mes == 0) {
						stmt.bindLong(13, item.getNroPreVentas());
						stmt.bindDouble(14, 0);
					} else {
						stmt.bindLong(13, 0);
						stmt.bindDouble(14, item.getCoberturaPorcentaje());
					}

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }

	public long insertarAvanceVenta(int vendedorId,int dia,int mes,int anio,String nombreVendedor,float presupuesto,
									float avance,float tendencia,float cobertura,String tipoAvanceVenta,
									String rol,String nombreProveedor,int noPreventas,float coberturaPorcentaje)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_vendedorId", vendedorId);
		localContentValues.put("_dia", dia);
		localContentValues.put("_mes", mes);
		localContentValues.put("_anio", anio);
		localContentValues.put("_nombreVendedor", nombreVendedor);
		localContentValues.put("_presupuesto", presupuesto);
		localContentValues.put("_avance", avance);
		localContentValues.put("_tendencia", tendencia);
		localContentValues.put("_cobertura", cobertura);
		localContentValues.put("_tipoAvanceVenta", tipoAvanceVenta);
		localContentValues.put("_rol", rol);
		localContentValues.put("_nombreProveedor", nombreProveedor);
		localContentValues.put("_noPreventas", noPreventas);
		localContentValues.put("_coberturaPorcentaje", coberturaPorcentaje);
		return this.db.insert(AVANCEVENTA_TABLE_NAME, null, localContentValues);
	}
	    
	  public Cursor obtenerAvancesVenta()
	  {
	    	Cursor localCursor = this.db.query(true,AVANCEVENTA_TABLE_NAME,AVANCEVENTA_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	  }
	    
	  public Cursor obtenerAvanceVentaPor(int vendedorId)
	  {
		  String str = "_vendedorId=" + vendedorId;
	      Cursor localCursor = this.db.query(true,AVANCEVENTA_TABLE_NAME,AVANCEVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerAvanceVentaPorTipoAvanceVentaYRol(String tipoAvanceVenta,String rol)
	  {
		  String str = "_tipoAvanceVenta='" + tipoAvanceVenta + "' and _rol='" + rol + "'";
	      Cursor localCursor = this.db.query(true,AVANCEVENTA_TABLE_NAME,AVANCEVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }

	//SINCRONIZACIONVENTA//
	  public static final String KEY_SINCRONIZACIONVENTA_ROW_ID = "_Id";
	  public static final String KEY_SINCRONIZACIONVENTA_VENTAPRODUCTOTEMPROWID = "_ventaProductoTempRowId";
	  public static final String KEY_SINCRONIZACIONVENTA_PREVENTAID = "_preventaId";
	  public static final String KEY_SINCRONIZACIONVENTA_CLIENTEID = "_clienteId";
	  public static final String KEY_SINCRONIZACIONVENTA_DISTRIBUIDORID = "_distribuidorId";
	  public static final String KEY_SINCRONIZACIONVENTA_PRODUCTOID = "_productoId";
	  public static final String KEY_SINCRONIZACIONVENTA_PROMOCIONID = "_promocionId";
	  public static final String KEY_SINCRONIZACIONVENTA_CANTIDAD = "_cantidad";
	  public static final String KEY_SINCRONIZACIONVENTA_CANTIDADPAQUETE = "_cantidadPaquete";
	  public static final String KEY_SINCRONIZACIONVENTA_MONTO = "_monto";
	  public static final String KEY_SINCRONIZACIONVENTA_DESCUENTO = "_descuento";
	  public static final String KEY_SINCRONIZACIONVENTA_MONTOFINAL = "_montoFinal";
	  public static final String KEY_SINCRONIZACIONVENTA_TIPOPAGOID = "_tipoPagoId";
	  public static final String KEY_SINCRONIZACIONVENTA_CANTIDADNUEVA = "_cantidadNueva";
	  public static final String KEY_SINCRONIZACIONVENTA_CANTIDADPAQUETENUEVA = "_cantidadPaqueteNueva";
	  public static final String KEY_SINCRONIZACIONVENTA_MONTONUEVO = "_montoNuevo";
	  public static final String KEY_SINCRONIZACIONVENTA_DESCUENTONUEVO = "_descuentoNuevo";
	  public static final String KEY_SINCRONIZACIONVENTA_MONTOFINALNUEVO = "_montoFinalNuevo";
	  public static final String KEY_SINCRONIZACIONVENTA_NUMERODEITEMS = "_numeroDeItems";
	  public static final String KEY_SINCRONIZACIONVENTA_MOTIVOID = "_motivoId";
	  public static final String KEY_SINCRONIZACIONVENTA_DIA = "_dia";
	  public static final String KEY_SINCRONIZACIONVENTA_MES = "_mes";
	  public static final String KEY_SINCRONIZACIONVENTA_ANIO = "_anio";
	  public static final String KEY_SINCRONIZACIONVENTA_LATITUD = "_latitud";
	  public static final String KEY_SINCRONIZACIONVENTA_LONGITUD = "_longitud";
	  public static final String KEY_SINCRONIZACIONVENTA_CONFIRMADO = "_confirmado";
	  public static final String KEY_SINCRONIZACIONVENTA_TIPOSINCRONIZACION = "_tipoSincronizacion";
	  public static final String KEY_SINCRONIZACIONVENTA_ESTADOSINCRONIZACION = "_estadoSincronizacion";
	  public static final String KEY_SINCRONIZACIONVENTA_HORA = "_hora";
	  public static final String KEY_SINCRONIZACIONVENTA_MINUTO = "_minuto";
	  public static final String KEY_SINCRONIZACIONVENTA_AUTOVENTA = "_autoventa";
	  public static final String KEY_SINCRONIZACIONVENTA_NOMBREFACTURA = "_nombreFactura";
	  public static final String KEY_SINCRONIZACIONVENTA_NIT = "_nit";
	  public static final String KEY_SINCRONIZACIONVENTA_NITNUEVO = "_nitNuevo";
	  public static final String KEY_SINCRONIZACIONVENTA_COSTO = "_costo";
	  public static final String KEY_SINCRONIZACIONVENTA_VENTAIDSERVER = "_ventaIdServer";
	  public static final String KEY_SINCRONIZACIONVENTA_VENTAID = "_ventaId";
	  public static final String KEY_SINCRONIZACIONVENTA_COSTOID = "_costoId";
	  public static final String KEY_SINCRONIZACIONVENTA_OBSERVACION = "_observacion";
	  public static final String KEY_SINCRONIZACIONVENTA_PRECIOID = "_precioId";
	  public static final String KEY_SINCRONIZACIONVENTA_APLICARBONIFICACION = "_aplicarBonificacion";
	  public static final String KEY_SINCRONIZACIONVENTA_NOAUTOVENTA = "_noAutoventa";
	  public static final String KEY_SINCRONIZACIONVENTA_DOSIFICACIONID = "_dosificacionId";
	  public static final String KEY_SINCRONIZACIONVENTA_TIPONIT = "_tipoNit";
	  public static final String KEY_SINCRONIZACIONVENTA_DESCUENTOCANAL = "_descuentoCanal";
	  public static final String KEY_SINCRONIZACIONVENTA_DESCUENTOAJUSTE = "_descuentoAjuste";
	  public static final String KEY_SINCRONIZACIONVENTA_CANALPRECIORUTAID = "_canalPrecioRutaId";
	  public static final String KEY_SINCRONIZACIONVENTA_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
	  public static final String KEY_SINCRONIZACIONVENTA_PRONTOPAGOID = "_prontoPagoId";
	  public static final String KEY_SINCRONIZACIONVENTA_DESCUENTOOBJETIVO = "_descuentoObjetivo";
	  public static final String KEY_SINCRONIZACIONVENTA_FORMAPAGOID = "_formaPagoId";
	  public static final String KEY_SINCRONIZACIONVENTA_CODTRANSFERENCIA = "_codTransferencia";
	  public static final String KEY_SINCRONIZACIONVENTA_FROMSHOPPING = "_fromShopping";
	  
	  public static final int COL_SINCRONIZACIONVENTA_ROW_ID = 0;
	  public static final int COL_SINCRONIZACIONVENTA_VENTAPRODUCTOTEMPROWID = 1;
	  public static final int COL_SINCRONIZACIONVENTA_PREVENTAID = 2;
	  public static final int COL_SINCRONIZACIONVENTA_CLIENTEID = 3;
	  public static final int COL_SINCRONIZACIONVENTA_DISTRIBUIDORID = 4;
	  public static final int COL_SINCRONIZACIONVENTA_PRODUCTOID = 5;
	  public static final int COL_SINCRONIZACIONVENTA_PROMOCIONID = 6;
	  public static final int COL_SINCRONIZACIONVENTA_CANTIDAD = 7;
	  public static final int COL_SINCRONIZACIONVENTA_CANTIDADPAQUETE = 8;
	  public static final int COL_SINCRONIZACIONVENTA_MONTO = 9;
	  public static final int COL_SINCRONIZACIONVENTA_DESCUENTO = 10;
	  public static final int COL_SINCRONIZACIONVENTA_MONTOFINAL = 11;
	  public static final int COL_SINCRONIZACIONVENTA_TIPOPAGOID = 12;
	  public static final int COL_SINCRONIZACIONVENTA_CANTIDADNUEVA = 13;
	  public static final int COL_SINCRONIZACIONVENTA_CANTIDADPAQUETENUEVA = 14;
	  public static final int COL_SINCRONIZACIONVENTA_MONTONUEVO = 15;
	  public static final int COL_SINCRONIZACIONVENTA_DESCUENTONUEVO = 16;
	  public static final int COL_SINCRONIZACIONVENTA_MONTOFINALNUEVO = 17;
	  public static final int COL_SINCRONIZACIONVENTA_NUMERODEITEMS = 18;
	  public static final int COL_SINCRONIZACIONVENTA_MOTIVOID = 19;
	  public static final int COL_SINCRONIZACIONVENTA_DIA = 20;
	  public static final int COL_SINCRONIZACIONVENTA_MES = 21;
	  public static final int COL_SINCRONIZACIONVENTA_ANIO = 22;
	  public static final int COL_SINCRONIZACIONVENTA_LATITUD = 23;
	  public static final int COL_SINCRONIZACIONVENTA_LONGITUD = 24;
	  public static final int COL_SINCRONIZACIONVENTA_CONFIRMADO = 25;
	  public static final int COL_SINCRONIZACIONVENTA_TIPOSINCRONIZACION  = 26;
	  public static final int COL_SINCRONIZACIONVENTA_ESTADOSINCRONIZACION  = 27;
	  public static final int COL_SINCRONIZACIONVENTA_HORA  = 28;
	  public static final int COL_SINCRONIZACIONVENTA_MINUTO  = 29;
	  public static final int COL_SINCRONIZACIONVENTA_AUTOVENTA  = 30;
	  public static final int COL_SINCRONIZACIONVENTA_NOMBREFACTURA  = 31;
	  public static final int COL_SINCRONIZACIONVENTA_NIT = 32;
	  public static final int COL_SINCRONIZACIONVENTA_NITNUEVO  = 33;
	  public static final int COL_SINCRONIZACIONVENTA_COSTO  = 34;
	  public static final int COL_SINCRONIZACIONVENTA_VENTAIDSERVER  = 35;
	  public static final int COL_SINCRONIZACIONVENTA_VENTAID  = 36;
	  public static final int COL_SINCRONIZACIONVENTA_COSTOID  = 37;
	  public static final int COL_SINCRONIZACIONVENTA_OBSERVACION  = 38;
	  public static final int COL_SINCRONIZACIONVENTA_PRECIOID  = 39;
	  public static final int COL_SINCRONIZACIONVENTA_APLICARBONIFICACION  = 40;
	  public static final int COL_SINCRONIZACIONVENTA_NOAUTOVENTA  = 41;
	  public static final int COL_SINCRONIZACIONVENTA_DOSIFICACIONID  = 42;
	  public static final int COL_SINCRONIZACIONVENTA_TIPONIT  = 43;
	  public static final int COL_SINCRONIZACIONVENTA_DESCUENTOCANAL = 44;
	  public static final int COL_SINCRONIZACIONVENTA_DESCUENTOAJUSTE = 45;
	  public static final int COL_SINCRONIZACIONVENTA_CANALPRECIORUTAID = 46;
	  public static final int COL_SINCRONIZACIONVENTA_DESCUENTOPRONTOPAGO = 47;
	  public static final int COL_SINCRONIZACIONVENTA_PRONTOPAGOID = 48;
	  public static final int COL_SINCRONIZACIONVENTA_DESCUENTOOBJETIVO = 49;
	  public static final int COL_SINCRONIZACIONVENTA_FORMAPAGOID = 50;
	  public static final int COL_SINCRONIZACIONVENTA_CODTRANSFERENCIA = 51;
	  public static final int COL_SINCRONIZACIONVENTA_FROMSHOPPING = 52;
	 
	  public static final String[] SINCRONIZACIONVENTA_ALL_KEYS = { 
		  KEY_SINCRONIZACIONVENTA_ROW_ID,KEY_SINCRONIZACIONVENTA_VENTAPRODUCTOTEMPROWID,KEY_SINCRONIZACIONVENTA_PREVENTAID,
		  KEY_SINCRONIZACIONVENTA_CLIENTEID,KEY_SINCRONIZACIONVENTA_DISTRIBUIDORID,KEY_SINCRONIZACIONVENTA_PRODUCTOID,
		  KEY_SINCRONIZACIONVENTA_PROMOCIONID,KEY_SINCRONIZACIONVENTA_CANTIDAD,KEY_SINCRONIZACIONVENTA_CANTIDADPAQUETE,
		  KEY_SINCRONIZACIONVENTA_MONTO,KEY_SINCRONIZACIONVENTA_DESCUENTO,KEY_SINCRONIZACIONVENTA_MONTOFINAL,
		  KEY_SINCRONIZACIONVENTA_TIPOPAGOID,KEY_SINCRONIZACIONVENTA_CANTIDADNUEVA,KEY_SINCRONIZACIONVENTA_CANTIDADPAQUETENUEVA,
		  KEY_SINCRONIZACIONVENTA_MONTONUEVO,KEY_SINCRONIZACIONVENTA_DESCUENTONUEVO,KEY_SINCRONIZACIONVENTA_MONTOFINALNUEVO,
		  KEY_SINCRONIZACIONVENTA_NUMERODEITEMS,KEY_SINCRONIZACIONVENTA_MOTIVOID,KEY_SINCRONIZACIONVENTA_DIA,
		  KEY_SINCRONIZACIONVENTA_MES,KEY_SINCRONIZACIONVENTA_ANIO,KEY_SINCRONIZACIONVENTA_LATITUD,
		  KEY_SINCRONIZACIONVENTA_LONGITUD,KEY_SINCRONIZACIONVENTA_CONFIRMADO,KEY_SINCRONIZACIONVENTA_TIPOSINCRONIZACION,
		  KEY_SINCRONIZACIONVENTA_ESTADOSINCRONIZACION,KEY_SINCRONIZACIONVENTA_HORA,KEY_SINCRONIZACIONVENTA_MINUTO,
		  KEY_SINCRONIZACIONVENTA_AUTOVENTA,KEY_SINCRONIZACIONVENTA_NOMBREFACTURA,KEY_SINCRONIZACIONVENTA_NIT,
		  KEY_SINCRONIZACIONVENTA_NITNUEVO,KEY_SINCRONIZACIONVENTA_COSTO,KEY_SINCRONIZACIONVENTA_VENTAIDSERVER,
		  KEY_SINCRONIZACIONVENTA_VENTAID,KEY_SINCRONIZACIONVENTA_COSTOID,KEY_SINCRONIZACIONVENTA_OBSERVACION,
		  KEY_SINCRONIZACIONVENTA_PRECIOID,KEY_SINCRONIZACIONVENTA_APLICARBONIFICACION,KEY_SINCRONIZACIONVENTA_NOAUTOVENTA,
		  KEY_SINCRONIZACIONVENTA_DOSIFICACIONID,KEY_SINCRONIZACIONVENTA_TIPONIT, KEY_SINCRONIZACIONVENTA_DESCUENTOCANAL,
		  KEY_SINCRONIZACIONVENTA_DESCUENTOAJUSTE, KEY_SINCRONIZACIONVENTA_CANALPRECIORUTAID, KEY_SINCRONIZACIONVENTA_DESCUENTOPRONTOPAGO,
		  KEY_SINCRONIZACIONVENTA_PRONTOPAGOID, KEY_SINCRONIZACIONVENTA_DESCUENTOOBJETIVO, KEY_SINCRONIZACIONVENTA_FORMAPAGOID,
		  KEY_SINCRONIZACIONVENTA_CODTRANSFERENCIA, KEY_SINCRONIZACIONVENTA_FROMSHOPPING};
	    
	  public static final String SINCRONIZACIONVENTA_TABLE_NAME = "tbl_SincronizacionVenta";
	    
	  public static final String SINCRONIZACIONVENTA_TABLE_CREATE = "CREATE TABLE " + SINCRONIZACIONVENTA_TABLE_NAME + " ("
	    		+ KEY_SINCRONIZACIONVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_SINCRONIZACIONVENTA_VENTAPRODUCTOTEMPROWID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_PREVENTAID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_DISTRIBUIDORID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_PRODUCTOID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_PROMOCIONID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_CANTIDAD + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_CANTIDADPAQUETE + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_MONTO + " float NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_DESCUENTO + " float NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_MONTOFINAL + " float NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_TIPOPAGOID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_CANTIDADNUEVA + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_CANTIDADPAQUETENUEVA + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_MONTONUEVO + " float NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_DESCUENTONUEVO + " float NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_MONTOFINALNUEVO + " float NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_NUMERODEITEMS + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_MOTIVOID + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_DIA + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_MES + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_ANIO + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_LATITUD + " double NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_LONGITUD + " double NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_CONFIRMADO + " boolean NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_TIPOSINCRONIZACION + " integer NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_ESTADOSINCRONIZACION + " boolean  NOT NULL, "
	    		+ KEY_SINCRONIZACIONVENTA_HORA + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_MINUTO + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_AUTOVENTA + " boolean NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_NOMBREFACTURA + " text NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_NIT + " text NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_NITNUEVO + " boolean NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_COSTO + " float NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_VENTAIDSERVER + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_VENTAID + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_COSTOID + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_OBSERVACION + " text NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_PRECIOID + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_APLICARBONIFICACION + " boolean NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_NOAUTOVENTA + " integer NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_DOSIFICACIONID + " integer NOT NULL , "
	  			+ KEY_SINCRONIZACIONVENTA_TIPONIT + " text NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_DESCUENTOCANAL + " float NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_DESCUENTOAJUSTE + " float NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_CANALPRECIORUTAID + " int NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_DESCUENTOPRONTOPAGO + " float NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_PRONTOPAGOID + " int NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_DESCUENTOOBJETIVO + " float NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_FORMAPAGOID + " int NOT NULL, "
	  			+ KEY_SINCRONIZACIONVENTA_CODTRANSFERENCIA + " text NOT NULL, "
			  	+ KEY_SINCRONIZACIONVENTA_FROMSHOPPING + " boolean NOT NULL);";
	    		
	  public void borrarSincronizacionesVenta()
	    {
	    	Cursor localCursor = obtenerSincronizacionesVenta();
	    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    	if (localCursor.moveToFirst()) 
	    	{
	    		do
	    		{
	    			borrarSincronizacionVentaPorId(localCursor.getLong((int)l));
	    		} 
	    		while (localCursor.moveToNext());
	    	}
	    	localCursor.close();
	    }
	  
	  public boolean borrarSincronizacionesVentaPor(int preventaId,int clienteId,int productoId)
	    {
	    	String str = "_preventaId =" + preventaId + " and _clienteId = " + clienteId + " and _productoId = " + productoId;
	    	return this.db.delete(SINCRONIZACIONVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	  public boolean borrarSincronizacionesVentaNoConfirmadas()
	    {
	    	String str = "_confirmado=0";
	    	return this.db.delete(SINCRONIZACIONVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	  public boolean borrarSincronizacionVentaPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(SINCRONIZACIONVENTA_TABLE_NAME, str, null) > 0;
	    }
	    
	  public long insertarSincronizacionVenta(int ventaProductoTempRowId,int preventaId,int clienteId,int distribuidorId,
	    										int productoId,int promocionId,int cantidad,int cantidadPaquete,float monto,
	    										float descuento,float montoFinal,int tipoPagoId,int cantidadNueva,
	    										int cantidadPaqueteNueva,float montoNuevo,float descuentoNuevo,
	    										float montoFinalNuevo,int numeroDeItems,int motivoId,int dia,int mes,
	    										int anio,double latitud,double longitud,boolean confirmado,
	    										int tipoSincronizacion,boolean estadoSincronizacion,int hora,int minuto,
	    										boolean autoventa,String nombreFactura,String nit,boolean nitNuevo,
	    										float costo,int ventaIdServer,int ventaId,int costoId,String observacion,
	    										int precioId,boolean aplicarBonificacion,int noAutoventa,int dosificacionId,
	    										String tipoNit, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId,
	    										float descuentoProntoPago, int prontoPagoId, float descuentoObjetivo, int formaPagoId,
	    										String codTransferencia, boolean fromShopping)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaProductoTempRowId", ventaProductoTempRowId);
	      localContentValues.put("_preventaId", preventaId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_distribuidorId", distribuidorId);
	      localContentValues.put("_productoId", productoId);
	      localContentValues.put("_promocionId", promocionId);
	      localContentValues.put("_cantidad", cantidad);
	      localContentValues.put("_cantidadPaquete", cantidadPaquete);
	      localContentValues.put("_monto", monto);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_tipoPagoId", tipoPagoId);
	      localContentValues.put("_cantidadNueva", cantidadNueva);
	      localContentValues.put("_cantidadPaqueteNueva", cantidadPaqueteNueva);
	      localContentValues.put("_montoNuevo", montoNuevo);
	      localContentValues.put("_descuentoNuevo", descuentoNuevo);
	      localContentValues.put("_montoFinalNuevo", montoFinalNuevo);
	      localContentValues.put("_numeroDeItems", numeroDeItems);
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_dia", dia);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_latitud", latitud);
	      localContentValues.put("_longitud", longitud);
	      localContentValues.put("_confirmado", confirmado);
	      localContentValues.put("_tipoSincronizacion", tipoSincronizacion);
	      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
	      localContentValues.put("_hora", hora);
	      localContentValues.put("_minuto", minuto);
	      localContentValues.put("_autoventa", autoventa);
	      localContentValues.put("_nombreFactura", nombreFactura);
	      localContentValues.put("_nit", nit);
	      localContentValues.put("_nitNuevo", nitNuevo);
	      localContentValues.put("_costo", costo);
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      localContentValues.put("_ventaId", ventaId);
	      localContentValues.put("_costoId", costoId);
	      localContentValues.put("_observacion", observacion);
	      localContentValues.put("_precioId", precioId);
	      localContentValues.put("_aplicarBonificacion", aplicarBonificacion);
	      localContentValues.put("_noAutoventa", noAutoventa);
	      localContentValues.put("_dosificacionId", dosificacionId);
	      localContentValues.put("_tipoNit", tipoNit);
	      localContentValues.put("_descuentoCanal", descuentoCanal);
	      localContentValues.put("_descuentoAjuste", descuentoAjuste);
	      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
	      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
	      localContentValues.put("_prontoPagoId", prontoPagoId);
	      localContentValues.put("_descuentoObjetivo", descuentoObjetivo);
	      localContentValues.put("_formaPagoId", formaPagoId);
	      localContentValues.put("_codTransferencia", codTransferencia);
	      localContentValues.put("_fromShopping", fromShopping);
	      return this.db.insert(SINCRONIZACIONVENTA_TABLE_NAME, null, localContentValues);
	    }
	    
	  public Cursor obtenerSincronizacionesVenta()
	  {
	    	Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	  }
	    
	  public Cursor obtenerSincronizacionVentaPor(int preventaId)
	  {
		  String str = "_preventaId=" + preventaId;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaNoConfirmadaPorVentaProductoTempRowId(int ventaProductoTempRowId)
	  {
		  String str = "_confirmado = 0 and _ventaProductoTempRowId=" + ventaProductoTempRowId;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaNoSincronizadasPorPreventaId(int preventaId)
	  {
		  String where = "_estadoSincronizacion = 0 and _preventaId =" + preventaId;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaNoSincronizadasPorVentaIdServer(int ventaIdServer)
	  {
		  String where = "_estadoSincronizacion = 0 and _ventaIdServer =" + ventaIdServer;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaNoSincronizadaPorVentaId(int ventaId)
	  {
		  String where = "_estadoSincronizacion = 0 and _ventaId =" + ventaId;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaVentaNoEntregadaPor(int clienteId)
	  {
		  String where = "_clienteId = " + clienteId + " and _tipoSincronizacion = 2";
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentasModificadasPor(int preventaId,int productoId,int promocionId)
	  {
		  String where = "_preventaId=" + preventaId + " and _productoId=" + productoId + " and _promocionId=" + promocionId;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaNoSincronizadasAgrupadas()
	  {
		  String where = "_estadoSincronizacion = 0 and _autoventa = 0";
		  String groupBy = "_preventaId";
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null,groupBy, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionAutoVentaNoSincronizadasAgrupadas()
	  {
		  String where = "_estadoSincronizacion = 0 and _autoventa = 1";
		  String groupBy = "_ventaId,_clienteId";
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null,groupBy, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerSincronizacionVentaModificada(int ventaProductoTempRowId)
	  {
		  String str = "_ventaProductoTempRowId = " + ventaProductoTempRowId + " and _tipoSincronizacion = 4";
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public int modificarSincronizacionVentaConfirmacionYSincronizacion(int preventaId,int tipoSincronizacion,boolean confirmado,boolean sincronizacion)
	  {
	      String str = "_preventaId=" + preventaId + " and _tipoSincronizacion=" + tipoSincronizacion;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_confirmado", confirmado);
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaSincronizacion(int rowId,boolean sincronizacion)
	  {
	      String str = "_Id=" + rowId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaVentaIdPorPreventaId(int preventaId,int ventaId)
	  {
	      String str = "_preventaId=" + preventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaId", ventaId);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaVentaIdServerPorVentaId(int ventaId,int ventaIdServer)
	  {
	      String str = "_ventaId=" + ventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionAutoventaSincronizacionPorVentaId(int ventaId,boolean sincronizacion)
	  {
	      String str = "_ventaId=" + ventaId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaSincronizacionPorVentaIdServer(int ventaIdServer,boolean sincronizacion)
	  {
	      String str = "_ventaIdServer=" + ventaIdServer;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaSincronizacionPorVentaProductoTempRowId(int ventaProductoTempRowId,boolean sincronizacion)
	  {
	      String str = "_ventaProductoTempRowId=" + ventaProductoTempRowId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaVentaIdServerPor(int preventaId,int clienteId,int ventaIdServer)
	  {
	      String str = "_preventaId = " + preventaId + " and _clienteId = " + clienteId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaVentaIdServerPorProducto(int clienteId,int preventaId,int productoId,int ventaIdServer)
	  {
	      String str = "_clienteId = " + clienteId + " and _preventaId = " + preventaId + " and _productoId = " + productoId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaVentaIdServerPorPromocion(int clienteId,int preventaId,int promocionId,int ventaIdServer)
	  {
	      String str = "_clienteId = " + clienteId + " and _preventaId = " + preventaId + " and _promocionId = " + promocionId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_ventaIdServer", ventaIdServer);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public Cursor obtenerSincronizacionVentaPorClienteId(int clienteId)
	  {
		  String where = "_clienteId = " + clienteId;
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public int modificarSincronizacionVentaClienteId(int Id,int clienteId)
	  {
	      String str = "_clienteId=" + Id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteId", clienteId);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public int modificarSincronizacionVentaConfirmacionYSincronizacionPorId(int rowId,int tipoSincronizacion,
			  														boolean confirmado,boolean sincronizacion)
	  {
	      String str = "_Id=" + rowId + " and _tipoSincronizacion=" + tipoSincronizacion;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_confirmado", confirmado);
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }
	  
	  public Cursor obtenerSincronizacionVentaAutoVentaNoEntregadaPor(int clienteId)
	  {
		  String where = "_clienteId = " + clienteId + " and _tipoSincronizacion = 6 and _autoventa = 1";
	      Cursor localCursor = this.db.query(true,SINCRONIZACIONVENTA_TABLE_NAME,SINCRONIZACIONVENTA_ALL_KEYS,where, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public int modificarSincronizacionAutoventaSincronizacionPorClienteId(int clienteId,boolean sincronizacion)
	  {
	      String str = "_clienteId=" + clienteId + " and _autoventa = 1 and _ventaId =0";
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_estadoSincronizacion", sincronizacion);
	      return this.db.update(SINCRONIZACIONVENTA_TABLE_NAME, localContentValues, str, null);
	  }

	//CATEGORIA//
	  public static final String KEY_CATEGORIA_ROW_ID = "_Id";
	  public static final String KEY_CATEGORIA_CATEGORIAID = "_categoriaId";
	  public static final String KEY_CATEGORIA_NOMBRE = "_nombre";
	  public static final String KEY_CATEGORIA_PADREID = "_padreId";
	  public static final String KEY_CATEGORIA_HIJOS = "_hijos";
	  public static final String KEY_CATEGORIA_PROVEEDORID = "_proveedorId";
	  public static final String KEY_CATEGORIA_JERARQUIA1ID = "_jerarquia1Id";
	  public static final String KEY_CATEGORIA_JERARQUIA2ID = "_jerarquia2Id";
	  public static final String KEY_CATEGORIA_JERARQUIA3ID = "_jerarquia3Id";
	  public static final String KEY_CATEGORIA_JERARQUIA5ID = "_jerarquia5Id";
	  
	  public static final int COL_CATEGORIA_ROW_ID = 0;
	  public static final int COL_CATEGORIA_CATEGORIAID = 1;
	  public static final int COL_CATEGORIA_NOMBRE = 2;
	  public static final int COL_CATEGORIA_PADREID = 3;
	  public static final int COL_CATEGORIA_HIJOS = 4;
	  public static final int COL_CATEGORIA_PROVEEDORID = 5;
	  public static final int COL_CATEGORIA_JERARQUIA1ID = 6;
	  public static final int COL_CATEGORIA_JERARQUIA2ID = 7;
	  public static final int COL_CATEGORIA_JERARQUIA3ID = 8;
	  public static final int COL_CATEGORIA_JERARQUIA5ID = 9;

	  public static final String[] CATEGORIA_ALL_KEYS = { 
		  KEY_CATEGORIA_ROW_ID,KEY_CATEGORIA_CATEGORIAID,KEY_CATEGORIA_NOMBRE,
		  KEY_CATEGORIA_PADREID,KEY_CATEGORIA_HIJOS,KEY_CATEGORIA_PROVEEDORID,
		  KEY_CATEGORIA_JERARQUIA1ID, KEY_CATEGORIA_JERARQUIA2ID, KEY_CATEGORIA_JERARQUIA3ID,
		  KEY_CATEGORIA_JERARQUIA5ID};
	    
	  public static final String CATEGORIA_TABLE_NAME = "tbl_Categoria";
	    
	  public static final String CATEGORIA_TABLE_CREATE = "CREATE TABLE " + CATEGORIA_TABLE_NAME + " ("
	    		+ KEY_CATEGORIA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_CATEGORIA_CATEGORIAID + " integer NOT NULL, "
	    		+ KEY_CATEGORIA_NOMBRE + " text NOT NULL, "
	    		+ KEY_CATEGORIA_PADREID + " integer NOT NULL, "
	    		+ KEY_CATEGORIA_HIJOS + " integer NOT NULL, "
	    		+ KEY_CATEGORIA_PROVEEDORID + " integer NOT NULL, "
	    		+ KEY_CATEGORIA_JERARQUIA1ID + " integer NOT NULL, "
	    		+ KEY_CATEGORIA_JERARQUIA2ID + " integer NOT NULL, "
	    		+ KEY_CATEGORIA_JERARQUIA3ID + " integer NOT NULL, "
	  			+ KEY_CATEGORIA_JERARQUIA5ID + " integer NOT NULL);";
	    		
	    public void borrarCategorias()
	    {
	    	Cursor localCursor = obtenerCategorias();
	    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    	if (localCursor.moveToFirst()) 
	    	{
	    		do
	    		{
	    			borrarCategoriaPorId(localCursor.getLong((int)l));
	    		} 
	    		while (localCursor.moveToNext());
	    	}
	    	localCursor.close();
	    }
	    
	    public boolean borrarCategoriaPorCategoriaId(long categoriaId)
	    {
	    	String str = "_categoriaId=" + categoriaId;
	    	return this.db.delete(CATEGORIA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarCategoriaPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(CATEGORIA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarCategoria(ArrayList<CategoriaWSResult> categorias)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_categoria(_categoriaId, _nombre, _padreId, _hijos, _proveedorId, _jerarquia1Id, _jerarquia2Id, _jerarquia3Id, _jerarquia5Id) " +
							"VALUES (?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (CategoriaWSResult item :categorias) {

					stmt.bindLong(1, item.getCategoriaId());
					stmt.bindString(2, item.getNombre());
					stmt.bindLong(3, item.getPadreId());
					stmt.bindLong(4, item.getHijos());
					stmt.bindLong(5, item.getProveedorId());
					stmt.bindLong(6, item.getJerarquia1Id());
					stmt.bindLong(7, item.getJerarquia2Id());
					stmt.bindLong(8, item.getJerarquia3Id());
					stmt.bindLong(9, item.getJerarquia5Id());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	  public Cursor obtenerCategorias()
	  {
	    	Cursor localCursor = this.db.query(true,CATEGORIA_TABLE_NAME,CATEGORIA_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	  }
	    
	  public Cursor obtenerCategoriaPor(int categoriaId)
	  {
		  String str = "_categoriaId=" + categoriaId;
	      Cursor localCursor = this.db.query(true,CATEGORIA_TABLE_NAME,CATEGORIA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerCategoriaPorProveedor(int proveedorId)
	  {
		  String str = "_proveedorId=" + proveedorId;
	      Cursor localCursor = this.db.query(true,CATEGORIA_TABLE_NAME,CATEGORIA_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }

	//CLIENTENIT//
	  public static final String KEY_CLIENTENIT_ROW_ID = "_Id";
	  public static final String KEY_CLIENTENIT_CLIENTEID = "_clienteId";
	  public static final String KEY_CLIENTENIT_NOMBREFACTURA = "_nombreFactura";
	  public static final String KEY_CLIENTENIT_NIT = "_nit";
	  public static final String KEY_CLIENTENIT_EMPLEADOID = "_empleadoId";
	  public static final String KEY_CLIENTENIT_DIA = "_dia";
	  public static final String KEY_CLIENTENIT_MES = "_mes";
	  public static final String KEY_CLIENTENIT_ANIO = "_anio";
	  public static final String KEY_CLIENTENIT_SINCRONIZACION = "_sincronizacion";
	  public static final String KEY_CLIENTENIT_TIPONIT = "_tipoNit";
	  
	  public static final int COL_CLIENTENIT_ROW_ID = 0;
	  public static final int COL_CLIENTENIT_CLIENTEID = 1;
	  public static final int COL_CLIENTENIT_NOMBREFACTURA = 2;
	  public static final int COL_CLIENTENIT_NIT = 3;
	  public static final int COL_CLIENTENIT_EMPLEADOID = 4;
	  public static final int COL_CLIENTENIT_DIA = 5;
	  public static final int COL_CLIENTENIT_MES = 6;
	  public static final int COL_CLIENTENIT_ANIO = 7;
	  public static final int COL_CLIENTENIT_SINCRONIZACION = 8;
	  public static final int COL_CLIENTENIT_TIPONIT = 9;

	  public static final String[] CLIENTENIT_ALL_KEYS = { 
		  KEY_CLIENTENIT_ROW_ID,KEY_CLIENTENIT_CLIENTEID,KEY_CLIENTENIT_NOMBREFACTURA,
		  KEY_CLIENTENIT_NIT,KEY_CLIENTENIT_EMPLEADOID,KEY_CLIENTENIT_DIA,
		  KEY_CLIENTENIT_MES,KEY_CLIENTENIT_ANIO,KEY_CLIENTENIT_SINCRONIZACION,
		  KEY_CLIENTENIT_TIPONIT};
	    
	  public static final String CLIENTENIT_TABLE_NAME = "tbl_ClienteNit";
	    
	  public static final String CLIENTENIT_TABLE_CREATE = "CREATE TABLE " + CLIENTENIT_TABLE_NAME + " ("
	    		+ KEY_CLIENTENIT_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_CLIENTENIT_CLIENTEID + " integer NOT NULL, "
	    		+ KEY_CLIENTENIT_NOMBREFACTURA + " text, "
	    		+ KEY_CLIENTENIT_NIT + " text, "
	    		+ KEY_CLIENTENIT_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_CLIENTENIT_DIA + " integer NOT NULL, "
	    		+ KEY_CLIENTENIT_MES + " integer NOT NULL, "
	    		+ KEY_CLIENTENIT_ANIO + " integer NOT NULL, "
	    		+ KEY_CLIENTENIT_SINCRONIZACION + " boolean NOT NULL, "
	  			+ KEY_CLIENTENIT_TIPONIT + " text NOT NULL);";
	    		
	    public void borrarClientesNit()
	    {
	    	Cursor localCursor = obtenerClientesNit();
	    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    	if (localCursor.moveToFirst()) 
	    	{
	    		do
	    		{
	    			borrarClienteNitPorId(localCursor.getLong((int)l));
	    		} 
	    		while (localCursor.moveToNext());
	    	}
	    	localCursor.close();
	    }
	    
	    public boolean borrarClienteNitPorClienteId(long clienteId)
	    {
	    	String str = "_clienteId=" + clienteId;
	    	return this.db.delete(CLIENTENIT_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarClienteNitPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(CLIENTENIT_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarClienteNit(ArrayList<ClienteNitWSResult> clientesNit, LoginEmpleado loginEmpleado)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_ClienteNit(_clienteId, _nombreFactura, _nit, _empleadoId, _dia, _mes, _anio, _sincronizacion, _tipoNit) VALUES (?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (ClienteNitWSResult item : clientesNit){

					stmt.bindLong(1, item.getClienteId());
					stmt.bindString(2, item.getNombreFactura());
					stmt.bindString(3, item.getNit());
					stmt.bindLong(4,loginEmpleado.get_empleadoId());
					stmt.bindLong(5,loginEmpleado.get_dia());
					stmt.bindLong(6,loginEmpleado.get_mes());
					stmt.bindLong(7,loginEmpleado.get_anio());
					stmt.bindLong(8,1);
					stmt.bindString(9, item.getTipoNit());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }

	public long insertarClienteNit(int clienteId,String nombreFactura,String nit,int empleadoId,int dia,int mes,
								   int anio,boolean sincronizacion,String tipoNit)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_clienteId", clienteId);
		localContentValues.put("_nombreFactura", nombreFactura);
		localContentValues.put("_nit", nit);
		localContentValues.put("_empleadoId", empleadoId);
		localContentValues.put("_dia", dia);
		localContentValues.put("_mes", mes);
		localContentValues.put("_anio", anio);
		localContentValues.put("_sincronizacion", sincronizacion);
		localContentValues.put("_tipoNit", tipoNit);
		return this.db.insert(CLIENTENIT_TABLE_NAME, null, localContentValues);
	}
	    
	    public int modificarClienteNit(int clienteId,String nit,String tipoNit)
	    {
	      String str = "_clienteId=" + clienteId + " and _nit ='" + nit + "'";
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_tipoNit", String.valueOf(tipoNit));
	      return this.db.update(CLIENTENIT_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarClienteNitSincro(int clienteId,int newClienteId)
	    {
	      String str = "_clienteId = " + clienteId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteId", newClienteId);
	      return this.db.update(CLIENTENIT_TABLE_NAME, localContentValues, str, null);
	    }
	    
	  public Cursor obtenerClientesNit()
	  {
	    	Cursor localCursor = this.db.query(true,CLIENTENIT_TABLE_NAME,CLIENTENIT_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	  }
	    
	  public Cursor obtenerClienteNitPor(int clienteId)
	  {
		  String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,CLIENTENIT_TABLE_NAME,CLIENTENIT_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }

	  public Cursor obtenerClienteNitNoSincronizados()
	  {
		  String str = "_sincronizacion = 0";
	      Cursor localCursor = this.db.query(true,CLIENTENIT_TABLE_NAME,CLIENTENIT_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	//MOTIVONOATENCION//
	    public static final String KEY_MOTIVONOATENCION_ROW_ID = "_Id";
	    public static final String KEY_MOTIVONOATENCION_MOTIVOID = "_motivoId";
	    public static final String KEY_MOTIVONOATENCION_DESCRIPCION = "_descripcion";
	    
	    public static final int COL_MOTIVONOATENCION_ROW_ID = 0;
	    public static final int COL_MOTIVONOATENCION_MOTIVOID = 1;
	    public static final int COL_MOTIVONOATENCION_DESCRIPCION = 2;
	    
	    public static final String[] MOTIVONOATENCION_ALL_KEYS = new String[] {
	    	KEY_MOTIVONOATENCION_ROW_ID,KEY_MOTIVONOATENCION_MOTIVOID,KEY_MOTIVONOATENCION_DESCRIPCION 
	    	};
	    
	    public static final String MOTIVONOATENCION_TABLE_NAME = "tbl_MotivoNoAtencion";
	    
	    public static final String MOTIVONOATENCION_TABLE_CREATE = "CREATE TABLE " + MOTIVONOATENCION_TABLE_NAME + " ("
	    		+ KEY_MOTIVONOATENCION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_MOTIVONOATENCION_MOTIVOID + " integer NOT NULL,"
	    		+ KEY_MOTIVONOATENCION_DESCRIPCION + " text NOT NULL);";
	    
	    public boolean borrarMotivoNoAtencionPor(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(MOTIVONOATENCION_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarMotivosNoAtencion()
	    {
	      Cursor localCursor = obtenerMotivosNoAtencion();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarMotivoNoAtencionPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarMotivoNoAtencion(ArrayList<MotivoNoAtencionWSResult> motivosNoAtencion)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_MotivoNoAtencion(_motivoId, _descripcion) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (MotivoNoAtencionWSResult item : motivosNoAtencion) {

					stmt.bindLong(1, item.getMotivoId());
					stmt.bindString(2, item.getDescripcion());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerMotivoNoAtencionPor(int motivoId)
	    {
	      String str = "_motivoId=" + motivoId;
	      Cursor localCursor = this.db.query(true,MOTIVONOATENCION_TABLE_NAME, MOTIVONOATENCION_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerMotivosNoAtencion()
	    {
	      Cursor localCursor = this.db.query(true,MOTIVONOATENCION_TABLE_NAME, MOTIVONOATENCION_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	  //CLIENTENOATENDIDO//
	    public static final String KEY_CLIENTENOATENDIDO_ROW_ID = "_id";
	    public static final String KEY_CLIENTENOATENDIDO_EMPLEADOID = "_empleadoId";
	    public static final String KEY_CLIENTENOATENDIDO_CLIENTEID = "_clienteId";
	    public static final String KEY_CLIENTENOATENDIDO_MOTIVOID = "_motivoId";
	    public static final String KEY_CLIENTENOATENDIDO_DIA = "_dia";
	    public static final String KEY_CLIENTENOATENDIDO_MES = "_mes";
	    public static final String KEY_CLIENTENOATENDIDO_ANIO = "_anio";
	    public static final String KEY_CLIENTENOATENDIDO_HORA = "_hora";
	    public static final String KEY_CLIENTENOATENDIDO_MINUTO = "_minuto";
	    public static final String KEY_CLIENTENOATENDIDO_LATITUD = "_latitud";
	    public static final String KEY_CLIENTENOATENDIDO_LONGITUD = "_longitud";
	    public static final String KEY_CLIENTENOATENDIDO_SINCRONIZACION = "_sincronizacion";
	    
	    public static final int COL_CLIENTENOATENDIDO_ROW_ID = 0;
	    public static final int COL_CLIENTENOATENDIDO_EMPLEADOID = 1;
	    public static final int COL_CLIENTENOATENDIDO_CLIENTEID = 2;
	    public static final int COL_CLIENTENOATENDIDO_MOTIVOID = 3;
	    public static final int COL_CLIENTENOATENDIDO_DIA = 4;
	    public static final int COL_CLIENTENOATENDIDO_MES = 5;
	    public static final int COL_CLIENTENOATENDIDO_ANIO = 6;
	    public static final int COL_CLIENTENOATENDIDO_HORA = 7;
	    public static final int COL_CLIENTENOATENDIDO_MINUTO = 8;
	    public static final int COL_CLIENTENOATENDIDO_LATITUD = 9;
	    public static final int COL_CLIENTENOATENDIDO_LONGITUD = 10;
	    public static final int COL_CLIENTENOATENDIDO_SINCRONIZACION = 11;
	    
	    public static final String[] CLIENTENOATENDIDO_ALL_KEYS = new String[] {
	    	KEY_CLIENTENOATENDIDO_ROW_ID,KEY_CLIENTENOATENDIDO_EMPLEADOID,KEY_CLIENTENOATENDIDO_CLIENTEID, 
	    	KEY_CLIENTENOATENDIDO_MOTIVOID,KEY_CLIENTENOATENDIDO_DIA,KEY_CLIENTENOATENDIDO_MES,
	    	KEY_CLIENTENOATENDIDO_ANIO,KEY_CLIENTENOATENDIDO_HORA,KEY_CLIENTENOATENDIDO_MINUTO,
	    	KEY_CLIENTENOATENDIDO_LATITUD,KEY_CLIENTENOATENDIDO_LONGITUD,KEY_CLIENTENOATENDIDO_SINCRONIZACION};
	    
	    public static final String CLIENTENOATENDIDO_TABLE_NAME = "tbl_ClienteNoAtendido";
	    
	    public static final String CLIENTENOATENDIDO_TABLE_CREATE = "CREATE TABLE " + CLIENTENOATENDIDO_TABLE_NAME + " ("
	    		+ KEY_CLIENTENOATENDIDO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_CLIENTENOATENDIDO_EMPLEADOID + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_CLIENTEID + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_MOTIVOID + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_DIA + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_MES + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_ANIO + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_HORA + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_MINUTO + " integer NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_LATITUD + " double NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_LONGITUD + " double NOT NULL,"
	    		+ KEY_CLIENTENOATENDIDO_SINCRONIZACION + " boolean NOT NULL);";
	    
	    public boolean borrarClienteNoAtendidoPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(CLIENTENOATENDIDO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarClientesNoAtendidos()
	    {
	      Cursor localCursor = obtenerClientesNoAtendidos();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarClienteNoAtendidoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarClienteNoAtendido(int empleadoId,int clienteId,int motivoId,int dia,int mes,int anio,int hora,
	    										int minuto,double latitud,double longitud, boolean sincronizacion)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_motivoId", motivoId);
	      localContentValues.put("_dia", dia);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_hora", hora);
	      localContentValues.put("_minuto", minuto);
	      localContentValues.put("_latitud", latitud);
	      localContentValues.put("_longitud", longitud);
	      localContentValues.put("_sincronizacion", sincronizacion);
	      return this.db.insert(CLIENTENOATENDIDO_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerClienteNoAtendidoPor(int empleadoId)
	    {
	      String str = "_empleadoId=" + empleadoId;
	      Cursor localCursor = this.db.query(true,CLIENTENOATENDIDO_TABLE_NAME, CLIENTENOATENDIDO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClienteNoAtendidoPorClienteId(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      Cursor localCursor = this.db.query(true,CLIENTENOATENDIDO_TABLE_NAME, CLIENTENOATENDIDO_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerClientesNoAtendidos()
	    {
	    	Cursor localCursor = this.db.query(true,CLIENTENOATENDIDO_TABLE_NAME, CLIENTENOATENDIDO_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerClientesNoAtendidosNoSincronizados()
	    {
	    	String str = "_sincronizacion = 0";
	    	Cursor localCursor = this.db.query(true,CLIENTENOATENDIDO_TABLE_NAME, CLIENTENOATENDIDO_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public int modificarClienteNoAtendidoSincronizacion(int clienteId)
	    {
	      String str = "_clienteId=" + clienteId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_sincronizacion", Boolean.TRUE);
	      return this.db.update(CLIENTENOATENDIDO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarClienteNoAtendidoSincronizacionDesdeVendedor(int id,int clienteId,boolean sincronizacion)
	    {
	      String str = "_clienteId=" + id;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_sincronizacion", sincronizacion);
	      return this.db.update(CLIENTENOATENDIDO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    //PROVEEDORPRECIOLISTAMARGEN//
	    public static final String KEY_PROVEEDORPRECIOLISTAMARGEN_ROW_ID = "_id";
	    public static final String KEY_PROVEEDORPRECIOLISTAMARGEN_PROVEEDORID = "_proveedorId";
	    public static final String KEY_PROVEEDORPRECIOLISTAMARGEN_PRECIOLISTAID = "_precioListaId";
	    public static final String KEY_PROVEEDORPRECIOLISTAMARGEN_MARGEN = "_margen";
	    
	    public static final int COL_PROVEEDORPRECIOLISTAMARGEN_ROW_ID = 0;
	    public static final int COL_PROVEEDORPRECIOLISTAMARGEN_PROVEEDORID = 1;
	    public static final int COL_PROVEEDORPRECIOLISTAMARGEN_PRECIOLISTAID = 2;
	    public static final int COL_PROVEEDORPRECIOLISTAMARGEN_MARGEN = 3;

	    public static final String[] PROVEEDORPRECIOLISTAMARGEN_ALL_KEYS = new String[] {
	    	KEY_PROVEEDORPRECIOLISTAMARGEN_ROW_ID,KEY_PROVEEDORPRECIOLISTAMARGEN_PROVEEDORID,
	    	KEY_PROVEEDORPRECIOLISTAMARGEN_PRECIOLISTAID,KEY_PROVEEDORPRECIOLISTAMARGEN_MARGEN
	    	};
	    
	    public static final String PROVEEDORPRECIOLISTAMARGEN_TABLE_NAME = "tbl_ProveedorPrecioListaMargen";
	    
	    public static final String PROVEEDORPRECIOLISTAMARGEN_TABLE_CREATE = "CREATE TABLE " + PROVEEDORPRECIOLISTAMARGEN_TABLE_NAME + " ("
	    		+ KEY_PROVEEDORPRECIOLISTAMARGEN_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_PROVEEDORPRECIOLISTAMARGEN_PROVEEDORID + " integer NOT NULL,"
	    		+ KEY_PROVEEDORPRECIOLISTAMARGEN_PRECIOLISTAID + " integer NOT NULL,"
	    		+ KEY_PROVEEDORPRECIOLISTAMARGEN_MARGEN + " float NOT NULL);";
	    
	    public boolean borrarProveedorPrecioListaMargenPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(PROVEEDORPRECIOLISTAMARGEN_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarProveedorPrecioListaMargen()
	    {
	      Cursor localCursor = obtenerProveedorPrecioListaMargen();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarProveedorPrecioListaMargenPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarProveedorPrecioListaMargen(ArrayList<ProveedorPrecioListaMargenWSResult> proveedoresPrecioListaMargen)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_ProveedorPrecioListaMargen(_proveedorId, _precioListaId, _margen) VALUES (?,?,?)"
			);
			try {
				db.beginTransaction();
				for (ProveedorPrecioListaMargenWSResult item : proveedoresPrecioListaMargen) {

					stmt.bindLong(1, item.getProveedorId());
					stmt.bindLong(2, item.getPrecioListaId());
					stmt.bindDouble(3, item.getMargen());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerMargenProveedorPrecioListaMargenPor(int proveedorId,int precioListaId)
	    {
	      String str = "_proveedorId=" + proveedorId + " and _precioListaId = " + precioListaId;
	      Cursor localCursor = this.db.query(true,PROVEEDORPRECIOLISTAMARGEN_TABLE_NAME, PROVEEDORPRECIOLISTAMARGEN_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerProveedorPrecioListaMargen()
	    {
	      Cursor localCursor = this.db.query(true,PROVEEDORPRECIOLISTAMARGEN_TABLE_NAME, PROVEEDORPRECIOLISTAMARGEN_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    //DOSIFICACION//
	    public static final String KEY_DOSIFICACION_ROW_ID = "_id";
	    public static final String KEY_DOSIFICACION_DOSIFICACIONID = "_dosificacionId";
	    public static final String KEY_DOSIFICACION_DIACREACION = "_diaCreacion";
	    public static final String KEY_DOSIFICACION_MESCREACION = "_mesCreacion";
	    public static final String KEY_DOSIFICACION_ANIOCREACION = "_anioCreacion";
	    public static final String KEY_DOSIFICACION_CODIGOAUTORIZACION = "_codigoAutorizacion";
	    public static final String KEY_DOSIFICACION_CANTIDAD = "_cantidad";
	    public static final String KEY_DOSIFICACION_NUMEROINICIAL = "_numeroInicial";
	    public static final String KEY_DOSIFICACION_NUMEROFINAL = "_numeroFinal";
	    public static final String KEY_DOSIFICACION_DIALIMITEEMISION = "_diaLimiteEmision";
	    public static final String KEY_DOSIFICACION_MESLIMITEEMISION = "_mesLimiteEmision";
	    public static final String KEY_DOSIFICACION_ANIOLIMITEEMISION = "_anioLimiteEmision";
	    public static final String KEY_DOSIFICACION_FACTURATIPOID = "_facturaTipoId";
	    public static final String KEY_DOSIFICACION_ALMACENID = "_almacenId";
	    public static final String KEY_DOSIFICACION_ACTIVA = "_activa";
	    public static final String KEY_DOSIFICACION_BLOQUEADA = "_bloqueada";
	    public static final String KEY_DOSIFICACION_LLAVEDOSIFICACION = "_llaveDosificacion";
	    public static final String KEY_DOSIFICACION_CANTIDADDISPONIBLE = "_cantidadDisponible";
	    public static final String KEY_DOSIFICACION_LEY = "_ley";
	    public static final String KEY_DOSIFICACION_SFC = "_sfc";
	    public static final String KEY_DOSIFICACION_SUCURSAL = "_sucursal";
	    public static final String KEY_DOSIFICACION_MENSAJE1 = "_mensaje1";
	    public static final String KEY_DOSIFICACION_MENSAJE2 = "_mensaje2";
	    public static final String KEY_DOSIFICACION_ACTIVIDAD = "_actividad";
	   
	    public static final int COL_DOSIFICACION_ROW_ID = 0;
	    public static final int COL_DOSIFICACION_DOSIFICACIONID = 1;
	    public static final int COL_DOSIFICACION_DIACREACION = 2;
	    public static final int COL_DOSIFICACION_MESCREACION = 3;
	    public static final int COL_DOSIFICACION_ANIOCREACION = 4;
	    public static final int COL_DOSIFICACION_CODIGOAUTORIZACION = 5;
	    public static final int COL_DOSIFICACION_CANTIDAD = 6;
	    public static final int COL_DOSIFICACION_NUMEROINICIAL = 7;
	    public static final int COL_DOSIFICACION_NUMEROFINAL = 8;
	    public static final int COL_DOSIFICACION_DIALIMITEEMISION = 9;
	    public static final int COL_DOSIFICACION_MESLIMITEEMISION = 10;
	    public static final int COL_DOSIFICACION_ANIOLIMITEEMISION = 11;
	    public static final int COL_DOSIFICACION_FACTURATIPOID = 12;
	    public static final int COL_DOSIFICACION_ALMACENID = 13;
	    public static final int COL_DOSIFICACION_ACTIVA = 14;
	    public static final int COL_DOSIFICACION_BLOQUEADA = 15;
	    public static final int COL_DOSIFICACION_LLAVEDOSIFICACION = 16;
	    public static final int COL_DOSIFICACION_CANTIDADDISPONIBLE = 17;
	    public static final int COL_DOSIFICACION_LEY = 18;
	    public static final int COL_DOSIFICACION_SFC = 19;
	    public static final int COL_DOSIFICACION_SUCURSAL = 20;
	    public static final int COL_DOSIFICACION_MENSAJE1 = 21;
	    public static final int COL_DOSIFICACION_MENSAJE2 = 22;
	    public static final int COL_DOSIFICACION_ACTIVIDAD = 23;

	    public static final String[] DOSIFICACION_ALL_KEYS = new String[] {
	    	KEY_DOSIFICACION_ROW_ID,KEY_DOSIFICACION_DOSIFICACIONID,KEY_DOSIFICACION_DIACREACION,
	    	KEY_DOSIFICACION_MESCREACION,KEY_DOSIFICACION_ANIOCREACION,KEY_DOSIFICACION_CODIGOAUTORIZACION,
	    	KEY_DOSIFICACION_CANTIDAD,KEY_DOSIFICACION_NUMEROINICIAL,KEY_DOSIFICACION_NUMEROFINAL,
	    	KEY_DOSIFICACION_DIALIMITEEMISION,KEY_DOSIFICACION_MESLIMITEEMISION,KEY_DOSIFICACION_ANIOLIMITEEMISION,
	    	KEY_DOSIFICACION_FACTURATIPOID,KEY_DOSIFICACION_ALMACENID,KEY_DOSIFICACION_ACTIVA,KEY_DOSIFICACION_BLOQUEADA,
	    	KEY_DOSIFICACION_LLAVEDOSIFICACION,KEY_DOSIFICACION_CANTIDADDISPONIBLE,KEY_DOSIFICACION_LEY,
	    	KEY_DOSIFICACION_SFC,KEY_DOSIFICACION_SUCURSAL,KEY_DOSIFICACION_MENSAJE1,KEY_DOSIFICACION_MENSAJE2,
	    	KEY_DOSIFICACION_ACTIVIDAD};
	    
	    public static final String DOSIFICACION_TABLE_NAME = "tbl_Dosificacion";
	    
	    public static final String DOSIFICACION_TABLE_CREATE = "CREATE TABLE " + DOSIFICACION_TABLE_NAME + " ("
	    		+ KEY_DOSIFICACION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_DOSIFICACION_DOSIFICACIONID + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_DIACREACION + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_MESCREACION + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_ANIOCREACION + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_CODIGOAUTORIZACION + " text NOT NULL,"
	    		+ KEY_DOSIFICACION_CANTIDAD + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_NUMEROINICIAL + " text NOT NULL,"
	    		+ KEY_DOSIFICACION_NUMEROFINAL + " text NOT NULL,"
	    		+ KEY_DOSIFICACION_DIALIMITEEMISION + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_MESLIMITEEMISION + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_ANIOLIMITEEMISION + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_FACTURATIPOID + " text NOT NULL,"
	    		+ KEY_DOSIFICACION_ALMACENID + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_ACTIVA + " boolean NOT NULL,"
	    		+ KEY_DOSIFICACION_BLOQUEADA + " boolean NOT NULL,"
	    		+ KEY_DOSIFICACION_LLAVEDOSIFICACION + " text NOT NULL,"
	    		+ KEY_DOSIFICACION_CANTIDADDISPONIBLE + " integer NOT NULL,"
	    		+ KEY_DOSIFICACION_LEY + " text NOT NULL, "
	    		+ KEY_DOSIFICACION_SFC + " text NOT NULL, "
	    		+ KEY_DOSIFICACION_SUCURSAL + " text NOT NULL, "
	    		+ KEY_DOSIFICACION_MENSAJE1 + " text NOT NULL, "
	    		+ KEY_DOSIFICACION_MENSAJE2 + " text NOT NULL, "
	    		+ KEY_DOSIFICACION_ACTIVIDAD + " text NOT NULL);";
	    
	    public boolean borrarDosificacionPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(DOSIFICACION_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarDosificacion()
	    {
	      Cursor localCursor = obtenerDosificacion();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarDosificacionPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarDosificacion(ArrayList<DosificacionWSResult> dosificaciones)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_Dosificacion(_dosificacionId, _diaCreacion, _diaCreacion, _mesCreacion, _anioCreacion, " +
							"_codigoAutorizacion, _cantidad, _numeroInicial, _numeroFinal, _diaLimiteEmision, _mesLimiteEmision, " +
							"_anioLimiteEmision, _facturaTipoId, _almacenId, _activa, _bloqueada, _llaveDosificacion, _cantidadDisponible, " +
							"_ley, _sfc, _sucursal, _mensaje1, _mensaje2, _actividad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (DosificacionWSResult item : dosificaciones) {

					stmt.bindLong(1, item.getDosificacionId());
					stmt.bindLong(2, item.getDiaCreacion());
					stmt.bindLong(3, item.getMesCreacion());
					stmt.bindLong(4, item.getAnioCreacion());
					stmt.bindString(5, item.getCodigoAutorizacion());
					stmt.bindLong(6, item.getCantidad());
					stmt.bindString(7, item.getNumeroInicial());
					stmt.bindString(8, item.getNumeroFinal());
					stmt.bindLong(9, item.getDiaLimiteEmision());
					stmt.bindLong(10, item.getMesLimiteEmision());
					stmt.bindLong(11, item.getAnioLimiteEmision());
					stmt.bindString(12, item.getFacturaTipoId());
					stmt.bindLong(13, item.getAlmacenId());
					stmt.bindLong(14, item.isActiva()?1:0);
					stmt.bindLong(15, item.isBloqueada()?1:0);
					stmt.bindString(16, item.getLlaveDosificacion());
					stmt.bindLong(17, item.getCantidadDisponible());
					stmt.bindString(18, item.getLey());
					stmt.bindString(19, item.getSfc());
					stmt.bindString(20, item.getSucursal());
					stmt.bindString(21, item.getMensaje1());
					stmt.bindString(22, item.getMensaje2());
					stmt.bindString(23, item.getActividad());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerDosificacion()
	    {
	      Cursor localCursor = this.db.query(true,DOSIFICACION_TABLE_NAME, DOSIFICACION_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public int modificarDosificacionNumeroFactura(int dosificacionId,int numeroActual)
	    {
	      String str = "_dosificacionId=" + dosificacionId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_numeroInicial", numeroActual);
	      return this.db.update(DOSIFICACION_TABLE_NAME, localContentValues, str, null);
	    }
	    
	  //FACTURA//
	    public static final String KEY_FACTURA_ROW_ID = "_id";
	    public static final String KEY_FACTURA_FACTURAID = "_facturaId";
	    public static final String KEY_FACTURA_NUMERO = "_numero";
	    public static final String KEY_FACTURA_NOMBRE = "_nombre";
	    public static final String KEY_FACTURA_NIT = "_nit";
	    public static final String KEY_FACTURA_FECHADIA = "_fechaDia";
	    public static final String KEY_FACTURA_FECHAMES = "_fechaMes";
	    public static final String KEY_FACTURA_FECHAANIO = "_fechaAnio";
	    public static final String KEY_FACTURA_FECHAHORA = "_fechaHora";
	    public static final String KEY_FACTURA_FECHAMINUTO = "_fechaMinuto";
	    public static final String KEY_FACTURA_FECHALIMITEEMISIONDIA = "_fechaLimiteEmisionDia";
	    public static final String KEY_FACTURA_FECHALIMITEEMISIONMES = "_fechaLimiteEmisionMes";
	    public static final String KEY_FACTURA_FECHALIMITEEMISIONANIO = "_fechaLimiteEmisionAnio";
	    public static final String KEY_FACTURA_FECHALIMITEEMISIONHORA = "_fechaLimiteEmisionHora";
	    public static final String KEY_FACTURA_FECHALIMITEEMISIONMINUTO = "_fechaLimiteEmisionMinuto";
	    public static final String KEY_FACTURA_MONTOTOTAL = "_montoTotal";
	    public static final String KEY_FACTURA_DESCUENTO = "_descuento";
	    public static final String KEY_FACTURA_MONTOFINAL = "_montoFinal";
	    public static final String KEY_FACTURA_MONTOPALABRAS = "_montoPalabras";
	    public static final String KEY_FACTURA_CODIGOAUTORIZACION = "_codigoAutorizacion";
	    public static final String KEY_FACTURA_CODIGOCONTROL = "_codigoControl";
	    public static final String KEY_FACTURA_FACTURATIPOID = "_facturaTipoId";
	    public static final String KEY_FACTURA_ALMACENID = "_almacenId";
	    public static final String KEY_FACTURA_ANULADA = "_anulada";
	    public static final String KEY_FACTURA_ANULACIONUSUARIOID = "_anulacionUsuarioId";
	    public static final String KEY_FACTURA_ANULACIONMOTIVO = "_anulacionMotivo";
	    public static final String KEY_FACTURA_ANULACIONFECHADIA = "_anulacionFechaDia";
	    public static final String KEY_FACTURA_ANULACIONFECHAMES = "_anulacionFechaMes";
	    public static final String KEY_FACTURA_ANULACIONFECHAANIO = "_anulacionFechaAnio";
	    public static final String KEY_FACTURA_DOSIFICACIONID = "_dosificacionId";
	    public static final String KEY_FACTURA_EMPLEADOID = "_empleadoId";
	    public static final String KEY_FACTURA_QRTAMANO = "_qrTamano";
	    public static final String KEY_FACTURA_QREXTENSION = "_qrExtension";
	    public static final String KEY_FACTURA_QRBINARIO = "_qrBinario";
	    public static final String KEY_FACTURA_QRMIMETYPE = "_qrMimeType";
	    public static final String KEY_FACTURA_CLIENTEID = "_clienteId";
	    public static final String KEY_FACTURA_NUMEROITEMS = "_numeroItems";
	    public static final String KEY_FACTURA_SINCRONIZACION = "_sincronizacion";
	    public static final String KEY_FACTURA_IMPRESO = "_impreso";
	    public static final String KEY_FACTURA_VENTAID = "_ventaId";
	    public static final String KEY_FACTURA_SERVERVENTAID = "_serverVentaId";
	    public static final String KEY_FACTURA_NITNUEVO = "_nitNuevo";
	    public static final String KEY_FACTURA_NOFACTURA = "_noFactura";
	    public static final String KEY_FACTURA_TIPONIT = "_tipoNit";
	    
	    public static final int COL_FACTURA_ROW_ID = 0;
	    public static final int COL_FACTURA_NUMERO = 1;
	    public static final int COL_FACTURA_NOMBRE = 2;
	    public static final int COL_FACTURA_NIT = 3;
	    public static final int COL_FACTURA_FECHADIA = 4;
	    public static final int COL_FACTURA_FECHAMES = 5;
	    public static final int COL_FACTURA_FECHAANIO = 6;
	    public static final int COL_FACTURA_FECHAHORA = 7;
	    public static final int COL_FACTURA_FECHAMINUTO = 8;
	    public static final int COL_FACTURA_FECHALIMITEEMISIONDIA = 9;
	    public static final int COL_FACTURA_FECHALIMITEEMISIONMES = 10;
	    public static final int COL_FACTURA_FECHALIMITEEMISIONANIO = 11;
	    public static final int COL_FACTURA_FECHALIMITEEMISIONHORA = 12;
	    public static final int COL_FACTURA_FECHALIMITEEMISIONMINUTO = 13;
	    public static final int COL_FACTURA_MONTOTOTAL = 14;
	    public static final int COL_FACTURA_DESCUENTO = 15;
	    public static final int COL_FACTURA_MONTOFINAL = 16;
	    public static final int COL_FACTURA_MONTOPALABRAS = 17;
	    public static final int COL_FACTURA_CODIGOAUTORIZACION = 18;
	    public static final int COL_FACTURA_CODIGOCONTROL = 19;
	    public static final int COL_FACTURA_FACTURATIPOID = 20;
	    public static final int COL_FACTURA_ALMACENID = 21;
	    public static final int COL_FACTURA_ANULADA = 22;
	    public static final int COL_FACTURA_ANULACIONUSUARIOID = 23;
	    public static final int COL_FACTURA_ANULACIONMOTIVO = 24;
	    public static final int COL_FACTURA_ANULACIONFECHADIA = 25;
	    public static final int COL_FACTURA_ANULACIONFECHAMES = 26;
	    public static final int COL_FACTURA_ANULACIONFECHAANIO = 27;
	    public static final int COL_FACTURA_DOSIFICACIONID = 28;
	    public static final int COL_FACTURA_EMPLEADOID = 29;
	    public static final int COL_FACTURA_QRTAMANO = 30;
	    public static final int COL_FACTURA_QREXTENSION = 31;
	    public static final int COL_FACTURA_QRBINARIO = 32;
	    public static final int COL_FACTURA_QRMIMETYPE = 33;
	    public static final int COL_FACTURA_CLIENTEID = 34;
	    public static final int COL_FACTURA_NUMEROITEMS = 35;
	    public static final int COL_FACTURA_SINCRONIZACION = 36;
	    public static final int COL_FACTURA_IMPRESO = 37;
	    public static final int COL_FACTURA_VENTAID = 38;
	    public static final int COL_FACTURA_SERVERVENTAID = 39;
	    public static final int COL_FACTURA_NITNUEVO = 40;
	    public static final int COL_FACTURA_NOFACTURA = 41;
	    public static final int COL_FACTURA_TIPONIT = 42;

	    public static final String[] FACTURA_ALL_KEYS = new String[] {
	    	KEY_FACTURA_ROW_ID,KEY_FACTURA_NUMERO,KEY_FACTURA_NOMBRE,KEY_FACTURA_NIT,KEY_FACTURA_FECHADIA,
		    KEY_FACTURA_FECHAMES,KEY_FACTURA_FECHAANIO,KEY_FACTURA_FECHAHORA,KEY_FACTURA_FECHAMINUTO,
		    KEY_FACTURA_FECHALIMITEEMISIONDIA,KEY_FACTURA_FECHALIMITEEMISIONMES,KEY_FACTURA_FECHALIMITEEMISIONANIO,
		    KEY_FACTURA_FECHALIMITEEMISIONHORA,KEY_FACTURA_FECHALIMITEEMISIONMINUTO,KEY_FACTURA_MONTOTOTAL,
		    KEY_FACTURA_DESCUENTO,KEY_FACTURA_MONTOFINAL,KEY_FACTURA_MONTOPALABRAS,KEY_FACTURA_CODIGOAUTORIZACION,
		    KEY_FACTURA_CODIGOCONTROL,KEY_FACTURA_FACTURATIPOID,KEY_FACTURA_ALMACENID,KEY_FACTURA_ANULADA,
		    KEY_FACTURA_ANULACIONUSUARIOID,KEY_FACTURA_ANULACIONMOTIVO,KEY_FACTURA_ANULACIONFECHADIA,
		    KEY_FACTURA_ANULACIONFECHAMES,KEY_FACTURA_ANULACIONFECHAANIO,KEY_FACTURA_DOSIFICACIONID,
		    KEY_FACTURA_EMPLEADOID,KEY_FACTURA_QRTAMANO,KEY_FACTURA_QREXTENSION,KEY_FACTURA_QRBINARIO,
		    KEY_FACTURA_QRMIMETYPE,KEY_FACTURA_CLIENTEID,KEY_FACTURA_NUMEROITEMS,KEY_FACTURA_SINCRONIZACION,
		    KEY_FACTURA_IMPRESO,KEY_FACTURA_VENTAID,KEY_FACTURA_SERVERVENTAID,KEY_FACTURA_NITNUEVO,
		    KEY_FACTURA_NOFACTURA,KEY_FACTURA_TIPONIT};
	    
	    public static final String FACTURA_TABLE_NAME = "tbl_Factura";
	    
	    public static final String FACTURA_TABLE_CREATE = "CREATE TABLE " + FACTURA_TABLE_NAME + " ("
	    		+ KEY_FACTURA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_FACTURA_NUMERO + " text NOT NULL,"
	    		+ KEY_FACTURA_NOMBRE + " text NOT NULL,"
	    		+ KEY_FACTURA_NIT + " text NOT NULL,"
	    		+ KEY_FACTURA_FECHADIA + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHAMES + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHAANIO + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHAHORA + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHAMINUTO + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHALIMITEEMISIONDIA + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHALIMITEEMISIONMES + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHALIMITEEMISIONANIO + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHALIMITEEMISIONHORA + " integer NOT NULL,"
	    		+ KEY_FACTURA_FECHALIMITEEMISIONMINUTO + " integer NOT NULL,"
	    		+ KEY_FACTURA_MONTOTOTAL + " float NOT NULL,"
	    		+ KEY_FACTURA_DESCUENTO + " float NOT NULL,"
	    		+ KEY_FACTURA_MONTOFINAL + " float NOT NULL,"
	    		+ KEY_FACTURA_MONTOPALABRAS + " text NOT NULL,"
	    		+ KEY_FACTURA_CODIGOAUTORIZACION + " text NOT NULL,"
	    		+ KEY_FACTURA_CODIGOCONTROL + " text NOT NULL,"
	    		+ KEY_FACTURA_FACTURATIPOID + " text NOT NULL,"
	    		+ KEY_FACTURA_ALMACENID + " integer NOT NULL,"
	    		+ KEY_FACTURA_ANULADA + " boolean NOT NULL,"
	    		+ KEY_FACTURA_ANULACIONUSUARIOID + " integer NOT NULL,"
	    		+ KEY_FACTURA_ANULACIONMOTIVO + " text NOT NULL,"
	    		+ KEY_FACTURA_ANULACIONFECHADIA + " integer NOT NULL,"
	    		+ KEY_FACTURA_ANULACIONFECHAMES + " integer NOT NULL,"
	    		+ KEY_FACTURA_ANULACIONFECHAANIO + " integer NOT NULL,"
	    		+ KEY_FACTURA_DOSIFICACIONID + " integer NOT NULL,"
	    		+ KEY_FACTURA_EMPLEADOID + " integer NOT NULL,"
	    		+ KEY_FACTURA_QRTAMANO + " long NOT NULL,"
	    		+ KEY_FACTURA_QREXTENSION + " text NOT NULL,"
	    		+ KEY_FACTURA_QRBINARIO + " text NOT NULL,"
	    		+ KEY_FACTURA_QRMIMETYPE + " text NOT NULL,"
	    		+ KEY_FACTURA_CLIENTEID + " integer NOT NULL,"	    		
	    		+ KEY_FACTURA_NUMEROITEMS + " integer NOT NULL,"
	    		+ KEY_FACTURA_SINCRONIZACION + " boolean NOT NULL,"
	    		+ KEY_FACTURA_IMPRESO + " boolean NOT NULL,"
	    		+ KEY_FACTURA_VENTAID + " integer NOT NULL,"
	    		+ KEY_FACTURA_SERVERVENTAID + " integer NOT NULL, "
	    		+ KEY_FACTURA_NITNUEVO + " boolean NOT NULL, "
	    		+ KEY_FACTURA_NOFACTURA + " integer NOT NULL, "
	    		+ KEY_FACTURA_TIPONIT + " text NOT NULL);";
	    
	    public boolean borrarFacturaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(FACTURA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarFacturas()
	    {
	      Cursor localCursor = obtenerFacturas();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarFacturaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarFactura(String numero, String nombre, String nit, int fechaDia,int fechaMes, 
	    							int fechaAnio,int fechaHora, int fechaMinuto,int fechaLimiteEmisionDia, 
	    							int fechaLimiteEmisionMes,int fechaLimiteEmisionAnio, int fechaLimiteEmisionHora,
	    							int fechaLimiteEmisionMinuto,float montoTotal, float descuento,float montoFinal, 
	    							String montoPalabras,String codigoAutorizacion, String codigoControl,String facturaTipoId, 
	    							int almacenId,boolean anulada,int anulacionUsuarioId, String anulacionMotivo,
	    							int anulacionFechaDia,int anulacionFechaMes,int anulacionFechaAnio, int dosificacionId, 
	    							int empleadoId,long qrTamano, String qrExtension, String qrBinario,String qrMimeType, 
	    							int clienteId,int numeroItems,boolean sincronizacion,boolean impreso,int ventaId,
	    							int serverVentaId,boolean nitNuevo,int noFactura,String tipoNit)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_numero", String.valueOf(numero));
	      localContentValues.put("_nombre", String.valueOf(nombre));
	      localContentValues.put("_nit", String.valueOf(nit));
	      localContentValues.put("_fechaDia", fechaDia);
	      localContentValues.put("_fechaMes", fechaMes);
	      localContentValues.put("_fechaAnio", fechaAnio);
	      localContentValues.put("_fechaHora", fechaHora);
	      localContentValues.put("_fechaMinuto", fechaMinuto);
	      localContentValues.put("_fechaLimiteEmisionDia", fechaLimiteEmisionDia);
	      localContentValues.put("_fechaLimiteEmisionMes", fechaLimiteEmisionMes);
	      localContentValues.put("_fechaLimiteEmisionAnio", fechaLimiteEmisionAnio);
	      localContentValues.put("_fechaLimiteEmisionHora", fechaLimiteEmisionHora);
	      localContentValues.put("_fechaLimiteEmisionMinuto", fechaLimiteEmisionMinuto);
	      localContentValues.put("_montoTotal", montoTotal);
	      localContentValues.put("_descuento", descuento);
	      localContentValues.put("_montoFinal", montoFinal);
	      localContentValues.put("_montoPalabras", String.valueOf(montoPalabras));
	      localContentValues.put("_codigoAutorizacion", String.valueOf(codigoAutorizacion));
	      localContentValues.put("_codigoControl", String.valueOf(codigoControl));
	      localContentValues.put("_facturaTipoId", String.valueOf(facturaTipoId));
	      localContentValues.put("_almacenId", almacenId);
	      localContentValues.put("_anulada", anulada);
	      localContentValues.put("_anulacionUsuarioId", anulacionUsuarioId);
	      localContentValues.put("_anulacionMotivo", String.valueOf(anulacionMotivo));
	      localContentValues.put("_anulacionFechaDia", anulacionFechaDia);
	      localContentValues.put("_anulacionFechaMes", anulacionFechaMes);
	      localContentValues.put("_anulacionFechaAnio", anulacionFechaAnio);
	      localContentValues.put("_dosificacionId", dosificacionId);
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_qrTamano", qrTamano);
	      localContentValues.put("_qrExtension", String.valueOf(qrExtension));
	      localContentValues.put("_qrBinario", String.valueOf(qrBinario));
	      localContentValues.put("_qrMimeType", String.valueOf(qrMimeType));
	      localContentValues.put("_clienteId", clienteId);
	      localContentValues.put("_numeroItems", numeroItems);
	      localContentValues.put("_sincronizacion", sincronizacion);
	      localContentValues.put("_impreso", impreso);
	      localContentValues.put("_ventaId", ventaId);
	      localContentValues.put("_serverVentaId", serverVentaId);
	      localContentValues.put("_nitNuevo", nitNuevo);
	      localContentValues.put("_noFactura", noFactura);
	      localContentValues.put("_tipoNit", String.valueOf(tipoNit));
	      return this.db.insert(FACTURA_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerFacturas()
	    {
	      Cursor localCursor = this.db.query(true,FACTURA_TABLE_NAME, FACTURA_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerFacturasNoSincronizadas()
	    {
	    	String str = "_sincronizacion = 0";
	    	Cursor localCursor = this.db.query(true,FACTURA_TABLE_NAME, FACTURA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerFacturasNoImpresas()
	    {
	    	String str = "_impreso = 0";
	    	Cursor localCursor = this.db.query(true,FACTURA_TABLE_NAME, FACTURA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerFacturasPorVentaId(int ventaId)
	    {
	    	String str = "_ventaId = " + ventaId;
	    	Cursor localCursor = this.db.query(true,FACTURA_TABLE_NAME, FACTURA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerFacturasPorRowId(int rowId)
	    {
	    	String str = "_id = " + rowId;
	    	Cursor localCursor = this.db.query(true,FACTURA_TABLE_NAME, FACTURA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public int modificarEstadoImpresionPorVentaId(int ventaId,boolean estado)
	    {
	    	String str = "_ventaId=" + ventaId;
	        ContentValues localContentValues = new ContentValues();
	        localContentValues.put("_impreso", estado);
	        return this.db.update(FACTURA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarFacturasincronizacion(int rowId,boolean estado)
	    {
	    	String str = "_id=" + rowId;
	        ContentValues localContentValues = new ContentValues();
	        localContentValues.put("_sincronizacion", estado);
	        return this.db.update(FACTURA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarFacturaServerVentaIdPorVentaId(int ventaId,int serverVentaId)
	    {
	    	String str = "_ventaId=" + ventaId;
	        ContentValues localContentValues = new ContentValues();
	        localContentValues.put("_serverVentaId", serverVentaId);
	        return this.db.update(FACTURA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	    public int modificarFacturaDatosFacturaPorVentaId(int ventaId,String nombreFactura,String nit,boolean nitNuevo)
	    {
	    	String str = "_ventaId=" + ventaId;
	        ContentValues localContentValues = new ContentValues();
	        localContentValues.put("_nombre", nombreFactura);
	        localContentValues.put("_nit", nit);
	        localContentValues.put("_nitNuevo", nitNuevo);
	        return this.db.update(FACTURA_TABLE_NAME, localContentValues, str, null);
	    }
	    
	  //FACTURAPRODUCTO//
	    public static final String KEY_FACTURAPRODUCTO_ROW_ID = "_id";
	    public static final String KEY_FACTURAPRODUCTO_FACTURAID = "_facturaId";
	    public static final String KEY_FACTURAPRODUCTO_PRODUCTOID = "_productoId";
	    public static final String KEY_FACTURAPRODUCTO_PROMOCIONID = "_promocionId";
	    public static final String KEY_FACTURAPRODUCTO_CANTIDAD = "_cantidad";
	    public static final String KEY_FACTURAPRODUCTO_CANTIDADPAQUETE = "_cantidadPaquete";
	    public static final String KEY_FACTURAPRODUCTO_MONTO = "_monto";
	    public static final String KEY_FACTURAPRODUCTO_DESCUENTO = "_descuento";
	    public static final String KEY_FACTURAPRODUCTO_MONTOFINAL = "_montoFinal";
	    public static final String KEY_FACTURAPRODUCTO_CLIENTEID = "_clienteId";
	    public static final String KEY_FACTURAPRODUCTO_EMPLEADOID = "_empleadoId";
	    public static final String KEY_FACTURAPRODUCTO_SINCRONIZACION = "_sincronizacion";
	    public static final String KEY_FACTURAPRODUCTO_PRECIOID = "_precioId";
	    public static final String KEY_FACTURAPRODUCTO_NOFACTURA = "_noFactura";
	    
	    public static final int COL_FACTURAPRODUCTO_ROW_ID = 0;
	    public static final int COL_FACTURAPRODUCTO_FACTURAID = 1;	    
	    public static final int COL_ACTURAPRODUCTO_PRODUCTOID = 2;
	    public static final int COL_FACTURAPRODUCTO_PROMOCIONID = 3;
	    public static final int COL_FACTURAPRODUCTO_CANTIDAD = 4;
	    public static final int COL_FACTURAPRODUCTO_CANTIDADPAQUETE = 5;
	    public static final int COL_FACTURAPRODUCTO_MONTO  = 6;
	    public static final int COL_FACTURAPRODUCTO_DESCUENTO = 7;
	    public static final int COL_FACTURAPRODUCTO_MONTOFINA= 8;
	    public static final int COL_FACTURAPRODUCTO_CLIENTEID = 9;
	    public static final int COL_FACTURAPRODUCTO_EMPLEADOID = 10;
	    public static final int COL_FACTURAPRODUCTO_SINCRONIZACION = 11;
	    public static final int COL_FACTURAPRODUCTO_PRECIOID = 12;
	    public static final int COL_FACTURAPRODUCTO_NOFACTURA = 13;
	    
	    public static final String[] FACTURAPRODUCTO_ALL_KEYS = new String[] {
	    	KEY_FACTURAPRODUCTO_ROW_ID,KEY_FACTURAPRODUCTO_FACTURAID,KEY_FACTURAPRODUCTO_PRODUCTOID,
	    	KEY_FACTURAPRODUCTO_PROMOCIONID,KEY_FACTURAPRODUCTO_CANTIDAD,KEY_FACTURAPRODUCTO_CANTIDADPAQUETE,
	    	KEY_FACTURAPRODUCTO_MONTO,KEY_FACTURAPRODUCTO_DESCUENTO,KEY_FACTURAPRODUCTO_MONTOFINAL,
	    	KEY_FACTURAPRODUCTO_CLIENTEID,KEY_FACTURAPRODUCTO_EMPLEADOID,KEY_FACTURAPRODUCTO_SINCRONIZACION,
	    	KEY_FACTURAPRODUCTO_PRECIOID,KEY_FACTURAPRODUCTO_NOFACTURA};
	    
	    public static final String FACTURAPRODUCTO_TABLE_NAME = "tbl_FacturaProducto";
	    
	    public static final String FACTURAPRODUCTO_TABLE_CREATE = "CREATE TABLE " + FACTURAPRODUCTO_TABLE_NAME + " ("
	    		+ KEY_FACTURAPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_FACTURAPRODUCTO_FACTURAID + " long NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_PRODUCTOID + " integer NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_PROMOCIONID + " integer NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_CANTIDAD + " integer NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_CANTIDADPAQUETE + " integer NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_MONTO + " float NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_DESCUENTO + " float NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_MONTOFINAL + " float NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_CLIENTEID + " integer NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_EMPLEADOID + " integer NOT NULL,"
	    		+ KEY_FACTURAPRODUCTO_SINCRONIZACION + " boolean NOT NULL, "
	    		+ KEY_FACTURAPRODUCTO_PRECIOID + " integer NOT NULL, "
	    		+ KEY_FACTURAPRODUCTO_NOFACTURA + " integer NOT NULL);";
	    
	    public boolean borrarFacturaProductoPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(FACTURAPRODUCTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarFacturasProducto()
	    {
	      Cursor localCursor = obtenerFacturasProducto();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	          borrarFacturaProductoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarFacturaProducto(long facturaId,int productoId, int promocionId, int cantidad,int cantidadPaquete,
	    									float monto, float descuento,	float montoFinal, int clienteId, int empleadoId,
	    									boolean sincronizacion,int precioId,int noFactura)
	    {
	    	ContentValues localContentValues = new ContentValues();
	    	localContentValues.put("_facturaId", facturaId);
	    	localContentValues.put("_productoId", productoId);
	    	localContentValues.put("_promocionId", promocionId);
	    	localContentValues.put("_cantidad", cantidad);
	    	localContentValues.put("_cantidadPaquete", cantidadPaquete);
	    	localContentValues.put("_monto", monto);
	    	localContentValues.put("_descuento", descuento);
	    	localContentValues.put("_montoFinal", montoFinal);
	    	localContentValues.put("_clienteId", clienteId);
	    	localContentValues.put("_empleadoId", empleadoId);
	    	localContentValues.put("_sincronizacion", sincronizacion);
	    	localContentValues.put("_precioId", precioId);
	    	localContentValues.put("_noFactura", noFactura);
	    	return this.db.insert(FACTURAPRODUCTO_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerFacturasProducto()
	    {
	    	Cursor localCursor = this.db.query(true,FACTURAPRODUCTO_TABLE_NAME,FACTURAPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerFacturasProductoNoSincronizadasPorFacturaId(int facturaId)
	    {
	    	String str = "_facturaId = "+ facturaId + " and _sincronizacion = 0";
	    	Cursor localCursor = this.db.query(true,FACTURAPRODUCTO_TABLE_NAME,FACTURAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerFacturasProductoPorFacturaId(int facturaId)
	    {
	    	String str = "_facturaId = " + facturaId;
	    	Cursor localCursor = this.db.query(true,FACTURAPRODUCTO_TABLE_NAME,FACTURAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public int modificarFacturaProductoSincronizacion(int rowId,boolean sincronizacion)
	    {
	      String str = "_id =" + rowId;
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_sincronizacion", sincronizacion);
	      return this.db.update(FACTURAPRODUCTO_TABLE_NAME, localContentValues, str, null);
	    }
	    
	  //PROMOCIONPRECIOLISTA//
	    public static final String KEY_PROMOCIONPRECIOLISTA_ROW_ID = "_id";
	    public static final String KEY_PROMOCIONPRECIOLISTA_PROMOCIONID = "_promocionId";
	    public static final String KEY_PROMOCIONPRECIOLISTA_PRECIOLISTAID = "_precioListaId";
	    
	    public static final int COL_PROMOCIONPRECIOLISTA_ROW_ID = 0;
	    public static final int COL_PROMOCIONPRECIOLISTA_PROMOCIONID = 1;
	    public static final int COL_PROMOCIONPRECIOLISTA_PRECIOLISTAID = 2;
	    
	    public static final String[] PROMOCIONPRECIOLISTA_ALL_KEYS = new String[] {
	    	KEY_FACTURAPRODUCTO_ROW_ID,KEY_PROMOCIONPRECIOLISTA_PROMOCIONID,KEY_PROMOCIONPRECIOLISTA_PRECIOLISTAID};
	    
	    public static final String PROMOCIONPRECIOLISTA_TABLE_NAME = "tbl_PromocionPrecioLista";
	    
	    public static final String PROMOCIONPRECIOLISTA_TABLE_CREATE = "CREATE TABLE " + PROMOCIONPRECIOLISTA_TABLE_NAME + " ("
	    		+ KEY_PROMOCIONPRECIOLISTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_PROMOCIONPRECIOLISTA_PROMOCIONID + " integer NOT NULL,"
	    		+ KEY_PROMOCIONPRECIOLISTA_PRECIOLISTAID + " integer NOT NULL);";
	    
	    public boolean borrarPromocionPrecioListaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(PROMOCIONPRECIOLISTA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPromocionesPrecioLista()
	    {
	      Cursor localCursor = obtenerPromocionesPrecioLista();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	        	borrarPromocionPrecioListaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPromocionPrecioLista(ArrayList<PromocionPrecioListWSResult> promocionesPrecioLista)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_PromocionPrecioLista(_promocionId, _precioListaId) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (PromocionPrecioListWSResult item : promocionesPrecioLista) {

					stmt.bindLong(1, item.getPromocionId());
					stmt.bindLong(2, item.getPrecioListaId());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerPromocionesPrecioLista()
	    {
	    	Cursor localCursor = this.db.query(true,PROMOCIONPRECIOLISTA_TABLE_NAME,PROMOCIONPRECIOLISTA_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionPrecioListaPor(int promocionId)
	    {
	    	String str = "_promocionId = " + promocionId;
	    	Cursor localCursor = this.db.query(true,PROMOCIONPRECIOLISTA_TABLE_NAME,PROMOCIONPRECIOLISTA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    //DATOSFACTURA//
	    public static final String KEY_DATOSFACTURA_ROW_ID = "_id";
	    public static final String KEY_DATOSFACTURA_NOMBREEMPRESA = "_nombreEmpresa";
	    public static final String KEY_DATOSFACTURA_NIT = "_nit";
	    
	    public static final int COL_DATOSFACTURA_ROW_ID = 0;
	    public static final int COL_DATOSFACTURA_NOMBREEMPRESA = 1;
	    public static final int COL_DATOSFACTURA_NIT = 2;
	    
	    public static final String[] DATOSFACTURA_ALL_KEYS = new String[] {
	    	KEY_DATOSFACTURA_ROW_ID,KEY_DATOSFACTURA_NOMBREEMPRESA,KEY_DATOSFACTURA_NIT,};
	    
	    public static final String DATOSFACTURA_TABLE_NAME = "tbl_DatosFactura";
	    
	    public static final String DATOSFACTURA_TABLE_CREATE = "CREATE TABLE " + DATOSFACTURA_TABLE_NAME + " ("
	    		+ KEY_DATOSFACTURA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_DATOSFACTURA_NOMBREEMPRESA + " text NOT NULL,"
	    		+ KEY_DATOSFACTURA_NIT + " text NOT NULL);";
	    
	    public boolean borrarDatosFacturaPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(DATOSFACTURA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarDatosFactura()
	    {
	      Cursor localCursor = obtenerDatosFactura();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	        	borrarDatosFacturaPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarDatosFactura(DatosFacturaWSResult datosFactura)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_DatosFactura(_nombreEmpresa, _nit) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				//for (int i = 0; i < datosFactura.getPropertyCount(); i++) {
				//	SoapObject soapObject = (SoapObject) datosFactura.getProperty(i);

					stmt.bindString(1, datosFactura.getNombreEmpresa());
					stmt.bindString(2, datosFactura.getNit());

					stmt.executeInsert();
					stmt.clearBindings();
				//}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerDatosFactura()
	    {
	    	Cursor localCursor = this.db.query(true,DATOSFACTURA_TABLE_NAME,DATOSFACTURA_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	  //EMPLEADO//
	    public static final String KEY_EMPLEADO_ROW_ID = "_id";
	    public static final String KEY_EMPLEADO_EMPLEADOID = "_empleadoId";
	    public static final String KEY_EMPLEADO_NOMBRECOMPLETO = "_nombreCompleto";
	        
	    public static final int COL_EMPLEADO_ROW_ID = 0;
	    public static final int COL_EMPLEADO_EMPLEADOID = 1;
	    public static final int COL_EMPLEADO_NOMBRECOMPLETO = 2;

	    
	    public static final String[] EMPLEADO_ALL_KEYS = new String[] {
	    	KEY_EMPLEADO_ROW_ID,KEY_EMPLEADO_EMPLEADOID,KEY_EMPLEADO_NOMBRECOMPLETO};
	    
	    public static final String EMPLEADO_TABLE_NAME = "tbl_Empleado";
	    
	    public static final String EMPLEADO_TABLE_CREATE = "CREATE TABLE " + EMPLEADO_TABLE_NAME + " ("
	    		+ KEY_EMPLEADO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_EMPLEADO_EMPLEADOID + " integer NOT NULL,"
	    		+ KEY_EMPLEADO_NOMBRECOMPLETO + " text NOT NULL);";
	    
	    public boolean borrarEmpleadoPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(EMPLEADO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarEmpleados()
	    {
	      Cursor localCursor = obtenerEmpleados();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	        	borrarEmpleadoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarEmpleado(ArrayList<EmpleadoWSResult> empleados)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_Empleado(_empleadoId, _nombreCompleto) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (EmpleadoWSResult item : empleados) {

					stmt.bindLong(1, item.getEmpleadoId());
					stmt.bindString(2, item.getNombreCompleto());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerEmpleados()
	    {
	    	Cursor localCursor = this.db.query(true,EMPLEADO_TABLE_NAME,EMPLEADO_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerEmpleadosPor(int empleadoId)
	    {
	    	String str = "_empleadoId = "+ empleadoId;
	    	Cursor localCursor = this.db.query(true,EMPLEADO_TABLE_NAME,EMPLEADO_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    //IMPRESORA//
	    public static final String KEY_IMPRESORA_ROW_ID = "_id";
	    public static final String KEY_IMPRESORA_IMPRESORAID = "_impresoraId";
	    public static final String KEY_IMPRESORA_NOMBRE = "_nombre";
	    public static final String KEY_IMPRESORA_NROSERIE = "_nroSerie";
	    public static final String KEY_IMPRESORA_MARCA = "_marca";
	    public static final String KEY_IMPRESORA_MODELO = "_modelo";
	    public static final String KEY_IMPRESORA_ADDRESS = "_address";
	    
	    public static final int COL_IMPRESORA_ROW_ID = 0;
	    public static final int COL_IMPRESORA_IMPRESORAID = 1;
	    public static final int COL_IMPRESORA_NOMBRE = 2;
	    public static final int COL_IMPRESORA_NROSERIE = 3;
	    public static final int COL_IMPRESORA_MARCA = 4;
	    public static final int COL_IMPRESORA_MODELO = 5;
	    public static final int COL_IMPRESORA_ADDRESS = 6;

	    public static final String[] IMPRESORA_ALL_KEYS = new String[] {
	    	KEY_IMPRESORA_ROW_ID,KEY_IMPRESORA_IMPRESORAID,KEY_IMPRESORA_NOMBRE,
	    	KEY_IMPRESORA_NROSERIE,KEY_IMPRESORA_MARCA,KEY_IMPRESORA_MODELO,KEY_IMPRESORA_ADDRESS};
	    
	    public static final String IMPRESORA_TABLE_NAME = "tbl_Impresora";
	    
	    public static final String IMPRESORA_TABLE_CREATE = "CREATE TABLE " + IMPRESORA_TABLE_NAME + " ("
	    		+ KEY_IMPRESORA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_IMPRESORA_IMPRESORAID + " integer NOT NULL,"
	    		+ KEY_IMPRESORA_NOMBRE + " text NOT NULL,"
	    		+ KEY_IMPRESORA_NROSERIE + " text NOT NULL,"
	    		+ KEY_IMPRESORA_MARCA + " text NOT NULL,"
	    		+ KEY_IMPRESORA_MODELO + " text NOT NULL,"
	    		+ KEY_IMPRESORA_ADDRESS + " text NOT NULL);";
	    
	    public boolean borrarImpresoraPor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(IMPRESORA_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarImpresoras()
	    {
	      Cursor localCursor = obtenerImpresoras();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	        	borrarImpresoraPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarImpresora(ArrayList<ImpresoraWSResult> impresoras)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_Impresora(_impresoraId, _nombre, _nombre, _nroSerie, _marca, _modelo, _address) VALUES (?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (ImpresoraWSResult item : impresoras) {

					stmt.bindLong(1, item.getImpresoraId());
					stmt.bindString(2, item.getNombre());
					stmt.bindString(3, item.getNroSerie());
					stmt.bindString(4, item.getMarca());
					stmt.bindString(5, item.getModelo());
					stmt.bindString(6, item.getAddress());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerImpresoras()
	    {
	    	Cursor localCursor = this.db.query(true,IMPRESORA_TABLE_NAME,IMPRESORA_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerImpresoraPor(int impresoraId)
	    {
	    	String str = "_impresoraId = "+ impresoraId;
	    	Cursor localCursor = this.db.query(true,IMPRESORA_TABLE_NAME,IMPRESORA_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    //APKRUTACLIENTE//
	    public static final String KEY_APKRUTACLIENTE_ROW_ID = "_id";
	    public static final String KEY_APKRUTACLIENTE_RUTAID = "_rutaId";
	    public static final String KEY_APKRUTACLIENTE_DIAID = "_diaId";
	    public static final String KEY_APKRUTACLIENTE_RUTANOMBRE = "_rutaNombre";
	    public static final String KEY_APKRUTACLIENTE_DIANOMBRE = "_diaNombre";
	    public static final String KEY_APKRUTACLIENTE_BLOQUEARPREVENTADISTANCIA = "_bloquearPreventaDistancia";
	    public static final String KEY_APKRUTACLIENTE_RUTACOMPLETA = "_rutaCompleta";
	    
	    public static final int COL_APKRUTACLIENTE_ROW_ID = 0;
	    public static final int COL_APKRUTACLIENTE_RUTAID = 1;
	    public static final int COL_APKRUTACLIENTE_DIAID = 2;
	    public static final int COL_APKRUTACLIENTE_RUTANOMBRE = 3;
	    public static final int COL_APKRUTACLIENTE_DIANOMBRE = 4;
	    public static final int COL_APKRUTACLIENTE_BLOQUEARPREVENTADISTANCIA = 5;
	    public static final int COL_APKRUTACLIENTE_RUTACOMPLETA = 6;
	    
	    public static final String[] APKRUTACLIENTE_ALL_KEYS = new String[] {
	    	KEY_APKRUTACLIENTE_ROW_ID,KEY_APKRUTACLIENTE_RUTAID,KEY_APKRUTACLIENTE_DIAID,
	    	KEY_APKRUTACLIENTE_RUTANOMBRE,KEY_APKRUTACLIENTE_DIANOMBRE,KEY_APKRUTACLIENTE_BLOQUEARPREVENTADISTANCIA,
	    	KEY_APKRUTACLIENTE_RUTACOMPLETA};
	    
	    public static final String APKRUTACLIENTE_TABLE_NAME = "tbl_apkRutaCliente";
	    
	    public static final String APKRUTACLIENTE_TABLE_CREATE = "CREATE TABLE " + APKRUTACLIENTE_TABLE_NAME + " ("
	    		+ KEY_APKRUTACLIENTE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
	    		+ KEY_APKRUTACLIENTE_RUTAID + " integer NOT NULL,"
	    		+ KEY_APKRUTACLIENTE_DIAID + " integer NOT NULL,"
	    		+ KEY_APKRUTACLIENTE_RUTANOMBRE + " text NOT NULL,"
	    		+ KEY_APKRUTACLIENTE_DIANOMBRE + " text NOT NULL, "
	    		+ KEY_APKRUTACLIENTE_BLOQUEARPREVENTADISTANCIA + " boolean NOT NULL, "
	    		+ KEY_APKRUTACLIENTE_RUTACOMPLETA + " boolean NOT NULL);";
	    
	    public boolean borrarApkRutaClientePor(long id)
	    {
	      String str = "_id=" + id;
	      return this.db.delete(APKRUTACLIENTE_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarApksRutaCliente()
	    {
	      Cursor localCursor = obtenerApksRutaCliente();
	      long l = localCursor.getColumnIndexOrThrow("_id");
	      if (localCursor.moveToFirst()) 
	      {
	        do
	        {
	        	borrarApkRutaClientePor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }

	public long insertarApkRutaCliente(ArrayList<ApkRutaClienteWSResult> apkRutas)
	{
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ApkRutaCliente(_rutaId, _diaId, _rutaNombre, _diaNombre, _bloquearPreventaDistancia, _rutaCompleta) VALUES (?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ApkRutaClienteWSResult item : apkRutas) {

				stmt.bindLong(1, item.getRutaId());
				stmt.bindLong(2, item.getDiaId());
				stmt.bindString(3, item.getRutaNombre());
				stmt.bindString(4, item.getDiaNombre());
				stmt.bindLong(5, item.isBloquearPreventaDistancia()?1:0);
				stmt.bindLong(6, item.isRutaCompleta()?1:0);

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
	}

	    public long insertarApkRutaCliente(int rutaId,int diaId,String rutaNombre,String diaNombre,boolean bloquearPreventaDistancia,boolean rutaCompleta)
	    {
	    	ContentValues localContentValues = new ContentValues();
	    	localContentValues.put("_rutaId", rutaId);
	    	localContentValues.put("_diaId", String.valueOf(diaId));
	    	localContentValues.put("_rutaNombre", String.valueOf(rutaNombre));
	    	localContentValues.put("_diaNombre", String.valueOf(diaNombre));
	    	localContentValues.put("_bloquearPreventaDistancia", bloquearPreventaDistancia);
	    	localContentValues.put("_rutaCompleta", rutaCompleta);
	    	return this.db.insert(APKRUTACLIENTE_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerApksRutaCliente()
	    {
	    	Cursor localCursor = this.db.query(true,APKRUTACLIENTE_TABLE_NAME,APKRUTACLIENTE_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerApkRutaClientePor(int rutaId)
	    {
	    	String str = "_rutaId = "+ rutaId;
	    	Cursor localCursor = this.db.query(true,APKRUTACLIENTE_TABLE_NAME,APKRUTACLIENTE_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	  //CIERREDISTRIBUIDOR//
	    public static final String KEY_CIERREDISTRIBUIDOR_ROW_ID = "_Id";
	    public static final String KEY_CIERREDISTRIBUIDOR_EMPLEADOID = "_empleadoId";
	    public static final String KEY_CIERREDISTRIBUIDOR_ANIO = "_anio";
	    public static final String KEY_CIERREDISTRIBUIDOR_MES = "_mes";
	    public static final String KEY_CIERREDISTRIBUIDOR_DIA = "_dia";

	    public static final int COL_CIERREDISTRIBUIDOR_ROW_ID = 0;
	    public static final int COL_CIERREDISTRIBUIDOR_EMPLEADOID = 1;
	    public static final int COL_CIERREDISTRIBUIDOR_ANIO = 2;
	    public static final int COL_CIERREDISTRIBUIDOR_MES = 3;
	    public static final int COL_CIERREDISTRIBUIDOR_DIA = 4;	    
	    
	    public static final String[] CIERREDISTRIBUIDOR_ALL_KEYS = new String[] { 
	    	KEY_CIERREDISTRIBUIDOR_ROW_ID,KEY_CIERREDISTRIBUIDOR_EMPLEADOID,KEY_CIERREDISTRIBUIDOR_ANIO,
	    	KEY_CIERREDISTRIBUIDOR_MES,KEY_CIERREDISTRIBUIDOR_DIA};
	    
	    public static final String CIERREDISTRIBUIDOR_TABLE_NAME = "tbl_CierreDistribuidor";
	    
	    public static final String CIERREDISTRIBUIDOR_TABLE_CREATE = "CREATE TABLE " + CIERREDISTRIBUIDOR_TABLE_NAME + "("
	    		+ KEY_CIERREDISTRIBUIDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_CIERREDISTRIBUIDOR_EMPLEADOID + " integer NOT NULL, "
	    		+ KEY_CIERREDISTRIBUIDOR_ANIO + " integer NOT NULL, "
	    		+ KEY_CIERREDISTRIBUIDOR_MES + " integer NOT NULL, "
	    		+ KEY_CIERREDISTRIBUIDOR_DIA + " integer NOT NULL);";
	    
	    public boolean borrarCierreDistribuidorPor(long rowId)
	    {
	      String str = "_Id=" + rowId;
	      return this.db.delete(CIERREDISTRIBUIDOR_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarCierresDistribuidor()
	    {
	      Cursor localCursor = obtenerCierresDistribuidor();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) {
	        do
	        {
	          borrarCierreDistribuidorPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarCierreDistribuidor(int empleadoId,int anio,int mes,int dia)
	    {
	      ContentValues localContentValues = new ContentValues();
	      localContentValues.put("_empleadoId", empleadoId);
	      localContentValues.put("_anio", anio);
	      localContentValues.put("_mes", mes);
	      localContentValues.put("_dia", dia);
	      return this.db.insert(CIERREDISTRIBUIDOR_TABLE_NAME, null, localContentValues);
	    }
	    
	    public Cursor obtenerCierresDistribuidor()
	    {
	      Cursor localCursor = this.db.query(true,CIERREDISTRIBUIDOR_TABLE_NAME, CIERREDISTRIBUIDOR_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerCierreDistribuidorPorEmpleadoIdFecha(int empleadoId,int anio,int mes,int dia)
	    {
	    	String str = "_empleadoId="+ empleadoId + " and _anio=" + anio + " and _mes=" + mes + " and _dia=" + dia;
	    	Cursor localCursor = this.db.query(true,CIERREDISTRIBUIDOR_TABLE_NAME, CIERREDISTRIBUIDOR_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    //PROMOCIONCOSTO//
	    public static final String KEY_PROMOCIONCOSTO_ROW_ID = "_Id";
	    public static final String KEY_PROMOCIONCOSTO_PROMOCIONID = "_promocionId";
	    public static final String KEY_PROMOCIONCOSTO_COSTOID = "_costoId";

	    public static final int COL_PROMOCIONCOSTO_ROW_ID = 0;
	    public static final int COL_PROMOCIONCOSTO_PROMOCIONID = 1;
	    public static final int COL_PROMOCIONCOSTO_COSTOID = 2;   
	    
	    public static final String[] PROMOCIONCOSTO_ALL_KEYS = new String[] { 
	    	KEY_PROMOCIONCOSTO_ROW_ID,KEY_PROMOCIONCOSTO_PROMOCIONID,KEY_PROMOCIONCOSTO_COSTOID};
	    
	    public static final String PROMOCIONCOSTO_TABLE_NAME = "tbl_PromocionCosto";
	    
	    public static final String PROMOCIONCOSTO_TABLE_CREATE = "CREATE TABLE " + PROMOCIONCOSTO_TABLE_NAME + "("
	    		+ KEY_PROMOCIONCOSTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PROMOCIONCOSTO_PROMOCIONID + " int NOT NULL, "
	    		+ KEY_PROMOCIONCOSTO_COSTOID + " int NOT NULL);";
	    
	    public boolean borrarPromocionCostoPor(long rowId)
	    {
	      String str = "_Id=" + rowId;
	      return this.db.delete(PROMOCIONCOSTO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPromocionesCosto()
	    {
	      Cursor localCursor = obtenerPromocionesCosto();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) {
	        do
	        {
	          borrarPromocionCostoPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPromocionCosto(ArrayList<PromocionCostoWSResult> promocionesCosto)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_PromocionCosto(_promocionId, _costoId) VALUES (?,?)"
			);
			try {
				db.beginTransaction();
				for (PromocionCostoWSResult item : promocionesCosto) {

					stmt.bindLong(1, item.getPromocionId());
					stmt.bindLong(2, item.getCostoId());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerPromocionesCosto()
	    {
	      Cursor localCursor = this.db.query(true,PROMOCIONCOSTO_TABLE_NAME, PROMOCIONCOSTO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromocionCostoPorPromocionId(int promocionId)
	    {
	    	String str = "_promocionId="+ promocionId;
	    	Cursor localCursor = this.db.query(true,PROMOCIONCOSTO_TABLE_NAME, PROMOCIONCOSTO_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }

	    //PROMOCIONPRECIO//
	    public static final String KEY_PROMOCIONPRECIO_ROW_ID = "_Id";
	    public static final String KEY_PROMOCIONPRECIO_PRECIOID = "_precioId";
	    public static final String KEY_PROMOCIONPRECIO_PROMOCIONID = "_promocionId";
	    public static final String KEY_PROMOCIONPRECIO_PRECIOLISTAID = "_precioListaId";
	    public static final String KEY_PROMOCIONPRECIO_PRECIO = "_precio";
	    public static final String KEY_PROMOCIONPRECIO_PRECIOORIGINAL = "_precioOriginal";

	    public static final int COL_PROMOCIONPRECIO_ROW_ID = 0;
	    public static final int COL_PROMOCIONPRECIO_PRECIOID = 1;
	    public static final int COL_PROMOCIONPRECIO_PROMOCIONID = 2;
	    public static final int COL_PROMOCIONPRECIO_PRECIOLISTAID = 3;
	    public static final int COL_PROMOCIONPRECIO_PRECIO = 4;
	    public static final int COL_PROMOCIONPRECIO_PRECIOORIGINAL = 5;
	    
	    public static final String[] PROMOCIONPRECIO_ALL_KEYS = new String[] { 
	    	KEY_PROMOCIONPRECIO_ROW_ID,KEY_PROMOCIONPRECIO_PRECIOID,KEY_PROMOCIONPRECIO_PROMOCIONID,
	    	KEY_PROMOCIONPRECIO_PRECIOLISTAID,KEY_PROMOCIONPRECIO_PRECIO,KEY_PROMOCIONPRECIO_PRECIOORIGINAL};
	    
	    public static final String PROMOCIONPRECIO_TABLE_NAME = "tbl_PromocionPrecio";
	    
	    public static final String PROMOCIONPRECIO_TABLE_CREATE = "CREATE TABLE " + PROMOCIONPRECIO_TABLE_NAME + "("
	    		+ KEY_PROMOCIONPRECIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PROMOCIONPRECIO_PRECIOID + " int NOT NULL, "
	    		+ KEY_PROMOCIONPRECIO_PROMOCIONID + " int NOT NULL, "
	    		+ KEY_PROMOCIONPRECIO_PRECIOLISTAID + " int NOT NULL, "
	    		+ KEY_PROMOCIONPRECIO_PRECIO + " float NOT NULL, "
	    		+ KEY_PROMOCIONPRECIO_PRECIOORIGINAL + " float NOT NULL);";
	    
	    public boolean borrarPromocionPrecioPor(long rowId)
	    {
	      String str = "_Id=" + rowId;
	      return this.db.delete(PROMOCIONPRECIO_TABLE_NAME, str, null) > 0;
	    }
	    
	    public void borrarPromocionesPrecio()
	    {
	      Cursor localCursor = obtenerPromocionesPrecio();
	      long l = localCursor.getColumnIndexOrThrow("_Id");
	      if (localCursor.moveToFirst()) {
	        do
	        {
	          borrarPromocionPrecioPor(localCursor.getLong((int)l));
	        } 
	        while (localCursor.moveToNext());
	      }
	      localCursor.close();
	    }
	    
	    public long insertarPromocionPrecio(ArrayList<PromocionPrecioWSResult> promocionesPrecio)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_PromocionPrecio(_precioId, _promocionId, _precioListaId, _precio, _precioOriginal) VALUES (?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (PromocionPrecioWSResult item: promocionesPrecio) {

					stmt.bindLong(1, item.getPrecioId());
					stmt.bindLong(2, item.getPromocionId());
					stmt.bindLong(3, item.getPrecioListaId());
					stmt.bindDouble(4, item.getPrecio());
					stmt.bindDouble(5, item.getPrecioOriginal());

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	    public Cursor obtenerPromocionesPrecio()
	    {
	      Cursor localCursor = this.db.query(true,PROMOCIONPRECIO_TABLE_NAME, PROMOCIONPRECIO_ALL_KEYS, null, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }
	    
	    public Cursor obtenerPromocionPrecioPorPromocionId(int promocionId)
	    {
	    	String str = "_promocionId="+ promocionId;
	    	Cursor localCursor = this.db.query(true,PROMOCIONPRECIO_TABLE_NAME, PROMOCIONPRECIO_ALL_KEYS, str, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	    }
	    
	    public Cursor obtenerPromocionPrecioPor(int promocionId,int precioListaId)
	    {
	    	String query = "_promocionId = " + promocionId + " and _precioListaId = " + precioListaId;
	      Cursor localCursor = this.db.query(true,PROMOCIONPRECIO_TABLE_NAME, PROMOCIONPRECIO_ALL_KEYS, query, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	        localCursor.moveToFirst();
	      }
	      return localCursor;
	    }

    //APKDISTRUTACLIENTE//
    public static final String KEY_APKDISTRUTACLIENTE_ROW_ID = "_id";
    public static final String KEY_APKDISTRUTACLIENTE_RUTAID = "_rutaId";
    public static final String KEY_APKDISTRUTACLIENTE_DIAID = "_diaId";
    public static final String KEY_APKDISTRUTACLIENTE_RUTANOMBRE = "_rutaNombre";
    public static final String KEY_APKDISTRUTACLIENTE_DIANOMBRE = "_diaNombre";
    
    public static final int COL_APKDISTRUTACLIENTE_ROW_ID = 0;
    public static final int COL_APKDISTRUTACLIENTE_RUTAID = 1;
    public static final int COL_APKDISTRUTACLIENTE_DIAID = 2;
    public static final int COL_APKDISTRUTACLIENTE_RUTANOMBRE = 3;
    public static final int COL_APKDISTRUTACLIENTE_DIANOMBRE = 4;
    
    public static final String[] APKDISTRUTACLIENTE_ALL_KEYS = new String[] {
    	KEY_APKDISTRUTACLIENTE_ROW_ID,KEY_APKDISTRUTACLIENTE_RUTAID,KEY_APKDISTRUTACLIENTE_DIAID,
    	KEY_APKDISTRUTACLIENTE_RUTANOMBRE,KEY_APKDISTRUTACLIENTE_DIANOMBRE};
    
    public static final String APKDISTRUTACLIENTE_TABLE_NAME = "tbl_apkDistRutaCliente";
    
    public static final String APKDISTRUTACLIENTE_TABLE_CREATE = "CREATE TABLE " + APKDISTRUTACLIENTE_TABLE_NAME + " ("
    		+ KEY_APKDISTRUTACLIENTE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_APKDISTRUTACLIENTE_RUTAID + " integer NOT NULL,"
    		+ KEY_APKDISTRUTACLIENTE_DIAID + " integer NOT NULL,"
    		+ KEY_APKDISTRUTACLIENTE_RUTANOMBRE + " text NOT NULL,"
    		+ KEY_APKDISTRUTACLIENTE_DIANOMBRE + " text NOT NULL);";
    
    public boolean borrarApkDistRutaClientePor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(APKDISTRUTACLIENTE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarApksDistRutaCliente()
    {
      Cursor localCursor = obtenerApksDistRutaCliente();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
        	borrarApkDistRutaClientePor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarApkDistRutaCliente(ArrayList<ApkDistRutaClienteWSResult> apksDistRutaCliente)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ApDistRutaCliente(_rutaId, _diaId, _rutaNombre, _diaNombre) VALUES (?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ApkDistRutaClienteWSResult item : apksDistRutaCliente) {

				stmt.bindLong(1, item.getRutaId());
				stmt.bindLong(2, item.getDiaId());
				stmt.bindString(3, item.getRutaNombre());
				stmt.bindString(4, item.getDiaNombre());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerApksDistRutaCliente()
    {
    	Cursor localCursor = this.db.query(true,APKDISTRUTACLIENTE_TABLE_NAME,APKDISTRUTACLIENTE_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerApkDistRutaClientePor(int rutaId)
    {
    	String str = "_rutaId = "+ rutaId;
    	Cursor localCursor = this.db.query(true,APKDISTRUTACLIENTE_TABLE_NAME,APKDISTRUTACLIENTE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //COBROSPENDIENTES//
    public static final String KEY_COBROPENDIENTE_ROW_ID = "_id";
    public static final String KEY_COBROPENDIENTE_VENTAID = "_ventaId";
    public static final String KEY_COBROPENDIENTE_CLIENTEID = "_clienteId";
    public static final String KEY_COBROPENDIENTE_CLIENTENOMBRE = "_clienteNombre";
    public static final String KEY_COBROPENDIENTE_FECHAVENTA = "_fechaVenta";
    public static final String KEY_COBROPENDIENTE_MONTO = "_monto";
    public static final String KEY_COBROPENDIENTE_FECHAVENCIMIENTO = "_fechaVencimiento";
    public static final String KEY_COBROPENDIENTE_DIASMORA = "_diasMora";
    public static final String KEY_COBROPENDIENTE_SALDO = "_saldo";
    
    public static final int COL_COBROPENDIENTE_ROW_ID = 0;
    public static final int COL_COBROPENDIENTE_VENTAID = 1;
    public static final int COL_COBROPENDIENTE_CLIENTEID = 2;
    public static final int COL_COBROPENDIENTE_CLIENTENOMBRE = 3;
    public static final int COL_COBROPENDIENTE_FECHAVENTA = 4;
    public static final int COL_COBROPENDIENTE_MONTO = 5;
    public static final int COL_COBROPENDIENTE_FECHAVENCIMIENTO = 6;
    public static final int COL_COBROPENDIENTE_DIASMORA = 7;
    public static final int COL_COBROPENDIENTE_SALDO = 8;
        
    public static final String[] COBROPENDIENTE_ALL_KEYS = new String[] {
    	KEY_COBROPENDIENTE_ROW_ID,KEY_COBROPENDIENTE_VENTAID,KEY_COBROPENDIENTE_CLIENTEID,
    	KEY_COBROPENDIENTE_CLIENTENOMBRE,KEY_COBROPENDIENTE_FECHAVENTA,KEY_COBROPENDIENTE_MONTO,
    	KEY_COBROPENDIENTE_FECHAVENCIMIENTO,KEY_COBROPENDIENTE_DIASMORA,KEY_COBROPENDIENTE_SALDO};
    
    public static final String COBROPENDIENTE_TABLE_NAME = "tbl_cobroPendiente";
    
    public static final String COBROPENDIENTE_TABLE_CREATE = "CREATE TABLE " + COBROPENDIENTE_TABLE_NAME + " ("
    		+ KEY_COBROPENDIENTE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_COBROPENDIENTE_VENTAID + " integer NOT NULL,"
    		+ KEY_COBROPENDIENTE_CLIENTEID + " integer NOT NULL,"
    		+ KEY_COBROPENDIENTE_CLIENTENOMBRE + " text NOT NULL,"
    		+ KEY_COBROPENDIENTE_FECHAVENTA + " text NOT NULL,"
    		+ KEY_COBROPENDIENTE_MONTO + " float NOT NULL,"
    		+ KEY_COBROPENDIENTE_FECHAVENCIMIENTO + " text NOT NULL,"
    		+ KEY_COBROPENDIENTE_DIASMORA + " integer NOT NULL,"
    		+ KEY_COBROPENDIENTE_SALDO + " float NOT NULL);";
    		
    
    public boolean borrarCobroPendientePor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(COBROPENDIENTE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCobrosPendientes()
    {
    	Cursor localCursor = obtenerCobrosPendientes();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarCobroPendientePor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarCobroPendiente(ArrayList<CobroPendienteWSResult> cobrosPendiente)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_CobroPendiente(_ventaId, _clienteId, _clienteNombre, _fechaVenta, _monto, _fechaVencimiento, _diasMora, _saldo) VALUES (?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (CobroPendienteWSResult item : cobrosPendiente) {

				stmt.bindLong(1, item.getVentaId());
				stmt.bindLong(2, item.getClienteId());
				stmt.bindString(3, item.getCliente());
				stmt.bindString(4, item.getFechaFD());
				stmt.bindDouble(5, item.getMontoFinal());
				stmt.bindString(6, item.getVencimientoFD());
				stmt.bindLong(7, item.getDiasMora());
				stmt.bindDouble(8, item.getSaldo());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerCobrosPendientes()
    {
    	Cursor localCursor = this.db.query(true,COBROPENDIENTE_TABLE_NAME,COBROPENDIENTE_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerCobroPendientePor(int clienteId)
    {
    	String str = "_clienteId = "+ clienteId;
    	Cursor localCursor = this.db.query(true,COBROPENDIENTE_TABLE_NAME,COBROPENDIENTE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //VENTADIRECTA//
    public static final String KEY_VENTADIRECTA_ROW_ID = "_id";
    public static final String KEY_VENTADIRECTA_VENTADIRECTAIDSERVER = "_ventaDirectaIdServer";
    public static final String KEY_VENTADIRECTA_DIA = "_dia";
    public static final String KEY_VENTADIRECTA_MES = "_mes";
    public static final String KEY_VENTADIRECTA_ANIO = "_anio";
    public static final String KEY_VENTADIRECTA_CLIENTEID = "_clienteId";
    public static final String KEY_VENTADIRECTA_MONTO = "_monto";
    public static final String KEY_VENTADIRECTA_DESCUENTO = "_descuento";
    public static final String KEY_VENTADIRECTA_MONTOFINAL = "_montoFinal";
    public static final String KEY_VENTADIRECTA_TIPOPAGOID = "_tipoPagoId";
    public static final String KEY_VENTADIRECTA_LATITUD = "_latitud";
    public static final String KEY_VENTADIRECTA_LONGITUD = "_longitud";		    
    public static final String KEY_VENTADIRECTA_HORA = "_hora";
    public static final String KEY_VENTADIRECTA_MINUTO = "_minuto";
    public static final String KEY_VENTADIRECTA_OBSERVACION = "_observacion";
    public static final String KEY_VENTADIRECTA_EMPLEADOID = "_empleadoId";
    public static final String KEY_VENTADIRECTA_FACTURA = "_factura";
    public static final String KEY_VENTADIRECTA_NIT = "_nit";
    public static final String KEY_VENTADIRECTA_NITNUEVO = "_nitNuevo";
    public static final String KEY_VENTADIRECTA_NOVENTADIRECTA = "_noVentaDirecta";
    public static final String KEY_VENTADIRECTA_ESTADO = "_estado";
    public static final String KEY_VENTADIRECTA_APLICARBONIFICACION = "_aplicarBonificacion";
    public static final String KEY_VENTADIRECTA_TIPONIT = "_tipoNit";
    public static final String KEY_VENTADIRECTA_RUTA = "_ruta";
    public static final String KEY_VENTADIRECTA_TIPOVISITA = "_tipoVisita";
    public static final String KEY_VENTADIRECTA_ZONAVENTAID = "_zonaVentaId";
    public static final String KEY_VENTADIRECTA_PRONTOPAGOID = "_prontoPagoId";
    public static final String KEY_VENTADIRECTA_DESCUENTOCANAL = "_descuentoCanal";
    public static final String KEY_VENTADIRECTA_DESCUENTOAJUSTE = "_descuentoAjuste";
    public static final String KEY_VENTADIRECTA_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
    public static final String KEY_VENTADIRECTA_DESCUENTOOBJETIVO = "_descuentoObjetivo";
    public static final String KEY_VENTADIRECTA_FORMAPAGOID = "_formaPagoId";
    public static final String KEY_VENTADIRECTA_CODTRANSFERENCIA = "_codTransferencia";
	public static final String KEY_VENTADIRECTA_DESCUENTOINCENTIVO = "_descuentoIncentivo";

    public static final int COL_VENTADIRECTA_ROW_ID = 0;
    public static final int COL_VENTADIRECTA_VENTADIRECTAIDSERVER = 1;
    public static final int COL_VENTADIRECTA_DIA = 2;
    public static final int COL_VENTADIRECTA_MES = 3;
    public static final int COL_VENTADIRECTA_ANIO = 4;
    public static final int COL_VENTADIRECTA_CLIENTEID = 5;
    public static final int COL_VENTADIRECTA_MONTO = 6;
    public static final int COL_VENTADIRECTA_DESCUENTO = 7;
    public static final int COL_VENTADIRECTA_MONTOFINAL = 8;
    public static final int COL_VENTADIRECTA_TIPOPAGOID = 9;
    public static final int COL_VENTADIRECTA_LATITUD = 10;
    public static final int COL_VENTADIRECTA_LONGITUD = 11;
    public static final int COL_VENTADIRECTA_HORA = 12;
    public static final int COL_VENTADIRECTA_MINUTO = 13;
    public static final int COL_VENTADIRECTA_OBSERVACION = 14;
    public static final int COL_VENTADIRECTA_EMPLEADOID = 15;
    public static final int COL_VENTADIRECTA_FACTURA = 16;
    public static final int COL_VENTADIRECTA_NIT = 17;
    public static final int COL_VENTADIRECTA_NITNUEVO = 18;
    public static final int COL_VENTADIRECTA_NOVENTADIRECTA = 19;
    public static final int COL_VENTADIRECTA_ESTADO = 20;
    public static final int COL_VENTADIRECTA_APLICARBONIFICACION = 21;
    public static final int COL_VENTADIRECTA_TIPONIT = 22;
    public static final int COL_VENTADIRECTA_RUTA = 23;
    public static final int COL_VENTADIRECTA_TIPOVISITA = 24;
    public static final int COL_VENTADIRECTA_ZONAVENTAID = 25;
    public static final int COL_VENTADIRECTA_PRONTOPAGOID = 26;
    public static final int COL_VENTADIRECTA_DESCUENTOCANAL = 27;
    public static final int COL_VENTADIRECTA_DESCUENTOAJUSTE = 28;
    public static final int COL_VENTADIRECTA_DESCUENTOPRONTOPAGO = 29;
    public static final int COL_VENTADIRECTA_DESCUENTOOBJETIVO = 30;
    public static final int COL_VENTADIRECTA_FORMAPAGOID = 31;
    public static final int COL_VENTADIRECTA_CODTRANSFERENCIA = 32;
	public static final int COL_VENTADIRECTA_DESCUENTOINCENTIVO = 33;
    
    public static final String[] VENTADIRECTA_ALL_KEYS = new String[] {
    	KEY_VENTADIRECTA_ROW_ID,KEY_VENTADIRECTA_VENTADIRECTAIDSERVER,KEY_VENTADIRECTA_DIA,KEY_VENTADIRECTA_MES,
    	KEY_VENTADIRECTA_ANIO,KEY_VENTADIRECTA_CLIENTEID,KEY_VENTADIRECTA_MONTO,KEY_VENTADIRECTA_DESCUENTO,
    	KEY_VENTADIRECTA_MONTOFINAL,KEY_VENTADIRECTA_TIPOPAGOID,KEY_VENTADIRECTA_LATITUD,KEY_VENTADIRECTA_LONGITUD,
    	KEY_VENTADIRECTA_HORA,KEY_VENTADIRECTA_MINUTO,KEY_VENTADIRECTA_OBSERVACION,KEY_VENTADIRECTA_EMPLEADOID,
    	KEY_VENTADIRECTA_FACTURA,KEY_VENTADIRECTA_NIT,KEY_VENTADIRECTA_NITNUEVO,KEY_VENTADIRECTA_NOVENTADIRECTA,
    	KEY_VENTADIRECTA_ESTADO,KEY_VENTADIRECTA_APLICARBONIFICACION,KEY_VENTADIRECTA_TIPONIT,
    	KEY_VENTADIRECTA_RUTA,KEY_VENTADIRECTA_TIPOVISITA,KEY_VENTADIRECTA_ZONAVENTAID,
    	KEY_VENTADIRECTA_PRONTOPAGOID, KEY_VENTADIRECTA_DESCUENTOCANAL, KEY_VENTADIRECTA_DESCUENTOAJUSTE,
    	KEY_VENTADIRECTA_DESCUENTOPRONTOPAGO, KEY_VENTADIRECTA_DESCUENTOOBJETIVO, KEY_VENTADIRECTA_FORMAPAGOID,
    	KEY_VENTADIRECTA_CODTRANSFERENCIA, KEY_VENTADIRECTA_DESCUENTOINCENTIVO};
    
    public static final String VENTADIRECTA_TABLE_NAME = "tbl_VentaDirecta";
    
    public static final String VENTADIRECTA_TABLE_CREATE = "CREATE TABLE " + VENTADIRECTA_TABLE_NAME + "("
    		+ KEY_VENTADIRECTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_VENTADIRECTA_VENTADIRECTAIDSERVER + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_DIA + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_MES + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_ANIO + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_CLIENTEID + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_MONTO + " float NOT NULL,"
    		+ KEY_VENTADIRECTA_DESCUENTO + " float NOT NULL,"
    		+ KEY_VENTADIRECTA_MONTOFINAL + " float NOT NULL,"
    		+ KEY_VENTADIRECTA_TIPOPAGOID + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_LATITUD + " double NOT NULL,"
    		+ KEY_VENTADIRECTA_LONGITUD + " double NOT NULL,"
    		+ KEY_VENTADIRECTA_HORA + " integer NOT NULL,"
    		+ KEY_VENTADIRECTA_MINUTO + " integer NOT NULL, "
    		+ KEY_VENTADIRECTA_OBSERVACION + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_EMPLEADOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTA_FACTURA + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_NIT + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_NITNUEVO + " boolean NOT NULL, "
    		+ KEY_VENTADIRECTA_NOVENTADIRECTA + " integer NOT NULL, "
    		+ KEY_VENTADIRECTA_ESTADO + " boolean NOT NULL, "
    		+ KEY_VENTADIRECTA_APLICARBONIFICACION + " boolean NOT NULL, "
    		+ KEY_VENTADIRECTA_TIPONIT + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_RUTA + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_TIPOVISITA + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_ZONAVENTAID + " text NOT NULL, "
    		+ KEY_VENTADIRECTA_PRONTOPAGOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTA_DESCUENTOCANAL + " float NOT NULL, "
    		+ KEY_VENTADIRECTA_DESCUENTOAJUSTE + " float NOT NULL, "
    		+ KEY_VENTADIRECTA_DESCUENTOPRONTOPAGO + " float NOT NULL, "
    		+ KEY_VENTADIRECTA_DESCUENTOOBJETIVO + " float NOT NULL, "
    		+ KEY_VENTADIRECTA_FORMAPAGOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTA_CODTRANSFERENCIA + " text NOT NULL,"
			+ KEY_VENTADIRECTA_DESCUENTOINCENTIVO + " float NOT NULL);";
    
    
    public void borrarVentasDirectas()
    {
    	Cursor localCursor = obtenerVentasDirectas();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarVentaDirectaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public boolean borrarVentaDirectaPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(VENTADIRECTA_TABLE_NAME, str, null) > 0;
    }
    
    public long insertarVentaDirecta(int ventaDirectaIdServer, int dia, int mes, int anio, int clienteId, float monto, 
    		float descuento, float montoFinal, int tipoPagoId, double latitud, double longitud, int hora, int minuto,
    		String observacion,int empleadoId,String factura,String nit,boolean nitNuevo,int noVentaDirecta,
    		boolean estado,boolean aplicarBonificacion,String tipoNit,String ruta,String tipoVisita,int zonaVentaId,
    		int prontoPagoId, float descuentoCanal, float descuentoAjuste, float descuentoProntoPago, float descuentoObjetivo,
    		int formaPagoId, String codTransferencia, float descuentoIncentivo)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_ventaDirectaIdServer", ventaDirectaIdServer);
      localContentValues.put("_dia", dia);
      localContentValues.put("_mes", mes);
      localContentValues.put("_anio", anio);
      localContentValues.put("_clienteId", clienteId);
      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_tipoPagoId", tipoPagoId);
      localContentValues.put("_latitud", latitud);
      localContentValues.put("_longitud", longitud);
      localContentValues.put("_hora", hora);
      localContentValues.put("_minuto", minuto);
      localContentValues.put("_observacion", String.valueOf(observacion));
      localContentValues.put("_empleadoId", empleadoId);
      localContentValues.put("_factura", String.valueOf(factura));
      localContentValues.put("_nit", String.valueOf(nit));
      localContentValues.put("_nitNuevo", nitNuevo);
      localContentValues.put("_noVentaDirecta", noVentaDirecta);
      localContentValues.put("_estado", estado);
      localContentValues.put("_aplicarBonificacion", aplicarBonificacion);
      localContentValues.put("_tipoNit", String.valueOf(tipoNit));
      localContentValues.put("_ruta", ruta);
      localContentValues.put("_tipoVisita", tipoVisita);
      localContentValues.put("_zonaVentaId", zonaVentaId);
      localContentValues.put("_prontoPagoId", prontoPagoId);
      localContentValues.put("_descuentoCanal", descuentoCanal);
      localContentValues.put("_descuentoAjuste", descuentoAjuste);
      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
      localContentValues.put("_descuentoObjetivo", descuentoObjetivo);
      localContentValues.put("_formaPagoId", formaPagoId);
      localContentValues.put("_codTransferencia", codTransferencia);
	  localContentValues.put("_descuentoIncentivo", descuentoIncentivo);
      return this.db.insert(VENTADIRECTA_TABLE_NAME, null, localContentValues);
    }
    
    public int modificarVentaDirectaMontosYDescuentos(int id,float monto,float descuento,float montoFinal)
    {
      String str = "_id=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_monto", monto);
      localContentValues.put("_descuento", descuento);
      localContentValues.put("_montoFinal", montoFinal);
      return this.db.update(VENTADIRECTA_TABLE_NAME, localContentValues, str, null);
    }

	public int modificarVentaDirectaDescuentoIncentivo(int ventaIdServer,float montoFinal,float descuentoIncentivo)
	{
		String str = "_ventaDirectaIdServer=" + ventaIdServer;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_montoFinal", montoFinal);
		localContentValues.put("_descuentoIncentivo", descuentoIncentivo);
		return this.db.update(VENTADIRECTA_TABLE_NAME, localContentValues, str, null);
	}
    
    public Cursor obtenerVentaDirectaPorVentaIdServer(long ventaDirectaIdServer)
    {
      String str = "_ventaDirectaIdServer =" + ventaDirectaIdServer;
      Cursor localCursor = this.db.query(true,VENTADIRECTA_TABLE_NAME, VENTADIRECTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentaDirectaPor(long rowId)
    {
      String str = "_id=" + rowId;
      Cursor localCursor = this.db.query(true,VENTADIRECTA_TABLE_NAME, VENTADIRECTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentasDirectas()
    {
      Cursor localCursor = this.db.query(true,VENTADIRECTA_TABLE_NAME, VENTADIRECTA_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
      
    public int modificarVentaDirectaPorVentaIdServer(int rowId, int ventaIdServer)
    {
      String str = "_id=" + rowId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_ventaIdServer", ventaIdServer);
      return this.db.update(VENTADIRECTA_TABLE_NAME, localContentValues, str, null);
    }
    
    public Cursor obtenerVentaDirectaPorClienteId(long clienteId)
    {
      String str = "_clienteId=" + clienteId;
      Cursor localCursor = this.db.query(true,VENTADIRECTA_TABLE_NAME,VENTADIRECTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public int modificarVentaDirectaNoSincronizadaPor(int Id)
    {
    	String str = "_Id=" + Id;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_estado", Boolean.TRUE);
    	return this.db.update(VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, localContentValues, str, null);
    }
    
    //VENTADIRECTAPRODUCTO//
    public static final String KEY_VENTADIRECTAPRODUCTO_ROW_ID = "_id";
    public static final String KEY_VENTADIRECTAPRODUCTO_VENTADIRECTAIDSERVER = "_ventaDirectaIdServer";
    public static final String KEY_VENTADIRECTAPRODUCTO_PRODUCTOID = "_productoId";
    public static final String KEY_VENTADIRECTAPRODUCTO_PROMOCIONID = "_promocionId";
    public static final String KEY_VENTADIRECTAPRODUCTO_CANTIDAD = "_cantidad";
    public static final String KEY_VENTADIRECTAPRODUCTO_CANTIDADPAQUETE = "_cantidadPaquete";
    public static final String KEY_VENTADIRECTAPRODUCTO_MONTO = "_monto";
    public static final String KEY_VENTADIRECTAPRODUCTO_DESCUENTO = "_descuento";
    public static final String KEY_VENTADIRECTAPRODUCTO_MONTOFINAL = "_montoFinal";
    public static final String KEY_VENTADIRECTAPRODUCTO_MOTIVOID = "_motivoId";
    public static final String KEY_VENTADIRECTAPRODUCTO_COSTOID = "_costoId";
    public static final String KEY_VENTADIRECTAPRODUCTO_PRECIOID = "_precioId";
    public static final String KEY_VENTADIRECTAPRODUCTO_NOVENTADIRECTA = "_noVentaDirecta";
    public static final String KEY_VENTADIRECTAPRODUCTO_DESCUENTOCANAL = "_descuentoCanal";
    public static final String KEY_VENTADIRECTAPRODUCTO_DESCUENTOAJUSTE = "_descuentoAjuste";
    public static final String KEY_VENTADIRECTAPRODUCTO_CANALPRECIORUTAID = "_canalPrecioRutaId";
    
    public static final int COL_VENTADIRECTAPRODUCTO_ROW_ID = 0;
    public static final int COL_VENTADIRECTAPRODUCTO_VENTADIRECTAIDSERVER = 1;
    public static final int COL_VENTADIRECTAPRODUCTO_PRODUCTOID = 2;
    public static final int COL_VENTADIRECTAPRODUCTO_PROMOCIONID = 3;
    public static final int COL_VENTADIRECTAPRODUCTO_CANTIDAD = 4;
    public static final int COL_VENTADIRECTAPRODUCTO_CANTIDADPAQUETE = 5;
    public static final int COL_VENTADIRECTAPRODUCTO_MONTO = 6;
    public static final int COL_VENTADIRECTAPRODUCTO_DESCUENTO = 7;
    public static final int COL_VENTADIRECTAPRODUCTO_MONTOFINAL = 8;
    public static final int COL_VENTADIRECTAPRODUCTO_MOTIVOID = 9;
    public static final int COL_VENTADIRECTAPRODUCTO_COSTOID = 10;
    public static final int COL_VENTADIRECTAPRODUCTO_PRECIOID = 11;
    public static final int COL_VENTADIRECTAPRODUCTO_NOVENTADIRECTA = 12;
    public static final int COL_VENTADIRECTAPRODUCTO_DESCUENTOCANAL = 13;
    public static final int COL_VENTADIRECTAPRODUCTO_DESCUENTOAJUSTE = 14;
    public static final int COL_VENTADIRECTAPRODUCTO_CANALPRECIORUTAID = 15;
    
    
    public static final String[] VENTADIRECTAPRODUCTO_ALL_KEYS = new String[] {
    	KEY_VENTADIRECTAPRODUCTO_ROW_ID,KEY_VENTADIRECTAPRODUCTO_VENTADIRECTAIDSERVER,KEY_VENTADIRECTAPRODUCTO_PRODUCTOID,
    	KEY_VENTADIRECTAPRODUCTO_PROMOCIONID,KEY_VENTADIRECTAPRODUCTO_CANTIDAD,KEY_VENTADIRECTAPRODUCTO_CANTIDADPAQUETE,
    	KEY_VENTADIRECTAPRODUCTO_MONTO,KEY_VENTADIRECTAPRODUCTO_DESCUENTO,KEY_VENTADIRECTAPRODUCTO_MONTOFINAL,
    	KEY_VENTADIRECTAPRODUCTO_MOTIVOID,KEY_VENTAPRODUCTO_COSTOID,KEY_VENTAPRODUCTO_PRECIOID,
    	KEY_VENTADIRECTAPRODUCTO_NOVENTADIRECTA, KEY_VENTADIRECTAPRODUCTO_DESCUENTOCANAL, KEY_VENTADIRECTAPRODUCTO_DESCUENTOAJUSTE,
    	KEY_VENTADIRECTAPRODUCTO_CANALPRECIORUTAID};
    
    public static final String VENTADIRECTAPRODUCTO_TABLE_NAME = "tbl_VentaDirectaProducto";
    
    public static final String VENTADIRECTAPRODUCTO_TABLE_CREATE = "CREATE TABLE " + VENTADIRECTAPRODUCTO_TABLE_NAME + "("
    		+ KEY_VENTADIRECTAPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_VENTADIRECTAPRODUCTO_VENTADIRECTAIDSERVER + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_PROMOCIONID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_CANTIDAD + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_CANTIDADPAQUETE + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_MONTO + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_DESCUENTO + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_MONTOFINAL + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_MOTIVOID + " int NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_COSTOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_PRECIOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_NOVENTADIRECTA + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_DESCUENTOCANAL + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_DESCUENTOAJUSTE + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTO_CANALPRECIORUTAID + " integer NOT NULL);";

    public void borrarVentasDirectasProducto()
    {
      Cursor localCursor = obtenerVentasDirectasProducto();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarVentaDirectaProductoPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public boolean borrarVentaDirectaProductoPor(long rowId)
    {
      String str = "_id=" + rowId;
      return this.db.delete(VENTADIRECTAPRODUCTO_TABLE_NAME, str, null) > 0;
    }
    
    public long insertarVentaDirectaProducto(int ventaDirectaIdServer, int productoId, int promocionId, int cantidad, 
    		int cantidadPaquete, float monto, float descuento, float montoFinal, int motivoId, 
    		int costoId,int precioId,int noVentaDirecta, float descuentoCanal, float descuentoAjuste, int canalPrecioRutaId)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_ventaDirectaIdServer", ventaDirectaIdServer);
      localContentValues.put("_productoId", productoId);
      localContentValues.put("_promocionId", promocionId);
      localContentValues.put("_cantidad", cantidad);
      localContentValues.put("_cantidadPaquete", cantidadPaquete);
      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_motivoId", motivoId);
      localContentValues.put("_costoId", costoId);
      localContentValues.put("_precioId", precioId);
      localContentValues.put("_noVentaDirecta", noVentaDirecta);
      localContentValues.put("_descuentoCanal", descuentoCanal);
      localContentValues.put("_descuentoAjuste", descuentoAjuste);
      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
      return this.db.insert(VENTADIRECTAPRODUCTO_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerVentaDirectaProductoPorRowId(long rowId)
    {
      String str = "_id=" + rowId;
      Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTO_TABLE_NAME, VENTADIRECTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProducto()
    {
      Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTO_TABLE_NAME, VENTADIRECTAPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoPorVentaId(int ventaId)
    {
    	String str = "_ventaId=" + ventaId;
    	Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTO_TABLE_NAME, VENTADIRECTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoPorNoVentaDirecta(int noVentaDirecta)
    {
    	String str = "_noVentaDirecta=" + noVentaDirecta;
    	Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTO_TABLE_NAME, VENTADIRECTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoNoSincronizadasPorVentaDirectaId(int ventaDirectaId)
    {
    	String str = "_ventaDirectaId=" + ventaDirectaId;
    	Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTO_TABLE_NAME, VENTADIRECTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoNoSincronizadasPorNoVentaDirecta(int noVentaDirecta)
    {
    	String str = "_noVentaDirecta=" + noVentaDirecta + " and _ventaDirectaIdServer=0";
    	Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTO_TABLE_NAME, VENTADIRECTAPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public int modificarVentaDirectaProductoIdServerPor(int noVentaDirecta,int ventaDirectaIdServer)
    {
    	String str = "_noVentaDirecta=" + noVentaDirecta;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_ventaDirectaIdServer", ventaDirectaIdServer);
    	return this.db.update(VENTADIRECTAPRODUCTO_TABLE_NAME, localContentValues, str, null);
    }
    
    //VENTADIRECTAPRODUCTOTEMP//
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_ROW_ID = "_id";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_VENTADIRECTAIDSERVER = "_ventaDirectaIdServer";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_PRODUCTOID = "_productoId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_PROMOCIONID = "_promocionId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_CANTIDAD = "_cantidad";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_CANTIDADPAQUETE = "_cantidadPaquete";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_MONTO = "_monto";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTO = "_descuento";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_MONTOFINAL = "_montoFinal";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_COSTOID = "_costoId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_PRECIOID = "_precioId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_NOVENTADIRECTA = "_noVentaDirecta";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_CLIENTEID = "_clienteId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_TEMPID = "_tempId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_EMPLEADOID = "_empleadoId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOCANAL = "_descuentoCanal";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOAJUSTE = "_descuentoAjuste";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_CANALPRECIORUTAID = "_canalPrecioRutaId";
    public static final String KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO = "_descuentoProntoPago";
    
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_ROW_ID = 0;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_VENTADIRECTAIDSERVER = 1;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_PRODUCTOID = 2;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_PROMOCIONID = 3;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_CANTIDAD = 4;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_CANTIDADPAQUETE = 5;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_MONTO = 6;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_DESCUENTO = 7;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_MONTOFINAL = 8;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_COSTOID = 9;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_PRECIOID = 10;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_NOVENTADIRECTA = 11;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_CLIENTEID = 12;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_TEMPID = 13;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_EMPLEADOID = 14;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_DESCUENTOCANAL = 15;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_DESCUENTOAJUSTE = 16;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_CANALPRECIORUTAID = 17;
    public static final int COL_VENTADIRECTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO = 18;
    
    public static final String[] VENTADIRECTAPRODUCTOTEMP_ALL_KEYS = new String[] {
    	KEY_VENTADIRECTAPRODUCTOTEMP_ROW_ID,KEY_VENTADIRECTAPRODUCTOTEMP_VENTADIRECTAIDSERVER,KEY_VENTADIRECTAPRODUCTOTEMP_PRODUCTOID,
    	KEY_VENTADIRECTAPRODUCTOTEMP_PROMOCIONID,KEY_VENTADIRECTAPRODUCTOTEMP_CANTIDAD,KEY_VENTADIRECTAPRODUCTOTEMP_CANTIDADPAQUETE,
    	KEY_VENTADIRECTAPRODUCTOTEMP_MONTO,KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTO,KEY_VENTADIRECTAPRODUCTOTEMP_MONTOFINAL,
    	KEY_VENTAPRODUCTOTEMP_COSTOID,KEY_VENTAPRODUCTOTEMP_PRECIOID,KEY_VENTADIRECTAPRODUCTOTEMP_NOVENTADIRECTA,
    	KEY_VENTADIRECTAPRODUCTOTEMP_CLIENTEID, KEY_VENTADIRECTAPRODUCTOTEMP_TEMPID, KEY_VENTADIRECTAPRODUCTOTEMP_EMPLEADOID,
    	KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOCANAL, KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOAJUSTE, KEY_VENTADIRECTAPRODUCTOTEMP_CANALPRECIORUTAID,
    	KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO};
    
    public static final String VENTADIRECTAPRODUCTOTEMP_TABLE_NAME = "tbl_VentaDirectaProductoTemp";
    
    public static final String VENTADIRECTAPRODUCTOTEMP_TABLE_CREATE = "CREATE TABLE " + VENTADIRECTAPRODUCTOTEMP_TABLE_NAME + "("
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_VENTADIRECTAIDSERVER + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_PROMOCIONID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_CANTIDAD + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_CANTIDADPAQUETE + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_MONTO + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTO + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_MONTOFINAL + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_COSTOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_PRECIOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_NOVENTADIRECTA + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_CLIENTEID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_TEMPID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_EMPLEADOID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOCANAL + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOAJUSTE + " float NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_CANALPRECIORUTAID + " integer NOT NULL, "
    		+ KEY_VENTADIRECTAPRODUCTOTEMP_DESCUENTOPRONTOPAGO + " float NOT NULL);";

    public void borrarVentasDirectasProductoTemp()
    {
      Cursor localCursor = obtenerVentasDirectasProductoTemp();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarVentaDirectaProductoTempPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public boolean borrarVentaDirectaProductoTempPor(long rowId)
    {
      String str = "_id=" + rowId;
      return this.db.delete(VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
    }
    
    public long insertarVentaDirectaProductoTemp(int ventaId, int productoId, int promocionId, int cantidad, 
    		int cantidadPaquete, float monto, float descuento, float montoFinal, int costoId,int precioId,
    		int noVentaDirecta,int clienteId,int tempId,int empleadoId, float descuentoCanal, float descuentoAjuste,
    		int canalPrecioRutaId, float descuentoProntoPago)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_ventaDirectaIdServer", ventaId);
      localContentValues.put("_productoId", productoId);
      localContentValues.put("_promocionId", promocionId);
      localContentValues.put("_cantidad", cantidad);
      localContentValues.put("_cantidadPaquete", cantidadPaquete);
      localContentValues.put("_monto", new BigDecimal(monto).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_descuento", new BigDecimal(descuento).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_montoFinal", new BigDecimal(montoFinal).setScale(5,RoundingMode.HALF_UP).toString());
      localContentValues.put("_costoId", costoId);
      localContentValues.put("_precioId", precioId);
      localContentValues.put("_noVentaDirecta", noVentaDirecta);
      localContentValues.put("_clienteId", clienteId);
      localContentValues.put("_tempId", tempId);
      localContentValues.put("_empleadoId", empleadoId);
      localContentValues.put("_descuentoCanal", descuentoCanal);
      localContentValues.put("_descuentoAjuste", descuentoAjuste);
      localContentValues.put("_canalPrecioRutaId", canalPrecioRutaId);
      localContentValues.put("_descuentoProntoPago", descuentoProntoPago);
      return this.db.insert(VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerVentaDirectaProductoTempPorRowId(long rowId)
    {
      String str = "_id=" + rowId;
      Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, VENTADIRECTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoTemp()
    {
      Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, VENTADIRECTAPRODUCTOTEMP_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoTempPorVentaId(int ventaId)
    {
    	String str = "_ventaId=" + ventaId;
    	Cursor localCursor = this.db.query(true, VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, VENTADIRECTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }    
    
    public Cursor obtenerVentasDirectasProductoTempPorClienteId(int clienteId)
    {
    	String str = "_clienteId=" + clienteId;
    	Cursor localCursor = this.db.query(true,VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, VENTADIRECTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public int modificarVentaDirectaProductoTempNoSincronizadaPor(int Id,int ventaDirectaIdServer)
    {
    	String str = "_Id=" + Id;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_ventaDirectaIdServer", ventaDirectaIdServer);
    	return this.db.update(VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, localContentValues, str, null);
    }
    
    public boolean borrarVentaDirectaProductoTempPorClienteIdEmpleadoIdNoVentaDirecta(int clienteId,int empleadoId,int noVentaDirecta)
    {
    	String str = "_clienteId = " + clienteId + " and _empleadoId = " + empleadoId + " and _noVentaDirecta =" + noVentaDirecta;
    	return this.db.delete(VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, str, null) > 0;
    }
    
    public Cursor obtenerVentasDirectasProductoTempNoSincronizadasPorClienteIdNoVentaDirecta(int clienteId,int noVentaDirecta)
    {
    	String str = "_clienteId = " + clienteId + " and _noVentaDirecta = " + noVentaDirecta + " and _ventaDirectaIdServer = 0";
    	Cursor localCursor = this.db.query(true,VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, VENTADIRECTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasDirectasProductoTempPorClienteIdNoVentaDirecta(int clienteId, int noVentaDirecta)
    {
    	String str = "_clienteId=" + clienteId + " and _noVentaDirecta=" + noVentaDirecta;
    	Cursor localCursor = this.db.query(true,VENTADIRECTAPRODUCTOTEMP_TABLE_NAME, VENTADIRECTAPRODUCTOTEMP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //DISTRIBUIDOR//
    public static final String KEY_DISTRIBUIDOR_ROW_ID = "_id";
    public static final String KEY_DISTRIBUIDOR_DISTRIBUIDORID = "_distribuidorId";
    public static final String KEY_DISTRIBUIDOR_NOMBRECOMPLETO = "_nombreCompleto";
    
    public static final int COL_DISTRIBUIDOR_ROW_ID = 0;
    public static final int COL_DISTRIBUIDOR_DISTRIBUIDORID = 1;
    public static final int COL_DISTRIBUIDOR_NOMBRECOMPLETO = 2;
    
    public static final String[] DISTRIBUIDOR_ALL_KEYS = new String[] {
    	KEY_DISTRIBUIDOR_ROW_ID,KEY_DISTRIBUIDOR_DISTRIBUIDORID,KEY_DISTRIBUIDOR_NOMBRECOMPLETO};
    
    public static final String DISTRIBUIDOR_TABLE_NAME = "tbl_Distribuidor";
    
    public static final String DISTRIBUIDOR_TABLE_CREATE = "CREATE TABLE " + DISTRIBUIDOR_TABLE_NAME + "("
    		+ KEY_DISTRIBUIDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_DISTRIBUIDOR_DISTRIBUIDORID + " integer NOT NULL, "
    		+ KEY_DISTRIBUIDOR_NOMBRECOMPLETO + " text NOT NULL);";

    public void borrarDistribuidores()
    {
    	Cursor localCursor = obtenerDistribuidores();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarDistribuidorPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public boolean borrarDistribuidorPor(long rowId)
    {
      String str = "_id=" + rowId;
      return this.db.delete(DISTRIBUIDOR_TABLE_NAME, str, null) > 0;
    }
    
    public long insertarDistribuidor(ArrayList<DistribuidorWSResult> distribuidores)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_Distribuidor(_distribuidorId, _nombreCompleto) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (DistribuidorWSResult item : distribuidores) {

				stmt.bindLong(1, item.getEmpleadoId());
				stmt.bindString(2, item.getNombreCompleto());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerDistribuidorPorDistribuidorId(long distribuidorId)
    {
      String str = "_distribuidorId=" + distribuidorId;
      Cursor localCursor = this.db.query(true, DISTRIBUIDOR_TABLE_NAME, DISTRIBUIDOR_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerDistribuidores()
    {
      Cursor localCursor = this.db.query(true, DISTRIBUIDOR_TABLE_NAME, DISTRIBUIDOR_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    //PREVENTABONIFICACION//
    public static final String KEY_PREVENTABONIFICACION_ROW_ID = "_id";
    public static final String KEY_PREVENTABONIFICACION_PREVENTAID = "_preventaId";
    public static final String KEY_PREVENTABONIFICACION_BONIFICACIONID = "_bonificacionId";
    
    public static final int COL_PREVENTABONIFICACION_ROW_ID = 0;
    public static final int COL_PREVENTABONIFICACION_PREVENTAID = 1;
    public static final int COL_PREVENTABONIFICACION_BONIFICACIONID = 2;
    
    public static final String[] PREVENTABONIFICACION_ALL_KEYS = new String[] {
    	KEY_PREVENTABONIFICACION_ROW_ID,KEY_PREVENTABONIFICACION_PREVENTAID,KEY_PREVENTABONIFICACION_BONIFICACIONID,};
    
    public static final String PREVENTABONIFICACION_TABLE_NAME = "tbl_PreventaBonificacion";
    
    public static final String PREVENTABONIFICACION_TABLE_CREATE = "CREATE TABLE " + PREVENTABONIFICACION_TABLE_NAME + "("
    		+ KEY_PREVENTABONIFICACION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PREVENTABONIFICACION_PREVENTAID + " integer NOT NULL, "
    		+ KEY_PREVENTABONIFICACION_BONIFICACIONID + " integer NOT NULL);";

    public void borrarPreventasBonificacion()
    {
    	Cursor localCursor = obtenerPreventasBonificacion();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarPreventaBonificacionPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public boolean borrarPreventaBonificacionPor(long rowId)
    {
      String str = "_id=" + rowId;
      return this.db.delete(PREVENTABONIFICACION_TABLE_NAME, str, null) > 0;
    }
    
    public long insertarPreventaBonificacion(ArrayList<PreventaBonificacionWSResult> preventasBonificacion)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_PreventaBonificacion(_preventaId, _bonificacionId) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (PreventaBonificacionWSResult item : preventasBonificacion) {

				stmt.bindLong(1, item.getPreVentaId());
				stmt.bindLong(2, item.getBonificacionId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerPreventaBonificacionPorPreventaId(int preventaId)
    {
      String str = "_preventaId=" + preventaId;
      Cursor localCursor = this.db.query(true, PREVENTABONIFICACION_TABLE_NAME, PREVENTABONIFICACION_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasBonificacion()
    {
      Cursor localCursor = this.db.query(true, PREVENTABONIFICACION_TABLE_NAME, PREVENTABONIFICACION_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //VENTABONIFICACION//
    public static final String KEY_VENTABONIFICACION_ROW_ID = "_id";
    public static final String KEY_VENTABONIFICACION_VENTAID = "_ventaId";
    public static final String KEY_VENTABONIFICACION_BONIFICACIONID = "_bonificacionId";
    
    public static final int COL_VENTABONIFICACION_ROW_ID = 0;
    public static final int COL_VENTABONIFICACION_VENTAID = 1;
    public static final int COL_VENTABONIFICACION_BONIFICACIONID = 2;
    
    public static final String[] VENTABONIFICACION_ALL_KEYS = new String[] {
    	KEY_PREVENTABONIFICACION_ROW_ID,KEY_VENTABONIFICACION_VENTAID,KEY_VENTABONIFICACION_BONIFICACIONID,};
    
    public static final String VENTABONIFICACION_TABLE_NAME = "tbl_VentaBonificacion";
    
    public static final String VENTABONIFICACION_TABLE_CREATE = "CREATE TABLE " + VENTABONIFICACION_TABLE_NAME + "("
    		+ KEY_VENTABONIFICACION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_VENTABONIFICACION_VENTAID + " integer NOT NULL, "
    		+ KEY_VENTABONIFICACION_BONIFICACIONID + " integer NOT NULL);";

    public void borrarVentasBonificacion()
    {
    	Cursor localCursor = obtenerVentasBonificacion();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarVentaBonificacionPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public boolean borrarVentaBonificacionPor(long rowId)
    {
      String str = "_id=" + rowId;
      return this.db.delete(VENTABONIFICACION_TABLE_NAME, str, null) > 0;
    }
    
    public long insertarVentaBonificacion(ArrayList<VentaBonificacionWSResult> ventasBonificacion)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_VentaBonificacion(_ventaId, _bonificacionId) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (VentaBonificacionWSResult item : ventasBonificacion) {

				stmt.bindLong(1, item.getVentaId());
				stmt.bindLong(2, item.getBonificacionId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerVentaBonificacionPorVentaId(int ventaId)
    {
      String str = "_ventaId=" + ventaId;
      Cursor localCursor = this.db.query(true, VENTABONIFICACION_TABLE_NAME, VENTABONIFICACION_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVentasBonificacion()
    {
      Cursor localCursor = this.db.query(true, VENTABONIFICACION_TABLE_NAME, VENTABONIFICACION_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
  //AVANCEDISTRIBUCION//
	  public static final String KEY_AVANCEDISTRIBUCION_ROW_ID = "_Id";
	  public static final String KEY_AVANCEDISTRIBUCION_DISTRIBUIDORID = "_distribuidorId";
	  public static final String KEY_AVANCEDISTRIBUCION_DIA = "_dia";
	  public static final String KEY_AVANCEDISTRIBUCION_MES = "_mes";
	  public static final String KEY_AVANCEDISTRIBUCION_ANIO = "_anio";
	  public static final String KEY_AVANCEDISTRIBUCION_NOMBREDISTRIBUIDOR = "_nombreDistribuidor";
	  public static final String KEY_AVANCEDISTRIBUCION_NOPREVENTAS = "_noPreventas";
	  public static final String KEY_AVANCEDISTRIBUCION_MONTOPREVENTAS = "_montoPreventas";
	  public static final String KEY_AVANCEDISTRIBUCION_NOVENTAS = "_noVentas";
	  public static final String KEY_AVANCEDISTRIBUCION_MONTOVENTAS = "_montoVentas";
	  public static final String KEY_AVANCEDISTRIBUCION_TIPOAVANCEDISTRIBUCION = "_tipoAvanceDistribucion";
	  public static final String KEY_AVANCEDISTRIBUCION_ROL = "_rol";
	  
	  public static final int COL_AVANCEDISTRIBUCION_ROW_ID = 0;
	  public static final int COL_AVANCEDISTRIBUCION_DISTRIBUIDORID = 1;
	  public static final int COL_AVANCEDISTRIBUCION_DIA = 2;
	  public static final int COL_AVANCEDISTRIBUCION_MES = 3;
	  public static final int COL_AVANCEDISTRIBUCION_ANIO = 4;
	  public static final int COL_AVANCEDISTRIBUCION_NOMBREDISTRIBUIDOR = 5;
	  public static final int COL_AVANCEDISTRIBUCION_NOPREVENTAS = 6;
	  public static final int COL_AVANCEDISTRIBUCION_MONTOPREVENTAS = 7;
	  public static final int COL_AVANCEDISTRIBUCION_NOVENTAS = 8;
	  public static final int COL_AVANCEDISTRIBUCION_MONTOVENTAS = 9;
	  public static final int COL_AVANCEDISTRIBUCION_TIPOAVANCEDISTRIBUCION = 10;
	  public static final int COL_AVANCEDISTRIBUCION_ROL = 11;
	  
	  public static final String[] AVANCEDISTRIBUCION_ALL_KEYS = { 
		  KEY_AVANCEDISTRIBUCION_ROW_ID,KEY_AVANCEDISTRIBUCION_DISTRIBUIDORID,KEY_AVANCEDISTRIBUCION_DIA,
		  KEY_AVANCEDISTRIBUCION_MES,KEY_AVANCEDISTRIBUCION_ANIO,KEY_AVANCEDISTRIBUCION_NOMBREDISTRIBUIDOR,
		  KEY_AVANCEDISTRIBUCION_NOPREVENTAS,KEY_AVANCEDISTRIBUCION_MONTOPREVENTAS,KEY_AVANCEDISTRIBUCION_NOVENTAS,
		  KEY_AVANCEDISTRIBUCION_MONTOVENTAS,KEY_AVANCEDISTRIBUCION_TIPOAVANCEDISTRIBUCION,KEY_AVANCEDISTRIBUCION_ROL};
	    
	  public static final String AVANCEDISTRIBUCION_TABLE_NAME = "tbl_AvanceDistribucion";
	    
	  public static final String AVANCEDISTRIBUCION_TABLE_CREATE = "CREATE TABLE " + AVANCEDISTRIBUCION_TABLE_NAME + " ("
	    		+ KEY_AVANCEDISTRIBUCION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_AVANCEDISTRIBUCION_DISTRIBUIDORID + " integer NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_DIA + " integer NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_MES + " integer NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_ANIO + " integer NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_NOMBREDISTRIBUIDOR + " String, "
	    		+ KEY_AVANCEDISTRIBUCION_NOPREVENTAS + " integer NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_MONTOPREVENTAS + " float NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_NOVENTAS + " integer NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_MONTOVENTAS + " float NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_TIPOAVANCEDISTRIBUCION + " text NOT NULL, "
	    		+ KEY_AVANCEDISTRIBUCION_ROL + " text NOT NULL);";
	    		
	    public void borrarAvancesDistribucion()
	    {
	    	Cursor localCursor = obtenerAvancesDistribucion();
	    	long l = localCursor.getColumnIndexOrThrow("_Id");
	    	if (localCursor.moveToFirst()) 
	    	{
	    		do
	    		{
	    			borrarAvanceDistribucionPorId(localCursor.getLong((int)l));
	    		} 
	    		while (localCursor.moveToNext());
	    	}
	    	localCursor.close();
	    }
	    
	    public boolean borrarAvanceDistribucionPorDistribuidorId(long distribuidorId)
	    {
	    	String str = "_distribuidorId=" + distribuidorId;
	    	return this.db.delete(AVANCEDISTRIBUCION_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarAvanceDistribucionPorId(long id)
	    {
	      String str = "_Id=" + id;
	      return this.db.delete(AVANCEDISTRIBUCION_TABLE_NAME, str, null) > 0;
	    }
	    
	    public boolean borrarAvanceDistribucionPorTipoAvanceDistribuidorYRol(String tipoAvanceDistribucion,String rol)
	    {
	      String str = "_tipoAvanceDistribucion='" + tipoAvanceDistribucion + "' and _rol='" + rol + "'";
	      return this.db.delete(AVANCEDISTRIBUCION_TABLE_NAME, str, null) > 0;
	    }
	    
	    public long insertarAvanceDistribuidor(SoapObject avanceDistribucion)
	    {
			SQLiteStatement stmt = db.compileStatement(
					"INSERT INTO tbl_AvanceDistribucion(_distribuidorId, _dia, _dia, _mes, _anio, _nombreDistribuidor, _noPreventas, " +
							"_montoPreventas, _noVentas, _montoVentas, _tipoAvanceDistribucion, _rol) VALUES (?,?,?,?,?,?,?,?,?,?,?)"
			);
			try {
				db.beginTransaction();
				for (int i = 0; i < avanceDistribucion.getPropertyCount(); i++) {
					SoapObject soapObject = (SoapObject) avanceDistribucion.getProperty(i);

					stmt.bindLong(1,Integer.parseInt(soapObject.getPropertyAsString("DistribuidorId")));
					stmt.bindLong(2,Integer.parseInt(soapObject.getPropertyAsString("Dia")));
					stmt.bindLong(3,Integer.parseInt(soapObject.getPropertyAsString("Mes")));
					stmt.bindLong(4,Integer.parseInt(soapObject.getPropertyAsString("Anio")));
					stmt.bindString(5,soapObject.getPropertyAsString("NombreDistribuidor"));
					stmt.bindLong(6,Integer.parseInt(soapObject.getPropertyAsString("NroPreVentas")));
					stmt.bindDouble(7,Float.parseFloat(soapObject.getPropertyAsString("MontoPreVentas")));
					stmt.bindLong(8,Integer.parseInt(soapObject.getPropertyAsString("NroVentas")));
					stmt.bindDouble(9,Float.parseFloat(soapObject.getPropertyAsString("MontoVentas")));
					stmt.bindString(10,"DIA");
					stmt.bindString(11,"Supervisor");

					stmt.executeInsert();
					stmt.clearBindings();
				}
				db.setTransactionSuccessful();
				db.endTransaction();
				return 1;
			}catch(Exception localException){
				if(db.inTransaction()){
					db.endTransaction();
				}
				return 0;
			}
	    }
	    
	  public Cursor obtenerAvancesDistribucion()
	  {
	    	Cursor localCursor = this.db.query(true,AVANCEDISTRIBUCION_TABLE_NAME,AVANCEDISTRIBUCION_ALL_KEYS, null, null, null, null, null, null);
	    	if (localCursor != null) 
	    	{
	    		localCursor.moveToFirst();
	    	}
	    	return localCursor;
	  }
	    
	  public Cursor obtenerAvanceDistribucionPor(int distribuidorId)
	  {
		  String str = "_distribuidorId=" + distribuidorId;
	      Cursor localCursor = this.db.query(true,AVANCEDISTRIBUCION_TABLE_NAME,AVANCEDISTRIBUCION_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	  public Cursor obtenerAvanceDistribucionPorTipoAvanceDistribuidorYRol(String tipoAvanceDistribucion,String rol)
	  {
		  String str = "_tipoAvanceDistribucion='" + tipoAvanceDistribucion + "' and _rol='" + rol + "'";
	      Cursor localCursor = this.db.query(true,AVANCEDISTRIBUCION_TABLE_NAME,AVANCEDISTRIBUCION_ALL_KEYS, str, null, null, null, null, null);
	      if (localCursor != null) 
	      {
	    	  localCursor.moveToFirst();
	      }
	      return localCursor;
	  }
	  
	//MERCADO//
    public static final String KEY_MERCADO_ROW_ID = "_id";
    public static final String KEY_MERCADO_MERCADOID = "_mercadoId";
    public static final String KEY_MERCADO_NOMBRE = "_nombre";
   
    public static final int COL_MERCADO_ROW_ID = 0;
    public static final int COL_MERCADO_MERCADOID = 1;
    public static final int COL_MERCADO_NOMBRE = 2;
    
    public static final String[] MERCADO_ALL_KEYS = new String[] { 
    	KEY_MERCADO_ROW_ID,KEY_MERCADO_MERCADOID,KEY_MERCADO_NOMBRE};
    
    public static final String MERCADO_TABLE_NAME = "tbl_Mercado";
    
    public static final String MERCADO_TABLE_CREATE = "CREATE TABLE " + MERCADO_TABLE_NAME + "("
    		+ KEY_MERCADO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_MERCADO_MERCADOID + " integer NOT NULL, "
    		+ KEY_MERCADO_NOMBRE + " text NOT NULL);";

    public boolean borrarMercadoPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(MERCADO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarMercados()
    {
      Cursor localCursor = obtenerMercados();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarMercadoPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarMercado(ArrayList<MercadoWSResult> mercados)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_Mercado(_mercadoId, _nombre) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (MercadoWSResult item : mercados) {

				stmt.bindLong(1, item.getMercadoId());
				stmt.bindString(2, item.getNombre());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
        
    public Cursor obtenerMercadoPorMercadoId(int mercadoId)
    {
      String str = "_mercadoId=" + mercadoId;
      Cursor localCursor = this.db.query(true,MERCADO_TABLE_NAME,MERCADO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
        
    public Cursor obtenerMercados()
    {
      Cursor localCursor = this.db.query(true,MERCADO_TABLE_NAME,MERCADO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //CONSIGNACION//
    public static final String KEY_CONSIGNACION_ROW_ID = "_id";
    public static final String KEY_CONSIGNACION_CONSIGNACIONID = "_consignacionId";
    public static final String KEY_CONSIGNACION_DISTRIBUIDORID = "_distribuidorId";
    public static final String KEY_CONSIGNACION_ANIO = "_anio";
    public static final String KEY_CONSIGNACION_MES = "_mes";
    public static final String KEY_CONSIGNACION_DIA = "_dia";
    public static final String KEY_CONSIGNACION_ALMACENID = "_almacenId";
    public static final String KEY_CONSIGNACION_PRECIOLISTAID = "_precioListaId";
    public static final String KEY_CONSIGNACION_ESTADOSINCRONIZACION = "_estadoSincronizacion";
   
    public static final int COL_CONSIGNACION_ROW_ID = 0;
    public static final int COL_CONSIGNACION_CONSIGNACIONID = 1;
    public static final int COL_CONSIGNACION_DISTRIBUIDORID = 2;
    public static final int COL_CONSIGNACION_ANIO = 3;
    public static final int COL_CONSIGNACION_MES = 4;
    public static final int COL_CONSIGNACION_DIA = 5;
    public static final int COL_CONSIGNACION_ALMACENID= 6;
    public static final int COL_CONSIGNACION_PRECIOLISTAID = 7;
    public static final int COL_CONSIGNACION_ESTADOSINCRONIZACION = 8;
    
    public static final String[] CONSIGNACION_ALL_KEYS = new String[] { 
    	KEY_CONSIGNACION_ROW_ID,KEY_CONSIGNACION_CONSIGNACIONID,KEY_CONSIGNACION_DISTRIBUIDORID,
    	KEY_CONSIGNACION_ANIO,KEY_CONSIGNACION_MES,KEY_CONSIGNACION_DIA,
    	KEY_CONSIGNACION_ALMACENID,KEY_CONSIGNACION_PRECIOLISTAID,KEY_CONSIGNACION_ESTADOSINCRONIZACION
    	};
    
    public static final String CONSIGNACION_TABLE_NAME = "tbl_Consignacion";
    
    public static final String CONSIGNACION_TABLE_CREATE = "CREATE TABLE " + CONSIGNACION_TABLE_NAME + "("
    		+ KEY_CONSIGNACION_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CONSIGNACION_CONSIGNACIONID + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_DISTRIBUIDORID + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_ANIO + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_MES + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_DIA + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_ALMACENID + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_PRECIOLISTAID + " integer NOT NULL, "
    		+ KEY_CONSIGNACION_ESTADOSINCRONIZACION + " boolean NOT NULL);";

    public boolean borrarConsignacionPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(CONSIGNACION_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarConsignaciones()
    {
      Cursor localCursor = obtenerConsignaciones();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarConsignacionPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarConsignacion(int consignacionId, int distribuidorId, int anio, int mes, 
    		int dia, int almacenId, int precioListaId, boolean estadoSincronizacion)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_consignacionId", consignacionId);
      localContentValues.put("_distribuidorId", distribuidorId);
      localContentValues.put("_anio", anio);
      localContentValues.put("_mes", mes);
      localContentValues.put("_dia", dia);
      localContentValues.put("_almacenId", almacenId);
      localContentValues.put("_precioListaId", precioListaId);
      localContentValues.put("_estadoSincronizacion", estadoSincronizacion);
      return this.db.insert(CONSIGNACION_TABLE_NAME, null, localContentValues);
    }
    
    public int modificarConsignacionId(long id)
    {
      String str = "_id=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_consignacionId", id);
      return this.db.update(CONSIGNACION_TABLE_NAME, localContentValues, str, null);
    }
    
    public Cursor obtenerConsignacionPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,CONSIGNACION_TABLE_NAME,CONSIGNACION_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerConsignacionPorDistribuidor(int distribuidorId)
    {
      String str = "_distribuidorId=" + distribuidorId;
      Cursor localCursor = this.db.query(true,CONSIGNACION_TABLE_NAME, CONSIGNACION_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerConsignacionPorDistribuidorYFecha(int distribuidorId, int dia, int mes, int anio)
    {
      String str = "_distribuidorId=" + distribuidorId + " and _dia" + "=" + dia + " and _mes" + "=" + mes + " and _anio=" + anio;
      Cursor localCursor = this.db.query(true,CONSIGNACION_TABLE_NAME, CONSIGNACION_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerConsignaciones()
    {
      Cursor localCursor = this.db.query(true,CONSIGNACION_TABLE_NAME, CONSIGNACION_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    //ALMACENPOP//
    public static final String KEY_ALMACENPOP_ROW_ID = "_id";
    public static final String KEY_ALMACENPOP_ALMACENID = "_almacenId";
    public static final String KEY_ALMACENPOP_DESCRIPCION = "_descripcion";
    
    public static final int COL_ALMACENPOP_ROW_ID = 0;
    public static final int COL_ALMACENPOP_ALMACENID = 1;
    public static final int COL_ALMACENPOP_DESCRIPCION = 2;
    
    public static final String[] ALMACENPOP_ALL_KEYS = new String[] { 
    	KEY_ALMACENPOP_ROW_ID,KEY_ALMACENPOP_ALMACENID,KEY_ALMACENPOP_DESCRIPCION
    	};
    
    public static final String ALMACENPOP_TABLE_NAME = "tbl_AlmacenPOP";
    
    public static final String ALMACENPOP_TABLE_CREATE = "CREATE TABLE " + ALMACENPOP_TABLE_NAME + "("
    		+ KEY_ALMACENPOP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ALMACENPOP_ALMACENID + " integer NOT NULL, "
    		+ KEY_ALMACENPOP_DESCRIPCION + " text NOT NULL);";

    public boolean borrarAlmacenPOPPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ALMACENPOP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarAlmacenesPOP()
    {
      Cursor localCursor = obtenerAlmacenesPOP();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarAlmacenPOPPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarAlmacenPOP(AlmacenPOPWSResult almacenPOP)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_AlmacenPOP(_almacenId, _descripcion) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			//for (int i = 0; i < almacenPOP.getPropertyCount(); i++) {
			//	SoapObject soapObject = (SoapObject) almacenPOP.getProperty(i);

				stmt.bindLong(1, almacenPOP.getAlmacenId());
				stmt.bindString(2, almacenPOP.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			//}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerAlmacenPOPPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,ALMACENPOP_TABLE_NAME,ALMACENPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerAlmacenesPOP()
    {
      Cursor localCursor = this.db.query(true,ALMACENPOP_TABLE_NAME, ALMACENPOP_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    //ALMACENPOPPRODUCTO//
    public static final String KEY_ALMACENPOPPRODUCTO_ROW_ID = "_id";
    public static final String KEY_ALMACENPOPPRODUCTO_ALMACENID = "_almacenId";
    public static final String KEY_ALMACENPOPPRODUCTO_PRODUCTOID = "_productoId";
    public static final String KEY_ALMACENPOPPRODUCTO_SALDO = "_saldo";
    
    public static final int COL_ALMACENPOPRODUCTOP_ROW_ID = 0;
    public static final int COL_ALMACENPOPPRODUCTO_ALMACENID = 1;
    public static final int COL_ALMACENPOPPRODUCTO_PRODUCTOID = 2;
    public static final int COL_ALMACENPOPPRODUCTO_SALDO = 3;
    
    public static final String[] ALMACENPOPPRODUCTO_ALL_KEYS = new String[] { 
    		KEY_ALMACENPOPPRODUCTO_ROW_ID,KEY_ALMACENPOPPRODUCTO_ALMACENID,KEY_ALMACENPOPPRODUCTO_PRODUCTOID,
    		KEY_ALMACENPOPPRODUCTO_SALDO};
    
    public static final String ALMACENPOPPRODUCTO_TABLE_NAME = "tbl_AlmacenPOPProducto";
    
    public static final String ALMACENPOPPRODUCTO_TABLE_CREATE = "CREATE TABLE " + ALMACENPOPPRODUCTO_TABLE_NAME + "("
    		+ KEY_ALMACENPOPPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ALMACENPOPPRODUCTO_ALMACENID + " integer NOT NULL, "
    		+ KEY_ALMACENPOPPRODUCTO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_ALMACENPOPPRODUCTO_SALDO + " integer NOT NULL);";

    public boolean borrarAlmacenPOPProductoPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ALMACENPOPPRODUCTO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarAlmacenesPOPProducto()
    {
      Cursor localCursor = obtenerAlmacenesPOPProducto();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarAlmacenPOPProductoPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarAlmacenPOPProducto(ArrayList<AlmacenPOPProductoWSResult> almacenesPopProducto)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_AlmacenPopProducto(_almacenId, _productoId, _saldo) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (AlmacenPOPProductoWSResult item : almacenesPopProducto) {

				stmt.bindLong(1, item.getAlmacenId());
				stmt.bindLong(2, item.getProductoId());
				stmt.bindLong(3, item.getSaldo());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerAlmacenPOPProductoPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,ALMACENPOPPRODUCTO_TABLE_NAME,ALMACENPOPPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerAlmacenesPOPProducto()
    {
      Cursor localCursor = this.db.query(true,ALMACENPOPPRODUCTO_TABLE_NAME, ALMACENPOPPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerAlmacenPOPProductoPorProductoId(int productoId)
    {
      String str = "_productoId" + "=" + productoId;
      Cursor localCursor = this.db.query(true, ALMACENPOPPRODUCTO_TABLE_NAME, ALMACENPOPPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor ValidarExistenciasAlmacenPOPDispositivo(int productoId,int cantidad)
    {
      String str = "_productoId =" + productoId + " and _saldo >= " + cantidad;
     
      Cursor localCursor = this.db.query(true, ALMACENPOPPRODUCTO_TABLE_NAME, ALMACENPOPPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //CATEGORIAPOP//
    public static final String KEY_CATEGORIAPOP_ROW_ID = "_id";
    public static final String KEY_CATEGORIAPOP_CATEGORIAID = "_categoriaId";
    public static final String KEY_CATEGORIAPOP_NOMBRE = "_nombre";
    public static final String KEY_CATEGORIAPOP_PROVEEDORID = "_proveedorId";
    
    public static final int COL_CATEGORIAPOP_ROW_ID = 0;
    public static final int COL_CATEGORIAPOP_CATEGORIAID = 1;
    public static final int COL_CATEGORIAPOP_NOMBRE = 2;
    public static final int COL_CATEGORIAPOP_PROVEEDORID = 3;
    
    public static final String[] CATEGORIAPOP_ALL_KEYS = new String[] { 
    		KEY_CATEGORIAPOP_ROW_ID,KEY_CATEGORIAPOP_CATEGORIAID,KEY_CATEGORIAPOP_NOMBRE,
    		KEY_CATEGORIAPOP_PROVEEDORID};
    
    public static final String CATEGORIAPOP_TABLE_NAME = "tbl_CategoriaPOP";
    
    public static final String CATEGORIAPOP_TABLE_CREATE = "CREATE TABLE " + CATEGORIAPOP_TABLE_NAME + "("
    		+ KEY_CATEGORIAPOP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CATEGORIAPOP_CATEGORIAID + " integer NOT NULL, "
    		+ KEY_CATEGORIAPOP_NOMBRE + " text NOT NULL, "
    		+ KEY_CATEGORIAPOP_PROVEEDORID + " integer NOT NULL);";

    public boolean borrarCategoriaPOPPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(CATEGORIAPOP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCategoriasPOP()
    {
      Cursor localCursor = obtenerCategoriasPOP();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarCategoriaPOPPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarCategoriaPOP(ArrayList<CategoriaPOPWSResult> categoriasPop)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_CategoriaPop(_categoriaId, _nombre, _proveedorId) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (CategoriaPOPWSResult item : categoriasPop) {

				stmt.bindLong(1, item.getCategoriaId());
				stmt.bindString(2, item.getNombre());
				stmt.bindLong(3, item.getProveedorId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerCateoriaPOPPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,CATEGORIAPOP_TABLE_NAME,CATEGORIAPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerCategoriasPOP()
    {
      Cursor localCursor = this.db.query(true,CATEGORIAPOP_TABLE_NAME,CATEGORIAPOP_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerCategoriasPOPPorProvedorId(int proveedorId)
    {
    	String str = "_proveedorId=" + proveedorId;
    	Cursor localCursor = this.db.query(true,CATEGORIAPOP_TABLE_NAME,CATEGORIAPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PRODUCTOPOP//
    public static final String KEY_PRODUCTOPOP_ROW_ID = "_id";
    public static final String KEY_PRODUCTOPOP_PRODUCTOID = "_productoId";
    public static final String KEY_PRODUCTOPOP_DESCRIPCION25 = "_descripcion25";
    public static final String KEY_PRODUCTOPOP_CATEGORIAID = "_categoriaId";
    public static final String KEY_PRODUCTOPOP_CODIGOBARRA = "_codigoBarra";
    
    public static final int COL_PRODUCTOPOP_ROW_ID = 0;
    public static final int COL_PRODUCTOPOP_PRODUCTOID = 1;
    public static final int COL_PRODUCTOPOP_DESCRIPCION25 = 2;
    public static final int COL_PRODUCTOPOP_CATEGORIAID = 3;
    public static final int COL_PRODUCTOPOP_CODIGOBARRA = 4;
    
    public static final String[] PRODUCTOPOP_ALL_KEYS = new String[] { 
    		KEY_PRODUCTOPOP_ROW_ID,KEY_PRODUCTOPOP_PRODUCTOID,KEY_PRODUCTOPOP_DESCRIPCION25,
    		KEY_PRODUCTOPOP_CATEGORIAID,KEY_PRODUCTOPOP_CODIGOBARRA};
    
    public static final String PRODUCTOPOP_TABLE_NAME = "tbl_ProductoPOP";
    
    public static final String PRODUCTOPOP_TABLE_CREATE = "CREATE TABLE " + PRODUCTOPOP_TABLE_NAME + "("
    		+ KEY_PRODUCTOPOP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRODUCTOPOP_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOPOP_DESCRIPCION25 + " text NOT NULL, "
    		+ KEY_PRODUCTOPOP_CATEGORIAID + " integer NOT NULL, "
    		+ KEY_PRODUCTOPOP_CODIGOBARRA + " text NOT NULL);";

    public boolean borrarProductoPOPPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PRODUCTOPOP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProductosPOP()
    {
      Cursor localCursor = obtenerProductosPOP();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarProductoPOPPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarProductoPOP(ArrayList<ProductoPOPWSResult> productosPOP)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProductoPOP(_productoId, _descripcion25, _categoriaId, _codigoBarra) VALUES (?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ProductoPOPWSResult item : productosPOP) {

				stmt.bindLong(1, item.getProductoId());
				stmt.bindString(2, item.getDescripcion25());
				stmt.bindLong(3, item.getCategoriaId());
				stmt.bindString(4, item.getCodigoBarra());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProductoPOPPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,PRODUCTOPOP_TABLE_NAME,PRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerProductosPOP()
    {
      Cursor localCursor = this.db.query(true,PRODUCTOPOP_TABLE_NAME,PRODUCTOPOP_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProductosPOPPorProveedorNoEnPreventaProductoPOP(int proveedorId,int categoriaId,int preventaPOPId)
    {
    	String query = "SELECT P._productoId, P._descripcion25, P._categoriaId, P._codigoBarra " +
    					"FROM tbl_productoPOP AS P " +
    					"JOIN tbl_almacenPOPProducto AS AP ON (P._productoId = AP._productoId) " +
    					"WHERE (AP._saldo > 0) AND P._categoriaId = ? " +
    					//"AND P._productoId NOT IN (SELECT PPP._productoPOPId " +
    					//"FROM tbl_PreventaProductoPop as PPP " +
    					//"WHERE _preventaPOPId = ?) " +
    					"ORDER BY P._descripcion25";
    	
    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(categoriaId)});//,String.valueOf(preventaPOPId)});
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerProductoPOPPorProductoId(int productoId)
    {
    	String str = "_productoId= " +productoId;
    	Cursor localCursor = this.db.query(true,PRODUCTOPOP_TABLE_NAME,PRODUCTOPOP_ALL_KEYS,str, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PREVENTAPRODUCTOPOP//
    public static final String KEY_PREVENTAPRODUCTOPOP_ROW_ID = "_id";
    public static final String KEY_PREVENTAPRODUCTOPOP_PREVENTAPOPID = "_preventaPOPId";
    public static final String KEY_PREVENTAPRODUCTOPOP_PREVENTAPOPIDSERVER = "_preventaPOPIdServer";
    public static final String KEY_PREVENTAPRODUCTOPOP_PRODUCTOPOPID = "_productoPOPId";
    public static final String KEY_PREVENTAPRODUCTOPOP_CANTIDAD = "_cantidad";
    public static final String KEY_PREVENTAPRODUCTOPOP_CLIENTEID = "_clienteId";
    public static final String KEY_PREVENTAPRODUCTOPOP_SYNCRO = "_syncro";
    public static final String KEY_PREVENTAPRODUCTOPOP_OBSERVACION = "_observacion";
    public static final String KEY_PREVENTAPRODUCTOPOP_MOTIVOPOPID = "_motivoPopId";
    
    public static final int COL_PREVENTAPRODUCTOPOP_ROW_ID = 0;
    public static final int COL_PREVENTAPRODUCTOPOP_PREVENTAPOPID = 1;
    public static final int COL_PREVENTAPRODUCTOPOP_PREVENTAPOPIDSERVER = 2;
    public static final int COL_PREVENTAPRODUCTOPOP_PRODUCTOPOPID = 3;
    public static final int COL_PREVENTAPRODUCTOPOP_CANTIDAD = 4;
    public static final int COL_PREVENTAPRODUCTOPOP_CLIENTEID = 5;
    public static final int COL_PREVENTAPRODUCTOPOP_SYNCRO = 6;
    public static final int COL_PREVENTAPRODUCTOPOP_OBSERVACION = 7;
    public static final int COL_PREVENTAPRODUCTOPOP_MOTIVOPOPID = 8;
    
    public static final String[] PREVENTAPRODUCTOPOP_ALL_KEYS = new String[] { 
    		KEY_PREVENTAPRODUCTOPOP_ROW_ID,KEY_PREVENTAPRODUCTOPOP_PREVENTAPOPID,KEY_PREVENTAPRODUCTOPOP_PREVENTAPOPIDSERVER,
    		KEY_PREVENTAPRODUCTOPOP_PRODUCTOPOPID,KEY_PREVENTAPRODUCTOPOP_CANTIDAD,KEY_PREVENTAPRODUCTOPOP_CLIENTEID,
    		KEY_PREVENTAPRODUCTOPOP_SYNCRO,KEY_PREVENTAPRODUCTOPOP_OBSERVACION,KEY_PREVENTAPRODUCTOPOP_MOTIVOPOPID};
    
    public static final String PREVENTAPRODUCTOPOP_TABLE_NAME = "tbl_PreventaProductoPOP";
    
    public static final String PREVENTAPRODUCTOPOP_TABLE_CREATE = "CREATE TABLE " + PREVENTAPRODUCTOPOP_TABLE_NAME + "("
    		+ KEY_PREVENTAPRODUCTOPOP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PREVENTAPRODUCTOPOP_PREVENTAPOPID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOPOP_PREVENTAPOPIDSERVER + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOPOP_PRODUCTOPOPID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOPOP_CANTIDAD + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOPOP_CLIENTEID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOPOP_SYNCRO + " boolean NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOPOP_OBSERVACION + " text, "
    		+ KEY_PREVENTAPRODUCTOPOP_MOTIVOPOPID + " integer NOT NULL);";

    public boolean borrarPreventaProductoPOPPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PREVENTAPRODUCTOPOP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarPreventasProductoPOP()
    {
      Cursor localCursor = obtenerPreventasProductoPOP();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarPreventaProductoPOPPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarPreventaProductoPOP(ArrayList<PreventaProductoPOPWSResult> preventasProductoPOP)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_PreventaProductoPOP(_preventaPOPId, _preventaPOPIdServer, _productoPOPId, _cantidad, " +
						"_clienteId, _syncro, _observacion, _motivoPopId) VALUES (?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (PreventaProductoPOPWSResult item : preventasProductoPOP) {

				stmt.bindLong(1, item.getPreVentaProductoPOPId());
				stmt.bindLong(2, item.getPreVentaId());
				stmt.bindLong(3, item.getProductoId());
				stmt.bindLong(4, item.getCantidad());
				stmt.bindLong(5,0);
				stmt.bindLong(6,1);
				stmt.bindString(7,"");
				stmt.bindLong(8, item.getMotivoPopId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }

	public long insertarPreventaProductoPOP(int preventaPOPId,int preventaPOPIdServer,int productoPOPId,int cantidad,int clienteId,boolean syncro,
											String observacion,int motivoPopId)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_preventaPOPId", Integer.valueOf(preventaPOPId));
		localContentValues.put("_preventaPOPIdServer", Integer.valueOf(preventaPOPIdServer));
		localContentValues.put("_productoPOPId", Integer.valueOf(productoPOPId));
		localContentValues.put("_cantidad", Integer.valueOf(cantidad));
		localContentValues.put("_clienteId", Integer.valueOf(clienteId));
		localContentValues.put("_syncro", Boolean.valueOf(syncro));
		localContentValues.put("_observacion", String.valueOf(observacion));
		localContentValues.put("_motivoPopId", Integer.valueOf(motivoPopId));
		return this.db.insert(PREVENTAPRODUCTOPOP_TABLE_NAME, null, localContentValues);
	}
    
    public Cursor obtenerPreventaProductoPOPPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOPOP_TABLE_NAME,PREVENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerPreventasProductoPOP()
    {
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOPOP_TABLE_NAME,PREVENTAPRODUCTOPOP_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoPOPPorClienteId(int clienteId)
    {
      String str = "_clienteId=" + clienteId;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOPOP_TABLE_NAME,PREVENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoPOPPorPreventaPOPId(int preventaPOPId)
    {
      String str = "_preventaPOPId=" + preventaPOPId;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOPOP_TABLE_NAME,PREVENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoPOPPorPreventaPOPIdServer(int preventaPOPIdServer)
    {
      String str = "_preventaPOPIdServer=" + preventaPOPIdServer;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOPOP_TABLE_NAME,PREVENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoPOPNoSincronizadasPor(int preventaPOPId)
    {
      String str = "_preventaPOPId=" + preventaPOPId + " and _syncro = 0";
      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTOPOP_TABLE_NAME, PREVENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoPOPNoSincronizadas()
    {
      String str = "_syncro = 0";
      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTOPOP_TABLE_NAME, PREVENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public int modificarPreventaProductoPOPNoSincronizado(int Id, int preventaPOPIdServer)
    {
      String str = "_Id=" + Id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_syncro", Boolean.TRUE);
      localContentValues.put("_preventaPOPIdServer", preventaPOPIdServer);
      return this.db.update(PREVENTAPRODUCTOPOP_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarPreventaProductosPOPNoSincronizado(int preventaPOPId, int preventaPOPIdServer)
    {
      String str = "_preventaPOPId=" + preventaPOPId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_preventaPOPIdServer", preventaPOPIdServer);
      return this.db.update(PREVENTAPRODUCTOPOP_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarPreventaProductosPOPPor(int id,String observacion)
    {
      String str = "_id=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_observacion", String.valueOf(observacion));
      return this.db.update(PREVENTAPRODUCTOPOP_TABLE_NAME, localContentValues, str, null);
    }
    
    //VENTAPRODUCTOPOP//
    public static final String KEY_VENTAPRODUCTOPOP_ROW_ID = "_id";
    public static final String KEY_VENTAPRODUCTOPOP_VENTAPOPID = "_ventaPOPId";
    public static final String KEY_VENTAPRODUCTOPOP_VENTAPOPIDSERVER = "_ventaPOPIdServer";
    public static final String KEY_VENTAPRODUCTOPOP_PRODUCTOPOPID = "_productoPOPId";
    public static final String KEY_VENTAPRODUCTOPOP_CANTIDAD = "_cantidad";
    public static final String KEY_VENTAPRODUCTOPOP_CLIENTEID = "_clienteId";
    public static final String KEY_VENTAPRODUCTOPOP_SYNCRO = "_syncro";
    public static final String KEY_VENTAPRODUCTOPOP_OBSERVACION = "_observacion";
    public static final String KEY_VENTAPRODUCTOPOP_MOTIVOPOPID = "_motivoPopId";
    
    public static final int COL_VENTAPRODUCTOPOP_ROW_ID = 0;
    public static final int COL_VENTAPRODUCTOPOP_VENTAPOPID = 1;
    public static final int COL_VENTAPRODUCTOPOP_VENTAPOPIDSERVER = 2;
    public static final int COL_VENTAPRODUCTOPOP_PRODUCTOPOPID = 3;
    public static final int COL_VENTAPRODUCTOPOP_CANTIDAD = 4;
    public static final int COL_VENTAPRODUCTOPOP_CLIENTEID = 5;
    public static final int COL_VENTAPRODUCTOPOP_SYNCRO = 6;
    public static final int COL_VENTAPRODUCTOPOP_OBSERVACION = 7;
    public static final int COL_VENTAPRODUCTOPOP_MOTIVOPOPID = 8;
    
    public static final String[] VENTAPRODUCTOPOP_ALL_KEYS = new String[] { 
    		KEY_VENTAPRODUCTOPOP_ROW_ID,KEY_VENTAPRODUCTOPOP_VENTAPOPID,KEY_VENTAPRODUCTOPOP_VENTAPOPIDSERVER,
    		KEY_VENTAPRODUCTOPOP_PRODUCTOPOPID,KEY_VENTAPRODUCTOPOP_CANTIDAD,KEY_VENTAPRODUCTOPOP_CLIENTEID,
    		KEY_VENTAPRODUCTOPOP_SYNCRO,KEY_VENTAPRODUCTOPOP_OBSERVACION,KEY_VENTAPRODUCTOPOP_MOTIVOPOPID};
    
    public static final String VENTAPRODUCTOPOP_TABLE_NAME = "tbl_VentaProductoPOP";
    
    public static final String VENTAPRODUCTOPOP_TABLE_CREATE = "CREATE TABLE " + VENTAPRODUCTOPOP_TABLE_NAME + "("
    		+ KEY_VENTAPRODUCTOPOP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_VENTAPRODUCTOPOP_VENTAPOPID + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOPOP_VENTAPOPIDSERVER + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOPOP_PRODUCTOPOPID + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOPOP_CANTIDAD + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOPOP_CLIENTEID + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOPOP_SYNCRO + " boolean NOT NULL, "
    		+ KEY_VENTAPRODUCTOPOP_OBSERVACION + " text, "
    		+ KEY_VENTAPRODUCTOPOP_MOTIVOPOPID + " integer NOT NULL);";

    public boolean borrarVentaProductoPOPPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(VENTAPRODUCTOPOP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarVentasProductoPOP()
    {
    	Cursor localCursor = obtenerVentasProductoPOP();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarVentaProductoPOPPor(localCursor.getLong((int)l));
    		} while (localCursor.moveToNext());
    	}
    	localCursor.close();
	}
    
    public long insertarVentaProductoPOP(int ventaPOPId,int ventaPOPIdServer,int productoPOPId,int cantidad,int clienteId,boolean syncro,
    										String observacion,int motivoPopId)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_ventaPOPId", ventaPOPId);
    	localContentValues.put("_ventaPOPIdServer", ventaPOPIdServer);
    	localContentValues.put("_productoPOPId", productoPOPId);
    	localContentValues.put("_cantidad", cantidad);
    	localContentValues.put("_clienteId", clienteId);
    	localContentValues.put("_syncro", syncro);
    	localContentValues.put("_observacion", String.valueOf(observacion));
    	localContentValues.put("_motivoPopId", motivoPopId);
    	return this.db.insert(VENTAPRODUCTOPOP_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerVentaProductoPOPPor(int id)
    {
    	String str = "_id=" + id;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }

    public Cursor obtenerVentasProductoPOP()
    {
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoPOPPorClienteId(int clienteId)
    {
    	String str = "_clienteId=" + clienteId;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoPOPPorVentaPOPId(int ventaPOPId)
    {
    	String str = "_ventaPOPId=" + ventaPOPId;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoPOPPorVentaPOPIdServer(int ventaPOPIdServer)
    {
    	String str = "_ventaPOPIdServer=" + ventaPOPIdServer;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoPOPNoSincronizadasPor(int ventaPOPId)
    {
    	String str = "_ventaPOPId=" + ventaPOPId + " and _syncro = 0";
    	Cursor localCursor = this.db.query(true, VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoPOPNoSincronizadas()
    {
    	String str = "_syncro = 0";
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOPOP_TABLE_NAME,VENTAPRODUCTOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public int modificarVentaProductoPOPNoSincronizado(int Id, int ventaPOPIdServer)
    {
    	String str = "_Id=" + Id;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_syncro", Boolean.TRUE);
    	localContentValues.put("_ventaPOPIdServer", ventaPOPIdServer);
    	return this.db.update(VENTAPRODUCTOPOP_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarVentaProductosPOPNoSincronizado(int ventaPOPId, int ventaPOPIdServer)
    {
    	String str = "_ventaPOPId=" + ventaPOPId;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_ventaPOPIdServer", ventaPOPIdServer);
    	return this.db.update(VENTAPRODUCTOPOP_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarVentaProductosPOPPor(int id,String observacion)
    {
    	String str = "_id=" + id;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_observacion", String.valueOf(observacion));
    	return this.db.update(VENTAPRODUCTOPOP_TABLE_NAME, localContentValues, str, null);
    }
        
    //PRODUCTOPOPCOSTO//
    public static final String KEY_PRODUCTOPOPCOSTO_ROW_ID = "_id";
    public static final String KEY_PRODUCTOPOPCOSTO_COSTOID = "_costoId";
    public static final String KEY_PRODUCTOPOPCOSTO_PRODUCTOID = "_productoId";
    public static final String KEY_PRODUCTOPOPCOSTO_COSTO = "_costo";
        
    public static final int COL_PRODUCTOPOPCOSTO_ROW_ID = 0;
    public static final int COL_PRODUCTOPOPCOSTO_COSTOID = 1;
    public static final int COL_PRODUCTOPOPCOSTO_PRODUCTOID = 2;
    public static final int COL_PRODUCTOPOPCOSTO_COSTO = 3;
    
    public static final String[] PRODUCTOPOPCOSTO_ALL_KEYS = new String[] {
    		KEY_PRODUCTOPOPCOSTO_ROW_ID,KEY_PRODUCTOPOPCOSTO_COSTOID,KEY_PRODUCTOPOPCOSTO_PRODUCTOID,
    		KEY_PRODUCTOPOPCOSTO_COSTO};
    
    public static final String PRODUCTOPOPCOSTO_TABLE_NAME = "tbl_ProductoPOPCosto";
    
    public static final String PRODUCTOPOPCOSTO_TABLE_CREATE = "CREATE TABLE " + PRODUCTOPOPCOSTO_TABLE_NAME + "("
    		+ KEY_PRODUCTOPOPCOSTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRODUCTOPOPCOSTO_COSTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOPOPCOSTO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOPOPCOSTO_COSTO + " float NOT NULL);";

    public boolean borrarProductoPOPCostoPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PRODUCTOPOPCOSTO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProductosPOPCosto()
    {
      Cursor localCursor = obtenerProductosPOPCosto();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarProductoPOPCostoPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarProductoPOPCosto(ArrayList<ProductoPOPCostoWSResult> productosPOPCosto)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProductoPOPCosto(_costoId, _productoId, _costo) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ProductoPOPCostoWSResult item : productosPOPCosto) {

				stmt.bindLong(1, item.getCostoId());
				stmt.bindLong(2, item.getProductoId());
				stmt.bindDouble(3, item.getCosto());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProductoPOPCostoPor(int productoId)
    {
      String str = "_productoId=" + productoId;
      Cursor localCursor = this.db.query(true,PRODUCTOPOPCOSTO_TABLE_NAME,PRODUCTOPOPCOSTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerProductosPOPCosto()
    {
      Cursor localCursor = this.db.query(true,PRODUCTOPOPCOSTO_TABLE_NAME,PRODUCTOPOPCOSTO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //PRODUCTOCAMBIO//
    public static final String KEY_PRODUCTOCAMBIO_ROW_ID = "_id";
    public static final String KEY_PRODUCTOCAMBIO_PRODUCTOID = "_productoId";
    public static final String KEY_PRODUCTOCAMBIO_DESCRIPCION25 = "_descripcion25";
    public static final String KEY_PRODUCTOCAMBIO_PROVEEDORID = "_proveedorId";
    public static final String KEY_PRODUCTOCAMBIO_CATEGORIAID = "_categoriaId";
    public static final String KEY_PRODUCTOCAMBIO_CONVERSION = "_conversion";
    public static final String KEY_PRODUCTOCAMBIO_PRECIOID = "_precioId";
    public static final String KEY_PRODUCTOCAMBIO_PRECIO = "_precio";
    public static final String KEY_PRODUCTOCAMBIO_COSTOID = "_costoId";
        
    public static final int COL_PRODUCTOCAMBIO_ROW_ID = 0;
    public static final int COL_PRODUCTOCAMBIO_PRODUCTOID = 1;
    public static final int COL_PRODUCTOCAMBIO_DESCRIPCION25 = 2;
    public static final int COL_PRODUCTOCAMBIO_PROVEEDORID = 3;
    public static final int COL_PRODUCTOCAMBIO_CATEGORIAID = 4;
    public static final int COL_PRODUCTOCAMBIO_CONVERSION = 5;
    public static final int COL_PRODUCTOCAMBIO_PRECIOID = 6;
    public static final int COL_PRODUCTOCAMBIO_PRECIO = 7;
    public static final int COL_PRODUCTOCAMBIO_COSTOID = 8;
    
    public static final String[] PRODUCTOCAMBIO_ALL_KEYS = new String[] {
    		KEY_PRODUCTOCAMBIO_ROW_ID,KEY_PRODUCTOCAMBIO_PRODUCTOID,KEY_PRODUCTOCAMBIO_DESCRIPCION25,
    		KEY_PRODUCTOCAMBIO_PROVEEDORID,KEY_PRODUCTOCAMBIO_CATEGORIAID,KEY_PRODUCTOCAMBIO_CONVERSION,
    		KEY_PRODUCTOCAMBIO_PRECIOID,KEY_PRODUCTOCAMBIO_PRECIO,KEY_PRODUCTOCAMBIO_COSTOID};
    
    public static final String PRODUCTOCAMBIO_TABLE_NAME = "tbl_ProductoCambio";
    
    public static final String PRODUCTOCAMBIO_TABLE_CREATE = "CREATE TABLE " + PRODUCTOCAMBIO_TABLE_NAME + "("
    		+ KEY_PRODUCTOCAMBIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRODUCTOCAMBIO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_DESCRIPCION25 + " text NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_PROVEEDORID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_CATEGORIAID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_CONVERSION + " integer NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_PRECIOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_PRECIO + " float NOT NULL, "
    		+ KEY_PRODUCTOCAMBIO_COSTOID + " integer NOT NULL);";

    public boolean borrarProductoCambioPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PRODUCTOCAMBIO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProductosCambio()
    {
      Cursor localCursor = obtenerProductosCambio();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarProductoCambioPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarProductoCambio(ArrayList<ProductoCambioWSResult> productosCambio)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProductoCambio(_productoId, _descripcion25, _proveedorId, _categoriaId, _conversion, _precioId, _precio, _costoId) VALUES (?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ProductoCambioWSResult item : productosCambio) {

				stmt.bindLong(1, item.getProductoId());
				stmt.bindString(2, item.getDescripcion25());
				stmt.bindLong(3, item.getProveedorId());
				stmt.bindLong(4, item.getCategoriaId());
				stmt.bindLong(5, item.getConversion());
				stmt.bindLong(6, item.getPrecioId());
				stmt.bindDouble(7, item.getPrecio());
				stmt.bindLong(8, item.getCostoId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProductoCambioPor(int productoId)
    {
      String str = "_productoId=" + productoId;
      Cursor localCursor = this.db.query(true,PRODUCTOCAMBIO_TABLE_NAME,PRODUCTOCAMBIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerProductosCambio()
    {
      Cursor localCursor = this.db.query(true,PRODUCTOCAMBIO_TABLE_NAME,PRODUCTOCAMBIO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProductosCambioPorProveedorIdNoEnPreventaProductoCambioPor(int proveedorId,int categoriaId, int clienteId)
    {
    	String query = "SELECT P._id, P._productoId, P._descripcion25, P._proveedorId, P._categoriaId, P._conversion, P._precioId, P._precio, P._costoId " +
    					"FROM tbl_productoCambio AS P " +
    					"WHERE _proveedorId = ? AND _categoriaId = ? " +
    					//"AND P._productoId NOT IN (SELECT PPC._productoId " +
    					//"FROM tbl_PreventaProductoCambio as PPC " +
    					//"WHERE PPC._clienteId = ?) " +
    					"ORDER BY P._descripcion25";
    	
    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(proveedorId),String.valueOf(categoriaId)});//,String.valueOf(clienteId)});
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PREVENTAPRODUCTOCAMBIO//
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_ROW_ID = "_id";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_PREVENTAID = "_preventaId";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_PREVENTAIDSERVER = "_preventaIdServer";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_PRODUCTOID = "_productoId";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_CANTIDAD = "_cantidad";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_CLIENTEID = "_clienteId";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_SINCRO = "_sincro";
    public static final String KEY_PREVENTAPRODUCTOCAMBIO_MOTIVOCAMBIOID = "_motivoCambioId";
        
    public static final int COL_PREVENTAPRODUCTOCAMBIO_ROW_ID = 0;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_PREVENTAID = 1;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_PREVENTAIDSERVER = 2;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_PRODUCTOID = 3;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_CANTIDAD = 4;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_CLIENTEID = 5;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_SINCRO = 6;
    public static final int COL_PREVENTAPRODUCTOCAMBIO_MOTIVOCAMBIOID = 7;
    
    public static final String[] PREVENTAPRODUCTOCAMBIO_ALL_KEYS = new String[] {
    		KEY_PREVENTAPRODUCTOCAMBIO_ROW_ID,KEY_PREVENTAPRODUCTOCAMBIO_PREVENTAID,KEY_PREVENTAPRODUCTOCAMBIO_PREVENTAIDSERVER,
    		KEY_PREVENTAPRODUCTOCAMBIO_PRODUCTOID,KEY_PREVENTAPRODUCTOCAMBIO_CANTIDAD,KEY_PREVENTAPRODUCTOCAMBIO_CLIENTEID,
    		KEY_PREVENTAPRODUCTOCAMBIO_SINCRO,KEY_PREVENTAPRODUCTOCAMBIO_MOTIVOCAMBIOID};
    
    public static final String PREVENTAPRODUCTOCAMBIO_TABLE_NAME = "tbl_PreventaProductoCambio";
    
    public static final String PREVENTAPRODUCTOCAMBIO_TABLE_CREATE = "CREATE TABLE " + PREVENTAPRODUCTOCAMBIO_TABLE_NAME + "("
    		+ KEY_PREVENTAPRODUCTOCAMBIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_PREVENTAID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_PREVENTAIDSERVER + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_CANTIDAD + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_CLIENTEID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_SINCRO + " boolean NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCAMBIO_MOTIVOCAMBIOID + " integer NOT NULL);";

    public boolean borrarPreventaProductoCambioPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(PREVENTAPRODUCTOCAMBIO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarPreventasProductoCambio()
    {
    	Cursor localCursor = obtenerPreventasProductoCambio();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarPreventaProductoCambioPor(localCursor.getLong((int)l));
    		}
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarPreventaProductoCambio(ArrayList<PreventaProductoCambioWSResult> preventasProductoCambio)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_PreventaProductoCambio(_preventaId, _preventaIdServer, _productoId, " +
						"_cantidad, _clienteId, _sincro, _motivoCambioId) VALUES (?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (PreventaProductoCambioWSResult item : preventasProductoCambio) {

				stmt.bindLong(1, item.getPreVentaProductoCambioId());
				stmt.bindLong(2, item.getPreVentaId());
				stmt.bindLong(3, item.getProductoId());
				stmt.bindLong(4, item.getCantidad());
				stmt.bindLong(5,0);
				stmt.bindLong(6,1);
				stmt.bindLong(7, item.getMotivoCambioId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }

	public long insertarPreventaProductoCambio(int preventaId,int preventaIdServer,int productoId,int cantidad,int clienteId,boolean sincro,int motivoCambioId)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_preventaId", Integer.valueOf(preventaId));
		localContentValues.put("_preventaIdServer", Integer.valueOf(preventaIdServer));
		localContentValues.put("_productoId", Integer.valueOf(productoId));
		localContentValues.put("_cantidad", Integer.valueOf(cantidad));
		localContentValues.put("_clienteId", Integer.valueOf(clienteId));
		localContentValues.put("_sincro", Boolean.valueOf(sincro));
		localContentValues.put("_motivoCambioId", Integer.valueOf(motivoCambioId));
		return this.db.insert(PREVENTAPRODUCTOCAMBIO_TABLE_NAME, null, localContentValues);
	}
    
    public Cursor obtenerPreventasProductoCambioPorPreventaId(int preventaId)
    {
    	String str = "_preventaId=" + preventaId;
    	Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCAMBIO_TABLE_NAME,PREVENTAPRODUCTOCAMBIO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCambioPorPreventaIdServer(int preventaIdServer)
    {
    	String str = "_preventaIdServer=" + preventaIdServer;
    	Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCAMBIO_TABLE_NAME,PREVENTAPRODUCTOCAMBIO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }

    public Cursor obtenerPreventasProductoCambio()
    {
    	Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCAMBIO_TABLE_NAME,PREVENTAPRODUCTOCAMBIO_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCambioNoSincronizadasPor(int preventaId)
    {
      String str = "_preventaId = " +  preventaId + " and _sincro = 0";
      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTOCAMBIO_TABLE_NAME, PREVENTAPRODUCTOCAMBIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCambioNoSincronizadas()
    {
      String str = "_sincro = 0";
      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTOCAMBIO_TABLE_NAME, PREVENTAPRODUCTOCAMBIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public int modificarPreventaProductoCambioNoSincronizado(int Id, int preventaIdServer)
    {
      String str = "_id=" + Id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_preventaIdServer", preventaIdServer);
      localContentValues.put("_sincro", true);
      return this.db.update(PREVENTAPRODUCTOCAMBIO_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarPreventaProductoCambioPreventaIdServer(int Id, int preventaIdServer)
    {
      String str = "_preventaId=" + Id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_preventaIdServer", preventaIdServer);
      return this.db.update(PREVENTAPRODUCTOCAMBIO_TABLE_NAME, localContentValues, str, null);
    }
    
    //MOTIVOCAMBIO//
    public static final String KEY_MOTIVOCAMBIO_ROW_ID = "_Id";
    public static final String KEY_MOTIVOCAMBIO_MOTIVOCAMBIOID = "_motivoCambioId";
    public static final String KEY_MOTIVOCAMBIO_DESCRIPCION = "_descripcion";
    
    public static final int COL_MOTIVOCAMBIO_ROW_ID = 0;
    public static final int COL_MOTIVOCAMBIO_MOTIVOCAMBIOID = 1;
    public static final int COL_MOTIVOCAMBIO_DESCRIPCION = 2;
    
    public static final String[] MOTIVOCAMBIO_ALL_KEYS = new String[] {
    	KEY_MOTIVOCAMBIO_ROW_ID,KEY_MOTIVOCAMBIO_MOTIVOCAMBIOID,KEY_MOTIVOCAMBIO_DESCRIPCION 
    	};
    
    public static final String MOTIVOCAMBIO_TABLE_NAME = "tbl_MotivoCambio";
    
    public static final String MOTIVOCAMBIO_TABLE_CREATE = "CREATE TABLE " + MOTIVOCAMBIO_TABLE_NAME + " ("
    		+ KEY_MOTIVOCAMBIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_MOTIVOCAMBIO_MOTIVOCAMBIOID + " integer NOT NULL,"
    		+ KEY_MOTIVOCAMBIO_DESCRIPCION + " text NOT NULL);";
    
    public boolean borrarMotivoCambioPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(MOTIVOCAMBIO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarMotivosCambio()
    {
      Cursor localCursor = obtenerMotivosCambio();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarMotivoCambioPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarMotivoCambio(ArrayList<MotivoCambioWSResult> motivosCambio)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_motivoCambio(_motivoCambioId, _descripcion) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (MotivoCambioWSResult item : motivosCambio) {

				stmt.bindLong(1, item.getMotivoCambioId());
				stmt.bindString(2, item.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerMotivoCambioPor(int motivoCambioId)
    {
      String str = "_motivoCambioId=" + motivoCambioId;
      Cursor localCursor = this.db.query(true,MOTIVOCAMBIO_TABLE_NAME, MOTIVOCAMBIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerMotivosCambio()
    {
      Cursor localCursor = this.db.query(true,MOTIVOCAMBIO_TABLE_NAME, MOTIVOCAMBIO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //PREVENTACAMBIO//
    public static final String KEY_PREVENTACAMBIO_ROW_ID = "_Id";
    public static final String KEY_PREVENTACAMBIO_PREVENTAID = "_preventaId";
    
    public static final int COL_PREVENTACAMBIO_ROW_ID = 0;
    public static final int COL_PREVENTACAMBIO_PREVENTAID = 1;
    
    public static final String[] PREVENTACAMBIO_ALL_KEYS = new String[] {
    		KEY_PREVENTACAMBIO_ROW_ID,KEY_PREVENTACAMBIO_PREVENTAID 
    	};
    
    public static final String PREVENTACAMBIO_TABLE_NAME = "tbl_PreventaCambio";
    
    public static final String PREVENTACAMBIO_TABLE_CREATE = "CREATE TABLE " + PREVENTACAMBIO_TABLE_NAME + " ("
    		+ KEY_PREVENTACAMBIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_PREVENTACAMBIO_PREVENTAID + " integer NOT NULL);";
    
    public boolean borrarPreventaCambioPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(PREVENTACAMBIO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarPreventasCambio()
    {
      Cursor localCursor = obtenerPreventasCambio();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarPreventaCambioPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarPreventaCambio(ArrayList<PreventaCambioWSResult> preventasCambio)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_PreventaCambio(_preventaId) VALUES (?)"
		);
		try {
			db.beginTransaction();
			for (PreventaCambioWSResult item : preventasCambio) {

				stmt.bindLong(1, item.getPreVentaId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerPreventaCambioPor(int preventaId)
    {
      String str = "_preventaId=" + preventaId;
      Cursor localCursor = this.db.query(true,PREVENTACAMBIO_TABLE_NAME, PREVENTACAMBIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasCambio()
    {
      Cursor localCursor = this.db.query(true,PREVENTACAMBIO_TABLE_NAME, PREVENTACAMBIO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //DOSIFICACIONPROVEEDOR//
    public static final String KEY_DOSIFICACIONPROVEEDOR_ROW_ID = "_Id";
    public static final String KEY_DOSIFICACIONPROVEEDOR_DOSIFICACIONID = "_dosificacionId";
    public static final String KEY_DOSIFICACIONPROVEEDOR_PROVEEDORID = "_proveedorId";
    public static final String KEY_DOSIFICACIONPROVEEDOR_ACTIVA = "_activa";
    public static final String KEY_DOSIFICACIONPROVEEDOR_DESCRIPCION = "_descripcion";
    
    public static final int COL_DOSIFICACIONPROVEEDOR_ROW_ID = 0;
    public static final int COL_DOSIFICACIONPROVEEDOR_DOSIFICACIONID = 1;
    public static final int COL_DOSIFICACIONPROVEEDOR_PROVEEDORID = 2;
    public static final int COL_DOSIFICACIONPROVEEDOR_ACTIVA = 3;
    public static final int COL_DOSIFICACIONPROVEEDOR_DESCRIPCION = 4;
    
    public static final String[] DOSIFICACIONPROVEEDOR_ALL_KEYS = new String[] {
    		KEY_DOSIFICACIONPROVEEDOR_ROW_ID,KEY_DOSIFICACIONPROVEEDOR_DOSIFICACIONID,KEY_DOSIFICACIONPROVEEDOR_PROVEEDORID,
    		KEY_DOSIFICACIONPROVEEDOR_ACTIVA,KEY_DOSIFICACIONPROVEEDOR_DESCRIPCION
    	};
    
    public static final String DOSIFICACIONPROVEEDOR_TABLE_NAME = "tbl_DosificacionProveedor";
    
    public static final String DOSIFICACIONPROVEEDOR_TABLE_CREATE = "CREATE TABLE " + DOSIFICACIONPROVEEDOR_TABLE_NAME + " ("
    		+ KEY_DOSIFICACIONPROVEEDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_DOSIFICACIONPROVEEDOR_DOSIFICACIONID + " integer NOT NULL, "
    		+ KEY_DOSIFICACIONPROVEEDOR_PROVEEDORID + " integer NOT NULL, "
    		+ KEY_DOSIFICACIONPROVEEDOR_ACTIVA + " boolean NOT NULL, "
    		+ KEY_DOSIFICACIONPROVEEDOR_DESCRIPCION + " text NOT NULL);";
    
    public boolean borrarDosificacionProveedorPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(DOSIFICACIONPROVEEDOR_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarDosificacionesProveedor()
    {
      Cursor localCursor = obtenerDosificacionesProveedor();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarDosificacionProveedorPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarDosificacionProveedor(ArrayList<DosificacionProveedorWSResult> dosificacionesProveedor)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_DosificacionProveedor(_dosificacionId, _proveedorId, _activa, _descripcion) VALUES (?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (DosificacionProveedorWSResult item : dosificacionesProveedor) {

				stmt.bindLong(1, item.getDosificacionId());
				stmt.bindLong(2, item.getProveedorId());
				stmt.bindLong(3, item.isActiva()?1:0);
				stmt.bindString(4, item.getDosificacionFD());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerDosificacionProveedorPor(int dosificacionId)
    {
    	String str = "_dosificacionId=" + dosificacionId;
        Cursor localCursor = this.db.query(true,DOSIFICACIONPROVEEDOR_TABLE_NAME, DOSIFICACIONPROVEEDOR_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerDosificacionProveedorPorProveedorId(int proveedorId)
    {
    	String str = "_proveedorId=" + proveedorId;
        Cursor localCursor = this.db.query(true,DOSIFICACIONPROVEEDOR_TABLE_NAME, DOSIFICACIONPROVEEDOR_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerDosificacionesProveedor()
    {
    	Cursor localCursor = this.db.query(true,DOSIFICACIONPROVEEDOR_TABLE_NAME, DOSIFICACIONPROVEEDOR_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
	    }
    	return localCursor;
    }
    
    public Cursor obtenerDosificacionesProveedorVendedor()
    {
    	String query = "SELECT dp._id, dp._dosificacionId, dp._proveedorId, dp._activa, dp._descripcion "  
    					+ "FROM tbl_dosificacionproveedor dp "
    					+ "join tbl_proveedor p on (dp._proveedorId = p._proveedorId) ";
    	Cursor localCursor = db.rawQuery(query,null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
	    }
    	return localCursor;
    }
    
    //DESCUENTO2//
    public static final String KEY_DESCUENTO2_ROW_ID = "_Id";
    public static final String KEY_DESCUENTO2_DISTRIBUIDORID = "_distribuidorId";
    public static final String KEY_DESCUENTO2_MONTO = "_monto";
    
    public static final int COL_DESCUENTO2_ROW_ID = 0;
    public static final int COL_DESCUENTO2_DISTRIBUIDORID = 1;
    public static final int COL_DESCUENTO2_MONTO = 2;
    
    public static final String[] DESCUENTO2_ALL_KEYS = new String[] {
    	KEY_DESCUENTO2_ROW_ID,KEY_DESCUENTO2_DISTRIBUIDORID,KEY_DESCUENTO2_MONTO 
    	};
    
    public static final String DESCUENTO2_TABLE_NAME = "tbl_Descuento2";
    
    public static final String DESCUENTO2_TABLE_CREATE = "CREATE TABLE " + DESCUENTO2_TABLE_NAME + " ("
    		+ KEY_DESCUENTO2_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_DESCUENTO2_DISTRIBUIDORID + " float NOT NULL,"
    		+ KEY_DESCUENTO2_MONTO + " text NOT NULL);";
    
    public boolean borrarDescuento2Por(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(DESCUENTO2_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarDescuentos2()
    {
    	Cursor localCursor = obtenerDescuentos2();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarDescuento2Por(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarDescuento2(int distribuidorId, float monto)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_distribuidorId", distribuidorId);
    	localContentValues.put("_monto", monto);
    	return this.db.insert(DESCUENTO2_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerDescuentos2Por(int distribuidorId)
    {
      String str = "_distribuidorId =" + distribuidorId;
      Cursor localCursor = this.db.query(true,DESCUENTO2_TABLE_NAME, DESCUENTO2_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerDescuentos2()
    {
    	Cursor localCursor = this.db.query(true,DESCUENTO2_TABLE_NAME, DESCUENTO2_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //TIPONIT//
    public static final String KEY_TIPONIT_ROW_ID = "_id";
    public static final String KEY_TIPONIT_TIPONIT = "_tipoNit";
    
    public static final int COL_TIPONIT_ROW_ID = 0;
    public static final int COL_TIPONIT_TIPONIT = 1;
    
    public static final String[] TIPONIT_ALL_KEYS = new String[] {
    		KEY_TIPONIT_ROW_ID,KEY_TIPONIT_TIPONIT};
    
    public static final String TIPONIT_TABLE_NAME = "tbl_TipoNit";
    
    public static final String TIPONIT_TABLE_CREATE = "CREATE TABLE " + TIPONIT_TABLE_NAME + " ("
    		+ KEY_TIPONIT_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_TIPONIT_TIPONIT + " text NOT NULL);";
    
    public boolean borrarTipoNitPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(TIPONIT_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarTiposNit()
    {
    	Cursor localCursor = obtenerTiposNit();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarTipoNitPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarTipoNit(ArrayList<TipoNitWSResult> tiposNit)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_tipoNit(_tipoNit) VALUES (?)"
		);
		try {
			db.beginTransaction();
			for (TipoNitWSResult item : tiposNit) {

				stmt.bindString(1, item.getTipoNitId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerTiposNit()
    {
    	Cursor localCursor = this.db.query(true,TIPONIT_TABLE_NAME,TIPONIT_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //CLIENTECENSO//
  	public static final String KEY_CLIENTECENSO_ROW_ID = "_id";
  	public static final String KEY_CLIENTECENSO_CODIGO = "_codigo";
  	public static final String KEY_CLIENTECENSO_REFERENCIA = "_referencia";
  	public static final String KEY_CLIENTECENSO_TIPONEGOCIOIDVENDER = "_tipoNegocioIdVender";
  	public static final String KEY_CLIENTECENSO_TIPONEGOCIO = "_tipoNegocio";
  	public static final String KEY_CLIENTECENSO_CONTACTO = "_contacto";
  	public static final String KEY_CLIENTECENSO_LATITUD = "_latitud";
  	public static final String KEY_CLIENTECENSO_LONGITUD = "_longitud";
  	public static final String KEY_CLIENTECENSO_NOMBRES = "_nombres";
  	public static final String KEY_CLIENTECENSO_PATERNO = "_paterno";
  	public static final String KEY_CLIENTECENSO_CREADORID = "_creadorId";
  	public static final String KEY_CLIENTECENSO_LATITUDCREADOR = "_latitudCreador";
  	public static final String KEY_CLIENTECENSO_LONGITUDCREADOR = "_longitudCreador";
  	public static final String KEY_CLIENTECENSO_ZONAID = "_zonaId";
  	public static final String KEY_CLIENTECENSO_RUTAID = "_rutaId";
  	public static final String KEY_CLIENTECENSO_DIAID = "_diaId";
  	public static final String KEY_CLIENTECENSO_MERCADOID = "_mercadoId";
  	public static final String KEY_CLIENTECENSO_DIACREACION = "_diaCreacion";
  	public static final String KEY_CLIENTECENSO_MESCREACION = "_mesCreacion";
  	public static final String KEY_CLIENTECENSO_ANIOCREACION = "_anioCreacion";
  	public static final String KEY_CLIENTECENSO_ESTADO = "_estado";
  	public static final String KEY_CLIENTECENSO_CLIENTEID = "_clienteId";
  	public static final String KEY_CLIENTECENSO_SINCRO = "_sincro";
  	public static final String KEY_CLIENTECENSO_MOTIVOELIMINACIONID = "_motivoEliminacionId";
  	      
  	public static final int COL_CLIENTECENSO_ROW_ID = 0;
  	public static final int COL_CLIENTECENSO_CODIGO = 1;
  	public static final int COL_CLIENTECENSO_REFERENCIA = 2;
  	public static final int COL_CLIENTECENSO_TIPONEGOCIOIDVENDER = 3;
  	public static final int COL_CLIENTECENSO_TIPONEGOCIO = 4;
  	public static final int COL_CLIENTECENSO_CONTACTO = 5;
  	public static final int COL_CLIENTECENSO_LATITUD = 6;
  	public static final int COL_CLIENTECENSO_LONGITUD = 7;
  	public static final int COL_CLIENTECENSO_NOMBRES = 8;
  	public static final int COL_CLIENTECENSO_PATERNO = 9;
  	public static final int COL_CLIENTECENSO_CREADORID = 10;
  	public static final int COL_CLIENTECENSO_LATITUDCREADOR = 11;
  	public static final int COL_CLIENTECENSO_LONGITUDCREADOR = 12;
  	public static final int COL_CLIENTECENSO_ZONAID = 13;
  	public static final int COL_CLIENTECENSO_RUTAID = 14;
  	public static final int COL_CLIENTECENSO_DIAID = 15;
  	public static final int COL_CLIENTECENSO_MERCADOID = 16;
  	public static final int COL_CLIENTECENSO_DIACREACION = 17;
  	public static final int COL_CLIENTECENSO_MESCREACION = 18;
  	public static final int COL_CLIENTECENSO_ANIOCREACION = 19;
  	public static final int COL_CLIENTECENSO_ESTADO = 20;
  	public static final int COL_CLIENTECENSO_CLIENTEID = 21;
  	public static final int COL_CLIENTECENSO_SINCRO = 22;
  	public static final int COL_CLIENTECENSO_MOTIVOELIMINCACIONID = 23;
  	  
  	public static final String[] CLIENTECENSO_ALL_KEYS = new String[] { 
  		KEY_CLIENTECENSO_ROW_ID,KEY_CLIENTECENSO_CODIGO,KEY_CLIENTECENSO_REFERENCIA,
  		KEY_CLIENTECENSO_TIPONEGOCIOIDVENDER,KEY_CLIENTECENSO_TIPONEGOCIO,KEY_CLIENTECENSO_CONTACTO,
  		KEY_CLIENTECENSO_LATITUD,KEY_CLIENTECENSO_LONGITUD,KEY_CLIENTECENSO_NOMBRES,KEY_CLIENTECENSO_PATERNO,
  		KEY_CLIENTECENSO_CREADORID,KEY_CLIENTECENSO_LATITUDCREADOR,KEY_CLIENTECENSO_LONGITUDCREADOR,
  		KEY_CLIENTECENSO_ZONAID,KEY_CLIENTECENSO_RUTAID,KEY_CLIENTECENSO_DIAID,
  		KEY_CLIENTECENSO_MERCADOID,KEY_CLIENTECENSO_DIACREACION,KEY_CLIENTECENSO_MESCREACION,
  		KEY_CLIENTECENSO_ANIOCREACION,KEY_CLIENTECENSO_ESTADO,KEY_CLIENTECENSO_CLIENTEID,
  		KEY_CLIENTECENSO_SINCRO,KEY_CLIENTECENSO_MOTIVOELIMINACIONID};
  	    
  	public static final String CLIENTECENSO_TABLE_NAME = "tbl_ClienteCenso";
  	  
  	public static final String CLIENTECENSO_TABLE_CREATE = "CREATE TABLE " + CLIENTECENSO_TABLE_NAME + "("
  	  		+ KEY_CLIENTECENSO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
  	  		+ KEY_CLIENTECENSO_CODIGO + " text NOT NULL, "
  	  		+ KEY_CLIENTECENSO_REFERENCIA + " text NOT NULL, "
  	  		+ KEY_CLIENTECENSO_TIPONEGOCIOIDVENDER + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_TIPONEGOCIO + " text NOT NULL, "
  	  		+ KEY_CLIENTECENSO_CONTACTO + " text NOT NULL, "
  	  		+ KEY_CLIENTECENSO_LATITUD + " double NOT NULL, "
  	  		+ KEY_CLIENTECENSO_LONGITUD + " double NOT NULL, "
  	  		+ KEY_CLIENTECENSO_NOMBRES + " text NOT NULL, "
  	  		+ KEY_CLIENTECENSO_PATERNO + " text NOT NULL, "
  	  		+ KEY_CLIENTECENSO_CREADORID + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_LATITUDCREADOR + " doubler NOT NULL, "
  	  		+ KEY_CLIENTECENSO_LONGITUDCREADOR + " doubler NOT NULL, "
  	  		+ KEY_CLIENTECENSO_ZONAID + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_RUTAID + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_DIAID + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_MERCADOID + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_DIACREACION + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_MESCREACION + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_ANIOCREACION + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_ESTADO +" integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_CLIENTEID + " integer NOT NULL, "
  	  		+ KEY_CLIENTECENSO_SINCRO + " boolean NOT NULL, "
  			+ KEY_CLIENTECENSO_MOTIVOELIMINACIONID + " integer NOT NULL);";
  	  
  	public boolean borrarClienteCensoPor(long id)
  	  {
  	    String str = "_id=" + id;
  	    return this.db.delete(CLIENTECENSO_TABLE_NAME, str, null) > 0;
  	  }
  	  
  	public void borrarClientesCenso()
  	{
  		Cursor localCursor = obtenerClientesCenso();
      	long l = localCursor.getColumnIndexOrThrow("_id");
  	    if (localCursor.moveToFirst()) 
  	    {
  	      do
  	      {
  	        borrarClienteCensoPor(localCursor.getLong((int)l));
  	      } 
  	      while (localCursor.moveToNext());
  	    }
  	    localCursor.close();
  	}
  	  
  	public long insertarClienteCenso(ArrayList<ClienteCensoWSResult> clientesCenso)
  	{
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClienteCenso(_codigo, _referencia, _tipoNegocioIdVender, _tipoNegocio, _contacto, _latitud, _longitud, " +
						"_nombres, _paterno, _creadorId, _latitudCreador, _longitudCreador, _zonaId, _rutaId, _diaId, _mercadoId, _diaCreacion, " +
						"_mesCreacion, _anioCreacion, _estado, _clienteId, _sincro, _motivoEliminacionId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ClienteCensoWSResult item : clientesCenso) {

				stmt.bindString(1, String.valueOf(item.getId()));
				stmt.bindString(2, item.getReferencia());
				stmt.bindLong(3,0);
				stmt.bindString(4, item.getTipoNegocio());
				stmt.bindString(5, item.getContacto());
				stmt.bindDouble(6, item.getLatitud());
				stmt.bindDouble(7, item.getLongitud());
				stmt.bindString(8,"");
				stmt.bindString(9,"");
				stmt.bindLong(10,0);
				stmt.bindLong(11,0);
				stmt.bindLong(12,0);
				stmt.bindLong(13,0);
				stmt.bindLong(14, item.getRutaId());
				stmt.bindLong(15, item.getDiaId());
				stmt.bindLong(16,0);
				stmt.bindLong(17,0);
				stmt.bindLong(18,0);
				stmt.bindLong(19,0);
				stmt.bindLong(20, item.getEstado());
				stmt.bindLong(21, item.getClienteId());
				stmt.bindLong(22,1);
				stmt.bindLong(23, item.getMotivoEliminacionId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
  	  }
  	  
  	public Cursor obtenerClientesCenso()
  	{
  		String str = "_estado = 0";
  	    Cursor localCursor = this.db.query(true, CLIENTECENSO_TABLE_NAME, CLIENTECENSO_ALL_KEYS, str, null, null, null, null, null);
  	    if (localCursor != null) 
  	    {
  	      localCursor.moveToFirst();
  	    }
  	    return localCursor;
  	}
  	  
  	public Cursor obtenerClientesCensoPor(String codigo)
    	{
  		String str = "_codigo=" + codigo;
  	    Cursor localCursor = this.db.query(true, CLIENTECENSO_TABLE_NAME, CLIENTECENSO_ALL_KEYS, str, null, null, null, null, null);
  	    if (localCursor != null) 
  	    {
  	      localCursor.moveToFirst();
  	    }
  	    return localCursor;
    	}
  	
  	public Cursor obtenerClientesCensoPor(int id)
	{
		String str = "_id=" + id;
	    Cursor localCursor = this.db.query(true, CLIENTECENSO_TABLE_NAME, CLIENTECENSO_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	}
  	
  	public int modificarClienteCenso(int id,String codigo,String referencia,int tipoNegocioIdVender, String tipoNegocio, String contacto, 
			  						double latitud, double longitud,String nombres,String paterno,int creadorId,double latitudCreador,
			  						double longitudCreador,int zonaId,int rutaId,int diaId,int mercadoId,int diaCreacion,
			  						int mesCreacion,int anioCreacion,int estado,int clienteId,boolean sincro)
  	{
  		String str = "_id=" + id;
  		ContentValues localContentValues = new ContentValues();
  		localContentValues.put("_codigo", codigo);
  		localContentValues.put("_referencia", referencia);
  		localContentValues.put("_tipoNegocioIdVender", tipoNegocioIdVender);
  		localContentValues.put("_tipoNegocio",tipoNegocio);
  		localContentValues.put("_contacto", contacto);
  	    localContentValues.put("_latitud", latitud);
  	    localContentValues.put("_longitud", longitud);
  	    localContentValues.put("_nombres", nombres);
  	    localContentValues.put("_paterno", paterno);
  	    localContentValues.put("_creadorId", creadorId);
  	    localContentValues.put("_latitudCreador", latitudCreador);
  	    localContentValues.put("_longitudCreador", longitudCreador);
  	    localContentValues.put("_ZonaId", zonaId);
  	    localContentValues.put("_rutaId", rutaId);
  	    localContentValues.put("_diaId", diaId);
  	    localContentValues.put("_mercadoId", mercadoId);
  	    localContentValues.put("_diaCreacion", diaCreacion);
  	    localContentValues.put("_mesCreacion", mesCreacion);
  	    localContentValues.put("_anioCreacion", anioCreacion);
  	    localContentValues.put("_estado", estado);
  	    localContentValues.put("_clienteId", clienteId);
  	    localContentValues.put("_sincro", sincro);
  		return this.db.update(CLIENTECENSO_TABLE_NAME, localContentValues, str, null);
  	}
  	
  	public int modificarClienteCensoEstado(int id,int estado,String codigo,String referencia,String contacto,int motivoEliminacionId)
	{
	  	String str = "_id=" + id;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_estado", estado);
		localContentValues.put("_codigo", String.valueOf(codigo));
		localContentValues.put("_referencia", String.valueOf(referencia));
		localContentValues.put("_contacto", String.valueOf(contacto));
		localContentValues.put("_motivoEliminacionId", motivoEliminacionId);
		return this.db.update(CLIENTECENSO_TABLE_NAME, localContentValues, str, null);
	}
  	
  	public int modificarClienteCensoSincronizacion(int id,boolean sincro)
      {
        String str = "_id =" + id;
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("_sincro", sincro);
        return this.db.update(CLIENTECENSO_TABLE_NAME, localContentValues, str, null);
      }
  	
  	public Cursor obtenerClientesCensoNoSincronizados()
	{
		String str = "_sincro = 0 and _estado <> 0";
	    Cursor localCursor = this.db.query(true, CLIENTECENSO_TABLE_NAME, CLIENTECENSO_ALL_KEYS, str, null, null, null, null, null);
	    if (localCursor != null) 
	    {
	      localCursor.moveToFirst();
	    }
	    return localCursor;
	}
  	
  	//MOTIVOELIMINACIONMATCH//
    public static final String KEY_MOTIVOELIMINACIONMATCH_ROW_ID = "_id";
    public static final String KEY_MOTIVOELIMINACIONMATCH_MOTIVOID = "_motivoId";
    public static final String KEY_MOTIVOELIMINACIONMATCH_MOTIVO = "_motivo";
    
    public static final int COL_MOTIVOELIMINACIONMATCH_ROW_ID = 0;
    public static final int COL_MOTIVOELIMINACIONMATCH_MOTIVOID = 1;
    public static final int COL_MOTIVOELIMINACIONMATCH_MOTIVO = 2;
    
    public static final String[] MOTIVOELIMINACIONMATCH_ALL_KEYS = new String[] {
    		KEY_MOTIVOELIMINACIONMATCH_ROW_ID,KEY_MOTIVOELIMINACIONMATCH_MOTIVOID,KEY_MOTIVOELIMINACIONMATCH_MOTIVO};
    
    public static final String MOTIVOELIMINACIONMATCH_TABLE_NAME = "tbl_MotivoEliminacionMatch";
    
    public static final String MOTIVOELIMINACIONMATCH_TABLE_CREATE = "CREATE TABLE " + MOTIVOELIMINACIONMATCH_TABLE_NAME + " ("
    		+ KEY_MOTIVOELIMINACIONMATCH_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_MOTIVOELIMINACIONMATCH_MOTIVOID + " integer NOT NULL, "
    		+ KEY_MOTIVOELIMINACIONMATCH_MOTIVO + " text NOT NULL);";
    
    public boolean borrarMotivoEliminacionMatchPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(MOTIVOELIMINACIONMATCH_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarMotivosEliminacionMatch()
    {
    	Cursor localCursor = obtenerMotivosEliminacionMatch();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarMotivoEliminacionMatchPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarMotivoEliminacionMatch(ArrayList<MotivoEliminacionMatchWSResult> motivosEliminacion)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_MotivoEliminacionMatch(_motivoId, _motivo) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (MotivoEliminacionMatchWSResult item: motivosEliminacion) {

				stmt.bindLong(1, item.getMotivoId());
				stmt.bindString(2, item.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerMotivoEliminacionMatchPor(int motivoId)
    {
      String str = "_motivoId =" + motivoId;
      Cursor localCursor = this.db.query(true,MOTIVOELIMINACIONMATCH_TABLE_NAME,MOTIVOELIMINACIONMATCH_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerMotivosEliminacionMatch()
    {
    	Cursor localCursor = this.db.query(true,MOTIVOELIMINACIONMATCH_TABLE_NAME,MOTIVOELIMINACIONMATCH_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
  	
  	//ULTIMACOORDENADA//
    public static final String KEY_ULTIMACOORDENADA_ROW_ID = "_id";
    public static final String KEY_ULTIMACOORDENADA_CLIENTEID = "_clienteId";
    public static final String KEY_ULTIMACOORDENADA_LATITUD = "_latitud";
    public static final String KEY_ULTIMACOORDENADA_LONGITUD = "_longitud";
    
    public static final int COL_ULTIMACOORDENADA_ROW_ID = 0;
    public static final int COL_ULTIMACOORDENADA_CLIENTEID = 1;
    public static final int COL_ULTIMACOORDENADA_LATITUD = 2;
    public static final int COL_ULTIMACOORDENADA_LONGITUD = 3;
    
    public static final String[] ULTIMACOORDENADA_ALL_KEYS = new String[] {
    		KEY_ULTIMACOORDENADA_ROW_ID,KEY_ULTIMACOORDENADA_CLIENTEID,KEY_ULTIMACOORDENADA_LATITUD,
    		KEY_ULTIMACOORDENADA_LONGITUD};
    
    public static final String ULTIMACOORDENADA_TABLE_NAME = "tbl_UltimaCoordenada";
    
    public static final String ULTIMACOORDENADA_TABLE_CREATE = "CREATE TABLE " + ULTIMACOORDENADA_TABLE_NAME + " ("
    		+ KEY_ULTIMACOORDENADA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_ULTIMACOORDENADA_CLIENTEID + " integer NOT NULL,"
    		+ KEY_ULTIMACOORDENADA_LATITUD + " double NOT NULL, "
    		+ KEY_ULTIMACOORDENADA_LONGITUD + " double NOT NULL);";
    
    public boolean borrarUltimaCoordenadaPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ULTIMACOORDENADA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarUltimasCoordenada()
    {
    	Cursor localCursor = obtenerUltimasCoordenadas();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarUltimaCoordenadaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarUltimaCoordenada(int clienteId,double latitud,double longitud)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_clienteId", clienteId);
    	localContentValues.put("_latitud", latitud);
    	localContentValues.put("_longitud", longitud);
    	return this.db.insert(ULTIMACOORDENADA_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerUltimaCoordenadaPor(int clienteId)
    {
      String str = "_clienteId =" + clienteId;
      Cursor localCursor = this.db.query(true,ULTIMACOORDENADA_TABLE_NAME,ULTIMACOORDENADA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerUltimasCoordenadas()
    {
    	Cursor localCursor = this.db.query(true,ULTIMACOORDENADA_TABLE_NAME,ULTIMACOORDENADA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //CONDICIONTRIBUTARIA//
    public static final String KEY_CONDICIONTRIBUTARIA_ROW_ID = "_id";
    public static final String KEY_CONDICIONTRIBUTARIA_NIT = "_nit";
    public static final String KEY_CONDICIONTRIBUTARIA_ANIO = "_anio";
    public static final String KEY_CONDICIONTRIBUTARIA_MONTOACUMULADO = "_montoAcumulado";
    
    public static final int COL_CONDICIONTRIBUTARIA_ROW_ID = 0;
    public static final int COL_CONDICIONTRIBUTARIA_NIT = 1;
    public static final int COL_CONDICIONTRIBUTARIA_ANIO = 2;
    public static final int COL_CONDICIONTRIBUTARIA_MONTOACUMULADO = 3;
    
    public static final String[] CONDICIONTRIBUTARIA_ALL_KEYS = new String[] {
    		KEY_CONDICIONTRIBUTARIA_ROW_ID,KEY_CONDICIONTRIBUTARIA_NIT,KEY_CONDICIONTRIBUTARIA_ANIO,
    		KEY_CONDICIONTRIBUTARIA_MONTOACUMULADO};
    
    public static final String CONDICIONTRIBUTARIA_TABLE_NAME = "tbl_CondicionTributaria";
    
    public static final String CONDICIONTRIBUTARIA_TABLE_CREATE = "CREATE TABLE " + CONDICIONTRIBUTARIA_TABLE_NAME + " ("
    		+ KEY_CONDICIONTRIBUTARIA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_CONDICIONTRIBUTARIA_NIT + " text NOT NULL,"
    		+ KEY_CONDICIONTRIBUTARIA_ANIO + " int NOT NULL, "
    		+ KEY_CONDICIONTRIBUTARIA_MONTOACUMULADO + " float NOT NULL);";
    
    public boolean borrarCondicionTributariaPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(CONDICIONTRIBUTARIA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCondicionesTrbutarias()
    {
    	Cursor localCursor = obtenerCondicionesTributarias();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarCondicionTributariaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarCondicionTributaria(ArrayList<CondicionTributariaWSResult> condicionesTributaria)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_CondicionTributaria(_nit, _anio, _montoAcumulado) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (CondicionTributariaWSResult item : condicionesTributaria) {

				stmt.bindString(1, item.getNit());
				stmt.bindLong(2, item.getAnio());
				stmt.bindDouble(3, item.getMontoAcumulado());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerCondicionTributariaPor(String nit,int anio)
    {
    	String str = "_nit = '" + nit +"' and _anio = " + anio;
    	Cursor localCursor = this.db.query(true,CONDICIONTRIBUTARIA_TABLE_NAME,CONDICIONTRIBUTARIA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerCondicionesTributarias()
    {
    	Cursor localCursor = this.db.query(true,CONDICIONTRIBUTARIA_TABLE_NAME,CONDICIONTRIBUTARIA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //ENCUESTA//
    public static final String KEY_ENCUESTA_ROW_ID = "_id";
    public static final String KEY_ENCUESTA_ENCUESTAID = "_encuestaId";
    public static final String KEY_ENCUESTA_NOMBRE = "_nombre";
    public static final String KEY_ENCUESTA_ACTIVA = "_activa";
    
    public static final int COL_ENCUESTA_ROW_ID = 0;
    public static final int COL_ENCUESTA_ENCUESTAID = 1;
    public static final int COL_ENCUESTA_NOMBRE = 2;
    public static final int COL_ENCUESTA_ACTIVA = 3;
    
    public static final String[] ENCUESTA_ALL_KEYS = new String[] {
    		KEY_ENCUESTA_ROW_ID,KEY_ENCUESTA_ENCUESTAID,KEY_ENCUESTA_NOMBRE,
    		KEY_ENCUESTA_ACTIVA};
    
    public static final String ENCUESTA_TABLE_NAME = "tbl_Encuesta";
    
    public static final String ENCUESTA_TABLE_CREATE = "CREATE TABLE " + ENCUESTA_TABLE_NAME + " ("
    		+ KEY_ENCUESTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_ENCUESTA_ENCUESTAID + " int NOT NULL,"
    		+ KEY_ENCUESTA_NOMBRE + " text NOT NULL, "
    		+ KEY_ENCUESTA_ACTIVA + " boolean NOT NULL);";
    
    public boolean borrarEncuestaPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ENCUESTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarEncuestas()
    {
    	Cursor localCursor = obtenerEncuestas();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarEncuestaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarEncuesta(ArrayList<EncuestaWSResult> encuestas)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_Encuesta(_encuestaId, _nombre, _activa) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (EncuestaWSResult item : encuestas) {

				stmt.bindLong(1, item.getEncuestaId());
				stmt.bindString(2, item.getNombre());
				stmt.bindLong(3, item.isActiva()?1:0);

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerEncuestaPor(int encuestaId)
    {
    	String str = "_encuestaId = " + encuestaId;
    	Cursor localCursor = this.db.query(true,ENCUESTA_TABLE_NAME,ENCUESTA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerEncuestas()
    {
    	Cursor localCursor = this.db.query(true,ENCUESTA_TABLE_NAME,ENCUESTA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //ENCUESTADETALLE//
    public static final String KEY_ENCUESTADETALLE_ROW_ID = "_id";
    public static final String KEY_ENCUESTADETALLE_DETALLEID = "_detalleId";
    public static final String KEY_ENCUESTADETALLE_ENCUESTAID = "_encuestaId";
    public static final String KEY_ENCUESTADETALLE_PREGUNTA = "_pregunta";
    public static final String KEY_ENCUESTADETALLE_DEFINICION = "_definicion";
    public static final String KEY_ENCUESTADETALLE_TIPOPREGUNTA = "_tipoPregunta";
    public static final String KEY_ENCUESTADETALLE_ESPECIFICACION = "_especificacion";
    public static final String KEY_ENCUESTADETALLE_ESTADO = "_estado";
    public static final String KEY_ENCUESTADETALLE_ORDEN = "_orden";
    public static final String KEY_ENCUESTADETALLE_TIPOESPECIFICACION = "_tipoEspecificacion";
    
    public static final int COL_ENCUESTADETALLE_ROW_ID = 0;
    public static final int COL_ENCUESTADETALLE_DETALLEID = 1;
    public static final int COL_ENCUESTADETALLE_ENCUENTAID = 2;
    public static final int COL_ENCUESTADETALLE_PREGUNTA = 3;
    public static final int COL_ENCUESTADETALLE_DEFINICION = 4;
    public static final int COL_ENCUESTADETALLE_TIPOPREGUNTA = 5;
    public static final int COL_ENCUESTADETALLE_ESPECIFICACION = 6;
    public static final int COL_ENCUESTADETALLE_ESTADO = 7;
    public static final int COL_ENCUESTADETALLE_ORDEN = 8;
    public static final int COL_ENCUESTADETALLE_TIPOESPECIFICACION = 9;
    
    public static final String[] ENCUESTADETALLE_ALL_KEYS = new String[] {
    		KEY_ENCUESTADETALLE_ROW_ID,KEY_ENCUESTADETALLE_DETALLEID,KEY_ENCUESTADETALLE_ENCUESTAID,
    		KEY_ENCUESTADETALLE_PREGUNTA,KEY_ENCUESTADETALLE_DEFINICION,KEY_ENCUESTADETALLE_TIPOPREGUNTA,
    		KEY_ENCUESTADETALLE_ESPECIFICACION,KEY_ENCUESTADETALLE_ESTADO,KEY_ENCUESTADETALLE_ORDEN,
    		KEY_ENCUESTADETALLE_TIPOESPECIFICACION};
    
    public static final String ENCUESTADETALLE_TABLE_NAME = "tbl_EncuestaDetalle";
    
    public static final String ENCUESTADETALLE_TABLE_CREATE = "CREATE TABLE " + ENCUESTADETALLE_TABLE_NAME + " ("
    		+ KEY_ENCUESTADETALLE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_ENCUESTADETALLE_DETALLEID + " int NOT NULL,"
    		+ KEY_ENCUESTADETALLE_ENCUESTAID + " int NOT NULL,"
    		+ KEY_ENCUESTADETALLE_PREGUNTA + " text NOT NULL,"
    		+ KEY_ENCUESTADETALLE_DEFINICION + " text NOT NULL,"
    		+ KEY_ENCUESTADETALLE_TIPOPREGUNTA + " text NOT NULL,"
    		+ KEY_ENCUESTADETALLE_ESPECIFICACION + " text NOT NULL,"
    		+ KEY_ENCUESTADETALLE_ESTADO + " boolean NOT NULL,"
    		+ KEY_ENCUESTADETALLE_ORDEN + " int NOT NULL,"
    		+ KEY_ENCUESTADETALLE_TIPOESPECIFICACION + " text NOT NULL);";
    
    public boolean borrarEncuestaDetallePor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ENCUESTADETALLE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarEncuestasDetalle()
    {
    	Cursor localCursor = obtenerEncuestasDetalle();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarEncuestaDetallePor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarEncuestaDetalle(ArrayList<EncuestaDetalleWSResult> encuestasDetalle)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_EncuestaDetalle(_detalleId, _encuestaId, _pregunta, _definicion, _tipoPregunta, _especificacion, _estado, _orden, _tipoEspecificacion) " +
						"VALUES (?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (EncuestaDetalleWSResult item : encuestasDetalle) {

				stmt.bindLong(1, item.getDetalleId());
				stmt.bindLong(2, item.getEncuestaId());
				stmt.bindString(3, item.getPregunta());
				stmt.bindString(4, item.getDefinicion());
				stmt.bindString(5, item.getTipoPregunta());
				stmt.bindString(6, item.getEspecificar());
				stmt.bindLong(7, item.isEstado()?1:0);
				stmt.bindLong(8, item.getOrden());
				stmt.bindString(9, item.getTipoPregunta());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerEncuestaDetallePor(int encuestaId)
    {
    	String str = "_encuestaId = " + encuestaId;
    	Cursor localCursor = this.db.query(true,ENCUESTADETALLE_TABLE_NAME,ENCUESTADETALLE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    
    public Cursor obtenerEncuestasDetalle()
    {
    	Cursor localCursor = this.db.query(true,ENCUESTADETALLE_TABLE_NAME,ENCUESTADETALLE_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //ENCUESTARESPUESTA//
    public static final String KEY_ENCUESTARESPUESTA_ROW_ID = "_id";
    public static final String KEY_ENCUENTARESPUESTA_ENCUESTARESPUESTAID = "_encuestaRespuestaId";
    public static final String KEY_ENCUESTARESPUESTA_DETALLEID = "_detalleId";
    public static final String KEY_ENCUESTARESPUESTA_CLIENTEID = "_clienteId";
    public static final String KEY_ENCUESTARESPUESTA_RESPUESTA = "_respuesta";
    public static final String KEY_ENCUESTARESPUESTA_ESPECIFICACION = "_especificacion";
    public static final String KEY_ENCUESTARESPUESTA_OBSERVACION = "_observacion";
    public static final String KEY_ENCUESTARESPUESTA_EMPLEADOID = "_empleadoId";
    public static final String KEY_ENCUESTARESPUESTA_DIA = "_dia";
    public static final String KEY_ENCUESTARESPUESTA_MES = "_mes";
    public static final String KEY_ENCUESTARESPUESTA_ANIO = "_anio";
    
    public static final int COL_ENCUESTARESPUESTA_ROW_ID = 0;
    public static final int COL_ENCUESTARESPUESTA_ENCUESTARESPUESTAID = 1;
    public static final int COL_ENCUESTARESPUESTA_DETALLEID = 2;
    public static final int COL_ENCUESTARESPUESTA_CLIENTEID = 3;
    public static final int COL_ENCUESTARESPUESTA_RESPUESTA = 4;
    public static final int COL_ENCUESTARESPUESTA_ESPECIFICACION = 5;
    public static final int COL_ENCUESTARESPUESTA_OBSERVACION = 6;
    public static final int COL_ENCUESTARESPUESTA_EMPLEADOID = 7;
    public static final int COL_ENCUESTARESPUESTA_DIA = 8;
    public static final int COL_ENCUESTARESPUESTA_MES = 9;
    public static final int COL_ENCUESTARESPUESTA_ANIO = 10;
    
    public static final String[] ENCUESTARESPUESTA_ALL_KEYS = new String[] {
    		KEY_ENCUESTARESPUESTA_ROW_ID,KEY_ENCUENTARESPUESTA_ENCUESTARESPUESTAID,KEY_ENCUESTARESPUESTA_DETALLEID,
    		KEY_ENCUESTARESPUESTA_CLIENTEID,KEY_ENCUESTARESPUESTA_RESPUESTA,KEY_ENCUESTARESPUESTA_ESPECIFICACION,
    		KEY_ENCUESTARESPUESTA_OBSERVACION,KEY_ENCUESTARESPUESTA_EMPLEADOID,KEY_ENCUESTARESPUESTA_DIA,
    		KEY_ENCUESTARESPUESTA_MES,KEY_ENCUESTARESPUESTA_ANIO};
    
    public static final String ENCUESTARESPUESTA_TABLE_NAME = "tbl_EncuestaRespuesta";
    
    public static final String ENCUESTARESPUESTA_TABLE_CREATE = "CREATE TABLE " + ENCUESTARESPUESTA_TABLE_NAME + " ("
    		+ KEY_ENCUESTARESPUESTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_ENCUENTARESPUESTA_ENCUESTARESPUESTAID + " int NOT NULL,"
    		+ KEY_ENCUESTARESPUESTA_DETALLEID + " int NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_CLIENTEID + " int NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_RESPUESTA + " text NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_ESPECIFICACION + " text NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_OBSERVACION + " text NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_EMPLEADOID + " text NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_DIA + " int NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_MES + " int NOT NULL, "
    		+ KEY_ENCUESTARESPUESTA_ANIO + " int NOT NULL);";
    
    public boolean borrarEncuestaRespuestaPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ENCUESTARESPUESTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarEncuestasRespuesta()
    {
    	Cursor localCursor = obtenerEncuestasRespuesta();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarEncuestaRespuestaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarEncuestaRespuesta(int encuestaRespuestaId,int detalleId,int clienteId,
    									String respuesta,String especificacion,String observacion,
    									int empleadoId,int dia,int mes,int anio)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_encuestaRespuestaId", encuestaRespuestaId);
    	localContentValues.put("_detalleId", detalleId);
    	localContentValues.put("_clienteId", clienteId);
    	localContentValues.put("_respuesta", String.valueOf(respuesta));
    	localContentValues.put("_especificacion", String.valueOf(especificacion));
    	localContentValues.put("_observacion", String.valueOf(observacion));
    	localContentValues.put("_empleadoId", empleadoId);
    	localContentValues.put("_dia", dia);
    	localContentValues.put("_mes", mes);
    	localContentValues.put("_anio", anio);
    	return this.db.insert(ENCUESTARESPUESTA_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerEncuestaRespuestaPor(int encuestaRespuestaId)
    {
    	String str = "_encuestaRespuestaId = " + encuestaRespuestaId;
    	Cursor localCursor = this.db.query(true,ENCUESTARESPUESTA_TABLE_NAME,ENCUESTARESPUESTA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerEncuestasRespuesta()
    {
    	Cursor localCursor = this.db.query(true,ENCUESTARESPUESTA_TABLE_NAME,ENCUESTARESPUESTA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerEncuestasRespuestaRangoDetalle(int clienteId,int detalleInicio,int detalleFin)
    {
    	String str = "_clienteId =" + clienteId + " and _detalleId >= " + detalleInicio + " and _detalleId <= " + detalleFin;
    	Cursor localCursor = this.db.query(true,ENCUESTARESPUESTA_TABLE_NAME,ENCUESTARESPUESTA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerEncuestasRespuestaRangoDetalleNoSincro(int clienteId,int detalleInicio,int detalleFin)
    {
    	String str = "_clienteId =" + clienteId + " and _detalleId >= " + detalleInicio + " and _detalleId <= " + detalleFin + " and _especificacion = 0";
    	Cursor localCursor = this.db.query(true,ENCUESTARESPUESTA_TABLE_NAME,ENCUESTARESPUESTA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public int ModificarEncuestaRespuestaSincro(int id,int especificacion)
    {
      String str = "_id=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_especificacion", especificacion);
      return this.db.update(ENCUESTARESPUESTA_TABLE_NAME, localContentValues, str, null);
    }
    
    //ENCUESTALISTA//
    public static final String KEY_ENCUESTALISTA_ROW_ID = "_id";
    public static final String KEY_ENCUESTALISTA_DETALLEID = "_detalleId";
    public static final String KEY_ENCUESTALISTA_VALOR = "_valor";
    
    public static final int COL_ENCUESTALISTA_ROW_ID = 0;
    public static final int COL_ENCUESTALISTA_DETALLEID = 1;
    public static final int COL_ENCUESTALISTA_VALOR = 2;
    
    public static final String[] ENCUESTALISTA_ALL_KEYS = new String[] {
    		KEY_ENCUESTALISTA_ROW_ID,KEY_ENCUESTALISTA_DETALLEID,KEY_ENCUESTALISTA_VALOR};
    
    public static final String ENCUESTALISTA_TABLE_NAME = "tbl_EncuestaLista";
    
    public static final String ENCUESTALISTA_TABLE_CREATE = "CREATE TABLE " + ENCUESTALISTA_TABLE_NAME + " ("
    		+ KEY_ENCUESTALISTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_ENCUESTALISTA_DETALLEID + " int NOT NULL,"
    		+ KEY_ENCUESTALISTA_VALOR + " text NOT NULL);";
    
    public boolean borrarEncuestaListaPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ENCUESTALISTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarEncuestasLista()
    {
    	Cursor localCursor = obtenerEncuestasLista();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarEncuestaListaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarEncuestaLista(ArrayList<EncuestaListaWSResult> encuestasLista)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_EncuestaLista(_detalleId, _valor) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (EncuestaListaWSResult item : encuestasLista) {

				stmt.bindLong(1, item.getDetalleId());
				stmt.bindString(2, item.getValor());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerEncuestaListaPor(int detalleId)
    {
    	String str = "_detalleId = " + detalleId;
    	Cursor localCursor = this.db.query(true,ENCUESTALISTA_TABLE_NAME,ENCUESTALISTA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerEncuestasLista()
    {
    	Cursor localCursor = this.db.query(true,ENCUESTALISTA_TABLE_NAME,ENCUESTALISTA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //CLIENTEENCUESTADO//
    public static final String KEY_CLIENTEENCUESTADO_ROW_ID = "_id";
    public static final String KEY_CLIENTEENCUESTADO_CLIENTEID = "_clienteId";
    public static final String KEY_CLIENTEENCUESTADO_ENCUESTAID = "_encuestaId";
    
    public static final int COL_CLIENTEENCUESTADO_ROW_ID = 0;
    public static final int COL_CLIENTEENCUESTADO_CLIENTEID = 1;
    public static final int COL_CLIENTEENCUESTADO_ENCUESTAID = 2;
    
    public static final String[] CLIENTEENCUESTADO_ALL_KEYS = new String[] {
    		KEY_CLIENTEENCUESTADO_ROW_ID,KEY_CLIENTEENCUESTADO_CLIENTEID,KEY_CLIENTEENCUESTADO_ENCUESTAID};
    
    public static final String CLIENTEENCUESTADO_TABLE_NAME = "tbl_ClienteEncuestado";
    
    public static final String CLIENTEENCUESTADO_TABLE_CREATE = "CREATE TABLE " + CLIENTEENCUESTADO_TABLE_NAME + " ("
    		+ KEY_CLIENTEENCUESTADO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_CLIENTEENCUESTADO_CLIENTEID + " int NOT NULL, " 
    		+ KEY_CLIENTEENCUESTADO_ENCUESTAID + " int NOT NULL);";
    
    public boolean borrarClienteEncuestadoPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(CLIENTEENCUESTADO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarClientesEncuestado()
    {
    	Cursor localCursor = obtenerClientesEncuestado();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarClienteEncuestadoPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarClienteEncuestado(ArrayList<ClienteEncuestadoWSResult> clientesEncuestado)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClienteEncuestado(_clienteId, _encuestaId) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (ClienteEncuestadoWSResult item : clientesEncuestado) {

				stmt.bindLong(1, item.getClienteId());
				stmt.bindLong(2, item.getClienteId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerClienteEncuestadoPor(int clienteId)
    {
    	String str = "_clienteId = " + clienteId;
    	Cursor localCursor = this.db.query(true,CLIENTEENCUESTADO_TABLE_NAME,CLIENTEENCUESTADO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerClientesEncuestado()
    {
    	Cursor localCursor = this.db.query(true,CLIENTEENCUESTADO_TABLE_NAME,CLIENTEENCUESTADO_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
	
	//AVANCEVENTAVENDEDOR//
    public static final String KEY_AVANCEVENTAVENDEDOR_ROW_ID = "_id";
    public static final String KEY_AVANCEVENTAVENDEDOR_VENDEDORID = "_vendedorId";
    public static final String KEY_AVANCEVENTAVENDEDOR_DIA = "_dia";
    public static final String KEY_AVANCEVENTAVENDEDOR_MES = "_mes";
    public static final String KEY_AVANCEVENTAVENDEDOR_ANIO = "_anio";
    public static final String KEY_AVANCEVENTAVENDEDOR_NOMBREVENDEDOR = "_nombreVendedor";
    public static final String KEY_AVANCEVENTAVENDEDOR_PRESUPUESTO = "_presupuesto";
    public static final String KEY_AVANCEVENTAVENDEDOR_AVANCE = "_avance";
    public static final String KEY_AVANCEVENTAVENDEDOR_TENDENCIA = "_tendencia";
    public static final String KEY_AVANCEVENTAVENDEDOR_COBERTURA = "_cobertura";
    public static final String KEY_AVANCEVENTAVENDEDOR_OBJETO = "_objeto";
    public static final String KEY_AVANCEVENTAVENDEDOR_COBERTURAPORCENTAJE = "_coberturaPorcentaje";
    
    public static final int COL_AVANCEVENTAVENDEDOR_ROW_ID = 0;
    public static final int COL_AVANCEVENTAVENDEDOR_VENDEDORID = 1;
    public static final int COL_AVANCEVENTAVENDEDOR_DIA = 2;
    public static final int COL_AVANCEVENTAVENDEDOR_MES = 3;
    public static final int COL_AVANCEVENTAVENDEDOR_ANIO = 4;
    public static final int COL_AVANCEVENTAVENDEDOR_NOMBREVENDEDOR = 5;
    public static final int COL_AVANCEVENTAVENDEDOR_PRESUPUESTO = 6;
    public static final int COL_AVANCEVENTAVENDEDOR_AVANCE = 7;
    public static final int COL_AVANCEVENTAVENDEDOR_TENDENCIA = 8;
    public static final int COL_AVANCEVENTAVENDEDOR_COBERTURA = 9;
    public static final int COL_AVANCEVENTAVENDEDOR_OBJETO = 10;
    public static final int COL_AVANCEVENTAVENDEDOR_COBERTURAPORCENTAJE = 11;    
    
    public static final String[] AVANCEVENTAVENDEDOR_ALL_KEYS = new String[] {
    		KEY_AVANCEVENTAVENDEDOR_ROW_ID,KEY_AVANCEVENTAVENDEDOR_VENDEDORID,KEY_AVANCEVENTAVENDEDOR_DIA,
    		KEY_AVANCEVENTAVENDEDOR_MES,KEY_AVANCEVENTAVENDEDOR_ANIO,KEY_AVANCEVENTAVENDEDOR_NOMBREVENDEDOR,
    		KEY_AVANCEVENTAVENDEDOR_PRESUPUESTO,KEY_AVANCEVENTAVENDEDOR_AVANCE,KEY_AVANCEVENTAVENDEDOR_TENDENCIA,
    		KEY_AVANCEVENTAVENDEDOR_COBERTURA,KEY_AVANCEVENTAVENDEDOR_OBJETO,KEY_AVANCEVENTAVENDEDOR_COBERTURAPORCENTAJE};
    
    public static final String AVANCEVENTAVENDEDOR_TABLE_NAME = "tbl_AvanceVentaVendedor";
    
    public static final String AVANCEVENTAVENDEDOR_TABLE_CREATE = "CREATE TABLE " + AVANCEVENTAVENDEDOR_TABLE_NAME + " ("
    		+ KEY_AVANCEVENTAVENDEDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_AVANCEVENTAVENDEDOR_VENDEDORID + " int NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_DIA + " int NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_MES + " int NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_ANIO + " int NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_NOMBREVENDEDOR + " text NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_PRESUPUESTO + " float NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_AVANCE + " float NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_TENDENCIA + " float NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_COBERTURA + " int NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_OBJETO + " text NOT NULL, "
    		+ KEY_AVANCEVENTAVENDEDOR_COBERTURAPORCENTAJE + " float NOT NULL);";
    
    public boolean borrarAvanceVentaVendedorPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(AVANCEVENTAVENDEDOR_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarAvancesVentaVendedor()
    {
    	Cursor localCursor = obtenerAvancesVentaVendedor();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarAvanceVentaVendedorPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarAvanceVentaVendedor(ArrayList<AvanceVentaVendedorWSResult> avancesVentaVendedor, int dia)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_AvanceVentaVendedor(_vendedorId, _dia, _mes, _anio, _nombreVendedor, _presupuesto, _avance, _tendencia, _cobertura, _objeto, _coberturaPorcentaje) " +
						"VALUES (?,?,?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (AvanceVentaVendedorWSResult item : avancesVentaVendedor) {

				stmt.bindLong(1, item.getVendedorId());
				stmt.bindLong(2, dia);
				stmt.bindLong(3, item.getMes());
				stmt.bindLong(4, item.getAnio());
				stmt.bindString(5, item.getNombreVendedor());
				stmt.bindDouble(6, item.getPresupuesto());
				stmt.bindDouble(7, item.getAvance());
				stmt.bindDouble(8, item.getTendencia());
				stmt.bindLong(9, item.getCobertura());
				stmt.bindString(10, item.getObjeto());
				stmt.bindDouble(11, item.getCoberturaPorcentaje());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerAvanceVentaVendedorPor(int vendedorId)
    {
    	String str = "_vendedorId = " + vendedorId;
    	Cursor localCursor = this.db.query(true,AVANCEVENTAVENDEDOR_TABLE_NAME,AVANCEVENTAVENDEDOR_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerAvancesVentaVendedor()
    {
    	Cursor localCursor = this.db.query(true,AVANCEVENTAVENDEDOR_TABLE_NAME,AVANCEVENTAVENDEDOR_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //MOTIVOPOP//
    public static final String KEY_MOTIVOPOP_ROW_ID = "_id";
    public static final String KEY_MOTIVOPOP_MOTIVOPOPID = "_motivoPopId";
    public static final String KEY_MOTIVOPOP_DESCRIPCION = "_descripcion";
    
    public static final int COL_MOTIVOPOP_ROW_ID = 0;
    public static final int COL_MOTIVOPOP_MOTIVOPOPID = 1;
    public static final int COL_MOTIVOPOP_DESCRIPCION = 2;
    
    public static final String[] MOTIVOPOP_ALL_KEYS = new String[] {
    		KEY_MOTIVOPOP_ROW_ID,KEY_MOTIVOPOP_MOTIVOPOPID,KEY_MOTIVOPOP_DESCRIPCION};
    
    public static final String MOTIVOPOP_TABLE_NAME = "tbl_MotivoPop";
    
    public static final String MOTIVOPOP_TABLE_CREATE = "CREATE TABLE " + MOTIVOPOP_TABLE_NAME + " ("
    		+ KEY_MOTIVOPOP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_MOTIVOPOP_MOTIVOPOPID + " int NOT NULL, "
    		+ KEY_MOTIVOPOP_DESCRIPCION + " text NOT NULL);";
    
    public boolean borrarMotivoPopPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(MOTIVOPOP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarMotivosPop()
    {
    	Cursor localCursor = obtenerMotivosPop();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarMotivoPopPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarMotivoPop(ArrayList<MotivoPopWSResult> motivosPOP)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_MotivoPOP(_motivoPopId, _descripcion) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (MotivoPopWSResult item : motivosPOP) {

				stmt.bindLong(1, item.getMotivoPopId());
				stmt.bindString(2, item.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerMotivoPopPor(int motivoPopId)
    {
    	String str = "_motivoPopId = " + motivoPopId;
    	Cursor localCursor = this.db.query(true,MOTIVOPOP_TABLE_NAME,MOTIVOPOP_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerMotivosPop()
    {
    	Cursor localCursor = this.db.query(true,MOTIVOPOP_TABLE_NAME,MOTIVOPOP_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //ALMACENCHANGE//
    public static final String KEY_ALMACENCHANGE_ROW_ID = "_id";
    public static final String KEY_ALMACENCHANGE_ALMACENID = "_almacenId";
    public static final String KEY_ALMACENCHANGE_DESCRIPCION = "_descripcion";
    
    public static final int COL_ALMACENCHANGE_ROW_ID = 0;
    public static final int COL_ALMACENCHANGE_ALMACENID = 1;
    public static final int COL_ALMACENCHANGE_DESCRIPCION = 2;
    
    public static final String[] ALMACENCHANGE_ALL_KEYS = new String[] { 
    	KEY_ALMACENCHANGE_ROW_ID,KEY_ALMACENCHANGE_ALMACENID,KEY_ALMACENCHANGE_DESCRIPCION
    	};
    
    public static final String ALMACENCHANGE_TABLE_NAME = "tbl_AlmacenCHANGE";
    
    public static final String ALMACENCHANGE_TABLE_CREATE = "CREATE TABLE " + ALMACENCHANGE_TABLE_NAME + "("
    		+ KEY_ALMACENCHANGE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ALMACENCHANGE_ALMACENID + " integer NOT NULL, "
    		+ KEY_ALMACENCHANGE_DESCRIPCION + " text NOT NULL);";

    public boolean borrarAlmacenCHANGEPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ALMACENCHANGE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarAlmacenesCHANGE()
    {
      Cursor localCursor = obtenerAlmacenesCHANGE();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarAlmacenCHANGEPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarAlmacenCHANGE(int almacenId, String descripcion)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_almacenId", almacenId);
      localContentValues.put("_descripcion", String.valueOf(descripcion));
      return this.db.insert(ALMACENCHANGE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerAlmacenCHANGEPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,ALMACENCHANGE_TABLE_NAME,ALMACENCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerAlmacenesCHANGE()
    {
      Cursor localCursor = this.db.query(true,ALMACENCHANGE_TABLE_NAME, ALMACENCHANGE_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
  //ALMACENCHANGEPRODUCTO//
    public static final String KEY_ALMACENCHANGEPRODUCTO_ROW_ID = "_id";
    public static final String KEY_ALMACENCHANGEPRODUCTO_ALMACENID = "_almacenId";
    public static final String KEY_ALMACENCHANGEPRODUCTO_PRODUCTOID = "_productoId";
    public static final String KEY_ALMACENCHANGEPRODUCTO_SALDO = "_saldo";
    
    public static final int COL_ALMACENCHANGERODUCTOP_ROW_ID = 0;
    public static final int COL_ALMACENCHANGEPRODUCTO_ALMACENID = 1;
    public static final int COL_ALMACENCHANGEPRODUCTO_PRODUCTOID = 2;
    public static final int COL_ALMACENCHANGEPRODUCTO_SALDO = 3;
    
    public static final String[] ALMACENCHANGEPRODUCTO_ALL_KEYS = new String[] { 
    		KEY_ALMACENCHANGEPRODUCTO_ROW_ID,KEY_ALMACENCHANGEPRODUCTO_ALMACENID,KEY_ALMACENCHANGEPRODUCTO_PRODUCTOID,
    		KEY_ALMACENCHANGEPRODUCTO_SALDO};
    
    public static final String ALMACENCHANGEPRODUCTO_TABLE_NAME = "tbl_AlmacenCHANGEProducto";
    
    public static final String ALMACENCHANGEPRODUCTO_TABLE_CREATE = "CREATE TABLE " + ALMACENCHANGEPRODUCTO_TABLE_NAME + "("
    		+ KEY_ALMACENCHANGEPRODUCTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ALMACENCHANGEPRODUCTO_ALMACENID + " integer NOT NULL, "
    		+ KEY_ALMACENCHANGEPRODUCTO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_ALMACENCHANGEPRODUCTO_SALDO + " integer NOT NULL);";

    public boolean borrarAlmacenCHANGEProductoPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(ALMACENCHANGEPRODUCTO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarAlmacenesCHANGEProducto()
    {
      Cursor localCursor = obtenerAlmacenesCHANGEProducto();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarAlmacenCHANGEProductoPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarAlmacenCHANGEProducto(int almacenId, int productoId,int saldo)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_almacenId", almacenId);
      localContentValues.put("_productoId", productoId);
      localContentValues.put("_saldo", saldo);
      return this.db.insert(ALMACENCHANGEPRODUCTO_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerAlmacenCHANGEProductoPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,ALMACENCHANGEPRODUCTO_TABLE_NAME,ALMACENCHANGEPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerAlmacenesCHANGEProducto()
    {
      Cursor localCursor = this.db.query(true,ALMACENCHANGEPRODUCTO_TABLE_NAME, ALMACENCHANGEPRODUCTO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerAlmacenCHANGEProductoPorProductoId(int productoId)
    {
      String str = "_productoId" + "=" + productoId;
      Cursor localCursor = this.db.query(true, ALMACENCHANGEPRODUCTO_TABLE_NAME, ALMACENCHANGEPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor ValidarExistenciasAlmacenCHANGEDispositivo(int productoId,int cantidad)
    {
      String str = "_productoId =" + productoId + " and _saldo >= " + cantidad;
     
      Cursor localCursor = this.db.query(true, ALMACENCHANGEPRODUCTO_TABLE_NAME, ALMACENCHANGEPRODUCTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //CATEGORIACHANGE//
    public static final String KEY_CATEGORIACHANGE_ROW_ID = "_id";
    public static final String KEY_CATEGORIACHANGE_CATEGORIAID = "_categoriaId";
    public static final String KEY_CATEGORIACHANGE_NOMBRE = "_nombre";
    public static final String KEY_CATEGORIACHANGE_PROVEEDORID = "_proveedorId";
    
    public static final int COL_CATEGORIACHANGE_ROW_ID = 0;
    public static final int COL_CATEGORIACHANGE_CATEGORIAID = 1;
    public static final int COL_CATEGORIACHANGE_NOMBRE = 2;
    public static final int COL_CATEGORIACHANGE_PROVEEDORID = 3;
    
    public static final String[] CATEGORIACHANGE_ALL_KEYS = new String[] { 
    		KEY_CATEGORIACHANGE_ROW_ID,KEY_CATEGORIACHANGE_CATEGORIAID,KEY_CATEGORIACHANGE_NOMBRE,
    		KEY_CATEGORIACHANGE_PROVEEDORID};
    
    public static final String CATEGORIACHANGE_TABLE_NAME = "tbl_CategoriaCHANGE";
    
    public static final String CATEGORIACHANGE_TABLE_CREATE = "CREATE TABLE " + CATEGORIACHANGE_TABLE_NAME + "("
    		+ KEY_CATEGORIACHANGE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CATEGORIACHANGE_CATEGORIAID + " integer NOT NULL, "
    		+ KEY_CATEGORIACHANGE_NOMBRE + " text NOT NULL, "
    		+ KEY_CATEGORIACHANGE_PROVEEDORID + " integer NOT NULL);";

    public boolean borrarCategoriaCHANGEPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(CATEGORIACHANGE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCategoriasCHANGE()
    {
      Cursor localCursor = obtenerCategoriasCHANGE();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarCategoriaPOPPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarCategoriaCHANGE(int categoriaId,String nombre,int proveedorId)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_categoriaId", categoriaId);
      localContentValues.put("_nombre", String.valueOf(nombre));
      localContentValues.put("_proveedorId", proveedorId);
      return this.db.insert(CATEGORIACHANGE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerCateoriaCHANGEPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,CATEGORIACHANGE_TABLE_NAME,CATEGORIACHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerCategoriasCHANGE()
    {
      Cursor localCursor = this.db.query(true,CATEGORIACHANGE_TABLE_NAME,CATEGORIACHANGE_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerCategoriasCHANGEPorProvedorId(int proveedorId)
    {
    	String str = "_proveedorId=" + proveedorId;
    	Cursor localCursor = this.db.query(true,CATEGORIACHANGE_TABLE_NAME,CATEGORIACHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PRODUCTOCHANGE//
    public static final String KEY_PRODUCTOCHANGE_ROW_ID = "_id";
    public static final String KEY_PRODUCTOCHANGE_PRODUCTOID = "_productoId";
    public static final String KEY_PRODUCTOCHANGE_DESCRIPCION25 = "_descripcion25";
    public static final String KEY_PRODUCTOCHANGE_CATEGORIAID = "_categoriaId";
    public static final String KEY_PRODUCTOCHANGE_CODIGOBARRA = "_codigoBarra";
    
    public static final int COL_PRODUCTOCHANGE_ROW_ID = 0;
    public static final int COL_PRODUCTOCHANGE_PRODUCTOID = 1;
    public static final int COL_PRODUCTOCHANGE_DESCRIPCION25 = 2;
    public static final int COL_PRODUCTOCHANGE_CATEGORIAID = 3;
    public static final int COL_PRODUCTOCHANGE_CODIGOBARRA = 4;
    
    public static final String[] PRODUCTOCHANGE_ALL_KEYS = new String[] { 
    		KEY_PRODUCTOCHANGE_ROW_ID,KEY_PRODUCTOCHANGE_PRODUCTOID,KEY_PRODUCTOCHANGE_DESCRIPCION25,
    		KEY_PRODUCTOCHANGE_CATEGORIAID,KEY_PRODUCTOCHANGE_CODIGOBARRA};
    
    public static final String PRODUCTOCHANGE_TABLE_NAME = "tbl_ProductoCHANGE";
    
    public static final String PRODUCTOCHANGE_TABLE_CREATE = "CREATE TABLE " + PRODUCTOCHANGE_TABLE_NAME + "("
    		+ KEY_PRODUCTOCHANGE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRODUCTOCHANGE_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCHANGE_DESCRIPCION25 + " text NOT NULL, "
    		+ KEY_PRODUCTOCHANGE_CATEGORIAID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCHANGE_CODIGOBARRA + " text NOT NULL);";

    public boolean borrarProductoCHANGEPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PRODUCTOCHANGE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProductosCHANGE()
    {
      Cursor localCursor = obtenerProductosCHANGE();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarProductoCHANGEPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarProductoCHANGE(int productoId,String descripcion25,int categoriaId,String codigoBarra)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_productoId", productoId);
      localContentValues.put("_descripcion25", String.valueOf(descripcion25));
      localContentValues.put("_categoriaId", categoriaId);
      localContentValues.put("_codigoBarra", String.valueOf(codigoBarra));
      return this.db.insert(PRODUCTOCHANGE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerProductoCHANGEPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,PRODUCTOCHANGE_TABLE_NAME,PRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerProductosCHANGE()
    {
      Cursor localCursor = this.db.query(true,PRODUCTOCHANGE_TABLE_NAME,PRODUCTOCHANGE_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProductosCHANGEPorProveedorNoEnPreventaProductoCHANGE(int proveedorId,int categoriaId,int preventaPOPId)
    {
    	String query = "SELECT P._productoId, P._descripcion25, P._categoriaId, P._codigoBarra " +
    					"FROM tbl_productoCHANGE AS P " +
    					"JOIN tbl_almacenCHANGEProducto AS AP ON (P._productoId = AP._productoId) " +
    					"WHERE (AP._saldo > 0) AND P._categoriaId = ? " +
    					//"AND P._productoId NOT IN (SELECT PPP._productoCHANGEId " +
    					//"FROM tbl_PreventaProductoCHANGE as PPP " +
    					//"WHERE _preventaCHANGEId = ?) " +
    					"ORDER BY P._descripcion25";
    	
    	Cursor localCursor = db.rawQuery(query,new String[]{String.valueOf(categoriaId)});//,String.valueOf(preventaCHANGEId)});
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerProductoCHANGEPorProductoId(int productoId)
    {
    	String str = "_productoId= " +productoId;
    	Cursor localCursor = this.db.query(true,PRODUCTOCHANGE_TABLE_NAME,PRODUCTOCHANGE_ALL_KEYS,str, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PREVENTAPRODUCTOCHANGE//
    public static final String KEY_PREVENTAPRODUCTOCHANGE_ROW_ID = "_id";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEID = "_preventaCHANGEId";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEIDSERVER = "_preventaCHANGEIdServer";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_PRODUCTOCHANGEID = "_productoCHANGEId";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_CANTIDAD = "_cantidad";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_CLIENTEID = "_clienteId";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_SYNCRO = "_syncro";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_OBSERVACION = "_observacion";
    public static final String KEY_PREVENTAPRODUCTOCHANGE_MOTIVOCHANGEID = "_motivoCHANGEId";
    
    public static final int COL_PREVENTAPRODUCTOCHANGE_ROW_ID = 0;
    public static final int COL_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEID = 1;
    public static final int COL_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEIDSERVER = 2;
    public static final int COL_PREVENTAPRODUCTOCHANGE_PRODUCTOCHANGEID = 3;
    public static final int COL_PREVENTAPRODUCTOCHANGE_CANTIDAD = 4;
    public static final int COL_PREVENTAPRODUCTOCHANGE_CLIENTEID = 5;
    public static final int COL_PREVENTAPRODUCTOCHANGE_SYNCRO = 6;
    public static final int COL_PREVENTAPRODUCTOCHANGE_OBSERVACION = 7;
    public static final int COL_PREVENTAPRODUCTOCHANGE_MOTIVOCHANGEID = 8;
    
    public static final String[] PREVENTAPRODUCTOCHANGE_ALL_KEYS = new String[] { 
    		KEY_PREVENTAPRODUCTOCHANGE_ROW_ID,KEY_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEID,KEY_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEIDSERVER,
    		KEY_PREVENTAPRODUCTOCHANGE_PRODUCTOCHANGEID,KEY_PREVENTAPRODUCTOCHANGE_CANTIDAD,KEY_PREVENTAPRODUCTOCHANGE_CLIENTEID,
    		KEY_PREVENTAPRODUCTOCHANGE_SYNCRO,KEY_PREVENTAPRODUCTOCHANGE_OBSERVACION,KEY_PREVENTAPRODUCTOCHANGE_MOTIVOCHANGEID};
    
    public static final String PREVENTAPRODUCTOCHANGE_TABLE_NAME = "tbl_PreventaProductoCHANGE";
    
    public static final String PREVENTAPRODUCTOCHANGE_TABLE_CREATE = "CREATE TABLE " + PREVENTAPRODUCTOCHANGE_TABLE_NAME + "("
    		+ KEY_PREVENTAPRODUCTOCHANGE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_PREVENTACHANGEIDSERVER + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_PRODUCTOCHANGEID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_CANTIDAD + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_CLIENTEID + " integer NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_SYNCRO + " boolean NOT NULL, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_OBSERVACION + " text, "
    		+ KEY_PREVENTAPRODUCTOCHANGE_MOTIVOCHANGEID + " integer NOT NULL);";

    public boolean borrarPreventaProductoCHANGEPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PREVENTAPRODUCTOCHANGE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarPreventasProductoCHANGE()
    {
      Cursor localCursor = obtenerPreventasProductoCHANGE();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarPreventaProductoCHANGEPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarPreventaProductoCHANGE(int preventaCHANGEId,int preventaCHANGEIdServer,int productoCHANGEId,int cantidad,int clienteId,boolean syncro,
    										String observacion,int motivoCHANGEId)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_preventaCHANGEId", preventaCHANGEId);
      localContentValues.put("_preventaCHANGEIdServer", preventaCHANGEIdServer);
      localContentValues.put("_productoCHANGEId", productoCHANGEId);
      localContentValues.put("_cantidad", cantidad);
      localContentValues.put("_clienteId", clienteId);
      localContentValues.put("_syncro", syncro);
      localContentValues.put("_observacion", String.valueOf(observacion));
      localContentValues.put("_motivoCHANGEId", motivoCHANGEId);
      return this.db.insert(PREVENTAPRODUCTOCHANGE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerPreventaProductoCHANGEPor(int id)
    {
      String str = "_id=" + id;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCHANGE_TABLE_NAME,PREVENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerPreventasProductoCHANGE()
    {
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCHANGE_TABLE_NAME,PREVENTAPRODUCTOCHANGE_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCHANGEPorClienteId(int clienteId)
    {
      String str = "_clienteId=" + clienteId;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCHANGE_TABLE_NAME,PREVENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCHANGEPorPreventaCHANGEId(int preventaCHANGEId)
    {
      String str = "_preventaCHANGEId=" + preventaCHANGEId;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCHANGE_TABLE_NAME,PREVENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCHANGEPorPreventaCHANGEIdServer(int preventaCHANGEIdServer)
    {
      String str = "_preventaCHANGEIdServer=" + preventaCHANGEIdServer;
      Cursor localCursor = this.db.query(true,PREVENTAPRODUCTOCHANGE_TABLE_NAME,PREVENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCHANGENoSincronizadasPor(int preventaCHANGEId)
    {
      String str = "_preventaCHANGEId=" + preventaCHANGEId + " and _syncro = 0";
      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTOCHANGE_TABLE_NAME, PREVENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerPreventasProductoCHANGENoSincronizadas()
    {
      String str = "_syncro = 0";
      Cursor localCursor = this.db.query(true, PREVENTAPRODUCTOCHANGE_TABLE_NAME, PREVENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public int modificarPreventaProductoCHANGENoSincronizado(int Id, int preventaCHANGEIdServer)
    {
      String str = "_Id=" + Id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_syncro", Boolean.TRUE);
      localContentValues.put("_preventaCHANGEIdServer", preventaCHANGEIdServer);
      return this.db.update(PREVENTAPRODUCTOCHANGE_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarPreventaProductosCHANGENoSincronizado(int preventaCHANGEId, int preventaCHANGEIdServer)
    {
      String str = "_preventaCHANGEId=" + preventaCHANGEId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_preventaCHANGEIdServer", preventaCHANGEIdServer);
      return this.db.update(PREVENTAPRODUCTOCHANGE_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarPreventaProductosCHANGEPor(int id,String observacion)
    {
      String str = "_id=" + id;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_observacion", String.valueOf(observacion));
      return this.db.update(PREVENTAPRODUCTOCHANGE_TABLE_NAME, localContentValues, str, null);
    }
    
    //PRODUCTOCHANGECOSTO//
    public static final String KEY_PRODUCTOCHANGECOSTO_ROW_ID = "_id";
    public static final String KEY_PRODUCTOCHANGECOSTO_COSTOID = "_costoId";
    public static final String KEY_PRODUCTOCHANGECOSTO_PRODUCTOID = "_productoId";
    public static final String KEY_PRODUCTOCHANGECOSTO_COSTO = "_costo";
        
    public static final int COL_PRODUCTOCHANGECOSTO_ROW_ID = 0;
    public static final int COL_PRODUCTOCHANGECOSTO_COSTOID = 1;
    public static final int COL_PRODUCTOCHANGECOSTO_PRODUCTOID = 2;
    public static final int COL_PRODUCTOCHANGECOSTO_COSTO = 3;
    
    public static final String[] PRODUCTOCHANGECOSTO_ALL_KEYS = new String[] {
    		KEY_PRODUCTOCHANGECOSTO_ROW_ID,KEY_PRODUCTOCHANGECOSTO_COSTOID,KEY_PRODUCTOCHANGECOSTO_PRODUCTOID,
    		KEY_PRODUCTOCHANGECOSTO_COSTO};
    
    public static final String PRODUCTOCHANGECOSTO_TABLE_NAME = "tbl_ProductoCHANGECosto";
    
    public static final String PRODUCTOCHANGECOSTO_TABLE_CREATE = "CREATE TABLE " + PRODUCTOCHANGECOSTO_TABLE_NAME + "("
    		+ KEY_PRODUCTOCHANGECOSTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRODUCTOCHANGECOSTO_COSTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCHANGECOSTO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_PRODUCTOCHANGECOSTO_COSTO + " float NOT NULL);";

    public boolean borrarProductoCHANGECostoPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PRODUCTOCHANGECOSTO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProductosCHANGECosto()
    {
      Cursor localCursor = obtenerProductosCHANGECosto();
      long l = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarProductoCHANGECostoPor(localCursor.getLong((int)l));
        } while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarProductoCHANGECosto(int costoId,int productoId,float costo)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_costoId", costoId);
      localContentValues.put("_productoId", productoId);
      localContentValues.put("_costo", costo);
      return this.db.insert(PRODUCTOCHANGECOSTO_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerProductoCHANGECostoPor(int productoId)
    {
      String str = "_productoId=" + productoId;
      Cursor localCursor = this.db.query(true,PRODUCTOCHANGECOSTO_TABLE_NAME,PRODUCTOCHANGECOSTO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }

    public Cursor obtenerProductosCHANGECosto()
    {
      Cursor localCursor = this.db.query(true,PRODUCTOCHANGECOSTO_TABLE_NAME,PRODUCTOCHANGECOSTO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null)
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //MOTIVOCHANGE//
    public static final String KEY_MOTIVOCHANGE_ROW_ID = "_id";
    public static final String KEY_MOTIVOCHANGE_MOTIVOCHANGEID = "_motivoCHANGEId";
    public static final String KEY_MOTIVOCHANGE_DESCRIPCION = "_descripcion";
    
    public static final int COL_MOTIVOCHANGE_ROW_ID = 0;
    public static final int COL_MOTIVOCHANGE_MOTIVOCHANGEID = 1;
    public static final int COL_MOTIVOCHANGE_DESCRIPCION = 2;
    
    public static final String[] MOTIVOCHANGE_ALL_KEYS = new String[] {
    		KEY_MOTIVOCHANGE_ROW_ID,KEY_MOTIVOCHANGE_MOTIVOCHANGEID,KEY_MOTIVOCHANGE_DESCRIPCION};
    
    public static final String MOTIVOCHANGE_TABLE_NAME = "tbl_MotivoCHANGE";
    
    public static final String MOTIVOCHANGE_TABLE_CREATE = "CREATE TABLE " + MOTIVOCHANGE_TABLE_NAME + " ("
    		+ KEY_MOTIVOCHANGE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_MOTIVOCHANGE_MOTIVOCHANGEID + " integer NOT NULL, "
    		+ KEY_MOTIVOCHANGE_DESCRIPCION + " text NOT NULL);";
    
    public boolean borrarMotivoCHANGEPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(MOTIVOCHANGE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarMotivosCHANGE()
    {
    	Cursor localCursor = obtenerMotivosCHANGE();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarMotivoCHANGEPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarMotivoCHANGE(int motivoCHANGEId,String descripcion)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_motivoCHANGEId", motivoCHANGEId);
    	localContentValues.put("_descripcion", String.valueOf(descripcion));
    	return this.db.insert(MOTIVOCHANGE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerMotivoCHANGEPor(int motivoCHANGEId)
    {
    	String str = "_motivoCHANGEId = " + motivoCHANGEId;
    	Cursor localCursor = this.db.query(true,MOTIVOCHANGE_TABLE_NAME,MOTIVOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerMotivosCHANGE()
    {
    	Cursor localCursor = this.db.query(true,MOTIVOCHANGE_TABLE_NAME,MOTIVOCHANGE_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //VENTAPRODUCTOCHANGE//
    public static final String KEY_VENTAPRODUCTOCHANGE_ROW_ID = "_id";
    public static final String KEY_VENTAPRODUCTOCHANGE_VENTACHANGEID = "_ventaCHANGEId";
    public static final String KEY_VENTAPRODUCTOCHANGE_VENTACHANGEIDSERVER = "_ventaCHANGEIdServer";
    public static final String KEY_VENTAPRODUCTOCHANGE_PRODUCTOCHANGEID = "_productoCHANGEId";
    public static final String KEY_VENTAPRODUCTOCHANGE_CANTIDAD = "_cantidad";
    public static final String KEY_VENTAPRODUCTOCHANGE_CLIENTEID = "_clienteId";
    public static final String KEY_VENTAPRODUCTOCHANGE_SYNCRO = "_syncro";
    public static final String KEY_VENTAPRODUCTOCHANGE_OBSERVACION = "_observacion";
    public static final String KEY_VENTAPRODUCTOCHANGE_MOTIVOCHANGEID = "_motivoCHANGEId";
    
    public static final int COL_VENTAPRODUCTOCHANGE_ROW_ID = 0;
    public static final int COL_VENTAPRODUCTOCHANGE_VENTACHANGEID = 1;
    public static final int COL_VENTAPRODUCTOCHANGE_VENTACHANGEIDSERVER = 2;
    public static final int COL_VENTAPRODUCTOCHANGE_PRODUCTOCHANGEID = 3;
    public static final int COL_VENTAPRODUCTOCHANGE_CANTIDAD = 4;
    public static final int COL_VENTAPRODUCTOCHANGE_CLIENTEID = 5;
    public static final int COL_VENTAPRODUCTOCHANGE_SYNCRO = 6;
    public static final int COL_VENTAPRODUCTOCHANGE_OBSERVACION = 7;
    public static final int COL_VENTAPRODUCTOCHANGE_MOTIVOCHANGEID = 8;
    
    public static final String[] VENTAPRODUCTOCHANGE_ALL_KEYS = new String[] { 
    		KEY_VENTAPRODUCTOCHANGE_ROW_ID,KEY_VENTAPRODUCTOCHANGE_VENTACHANGEID,KEY_VENTAPRODUCTOCHANGE_VENTACHANGEIDSERVER,
    		KEY_VENTAPRODUCTOCHANGE_PRODUCTOCHANGEID,KEY_VENTAPRODUCTOCHANGE_CANTIDAD,KEY_VENTAPRODUCTOCHANGE_CLIENTEID,
    		KEY_VENTAPRODUCTOCHANGE_SYNCRO,KEY_VENTAPRODUCTOCHANGE_OBSERVACION,KEY_VENTAPRODUCTOCHANGE_MOTIVOCHANGEID};
    
    public static final String VENTAPRODUCTOCHANGE_TABLE_NAME = "tbl_VentaProductoCHANGE";
    
    public static final String VENTAPRODUCTOCHANGE_TABLE_CREATE = "CREATE TABLE " + VENTAPRODUCTOCHANGE_TABLE_NAME + "("
    		+ KEY_VENTAPRODUCTOCHANGE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_VENTAPRODUCTOCHANGE_VENTACHANGEID + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOCHANGE_VENTACHANGEIDSERVER + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOCHANGE_PRODUCTOCHANGEID + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOCHANGE_CANTIDAD + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOCHANGE_CLIENTEID + " integer NOT NULL, "
    		+ KEY_VENTAPRODUCTOCHANGE_SYNCRO + " boolean NOT NULL, "
    		+ KEY_VENTAPRODUCTOCHANGE_OBSERVACION + " text, "
    		+ KEY_VENTAPRODUCTOCHANGE_MOTIVOCHANGEID + " integer NOT NULL);";

    public boolean borrarVentaProductoCHANGEPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(VENTAPRODUCTOCHANGE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarVentasProductoCHANGE()
    {
    	Cursor localCursor = obtenerVentasProductoCHANGE();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarVentaProductoCHANGEPor(localCursor.getLong((int)l));
    		} while (localCursor.moveToNext());
    	}
    	localCursor.close();
	}
    
    public long insertarVentaProductoCHANGE(int ventaCHANGEId,int ventaCHANGEIdServer,int productoCHANGEId,int cantidad,int clienteId,boolean syncro,
    										String observacion,int motivoCHANGEId)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_ventaCHANGEId", ventaCHANGEId);
    	localContentValues.put("_ventaCHANGEIdServer", ventaCHANGEIdServer);
    	localContentValues.put("_productoCHANGEId", productoCHANGEId);
    	localContentValues.put("_cantidad", cantidad);
    	localContentValues.put("_clienteId", clienteId);
    	localContentValues.put("_syncro", syncro);
    	localContentValues.put("_observacion", String.valueOf(observacion));
    	localContentValues.put("_motivoCHANGEId", motivoCHANGEId);
    	return this.db.insert(VENTAPRODUCTOCHANGE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerVentaProductoCHANGEPor(int id)
    {
    	String str = "_id=" + id;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }

    public Cursor obtenerVentasProductoCHANGE()
    {
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoCHANGEPorClienteId(int clienteId)
    {
    	String str = "_clienteId=" + clienteId;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoCHANGEPorVentaCHANGEId(int ventaCHANGEId)
    {
    	String str = "_ventaCHANGEId=" + ventaCHANGEId;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoCHANGEPorVentaCHANGEIdServer(int ventaCHANGEIdServer)
    {
    	String str = "_ventaCHANGEIdServer=" + ventaCHANGEIdServer;
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoCHANGENoSincronizadasPor(int ventaCHANGEId)
    {
    	String str = "_ventaCHANGEId=" + ventaCHANGEId + " and _syncro = 0";
    	Cursor localCursor = this.db.query(true, VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerVentasProductoCHANGENoSincronizadas()
    {
    	String str = "_syncro = 0";
    	Cursor localCursor = this.db.query(true,VENTAPRODUCTOCHANGE_TABLE_NAME,VENTAPRODUCTOCHANGE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public int modificarVentaProductoCHANGENoSincronizado(int Id, int ventaCHANGEIdServer)
    {
    	String str = "_Id=" + Id;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_syncro", Boolean.TRUE);
    	localContentValues.put("_ventaCHANGEIdServer", ventaCHANGEIdServer);
    	return this.db.update(VENTAPRODUCTOCHANGE_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarVentaProductosCHANGENoSincronizado(int ventaCHANGEId, int ventaCHANGEIdServer)
    {
    	String str = "_ventaCHANGEId=" + ventaCHANGEId;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_ventaCHANGEIdServer", ventaCHANGEIdServer);
    	return this.db.update(VENTAPRODUCTOCHANGE_TABLE_NAME, localContentValues, str, null);
    }
    
    public int modificarVentaProductosCHANGEPor(int id,String observacion)
    {
    	String str = "_id=" + id;
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_observacion", String.valueOf(observacion));
    	return this.db.update(VENTAPRODUCTOCHANGE_TABLE_NAME, localContentValues, str, null);
    }
    
    //PROMOCIONTIPONEGOCIO//
    public static final String KEY_PROMOCIONTIPONEGOCIO_ROW_ID = "_id";
    public static final String KEY_PROMOCIONTIPONEGOCIO_REGISTROID = "_registroId";
    public static final String KEY_PROMOCIONTIPONEGOCIO_PROMOCIONID = "_promocionId";
    public static final String KEY_PROMOCIONTIPONEGOCIO_TIPONEGOCIOID = "_tipoNegocioId";
    
    public static final int COL_PROMOCIONTIPONEGOCIO_ROW_ID = 0;
    public static final int COL_PROMOCIONTIPONEGOCIO_REGISTROID = 1;
    public static final int COL_PROMOCIONTIPONEGOCIO_PROMOCIONID = 2;
    public static final int COL_PROMOCIONTIPONEGOCIO_TIPONEGOCIOID = 3;
    
    public static final String[] PROMOCIONTIPONEGOCIO_ALL_KEYS = new String[] {
    		KEY_PROMOCIONTIPONEGOCIO_ROW_ID,KEY_PROMOCIONTIPONEGOCIO_REGISTROID,KEY_PROMOCIONTIPONEGOCIO_PROMOCIONID,
    		KEY_PROMOCIONTIPONEGOCIO_TIPONEGOCIOID};
    
    public static final String PROMOCIONTIPONEGOCIO_TABLE_NAME = "tbl_PromocionTipoNegocio";
    
    public static final String PROMOCIONTIPONEGOCIO_TABLE_CREATE = "CREATE TABLE " + PROMOCIONTIPONEGOCIO_TABLE_NAME + " ("
    		+ KEY_PROMOCIONTIPONEGOCIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_PROMOCIONTIPONEGOCIO_REGISTROID + " integer NOT NULL, "
    		+ KEY_PROMOCIONTIPONEGOCIO_PROMOCIONID + " integer NOT NULL, "
    		+ KEY_PROMOCIONTIPONEGOCIO_TIPONEGOCIOID + " integer NOT NULL);";
    
    public boolean borrarPromocionTipoNegocioPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(PROMOCIONTIPONEGOCIO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarPromocionesTipoNegocio()
    {
    	Cursor localCursor = obtenerPromocionesTipoNegocio();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarPromocionTipoNegocioPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarPromocionTipoNegocio(ArrayList<PromocionTipoNegocioWSResult> promocionesTipoNegocio)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_PromocionTipoNegocio(_registroId, _promocionId, _tipoNegocioId) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (PromocionTipoNegocioWSResult item : promocionesTipoNegocio) {

				stmt.bindLong(1, item.getRegistroId());
				stmt.bindLong(2, item.getPromocionId());
				stmt.bindLong(3, item.getTipoNegocioId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerPromocionTipoNegocioPor(int promocionId)
    {
    	String str = "_promocionId = " + promocionId;
    	Cursor localCursor = this.db.query(true,PROMOCIONTIPONEGOCIO_TABLE_NAME,PROMOCIONTIPONEGOCIO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerPromocionesTipoNegocio()
    {
    	Cursor localCursor = this.db.query(true,PROMOCIONTIPONEGOCIO_TABLE_NAME,PROMOCIONTIPONEGOCIO_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //ZONAVENTA//
    public static final String KEY_ZONAVENTA_ROW_ID = "_Id";
    public static final String KEY_ZONAVENTA_ZONAVENTAID = "_zonaVentaId";
    public static final String KEY_ZONAVENTA_NOMBRE = "_nombre";

    public static final int COL_ZONAVENTA_ROW_ID = 0;
    public static final int COL_ZONAVENTA_ZONAVENTAID = 1;
    public static final int COL_ZONAVENTA_NOMBRE = 2;
    
    public static final String[] ZONAVENTA_ALL_KEYS= new String[] { 
    	KEY_ZONAVENTA_ROW_ID, KEY_ZONAVENTA_ZONAVENTAID, KEY_ZONAVENTA_NOMBRE 
    	};
    public static final String ZONAVENTA_TABLE_NAME = "tbl_ZonaVenta";
    
    public static final String ZONAVENTA_TABLE_CREATE = "CREATE TABLE " + ZONAVENTA_TABLE_NAME + "("
    		+ KEY_ZONAVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ZONAVENTA_ZONAVENTAID + " integer NOT NULL, "
    		+ KEY_ZONAVENTA_NOMBRE + " text NOT NULL);";

    public boolean borrarZonaVentaPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(ZONAVENTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarZonasVenta()
    {
      Cursor localCursor = obtenerZonasVenta();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarZonaVentaPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarZonaVenta(ArrayList<ZonaVentaWSResult> zonasVenta)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ZonaVenta(_zonaVentaId, _nombre) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (ZonaVentaWSResult item : zonasVenta) {

				stmt.bindLong(1, item.getZonaId());
				stmt.bindString(2, item.getNombre());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerZonaVentaPor(int zonaVentaId)
    {
      String str = "_zonaVentaId=" + zonaVentaId;
      Cursor localCursor = this.db.query(true,ZONA_TABLE_NAME, ZONA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerZonasVenta()
    {
      Cursor localCursor = this.db.query(true,ZONA_TABLE_NAME, ZONA_ALL_KEYS, null, null, null, null,KEY_ZONA_NOMBRE, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //ZONAMERCADO//
    public static final String KEY_ZONAMERCADO_ROW_ID = "_Id";
    public static final String KEY_ZONAMERCADO_ZONAMERCADOID = "_zonaMercadoId";
    public static final String KEY_ZONAMERCADO_NOMBRE = "_nombre";

    public static final int COL_ZONMERCADO_ROW_ID = 0;
    public static final int COL_ZONAMERCADO_ZONAMERCADOID = 1;
    public static final int COL_ZONAMERCADO_NOMBRE = 2;
    
    public static final String[] ZONAMERCADO_ALL_KEYS= new String[] { 
    	KEY_ZONAMERCADO_ROW_ID, KEY_ZONAMERCADO_ZONAMERCADOID, KEY_ZONAMERCADO_NOMBRE 
    	};
    public static final String ZONAMERCADO_TABLE_NAME = "tbl_ZonaMercado";
    
    public static final String ZONAMERCADO_TABLE_CREATE = "CREATE TABLE " + ZONAMERCADO_TABLE_NAME + "("
    		+ KEY_ZONAMERCADO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ZONAMERCADO_ZONAMERCADOID + " integer NOT NULL, "
    		+ KEY_ZONAMERCADO_NOMBRE + " text NOT NULL);";

    public boolean borrarZonaMercadoPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(ZONAMERCADO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarZonasMercado()
    {
      Cursor localCursor = obtenerZonasMercado();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarZonaMercadoPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarZonaMercado(ArrayList<ZonaMercadoWSResult> zonasMercado)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ZonaMercado(_zonaMercadoId, _nombre) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (ZonaMercadoWSResult item : zonasMercado) {

				stmt.bindLong(1, item.getZonaMercadoId());
				stmt.bindString(2, item.getNombre());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerZonaMercadoPor(int zonaMercadoId)
    {
      String str = "_zonaMercadoId=" + zonaMercadoId;
      Cursor localCursor = this.db.query(true,ZONAMERCADO_TABLE_NAME, ZONAMERCADO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerZonasMercado()
    {
      Cursor localCursor = this.db.query(true,ZONAMERCADO_TABLE_NAME, ZONAMERCADO_ALL_KEYS, null, null, null, null,KEY_ZONAMERCADO_NOMBRE, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
  //CLIENTECOBRANZA//
    public static final String KEY_CLIENTECOBRANZA_ROW_ID = "_id";
    public static final String KEY_CLIENTECOBRANZA_CLIENTEID = "_clienteId";
    public static final String KEY_CLIENTECOBRANZA_NOMBRE = "_nombre";
    public static final String KEY_CLIENTECOBRANZA_FECHA = "_fecha";
    public static final String KEY_CLIENTECOBRANZA_MONTO = "_monto";
    public static final String KEY_CLIENTECOBRANZA_SALDO = "_saldo";
    public static final String KEY_CLIENTECOBRANZA_NROFACTURA = "_nroFactura";
    public static final String KEY_CLIENTECOBRANZA_VENTAID = "_ventaId";
    public static final String KEY_CLIENTECOBRANZA_LATITUD = "_latitud";
    public static final String KEY_CLIENTECOBRANZA_LONGITUD = "_longitud";
    public static final String KEY_CLIENTECOBRANZA_SERVERID = "_serverId";
    
    public static final int COL_CLIENTECOBRANZA_ROW_ID = 0;
    public static final int COL_CLIENTECOBRANZA_CLIENTEID = 1;
    public static final int COL_CLIENTECOBRANZA_NOMBRE = 2;
    public static final int COL_CLIENTECOBRANZA_FECHA = 3;
    public static final int COL_CLIENTECOBRANZA_MONTO = 4;
    public static final int COL_CLIENTECOBRANZA_SALDO = 5;
    public static final int COL_CLIENTECOBRANZA_NROFACTURA = 6;
    public static final int COL_CLIENTECOBRANZA_VENTAID = 7;
    public static final int COL_CLIENTECOBRANZA_LATITUD = 8;
    public static final int COL_CLIENTECOBRANZA_LONGITUD = 9;
    public static final int COL_CLIENTECOBRANZA_SERVERID = 10;
    
    public static final String[] CLIENTECOBRANZA_ALL_KEYS = new String[] {
    		KEY_CLIENTECOBRANZA_ROW_ID,KEY_CLIENTECOBRANZA_CLIENTEID,KEY_CLIENTECOBRANZA_NOMBRE,
    		KEY_CLIENTECOBRANZA_FECHA,KEY_CLIENTECOBRANZA_MONTO,KEY_CLIENTECOBRANZA_SALDO,
    		KEY_CLIENTECOBRANZA_NROFACTURA,KEY_CLIENTECOBRANZA_VENTAID,KEY_CLIENTECOBRANZA_LATITUD,
    		KEY_CLIENTECOBRANZA_LONGITUD,KEY_CLIENTECOBRANZA_SERVERID};
    
    public static final String CLIENTECOBRANZA_TABLE_NAME = "tbl_ClienteCobranza";
    
    public static final String CLIENTECOBRANZA_TABLE_CREATE = "CREATE TABLE " + CLIENTECOBRANZA_TABLE_NAME + " ("
    		+ KEY_CLIENTECOBRANZA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT,"
    		+ KEY_CLIENTECOBRANZA_CLIENTEID + " int NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_NOMBRE + " text NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_FECHA + " text NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_MONTO + " float NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_SALDO + " float NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_NROFACTURA + " text NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_VENTAID + " integer NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_LATITUD + " double NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_LONGITUD + " double NOT NULL, "
    		+ KEY_CLIENTECOBRANZA_SERVERID + " integer NOT NULL);";
    
    public boolean borrarClienteCobranzaPor(long id)
    {
      String str = "_id=" + id;
      return this.db.delete(CLIENTECOBRANZA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarClientesCobranza()
    {
    	Cursor localCursor = obtenerClientesCobranza();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarClienteCobranzaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarClienteCobranza(SoapObject clienteCobranza)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClienteCobranza(_clienteId, _nombre, _fecha, _monto, _saldo, _nroFactura, _ventaId, _latitud, _longitud, _serverId) " +
						"VALUES (?,?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (int i = 0; i < clienteCobranza.getPropertyCount(); i++) {
				SoapObject soapObject = (SoapObject) clienteCobranza.getProperty(i);

				stmt.bindLong(1,Integer.parseInt(soapObject.getPropertyAsString("ClienteId")));
				stmt.bindString(2,soapObject.getPropertyAsString("Nombre").equals("anyType{}")?"":soapObject.getPropertyAsString("Nombre"));
				stmt.bindString(3,soapObject.getPropertyAsString("Fecha").equals("anyType{}")?"":soapObject.getPropertyAsString("Fecha"));
				stmt.bindDouble(4,Float.parseFloat(soapObject.getPropertyAsString("Monto")));
				stmt.bindDouble(5,Float.parseFloat(soapObject.getPropertyAsString("Saldo")));
				stmt.bindString(6,soapObject.getPropertyAsString("NroFactura").equals("anyType{}")?"":soapObject.getPropertyAsString("NroFactura"));
				stmt.bindLong(7,Integer.parseInt(soapObject.getPropertyAsString("VentaId")));
				stmt.bindDouble(8,Double.parseDouble(soapObject.getPropertyAsString("Latitud")));
				stmt.bindDouble(9,Double.parseDouble(soapObject.getPropertyAsString("Longitud")));
				stmt.bindLong(10,0);

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerClientesCobranzaPor(int clienteId)
    {
    	String str = "_clienteId = " + clienteId;
    	Cursor localCursor = this.db.query(true,CLIENTECOBRANZA_TABLE_NAME,CLIENTECOBRANZA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerClientesCobranza()
    {
    	Cursor localCursor = this.db.query(true,CLIENTECOBRANZA_TABLE_NAME,CLIENTECOBRANZA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public int ModificarClienteCobranzaServerId(int rowId,int serverId,float saldo)
    {
      String str = "_Id=" + rowId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_serverId", serverId);
      localContentValues.put("_saldo", saldo);
      return this.db.update(CLIENTECOBRANZA_TABLE_NAME, localContentValues, str, null);
    }
    
    //METASAP//
    public static final String KEY_METASAP_ROW_ID = "_Id";
    public static final String KEY_METASAP_CATEGORIAVENDEDOR = "_categoriaVendedor";
    public static final String KEY_METASAP_NIVELVENDEDOR = "_nivelVendedor";
    public static final String KEY_METASAP_TIPO = "_tipo";
    public static final String KEY_METASAP_NIVEL = "_nivel";
    public static final String KEY_METASAP_OBJETO = "_objeto";
    public static final String KEY_METASAP_PORCENTAJE = "_porcentaje";
    public static final String KEY_METASAP_MONTO = "_monto";
    public static final String KEY_METASAP_COBERTURA = "_cobertura";
    public static final String KEY_METASAP_MONTOVENTA = "_montoVenta";
    public static final String KEY_METASAP_COBERTURAVENTA = "_coberturaVenta";
    public static final String KEY_METASAP_AVANCEMONTO = "_avanceMonto";
    public static final String KEY_METASAP_AVANCECOBERTURA = "_avanceCobertura";

    public static final int COL_METASAP_ROW_ID = 0;
    public static final int COL_METASAP_CATEGORIAVENDEDOR = 1;
    public static final int COL_METASAP_NIVELVENDEDOR = 2;
    public static final int COL_METASAP_TIPO = 3;
    public static final int COL_METASAP_NIVEL = 4;
    public static final int COL_METASAP_OBJETO = 5;
    public static final int COL_METASAP_PORCENTAJE = 6;
    public static final int COL_METASAP_MONTO = 7;
    public static final int COL_METASAP_COBERTURA = 8;
    public static final int COL_METASAP_MONTOVENTA = 9;
    public static final int COL_METASAP_COBERTURAVENTA = 10;
    public static final int COL_METASAP_AVANCEMONTO = 11;
    public static final int COL_METASAP_AVANCECOBERTURA = 12;
    
    public static final String[] METASAP_ALL_KEYS = new String[] { 
    		KEY_METASAP_ROW_ID, KEY_METASAP_CATEGORIAVENDEDOR, KEY_METASAP_NIVELVENDEDOR,
    		KEY_METASAP_TIPO,KEY_METASAP_NIVEL,KEY_METASAP_OBJETO,
    		KEY_METASAP_PORCENTAJE,KEY_METASAP_MONTO,KEY_METASAP_COBERTURA,
    		KEY_METASAP_MONTOVENTA,KEY_METASAP_COBERTURAVENTA,KEY_METASAP_AVANCEMONTO,
    		KEY_METASAP_AVANCECOBERTURA
    	};
    public static final String METASAP_TABLE_NAME = "tbl_MetaSap";
    
    public static final String METASAP_TABLE_CREATE = "CREATE TABLE " + METASAP_TABLE_NAME + "("
    		+ KEY_METASAP_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_METASAP_CATEGORIAVENDEDOR + " text NOT NULL, "
    		+ KEY_METASAP_NIVELVENDEDOR + " text NOT NULL, "
    		+ KEY_METASAP_TIPO + " text NOT NULL, "
    		+ KEY_METASAP_NIVEL + " text NOT NULL, "
    		+ KEY_METASAP_OBJETO + " text NOT NULL, "
    		+ KEY_METASAP_PORCENTAJE + " float NOT NULL, "
    		+ KEY_METASAP_MONTO + " float NOT NULL, "
    		+ KEY_METASAP_COBERTURA + " integer NOT NULL, "
    		+ KEY_METASAP_MONTOVENTA + " float NOT NULL, "
    		+ KEY_METASAP_COBERTURAVENTA + " float NOT NULL, "
    		+ KEY_METASAP_AVANCEMONTO + " float NOT NULL, "
    		+ KEY_METASAP_AVANCECOBERTURA + " float NOT NULL);";

    public boolean borrarMetaSapPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(METASAP_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarMetasSap()
    {
      Cursor localCursor = obtenerMetasSap();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarMetaSapPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarMetaSap(String categoriaVendedor,String nivelVendedor,String tipo,String nivel,String objeto,float porcentaje,float monto,
    								int cobertura,float montoVenta,int coberturaVenta,float avanceMonto,float avanceCobertura)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_categoriaVendedor", categoriaVendedor);
      localContentValues.put("_nivelVendedor", nivelVendedor);
      localContentValues.put("_tipo", tipo);
      localContentValues.put("_nivel", nivel);
      localContentValues.put("_objeto", objeto);
      localContentValues.put("_porcentaje", porcentaje);
      localContentValues.put("_monto", monto);
      localContentValues.put("_cobertura", cobertura);
      localContentValues.put("_montoVenta", montoVenta);
      localContentValues.put("_coberturaVenta", coberturaVenta);
      localContentValues.put("_avanceMonto", avanceMonto);
      localContentValues.put("_avanceCobertura", avanceCobertura);
      return this.db.insert(METASAP_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerMetaSapPor(String tipo)
    {
      String str = "_tipo=" + tipo;
      Cursor localCursor = this.db.query(true,METASAP_TABLE_NAME, METASAP_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerMetasSap()
    {
      Cursor localCursor = this.db.query(true,METASAP_TABLE_NAME, METASAP_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //CLIENTENROFOTO//
    public static final String KEY_CLIENTENROFOTO_ROW_ID = "_Id";
    public static final String KEY_CLIENTENROFOTO_CLIENTEID = "_clienteId";
    public static final String KEY_CLIENTENROFOTO_NUMERO = "_numero";
    
    public static final int COL_CLIENTENROFOTO_ROW_ID = 0;
    public static final int COL_CLIENTENROFOTO_CLIENTEID = 1;
    public static final int COL_CLIENTENROFOTO_NUMERO = 2;
    
    public static final String[] CLIENTENROFOTO_ALL_KEYS = new String[] { 
    		KEY_CLIENTENROFOTO_ROW_ID, KEY_CLIENTENROFOTO_CLIENTEID, KEY_CLIENTENROFOTO_NUMERO
    	};
    public static final String CLIENTENROFOTO_TABLE_NAME = "tbl_ClienteNroFoto";
    
    public static final String CLIENTENROFOTO_TABLE_CREATE = "CREATE TABLE " + CLIENTENROFOTO_TABLE_NAME + "("
    		+ KEY_CLIENTENROFOTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CLIENTENROFOTO_CLIENTEID + " integer NOT NULL, "
    		+ KEY_CLIENTENROFOTO_NUMERO + " integer NOT NULL);";

    public boolean borrarClientesNroFotoPor(long id)
    {
    	String str = "_Id=" + id;
    	return this.db.delete(CLIENTENROFOTO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarClientesNroFoto()
    {
    	Cursor localCursor = obtenerClientesNroFoto();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarClientesNroFotoPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarClienteNroFoto(ArrayList<ClienteNroFotoWSResult> clientesNroFoto)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClienteNroFoto(_clienteId, _numero) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (ClienteNroFotoWSResult item : clientesNroFoto) {

				stmt.bindLong(1, item.getClienteId());
				stmt.bindLong(2, item.getNumero());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerClienteNroFotoPor(int clienteId)
    {
    	String str = "_clienteId=" + clienteId;
    	Cursor localCursor = this.db.query(true,CLIENTENROFOTO_TABLE_NAME,CLIENTENROFOTO_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerClientesNroFoto()
    {
    	Cursor localCursor = this.db.query(true,CLIENTENROFOTO_TABLE_NAME,CLIENTENROFOTO_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //FOTOCATEGORIA//
    public static final String KEY_FOTOCATEGORIA_ROW_ID = "_Id";
    public static final String KEY_FOTOCATEGORIA_CATEGORIAID = "_categoriaId";
    public static final String KEY_FOTOCATEGORIA_DESCRIPCION = "_descripcion";
    
    public static final int COL_FOTOCATEGORIA_ROW_ID = 0;
    public static final int COL_FOTOCATEGORIA_CATEGORIAID = 1;
    public static final int COL_FOTOCATEGORIA_DESCRIPCION = 2;
    
    public static final String[] FOTOCATEGORIA_ALL_KEYS = new String[] { 
    		KEY_FOTOCATEGORIA_ROW_ID, KEY_FOTOCATEGORIA_CATEGORIAID, KEY_FOTOCATEGORIA_DESCRIPCION
    	};
    public static final String FOTOCATEGORIA_TABLE_NAME = "tbl_FotoCategoria";
    
    public static final String FOTOCATEGORIA_TABLE_CREATE = "CREATE TABLE " + FOTOCATEGORIA_TABLE_NAME + "("
    		+ KEY_FOTOCATEGORIA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_FOTOCATEGORIA_CATEGORIAID + " integer NOT NULL, "
    		+ KEY_FOTOCATEGORIA_DESCRIPCION + " text NOT NULL);";

    public boolean borrarFotoCategoriaPor(long id)
    {
    	String str = "_Id=" + id;
    	return this.db.delete(FOTOCATEGORIA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarFotosCategoria()
    {
    	Cursor localCursor = obtenerFotosCategoria();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarFotoCategoriaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarFotoCategoria(ArrayList<FotoCategoriaWSResult> fotosCategoria)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_FotoCategoria(_categoriaId, _descripcion) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (FotoCategoriaWSResult item : fotosCategoria) {

				stmt.bindLong(1, item.getCategoriaId());
				stmt.bindString(2, item.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerFotoCategoriaPor(int categoriaId)
    {
    	String str = "_categoriaId=" + categoriaId;
    	Cursor localCursor = this.db.query(true,FOTOCATEGORIA_TABLE_NAME,FOTOCATEGORIA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerFotosCategoria()
    {
    	Cursor localCursor = this.db.query(true,FOTOCATEGORIA_TABLE_NAME,FOTOCATEGORIA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //CIUDAD//
    public static final String KEY_CIUDAD_ROW_ID = "_id";
    public static final String KEY_CIUDAD_CIUDADID = "_ciudadId";
    public static final String KEY_CIUDAD_NOMBRE = "_nombre";
   
    public static final int COL_CIUDAD_ROW_ID = 0;
    public static final int COL_CIUDAD_CIUDADID = 1;
    public static final int COL_CIUDAD_NOMBRE = 2;
    
    public static final String[] CIUDAD_ALL_KEYS = new String[] { 
    		KEY_CIUDAD_ROW_ID,KEY_CIUDAD_CIUDADID,KEY_CIUDAD_NOMBRE};
    
    public static final String CIUDAD_TABLE_NAME = "tbl_Ciudad";
    
    public static final String CIUDAD_TABLE_CREATE = "CREATE TABLE " + CIUDAD_TABLE_NAME + "("
    		+ KEY_CIUDAD_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CIUDAD_CIUDADID + " integer NOT NULL, "
    		+ KEY_CIUDAD_NOMBRE + " text NOT NULL);";

    public boolean borrarCiudadPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(CIUDAD_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCiudades()
    {
    	Cursor localCursor = obtenerCiudades();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarCiudadPor(localCursor.getLong((int)l));
    		} while (localCursor.moveToNext());
    	}
    	localCursor.close();
	}
    
    public long insertarCiudad(ArrayList<CiudadWSResult> ciudades)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_Ciudad(_ciudadId, _nombre) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (CiudadWSResult item : ciudades) {

				stmt.bindLong(1, item.getCiudadId());
				stmt.bindString(2, item.getNombre());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
        
    public Cursor obtenerCiudadPorCiudadId(int ciudadId)
    {
    	String str = "_ciudadId=" + ciudadId;
    	Cursor localCursor = this.db.query(true,CIUDAD_TABLE_NAME,CIUDAD_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
        
    public Cursor obtenerCiudades()
    {
    	Cursor localCursor = this.db.query(true,CIUDAD_TABLE_NAME,CIUDAD_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PROVINCIA//
    public static final String KEY_PROVINCIA_ROW_ID = "_id";
    public static final String KEY_PROVINCIA_PROVINCIAID = "_provinciaId";
    public static final String KEY_PROVINCIA_NOMBRE = "_nombre";
    public static final String KEY_PROVINCIA_CIUDADID = "_ciudadId";
   
    public static final int COL_PROVINCIA_ROW_ID = 0;
    public static final int COL_PROVINCIA_PROVINCIAID = 1;
    public static final int COL_PROVINCIA_NOMBRE = 2;
    public static final int COL_PROVINCIA_CIUDADID = 3;
    
    public static final String[] PROVINCIA_ALL_KEYS = new String[] { 
    		KEY_PROVINCIA_ROW_ID,KEY_PROVINCIA_PROVINCIAID,KEY_PROVINCIA_NOMBRE,
    		KEY_PROVINCIA_CIUDADID};
    
    public static final String PROVINCIA_TABLE_NAME = "tbl_Provincia";
    
    public static final String PROVINCIA_TABLE_CREATE = "CREATE TABLE " + PROVINCIA_TABLE_NAME + "("
    		+ KEY_PROVINCIA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PROVINCIA_PROVINCIAID + " integer NOT NULL, "
    		+ KEY_PROVINCIA_NOMBRE + " text NOT NULL, "
    		+ KEY_PROVINCIA_CIUDADID + " integer NOT NULL);";

    public boolean borrarProvinciaPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(PROVINCIA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProvincias()
    {
    	Cursor localCursor = obtenerProvincias();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarProvinciaPor(localCursor.getLong((int)l));
    		} while (localCursor.moveToNext());
    	}
    	localCursor.close();
	}
    
    public long insertarProvincia(ArrayList<ProvinciaWSResult> provincias)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_Provincia(_provinciaId, _nombre, _ciudadId) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ProvinciaWSResult item : provincias) {

				stmt.bindLong(1, item.getProvinciaId());
				stmt.bindString(2, item.getNombre());
				stmt.bindLong(3, item.getCiudadId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
        
    public Cursor obtenerProvinciaPorProvinciaId(int provinciaId)
    {
    	String str = "_provinciaId=" + provinciaId;
    	Cursor localCursor = this.db.query(true,PROVINCIA_TABLE_NAME,PROVINCIA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    public Cursor obtenerProvinciasPorCiudadId(int ciudadId)
    {
    	String str = "_ciudadId=" + ciudadId;
    	Cursor localCursor = this.db.query(true,PROVINCIA_TABLE_NAME,PROVINCIA_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
        
    public Cursor obtenerProvincias()
    {
    	Cursor localCursor = this.db.query(true,PROVINCIA_TABLE_NAME,PROVINCIA_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //PRECIOLISTANOMBRE//
    public static final String KEY_PRECIOLISTANOMBRE_ROW_ID = "_id";
    public static final String KEY_PRECIOLISTANOMBRE_PRECIOLISTANOMBREID = "_precioListaNombreId";
    public static final String KEY_PRECIOLISTANOMBRE_NOMBRE = "_nombre";
   
    public static final int COL_PRECIOLISTANOMBRE_ROW_ID = 0;
    public static final int COL_PRECIOLISTANOMBRE_PRECIOLISTANOMBREID = 1;
    public static final int COL_PRECIOLISTANOMBRE_NOMBRE = 2;
    
    public static final String[] PRECIOLISTANOMBRE_ALL_KEYS = new String[] { 
    		KEY_PRECIOLISTANOMBRE_ROW_ID,KEY_PRECIOLISTANOMBRE_PRECIOLISTANOMBREID,KEY_PRECIOLISTANOMBRE_NOMBRE};
    
    public static final String PRECIOLISTANOMBRE_TABLE_NAME = "tbl_PrecioListaNombre";
    
    public static final String PRECIOLISTANOMBRE_TABLE_CREATE = "CREATE TABLE " + PRECIOLISTANOMBRE_TABLE_NAME + "("
    		+ KEY_PRECIOLISTANOMBRE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRECIOLISTANOMBRE_PRECIOLISTANOMBREID + " integer NOT NULL, "
    		+ KEY_PRECIOLISTANOMBRE_NOMBRE + " text NOT NULL);";

    public boolean borrarPrecioListaNombrePor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(PRECIOLISTANOMBRE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarPreciosListaNombre()
    {
    	Cursor localCursor = obtenerPreciosListaNombre();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarPrecioListaNombrePor(localCursor.getLong((int)l));
    		} while (localCursor.moveToNext());
    	}
    	localCursor.close();
	}
    
    public long insertarPrecioListaNombre(ArrayList<PrecioListaNombreWSResult> preciosLista)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_PrecioListaNombre(_precioListaNombreId, _nombre) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (PrecioListaNombreWSResult item : preciosLista) {

				stmt.bindLong(1, item.getPrecioListaId());
				stmt.bindString(2, item.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
        
    public Cursor obtenerPrecioListaNombrePorPrecioListaNombreId(int precioListaNombreId)
    {
    	String str = "_precioListaNombreId=" + precioListaNombreId;
    	Cursor localCursor = this.db.query(true,PRECIOLISTANOMBRE_TABLE_NAME,PRECIOLISTANOMBRE_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
        
    public Cursor obtenerPreciosListaNombre()
    {
    	Cursor localCursor = this.db.query(true,PRECIOLISTANOMBRE_TABLE_NAME,PRECIOLISTANOMBRE_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //RUTAVENDEDOR//
    public static final String KEY_RUTAVENDEDOR_ROW_ID = "_id";
    public static final String KEY_RUTAVENDEDOR_VENDEDORID = "_vendedorId";
    public static final String KEY_RUTAVENDEDOR_RUTAID = "_rutaId";
    public static final String KEY_RUTAVENDEDOR_DIAID = "_diaId";
    public static final String KEY_RUTAVENDEDOR_DIAVISITAID = "_diaVisitaId";
    public static final String KEY_RUTAVENDEDOR_DESCRIPCION = "_descripcion";
   
    public static final int COL_RUTAVENDEDOR_ROW_ID = 0;
    public static final int COL_RUTAVENDEDOR_VENDEDORID = 1;
    public static final int COL_RUTAVENDEDOR_RUTAID = 2;
    public static final int COL_RUTAVENDEDOR_DIAID = 3;
    public static final int COL_RUTAVENDEDOR_DIAVISITAID = 4;
    public static final int COL_RUTAVENDEDOR_DESCRIPCION = 5;
    
    public static final String[] RUTAVENDEDOR_ALL_KEYS = new String[] { 
    		KEY_RUTAVENDEDOR_ROW_ID,KEY_RUTAVENDEDOR_VENDEDORID,KEY_RUTAVENDEDOR_RUTAID,
    		KEY_RUTAVENDEDOR_DIAID,KEY_RUTAVENDEDOR_DIAVISITAID,KEY_RUTAVENDEDOR_DESCRIPCION};
    
    public static final String RUTAVENDEDOR_TABLE_NAME = "tbl_RutaVendedor";
    
    public static final String RUTAVENDEDOR_TABLE_CREATE = "CREATE TABLE " + RUTAVENDEDOR_TABLE_NAME + "("
    		+ KEY_RUTAVENDEDOR_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_RUTAVENDEDOR_VENDEDORID + " integer NOT NULL, "
    		+ KEY_RUTAVENDEDOR_RUTAID + " integer NOT NULL, "
    		+ KEY_RUTAVENDEDOR_DIAID + " integer NOT NULL, "
    		+ KEY_RUTAVENDEDOR_DIAVISITAID + " integer NOT NULL, "
    		+ KEY_RUTAVENDEDOR_DESCRIPCION + " text NOT NULL);";

    public boolean borrarRutaVendedorPor(long id)
    {
    	String str = "_id=" + id;
    	return this.db.delete(RUTAVENDEDOR_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarRutasVendedor()
    {
    	Cursor localCursor = obtenerRutasVendedor();
    	long l = localCursor.getColumnIndexOrThrow("_id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarRutaVendedorPor(localCursor.getLong((int)l));
    		} while (localCursor.moveToNext());
    	}
    	localCursor.close();
	}
    
    public long insertarRutaVendedor(int vendedorId,int rutaId,int diaId,int diaVisitaId,String descripcion)
    {
    	ContentValues localContentValues = new ContentValues();
    	localContentValues.put("_vendedorId", vendedorId);
    	localContentValues.put("_rutaId", rutaId);
    	localContentValues.put("_diaId", diaId);
    	localContentValues.put("_diaVisitaId", diaVisitaId);
    	localContentValues.put("_descripcion", String.valueOf(descripcion));
    	return this.db.insert(RUTAVENDEDOR_TABLE_NAME, null, localContentValues);
    }
        
    public Cursor obtenerRutaVendedorPor(int diaVisitaId)
    {
    	String str = "_diaVisitaId=" + diaVisitaId;
    	Cursor localCursor = this.db.query(true,RUTAVENDEDOR_TABLE_NAME,RUTAVENDEDOR_ALL_KEYS, str, null, null, null, null, null);
    	if (localCursor != null) 
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
        
    public Cursor obtenerRutasVendedor()
    {
    	Cursor localCursor = this.db.query(true,RUTAVENDEDOR_TABLE_NAME,RUTAVENDEDOR_ALL_KEYS, null, null, null, null, null, null);
    	if (localCursor != null)
    	{
    		localCursor.moveToFirst();
    	}
    	return localCursor;
    }
    
    //VENDEDORZONAVENTA//	    
    public static final String KEY_VENDEDORZONAVENTA_ROW_ID = "_Id";
    public static final String KEY_VENDEDORZONAVENTA_ZONAVENTAID = "_zonaVentaId";
    public static final String KEY_VENDEDORZONAVENTA_RUTAID = "_rutaId";
    
    public static final int COL_VENDEDORZONAVENTA_ROW_ID = 0;
    public static final int COL_VENDEDORZONAVENTA_ZONAVENTAID = 1;
    public static final int COL_VENDEDORZONAVENTA_RUTAID = 2;
    
    public static final String[] VENDEDORZONAVENTA_ALL_KEYS = new String[] {
    		KEY_VENDEDORZONAVENTA_ROW_ID,KEY_VENDEDORZONAVENTA_ZONAVENTAID,KEY_VENDEDORZONAVENTA_RUTAID
    	};
    public static final String VENDEDORZONAVENTA_TABLE_NAME = "tbl_vendedorZonaVenta";
    
    public static final String VENDEDORZONAVENTA_TABLE_CREATE = "CREATE TABLE " + VENDEDORZONAVENTA_TABLE_NAME + "("
    		+ KEY_VENDEDORZONAVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_VENDEDORZONAVENTA_ZONAVENTAID + " integer NOT NULL, "
    		+ KEY_VENDEDORZONAVENTA_RUTAID + " boolean NOT NULL);";
    
    public boolean borrarVendedorZonaVentaPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(VENDEDORZONAVENTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarVendedoresZonaVenta()
    {
    	Cursor localCursor = obtenerVendedoresZonaVenta();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarVendedorZonaVentaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarVendedorZonaVenta(ArrayList<VendedorZonaVentaWSResult> vendedoresZonaVenta)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_VendedorZonaVenta(_zonaVentaId, _rutaId) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (VendedorZonaVentaWSResult item : vendedoresZonaVenta) {

				stmt.bindLong(1, item.getZonaVentaId());
				stmt.bindString(2, item.getRutaId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerVendedorZonaVentaPor(int rutaId)
    {
      String str = "_rutaId=" + rutaId;
      Cursor localCursor = this.db.query(true,VENDEDORZONAVENTA_TABLE_NAME, VENDEDORZONAVENTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerVendedoresZonaVenta()
    {
      Cursor localCursor = this.db.query(true,VENDEDORZONAVENTA_TABLE_NAME, VENDEDORZONAVENTA_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //ASIGNACIONRUTA//	    
    public static final String KEY_ASIGNACIONRUTA_ROW_ID = "_Id";
    public static final String KEY_ASIGNACIONRUTA_CLIENTEID = "_clienteId";
    public static final String KEY_ASIGNACIONRUTA_A = "_A";
    public static final String KEY_ASIGNACIONRUTA_B = "_B";
    public static final String KEY_ASIGNACIONRUTA_C = "_C";
    public static final String KEY_ASIGNACIONRUTA_D = "_D";
    public static final String KEY_ASIGNACIONRUTA_E = "_E";
    public static final String KEY_ASIGNACIONRUTA_F = "_F";
    public static final String KEY_ASIGNACIONRUTA_G = "_G";
    public static final String KEY_ASIGNACIONRUTA_H = "_H";
    public static final String KEY_ASIGNACIONRUTA_I = "_I";
    public static final String KEY_ASIGNACIONRUTA_J = "_J";
    public static final String KEY_ASIGNACIONRUTA_K = "_K";
    public static final String KEY_ASIGNACIONRUTA_L = "_L";
    public static final String KEY_ASIGNACIONRUTA_M = "_M";
    public static final String KEY_ASIGNACIONRUTA_N = "_N";
    public static final String KEY_ASIGNACIONRUTA_O = "_O";
    public static final String KEY_ASIGNACIONRUTA_P = "_P";
    public static final String KEY_ASIGNACIONRUTA_Q = "_Q";
    public static final String KEY_ASIGNACIONRUTA_R = "_R";
    public static final String KEY_ASIGNACIONRUTA_Ivs = "_Ivs";
    public static final String KEY_ASIGNACIONRUTA_Ivm = "_Ivm";
            
    public static final int COL_ASIGNACIONRUTA_ROW_ID = 0;
    public static final int COL_ASIGNACIONRUTA_CLIENTEID = 1;
    public static final int COL_ASIGNACIONRUTA_A = 2;
    public static final int COL_ASIGNACIONRUTA_B = 3;
    public static final int COL_ASIGNACIONRUTA_C = 4;
    public static final int COL_ASIGNACIONRUTA_D = 5;
    public static final int COL_ASIGNACIONRUTA_E = 6;
    public static final int COL_ASIGNACIONRUTA_F = 7;
    public static final int COL_ASIGNACIONRUTA_G = 8;
    public static final int COL_ASIGNACIONRUTA_H = 9;
    public static final int COL_ASIGNACIONRUTA_I = 10;
    public static final int COL_ASIGNACIONRUTA_J = 11;
    public static final int COL_ASIGNACIONRUTA_K = 12;
    public static final int COL_ASIGNACIONRUTA_L = 13;
    public static final int COL_ASIGNACIONRUTA_M = 14;
    public static final int COL_ASIGNACIONRUTA_N = 15;
    public static final int COL_ASIGNACIONRUTA_O = 16;
    public static final int COL_ASIGNACIONRUTA_P = 17;
    public static final int COL_ASIGNACIONRUTA_Q = 18;
    public static final int COL_ASIGNACIONRUTA_R = 19;
    public static final int COL_ASIGNACIONRUTA_S = 20;
    public static final int COL_ASIGNACIONRUTA_Ivs = 21;
    public static final int COL_ASIGNACIONRUTA_Ivm = 22;
    
    
    public static final String[] ASIGNACIONRUTA_ALL_KEYS = new String[] {
    		KEY_ASIGNACIONRUTA_ROW_ID,KEY_ASIGNACIONRUTA_CLIENTEID,KEY_ASIGNACIONRUTA_A,
    		KEY_ASIGNACIONRUTA_B,KEY_ASIGNACIONRUTA_C,KEY_ASIGNACIONRUTA_D,
    		KEY_ASIGNACIONRUTA_E,KEY_ASIGNACIONRUTA_F,KEY_ASIGNACIONRUTA_G,
    		KEY_ASIGNACIONRUTA_H,KEY_ASIGNACIONRUTA_I,KEY_ASIGNACIONRUTA_J,
    		KEY_ASIGNACIONRUTA_K,KEY_ASIGNACIONRUTA_L,KEY_ASIGNACIONRUTA_M,
    		KEY_ASIGNACIONRUTA_N,KEY_ASIGNACIONRUTA_O,KEY_ASIGNACIONRUTA_P,
    		KEY_ASIGNACIONRUTA_Q,KEY_ASIGNACIONRUTA_R,KEY_ASIGNACIONRUTA_Ivs,
    		KEY_ASIGNACIONRUTA_Ivm
    	};
    public static final String ASIGNACIONRUTA_TABLE_NAME = "tbl_signacionRuta";
    
    public static final String ASIGNACIONRUTA_TABLE_CREATE = "CREATE TABLE " + ASIGNACIONRUTA_TABLE_NAME + "("
    		+ KEY_ASIGNACIONRUTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_ASIGNACIONRUTA_CLIENTEID + " integer NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_A + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_B + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_C + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_D + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_E + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_F + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_G + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_H + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_I + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_J + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_K + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_L + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_M + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_N + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_O + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_P + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_Q + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_R + " boolean NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_Ivs + " float NOT NULL, "
    		+ KEY_ASIGNACIONRUTA_Ivm + " float NOT NULL);";
    
    public boolean borrarAsignacionRutaPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(ASIGNACIONRUTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarAsignacionesRuta()
    {
      Cursor localCursor = obtenerAsignacionesRuta();
      long l = localCursor.getColumnIndexOrThrow("_Id");
      if (localCursor.moveToFirst()) 
      {
        do
        {
          borrarAsignacionRutaPor(localCursor.getLong((int)l));
        } 
        while (localCursor.moveToNext());
      }
      localCursor.close();
    }
    
    public long insertarAsignacionRuta(ArrayList<AsignacionRutaWSResult> asignacionesRuta)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_signacionRuta(_clienteId, _A, _B, _C, _D, _E, _F, _G, _H, _I, _J, _K, _L, _M, _N, _O, _P, _Q, _R, _Ivs, _Ivm) " +
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (AsignacionRutaWSResult item : asignacionesRuta) {

				stmt.bindLong(1, item.getClienteId());
				stmt.bindLong(2, item.isA()?1:0);
				stmt.bindLong(3, item.isB()?1:0);
				stmt.bindLong(4, item.isC()?1:0);
				stmt.bindLong(5, item.isD()?1:0);
				stmt.bindLong(6, item.isE()?1:0);
				stmt.bindLong(7, item.isF()?1:0);
				stmt.bindLong(8, item.isG()?1:0);
				stmt.bindLong(9, item.isH()?1:0);
				stmt.bindLong(10, item.isI()?1:0);
				stmt.bindLong(11, item.isJ()?1:0);
				stmt.bindLong(12, item.isK()?1:0);
				stmt.bindLong(13, item.isL()?1:0);
				stmt.bindLong(14, item.isM()?1:0);
				stmt.bindLong(15, item.isN()?1:0);
				stmt.bindLong(16, item.isO()?1:0);
				stmt.bindLong(17, item.isP()?1:0);
				stmt.bindLong(18, item.isQ()?1:0);
				stmt.bindLong(19, item.isR()?1:0);
				stmt.bindLong(20,0);
				stmt.bindLong(21,0);

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerAsignacionRutaPor(int clienteId)
    {
      String str = "_clienteId=" + clienteId;
      Cursor localCursor = this.db.query(true,ASIGNACIONRUTA_TABLE_NAME, ASIGNACIONRUTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerAsignacionesRuta()
    {
      Cursor localCursor = this.db.query(true,ASIGNACIONRUTA_TABLE_NAME, ASIGNACIONRUTA_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //CANALRUTA//	    
    public static final String KEY_CANALRUTA_ROW_ID = "_Id";
    public static final String KEY_CANALRUTA_CANALRUTAID = "_canalRutaId";
    public static final String KEY_CANALRUTA_DESCRIPCION = "_descripcion";
        
    public static final int COL_CANALRUTA_ROW_ID = 0;
    public static final int COL_CANALRUTA_CANALRUTAID = 1;
    public static final int COL_CANALRUTA_DESCRIPCION = 2;
   
    
    public static final String[] CANALRUTA_ALL_KEYS = new String[] {
    		KEY_CANALRUTA_ROW_ID,KEY_CANALRUTA_CANALRUTAID,KEY_CANALRUTA_DESCRIPCION
    	};
    public static final String CANALRUTA_TABLE_NAME = "tbl_CanalRuta";
    
    public static final String CANALRUTA_TABLE_CREATE = "CREATE TABLE " + CANALRUTA_TABLE_NAME + "("
    		+ KEY_CANALRUTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CANALRUTA_CANALRUTAID + " integer NOT NULL, "
    		+ KEY_CANALRUTA_DESCRIPCION + " text NOT NULL);";
    
    public boolean borrarCanalRutaPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(CANALRUTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCanalesRuta()
    {
    	Cursor localCursor = obtenerCanalesRuta();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarCanalRutaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarCanalRuta(ArrayList<CanalRutaWSResult> canalesRuta)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_canalRuta(_canalRutaId, _descripcion) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (CanalRutaWSResult item : canalesRuta) {

				stmt.bindLong(1, item.getCanalRutaId());
				stmt.bindString(2, item.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerCanalRutaPor(int canalRutaId)
    {
      String str = "_Id=" + canalRutaId;
      Cursor localCursor = this.db.query(true,CANALRUTA_TABLE_NAME, CANALRUTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerCanalesRuta()
    {
      Cursor localCursor = this.db.query(true,CANALRUTA_TABLE_NAME, CANALRUTA_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //CANALRUTAPRECIO//	    
    public static final String KEY_CANALRUTAPRECIO_ROW_ID = "_Id";
    public static final String KEY_CANALRUTAPRECIO_CANALPRECIORUTAID = "_canalPrecioRutaId";
    public static final String KEY_CANALRUTAPRECIO_CANALRUTAID = "_canalRutaId";
    public static final String KEY_CANALRUTAPRECIO_PRODUCTOID = "_productoId";
    public static final String KEY_CANALRUTAPRECIO_HPB = "_hpb";
    public static final String KEY_CANALRUTAPRECIO_DESCUENTOCANAL = "_descuentoCanal";
    public static final String KEY_CANALRUTAPRECIO_DESCUENTOAJUSTE = "_descuentoAjuste";
    
    public static final int COL_CANALRUTAPRECIO_ROW_ID = 0;
    public static final int COL_CANALRUTAPRECIO_CANALPRECIORUTAID = 1;
    public static final int COL_CANALRUTAPRECIO_CANALRUTAID = 2;
    public static final int COL_CANALRUTAPRECIO_PRODUCTOID = 3;
    public static final int COL_CANALRUTAPRECIO_HPB = 4;
    public static final int COL_CANALRUTAPRECIO_DESCUENTOCANAL = 5;
    public static final int COL_CANALRUTAPRECIO_DESCUENTOAJUSTE = 6;
    
    public static final String[] CANALRUTAPRECIO_ALL_KEYS = new String[] {
    		KEY_CANALRUTAPRECIO_ROW_ID,KEY_CANALRUTAPRECIO_CANALPRECIORUTAID,KEY_CANALRUTAPRECIO_CANALRUTAID,
    		KEY_CANALRUTAPRECIO_PRODUCTOID,KEY_CANALRUTAPRECIO_HPB,KEY_CANALRUTAPRECIO_DESCUENTOCANAL,
    		KEY_CANALRUTAPRECIO_DESCUENTOAJUSTE
    	};
    public static final String CANALRUTAPRECIO_TABLE_NAME = "tbl_CanalRutaPrecio";
    
    public static final String CANALRUTAPRECIO_TABLE_CREATE = "CREATE TABLE " + CANALRUTAPRECIO_TABLE_NAME + "("
    		+ KEY_CANALRUTAPRECIO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_CANALRUTAPRECIO_CANALPRECIORUTAID + " integer NOT NULL, "
    		+ KEY_CANALRUTAPRECIO_CANALRUTAID + " integer NOT NULL, "
    		+ KEY_CANALRUTAPRECIO_PRODUCTOID + " integer NOT NULL, "
    		+ KEY_CANALRUTAPRECIO_HPB + " float NOT NULL, "
    		+ KEY_CANALRUTAPRECIO_DESCUENTOCANAL + " float NOT NULL, "
    		+ KEY_CANALRUTAPRECIO_DESCUENTOAJUSTE + " float NOT NULL);";
    
    public boolean borrarCanalRutaPrecioPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(CANALRUTAPRECIO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarCanalesRutaPrecio()
    {
    	Cursor localCursor = obtenerCanalesRutaPrecio();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarCanalRutaPrecioPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarCanalRutaPrecio(ArrayList<CanalRutaPrecioWSResult> canalesRutaPrecio)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_CanalRutaPrecio(_canalPrecioRutaId, _canalRutaId, _productoId, _hpb, _descuentoCanal, _descuentoAjuste) VALUES (?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (CanalRutaPrecioWSResult item : canalesRutaPrecio) {

				stmt.bindLong(1, item.getCanalPrecioRutaId());
				stmt.bindLong(2, item.getCanalRutaId());
				stmt.bindLong(3, item.getProductoId());
				stmt.bindDouble(4, item.getHpb());
				stmt.bindDouble(5, item.getDescuentoCanal());
				stmt.bindDouble(6, item.getDescuentoAjuste());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerCanalRutaPrecioPor(int canalPrecioRutaId)
    {
      String str = "_Id=" + canalPrecioRutaId;
      Cursor localCursor = this.db.query(true,CANALRUTAPRECIO_TABLE_NAME, CANALRUTAPRECIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerCanalRutaPrecioPorCanalRutaIdYProductoId(int canalRutaId, int productoId)
    {
      String str = "_canalRutaId = " + canalRutaId + " and _productoId = " + productoId;
      Cursor localCursor = this.db.query(true,CANALRUTAPRECIO_TABLE_NAME, CANALRUTAPRECIO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerCanalesRutaPrecio()
    {
      Cursor localCursor = this.db.query(true,CANALRUTAPRECIO_TABLE_NAME, CANALRUTAPRECIO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //PRONTOPAGOUNI//	    
    public static final String KEY_PRONTOPAGOUNI_ROW_ID = "_Id";
    public static final String KEY_PRONTOPAGOUNI_PRONTOPAGOID = "_prontoPagoId";
    public static final String KEY_PRONTOPAGOUNI_DESCRIPCION = "_descripcion";
    
    public static final int COL_PRONTOPAGOUNI_ROW_ID = 0;
    public static final int COL_PRONTOPAGOUNI_PRONTOPAGOID = 1;
    public static final int COL_PRONTOPAGOUNI_DESCRIPCION = 2;
    
    public static final String[] PRONTOPAGOUNI_ALL_KEYS = new String[] {
    		KEY_PRONTOPAGOUNI_ROW_ID,KEY_PRONTOPAGOUNI_PRONTOPAGOID,KEY_PRONTOPAGOUNI_DESCRIPCION
    	};
    
    public static final String PRONTOPAGOUNI_TABLE_NAME = "tbl_prontoPagoUni";
    
    public static final String PRONTOPAGOUNI_TABLE_CREATE = "CREATE TABLE " + PRONTOPAGOUNI_TABLE_NAME + "("
    		+ KEY_PRONTOPAGOUNI_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRONTOPAGOUNI_PRONTOPAGOID + " integer NOT NULL, "
    		+ KEY_PRONTOPAGOUNI_DESCRIPCION + " text NOT NULL);";
    
    public boolean borrarProntoPagoUniPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(PRONTOPAGOUNI_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProntosPagoUni()
    {
    	Cursor localCursor = obtenerProntosPagoUni();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarProntoPagoUniPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarProntoPagoUni(ProntoPagoUniWSResult prontosPagoUni)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProntoPagoUni(_prontoPagoId, _descripcion) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			//for (int i = 0; i < prontoPagoUni.getPropertyCount(); i++) {
			//	SoapObject soapObject = (SoapObject) prontoPagoUni.getProperty(i);

				stmt.bindLong(1, prontosPagoUni.getProntoPagoId());
				stmt.bindString(2, prontosPagoUni.getDescripcion());

				stmt.executeInsert();
				stmt.clearBindings();
			//}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProntoPagoUniPor(int prontoPagoId)
    {
      String str = "_prontoPagoId = " + prontoPagoId;
      Cursor localCursor = this.db.query(true,PRONTOPAGOUNI_TABLE_NAME, PRONTOPAGOUNI_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProntosPagoUni()
    {
      Cursor localCursor = this.db.query(true,PRONTOPAGOUNI_TABLE_NAME, PRONTOPAGOUNI_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //PRONTOPAGOJERARQUIA//
    public static final String KEY_PRONTOPAGOJERARQUIA_ROW_ID = "_Id";
    public static final String KEY_PRONTOPAGOJERARQUIA_PRONTOPAGOID = "_prontoPagoId";
    public static final String KEY_PRONTOPAGOJERARQUIA_JERARQUIA = "_jerarquia";
	public static final String KEY_PRONTOPAGOJERARQUIA_JERARQUIAID = "_jerarquiaId";
    public static final String KEY_PRONTOPAGOJERARQUIA_DESCUENTO = "_descuento";
    
    public static final int COL_PRONTOPAGOJERARQUIA_ROW_ID = 0;
    public static final int COL_PRONTOPAGOJERARQUIA_PRONTOPAGOID = 1;
    public static final int COL_PRONTOPAGOJERARQUIA_JERARQUIA = 2;
	public static final int COL_PRONTOPAGOJERARQUIA_JERARQUIAID = 3;
    public static final int COL_PRONTOPAGOJERARQUIA_DESCUENTO = 4;
    
    public static final String[] PRONTOPAGOJERARQUIA_ALL_KEYS = new String[] {
    		KEY_PRONTOPAGOJERARQUIA_ROW_ID, KEY_PRONTOPAGOJERARQUIA_PRONTOPAGOID, KEY_PRONTOPAGOJERARQUIA_JERARQUIA,
			KEY_PRONTOPAGOJERARQUIA_JERARQUIAID, KEY_PRONTOPAGOJERARQUIA_DESCUENTO
    	};
    
    public static final String PRONTOPAGOJERARQUIA_TABLE_NAME = "tbl_prontoPagoJerarquia";
    
    public static final String PRONTOPAGOJERARQUIA_TABLE_CREATE = "CREATE TABLE " + PRONTOPAGOJERARQUIA_TABLE_NAME + "("
    		+ KEY_PRONTOPAGOJERARQUIA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRONTOPAGOJERARQUIA_PRONTOPAGOID + " integer NOT NULL, "
			+ KEY_PRONTOPAGOJERARQUIA_JERARQUIA + " text NOT NULL, "
    		+ KEY_PRONTOPAGOJERARQUIA_JERARQUIAID + " integer NOT NULL, "
    		+ KEY_PRONTOPAGOJERARQUIA_DESCUENTO + " float NOT NULL);";
    
    public boolean borrarProntoPagoJerarquiaPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(PRONTOPAGOJERARQUIA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProntosPagoJerarquia()
    {
    	Cursor localCursor = obtenerProntosPagoJerarquia();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarProntoPagoJerarquiaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarProntoPagoJerarquia(ArrayList<ProntoPagoJerarquiaWSResult> prontosPagoJerarquia)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProntoPagoJerarquia(_prontoPagoId, _jerarquia, _jerarquiaId, _descuento) VALUES (?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ProntoPagoJerarquiaWSResult item : prontosPagoJerarquia) {

				stmt.bindLong(1, item.getProntoPagoId());
				stmt.bindString(2, item.getJerarquia());
				stmt.bindLong(3, item.getJerarquiaId());
				stmt.bindDouble(4, item.getDescuento());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProntoPagoJerarquiaPor(int prontoPagoId)
    {
      String str = "_prontoPagoId = " + prontoPagoId;
      Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProntoPagoJerarquia1IdPorProductoId(int productoId)
    {
    	String query = "select _jerarquia1Id from tbl_Categoria where _categoriaId in"
    					+ "(select _categoriaId from tbl_producto where _productoid =" + productoId + ")";

		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null) 
		{
			localCursor.moveToFirst();
		}
		return localCursor;
    }

	public Cursor obtenerProntoPagoJerarquia2IdPorProductoId(int productoId)
	{
		String query = "select _jerarquia2Id from tbl_Categoria where _categoriaId in"
				+ "(select _categoriaId from tbl_producto where _productoid =" + productoId + ")";

		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoJerarquia3IdPorProductoId(int productoId)
	{
		String query = "select _jerarquia3Id from tbl_Categoria where _categoriaId in"
				+ "(select _categoriaId from tbl_producto where _productoid =" + productoId + ")";

		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoJerarquia5IdPorProductoId(int productoId)
	{
		String query = "select _jerarquia5Id from tbl_Categoria where _categoriaId in"
				+ "(select _categoriaId from tbl_producto where _productoid =" + productoId + ")";

		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoCategoriaIdPorProductoId(int productoId)
	{
		String query = "select _categoriaId from tbl_producto where _productoid =" + productoId;

		Cursor localCursor = db.rawQuery(query,null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoJerarquia1Por(int jerarquia1Id)
	{
		String str = "_jerarquia = 'Jerarquia1' and _jerarquiaId = " + jerarquia1Id;
		Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoJerarquia2Por(int jerarquia2Id)
	{
		String str = "_jerarquia = 'Jerarquia2' and _jerarquiaId = " + jerarquia2Id;
		Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoJerarquia3Por(int jerarquia3Id)
	{
		String str = "_jerarquia = 'Jerarquia3' and _jerarquiaId = " + jerarquia3Id;
		Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoJerarquia5Por(int jerarquia5Id)
	{
		String str = "_jerarquia = 'Jerarquia5' and _jerarquiaId = " + jerarquia5Id;
		Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

	public Cursor obtenerProntoPagoCategoriaIdPor(int categoriaId)
	{
		String str = "_jerarquia = 'Categoria' and _jerarquiaId = " + categoriaId;
		Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, str, null, null, null, null, null);
		if (localCursor != null)
		{
			localCursor.moveToFirst();
		}
		return localCursor;
	}

    public Cursor obtenerProntosPagoJerarquia()
    {
      Cursor localCursor = this.db.query(true,PRONTOPAGOJERARQUIA_TABLE_NAME, PRONTOPAGOJERARQUIA_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //PRONTOPAGOCANALRUTA//	    
    public static final String KEY_PRONTOPAGOCANALRUTA_ROW_ID = "_Id";
    public static final String KEY_PRONTOPAGOCANALRUTA_PRONTOPAGOID = "_prontoPagoId";
    public static final String KEY_PRONTOPAGOCANALRUTA_CANALRUTAID = "_canalRutaId";
    
    public static final int COL_PRONTOPAGOCANALRUTA_ROW_ID = 0;
    public static final int COL_PRONTOPAGOCANALRUTA_PRONTOPAGOID = 1;
    public static final int COL_PRONTOPAGOCANALRUTA_CANALRUTAID = 2;
    
    public static final String[] PRONTOPAGOCANALRUTA_ALL_KEYS = new String[] {
    		KEY_PRONTOPAGOCANALRUTA_ROW_ID, KEY_PRONTOPAGOCANALRUTA_PRONTOPAGOID, KEY_PRONTOPAGOCANALRUTA_CANALRUTAID
    	};
    
    public static final String PRONTOPAGOCANALRUTA_TABLE_NAME = "tbl_prontoPagoCanalRuta";
    
    public static final String PRONTOPAGOCANALRUTA_TABLE_CREATE = "CREATE TABLE " + PRONTOPAGOCANALRUTA_TABLE_NAME + "("
    		+ KEY_PRONTOPAGOCANALRUTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRONTOPAGOCANALRUTA_PRONTOPAGOID + " integer NOT NULL, "
    		+ KEY_PRONTOPAGOCANALRUTA_CANALRUTAID + " integer NOT NULL);";
    
    public boolean borrarProntoPagoCanalRutaPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(PRONTOPAGOCANALRUTA_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProntosPagoCanalRuta()
    {
    	Cursor localCursor = obtenerProntosPagoCanalRuta();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarProntoPagoCanalRutaPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarProntoPagoCanalRuta(ArrayList<ProntoPagoCanalRutaWSResult> prontosPagoCanalRuta)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProntoPagoCanalRuta(_prontoPagoId, _canalRutaId) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (ProntoPagoCanalRutaWSResult item : prontosPagoCanalRuta) {

				stmt.bindLong(1, item.getProntoPagoId());
				stmt.bindLong(2, item.getCanalRutaId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProntoPagoCanalRutaPor(int canalRutaId)
    {
      String str = "_canalRutaId = " + canalRutaId;
      Cursor localCursor = this.db.query(true,PRONTOPAGOCANALRUTA_TABLE_NAME, PRONTOPAGOCANALRUTA_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProntosPagoCanalRuta()
    {
      Cursor localCursor = this.db.query(true,PRONTOPAGOCANALRUTA_TABLE_NAME, PRONTOPAGOCANALRUTA_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //PRONTOPAGOCLIENTE//	    
    public static final String KEY_PRONTOPAGOCLIENTE_ROW_ID = "_Id";
    public static final String KEY_PRONTOPAGOCLIENTE_PRONTOPAGOID = "_prontoPagoId";
    public static final String KEY_PRONTOPAGOCLIENTE_CLIENTEID = "_clienteId";
    
    public static final int COL_PRONTOPAGOCLIENTE_ROW_ID = 0;
    public static final int COL_PRONTOPAGOCLIENTE_PRONTOPAGOID = 1;
    public static final int COL_PRONTOPAGOCLIENTE_CLIENTEID = 2;
    
    public static final String[] PRONTOPAGOCLIENTE_ALL_KEYS = new String[] {
    		KEY_PRONTOPAGOCLIENTE_ROW_ID, KEY_PRONTOPAGOCLIENTE_PRONTOPAGOID, KEY_PRONTOPAGOCLIENTE_CLIENTEID
    	};
    
    public static final String PRONTOPAGOCLIENTE_TABLE_NAME = "tbl_prontoPagoCliente";
    
    public static final String PRONTOPAGOCLIENTE_TABLE_CREATE = "CREATE TABLE " + PRONTOPAGOCLIENTE_TABLE_NAME + "("
    		+ KEY_PRONTOPAGOCLIENTE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_PRONTOPAGOCLIENTE_PRONTOPAGOID + " integer NOT NULL, "
    		+ KEY_PRONTOPAGOCLIENTE_CLIENTEID + " integer NOT NULL);";
    
    public boolean borrarProntoPagoClientePor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(PRONTOPAGOCLIENTE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarProntosPagoCliente()
    {
    	Cursor localCursor = obtenerProntosPagoCliente();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarProntoPagoClientePor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarProntoPagoCliente(ArrayList<ProntoPagoClienteWSResult> prontosPagocliente)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ProntoPagoCliente(_prontoPagoId, _clienteId) VALUES (?,?)"
		);
		try {
			db.beginTransaction();
			for (ProntoPagoClienteWSResult item : prontosPagocliente) {

				stmt.bindLong(1, item.getProntoPagoId());
				stmt.bindLong(2, item.getClienteId());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerProntoPagoClientePor(int clienteId)
    {
      String str = "_clienteId = " + clienteId;
      Cursor localCursor = this.db.query(true,PRONTOPAGOCLIENTE_TABLE_NAME, PRONTOPAGOCLIENTE_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerProntosPagoCliente()
    {
      Cursor localCursor = this.db.query(true,PRONTOPAGOCLIENTE_TABLE_NAME, PRONTOPAGOCLIENTE_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    //DRAREBATESALDO//	    
    public static final String KEY_DRAREBATESALDO_ROW_ID = "_Id";
    public static final String KEY_DRAREBATESALDO_CLIENTEID = "_clienteId";
    public static final String KEY_DRAREBATESALDO_SALDO = "_saldo";
    public static final String KEY_DRAREBATESALDO_MAXDESCUENTO = "_maxDescuento";
    
    public static final int COL_DRAREBATESALDO_ROW_ID = 0;
    public static final int COL_DRAREBATESALDO_CLIENTEID = 1;
    public static final int COL_DRAREBATESALDO_SALDO = 2;
    public static final int COL_DRAREBATESALDO_MAXDESCUENTO = 3;
    
    public static final String[] DRAREBATESALDO_ALL_KEYS = new String[] {
    		KEY_DRAREBATESALDO_ROW_ID, KEY_DRAREBATESALDO_CLIENTEID, KEY_DRAREBATESALDO_SALDO,
    		KEY_DRAREBATESALDO_MAXDESCUENTO
    	};
    
    public static final String DRAREBATESALDO_TABLE_NAME = "tbl_DraRebateSaldo";
    
    public static final String DRAREBATESALDO_TABLE_CREATE = "CREATE TABLE " + DRAREBATESALDO_TABLE_NAME + "("
    		+ KEY_DRAREBATESALDO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_DRAREBATESALDO_CLIENTEID + " integer NOT NULL, "
    		+ KEY_DRAREBATESALDO_SALDO + " float NOT NULL, "
    		+ KEY_DRAREBATESALDO_MAXDESCUENTO + " float NOT NULL);";
    
    public boolean borrarDraRebateSaldoPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(DRAREBATESALDO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarDraRebatesSaldo()
    {
    	Cursor localCursor = obtenerDraRebatesSaldo();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarDraRebateSaldoPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarDraRebateSaldo(ArrayList<DraRebateSaldoWSResult> drasRebateSaldo)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_DraRebateSaldo(_clienteId, _saldo, _maxDescuento) VALUES (?,?,?)"
		);
		try {
			db.beginTransaction();
			for (DraRebateSaldoWSResult item : drasRebateSaldo) {

				stmt.bindLong(1, item.getClienteId());
				stmt.bindDouble(2, item.getSaldo());
				stmt.bindDouble(3, item.getMaxDescuento());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerDraRebateSaldoPor(int clienteId)
    {
      String str = "_clienteId = " + clienteId;
      Cursor localCursor = this.db.query(true,DRAREBATESALDO_TABLE_NAME, DRAREBATESALDO_ALL_KEYS, str, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerDraRebatesSaldo()
    {
      Cursor localCursor = this.db.query(true,DRAREBATESALDO_TABLE_NAME, DRAREBATESALDO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public int modificarSaldoDraRebateSaldo(int clienteId, float saldo)
	{
		String str = "_clienteId =" + clienteId;
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("_saldo", saldo);
		return this.db.update(DRAREBATESALDO_TABLE_NAME, localContentValues, str, null);
	}
    
    public int abonarSaldoDraRebateSaldo(int clienteId, float saldo)
   	{
       	int control = 0;
       	String query = "update tbl_DraRebateSaldo set _saldo = _saldo + " + String.valueOf(saldo) + " where _clienteId = " + String.valueOf(clienteId);
       	try
       	{
       		db.execSQL(query);
       		control = 1;
       	}
       	catch(SQLException ex)
       	{
       		control = 0;
       	}
       	
       	return control;
   	}
    
  //RELEVAMIENTO//	    
    public static final String KEY_RELEVAMIENTO_ROW_ID = "_Id";
    public static final String KEY_RELEVAMIENTO_RELEVAMIENTOID = "_relevamientoId";
    public static final String KEY_RELEVAMIENTO_NOMBRE = "_nombre";
    public static final String KEY_RELEVAMIENTO_FECHA = "_fecha";
    public static final String KEY_RELEVAMIENTO_EMPLEADOID = "_empleadoId";
    public static final String KEY_RELEVAMIENTO_DESCRIPCION = "_descripcion";
    public static final String KEY_RELEVAMIENTO_ACTIVO = "_activo";
    
    public static final int COL_RELEVAMIENTO_ROW_ID = 0;
    public static final int COL_RELEVAMIENTO_RELEVAMIENTOID = 1;
    public static final int COL_RELEVAMIENTO_NOMBRE = 2;
    public static final int COL_RELEVAMIENTO_FECHA = 3;
    public static final int COL_RELEVAMIENTO_EMPLEADOID = 4;
    public static final int COL_RELEVAMIENTO_DESCRIPCION = 5;
    public static final int COL_RELEVAMIENTO_ACTIVO = 6;
    
    public static final String[] RELEVAMIENTO_ALL_KEYS = new String[] {
    		KEY_RELEVAMIENTO_ROW_ID, KEY_RELEVAMIENTO_RELEVAMIENTOID, KEY_RELEVAMIENTO_NOMBRE,
    		KEY_RELEVAMIENTO_FECHA, KEY_RELEVAMIENTO_EMPLEADOID, KEY_RELEVAMIENTO_DESCRIPCION,
    		KEY_RELEVAMIENTO_ACTIVO
    	};
    
    public static final String RELEVAMIENTO_TABLE_NAME = "tbl_Relevamiento";
    
    public static final String RELEVAMIENTO_TABLE_CREATE = "CREATE TABLE " + RELEVAMIENTO_TABLE_NAME + "("
    		+ KEY_RELEVAMIENTO_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_RELEVAMIENTO_RELEVAMIENTOID + " integer NOT NULL, "
    		+ KEY_RELEVAMIENTO_NOMBRE + " text NOT NULL, "
    		+ KEY_RELEVAMIENTO_FECHA + " text NOT NULL, "
    		+ KEY_RELEVAMIENTO_EMPLEADOID + " integer NOT NULL, "
    		+ KEY_RELEVAMIENTO_DESCRIPCION + " text NOT NULL, "
    		+ KEY_RELEVAMIENTO_ACTIVO + " boolean NOT NULL);";
    
    public boolean borrarRelevamientoPor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(RELEVAMIENTO_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarRelevamientos()
    {
    	Cursor localCursor = obtenerRelevamientos();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarRelevamientoPor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarRelevamiento(List<Relevamiento> relevamiento)
    {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_Relevamiento(_relevamientoId, _nombre, _fecha, _empleadoId, _descripcion, _activo) VALUES (?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (Relevamiento item : relevamiento) {

				stmt.bindLong(1, item.getRelevamientoId());
				stmt.bindString(2, item.getNombre());
				stmt.bindString(3, item.getFecha());
				stmt.bindLong(4, item.getEmpleadoId());
				stmt.bindString(5, item.getDescripcion());
				stmt.bindLong(6, item.isActivo()?1:0);

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }
    
    public Cursor obtenerRelevamientos()
    {
      Cursor localCursor = this.db.query(true,RELEVAMIENTO_TABLE_NAME, RELEVAMIENTO_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
  //RELEVAMIENTODETALLE//	    
    public static final String KEY_RELEVAMIENTODETALLE_ROW_ID = "_Id";
    public static final String KEY_RELEVAMIENTODETALLE_RELEVAMIENTOID = "_relevamientoId";
    public static final String KEY_RELEVAMIENTODETALLE_NOMBRE = "_nombre";
    public static final String KEY_RELEVAMIENTODETALLE_TIPONEGOCIOID = "_tipoNegocioId";
    public static final String KEY_RELEVAMIENTODETALLE_CATEGORIAID = "_categoriaId";
    public static final String KEY_RELEVAMIENTODETALLE_FOTOSIZE = "_fotoSize";
    public static final String KEY_RELEVAMIENTODETALLE_FOTOBINARY = "_fotoBinary";
    public static final String KEY_RELEVAMIENTODETALLE_LATITUD = "_latitud";
    public static final String KEY_RELEVAMIENTODETALLE_LONGITUD = "_longitud";
    public static final String KEY_RELEVAMIENTODETALLE_SINCRO = "_sincro";
    
    public static final int COL_RELEVAMIENTODETALLE_ROW_ID = 0;
    public static final int COL_RELEVAMIENTODETALLE_RELEVAMIENTOID = 1;
    public static final int COL_RELEVAMIENTODETALLE_NOMBRE = 2;
    public static final int COL_RELEVAMIENTODETALLE_TIPONEGOCIOID = 3;
    public static final int COL_RELEVAMIENTODETALLE_CATEGORIAID = 4;
    public static final int COL_RELEVAMIENTODETALLE_FOTOSIZE = 5;
    public static final int COL_RELEVAMIENTODETALLE_FOTOBINARY = 6;
    public static final int COL_RELEVAMIENTODETALLE_LATITUD = 7;
    public static final int COL_RELEVAMIENTODETALLE_LONGITUD = 8;
    public static final int COL_RELEVAMIENTODETALLE_SINCRO = 9;
    
    public static final String[] RELEVAMIENTODETALLE_ALL_KEYS = new String[] {
    		KEY_RELEVAMIENTODETALLE_ROW_ID, KEY_RELEVAMIENTODETALLE_RELEVAMIENTOID, KEY_RELEVAMIENTODETALLE_NOMBRE,
    		KEY_RELEVAMIENTODETALLE_TIPONEGOCIOID, KEY_RELEVAMIENTODETALLE_CATEGORIAID, KEY_RELEVAMIENTODETALLE_FOTOSIZE,
    		KEY_RELEVAMIENTODETALLE_FOTOBINARY, KEY_RELEVAMIENTODETALLE_LATITUD, KEY_RELEVAMIENTODETALLE_LONGITUD,
    		KEY_RELEVAMIENTODETALLE_SINCRO
    	};
    
    public static final String RELEVAMIENTODETALLE_TABLE_NAME = "tbl_RelevamientoDetalle";
    
    public static final String RELEVAMIENTODETALLE_TABLE_CREATE = "CREATE TABLE " + RELEVAMIENTODETALLE_TABLE_NAME + "("
    		+ KEY_RELEVAMIENTODETALLE_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
    		+ KEY_RELEVAMIENTODETALLE_RELEVAMIENTOID + " integer NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_NOMBRE + " text NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_TIPONEGOCIOID + " integer NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_CATEGORIAID + " text NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_FOTOSIZE + " integer NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_FOTOBINARY + " blob NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_LATITUD + " double NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_LONGITUD + " double NOT NULL, "
    		+ KEY_RELEVAMIENTODETALLE_SINCRO + " integer NOT NULL);";
    
    public boolean borrarRelevamientoDetallePor(long id)
    {
      String str = "_Id=" + id;
      return this.db.delete(RELEVAMIENTODETALLE_TABLE_NAME, str, null) > 0;
    }
    
    public void borrarRelevamientosDetalle()
    {
    	Cursor localCursor = obtenerRelevamientosDetalle();
    	long l = localCursor.getColumnIndexOrThrow("_Id");
    	if (localCursor.moveToFirst()) 
    	{
    		do
    		{
    			borrarRelevamientoDetallePor(localCursor.getLong((int)l));
    		} 
    		while (localCursor.moveToNext());
    	}
    	localCursor.close();
    }
    
    public long insertarRelevamientoDetalle(int relevamientoId, String nombre, int tipoNegocioId, String categoriaId, int fotoSize, 
    		byte[] fotoBinary, double latitud, double longitud, int sincro)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_relevamientoId", relevamientoId);
      localContentValues.put("_nombre", nombre);
      localContentValues.put("_tipoNegocioId", tipoNegocioId);
      localContentValues.put("_categoriaId", categoriaId);
      localContentValues.put("_fotoSize", fotoSize);
      localContentValues.put("_fotoBinary", fotoBinary);
      localContentValues.put("_latitud", latitud);
      localContentValues.put("_longitud", longitud);
      localContentValues.put("_sincro", sincro);
      return this.db.insert(RELEVAMIENTODETALLE_TABLE_NAME, null, localContentValues);
    }
    
    public Cursor obtenerRelevamientosDetalle()
    {
      Cursor localCursor = this.db.query(true,RELEVAMIENTODETALLE_TABLE_NAME, RELEVAMIENTODETALLE_ALL_KEYS, null, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public Cursor obtenerRelevamientosDetalleNoSincro()
    {
    	String query = "_sincro = 0";
      Cursor localCursor = this.db.query(true,RELEVAMIENTODETALLE_TABLE_NAME, RELEVAMIENTODETALLE_ALL_KEYS, query, null, null, null, null, null);
      if (localCursor != null) 
      {
        localCursor.moveToFirst();
      }
      return localCursor;
    }
    
    public int ModificarRelevamientoDetalleSincro(int rowId, int sincro)
    {
      String str = "_Id=" + rowId;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_sincro", sincro);
      return this.db.update(RELEVAMIENTODETALLE_TABLE_NAME, localContentValues, str, null);
    }
    
    //CLIENTEDATOSVENTA//
    public static final String KEY_CLIENTEDATOSVENTA_ROW_ID = "_id";
    public static final String KEY_CLIENTEDATOSVENTA_CLIENTEID = "_clienteId";
    public static final String KEY_CLIENTEDATOSVENTA_PROMEDIOSEMANAL = "_promedioSemanal";
    public static final String KEY_CLIENTEDATOSVENTA_INTENSIDADCOMPRA = "_intensidadCompra";
    public static final String KEY_CLIENTEDATOSVENTA_ULTIMACOMPRAFECHA = "_ultimaCompraFecha";
    public static final String KEY_CLIENTEDATOSVENTA_ULTIMACOMPRAMONTO = "_ultimaCompraMonto";
    public static final String KEY_CLIENTEDATOSVENTA_ESCADOPREVENTAFECHA = "_escadoPreventaFecha";
    public static final String KEY_CLIENTEDATOSVENTA_ESCADOPREVENTA = "_escadoPreventa";
    public static final String KEY_CLIENTEDATOSVENTA_ESCADOVENTAFECHA = "_escadoVentaFecha";
    public static final String KEY_CLIENTEDATOSVENTA_ESCADOVENTA = "_escadoVenta";

    public static final int COL_CLIENTEDATOSVENTA_ROW_ID = 0;
    public static final int COL_CLIENTEDATOSVENTA_CLIENTEID = 1;
    public static final int COL_CLIENTEDATOSVENTA_PROMEDIOSEMANAL = 2;
    public static final int COL_CLIENTEDATOSVENTA_INTENSIDADCOMPRA = 3;
    public static final int COL_CLIENTEDATOSVENTA_ULTIMACOMPRAFECHA = 4;
    public static final int COL_CLIENTEDATOSVENTA_ULTIMACOMPRAMONTO = 5;
    public static final int COL_CLIENTEDATOSVENTA_ESCADOPREVENTAFECHA = 6;
    public static final int COL_CLIENTEDATOSVENTA_ESCADOPREVENTA = 7;
    public static final int COL_CLIENTEDATOSVENTA_ESCADOVENTAFECHA = 8;
    public static final int COL_CLIENTEDATOSVENTA_ESCADOVENTA = 9;

    public static final String[] CLIENTEDATOSVENTA_ALL_KEYS = {
            KEY_CLIENTEDATOSVENTA_ROW_ID,
            KEY_CLIENTEDATOSVENTA_CLIENTEID,
            KEY_CLIENTEDATOSVENTA_PROMEDIOSEMANAL,
            KEY_CLIENTEDATOSVENTA_INTENSIDADCOMPRA,
            KEY_CLIENTEDATOSVENTA_ULTIMACOMPRAFECHA,
            KEY_CLIENTEDATOSVENTA_ULTIMACOMPRAMONTO,
            KEY_CLIENTEDATOSVENTA_ESCADOPREVENTAFECHA,
            KEY_CLIENTEDATOSVENTA_ESCADOPREVENTA,
            KEY_CLIENTEDATOSVENTA_ESCADOVENTAFECHA,
            KEY_CLIENTEDATOSVENTA_ESCADOVENTA
    };

    public static final String CLIENTEDATOSVENTA_TABLE_NAME = "tbl_ClienteDatosVenta";

    public static final String CLIENTEDATOSVENTA_TABLE_CREATE = "CREATE TABLE " + CLIENTEDATOSVENTA_TABLE_NAME + "("
            + KEY_CLIENTEDATOSVENTA_ROW_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + KEY_CLIENTEDATOSVENTA_CLIENTEID + " integer NOT NULL, "
            + KEY_CLIENTEDATOSVENTA_PROMEDIOSEMANAL + " float NULL, "
            + KEY_CLIENTEDATOSVENTA_INTENSIDADCOMPRA + " integer NULL, "
            + KEY_CLIENTEDATOSVENTA_ULTIMACOMPRAFECHA + " text NULL, "
            + KEY_CLIENTEDATOSVENTA_ULTIMACOMPRAMONTO + " float NULL, "
            + KEY_CLIENTEDATOSVENTA_ESCADOPREVENTAFECHA + " text NULL, "
            + KEY_CLIENTEDATOSVENTA_ESCADOPREVENTA + " text NULL, "
            + KEY_CLIENTEDATOSVENTA_ESCADOVENTAFECHA + " text NULL, "
            + KEY_CLIENTEDATOSVENTA_ESCADOVENTA + " text NULL);";

    public boolean BorrarClienteDatosVentaPor(long id) {
        String str = "_id = " + id;
        return this.db.delete(CLIENTEDATOSVENTA_TABLE_NAME, str, null) > 0;
    }

    public void BorrarClienteDatosVentas() {
        Cursor localCursor = ObtenerClienteDatosVentas();
        long l = localCursor.getColumnIndexOrThrow("_id");
        if (localCursor.moveToFirst()) {
            do {
                BorrarClienteDatosVentaPor(localCursor.getLong((int) l));
            }
            while (localCursor.moveToNext());
        }
        localCursor.close();
    }

    public Cursor ObtenerClienteDatosVentaPor(
            int clienteId
    ) {
        String str = "_clienteId = " + clienteId;
        Cursor localCursor = this.db.query(true, CLIENTEDATOSVENTA_TABLE_NAME, CLIENTEDATOSVENTA_ALL_KEYS, str, null, null, null, null, null);
        if (localCursor != null) {
            localCursor.moveToFirst();
        }
        return localCursor;
    }

    public Cursor ObtenerClienteDatosVentas() {
        Cursor localCursor = this.db.query(true, CLIENTEDATOSVENTA_TABLE_NAME, CLIENTEDATOSVENTA_ALL_KEYS, null, null, null, null, null, null);
        if (localCursor != null) {
            localCursor.moveToFirst();
        }
        return localCursor;
    }

    public long InsertarClienteDatosVenta(List<ClienteDatosVentaWS> clienteDatosVenta) {
		SQLiteStatement stmt = db.compileStatement(
				"INSERT INTO tbl_ClienteDatosVenta(_clienteId, _promedioSemanal, _intensidadCompra, _ultimaCompraFecha, _ultimaCompraMonto, _escadoPreventaFecha, " +
						"_escadoPreventa, _escadoVentaFecha, _escadoVenta) VALUES (?,?,?,?,?,?,?,?,?)"
		);
		try {
			db.beginTransaction();
			for (ClienteDatosVentaWS item: clienteDatosVenta) {

				stmt.bindLong(1,item.getClienteId());
				stmt.bindDouble(2,item.getPromedioSemanal());
				stmt.bindLong(3,item.getIntensidadCompra());
				stmt.bindString(4,ObtenerFechaStringFromJson(item.getUltimaCompraFecha()));
				stmt.bindDouble(5,item.getUltimaCompraMonto());
				stmt.bindString(6,ObtenerFechaStringFromJson(item.getEscadoPreventaFecha()));
				stmt.bindString(7,item.getEscadoPreventa());
				stmt.bindString(8,ObtenerFechaStringFromJson(item.getEscadoVentaFecha()));
				stmt.bindString(9,item.getEscadoVenta());

				stmt.executeInsert();
				stmt.clearBindings();
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return 1;
		}catch(Exception localException){
			if(db.inTransaction()){
				db.endTransaction();
			}
			return 0;
		}
    }

    public long ModificarClienteDatosVenta(
            int id,
            int clienteId,
            float promedioSemanal,
            int intensidadCompra,
            String ultimaCompraFecha,
            float ultimaCompraMonto,
            String escadoPreventaFecha,
            String escadoPreventa,
            String escadoVentaFecha,
            String escadoVenta
    ) {
        String condicion = KEY_CLIENTEDATOSVENTA_ROW_ID + " = " + id;

        ContentValues localContentValues = new ContentValues();

        localContentValues.put("clienteId", clienteId);
        localContentValues.put("promedioSemanal", promedioSemanal);
        localContentValues.put("intensidadCompra", intensidadCompra);
        localContentValues.put("ultimaCompraFecha", ultimaCompraFecha);
        localContentValues.put("ultimaCompraMonto", ultimaCompraMonto);
        localContentValues.put("escadoPreventaFecha", escadoPreventaFecha);
        localContentValues.put("escadoPreventa", escadoPreventa);
        localContentValues.put("escadoVentaFecha", escadoVentaFecha);
        localContentValues.put("escadoVenta", escadoVenta);

        return this.db.update(CLIENTEDATOSVENTA_TABLE_NAME, localContentValues, condicion, null);
    }
}