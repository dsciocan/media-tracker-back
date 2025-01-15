package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.ShowDAO;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.duroc.mediatracker.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ShowServiceImplementation implements ShowService {

    @Autowired
    ShowRepository showRepository;


    @Override
    public ShowSearchResult getShowSearchResults(String query) throws IOException, InterruptedException {
        return ShowDAO.requestShowSearchData(query);
    }
}
