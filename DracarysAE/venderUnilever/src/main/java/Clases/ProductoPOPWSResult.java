package Clases;

public class ProductoPOPWSResult
{
    private int ProductoId;
    private String Descripcion25;
    private int CategoriaId;
    private String CodigoBarra;

    public ProductoPOPWSResult(int productoId, String descripcion25, int categoriaId, String codigoBarra) {
        ProductoId = productoId;
        Descripcion25 = descripcion25;
        CategoriaId = categoriaId;
        CodigoBarra = codigoBarra;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public String getDescripcion25() {
        return Descripcion25;
    }

    public void setDescripcion25(String descripcion25) {
        Descripcion25 = descripcion25;
    }

    public int getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        CategoriaId = categoriaId;
    }

    public String getCodigoBarra() {
        return CodigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        CodigoBarra = codigoBarra;
    }
}
