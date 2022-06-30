import com.google.gson.Gson;

import java.io.File;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class gameFunctionality {

    public static void draw_screen(String[] secret_display_items) {
        //  DRAW HANGMAN
        try {
            File hangman = new File(Files.readString(Paths.get("src/hangman.txt"), StandardCharsets.UTF_8));
            System.out.println(hangman);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.print("\n");

        //draw secret word
        System.out.println(Arrays.toString(secret_display_items)
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
        );
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
                    word = word.substring(0, word.indexOf(user_guess.charAt(i))) + "_" + word.substring(word.indexOf(user_guess.charAt(i)) + 1);
                    found = true;
                } catch (Exception e) {
                    missed_letters.add(user_guess.charAt(i));
                }
                if (original_word.contains(String.valueOf(user_guess.charAt(i)))) {
                    missed_letters.remove(user_guess.charAt(i));
                }
            }
        }


        //CHECK IF WORD CONTAINS USER GUESS//
//        Object[] check = Arrays.stream(user_guess.split("")).filter(
//                (i) -> {
//                    if (original_word.contains(i)) {
//                        return true;
//                    } else {
//                        missed_letters.add(i);
//                        return false;
//                    }
//                }
//        ).toArray();
//
//
//        //BOOLEAN TO HANG THE MAN
//        if (check.length > 0) {
//            found = true;
//        } else {
//            found = false;
//        }
//

        //CHECK IF GAME COMPLETE
        if (Arrays.stream(word.split("")).filter(i -> i.equals("_")).toArray().length != original_word.length()) {
            complete = false;
        }


        //RETURN STATUS STATE
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





