package br.com.velsis.case_tecnico.infrastructure.external;

import br.com.velsis.case_tecnico.infrastructure.external.dto.GetViaCepAddressResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ViaCepClient {
    private final HttpClient httpClient;

    public ViaCepClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public GetViaCepAddressResponseDTO consultZipcode(String zipcode) {
        final String url = "https://viacep.com.br/ws" + zipcode + "/json";
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
