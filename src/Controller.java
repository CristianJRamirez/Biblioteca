import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;
import java.util.List;



public class Controller {

    //region Libro Parametros
    public TextArea txtIdLibro;
    public TextArea txtTituloLibro;
    public TextField txtEditorialLibro;
    public TextArea txtEjemplaresLibro;
    public TextField txtPaginasLibro;
    public TextField txtAnyoLibro;
    public TableView tblLibro;
    //endregion
    //region Socio Parametros
    public TextArea txtIdSocio;
    public TextArea txtNombreSocio;
    public TextField txtEdadSocio;
    public TextArea txtDireccionSocio;
    public TextField txtTelefonoSocio;
    public TableView tblSocio;
    //endregion
    //region Prestamo Parametros
    public TextArea txtIdPrestamo;
    public TextArea txtIdLibroPrestamo;
    public TextField txtIdSocioPrestamo;
    public TextArea txtFechaInicioPrestamo;
    public TextField txtFechaFinalPrestamo;
    public TableView tblPrestamo;
    //endregion
    //region Consulta Parametros
    public TextArea txtNombreConsulta;
    public TextField txtEdadConsulta;
    public TextField txtAnyoConsulta;
    public TextArea txtTituloConsulta;
    //endregion

    private Program pr;

    //region Controladores
@FXML
//endregion



    public void initialize() {
        pr= new Program();
        cargarDatos();

    }

    public void cargarDatos()
    {
        cargarDatosLibro();
        cargarDatosSocio();
        cargarDatosPrestamos();
    }


    //region Consultar
    public void BuscarTituloConsultaLibro(ActionEvent actionEvent) {
        pr.buscarLibroTitulo(txtTituloConsulta.getText());
    }

    public void BuscarAnyoConsultaLibro(ActionEvent actionEvent) {
        pr.buscarLibroAnyo(Integer.parseInt(0+txtAnyoConsulta.getText()));
    }

    public void BuscarNombreConsultaSocio(ActionEvent actionEvent) {
        pr.buscarSocioNombre(txtNombreConsulta.getText());
    }

    public void BuscarEdadConsultaSocio(ActionEvent actionEvent) {
        pr.buscarSocioEdat(Integer.parseInt(0+txtEdadConsulta.getText()));

    }

    //endregion

    //region SociosPrestmaos
    public void TodosSociosPrestamos(ActionEvent actionEvent) {
    }

    public void FiltrarSociosPrestamos(ActionEvent actionEvent) {
        pr.listaSocios();
    }
    //endregion

    //region Libros Prestados
    public void FiltrarLibrosPrestados(ActionEvent actionEvent) {
        pr.listaLibrosSocio();
        pr.listaLibrosFecha();
    }

    public void TodosLibrosPrestados(ActionEvent actionEvent) {
        pr.listaTodosLibros();;
    }
    //endregion

    //region Prestamo
    public void AddPrestamo(ActionEvent actionEvent) {
        pr.añadirPrestamos(Integer.parseInt(0+txtIdLibroPrestamo.getText()),Integer.parseInt(0+txtIdSocioPrestamo.getText()),pr.getFecha(txtFechaInicioPrestamo.getText()),pr.getFecha(txtFechaFinalPrestamo.getText()));
        cargarDatos();
    }



    public void FiltrarPRestamo(ActionEvent actionEvent) {
    }


    //endregion

    //region SOCIO
    public void AddSocio(ActionEvent actionEvent) {
        pr.añadirSocio(1, Integer.parseInt(0+txtIdSocio.getText()),txtNombreSocio.getText() , Integer.parseInt(0+txtEdadSocio.getText()) ,txtDireccionSocio.getText()  ,Integer.parseInt(0+txtTelefonoSocio.getText()));
        cargarDatos();

    }

    public void SaveSocio(ActionEvent actionEvent) {
        pr.añadirSocio(2, Integer.parseInt(0+txtIdSocio.getText()),txtNombreSocio.getText() , Integer.parseInt(0+txtEdadSocio.getText()) ,txtDireccionSocio.getText()  ,Integer.parseInt(0+txtTelefonoSocio.getText()));
        cargarDatos();

    }

    public void FiltrarSocio(ActionEvent actionEvent) {
    }
    //endregion

    //region Libro
    public void AddLibro(ActionEvent actionEvent) {
        
        pr.añadirLIbro(1, Integer.parseInt(0+txtIdLibro.getText()),txtTituloLibro.getText(), Integer.parseInt(0+txtEjemplaresLibro.getText()),txtEditorialLibro.getText(), Integer.parseInt(0+txtPaginasLibro.getText()), Integer.parseInt(0+txtAnyoLibro.getText()));
        cargarDatos();
        //añadirLIbro(int opcion, int id, String titulo, int numEjemplares, String editorial, int numPaginas, int anyoEdicion)
    }

    public void SaveLibro(ActionEvent actionEvent) {
        pr.añadirLIbro(2,Integer.parseInt(0+txtIdLibro.getText()),txtTituloLibro.getText(), Integer.parseInt(0+txtEjemplaresLibro.getText()),txtEditorialLibro.getText(), Integer.parseInt(0+txtPaginasLibro.getText()), Integer.parseInt(0+txtAnyoLibro.getText()));
        cargarDatos();
    }

    public void FiltroLibro(ActionEvent actionEvent)
    {

    }

    //endregion

    //region CARGAR DATOS

    public void cargarDatosLibro() {
        TableColumn titulo = new TableColumn<>("titulo");
        titulo.setCellValueFactory(new PropertyValueFactory("titulo"));

        TableColumn numEjemplares = new TableColumn<>("numEjemplares");
        numEjemplares.setCellValueFactory(new PropertyValueFactory("numEjemplares"));

        TableColumn numPaginas = new TableColumn<>("numPaginas");
        numPaginas.setCellValueFactory(new PropertyValueFactory("numPaginas"));

        TableColumn anyoEdicion = new TableColumn<>("anyoEdicion");
        anyoEdicion.setCellValueFactory(new PropertyValueFactory("anyoEdicion"));

        tblLibro.getColumns().addAll(titulo, numEjemplares, numPaginas,anyoEdicion);

        final ObservableList datos = FXCollections.observableArrayList();

        ManageLibro ML = new ManageLibro();

        List<Libro> libros =getLibros();

        for (Libro libro: libros)
        {
            datos.add(new Libro(libro.getTitulo() , libro.getNumEjemplares() , libro.getEditorial() , libro.getNumPaginas() ,libro.getAnyoEdicion() ));

        }

        tblLibro.setItems(datos);

        tblLibro.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public List<Libro> getLibros()
    {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Libro> libros=null;

            tx = session.beginTransaction();

            libros = session.createQuery("FROM Libro").list();

            tx.commit();
            return libros;
    }

    private void cargarDatosSocio() {

        TableColumn nombre = new TableColumn<>("nombre");
        nombre.setCellValueFactory(new PropertyValueFactory("nombre"));

        TableColumn edad = new TableColumn<>("edad");
        edad.setCellValueFactory(new PropertyValueFactory("edad"));

        TableColumn direccion = new TableColumn<>("direccion");
        direccion.setCellValueFactory(new PropertyValueFactory("direccion"));

        TableColumn telefono = new TableColumn<>("telefono");
        telefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        tblSocio.getColumns().addAll(nombre,edad, direccion,telefono);

        final ObservableList datos = FXCollections.observableArrayList();


        List<Socio> socios =getSocios();

        for (Socio socio: socios)
        {
            datos.add(new Socio(socio.getNombre(),socio.getEdad(),socio.getDireccion(),socio.getTelefono()));
        }

        tblSocio.setItems(datos);

        tblSocio.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    public List<Socio> getSocios()
    {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Socio> socios=null;

        tx = session.beginTransaction();

        socios = session.createQuery("FROM Socio").list();

        tx.commit();
        return socios;
    }

    private void cargarDatosPrestamos() {

        TableColumn idSocio = new TableColumn<>("idSocio");
        idSocio.setCellValueFactory(new PropertyValueFactory("idSocio"));

        TableColumn idLibro = new TableColumn<>("idLibro");
            idLibro.setCellValueFactory(new PropertyValueFactory("idLibro"));

        TableColumn fechaInicio = new TableColumn<>("fechaInicio");
            fechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));

        TableColumn fechaFinal = new TableColumn<>("fechaFinal");
            fechaFinal.setCellValueFactory(new PropertyValueFactory("fechaFinal"));

        tblPrestamo.getColumns().addAll(idLibro,idSocio, fechaInicio,fechaFinal);

        final ObservableList datos = FXCollections.observableArrayList();



        List<Prestamo> prestamos =getPrestamos();

        for (Prestamo prestamo: prestamos)
        {
            datos.add(new Prestamo(prestamo.getIdLibro(),prestamo.getIdSocio(),prestamo.getFechaInicio(),prestamo.getFechaFinal()));

        }

        tblPrestamo.setItems(datos);

        tblPrestamo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public List<Prestamo> getPrestamos()
    {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List<Prestamo> prestamso=null;

        tx = session.beginTransaction();

        prestamso = session.createQuery("FROM Prestamo").list();

        tx.commit();
        return prestamso;
    }

    //endregion

}
