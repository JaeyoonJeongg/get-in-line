package com.example.getinline.service;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDto;
import com.example.getinline.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks private EventService sut;
    @Mock private EventRepository eventRepository;

    @DisplayName("검색 조건 없이 이벤트 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnsEntireEventList(){
        //Given
        given(eventRepository.findEvents(null,null,null,null,null))
                .willReturn(List.of(
                        createEventDto(1L,"오전 운동", true),
                        createEventDto(1L,"오후 운동", false)
                ));

        //When
        List<EventDto> list = sut.getEvents(null, null, null, null, null);

        //Then
        assertThat(list).hasSize(2);
        then(eventRepository).should().findEvents(null,null,null,null,null);
    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면, 검색된 결과를 출력하여 보여준다.")
    @Test
    void givenSearchParams_whenSearchingEvents_thenReturnsEventList(){
        //Given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2021,1,1,0,0,0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2021,1,2,0,0,0);

        given(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime))
                .willReturn(List.of(
                        createEventDto(1L,"오전 운동", eventStatus, eventStartDatetime, eventEndDatetime)
                ));
        //When
        List<EventDto> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);

        //Then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime);
                    assertThat(event.eventStartDatetime()).isBeforeOrEqualTo(eventStartDatetime);
                });
        then(eventRepository).should().findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchingExistingEvent_thenReturnsEvent() {
        //Given
        Long eventId = 1L;
        EventDto eventDto = createEventDto(1L, "오전 운동", true);
        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDto));

        //When
        Optional<EventDto> result = sut.getEvent(eventId);

        //Then
        assertThat(result).hasValue(eventDto);
        then(eventRepository).should().findEvent(eventId);

    }

    @DisplayName("이벤트 ID로 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchingNoneExistentEvent_thenReturnsEmptyOptional(){
        //Given
        Long eventId = 2L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        //When
        Optional<EventDto> result = sut.getEvent(eventId);

        //Then
        assertThat(result).isEmpty();
        then(eventRepository).should().findEvent(eventId);
    }

    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true로 보여준다.")
    @Test
    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue(){
        //Given
        EventDto dto = createEventDto(1L, "오후 운동", false);
        given(eventRepository.insertEvent(dto)).willReturn(true);

        //When
        boolean result = sut.createEvent(dto);
        //Then
        assertThat(result).isTrue();
        then(eventRepository).should().insertEvent(dto);
    }

    @DisplayName("이벤트 정보를 주지 않으면, 생성 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse(){
        //Given
        given(eventRepository.insertEvent(null)).willReturn(false);

        //When
        boolean result = sut.createEvent(null);
        //Then
        assertThat(result).isFalse();
        then(eventRepository).should().insertEvent(null);
    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue(){
        //Given
        Long eventId = 1L;
        EventDto dto = createEventDto(1L, "오후 운동", false);
        given(eventRepository.updateEvent(eventId,dto)).willReturn(true);

        //When
        boolean result = sut.modifyEvent(eventId, dto);

        //Then
        assertThat(result).isTrue();
        then(eventRepository).should().updateEvent(eventId, dto);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse(){
        //Given
        EventDto dto = createEventDto(1L, "오후 운동", false);
        given(eventRepository.updateEvent(null, dto)).willReturn(false);

        //When
        boolean result = sut.modifyEvent(null, dto);

        //Then
        assertThat(result).isFalse();
        then(eventRepository).should().updateEvent(null, dto);
    }

    @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNoEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse(){
        //Given
        Long eventId = 1L;
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);

        //When
        boolean result = sut.modifyEvent(eventId, null);

        //Then
        assertThat(result).isFalse();
        then(eventRepository).should().updateEvent(eventId, null);

    }

    @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true 로 보여준다.")
    @Test
    void givenNoEventId_whenDeleting_thenDeletesEventAndReturnsTrue(){
        //Given
        Long eventId = 1L;
        given(eventRepository.deleteEvent(eventId)).willReturn(true);
        //When
        boolean result = sut.removeEvent(eventId);

        //Then
        assertThat(result).isTrue();
        then(eventRepository).should().deleteEvent(eventId);

    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenDeleting_thenAbortsDeletingAndReturnsFalse(){
        //Given
        given(eventRepository.deleteEvent(null)).willReturn(false);

        //When
        boolean result = sut.removeEvent(null);

        //Then
        assertThat(result).isFalse();
        then(eventRepository).should().deleteEvent(null);

    }

    private EventDto createEventDto(
            Long placeId,
            String eventName,
            boolean isMorning
    ){
        String hourStart = isMorning ? "00" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDto(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
        );
    }

    private EventDto createEventDto(
            long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ){
        return EventDto.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}