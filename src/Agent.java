import java.util.ArrayList;
import java.util.Scanner;

public class Agent {
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
