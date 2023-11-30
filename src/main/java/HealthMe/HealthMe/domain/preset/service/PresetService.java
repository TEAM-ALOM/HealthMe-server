package HealthMe.HealthMe.domain.preset.service;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import HealthMe.HealthMe.domain.preset.dto.PresetDto;
import HealthMe.HealthMe.domain.preset.repository.PresetRepository;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional    // 필요한지 확인

public class PresetService {
    private final PresetRepository presetRepository;
    private final UserRepository userRepository;

    // new preset -> preset table에 저장
    public PresetDto savePreset(PresetDto presetDto, User user){
        // errorcode 새로 만들어서 그걸로 throw 하도록 수정 필요?
        if(presetDto == null) {
            throw new IllegalArgumentException("PresetDto is null");
        }
        if(presetDto.getExerciseName() == null){
            throw new IllegalArgumentException("Exercise is null");
        }

        Preset newPreset = presetDto.toEntity(user);
        newPreset = presetRepository.save(newPreset);

        PresetDto newPresetDto = PresetDto.builder()
                .id(newPreset.getId())
                .presetNumber(newPreset.getPresetNumber())
                .weight(newPreset.getWeight())
                .setCount(newPreset.getSetCount())
                .repetitionCount(newPreset.getRepetitionCount())
                .exerciseName(newPreset.getExerciseName())
                .category(newPreset.getCategory())
                .userDto(newPreset.getUser())
                .build();

        return newPresetDto;
    }

    //db에 있는 프리셋 불러오기 ( userId를 통해 그 유저의 모든 프리셋), null 체크 추가?
    public List<PresetDto> findPresetByUserId(Long userId){
        if(userId == null){
            return new ArrayList<>();
            // null 들어와도 빈 객체 반환? 아님 throw exception?
        }

        List<Preset> presets = presetRepository.findByUserId(userId);

        // List<Preset> 이 null 인 경우 exception

        List<PresetDto> presetDtos = new ArrayList<>();

        /*
        User user = userRepository.findById(userId);
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .autoLogin(user.isAutoLogin())
                .build();
        */

        // List<Preset> to List<PresetDto>
        for(Preset preset : presets){
            PresetDto presetDto = PresetDto.builder()
                    .id(preset.getId())
                    .presetNumber(preset.getPresetNumber())
                    .weight(preset.getWeight())
                    .setCount(preset.getSetCount())
                    .repetitionCount(preset.getRepetitionCount())
                    .exerciseName(preset.getExerciseName())
                    .category(preset.getCategory())
                    .userDto(userDto)
                    .build();
            presetDtos.add(presetDto);
        }
        return presetDtos;
    }

}
