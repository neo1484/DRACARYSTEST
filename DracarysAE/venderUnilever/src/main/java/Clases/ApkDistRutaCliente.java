package Clases;

public class ApkDistRutaCliente
{
	private int _rutaId;
	private int _diaId;
	private String _rutaNombre;
	private String _diaNombre;
	
	public ApkDistRutaCliente(){}

	public ApkDistRutaCliente(int _rutaId, int _diaId, String _rutaNombre,
			String _diaNombre) 
	{
		super();
		this._rutaId = _rutaId;
		this._diaId = _diaId;
		this._rutaNombre = _rutaNombre;
		this._diaNombre = _diaNombre;
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

	public String get_rutaNombre() {
		return _rutaNombre;
	}

	public void set_rutaNombre(String _rutaNombre) {
		this._rutaNombre = _rutaNombre;
	}

	public String get_diaNombre() {
		return _diaNombre;
	}

	public void set_diaNombre(String _diaNombre) {
		this._diaNombre = _diaNombre;
	}
	
	public String toString()
	{
	    return this._rutaNombre + " - " + this._diaNombre;
	}
}
