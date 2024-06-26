package it.unicam.cs.mpmgc.formula1.model.parser;

import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.track.TrackJTO;
import it.unicam.cs.mpmgc.formula1.model.mapper.TrackMapper;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackParser implements Parser<Track> {

    private final TrackMapper trackMapper;

    public TrackParser() {
        PointMapper pointMapper = new PointMapper();
        this.trackMapper = new TrackMapper(pointMapper);
    }

    public TrackParser(TrackMapper trackMapper) {
        this.trackMapper = trackMapper;
    }

    private static final int AXIS_FACTOR = 10;

    @Override
    public Track parse(String filePath) throws IOException {

        JSONObject jsonObject;
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonObject = new JSONObject(tokener);
        }

        List<PointJTO> innerTrackDTOs = parseTrackPoints(jsonObject.getJSONArray("inner_track"));
        List<PointJTO> outerTrackDTOs = parseTrackPoints(jsonObject.getJSONArray("outer_track"));
        PointJTO startingPointDTO = parseStartingPoint(jsonObject.getJSONObject("starting_point"));

        TrackJTO trackJTO = new TrackJTO(innerTrackDTOs, outerTrackDTOs, startingPointDTO);
        return trackMapper.fromDTO(trackJTO);
    }

    public List<PointJTO> parseTrackPoints(JSONArray jsonArray) {
        List<PointJTO> pointDTOs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject pointObject = jsonArray.getJSONObject(i);
            int x = pointObject.getInt("x");
            int y = pointObject.getInt("y");
            pointDTOs.add(new PointJTO(x * AXIS_FACTOR, y * AXIS_FACTOR));
        }
        return pointDTOs;
    }

    private PointJTO parseStartingPoint(JSONObject jsonObject) {
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        return new PointJTO(x * AXIS_FACTOR, y * AXIS_FACTOR);
    }
}
