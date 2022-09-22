package Clases;

public class AlmacenProducto 
{
	private int _almacenId;
	private int _productoId;
	private int _saldoUnitario;
	private int _saldoPaquete;
	private float _costoUnitario;
	private float _costoPaquete;
	
	public AlmacenProducto(){}
	
	public AlmacenProducto(int _almacenId, int _productoId, int _saldoUnitario,
			int _saldoPaquete, float _costoUnitario, float _costoPaquete) 
	{
		super();
		this._almacenId = _almacenId;
		this._productoId = _productoId;
		this._saldoUnitario = _saldoUnitario;
		this._saldoPaquete = _saldoPaquete;
		this._costoUnitario = _costoUnitario;
		this._costoPaquete = _costoPaquete;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public int get_saldoUnitario() {
		return _saldoUnitario;
	}

	public void set_saldoUnitario(int _saldoUnitario) {
		this._saldoUnitario = _saldoUnitario;
	}

	public int get_saldoPaquete() {
		return _saldoPaquete;
	}

	public void set_saldoPaquete(int _saldoPaquete) {
		this._saldoPaquete = _saldoPaquete;
	}

	public float get_costoUnitario() {
		return _costoUnitario;
	}

	public void set_costoUnitario(float _costoUnitario) {
		this._costoUnitario = _costoUnitario;
	}

	public float get_costoPaquete() {
		return _costoPaquete;
	}

	public void set_costoPaquete(float _costoPaquete) {
		this._costoPaquete = _costoPaquete;
	}
}
