import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    public TextField fechafinPrestado;
    public TableView tblLibrosPrestados;
    //endregion

    private ObservableList datosLibros;
    private ObservableList datosSocios;
    private ObservableList datosPrestamos;
    private ObservableList datosPrestados;
    private Program pr;

    //region Controladores
@FXML
//endregion



    public void initialize() {
        pr= new Program();
        cargarDatos();

    }

    public void cargarDatos()    {
        cargarDatosLibro();
        cargarDatosSocio();
        cargarDatosPrestamos();

/*
        for (int i = 0; i < datosLibros.size(); i++) {
            System.out.println(((Libro)datosLibros.get(i)).getTitulo());
        }
        for (int i = 0; i < datosSocios.size(); i++) {
            System.out.println(((Socio)datosSocios.get(i)).getNombre());
        }
        for (int i = 0; i < datosPrestamos.size(); i++) {
            System.out.println(((Prestamo)datosPrestamos.get(i)).getId());
        }
        */
    }


    //region Consultar
    public void BuscarTituloConsultaLibro(ActionEvent actionEvent) {
        pr.buscarLibroTitulo(txtTituloConsulta.getText());
    }

    public void BuscarAnyoConsultaLibro(ActionEvent actionEvent) {
        pr.buscarLibroAnyo(Integer.parseInt(String.valueOf(txtAnyoConsulta.getText())));
    }

    public void BuscarNombreConsultaSocio(ActionEvent actionEvent) {
        pr.buscarSocioNombre(txtNombreConsulta.getText());
    }

    public void BuscarEdadConsultaSocio(ActionEvent actionEvent) {
        pr.buscarSocioEdat(Integer.parseInt(txtEdadConsulta.getText()));

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
        //pr.listaLibrosSocio();
        //pr.listaLibrosFecha();

        List<Libro> libros =getLibros();
        List<Socio> socios =getSocios();
        List<Prestamo> prestamos = buscarPrestados();


        TableColumn tituloLibro = new TableColumn<>("tituloLibro");
        tituloLibro.setCellValueFactory(new PropertyValueFactory("tituloLibro"));

        TableColumn numEjemplares = new TableColumn<>("numEjemplares");
        numEjemplares.setCellValueFactory(new PropertyValueFactory("numEjemplares"));

        TableColumn nombreSocio = new TableColumn<>("nombreSocio");
        nombreSocio.setCellValueFactory(new PropertyValueFactory("nombreSocio"));

        TableColumn edad = new TableColumn<>("edad");
        edad.setCellValueFactory(new PropertyValueFactory("edad"));


        tblLibrosPrestados.getColumns().clear();
        tblLibrosPrestados.getColumns().addAll(nombreSocio,tituloLibro, numEjemplares, edad);

        datosPrestados = FXCollections.observableArrayList();

        ArrayList<Prestado> prestados= new ArrayList<>();
        for (Prestamo prestamo: prestamos)
        {
            //System.out.println("P"+prestamo.toString());
            Prestado prest = new Prestado();
            for (Libro lib:libros) {
                if (prestamo.getIdLibro()==lib.getId())
                {
                    prest.setTituloLibro(lib.getTitulo());
                    prest.setNumEjemplares(lib.getNumEjemplares());
                }
            }
            for (Socio soc:socios) {
                if (prestamo.getIdSocio()==soc.getId())
                {
                    prest.setNombreSocio(soc.getNombre());
                    prest.setEdad(soc.getEdad());
                }
            }
            prestados.add(new Prestado(prest.getNombreSocio(),prest.getTituloLibro(),prest.getNumEjemplares(),prest.getEdad()));
        }


        for (Prestado pres: prestados)
        {

            System.out.println("PRESTADO -->"+pres.toString());
            datosPrestados.add(new Prestado(pres.getNombreSocio(),pres.getTituloLibro(),pres.getNumEjemplares(),pres.getEdad()));   //(libro.getTitulo() , libro.getNumEjemplares() );
            //System.out.println(libro.getId()+"-"+ libro.getTitulo() +"-"+ libro.getNumEjemplares() +"-"+ libro.getEditorial() +"-"+ libro.getNumPaginas() +"-"+libro.getAnyoEdicion());
        }

        tblLibrosPrestados.setItems(datosPrestados);

        tblLibrosPrestados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    public ArrayList<Prestamo> buscarPrestados()
    {

        if (!fechafinPrestado.getText().isEmpty()) {
            Date fecha = pr.getFecha(fechafinPrestado.getText());
            ManagePrestamo MP = new ManagePrestamo();
            ArrayList<Prestamo> prestamos = new ArrayList<>();
            for (Iterator iterator =MP.listPrestamos().iterator(); iterator.hasNext(); )
            {
                Prestamo prestamo = (Prestamo) iterator.next();

                if (prestamo.getFechaFinal().before(fecha)) {
                    /*System.out.print("ID : " + prestamo.getId());
                    System.out.print("\tID Libro: " + prestamo.getIdLibro());
                    System.out.print("\tID Socio: " + prestamo.getIdSocio());
                    System.out.println("\tFecha Inicio: " + prestamo.getFechaInicio());
                    System.out.println("\tFecha Final: " + prestamo.getFechaFinal());*/
                    prestamos.add(prestamo);
                }

            }
            return prestamos;
        }

        return null;
    }

    public void TodosLibrosPrestados(ActionEvent actionEvent) {
        pr.listaTodosLibros();;
    }


    //endregion

    //region Prestamo
    public void AddPrestamo(ActionEvent actionEvent) {
        pr.añadirPrestamos(Integer.parseInt(String.valueOf(txtIdLibroPrestamo.getText())),Integer.parseInt(txtIdSocioPrestamo.getText()),pr.getFecha(txtFechaInicioPrestamo.getText()),pr.getFecha(txtFechaFinalPrestamo.getText()));
        cargarDatos();
    }


    public void FiltrarPRestamo(ActionEvent actionEvent) {
    }


    //endregion

    //region SOCIO
    public void AddSocio(ActionEvent actionEvent) {
        pr.añadirSocio(1, 0,txtNombreSocio.getText() , Integer.parseInt(txtEdadSocio.getText()) ,txtDireccionSocio.getText()  ,Integer.parseInt(txtTelefonoSocio.getText()));
        cargarDatos();

    }

    public void SaveSocio(ActionEvent actionEvent) {
        pr.añadirSocio(2, Integer.parseInt(txtIdSocio.getText()),txtNombreSocio.getText() , Integer.parseInt(txtEdadSocio.getText()) ,txtDireccionSocio.getText()  ,Integer.parseInt(txtTelefonoSocio.getText()));
        cargarDatos();

    }

    public void FiltrarSocio(ActionEvent actionEvent) {
    }
    //endregion

    //region Libro
    public void AddLibro(ActionEvent actionEvent) {
        
        pr.añadirLIbro(1, 0,txtTituloLibro.getText(), Integer.parseInt(txtEjemplaresLibro.getText()),txtEditorialLibro.getText(), Integer.parseInt(txtPaginasLibro.getText()), Integer.parseInt(txtAnyoLibro.getText()));
        cargarDatos();
        //añadirLIbro(int opcion, int id, String titulo, int numEjemplares, String editorial, int numPaginas, int anyoEdicion)
    }

    public void SaveLibro(ActionEvent actionEvent) {
        pr.añadirLIbro(2,Integer.parseInt(txtIdLibro.getText()),txtTituloLibro.getText(), Integer.parseInt(txtEjemplaresLibro.getText()),txtEditorialLibro.getText(), Integer.parseInt(txtPaginasLibro.getText()), Integer.parseInt(txtAnyoLibro.getText()));
        cargarDatos();
    }

    public void FiltroLibro(ActionEvent actionEvent)
    {

    }

    //endregion

    //region CARGAR DATOS -> OK

    public void cargarDatosLibro() {
        TableColumn id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn titulo = new TableColumn<>("titulo");
        titulo.setCellValueFactory(new PropertyValueFactory("titulo"));

        TableColumn numEjemplares = new TableColumn<>("numEjemplares");
        numEjemplares.setCellValueFactory(new PropertyValueFactory("numEjemplares"));

        TableColumn numPaginas = new TableColumn<>("numPaginas");
        numPaginas.setCellValueFactory(new PropertyValueFactory("numPaginas"));

        TableColumn anyoEdicion = new TableColumn<>("anyoEdicion");
        anyoEdicion.setCellValueFactory(new PropertyValueFactory("anyoEdicion"));
        tblLibro.getColumns().clear();
        tblLibro.getColumns().addAll(id,titulo, numEjemplares, numPaginas,anyoEdicion);

        datosLibros = FXCollections.observableArrayList();

        ManageLibro ML = new ManageLibro();

        List<Libro> libros =getLibros();

        for (Libro libro: libros)
        {
            datosLibros.add(new Libro(libro.getId(),libro.getTitulo() , libro.getNumEjemplares() , libro.getEditorial() , libro.getNumPaginas() ,libro.getAnyoEdicion() ));
            //System.out.println(libro.getId()+"-"+ libro.getTitulo() +"-"+ libro.getNumEjemplares() +"-"+ libro.getEditorial() +"-"+ libro.getNumPaginas() +"-"+libro.getAnyoEdicion());
        }

        tblLibro.setItems(datosLibros);

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
        TableColumn id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn nombre = new TableColumn<>("nombre");
        nombre.setCellValueFactory(new PropertyValueFactory("nombre"));

        TableColumn edad = new TableColumn<>("edad");
        edad.setCellValueFactory(new PropertyValueFactory("edad"));

        TableColumn direccion = new TableColumn<>("direccion");
        direccion.setCellValueFactory(new PropertyValueFactory("direccion"));

        TableColumn telefono = new TableColumn<>("telefono");
        telefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        tblSocio.getColumns().clear();
        tblSocio.getColumns().addAll(id,nombre,edad, direccion,telefono);


        datosSocios = FXCollections.observableArrayList();


        List<Socio> socios =getSocios();

        for (Socio socio: socios)
        {
            datosSocios.add(new Socio(socio.getId(),socio.getNombre(),socio.getEdad(),socio.getDireccion(),socio.getTelefono()));
            //System.out.println(socio.getId()+"-"+ socio.getNombre()+"-"+ socio.getEdad()+"-"+ socio.getDireccion()+"-"+ socio.getTelefono());
        }

        tblSocio.setItems(datosSocios);

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
        TableColumn id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn idSocio = new TableColumn<>("idSocio");
        idSocio.setCellValueFactory(new PropertyValueFactory("idSocio"));

        TableColumn idLibro = new TableColumn<>("idLibro");
            idLibro.setCellValueFactory(new PropertyValueFactory("idLibro"));

        TableColumn fechaInicio = new TableColumn<>("fechaInicio");
            fechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));

        TableColumn fechaFinal = new TableColumn<>("fechaFinal");
            fechaFinal.setCellValueFactory(new PropertyValueFactory("fechaFinal"));
        tblPrestamo.getColumns().clear();
        tblPrestamo.getColumns().addAll(id,idLibro,idSocio, fechaInicio,fechaFinal);

        datosPrestamos = FXCollections.observableArrayList();



        List<Prestamo> prestamos =getPrestamos();

        for (Prestamo prestamo: prestamos)
        {
            datosPrestamos.add(new Prestamo(prestamo.getId(),prestamo.getIdLibro(),prestamo.getIdSocio(),prestamo.getFechaInicio(),prestamo.getFechaFinal()));
            //System.out.println(prestamo.getId()+"-"+ prestamo.getIdLibro()+"-"+ prestamo.getIdSocio()+"-"+ prestamo.getFechaInicio()+"-"+ prestamo.getFechaFinal());

        }

        tblPrestamo.setItems(datosPrestamos);

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

    //region tablas -> OK
    public void selecionLibro(MouseEvent mouseEvent) {
        Libro libro =(Libro)tblLibro.getSelectionModel().getSelectedItem();

        txtIdLibro.setText(String.valueOf(libro.getId()));
        txtTituloLibro.setText(libro.getTitulo());
        txtEditorialLibro.setText(libro.getEditorial());
        txtEjemplaresLibro.setText(String.valueOf(libro.getNumEjemplares()));
        txtPaginasLibro.setText(String.valueOf(libro.getNumPaginas()));
        txtAnyoLibro.setText(String.valueOf(libro.getAnyoEdicion()));

    }

    public void selecionSocio(MouseEvent mouseEvent) {
        Socio socio =(Socio)tblSocio.getSelectionModel().getSelectedItem();

        txtIdSocio.setText(String.valueOf(socio.getId()));
        txtNombreSocio.setText(socio.getNombre());
        txtEdadSocio.setText(String.valueOf(socio.getEdad()));
        txtDireccionSocio.setText(socio.getDireccion());
        txtTelefonoSocio.setText(String.valueOf(socio.getTelefono()));


    }

    public void selecionPrestamo(MouseEvent mouseEvent) {
        Prestamo prestamo =(Prestamo)tblPrestamo.getSelectionModel().getSelectedItem();

        txtIdPrestamo.setText(String.valueOf(prestamo.getId()));
        txtIdLibroPrestamo.setText(String.valueOf(prestamo.getIdLibro()));
        txtIdSocioPrestamo.setText(String.valueOf(prestamo.getIdSocio()));
        txtFechaInicioPrestamo.setText(String.valueOf(prestamo.getFechaInicio()));
        txtFechaFinalPrestamo.setText(String.valueOf(prestamo.getFechaFinal()));

    }

    //endregion
}

    /*
    TODO:
        boton de filtrar, :libro, socio, prestamos
        mostrar libros prestados y filtrar
        mostrar socios prestamos y filtrar
        consultas motrar resultados

    */