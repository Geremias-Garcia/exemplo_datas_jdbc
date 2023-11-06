package ifpr.pgua.eic.exemplodatas.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class TelaLoginMedico {

    @FXML
    private TextField tfCpf;
    
    @FXML
    private TextField tfSenha;  

    private RepositorioLogin repositorioLogin;
    private RepositorioPessoa repositorioPessoa;
    private RepositorioMedico repositorioMedico;
    private RepositorioAgendamento repositorioAgendamento;

    public TelaLoginMedico(RepositorioMedico repositorioMedico, RepositorioLogin repositorioLogin, RepositorioPessoa repositorioPessoa, RepositorioAgendamento repositorioAgendamento){
        this.repositorioMedico = repositorioMedico;
        this.repositorioLogin = repositorioLogin;
        this.repositorioPessoa = repositorioPessoa;
        this.repositorioAgendamento = repositorioAgendamento;
    }

    @FXML
    void login(ActionEvent event){
        System.out.println("ok");

        String cpf = tfCpf.getText();
        String senha = tfSenha.getText();

        Resultado resultado = repositorioLogin.validarLoginMedico(cpf, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            Resultado<Medico> rs = repositorioMedico.buscarPorCpf(cpf);
            Medico medico = rs.comoSucesso().getObj();

            System.out.println(medico);

            App.pushScreen("TELAINICIALMEDICO",o-> new TelaInicialMedico(repositorioAgendamento, repositorioMedico, repositorioPessoa, medico));
        }

        alert.showAndWait();
    }

}

