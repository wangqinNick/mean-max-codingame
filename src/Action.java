public class Action {
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
