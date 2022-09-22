package Clases;

public class ProductoCambioWSResult
{
    private int ProductoId;
    private String Descripcion25;
    int ProveedorId;
    int CategoriaId;
    int Conversion;
    int PrecioId;
    float Precio;
    int CostoId;

    public ProductoCambioWSResult(int productoId, String descripcion25, int proveedorId, int categoriaId,
                                  int conversion, int precioId, float precio, int costoId) {
        ProductoId = productoId;
        Descripcion25 = descripcion25;
        ProveedorId = proveedorId;
        CategoriaId = categoriaId;
        Conversion = conversion;
        PrecioId = precioId;
        Precio = precio;
        CostoId = costoId;
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

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }

    public int getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        CategoriaId = categoriaId;
    }

    public int getConversion() {
        return Conversion;
    }

    public void setConversion(int conversion) {
        Conversion = conversion;
    }

    public int getPrecioId() {
        return PrecioId;
    }

    public void setPrecioId(int precioId) {
        PrecioId = precioId;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public int getCostoId() {
        return CostoId;
    }

    public void setCostoId(int costoId) {
        CostoId = costoId;
    }
}
