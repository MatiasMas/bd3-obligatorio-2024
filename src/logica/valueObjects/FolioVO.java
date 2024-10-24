package logica.valueObjects;

public class FolioVO {
    private String codigo;
    private String caratula;
    private String paginas;

    public FolioVO(String codigo, String caratula, String paginas) {
        this.codigo = codigo;
        this.caratula = caratula;
        this.paginas = paginas;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCaratula() {
        return caratula;
    }

    public String getPaginas() {
        return paginas;
    }
}
