package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Stack;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import com.dkwondev.stackpedia_v2_api.repository.StackRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class StackServiceImpl implements StackService {

    private final StackRepository stackRepository;

    @Override
    public List<Stack> getAllStacks() {
        return stackRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Stack getStackById(Long id) {
        return unwrapStack(stackRepository.findById(id));
    }

    @Override
    public Stack getStackBySlug(String slug) {
        return stackRepository.findBySlug(slug);
    }

    @Override
    public Stack createStack(Stack stack) {
        return stackRepository.save(stack);
    }

    @Override
    public Stack updateStack(Long id, Stack stack) {
        return stackRepository.findById(id)
            .map(existingStack -> {
                existingStack.setName(stack.getName());
                existingStack.setDescription(stack.getDescription());
                existingStack.setSlug(stack.getSlug());
                return stackRepository.save(existingStack);
            }).orElseThrow(() -> new EntityNotFoundException("Stack with id: " + id + "does not exist."));
    }

    @Override
    public Stack patchStack(Long id, Map<String, Object> updates) {
        Stack existingStack = unwrapStack(stackRepository.findById(id));

        updates.forEach((field, value) -> {
            switch (field) {
                case "name":
                    if (value instanceof String) {
                        existingStack.setName((String) value);
                    }
                    break;
                case "description":
                    if (value instanceof String) {
                        existingStack.setDescription((String) value);
                    }
                    break;
                case "slug":
                    if (value instanceof String) {
                        existingStack.setSlug((String) value);
                    }
                    break;
            }
        });
        return null;
    }

    @Override
    public void deleteStack(Long id) {
        unwrapStack(stackRepository.findById(id));
        stackRepository.deleteById(id);
    }

    public static Stack unwrapStack(Optional<Stack> stack) {
        if (stack.isPresent()) {
            return stack.get();
        } else {
            throw new EntityNotFoundException("Technology not found.");
        }
    }
}
