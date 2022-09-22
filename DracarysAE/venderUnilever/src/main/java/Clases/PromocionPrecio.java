package Clases;

public class PromocionPrecio 
{
	private int _precioId;
	private int _promocionId;
	private int _precioListaId;
	private float _precio;
	private float _precioOriginal;
	
	public PromocionPrecio(){}

	public PromocionPrecio(int _precioId,int _promocionId,int _precioListaId,float _precio,float _precioOriginal) 
	{
		super();		
		this._precioId = _precioId;
		this._promocionId = _promocionId;
		this._precioListaId = _precioListaId;
		this._precio = _precio;
		this._precioOriginal = _precioOriginal;
	}

	public int get_precioId() {
		return _precioId;
	}

	public void set_precioId(int _precioId) {
		this._precioId = _precioId;
	}
	
	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public int get_precioListaId() {
		return _precioListaId;
	}

	public void set_precioListaId(int _precioListaId) {
		this._precioListaId = _precioListaId;
	}

	public float get_precio() {
		return _precio;
	}

	public void set_precio(float _precio) {
		this._precio = _precio;
	}

	public float get_precioOriginal() {
		return _precioOriginal;
	}

	public void set_precioOriginal(float _precioOriginal) {
		this._precioOriginal = _precioOriginal;
	}
}
