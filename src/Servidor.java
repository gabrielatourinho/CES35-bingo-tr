import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
	
	private static Socket cliente;
    private OutputStream output;
    private InputStream input;
    private static Scanner in;
    private static PrintStream out;
    
    Servidor(Socket s) throws IOException {
        cliente = s;
        output = cliente.getOutputStream();
        input = cliente.getInputStream();
        in = new Scanner(input);
        out = new PrintStream(output);
    }
    
	
	public String geraCartela(){
		return "1.2.3.4.5.6.7.8.9";
	}
    
	public void bingoprotocol() throws InterruptedException{
		Mockada teste = mock(Mockada.class);
		when(teste.sorteia()).thenReturn(5).thenReturn(8).thenReturn(9).thenReturn(12).thenReturn(1).thenReturn(15).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(6).thenReturn(7).thenReturn(10);
		
		boolean tr = true; 
		boolean inicio = false, bingo = false;
	    while (tr) {
	    	 String linha = "";
	    	 if(in.hasNextLine()){
	    		 linha = in.nextLine();
	    	 }
	    	 else {
	    		 linha = "";
	    	 }
	    	 
	    	 if(linha.equals("Gostaria de uma cartela.")){
	    		 System.out.println(cliente.getInetAddress().getHostAddress()+" : "+ linha);
	    		  out.println(this.geraCartela());
	    	  }
	    	  
	    	 if(linha.equals("Confirma")){
	    		 System.out.println(cliente.getInetAddress().getHostAddress()+" : "+ linha);
	    		 out.println("Inicio do Bingo"); //é realmente necessária?
	    		 inicio = true;
	    	 }
	    	 
	    	 while(inicio && !bingo){
	    		 
	    		 int numero = teste.sorteia();
	    		 System.out.println("Número "+numero);
	    		 out.println(numero);
	    		 Thread.sleep(500);
	    		 
	    		 if(in.hasNextLine()){ //scanner não é o melhor para usar o inputstream
	    			 linha = in.nextLine();
	    			 if (linha.equals("BINGO")){
			    		 bingo = true;
			    		 System.out.println(cliente.getInetAddress().getHostAddress()+" : BINGO");
			    		 System.out.println("VITORIA: "+cliente.getInetAddress().getHostAddress());
			    		 out.println("VITORIA: "+cliente.getInetAddress().getHostAddress());
			    		 tr = false;
	    			 }//if
		    	 }//if
	    	 }//while
	    }//while
	}
	
  public static void main(String[] args) {
    try {
      // Instancia o ServerSocket ouvindo a porta 12345
      ServerSocket servidor = new ServerSocket(12345);
      System.out.println("Servidor ouvindo a porta 12345");
      
      Servidor s = new Servidor(servidor.accept());
      System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
      
      s.bingoprotocol();
        
      in.close();
      servidor.close();
      cliente.close(); 
    }   
    catch(Exception e) {
       System.out.println("Erro: " + e.getMessage());
    }
  }     
}