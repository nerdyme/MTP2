package com.example.surbhi.sample1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Message1 extends BaseActionbar {


    public EditText msgvalue=null;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    Button b1 =null;
    String mymsg=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message1);

        msgvalue= (EditText)findViewById(R.id.msg1_value);
        b1 = (Button) findViewById(R.id.pick);

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        b1.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                mymsg = msgvalue.getText().toString();

                System.out.println("value of message" + mymsg);

                if(mymsg.equals(null) || mymsg.trim().equals("") ||mymsg.equals("") || mymsg.equalsIgnoreCase(" "))
                {
                    Toast.makeText(getApplicationContext(), "Please enter some message before sending", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(),ContactOptions.class);
                    i.putExtra("msg", mymsg);
                    startActivity(i);
                }
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    msgvalue.setText(result.get(0));
                }
                break;
            }

        }
    }
}

