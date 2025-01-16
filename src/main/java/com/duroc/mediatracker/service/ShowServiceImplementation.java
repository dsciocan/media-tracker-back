package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.ShowDAO;
import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.info.TvGenres;
import com.duroc.mediatracker.model.show_detail.Genre;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.Result;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.duroc.mediatracker.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShowServiceImplementation implements ShowService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    EpisodeService episodeService;


    @Override
    public ShowSearchResult getShowSearchResults(String query) throws IOException, InterruptedException {
        ShowSearchResult showSearchResult = ShowDAO.requestShowSearchData(query);
        for(Result result : showSearchResult.results()) {
            String url = "https://image.tmdb.org/t/p/original" + result.getPoster_path();
            result.setPoster_path(url);
        }
        return showSearchResult;
    }

    @Override
    public ShowDetails getShowDetails(Long apiId) throws IOException, InterruptedException {
       ShowDetails showDetails =  ShowDAO.requestShowDetails(apiId);
       String url = "https://image.tmdb.org/t/p/original" + showDetails.getPoster_path();
       showDetails.setPoster_path(url);
       return showDetails;
    }

//    @Override
//    public Show getShowDetails2(Long id) throws IOException, InterruptedException {
//        ShowDetails showDetails =  ShowDAO.requestShowDetails(id);
//        String url = "https://image.tmdb.org/t/p/original" + showDetails.getPoster_path();
//        showDetails.setPoster_path(url);
//        List<TvGenres> showGenres = new ArrayList<>();
//        for(Genre genre : showDetails.getGenres()) {
//            for(TvGenres tvGenre : TvGenres.values()) {
//                if(Objects.equals(tvGenre.getName(), genre.name())) {
//                    showGenres.add(tvGenre);
//                }
//
//            }
//        }
//        Show savedShow = Show.builder()
//                .title(showDetails.getName())
//                .synopsis(showDetails.getOverview())
//                .releaseYear(Integer.parseInt(showDetails.getFirst_air_date().substring(0,4)))
//                .finishedYear(Integer.parseInt(showDetails.getLast_air_date().substring(0,4)))
//                .isComplete(!showDetails.isIn_production())
//                .posterUrl(showDetails.getPoster_path())
//                .genres(showGenres)
//                .numberOfSeasons(showDetails.getNumber_of_seasons())
//                .numberOfEpisodes(showDetails.getNumber_of_episodes())
//                .country(showDetails.getOrigin_country().getFirst())
//                .language(showDetails.getOriginal_language())
//                .build();
//        savedShow.setEpisodes(episodeService.getAllEpisodes2(showDetails.getId(), savedShow.getNumberOfSeasons()));
//        return savedShow;
//    }

    @Override
    public Show saveShowDetails(Long apiId) throws IOException, InterruptedException {
        ShowDetails showDetails =  getShowDetails(apiId);
        List<String> showGenres = new ArrayList<>();
        for(Genre genre : showDetails.getGenres()) {
            showGenres.add(genre.name());
        }
        boolean isComplete = !showDetails.isIn_production();
        Show savedShow = Show.builder()
                .title(showDetails.getName())
                .synopsis(showDetails.getOverview())
                .releaseYear(Integer.parseInt(showDetails.getFirst_air_date().substring(0,4)))
                .finishedYear(Integer.parseInt(showDetails.getLast_air_date().substring(0,4)))
                .isComplete(isComplete)
                .posterUrl(showDetails.getPoster_path())
                .genres(showGenres)
                .numberOfSeasons(showDetails.getNumber_of_seasons())
                .numberOfEpisodes(showDetails.getNumber_of_episodes())
                .country(showDetails.getOrigin_country().getFirst())
                .language(showDetails.getOriginal_language())
                .build();
        showRepository.save(savedShow);
        episodeService.saveEpisodes(showDetails.getId(), savedShow.getNumberOfSeasons(), savedShow);
        List<Episode> episodeList = episodeService.getSavedEpisodesByShowId(savedShow.getId());
        savedShow.setEpisodes(episodeList);
        showRepository.save(savedShow);
        return savedShow;
    }

    @Override
    public Show getSavedShow(Long id) {
        if(showRepository.findById(id).isPresent()) {
            return showRepository.findById(id).get();
        } else {
            return null;
        }
    }


    @Override
    public String deleteShowFromDb(Long id) {
        if(showRepository.existsById(id)) {
            showRepository.deleteById(id);
            return "Success";
        } else {
            return "Show with specified id could not be found";
        }
    }


}
