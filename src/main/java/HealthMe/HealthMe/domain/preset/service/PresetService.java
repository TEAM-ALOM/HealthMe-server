package HealthMe.HealthMe.domain.preset.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
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
public class PresetService {
    private final PresetRepository presetRepository;
    private final UserRepository userRepository;

    /**
     * TODO : request body 리스트로 변경
     */
    @Transactional
    public List<PresetDto> savePreset(List<PresetDto> presetDtoList) throws CustomException {
        User user = userRepository.findByEmail(presetDtoList.get(0).getUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        List<PresetDto> result = new ArrayList<>();
        for (PresetDto presetDto : presetDtoList) {
        if(presetDto == null) {
            throw new CustomException(ErrorCode.PRESET_NOT_FOUND);
        }
        if(presetDto.getExerciseName() == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }
        if(presetDto.getPresetNumber() == null){
            throw new CustomException(ErrorCode.PRESET_NUMBER_NOT_FOUND);
        }
        if(presetDto.getRepetitionCount() == null){
            throw new CustomException(ErrorCode.REPETITION_COUNT_NOT_FOUND);
        }
        if(presetDto.getSetCount() == null){
            throw new CustomException(ErrorCode.SET_COUNT_NOT_FOUND);
        }
        String email = presetDto.getUserEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        Preset newPreset = presetDto.toEntity(user);
        presetRepository.save(newPreset);

        result.add(PresetDto.builder()
                .userEmail(email)
                .category(presetDto.getCategory())
                .presetNumber(presetDto.getPresetNumber())
                .exerciseName(presetDto.getExerciseName())
                .weight(presetDto.getWeight())
                .setCount(presetDto.getSetCount())
                .repetitionCount(presetDto.getRepetitionCount())
                .build());
        }




        return result;
    }

    //db에 있는 프리셋 불러오기 ( userId를 통해 그 유저의 모든 프리셋), null 체크 추가?
    // id X email로 받아옴
    public List<PresetDto> findPresetByUserEmail(UserDto userDto) throws CustomException {
        if(userDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        String userEmail = userDto.getEmail();
        if(userEmail == null){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        List<Preset> presets = presetRepository.findByUserEmail(userEmail);
        // List<Preset> 이 null 인 경우 exception
        if(presets == null){
            throw new CustomException(ErrorCode.PRESET_NOT_FOUND);
        }

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
                    .userEmail(userEmail)
                    .build();
            presetDtos.add(presetDto);
        }
        return presetDtos;
    }

}
