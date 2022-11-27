package site.hobbyup.class_final_back.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.dto.ResponseDto;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;
import site.hobbyup.class_final_back.dto.user.UserRespDto.JoinRespDto;
import site.hobbyup.class_final_back.service.ProfileService;
import site.hobbyup.class_final_back.service.UserService;

@RequiredArgsConstructor
@RestController
public class ProfileApiController {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ProfileService profileService;

}
