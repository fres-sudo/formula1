package it.unicam.cs.oop.formulauno.model.parser;

import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.model.mapper.PointMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static it.unicam.cs.oop.formulauno.model.point.PointJTO.AXIS_FACTOR;
import static it.unicam.cs.oop.formulauno.model.track.Track.TRACK_FACTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathParserTest {

    private final PointMapper pointMapper = new PointMapper();
    private final PathParser pathParser = new PathParser(pointMapper);

    @Test
    public void testParse() throws IOException {
        // Mock JSON data
        String json = """
        {
            "player1": [{"x": 1, "y": 2}],
            "player2": [{"x": 3, "y": 4}]
        }
        """;

        // Write mock JSON to temporary file
        Path tempFile = Files.createTempFile("testPath", ".json");
        Files.writeString(tempFile, json);

        String path = tempFile.toAbsolutePath().toString();
        List<List<Point>> pathList = pathParser.parse(path);

        assertEquals(AXIS_FACTOR * TRACK_FACTOR, pathList.getFirst().getFirst().x());
        assertEquals(2 * AXIS_FACTOR * TRACK_FACTOR, pathList.getFirst().getFirst().y());
        assertEquals(3 * AXIS_FACTOR * TRACK_FACTOR, pathList.get(1).getFirst().x());
        assertEquals(4 * AXIS_FACTOR * TRACK_FACTOR, pathList.get(1).getFirst().y());

        // Clean up
        Files.delete(tempFile);
    }
}
