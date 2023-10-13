package br.com.example.apivideo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.example.apivideo.config.FileModel;

@Service
public class ArquivoService implements ArquivoInterface {

	@Override
	public FileModel uploadVideo(String path, MultipartFile file) throws IOException {
		FileModel fileModel = new FileModel();
		String fileName = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String finalName = randomId.concat(fileName.substring(fileName.indexOf(".")));

		String filePath = path + File.separator + finalName;

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		Files.copy(file.getInputStream(), Paths.get(filePath));

		fileModel.setVideoNomeAquivo(finalName);

		return fileModel;
	}

	@Override
	public InputStream getResource(String path, String fileName, int id) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
