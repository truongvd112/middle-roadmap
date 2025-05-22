package com.example.middle_roadmap.controller;
import com.example.middle_roadmap.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SaleController.class);

    @PostMapping("/uploadData")
    public ResponseEntity<Object> save() {
        return new ResponseEntity<>(saleService.uploadData(), HttpStatus.OK);
    }

    @GetMapping("/test-log")
    public String testLog() {
        logger.info("Demo log for ELK integration");
        return "Logged to ELK!";
    }
}
