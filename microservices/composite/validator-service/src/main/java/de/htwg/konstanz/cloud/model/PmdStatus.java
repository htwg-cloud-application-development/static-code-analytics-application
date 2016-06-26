package de.htwg.konstanz.cloud.model;

import lombok.Data;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Data
@SuppressWarnings("PMD")
public class PmdStatus {

    private int availablePmdInstances = 0;

    private Map<URI, String> blockedPmdInstancesList = new HashMap<>();

    private List<URI> availablePmdInstancesList = new ArrayList<>();

    private List<Future<String>> pmdTaskList = new ArrayList<>();

    public boolean containsBlockedPmdInstance(URI uri) {
        return blockedPmdInstancesList.containsKey(uri);
    }

    public boolean noInstanceAvailable() {
        return availablePmdInstancesList.isEmpty();
    }

    public boolean isServiceAvailable() {
        return availablePmdInstances > 0;
    }
}
