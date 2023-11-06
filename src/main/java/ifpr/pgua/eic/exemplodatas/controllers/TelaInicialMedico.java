package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPessoa;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TelaInicialMedico implements Initializable{

    @FXML
    private Label bemVindo;
    
    private RepositorioAgendamento repositorioAgendamento;
    private RepositorioMedico repositorioMedico;
    private RepositorioPessoa repositorioPessoa;
    private Medico medico;

    public TelaInicialMedico(RepositorioAgendamento repositorioAgendamento, RepositorioMedico repositorioMedico, RepositorioPessoa repositorioPessoa, Medico medico){
        this.repositorioAgendamento = repositorioAgendamento;
        this.repositorioMedico = repositorioMedico;
        this.repositorioPessoa = repositorioPessoa;
        this.medico = medico;
    }    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        textoLabel();
    }

    public void textoLabel(){
        if(medico.getGenero().equals("Masculino")){
            bemVindo.setText("Bem vindo, "+medico.getNome());
        }else{
            bemVindo.setText("Bem vinda, "+medico.getNome());
        }
        
    }
}
