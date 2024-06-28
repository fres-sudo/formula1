package it.unicam.cs.mpmgc.formula1.model.parser;

import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for parsing a JSON file into a list of lists of Point objects,
 * that represent the path of the bots.
 * It uses a PointMapper to map the parsed data from PointJTO to Point.
 */
public class PathParser implements Parser<List<List<Point>>> {

    private static final int TRACK_FACTOR = 2;
    private final PointMapper pointMapper;

    /**
     * Constructs a PathParser with a given PointMapper.
     *
     * @param pointMapper the PointMapper to use for mapping PointJTO to Point
     */
    public PathParser(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    /**
     * Parses a JSON file into a list of lists of Point objects that represent the path of the bots.
     *
     * @param filePath the path of the JSON file to parse
     * @return a list of lists of Point objects parsed from the JSON file.
     * @throws IOException if an I/O error occurs reading from the file
     */
    @Override
    public List<List<Point>> parse(String filePath) throws IOException {
        JSONObject jsonObject;
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonObject = new JSONObject(tokener);
        }

        List<List<PointJTO>> pathListJTO = new ArrayList<>();
        List<List<Point>> pathList = new ArrayList<>();

        for (int i = 1; i <= 2; i++) { //iterate over all player
            JSONArray playerArray = jsonObject.getJSONArray("player" + i);
            List<PointJTO> path = new ArrayList<>();
            for (int j = 0; j < playerArray.length(); j++) { //iterate over the list of points of the given player
                JSONObject pointObject = playerArray.getJSONObject(j);
                path.add(PointJTO.fromJson(pointObject.toString())); //parse the Json Point to PointJTO
            }
            pathListJTO.add(path);
        }

        for (List<PointJTO> innerListJTO : pathListJTO) { //convert the DTO to real Object
            List<Point> innerList = new ArrayList<>(); //define the
            for (PointJTO pointJTO : innerListJTO) {
                Point point = pointMapper.fromDTO(pointJTO); //map the object, from PointJTO to Point
                innerList.add(new Point(point.x() * TRACK_FACTOR, point.y() * TRACK_FACTOR)); // add the new "scaled" point
            }
            pathList.add(innerList);
        }

        return pathList;
    }
}
