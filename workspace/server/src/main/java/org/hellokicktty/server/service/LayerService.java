package org.hellokicktty.server.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.domain.Coordinate;
import org.hellokicktty.server.domain.Layer;
import org.hellokicktty.server.domain.RestrictLayer;
import org.hellokicktty.server.domain.SpareLayer;
import org.hellokicktty.server.repository.LayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LayerService {

    Logger log = LoggerFactory.getLogger(Logger.class);
    private final LayerRepository layerRepository;

    @PostConstruct
    void init() {
        // something happens ...
    }

    public List<Layer> groupRestrictLayers() {
        List<Layer> groupedLayers = new ArrayList<>();
        layerRepository
                .findAllRestrictLayers()
                .stream()
                .collect(Collectors
                        .groupingBy(
                                RestrictLayer::getId,
                                Collectors
                                        .mapping(l -> new Coordinate(l.getLat(), l.getLng()), Collectors.toList())))
                .forEach((id, coordinates) -> {
                    Layer layer = new Layer(id, coordinates);
                    groupedLayers.add(layer);
                });

        log.info("{} restrict layers called", groupedLayers.size());
        return groupedLayers;
    }

    public List<Layer> groupSpareLayers() {
        List<Layer> groupedLayers = new ArrayList<>();
        layerRepository
                .findAllSpareLayers()
                .stream()
                .collect(
                        Collectors
                                .groupingBy(
                                        SpareLayer::getId,
                                        Collectors
                                                .mapping(l -> new Coordinate(l.getLat(), l.getLng()), Collectors.toList())))
                .forEach((id, coordinates) -> {
                    Layer layer = new Layer(id, coordinates);
                    groupedLayers.add(layer);
                });

        log.info("{} spare layers called", groupedLayers.size());
        return groupedLayers;
    }
}
