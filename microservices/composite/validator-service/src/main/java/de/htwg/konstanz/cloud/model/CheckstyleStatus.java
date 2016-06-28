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
public class CheckstyleStatus {

    private int availableCheckstyleInstances = 0;

    private List<URI> availableCheckstyleInstancesList = new ArrayList<>();

    private Map<URI, String> blockedCheckstyleInstancesList = new HashMap<>();

    private List<Future<String>> checkstyleTaskList = new ArrayList<>();

    /**
     * Check if instance is blcoked, because of use in another validation.
     * @param uri to service instance
     * @return true if instance blocked.
     */
    public boolean containsBlockedCheckstyleInstance(URI uri) {
        return blockedCheckstyleInstancesList.containsKey(uri);
    }

    /**
     * Validate if instances of checkstyle available
     * @return true if instances available
     */
    public boolean noInstanceAvailable() {
        return (availableCheckstyleInstancesList.isEmpty());
    }

    /**
     * Number of available instances.
     * @return number of available instances.
     */
    public boolean isServiceAvailable() {
        return availableCheckstyleInstances > 0;
    }
}
