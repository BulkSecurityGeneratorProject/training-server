package com.training.web.rest;

import com.training.TrainingApp;

import com.training.domain.SectionContent;
import com.training.repository.SectionContentRepository;
import com.training.service.SectionContentService;
import com.training.service.dto.SectionContentDTO;
import com.training.service.mapper.SectionContentMapper;
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
 * Test class for the SectionContentResource REST controller.
 *
 * @see SectionContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class SectionContentResourceIntTest {

    private static final Integer DEFAULT_SECTION_TIME = 1;
    private static final Integer UPDATED_SECTION_TIME = 2;

    private static final String DEFAULT_SECTION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_ONE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVED_TWO = "AAAAAAAAAA";
    private static final String UPDATED_RESERVED_TWO = "BBBBBBBBBB";

    @Autowired
    private SectionContentRepository sectionContentRepository;

    @Autowired
    private SectionContentMapper sectionContentMapper;

    @Autowired
    private SectionContentService sectionContentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionContentMockMvc;

    private SectionContent sectionContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectionContentResource sectionContentResource = new SectionContentResource(sectionContentService);
        this.restSectionContentMockMvc = MockMvcBuilders.standaloneSetup(sectionContentResource)
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
    public static SectionContent createEntity(EntityManager em) {
        SectionContent sectionContent = new SectionContent()
            .sectionTime(DEFAULT_SECTION_TIME)
            .sectionTitle(DEFAULT_SECTION_TITLE)
            .sectionContent(DEFAULT_SECTION_CONTENT)
            .reservedOne(DEFAULT_RESERVED_ONE)
            .reservedTwo(DEFAULT_RESERVED_TWO);
        return sectionContent;
    }

    @Before
    public void initTest() {
        sectionContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSectionContent() throws Exception {
        int databaseSizeBeforeCreate = sectionContentRepository.findAll().size();

        // Create the SectionContent
        SectionContentDTO sectionContentDTO = sectionContentMapper.toDto(sectionContent);
        restSectionContentMockMvc.perform(post("/api/section-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionContentDTO)))
            .andExpect(status().isCreated());

        // Validate the SectionContent in the database
        List<SectionContent> sectionContentList = sectionContentRepository.findAll();
        assertThat(sectionContentList).hasSize(databaseSizeBeforeCreate + 1);
        SectionContent testSectionContent = sectionContentList.get(sectionContentList.size() - 1);
        assertThat(testSectionContent.getSectionTime()).isEqualTo(DEFAULT_SECTION_TIME);
        assertThat(testSectionContent.getSectionTitle()).isEqualTo(DEFAULT_SECTION_TITLE);
        assertThat(testSectionContent.getSectionContent()).isEqualTo(DEFAULT_SECTION_CONTENT);
        assertThat(testSectionContent.getReservedOne()).isEqualTo(DEFAULT_RESERVED_ONE);
        assertThat(testSectionContent.getReservedTwo()).isEqualTo(DEFAULT_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void createSectionContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionContentRepository.findAll().size();

        // Create the SectionContent with an existing ID
        sectionContent.setId(1L);
        SectionContentDTO sectionContentDTO = sectionContentMapper.toDto(sectionContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionContentMockMvc.perform(post("/api/section-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SectionContent in the database
        List<SectionContent> sectionContentList = sectionContentRepository.findAll();
        assertThat(sectionContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSectionContents() throws Exception {
        // Initialize the database
        sectionContentRepository.saveAndFlush(sectionContent);

        // Get all the sectionContentList
        restSectionContentMockMvc.perform(get("/api/section-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sectionContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionTime").value(hasItem(DEFAULT_SECTION_TIME)))
            .andExpect(jsonPath("$.[*].sectionTitle").value(hasItem(DEFAULT_SECTION_TITLE.toString())))
            .andExpect(jsonPath("$.[*].sectionContent").value(hasItem(DEFAULT_SECTION_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].reservedOne").value(hasItem(DEFAULT_RESERVED_ONE.toString())))
            .andExpect(jsonPath("$.[*].reservedTwo").value(hasItem(DEFAULT_RESERVED_TWO.toString())));
    }

    @Test
    @Transactional
    public void getSectionContent() throws Exception {
        // Initialize the database
        sectionContentRepository.saveAndFlush(sectionContent);

        // Get the sectionContent
        restSectionContentMockMvc.perform(get("/api/section-contents/{id}", sectionContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sectionContent.getId().intValue()))
            .andExpect(jsonPath("$.sectionTime").value(DEFAULT_SECTION_TIME))
            .andExpect(jsonPath("$.sectionTitle").value(DEFAULT_SECTION_TITLE.toString()))
            .andExpect(jsonPath("$.sectionContent").value(DEFAULT_SECTION_CONTENT.toString()))
            .andExpect(jsonPath("$.reservedOne").value(DEFAULT_RESERVED_ONE.toString()))
            .andExpect(jsonPath("$.reservedTwo").value(DEFAULT_RESERVED_TWO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSectionContent() throws Exception {
        // Get the sectionContent
        restSectionContentMockMvc.perform(get("/api/section-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSectionContent() throws Exception {
        // Initialize the database
        sectionContentRepository.saveAndFlush(sectionContent);
        int databaseSizeBeforeUpdate = sectionContentRepository.findAll().size();

        // Update the sectionContent
        SectionContent updatedSectionContent = sectionContentRepository.findOne(sectionContent.getId());
        // Disconnect from session so that the updates on updatedSectionContent are not directly saved in db
        em.detach(updatedSectionContent);
        updatedSectionContent
            .sectionTime(UPDATED_SECTION_TIME)
            .sectionTitle(UPDATED_SECTION_TITLE)
            .sectionContent(UPDATED_SECTION_CONTENT)
            .reservedOne(UPDATED_RESERVED_ONE)
            .reservedTwo(UPDATED_RESERVED_TWO);
        SectionContentDTO sectionContentDTO = sectionContentMapper.toDto(updatedSectionContent);

        restSectionContentMockMvc.perform(put("/api/section-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionContentDTO)))
            .andExpect(status().isOk());

        // Validate the SectionContent in the database
        List<SectionContent> sectionContentList = sectionContentRepository.findAll();
        assertThat(sectionContentList).hasSize(databaseSizeBeforeUpdate);
        SectionContent testSectionContent = sectionContentList.get(sectionContentList.size() - 1);
        assertThat(testSectionContent.getSectionTime()).isEqualTo(UPDATED_SECTION_TIME);
        assertThat(testSectionContent.getSectionTitle()).isEqualTo(UPDATED_SECTION_TITLE);
        assertThat(testSectionContent.getSectionContent()).isEqualTo(UPDATED_SECTION_CONTENT);
        assertThat(testSectionContent.getReservedOne()).isEqualTo(UPDATED_RESERVED_ONE);
        assertThat(testSectionContent.getReservedTwo()).isEqualTo(UPDATED_RESERVED_TWO);
    }

    @Test
    @Transactional
    public void updateNonExistingSectionContent() throws Exception {
        int databaseSizeBeforeUpdate = sectionContentRepository.findAll().size();

        // Create the SectionContent
        SectionContentDTO sectionContentDTO = sectionContentMapper.toDto(sectionContent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionContentMockMvc.perform(put("/api/section-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionContentDTO)))
            .andExpect(status().isCreated());

        // Validate the SectionContent in the database
        List<SectionContent> sectionContentList = sectionContentRepository.findAll();
        assertThat(sectionContentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSectionContent() throws Exception {
        // Initialize the database
        sectionContentRepository.saveAndFlush(sectionContent);
        int databaseSizeBeforeDelete = sectionContentRepository.findAll().size();

        // Get the sectionContent
        restSectionContentMockMvc.perform(delete("/api/section-contents/{id}", sectionContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SectionContent> sectionContentList = sectionContentRepository.findAll();
        assertThat(sectionContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionContent.class);
        SectionContent sectionContent1 = new SectionContent();
        sectionContent1.setId(1L);
        SectionContent sectionContent2 = new SectionContent();
        sectionContent2.setId(sectionContent1.getId());
        assertThat(sectionContent1).isEqualTo(sectionContent2);
        sectionContent2.setId(2L);
        assertThat(sectionContent1).isNotEqualTo(sectionContent2);
        sectionContent1.setId(null);
        assertThat(sectionContent1).isNotEqualTo(sectionContent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionContentDTO.class);
        SectionContentDTO sectionContentDTO1 = new SectionContentDTO();
        sectionContentDTO1.setId(1L);
        SectionContentDTO sectionContentDTO2 = new SectionContentDTO();
        assertThat(sectionContentDTO1).isNotEqualTo(sectionContentDTO2);
        sectionContentDTO2.setId(sectionContentDTO1.getId());
        assertThat(sectionContentDTO1).isEqualTo(sectionContentDTO2);
        sectionContentDTO2.setId(2L);
        assertThat(sectionContentDTO1).isNotEqualTo(sectionContentDTO2);
        sectionContentDTO1.setId(null);
        assertThat(sectionContentDTO1).isNotEqualTo(sectionContentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sectionContentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sectionContentMapper.fromId(null)).isNull();
    }
}
