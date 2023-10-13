package br.com.example.apivideo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.apivideo.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
