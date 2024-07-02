package it.unicam.cs.oop.formulauno.model.mapper;

import it.unicam.cs.oop.formulauno.model.point.Point;
import it.unicam.cs.oop.formulauno.model.point.PointJTO;
import it.unicam.cs.oop.formulauno.model.track.Track;
import it.unicam.cs.oop.formulauno.model.track.TrackJTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for mapping between TrackJTO and Track objects.
 */
public class TrackMapper implements DTOMapper<TrackJTO, Track> {

    private final PointMapper pointMapper;

    /**
     * Constructs a TrackMapper with a given PointMapper.
     *
     * @param pointMapper the PointMapper to use for mapping PointJTO to Point
     */
    public TrackMapper(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    /**
     * Maps a TrackJTO to a Track.
     *
     * @param dto the TrackJTO to map from
     * @return the mapped Track object
     */
    @Override
    public Track fromDTO(TrackJTO dto) {
        List<Point> innerTrack = dto.getInnerTrack().stream()
                .map(pointMapper::fromDTO)
                .toList();

        List<Point> outerTrack = dto.getOuterTrack().stream()
                .map(pointMapper::fromDTO)
                .toList();

        List<Point> validPositions = dto.getValidPositions().stream()
                .map(pointMapper::fromDTO)
                .toList();

        Point startingPoint = pointMapper.fromDTO(dto.getStartingPoint());

        List<Point> allPoints = Stream.concat(innerTrack.stream(), outerTrack.stream())
                .collect(Collectors.toList());

        return new Track(innerTrack, outerTrack, allPoints, validPositions, startingPoint);
    }

    /**
     * Maps a Track to a TrackJTO.
     *
     * @param model the Track to map from
     * @return the mapped TrackJTO object
     */
    @Override
    public TrackJTO toDTO(Track model) {
        List<PointJTO> innerTrack = model.getInnerTrack().stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());

        List<PointJTO> outerTrack = model.getOuterTrack().stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());

        List<PointJTO> validPositions = model.getValidPositions().stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());

        PointJTO startingPoint = pointMapper.toDTO(model.getStartPoint());

        return new TrackJTO(innerTrack, outerTrack, validPositions, startingPoint);
    }
}
