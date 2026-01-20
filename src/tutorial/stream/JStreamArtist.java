package tutorial.stream;

import tutorial.models.album.Album;
import tutorial.models.album.Artist;
import tutorial.models.album.Track;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class JStreamArtist {

    private static final Function<Artist, Integer> getCount = artist -> artist.getMembers().size();

    public static void main(String[] args) {
        Album album1 = new Album(1L, "Album1");
        List<Track> tracks1 = asList(new Track(1L, "Bakai", 524),
                new Track(2L, "Violets for Your Furs", 378),
                new Track(3L, "Time Was", 451));
        album1.setTracks(tracks1);

        Album album2 = new Album(2L, "Album2");
        List<Track> tracks2 = asList(new Track(4L, "Conoraione", 124),
                new Track(5L, "Flowers in mind", 278),
                new Track(6L, "jester dexter", 421));
        album2.setTracks(tracks2);

        List<Album> albums = List.of(album1, album2);

        flatMap(albums);
        min(tracks1);
        max(tracks1);
        reduce();
        reduceExt();
        findLongTracks(albums);
        simpleSort(List.of(4, 3, 2, 1));
    }

    public static void flatMap(List<Album> albums) {
        albums.stream().flatMap(a -> a.getTracks().stream()).forEach(System.out::println);
    }

    public static void min(List<Track> tracks) {
        Track shortestTrack = tracks.stream()
                .min(comparing(track -> track.getLength()))
                .get();
    }

    public static void max(List<Track> tracks) {
        Track shortestTrack = tracks.stream()
                .max(comparing(track -> track.getLength()))
                .get();
    }

    public static void reduce() {
        int count = Stream.of(1, 2, 3)
                .reduce(0, (acc, element) -> acc + element); // = 6
    }

    public static void reduceExt() {
        BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
        int count = accumulator.apply(
                accumulator.apply(
                        accumulator.apply(0, 1),
                        2),
                3);
    }

    public static List<String> findLongTracks(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getTracks().stream())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .collect(toList());
    }

    public static void simpleSort(List<Integer> numbers) {
        List<Integer> sameOrder = numbers.stream()
                .sorted()
                .collect(toList());
    }

    // Trova la band con piu membri
    public Optional<Artist> biggestGroup(Stream<Artist> artists) {
        return artists.collect(maxBy(comparing(getCount)));
    }

    // Finding the average number of tracks for a list of albums
    public double averageNumberOfTracks(List<Album> albums) {
        return albums.stream()
                .collect(averagingInt(album -> album.getTracks().size()));
    }

    // Partitioning a stream of artists into bands and solo artists
    public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
        return artists.collect(partitioningBy(Artist::isSolo));
    }

    // Grouping albums by their main artist
    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(groupingBy(album -> album.getMainMusician()));
    }

    public void artistsJoin(Stream<Artist> artists) {
        String result = artists.map(Artist::getName)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    // Using collectors to count the number of albums for each artist
    public Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
        return albums.collect(groupingBy(album -> album.getMainMusician(),
                counting()));
    }

    // Using collectors to find the names of every album that an artist has produced
    public Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician, mapping(Album::getName, toList())));
    }

    //Using a reduce and a custom StringCombiner to pretty-print the names of artists
    public void stringCombinerPrettyPrintArtists(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .reduce(new StringCombiner(", ", "[", "]"),
                                StringCombiner::add,
                                StringCombiner::merge)
                        .toString();
    }

    // Collecting strings using a custom StringCollector
    public void stringCollector(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .collect(new StringCollector(", ", "[", "]"));
    }

    public void reductionAsCollector(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .collect(Collectors.reducing(
                                new StringCombiner(", ", "[", "]"),
                                name -> new StringCombiner(", ", "[", "]").add(name),
                                StringCombiner::merge))
                        .toString();
    }


    public int serialArraySum(List<Album> albums) {
        return albums.stream()
                .flatMap(t -> t.getTracks().stream())
                .mapToInt(Track::getLength)
                .sum();
    }

    /*
        When benchmarking the code in Examples 6-1 and 6-2 on a 4-core machine with 10
        albums, the sequential code was 8× faster. Upon expanding the number of albums to
        100, they were both equally fast, and by the time we hit 10,000 albums, the parallel code
        was 2.5× faster.
     */
    public int parallelArraySum(List<Album> albums) {
        return albums.parallelStream()
                .flatMap(t -> t.getTracks().stream())
                .mapToInt(Track::getLength)
                .sum();
    }

    // Using peek to log intermediate values
    public void peek(Album album) {
        Set<String> nationalities
                = album.getMusicians().stream()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .peek(nation -> System.out.println("Found nationality: " + nation))
                .collect(Collectors.<String>toSet());
    }
}
