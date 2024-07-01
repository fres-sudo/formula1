package it.unicam.cs.mpmgc.formula1.model.mapper;

import it.unicam.cs.mpmgc.formula1.model.point.Point;
import it.unicam.cs.mpmgc.formula1.model.point.PointJTO;
import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointMapperTest {

    private final PointMapper pointMapper = new PointMapper();

    @Test
    public void testFromDTO() {
        PointJTO pointJTO = new PointJTO(5, 10);
        Point point = pointMapper.fromDTO(pointJTO);
        assertEquals(5, point.x());
        assertEquals(10, point.y());
    }

    @Test
    public void testToDTO() {
        Point point = new Point(7, 14);
        PointJTO pointJTO = pointMapper.toDTO(point);
        assertEquals(7, pointJTO.x());
        assertEquals(14, pointJTO.y());
    }
}
