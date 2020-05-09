package edu.depaul.ntessema.jaxws.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class QuoteOfTheDay {

    private static final String[][] COMMANDS = new String[][] {
            { "get", "Get a quote from the quote service." },
            { "add", "Add a quote to the quote service." },
            { "exit", "Exit the client program." },
            { "?", "Print available commands." }
    };

    public static void main(String[] args) {
        QuoteServiceService service = new QuoteServiceService();
        Service port = service.getQuoteServicePort();
        commandLoop(port);
    }

    private static void commandLoop(Service port) {
        boolean clientAlive = true;
        while(clientAlive) {
            System.out.print("client> ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
                String userInput = in.readLine();
                String tokens[] = userInput.split("\\s+", 2);
                String command = tokens[0].toLowerCase();
                String argument = "";
                if(tokens.length > 1) {
                    argument = tokens[1];
                }
                if(command.equals("?")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.format("%3s%s", "", "available commands"));
                    sb.append("\n");
                    sb.append(String.format("%3s%s", "", "-------------------------------------------"));
                    sb.append("\n");
                    Stream.of(COMMANDS).forEach(c -> sb.append(String.format("%7s - %s\n", c[0], c[1])));
                    sb.append(String.format("%3s%s", "", "-------------------------------------------"));
                    display(sb.toString(), Status.INFO);
                    continue;
                }
                if(command.equals("exit")) {
                    clientAlive = false;
                    continue;
                }
                if(command.equals("get")) {
                    display(port.getQuote(), Status.GET_OK);
                    continue;
                }
                if(command.equals("add")) {
                    if(argument.equals("")) {
                        display("no quote provided for addition", Status.ERROR);
                    } else {
                        port.addQuote(argument);
                        display("quote sent for addition", Status.ADD_OK);
                    }
                    continue;
                }
            } catch(IOException ioe) {
                System.out.println("I/O error.");
            }
        }
    }

    private static void display(String message, Status status) {
        String statusMessage = ((status == Status.ERROR) ? ("[" + status.toString() + "]") : "");
        String formattedMessage = ((status == Status.GET_OK)) ? ("\"" + message + "\"") : message;
        System.out.println(String.format("%s %s", statusMessage, formattedMessage));
    }
}
