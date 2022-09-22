package Clases;

public class EncuestaDetalleWSResult
{
    private int DetalleId;
    private int EncuestaId;
    private String Pregunta;
    private String Definicion;
    private String TipoPregunta;
    private String Especificar;
    private boolean Estado;
    private int Orden;
    private String TipoEspecificacion;

    public EncuestaDetalleWSResult(int detalleId, int encuestaId, String pregunta, String definicion, String tipoPregunta, String especificar, boolean estado, int orden, String tipoEspecificacion) {
        DetalleId = detalleId;
        EncuestaId = encuestaId;
        Pregunta = pregunta;
        Definicion = definicion;
        TipoPregunta = tipoPregunta;
        Especificar = especificar;
        Estado = estado;
        Orden = orden;
        TipoEspecificacion = tipoEspecificacion;
    }

    public int getDetalleId() {
        return DetalleId;
    }

    public void setDetalleId(int detalleId) {
        DetalleId = detalleId;
    }

    public int getEncuestaId() {
        return EncuestaId;
    }

    public void setEncuestaId(int encuestaId) {
        EncuestaId = encuestaId;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public String getDefinicion() {
        return Definicion;
    }

    public void setDefinicion(String definicion) {
        Definicion = definicion;
    }

    public String getTipoPregunta() {
        return TipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        TipoPregunta = tipoPregunta;
    }

    public String getEspecificar() {
        return Especificar;
    }

    public void setEspecificar(String especificar) {
        Especificar = especificar;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public int getOrden() {
        return Orden;
    }

    public void setOrden(int orden) {
        Orden = orden;
    }

    public String getTipoEspecificacion() {
        return TipoEspecificacion;
    }

    public void setTipoEspecificacion(String tipoEspecificacion) {
        TipoEspecificacion = tipoEspecificacion;
    }
}
