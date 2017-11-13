package app.sanfrancisco.movies.challenge.util;

import app.sanfrancisco.movies.challenge.dto.MovieDTO;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieUtil {


    public static List<MovieDTO> filterMoviesWithNullTitle(List<MovieDTO> movies){

        return movies.stream()
                .filter(dto -> Objects.nonNull(dto.getTitle())).collect(Collectors.toList());
    }


    public static List<MovieDTO> filterMoviesWithEmptyLocation(List<MovieDTO> movies){

        return movies.stream()
                .filter(dto ->  !StringUtils.isEmpty(dto.getLocations())).collect(Collectors.toList());
    }

    public static List<MovieDTO> filterMoviesWithEmptyTitle(List<MovieDTO> movies){

        return movies
                .stream()
                .filter(movieDTO -> !StringUtils.isEmpty(movieDTO.getLocations()))
                .collect(Collectors.toList());
    }

    public static List<MovieDTO> filterByTitle(List<MovieDTO> movies, String title){

        return movies.stream().filter(movie -> movie.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
    }
}
