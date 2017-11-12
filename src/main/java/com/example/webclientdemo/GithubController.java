package com.example.webclientdemo;

import com.example.webclientdemo.config.AppProperties;
import com.example.webclientdemo.payload.RepoRequest;
import com.example.webclientdemo.payload.GithubRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Created by rajeevkumarsingh on 11/11/17.
 */
@RestController
@RequestMapping("/api")
public class GithubController {

    @Autowired
    private GithubClient githubClient;

    @Autowired
    private AppProperties appProperties;

    private static final Logger logger = LoggerFactory.getLogger(GithubController.class);

    @GetMapping("/repos")
    public Flux<GithubRepo> listRepositories() {
        return githubClient.listRepositories();
    }

    @PostMapping("/repos")
    public Mono<GithubRepo> createRepository(@RequestBody RepoRequest repoRequest) {
        return githubClient.createRepository(repoRequest);
    }

    @GetMapping("/repos/{repo}")
    public Mono<GithubRepo> getRepository(@PathVariable String repo) {
        return githubClient.getRepository(appProperties.getGithub().getUsername(), repo);
    }

    @PatchMapping("/repos/{repo}")
    public Mono<GithubRepo> editRepository(@PathVariable String repo, @Valid @RequestBody RepoRequest repoRequest) {
        return githubClient.editRepository(appProperties.getGithub().getUsername(), repo, repoRequest);
    }

    @DeleteMapping("/repos/{repo}")
    public Mono<Void> deleteRepository(@PathVariable String repo) {
        return githubClient.deleteRepository(appProperties.getGithub().getUsername(), repo);
    }


    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        logger.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
        return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
    }
}
