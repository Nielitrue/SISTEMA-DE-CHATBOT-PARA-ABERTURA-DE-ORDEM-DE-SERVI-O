import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ChatBot {
    private static final String[] SERVICOS = {
            "Suporte Tecnico",
            "Reclamacao",
            "Solicitacao de Orcamento",
            "Outros"
    };

    private final Scanner scanner;

    public ChatBot(Scanner scanner) {
        this.scanner = scanner;
    }

    public void iniciar() {
        Atendimento atendimento = new Atendimento();

        exibirSaudacao();
        atendimento.setTipoServico(escolherServico());
        atendimento.setNomeCompleto(solicitarCampo("ChatBot: Informe seu nome completo:",
                "ChatBot: O nome completo nao pode ficar vazio."));
        atendimento.setCpf(solicitarCpf());
        atendimento.setEmpresa(solicitarCampo("ChatBot: Informe o nome da empresa:",
                "ChatBot: O nome da empresa nao pode ficar vazio."));
        atendimento.setEnderecoEmpresa(solicitarCampo("ChatBot: Informe o endereco da empresa:",
                "ChatBot: O endereco da empresa nao pode ficar vazio."));
        atendimento.setDescricaoProblema(solicitarDescricao());
        atendimento.setProtocolo(gerarProtocolo());

        exibirResumo(atendimento);
    }

    private void exibirSaudacao() {
        System.out.println("ChatBot: Ola! Bem-vindo ao nosso atendimento.");
        System.out.println("ChatBot: Selecione o servico desejado:");

        for (int i = 0; i < SERVICOS.length; i++) {
            System.out.println((i + 1) + " - " + SERVICOS[i]);
        }
    }

    private String escolherServico() {
        while (true) {
            System.out.print("Usuario: ");
            String entrada = scanner.nextLine().trim();

            try {
                int opcao = Integer.parseInt(entrada);

                if (opcao >= 1 && opcao <= SERVICOS.length) {
                    return SERVICOS[opcao - 1];
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("ChatBot: Opcao invalida. Escolha um numero entre 1 e 4.");
        }
    }

    private String solicitarCampo(String mensagem, String mensagemErro) {
        while (true) {
            System.out.println(mensagem);
            System.out.print("Usuario: ");
            String valor = scanner.nextLine().trim();

            if (Validador.campoPreenchido(valor)) {
                return valor;
            }

            System.out.println(mensagemErro);
        }
    }

    private String solicitarCpf() {
        while (true) {
            System.out.println("ChatBot: Informe seu CPF no formato 000.000.000-00:");
            System.out.print("Usuario: ");
            String cpf = scanner.nextLine().trim();

            if (Validador.cpfValido(cpf)) {
                return cpf;
            }

            System.out.println("ChatBot: CPF invalido. Confira o formato e tente novamente.");
        }
    }

    private String solicitarDescricao() {
        while (true) {
            System.out.println("ChatBot: Descreva o problema ou solicitacao com pelo menos 15 caracteres e 3 palavras:");
            System.out.print("Usuario: ");
            String descricao = scanner.nextLine().trim();

            if (Validador.descricaoValida(descricao)) {
                return descricao;
            }

            System.out.println("ChatBot: A descricao esta muito curta. Tente explicar um pouco mais.");
        }
    }

    private String gerarProtocolo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        int sufixo = ThreadLocalRandom.current().nextInt(1000, 10000);
        return "#" + LocalDateTime.now().format(formatter) + sufixo;
    }

    private void exibirResumo(Atendimento atendimento) {
        System.out.println();
        System.out.println("ChatBot: Confira os dados do seu atendimento:");
        System.out.println("Servico: " + atendimento.getTipoServico());
        System.out.println("Nome completo: " + atendimento.getNomeCompleto());
        System.out.println("CPF: " + atendimento.getCpf());
        System.out.println("Empresa: " + atendimento.getEmpresa());
        System.out.println("Endereco da empresa: " + atendimento.getEnderecoEmpresa());
        System.out.println("Descricao: " + atendimento.getDescricaoProblema());
        System.out.println();
        System.out.println("ChatBot: Seu atendimento foi registrado com sucesso!");
        System.out.println("ChatBot: Em breve nossa equipe entrara em contato.");
        System.out.println("ChatBot: Numero do protocolo: " + atendimento.getProtocolo());
        System.out.println("ChatBot: Ate logo!");
    }
}
