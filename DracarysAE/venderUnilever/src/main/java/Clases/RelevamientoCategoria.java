package Clases;

public class RelevamientoCategoria 
{
	private int _categoriaId;
	private String _categoria;
	
	public RelevamientoCategoria(){}

	public RelevamientoCategoria(int _categoriaId, String _categoria) {
		super();
		this._categoriaId = _categoriaId;
		this._categoria = _categoria;
	}

	public int get_categoriaId() {
		return _categoriaId;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public String get_categoria() {
		return _categoria;
	}

	public void set_categoria(String _categoria) {
		this._categoria = _categoria;
	}
	
	public String toString() { return this._categoria; }
}
