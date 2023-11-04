package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class CadastroPaciente implements Initializable{

    @FXML
    private ListView<Pessoa> lstPacientes;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane cadastrar;

    @FXML
    private TitledPane listar;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFiltro;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox cbGenero;

    @FXML
    private TextArea detalhes;

    private RepositorioPessoa repositorioPessoa;

    public CadastroPaciente(RepositorioPessoa repositorioPessoa){
        this.repositorioPessoa = repositorioPessoa;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbGenero.getItems().addAll("Masculino", "Feminino");

        lstPacientes.getItems().clear();

        lstPacientes.setCellFactory(new Callback<ListView<Pessoa>, ListCell<Pessoa>>() {
        @Override
        public ListCell<Pessoa> call(ListView<Pessoa> listView) {
            return new ListCell<Pessoa>() {
                @Override
                protected void updateItem(Pessoa pessoa, boolean empty) {
                    super.updateItem(pessoa, empty);
                    if (pessoa != null) {
                        setText(pessoa.getNome()); 
                    } else {
                        setText(null);
                    }
                    }
                };
            }
        });

        Resultado resultado = repositorioPessoa.listar();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }else{
            List lista = (List)resultado.comoSucesso().getObj();
            Collections.sort(lista, Comparator.comparing(Pessoa::getNome));
            lstPacientes.getItems().addAll(lista);
        }

        //criarPessoa();
    }

    @FXML
    private void listar(MouseEvent evento){
        Pessoa pessoa = lstPacientes.getSelectionModel().getSelectedItem();
        if (pessoa != null) {
            // Agora você pode acessar as propriedades de pessoa
            System.out.println(pessoa.getId());
            System.out.println("ok");
            detalhes.clear();
            detalhes.appendText("ID: " + pessoa.getId() + "\n");
            detalhes.appendText("Nome: " + pessoa.getNome() + "\n");
            detalhes.appendText("CPF: " + pessoa.getCpf() + "\n");
            detalhes.appendText("Telefone: " + pessoa.getTelefone() + "\n\n");
            detalhes.appendText("Email: " + pessoa.getEmail() + "\n");
            detalhes.appendText("Data de nascimento: " + pessoa.getDataNascimento() + "\n");
            boolean cadastro = pessoa.isAtive();
            if (cadastro == true) {
                detalhes.appendText("Situação do cadastro: Ativo");
            } else {
                detalhes.appendText("Situação do cadastro: Inativo");
            }
        }
         
    }

    private void criarPessoa() {

        ArrayList<Pessoa> lista = new ArrayList<>();
        /* 
        lista.add(new Pessoa("Mariana Oliveira", "12345678903", "555-555-5560", "mariana@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Carlos Lima", "98765432111", "555-555-5561", "carlos@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Luisa Pereira", "55555555557", "555-555-5562", "luisa@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Fábio Silva", "11111111113", "555-555-5563", "fabio@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Juliana Souza", "99999999997", "555-555-5564", "juliana@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Rafael Santos", "12345678904", "555-555-5565", "rafael@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Renata Oliveira", "98765432112", "555-555-5566", "renata@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Gustavo Lima", "55555555558", "555-555-5567", "gustavo@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Vitória Silva", "11111111114", "555-555-5568", "vitoria@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Maurício Souza", "99999999996", "555-555-5569", "mauricio@example.com", criarData(), "Masculino", true));
        */
        /* 
        lista.add(new Pessoa("Paula Santos", "12345678905", "555-555-5560", "paula@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Antônio Oliveira", "98765432115", "555-555-5561", "antonio@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Larissa Pereira", "55555555565", "555-555-5562", "larissa@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Mateus Silva", "11111111115", "555-555-5563", "mateus@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Mônica Souza", "99999999995", "555-555-5564", "monica@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Bruno Lima", "12345678906", "555-555-5565", "bruno@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Clara Oliveira", "98765432116", "555-555-5566", "clara@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Fernando Santos", "55555555566", "555-555-5567", "fernando@example.com", criarData(), "Masculino", true));
        lista.add(new Pessoa("Valentina Silva", "11111111116", "555-555-5568", "valentina@example.com", criarData(), "Feminino", true));
        lista.add(new Pessoa("Ricardo Souza", "99999999994", "555-555-5569", "ricardo@example.com", criarData(), "Masculino", true));
        */
        for (Pessoa pessoa : lista) {
            repositorioPessoa.criar(pessoa);
        }
        

    }

    private LocalDate criarData(){
        Random random = new Random();
        LocalDate dataNascimento = LocalDate.of(1990 + random.nextInt(30), random.nextInt(12) + 1, random.nextInt(28) + 1);
        return dataNascimento;
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

        Resultado resultado = repositorioPessoa.criar(pessoa);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        atualizar();
        alert.showAndWait();
    }

    @FXML
    private void atualizar(){
        Resultado<ArrayList<Pessoa>> resultado = repositorioPessoa.listar();

        if(resultado.foiSucesso()){
            atualizarTabela(resultado.comoSucesso().getObj());
        }
    }

    @FXML
    private void mostrarDetalhes(MouseEvent evento){
        Pessoa pessoa = lstPacientes.getSelectionModel().getSelectedItem();
        System.out.println(pessoa.getId());
       
        if(pessoa != null){
            detalhes.clear();
            detalhes.appendText("ID: "+pessoa.getId()+"\n");
            detalhes.appendText("Nome: "+pessoa.getNome()+"\n");
            detalhes.appendText("CPF: "+pessoa.getCpf()+"\n");
            detalhes.appendText("Telefone: "+pessoa.getTelefone()+"\n\n");
            detalhes.appendText("Email: "+pessoa.getEmail()+"\n");
            detalhes.appendText("Data de nascimento: "+pessoa.getDataNascimento()+"\n");
            boolean cadastro = pessoa.isAtive();
            if(cadastro==true){
                detalhes.appendText("Situação do cadastro: Ativo");
            }
            else{
                detalhes.appendText("Situação do cadastro: Inativo");
            }
        }
    }

    @FXML
    private void filtrar(KeyEvent evt){
        if(!tfFiltro.getText().isBlank() || !tfFiltro.getText().isEmpty()){
            
            Resultado<ArrayList<Pessoa>> resultado = repositorioPessoa.filtrarNome(tfFiltro.getText());

            if(resultado.foiSucesso()){
                atualizarTabela(resultado.comoSucesso().getObj());
            }
        }else{
           Resultado<ArrayList<Pessoa>> resultado = repositorioPessoa.listar();

           atualizarTabela(resultado.comoSucesso().getObj());
        }
    }

    private void atualizarTabela(List<Pessoa> pessoa){
        lstPacientes.getItems().clear();
        lstPacientes.getItems().addAll(pessoa);
    }
    
    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
    
}
