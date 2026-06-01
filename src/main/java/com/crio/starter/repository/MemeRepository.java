package com.crio.starter.repository;

import com.crio.starter.data.Meme;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<Meme, String> {

  boolean existsByNameAndCaptionAndUrl(String name, String caption, String url);

  List<Meme> findAllByOrderByIdDesc(Pageable pageable);
}