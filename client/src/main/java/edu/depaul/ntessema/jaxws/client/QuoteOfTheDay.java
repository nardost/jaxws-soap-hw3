package edu.depaul.ntessema.jaxws.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * @author nardos
 *
 * The Quote of the Day client class.
 * The names of the service classes and the APIs
 * exposed by the service are obtained from the WSDL file.
 *
 * The client module could not be built without prior knowledge of
 * the service contracts.
 *
 * The service was independently built and published. The WSDL file
 * and the schema definition were copied from the running service URIs
 * http://localhost:8888/quotes?wsdl & http://localhost:8888/quotes?xsd=1
 * The files were saved in the classpath and maven was configured to generate
 * the stubs in the generate-sources phase using the WSDL & XSD files.
 *
 * By compile time, all classes needed are there in the classpath and the
 * build passes successfully.
 */
public class QuoteOfTheDay {

    public static void main(String[] args) {
        displayLogo();
        QuoteServiceService service = new QuoteServiceService();
        Service port = service.getQuoteServicePort();
        /*
         * A sort of REPL
         */
        commandLoop(port);
    }

    /**
     * The client has a Read-Eval-Print Loop (REPL)
     * where the user interacts with the service by
     * entering available commands and getting responses.
     * This method starts the REPL loop.
     * @param port
     */
    private static void commandLoop(Service port) {
        boolean clientAlive = true;
        while(clientAlive) {
            System.out.print("client> ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
                String userInput = in.readLine();
                /*
                 * User input could be a command and a maximum of one
                 * argument. Separate the command part and the argument part.
                 */
                String tokens[] = userInput.split("\\s+", 2);
                /*
                 * Make commands case-insensitive
                 */
                String command = tokens[0].toLowerCase();
                /*
                 * Argument to a command is empty string if
                 * not supplied by the user.
                 */
                String argument = "";
                /*
                 * If user supplies an argument, store
                 * it in a variable called argument.
                 */
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
                            /*
                             * We can only be sure tha the quote is sent.
                             * It might be rejected by the service if it is a
                             * duplicate. So we are cautious with the status message.
                             */
                            display("quote sent for addition", Status.ADD_OK);
                        }
                        continue;
                    default: /* Ignore unknown commands. */
                }
            } catch(IOException ioe) {
                /*
                 * readLine() could go wrong sometimes.
                 */
                System.out.println("I/O error.");
            } catch (Exception e) {
                /*
                 * If service is not running, a client request will throw
                 * an exception. Do not let the client break if such exception
                 * occurs. Just inform the user and continue the command loop.
                 *
                 * Since I do not know the exact exception type, I make sure
                 * that it is handled in the last catch block. If any catch block is
                 * written next to this block, it will be wrongly captured here
                 * as Exception is the parent of all exception classes.
                 */
                display("unable to connect to service", Status.ERROR);
            }
        }
    }

    /*
     * Format response and print on the front end (console).
     */
    private static void display(String message, Status status) {
        String statusMessage = ((status == Status.ERROR) ? ("[" + status.toString() + "]") : "");
        String formattedMessage = ((status == Status.GET_OK)) ? ("\"" + message + "\"") : message;
        System.out.println(String.format("%s %s", statusMessage, formattedMessage));
    }

    /*
     * Print an ASCII logo
     */
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

    /*
     * Display a sort of a man page
     */
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

    /*
     * All available commands are centrally
     * registered with their descriptions.
     */
    private static final String[][] COMMANDS = new String[][] {
            { "get", "Get a quote from the quote service" },
            { "add", "Add a quote to the quote service" },
            { "exit", "Exit the client program" },
            { "help", "Print available commands" }
    };
}
