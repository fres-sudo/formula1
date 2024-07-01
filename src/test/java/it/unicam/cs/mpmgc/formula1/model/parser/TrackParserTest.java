package it.unicam.cs.mpmgc.formula1.model.parser;

import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.mapper.TrackMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

        assertEquals(1, track.getInnerTrack().get(0).x());
        assertEquals(2, track.getInnerTrack().get(0).y());
        assertEquals(3, track.getOuterTrack().get(0).x());
        assertEquals(4, track.getOuterTrack().get(0).y());
        assertEquals(5, track.getValidPositions().get(0).x());
        assertEquals(6, track.getValidPositions().get(0).y());
        assertEquals(7, track.getStartPoint().x());
        assertEquals(8, track.getStartPoint().y());

        // Clean up
        Files.delete(tempFile);
    }
}
