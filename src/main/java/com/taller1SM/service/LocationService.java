package com.taller1SM.service;
import java.util.Optional;

import com.taller1SM.model.prod.Location;

public interface LocationService {
	
public void saveLocation(Location location);
public Location editLocation(Integer id,Location location);
public Iterable<Location> findAll();
public Optional<Location> findById(Integer id);
}
