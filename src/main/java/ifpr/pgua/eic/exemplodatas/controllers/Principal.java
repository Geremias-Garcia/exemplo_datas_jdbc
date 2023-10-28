package ifpr.pgua.eic.exemplodatas.controllers;


import ifpr.pgua.eic.exemplodatas.App;
import javafx.fxml.FXML;

public class Principal {

    @FXML
    private void clinicaOpcoes(){
        App.pushScreen("CLINICAOPCOES");
    }

    @FXML
    private void loginPaciente(){
        App.pushScreen("TELALOGINPACIENTE");
    }

}
