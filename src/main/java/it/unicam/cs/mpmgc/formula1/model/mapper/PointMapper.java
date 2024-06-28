package it.unicam.cs.mpmgc.formula1.model.mapper;

import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;

/**
 * This class is responsible for mapping between PointJTO and Point objects.
 */
public class PointMapper implements DTOMapper<PointJTO, Point> {

    /**
     * Maps a PointJTO to a Point.
     *
     * @param dto the PointJTO to map from
     * @return the mapped Point object
     */
    @Override
    public Point fromDTO(PointJTO dto) {
        return new Point(dto.x(), dto.y());
    }

    /**
     * Maps a Point to a PointJTO.
     *
     * @param model the Point to map from
     * @return the mapped PointJTO object
     */
    @Override
    public PointJTO toDTO(Point model) {
        return new PointJTO(model.x(), model.y());
    }
}
