package edu.depaul.ntessema.jaxws.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class QuoteOfTheDay {

    public static void main(String[] args) {
        QuoteServiceService service = new QuoteServiceService();
        Service port = service.getQuoteServicePort();
        displayLogo();
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
                switch(command) {
                    case "help":
                        displayHelp();
                        continue;
                    case "exit":
                        clientAlive = false;
                        continue;
                    case "get":
                        display(port.getQuote(), Status.GET_OK);
                        continue;
                    case "add":
                        if(argument.equals("")) {
                            display("no quote provided for addition", Status.ERROR);
                        } else {
                            port.addQuote(argument);
                            display("quote sent for addition", Status.ADD_OK);
                        }
                        continue;
                    default:
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

    private static void displayLogo() {
        StringBuilder sb = new StringBuilder();
        final String FORMAT = "%8s%s\n";
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        sb.append(String.format(FORMAT, "", "|         Quote of the Day Client           |"));
        sb.append(String.format(FORMAT, "", "|              Nardos Tessema               |"));
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        sb.append(String.format(FORMAT, "", "|    (Commands: get, add, exit, help)       |"));
        sb.append(String.format(FORMAT, "", "+-------------------------------------------+"));
        System.out.println(sb.toString());
    }

    private static void displayHelp() {
        final String SEPARATOR = "  ";
        final String FORMAT = "%s%7s%s%s\n";
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(String.format(FORMAT, SEPARATOR, "-------", SEPARATOR, "-----------------------------------"));
        sb.append(String.format(FORMAT, SEPARATOR, "command", SEPARATOR, "description"));
        sb.append(String.format(FORMAT, SEPARATOR, "-------", SEPARATOR, "-----------------------------------"));
        Stream.of(COMMANDS).forEach(c -> sb.append(String.format(FORMAT, SEPARATOR, c[0], SEPARATOR, c[1])));
        sb.append(String.format(FORMAT, SEPARATOR, "-------", SEPARATOR, "-----------------------------------"));
        display(sb.toString(), Status.INFO);
    }
    private static final String[][] COMMANDS = new String[][] {
            { "get", "Get a quote from the quote service" },
            { "add", "Add a quote to the quote service" },
            { "exit", "Exit the client program" },
            { "help", "Print available commands" }
    };
}
