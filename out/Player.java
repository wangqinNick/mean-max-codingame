import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.ArrayList;
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

    public Agent() {
        this.state = new State();
        this.in = new Scanner(System.in);
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
            if (player == ConstantField.MINE) state.myUnits.add(unit);
        }
    }

    public void think() {
        Unit target = state.wrecks.get(0);
        System.out.println(target.pos.x + " " + target.pos.y + " 300 FULL SPEED");
        System.out.println("WAIT");
        System.out.println("WAIT");
    }
}
class Pos {
    public int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
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
        }
    }
}
class ChessPlayer {
    public int score, rage;

    public ChessPlayer(int score, int rage) {
        this.score = score;
        this.rage = rage;
    }
}
final class ConstantField {
    protected static final int NUM_PLAYERS = 3;
    protected static final int MINE = 0;
}

class State {
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
}
