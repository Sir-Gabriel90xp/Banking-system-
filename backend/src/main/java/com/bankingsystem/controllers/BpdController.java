package com.bankingsystem.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.bpd.BpdAtmLocationResponse;
import com.bankingsystem.dto.bpd.BpdConfirmAccountRequest;
import com.bankingsystem.dto.bpd.BpdConfirmAccountResponse;
import com.bankingsystem.dto.common.ApiResponse;
import com.bankingsystem.services.BpdApiService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bpd")
@RequiredArgsConstructor
public class BpdController {

    private final BpdApiService bpdApiService;

    @PostMapping("/confirm-account")
    public ResponseEntity<ApiResponse<BpdConfirmAccountResponse>> confirmAccount(
            @Valid @RequestBody BpdConfirmAccountRequest request) {

        BpdConfirmAccountResponse response = bpdApiService.confirmAccount(request);
        return ResponseEntity.ok(ApiResponse.success("BPD account ownership checked successfully.", response));
    }

    @GetMapping("/atm-locations")
    public ResponseEntity<ApiResponse<List<BpdAtmLocationResponse>>> getAtmLocations(
            @RequestParam(defaultValue = "0") int page) {

        List<BpdAtmLocationResponse> locations = bpdApiService.getAtmLocations(page);
        return ResponseEntity.ok(ApiResponse.success("BPD ATM locations retrieved successfully.", locations));
    }
}
