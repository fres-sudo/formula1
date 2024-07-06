package it.unicam.cs.oop.formulauno.model.track;

import it.unicam.cs.oop.formulauno.model.mapper.DTO;
import it.unicam.cs.oop.formulauno.model.point.PointJTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackJTO extends DTO {
    private final List<PointJTO> innerTrack;
    private final List<PointJTO> outerTrack;
    private final List<PointJTO> validPositions;
    private final PointJTO startingPoint;

    public TrackJTO(List<PointJTO> innerTrack, List<PointJTO> outerTrack, List<PointJTO> validPositions, PointJTO startingPoint) {
        this.innerTrack = innerTrack;
        this.outerTrack = outerTrack;
        this.validPositions = validPositions;
        this.startingPoint = startingPoint;
    }

    public List<PointJTO> getInnerTrack() {
        return innerTrack;
    }

    public List<PointJTO> getOuterTrack() {
        return outerTrack;
    }

    public List<PointJTO> getValidPositions() {
        return validPositions;
    }

    public PointJTO getStartingPoint() {
        return startingPoint;
    }

    /**
     * Method to create an instance of TrackJTO from a JSON string.
     *
     * @param jsonObject instance of a Json Object to map
     * @return an instance of TrackJTO
     */
    public static TrackJTO fromJson(JSONObject jsonObject) {
        List<PointJTO> innerTrack = parsePoints(jsonObject.getJSONArray("inner_track"));
        List<PointJTO> outerTrack = parsePoints(jsonObject.getJSONArray("outer_track"));
        List<PointJTO> validPositions = parsePoints(jsonObject.getJSONArray("valid_positions"));
        PointJTO startingPoint = PointJTO.fromJson(jsonObject.getJSONObject("starting_point").toString());

        return new TrackJTO(innerTrack, outerTrack, validPositions, startingPoint);
    }

    /**
     * Helper method to parse alla the point of a Json Array and convert it to a List of PointJTO
     *
     * @param jsonArray instance of JSON Array
     * @return an instance of a List of PointJTO
     */
    private static List<PointJTO> parsePoints(JSONArray jsonArray) {
        List<PointJTO> points = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject pointJson = jsonArray.getJSONObject(i);
            points.add(PointJTO.fromJson(pointJson.toString()));
        }
        return points;
    }
}
