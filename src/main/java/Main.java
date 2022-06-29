import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {


    public static void main(String[] args) {


        //USER INPUT DATA INIT
        Scanner scanner = new Scanner(System.in);
        String word = "";
        String original_word = "";

        //JSON DATA INIT
        ArrayList<String> words = new ArrayList<>();
        Random random = new Random();
        int randomNumber;

        //LOAD WORDLIST
        try {
            //FETCH JSON
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src/wordslist.json"));
            Map<String, ArrayList<String>> map = gson.fromJson(reader, Map.class);

            //RANDOM WORD FROM LIST
            randomNumber = random.ints(0, map.get("data").size()).findFirst().getAsInt();
            word = map.get("data").get(randomNumber);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cannot find file 'wordslist.json'");
            System.exit(0);
        }


        //FINAL WORD INIT
        word = word.toUpperCase();
        original_word = word.toUpperCase();


        original_word = "testing".toUpperCase();
        word = "testing".toUpperCase();


        //INIT GAME SCREEN DATA
        //hangman display init
        List<String> displayItems = new ArrayList<String>() {{
            add("---------");                            // 0
            add("||      |");                            // 1
            add("||       ");                            // 2 * HUMAN LIMBS
            add("||       ");                            // 3 * HUMAN LIMBS
            add("||       ");                            // 4 * HUMAN LIMBS
            add("||       ");                            // 5 * HUMAN LIMBS
            add("||       ");                            // 6
            add("||       ");                            // 7
            add("==================================||"); // 8
        }};


        //secret word display init STRING[ ]
        String[] secret_display_items = new String[word.length()];
        Arrays.fill(secret_display_items, "_");


        // ADDING TO HANGMAN DATA
        Stack<String> hangman_body_items = new Stack<String>();
        hangman_body_items.push("||     / \\                           ");
        hangman_body_items.push("||      |                             ");
        hangman_body_items.push("||    --|--                           ");
        hangman_body_items.push("||     ( )                            ");


        //RUNTIME GAME-LOOP VARIABLES INIT
        int lives = hangman_body_items.size();
        boolean valid = false;
        ArrayList<String> used_phrases = new ArrayList<String>();
        List<Serializable> validity;
        List<Serializable> output_data_manipulation;
        String answer;
        String user_guess = "";

        HashSet<Character> missed_letters = new HashSet<>();

        // MAIN GAME LOOP
        while (lives > 0) {
            //DRAW GAME SCREEN
            gameFunctionality.draw_screen(displayItems, secret_display_items);

            //USER INPUT & VALIDATION
            System.out.println("MISSED LETTERS: " + missed_letters.toString());
            System.out.println("\nPlease enter input \n");
            System.out.print(">  ");
            user_guess = scanner.nextLine().toUpperCase();
            validity = gameFunctionality.checkValidInput(user_guess, used_phrases, word);
            valid = (boolean) validity.get(0);
            used_phrases = (ArrayList<String>) validity.get(1);


            if (!valid) {
                System.out.println("INVALID INPUT : YOU WILL BE PUNISHED FOR YOUR ACTIONS...");
                lives--;
                String popped = hangman_body_items.pop();
                displayItems.set(6 - lives - 1, popped);
                continue;
            }

            //JUDGE ANSWER
            output_data_manipulation = gameFunctionality.correct_answer_check(original_word, secret_display_items, word, user_guess, missed_letters);

            answer = (String) output_data_manipulation.get(0);
            word = (String) output_data_manipulation.get(1);
            missed_letters = (HashSet<Character>) output_data_manipulation.get(2);


            if (answer.equals("complete")) {
                //YOU WIN
                System.out.println("CONGRATS! YOU WIN");
                System.out.println("Would you like to play again (y or n)");
                System.out.println("Please enter input \n");
                System.out.print(">  ");
                user_guess = scanner.nextLine().toUpperCase();
                if (user_guess.toLowerCase().startsWith("y")) {
                    break;
                } else {
                    System.out.println("GOODBYE");
                    System.exit(0);
                }
            }


            //KEEP PLAYING
            if (answer.equals("failed")) {
                //DEPLETE LIVES
                lives--;
                String popped = hangman_body_items.pop();
                displayItems.set(6 - lives - 1, popped);
            }


            // OUT OF LIVES GAME-OVER
            if (lives == 0) {
                System.out.print("\nGAME OVER\n");
                gameFunctionality.draw_screen(displayItems, secret_display_items);
                System.out.println("Correct Answer: " + word);

                System.out.println("Would you like to play again (y or n)");
                System.out.println("Please enter input \n");
                System.out.print(">  ");
                user_guess = scanner.nextLine().toUpperCase();
                if (user_guess.toLowerCase().startsWith("y")) {
                    break;
                } else {
                    System.out.println("GOODBYE");
                    System.exit(0);
                }
            }
        }
    }

}

