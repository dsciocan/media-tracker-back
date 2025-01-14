package com.duroc.mediatracker.model.show_detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Creator(String name) {
}
