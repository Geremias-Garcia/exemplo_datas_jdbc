package ifpr.pgua.eic.exemplodatas;

import ifpr.pgua.eic.exemplodatas.controllers.AtualizarHorariosDeAtendimento;
import ifpr.pgua.eic.exemplodatas.controllers.CadastroFuncionario;
import ifpr.pgua.eic.exemplodatas.controllers.CadastroMedico;
import ifpr.pgua.eic.exemplodatas.controllers.CadastroPaciente;
import ifpr.pgua.eic.exemplodatas.controllers.ClinicaOpcoes;
import ifpr.pgua.eic.exemplodatas.controllers.DadosPessoaisPaciente;
import ifpr.pgua.eic.exemplodatas.controllers.PacienteAgendamentoConsulta;
import ifpr.pgua.eic.exemplodatas.controllers.PacienteCriandoCadastro;
import ifpr.pgua.eic.exemplodatas.controllers.Principal;
import ifpr.pgua.eic.exemplodatas.controllers.TelaInicialMedico;
import ifpr.pgua.eic.exemplodatas.controllers.TelaInicialPaciente;
import ifpr.pgua.eic.exemplodatas.controllers.TelaLoginMedico;
import ifpr.pgua.eic.exemplodatas.controllers.TelaLoginPaciente;
import ifpr.pgua.eic.exemplodatas.controllers.VerificarConsultas;
import ifpr.pgua.eic.exemplodatas.model.daos.AgendamentoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.FabricaConexoes;
import ifpr.pgua.eic.exemplodatas.model.daos.FuncionarioDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.DisponibilidadeMedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCFuncionarioDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCDisponibilidadeMedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCLoginDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCMedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCPacienteDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCAgendamentoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.LoginDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.PacienteDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioFuncionario;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioDisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPaciente;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private PacienteDAO pacienteDAO = new JDBCPacienteDAO(FabricaConexoes.getInstance());
    private RepositorioPaciente repositorioPaciente = new RepositorioPaciente(pacienteDAO);

    private MedicoDAO medicoDAO = new JDBCMedicoDAO(FabricaConexoes.getInstance());
    private RepositorioMedico repositorioMedico = new RepositorioMedico(medicoDAO);

    private FuncionarioDAO funcionarioDAO = new JDBCFuncionarioDAO(FabricaConexoes.getInstance());
    private RepositorioFuncionario repositorioFuncionario = new RepositorioFuncionario(funcionarioDAO);

    private LoginDAO loginDAO = new JDBCLoginDAO(FabricaConexoes.getInstance());
    private RepositorioLogin repositorioLogin = new RepositorioLogin(loginDAO);

    private AgendamentoDAO agendamentoDAO = new JDBCAgendamentoDAO(FabricaConexoes.getInstance());
    private RepositorioAgendamento repositorioAgendamento = new RepositorioAgendamento(agendamentoDAO, pacienteDAO, medicoDAO);

    private DisponibilidadeMedicoDAO horariosIndisponiveisDAO = new JDBCDisponibilidadeMedicoDAO(FabricaConexoes.getInstance());
    private RepositorioDisponibilidadeMedico repositorioHorariosIndisponiveis = new RepositorioDisponibilidadeMedico(horariosIndisponiveisDAO);


    private Paciente paciente;
    private Medico medico;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "PRINCIPAL";
    }


    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Exemplo de datas";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(App.class, "principal.fxml", o->new Principal()));

        registraTela("CLINICAOPCOES", new ScreenRegistryFXML(App.class, "clinicaOpcoes.fxml", o->new ClinicaOpcoes(repositorioAgendamento)));

        registraTela("VERIFICARCONSULTAS", new ScreenRegistryFXML(App.class, "verificarConsultas.fxml", o->new VerificarConsultas(repositorioAgendamento)));

        registraTela("TELALOGINMEDICO", new ScreenRegistryFXML(App.class, "telaLoginMedico.fxml", o->new TelaLoginMedico(repositorioMedico,repositorioLogin,repositorioPaciente,repositorioAgendamento)));

        registraTela("CADASTRO", new ScreenRegistryFXML(App.class, "cadastroPaciente.fxml", o->new CadastroPaciente(repositorioPaciente, repositorioLogin)));

        registraTela("CADASTRARFUNCIONARIO", new ScreenRegistryFXML(App.class, "cadastrarFuncionario.fxml", o->new CadastroFuncionario(repositorioFuncionario)));

        registraTela("CADASTRARMEDICO", new ScreenRegistryFXML(App.class, "cadastrarMedico.fxml", o->new CadastroMedico(repositorioMedico)));

        registraTela("ATUALIZARHORARIOSDEATENDIMENTO", new ScreenRegistryFXML(App.class, "atualizarHorariosDeAtendimento.fxml", o->new AtualizarHorariosDeAtendimento(repositorioMedico, repositorioHorariosIndisponiveis)));

        registraTela("TELALOGINPACIENTE", new ScreenRegistryFXML(App.class, "telaLoginPaciente.fxml", o->new TelaLoginPaciente(repositorioMedico,repositorioLogin, repositorioPaciente, repositorioAgendamento)));

        registraTela("TELAINICIALPACIENTE", new ScreenRegistryFXML(App.class, "telaInicialPaciente.fxml", o->new TelaInicialPaciente(repositorioPaciente, repositorioLogin, repositorioMedico, paciente, repositorioAgendamento)));

        registraTela("DADOSPESSOAISPACIENTE", new ScreenRegistryFXML(App.class, "dadosPessoaisPaciente.fxml", o->new DadosPessoaisPaciente(repositorioLogin, repositorioPaciente, paciente)));

        registraTela("TELAINICIALMEDICO", new ScreenRegistryFXML(App.class, "telaInicialMedico.fxml", o->new TelaInicialMedico(repositorioAgendamento, repositorioMedico, repositorioPaciente, medico)));

        registraTela("TELAAGENDAMENTOCONSULTA", new ScreenRegistryFXML(App.class, "pacienteAgendamentoConsulta.fxml", o->new PacienteAgendamentoConsulta(repositorioMedico, paciente, repositorioAgendamento)));

        registraTela("PACIENTECRIANDOCADASTRO", new ScreenRegistryFXML(App.class, "pacienteCriandoCadastro.fxml", o->new PacienteCriandoCadastro(repositorioPaciente, repositorioLogin)));
    }
}