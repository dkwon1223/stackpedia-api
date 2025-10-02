package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.stack.StackDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Stack;
import com.dkwondev.stackpedia_v2_api.model.mapper.StackMapper;
import com.dkwondev.stackpedia_v2_api.service.StackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stack")
@AllArgsConstructor
public class StackController {

    private StackMapper stackMapper;
    private StackService stackService;

    @GetMapping("/all")
    public ResponseEntity<List<StackDTO>> getAllStacks() {
        List<Stack> stacks = stackService.getAllStacks();
        return new ResponseEntity<>(stackMapper.stacksToListDTOs(stacks), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StackDTO> getStackById(@PathVariable Long stackId) {
        return new ResponseEntity<>(stackMapper.stackToStackDTO(stackService.getStackById(stackId)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<StackDTO> getStackBySlug(@RequestParam String slug) {
        return new ResponseEntity<>(stackMapper.stackToStackDTO(stackService.getStackBySlug(slug)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StackDTO> createStack(@RequestBody Stack stack) {
        return new ResponseEntity<>(stackMapper.stackToStackDTO(stackService.createStack(stack)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StackDTO> updateStack(@PathVariable Long id, @RequestBody Stack stack) {
        return new ResponseEntity<>(stackMapper.stackToStackDTO(stackService.updateStack(id, stack)), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StackDTO> patchStack(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(stackMapper.stackToStackDTO(stackService.patchStack(id, updates)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStack(@PathVariable Long id) {
        stackService.deleteStack(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
