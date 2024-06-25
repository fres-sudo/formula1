package it.unicam.cs.mpmgc.formula1.model.track;

import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;

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
}
