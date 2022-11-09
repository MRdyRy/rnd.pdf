package com.ryan.rnd.pdf.service;

import com.ryan.rnd.pdf.domain.SampleReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

import static com.ryan.rnd.pdf.util.CommonConstant.UTF_8;

@Slf4j
@Service
public class PdfService {

    @Autowired
    private ClassLoaderTemplateResolver classLoaderTemplateResolver;

    @Autowired
    private ITextRenderer iTextRenderer;

    public byte[] generatePdf(SampleReq sampleReq, HttpServletResponse httpServletResponse) {
        log.info("generate pdf run........!");
        byte[] pdf = null;
        try{
            log.info("Initializing .......!");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(classLoaderTemplateResolver);
            Context context = new Context();
            context.setVariable("data",sampleReq);
            String renderHtmlContent = templateEngine.process("old",context);
            String xHtml = converToXHtml(renderHtmlContent);

            String fileName = sampleReq.getFullName()+"_"+System.currentTimeMillis();
            File output = new File(fileName+".pdf");
            log.info("Intializing output path : {}",output.getPath());
            iTextRenderer.setDocumentFromString(xHtml);
            iTextRenderer.layout();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            iTextRenderer.createPDF(outputStream);
            pdf = outputStream.toByteArray();
            outputStream.close();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"");
            httpServletResponse.setHeader("Content-Filename", fileName + ".pdf");
            return pdf;
        }catch (Exception e){
            e.printStackTrace();
        }
        return pdf;
    }

    private String converToXHtml(String renderHtmlContent) throws UnsupportedEncodingException {
        log.info("convert to xhtml......!");
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(renderHtmlContent.getBytes(UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }


}
