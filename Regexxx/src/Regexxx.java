import java.util.*;

public class Regexxx {

    private int maxDistance;
    private String pattern;
    private Set<String> variations;
    private String command;

    public Regexxx(String pattern, String command, int maxDistance) {
        this.pattern = pattern;
        this.maxDistance = maxDistance;
        this.command = removeDuplicates(command);
        
    }

    public boolean match(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        this.variations = getVariations(input, maxDistance);
        for (String string : variations) {
			if(string.matches(pattern))return true;
		}
        return false;
    }

    private Set<String> getVariations(String str, int maxDistance) {
        Set<String> variations = new HashSet<>();
        variations.add(str);
        if (maxDistance == 0) {
            
            return variations;
        }
        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            switch(c)
            {
            	case 'd':
            		variations.addAll(getDeletions(str, maxDistance));
            		break;
            	case 'i':
                    variations.addAll(getInsertions(str, maxDistance));
                    break;
            	case 's':
            		variations.addAll(getSubstitutions(str, maxDistance));
            		break;
            	case 't':
            		variations.addAll(getTranspositions(str, maxDistance));
            		break;      	
            }
        }
        

        
        
        return variations;
    }

    private Set<String> getDeletions(String str, int maxDistance) {
        Set<String> variations = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            String deletion = str.substring(0, i) + str.substring(i + 1);
            if (maxDistance == 1) {
                variations.add(deletion);
            } else {
                variations.addAll(getDeletions(deletion, maxDistance - 1));
            }
        }
        return variations;
    }

    private Set<String> getInsertions(String str, int maxDistance) {
        Set<String> variations = new HashSet<>();
        for (int i = 0; i <= str.length(); i++) {
            for (char c = ' '; c <= '~'; c++) {
                String insertion = str.substring(0, i) + c + str.substring(i);
                if (maxDistance == 1) {
                    variations.add(insertion);
                } else {
                    variations.addAll(getInsertions(insertion, maxDistance - 1));
                }
            }
        }
        return variations;
    }

    private Set<String> getSubstitutions(String str, int maxDistance) {
        Set<String> variations = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            for (char c = ' '; c <= '~'; c++) {
                String substitution = str.substring(0, i) + c + str.substring(i + 1);
                if (!substitution.equals(str)) {
                    if (maxDistance == 1) {
                        variations.add(substitution);
                    } else {
                        variations.addAll(getSubstitutions(substitution, maxDistance - 1));
                    }
                }
            }
        }
        return variations;
    }

    private Set<String> getTranspositions(String str, int maxDistance) {
        Set<String> variations = new HashSet<>();
        for (int i = 0; i < str.length() - 1; i++) {
            String transposition = str.substring(0, i) + str.charAt(i + 1) + str.charAt(i) + str.substring(i + 2);
            if (maxDistance == 1) {
                variations.add(transposition);
            } else {
                variations.addAll(getTranspositions(transposition, maxDistance - 1));
            }
        }
        return variations;
    }
    public static String removeDuplicates(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!set.contains(c)) {
                sb.append(c);
                set.add(c);
            }
        }
        return sb.toString();
    }

    
    public static void main(String[] args) {

		Regexxx regxxx = new Regexxx("password", "dist", 2);

		
		System.out.println(regxxx.match("p@ssword"));
		System.out.println(regxxx.match("password"));
		System.out.println(regxxx.match("hello"));
		
		regxxx = new Regexxx("pa...ord", "d", 4);

		
		System.out.println(regxxx.match("p@ssword"));
		System.out.println(regxxx.match("password"));
		System.out.println(regxxx.match("pannno"));
	}
}
