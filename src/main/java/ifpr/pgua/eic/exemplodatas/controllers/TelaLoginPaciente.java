package ifpr.pgua.eic.exemplodatas.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class TelaLoginPaciente {

    @FXML
    private TextField tfCpf;
    
    @FXML
    private TextField tfSenha;  

    private RepositorioLogin repositorioLogin;
    private RepositorioPessoa repositorioPessoa;

    public TelaLoginPaciente(RepositorioLogin repositorioLogin, RepositorioPessoa repositorioPessoa){
        this.repositorioLogin = repositorioLogin;
        this.repositorioPessoa = repositorioPessoa;
    }

    @FXML
    void login(ActionEvent event){
        System.out.println("ok");

        String cpf = tfCpf.getText();
        String senha = tfSenha.getText();

        Resultado resultado = repositorioLogin.validar(cpf, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            Resultado<Pessoa> rs = repositorioPessoa.buscarPorCpf(cpf);
            Pessoa pessoa = rs.comoSucesso().getObj();
            System.out.println(pessoa);

            App.pushScreen("TELAINICIALPACIENTE",o-> new TelaInicialPaciente(pessoa));
        }

        alert.showAndWait();
    }

}

