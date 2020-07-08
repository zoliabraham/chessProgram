package ChessGame.View;

import ChessGame.Vector;

public abstract class Renderable {
    Vector position;
    Vector size;

    public abstract void draw(DrawInfo drawInfo);

    Vector getStart() {
        return position;
    }

    Vector getCenter() {
        return new Vector(position.x + size.x / 2.0f, position.y + size.y / 2.0f);
    }

    public Vector getSize() {
        return size;
    }
}
