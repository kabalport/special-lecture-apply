package com.tdd.speciallectureapply.speciallecture.controller;

import com.tdd.speciallectureapply.speciallecture.model.dto.request.SpecialLectureApplyRequest;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyStatusResponse;
import com.tdd.speciallectureapply.global.response.ApiResponse;
import com.tdd.speciallectureapply.speciallecture.service.SpecialLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/special-lectures")
public class SpecialLectureController {

    private final SpecialLectureService specialLectureService;

    @Autowired
    public SpecialLectureController(SpecialLectureService specialLectureService) {
        this.specialLectureService = specialLectureService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<SpecialLectureApplyResponse>> applyForLecture(@RequestBody SpecialLectureApplyRequest request) {
//        SpecialLectureApplyResponse response = specialLectureService.applyForLecture(request);
//        return ResponseEntity.ok(ApiResponse.success(response));
        return null;
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<SpecialLectureApplyStatusResponse>> getLectureApplicationStatus(@RequestParam String userId) {
        SpecialLectureApplyStatusResponse response = specialLectureService.getLectureApplicationStatus(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
