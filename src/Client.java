import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {

    public static int vitorias = 0;
    public static int derrotas = 0;
    public static int empates = 0;

    private static Game game = new Game();

    private static Scanner scanner = new Scanner(System.in);

    public Scanner getScanner() {
        return scanner;
    }

    public static void main(String[] args) {
        System.out.println("*!BEM VINDO AO JOGO!*");
        System.out.println("No Jokempô, cada jogador escolhe entre Pedra (0), Papel (1) ou Tesoura (2). " +
                "Pedra vence Tesoura, Tesoura vence Papel e Papel vence Pedra. " +
                "\nOs jogadores digitam o número correspondente à sua escolha e o vencedor é determinado " +
                "com base nessas regras");

        System.out.println("\nDigite (1) para JOGADOR VS CPU | (2) para JOGADOR VS JOGADOR | (3) para FECHAR O JOGO:");

        int gamemode = Integer.parseInt(scanner.nextLine());

        if (gamemode == 1) {
            playVersusGPU();
        } else if (gamemode == 2) {

            System.out.println("Digite o endereço IP do servidor: ");
            String host = scanner.nextLine();

            System.out.println("Digite a porta do servidor: ");
            int port = Integer.parseInt(scanner.nextLine());

            try {

                Socket socket = new Socket(host, port);
                Connection connection = new Connection();

                while (true) {

                    Options option = choseOption();

                    System.out.println("\nEsperando resposta do servidor... Sua escolha foi: " + option);

                    if (option == Options.SAIR) {
                        main(new String[] {});
                    }

                    connection.send(socket, option.toString());

                    String serverResult = connection.receive(socket);

                    System.out.println("\nResultado: " + serverResult);

                    Results resultado = Results.valueOf(serverResult.trim());

                    switch (resultado) {
                        case VITORIA:
                            System.out.println("\nVOCÊ VENCEU!");
                            vitorias++;
                            break;

                        case DERROTA:
                            System.out.println("\nVOCÊ PERDEU!");
                            derrotas++;
                            break;

                        case EMPATE:
                            System.out.println("\nEMPATE!");
                            empates++;
                            break;
                        case EXIT:
                            main(new String[] {});
                            System.out.println("Conexão fechada");
                        default:
                            break;
                    }

                    System.out.println(
                            "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);
                }
            } catch (IOException e) {
                e.printStackTrace();
                main(new String[] {});
            }
        } else if (gamemode == 3) {
            scanner.close();
            System.exit(0);

        }
    }

    private static void playVersusGPU() {

        Options playerChose = choseOption();

        Options cpuOptionChose = Options.values()[new Random().nextInt(Options.values().length - 1)];

        String result = game.getGameResult(playerChose, cpuOptionChose);

        System.out.println("\nResultado Cliente: " + result);

        if (playerChose == Options.SAIR) {
            main(new String[] {});
            return;
        } else {
            System.out.println("\nO bot escolheu: " + cpuOptionChose.toString());
        }

        switch (result) {
            case "1":
                System.out.println("\nVOCÊ VENCEU!");
                vitorias++;
                break;

            case "2":
                System.out.println("\nVOCÊ PERDEU!");
                derrotas++;
                break;

            case "0":
                System.out.println("\nEMPATE!");
                empates++;
                break;
            case "3":
                System.out.println("Jogada invalida!");
            default:
                break;
        }

        System.out.println(
                "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);

        playVersusGPU();
    }

    private static Options choseOption() {

        Options option = null;

        while (option == null) {
            System.out.println(
                    "\nDIGITE: Pedra | Papel | Tesoura | (Para sair do jogo digite - (Sair))");
            String userEntry = scanner.nextLine().trim().toUpperCase();
            try {
                option = Options.valueOf(userEntry);
            } catch (Exception e) {
                System.out.println("Opção inválida");
            }
        }

        return option;

    }

}