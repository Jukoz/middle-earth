package net.sevenstars.middleearth.network.handlers;

public record OnboardingReturnResult(Status status, int retryAfterMillis) {
    public OnboardingReturnResult {
        status = status == null ? Status.INTERNAL_ERROR : status;
        retryAfterMillis = Math.max(0, retryAfterMillis);
    }

    public static OnboardingReturnResult success() {
        return new OnboardingReturnResult(Status.SUCCESS, 0);
    }

    public static OnboardingReturnResult failure(Status status) {
        return new OnboardingReturnResult(status, 0);
    }

    public static OnboardingReturnResult retry(Status status, int retryAfterMillis) {
        return new OnboardingReturnResult(status, retryAfterMillis);
    }

    public enum Status {
        SUCCESS(0, false, "success"),
        NOT_READY(1, true, "not_ready"),
        RETRY_LATER(2, true, "retry_later"),
        INVALID_SESSION(3, false, "invalid_session"),
        DISABLED(4, false, "disabled"),
        TELEPORT_FAILED(5, true, "teleport_failed"),
        PERSISTENCE_FAILED(6, false, "persistence_failed"),
        INTERNAL_ERROR(7, false, "internal_error");

        private final int code;
        private final boolean retryable;
        private final String translationSuffix;

        Status(int code, boolean retryable, String translationSuffix) {
            this.code = code;
            this.retryable = retryable;
            this.translationSuffix = translationSuffix;
        }

        public int code() {
            return code;
        }

        public boolean retryable() {
            return retryable;
        }

        public String translationSuffix() {
            return translationSuffix;
        }

        public static Status fromCode(int code) {
            return switch (code) {
                case 0 -> SUCCESS;
                case 1 -> NOT_READY;
                case 2 -> RETRY_LATER;
                case 3 -> INVALID_SESSION;
                case 4 -> DISABLED;
                case 5 -> TELEPORT_FAILED;
                case 6 -> PERSISTENCE_FAILED;
                case 7 -> INTERNAL_ERROR;
                default -> INTERNAL_ERROR;
            };
        }
    }
}
