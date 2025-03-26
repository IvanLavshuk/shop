package market.retail.shop.service;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CategoryDto;
import market.retail.shop.dto.CreateCategoryDto;
import market.retail.shop.dto.UserDto;
import market.retail.shop.entity.Category;
import market.retail.shop.entity.User;
import market.retail.shop.mapper.CategoryMapper;
import market.retail.shop.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Category " + id + "is not found"));

    }

    @Transactional(readOnly = true)
    public CategoryDto findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User with email " + name + " not found"));

    }

    @Transactional
    public  CategoryDto update(Integer id, CreateCategoryDto createCategoryDto){
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Category " + id + "is not found"));
        String name = createCategoryDto.getName();
        category.setName(name);
        Category saved =categoryRepository.save(category);
        return  categoryMapper.toDto(saved);

    }

    @Transactional
    public CategoryDto create(CreateCategoryDto createCategoryDto){
        String name = createCategoryDto.getName();
        Optional<Category> optionalCategory = categoryRepository
                .findByName(name);
        if(optionalCategory.isPresent()){
            throw new IllegalStateException("Category with this email already exists");
        }
            Category category = categoryMapper.toEntity(createCategoryDto);
            Category saved = categoryRepository.save(category);
            return  categoryMapper.toDto(saved);

    }
    @Transactional
    public void deleteById(Integer id) {
        Optional<Category> optionalCategoryDto = categoryRepository.findById(id);
        if (optionalCategoryDto.isEmpty()) {
            throw new IllegalStateException("User does not exist");
        }
        categoryRepository.deleteById(id);
    }
}
