package com.example.middle_roadmap.controller;
import com.example.middle_roadmap.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/sale")
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/uploadData")
    public ResponseEntity<Object> save() {
        return new ResponseEntity<>(saleService.uploadData(), HttpStatus.OK);
    }
}
