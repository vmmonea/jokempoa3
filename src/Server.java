import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static ServerSocket serverSocket;
    static Socket client_socket;
    static String msg;

    static Socket playerOne;
    static Socket playerTwo;

    static Connection connection = new Connection();

    private static Game game = new Game();

    public static void main(String[] args) {

        try {

            System.out.println("Digite a porta do servidor:");
            Scanner scanner = new Scanner(System.in);
            int port = scanner.nextInt();

            serverSocket = new ServerSocket(port);

            System.out.println("Servidor iniciado. Conectado na porta " + port);

            playerOne = serverSocket.accept();
            System.out.println("Jogador 1 conectado");

            playerTwo = serverSocket.accept();
            System.out.println("Jogador 2 conectado");

            while (playerOne.isConnected() && playerTwo.isConnected()) {
                playGame();
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void playGame() {

        String moveOneString = connection.receive(playerOne).trim();

        System.out.println("Jogada do jogador 1: " + moveOneString);

        Options moveOne = Options.valueOf(moveOneString);

        System.out.println("Jogada do jogador 1: " + moveOne);

        if (moveOne.equals(Options.SAIR)) {
            connection.send(playerTwo, Results.EXIT.toString());
            return;
        }

        String moveTwoString = connection.receive(playerTwo).trim();

        Options moveTwo = Options.valueOf(moveTwoString);

        if (moveTwo.equals(Options.SAIR)) {
            connection.send(playerOne, Results.EXIT.toString());
            return;
        }

        System.out.println("Jogada do jogador 2: " + moveTwo);

        String result = game.getGameResult(moveOne, moveTwo);

        System.out.println("Resultado: " + result);

        if (result == "0") {
            connection.send(playerOne, Results.EMPATE.toString());
            connection.send(playerTwo, Results.EMPATE.toString());
        } else if (result == "1") {
            connection.send(playerOne, Results.VITORIA.toString());
            connection.send(playerTwo, Results.DERROTA.toString());
        } else if (result == "2") {
            connection.send(playerOne, Results.DERROTA.toString());
            connection.send(playerTwo, Results.VITORIA.toString());
        } else {
            connection.send(playerOne, Results.INVALIDO.toString());
            connection.send(playerTwo, Results.INVALIDO.toString());
        }

    }

}