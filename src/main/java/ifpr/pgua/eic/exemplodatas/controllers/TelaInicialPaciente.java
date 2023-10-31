package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TelaInicialPaciente implements Initializable{

    @FXML
    private Label bemVindo;

    private Pessoa pessoa;
    private RepositorioMedico repositorioMedico;
    private RepositorioAgendamento repositorioAgendamento;

    public TelaInicialPaciente(RepositorioMedico repositorioMedico, Pessoa pessoa, RepositorioAgendamento repositorioAgendamento){
        this.repositorioMedico = repositorioMedico;
        this.pessoa = pessoa;
        this.repositorioAgendamento = repositorioAgendamento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        textoLabel();
    }

    public void textoLabel(){
        if(pessoa.getGenero().equals("Masculino")){
            bemVindo.setText("Bem vindo, "+pessoa.getNome());
        }else{
            bemVindo.setText("Bem vinda, "+pessoa.getNome());
        }
        
    }

    @FXML
    private void agendamento(){
        App.pushScreen("TELAAGENDAMENTOCONSULTA",o-> new PacienteAgendamentoConsulta(repositorioMedico, pessoa, repositorioAgendamento));
    }

}
