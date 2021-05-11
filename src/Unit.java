public class Unit {
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
