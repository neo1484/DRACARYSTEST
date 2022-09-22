package Clases;

public class CanalRutaPrecio 
{
	private int _canalPrecioRutaId;
	private int _canalRutaId;
	private int _productoId;
	private float _hpb;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	
	public CanalRutaPrecio(){}
	
	public CanalRutaPrecio(int _canalPrecioRutaId, int _canalRutaId, int _productoId, float _hpb, float _descuentoCanal,
			float _descuentoAjuste) 
	{
		super();
		this._canalPrecioRutaId = _canalPrecioRutaId;
		this._canalRutaId = _canalRutaId;
		this._productoId = _productoId;
		this._hpb = _hpb;
		this._descuentoCanal = _descuentoCanal;
		this._descuentoAjuste = _descuentoAjuste;
	}

	public int get_canalPrecioRutaId() {
		return _canalPrecioRutaId;
	}

	public void set_canalPrecioRutaId(int _canalPrecioRutaId) {
		this._canalPrecioRutaId = _canalPrecioRutaId;
	}

	public int get_canalRutaId() {
		return _canalRutaId;
	}

	public void set_canalRutaId(int _canalRutaId) {
		this._canalRutaId = _canalRutaId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public float get_hpb() {
		return _hpb;
	}

	public void set_hpb(float _hpb) {
		this._hpb = _hpb;
	}

	public float get_descuentoCanal() {
		return _descuentoCanal;
	}

	public void set_descuentoCanal(float _descuentoCanal) {
		this._descuentoCanal = _descuentoCanal;
	}

	public float get_descuentoAjuste() {
		return _descuentoAjuste;
	}

	public void set_descuentoAjuste(float _descuentoAjuste) {
		this._descuentoAjuste = _descuentoAjuste;
	}
}
