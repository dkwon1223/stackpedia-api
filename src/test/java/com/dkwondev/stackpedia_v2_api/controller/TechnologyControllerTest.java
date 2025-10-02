package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.technology.TechnologyDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import com.dkwondev.stackpedia_v2_api.model.mapper.CategoryMapper;
import com.dkwondev.stackpedia_v2_api.model.mapper.TechnologyMapper;
import com.dkwondev.stackpedia_v2_api.service.TechnologyService;
import com.dkwondev.stackpedia_v2_api.utils.RSAKeyProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TechnologyController.class)
public class TechnologyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TechnologyService technologyService;

    @MockitoBean
    private TechnologyMapper technologyMapper;

    @MockitoBean
    private CategoryMapper categoryMapper;

    @MockitoBean
    private RSAKeyProperties rsaKeyProperties;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private List<Technology> testTechnologies;
    private List<TechnologyDTO> testTechnologiesDTO;
    private Technology reactTech;
    private Technology vueTech;
    private TechnologyDTO reactDTO;
    private TechnologyDTO vueDTO;

    @BeforeEach
    void setUp() {
        reactTech = new Technology(
                1L,
                "React",
                "A JavaScript library",
                "A JavaScript library for building user interfaces",
                "react",
                "https://reactjs.org",
                "https://github.com/facebook/react",
                "https://reactjs.org/docs",
                LocalDateTime.now(),
                new HashSet<>()
        );

        vueTech = new Technology(
                2L,
                "Vue.js",
                "The Progressive JavaScript Framework",
                "Vue.js is a progressive framework for building user interfaces",
                "vuejs",
                "https://vuejs.org",
                "https://github.com/vuejs/vue",
                "https://vuejs.org/guide/",
                LocalDateTime.now(),
                new HashSet<>()
        );

        testTechnologies = List.of(reactTech, vueTech);

        reactDTO = new TechnologyDTO(
                1L,
                "React",
                "A JavaScript library",
                "A JavaScript library for building user interfaces",
                "react",
                "https://reactjs.org",
                "https://github.com/facebook/react",
                "https://reactjs.org/docs",
                new HashSet<>(),
                LocalDateTime.now()
        );

        vueDTO = new TechnologyDTO(
                2L,
                "Vue.js",
                "The Progressive JavaScript Framework",
                "Vue.js is a progressive framework for building user interfaces",
                "vuejs",
                "https://vuejs.org",
                "https://github.com/vuejs/vue",
                "https://vuejs.org/guide/",
                new HashSet<>(),
                LocalDateTime.now()
        );

        testTechnologiesDTO = List.of(reactDTO, vueDTO);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnAllTechnologies() throws Exception {
        Mockito.when(technologyService.getAllTechnologies()).thenReturn(testTechnologies);
        Mockito.when(technologyMapper.technologiesToListDTOs(testTechnologies)).thenReturn(testTechnologiesDTO);

        mockMvc.perform(get("/api/technology/all"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].name").value("React"),
                        jsonPath("$[0].shortDescription").value("A JavaScript library"),
                        jsonPath("$[0].description").value("A JavaScript library for building user interfaces"),
                        jsonPath("$[0].slug").value("react"),
                        jsonPath("$[0].websiteUrl").value("https://reactjs.org"),
                        jsonPath("$[0].githubUrl").value("https://github.com/facebook/react"),
                        jsonPath("$[0].documentationUrl").value("https://reactjs.org/docs"),
                        jsonPath("$[1].id").value(2L),
                        jsonPath("$[1].name").value("Vue.js"),
                        jsonPath("$[1].shortDescription").value("The Progressive JavaScript Framework"),
                        jsonPath("$[1].description").value("Vue.js is a progressive framework for building user interfaces"),
                        jsonPath("$[1].slug").value("vuejs"),
                        jsonPath("$[1].websiteUrl").value("https://vuejs.org"),
                        jsonPath("$[1].githubUrl").value("https://github.com/vuejs/vue"),
                        jsonPath("$[1].documentationUrl").value("https://vuejs.org/guide/")
                );
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnTechnologyBySlug() throws Exception {
        Mockito.when(technologyService.getTechnologyBySlug("react")).thenReturn(reactTech);
        Mockito.when(technologyMapper.technologyToDTO(reactTech)).thenReturn(reactDTO);
        Mockito.when(technologyService.getTechnologyBySlug("vuejs")).thenReturn(vueTech);
        Mockito.when(technologyMapper.technologyToDTO(vueTech)).thenReturn(vueDTO);

        mockMvc.perform(get("/api/technology?slug=react"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1l),
                        jsonPath("$.name").value("React"),
                        jsonPath("$.shortDescription").value("A JavaScript library"),
                        jsonPath("$.description").value("A JavaScript library for building user interfaces"),
                        jsonPath("$.slug").value("react"),
                        jsonPath("$.websiteUrl").value("https://reactjs.org"),
                        jsonPath("$.githubUrl").value("https://github.com/facebook/react"),
                        jsonPath("$.documentationUrl").value("https://reactjs.org/docs")
                );

        mockMvc.perform(get("/api/technology?slug=vuejs"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(2L),
                        jsonPath("$.name").value("Vue.js"),
                        jsonPath("$.shortDescription").value("The Progressive JavaScript Framework"),
                        jsonPath("$.description").value("Vue.js is a progressive framework for building user interfaces"),
                        jsonPath("$.slug").value("vuejs"),
                        jsonPath("$.websiteUrl").value("https://vuejs.org"),
                        jsonPath("$.githubUrl").value("https://github.com/vuejs/vue"),
                        jsonPath("$.documentationUrl").value("https://vuejs.org/guide/")
                );
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnTechnologyById() throws Exception {
        Mockito.when(technologyService.getTechnologyById(1L)).thenReturn(reactTech);
        Mockito.when(technologyMapper.technologyToDTO(reactTech)).thenReturn(reactDTO);
        Mockito.when(technologyService.getTechnologyById(2L)).thenReturn(vueTech);
        Mockito.when(technologyMapper.technologyToDTO(vueTech)).thenReturn(vueDTO);

        mockMvc.perform(get("/api/technology/1"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1l),
                        jsonPath("$.name").value("React"),
                        jsonPath("$.shortDescription").value("A JavaScript library"),
                        jsonPath("$.description").value("A JavaScript library for building user interfaces"),
                        jsonPath("$.slug").value("react"),
                        jsonPath("$.websiteUrl").value("https://reactjs.org"),
                        jsonPath("$.githubUrl").value("https://github.com/facebook/react"),
                        jsonPath("$.documentationUrl").value("https://reactjs.org/docs")
                );

        mockMvc.perform(get("/api/technology/2"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(2L),
                        jsonPath("$.name").value("Vue.js"),
                        jsonPath("$.shortDescription").value("The Progressive JavaScript Framework"),
                        jsonPath("$.description").value("Vue.js is a progressive framework for building user interfaces"),
                        jsonPath("$.slug").value("vuejs"),
                        jsonPath("$.websiteUrl").value("https://vuejs.org"),
                        jsonPath("$.githubUrl").value("https://github.com/vuejs/vue"),
                        jsonPath("$.documentationUrl").value("https://vuejs.org/guide/")
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateTechnologyById() throws Exception {

        Technology reactTechUpdated = new Technology(
                1L,
                "React (with updates)",
                "A JavaScript library (with updates)",
                "A JavaScript library for building user interfaces (with updates)",
                "react",
                "https://reactjs.org",
                "https://github.com/facebook/react",
                "https://reactjs.org/docs",
                LocalDateTime.now(),
                new HashSet<>()
        );

        TechnologyDTO reactUpdatedDTO = new TechnologyDTO(
                1L,
                "React (with updates)",
                "A JavaScript library (with updates)",
                "A JavaScript library for building user interfaces (with updates)",
                "react",
                "https://reactjs.org",
                "https://github.com/facebook/react",
                "https://reactjs.org/docs",
                new HashSet<>(),
                LocalDateTime.now()
        );

        Mockito.when(technologyService.updateTechnology(eq(1L), any(Technology.class))).thenReturn(reactTechUpdated);
        Mockito.when(technologyMapper.technologyToDTO(any(Technology.class))).thenReturn(reactUpdatedDTO);

        mockMvc.perform(put("/api/technology/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reactTechUpdated))
                .with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name").value("React (with updates)"),
                        jsonPath("$.shortDescription").value("A JavaScript library (with updates)"),
                        jsonPath("$.description").value("A JavaScript library for building user interfaces (with updates)")
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldPatchTechnologyById() throws Exception {

    }
}