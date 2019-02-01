package zws.space;

public class Grid {

    int gridId;
    int x;
    int y;
    long mask;
    Object placeholder;

    public Grid(int gridId, int x, int y, long mask, Object placeholder) {
        this.gridId = gridId;
        this.x = x;
        this.y = y;
        this.mask = mask;
        this.placeholder = placeholder;
    }

}
