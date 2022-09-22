package Clases;

public class FotoCategoria 
{
	private int _categoriaId;
	private String _descripcion;
	
	public FotoCategoria(){}

	public int get_categoriaId() {
		return _categoriaId;
	}

	public FotoCategoria(int _categoriaId, String _descripcion) {
		super();
		this._categoriaId = _categoriaId;
		this._descripcion = _descripcion;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
	
	public String toString(){
		return this._descripcion;
	}
}
