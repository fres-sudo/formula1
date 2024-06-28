package it.unicam.cs.mpmgc.formula1.model.mapper;

import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.track.TrackJTO;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrackMapper implements DTOMapper<TrackJTO, Track>{

    private final PointMapper pointMapper;

    public TrackMapper(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }

    @Override
    public Track fromDTO(TrackJTO dto) {
        List<Point> innerTrack = dto.getInnerTrack().stream()
                .map(pointMapper::fromDTO)
                .toList();

        List<Point> outerTrack = dto.getOuterTrack().stream()
                .map(pointMapper::fromDTO)
                .toList();

        Point startingPoint = pointMapper.fromDTO(dto.getStartingPoint());

        List<Point> allPoints = Stream.concat(innerTrack.stream(), outerTrack.stream())
                .collect(Collectors.toList());

        Track track = new Track(allPoints);
        track.setStartingPoint(startingPoint);
        return track;
    }

    @Override
    public TrackJTO toDTO(Track model) {
        List<PointJTO> innerTrack = model.getInnerTrack().stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());

        List<PointJTO> outerTrack = model.getOuterTrack().stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());

        PointJTO startingPoint = pointMapper.toDTO(model.getStartPoint());

        return new TrackJTO(innerTrack, outerTrack, startingPoint);
    }
}
