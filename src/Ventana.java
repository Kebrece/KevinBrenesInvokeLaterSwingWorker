//Autor: Kevin Brenes Cerdas Grupo 26

//Este ejemplo consiste en una ventana que ejecuta 2 acciones de manera simultanea
//La primera accion muestra el "estado de la bateria" de un ordenador portatil y
//conforme vaya avanzando el tiempo y el porcentaje llegue a un punto bajo de carga esta va
//a lanzar un  mensaje avisando que es necesario conectar el ordenador a su cargador.
//Esta accion fue ejecutada por medio del SwingUtilities.InvokeLater.
//------------------------------------------------------------------
//La segunda accion consiste en la supuesta instalacion de un programa por medio de un
//SwingWorker mostrando por medio de una barra de progreso el avance que lleva asi como un
//espacio donde nos va avisando de los "complementos que han sido instalados".
//-------------------------------------------------------------------------
//SwingUtilities.InvokeLater: Este metodo nos ayuda a usar el Event Threat para poder ejecutar una
//accion en especifico, como lo es mostrar el porcentaje de la bateria. Ademas, utilizando el
//SwingUtilities.InvokeLater nos aseguraremos que el Event Threat sea el hilo que va a interactuar
//directamente con la GUI para evitar posibles errores a futuro. Es mas que todo una medida de seguridad.
//--------------------------------------------------------------------------------------
//SwingWorker: Se utiliza cuando se quiere realizar tareas de larga duracion de manera simultanea
//al Event Threat nada mas que se debe crear un hilo por aparte a este para lograr dicho fin, ya que
//de no ser asi el programa no va a funcionar. En este caso la ventana se puede llegar a congelar,
//generando incluso que no se pueda cerrar.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame{
    private JButton buttonInstalador;
    private JTextArea textArea1;
    private JProgressBar progressBar1;
    private JTextArea textArea2;
    private JPanel panel;
    private int porcentajeBateria = 100;


    public void go()
    {
        setTitle("Ejemplo InvokeLater y SwingWorker");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponents(getContentPane());
        setVisible(true);

        buttonInstalador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new SwingWorker()//Crea un hilo extra al del evento para operaciones extensas
                                 // trabajando simultanea e independientemente del hilo del evento
                {

                    @Override
                    protected Object doInBackground() throws Exception {
                        //ponemos los valores maximos y minimos de la barra
                        progressBar1.setMinimum(0);
                        progressBar1.setMaximum(100);
                        textArea1.setText("");
                        //creamos un for para la instalacion de los complementos
                        for (int i = 1; i <= 100; i++) {
                            textArea1.append("\nInstalando  complemento numero: " + i);
                            textArea1.append("");
                            progressBar1.setValue(i);
                            Thread.sleep(200);
                        }
                        return null;
                    }
                    @Override
                    protected void done() {
                        JOptionPane.showMessageDialog(null, "Se ha terminado la instalacion");
                    }
                }.execute();
            }
        });

        for(int i = 10; i>0; i--)
        {
            try
            {
                Thread.sleep(2000);
            }catch (InterruptedException ex)
            {}
            SwingUtilities.invokeLater(new Runnable() {//Trabaja con el hilo del evento de manera segura
                @Override
                public void run() {
                    textArea2.append("\nPorcentaje de la bateria: "+porcentajeBateria+"%\n");
                    porcentajeBateria = porcentajeBateria-10;
                }

            });
        }
        JOptionPane.showMessageDialog(null, "Nivel de bateria bajo, favor conectar el ordenador");
    }


    private void addComponents(Container contentPane)//Agrega los componentes botones,
                                                    // barra de progreso etc, al panel principal
    {
        contentPane.add(panel);
    }

    public static void main(String[] args)//Funcion main
    {
        new Ventana().go();
    }
}
