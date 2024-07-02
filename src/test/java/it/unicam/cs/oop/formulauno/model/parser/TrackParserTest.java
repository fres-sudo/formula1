package it.unicam.cs.oop.formulauno.model.parser;

import it.unicam.cs.oop.formulauno.model.track.Track;
import it.unicam.cs.oop.formulauno.model.mapper.PointMapper;
import it.unicam.cs.oop.formulauno.model.mapper.TrackMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static it.unicam.cs.oop.formulauno.model.point.PointJTO.AXIS_FACTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackParserTest {

    private final PointMapper pointMapper = new PointMapper();
    private final TrackMapper trackMapper = new TrackMapper(pointMapper);
    private final TrackParser trackParser = new TrackParser(trackMapper);

    @Test
    public void testParse() throws IOException {
        // Mock JSON data
        String json = """
        {
            "inner_track": [{"x": 1, "y": 2}],
            "outer_track": [{"x": 3, "y": 4}],
            "valid_positions": [{"x": 5, "y": 6}],
            "starting_point": {"x": 7, "y": 8}
        }
        """;

        // Write mock JSON to temporary file
        Path tempFile = Files.createTempFile("testTrack", ".json");
        Files.writeString(tempFile, json);

        Track track = trackParser.parse(tempFile.toString());

        assertEquals(AXIS_FACTOR, track.getInnerTrack().getFirst().x());
        assertEquals(2 * AXIS_FACTOR, track.getInnerTrack().getFirst().y());
        assertEquals(3 * AXIS_FACTOR, track.getOuterTrack().getFirst().x());
        assertEquals(4 * AXIS_FACTOR, track.getOuterTrack().getFirst().y());
        assertEquals(5 * AXIS_FACTOR, track.getValidPositions().getFirst().x());
        assertEquals(6 * AXIS_FACTOR, track.getValidPositions().getFirst().y());
        assertEquals(7 * AXIS_FACTOR, track.getStartPoint().x());
        assertEquals(8 * AXIS_FACTOR, track.getStartPoint().y());

        // Clean up
        Files.delete(tempFile);
    }
}
