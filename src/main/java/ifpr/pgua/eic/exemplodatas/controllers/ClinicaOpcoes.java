package ifpr.pgua.eic.exemplodatas.controllers;

import ifpr.pgua.eic.exemplodatas.App;
import javafx.fxml.FXML;

public class ClinicaOpcoes {
 
    @FXML
    private void cadastrarPacientes(){
        App.pushScreen("CADASTRO");
    }

    @FXML
    private void cadastrarFuncionario(){
        App.pushScreen("CADASTRARFUNCIONARIO");
    }

    @FXML
    private void cadastrarMedico(){
        App.pushScreen("CADASTRARMEDICO");
    }

    @FXML
    private void listarPacientes(){
        App.pushScreen("LISTARPACIENTES");
    }

}
