import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Task4 
{
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static ArrayList<Question> questions = new ArrayList<>();
    private static JFrame frame;
    private static JLabel timerLabel;
    private static int timeRemaining;
    private static Timer timer;
    private static JPanel questionPanel;  

    public static void main(String[] args) 
    {
        // New OS questions
        questions.add(new Question("What does OS stand for?", new String[]{"Operating System", "Open Source", "Office Software", "Online Service"}, "Operating System"));
        questions.add(new Question("Which of these is a Unix-based OS?", new String[]{"Windows", "MacOS", "Linux", "Android"}, "MacOS"));
        questions.add(new Question("Which company develops the Windows OS?", new String[]{"Apple", "Microsoft", "Google", "IBM"}, "Microsoft"));
        questions.add(new Question("What is the main function of an OS?", new String[]{"To run applications", "To manage hardware resources", "To provide Internet access", "To enhance graphics"}, "To manage hardware resources"));
        questions.add(new Question("Which OS is known for its open-source nature?", new String[]{"Windows", "Linux", "MacOS", "iOS"}, "Linux"));
        questions.add(new Question("Which of the following is a mobile OS?", new String[]{"Ubuntu", "Windows", "Android", "Debian"}, "Android"));
        questions.add(new Question("Which OS is primarily used in servers?", new String[]{"Windows", "Linux", "MacOS", "Chrome OS"}, "Linux"));
        questions.add(new Question("Which OS is developed by Apple?", new String[]{"Android", "Linux", "iOS", "Windows"}, "iOS"));
        questions.add(new Question("What is the purpose of device drivers in an OS?", new String[]{"To increase processing speed", "To manage software updates", "To control hardware components", "To enhance security"}, "To control hardware components"));
        questions.add(new Question("What is a kernel in an operating system?", new String[]{"A software application", "The core part of the OS", "A type of computer virus", "An external storage device"}, "The core part of the OS"));

        frame = new JFrame("Math Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout());

        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.LIGHT_GRAY);
        timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        timerLabel = new JLabel("Time: 15");
        timerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        timerLabel.setOpaque(true);
        timerLabel.setBackground(Color.BLUE);
        timerLabel.setForeground(Color.WHITE);

        timerPanel.add(timerLabel);
        frame.add(timerPanel, BorderLayout.NORTH);

        questionPanel = new JPanel();
        questionPanel.setBackground(Color.LIGHT_GRAY);
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        frame.add(questionPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        startQuiz();
    }

    public static void startQuiz() 
    {
        displayQuestion(questions.get(currentQuestionIndex));
    }

    public static void displayQuestion(Question q) 
    {
        questionPanel.removeAll();

        JLabel questionLabel = new JLabel(q.getQuestion());
        questionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.add(questionLabel);

        ButtonGroup group = new ButtonGroup();
        for (String option : q.getOptions()) 
        {
            JRadioButton button = new JRadioButton(option);
            button.setActionCommand(option);
            button.setBackground(Color.WHITE);
            button.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            group.add(button);
            questionPanel.add(button);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setBackground(Color.GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> 
        {
            String userAnswer = group.getSelection().getActionCommand();
            submitAnswer(userAnswer, q);
            moveToNextQuestion();
        });
        questionPanel.add(submitButton);

        frame.revalidate();
        frame.repaint();

        startTimer();
    }

    public static void startTimer() 
    {
        timeRemaining = 15; 
        updateTimerLabel(); 
        if (timer != null) 
        {
            timer.cancel(); 
        }
        timer = new Timer();
        TimerTask task = new TimerTask() 
        {
            public void run() 
            {
                timeRemaining--;
                updateTimerLabel();
                if (timeRemaining <= 0) 
                {
                    timer.cancel();
                    moveToNextQuestion();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000); 
    }

    private static void updateTimerLabel() 
    {
        timerLabel.setText("Time: " + timeRemaining);
        if (timeRemaining > 10) 
        {
            timerLabel.setBackground(Color.GREEN);
        } 
        else if (timeRemaining > 5) 
        {
            timerLabel.setBackground(Color.YELLOW);
        } 
        else 
        {
            timerLabel.setBackground(Color.RED);
        }
    }

    public static void moveToNextQuestion() 
    {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) 
        {
            displayQuestion(questions.get(currentQuestionIndex));
        } 
        else 
        {
            showResults();
        }
    }

    public static void submitAnswer(String userAnswer, Question q) 
    {
        if (q.isCorrect(userAnswer)) 
        {
            score++;
            playSound("correct.wav");
            JOptionPane.showMessageDialog(frame, "Great job!", "Correct", JOptionPane.INFORMATION_MESSAGE);
        } 
        else 
        {
            playSound("incorrect.wav");
            JOptionPane.showMessageDialog(frame, "Try again!", "Incorrect", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showResults() 
    {
        questionPanel.removeAll();
        JLabel resultLabel = new JLabel("Your final score is: " + score + " out of " + questions.size(), SwingConstants.CENTER);
        resultLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        resultLabel.setForeground(Color.BLUE);
        questionPanel.add(resultLabel);
        frame.revalidate();
        frame.repaint();
    }

    public static void playSound(String soundFile) 
    {
        // Implement sound playing logic using javax.sound.sampled package
        // For example: AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
    }
}

class Question 
{
    private String questionText;
    private String[] options;
    private String correctAnswer;

    public Question(String questionText, String[] options, String correctAnswer) 
    {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() 
    {
        return questionText;
    }

    public String[] getOptions() 
    {
        return options;
    }

    public boolean isCorrect(String answer) 
    {
        return correctAnswer.equals(answer);
    }
}
