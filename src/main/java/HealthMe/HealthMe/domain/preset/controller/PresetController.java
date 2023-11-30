package HealthMe.HealthMe.domain.preset.controller;

import HealthMe.HealthMe.domain.preset.dto.PresetDto;
import HealthMe.HealthMe.domain.preset.service.PresetService;
import HealthMe.HealthMe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("나중에 설정")
public class PresetController {
    private final PresetService presetService;

    // 1
    @PostMapping
    public ResponseEntity savePreset(@RequestBody PresetDto presetDto, @RequestBody User user){
        return new ResponseEntity(presetService.savePreset(presetDto,user), HttpStatus.OK);
    }
    //@RequestBody 한 메소드에 한개만 사용 가능 (한번의 HTTP 요청에는 하나의 본문 내용만 존재하기 때문)
    // >>> 그러면 user를 어떻게 받나 ?
    // >>> presetDto 내부의 userDto 사용해서 받기 ?
    // >>> 그러면 savePreset 메소드 의 매개변수 다 presetDto 하나만 받는걸로 수정?

    // 2
    @PostMapping("나중에 설정")
    public ResponseEntity<PresetDto> savePreset(@RequestBody PresetDto presetDto){  // Dto로 리턴 <PresetDto>
        PresetDto savedPresetDto = presetService.savePreset(presetDto, presetDto.getUserDto().toEntity());
        //presetService.savePreset이 지금 Preset를 리턴하게 돼있음 -> PresetDto 리턴하게 메소드 수정 필요

        //PresetDto savedPresetDto = presetService.savePreset(presetDto); // 메소드 매개변수 하나로 수정?
        //PresetDto savedPresetDto2 = presetService.savePreset(presetDto, presetDto.getUserDto().toEntity());
        // 아니면 여기서 service의 savePreset 부를땐 presetDto 내부의 userDto를 toEntity로 변환해서
        // 그걸 매개변수 값으로 넣어주면 상관 없나?
        return new ResponseEntity<>(savedPresetDto, HttpStatus.CREATED);
    }

    @GetMapping("user/userId")  // @PathVariable -> user/뒤의 userId를 파라미터로 전달
    public ResponseEntity<List<PresetDto>> findPresetByUserId(@PathVariable Long userId) {
        List<PresetDto> presetDtos = presetService.findPresetByUserId(userId);
        return new ResponseEntity<>(presetDtos, HttpStatus.OK);
    }



}
