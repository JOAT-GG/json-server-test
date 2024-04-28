package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.exception.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class HashtagServiceImplTest {

    @MockBean
    private HashtagService hashtagService;

    @Test
    @DisplayName("주어진 플레이어 소환사 이름, 태그를 통해 해당 소환사의 해시 태그 목록을 반환한다")
    void doesReturnPlayersHashtags() throws Exception {
        // given - 상황 만들기
        String gameName = "07년생피노키오";
        String tagLine = "KR1";
        when(hashtagService.getPlayerHashtags(gameName, tagLine)).thenReturn(HashtagResponse.of(
                false, false, false
        ));

        // when - 동작
        HashtagResponse playerHashtags = hashtagService.getPlayerHashtags(gameName, tagLine); // 이걸 Mocking?
        HashtagResponse expectedResponse = HashtagResponse.of(false, false, false);

        // then - 검증
        assertEquals(playerHashtags.isOverAverageDeath(), expectedResponse.isOverAverageDeath());
        assertEquals(playerHashtags.isDoesPlayerDieMidToLateOften(), expectedResponse.isDoesPlayerDieMidToLateOften());
        assertEquals(playerHashtags.isDoesPlayUnskilledChampRecently(), expectedResponse.isDoesPlayUnskilledChampRecently());

    }

    @Test
    @DisplayName("존재하지 않는 (소환사의 이름+태그) 조합인 경우, InvalidGameNameOrTag Exception 이 일어난다.")
    void returnsExceptionWhenWrongGameNameOrTag() throws Exception {
        // given - 상황 만들기
        String gameName = "07년생피노키오";
        String tagLine = "Wrong";
        when(hashtagService.getPlayerHashtags(gameName, tagLine)).thenThrow(CustomException.class);

        // when - 동작, then - 검증
        assertThrows(CustomException.class,
                () -> hashtagService.getPlayerHashtags(gameName, tagLine));
    }
}