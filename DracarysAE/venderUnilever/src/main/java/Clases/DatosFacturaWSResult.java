package Clases;

public class DatosFacturaWSResult
{
    private String NombreEmpresa;
    private String Nit;

    public DatosFacturaWSResult(String nombreEmpresa, String nit) {
        NombreEmpresa = nombreEmpresa;
        Nit = nit;
    }

    public String getNombreEmpresa() {
        return NombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        NombreEmpresa = nombreEmpresa;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }
}
