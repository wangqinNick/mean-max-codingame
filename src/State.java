import java.util.ArrayList;

public class State {
    public ArrayList<Unit> wrecks;
    public ArrayList<Unit> myUnits;
    public ChessPlayer[] players;

    public State() {
        this.wrecks = new ArrayList<>();
        this.myUnits = new ArrayList<>();
        this.players = new ChessPlayer[ConstantField.NUM_PLAYERS];
    }

    public void clear() {
        wrecks.clear();
        myUnits.clear();
        players = new ChessPlayer[ConstantField.NUM_PLAYERS];
    }
}
