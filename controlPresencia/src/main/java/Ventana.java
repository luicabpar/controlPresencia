import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import static java.time.temporal.ChronoUnit.MINUTES;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
public class Ventana {

	public static void main(String[] args) {
		
		SimpleDateFormat format= new SimpleDateFormat ("dd-MM-yyyy HH:mm:ss");
		
		JFrame frame = new JFrame("Control de Presencia");              
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        
        JLabel fecha= new JLabel("Fecha: "+format.format(new Date()));
        fecha.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        JLabel usuarioConectado= new JLabel("Usuario: "+"Luis Caballero");
        usuarioConectado.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        JLabel presenciaObligatoria= new JLabel("Presencia Obligatoria Cumplida: "+"SI");
        presenciaObligatoria.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        JLabel saldoSemanal= new JLabel("Saldo Semanal: "+"00:32");
        saldoSemanal.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        JLabel diasIncumplidos= new JLabel("Dias incumplidos: "+"0");
        diasIncumplidos.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        JLabel saldoDia= new JLabel("Saldo Hoy: "+"32");
        saldoDia.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        String saldoHoy= "08:10";
        String jornada= "08:15";
        LocalTime saldoHoyLocalTime= LocalTime.parse(saldoHoy,DateTimeFormatter.ISO_TIME);
        LocalTime jornadaLocalTime= LocalTime.parse(jornada,DateTimeFormatter.ISO_TIME);
        
        Long minutosDiferencia= MINUTES.between(saldoHoyLocalTime, jornadaLocalTime);
        String horasRestantes= "";
        String minutosRestantes= "";
        if(minutosDiferencia<60){
        	horasRestantes= "0";
        	minutosRestantes= minutosDiferencia.toString();
        }
        else{
        	Long a = minutosDiferencia/60L;
        	Long b =minutosDiferencia%60L;
        	horasRestantes= a.toString();
        	minutosRestantes= b.toString();
        }
        
        
        
        JLabel restanteHoy= new JLabel("Te queda "+horasRestantes+" horas y "+minutosRestantes+" minutos");
        restanteHoy.setBorder(new EmptyBorder(5, 5, 5, 0));
        
        panel.add(fecha);
        panel.add(usuarioConectado);
        panel.add(presenciaObligatoria);
        panel.add(saldoSemanal);
        panel.add(diasIncumplidos);
        panel.add(saldoDia);
        panel.add(restanteHoy);
            
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        frame.setSize(300, 200); 
        frame.add(panel);
        frame.setVisible(true);   

	}
	
	public static void crearVentana(Map<String, String> mapaDatos){
		
		SimpleDateFormat format= new SimpleDateFormat ("dd-MM-yyyy HH:mm:ss");		
		
		JFrame frame = new JFrame("Control de Presencia");              
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        
        JLabel fecha= new JLabel("Fecha: "+format.format(new Date()));
        JLabel usuarioConectado= new JLabel("Usuario: "+mapaDatos.get("usuarioConectado"));
        JLabel presenciaObligatoria= new JLabel("Presencia Obligatoria Cumplida: "+mapaDatos.get("presenciaObligatoria"));
        JLabel saldoSemanal= new JLabel("Saldo Semanal: "+mapaDatos.get("saldoSemanal"));
        JLabel diasIncumplidos= new JLabel("Dias incumplidos: "+mapaDatos.get("diasIncumplidosSemana"));
        JLabel saldoDia= new JLabel("Saldo Hoy: "+mapaDatos.get("saldoDia"));
        JLabel restanteHoy= new JLabel(mapaDatos.get("restanteHoy"));
        
        panel.add(fecha);
        panel.add(usuarioConectado);
        panel.add(presenciaObligatoria);
        panel.add(saldoSemanal);
        panel.add(diasIncumplidos);
        panel.add(saldoDia);
        panel.add(restanteHoy);
            
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        frame.setSize(300, 150); 
        frame.add(panel);
        frame.setVisible(true); 
		
		
	}

}
