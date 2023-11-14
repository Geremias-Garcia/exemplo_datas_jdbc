package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPaciente;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DadosPessoaisPaciente implements Initializable{

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
    private PasswordField tfSenhaAntiga;

    @FXML
    private PasswordField tfSenhaNova;

    @FXML
    private PasswordField tfSenhaNovaConfirma;

    private RepositorioLogin repositorioLogin;
    private RepositorioPaciente repositorioPaciente;
    private Paciente paciente;
    
    public DadosPessoaisPaciente(RepositorioLogin repositorioLogin, RepositorioPaciente repositorioPaciente, Paciente paciente) {
        this.repositorioLogin = repositorioLogin;
        this.repositorioPaciente = repositorioPaciente;
        this.paciente = paciente;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbGenero.getItems().addAll("Masculino", "Feminino");

        atualizar();
    }

    @FXML
    private void salvarAlteracoes(ActionEvent event){
        String nome = tfNome.getText();
        String novoCpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        LocalDate data = date.getValue();
        String genero = (String) cbGenero.getValue();

        Paciente pacienteNovo = new Paciente(paciente.getId(), nome, novoCpf, telefone, email, data, genero, true);
        Resultado resultado;

        Alert alert;

        if (!novoCpf.equals(paciente.getCpf())) {
            Resultado<Paciente> rs = repositorioPaciente.buscarPorCpf(novoCpf);

            Paciente pacienteRetorno = rs.comoSucesso().getObj();

            if(pacienteRetorno == null){
                resultado = repositorioPaciente.alterarDados(pacienteNovo);
                resultado = repositorioLogin.alterarCpf(paciente.getCpf(), novoCpf);
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }else{
                resultado = repositorioPaciente.alterarDados(pacienteNovo);
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }
        }else{
            resultado = repositorioPaciente.alterarDados(pacienteNovo);
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        atualizar();
        alert.showAndWait();
        System.out.println(paciente);
    }
    
    @FXML
    private void alterarSenha(ActionEvent event){
        String novaSenha = tfSenhaNova.getText();
        String novaSenhaConfirma = tfSenhaNovaConfirma.getText();

        System.out.println(novaSenha+" vs "+novaSenhaConfirma);

        Alert alert;

        if (novaSenha.equals(novaSenhaConfirma) && (!novaSenha.isEmpty() || !novaSenha.isBlank() || !novaSenhaConfirma.isEmpty() || !novaSenha.isBlank())) {
            Resultado resultado = repositorioLogin.validarLoginPaciente(paciente.getCpf(), tfSenhaAntiga.getText());

            if(resultado.foiErro()){
                alert = new Alert(AlertType.ERROR, "Senha antiga não compativel");
            }else{
                resultado = repositorioLogin.alterarSenha(paciente.getCpf(), tfSenhaNova.getText());

                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }
        }else {
            alert = new Alert(AlertType.ERROR, "As novas senhas não são compatíveis");
        }

        atualizar();
        alert.showAndWait();
    }

    private void atualizar(){
        Resultado<Paciente> resultado = repositorioPaciente.buscarPorId(paciente.getId());

        paciente = resultado.comoSucesso().getObj();

        if (resultado.foiSucesso()) {
            tfNome.setText(paciente.getNome());
            tfCpf.setText(paciente.getCpf());
            tfTelefone.setText(paciente.getTelefone());
            tfEmail.setText(paciente.getEmail());
            date.setValue(paciente.getDataNascimento());
            cbGenero.setValue(paciente.getGenero());
            
        }
    }

    @FXML
    private void voltar(ActionEvent event){
        App.popScreen();
    }
}
