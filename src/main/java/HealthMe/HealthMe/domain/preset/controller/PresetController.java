package HealthMe.HealthMe.domain.preset.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.preset.dto.*;
import HealthMe.HealthMe.domain.preset.service.PresetService;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/preset")
@Tag(name = "프리셋 관련 컨트롤러")
public class PresetController {
    private final PresetService presetService;

    //@RequestBody 메소드에 한번만 사용 -> presetDto만 받아오기 (userDto 정보 같이 들어있다 가정, 나중에 상의)
    @PostMapping("/save")
    @Operation(summary = "프리셋 저장", description = "해당 url로 요청시 프리셋을 저장")
    public ResponseEntity<List<PresetDto>> savePreset(@RequestBody PresetSaveDto presetSaveDto) throws CustomException {
        return new ResponseEntity<>(presetService.savePreset(presetSaveDto), HttpStatus.CREATED);
    }

    // @PathVariable -> user/뒤의 userId를 파라미터로 전달 -> dto 받기 @RequestBody로 수정
    // 받아온 userDto에서 email 얻어온 다음 findPresetByUserEmail 실행
    @PostMapping("/by-email")
    @Operation(summary = "프리셋을 email로 로드", description = "해당 url로 요청시 프리셋을 user의 email을 통해 로드")
    public ResponseEntity<List<PresetFindResultDto>> findPresetByUserId(@RequestBody PresetFindDto presetFindDto) throws CustomException {
        return new ResponseEntity<>(presetService.findPresetByUserEmail(presetFindDto), HttpStatus.OK);
    }

}
