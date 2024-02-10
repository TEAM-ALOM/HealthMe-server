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

    @Transactional
    public List<PresetFindResultDto> findPresetByUserEmailV2(PresetFindDto presetFindDto) throws CustomException{
        Integer presetCnt=0;
        if(presetFindDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = presetFindDto.getEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        
        List<Preset> presets = presetRepository.findByUserEmail(email);
        List<PresetDto> presetDtoList = new ArrayList<>();
        List<SplitPresetDto> splitPresetDtoList = new ArrayList<>();
        for (Preset preset : presets) {
            if(preset.getPresetNumber().intValue() != presetCnt.intValue()){
                splitPresetDtoList.add(SplitPresetDto.builder()
                        .id(presetCnt.longValue())
                        .presetDtoList(presetDtoList)
                        .build());
                presetDtoList = new ArrayList<>();
                presetCnt += 1;
            }
            PresetDto presetDto = PresetDto.builder()
                    .presetNumber(preset.getPresetNumber())
                    .presetTitle(preset.getPresetTitle())
                    .weight(preset.getWeight())
                    .setCount(preset.getSetCount())
                    .exerciseName(preset.getExerciseName())
                    .category(preset.getCategory())
                    .repetitionCount(preset.getRepetitionCount())
                    .exerciseNumber(preset.getExerciseNumber())
                    .build();
            presetDtoList.add(presetDto);

        }

        if(!presetDtoList.isEmpty()){
            splitPresetDtoList.add(SplitPresetDto.builder()
                    .id(presetCnt.longValue())
                    .presetDtoList(presetDtoList)
                    .build());
        }



        List<PresetFindResultDto> result = new ArrayList<>();
        for (SplitPresetDto splitPresetDto : splitPresetDtoList) {
            Integer exerciseNumber = 0;
            List<PresetDto> presetDto = splitPresetDto.getPresetDtoList();
            List<SplitExerciseDetailDto> splitExerciseDetails = new ArrayList<>();
            List<SplitExerciseDto> splitExerciseDtoList = new ArrayList<>();
            if(presetDto.size()>1) {
                for (int i = 0; i < presetDto.size(); i++) {
                    if (presetDto.get(i).getExerciseNumber() != exerciseNumber.intValue()) {  // 여기가 문제임 반복문 끝나서 암것도 못하네
                        SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                                .exerciseName(presetDto.get(i - 1).getExerciseName())
                                .exerciseNumber(presetDto.get(i - 1).getExerciseNumber().intValue())
                                .category(presetDto.get(i - 1).getCategory())
                                .splitExerciseDetailDto(splitExerciseDetails)
                                .build();
                        splitExerciseDtoList.add(splitExerciseDto);
                        splitExerciseDetails = new ArrayList<>();
                        exerciseNumber += 1;
                    }

                    SplitExerciseDetailDto splitExerciseDetailDto = SplitExerciseDetailDto.builder()
                            .weight(presetDto.get(i).getWeight())
                            .repetitionCount(presetDto.get(i).getRepetitionCount())
                            .setCount(presetDto.get(i).getSetCount())
                            .build();
                    splitExerciseDetails.add(splitExerciseDetailDto);
                }

                SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                        .exerciseName(presetDto.get(presetDto.size()-1).getExerciseName())
                        .exerciseNumber(presetDto.get(presetDto.size()-1).getExerciseNumber().intValue())
                        .category(presetDto.get(presetDto.size()-1).getCategory())
                        .splitExerciseDetailDto(splitExerciseDetails)
                        .build();
                splitExerciseDtoList.add(splitExerciseDto);
            }
            else{
                SplitExerciseDetailDto splitExerciseDetailDto = SplitExerciseDetailDto.builder()
                        .weight(presetDto.get(0).getWeight())
                        .repetitionCount(presetDto.get(0).getRepetitionCount())
                        .setCount(presetDto.get(0).getSetCount())
                        .build();

                splitExerciseDetails.add(splitExerciseDetailDto);

                SplitExerciseDto splitExerciseDto = SplitExerciseDto.builder()
                        .exerciseName(presetDto.get(0).getExerciseName())
                        .exerciseNumber(presetDto.get(0).getExerciseNumber().intValue())
                        .category(presetDto.get(0).getCategory())
                        .splitExerciseDetailDto(splitExerciseDetails)
                        .build();
                splitExerciseDtoList.add(splitExerciseDto);
            }

            PresetFindResultDto presetFindResultDto = PresetFindResultDto.builder()
                    .presetName(splitPresetDto.getPresetDtoList().get(0).getPresetTitle())
                    .email(email)
                    .presetNumber(splitPresetDto.getPresetDtoList().get(0).getPresetNumber())
                    .splitPresetDto(splitExerciseDtoList)
                    .build();
            result.add(presetFindResultDto);
        }
        return result;
    }

    @Transactional
    public void deletePreset(PresetDeleteDto presetDeleteDto) throws CustomException {
        if(presetDeleteDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = presetDeleteDto.getEmail();
        Long presetNumber = presetDeleteDto.getPresetNumber();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        if(presetNumber == null){
            throw new CustomException(ErrorCode.PRESET_NOT_FOUND);
        }

        userRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        presetRepository.deleteByPresetNumber(presetNumber);
        this.updatePresetNumber(email, presetNumber);
    }


    private void updatePresetNumber(String email, Long presetNumber){
        List<Preset> byUserEmail = presetRepository.findByUserEmail(email);
        for (Preset preset : byUserEmail) {
            if(preset.getPresetNumber() > presetNumber){
                preset.setPresetNumber(preset.getPresetNumber()-1);
                presetRepository.save(preset);
            }
        }
    }
}
