import java.util.HashMap;
import java.util.Map;

public class ChessPlayer {
    public int score, rage;
    public Map<UnitType, Unit> units;

    public ChessPlayer(int score, int rage) {
        this.score = score;
        this.rage = rage;
        this.units = new HashMap<>();
    }

    public void clear(){
        units.clear();
    }
}
