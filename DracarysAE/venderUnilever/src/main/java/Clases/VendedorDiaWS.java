package Clases;

public class VendedorDiaWS
{
    private int _vendedorId;
    private int _diaId;
    private String _username;
    private String _token;

    public VendedorDiaWS(){}

    public VendedorDiaWS(int _vendedorId, int _diaId, String _username, String _token) {
        this._vendedorId = _vendedorId;
        this._diaId = _diaId;
        this._username = _username;
        this._token = _token;
    }

    public int get_vendedorId() {
        return _vendedorId;
    }

    public void set_vendedorId(int _vendedorId) {
        this._vendedorId = _vendedorId;
    }

    public int get_diaId() {
        return _diaId;
    }

    public void set_diaId(int _diaId) {
        this._diaId = _diaId;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }
}
