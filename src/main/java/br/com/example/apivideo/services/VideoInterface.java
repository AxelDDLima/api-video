package br.com.example.apivideo.services;

import java.util.List;

import br.com.example.apivideo.dto.VideoRecord;
import br.com.example.apivideo.entities.Video;

public interface VideoInterface {

	public VideoRecord salvarVideo(Video video);

	public VideoRecord getVideosById(Integer id);

	public List<VideoRecord> getAllVideos();

	public VideoRecord atualizarVideo(Video video, Integer id);

	public void deletarVideo(Integer id);

}
