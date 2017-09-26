import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	
	private ArrayList<String> cartela;
	private String msg;
	private static Socket socketCliente;
	private OutputStream output;
	private InputStream input;
	private static Scanner in;
	private static PrintStream out;

	Cliente (String server, int port) throws IOException, UnknownHostException {
		System.out.println("Iniciando conexão com servidor.");
		socketCliente = new Socket(server, port);
		System.out.println("Conexão estabelecida.");
		output = socketCliente.getOutputStream();
		input = socketCliente.getInputStream();
		in = new Scanner(input);
		out = new PrintStream(output);
		cartela = new ArrayList<String>();
	}

	private void solicitaCartela(){
		out.println("Gostaria de uma cartela.");
	}
	
	private void confirmaCartela(){
		out.println("Confirma");
	}

	private void getCartela(){
		String nums[];
		
		msg = in.nextLine();
		nums = msg.split("\\.");
		for(int i=0; i<9; i++)
			cartela.add(nums[i]);
		System.out.println("Minha cartela: "+cartela.toString());
	}
	
	private String getMensagem(){
		msg = in.nextLine();
		return msg;
	}
	
	private void comecaJogo(){
		while(true){
			if(in.hasNextLine()){
				out.println("OK");
				getMensagem();
				if(cartela.contains(msg)){
					cartela.remove(msg);
					System.out.println(msg+" ok!");
				}
				if(cartela.isEmpty()){
					out.println("BINGO");
					System.out.println("Bingoo");
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			Cliente cliente = new Cliente("localhost",12345);

			cliente.solicitaCartela();
			cliente.getCartela();
			cliente.confirmaCartela();
	
			if(cliente.getMensagem().equals("Inicio do Bingo")){
				System.out.println("Comecou o bingo!!");
				cliente.comecaJogo();
			}
			
			out.close();

			socketCliente.close();
		}
		catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}