package Clases;

public class Cliente 
{
	private int _id;
	private int _clienteId;
	private String _codigo;
	private String _nombres;
	private String _paterno;
	private String _materno;
	private String _apellidoCasada;
	private String _nombreCompleto;
	private boolean _tieneCi;
	private String _ci;
	private String _expedidoId;
	private boolean _tieneCelular;
	private String _celular;
	private int _tipoCalleId;
	private String _direccion;
	private String _numero;
	private String _referencia;
	private int _entreTipoCalle1Id;
	private String _calle1;
	private int _entreTipoCalle2Id;
	private String _calle2;
	private String _edificio;
	private String _edificioPiso;
	private String _edificioNumero;
	private String _manzano;
	private String _uv;	
	private String _nombreFactura;
	private String _nit;
	private String _razonSocial;
	private String _contacto;
	private boolean _tieneTelefono;
	private String _telefono;
	private String _paginaWeb;
	private String _email;
	private int _tipoNegocioId;
	private int _zonaId;
	private double _latitud;
	private double _longitud;
	private int _creadorId;
	private double _latitudCreador;
	private double _longitudCreador;
	private int _tipoPagoId;
	private int _diasPago;
	private float _topeCredito;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _hora;
	private int _minuto;
	private boolean _verificado;
	private boolean _completo;
	private boolean _sincronizacion;
	private int _rutaId;
	private int _rutaDiaId;
	private int _mercadoId;
	private boolean _activo;
	private boolean _editado;
	private int _tatId;
	private String _tipoNit;
	private boolean _clienteVisita;
	private int _zonaMercadoId;
	private int _a;
	private int _b;
	private int _c;
	private int _d;
	private int _e;
	private int _f;
	private int _g;
	private int _h;
	private int _i;
	private int _j;
	private int _k;
	private int _l;
	private int _m;
	private int _n;
	private int _o;
	private int _p;
	private int _q;
	private int _r;
	private float _secuenciaPreventa;
	private float _secuenciaVenta;
	private int _provinciaId;
	private int _precioListaNombreId;
	private String _ruta;
	private String _tipoVisita;
	private int _zonaVentaId;
	private int _canalRutaId;
	
	public Cliente(){}

	public Cliente(int _id, int _clienteId, String _codigo, String _nombres,
			String _paterno, String _materno, String _apellidoCasada,
			String _nombreCompleto, boolean _tieneCi, String _ci,
			String _expedidoId, boolean _tieneCelular, String _celular,
			int _tipoCalleId, String _direccion, String _numero,
			String _referencia, int _entreTipoCalle1Id, String _calle1,
			int _entreTipoCalle2Id, String _calle2, String _edificio,
			String _edificioPiso, String _edificioNumero, String _manzano,
			String _uv, String _nombreFactura, String _nit,
			String _razonSocial, String _contacto, boolean _tieneTelefono,
			String _telefono, String _paginaWeb, String _email,
			int _tipoNegocioId, int _zonaId,double _latitud, double _longitud, 
			int _creadorId,	double _latitudCreador, double _longitudCreador,
			int _tipoPagoId,int _diasPago, float _topeCredito, int _dia, 
			int _mes, int _anio,int _hora, int _minuto, boolean _verificado,
			boolean _completo,boolean _sincronizacion,int _rutaId,int _rutaDiaId,
			int _mercadoId,boolean _activo,boolean _editado,int _tatId,String _tipoNit,
			boolean _clienteVisita,int _zonaMercadoId,int _a,int _b,int _c,int _d,int _e,
			int _f,int _g,int _h,int _i,int _j,int _k,int _l,int _m,int _n,int _o,int _p,
			int _q,int _r,float _secuenciaPreventa,float _secuenciaVenta,int _provinciaId,
			int _precioListaNombreId,String _ruta,String _tipoVisita,int _zonaVentaId, int _canalRutaId) 
	{
		super();
		this._id = _id;
		this._clienteId = _clienteId;
		this._codigo = _codigo;
		this._nombres = _nombres;
		this._paterno = _paterno;
		this._materno = _materno;
		this._apellidoCasada = _apellidoCasada;
		this._nombreCompleto = _nombreCompleto;
		this._tieneCi = _tieneCi;
		this._ci = _ci;
		this._expedidoId = _expedidoId;
		this._tieneCelular = _tieneCelular;
		this._celular = _celular;
		this._tipoCalleId = _tipoCalleId;
		this._direccion = _direccion;
		this._numero = _numero;
		this._referencia = _referencia;
		this._entreTipoCalle1Id = _entreTipoCalle1Id;
		this._calle1 = _calle1;
		this._entreTipoCalle2Id = _entreTipoCalle2Id;
		this._calle2 = _calle2;
		this._edificio = _edificio;
		this._edificioPiso = _edificioPiso;
		this._edificioNumero = _edificioNumero;
		this._manzano = _manzano;
		this._uv = _uv;
		this._nombreFactura = _nombreFactura;
		this._nit = _nit;
		this._razonSocial = _razonSocial;
		this._contacto = _contacto;
		this._tieneTelefono = _tieneTelefono;
		this._telefono = _telefono;
		this._paginaWeb = _paginaWeb;
		this._email = _email;
		this._tipoNegocioId = _tipoNegocioId;
		this._zonaId = _zonaId;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._creadorId = _creadorId;
		this._latitudCreador = _latitudCreador;
		this._longitudCreador = _longitudCreador;
		this._tipoPagoId = _tipoPagoId;
		this._diasPago = _diasPago;
		this._topeCredito = _topeCredito;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._hora = _hora;
		this._minuto = _minuto;
		this._verificado = _verificado;
		this._completo = _completo;
		this._sincronizacion = _sincronizacion;
		this._rutaId = _rutaId;
		this._rutaDiaId = _rutaDiaId;
		this._mercadoId = _mercadoId;
		this._activo = _activo;
		this._editado = _editado;
		this._tatId = _tatId;
		this._tipoNit = _tipoNit;
		this._clienteVisita = _clienteVisita;
		this._zonaMercadoId = _zonaMercadoId;
		this._a = _a;
		this._b = _b;
		this._c = _c;
		this._d = _d;
		this._e = _e;
		this._f = _f;
		this._g = _g;
		this._h = _h;
		this._i = _i;
		this._j = _j;
		this._k = _k;
		this._l = _l;
		this._m = _m;
		this._n = _n;
		this._o = _o;
		this._p = _p;
		this._q = _q;
		this._r = _r;
		this._secuenciaPreventa = _secuenciaPreventa;
		this._secuenciaVenta = _secuenciaVenta;
		this._provinciaId = _provinciaId;
		this._precioListaNombreId = _precioListaNombreId;
		this._ruta = _ruta;
		this._tipoVisita = _tipoVisita;
		this._zonaVentaId = _zonaVentaId;
		this._canalRutaId = _canalRutaId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public String get_codigo() {
		return _codigo;
	}

	public void set_codigo(String _codigo) {
		this._codigo = _codigo;
	}

	public String get_nombres() {
		return _nombres;
	}

	public void set_nombres(String _nombres) {
		this._nombres = _nombres;
	}

	public String get_paterno() {
		return _paterno;
	}

	public void set_paterno(String _paterno) {
		this._paterno = _paterno;
	}

	public String get_materno() {
		return _materno;
	}

	public void set_materno(String _materno) {
		this._materno = _materno;
	}

	public String get_apellidoCasada() {
		return _apellidoCasada;
	}

	public void set_apellidoCasada(String _apellidoCasada) {
		this._apellidoCasada = _apellidoCasada;
	}

	public String get_nombreCompleto() {
		return _nombreCompleto;
	}

	public void set_nombreCompleto(String _nombreCompleto) {
		this._nombreCompleto = _nombreCompleto;
	}

	public boolean is_tieneCi() {
		return _tieneCi;
	}

	public void set_tieneCi(boolean _tieneCi) {
		this._tieneCi = _tieneCi;
	}

	public String get_ci() {
		return _ci;
	}

	public void set_ci(String _ci) {
		this._ci = _ci;
	}

	public String get_expedidoId() {
		return _expedidoId;
	}

	public void set_expedidoId(String _expedidoId) {
		this._expedidoId = _expedidoId;
	}

	public boolean is_tieneCelular() {
		return _tieneCelular;
	}

	public void set_tieneCelular(boolean _tieneCelular) {
		this._tieneCelular = _tieneCelular;
	}

	public String get_celular() {
		return _celular;
	}

	public void set_celular(String _celular) {
		this._celular = _celular;
	}

	public int get_tipoCalleId() {
		return _tipoCalleId;
	}

	public void set_tipoCalleId(int _tipoCalleId) {
		this._tipoCalleId = _tipoCalleId;
	}

	public String get_direccion() {
		return _direccion;
	}

	public void set_direccion(String _direccion) {
		this._direccion = _direccion;
	}

	public String get_numero() {
		return _numero;
	}

	public void set_numero(String _numero) {
		this._numero = _numero;
	}

	public String get_referencia() {
		return _referencia;
	}

	public void set_referencia(String _referencia) {
		this._referencia = _referencia;
	}

	public int get_entreTipoCalle1Id() {
		return _entreTipoCalle1Id;
	}

	public void set_entreTipoCalle1Id(int _entreTipoCalle1Id) {
		this._entreTipoCalle1Id = _entreTipoCalle1Id;
	}

	public String get_calle1() {
		return _calle1;
	}

	public void set_calle1(String _calle1) {
		this._calle1 = _calle1;
	}

	public int get_entreTipoCalle2Id() {
		return _entreTipoCalle2Id;
	}

	public void set_entreTipoCalle2Id(int _entreTipoCalle2Id) {
		this._entreTipoCalle2Id = _entreTipoCalle2Id;
	}

	public String get_calle2() {
		return _calle2;
	}

	public void set_calle2(String _calle2) {
		this._calle2 = _calle2;
	}

	public String get_edificio() {
		return _edificio;
	}

	public void set_edificio(String _edificio) {
		this._edificio = _edificio;
	}

	public String get_edificioPiso() {
		return _edificioPiso;
	}

	public void set_edificioPiso(String _edificioPiso) {
		this._edificioPiso = _edificioPiso;
	}

	public String get_edificioNumero() {
		return _edificioNumero;
	}

	public void set_edificioNumero(String _edificioNumero) {
		this._edificioNumero = _edificioNumero;
	}

	public String get_manzano() {
		return _manzano;
	}

	public void set_manzano(String _manzano) {
		this._manzano = _manzano;
	}

	public String get_uv() {
		return _uv;
	}

	public void set_uv(String _uv) {
		this._uv = _uv;
	}

	public String get_nombreFactura() {
		return _nombreFactura;
	}

	public void set_nombreFactura(String _nombreFactura) {
		this._nombreFactura = _nombreFactura;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public String get_razonSocial() {
		return _razonSocial;
	}

	public void set_razonSocial(String _razonSocial) {
		this._razonSocial = _razonSocial;
	}

	public String get_contacto() {
		return _contacto;
	}

	public void set_contacto(String _contacto) {
		this._contacto = _contacto;
	}

	public boolean is_tieneTelefono() {
		return _tieneTelefono;
	}

	public void set_tieneTelefono(boolean _tieneTelefono) {
		this._tieneTelefono = _tieneTelefono;
	}

	public String get_telefono() {
		return _telefono;
	}

	public void set_telefono(String _telefono) {
		this._telefono = _telefono;
	}

	public String get_paginaWeb() {
		return _paginaWeb;
	}

	public void set_paginaWeb(String _paginaWeb) {
		this._paginaWeb = _paginaWeb;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public int get_tipoNegocioId() {
		return _tipoNegocioId;
	}

	public void set_tipoNegocioId(int _tipoNegocioId) {
		this._tipoNegocioId = _tipoNegocioId;
	}

	public int get_zonaId() {
		return _zonaId;
	}

	public void set_zonaId(int _zonaId) {
		this._zonaId = _zonaId;
	}

	public double get_latitud() {
		return _latitud;
	}

	public void set_latitud(double _latitud) {
		this._latitud = _latitud;
	}

	public double get_longitud() {
		return _longitud;
	}

	public void set_longitud(double _longitud) {
		this._longitud = _longitud;
	}

	public int get_creadorId() {
		return _creadorId;
	}

	public void set_creadorId(int _creadorId) {
		this._creadorId = _creadorId;
	}

	public double get_latitudCreador() {
		return _latitudCreador;
	}

	public void set_latitudCreador(double _latitudCreador) {
		this._latitudCreador = _latitudCreador;
	}

	public double get_longitudCreador() {
		return _longitudCreador;
	}

	public void set_longitudCreador(double _longitudCreador) {
		this._longitudCreador = _longitudCreador;
	}

	public int get_tipoPagoId() {
		return _tipoPagoId;
	}

	public void set_tipoPagoId(int _tipoPagoId) {
		this._tipoPagoId = _tipoPagoId;
	}

	public int get_diasPago() {
		return _diasPago;
	}

	public void set_diasPago(int _diasPago) {
		this._diasPago = _diasPago;
	}

	public float get_topeCredito() {
		return _topeCredito;
	}

	public void set_topeCredito(float _topeCredito) {
		this._topeCredito = _topeCredito;
	}

	public int get_dia() {
		return _dia;
	}

	public void set_dia(int _dia) {
		this._dia = _dia;
	}

	public int get_mes() {
		return _mes;
	}

	public void set_mes(int _mes) {
		this._mes = _mes;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
	}

	public int get_hora() {
		return _hora;
	}

	public void set_hora(int _hora) {
		this._hora = _hora;
	}

	public int get_minuto() {
		return _minuto;
	}

	public void set_minuto(int _minuto) {
		this._minuto = _minuto;
	}

	public boolean is_verificado() {
		return _verificado;
	}

	public void set_verificado(boolean _verificado) {
		this._verificado = _verificado;
	}

	public boolean is_completo() {
		return _completo;
	}

	public void set_completo(boolean _completo) {
		this._completo = _completo;
	}

	public boolean is_sincronizacion() {
		return _sincronizacion;
	}

	public void set_sincronizacion(boolean _sincronizacion) {
		this._sincronizacion = _sincronizacion;
	}

	public int get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(int _rutaId) {
		this._rutaId = _rutaId;
	}

	public int get_rutaDiaId() {
		return _rutaDiaId;
	}

	public void set_rutaDiaId(int _rutaDiaId) {
		this._rutaDiaId = _rutaDiaId;
	}

	public int get_mercadoId() {
		return _mercadoId;
	}

	public void set_mercadoId(int _mercadoId) {
		this._mercadoId = _mercadoId;
	}

	public boolean is_activo() {
		return _activo;
	}

	public void set_activo(boolean _activo) {
		this._activo = _activo;
	}

	public boolean is_editado() {
		return _editado;
	}

	public void set_editado(boolean _editado) {
		this._editado = _editado;
	}

	public int get_tatId() {
		return _tatId;
	}

	public void set_tatId(int _tatId) {
		this._tatId = _tatId;
	}

	public String get_tipoNit() {
		return _tipoNit;
	}

	public void set_tipoNit(String _tipoNit) {
		this._tipoNit = _tipoNit;
	}

	public boolean is_clienteVisita() {
		return _clienteVisita;
	}

	public void set_clienteVisita(boolean _clienteVisita) {
		this._clienteVisita = _clienteVisita;
	}

	public int get_zonaMercadoId() {
		return _zonaMercadoId;
	}

	public void set_zonaMercadoId(int _zonaMercadoId) {
		this._zonaMercadoId = _zonaMercadoId;
	}

	public int get_a() {
		return _a;
	}

	public void set_a(int _a) {
		this._a = _a;
	}

	public int get_b() {
		return _b;
	}

	public void set_b(int _b) {
		this._b = _b;
	}

	public int get_c() {
		return _c;
	}

	public void set_c(int _c) {
		this._c = _c;
	}

	public int get_d() {
		return _d;
	}

	public void set_d(int _d) {
		this._d = _d;
	}

	public int get_e() {
		return _e;
	}

	public void set_e(int _e) {
		this._e = _e;
	}

	public int get_f() {
		return _f;
	}

	public void set_f(int _f) {
		this._f = _f;
	}

	public int get_g() {
		return _g;
	}

	public void set_g(int _g) {
		this._g = _g;
	}

	public int get_h() {
		return _h;
	}

	public void set_h(int _h) {
		this._h = _h;
	}

	public int get_i() {
		return _i;
	}

	public void set_i(int _i) {
		this._i = _i;
	}

	public int get_j() {
		return _j;
	}

	public void set_j(int _j) {
		this._j = _j;
	}

	public int get_k() {
		return _k;
	}

	public void set_k(int _k) {
		this._k = _k;
	}

	public int get_l() {
		return _l;
	}

	public void set_l(int _l) {
		this._l = _l;
	}

	public int get_m() {
		return _m;
	}

	public void set_m(int _m) {
		this._m = _m;
	}

	public int get_n() {
		return _n;
	}

	public void set_n(int _n) {
		this._n = _n;
	}

	public int get_o() {
		return _o;
	}

	public void set_o(int _o) {
		this._o = _o;
	}

	public int get_p() {
		return _p;
	}

	public void set_p(int _p) {
		this._p = _p;
	}

	public int get_q() {
		return _q;
	}

	public void set_q(int _q) {
		this._q = _q;
	}

	public int get_r() {
		return _r;
	}

	public void set_r(int _r) {
		this._r = _r;
	}

	public float get_secuenciaPreventa() {
		return _secuenciaPreventa;
	}

	public void set_secuenciaPreventa(int _secuenciaPreventa) {
		this._secuenciaPreventa = _secuenciaPreventa;
	}
	
	public float get_secuenciaVenta() {
		return _secuenciaVenta;
	}

	public void set_secuenciaVenta(int _secuenciaVenta) {
		this._secuenciaVenta = _secuenciaVenta;
	}

	public int get_provinciaId() {
		return _provinciaId;
	}

	public void set_provinciaId(int _provinciaId) {
		this._provinciaId = _provinciaId;
	}

	public int get_precioListaNombreId() {
		return _precioListaNombreId;
	}

	public void set_precioListaNombreId(int _precioListaNombreId) {
		this._precioListaNombreId = _precioListaNombreId;
	}

	public String get_ruta() {
		return _ruta;
	}

	public void set_ruta(String _ruta) {
		this._ruta = _ruta;
	}

	public String get_tipoVisita() {
		return _tipoVisita;
	}

	public void set_tipoVisita(String _tipoVisita) {
		this._tipoVisita = _tipoVisita;
	}

	public int get_zonaVentaId() {
		return _zonaVentaId;
	}

	public void set_zonaVentaId(int _zonaVentaId) {
		this._zonaVentaId = _zonaVentaId;
	}

	public void set_secuenciaPreventa(float _secuenciaPreventa) {
		this._secuenciaPreventa = _secuenciaPreventa;
	}

	public void set_secuenciaVenta(float _secuenciaVenta) {
		this._secuenciaVenta = _secuenciaVenta;
	}

	public int get_canalRutaId() {
		return _canalRutaId;
	}

	public void set_canalRutaId(int _canalRutaId) {
		this._canalRutaId = _canalRutaId;
	}
}
