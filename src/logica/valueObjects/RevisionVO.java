package logica.valueObjects;

public class RevisionVO {
    private int numero;
    private String descripcion;
    private String codFolio;

    public RevisionVO(int numero, String descripcion, String codFolio) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.codFolio = codFolio;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getCodigoFolio() {
        return this.codFolio;
    }
}