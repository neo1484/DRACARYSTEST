package Clases;

public class LoginEmpleado 
{
	private int _empleadoId;
	private String _empleadoNombre;
	private String _empleadoUsuario;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _estado;
	private String _mensaje;
	private int _almacenId;
	private int _vendedorRutaId;
	private String _imei;
	private String _password;
	private boolean _modificarClienteApk;
	private String _token;

	public LoginEmpleado(){}

	public LoginEmpleado(int _empleadoId, String _empleadoNombre,
			String _empleadoUsuario, int _dia, int _mes, int _anio,
			int _estado, String _mensaje, int _almacenId,
			int _vendedorRutaId, String _imei, String _password,
			boolean _modificarClienteApk, String _token) {
		super();
		this._empleadoId = _empleadoId;
		this._empleadoNombre = _empleadoNombre;
		this._empleadoUsuario = _empleadoUsuario;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._estado = _estado;
		this._mensaje = _mensaje;
		this._almacenId = _almacenId;
		this._vendedorRutaId = _vendedorRutaId;
		this._imei = _imei;
		this._password = _password;
		this._modificarClienteApk = _modificarClienteApk;
		this._token = _token;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public String get_empleadoNombre() {
		return _empleadoNombre;
	}

	public void set_empleadoNombre(String _empleadoNombre) {
		this._empleadoNombre = _empleadoNombre;
	}

	public String get_empleadoUsuario() {
		return _empleadoUsuario;
	}

	public void set_empleadoUsuario(String _empleadoUsuario) {
		this._empleadoUsuario = _empleadoUsuario;
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

	public int get_estado() {
		return _estado;
	}

	public void set_estado(int _estado) {
		this._estado = _estado;
	}

	public String get_mensaje() {
		return _mensaje;
	}

	public void set_mensaje(String _mensaje) {
		this._mensaje = _mensaje;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public int get_vendedorRutaId() {
		return _vendedorRutaId;
	}

	public void set_vendedorRutaId(int _vendedorRutaId) {
		this._vendedorRutaId = _vendedorRutaId;
	}

	public String get_imei() {
		return _imei;
	}

	public void set_imei(String _imei) {
		this._imei = _imei;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public boolean is_modificarClienteApk() {
		return _modificarClienteApk;
	}

	public void set_modificarClienteApk(boolean _modificarClienteApk) {
		this._modificarClienteApk = _modificarClienteApk;
	}

	public String get_token() {
		return _token;
	}

	public void set_token(String _token) {
		this._token = _token;
	}
}
