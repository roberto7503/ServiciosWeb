package org.example.serviciosweb.controller;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.serviciosweb.model.Usuario;
import org.example.serviciosweb.service.UsuarioService;

import java.util.List;

public class UsuarioController {

    @FXML
    private Button btnConsultar;

    @FXML
    private Label lblEstado;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colId;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colUsername;

    @FXML
    private TableColumn<Usuario, String> colCorreo;

    @FXML
    private TableColumn<Usuario, String> colTelefono;

    @FXML
    private TableColumn<Usuario, String> colCiudad;

    @FXML
    private TableColumn<Usuario, String> colEmpresa;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
    }

    @FXML
    private void onConsultarUsuarios() {
        lblEstado.setText("Consultando API REST...");

        Task<List<Usuario>> task = new Task<>() {
            @Override
            protected List<Usuario> call() throws Exception {
                return usuarioService.obtenerUsuarios();
            }
        };

        btnConsultar.disableProperty().bind(task.runningProperty());

        task.setOnSucceeded(e -> {
            List<Usuario> usuarios = task.getValue();
            tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
            lblEstado.setText("Usuarios encontrados: " + usuarios.size());
        });

        task.setOnFailed(e -> {
            Throwable error = task.getException();
            String mensaje = (error != null && error.getMessage() != null)
                    ? error.getMessage()
                    : "Error al consultar la API";
            lblEstado.setText(mensaje);
        });

        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
    }
}