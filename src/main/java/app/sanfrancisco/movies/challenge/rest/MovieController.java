package app.sanfrancisco.movies.challenge.rest;


import app.sanfrancisco.movies.challenge.dto.MovieDTO;
import app.sanfrancisco.movies.challenge.util.MovieUtil;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie")
public class MovieController {



    @RequestMapping(value = "/from-file",method = RequestMethod.GET)
    public ResponseEntity<List<MovieDTO>> getMoviesFromFile(@RequestParam(required = false) String title) {

        List<MovieDTO> result = MovieUtil.filterMoviesWithEmptyTitle(getData());

        if(Objects.nonNull(title)){

            result = MovieUtil.filterByTitle(result, title);
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/from-api", method = RequestMethod.GET)
    public ResponseEntity<List<MovieDTO>> getMoviesFromApi(@RequestParam(required = false) String title) {


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<MovieDTO>> response = restTemplate
                .exchange("https://data.sfgov.org/resource/wwmu-gmzc.json", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<MovieDTO>>() {
                        });
        List<MovieDTO> result = MovieUtil.filterMoviesWithNullTitle(response.getBody());
        if(Objects.nonNull(title)){

            result = MovieUtil.filterByTitle(result, title);
        }
        return ResponseEntity.ok(result);
    }

    private List<MovieDTO> getData(){

            try {
                CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
                CsvMapper mapper = new CsvMapper();
                File file = new ClassPathResource("data_movies.csv").getFile();
                MappingIterator<MovieDTO> readValues =
                        mapper.reader(MovieDTO.class).with(bootstrapSchema)
                                .readValues(file);
                return readValues.readAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

}
