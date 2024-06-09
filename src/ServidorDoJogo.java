import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorDoJogo {
    public static void main(String[] args) {

        System.out.println("Digite a porta do servidor:");
        Scanner scanner = new Scanner(System.in);
        int porta = scanner.nextInt();

        try (ServerSocket servidorSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado. Conectado na porta " + porta);

            // Esperar dois jogadores se conectarem
            Socket jogador1Socket = servidorSocket.accept();
            System.out.println("Jogador 1 conectado: " + jogador1Socket.getInetAddress());

            Socket jogador2Socket = servidorSocket.accept();
            System.out.println("Jogador 2 conectado: " + jogador2Socket.getInetAddress());


            iniciarJogo(jogador1Socket, jogador2Socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void iniciarJogo(Socket jogador1Socket, Socket jogador2Socket)
    {
        try (
                BufferedReader jogador1Entrada = new BufferedReader(new InputStreamReader(jogador1Socket.getInputStream()));
                PrintWriter jogador1Saida = new PrintWriter(jogador1Socket.getOutputStream(), true);
                BufferedReader jogador2Entrada = new BufferedReader(new InputStreamReader(jogador2Socket.getInputStream()));
                PrintWriter jogador2Saida = new PrintWriter(jogador2Socket.getOutputStream(), true);
        )
        {
            while (true)
            {
                // Recebe escolhas dos jogadores
                int escolhaJogador1 = Integer.parseInt(jogador1Entrada.readLine());
                int escolhaJogador2 = Integer.parseInt(jogador2Entrada.readLine());

                // Determina o resultado do jogo
                int resultado = obterResultado(escolhaJogador1, escolhaJogador2);

                // Envia o resultado para os jogadores
                jogador1Saida.println(resultado);
                jogador2Saida.println(resultado);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                jogador1Socket.close();
                jogador2Socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static int obterResultado(int escolha1, int escolha2)
    {
        if ((escolha1 <= 2 && escolha2 <= 2) && (escolha1 == escolha2))
        {
            return 0; // Empate
        }
        else if ((escolha1 == 0 && escolha2 == 2) || (escolha1 == 1 && escolha2 == 0) || (escolha1 == 2 && escolha2 == 1)) {
            return 1; // jogador 1 vence
        }
        else if((escolha2 == 0 && escolha1 == 2) || (escolha2 == 1 && escolha1 == 0) || (escolha2 == 2 && escolha1 == 1)){
            return 2; // jogador 2 vence
        }
        else if(escolha1 == 3 || escolha2 == 4){
            return 3; // Fechar jogo
        }
        else {
            return 4; // Jogada fora do padrão
        }
    }
}