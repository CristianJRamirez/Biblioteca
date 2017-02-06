/**
 * Created by 45858000w on 06/02/17.
 */
public class Prestado {

    private String nombreSocio;
    private String tituloLibro;
    private int numEjemplares;
    private int edad;

    public Prestado() {
    }

    public Prestado(String nombreSocio, String tituloLibro, int numEjemplares, int edad) {
        this.nombreSocio = nombreSocio;
        this.tituloLibro = tituloLibro;
        this.numEjemplares = numEjemplares;
        this.edad = edad;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public int getNumEjemplares() {
        return numEjemplares;
    }

    public void setNumEjemplares(int numEjemplares) {
        this.numEjemplares = numEjemplares;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Prestado{" +
                "nombreSocio='" + nombreSocio + '\'' +
                ", tituloLibro='" + tituloLibro + '\'' +
                ", numEjemplares=" + numEjemplares +
                ", edad=" + edad +
                '}';
    }
}
