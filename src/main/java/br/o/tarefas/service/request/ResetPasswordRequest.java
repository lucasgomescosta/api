package br.o.tarefas.service.request;

public record ResetPasswordRequest(
        String type,
        String value,
        boolean temporary) {}
