package com.goomoong.room9backend.domain.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReserveStatus {

    CANCEL("CANCEL", "결제실패, 취소 등으로 취소된상태"),
    WAITING("WAITING", "대기중(default) 생성될때 WAITING으로 생성되고, 결제가 되면 COMPLETE로 바뀜"),
    COMPLETE("COMPLETE", "예약 완료(예약하고 아직 finalDate가 되기 전 상태)"),
    DONE("DONE", "예약이 정상적으로 진행되고 사용까지 끝난 상태");

    private final String key;
    private final String value;
}
