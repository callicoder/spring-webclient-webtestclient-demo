package com.example.webclientdemo;

import com.example.webclientdemo.payload.GithubRepo;
import com.example.webclientdemo.payload.RepoRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebclientDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	@Order(1)
	public void testCreateGithubRepository() {
		RepoRequest repoRequest = new RepoRequest("test-webclient-repository", "Repository created for testing WebClient");

		webTestClient.post().uri("/api/repos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(repoRequest), RepoRequest.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.name").isNotEmpty()
				.jsonPath("$.name").isEqualTo("test-webclient-repository");
	}

	@Test
	@Order(2)
	public void testGetAllGithubRepositories() {
		webTestClient.get().uri("/api/repos")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(GithubRepo.class);
	}

	@Test
	@Order(3)
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
	@Order(4)
	public void testEditGithubRepository() {
		RepoRequest newRepoDetails = new RepoRequest("updated-webclient-repository", "Updated name and description");
		webTestClient.patch()
				.uri("/api/repos/{repo}", "test-webclient-repository")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(newRepoDetails), RepoRequest.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.name").isEqualTo("updated-webclient-repository");
	}

	@Test
	@Order(5)
	public void testDeleteGithubRepository() {
		webTestClient.delete()
				.uri("/api/repos/{repo}", "updated-webclient-repository")
				.exchange()
				.expectStatus().isOk();
	}
}
