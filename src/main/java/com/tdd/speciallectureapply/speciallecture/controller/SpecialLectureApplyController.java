package com.tdd.speciallectureapply.speciallecture.controller;

import com.tdd.speciallectureapply.speciallecture.model.dto.request.SpecialLectureApplyRequest;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyStatusResponse;
import com.tdd.speciallectureapply.global.response.ApiResponse;
import com.tdd.speciallectureapply.speciallecture.service.SpecialLectureApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/special-lectures")
public class SpecialLectureApplyController {

    private final SpecialLectureApplyService specialLectureApplyService;

    @Autowired
    public SpecialLectureApplyController(SpecialLectureApplyService specialLectureApplyService) {
        this.specialLectureApplyService = specialLectureApplyService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<SpecialLectureApplyResponse>> applyForLecture(@RequestBody SpecialLectureApplyRequest request) {
//        SpecialLectureApplyResponse response = specialLectureService.applyForLecture(request);
//        return ResponseEntity.ok(ApiResponse.success(response));
        return null;
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<SpecialLectureApplyStatusResponse>> getLectureApplicationStatus(@RequestParam String userId, LocalDate date) {
        SpecialLectureApplyStatusResponse response = specialLectureApplyService.getLectureApplicationStatus(userId,date);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
