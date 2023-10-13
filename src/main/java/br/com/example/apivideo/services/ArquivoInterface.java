package br.com.example.apivideo.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import br.com.example.apivideo.config.FileModel;

public interface ArquivoInterface {

	FileModel uploadVideo(String path, MultipartFile file) throws IOException;

	InputStream getResource(String path, String fileName, int id) throws FileNotFoundException;

}
