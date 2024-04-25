package com.example.demo.service;

import com.example.demo.domain.RateResponse;
import com.example.demo.repository.MatchResultQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final MatchResultQueryRepository queryRepository;

    @Override
    public RateResponse getRateData() {
        return RateResponse.of(
                queryRepository.getTotalWinRate(),
                queryRepository.getChampionWinLoseRate(),
                queryRepository.getPositionWinRate()
        );
    }
}
