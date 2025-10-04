package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Stack;

import java.util.List;
import java.util.Map;

public interface StackService {

    List<Stack> getAllStacks();

    Stack getStackById(Long id);

    Stack getStackBySlug(String slug);

    Stack createStack(Stack stack);

    Stack updateStack(Long id, Stack stack);

    Stack patchStack(Long id, Map<String, Object> updates);

    void deleteStack(Long id);
}
