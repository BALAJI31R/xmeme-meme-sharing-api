package com.crio.starter.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "memes")
public class Meme {

  @Id
  private String id;        // MongoDB ObjectId as String

  private String name;
  private String caption;
  private String url;
}
