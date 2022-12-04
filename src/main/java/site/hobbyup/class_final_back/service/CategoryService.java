package site.hobbyup.class_final_back.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

}
