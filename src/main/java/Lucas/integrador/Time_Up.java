package Lucas.integrador;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Time_Up extends AppCompatActivity {
    Button playAgainButton;
    TextView timeUpText;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time__up);
        //Inicializa o metodo.
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        timeUpText = (TextView)findViewById(R.id.timeUpText);
        player = MediaPlayer.create(Time_Up.this, R.raw.semtempo);
        player.start();

        //Botão jogar de novo.
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lucas.integrador.Time_Up.this, Lucas.integrador.MainGameActivity.class);
                startActivity(intent);
                finish();

            }
        });


        //Setting typefaces for textview and button - this will give stylish fonts on textview and button
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/shablagooital.ttf");
        timeUpText.setTypeface(typeface);
        playAgainButton.setTypeface(typeface);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
