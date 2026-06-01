package com.crio.starter.controller;

import com.crio.starter.data.Meme;
import com.crio.starter.exchange.CreateMemeRequestDto;
import com.crio.starter.exchange.CreateMemeResponseDto;
import com.crio.starter.exchange.MemeResponseDto;
import com.crio.starter.repository.MemeRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memes")
public class MemeController {

  private final MemeRepository memeRepository;

  public MemeController(MemeRepository memeRepository) {
    this.memeRepository = memeRepository;
  }

  @GetMapping({"", "/"})
  public List<MemeResponseDto> getMemes() {
    List<Meme> memes = memeRepository.findAllByOrderByIdDesc(
        PageRequest.of(0, 100)
    );

    return memes.stream()
        .map(m -> new MemeResponseDto(
            m.getId(),
            m.getName(),
            m.getCaption(),
            m.getUrl()
        ))
        .collect(Collectors.toList());
  }

  @PostMapping({"", "/"})
  public ResponseEntity<CreateMemeResponseDto> createMeme(
      @RequestBody @Valid CreateMemeRequestDto req
  ) {

    if (isInvalid(req)) {
      return ResponseEntity.badRequest().build();
    }

    boolean dup = memeRepository.existsByNameAndCaptionAndUrl(
        req.getName(),
        req.getCaption(),
        req.getUrl()
    );

    if (dup) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    Meme saved = memeRepository.save(
        new Meme(null, req.getName(), req.getCaption(), req.getUrl())
    );

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateMemeResponseDto(saved.getId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MemeResponseDto> getMeme(@PathVariable String id) {
    return memeRepository.findById(id)
        .map(m -> ResponseEntity.ok(new MemeResponseDto(
            m.getId(),
            m.getName(),
            m.getCaption(),
            m.getUrl()
        )))
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  private boolean isInvalid(CreateMemeRequestDto req) {
    return req == null
        || req.getName() == null || req.getName().isBlank()
        || req.getCaption() == null || req.getCaption().isBlank()
        || req.getUrl() == null || req.getUrl().isBlank();
  }
}
