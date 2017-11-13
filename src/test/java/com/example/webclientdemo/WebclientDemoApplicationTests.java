package com.example.webclientdemo;

import com.example.webclientdemo.payload.GithubRepo;
import com.example.webclientdemo.payload.RepoRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebclientDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testCreateGithubRepository() {
		RepoRequest repoRequest = new RepoRequest("test-webclient-repository", "Repository created for testing WebClient");

		webTestClient.post().uri("/api/repos")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(repoRequest), RepoRequest.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.name").isNotEmpty()
				.jsonPath("$.name").isEqualTo("test-webclient-repository");
	}

	@Test
	public void testGetAllGithubRepositories() {
		webTestClient.get().uri("/api/repos")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(GithubRepo.class);
	}

	@Test
	public void testGetSingleGithubRepository() {
		webTestClient.get()
				.uri("/api/repos/{repo}", "test-webclient-repository")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

	@Test
	public void testEditGithubRepository() {
		RepoRequest newRepoDetails = new RepoRequest("updated-webclient-repository", "Updated name and description");
		webTestClient.patch()
				.uri("/api/repos/{repo}", "test-webclient-repository")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(newRepoDetails), RepoRequest.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.name").isEqualTo("updated-webclient-repository");
	}

	@Test
	public void testDeleteGithubRepository() {
		webTestClient.delete()
				.uri("/api/repos/{repo}", "updated-webclient-repository")
				.exchange()
				.expectStatus().isOk();
	}
}
