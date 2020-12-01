package com.example.parser;

import java.nio.file.Path;
import java.util.List;

public interface Parsable<T> {
    List<T> parse(Path file);
}
