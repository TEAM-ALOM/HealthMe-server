package HealthMe.HealthMe.domain.preset.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.preset.domain.Preset;
import HealthMe.HealthMe.domain.preset.dto.*;
import HealthMe.HealthMe.domain.preset.repository.PresetRepository;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class PresetService {
    private final PresetRepository presetRepository;
    private final UserRepository userRepository;


    @Transactional
    public List<PresetDto> savePreset(PresetSaveDto presetSaveDto) throws CustomException {
        User user = userRepository.findByEmail(presetSaveDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        if(presetSaveDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        if(presetSaveDto.getPresetDto() == null){
            throw new CustomException(ErrorCode.PRESET_NOT_FOUND);
        }
        List<PresetDto> presetDto = presetSaveDto.getPresetDto();
        List<PresetDto> result = new ArrayList<>();
        for (PresetDto dto : presetDto) {
            result.add(dto);
            presetRepository.save(dto.toEntity(user));
        }
        return result;
    }


    /**
     * TODO : preset 별로 나누고 -> exercise 별로 나누고
     * 해당 exercise 순서에 맞게 정렬
     * 단일 반복문 2개 + dto 재설계
     */
    @Transactional
    public List<PresetFindResultDto> findPresetByUserEmail(PresetFindDto presetFindDto) throws CustomException {
        Integer exerciseCnt = 0;
        Integer presetCnt = 0;
        if(presetFindDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = presetFindDto.getEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));


        List<PresetFindResultDto> result = new ArrayList<>();
        List<Preset> presets = presetRepository.findByUserEmail(email);
        List<SplitExerciseDetailDto> splitExerciseDetails = new ArrayList<>();
        List<SplitExerciseDto> splitExercises = new ArrayList<>();

        for(int i =0; i< presets.size(); i++){
            if(exerciseCnt.intValue() != presets.get(i).getExerciseNumber()){
                SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                        .exerciseNumber(exerciseCnt)
                        .splitExerciseDetailDto(splitExerciseDetails)
                        .exerciseName(presets.get(i - 1).getExerciseName())
                        .category(presets.get(i-1).getCategory())
                        .build();

                splitExercises.add(splitExerciseDto);
                exerciseCnt += 1;
                splitExerciseDetails = new ArrayList<>();
            }

            if(presetCnt.intValue() != presets.get(i).getPresetNumber()){
                PresetFindResultDto presetFindResultDto = PresetFindResultDto.builder()
                        .email(email)
                        .presetNumber(presetCnt.longValue())
                        .presetName(presets.get(i - 1).getPresetTitle())
                        .splitPresetDto(splitExercises)
                        .build();
                result.add(presetFindResultDto);
                presetCnt += 1;
                exerciseCnt = 0;
                splitExercises = new ArrayList<>();
            }
            SplitExerciseDetailDto splitExerciseDetailDto = SplitExerciseDetailDto.builder()
                    .order(presets.get(i).getExerciseOrder())
                    .weight(presets.get(i).getWeight())
                    .setCount(presets.get(i).getSetCount())
                    .repetitionCount(presets.get(i).getRepetitionCount())
                    .build();
            splitExerciseDetails.add(splitExerciseDetailDto);

        }
        SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                .exerciseNumber(exerciseCnt)
                .splitExerciseDetailDto(splitExerciseDetails)
                .exerciseName(presets.get(presets.size()-1).getExerciseName())
                .category(presets.get(presets.size()-1).getCategory())
                .build();

        splitExercises.add(splitExerciseDto);

        PresetFindResultDto presetFindResultDto = PresetFindResultDto.builder()
                .email(email)
                .presetNumber(presetCnt.longValue())
                .presetName(presets.get(presets.size()-1).getPresetTitle())
                .splitPresetDto(splitExercises)
                .build();
        result.add(presetFindResultDto);


        return result;
    }

}
