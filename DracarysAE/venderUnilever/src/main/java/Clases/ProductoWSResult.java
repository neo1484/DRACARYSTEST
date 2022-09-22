package Clases;

public class ProductoWSResult
{
    private int ProductoId;
    private String Codigo;
    private String Descripcion25;
    private String DescripcionUnidad25;
    private int ProveedorId;
    private int Conversion;
    private int CategoriaId;
    private boolean ProductoDePromocion;
    private int ProductoReferenciaId;

    public ProductoWSResult(int productoId, String codigo, String descripcion25, String descripcionUnidad25, int proveedorId, int conversion, int categoriaId, boolean productoDePromocion, int productoReferenciaId) {
        ProductoId = productoId;
        Codigo = codigo;
        Descripcion25 = descripcion25;
        DescripcionUnidad25 = descripcionUnidad25;
        ProveedorId = proveedorId;
        Conversion = conversion;
        CategoriaId = categoriaId;
        ProductoDePromocion = productoDePromocion;
        ProductoReferenciaId = productoReferenciaId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getDescripcion25() {
        return Descripcion25;
    }

    public void setDescripcion25(String descripcion25) {
        Descripcion25 = descripcion25;
    }

    public String getDescripcionUnidad25() {
        return DescripcionUnidad25;
    }

    public void setDescripcionUnidad25(String descripcionUnidad25) {
        DescripcionUnidad25 = descripcionUnidad25;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }

    public int getConversion() {
        return Conversion;
    }

    public void setConversion(int conversion) {
        Conversion = conversion;
    }

    public int getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        CategoriaId = categoriaId;
    }

    public boolean isProductoDePromocion() {
        return ProductoDePromocion;
    }

    public void setProductoDePromocion(boolean productoDePromocion) {
        ProductoDePromocion = productoDePromocion;
    }

    public int getProductoReferenciaId() {
        return ProductoReferenciaId;
    }

    public void setProductoReferenciaId(int productoReferenciaId) {
        ProductoReferenciaId = productoReferenciaId;
    }
}
