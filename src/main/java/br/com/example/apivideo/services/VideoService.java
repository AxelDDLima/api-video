package br.com.example.apivideo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.example.apivideo.dto.VideoRecord;
import br.com.example.apivideo.entities.Video;
import br.com.example.apivideo.exeception.RecursoNaoEncontrado;
import br.com.example.apivideo.repositories.VideoRepository;

@Service
public class VideoService implements VideoInterface {

	@Autowired
	private VideoRepository videoRepository;

	@Override
	public VideoRecord salvarVideo(Video video) {
		if (video.getTitulo().isEmpty()) {
			throw new RecursoNaoEncontrado("402", "Por favor preencha os dados obrigatórios");
		}
		try {
			video.setDataUpdate(new Date());
			video.setDataUltimaModificacao(new Date());
			Video savedVideo = videoRepository.save(video);
			return VideoRecord.fromVideo(savedVideo);
		} catch (IllegalArgumentException i) {
			throw new RecursoNaoEncontrado("401", "A data esta vazia!");
		} catch (Exception e) {
			throw new RecursoNaoEncontrado("401", "Aconteu algo inesperado: " + e.getMessage());
		}

	}

	@Override
	public VideoRecord getVideosById(Integer id) {
			Video video = videoRepository.findById(id.longValue()).orElseThrow();
        return VideoRecord.fromVideo(video);
	}

	@Override
	public List<VideoRecord> getAllVideos() {
		List<VideoRecord> listaVideos = null;
		try {
			List<Video> listaVideoRepository = videoRepository.findAll();
			if (!listaVideoRepository.equals(null)) {
				for (Video video : listaVideoRepository) {
					listaVideos.add(VideoRecord.fromVideo(video));
				}
			}
			return listaVideos;
		} catch (Exception e) {
			throw new RecursoNaoEncontrado("404", "Desculpe! " + e.getMessage());
		}
	}

	@Override
	public VideoRecord atualizarVideo(Video video, Integer id) {
		Video videoRepositoryOld = videoRepository.findById(id.longValue()).orElseThrow(() -> new RecursoNaoEncontrado("501", "ID não existe"));

		videoRepositoryOld.setTitulo(video.getTitulo());
		videoRepositoryOld.setDescricao(video.getDescricao());
		videoRepositoryOld.setTag(video.getTag());
		videoRepositoryOld.setDataUltimaModificacao(new Date());
		
		videoRepository.save(video);
		
		return VideoRecord.fromVideo(video);
	}

	@Override
	public void deletarVideo(Integer id) {
		Video video = videoRepository.findById(id.longValue())
				.orElseThrow(() -> new RecursoNaoEncontrado("403", "video ID not found"));
		videoRepository.delete(video);
	}

}
