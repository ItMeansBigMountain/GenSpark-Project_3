import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class gameFunctionalityTest {


    @BeforeEach
    void setUp() {
        //before test runs
    }

    @AfterEach
    void tearDown() {
        //runs after tests run
    }


    @Test
    void checkValidInput() {
        //FUNCTION PARAMETERS
        String user_input = "TEST" ;
        ArrayList<String> used_phrases = new ArrayList<String>() {{ add("TEST");  }};
        String word = "____ING" ;

        // EXPECTED (already input answer)
        List<Serializable> expected = new ArrayList<>(){{ add(false); add(new ArrayList<String>() {{ add("TEST"); }}); }};

        // ACTUAL
        List<Serializable> actual = gameFunctionality.checkValidInput(user_input,used_phrases , word );

        // TEST
        assertEquals(expected, actual);
    }


    @Test
    void correct_answer_check() {
        //FUNCTION PARAMETERS

        String word = "T___";
        String original_word = "TEST";
        String user_guess = "T" ;

        HashSet<Character> missed_letters = new HashSet<>();

        String[] secret_display_items = new String[word.length()];
        Arrays.fill(secret_display_items, "_"); ;



        //SCENARIO SIMULATION (winning game)
        List<Serializable> expected = Arrays.asList("complete",  "____" ,  missed_letters , new String[]{"T","_","_","_"} );
        List<Serializable> actual = gameFunctionality.correct_answer_check( original_word , secret_display_items, word, user_guess , missed_letters);

        expected.set(3 , Arrays.toString((String[]) expected.get(3)));
        actual.set(3 , Arrays.toString((String[]) actual.get(3)));


        assertEquals(expected, actual);



        //SCENARIO SIMULATION (carry on.. game)
        word = "TE_T";
        original_word = "TEST";
        user_guess = "T" ;
        missed_letters = new HashSet<>();

        expected = Arrays.asList("passed" ,  "_E__"  ,  missed_letters, new String[]{"T","_","_","T"} );
        actual = gameFunctionality.correct_answer_check(original_word , secret_display_items, word, user_guess , missed_letters);

        expected.set(3 , Arrays.toString((String[]) expected.get(3)));
        actual.set(3 , Arrays.toString((String[]) actual.get(3)));


        assertEquals(expected, actual);
    }






}