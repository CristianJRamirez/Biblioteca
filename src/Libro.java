/**
 * Created by 45858000w on 12/12/16.
 */
public class Libro {

    private int id;
    private String titulo;
    private int numEjemplares;
    private String Editorial;
    private int numPaginas;
    private int anyoEdicion;

    public Libro() {
    }

    public Libro(String titulo, int numEjemplares, String editorial, int numPaginas, int anyoEdicion) {
        this.titulo = titulo;
        this.numEjemplares = numEjemplares;
        Editorial = editorial;
        this.numPaginas = numPaginas;
        this.anyoEdicion = anyoEdicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumEjemplares() {
        return numEjemplares;
    }

    public void setNumEjemplares(int numEjemplares) {
        this.numEjemplares = numEjemplares;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String editorial) {
        Editorial = editorial;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public int getAnyoEdicion() {
        return anyoEdicion;
    }

    public void setAnyoEdicion(int anyoEdicion) {
        this.anyoEdicion = anyoEdicion;
    }
}
