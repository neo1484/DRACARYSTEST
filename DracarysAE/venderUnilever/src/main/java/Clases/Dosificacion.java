package Clases;

public class Dosificacion 
{
	private int _dosificacionId;
	private int _diaCreacion;
	private int _mesCreacion;
	private int _anioCreacion;
	private String _codigoAutorizacion;
	private int _cantidad;
	private String _numeroInicial;
	private String _numeroFinal;
	private int _diaLimiteEmision;
	private int _mesLimiteEmision;
	private int _anioLimiteEmision;
	private String _facturaTipoId;
	private int _almacenId;
	private boolean _activa;
	private boolean _bloqueada;
	private String _llaveDosificacion;
	private int _cantidadDisponible;
	private String _ley;
	private String _sfc;
	private String _sucursal;
	private String _mensaje1;
	private String _mensaje2;
	private String _actividad;
	
	public Dosificacion(){}
	
	public Dosificacion(int _dosificacionId, int _diaCreacion,
			int _mesCreacion, int _anioCreacion, String _codigoAutorizacion,
			int _cantidad, String _numeroInicial, String _numeroFinal,
			int _diaLimiteEmision, int _mesLimiteEmision,
			int _anioLimiteEmision, String _facturaTipoId, int _almacenId,
			boolean _activa, boolean _bloqueada, String _llaveDosificacion,
			int _cantidadDisponible, String _ley,String _sfc,String _sucursal,
			String _mensaje1,String _mensaje2,String _actividad) 
	{
		super();
		this._dosificacionId = _dosificacionId;
		this._diaCreacion = _diaCreacion;
		this._mesCreacion = _mesCreacion;
		this._anioCreacion = _anioCreacion;
		this._codigoAutorizacion = _codigoAutorizacion;
		this._cantidad = _cantidad;
		this._numeroInicial = _numeroInicial;
		this._numeroFinal = _numeroFinal;
		this._diaLimiteEmision = _diaLimiteEmision;
		this._mesLimiteEmision = _mesLimiteEmision;
		this._anioLimiteEmision = _anioLimiteEmision;
		this._facturaTipoId = _facturaTipoId;
		this._almacenId = _almacenId;
		this._activa = _activa;
		this._bloqueada = _bloqueada;
		this._llaveDosificacion = _llaveDosificacion;
		this._cantidadDisponible = _cantidadDisponible;
		this._ley = _ley;
		this._sfc = _sfc;
		this._sucursal = _sucursal;
		this._mensaje1 = _mensaje1;
		this._mensaje2 = _mensaje2;
		this._actividad = _actividad;
	}

	public int get_dosificacionId() {
		return _dosificacionId;
	}

	public void set_dosificacionId(int _dosificacionId) {
		this._dosificacionId = _dosificacionId;
	}

	public int get_diaCreacion() {
		return _diaCreacion;
	}

	public void set_diaCreacion(int _diaCreacion) {
		this._diaCreacion = _diaCreacion;
	}

	public int get_mesCreacion() {
		return _mesCreacion;
	}

	public void set_mesCreacion(int _mesCreacion) {
		this._mesCreacion = _mesCreacion;
	}

	public int get_anioCreacion() {
		return _anioCreacion;
	}

	public void set_anioCreacion(int _anioCreacion) {
		this._anioCreacion = _anioCreacion;
	}

	public String get_codigoAutorizacion() {
		return _codigoAutorizacion;
	}

	public void set_codigoAutorizacion(String _codigoAutorizacion) {
		this._codigoAutorizacion = _codigoAutorizacion;
	}

	public int get_cantidad() {
		return _cantidad;
	}

	public void set_cantidad(int _cantidad) {
		this._cantidad = _cantidad;
	}

	public String get_numeroInicial() {
		return _numeroInicial;
	}

	public void set_numeroInicial(String _numeroInicial) {
		this._numeroInicial = _numeroInicial;
	}

	public String get_numeroFinal() {
		return _numeroFinal;
	}

	public void set_numeroFinal(String _numeroFinal) {
		this._numeroFinal = _numeroFinal;
	}

	public int get_diaLimiteEmision() {
		return _diaLimiteEmision;
	}

	public void set_diaLimiteEmision(int _diaLimiteEmision) {
		this._diaLimiteEmision = _diaLimiteEmision;
	}

	public int get_mesLimiteEmision() {
		return _mesLimiteEmision;
	}

	public void set_mesLimiteEmision(int _mesLimiteEmision) {
		this._mesLimiteEmision = _mesLimiteEmision;
	}

	public int get_anioLimiteEmision() {
		return _anioLimiteEmision;
	}

	public void set_anioLimiteEmision(int _anioLimiteEmision) {
		this._anioLimiteEmision = _anioLimiteEmision;
	}

	public String get_facturaTipoId() {
		return _facturaTipoId;
	}

	public void set_facturaTipoId(String _facturaTipoId) {
		this._facturaTipoId = _facturaTipoId;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public boolean is_activa() {
		return _activa;
	}

	public void set_activa(boolean _activa) {
		this._activa = _activa;
	}

	public boolean is_bloqueada() {
		return _bloqueada;
	}

	public void set_bloqueada(boolean _bloqueada) {
		this._bloqueada = _bloqueada;
	}

	public String get_llaveDosificacion() {
		return _llaveDosificacion;
	}

	public void set_llaveDosificacion(String _llaveDosificacion) {
		this._llaveDosificacion = _llaveDosificacion;
	}

	public int get_cantidadDisponible() {
		return _cantidadDisponible;
	}

	public void set_cantidadDisponible(int _cantidadDisponible) {
		this._cantidadDisponible = _cantidadDisponible;
	}

	public String get_ley() {
		return _ley;
	}

	public void set_ley(String _ley) {
		this._ley = _ley;
	}

	public String get_sfc() {
		return _sfc;
	}

	public void set_sfc(String _sfc) {
		this._sfc = _sfc;
	}

	public String get_sucursal() {
		return _sucursal;
	}

	public void set_sucursal(String _sucursal) {
		this._sucursal = _sucursal;
	}

	public String get_mensaje1() {
		return _mensaje1;
	}

	public void set_mensaje1(String _mensaje1) {
		this._mensaje1 = _mensaje1;
	}

	public String get_mensaje2() {
		return _mensaje2;
	}

	public void set_mensaje2(String _mensaje2) {
		this._mensaje2 = _mensaje2;
	}

	public String get_actividad() {
		return _actividad;
	}

	public void set_actividad(String _actividad) {
		this._actividad = _actividad;
	}
}
