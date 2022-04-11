package snake.domain;

public enum UserCreationResult {
    /**
     * The user was created successfully.
     */
    SUCCESS,
    /**
     * The username is too short.
     */
    NAME_TOO_SHORT {
        @Override
        public String toString() {
            return "Too short!";
        }
    },
    /**
     * This username is already taken.
     */
    NAME_TAKEN {
        @Override
        public String toString() {
            return "This name is taken!";
        }
    },
    /**
     * An internal error occurred while creating the user.
     */
    INTERNAL_ERROR {
        @Override
        public String toString() {
            return "An internal error occurred...";
        }
    };
}
