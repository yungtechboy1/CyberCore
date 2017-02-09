/**
 * Difficulty.java
 * 
 * Created on 14:18:19
 */
package net.yungtechboy1.CyberCore.entities.autospawn;


/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz (mige)</a>
 *
 */
public enum Difficulty {
    
    PEACEFUL(0), EASY(1), NORMAL(2), HARD(3);

    private int difficulty;
    
    private Difficulty (int difficulty) {
        this.difficulty = difficulty;
    }
    
    public int getDifficulty () {
        return difficulty;
    }
    
    public static net.yungtechboy1.CyberCore.entities.autospawn.Difficulty getByDiffculty (int difficulty) {
        switch (difficulty) {
            case 0:
                return PEACEFUL;
            case 1:
                return EASY;
            case 2:  
                return NORMAL;
            case 3:
                return HARD;
            default:
                return PEACEFUL;
        }
    }
    
}
