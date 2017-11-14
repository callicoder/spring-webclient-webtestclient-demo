package com.example.webclientdemo;

import com.example.webclientdemo.payload.GithubRepo;
import com.example.webclientdemo.payload.RepoRequest;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebclientDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void test1CreateGithubRepository() {
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
	public void test2GetAllGithubRepositories() {
		webTestClient.get().uri("/api/repos")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(GithubRepo.class);
	}

	@Test
	public void test3GetSingleGithubRepository() {
		webTestClient.get()
				.uri("/api/repos/{repo}", "test-webclient-repository")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

	@Test
	public void test4EditGithubRepository() {
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
	public void test5DeleteGithubRepository() {
		webTestClient.delete()
				.uri("/api/repos/{repo}", "updated-webclient-repository")
				.exchange()
				.expectStatus().isOk();
	}
}
