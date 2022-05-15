package snake.domain;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Encapsulates a Snake game. Implements all functionality needed to render a
 * Snake game.
 */
public class Game {
    private final int size;
    private int direction; // 0 = up, 1 = right, 2 = down, 3 = left
    private final ArrayDeque<Integer> directionQueue;
    private final LinkedList<Position> positions;
    private Position food;
    private final Random rand;
    private boolean grow;
    private int score;
    
    public Game(int size) {
        this.size = size;
        this.directionQueue = new ArrayDeque<>();
        this.positions = new LinkedList<>();
        this.rand = new Random();
        this.score = 0;
        
        Position head = this.randomPosition(1);
        this.positions.addFirst(head);
        
        int[] initialDirections = { 3, 2, 1, 2, 1, 0, 3, 0 };
        int initialQuadrant = (head.x / (this.size / 2)) ^ (head.y / (this.size / 2) + 1);
        this.direction = initialDirections[initialQuadrant * 2 + this.rand.nextInt(2)];
        
        this.positions.addLast(this.getInitialTailPos());
        
        this.food = this.randomPosition(0);
        while (this.isPositionOnSnake(this.food)) {
            this.food = this.randomPosition(0);
        }
    }
    
    /**
     * Advance the game by one step. Moves the head to the current direction, grows
     * snake if it eats a piece of food.
     */
    public void step() {
        this.updateDirection();
        
        Position head = this.getNewHeadPosition();
        this.positions.addFirst(head);
        
        if (!grow) {
            this.positions.removeLast();
        } else {
            grow = false;
        }
        
        if (head.equals(this.food)) {
            this.score++;
            this.food = this.randomPosition(0);
            while (this.isPositionOnSnake(this.food)) {
                this.food = this.randomPosition(0);
            }
            
            this.grow();
        }
        
    }
    
    /**
     * @return Whether this game is over (the snake's head has bumped to its body).
     */
    public boolean isGameOver() {
        Position head = this.positions.getFirst();
        
        ListIterator<Position> it = this.positions.listIterator();
        it.next();
        while (it.hasNext()) {
            if (head.equals(it.next())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Grow the snake (during the next step).
     */
    public void grow() {
        this.grow = true;
    }

    public LinkedList<Position> getPositions() {
        return this.positions;
    }
    
    public Position getFoodPosition() {
        return this.food;
    }
    
    public void setFoodPosition(Position pos) {
        this.food = pos;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    /**
     * Add a direction to the direction queue. Upon every step of the game, the
     * first element of the queue is taken and will be set as the new direction.
     * 
     * @param direction The direction to enqueue. 0 = up, 1 = right, 2 = down, 3 = left
     */
    public void enqueueDirection(int direction) {
        if (direction < 0 || direction > 3 ||
                (this.directionQueue.isEmpty() && Math.abs(direction - this.direction) % 2 == 0)
                || (!this.directionQueue.isEmpty() &&
                Math.abs(direction - this.directionQueue.getLast()) % 2 == 0)) {
            return;
        }
        
        this.directionQueue.add(direction);
    }
    
    private Position getNewHeadPosition() {
        Position oldHead = this.positions.getFirst(), head = null;
        switch (this.direction) {
            case 0:
                head = new Position(oldHead.x, oldHead.y - 1);
                break;
            case 1:
                head = new Position(oldHead.x + 1, oldHead.y);
                break;
            case 2:
                head = new Position(oldHead.x, oldHead.y + 1);
                break;
            case 3:
                head = new Position(oldHead.x - 1, oldHead.y);
                break;
        }
        
        this.correctLimits(head);
        return head;
    }
    
    private Position randomPosition(int margin) {
        return new Position(
                margin + (this.rand.nextInt(this.size - margin * 2)),
                margin + (this.rand.nextInt(this.size - margin * 2))
        );
    }
    
    private Position getInitialTailPos() {
        Position head = this.positions.getFirst(), tail = null;
        switch (this.direction) {
            case 0:
                tail = new Position(head.x, head.y + 1);
                break;
            case 1:
                tail = new Position(head.x - 1, head.y);
                break;
            case 2:
                tail = new Position(head.x, head.y - 1);
                break;
            case 3:
                tail = new Position(head.x + 1, head.y);
                break;
        }
        
        return tail;
    }
    
    private boolean isPositionOnSnake(Position position) {
        for (ListIterator<Position> it = this.positions.listIterator(); it.hasNext();) {
            if (it.next().equals(position)) {
                return true;
            }
        }
        
        return false;
    }
    
    private void updateDirection() {
        if (!this.directionQueue.isEmpty()) {
            this.direction = this.directionQueue.pop();
        }
    }
    
    private void correctLimits(Position pos) {
        if (pos.x < 0) {
            pos.x = this.size - 1;
        }
        
        if (pos.x >= this.size) {
            pos.x = 0;
        }
        
        if (pos.y < 0) {
            pos.y = this.size - 1;
        }
        
        if (pos.y >= this.size) {
            pos.y = 0;
        }
    }
    
    /**
     * A position relating to a snake game. A pair of an x and a y coordinate.
     */
    public static class Position {
        /**
         * The x component of the position.
         */
        public int x;
        
        /**
         * The y component of the position.
         */
        public int y;
        
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        /**
         * Return a new position that is the current position, with having added
         * <code>addX</code> to the x coordinate and <code>addY</code> to the y
         * coordinate.
         * 
         * @param addX The amount to add on the x axis.
         * @param addY The amount to add on the y axis.
         * @return The newly calculated position.
         */
        public Position add(int addX, int addY) {
            return new Position(this.x + addX, this.y + addY);
        }
        
        /**
         * @return A text representation of this position.
         */
        @Override
        public String toString() {
            return "Position { x = " + this.x + ", y = " + this.y + " }";
        }
        
        /**
         * Test whether this Position object equals to another object. It equals
         * to another object if and only if the other object is an instance of
         * Position and it is true that <code>this.x == other.x && this.y == other.y</code>.
         * 
         * @param o The object to compare to.
         * @return Whether the objects are equal.
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Position)) {
                return false;
            }
            
            Position other = (Position) o;
            return this.x == other.x && this.y == other.y;
        }
    }
}