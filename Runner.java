import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Runner extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 15;
    
    private int player1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int player2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballXSpeed = 3;
    private int ballYSpeed = 3;
    private int player1Score = 0;
    private int player2Score = 0;

    public Runner() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    player1Y = Math.max(0, player1Y - 20);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    player1Y = Math.min(HEIGHT - PADDLE_HEIGHT, player1Y + 20);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player2Y = Math.max(0, player2Y - 20);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player2Y = Math.min(HEIGHT - PADDLE_HEIGHT, player2Y + 20);
                }
                repaint();
            }
        });

        Timer timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(30, player1Y, PADDLE_WIDTH, PADDLE_HEIGHT); // Player 1 paddle
        g.fillRect(WIDTH - 45, player2Y, PADDLE_WIDTH, PADDLE_HEIGHT); // Player 2 paddle
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE); // Ball
        
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Player 1: " + player1Score, 50, 30);
        g.drawString("Player 2: " + player2Score, WIDTH - 150, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Ball collision with top and bottom
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball collision with paddles
        if (ballX <= 45 && ballY + BALL_SIZE >= player1Y && ballY <= player1Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        } else if (ballX >= WIDTH - 60 && ballY + BALL_SIZE >= player2Y && ballY <= player2Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }

        // Ball out of bounds
        if (ballX < 0) {
            player2Score++;
            resetBall();
        } else if (ballX > WIDTH) {
            player1Score++;
            resetBall();
        }

        repaint();
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = -ballXSpeed; // Change direction
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        Runner game = new Runner();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
