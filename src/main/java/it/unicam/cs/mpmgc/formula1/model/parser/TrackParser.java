package it.unicam.cs.mpmgc.formula1.model.parser;

import it.unicam.cs.mpmgc.formula1.model.Track;
import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;

import java.io.IOException;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.stream.Collectors;

public class TrackParser implements Parser<Track> {

    private final PointMapper pointMapper;

    public TrackParser(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    @Override
    public Track parse(String filePath) throws IOException {

        List<PointJTO> pointDTOs = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pointObject = jsonArray.getJSONObject(i);
                int x = pointObject.getInt("x");
                int y = pointObject.getInt("y");
                pointDTOs.add(new PointJTO(x, y));
            }
        }
        List<Point> points = pointDTOs.stream()
                .map(pointMapper::fromDTO)
                .collect(Collectors.toList());

        return new Track(points);
    }
}
