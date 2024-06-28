package it.unicam.cs.mpmgc.formula1.model.mapper;

import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;

public class PointMapper implements DTOMapper<PointJTO, Point> {
    @Override
    public Point fromDTO(PointJTO dto) {
        return new Point(dto.x(), dto.y());
    }

    @Override
    public PointJTO toDTO(Point model) {
        return new PointJTO(model.x(), model.y());
    }
}
