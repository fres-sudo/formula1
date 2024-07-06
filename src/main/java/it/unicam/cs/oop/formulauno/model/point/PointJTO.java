package it.unicam.cs.oop.formulauno.model.point;

import it.unicam.cs.oop.formulauno.model.mapper.DTO;
import org.json.JSONObject;

import java.util.Objects;


public final class PointJTO extends DTO {

    public static final int AXIS_FACTOR = 10;
    private final int x;
    private final int y;

    public PointJTO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    /**
     * Method to create an instance of PointJTO from a JSON string.
     *
     * @param json the String that correspond to a Json
     * @return an instance of PointJTO
     */
    public static PointJTO fromJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        return new PointJTO(x * AXIS_FACTOR, y * AXIS_FACTOR);
    }

    @Override
    public String toString() {
        return "PointJTO[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PointJTO) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
