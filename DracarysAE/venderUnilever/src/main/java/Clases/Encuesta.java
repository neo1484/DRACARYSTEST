package Clases;

public class Encuesta 
{
	private int _encuestaId;
    private String _nombre;
    private boolean _activa;
    
    public Encuesta(){}

	public Encuesta(int _encuestaId, String _nombre, boolean _activa) 
	{
		super();
		this._encuestaId = _encuestaId;
		this._nombre = _nombre;
		this._activa = _activa;
	}

	public int get_encuestaId() {
		return _encuestaId;
	}

	public void set_encuestaId(int _encuestaId) {
		this._encuestaId = _encuestaId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public boolean is_activa() {
		return _activa;
	}

	public void set_activa(boolean _activa) {
		this._activa = _activa;
	}

}
