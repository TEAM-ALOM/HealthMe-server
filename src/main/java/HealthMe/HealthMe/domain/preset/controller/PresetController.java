package HealthMe.HealthMe.domain.preset.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.preset.dto.PresetDto;
import HealthMe.HealthMe.domain.preset.service.PresetService;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/preset")
public class PresetController {
    private final PresetService presetService;

    //@RequestBody 메소드에 한번만 사용 -> presetDto만 받아오기 (userDto 정보 같이 들어있다 가정, 나중에 상의)
    @PostMapping("/save")
    public ResponseEntity<PresetDto> savePreset(@RequestBody PresetDto presetDto) throws CustomException {  // Dto로 리턴 <PresetDto>
        PresetDto savedPresetDto = presetService.savePreset(presetDto);
        return new ResponseEntity<>(savedPresetDto, HttpStatus.CREATED);
    }

    // @PathVariable -> user/뒤의 userId를 파라미터로 전달 -> dto 받기 @RequestBody로 수정
    // 받아온 userDto에서 email 얻어온 다음 findPresetByUserEmail 실행
    @GetMapping("/by-email")
    public ResponseEntity<List<PresetDto>> findPresetByUserId(@RequestBody UserDto userDto) throws CustomException {

        List<PresetDto> presetDtos = presetService.findPresetByUserEmail(userDto);
        return new ResponseEntity<>(presetDtos, HttpStatus.OK);
    }

}
