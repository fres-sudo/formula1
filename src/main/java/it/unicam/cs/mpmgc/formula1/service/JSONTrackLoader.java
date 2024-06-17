package it.unicam.cs.mpmgc.formula1.service;

import it.unicam.cs.mpmgc.formula1.model.Point;
import it.unicam.cs.mpmgc.formula1.model.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONTrackLoader implements TrackLoader {
    @Override
    public Track loadTrack(String filePath) throws IOException {
        List<Point> points = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pointObject = jsonArray.getJSONObject(i);
                int x = pointObject.getInt("x");
                int y = pointObject.getInt("y");
                points.add(new Point(x, y));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace(); // Handle or log exception as needed
        }

        return new Track(points);
    }
}
