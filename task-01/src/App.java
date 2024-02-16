import java.io.Console;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome.");
        Console cons = System.console();
        String input = "";
        int $last = 0;

        while(!input.equals("exit")){
            input = cons.readLine(">").toLowerCase();
            if (input.equals("exit")){
                break;
            }
            String[] parts = input.split("\\s+");
            for(int i = 0; i < parts.length; i++){
                if (parts[i].equals("$last")){
                    parts[i] = Integer.toString($last);
                }
            }
            if (parts.length == 3){
                $last = calculate(parts);
                System.out.println($last);
            }
        }
        System.out.println("Bye bye");
    }
    
    public static int calculate(String[] parts){
        int $last = 0;
        if (parts[1].equals("+")){
            $last = Integer.parseInt(parts[0]) + Integer.parseInt(parts[2]);
        }
        else if (parts[1].equals("-")){
            $last = Integer.parseInt(parts[0]) - Integer.parseInt(parts[2]);
        }
        else if (parts[1].equals("/")){
            $last = Integer.parseInt(parts[0]) / Integer.parseInt(parts[2]);
        }
        else if (parts[1].equals("*")){
            $last = Integer.parseInt(parts[0]) * Integer.parseInt(parts[2]);
        }

        return $last;
    }
}
