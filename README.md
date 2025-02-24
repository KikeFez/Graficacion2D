# Graficacion2D

Este proyecto en Java con Swing crea un juego simple donde un dinosaurio se mueve, salta, se agacha y recibe daño al colisionar con un objeto estático. A continuación, te explico los elementos clave:

Interfaz Gráfica (JPanel y JFrame)

Se usa JPanel para dibujar el fondo, el sprite del dinosaurio y los elementos de la interfaz.
JFrame se encarga de mostrar la ventana del juego.
Sprites y Animaciones

Se cargan imágenes (ImageIcon) para representar diferentes estados del dinosaurio (quieto, corriendo, saltando, agachado).
Se cambia la imagen del sprite según la acción realizada (ejemplo: dinoActual = dinoCorre1; al moverse).
Movimiento y Controles (KeyListener)

Se detectan las teclas de dirección para mover el dinosaurio:
Izquierda/Derecha: alterna entre dos imágenes para simular correr.
Arriba: inicia un salto.
Abajo: cambia el sprite a la posición agachada.
Se usa moveSprite(int dx, int dy) para actualizar la posición.
Gravedad y Caída

Se implementa un efecto de gravedad con applyGravity(), aumentando dy (velocidad de caída) progresivamente.
Al tocar el suelo, el personaje se detiene.
Colisiones y Daño

Se dibuja un cuadrado estático como obstáculo.
Se verifican colisiones con checkCollision(Rectangle r1, Rectangle r2).
Si hay colisión, el dinosaurio recibe un daño aleatorio, reduciendo su vida.
Barra de Vida y Daño Visual

Se dibuja una barra de vida en la parte superior.
Al recibir daño, se muestra un texto con la cantidad de daño recibido por unos instantes.
Bucle del Juego y Actualización

Un Timer de 20ms ejecuta applyGravity() para actualizar la caída y redibujar la pantalla.
