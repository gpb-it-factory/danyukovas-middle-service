package middle.example.gpb.models;

import java.util.UUID;

public record Error(String message, String type, String code, UUID traceId) {}
