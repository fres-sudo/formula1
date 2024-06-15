package it.unicam.cs.mpmgc.formula1.model;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class JSONTrackLoader implements TrackLoader {
    @Override
    public Track loadTrack(String filePath) throws IOException {
        List<Point> points = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONObject trackObject = jsonObject.getJSONObject("track");
            for (String key : trackObject.keySet()) {
                int x = Integer.parseInt(key);
                int y = trackObject.getInt(key);
                points.add(new Point(x, y));
            }
        }

        return new Track(points);
    }
}
