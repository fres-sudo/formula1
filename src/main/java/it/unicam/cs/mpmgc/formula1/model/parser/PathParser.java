package it.unicam.cs.mpmgc.formula1.model.parser;

import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PathParser implements Parser<List<List<Point>>>{

    PointMapper pointMapper;

    public PathParser(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    @Override
    public List<List<Point>> parse(String filePath) throws IOException {
        JSONObject jsonObject;
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonObject = new JSONObject(tokener);
        }

        List<List<PointJTO>> pathListJTO = new ArrayList<>();

        List<List<Point>> pathList = new ArrayList<>();

        for(int i = 1; i <= 2; i ++){
            List<PointJTO> path = new TrackParser().parseTrackPoints(jsonObject.getJSONArray("player" + i));
            pathListJTO.add(path);
        }

        for (List<PointJTO> innerListJTO : pathListJTO) {
            List<Point> innerList = new ArrayList<>();
            for (PointJTO pointJTO : innerListJTO) {
                Point point = pointMapper.fromDTO(pointJTO);
                innerList.add(new Point(point.x() * 2, point.y() * 2));
            }
            pathList.add(innerList);
        }
        return pathList;
    }
}
