package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class TelaInicialPaciente implements Initializable{

    @FXML
    private Label bemVindo;

    private Pessoa pessoa;
    
    public TelaInicialPaciente(Pessoa pessoa){
        this.pessoa = pessoa;
        System.out.println(pessoa+"login");
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
        App.pushScreen("TELAAGENDAMENTOCONSULTA");
    }

}
