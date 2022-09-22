package Clases;

public class Categoria 
{
	private int _id;
	private int _categoriaId;
	private String _nombre;
	private int _padreId;
	private int _hijos;
	private int _proveedorId;
	private int _jerarquia1Id;
	private int _jerarquia2Id;
	private int _jerarquia3Id;
	private int _jerarquia5Id;
	
	public Categoria(){}
	
	public Categoria(int _id,int _categoriaId, String _nombre, int _padreId,
			int _hijos, int _proveedorId, int _jerarquia1Id, int _jerarquia2Id,
			int _jerarquia3Id, int _jerarquia5Id)
	{
		super();
		this._id = _id;
		this._categoriaId = _categoriaId;
		this._nombre = _nombre;
		this._padreId = _padreId;
		this._hijos = _hijos;
		this._proveedorId = _proveedorId;
		this._jerarquia1Id = _jerarquia1Id;
		this._jerarquia2Id = _jerarquia2Id;
		this._jerarquia3Id = _jerarquia3Id;
		this._jerarquia5Id = _jerarquia5Id;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_categoriaId() {
		return _categoriaId;
	}

	public void set_categoriaId(int _categoriaId) {
		this._categoriaId = _categoriaId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public int get_padreId() {
		return _padreId;
	}

	public void set_padreId(int _padreId) {
		this._padreId = _padreId;
	}

	public int get_hijos() {
		return _hijos;
	}

	public void set_hijos(int _hijos) {
		this._hijos = _hijos;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}
	
	public String toString()
	{
		return this._nombre;
	}

	public int get_jerarquia1Id() {
		return _jerarquia1Id;
	}

	public void set_jerarquia1Id(int _jerarquia1Id) {
		this._jerarquia1Id = _jerarquia1Id;
	}

	public int get_jerarquia2Id() {
		return _jerarquia2Id;
	}

	public void set_jerarquia2Id(int _jerarquia2Id) {
		this._jerarquia2Id = _jerarquia2Id;
	}

	public int get_jerarquia3Id() {
		return _jerarquia3Id;
	}

	public void set_jerarquia3Id(int _jerarquia3Id) {
		this._jerarquia3Id = _jerarquia3Id;
	}

	public int get_jerarquia5Id() {
		return _jerarquia5Id;
	}

	public void set_jerarquia5Id(int _jerarquia5Id) {
		this._jerarquia5Id = _jerarquia5Id;
	}
}
