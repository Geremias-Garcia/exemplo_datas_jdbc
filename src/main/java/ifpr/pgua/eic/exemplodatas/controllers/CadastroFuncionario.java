package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioFuncionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadastroFuncionario implements Initializable{

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox cbGenero;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfSalario;

    @FXML
    private ComboBox cbSetor;

    private RepositorioFuncionario repositorioFuncionario;

    public CadastroFuncionario(RepositorioFuncionario repositorioFuncionario) {
        this.repositorioFuncionario = repositorioFuncionario;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbGenero.getItems().addAll("Masculino", "Feminino");
    }

    @FXML
    void cadastrar(ActionEvent event){
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        LocalDate data = date.getValue();
        String genero = (String) cbGenero.getValue();


        Pessoa pessoa = new Pessoa(nome, cpf, telefone, email, data, genero, true);


        System.out.println(pessoa);
    }

   
    

    
    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
    
}
