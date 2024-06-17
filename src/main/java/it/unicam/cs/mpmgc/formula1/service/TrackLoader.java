package it.unicam.cs.mpmgc.formula1.service;

import it.unicam.cs.mpmgc.formula1.model.Track;

import java.io.IOException;

public interface TrackLoader {
    Track loadTrack(String filePath) throws IOException;
}

