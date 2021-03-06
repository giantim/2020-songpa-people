package com.songpapeople.hashtagmap.kakao.schedule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PeriodHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expression;
    private String member;

    @CreationTimestamp
    private Date changedDate;

    public PeriodHistory(String expression) {
        this.expression = expression;
        this.member = null;
    }

    public PeriodHistoryDto toDto() {
        PeriodHistoryDto dto = PeriodHistoryDto.builder()
                .id(id)
                .expression(expression)
                .member(member)
                .changedDate(changedDate)
                .build();
        return dto;
    }
}
