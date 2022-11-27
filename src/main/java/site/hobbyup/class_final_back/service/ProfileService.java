package site.hobbyup.class_final_back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.profile.ProfileRepository;

@RequiredArgsConstructor
@Service
public class ProfileService {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ProfileRepository profileRepository;

}
