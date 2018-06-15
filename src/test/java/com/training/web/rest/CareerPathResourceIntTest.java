package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.CareerPath;
import com.training.repository.CareerPathRepository;
import com.training.service.CareerPathService;
import com.training.service.dto.CareerPathDTO;
import com.training.service.mapper.CareerPathMapper;
import com.training.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.training.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CareerPathResource REST controller.
 *
 * @see CareerPathResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class CareerPathResourceIntTest {

    private static final String DEFAULT_CAREER_PATH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAREER_PATH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAREER_PATH_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_CAREER_PATH_INTRODUCE = "BBBBBBBBBB";

    private static final String DEFAULT_CAREER_PATH_IMG = "AAAAAAAAAA";
    private static final String UPDATED_CAREER_PATH_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private CareerPathRepository careerPathRepository;

    @Autowired
    private CareerPathMapper careerPathMapper;

    @Autowired
    private CareerPathService careerPathService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCareerPathMockMvc;

    private CareerPath careerPath;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CareerPathResource careerPathResource = new CareerPathResource(careerPathService);
        this.restCareerPathMockMvc = MockMvcBuilders.standaloneSetup(careerPathResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerPath createEntity(EntityManager em) {
        CareerPath careerPath = new CareerPath()
            .careerPathName(DEFAULT_CAREER_PATH_NAME)
            .careerPathIntroduce(DEFAULT_CAREER_PATH_INTRODUCE)
            .careerPathImg(DEFAULT_CAREER_PATH_IMG)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return careerPath;
    }

    @Before
    public void initTest() {
        careerPath = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerPath() throws Exception {
        int databaseSizeBeforeCreate = careerPathRepository.findAll().size();

        // Create the CareerPath
        CareerPathDTO careerPathDTO = careerPathMapper.toDto(careerPath);
        restCareerPathMockMvc.perform(post("/api/career-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerPathDTO)))
            .andExpect(status().isCreated());

        // Validate the CareerPath in the database
        List<CareerPath> careerPathList = careerPathRepository.findAll();
        assertThat(careerPathList).hasSize(databaseSizeBeforeCreate + 1);
        CareerPath testCareerPath = careerPathList.get(careerPathList.size() - 1);
        assertThat(testCareerPath.getCareerPathName()).isEqualTo(DEFAULT_CAREER_PATH_NAME);
        assertThat(testCareerPath.getCareerPathIntroduce()).isEqualTo(DEFAULT_CAREER_PATH_INTRODUCE);
        assertThat(testCareerPath.getCareerPathImg()).isEqualTo(DEFAULT_CAREER_PATH_IMG);
        assertThat(testCareerPath.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testCareerPath.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createCareerPathWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = careerPathRepository.findAll().size();

        // Create the CareerPath with an existing ID
        careerPath.setId(1L);
        CareerPathDTO careerPathDTO = careerPathMapper.toDto(careerPath);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareerPathMockMvc.perform(post("/api/career-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerPathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CareerPath in the database
        List<CareerPath> careerPathList = careerPathRepository.findAll();
        assertThat(careerPathList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCareerPaths() throws Exception {
        // Initialize the database
        careerPathRepository.saveAndFlush(careerPath);

        // Get all the careerPathList
        restCareerPathMockMvc.perform(get("/api/career-paths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(careerPath.getId().intValue())))
            .andExpect(jsonPath("$.[*].careerPathName").value(hasItem(DEFAULT_CAREER_PATH_NAME.toString())))
            .andExpect(jsonPath("$.[*].careerPathIntroduce").value(hasItem(DEFAULT_CAREER_PATH_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].careerPathImg").value(hasItem(DEFAULT_CAREER_PATH_IMG.toString())))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getCareerPath() throws Exception {
        // Initialize the database
        careerPathRepository.saveAndFlush(careerPath);

        // Get the careerPath
        restCareerPathMockMvc.perform(get("/api/career-paths/{id}", careerPath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerPath.getId().intValue()))
            .andExpect(jsonPath("$.careerPathName").value(DEFAULT_CAREER_PATH_NAME.toString()))
            .andExpect(jsonPath("$.careerPathIntroduce").value(DEFAULT_CAREER_PATH_INTRODUCE.toString()))
            .andExpect(jsonPath("$.careerPathImg").value(DEFAULT_CAREER_PATH_IMG.toString()))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCareerPath() throws Exception {
        // Get the careerPath
        restCareerPathMockMvc.perform(get("/api/career-paths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerPath() throws Exception {
        // Initialize the database
        careerPathRepository.saveAndFlush(careerPath);
        int databaseSizeBeforeUpdate = careerPathRepository.findAll().size();

        // Update the careerPath
        CareerPath updatedCareerPath = careerPathRepository.findOne(careerPath.getId());
        // Disconnect from session so that the updates on updatedCareerPath are not directly saved in db
        em.detach(updatedCareerPath);
        updatedCareerPath
            .careerPathName(UPDATED_CAREER_PATH_NAME)
            .careerPathIntroduce(UPDATED_CAREER_PATH_INTRODUCE)
            .careerPathImg(UPDATED_CAREER_PATH_IMG)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        CareerPathDTO careerPathDTO = careerPathMapper.toDto(updatedCareerPath);

        restCareerPathMockMvc.perform(put("/api/career-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerPathDTO)))
            .andExpect(status().isOk());

        // Validate the CareerPath in the database
        List<CareerPath> careerPathList = careerPathRepository.findAll();
        assertThat(careerPathList).hasSize(databaseSizeBeforeUpdate);
        CareerPath testCareerPath = careerPathList.get(careerPathList.size() - 1);
        assertThat(testCareerPath.getCareerPathName()).isEqualTo(UPDATED_CAREER_PATH_NAME);
        assertThat(testCareerPath.getCareerPathIntroduce()).isEqualTo(UPDATED_CAREER_PATH_INTRODUCE);
        assertThat(testCareerPath.getCareerPathImg()).isEqualTo(UPDATED_CAREER_PATH_IMG);
        assertThat(testCareerPath.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testCareerPath.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingCareerPath() throws Exception {
        int databaseSizeBeforeUpdate = careerPathRepository.findAll().size();

        // Create the CareerPath
        CareerPathDTO careerPathDTO = careerPathMapper.toDto(careerPath);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCareerPathMockMvc.perform(put("/api/career-paths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerPathDTO)))
            .andExpect(status().isCreated());

        // Validate the CareerPath in the database
        List<CareerPath> careerPathList = careerPathRepository.findAll();
        assertThat(careerPathList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCareerPath() throws Exception {
        // Initialize the database
        careerPathRepository.saveAndFlush(careerPath);
        int databaseSizeBeforeDelete = careerPathRepository.findAll().size();

        // Get the careerPath
        restCareerPathMockMvc.perform(delete("/api/career-paths/{id}", careerPath.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerPath> careerPathList = careerPathRepository.findAll();
        assertThat(careerPathList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerPath.class);
        CareerPath careerPath1 = new CareerPath();
        careerPath1.setId(1L);
        CareerPath careerPath2 = new CareerPath();
        careerPath2.setId(careerPath1.getId());
        assertThat(careerPath1).isEqualTo(careerPath2);
        careerPath2.setId(2L);
        assertThat(careerPath1).isNotEqualTo(careerPath2);
        careerPath1.setId(null);
        assertThat(careerPath1).isNotEqualTo(careerPath2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerPathDTO.class);
        CareerPathDTO careerPathDTO1 = new CareerPathDTO();
        careerPathDTO1.setId(1L);
        CareerPathDTO careerPathDTO2 = new CareerPathDTO();
        assertThat(careerPathDTO1).isNotEqualTo(careerPathDTO2);
        careerPathDTO2.setId(careerPathDTO1.getId());
        assertThat(careerPathDTO1).isEqualTo(careerPathDTO2);
        careerPathDTO2.setId(2L);
        assertThat(careerPathDTO1).isNotEqualTo(careerPathDTO2);
        careerPathDTO1.setId(null);
        assertThat(careerPathDTO1).isNotEqualTo(careerPathDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(careerPathMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(careerPathMapper.fromId(null)).isNull();
    }
}
