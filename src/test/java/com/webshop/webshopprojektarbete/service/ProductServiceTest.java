package com.webshop.webshopprojektarbete.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ProductService productService;


    @Test
    public void testHandleFileUploadSuccessful() throws IOException{
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test.img");

        String filePath = productService.handleFileUploadTARGET(multipartFile);

        assertTrue(filePath.endsWith(File.separator + "test.img"));
        assertFalse(filePath.endsWith((File.separator + 123)));
    }
    @Test
    public void testHandleFileUploadEmptyFile(){
        when(multipartFile.isEmpty()).thenReturn(true);

        String result = productService.handleFileUploadTARGET(multipartFile);

        assertEquals("File is empty", result);
    }
    @Test
    public void testHandleFileUploadIOException() throws IOException {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test.img");

        doThrow(IOException.class).when(multipartFile).transferTo(any(File.class));

        String result = productService.handleFileUploadTARGET(multipartFile);

        assertEquals("Failed to upload file", result);
    }



}
