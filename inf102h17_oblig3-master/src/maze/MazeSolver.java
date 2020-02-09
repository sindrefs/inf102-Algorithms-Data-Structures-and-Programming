package maze;

import graphics.Svg;

import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.*;

/**
 * Created by knutandersstokke on 28 28.10.2017.
 */
public class MazeSolver {

	private final static String MAZE_FILE = "maze/maze.txt"; // or
	// maze.txt

	/**
	 * Solves the maze
	 * 
	 * @return A list of points showing where the player needs to go to reach
	 *         the end. The points should be in the correct order, meaning the
	 *         first point in the list should be next to the player.
	 */
	public static List<Point> solve(Maze m) {

		int graphSize = m.getHeight() * m.getWidth();
		// System.out.println(graphSize);
		Graph graph = new Graph(graphSize);

		// Adding connections to all possible neighbors for all not-edge-points
		for (int y = 0; y < m.getHeight(); y++)
			for (int x = 0; x < m.getWidth(); x++) {
				if (!m.getWalls().contains(new Point(x, y))) {

					List<Integer> connections = new ArrayList<>();
					for (int i : graph.adj(x + m.getWidth() * y))
						connections.add(i);

					if ((x + 1) < m.getWidth())
						if (!m.getWalls().contains(new Point(x + 1, y)))
							if (!connections.contains(x + m.getWidth() * y + 1))
								graph.addEdge(x + m.getWidth() * y, x + m.getWidth() * y + 1);

					if ((x - 1) >= 0)
						if (!m.getWalls().contains(new Point(x - 1, y)))
							if (!connections.contains(x + m.getWidth() * y - 1))
								graph.addEdge(x + m.getWidth() * y, x + m.getWidth() * y - 1);

					if ((y + 1) < m.getHeight())
						if (!m.getWalls().contains(new Point(x, y + 1)))
							if (!connections.contains(x + m.getWidth() * (y + 1)))
								graph.addEdge(x + m.getWidth() * y, x + m.getWidth() * (y + 1));

					if (y - 1 >= 0)
						if (!m.getWalls().contains(new Point(x, y - 1)))
							if (!connections.contains(x + m.getWidth() * (y - 1)))
								graph.addEdge(x + m.getWidth() * y, x + m.getWidth() * (y - 1));

				}

			}

		// Initializing start and end point
		int player = m.getPlayer().getX() + m.getPlayer().getY() * m.getWidth();
		int goal = m.getGoal().getX() + m.getGoal().getY() * m.getWidth();

		List<Point> points = new ArrayList<>();

		// Goes through all steps
		for (Integer i : bestPath(graph, player, goal)) {
			points.add(new Point(i % m.getWidth(), i / m.getWidth()));
			System.out.println("x: " + i % m.getWidth() + ", y: " + i / m.getWidth());
		}
		points.remove(new Point(m.getPlayer().getX(), m.getPlayer().getY()));
		return points;
	}

	// Standard algorithm for bfs
	public static Iterable<Integer> bestPath(Graph graph, int p, int g) {
		boolean[] marked = new boolean[graph.V()];
		int[] edgeTo = new int[graph.V()];
		int[] distTo = new int[graph.V()];
		Queue<Integer> q = new Queue<Integer>();

		distTo[p] = 0;
		marked[p] = true;
		q.enqueue(p);

		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : graph.adj(v)) {
				if (!marked[w]) {
					distTo[w] = distTo[v] + 1;
					edgeTo[w] = v;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}

		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = g; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;

	}

	public static void main(String[] args) {
		Maze maze = new Maze(MAZE_FILE);
		String finalHtmlContent = Svg.buildSvgFromMaze(maze);
		Svg.runSVG(finalHtmlContent);
	}
}
