import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteUm {

    public static int vitorias = 0;
    public static int derrotas = 0;
    public static int empates = 0;

    public static void main(String[] args) {
        System.out.println("*!BEM VINDO AO JOGO!*");

        System.out.println("No Jokempô, cada jogador escolhe entre Pedra (0), Papel (1) ou Tesoura (2). " +
                "Pedra vence Tesoura, Tesoura vence Papel e Papel vence Pedra. " +
                "\nOs jogadores digitam o número correspondente à sua escolha e o vencedor é determinado " +
                "com base nessas regras");

        System.out.println("\nDigite (1) para JOGADOR VS CPU | (2) para JOGADOR VS JOGADOR | (3) para FECHAR O JOGO:");
        Scanner scanner = new Scanner(System.in);
        int modoJogo = scanner.nextInt();

        if (modoJogo == 1) {
            iniciarJogoVsCPU();
        } else if (modoJogo == 2) {
            System.out.println("Digite o endereço IP do servidor: ");
            Scanner scanner1 = new Scanner(System.in);
            String enderecoServidor = scanner1.nextLine();

            System.out.println("Digite a porta do servidor: ");
            Scanner scanner2 = new Scanner(System.in);
            int portaServidor = scanner2.nextInt();

            try {
                Socket soquete = new Socket(enderecoServidor, portaServidor);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(soquete.getInputStream()));
                PrintWriter saida = new PrintWriter(soquete.getOutputStream(), true);
                Scanner entradaUsuario = new Scanner(System.in);
                while (true) {
                    System.out.println(
                            "\nDIGITE: (0) - Pedra | (1) - Papel | (2) - Tesoura | (Para sair do jogo digite - (3))");
                    int escolha = entradaUsuario.nextInt();

                    saida.println(escolha);

                    int resultado = Integer.parseInt(entrada.readLine());

                    if (resultado == 0) {
                        System.out.println("\nEMPATE!");
                        empates++;
                        System.out.println(
                                "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);

                    } else if (resultado == 1) {
                        System.out.println("\nVOCÊ VENCEU!");
                        vitorias++;
                        System.out.println(
                                "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);
                    } else if (resultado == 2) {
                        System.out.println("\nVOCÊ PERDEU!");
                        derrotas++;
                        System.out.println(
                                "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);
                    } else if (resultado == 3) {
                        main(new String[] {});
                        System.out.println("Conexão fechada");
                    } else {
                        System.out.println("\nFAÇA UMA JOGADA VALIDA!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (modoJogo == 3) {
            System.exit(0);
        }
    }

    private static void iniciarJogoVsCPU() {

        System.out.println("\nEscolha: 0 - Pedra | 1 - Papel | 2 - Tesoura | (Para sair do jogo digite - 3)");
        Scanner scanner = new Scanner(System.in);
        int escolha = scanner.nextInt();

        int escolhaCPU = (int) (Math.random() * 3);

        obterResultadoCPU(escolha, escolhaCPU);

        if (escolha == 3) {
            main(new String[] {});
        } else {
            if (escolhaCPU == 0) {
                System.out.println("\nO bot escolheu pedra!");
            } else if (escolhaCPU == 1) {
                System.out.println("\nO bot escolheu papel!");
            } else if (escolhaCPU == 2) {
                System.out.println("\nO bot escolheu tesoura!");
            }

            iniciarJogoVsCPU();
        }
    }

    private static int obterResultadoCPU(int escolha, int escolhaCPU) {

        if ((escolha <= 2 && escolhaCPU <= 2) && (escolha == escolhaCPU)) {
            System.out.println("\nEMPATE");
            empates++;
            System.out.println(
                    "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);

            return 0;
        } else if ((escolha == 0 && escolhaCPU == 2) || (escolha == 1 && escolhaCPU == 0)
                || (escolha == 2 && escolhaCPU == 1)) {
            System.out.println("\nO jogador VENCEU!");
            vitorias++;
            System.out.println(
                    "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);

            return 1;
        } else if ((escolhaCPU == 0 && escolha == 2) || (escolhaCPU == 1 && escolha == 0)
                || (escolhaCPU == 2 && escolha == 1)) {
            System.out.println("\nO bot VENCEU!");
            derrotas++;
            System.out.println(
                    "Empates: " + empates + " | Vitórias: " + vitorias + " | Derrotas: " + derrotas);

            return 2;
        } else if (escolha == 3) {
            System.out.println("\nJogo resetado\n");
            return 3; // Reinicia o jogo
        } else {
            System.out.println("\nFAÇA UMA JOGADA VALIDA!");
            return 4;
        }
    }
}