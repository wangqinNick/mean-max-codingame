import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
enum UnitType {
    REAPER(0),
    DESTROYER(1),
    DOOF(2),
    TANKER(3),
    WRECK(4);

    protected int value;

    UnitType(int value) {
        this.value = value;
    }
}

class Agent {
    public State state;
    public Scanner in;
    public ArrayList<Action> actions;

    public Agent() {
        this.state = new State();
        this.in = new Scanner(System.in);
        this.actions = new ArrayList<>();
    }

    public void read() {
        state.clear();
        /* read players info */
        int myScore = in.nextInt();
        int enemyScore1 = in.nextInt();
        int enemyScore2 = in.nextInt();
        int myRage = in.nextInt();
        int enemyRage1 = in.nextInt();
        int enemyRage2 = in.nextInt();
        ChessPlayer player0 = new ChessPlayer(myScore, myRage);
        ChessPlayer player1 = new ChessPlayer(enemyScore1, enemyRage1);
        ChessPlayer player2 = new ChessPlayer(enemyScore2, enemyRage2);
        state.players[0] = player0;
        state.players[1] = player1;
        state.players[2] = player2;

        /* read units info */
        int unitCount = in.nextInt();
        for (int i = 0; i < unitCount; i++) {
            int unitId = in.nextInt();
            int type = in.nextInt();
            int player = in.nextInt();
            float mass = in.nextFloat();
            int radius = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            int vx = in.nextInt();
            int vy = in.nextInt();
            int extra = in.nextInt();
            int extra2 = in.nextInt();
            UnitType unitType;
            switch (type) {
                case 0: unitType = UnitType.REAPER;break;
                case 1: unitType = UnitType.DESTROYER;break;
                case 2: unitType = UnitType.DOOF;break;
                case 3: unitType = UnitType.TANKER;break;
                case 4: unitType = UnitType.WRECK;break;
                default: unitType = null;
            }
            Unit unit = new Unit(unitId, unitType, player, radius, vx, vy, extra, extra2, x, y, mass);
            if (unitType == UnitType.WRECK) state.wrecks.add(unit);
            if (unitType == UnitType.TANKER) state.tanks.add(unit);
            switch (player) {
                case ConstantField.MINE: state.players[ConstantField.MINE].units.put(unitType, unit); break;
                case ConstantField.OPPONENT_1: state.players[ConstantField.OPPONENT_1].units.put(unitType, unit); break;
                case ConstantField.OPPONENT_2: state.players[ConstantField.OPPONENT_2].units.put(unitType, unit); break;
                default:
            }
        }
    }

    public void print() {
        for (Action action: actions) {
            action.print();
        }
    }

    public void think() {
        reaperPlan();
        destroyerPlan();
        doofPlan();
    }

    public void reaperPlan() {
        /* Reaper action */
        int thrust = 300;
        Unit myReaper = state.getMyReaper();
        Unit myDestroyer = state.getMyDestroyer();
        Unit reaperTarget = state.getClosestWreckToReaper();
        if (reaperTarget == null) reaperTarget = myDestroyer;
        System.out.println((reaperTarget.pos.x - myReaper.vx) + " " + (reaperTarget.pos.y - myReaper.vy) + " " + thrust + " REAPER");
    }

    public void destroyerPlan() {
        /* Tanker action */
        int thrust = 300;
        Unit myDestroyer = state.getMyDestroyer();
        Unit destroyerTarget = state.getClosestTankerToReaper();
        if (destroyerTarget == null) destroyerTarget = state.getBestOpponent().units.get(UnitType.REAPER);
        System.out.println((destroyerTarget.pos.x - myDestroyer.vx) + " " + (destroyerTarget.pos.y - myDestroyer.vy) + " " + thrust + " DESTROYER");

    }

    public void doofPlan() {
        /* Doof action */
        int thrust = 300;
        Unit myDoof = state.getMyDoof();
        Unit doofTarget = state.getBestOpponent().units.get(UnitType.REAPER);
        System.out.println((doofTarget.pos.x - myDoof.vx) + " " + (doofTarget.pos.y - myDoof.vy) + " " + thrust + " DOOF");
    }
}
class Pos {
    public int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Pos other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String[] args) {
        Agent agent = new Agent();
        // game loop
        while (true) {
            agent.read();
            agent.think();
            agent.print();
        }
    }
}
class Action {
    public Pos pos;
    public int thrust;
    public boolean isWait;

    public Action() {
    }

    public void set(Pos pos, int thrust) {
        this.pos = pos;
        this.thrust = thrust;
        this.isWait = false;
    }

    public void setWait() {
        this.isWait = true;
    }

    public void print() {
        if (isWait) System.out.println("WAIT");
        else System.out.println(pos.x + " " + pos.y + " " + thrust);
    }
}

class ChessPlayer {
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
final class ConstantField {
    protected static final int NUM_PLAYERS = 3;
    protected static final int MINE = 0;
    protected static final int OPPONENT_1 = 1;
    protected static final int OPPONENT_2 = 2;
}

class State {
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
class Unit {
    public int id, player, radius, vx, vy, extra, extra2;
    public UnitType unitType;
    public double mass;
    public Pos pos = null;

    public Unit(int id, UnitType unitType, int player, int radius, int vx, int vy, int extra, int extra2, int x, int y, double mass) {
        this.id = id;
        this.unitType = unitType;
        this.player = player;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.extra = extra;
        this.extra2 = extra2;
        this.pos = new Pos(x, y);
        this.mass = mass;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                '}';
    }
}
