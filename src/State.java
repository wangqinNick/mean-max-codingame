import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class State {
    public ArrayList<Unit> wrecks;
    public ArrayList<Unit> tanks;
    public ChessPlayer[] players;

    public State() {
        this.wrecks = new ArrayList<>();
        this.tanks = new ArrayList<>();
        this.players = new ChessPlayer[ConstantField.NUM_PLAYERS];
    }

    public void clear() {
        wrecks.clear();
        tanks.clear();
        players = new ChessPlayer[ConstantField.NUM_PLAYERS];
    }

    public static int compareDist(Pos a, Pos b, Pos c) {
        return a.distance(c) < b.distance(c) ? -1 : 1;
    }

    public Unit getClosestWreckToReaper() {
        if (wrecks.isEmpty()) return null;
        wrecks.sort((a, b) -> compareDist(a.pos, b.pos, getMyReaper().pos));
        return wrecks.get(0);
    }

    public Unit getClosestTankerToReaper() {
        if (tanks.isEmpty()) return null;
        tanks.sort((a, b) -> compareDist(a.pos, b.pos, getMyReaper().pos));
        return tanks.get(0);
    }

    public Unit getMyReaper() {
        return players[0].units.get(UnitType.REAPER);
    }

    public Unit getMyDestroyer() {
        return players[0].units.get(UnitType.DESTROYER);
    }

    public Unit getMyDoof() {return players[0].units.get(UnitType.DOOF);}

    public ChessPlayer getBestOpponent() {
        return players[1].score > players[2].score ? players[1] : players[2];
    }
}
