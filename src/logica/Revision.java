package logica;

public class Revision {
    private int numero;         
    private String descripcion; 

    public Revision(int numero, String descripcion) {
        this.numero = numero;           
        this.descripcion = descripcion; 
    }

    public int getNumero() {
        return numero; 
    }

    public String getDescripcion() {
        return descripcion;
    }
}
