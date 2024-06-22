package middle.example.gpb.models;

import java.util.UUID;

public record InnerErrorV2(String message, String type, String code, UUID traceId) {}