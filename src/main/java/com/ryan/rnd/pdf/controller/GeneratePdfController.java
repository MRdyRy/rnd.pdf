package com.ryan.rnd.pdf.controller;

import com.ryan.rnd.pdf.domain.SampleReq;
import com.ryan.rnd.pdf.service.PdfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/generate")
public class GeneratePdfController {

    @Autowired
    PdfService pdfService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generatePdf(@RequestBody SampleReq sampleReq, HttpServletResponse httpServletResponse){
        log.info("genereate pdf call : with request {}",sampleReq);
        return pdfService.generatePdf(sampleReq,httpServletResponse);
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/test/ios", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generatePdfIos(HttpServletResponse httpServletResponse){
        SampleReq sampleReq = SampleReq.builder()
                .fullName("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890")
                .price("1234565591,./'';!@#$%^&*()")
                .nationality("abcdefghijklmnopqrstuvwxyz")
                .position("=+-:")
                .build();
        log.info("genereate pdf using ios call : with request {}",sampleReq);
        return pdfService.generatePdf(sampleReq,httpServletResponse);
    }

}
