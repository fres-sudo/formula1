package it.unicam.cs.mpmgc.formula1.model.mapper;

import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.track.TrackJTO;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackMapperTest {

    private final PointMapper pointMapper = new PointMapper();
    private final TrackMapper trackMapper = new TrackMapper(pointMapper);

    @Test
    public void testFromDTO() {
        List<PointJTO> innerTrackJTO = List.of(new PointJTO(1, 2));
        List<PointJTO> outerTrackJTO = List.of(new PointJTO(3, 4));
        List<PointJTO> validPositionsJTO = List.of(new PointJTO(5, 6));
        PointJTO startingPointJTO = new PointJTO(7, 8);

        TrackJTO trackJTO = new TrackJTO(innerTrackJTO, outerTrackJTO, validPositionsJTO, startingPointJTO);
        Track track = trackMapper.fromDTO(trackJTO);

        assertEquals(1, track.getInnerTrack().get(0).x());
        assertEquals(2, track.getInnerTrack().get(0).y());
        assertEquals(3, track.getOuterTrack().get(0).x());
        assertEquals(4, track.getOuterTrack().get(0).y());
        assertEquals(5, track.getValidPositions().get(0).x());
        assertEquals(6, track.getValidPositions().get(0).y());
        assertEquals(7, track.getStartPoint().x());
        assertEquals(8, track.getStartPoint().y());
    }

    @Test
    public void testToDTO() {
        List<Point> innerTrack = List.of(new Point(1, 2));
        List<Point> outerTrack = List.of(new Point(3, 4));
        List<Point> validPositions = List.of(new Point(5, 6));
        Point startingPoint = new Point(7, 8);

        Track track = new Track(innerTrack, outerTrack, List.of(), validPositions, startingPoint);
        TrackJTO trackJTO = trackMapper.toDTO(track);

        assertEquals(1, trackJTO.getInnerTrack().get(0).x());
        assertEquals(2, trackJTO.getInnerTrack().get(0).y());
        assertEquals(3, trackJTO.getOuterTrack().get(0).x());
        assertEquals(4, trackJTO.getOuterTrack().get(0).y());
        assertEquals(5, trackJTO.getValidPositions().get(0).x());
        assertEquals(6, trackJTO.getValidPositions().get(0).y());
        assertEquals(7, trackJTO.getStartingPoint().x());
        assertEquals(8, trackJTO.getStartingPoint().y());
    }
}

