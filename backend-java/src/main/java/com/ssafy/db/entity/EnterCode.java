package com.ssafy.db.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * 입장코드 모델 정의
 */
@Entity
@Getter
@NoArgsConstructor
public class EnterCode {

    // 난수 입장 코드
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @NotNull
    private boolean checked;

    @Builder
    public EnterCode(String id, Meeting meeting, boolean checked) {
        this.id = id;
        this.meeting = meeting;
        this.checked = checked;
    }

    // 입장코드 체크를 위한 편의 함수
    public void changeChecked() {
        this.checked = true;
    }
}
