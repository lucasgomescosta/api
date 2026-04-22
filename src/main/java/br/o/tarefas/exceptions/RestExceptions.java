package br.o.tarefas.exceptions;

/**
 * Exceções customizadas para a API de Tarefas
 */
public class RestExceptions {

    /**
     * Exceção para quando um recurso não é encontrado
     */
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }

        public ResourceNotFoundException(String resource, Object id) {
            super(String.format("%s com ID %s não encontrado", resource, id));
        }
    }

    /**
     * Exceção para quando há conflito de dados (ex: duplicação)
     */
    public static class ResourceConflictException extends RuntimeException {
        public ResourceConflictException(String message) {
            super(message);
        }

        public ResourceConflictException(String resource, String field, Object value) {
            super(String.format("%s com %s '%s' já existe", resource, field, value));
        }
    }

    /**
     * Exceção para operações não permitidas
     */
    public static class OperationNotAllowedException extends RuntimeException {
        public OperationNotAllowedException(String message) {
            super(message);
        }

        public OperationNotAllowedException(String operation, String reason) {
            super(String.format("Operação '%s' não permitida: %s", operation, reason));
        }
    }

    /**
     * Exceção para dados inválidos
     */
    public static class InvalidDataException extends RuntimeException {
        public InvalidDataException(String message) {
            super(message);
        }

        public InvalidDataException(String field, String reason) {
            super(String.format("Campo '%s' inválido: %s", field, reason));
        }
    }

    /**
     * Exceção para problemas de negócio
     */
    public static class BusinessRuleException extends RuntimeException {
        public BusinessRuleException(String message) {
            super(message);
        }

        public BusinessRuleException(String rule, String details) {
            super(String.format("Regra de negócio violada - %s: %s", rule, details));
        }
    }

    /**
     * Exceção para problemas de integração externa
     */
    public static class ExternalServiceException extends RuntimeException {
        public ExternalServiceException(String message) {
            super(message);
        }

        public ExternalServiceException(String service, String error) {
            super(String.format("Erro no serviço externo '%s': %s", service, error));
        }
    }

    /**
     * Exceção para problemas de autenticação/autorização
     */
    public static class AuthenticationException extends RuntimeException {
        public AuthenticationException(String reason) {
            super("Erro de autenticação: " + reason);
        }
    }

    /**
     * Exceção para problemas de autorização
     */
    public static class AuthorizationException extends RuntimeException {
        public AuthorizationException(String message) {
            super(message);
        }

        public AuthorizationException(String resource, String action) {
            super(String.format("Acesso negado: não autorizado para %s em %s", action, resource));
        }
    }

    /**
     * Exceção para rate limiting
     */
    public static class RateLimitExceededException extends RuntimeException {
        public RateLimitExceededException(String message) {
            super(message);
        }

        public RateLimitExceededException(int limit, String window) {
            super(String.format("Limite de requisições excedido: %d por %s", limit, window));
        }
    }

    /**
     * Exceção para manutenção do sistema
     */
    public static class SystemMaintenanceException extends RuntimeException {
        public SystemMaintenanceException(String maintenanceWindow) {
            super("Sistema em manutenção. Disponível novamente em: " + maintenanceWindow);
        }
    }

    /**
     * Exceção para validação de estado
     */
    public static class InvalidStateException extends RuntimeException {
        public InvalidStateException(String message) {
            super(message);
        }

        public InvalidStateException(String resource, String currentState, String requiredState) {
            super(String.format("%s está em estado '%s', mas precisa estar em '%s'",
                    resource, currentState, requiredState));
        }
    }

    /**
     * Exceção para timeout de operações
     */
    public static class OperationTimeoutException extends RuntimeException {
        public OperationTimeoutException(String message) {
            super(message);
        }

        public OperationTimeoutException(String operation, long timeoutMs) {
            super(String.format("Operação '%s' excedeu o timeout de %d ms", operation, timeoutMs));
        }
    }

    /**
     * Exceção para recursos temporariamente indisponíveis
     */
    public static class ResourceTemporarilyUnavailableException extends RuntimeException {
        public ResourceTemporarilyUnavailableException(String message) {
            super(message);
        }

        public ResourceTemporarilyUnavailableException(String resource, String reason) {
            super(String.format("Recurso '%s' temporariamente indisponível: %s", resource, reason));
        }
    }
}
