package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPaciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PacienteCriandoCadastro implements Initializable{

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFiltro;

    @FXML
    private TextField tfSenha;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<String> cbGenero;

    private RepositorioPaciente repositorioPaciente;
    private RepositorioLogin repositorioLogin;

    public PacienteCriandoCadastro(RepositorioPaciente repositorioPaciente, RepositorioLogin repositorioLogin) {
        this.repositorioPaciente = repositorioPaciente;
        this.repositorioLogin = repositorioLogin;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbGenero.getItems().addAll("Masculino", "Feminino");
    }

    @FXML
    private void criar(ActionEvent event){
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        LocalDate data = date.getValue();
        String genero = (String) cbGenero.getValue();
        String senha = tfSenha.getText();

        Paciente paciente = new Paciente(nome, cpf, telefone, email, data, genero, true);

        Resultado resultado = repositorioPaciente.criar(paciente);
        Resultado rs = repositorioLogin.criarLogin(cpf, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }

        if(rs.foiErro()){
            alert = new Alert(AlertType.ERROR, rs.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, rs.getMsg());
        }

        alert.showAndWait();
    }
    
    @FXML
    private void voltar(ActionEvent event){
        App.popScreen();
    }
}
