package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import com.dkwondev.stackpedia_v2_api.repository.TechnologyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;

    @Override
    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    @Override
    public Technology getTechnologyById(Long technologyId) {
        return unwrapTechnology(technologyRepository.findById(technologyId), technologyId);
    }

    @Override
    public Technology getTechnologyBySlug(String slug) {
        Technology technology = technologyRepository.findBySlug(slug);
        if (technology == null) {
            throw new EntityNotFoundException("Technology not found.");
        }
        return technology;
    }

    @Override
    public Technology createTechnology(Technology technology) {
        return technologyRepository.save(technology);
    }

    @Override
    public Technology updateTechnology(Long technologyId, Technology technology) {
        return technologyRepository.findById(technologyId)
                .map(existingTech -> {
                    existingTech.setName(technology.getName());
                    existingTech.setShortDescription(technology.getShortDescription());
                    existingTech.setDescription(technology.getDescription());
                    existingTech.setSlug(technology.getSlug());
                    existingTech.setWebsiteUrl(technology.getWebsiteUrl());
                    existingTech.setGithubUrl(technology.getGithubUrl());
                    existingTech.setDocumentationUrl(technology.getDocumentationUrl());
                    existingTech.setLogoUrl(technology.getLogoUrl());
                    return technologyRepository.save(existingTech);
                })
                .orElseThrow(() -> new EntityNotFoundException("Technology with id:" + technologyId + " does not exist"));
    }


    @Override
    public void deleteTechnology(Long technologyId) {
        unwrapTechnology(technologyRepository.findById(technologyId), technologyId);
        technologyRepository.deleteById(technologyId);
    }

    static Technology unwrapTechnology(Optional<Technology> entity, Long technologyId) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException("Technology not found.");
        }
    }
}
