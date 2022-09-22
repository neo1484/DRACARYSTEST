package Clases;

public class Impresora 
{
	private int _impresoraId;
	private String _nombre;
	private String _nroSerie;
	private String _marca;
	private String _modelo;
	private String _address;
	
	public Impresora(){}

	public Impresora(int _impresoraId, String _nombre, String _nroSerie,
			String _marca, String _modelo,String _address) 
	{
		super();
		this._impresoraId = _impresoraId;
		this._nombre = _nombre;
		this._nroSerie = _nroSerie;
		this._marca = _marca;
		this._modelo = _modelo;
		this._address = _address;
	}

	public int get_impresoraId() {
		return _impresoraId;
	}

	public void set_impresoraId(int _impresoraId) {
		this._impresoraId = _impresoraId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public String get_nroSerie() {
		return _nroSerie;
	}

	public void set_nroSerie(String _nroSerie) {
		this._nroSerie = _nroSerie;
	}

	public String get_marca() {
		return _marca;
	}

	public void set_marca(String _marca) {
		this._marca = _marca;
	}

	public String get_modelo() {
		return _modelo;
	}

	public void set_modelo(String _modelo) {
		this._modelo = _modelo;
	}

	public String get_address() {
		return _address;
	}

	public void set_address(String _address) {
		this._address = _address;
	}
}
