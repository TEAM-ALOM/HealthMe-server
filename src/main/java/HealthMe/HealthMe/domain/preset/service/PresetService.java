package HealthMe.HealthMe.domain.preset.service;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import HealthMe.HealthMe.domain.preset.dto.PresetDto;
import HealthMe.HealthMe.domain.preset.repository.PresetRepository;
import HealthMe.HealthMe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional    // 필요한지 확인

public class PresetService {
    private final PresetRepository presetRepository;
    /**
     * 1 : 새로운 프리셋을 preset table에 저장
     * 2 : db에 있는 프리셋 불러오기
     */

    // 1번 기능 presetSave 메소드, null 일 때 체크 추가?
    public Preset savePreset(PresetDto presetDto, User user){
        if(presetDto == null) {

        }
        if(presetDto.getExerciseName() == null){

        }
        Preset newPreset = presetDto.toEntity(user);
        return presetRepository.save(newPreset);
    }

    // 2번 기능 db에 있는 프리셋 불러오기 ( userId를 통해 그 유저의 모든 프리셋), null 체크 추가?
    // db에서 프리셋 받아오기 -> dto 변환 후 return?
    public List<PresetDto> findPresetByUserId(Long userId){           // preset 말고 presetDto 반환
        List<Preset> presets = presetRepository.findByUserId(userId);
        return presets.stream().map(PresetDto::from).collect(Collectors.toList());
    }

}
