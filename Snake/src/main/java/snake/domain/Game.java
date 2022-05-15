package snake.domain;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

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
    
    public void step() {
        this.updateDirection();
        
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
            this.grow = true;
        }
        
    }
    
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

    public LinkedList<Position> getPositions() {
        return this.positions;
    }
    
    public Position getFoodPosition() {
        return this.food;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public void enqueueDirection(int direction) {
        if (direction < 0 || direction > 3 ||
                (this.directionQueue.isEmpty() && Math.abs(direction - this.direction) % 2 == 0)
                || (!this.directionQueue.isEmpty() &&
                Math.abs(direction - this.directionQueue.getLast()) % 2 == 0)) {
            return;
        }
        
        this.directionQueue.add(direction);
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
    
    public static class Position {
        public int x;
        public int y;
        
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Position add(int addX, int addY) {
            return new Position(this.x + addX, this.y + addY);
        }
        
        @Override
        public String toString() {
            return "Position { x = " + this.x + ", y = " + this.y + " }";
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Position)) {
                return false;
            }
            
            Position other = (Position)o;
            return this.x == other.x && this.y == other.y;
        }
    }
}