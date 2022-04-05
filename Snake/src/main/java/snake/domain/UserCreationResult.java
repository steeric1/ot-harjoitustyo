package snake.domain;

public enum UserCreationResult {
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
    INTERNAL_ERROR {
        @Override
        public String toString() {
            return "An internal error occurred...";
        }
    };
}
