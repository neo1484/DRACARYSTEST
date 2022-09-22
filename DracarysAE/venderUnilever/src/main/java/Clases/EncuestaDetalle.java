package Clases;

public class EncuestaDetalle 
{
	private int _detalleId;
	private int _encuestaId;
	private String _pregunta;
    private String _definicion;
    private String _tipoPregunta;
    private String _especificacion;
    private boolean _estado;
    private int _orden;
    private String _tipoEspecificacion;
    
    public EncuestaDetalle(){}

	public EncuestaDetalle(int _detalleId, int _encuestaId, String _pregunta, String _definicion, String _tipoPregunta,
			String _especificacion, boolean _estado, int _orden, String _tipoEspecificacion) 
	{
		super();
		this._detalleId = _detalleId;
		this._encuestaId = _encuestaId;
		this._pregunta = _pregunta;
		this._definicion = _definicion;
		this._tipoPregunta = _tipoPregunta;
		this._especificacion = _especificacion;
		this._estado = _estado;
		this._orden = _orden;
		this._tipoEspecificacion = _tipoEspecificacion;
	}

	public int get_detalleId() {
		return _detalleId;
	}

	public void set_detalleId(int _detalleId) {
		this._detalleId = _detalleId;
	}

	public int get_encuestaId() {
		return _encuestaId;
	}

	public void set_encuestaId(int _encuestaId) {
		this._encuestaId = _encuestaId;
	}

	public String get_pregunta() {
		return _pregunta;
	}

	public void set_pregunta(String _pregunta) {
		this._pregunta = _pregunta;
	}

	public String get_definicion() {
		return _definicion;
	}

	public void set_definicion(String _definicion) {
		this._definicion = _definicion;
	}

	public String get_tipoPregunta() {
		return _tipoPregunta;
	}

	public void set_tipoPregunta(String _tipoPregunta) {
		this._tipoPregunta = _tipoPregunta;
	}

	public String get_especificacion() {
		return _especificacion;
	}

	public void set_especificacion(String _especificacion) {
		this._especificacion = _especificacion;
	}

	public boolean is_estado() {
		return _estado;
	}

	public void set_estado(boolean _estado) {
		this._estado = _estado;
	}

	public int get_orden() {
		return _orden;
	}

	public void set_orden(int _orden) {
		this._orden = _orden;
	}

	public String get_tipoEspecificacion() {
		return _tipoEspecificacion;
	}

	public void set_tipoEspecificacion(String _tipoEspecificacion) {
		this._tipoEspecificacion = _tipoEspecificacion;
	}
}
