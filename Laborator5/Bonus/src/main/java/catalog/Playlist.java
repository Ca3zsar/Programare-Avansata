package catalog;

import catalogEntries.Book;
import catalogEntries.CatalogEntry;
import catalogEntries.Movie;
import catalogEntries.Song;
import org.apache.commons.collections4.map.HashedMap;

import java.util.*;
import java.util.stream.Collectors;

public class Playlist {
    private final Catalog catalog;

    public Playlist(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * This algorithm is mainly a coloring problem algorithm, where the edges are between the entries with common
     * characteristics. First thing, the edges are identified and marked in a Map, then, the first entry is marked to be
     * played in the first day. Then, the others are marked according to the their "neighbours" characteristics.
     */
    public String createPlaylist() {
        Map<CatalogEntry, List<CatalogEntry>> edges = new HashMap<>();
        Map<CatalogEntry, Integer> colors = new HashMap<>();

        // Create the edges between the entries
        for (CatalogEntry entry : catalog.entries) {
            for (CatalogEntry toCompareEntry : catalog.entries) {
                // Check if they are not the same
                if (!entry.equals(toCompareEntry)) {
                    // Check if they are of the same class
                    if (entry.getClass().equals(toCompareEntry.getClass())) {
                        switch (entry.getClass().getSimpleName()) {
                            case "Song":
                                if (((Song) entry).getArtist().equals(((Song) toCompareEntry).getArtist()) ||
                                        ((Song) entry).getGenre().equals(((Song) toCompareEntry).getGenre()) ||
                                        ((Song) entry).getReleaseYear() == (((Song) toCompareEntry).getReleaseYear())
                                ) {

                                    if (edges.get(entry) == null) {
                                        edges.put(entry, new ArrayList<>(Collections.singletonList(toCompareEntry)));
                                    } else {
                                        List<CatalogEntry> temporaryEdge = edges.get(entry);
                                        temporaryEdge.add(toCompareEntry);
                                        edges.put(entry, temporaryEdge);
                                    }
                                }
                                break;
                            case "Book":
                                if (((Book) entry).getAuthor().equals(((Book) toCompareEntry).getAuthor())) {

                                    if (edges.get(entry) == null) {
                                        edges.put(entry, new ArrayList<>(Collections.singletonList(toCompareEntry)));
                                    } else {
                                        List<CatalogEntry> temporaryEdge = edges.get(entry);
                                        temporaryEdge.add(toCompareEntry);
                                        edges.put(entry, temporaryEdge);
                                    }
                                }
                                break;
                            case "Movie":
                                if (((Movie) entry).getGenre().equals(((Movie) toCompareEntry).getGenre()) ||
                                        ((Movie) entry).getReleaseYear() == (((Movie) toCompareEntry).getReleaseYear())) {

                                    if (edges.get(entry) == null) {
                                        edges.put(entry, new ArrayList<>(Collections.singletonList(toCompareEntry)));
                                    } else {
                                        List<CatalogEntry> temporaryEdge = edges.get(entry);
                                        temporaryEdge.add(toCompareEntry);
                                        edges.put(entry, temporaryEdge);
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }

        // The first entry will be played in the first day.
        colors.put(catalog.entries.get(0), 0);

        for (CatalogEntry entry : this.catalog.entries) {
            if (entry.equals(catalog.entries.get(0))) {
                continue;
            }

            if (edges.get(entry) == null) {
                colors.put(entry, 0);
                continue;
            }

            //Available colors
            Map<Integer, Boolean> available = new HashedMap<>();

            for (CatalogEntry neighbor : edges.get(entry)) {
                if (colors.get(neighbor) != null) {
                    available.put(colors.get(neighbor), false);
                }
            }

            int nextColor = 0;
            while (available.get(nextColor) != null) {
                nextColor++;
            }

            colors.put(entry, nextColor);
        }

        String solution;

        solution = colors.entrySet().stream().map(entry -> entry.getKey().getEntryName() + " is played on day " +
                                                entry.getValue()).collect(Collectors.joining("\n"));
        return solution;
    }
}
