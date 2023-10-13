package br.com.example.apivideo.dto;

import java.util.Date;

import br.com.example.apivideo.entities.Video;

public record VideoRecord(Long id, String titulo, String descricao, String tag, String nomeVideo, Date dataUpdate, Date dataUltimaModificacao){

	public static VideoRecord fromVideo(Video video) {
        return new VideoRecord(video.getId(), video.getTitulo(), video.getDescricao(),
                               video.getTag(), video.getNomeVideo(), video.getDataUpdate(), video.getDataUltimaModificacao());
    }

}
