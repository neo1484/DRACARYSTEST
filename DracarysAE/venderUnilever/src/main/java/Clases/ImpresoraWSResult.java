package Clases;

public class ImpresoraWSResult
{
    private int ImpresoraId;
    private String Nombre;
    private String NroSerie;
    private String Marca;
    private String Modelo;
    private String Address;

    public ImpresoraWSResult(int impresoraId, String nombre, String nroSerie, String marca, String modelo,
                             String address) {
        ImpresoraId = impresoraId;
        Nombre = nombre;
        NroSerie = nroSerie;
        Marca = marca;
        Modelo = modelo;
        Address = address;
    }

    public int getImpresoraId() {
        return ImpresoraId;
    }

    public void setImpresoraId(int impresoraId) {
        ImpresoraId = impresoraId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNroSerie() {
        return NroSerie;
    }

    public void setNroSerie(String nroSerie) {
        NroSerie = nroSerie;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
