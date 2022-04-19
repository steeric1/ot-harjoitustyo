package snake.domain;

public enum UserOperationResult {
    SUCCESS,
    NAME_TOO_SHORT {
        @Override
        public String toString() {
            return "Too short!";
        }
    },
    NAME_TAKEN {
        @Override
        public String toString() {
            return "This name is taken!";
        }
    },
    USER_NOT_FOUND {
        @Override
        public String toString() {
            return "Cannot find user!";
        }
    },
    INTERNAL_ERROR {
        @Override
        public String toString() {
            return "An internal error occurred...";
        }
    };
}
