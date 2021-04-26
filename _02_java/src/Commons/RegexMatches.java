package Commons;

import java.util.regex.Pattern;

public class RegexMatches {
    public boolean checkPattern (String input, String pattern){
        if(Pattern.matches(pattern, input)){
            return true;
        }else{
            return false;
        }

    }
}
