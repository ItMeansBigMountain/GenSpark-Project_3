import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main {


    public static void main(String[] args) throws IOException {

        //__INIT__
        Object[] init = gameFunctionality.init();
        Scanner scanner = (Scanner) init[0];
        String word = (String) init[1];
        String original_word = (String) init[2];
        String[] secret_display_items = (String[]) init[3];
        Stack<String> hangman_body_items = (Stack<String>) init[4];
        Path path = (Path) init[5];
        Path hs_path = (Path) init[6];
        List<String> hs_lines = (List<String>) init[7];
        int high_score_int = (int) init[8];


        //MISSED LETTERS
        HashSet<Character> missed_letters = new HashSet<>();


        //HIGH SCORES INIT
        System.out.print("Please Enter Your Name: ");
        String name = scanner.nextLine();







        //RUNTIME GAME-LOOP VARIABLES INIT
        int score = 0;
        int lives = hangman_body_items.size();
        boolean valid = false;
        ArrayList<String> used_phrases = new ArrayList<String>();
        List<Serializable> validity;
        List<Serializable> output_data_manipulation;
        String answer;
        String user_guess = "";


        // MAIN GAME LOOP
        while (lives > 0) {
            score++;

            //DRAW GAME SCREEN
            gameFunctionality.draw_screen(secret_display_items);

            //USER INPUT & VALIDATION
            System.out.println("MISSED LETTERS: " + missed_letters.toString());
            System.out.println("\nPlease enter input \n");
            System.out.print(">  ");
            user_guess = scanner.nextLine().toUpperCase();
            validity = gameFunctionality.checkValidInput(user_guess, used_phrases, word);
            valid = (boolean) validity.get(0);
            used_phrases = (ArrayList<String>) validity.get(1);


            if (!valid) {
                System.out.println("INVALID INPUT...");
                lives--;
                String popped = hangman_body_items.pop();

                //SET HANGMAN FILE WITH LINE REPLACED
                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                lines.set(6 - lives - 1, popped);
                Files.write(path, lines, StandardCharsets.UTF_8);
                continue;
            }

            //JUDGE ANSWER
            output_data_manipulation = gameFunctionality.correct_answer_check(original_word, secret_display_items, word, user_guess, missed_letters);

            answer = (String) output_data_manipulation.get(0);
            word = (String) output_data_manipulation.get(1);
            missed_letters = (HashSet<Character>) output_data_manipulation.get(2);
            secret_display_items = (String[]) output_data_manipulation.get(3);


            if (answer.equals("complete")) {
                //YOU WIN
                hs_lines.add(   String.format("%s:%s",name , score)     );
                Files.write(hs_path, hs_lines, StandardCharsets.UTF_8);
                System.out.println(  high_score_int < score? "HIGH SCORE: " + high_score_int : "YOU BEAT THE HIGH SCORE!:  " + score  );
                System.out.println("CONGRATS! YOU WIN");
                System.out.println("Would you like to play again (y or n)");
                System.out.println("Please enter input \n");
                System.out.print(">  ");
                user_guess = scanner.nextLine().toUpperCase();
                if (user_guess.toLowerCase().startsWith("y")) {
                    init = gameFunctionality.init();
                    scanner = (Scanner) init[0];
                    word = (String) init[1];
                    original_word = (String) init[2];
                    secret_display_items = (String[]) init[3];
                    hangman_body_items = (Stack<String>) init[4];
                    path = (Path) init[5];
                    missed_letters = new HashSet<>();
                    used_phrases = new ArrayList<String>();
                    lives = hangman_body_items.size();
                    score = 0;

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

                //SET HANGMAN FILE WITH LINE REPLACED
                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                lines.set(6 - lives - 1, popped);
                Files.write(path, lines, StandardCharsets.UTF_8);
            }


            // OUT OF LIVES GAME-OVER
            if (lives == 0) {
                System.out.print("\nGAME OVER\n");
                gameFunctionality.draw_screen(secret_display_items);
                System.out.println("Correct Answer: " + word);

                System.out.println("Would you like to play again (y or n)");
                System.out.println("Please enter input \n");
                System.out.print(">  ");
                user_guess = scanner.nextLine().toUpperCase();
                if (user_guess.toLowerCase().startsWith("y")) {
                    init = gameFunctionality.init();
                    scanner = (Scanner) init[0];
                    word = (String) init[1];
                    original_word = (String) init[2];
                    secret_display_items = (String[]) init[3];
                    hangman_body_items = (Stack<String>) init[4];
                    path = (Path) init[5];
                    missed_letters = new HashSet<>();
                    used_phrases = new ArrayList<String>();
                    lives = hangman_body_items.size();
                    score = 0;
                } else {
                    System.out.println("GOODBYE");
                    System.exit(0);
                }
            }
        }
    }

}

