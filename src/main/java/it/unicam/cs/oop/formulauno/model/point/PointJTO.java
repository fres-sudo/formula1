package it.unicam.cs.oop.formulauno.model.point;

import org.json.JSONObject;


public record PointJTO(int x, int y) {

    public static final int AXIS_FACTOR = 10;

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
}
