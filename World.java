package life;

import java.util.Random;

class World {

    private boolean[][] state;
    private int generation = 0;
    private int population;
    private final int WIDTH;
    private final int HEIGHT;


    public World(int height, int width) {
        this.WIDTH = width;
        this.HEIGHT = height;
        state = new boolean[HEIGHT][WIDTH];
        final Random GENERATOR = new Random();
        for (boolean[] row : state) {
            for (int i = 0; i < WIDTH; i++) {
                row[i] = GENERATOR.nextBoolean();
            }
        }
        calculatePopulation();
    }

    public int getPopulation(){
        return population;
    }

    public int getGeneration() { return generation; }

    public boolean[][] getState() {
        return state.clone();
    }

    public void calculatePopulation(){
        population = 0;
        for(boolean[] row : state) {
            for (boolean cell : row) {
                if (cell) population++;
            }
        }
    }

    public void nextGen() {
        boolean[][] next = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                next[i][j] = decideFate(i ,j);
            }
        }
        state = next;
        generation++;
        calculatePopulation();
    }

    //returns true if a cell survives or false if cell dies
    private boolean decideFate(int y, int x) {

        int left = x - 1 < 0 ? WIDTH - 1 : x - 1;
        int right = x + 1 >= WIDTH ? 0 : x + 1;
        int up = y - 1 < 0 ? HEIGHT - 1 : y - 1;
        int down = y + 1 >= HEIGHT ? 0 : y + 1;

        int numberOfNeighbors = 0;

        if (state[up][left]) numberOfNeighbors++;
        if (state[up][x]) numberOfNeighbors++;
        if (state[up][right]) numberOfNeighbors++;

        if (state[y][left]) numberOfNeighbors++;
        if (state[y][right]) numberOfNeighbors++;

        if (state[down][left]) numberOfNeighbors++;
        if (state[down][x]) numberOfNeighbors++;
        if (state[down][right]) numberOfNeighbors++;

        boolean isLiving = state[y][x];
        return numberOfNeighbors == 3 || (numberOfNeighbors == 2 && isLiving);
    }
}
