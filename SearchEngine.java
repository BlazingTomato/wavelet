import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("List of added Strings: %s", strings.toString());  
        }else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
                ArrayList<String> matchingStrings = new ArrayList<>();
                if (parameters[0].equals("s")) {
                    for (String string : strings) {
                        if(string.contains(parameters[1])){
                            matchingStrings.add(string);
                        }
                    }             
                }

                return String.format("List of found Strings: %s", matchingStrings.toString());
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return String.format("%s has been added!", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }

    /*
     * 
     */

    String getListofString(ArrayList<String> strings){
        String s = "";
        for (String string : strings) {
                if(s.equals(""))
                    s += string;
                else{
                    s += ", " + string;
                }
        }

        return s;
    }
}




class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
