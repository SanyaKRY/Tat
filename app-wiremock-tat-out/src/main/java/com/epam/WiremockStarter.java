package com.epam;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;

public class WiremockStarter {
    public static void main(String[] args) {
        WireMockServerRunner wireMockServerRunner = new WireMockServerRunner();
        wireMockServerRunner.run(args);
    }
}
