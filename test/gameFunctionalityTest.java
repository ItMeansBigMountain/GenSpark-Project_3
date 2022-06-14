import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

        // EXPECTED
        List<Serializable> expected = new ArrayList<>(){{ add(false); add(new ArrayList<String>() {{ add("TEST"); }}); }};

        // ACTUAL
        List<Serializable> actual = gameFunctionality.checkValidInput(user_input,used_phrases , word );

        // TEST
        assertEquals(expected, actual);
    }


    @Test
    void correct_answer_check() {
        //FUNCTION PARAMETERS
        String word = "T____";
        String user_guess = "T" ;
        String[] secret_display_items = new String[word.length()];
        Arrays.fill(secret_display_items, "_"); ;



        String expected = "complete";
        String actual = gameFunctionality.correct_answer_check( secret_display_items , word, user_guess  ) ;
        assertEquals(expected, actual);
    }





}