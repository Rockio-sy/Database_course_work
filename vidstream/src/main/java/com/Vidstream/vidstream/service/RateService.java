package com.Vidstream.vidstream.service;

import com.Vidstream.vidstream.dto.RateDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.Rate;
import com.Vidstream.vidstream.repository.RateRepository;
import com.Vidstream.vidstream.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateService {
    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private Converter converter;

    public void setRate(RateDTO dto) {
        Rate model = converter.dtoToRate(dto);
        try {
            rateRepository.saveRate(model);
        } catch (SQLException e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<RateDTO> getRate(Long videoId) {

        List<Rate> models = rateRepository.getRatesByVideoId(videoId);

        if (models.isEmpty()) {
            throw new CustomException("No data", HttpStatus.NOT_FOUND);
        }
        List<RateDTO> dtoS = new ArrayList<>();
        for (Rate r : models) {
            dtoS.add(converter.rateToDTO(r));
        }
        return dtoS;

    }
}
