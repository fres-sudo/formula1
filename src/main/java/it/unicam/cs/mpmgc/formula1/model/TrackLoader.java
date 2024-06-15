package it.unicam.cs.mpmgc.formula1.model;

import java.io.IOException;

public interface TrackLoader {
    Track loadTrack(String filePath) throws IOException;
}

