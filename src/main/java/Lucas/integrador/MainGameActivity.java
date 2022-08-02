package Lucas.integrador;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.support.v4.content.ContextCompat;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.List;

import static Lucas.integrador.R.color.colorPrimary;
import static Lucas.integrador.R.color.lightGreen;

public class MainGameActivity extends AppCompatActivity {
    Button buttonA, buttonB, buttonC, buttonD;
    TextView questionText, triviaQuizText, timeText, resultText, coinText;
    QuestionHelper questionHelper;
    Question currentQuestion;
    List<Question> list;
    int qid = 0;
    int timeValue = 30;
    int coinValue = 0;
    CountDownTimer countDownTimer;
    Typeface tb, sb;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);


        //Inicia as Variaveis
        questionText = (TextView) findViewById(R.id.triviaQuestion);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);
        triviaQuizText = (TextView) findViewById(R.id.triviaQuizText);
        timeText = (TextView) findViewById(R.id.timeText);
        resultText = (TextView) findViewById(R.id.resultText);
        coinText = (TextView) findViewById(R.id.coinText);


// Configurando fontes para visualização de texto e botões
// isso dará fontes estilosas em visualização de texto e botão, etc.
        tb = Typeface.createFromAsset(getAssets(), "fonts/Slackey-Regular.ttf");
        sb = Typeface.createFromAsset(getAssets(), "fonts/Slackey-Regular.ttf");
        triviaQuizText.setTypeface(sb);
        questionText.setTypeface(tb);
        buttonA.setTypeface(tb);
        buttonB.setTypeface(tb);
        buttonC.setTypeface(tb);
        buttonD.setTypeface(tb);
        timeText.setTypeface(tb);
        resultText.setTypeface(sb);
        coinText.setTypeface(tb);

        //database helper class
        questionHelper = new QuestionHelper(this);
        // Tornar db gravável
        questionHelper.getWritableDatabase();

        // Irá verificar se as questões, opções já foram adicionadas na tabela ou não
        // Se eles não forem adicionados, getAllOfTheQuestions () retornará uma lista de tamanho zero.

        if (questionHelper.getAllOfTheQuestions().size() == 0) {

        // Se não for adicionado, as opções questões na tabela
            questionHelper.allQuestion();
        }

        //Isso nos retornará uma lista de tipo de dados Com as perguntas.
        list = questionHelper.getAllOfTheQuestions();


        // Agora vamos embaralhar os elementos da lista para obter as perguntas aleatoriamente
          Collections.shuffle(list);

        //conterá a opção que são a até D e ans para id particular da resposta

        currentQuestion = list.get(qid);

        //Contador de tempo
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {

                //Logica que define o tempo
                timeText.setText(String.valueOf(timeValue) + "\"");

                //Com cada iteração diminui o tempo em 1 segundo
                timeValue -= 1;

                //Isso significa que o usuário está sem tempo, então onFinished será chamado após esta iteração.
                if (timeValue == -1) {

                    //Uma vez que o usuário está fora do tempo, defina o texto conforme o tempo acabou
                    resultText.setText(getString(R.string.timeup));

                    // Como o usuário está sem tempo, ele não poderá clicar em nenhum botão
                    // portanto, desabilitar todos os quatro botões de opções usando este método
                    disableButton();
                }
            }

            //Agora o usuário está sem tempo
            public void onFinish() {
                //Iremos conduzi-lo para o tempo esgotado usando o método abaixo
                timeUp();
            }
        }.start();

        //This method will set the que and four options
        updateQueAndOptions();


    }

    public void updateQueAndOptions() {

        //Este método irá definir texto para que e opções
        questionText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOptA());
        buttonB.setText(currentQuestion.getOptB());
        buttonC.setText(currentQuestion.getOptC());
        buttonD.setText(currentQuestion.getOptD());

        timeValue = 30;
        if(coinValue==0){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.perguntamil);
            player.start();
        }else if(coinValue==1000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta2mil);
            player.start();
        }else if(coinValue==2000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta3mil);
            player.start();
        }else if(coinValue==3000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta4mil);
            player.start();
        }else if(coinValue==4000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta5mil);
            player.start();
        }else if(coinValue==5000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta10mil);
            player.start();
        }else if(coinValue==10000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta20mil);
            player.start();
        }else if(coinValue==20000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta30mil);
            player.start();
        }else if(coinValue==30000){
            player = MediaPlayer.create(MainGameActivity.this, R.raw.pergunta1milhao);
            player.start();
        }

        player = MediaPlayer.create(MainGameActivity.this, R.raw.suspense);
        player.start();
        //Agora, uma vez que o usuário corrigiu, basta zerar o cronômetro de volta para outra pergunta - por cancelar e iniciar.
        countDownTimer.cancel();
        countDownTimer.start();

        //definir o valor do texto da moeda
        coinText.setText('$' + String.valueOf(coinValue));
        //Agora, uma vez que o usuário tem um incremento correto, o valor da moeda
        if(coinValue<5000){
            coinValue+=1000;
        }else if(coinValue==5000){
            coinValue+=5000;
        }else if(coinValue==10000){
            coinValue+=10000;
        }else if(coinValue==20000){
            coinValue+=10000;
        }else if(coinValue==30000){
            coinValue+=10000;
        }
    }

    //Onclick para o primeiro botão.
    public void buttonA(View view) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
            buttonA.setBackgroundResource(R.drawable.botao_correto);
            //Check if user has not exceeds the que limit
            if (qid < list.size() - 1) {


                // Agora desative todos os botões de opção, pois o usuário ans está correto, então
                // o usuário não será capaz de pressionar outro botão de opção após pressionar um botão da resposta.
                disableButton();

                //Mostra a caixa de diálogo que está correta.
                correctDialog();
            }
            // Se o usuário excedeu o limite de que, basta navegar para a atividade GameWon
            else {
                gameWon();

            }
        }
        //O usuário estando errado, chama o PlayAgain
        else {
            gameLostPlayAgain();
        }
    }

    //Onclick Botão b
    public void buttonB(View view) {
        if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())) {
            buttonB.setBackgroundResource(R.drawable.botao_correto);
            if (qid < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            gameLostPlayAgain();
        }
    }

    //Onclick botão C
    public void buttonC(View view) {
        if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
            buttonC.setBackgroundResource(R.drawable.botao_correto);
            if (qid < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            gameLostPlayAgain();
        }
    }

    //Onclick Botão D
    public void buttonD(View view) {
        if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())) {
            buttonD.setBackgroundResource(R.drawable.botao_correto);
            if (qid < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            gameLostPlayAgain();
        }
    }

    // Este método irá navegar da atividade atual para GameWon
    public void gameWon() {
        Intent intent = new Intent(this, Lucas.integrador.GameWon.class);
        startActivity(intent);
        player.release();
        player = MediaPlayer.create(MainGameActivity.this, R.raw.ganhou);
        player.start();
        finish();
    }

    // Este método é chamado quando o usuário está errado
    // este método irá navegar o usuário para a atividade PlayAgain
    public void gameLostPlayAgain() {
        Intent intent = new Intent(this, Lucas.integrador.PlayAgain.class);
        startActivity(intent);
        player.release();
        //chama o som caso erra a pergunta.
        player = MediaPlayer.create(MainGameActivity.this, R.raw.quepena);
        player.start();
        finish();
    }

    // Este método é chamado quando o tempo acaba
    // este método irá navegar o usuário para a atividade Time_Up.
    public void timeUp() {
        Intent intent = new Intent(this, Time_Up.class);
        startActivity(intent);
        player.release();
        finish();
    }

    // Se o usuário pressionar o botão home e entrar no jogo da memória, isso
    // método continuará o cronômetro da hora anterior que saiu
    @Override
    protected void onRestart() {
        super.onRestart();
             countDownTimer.start();
    }


    // Quando a atividade é terminada, isso irá cancelar o cronômetro.
    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    //Pausa o tempo
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    //Volta a Home do app.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        player.release();
        finish();
    }

    //Esta caixa de diálogo é mostrada ao usuário após ele corrigir.
    public void correctDialog() {
        final Dialog dialogCorrect = new Dialog(Lucas.integrador.MainGameActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        dialogCorrect.setCancelable(false);
        player.release();
        player = MediaPlayer.create(MainGameActivity.this, R.raw.certaresposta);
        player.start();
        dialogCorrect.show();

        //Uma vez que a caixa de diálogo é exibida para o usuário, basta pausar o cronômetro em segundo plano
        onPause();


        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        Button buttonNext = (Button) dialogCorrect.findViewById(R.id.dialogNext);

        correctText.setTypeface(sb);
        buttonNext.setTypeface(sb);

        //OnCLick que espera para ir para o proxima questão.
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Isso irá dispensar a caixa de diálogo
                dialogCorrect.dismiss();
                //incremento do id da pergunta
                qid++;
                //Pega as perguntas e respostas.
                currentQuestion = list.get(qid);
                //Define as respostas.
                updateQueAndOptions();
                //Reseta as cores dos botões.
                resetColor();
                //Habilita os botões.
                enableButton();
            }
        });
    }

    //Este método tornará a cor do botão Vermelho novamente, já que a cor de nosso botão mudou para verde,
    //na resposta certa.
    public void resetColor() {
        buttonA.setBackgroundResource(R.drawable.botao_show);
        buttonB.setBackgroundResource(R.drawable.botao_show);
        buttonC.setBackgroundResource(R.drawable.botao_show);
        buttonD.setBackgroundResource(R.drawable.botao_show);
    }

    //Metodo que irá desativar os botões.
    public void disableButton() {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        player.release();
    }

    //Metodo que irá ativar os botões.
    public void enableButton() {
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }


}
