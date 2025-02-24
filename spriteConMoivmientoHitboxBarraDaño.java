import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class spriteConMoivmientoHitboxBarraDaño extends JPanel implements KeyListener {
    private Image dinoQuieto, dinoAgachado1, dinoAgachado2, dinoSalta, dinoCorre1, dinoCorre2;
    private Image dinoIzquierda1, dinoIzquierda2;
    private Image dinoActual;
    private int x = 100, y = 300;
    private final int moveAmount = 5;
    private boolean corriendo = false;
    private boolean agachando = false;
    private boolean saltando = false;
    private int dy = 0;
    private BufferedImage background;

    private int vida = 100; // Vida inicial
    private int dañoRecibido = 0; // Daño recibido en la colisión
    private int dañoTotal = 0; // Daño total acumulado
    private boolean mostrarDaño = false; // Controla si se muestra el daño

    public spriteConMoivmientoHitboxBarraDaño() {
        // Cargar las imágenes de los sprites
        dinoQuieto = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino.png").getImage();
        dinoAgachado1 = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-down-run-1.png").getImage();
        dinoAgachado2 = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-down-run-2.png").getImage();
        dinoSalta = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-jump.png").getImage();
        dinoCorre1 = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-run-1.png").getImage();
        dinoCorre2 = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-run-2.png").getImage();
        dinoIzquierda1 = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-run-3.png").getImage();
        dinoIzquierda2 = new ImageIcon("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\dino-run-4.png").getImage();

        dinoActual = dinoQuieto;

        // Cargar imagen de fondo
        try {
            background = ImageIO.read(new File("C:\\Users\\Kike\\Documents\\NetBeansProjects\\java2D\\build\\classes\\banner.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setFocusable(true);
        addKeyListener(this);

        JFrame frame = new JFrame("Mover Sprite con Teclas");
        frame.add(this);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar el fondo de pantalla ajustado al tamaño del panel
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        // Dibujar el sprite en su posición actual
        g.drawImage(dinoActual, x, y, this);

        // Dibujar la hitbox del sprite (dino)
        Rectangle dinoHitbox = new Rectangle(x, y, dinoActual.getWidth(this), dinoActual.getHeight(this));
        g.setColor(Color.RED);
        g.drawRect(dinoHitbox.x, dinoHitbox.y, dinoHitbox.width, dinoHitbox.height);

        // Dibujar un cuadrado estático en el centro de la pantalla con su hitbox
        int squareSize = 100;
        int squareX = (getWidth() - squareSize) / 2;
        int squareY = (getHeight() - squareSize) / 2;
        Rectangle squareHitbox = new Rectangle(squareX, squareY, squareSize, squareSize);
        g.setColor(Color.BLUE);
        g.fillRect(squareX, squareY, squareSize, squareSize);

        // Dibujar la hitbox del cuadrado
        g.setColor(Color.GREEN);
        g.drawRect(squareHitbox.x, squareHitbox.y, squareHitbox.width, squareHitbox.height);

        // Dibujar barra de vida
        g.setColor(Color.BLACK);
        g.drawRect(50, 20, 200, 20);
        g.setColor(Color.RED);
        g.fillRect(51, 21, vida * 2 - 2, 18);

        // Mostrar daño recibido y daño total
        g.setColor(Color.ORANGE);
        g.drawString("Daño total: " + dañoTotal, 50, 60);

        if (mostrarDaño) {
            g.drawString("-" + dañoRecibido, squareX + squareSize / 2, squareY - 10);
        }

        // Verificar colisión
        if (checkCollision(dinoHitbox, squareHitbox)) {
            if (vida > 0) {
                dañoRecibido = (int) (Math.random() * 10) + 1;
                dañoTotal += dañoRecibido; // Sumar el daño recibido al daño total
                vida -= dañoRecibido;
                vida = Math.max(0, vida);
                mostrarDaño = true;

                // Ocultar daño después de un momento
                new Timer(500, e -> {
                    mostrarDaño = false;
                    repaint();
                }).start();
            }
        }
    }

    public void moveSprite(int dx, int dy) {
        x += dx;
        y += dy;
        repaint();
    }

    public void applyGravity() {
        if (saltando) {
            dy += 1; // Aumentar la velocidad de caída por gravedad
            moveSprite(0, dy);
            if (y >= 300) { // Volver al suelo
                y = 300;
                saltando = false;
                dy = 0; // Detener la caída
            }
        }
    }

    public boolean checkCollision(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN -> {
                agachando = !agachando;
                dinoActual = agachando ? dinoAgachado1 : dinoAgachado2;
                moveSprite(0, moveAmount);
            }
            case KeyEvent.VK_UP -> {
                if (!saltando) {
                    saltando = true;
                    dinoActual = dinoSalta;
                    dy = -10;
                    moveSprite(0, dy);
                }
            }
            case KeyEvent.VK_RIGHT -> {
                corriendo = !corriendo;
                dinoActual = corriendo ? dinoCorre1 : dinoCorre2;
                moveSprite(moveAmount, 0);
            }
            case KeyEvent.VK_LEFT -> {
                corriendo = !corriendo;
                dinoActual = corriendo ? dinoIzquierda1 : dinoIzquierda2;
                moveSprite(-moveAmount, 0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!saltando) {
            dinoActual = dinoQuieto;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            spriteConMoivmientoHitboxBarraDaño panel = new spriteConMoivmientoHitboxBarraDaño();
            Timer timer = new Timer(20, e -> panel.applyGravity());
            timer.start();
        });
    }
}
