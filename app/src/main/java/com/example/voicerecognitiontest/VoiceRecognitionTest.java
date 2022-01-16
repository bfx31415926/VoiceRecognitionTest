package com.example.voicerecognitiontest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;

import android.util.Log;
import android.widget.Toast;

public class VoiceRecognitionTest extends Activity implements OnClickListener
{

    private TextView mText;
    private TextView words;
    private SpeechRecognizer sr;
    private static final String TAG = "MyTag";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button speakButton = (Button) findViewById(R.id.btn_speak);
        mText = (TextView) findViewById(R.id.textView1);
        words = (TextView) findViewById(R.id.words);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
//            Toast.makeText(VoiceRecognitionTest.this, "onReadyForSpeech", Toast.LENGTH_SHORT).show();
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
//            Toast.makeText(VoiceRecognitionTest.this, "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
//            Toast.makeText(VoiceRecognitionTest.this, "onRmsChanged", Toast.LENGTH_SHORT).show();
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
//            Toast.makeText(VoiceRecognitionTest.this, "onBufferReceived", Toast.LENGTH_SHORT).show();
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
//            Toast.makeText(VoiceRecognitionTest.this, "onEndofSpeech", Toast.LENGTH_SHORT).show();
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            mText.setText("error " + error);
            String errS;
            if(error == 2)
                errS = "Отсутствует Интернет-соединение. Включите его!";
            else if(error == 7)
                errS = "Речь не распознана";
            else
                errS = "Проблемы речевого ввода";

            Toast.makeText(VoiceRecognitionTest.this, errS, Toast.LENGTH_SHORT).show();

        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            mText.setText("results: "+String.valueOf(data.size()));
            words.setText(str);
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
//            Toast.makeText(VoiceRecognitionTest.this, "onPartialResults", Toast.LENGTH_SHORT).show();
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
//            Toast.makeText(VoiceRecognitionTest.this, "onEvent " + eventType, Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.example.voicerecognitiontest");

            String languagePref = "ru_RU";
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
            Log.i("111111","11111111");
        }
    }
}