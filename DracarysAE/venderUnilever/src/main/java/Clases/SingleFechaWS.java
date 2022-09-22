package Clases;

public class SingleFechaWS
{
    private int _dia;
    private int _mes;
    private int _anio;
    private String _username;
    private String _token;

    public SingleFechaWS(int _dia, int _mes, int _anio, String _username, String _token) {
        this._dia = _dia;
        this._mes = _mes;
        this._anio = _anio;
        this._username = _username;
        this._token = _token;
    }

    public int get_dia() {
        return _dia;
    }

    public void set_dia(int _dia) {
        this._dia = _dia;
    }

    public int get_mes() {
        return _mes;
    }

    public void set_mes(int _mes) {
        this._mes = _mes;
    }

    public int get_anio() {
        return _anio;
    }

    public void set_anio(int _anio) {
        this._anio = _anio;
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
