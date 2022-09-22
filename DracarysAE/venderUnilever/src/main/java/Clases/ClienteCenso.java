package Clases;

public class ClienteCenso 
{
	private int _id;
	private String _codigo;
	private String _referencia;
	private int _tipoNegocioIdVender;
	private String _tipoNegocio;
	private String _contacto;
	private double _latitud;
	private double _longitud;
	private String _nombres;
	private String _paterno;
	private int _creadorId;
	private double _latitudCreador;
	private double _longitudCreador;
	private int _zonaId;
	private int _rutaId;
	private int _diaId;
	private int _mercadoId;
	private int _diaCreacion;
	private int _mesCreacion;
	private int _anioCreacion;
	private int _estado;
	private int _clienteId;
	private boolean _sincro;
	private int _motivoEliminacionId;
	
	public ClienteCenso(){}

	public ClienteCenso(int _id, String _codigo, String _referencia, int _tipoNegocioIdVender,String _tipoNegocio, String _contacto,
			double _latitud, double _longitud, String _nombres, String _paterno, int _creadorId, double _latitudCreador,
			double _longitudCreador, int _zonaId, int _rutaId, int _diaId, int _mercadoId, int _diaCreacion,
			int _mesCreacion, int _anioCreacion, int _estado, int _clienteId, boolean _sincro,int _motivoEliminacionId) 
	{
		super();
		this._id = _id;
		this._codigo = _codigo;
		this._referencia = _referencia;
		this._tipoNegocioIdVender = _tipoNegocioIdVender;
		this._tipoNegocio = _tipoNegocio;
		this._contacto = _contacto;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._nombres = _nombres;
		this._paterno = _paterno;
		this._creadorId = _creadorId;
		this._latitudCreador = _latitudCreador;
		this._longitudCreador = _longitudCreador;
		this._zonaId = _zonaId;
		this._rutaId = _rutaId;
		this._diaId = _diaId;
		this._mercadoId = _mercadoId;
		this._diaCreacion = _diaCreacion;
		this._mesCreacion = _mesCreacion;
		this._anioCreacion = _anioCreacion;
		this._estado = _estado;
		this._clienteId = _clienteId;
		this._sincro = _sincro;
		this._motivoEliminacionId = _motivoEliminacionId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_codigo() {
		return _codigo;
	}

	public void set_codigo(String _codigo) {
		this._codigo = _codigo;
	}

	public String get_referencia() {
		return _referencia;
	}

	public void set_referencia(String _referencia) {
		this._referencia = _referencia;
	}

	public int get_tipoNegocioIdVender() {
		return _tipoNegocioIdVender;
	}

	public void set_tipoNegocioIdVender(int _tipoNegocioIdVender) {
		this._tipoNegocioIdVender = _tipoNegocioIdVender;
	}
	
	public String get_tipoNegocio() {
		return _tipoNegocio;
	}

	public void set_tipoNegocio(String _tipoNegocio) {
		this._tipoNegocio = _tipoNegocio;
	}

	public String get_contacto() {
		return _contacto;
	}

	public void set_contacto(String _contacto) {
		this._contacto = _contacto;
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

	public int get_zonaId() {
		return _zonaId;
	}

	public void set_zonaId(int _zonaId) {
		this._zonaId = _zonaId;
	}

	public int get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(int _rutaId) {
		this._rutaId = _rutaId;
	}

	public int get_diaId() {
		return _diaId;
	}

	public void set_diaId(int _diaId) {
		this._diaId = _diaId;
	}

	public int get_mercadoId() {
		return _mercadoId;
	}

	public void set_mercadoId(int _mercadoId) {
		this._mercadoId = _mercadoId;
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

	public int get_estado() {
		return _estado;
	}

	public void set_estado(int _estado) {
		this._estado = _estado;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public boolean is_sincro() {
		return _sincro;
	}

	public void set_sincro(boolean _sincro) {
		this._sincro = _sincro;
	}

	public int get_motivoEliminacionId() {
		return _motivoEliminacionId;
	}

	public void set_motivoEliminacionId(int _motivoEliminacionId) {
		this._motivoEliminacionId = _motivoEliminacionId;
	}
}
