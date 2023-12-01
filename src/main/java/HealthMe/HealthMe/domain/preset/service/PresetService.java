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
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional    // 필요한지 확인

public class PresetService {
    private final PresetRepository presetRepository;
    private final UserRepository userRepository;

    // new preset -> preset table에 저장
    public PresetDto savePreset(PresetDto presetDto){
        // errorcode 새로 만들어서 그걸로 throw 하도록 수정 필요?
        if(presetDto == null) {
            throw new IllegalArgumentException("PresetDto is null");
        }
        if(presetDto.getExerciseName() == null){
            throw new IllegalArgumentException("Exercise is null");
        }

        // presetDto로 부터 userDto 얻어오기, userDto 널 exception 추가
        UserDto userDto = presetDto.getUserDto();
        if(userDto == null){
            throw new IllegalArgumentException("userDto is null");
        }

        User user = userDto.toEntity();

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
                .userDto(userDto)   // userdto 하나 만들어서 받아넣기
                .build();

        return newPresetDto;
    }

    //db에 있는 프리셋 불러오기 ( userId를 통해 그 유저의 모든 프리셋), null 체크 추가?
    // id X email로 받아옴
    public List<PresetDto> findPresetByUserEmail(UserDto userDto){
        if(userDto == null){
            throw new IllegalArgumentException("userDto is null");
        }

        String userEmail = userDto.getEmail();
        if(userEmail == null){
            throw new IllegalArgumentException("userEmail is null");
        }

        List<Preset> presets = presetRepository.findByUserEmail(userEmail);
        // List<Preset> 이 null 인 경우 exception

        List<PresetDto> presetDtos = new ArrayList<>();

        //findByEmail로 바꿔서 해야함, 매개변수도 userdto 받아서 해야 함
        //userDto 받으면서 userRepository로부터 받아올 필요 없이 매개변수로 들어온거 그냥 사용

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
