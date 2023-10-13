package br.com.example.apivideo.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.example.apivideo.config.FileModel;
import br.com.example.apivideo.dto.VideoRecord;
import br.com.example.apivideo.entities.Video;
import br.com.example.apivideo.exeception.RecursoNaoEncontrado;
import br.com.example.apivideo.exeception.ControllerException;
import br.com.example.apivideo.services.VideoInterface;
import jakarta.servlet.http.HttpServletResponse;
import br.com.example.apivideo.services.ArquivoInterface;

@RequestMapping("/api")
@RestController
public class VideoController {
	
	@Value("${project.video}")
	private String path;
	
	@Autowired
	private VideoInterface videoService;
	
	@Autowired
	private ArquivoInterface arquivoService;

	@PostMapping("/uploadVideo")
	public ResponseEntity<?> uploadVideo(@RequestParam(value = "video") MultipartFile arquivoVideo, @RequestParam(value = "titulo") String titulo, @RequestParam(value = "descricao") String descricao, @RequestParam(value = "tag") String tag) throws IOException{

		if (arquivoVideo.isEmpty() || titulo.isEmpty() || descricao.isEmpty() || tag.isEmpty()) {
			return ResponseEntity.badRequest().body("Todos os parâmetros são obrigatórios.");
		}

		Video video = new Video();
		video.setTitulo(titulo);
		video.setDescricao(descricao);
		video.setTag(tag);
		
		try {
			FileModel fileModel = arquivoService.uploadVideo(path, arquivoVideo);
			video.setNomeVideo(fileModel.getVideoNomeAquivo());

			VideoRecord videoResponse = videoService.salvarVideo(video);
			return new ResponseEntity<VideoRecord>(videoResponse, HttpStatus.OK);
		} catch (IOException e) {
			String errorMessage = "Erro ao processar o arquivo de vídeo: " + e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} catch (RecursoNaoEncontrado e) {
			ControllerException controllerException = new ControllerException(e.getCodigoErro(),
					e.getMensagemErro() + e.getMessage());
			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
		} 
	}

	//1. get Video by id ;
	@GetMapping("/getVideo/{id}")
	public ResponseEntity<?> getVideo(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		try {
			VideoRecord video = videoService.getVideosById(id);

			InputStream videoStream = arquivoService.getResource(path, video.nomeVideo(), id);
			byte[] videoBytes = StreamUtils.copyToByteArray(videoStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("video/mp4"));

			return ResponseEntity.ok()
					.headers(headers)
					.body(videoBytes);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			ControllerException controllerException = new ControllerException("504", "ID não encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(controllerException);
		}
	}

}
