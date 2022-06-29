import com.google.gson.Gson;

import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class gameFunctionality {

    public static void draw_screen(List displayItems, String[] secret_display_items) {
        // DRAW HANGMAN
        // for (int x = 0; x < displayItems.size(); x++) {
        //     System.out.println(displayItems.get(x));
        // }

        displayItems.stream().map(
                i -> {
                    System.out.println(i);
                    return i;
                }
        );

        System.out.print("\n");

        //draw secret word
        for (int x = 0; x < secret_display_items.length; x++) {
            System.out.print(secret_display_items[x]);
        }
        System.out.print("\n\n");
    }


    public static List<Serializable> checkValidInput(String user_input, ArrayList<String> used_phrases, String word) {
        boolean valid = false;
        if (used_phrases.contains(user_input)) {
            System.out.println("You already used that phrase!!");
        } else {
            if (!used_phrases.contains(user_input)) {
                used_phrases.add(user_input);
                valid = true;
            }
        }
        if (user_input.length() > word.length()) {
            System.out.println("Guess is too long!");
            valid = false;
        }


        return Arrays.asList(valid, used_phrases);
    }


    public static List<Serializable> correct_answer_check(String original_word, String[] secret_display_items, String word, String user_guess, HashSet missed_letters) {
        boolean found = false;
        boolean complete = true;
        String output = "";


        for (int y = 0; y < secret_display_items.length; y++) {

            for (int i = 0; i < user_guess.length(); i++) {
                try {
                    secret_display_items[word.indexOf(user_guess.charAt(i))] = String.valueOf(user_guess.charAt(i));
                    word = word.substring(0, word.indexOf(user_guess.charAt(i))) + '_' + word.substring(word.indexOf(user_guess.charAt(i)) + 1);
                    found = true;
                } catch (Exception e) {
                    missed_letters.add(user_guess.charAt(i));

                }

                if (original_word.contains(String.valueOf(user_guess.charAt(i)))) {
                    missed_letters.remove(user_guess.charAt(i));
                }
            }

        }


        // CHECK IF CODE CRACKED
        for (int x = 0; x < word.length(); x++) {
            if (word.charAt(x) != '_') {
                complete = false;
            }
        }


        if (complete) {
            output = "complete";
        } else if (found) {
            output = "passed";
        } else {
            output = "failed";
        }

        return Arrays.asList(output, word, missed_letters);

    }


}





