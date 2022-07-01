import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class gameFunctionality {


    public static Object[] init() throws IOException {


        //USER INPUT DATA INIT
        Scanner scanner = new Scanner(System.in);
        String word = "";
        String original_word = "";



        //HIGH SCORE INIT
        Path hs_path = Paths.get("src/highScores.txt");
        List<String> hs_lines = Files.readAllLines(hs_path, StandardCharsets.UTF_8);
        List<Integer> all_scores = hs_lines.stream().map( i -> Integer.parseInt( i.strip().split(":")[1] ) ).collect(Collectors.toList());
        Collections.sort(all_scores);
        int highest_score = all_scores.get(0); ;




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

        word = "testing".toUpperCase();
        original_word = "testing".toUpperCase();


        //secret word display init STRING[ ]
        String[] secret_display_items = new String[word.length()];
        Arrays.fill(secret_display_items, "_");


        // ADDING TO HANGMAN DATA
        Stack<String> hangman_body_items = new Stack<String>();
        hangman_body_items.push("||     / \\                           ");
        hangman_body_items.push("||      |                             ");
        hangman_body_items.push("||    --|--                           ");
        hangman_body_items.push("||     ( )                            ");


        //DISPLAY FILE PATH
        Path p = Paths.get("src/hangman.txt");
        Path original_path = Paths.get("src/original_hangman.txt");

        List<String> lines = Files.readAllLines(original_path, StandardCharsets.UTF_8);
        Files.write(p, lines, StandardCharsets.UTF_8);

        return new Object[]{scanner, word, original_word, secret_display_items, hangman_body_items, p , hs_path , hs_lines , highest_score};
    }


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




//        for (int y = 0; y < secret_display_items.length; y++) {
//            for (int i = 0; i < user_guess.length(); i++) {
//                try {
//                    secret_display_items[word.indexOf(user_guess.charAt(i))] = String.valueOf(user_guess.charAt(i));
//                    word = word.substring(0, word.indexOf(user_guess.charAt(i))) + "_" + word.substring(word.indexOf(user_guess.charAt(i)) + 1);
//                    found = true;
//                }
//                catch (Exception e) {
//                    missed_letters.add(user_guess.charAt(i));
//                }
//
//                if (original_word.contains(String.valueOf(user_guess.charAt(i))))
//                {
//                    missed_letters.remove(user_guess.charAt(i));
//                }
//
//            }
//        }

       List<String> temp_word  =  Arrays.stream(word.split("")).map(i -> user_guess.contains(i) ? "_":i   ).collect(Collectors.toList());
       List<String> temp_display  =  Arrays.stream(word.split("")).map(i -> user_guess.contains(i) ? i:"_"   ).collect(Collectors.toList());

        word = temp_word.stream().reduce("" , (s,i)->s+i);
        secret_display_items = temp_display.stream().reduce("" , (s,i)->s+i).split("");



//        CHECK IF WORD CONTAINS USER GUESS//
        Object[] check = Arrays.stream(user_guess.split("")).filter(
                (i) -> {
                    if (original_word.contains(i)) {
                        return true;
                    } else {
                        missed_letters.add(i);
                        return false;
                    }
                }
        ).toArray();


        //BOOLEAN TO HANG THE MAN
        if (check.length > 0) {
            found = true;
        } else {
            found = false;
        }
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

        return Arrays.asList(output, word, missed_letters , secret_display_items);

    }


}





