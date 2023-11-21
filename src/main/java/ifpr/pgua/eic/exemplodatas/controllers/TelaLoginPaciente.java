package ifpr.pgua.eic.exemplodatas.controllers;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAtendimento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioDisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPaciente;
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
    private RepositorioPaciente repositorioPaciente;
    private RepositorioMedico repositorioMedico;
    private RepositorioAgendamento repositorioAgendamento;
    private RepositorioDisponibilidadeMedico repositorioDisponibilidadeMedico;
    private RepositorioAtendimento repositorioAtendimento;

    public TelaLoginPaciente(RepositorioMedico repositorioMedico,RepositorioLogin repositorioLogin, RepositorioPaciente repositorioPaciente, RepositorioAgendamento repositorioAgendamento, RepositorioDisponibilidadeMedico repositorioDisponibilidadeMedico, RepositorioAtendimento repositorioAtendimento){
        this.repositorioMedico = repositorioMedico;
        this.repositorioLogin = repositorioLogin;
        this.repositorioPaciente = repositorioPaciente;
        this.repositorioAgendamento = repositorioAgendamento;
        this.repositorioDisponibilidadeMedico = repositorioDisponibilidadeMedico;
        this.repositorioAtendimento = repositorioAtendimento;
    }

    @FXML
    void login(ActionEvent event){
        System.out.println("ok");

        String cpf = tfCpf.getText();
        String senha = tfSenha.getText();

        Resultado resultado = repositorioLogin.validarLoginPaciente(cpf, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            Resultado<Paciente> rs = repositorioPaciente.buscarPorCpf(cpf);
            Paciente paciente = rs.comoSucesso().getObj();

            App.pushScreen("TELAINICIALPACIENTE",o-> new TelaInicialPaciente(repositorioPaciente, repositorioLogin ,repositorioMedico, paciente, repositorioAgendamento, repositorioDisponibilidadeMedico, repositorioAtendimento));
        }

        alert.showAndWait();
    }

    @FXML
    void telaCadastro(ActionEvent event){
        App.pushScreen("PACIENTECRIANDOCADASTRO",o-> new PacienteCriandoCadastro(repositorioPaciente, repositorioLogin));
    }

}

