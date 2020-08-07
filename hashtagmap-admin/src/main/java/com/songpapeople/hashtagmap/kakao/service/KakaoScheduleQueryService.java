package com.songpapeople.hashtagmap.kakao.service;

import com.songpapeople.hashtagmap.exception.AdminErrorCode;
import com.songpapeople.hashtagmap.exception.AdminException;
import com.songpapeople.hashtagmap.kakao.schedule.PeriodHistory;
import com.songpapeople.hashtagmap.kakao.schedule.PeriodHistoryDto;
import com.songpapeople.hashtagmap.kakao.schedule.PeriodHistoryRepository;
import com.songpapeople.hashtagmap.kakao.schedule.model.Schedule;
import com.songpapeople.hashtagmap.kakao.schedule.repository.ScheduleRepository;
import com.songpapeople.hashtagmap.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KakaoScheduleQueryService {
    private final ScheduleRepository scheduleRepository;
    private final PeriodHistoryRepository historyRepository;

    public boolean getKakaoScheduleActiveStatus(String name) {
        Schedule schedule = scheduleRepository.findByName(name)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.NOT_FOUND_SCHEDULER,
                        String.format("%s : 스케쥴러가 존재하지 않습니다.", name)
                ));
        return schedule.isActive();
    }

    public CustomResponse<List<PeriodHistoryDto>> showPeriodHistory() {
        List<PeriodHistoryDto> histories = historyRepository.findAll()
                .stream()
                .map(PeriodHistory::toDto)
                .collect(Collectors.toList());

        return CustomResponse.of(histories);
    }
}
