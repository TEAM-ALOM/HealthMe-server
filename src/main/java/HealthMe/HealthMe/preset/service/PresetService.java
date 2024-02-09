package HealthMe.HealthMe.preset.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.preset.domain.Preset;
import HealthMe.HealthMe.preset.dto.*;
import HealthMe.HealthMe.preset.repository.PresetRepository;
import HealthMe.HealthMe.user.domain.User;
import HealthMe.HealthMe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        List<PresetDto> presetDtos = presetSaveDto.getPresetDto();
        List<PresetDto> result = new ArrayList<>();
        for (PresetDto dto : presetDtos) {
            result.add(dto);
            presetRepository.save(dto.toEntity(user));
        }
        return result;
    }

//    public List<PresetFindResultDto> findPresetByUserEmailVersion2(PresetFindDto presetFindDto) throws CustomException{
//        if(presetFindDto == null){
//            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
//        }
//        String email = presetFindDto.getEmail();
//        if(email == null){
//            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
//        }
//
//        userRepository.findByEmail(email)
//                .orElseThrow(()-> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
//
//
//    }

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

        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        List<PresetFindResultDto> result = new ArrayList<>();
        List<Preset> presets = presetRepository.findByUserEmail(email);
        List<SplitExerciseDetailDto> splitExerciseDetails = new ArrayList<>();
        List<SplitExerciseDto> splitExercises = new ArrayList<>();
        if(presets.size()>0) {
            presetCnt = presets.get(0).getPresetNumber().intValue();
            exerciseCnt = presets.get(0).getExerciseNumber().intValue();
        }
        else{
            return new ArrayList<>();
        }

        for(int i =0; i< presets.size(); i++){
            if(exerciseCnt.intValue() != presets.get(i).getExerciseNumber()){
                SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                        .exerciseNumber(exerciseCnt)
                        .splitExerciseDetailDto(splitExerciseDetails)
                        .exerciseName(presets.get(i - 1).getExerciseName())
                        .category(presets.get(i - 1).getCategory())
                        .build();

                splitExercises.add(splitExerciseDto);
                exerciseCnt += 1;
                splitExerciseDetails = new ArrayList<>();
            }
                //preset number 초기값을 get으로 받아오기
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
        if(presets.size() > 0) {
            SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                    .exerciseNumber(exerciseCnt)
                    .splitExerciseDetailDto(splitExerciseDetails)
                    .exerciseName(presets.get(presets.size() - 1).getExerciseName())
                    .category(presets.get(presets.size() - 1).getCategory())
                    .build();

            splitExercises.add(splitExerciseDto);

            PresetFindResultDto presetFindResultDto = PresetFindResultDto.builder()
                    .email(email)
                    .presetNumber(presetCnt.longValue())
                    .presetName(presets.get(presets.size() - 1).getPresetTitle())
                    .splitPresetDto(splitExercises)
                    .build();
            result.add(presetFindResultDto);
        }

        return result;
    }

}
