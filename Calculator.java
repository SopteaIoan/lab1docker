import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.Stack;
import java.util.ArrayList;;
import java.util.List;

public class Calculator extends JFrame {
    JButton digits[] = {
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "),
            new JButton(" - "),
            new JButton(" * "),
            new JButton(" / "),
            new JButton(" = "),
            new JButton(" C "),
            new JButton(" ( "),
            new JButton(" ) ")
    };
    String oper_values[] = {"+", "-", "*", "/", "=", "", "(", ")"};

    String value;
    char operator;

    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setSize(300, 300);
        calculator.setTitle("calculator de buzunar");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i=0;i<10;i++)
            buttonpanel.add(digits[i]);

        for (int i=0;i<8;i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i=0;i<10;i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i=0;i<8;i++){
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (finalI == 5)
                        area.setText("");
                    else
                    if (finalI == 4) {
                        try {
                            String expresie= area.getText();
                            double rezultat= evaluarePoloneza(expresie);
                            area.append(" = " + rezultat);
                        } catch (Exception e) {
                            area.setText(" !!!Probleme!!! ");
                        }
                    }
                    else {
                        area.append(oper_values[finalI]);
                        operator = oper_values[finalI].charAt(0);
                    }
                }
            });
        }
    }

    public double evaluarePoloneza(String expresie)
    {
        List<String> lista= tok(expresie);
        Stack<Double> operanzi= new Stack<>();
        Stack<Character> operatori= new Stack<>();

        for(String element: lista)
        {
            char c=element.charAt(0);
            if(Character.isDigit(c))
                operanzi.push(Double.parseDouble(element));
            else if(c=='(')
                operatori.push(c);
            else if(c==')')
            {
                while(operatori.peek()!= '(')
                {
                    operanzi.push(calculeaza(operatori.pop(), operanzi.pop(), operanzi.pop()));
                }
                operatori.pop();
            }
            else
            {
                while(!operatori.isEmpty() && grad(operatori.peek())>=grad(c))
                {
                    operanzi.push(calculeaza(operatori.pop(),operanzi.pop(),operanzi.pop()));
                }
                operatori.push(c);
            }
        }

        while(!operatori.isEmpty())
        {
            operanzi.push(calculeaza(operatori.pop(),operanzi.pop(),operanzi.pop()));
        }
        return operanzi.pop();
    }

    public List<String> tok(String expresie){
        List<String> lista= new ArrayList<>();
        StringBuilder numar= new StringBuilder();

        for(int i=0; i<expresie.length(); i++)
        {
            char c= expresie.charAt(i);
            if(Character.isDigit(c))
                numar.append(c);
            else
            {
                if(numar.length()>0)
                {
                    lista.add(numar.toString());
                    numar.setLength(0);
                }

                lista.add(Character.toString(c));
            }
        }
        if(numar.length()>0)
            lista.add(numar.toString());
        return lista;
    }

    public double calculeaza(char op, double x2, double x1)
    {
        switch(op)
        {
            case '+': return x1+x2;
            case '-': return x1-x2;
            case '*': return x1*x2;
            case '/': return x1/x2;
            default: return 0;
        }
    }

    public int grad(char op)
    {
        if(op=='+' || op=='-')
            return 1;
        if(op=='*' || op=='/')
            return 2;
        return 0;
    }
}