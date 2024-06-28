package it.unicam.cs.mpmgc.formula1.model.track;

import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackJTO {
    private List<PointJTO> innerTrack;
    private List<PointJTO> outerTrack;
    private PointJTO startingPoint;

    public TrackJTO(List<PointJTO> innerTrack, List<PointJTO> outerTrack, PointJTO startingPoint) {
        this.innerTrack = innerTrack;
        this.outerTrack = outerTrack;
        this.startingPoint = startingPoint;
    }

    public List<PointJTO> getInnerTrack() {
        return innerTrack;
    }

    public List<PointJTO> getOuterTrack() {
        return outerTrack;
    }

    public PointJTO getStartingPoint() {
        return startingPoint;
    }

    public static TrackJTO fromJson(JSONObject jsonObject) {
        List<PointJTO> innerTrack = parsePoints(jsonObject.getJSONArray("inner_track"));
        List<PointJTO> outerTrack = parsePoints(jsonObject.getJSONArray("outer_track"));
        PointJTO startingPoint = PointJTO.fromJson(jsonObject.getJSONObject("starting_point").toString());

        return new TrackJTO(innerTrack, outerTrack, startingPoint);
    }

    private static List<PointJTO> parsePoints(JSONArray jsonArray) {
        List<PointJTO> points = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject pointJson = jsonArray.getJSONObject(i);
            points.add(PointJTO.fromJson(pointJson.toString()));
        }
        return points;
    }
}
